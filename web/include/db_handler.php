<?php
class DbHandler {
    private $conn;
    function __construct() {
        require_once dirname(__FILE__) . '/db_connect.php';
        // opening db connection
        $db = new DbConnect();
        $this->conn = $db->connect();
    }

	public function loginUser($name, $password, $fbtoken) {
        $response = array();
		if ($this->doesUserExists($name)) {
			$res = $this->getUserByName($name);
			if ($fbtoken != $res['fbtoken']) { // need to update firebase token
				$this->updateToken($res["user_id"], $fbtoken);
			}
			//$response["user"] = $res; // for debugging only!
			if ($password == $res['password']) {
				$response["error"] = false;
				$response["user_id"] = $res["user_id"];
				$response["token"] = $res['token'];
			}
			else {
				$response["error"] = true;
				$response["message"] = "Wrong password";
			}
		} else {
			$response["error"] = true;
			$response["message"] = "Wrong username.";
		}
		return $response;
    }
	
	public function updateToken($uid, $fbtoken) {
		if ($this->doesUserExistsById($uid)) {
			$response = array();
			$stmt = $this->conn->prepare("UPDATE users SET firebase_token = ? WHERE user_id = ?");
			$stmt->bind_param("si", $fbtoken, $uid);
			$stmt->execute();
			$stmt->close();
			$response["error"] = false;
		}
		else {
			$response["error"] = true;
			$response["message"] = "Wrong UID: " . $uid; 
		}
		return $response;
	}
	
	public function joinFlat($uid, $code){
	  $response = array();
      if ($this->verifyFlatCode($code)) {
		  $stmt = $this->conn->prepare("SELECT flat_id FROM flats WHERE invitation_code = ?");
		  $stmt->bind_param("s", $code);
		  $flatId = -1;
          if ($stmt->execute()) {
            $stmt->bind_result($flatId);
			$stmt->fetch();
			$stmt->close();
		  }
		  $stmt = $this->conn->prepare("UPDATE users SET flat_id = ? WHERE user_id = ?");
		  $stmt->bind_param("si", $flatId, $uid);
		  $stmt->execute();
	      $stmt->close();
		  $response["error"] = false;
	  }
	  else {
		  $response["error"] = true;
		  $response["message"] = "Wrong invitation code.";
	  }
	  return $response;
    }

	public function getRents($flat) {
		$stmt = $this->conn->prepare("SELECT rent_value, per_person, rent_date FROM rent_history WHERE flat_id = ? ORDER BY rent_date DESC");
		$stmt->bind_param("s", $flat);
		$stmt->execute();
		$result = $stmt->get_result();
		$stmt->close();
		$rents=array();
		while ($rent = $result->fetch_assoc()) {
			array_push($rents, $rent);
		}
		return $rents;
	}

	public function addRent($uid, $flat, $value) {
		$stmt = $this->conn->prepare("SELECT user_id FROM users WHERE flat_id = ?");
		$stmt->bind_param("s", $flat);
		$stmt->execute();
		$stmt->store_result();
		$num_rows = $stmt->num_rows;
		$stmt->close();
		$per_person = $value/$num_rows;

		$stmt = $this->conn->prepare("INSERT INTO rent_history(flat_id, rent_value, per_person, rent_date) values(?, ?, ?, now())");
		$stmt->bind_param("sss", $flat, $value, $per_person);
		$result = $stmt->execute();
		$stmt->close();
        if ($result) {
			$response["error"] = false;
			$stmt = $this->conn->prepare("SELECT firebase_token FROM users WHERE flat_id = ? AND firebase_token <> 'empty' AND user_id <> ?");
			$stmt->bind_param("ss", $flat, $uid);
			$stmt->execute();
			$result = $stmt->get_result();
			$stmt->close();
			$tokens=array();
			while ($token = $result->fetch_assoc()) {
				array_push($tokens, $token["firebase_token"]);
			}
			$msg =
				 [
					'message'   => $value . "(na glowe ". $per_person . ")",
					'title'   => "Czynsz w tym miesiacu",
					'chann_id' 	=> 2
				 ];

				 $fields =
				 [
					'registration_ids'  => $tokens,
					'data'      => $msg
				 ];

				 $headers =
				 [
				   'Authorization: key=' . FIREBASE_API_KEY,
				   'Content-Type: application/json'
				 ];

				 $ch = curl_init();
				 curl_setopt( $ch,CURLOPT_URL, 'https://fcm.googleapis.com/fcm/send' );
				 curl_setopt( $ch,CURLOPT_POST, true );
				 curl_setopt( $ch,CURLOPT_HTTPHEADER, $headers );
				 curl_setopt( $ch,CURLOPT_RETURNTRANSFER, true );
				 curl_setopt( $ch,CURLOPT_SSL_VERIFYPEER, false );
				 curl_setopt( $ch,CURLOPT_POSTFIELDS, json_encode( $fields ) );
				 $result = curl_exec($ch );
				 curl_close( $ch );
				$response["fbresponse"] = $result;
		} else {
			$response["error"] = true;
		}

	    return $response;
	}

	public function addMessage($id, $message) {

		$stmt = $this->conn->prepare("INSERT INTO messages(flat_id, user_id, message_text) values((select flat_id from users where user_id = ? ), ?, ?)");
		$stmt->bind_param("sss", $id, $id, $message);
		$result = $stmt->execute();
		$stmt->close();
		if ($result) {
			$response["error"] = false;
		} else {
			$response["error"] = true;
		}
		if($response["error"] == false){

			$stmt = $this->conn->prepare("select firebase_token from users where flat_id = (select flat_id from users where user_id = ?) and user_id <> ?");
			$stmt->bind_param("ss", $id, $id);
			$stmt->execute();
			$result = $stmt->get_result();
			$stmt->close();
			$tokens=array();
			while ($token = $result->fetch_assoc()) {
				array_push($tokens, $token["firebase_token"]);
            }
			$title = "Nowa waiadomość";
			$notification = $message;
			$msg =

			[
			'message'   => $notification,
			'title'   => $title,
			'chann_id' 	=> 1
			];

			$fields =

			[
			'registration_ids'  => $tokens,
			'data'      => $msg
			];

			$headers =

			[
			'Authorization: key=' . FIREBASE_API_KEY,
			'Content-Type: application/json'
			];

			$ch = curl_init();
			curl_setopt( $ch,CURLOPT_URL, 'https://fcm.googleapis.com/fcm/send' );
			curl_setopt( $ch,CURLOPT_POST, true );
			curl_setopt( $ch,CURLOPT_HTTPHEADER, $headers );
			curl_setopt( $ch,CURLOPT_RETURNTRANSFER, true );
			curl_setopt( $ch,CURLOPT_SSL_VERIFYPEER, false );
			curl_setopt( $ch,CURLOPT_POSTFIELDS, json_encode( $fields ) );
			$result = curl_exec($ch );
			curl_close( $ch );

		}
		return $response;

	}


	public function getMessages($id, $token) {
        $stmt = $this->conn->prepare("SELECT m.message_text, m.message_date, m.user_id as user_id, u.name as name FROM messages m inner join users u on u.user_id = m.user_id WHERE m.flat_id=(select flat_id from users where user_id = ?) ORDER BY message_date");
		$stmt->bind_param("s", $id);
		$stmt->execute();
		$messages = $stmt->get_result();
        $stmt->close();
        return $messages;

    }
    private function doesUserExists($name) {
        $stmt = $this->conn->prepare("SELECT user_id from users WHERE login = ?");
        $stmt->bind_param("s", $name);
        $stmt->execute();
        $stmt->store_result();
        $num_rows = $stmt->num_rows;
        $stmt->close();
        return $num_rows > 0;
    }
	private function verifyFlatCode($code) {
        $stmt = $this->conn->prepare("SELECT flat_id from flats WHERE invitation_code = ?");
        $stmt->bind_param("s", $code);
        $stmt->execute();
        $stmt->store_result();
        $num_rows = $stmt->num_rows;
        $stmt->close();
        return $num_rows > 0;
    }
	private function doesUserExistsById($id) {
        $stmt = $this->conn->prepare("SELECT user_id from users WHERE user_id = ?");
        $stmt->bind_param("s", $id);
        $stmt->execute();
        $stmt->store_result();
        $num_rows = $stmt->num_rows;
        $stmt->close();
        return $num_rows > 0;
    }

	public function getUserByName($name) {
        $stmt = $this->conn->prepare("SELECT user_id, name, password, token, firebase_token FROM users WHERE login = ?");
        $stmt->bind_param("s", $name);
        if ($stmt->execute()) {
            $stmt->bind_result($id, $name, $password, $token, $fbtoken);
            $stmt->fetch();
            $user = array();
			$user["user_id"] = $id;
            $user["name"] = $name;
			$user["password"] = $password;
            $user["token"] = $token;
			$user["fbtoken"] = $fbtoken;
            $stmt->close();
            return $user;
        } else {
            return NULL;
        }
    }
    public function getUserByFlatId($flat_id){
      $stmt = $this->conn->prepare("SELECT user_id, flat_id, login, name, email, balance FROM users WHERE flat_id = ?");
      $stmt->bind_param("s", $flat_id);
      $stmt->execute();
      $result = $stmt->get_result();
      $stmt->close();
      $users = array();
      while($user = $result->fetch_assoc()){
        array_push($users, $user);
      }
      return $users;
    }

    public function getShoppingHistoryByFlatId($flat_id){
      $stmt = $this->conn->prepare("SELECT s.item_id, s.flat_id, name, s.item_name, s.price, s.purchase_date FROM shopping s join users using(user_id) WHERE s.flat_id = ?");
      $stmt->bind_param("s", $flat_id);
      $stmt->execute();
      $result = $stmt->get_result();
      $stmt->close();
      $items = array();
      while($item = $result->fetch_assoc()){
        array_push($items, $item);
      }
      return $items;
    }
}
?>