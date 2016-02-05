<#import "/account/layout.ftl" as layout>
<#escape x as x?html>
<@layout.page title="权限列表">
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
		<h1>权限列表</h1>
		<ol class="breadcrumb">
			<li><a href="${basePath}/account/index"><i class="fa fa-dashboard"></i>首页</a></li>
			<li><a href="${basePath}/user/list">用户管理</a></li>
			<li class="active">权限列表</li>
		</ol>
	</section>
	<!-- Main content -->
	<section class="content">
	<#-- 权限列表开始 -->
	<div class="box">
		<div class="box-header">
			<#-- 查询 -->
			<form action="${basePath}/authorities/list" method="get">
			<div id="user_list_search" >
				<div class="form-group">
					<label>权限名称：</label>
					<input id="authName" name="authName" value="${aqb.authName!}" class="f-text">
					<input type="submit" value="搜索" >
					<@layout.security.authorize url="authorities/add">
						<input type="button" value="添加权限" onclick="javascript:addAuthorities();" >
					</@layout.security.authorize>
				</div>
			</div>
			</form>
		</div>
		
		<div class="box-body">
			<table id="authorities_list" class="table table-bordered table-hover">
				<thead>
					<tr>
						<th id="authName">权限名称</th>
						<th id="createdAt">添加时间</th>
						<th id="operate">操作</th>
					</tr>
				</thead>
				<tbody>
				<#list tbData.list as authorities>
				<tr>
					<td>${authorities.authName}</td>
					<td>${authorities.createdAt?string('yyyy-MM-dd')}</td>
					<td style="text-align:left">
						<#-- <a href="javascript:detailAuthorities(${authorities.authoritiesId})">查看</a> -->
					<@layout.security.authorize url="authorities/edit">
						<a href="javascript:editAuthorities(${authorities.authoritiesId})">编辑</a>
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
function addAuthorities(){
	window.location.href="${basePath}/authorities/add";
}
function editAuthorities(authoritiesId){
	window.location.href="${basePath}/authorities/edit/"+authoritiesId;
}
</script>
</body>
</@layout.page>
</#escape>