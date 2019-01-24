<?php
error_reporting(-1);
ini_set('display_errors', 'On');
require_once '../include/db_handler.php';
require '.././libs/Slim/Slim.php';
\Slim\Slim::registerAutoloader();
$app = new \Slim\Slim();
// User login
$app->post('/user/login', function() use ($app) {
    // check for required params
    verifyRequiredParams(array('login', 'password', 'firebasetoken'));
    // reading post params
    $login = $app->request->post('login');
    $password = $app->request->post('password');
	$fbtoken = $app->request->post('firebasetoken');

    $db = new DbHandler();
    $response = $db->loginUser($login, $password, $fbtoken);
    // echo json response
    echoRespnse(200, $response);
});

//sing up

$app->post('/user/singup', function() use ($app) {

    verifyRequiredParams(array('login', 'password', 'name','email'));
    $login = $app->request->post('login');
    $password = $app->request->post('password');
	$name = $app->request->post('name');
	$email = $app->request->post('email');

    $db = new DbHandler();
    $response = $db->singUp($login, $password, $name,$email);
    // echo json response
    echoRespnse(200, $response);
});

$app->post('/user/updatefbtoken', function() use ($app) {
    verifyRequiredParams(array('uid', 'firebasetoken'));
    // reading post params
    $uid = $app->request->post('uid');
	$fbtoken = $app->request->post('firebasetoken');

    $db = new DbHandler();
    $response = $db->updateToken($uid, $fbtoken);
    // echo json response
    echoRespnse(200, $response);
});

$app->post('/flat/join', function() use ($app) {
    verifyRequiredParams(array('uid', 'code'));
    // reading post params
    $uid = $app->request->post('uid');
	$code = $app->request->post('code');

    $db = new DbHandler();
    $response = $db->joinFlat($uid, $code);
    // echo json response
    echoRespnse(200, $response);
});

$app->post('/flat/remove_person', function() use ($app) {
    verifyRequiredParams(array('uid', 'flat'));
    // reading post params
    $uid = $app->request->post('uid');
	$flat = $app->request->post('flat');

    $db = new DbHandler();
    $response = $db->removeFromFlat($uid, $flat);
    // echo json response
    echoRespnse(200, $response);
});

$app->post('/chat/add_message', function() use ($app) {
// check for required params
verifyRequiredParams(array('id', 'token', 'message'));
// reading post params
$id = $app->request->post('id');
$token = $app->request->post('token');
$message = $app->request->post('message');
$db = new DbHandler();
$response = $db->addMessage($id, $message);
// echo json response
echoRespnse(200, $response);
});

$app->post('/flat/add_shopping_item', function() use ($app){
  verifyRequiredParams(array('flat_id', 'user_id', 'item_name', 'price', 'date'));
  $flat_id = $app->request->post('flat_id');
  $user_id = $app->request->post('user_id');
  $item_name = $app->request->post('item_name');
  $price = $app->request->post('price');
  $date = $app->request->post('date');
  $db = new DbHandler();
  $response = $db->addShoppingItem($flat_id, $user_id, $item_name, $price, $date);
  echoRespnse(200, $response);
});

$app->post('/flat/add_duty_todo', function() use ($app){
  verifyRequiredParams(array('flat_id', 'user_id', 'value', 'duty_name'));
  $flat_id = $app->request->post('flat_id');
  $user_id = $app->request->post('user_id');
  $value = $app->request->post('value');
  $duty_name = $app->request->post('duty_name');
  $db = new DbHandler();
  $response = $db->addDutyTodo($flat_id, $user_id, $value, $duty_name);
  echoRespnse(200, $response);
});

$app->post('/flat/add_duty_history', function() use ($app){
  verifyRequiredParams(array('flat_id', 'user_id', 'value', 'duty_name', 'completion_date'));
  $flat_id = $app->request->post('flat_id');
  $user_id = $app->request->post('user_id');
  $value = $app->request->post('value');
  $duty_name = $app->request->post('duty_name');
  $completion_date = $app->request->post('completion_date');
  $db = new DbHandler();
  $response = $db->addDutyHistory($flat_id, $duty_name, $user_id, $value, $completion_date);
  echoRespnse(200, $response);
});


$app->post('/flat/add_flat', function() use ($app){
  verifyRequiredParams(array('name', 'invitation_code', 'user_id'));
  $name = $app->request->post('name');
  $invitation_code = $app->request->post('invitation_code');
  $user_id = $app->request->post('user_id');
  $db = new DbHandler();
  $result = $db->addFlat($name, $invitation_code, $user_id);  
  $response = $db->setFlatId($user_id, $result);
  echoRespnse(200, $response);
});

$app->post('/flat/update_balance', function() use ($app){
  verifyRequiredParams(array('user_id', 'cost'));
  $user_id = $app->request->post('user_id');
  $cost = $app->request->post('cost');
  $db = new DbHandler();
  $response = $db->updateUserBalance($user_id, $cost);
  echoRespnse(200, $response);
});


$app->post('/chat/get_messages', function() use ($app) {
// check for required params
verifyRequiredParams(array('id', 'token'));
// reading post params
$id = $app->request->post('id');
$token = $app->request->post('token');
$db = new DbHandler();
$result = $db->getMessages($id,$token);
$response=array();
while ($message = $result->fetch_assoc()) {
    $tmp = array();
    $tmp["sender_id"] = $message["user_id"];
    $tmp["date"] = $message["message_date"];
    $tmp["message"] = $message["message_text"];
    $tmp["sender_name"] = $message["name"];
    array_push($response, $tmp);
}

echoRespnse(200, $response);
});


$app->post('/flat/rent', function() use ($app) {
    #verifyRequiredParams(array('uid', 'flat', 'value', 'user_ids', 'user_values'));
	$uid = $app->request()->post('uid');
	$flat = $app->request()->post('flat');
    $value = $app->request()->post('value');
	$user_ids = $app->request()->post('user_ids');
	$user_values = $app->request()->post('user_values');

    $db = new DbHandler();
    $response = $db->addRent($uid, $flat, $value, $user_ids, $user_values);
	$response = array();
	$response["error"] = false;
    echoRespnse(200, $response);
});


$app->post('/flat/rents', function() use ($app) {
    verifyRequiredParams(array('flat','uid'));
	$flat = $app->request->post('flat');
	$uid = $app->request->post('uid');

    $db = new DbHandler();
    $response = $db->getRents($flat,$uid);
    echoRespnse(200, $response);
});

$app->post('/flat/get_users', function() use($app) {
    verifyRequiredParams(array('flat_id'));
    $flat_id = $app->request->post('flat_id');
    $db = new DbHandler();
    $response = $db->getUserByFlatId($flat_id);
    echoRespnse(200, $response);
});

$app->post('/flat/get_users_points', function() use($app) {
    verifyRequiredParams(array('flat_id'));
    $flat_id = $app->request->post('flat_id');
    $db = new DbHandler();
    $response = $db->getUserByFlatIdPoints($flat_id);
    echoRespnse(200, $response);
});

$app->post('/flat/get_shoppings', function() use($app) {
    verifyRequiredParams(array('flat_id'));
    $flat_id = $app->request->post('flat_id');
    $db = new DbHandler();
    $response = $db->getShoppingHistoryByFlatId($flat_id);
    echoRespnse(200, $response);
});

$app->post('/flat/get_duties_todo', function() use($app) {
    verifyRequiredParams(array('flat_id'));
    $flat_id = $app->request->post('flat_id');
    $db = new DbHandler();
    $response = $db->getDutiesTodoByFlatId($flat_id);
    echoRespnse(200, $response);
});

$app->post('/flat/get_duties_history', function() use($app) {
    verifyRequiredParams(array('flat_id'));
    $flat_id = $app->request->post('flat_id');
    $db = new DbHandler();
    $response = $db->getDutiesHistoryByFlatId($flat_id);
    echoRespnse(200, $response);
});


/**
 * Verifying required params posted or not
 */
function verifyRequiredParams($required_fields) {
    $error = false;
    $error_fields = "";
    $request_params = array();
    $request_params = $_REQUEST;
    // Handling PUT request params
    if ($_SERVER['REQUEST_METHOD'] == 'PUT') {
        $app = \Slim\Slim::getInstance();
        parse_str($app->request()->getBody(), $request_params);
    }
    foreach ($required_fields as $field) {
        if (!isset($request_params[$field]) || strlen(trim($request_params[$field])) <= 0) {
            $error = true;
            $error_fields .= $field . ', ';
        }
    }
    if ($error) {
        // Required field(s) are missing or empty
        // echo error json and stop the app
        $response = array();
        $app = \Slim\Slim::getInstance();
        $response["error"] = true;
        $response["message"] = 'Required field(s) ' . substr($error_fields, 0, -2) . ' is missing or empty';
        echoRespnse(400, $response);
        $app->stop();
    }
}
/**
 * Echoing json response to client
 * @param String $status_code Http response code
 * @param Int $response Json response
 */
function echoRespnse($status_code, $response) {
    $app = \Slim\Slim::getInstance();
    // Http response code
    $app->status($status_code);
    // setting response content type to json
    $app->contentType('application/json');
    echo json_encode($response);
}
$app->run();
?>
