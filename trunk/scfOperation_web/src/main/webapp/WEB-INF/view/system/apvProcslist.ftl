<#import "/account/layout.ftl" as layout>
<#escape x as x?html>
<@layout.page title="审批流列表">
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
	    审批流列表
	  </h1>
	  <ol class="breadcrumb">
	    <li><a href="${basePath}/account/index"><i class="fa fa-dashboard"></i>首页</a></li>
	    <li><a href="${basePath}/finance/list">系统管理</a></li>
	    <li class="active">审批流列表</li>
	  </ol>
	</section>
	<!-- Main content -->
	<section class="content">
	<#-- 审批流列表 开始 -->
          <div class="box">
               <div class="box-header">
                  	<#-- 查询 -->
					<form id="queryTeams" action="${basePath}/flowmanage/apvFlowProcslist" method="get">
							<div class="form-group">
								<label>审批流名称：</label>
								<input id="procsName" name="procsName" value="${afpqb.procsName!""}">
								<input type="submit" value="搜索">
								<@layout.security.authorize url="flowmanage/addProcsView">
								<input type="button" value="添加审批流" onclick = "javascript:goProcsAdd()">
								</@layout.security.authorize>
							</div>
					</form>
                </div>
                
                <div class="box-body">
                  <table id="file_list" class="table table-bordered table-hover">
                    <thead>
                      <tr>
                      	<th>序号</th>
						<th id="procsName" style="text-align:center">审批流名称</th>
						<th id="isValid" style="text-align:center">是否有效</th>
						<th id="procsDesc" style="text-align:center">审批流简介</th>
						<th id="foID" style="text-align:center">金融机构ID</th>
						<th id="fpID" style="text-align:center">金融产品ID</th>
						<th id="createAt" style="text-align:center">创建时间</th>
						<th id="operate" style="text-align:center">操作</th>
                      </tr>
                    </thead>
                    <tbody>
                    <#list tbData.list as procs>
						<tr>
							<td style="text-align:center">${procs_index+1}</td>
							<td style="text-align:center">${procs.procsName!""}</td>
							<td style="text-align:center" >
								<#if '${procs.isValid!""}'=='1'>
									是
								<#else>
									否
								</#if>
							</td>
							
							<td style="text-align:center">${procs.procsDesc!""}</td>
							<td style="text-align:center">${procs.foID!""}</td>
							<td style="text-align:center">${procs.fpID!""}</td>
							<td style="text-align:center">${procs.createAt!!?string("yyyy-MM-dd")}</td>
							<td style="text-align:center">
								<@layout.security.authorize url="flowmanage/apvFlowNodeList">
								<a href="${basePath}/flowmanage/apvFlowNodeList/${procs.procsID!!}" >查看审批节点</a>
								</@layout.security.authorize>
								<@layout.security.authorize url="flowmanage/editProcsView">
								<a href="${basePath}/flowmanage/editProcsView/${procs.procsID!!}">编辑</a>
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
</body>
<script src="${basePath}/jslib/remodal.js"></script>
<script src="${basePath}/jslib/template.js"></script>
<!-- date-range-picker -->
<script src="${basePath}/jslib/moment.min.js"></script>
<script src="${basePath}/stylelib/adminlte/plugins/daterangepicker/daterangepicker.js"></script>
<!-- bootstrap color picker -->
<script src="${basePath}/stylelib/adminlte/plugins/colorpicker/bootstrap-colorpicker.min.js"></script>
<script>
	function goProcsAdd(){
		window.location.href="${basePath}/flowmanage/addProcsView";
	}

</script> 
</section>
</div>
<#-- 审核流列表页面结束 -->
</@layout.page>
</#escape>