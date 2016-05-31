<#global basePath="${request.getContextPath()}">
<#assign security=JspTaglibs["http://www.springframework.org/security/tags"] />
<#assign c=JspTaglibs["http://java.sun.com/jstl/core_rt"]>
<#macro page title>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <#--<meta property="csrf" csrf-token="${_csrf.token!}" csrf-param="${_csrf.parameterName!}"-->
          <#--csrf-header="${_csrf.headerName!}">-->
    <base id="basePath" href="${basePath}">
    <title>${title?html}</title>
<#-- Tell the browser to be responsive to screen width -->
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no"
          name="viewport">
<#-- Bootstrap 3.3.5 -->
    <link rel="stylesheet" href="${basePath}/stylelib/bootstrap/css/bootstrap.min.css">
<#-- Font Awesome -->
    <link rel="stylesheet" href="${basePath}/stylelib/fontawesome/css/font-awesome.min.css">
<#-- Ionicons -->
    <link rel="stylesheet" href="${basePath}/stylelib/ionicons/css/ionicons.min.css">
<#-- Theme style -->
    <link rel="stylesheet" href="${basePath}/stylelib/adminlte/css/AdminLTE.min.css">
<#-- AdminLTE Skins. -->
    <link rel="stylesheet" href="${basePath}/stylelib/adminlte/css/skins/_all-skins.min.css">
    <link rel="stylesheet" href="${basePath}/stylelib/bootstrap/css/bootstrap-select.min.css">
</head>
<body class="hold-transition skin-blue layout-boxed sidebar-mini">
<!-- Site wrapper -->
<div class="wrapper">
    <header class="main-header">
        <!-- Logo -->
        <a href="${basePath}/account/index" class="logo">
            <!-- mini logo for sidebar mini 50x50 pixels -->
            <span class="logo-mini">scf</span>
            <!-- logo for regular state and mobile devices -->
            <span class="logo-lg">用户管理系统</span>
        </a>
        <!-- Header Navbar: style can be found in header.less -->
        <nav class="navbar navbar-static-top" role="navigation">
            <!-- Sidebar toggle button-->
            <a href="#" class="sidebar-toggle" data-toggle="offcanvas" role="button">
                <span class="sr-only">Toggle navigation</span>
            </a>
            <div class="navbar-custom-menu">
                <ul class="nav navbar-nav">
                    <#assign user=Session["userinfo"]>
                    <!-- User Account: style can be found in dropdown.less -->
                    <li class="dropdown user user-menu">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                            <span class="hidden-xs">${user.userName}</span>
                        </a>
                        <ul class="dropdown-menu">
                            <!-- Menu Footer-->
                            <li class="user-footer">
                                <div class="pull-left">
                                    <a href="${basePath}/user/changePwd"
                                       class="btn btn-default btn-flat">修改密码</a>
                                </div>
                                <div class="pull-right">
                                    <a href="${basePath}/logout"
                                       class="btn btn-default btn-flat">退出</a>
                                </div>
                            </li>
                        </ul>
                    </li>
                </ul>
            </div>
        </nav>
    </header>

    <!-- =============================================== -->

    <!-- Left side column. contains the sidebar -->
    <aside class="main-sidebar">
        <!-- sidebar: style can be found in sidebar.less -->
        <section class="sidebar">
            <!-- sidebar menu: : style can be found in sidebar.less -->
            <ul class="sidebar-menu">
                <li class="treeview">
                    <a href="${basePath}/account/index">
                        <i class="fa fa-dashboard"></i>
                        <span>首页</span>
                        <i class="fa fa-angle-left pull-right"></i>
                    </a>
                </li>
                <#if userMenuTree??>
                    <#list userMenuTree.root as parent>
                        <li class="treeview<#if parent.selected> active</#if>">
                            <a href="javascript:;">
                                <i class="fa fa-files-o"></i>
                                <span>${parent.resourceName}</span>
                                <!-- <span class="label label-primary pull-right">2</span> -->
                            </a>
                            <ul class="treeview-menu">
                                <#if parent.children?? && parent.children?size gt 0>
                                    <#list parent.children as child>
                                        <li<#if child.selected> class="active"</#if>><a
                                                href="${basePath}/${child.resourceString}"><i
                                                class="fa fa-circle-o"></i>${child.resourceName}</a>
                                        </li>
                                    </#list>
                                </#if>
                            </ul>
                        </li>
                    </#list>
                </#if>
            </ul>
        </section>
        <!-- /.sidebar -->
    </aside>

    <!-- =============================================== -->

<#-- jQuery 2.1.4  -->
    <script src="${basePath}/jslib/jquery.js"></script>
<#-- Bootstrap 3.3.5 -->
    <script src="${basePath}/jslib/bootstrap.js"></script>
<#-- SlimScroll -->
    <script src="${basePath}/stylelib/adminlte/plugins/slimScroll/jquery.slimscroll.min.js"></script>
<#-- FastClick -->
    <script src="${basePath}/stylelib/adminlte/plugins/fastclick/fastclick.min.js"></script>
<#-- AdminLTE App -->
    <script src="${basePath}/stylelib/adminlte/js/app.min.js"></script>
<#-- -->
    <script src="${basePath}/jslib/bootstrap-select.min.js"></script>
    <#--<script src="${basePath}/jslib/csrf/csrf.js"></script>-->
    <script type="text/javascript">
        var basePath = "${basePath}/";
    </script>
<#-- body内容容器 -->
    <#nested>

    <!-- ./wrapper -->

</body>
</html>
</#macro>
