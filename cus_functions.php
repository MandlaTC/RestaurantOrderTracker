<?php

/**
* The staffFunction class contains all functions requested by the staff members UI.
*
* @author  Mandla Chavarika & Taufeeq Razak
* @version 2.0
* @since   2021-06-14 
*/

include_once './db-connect.php';


class customerFunction
{

    private $db;
    
    public function __construct(){
        $this->db = new DbConnect();
    }

    
    /**
     * This method is used for get a staff members average order rating.
     * @param staffID This is the userID of which we are trying to find all orders
     * @return json a json containing the average rating
     */
    public function updateRating($orderID, $rating){
        $query = "UPDATE ORDER_RATING SET rating = '".$rating."' WHERE orderID = '".$orderID."'";
        $result = mysqli_query($this->db->getDb(), $query);
        switch($result){
            case 1:
                $json['success'] = 1;
                $json['message'] = "Successfully updated rating to ".$rating."";
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


    public function getCurrentOrders($cusID){
        $query = "SELECT * FROM ORDERS INNER JOIN USER_TYPE on (ORDERS.staffID= USER_TYPE.userID) 
            INNER JOIN RESTAURANT ON (USER_TYPE.restaurantID = RESTAURANT.restaurantID) 
            INNER JOIN ORDER_STATUS on (ORDERS.orderID = ORDER_STATUS.orderID) 
            WHERE ORDERS.customerID = '".$cusID."' AND ORDER_STATUS.orderStatus != 'completed'";
        $result = mysqli_query($this->db->getDb(), $query);
        $rows = array();
        while($r = mysqli_fetch_array($result)) {
            $rows[] = $r;
          }
        return json_encode($rows, JSON_PRETTY_PRINT);
    }

    public function getPastOrders($cusID){
        $query = "SELECT * FROM ORDERS INNER JOIN USER_TYPE on (ORDERS.staffID= USER_TYPE.userID) 
            INNER JOIN RESTAURANT ON (USER_TYPE.restaurantID = RESTAURANT.restaurantID) 
            INNER JOIN ORDER_STATUS on (ORDERS.orderID = ORDER_STATUS.orderID) 
            INNER JOIN ORDER_RATING ON (ORDERS.orderID = ORDER_RATING.orderID) 
            WHERE ORDERS.customerID = '".$cusID."' AND ORDER_STATUS.orderStatus = 'completed'";
        $result = mysqli_query($this->db->getDb(), $query);
        $rows = array();
        while($r = mysqli_fetch_array($result)) {
            $rows[] = $r;
          }
        return json_encode($rows, JSON_PRETTY_PRINT);
    }
    
}
?>
