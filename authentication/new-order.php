
<?php

require_once 'order_functions.php';

$cusUsername = "";

$staffUsername = "";

$itemDescription = "";


if (isset($_REQUEST['customer'])) {

    $cusUsername = $_REQUEST['customer'];
}

if (isset($_REQUEST['staff'])) {

    $staffUsername = $_REQUEST['staff'];
}

if (isset($_REQUEST['item'])) {

    $itemDescription = $_REQUEST['item'];
}

$orderObject = new orderFunction();

if (!empty($cusUsername) && !empty($staffUsername) && !empty($itemDescription)) {


    $json_array = $orderObject->newOrder($cusUsername, $staffUsername, $itemDescription);

    echo json_encode($json_array);
}else{
    if(empty($cusUsername))
    echo json_encode($json['message']='Failure1');
    if(empty($staffUsername))
    echo json_encode($json['message']='Failure2');
    if(empty($itemDescription))
    echo json_encode($json['message']='Failure3');
}

?>
