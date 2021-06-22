<?php

require_once 'auth_functions.php';

$username = "";

$password = "";

$email = "";
$userType = "";

if (isset($_REQUEST['username'])) {

    $username = $_REQUEST['username'];
}

if (isset($_REQUEST['password'])) {
    echo $password;

    $password = $_REQUEST['password'];
}

if (isset($_REQUEST['email'])) {

    $email = $_REQUEST['email'];
}
if (isset($_REQUEST['userType'])) {

    $userType = $_REQUEST['userType'];
}
$userObject = new AuthFunction();

// Registration
//In our application, there will be more fields besides email. Thinking user type.
if (!empty($username) && !empty($password) && !empty($email) && !empty($userType)) {

    $hashed_password = md5($password);

    $json_registration = $userObject->createNewRegisterUser($username, $hashed_password, $email, $userType);

    echo json_encode($json_registration);
}

// Login

if (!empty($username) && !empty($password) && empty($email) && empty($userType)) {

    $hashed_password = md5($password);

    $json_array = $userObject->loginUsers($username, $hashed_password);

    echo json_encode($json_array);
}

?>
