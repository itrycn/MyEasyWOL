<?php
if ($_POST['a']=="magic")
{
	$port=20231;
	if (isset($_POST['port']))
	{
		$port=intval($_POST['port']);
	}
    include("WOL.php");
	$WOL = new WOL($_POST['ip'],$_POST['mac'],$port);
	$status = $WOL->wake_on_wan();
	if((strpos($status,'开机')>=0) || (strpos($status,'发送成功')>=0))
	{
	   echo "ok";
	}
	else
	{
	   echo $status;
	}
}
else
{
	echo "0";
}
?>