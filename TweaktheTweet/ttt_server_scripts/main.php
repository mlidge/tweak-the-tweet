<?php

mysql_connect("128.208.219.182","ttt","water2014");
mysql_select_db("tweak_the_tweet");
                 
$q=mysql_query("SELECT * FROM Coordinates");

if (!$q) {
  die('Invalid query: ' . mysql_error());
}

while($e=mysql_fetch_assoc($q))
  $output[]=$e;
  
print(json_encode($output));
                 
mysql_close();
?>