<?php


	$eventname = $_POST['name'];
	$bl_lat = $_POST['bl_lat'];
	$bl_long = $_POST['bl_long'];
	$tr_lat = $_POST['tr_lat'];
	$tr_long = $_POST['tr_long'];
	$tags = $_POST['tags'];
	$desc = $_POST['desc'];
	
	$username="ttt";
	$password="water2014";
	$database="tweak_the_tweet";
	$host="128.208.219.182";
	mysql_connect($host,$username,$password);
	@mysql_select_db($database) or die( "Unable to select database");
	
	$eventname = "#".$eventname;
	$arr = preg_split ("/$\R?^/m", $tags);
	$num = count($arr);
	$i = 1;
	$tag_string = "#".trim($arr[0]);
	while ($i < $num) {
		$tag = $arr[$i];
		$tag = trim($tag);
		$tag_string = $tag_string.",#".$tag;
		$i++;
	}
	$query="INSERT INTO Coordinates VALUES ('$eventname', '$desc', '$tag_string', '$tr_lat', '$tr_long', '$bl_lat', '$bl_long')";
	$result=mysql_query($query);
	
	mysql_close();

	header("Location: overview.php");
	die();

	
?>

