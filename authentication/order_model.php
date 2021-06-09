<?php
class Order
{
    public $orderID;
    public $customerID;
    public $staffID;
    public $itemDescription;
    public function __construct(

        $orderID,
        $customerID,
        $staffID,
        $itemDescription
    ) {
        $this->orderID = $orderID;
        $this->customerID = $customerID;
        $this->staffID = $staffID;
        $this->itemDescription = $itemDescription;
    }
}
