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
    <@security.csrfMetaTags/>  
    <#--
    <meta name="description" content="供应链金融系统">
    <link rel="icon" href="${bathPath}/favicon.ico">
    -->
    <base id="basePath" href="${basePath}">
    <title>供应链金融系统</title>
    <#-- Bootstrap -->
    <link rel="stylesheet" href="${basePath}/stylelib/bootstrap/css/bootstrap.min.css">
	<#-- Font Awesome -->
    <link rel="stylesheet" href="${basePath}/stylelib/fontawesome/css/font-awesome.min.css">
    <#-- Ionicons -->
    <link rel="stylesheet" href="${basePath}/stylelib/ionicons/css/ionicons.min.css">
    <#-- Theme style -->
    <link rel="stylesheet" href="${basePath}/stylelib/adminlte/css/AdminLTE.min.css">
    <#-- 表单美化插件 -->
    <link rel="stylesheet" href="${basePath}/stylelib/adminlte/plugins/iCheck/square/blue.css">
    <#-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <#-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <#--[if lt IE 9]>
      <script src="//cdn.bootcss.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="//cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
  </head>
  <body class="hold-transition login-page">

	<div class="login-box">
      <div class="login-box-body">
        <p class="login-box-msg">供应链金融后台</p>
        <font color='red' id="login_error">${loginError!""}</font>
        <form id="loginForm" action="${basePath}/login" method="post" data-parsley-validate="">
          <div class="form-group has-feedback">
            <input type="email" class="form-control" placeholder="Email" id="username" name="username"  required data-parsley-trigger="change"  data-parsley-maxlength="100">
            <span class="glyphicon glyphicon-envelope form-control-feedback"></span>
          </div>
          <div class="form-group has-feedback">
            <input type="password" class="form-control" placeholder="Password" id="password" name="password" required data-parsley-trigger="change"  data-parsley-maxlength="100" >
            <span class="glyphicon glyphicon-lock form-control-feedback"></span>
          </div>
	        <div>
		        <input type="text" class="form-control" style="display:inline-block;width:50%" name="code" placeholder="验证码" required>&nbsp;&nbsp;
		        <img src="${basePath}/account/captcha" onclick="this.src='${basePath}/account/captcha?t='+new Date()*1" height="30" width="70" />
		    </div>
		  
          <div class="row">
            <div class="col-xs-8">
              <div class="checkbox icheck">
                <label>
                  <input type="checkbox" id="remember-me" name="remember-me" value="true" > Remember Me
                </label>
              </div>
              <@security.csrfInput/>  
            </div><!-- /.col -->
            <div class="col-xs-4">
              <button type="submit" class="btn btn-primary btn-block btn-flat">登录</button>
            </div><!-- /.col -->
          </div>
        </form>
      </div><!-- /.login-box-body -->
    </div><!-- /.login-box -->
    
    <#-- jQuery 2.1.4  -->
    <script src="${basePath}/jslib/jquery.js"></script>
    <#-- Bootstrap 3.3.5 -->
    <script src="${basePath}/jslib/bootstrap.js"></script>
    <#-- iCheck -->
    <script src="${basePath}/stylelib/adminlte/plugins/iCheck/icheck.min.js"></script>
    <#-- parsley -->
    <script src="${basePath}/jslib/parsley/parsley.min.js"></script>
	<script src="${basePath}/jslib/parsley/parsley.js"></script>
	<script src="${basePath}/jslib/parsley/i18n/zh_cn.js"></script>
	<link rel="stylesheet" href="${basePath}/stylelib/parsley.css">
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