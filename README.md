### MyEasyWOL

------

本工具是一套远程唤醒辅助工具集。分客户端和服务端两部分。客户端可以安装在自己的手机上，服务端可以安装在nas、机顶盒、windows电脑，然后搭配公网IP,实现外网远程唤醒电脑。本工具永久无广告、永久免费使用。

------

##### 开发本工具的起因

由于本人的华为路由器不支持ARP功能，所以无法用WOL软件在外网远程唤醒家里的电脑。另外华为路由器app自带的唤醒功能非常不好用，当你有一大堆设备时,每次唤醒还要在一大堆设备中找到要唤醒的设备，体验非常不好。

后来用了小米智能插座和向日葵的远程开机插座来实现远程唤醒。但是我的电脑，需要断电10秒之后再来电才能远程开机。小米插座倒是可以用场景来实现，但是如果电脑开机着，不小心执行了场景，就会让电脑直接断电关机。向日葵的插座却是因为它自己程序设计的原因(断电间隔太短，用户又无法自主设置)导致无法远程开机。每次都要在app里断电10秒后，再在app里点击开机才能打开电脑。体验同样非常不好。

之后所以就有了开发本工具的念头。利用机顶盒或nas，再搭配公网ip，就可以实现远程开机。

------

##### 开源说明

由于本人不太会android和php开发，所以代码里采用了很多网上的代码，抱着人人为我，我为人人的想法，所以我将本项目开源了。本项目采用比较宽松的开源协议，个人使用完全免费。商用的话，需要告知本人。目前第一阶段我开源了手机端app，后续将开源机顶盒端app、Windows端程序。

------

##### 关于app

安卓端我是基于XUI示例程序开发的,所以源码中包含了很多示例代码,我没有做删除，有一点点冗余。app没有申请任何隐私权限，也保证不作恶，app每次启动时会去服务端检测新版本。app没有任何广告，完全免费开源。安卓端我是半路出家,代码写的乱，行家不要见笑。

------

##### 服务端(可选)

安装服务端后，搭配公网ip，就可以将客户端发来的开机信息，转发到服务端所在的局域网中,从而实现开机。如果不安装服务端，客户端也支持局域网唤醒，以及支持arp的路由器的外网唤醒。

目前成品中只包含了php程序,如需Windows版、机顶盒作为服务端,请发邮件联系我。

------

##### 客户端

客户端是用来方便管理远程唤醒的设备的。目前支持android版本，之后会支持windows版本。由于本人不懂ios开发，所以iphone版本不打算做支持。

------

##### Nas版安装步骤

1. 打开Server文件夹,复制里面的php文件夹到nas里(我是复制到Web共享文件夹下)。威联通需要在设置里开启Web服务器(默认是80端口,要外网访问，需要映射成其它端口),群晖我手头没有，大家可以百度下。

2. 打开浏览器，输入nas绑定的域名:Web服务器端口/php/index.php,看看能不能正常访问。如果能正常访问，则执行下一步。

3. 进入客户端安装步骤。


##### 安卓机顶盒版安装步骤

1. 打开Server文件夹,将【android机顶盒】文件夹里的apk文件安装到机顶盒，然后打开。
2. 在路由器中映射机顶盒的5000端口到外网任意端口A。使用绑定的域名:A作为服务端网址。
3. 进入客户端安装步骤。

##### 客户端安装步骤

1. 打开Client文件夹下的android文件夹，把里面的apk文件安装到手机上。

2. 打开手机app,选择中转服务器,点击右上角的+，然后名称随便输入，网址输入我们刚才服务端第二步的网址，成功标记留空即可，然后点击确定保存。

3. 然后到远程开机tab页，点击右上角的+，名称随便输入，MAC和IP就输入要唤醒的设备的，端口可留空不输入。服务器选择我们刚才保存的。然后点击确定保存。这样就完成了。

------
##### 界面预览

   ![](界面预览\1.png)
   ![](界面预览\2.png)