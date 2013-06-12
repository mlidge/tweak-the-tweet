
<?php
	$eventname = $_POST['name'];
	$desc = $_POST['desc'];
?>
<html>
<head>
<title>Update Event Description</title>
</head>


<body>
<form method="post" action="update_db_desc.php">
<?php echo $eventname; ?>
<input type="hidden" name="name" value="<?php echo $eventname; ?>">
<br/>
Description:
<br/>

<textarea name="desc" rows=10 cols=100><?php echo $desc; ?></textarea>

<br />
<input type="submit" value="Update Event Description">

</form>
