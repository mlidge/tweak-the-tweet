<?php


	$event = $_POST['old_name'];
	$new_event = $_POST['name'];


	$username="ttt";
	$password="water2014";
	$database="tweak_the_tweet";
	$host="128.208.219.182";
	$event = "#".$event;
	$new_event = "#".$new_event;
	mysql_connect($host,$username,$password);
	@mysql_select_db($database) or die( "Unable to select database");
	$query2="UPDATE Coordinates SET event_id = '$new_event' WHERE event_id = '$event'";
	$result=mysql_query($query2);



	mysql_close();
	
	header("Location: overview.php");
	die();
?>


