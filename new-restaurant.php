<?php

require_once 'restaurant_functions.php';

$restaurantName = "";

$restuarantURL = "";

$username = "";

$newRestaurant = 0;


if (isset($_REQUEST['restaurantName'])) {

    $restaurantName = $_REQUEST['restaurantName'];
}

if (isset($_REQUEST['restaurantURL'])) {

    $restuarantURL = $_REQUEST['restaurantURL'];
}

if (isset($_REQUEST['username'])) {

    $username = $_REQUEST['username'];
}

if (isset($_REQUEST['newRestaurant'])) {

    $newRestaurant = $_REQUEST['newRestaurant'];
}

$restaurantObject = new orderFunction();


if ($newRestaurant==1){
    if(!empty($restaurantName) && !empty($username)){
        $json_array = $restaurantObject->newStaff($restaurantName, $username);
    }else{
        if(empty($cusUsername))
        echo json_encode($json['message']='Failure1');
        if(empty($staffUsername))
        echo json_encode($json['message']='Failure2');
        if(empty($itemDescription))
        echo json_encode($json['message']='Failure3');
    }

}else{
    if (!empty($restaurantName) && !empty($restuarantURL) && !empty($username)) {


        $json_array = $restaurantObject->newRestaurant($restaurantName, $restuarantURL, $username);
    
        echo json_encode($json_array);
    }else{
        if(empty($restaurantName))
        echo json_encode($json['message']='Failure1');
        if(empty($restuarantURL))
        echo json_encode($json['message']='Failure2');
        if(empty($username))
        echo json_encode($json['message']='Failure3');
    }
}


?>
