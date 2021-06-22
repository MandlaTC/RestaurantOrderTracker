<?php
class User
{
    public $userID;
    public $username;
    public $email;
    public $userType;
    public function __construct(

        $userID,
        $username,
        $email,
        $userType
    ) {
        $this->userID = $userID;
        $this->username = $username;
        $this->email = $email;
        $this->userType = $userType;
    }
}
