
<?php

include_once './db-connect.php';
include_once './order_model.php';

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

    public function generateOrderObject($orderID, $customerID, $staffID, $itemDescription){
        $user = new  Order($orderID, $customerID, $staffID, $itemDescription);
        return json_encode($user);
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

    public function getRestID($staffID){
        $query = "select restaurantID from USER_TYPE where `userID` = '". $staffID. "' LIMIT 1";
        $result = mysqli_query($this->db->getDb(), $query)or die( mysqli_error($this->db->getDb()));;
        $rows = [];
        while($row = mysqli_fetch_array($result))
        {
            $rows[] = $row;
        }
        $output = "";
        $output = implode("",$rows);
        return $output;
 
    }

    public function newOrder($cusUsername, $staffUsername, $itemDescription){
        $uuid = $this->guidv4();

        $orderID = $uuid;
        //$userID =  $this->getUserID($cusUsername);
        //$staffID = $this->getUserID($staffUsername);
        $restaurantID = $this->getRestID($this->getUserID($staffUsername));

        //$query = "INSERT INTO " . $this->db_table1 . " VALUES('" . $orderID ."', '" . $userID . "', '" . $restaurantID . "', CURRENT_TIMESTAMP(), '" . $itemDescription . "')"; 
        $query = "insert into ORDERS values
        ('".$orderID."', 
        (select userID from USERS where `username` = '". $cusUsername ."'),
        (select userID from USERS where `username` = '". $staffUsername ."'), 
        (select restaurantID from USER_TYPE 
        where `userID` = (select userID from USERS where `username` = '". $staffUsername ."')), 
        CURRENT_TIMESTAMP(), 
        '" . $itemDescription . "')";
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
