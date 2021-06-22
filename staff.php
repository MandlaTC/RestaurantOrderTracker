<?php

/**
* Staff.php contains all the endpoints for staff members' functions
*
* @author  Mandla Chavarika & Taufeeq Razak
* @version 2.0
* @since   2021-06-14
*/
require_once 'staff_functions.php';

$rType = "";

if (isset($_REQUEST['rType'])) {

    $rType = $_REQUEST['rType'];
    switch ($rType){
        case "newOrder":
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

            $orderObject = new staffFunction();

            if (!empty($cusUsername) && !empty($staffUsername) && !empty($itemDescription)) {

                $json_array = $orderObject->newOrder($cusUsername, $staffUsername, $itemDescription);
                echo($json_array);
            }else{
                if(empty($cusUsername))
                echo json_encode($json['message']='Failure1');
                if(empty($staffUsername))
                echo json_encode($json['message']='Failure2');
                if(empty($itemDescription))
                echo json_encode($json['message']='Failure3');
            }
            return;

        case "newRestaurant":

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

            $restaurantObject = new staffFunction();

            /*
            * If newRestaurant is equal to one, the restaurant already exists
            * and the function newStaff is called
            * else the restaurant needs to be created and
            * the function newRestaurant is called
            */
            if ($newRestaurant==1){
                if(!empty($restaurantName) && !empty($username)){
                    $json_array = $restaurantObject->newStaff($restaurantName, $username);
                    echo ($json_array);
                }else{
                    if(empty($cusUsername))
                    echo json_encode($json['message']='Missing Customer Username');
                    if(empty($staffUsername))
                    echo json_encode($json['message']='Missing Staff Username');
                    if(empty($itemDescription))
                    echo json_encode($json['message']='Missing Item Description');
                }
            }else{
                if (!empty($restaurantName) && !empty($restuarantURL) && !empty($username)) {


                    $json_array = $restaurantObject->newRestaurant($restaurantName, $restuarantURL, $username);

                    echo ($json_array);
                }else{
                    if(empty($restaurantName))
                    echo json_encode($json['message']='Missing restaurant name');
                    if(empty($restuarantURL))
                    echo json_encode($json['message']='Missing restaurant URL');
                    if(empty($username))
                    echo json_encode($json['message']='Missing Staff Username');
                }
            }
            return;
        case "staffOrders":
            $staffID ="";

            if (isset($_REQUEST['staff'])) {

                $staffID = $_REQUEST['staff'];
                $staffObject = new staffFunction();
                $json_array = $staffObject->staffOrders($staffID);
                echo($json_array);
            }else{
                echo json_encode($json['message']='Missing StafID');
            }
            return;
        case "updateStatus":
            $orderID = "";
            $status = "";
            if (isset($_REQUEST['order'])){
                if(isset($_REQUEST['status'])){
                    $orderID = $_REQUEST['order'];
                    $status = $_REQUEST['status'];

                    $staffObject = new staffFunction();
                    $json_array = $staffObject->updateStatus($orderID, $status);
                    echo($json_array);
                }else{
                    echo json_encode($json['message']='Missing status');
                }
            }else{
                echo json_encode($json['message']='Missing orderID');
            }
            return;
        case "avgRating":
            $staffID = "";

            if (isset($_REQUEST['staff'])) {

                $staffID = $_REQUEST['staff'];
                $acObject = new staffFunction();
                $json_array = $acObject->avgRating($staffID);
                echo ($json_array);
            }else{
                echo json_encode($json['message']='Missing staffID');
            }
            return;
        case "cusAutocomplete":
            $username ="";

            if (isset($_REQUEST['username'])) {

                $username = $_REQUEST['username'];
                $acObject = new staffFunction();
                $json_array = $acObject->custAutocomplete($username);
                echo $json_array;
            }else{
                echo json_encode($json['message']='Missing username');
            }
            return;
        case "restAutocomplete":
            $restaurant ="";

            if (isset($_REQUEST['restaurant'])) {

                $restaurant = $_REQUEST['restaurant'];
                $acObject = new staffFunction();
                $json_array = $acObject->restAutocomplete($restaurant);
                echo ($json_array);
            }else{
                echo json_encode($json['message']='Missing restaurant name');
            }
            return;
        case "staffHasRestaurant":
            $staffID ="";

            if (isset($_REQUEST['staff'])) {

                $staffID = $_REQUEST['staff'];
                $staffObject = new staffFunction();
                $json_array = $staffObject->hasRestaurant($staffID);
                echo($json_array);
            }else{
                echo json_encode($json['message']='Missing StafID');
            }
            return;
        case "updateStaffRest":
            $restID = "";
            $staffID= "";
                if (isset($_REQUEST['restID']) && isset($_REQUEST["staffID"])) {
                    $restaurant = $_REQUEST['restID'];
                    $staffID = $_REQUEST['staffID'];
                    $acObject = new staffFunction();
                    $json_array = $acObject->addStaffToRestaurant($staffID, $restaurant );
                    echo ($json_array);
                }else{
                    echo json_encode($json['message']='Missing restaurant name or staff ID');
                }
                return;

    }
}


?>
