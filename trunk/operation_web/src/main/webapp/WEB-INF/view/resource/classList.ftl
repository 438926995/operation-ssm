<#import "/account/layout.ftl" as layout>
<#escape x as x?html>
<@layout.page title="资源组列表">
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
		<h1>资源组列表</h1>
		<ol class="breadcrumb">
			<li><a href="${basePath}/account/index"><i class="fa fa-dashboard"></i>首页</a></li>
			<li><a href="${basePath}/user/list">用户管理</a></li>
			<li class="active">资源组列表</li>
		</ol>
	</section>
	<!-- Main content -->
	<section class="content">
	<#-- 资源列表开始 -->
	<div class="box">
		<div class="box-header">
			<#-- 查询 -->
			<form action="${basePath}/resource/class/list" method="get">
			<div id="user_list_search" >
				<div class="form-group">
					<label>资源组名称：</label>
					<input id="className" name="className" value="${rqb.className!}" class="f-text">
					<input type="submit" value="搜索" >
					<@layout.security.authorize url="resource/class/add">
						<input type="button" value="添加资源组" onclick="javascript:addResourceClass();" >
					</@layout.security.authorize>
				</div>
			</div>
			</form>
		</div>
		
		<div class="box-body">
			<table id="resouce_list" class="table table-bordered table-hover">
				<thead>
					<tr>
						<th id="className">资源组名称</th>
						<th id="createdAt">添加时间</th>
						<th id="operate">操作</th>
					</tr>
				</thead>
				<tbody>
				<#list tbData.list as resouceClass>
				<tr>
					<td>${resouceClass.className}</td>
					<td>${resouceClass.createdAt?string('yyyy-MM-dd')}</td>
					<td style="text-align:left">
						<!-- <a href="javascript:detailResourceClass('${(resouceClass.classId)!}')">查看</a> -->
					<@layout.security.authorize url="resource/class/edit">
						<a href="javascript:editResourceClass('${(resouceClass.classId)!}')">编辑</a>
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
function addResourceClass(){
	window.location.href="${basePath}/resource/class/add";
}

function editResourceClass(resourceClassId){
	window.location.href="${basePath}/resource/class/edit/"+resourceClassId;
}
</script>
</body>
</@layout.page>
</#escape>