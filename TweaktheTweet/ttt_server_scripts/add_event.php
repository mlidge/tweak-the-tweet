<html>
<head>
<title>Add Event</title>
</head>


<body>
<form method="post" action="insert_event.php">
Event Name: <br/>
<input type="text"  name="name">
<br/>
South Latitude: <br/>
<input type="text"  name="bl_lat">
<br/>
West Longitude: <br/>
<input type="text"  name="bl_long">
<br/>
North Latitude: <br/>
<input type="text"  name="tr_lat">
<br/>
East Longitude: <br/>
<input type="text" name="tr_long">
<br/>

Event Tags (each on its own line):
<br />
<textarea name="tags" rows=10 cols=30></textarea>
<br />
Description of Event: 
<br />
<textarea name="desc" rows=10 cols=100></textarea>
<br/>


<input type="submit" value="Create New Event">

</form>
