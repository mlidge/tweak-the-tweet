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
	$tag_string = mysql_result($result,0,"category");
	$tag_array = preg_split ("/[\s,]+/", $tag_string);
	$num = count($tag_array);
	$new_tag_string = "";
	$i = 0;
	$tag = "#".$tag;
	while ($i < $num) {
		if (strcmp($tag, $tag_array[$i]) != 0) {
			$new_tag_string = $new_tag_string.",".$tag_array[$i];
		}
		$i++;
	}
	$new_tag_string = trim($new_tag_string, ",");
	
	$query="UPDATE Coordinates SET category = '$new_tag_string' WHERE event_id='$event'";
	$result=mysql_query($query);
	mysql_close();
	
	header("Location: overview.php");
	die();
?>

