<?php


	$event = $_POST['name'];


	$username="ttt";
	$password="water2014";
	$database="tweak_the_tweet";
	$host="128.208.219.182";
	mysql_connect($host,$username,$password);
	@mysql_select_db($database) or die( "Unable to select database");
	$query2="DELETE FROM Coordinates WHERE event_id='$event'";
	$result=mysql_query($query2);

	

	mysql_close();
	header("Location: overview.php");
	die();
?>


