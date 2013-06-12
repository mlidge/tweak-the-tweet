<?php
	$eventname = $_POST['name'];
	$bl_lat = $_POST['bl_lat'];
	$bl_long = $_POST['bl_long'];
	$tr_lat = $_POST['tr_lat'];
	$tr_long = $_POST['tr_long'];
?>
<html>
<head>
<title>Update Event Boundaries</title>
</head>


<body>
<form method="post" action="update_db_boundaries.php">
<?php echo $eventname; ?>
<input type="hidden" name="name" value="<?php echo $eventname; ?>">
<br/>
South Latitude: <br/>
<input type="text" name="bl_lat" value="<?php echo $bl_lat; ?>">
<br />
West Longitude: <br/>
<input type="text" name="bl_long" value="<?php echo $bl_long; ?>">
<br/>
North Latitude: <br/>
<input type="text" name="tr_lat" value="<?php echo $tr_lat; ?>">
<br/>
East Longitude: <br/>
<input type="text" name="tr_long" value="<?php echo $tr_long; ?>">
<br/>
<input type="submit" value="Update Event Boundaries">

</form>
