<?php

/**
* Staff.php contains all the endpoints for staff members' functions
*
* @author  Mandla Chavarika & Taufeeq Razak
* @version 2.0
* @since   2021-06-14 
*/
require_once 'cus_functions.php';

$rType = "";

if (isset($_REQUEST['rType'])) {

    $rType = $_REQUEST['rType'];
    switch ($rType){
        case "pastOrders":
            
            $customerID = "";
            

            if (isset($_REQUEST['customer'])) {

                $customerID = $_REQUEST['customer'];
                $customerObj = new customerFunction();
                $json_array = $customerObj->getPastOrders($customerID);
                echo($json_array);   
            }else{
                    if(empty($restaurantName))
                    echo json_encode($json['message']='Missing customer ID');
                    
            }
            return;

        case "currentOrders":

            $customerID = "";
            

            if (isset($_REQUEST['customer'])) {

                $customerID = $_REQUEST['customer'];
                $customerObj = new customerFunction();
                $json_array = $customerObj->getCurrentOrders($customerID);
                echo($json_array);   
            }else{
                    if(empty($restaurantName))
                    echo json_encode($json['message']='Missing customer ID');
                    
            }
            return;
        case "updateRating":
            $orderID ="";
            $rating ="";

            if (isset($_REQUEST['order'])) {
                if (isset($_REQUEST['rating'])) {
                $orderID = $_REQUEST['order'];
                $rating = $_REQUEST['rating'];
                $customerObj = new customerFunction();
                $json_array = $customerObj->updateRating($orderID, $rating);
                echo($json_array);   
                }
            }else{
                echo json_encode($json['message']='Missing StafID');
            }
            return;
        
       
    }
}


?>
