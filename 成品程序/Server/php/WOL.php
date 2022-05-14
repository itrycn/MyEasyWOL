<?php
/**
* 实现网络唤醒功能
*/
class WOL
{
    private $hostname;    // 唤醒设备的url地址
    private $mac;         // 唤醒设备的mac地址
    private $port;        // 唤醒设备的端口
    private $ip;          // 唤醒设备的ip地址(不是必须的,程序会自动根据$hostname来获取对应的ip)
 
    private $msg = array(
        0 => "目标机器已经是开机状态的.",
        1 => "socket_create 方法执行失败",
        2 => "socket_set_option 方法执行失败",
        3 => "magic packet 发送成功!",
        4 => "magic packet 发送成功!"
    );
     
    function __construct($hostname,$mac,$port,$ip = false)
    {
        $this->hostname = $hostname;
        $this->mac      = $mac;
        $this->port     = $port;
        if (!$ip)
        {
            $this->ip   = $this->get_ip_from_hostname();
        }
    }
 
    public function wake_on_wan()
    {
        if ($this->is_awake())
        {
            return $this->msg[0]; // 如果设备已经是唤醒的就不做其它操作了
        }
        else
        {
            $addr_byte = explode(':', $this->mac);
            $hw_addr = '';
            for ($a=0; $a<6; $a++) $hw_addr .= chr(hexdec($addr_byte[$a]));
            $msg = chr(255).chr(255).chr(255).chr(255).chr(255).chr(255);
            for ($a=1; $a<=16; $a++) $msg .= $hw_addr;
            // 通过 UDP 发送数据包
            $s = socket_create(AF_INET, SOCK_DGRAM, SOL_UDP);
             
            if ($s == false)
            {
                return $this->msg[1]; // socket_create 执行失败
            }
 
            $set_opt = @socket_set_option($s, 1, 6, TRUE);
 
            if ($set_opt < 0)
            {
                return $this->msg[2]; // socket_set_option 执行失败
            }
 
            $sendto = @socket_sendto($s, $msg, strlen($msg), 0, $this->ip, $this->port);
             
            if ($sendto)
            {
                socket_close($s);
                return $this->msg[3]; // magic packet 发送成功!
            }
 
            return $this->msg[4]; // magic packet 发送失败!
             
        }
    }
 
    private function is_awake()
    {
        $awake = @fsockopen($this->ip, 80, $errno, $errstr, 2);
         
        if ($awake)
        {
            fclose($awake);
        }
         
        return $awake;
    }
 
    private function get_ip_from_hostname()
    {
        return gethostbyname($this->hostname);
    }
 
}
?>