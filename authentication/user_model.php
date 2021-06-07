<?php
class User
{
    public $userId;
    public $username;
    public $email;
    public $userType;
    public function __construct(

        $userId,
        $username,
        $email,
        $userType
    ) {
        $this->userId = $userId;
        $this->username = $username;
        $this->email = $email;
        $this->userType = $userType;
    }
}
