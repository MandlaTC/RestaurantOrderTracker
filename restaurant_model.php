<?php
class Restaurant
{
    public $restaurantName;
    public $restaurantURL;
    public function __construct(

        $restaurantName,
        $restaurantURL
        
    ) {
        $this->restaurantName = $restaurantName;
        $this->restaurantURL = $restaurantURL;
        
    }
}
