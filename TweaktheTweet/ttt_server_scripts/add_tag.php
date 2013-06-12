<?php


	$event = $_POST['name'];
	$tag = $_POST['tag'];


	$username="ttt";
	$password="water2014";
	$database="tweak_the_tweet";
	$host="128.208.219.182";

	mysql_connect($host,$username,$password);
	@mysql_select_db($database) or die( "Unable to select database");
	$select_query = "SELECT category FROM Coordinates WHERE event_id='$event'";
	$result=mysql_query($select_query);
	$tag_string = mysql_result($result,0);
	$tag = $tag_string.",#".$tag;
	$tag = trim($tag, ",");
	$query="UPDATE Coordinates SET category = '$tag' WHERE event_id='$event'";
	$result=mysql_query($query);
	mysql_close();
	
	header("Location: overview.php");
	die();

	
?>


