<?php
	try {
	    $con = new PDO('mysql:host=localhost;dbname=webappdb;charset=utf8mb4','root', '');
	    $con->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
	} catch (PDOException $e){
	    exit($e->getMessage());
	}

		$name = $_POST['name'];
		$un = $_POST['un'];
		$pwd = $_POST['pwd'];
		
		$stmt = $con->prepare("SELECT * FROM user_info WHERE user_name LIKE :username");
		$stmt->bindParam(':username', $un, PDO::PARAM_STR);
		$stmt->execute(); 

		$response = array();
		if($stmt->rowCount() == 1){
		    $code = "reg_failed";
			$message = "Username already exists.";
			array_push($response, array("code"=>$code, "message"=>$message));
			echo json_encode($response);
		}else {
		    $sql = 'INSERT INTO user_info (name ,user_name, user_pass) 
		    VALUES (:name,:username,:password)';    
		    $query = $con->prepare($sql);

		    $query->execute(array(
		    ':name' => $name,
		    ':username' => $un,
		    ':password' => $pwd,
		    ));
		    
		    $code = "reg_success";
			$message = "Thank you for signing up.Login now!";
			array_push($response, array("code"=>$code, "message"=>$message));
			echo json_encode($response);
		    }
	   
?>