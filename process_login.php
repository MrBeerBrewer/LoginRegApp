<?php
	try {
	    $con = new PDO('mysql:host=localhost;dbname=webappdb;charset=utf8mb4','root', '');
	    $con->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
	} catch (PDOException $e){
	    exit($e->getMessage());
	}

	 
		$un = $_POST['un'];
		$pwd = $_POST['pwd'];
		
		$stmt = $con->prepare("SELECT * FROM user_info WHERE user_name = :username AND user_pass= :password");
		$stmt->bindParam(':username', $un, PDO::PARAM_STR);
		$stmt->bindParam(':password', $pwd, PDO::PARAM_STR);
		$stmt->execute(); 

		$response = array();
		if($stmt->rowCount() == 1){
			$res= $stmt -> fetch(); 
		    $code = "login_success";
			$message = "Welcome " . $res['user_name'];
			array_push($response, array("code"=>$code, "message"=>$message));
			echo json_encode($response);
		}else {
		    $code = "login_failed";
			$message = "Username or password is wrong.";
			array_push($response, array("code"=>$code, "message"=>$message));
			echo json_encode($response);
		    }
	
	
?>