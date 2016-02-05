<!DOCTYPE html>
<html>
	<head>
		<title>e金融-邮件</title>
		<meta http-equiv="content-type" content="text/html;charset=UTF-8" />
		<style>
			body{
				font-family:"Microsoft YaHei"
				font-size : 12em;
				padding-right: 2rem;
			}
			.footer{
				width: 100%;
				display:inline-flex;
				margin-top: 3rem;
			}
			.logo{
				padding-left: 1rem;
			}
			.logo img {
				width:70px;
			}
			.logo-text{
				padding-right: 1rem;
				padding-top:1rem;
			}
		</style>
	</head>
	<body>
		<div class="content">
			${mail.mailContent!}
		</div>
		<div class="footer"><!-- 
			<div class="logo">
				<img src="/tpl/mail/image/elogo.png"/>
			</div> -->
			<div class="logo-text">
				<p>---------------------------------------------------------------</p>
				<p>拉扎斯网络科技（上海）有限公司</p>
				<p>上海市普陀区金沙江路1518弄近铁城市广场401室</p>
 				<p>http://www.ele.me/</p>
 				<p>T:(021) 8024-1717 </p>
			</div>
		</div>
	</body>
</html>