<?php
	$eventname = $_POST['old_name'];
	$eventname = trim($eventname, "#");
?>
<html>
<head>
<title>Rename Event</title>
</head>


<body>
<form method="post" action="rename_db_event.php">
Event Name: <br/>
<input type="text" name="name" value="<?php echo $eventname; ?>">
<input type="hidden" name="old_name" value="<?php echo $eventname; ?>">

<input type="submit" value="Rename Event">

</form>

