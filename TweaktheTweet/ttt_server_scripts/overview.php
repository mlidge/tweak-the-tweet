<html>
	<head>
<title>Overview</title>
</head>
	
<body>
<?php
	$username="ttt";
	$password="water2014";
	$database="tweak_the_tweet";
	$host="128.208.219.182";
	mysql_connect($host,$username,$password);
	@mysql_select_db($database) or die( "Unable to select database");
	$query="SELECT event_id FROM Coordinates";
	$result=mysql_query($query);

	$num=mysql_numrows($result);


	mysql_close();
?>

<h2>Events</h2>
<form method="post" action="add_event.php">
    <input type="submit" value="Add Event">
</form>
<?php
	$username="ttt";
	$password="water2014";
	$database="tweak_the_tweet";
	$host="128.208.219.182";
	mysql_connect($host,$username,$password);
	@mysql_select_db($database) or die( "Unable to select database");
	$query="SELECT * FROM Coordinates";
	$result=mysql_query($query);

	$num=mysql_numrows($result);
	$event_array = array();

	$i=0;
	while ($i < $num) {

		$event=mysql_result($result,$i,"event_id");
		?>
<a href=#<?php echo $event; ?>><?php echo $event; ?></a>
<br />
<?php
		$event_array[]=$event;
		$i++;
	}
	mysql_close();


$i=0;
while ($i < $num) {

$event=mysql_result($result,$i,"event_id");
$bl_lat=mysql_result($result,$i,"latitude_bottom_left");
$bl_long=mysql_result($result,$i,"longitude_bottom_left");
$tr_lat=mysql_result($result,$i,"latitude_top_right");
$tr_long=mysql_result($result,$i,"longitude_top_right");
$desc = mysql_result($result,$i,"description");
?>
<br />
<br />
<a name=<?php echo $event; ?>></a>
<b><?php echo $event; ?></b> <br />
<form method="post" action="rename_event.php">  
    <input type="hidden" name="old_name" value="<?php echo $event; ?>">
    <input type="submit" value="Rename Event">
</form>
<form method="post" action="delete_event.php">  
    <input type="hidden" name="name" value="<?php echo $event; ?>">
    <input type="submit" value="Delete Event">
</form>
    
<b>Event Description:</b>
<br/>
 <?php echo $desc; ?>
 <br />
 <form method="post" action="update_desc.php">  
    <input type="hidden" name="name" value="<?php echo $event; ?>">
    <input type="hidden" name="desc" value="<?php echo $desc; ?>">
    <input type="submit" value="Update Description">
</form>

<b>South Latitude:</b> <?php echo $bl_lat; ?>
<br />
<b>West Longitude:</b> <?php echo $bl_long; ?>
<br />
<b>North Latitude:</b> <?php echo $tr_lat; ?>
<br />
<b>East Longitude:</b> <?php echo $tr_long; ?>
<br />

<form method="post" action="update_boundaries.php">
        
    
        <input type="hidden" name="bl_lat" value="<?php echo $bl_lat; ?>">
        <input type="hidden" name="bl_long" value="<?php echo $bl_long; ?>">
        <input type="hidden" name="tr_lat" value="<?php echo $tr_lat; ?>">
     <input type="hidden" name="tr_long" value="<?php echo $tr_long; ?>">

   
    <input type="hidden" name="name" value="<?php echo $event; ?>">

    <input type="submit" value="Update Boundaries">

</form>
<b>Category Tags:</b>
<br />
<list>
    <ul>
<?php
$tags = mysql_result($result, $i, "category");
$tag_array = preg_split ("/[\s,]+/", $tags);

$num_tags = count($tag_array);
if (strcmp($tags, "") == 0) {
	$num_tags = 0;
}
$j=0;
while ($j < $num_tags) {
$tag=$tag_array[$j];
$tag=trim($tag, "#");

?>
<li>
	<?php echo $tag; ?>
    <form method="post" action="delete_tag.php">
        
        <input type="hidden" name="name" value="<?php echo $event; ?>">
        <input type="hidden" name="tag" value="<?php echo $tag; ?>">
        <input type="submit" value="Delete Tag">
    </form>
</li>
<?php
    $j++;
}
?>
</ul>
</list>
<form method="post" action="add_tag.php">
        
        <input type="hidden" name="name" value="<?php echo $event; ?>">
        <input type="text" name="tag" >
        <input type="submit" value="Add Tag">
    </form>

<?php
    $i++;
}
?>

</body>
</html>
