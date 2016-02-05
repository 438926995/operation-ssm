<#import "/account/layout.ftl" as layout>
<#escape x as x?html>
<@layout.page title="权限模块列表">
<head>
	<#-- 弹出框 -->
	<link rel="stylesheet" href="${basePath}/stylelib/remodal.css">
	<link rel="stylesheet" href="${basePath}/stylelib/remodal-default-theme.css">
	<#-- Bootstrap 颜色选择器 -->
	<link rel="stylesheet" href="${basePath}/stylelib/adminlte/plugins/colorpicker/bootstrap-colorpicker.min.css">
	<#-- Bootstrap 日期选择器 -->
	<link rel="stylesheet" href="${basePath}/stylelib/adminlte/plugins/daterangepicker/daterangepicker-bs3.css">
</head>
<body>
<div class="content-wrapper">
	<!-- Content Header (Page header) -->
	<section class="content-header">
		<h1>权限模块列表</h1>
		<ol class="breadcrumb">
			<li><a href="${basePath}/account/index"><i class="fa fa-dashboard"></i>首页</a></li>
			<li><a href="${basePath}/user/list">用户管理</a></li>
			<li class="active">权限模块列表</li>
		</ol>
	</section>
	<!-- Main content -->
	<section class="content">
	<#-- 权限列表开始 -->
	<div class="box">
		<div class="box-header">
			<#-- 查询 -->
			<form action="${basePath}/authorities/module/list" method="get">
			<div id="user_list_search" >
				<div class="form-group">
					<label>权限模块名称：</label>
					<input id="authModuleName" name="authModuleName" value="${aqb.authModuleName!}" class="f-text">
					<input type="submit" value="搜索" >
					<@layout.security.authorize url="authorities/module/add">
						<input type="button" value="添加权限模块" onclick="javascript:addAuthoritiesModule();" >
					</@layout.security.authorize>
				</div>
			</div>
			</form>
		</div>
		
		<div class="box-body">
			<table id="authorities_list" class="table table-bordered table-hover">
				<thead>
					<tr>
						<th id="authModuleName">权限模块名称</th>
						<th id="createdAt">添加时间</th>
						<th id="operate">操作</th>
					</tr>
				</thead>
				<tbody>
				<#list tbData.list as authoritiesModule>
				<tr>
					<td>${authoritiesModule.authModuleName}</td>
					<td>${authoritiesModule.createdAt?string('yyyy-MM-dd')}</td>
					<td style="text-align:left">
						<!-- <a href="javascript:detailAuthoritiesModule('${(authoritiesModule.authModuleId)!}')">查看</a> -->
					<@layout.security.authorize url="authorities/module/edit">
						<a href="javascript:editAuthoritiesModule('${(authoritiesModule.authModuleId)!}')">编辑</a>
					</@layout.security.authorize>
					</td>
				</tr>
				</#list>
				</tbody>
			</table>
		</div><!-- /.box-body -->
		<div id = "userpager" class="box-footer">
			<#include "/common/pager_bar.ftl">
		</div>
	</div><!-- /.box -->
 	<#-- 用户页面结束 -->
	</section>
</div>
<script src="${basePath}/jslib/remodal.js"></script>
<!-- date-range-picker -->
<script src="${basePath}/jslib/moment.min.js"></script>
<script src="${basePath}/stylelib/adminlte/plugins/daterangepicker/daterangepicker.js"></script>
<!-- bootstrap color picker -->
<script src="${basePath}/stylelib/adminlte/plugins/colorpicker/bootstrap-colorpicker.min.js"></script>
<script>
function addAuthoritiesModule(){
	window.location.href="${basePath}/authorities/module/add";
}

function editAuthoritiesModule(authModuleId){
	window.location.href="${basePath}/authorities/module/edit/"+authModuleId;
}
</script>
</body>
</@layout.page>
</#escape>