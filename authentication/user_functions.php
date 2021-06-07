
<?php

include_once './db-connect.php';
include_once './user_model.php';
class UserFunction
{

    private $db;

    private $db_table = "users";

    public function __construct()
    {
        $this->db = new DbConnect();
    }
    public function guidv4($data = null)
    {
        // Generate 16 bytes (128 bits) of random data or use the data passed into the function.
        $data = $data ?? random_bytes(16);
        assert(strlen($data) == 16);

        // Set version to 0100
        $data[6] = chr(ord($data[6]) & 0x0f | 0x40);
        // Set bits 6-7 to 10
        $data[8] = chr(ord($data[8]) & 0x3f | 0x80);

        // Output the 36 character UUID.
        return vsprintf('%s%s-%s-%s-%s-%s%s%s', str_split(bin2hex($data), 4));
    }
    public function generateUserObject($userId, $username, $email, $userType)
    {
        $user = new  User($userId, $username, $email, $userType);
        return json_encode($user);
    }
    public function isLoginExist($username, $password)
    {

        $query = "select * from " . $this->db_table . " where username = '$username' AND userPass = '$password' Limit 1";

        $result = mysqli_query($this->db->getDb(), $query);

        if (mysqli_num_rows($result) > 0) {

            return true;
        }

        mysqli_close($this->db->getDb());

        return false;
    }

    public function isEmailUsernameExist($username, $email)
    {

        $query = "select * from " . $this->db_table . " where username = '$username' AND email = '$email'";

        $result = mysqli_query($this->db->getDb(), $query);

        if (mysqli_num_rows($result) > 0) {


            return true;
        }

        return false;
    }

    public function isValidEmail($email)
    {
        return filter_var($email, FILTER_VALIDATE_EMAIL) !== false;
    }

    public function createNewRegisterUser($username, $password, $email, $userType)
    {
        $uuid = $this->guidv4();
        $isExisting = $this->isEmailUsernameExist($username, $email);

        if ($isExisting) {

            $json['success'] = 0;
            $json['message'] = "Error in registering. Probably the username/email already exists";
        } else {

            $isValid = $this->isValidEmail($email);

            if ($isValid) {
                $query = "insert into " . $this->db_table . " (userID, username, userPass, email, userType) values ('$uuid', '$username', '$password', '$email', '$userType')";

                $inserted = mysqli_query($this->db->getDb(), $query);

                if ($inserted == 1) {

                    $json['success'] = 1;
                    $json['message'] = "Successfully registered the user";
                    $json['user'] = $this->generateUserObject($uuid, $username, $email, $userType);
                } else {

                    $json['success'] = 0;
                    $json['message'] = "Error in registering, likely missing arguments";
                }

                mysqli_close($this->db->getDb());
            } else {
                $json['success'] = 0;
                $json['message'] = "Error in registering. Email Address is not valid";
            }
        }

        return $json;
    }
    public function getUserDataFromDB($username, $password)
    {

        $query = "select * from " . $this->db_table . " where username = '$username' AND userPass = '$password' Limit 1";

        $result = mysqli_query($this->db->getDb(), $query);
        $data = $result->fetch_assoc();
        if (mysqli_num_rows($result) > 0) {

            mysqli_close($this->db->getDb());
            return $this->generateUserObject($data['userID'], $data['username'], $data['email'], $data['userType'],);
        }
    }
    public function loginUsers($username, $password)
    {

        $json = array();

        $canUserLogin = $this->isLoginExist($username, $password);

        if ($canUserLogin) {

            $json['success'] = 1;
            $json['message'] = "Successfully logged in";
            $userData = $this->getUserDataFromDB($username, $password);
            $json['user'] = $userData;
        } else {
            $json['success'] = 0;
            $json['message'] = "Incorrect details";
        }
        return $json;
    }
}
?>
