<?php

include_once './db-connect.php';
include_once './restaurant_model.php';

class orderFunction
{

    private $db;

    private $db_table1 = "ORDERS";

    public function __construct(){
        $this->db = new DbConnect();
    }

    public function guidv4($data = null){
        return uniqid();
    }

    public function generateRestaurantObject($restaurantName, $restaurantURL){
        $restaurant = new  Restaurant($restaurantName, $restaurantURL);
        return json_encode($restaurant);
    }

    public function getUserID($username){
        $query = "select userID from USERS where `username` = '". $username ."' LIMIT 1";
        $result = mysqli_query($this->db->getDb(), $query)or die( mysqli_error($this->db->getDb()));
        $rows = [];
        while($row = mysqli_fetch_array($result))
        {
            $rows[] = $row;
        }
        $output = "";
        $output = implode("",$rows);
        return $output;
    }

    public function newRestaurant($restaurantName, $restaurantURL, $username){
        $uuid = $this->guidv4();

        $restaurantID = $uuid;

        
        $query = "INSERT INTO RESTAURANT VALUES('".$restaurantID."', '".$restaurantName."', '".$restaurantURL."')";
        $result = mysqli_query($this->db->getDb(), $query);
        
        if ($result){
            $query1 = "UPDATE USER_TYPE SET restaurantID = '".$restaurantID."' 
                WHERE userID = (select userID from USERS where `username` = '". $username ."')";
            $result1 = mysqli_query($this->db->getDb(), $query1);

            if($result1){
                $json['success'] = 1;
                $json['message'] = "Successfully created restaurant";
            }else{
                $json['success'] = 0;
                $json['message'] = mysqli_error($this->db->getDb())." ".$query;
            }
        }else{
            $json['success'] = 0;
            $json['message'] = mysqli_error($this->db->getDb())." ".$query;
        }
        mysqli_close($this->db->getDb());

        return $json;
    }

    public function newStaff($restaurantName, $username){
        $query = "UPDATE USER_TYPE SET restaurantID = '(SELECT restaurantID FROM RESTAURANT WHERE restaurantName = '.$restaurantName.')' 
                WHERE userID = (select userID from USERS where `username` = '". $username ."')";
        $result = mysqli_query($this->db->getDb(), $query);
        switch($result){
            case 1:
                $json['success'] = 1;
                $json['message'] = "Successfully created order";
                return;
            default:
            $json['success'] = 0;
            $json['message'] = mysqli_error($this->db->getDb())." ".$query;
        }
        mysqli_close($this->db->getDb());
        return $json;

    }

    
}
?>
