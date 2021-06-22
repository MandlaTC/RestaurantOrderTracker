<?php

/**
* The staffFunction class contains all functions requested by the staff members UI.
*
* @author  Mandla Chavarika & Taufeeq Razak
* @version 2.0
* @since   2021-06-14
*/

include_once './db-connect.php';


class staffFunction
{

    private $db;

    public function __construct(){
        $this->db = new DbConnect();
    }

    public function guidv4($data = null){
        return uniqid();
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

    /**
     * This method is used for creating a new restaurant as well as adding a new staff member to that restaurant
     * @param restaurantName This is the restaurant name of which the new staff memeber belongsd
     * @param username This is username of the staff member being added by the restaurant
     * @param restaurantURL This is the url pertaining to the restaurant
     * @return json a json containg a success code (1 on success and 0 on failure) as well as a message (confirming success or showing MYSQL error on failure)
     */
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

        return json_encode($json);
    }

    /**
     * This method is used for adding a new staff member to an existing restaurant
     * @param restaurantName This is the restaurant name of which the new staff memeber belongsd
     * @param username This is username of the staff member being added by the restaurant
     * @return json a json containg a success code (1 on success and 0 on failure) as well as a message (confirming success or showing MYSQL error on failure)
     */
    public function newStaff($restaurantName, $username){
        $query = "UPDATE USER_TYPE SET restaurantID = '(SELECT restaurantID FROM RESTAURANT WHERE restaurantName = '.$restaurantName.')'
                WHERE userID = (select userID from USERS where `username` = '". $username ."')";
        $result = mysqli_query($this->db->getDb(), $query);
        switch($result){
            case 1:
                $json['success'] = 1;
                $json['message'] = "Successfully affiliated with restaurant";;
                return;
            default:
            $json['success'] = 0;
            $json['message'] = mysqli_error($this->db->getDb())." ".$query;
        }
        mysqli_close($this->db->getDb());
        return json_encode($json);

    }


    /**
     * This method is used for restaurant autocomplete functionality
     * Given a partial restaurant returns all restaurants in the RESTAURANT table with name containing the partial restaurantname
     * @param restaurant This is the partial restaurant name being searched for
     * @return json a json containing all the fields returned from the database
     */
    public function restAutocomplete($restaurant){

        $query = "select * from RESTAURANT where restaurantName LIKE '".$restaurant."%'";
        $result = mysqli_query($this->db->getDb(), $query);
        $rows = array();
        while($r = mysqli_fetch_array($result)) {
            $rows[] = $r;
          }
        return json_encode($rows, JSON_PRETTY_PRINT);
    }

    /**
     * This method is used for username autocomplete functionality
     * Given a partial customer username returns all customers in the USER table with name containing the partial username
     * @param username This is the partial username name being searched for
     * @return json a json containing all the fields returned from the database
     */
    public function custAutocomplete($username){

        $query = "select * from USERS where username LIKE '".$username."%'";
        $result = mysqli_query($this->db->getDb(), $query);
        $rows = array();
        while($r = mysqli_fetch_array($result)) {
            $rows[] = $r;
          }
        return json_encode($rows, JSON_PRETTY_PRINT);
    }

    /**
     * This method is used for searching the database for all order rows relating to a certain staff member
     * @param userID This is the userID of which we are trying to find all orders
     * @return json a json containing all the fields returned from the database
     */

    public function staffOrders($staffID){
        $query = "SELECT ORDERS.orderID, ORDERS.customerID, ORDERS.staffID, ORDERS.restaurantID, orderCreatedAt, itemDescription, USERS.username AS customerName, ORDER_STATUS.orderStatus
            FROM ORDERS
            JOIN USERS ON USERS.userID = ORDERS.customerID
            JOIN ORDER_STATUS ON ORDER_STATUS.orderID = ORDERS.orderID
            WHERE ORDERS.staffID = '".$staffID."'
            ORDER BY ORDERS.orderCreatedAt DESC";
        $result = mysqli_query($this->db->getDb(), $query);
        $rows = array();
        while($r = mysqli_fetch_array($result)) {
            $rows[] = $r;
          }
        return json_encode($rows, JSON_PRETTY_PRINT);
    }

    /**
     * This method is used for get a staff members average order rating.
     * @param staffID This is the userID of which we are trying to find all orders
     * @return json a json containing the average rating
     */
    public function avgRating($staffID){
        $query = "SELECT AVG(rating) FROM ORDER_RATING JOIN ORDERS ON ORDER_RATING.orderID=ORDERS.orderID WHERE ORDERS.staffID = '".$staffID."'";

        $result = mysqli_query($this->db->getDb(), $query);
        $rows = array();
        while($r = mysqli_fetch_array($result)) {
            $rows[] = $r;
          }
        return json_encode($rows, JSON_PRETTY_PRINT);
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
                $query1 = "INSERT INTO ORDER_RATING(orderID) VALUES ('".$orderID."')";
                $query2 = "INSERT INTO ORDER_STATUS VALUES ('".$orderID."', 'cooking')";
                mysqli_query($this->db->getDb(), $query1);
                mysqli_query($this->db->getDb(), $query2);
                $json['success'] = 1;
                $json['message'] = "Successfully created order";
                mysqli_close($this->db->getDb());

                return json_encode($json);
                return;
            default:
            $json['success'] = 0;
            $json['message'] = mysqli_error($this->db->getDb())." ".$query;
        }
        mysqli_close($this->db->getDb());

        return json_encode($json);
    }

    public function updateStatus($orderID, $status){
        $query = "UPDATE ORDER_STATUS SET orderStatus = '".$status."' WHERE orderID = '".$orderID."'";
        $result = mysqli_query($this->db->getDb(), $query);
        switch($result){
            case 1:
                $json['success'] = 1;
                $json['message'] = "Successfully updated status to ".$status."";
                mysqli_close($this->db->getDb());
                return json_encode($json);
                return;
            default:
            $json['success'] = 0;
            $json['message'] = mysqli_error($this->db->getDb())." ".$query;
        }
        mysqli_close($this->db->getDb());
        return json_encode($json);
    }

    public function hasRestaurant($staffID){
        $query = "select * from USER_TYPE where userID = '".$staffID."' AND restaurantID IS NOT NULL";

        $result = mysqli_query($this->db->getDb(), $query);

        if (mysqli_num_rows($result) > 0) {
            $json['message']='true';
            mysqli_close($this->db->getDb());
            return json_encode($json);
        }

        $json['message']='false';
        mysqli_close($this->db->getDb());
        return json_encode($json);
    }

    public function addStaffToRestaurant($staffID, $restaurantID) {
        $userType = "staff";
           $query = "UPDATE  USER_TYPE SET restaurantID = '$restaurantID' WHERE userID = '$staffID'";
        $result = mysqli_query($this->db->getDb(), $query);
           switch($result){
            case 1:
                $json['success'] = 1;
                $json['message'] = "Succesfully added staff to restaurant";
                mysqli_close($this->db->getDb());
                return json_encode($json);
                return;
            default:
            $json['success'] = 0;
            $json['message'] = mysqli_error($this->db->getDb())." ".$query;
        }
        mysqli_close($this->db->getDb());
        return json_encode($json);
    }


}
?>
