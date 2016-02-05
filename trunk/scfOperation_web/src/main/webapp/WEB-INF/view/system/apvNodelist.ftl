<#import "/account/layout.ftl" as layout>
<#escape x as x?html>
<@layout.page title="审批节点列表">
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
	  <h1>
	    审批节点列表
	  </h1>
	  <ol class="breadcrumb">
	    <li><a href="${basePath}/account/index"><i class="fa fa-dashboard"></i>首页</a></li>
	    <li><a href="${basePath}/finance/list">系统管理</a></li>
	    <li class="active">审批节点列表</li>
	  </ol>
	</section>
	<!-- Main content -->
	<section class="content">
	<#-- 金融超市审批节点列表 开始 -->
    <div class="box">
     <div class="box">
     	<div class="box-header">
     		<@layout.security.authorize url="flowmanage/addNodeView">
			<a href="${basePath}/flowmanage/addNodeView/${procsID!}">添加审批节点</a>
			</@layout.security.authorize>
        </div>
    
    	<div class="box-body">
        	<table id="file_list" class="table table-bordered table-hover">
            	<thead>
                	<tr>
                    	<th style="text-align:center">序号</th>
						<th id="nodeName" style="text-align:center">审批节点名称</th>
						<th id="parentID" style="text-align:center">上一节点ID</th>
						<th id="appUserIDs" style="text-align:center">审核人ID</th>
						<th id="appUserNames" style="text-align:center">审核人姓名</th>
						<th id="teamID" style="text-align:center">所属审批组ID</th>
						<th id="operate" style="text-align:center">操作</th>
                      </tr>
                </thead>
				<tbody>
                <#list afns as node>
					<tr>
						<td style="text-align:center">${node_index+1}</td>
						<td style="text-align:center">${node.nodeName!""}</td>
						<td style="text-align:center">${node.parentID!""}</td>
						<td style="text-align:center">${node.appUserIDs!""}</td>
						<td style="text-align:center">${node.appUserNames!""}</td>
						<td style="text-align:center">${node.teamID!""}</td>
						<td style="text-align:center">
							<@layout.security.authorize url="flowmanage/editNodeView">
							<a href="${basePath}/flowmanage/editNodeView/${node.nodeID!""}">编辑</a>
							</@layout.security.authorize>
						</td>
					</tr>
				</#list>
            	</tbody>
            </table>
        </div><!-- /.box-body -->
   </div><!-- /.box -->
</body>
<script src="${basePath}/jslib/remodal.js"></script>
<script src="${basePath}/jslib/template.js"></script>
<!-- date-range-picker -->
<script src="${basePath}/jslib/moment.min.js"></script>
<script src="${basePath}/stylelib/adminlte/plugins/daterangepicker/daterangepicker.js"></script>
<!-- bootstrap color picker -->
<script src="${basePath}/stylelib/adminlte/plugins/colorpicker/bootstrap-colorpicker.min.js"></script>
<script>

</script>
<#-- 审批节点列表页面结束 -->
</section>
</div>


</@layout.page>
</#escape>