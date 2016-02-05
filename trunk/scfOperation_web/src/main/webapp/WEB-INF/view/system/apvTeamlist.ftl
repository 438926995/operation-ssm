<#import "/account/layout.ftl" as layout>
<#escape x as x?html>
<@layout.page title="审批组列表">
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
	    审批组列表
	  </h1>
	  <ol class="breadcrumb">
	    <li><a href="${basePath}/account/index"><i class="fa fa-dashboard"></i>首页</a></li>
	    <li><a href="${basePath}/finance/list">系统管理</a></li>
	    <li class="active">审批组列表</li>
	  </ol>
	</section>
	<!-- Main content -->
	<section class="content">
	<#-- 审批组列表 开始 -->
          <div class="box">
               <div class="box-header">
                  	<#-- 查询 -->
					<form id="queryTeams" action="${basePath}/flowmanage/apvFlowTeamlist" method="get">
							<div class="form-group">
								<label>Team名称：</label>
								<input id="teamName" name="teamName" value="${aftqb.teamName!""}">
								<input type="submit" value="搜索">
								<@layout.security.authorize url="flowmanage/addTeamView">
								<input type="button" value="添加审批组" onclick = "javascript:goTeamAdd()">
								</@layout.security.authorize>
							</div>
					</form>
                </div>
                
                <div class="box-body">
                  <table id="file_list" class="table table-bordered table-hover">
                    <thead>
                      <tr>
                      	<th style="text-align:center">序号</th>
						<th id="teamName" style="text-align:center">Team名称</th>
						<th id="appUserIds" style="text-align:center">用户ID</th>
						<th id="appUserNames" style="text-align:center">用户名</th>
						<th id="operate" style="text-align:center">操作</th>
                      </tr>
                    </thead>
                    <tbody>
                    <#list tbData.list as team>
						<tr>
							<td style="text-align:center">${team_index+1}</td>
							<td style="text-align:center">${team.teamName!""}</td>
							<td style="text-align:center">${team.appUserIds!""}</td>
							<td style="text-align:center">${team.appUserNames!""}</td>
							<td style="text-align:center">
								<@layout.security.authorize url="flowmanage/editTeamView">
								<a href="${basePath}/flowmanage/editTeamView/${team.ftId!""}">编辑</a>
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
	function goTeamAdd(){
  		window.location.href="${basePath}/flowmanage/addTeamView";
  	}
</script>
<#-- 审批组列表页面结束 -->
</section>
</div>


</@layout.page>
</#escape>