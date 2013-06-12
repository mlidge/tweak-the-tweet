<?php


	$eventname = $_POST['name'];
	$bl_lat = $_POST['bl_lat'];
	$bl_long = $_POST['bl_long'];
	$tr_lat = $_POST['tr_lat'];
	$tr_long = $_POST['tr_long'];
	

	$username="ttt";
	$password="water2014";
	$database="tweak_the_tweet";
	$host="128.208.219.182";

	mysql_connect($host,$username,$password);
	@mysql_select_db($database) or die( "Unable to select database");
	$query="UPDATE Coordinates SET latitude_top_right = '$tr_lat', longitude_top_right = '$tr_long', latitude_bottom_left = '$bl_lat', longitude_bottom_left = '$bl_long' WHERE event_id = '$eventname'";
	$result=mysql_query($query);


	mysql_close();
	header("Location: overview.php");
	die();
?>

