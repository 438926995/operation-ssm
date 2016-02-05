<#import "/account/layout.ftl" as layout>
<#escape x as x?html>
<@layout.page title="资源列表">
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
		<h1>资源列表</h1>
		<ol class="breadcrumb">
			<li><a href="${basePath}/account/index"><i class="fa fa-dashboard"></i>首页</a></li>
			<li><a href="${basePath}/user/list">用户管理</a></li>
			<li class="active">资源列表</li>
		</ol>
	</section>
	<!-- Main content -->
	<section class="content">
	<#-- 资源列表开始 -->
	<div class="box">
		<div class="box-header">
			<#-- 查询 -->
			<form action="${basePath}/resource/list" method="get">
			<div id="user_list_search" >
				<div class="form-group">
					<label>资源名称：</label>
					<input id="resourceName" name="resourceName" value="${rqb.resourceName!}" class="f-text">
					<input type="submit" value="搜索" >
					<@layout.security.authorize url="resource/add">
						<input type="button" value="添加资源" onclick="javascript:addResource();" >
					</@layout.security.authorize>
				</div>
			</div>
			</form>
		</div>
		
		<div class="box-body">
			<table id="resource_list" class="table table-bordered table-hover">
				<thead>
					<tr>
						<th id="resourceName">资源名称</th>
						<th id="resourceType">资源类型</th>
						<th id="resourceString">资源内容</th>
						<th id="grade">菜单级别</th>
						<th id="createdAt">添加时间</th>
						<th id="operate">操作</th>
					</tr>
				</thead>
				<tbody>
				<#list tbData.list as resource>
				<tr>
					<td>${resource.resourceName}</td>
					<td>${resource.resourceType}</td>
					<td>${resource.resourceString}</td>
					<td>
						<#if (resource.grade) == '1'>一级菜单<#elseif (resource.grade) == '2'>二级菜单<#elseif (resource.grade) == '3'>三级菜单</#if>
					</td>
					<td>${resource.createdAt?string('yyyy-MM-dd')}</td>
					<td style="text-align:left">
						<#-- <a href="javascript:detailResource(${resource.resourceId})">查看</a> -->
					<@layout.security.authorize url="resource/edit">
						<a href="javascript:editResource(${resource.resourceId})">编辑</a>
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
function addResource(){
	window.location.href="${basePath}/resource/add";
}

function editResource(resourceId){
	window.location.href="${basePath}/resource/edit/"+resourceId;
}
</script>
</body>
</@layout.page>
</#escape>