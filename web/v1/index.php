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
    verifyRequiredParams(array('uid', 'flat', 'value'));
	$uid = $app->request->post('uid');
	$flat = $app->request->post('flat');
    $value = $app->request->post('value');

    $db = new DbHandler();
    $response = $db->addRent($uid, $flat, $value);

    echoRespnse(200, $response);
});


$app->post('/flat/rents', function() use ($app) {
    verifyRequiredParams(array('flat'));
	$flat = $app->request->post('flat');

    $db = new DbHandler();
    $response = $db->getRents($flat);
    echoRespnse(200, $response);
});

$app->post('/flat/get_users', function() use($app) {
    verifyRequiredParams(array('flat_id'));
    $flat_id = $app->request->post('flat_id');
    $db = new DbHandler();
    $response = $db->getUserByFlatId($flat_id);
    echoRespnse(200, $response);
});

$app->post('/flat/get_shoppings', function() use($app) {
    verifyRequiredParams(array('flat_id'));
    $flat_id = $app->request->post('flat_id');
    $db = new DbHandler();
    $response = $db->getShoppingHistoryByFlatId($flat_id);
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
