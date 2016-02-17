<#assign basePath="${request.getContextPath()}"> 
<#assign security=JspTaglibs["http://www.springframework.org/security/tags"] /> 
<#escape x as x?html>
<!DOCTYPE html>
<html lang="zh-CN">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <#-- Tell the browser to be responsive to screen width -->
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <#--
    <meta name="description" content="供应链金融系统">
    <meta name="author" content="penglau">
    <link rel="icon" href="${bathPath}/favicon.ico">
    -->
    <base id="basePath" href="${basePath}">
    <title>供应链金融系统</title>
    <#-- Bootstrap -->
    <link rel="stylesheet" href="${bathPath!"/sc_finance_pc_web"}/stylelib/bootstrap/css/bootstrap.min.css">
	<#-- Font Awesome -->
    <link rel="stylesheet" href="${bathPath!"/sc_finance_pc_web"}/stylelib/fontawesome/css/font-awesome.min.css">
    <#-- Ionicons -->
    <link rel="stylesheet" href="${bathPath!"/sc_finance_pc_web"}/stylelib/ionicons/css/ionicons.min.css">
    <#-- Theme style -->
    <link rel="stylesheet" href="${bathPath!"/sc_finance_pc_web"}/stylelib/adminlte/css/AdminLTE.min.css">
    <#-- iCheck -->
    <link rel="stylesheet" href="${bathPath!"/sc_finance_pc_web"}/stylelib/adminlte/plugins/iCheck/square/blue.css">
    <#-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <#-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <#--[if lt IE 9]>
      <script src="//cdn.bootcss.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="//cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
  </head>
  <body class="hold-transition login-page">

	<div class="content-wrapper">
		<#-- Main content -->
		<section class="content">
			<div class="error-page">
	            <div class="error-content">
	              <h3><i class="fa fa-warning text-red"></i> Oops! 会话关闭.</h3>
	              <p>
	                <a href="${basePath!"/sc_finance_pc_web"}/logout">退出系统</a> .
	              </p>
	            </div>
	      	</div>
		</section>
	</div>
    
    <#-- jQuery 2.1.4  -->
    <script src="${bathPath!"/sc_finance_pc_web"}/jslib/jquery.js"></script>
    <#-- Bootstrap 3.3.5 -->
    <script src="${bathPath!"/sc_finance_pc_web"}/jslib/bootstrap.js"></script>
    <#-- iCheck -->
    <script src="${bathPath!"/sc_finance_pc_web"}/stylelib/adminlte/plugins/iCheck/icheck.min.js"></script>
    <script>
      $(function () {
        $('input').iCheck({
          checkboxClass: 'icheckbox_square-blue',
          radioClass: 'iradio_square-blue',
          increaseArea: '20%' // optional
        });
      });
    </script>
  </body>
</html>
</#escape>