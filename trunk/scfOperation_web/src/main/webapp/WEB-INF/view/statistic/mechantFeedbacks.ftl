<#import "/account/layout.ftl" as layout>
<#escape x as x?html>
<@layout.page title="统计数据">
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
	    用户反馈统计
	  </h1>
	  <ol class="breadcrumb">
	    <li><a href="${basePath}/account/index"><i class="fa fa-dashboard"></i>首页</a></li>
	    <li><a href="${basePath}/statistic/mechantFeedbacks">用户反馈统计</a></li>
	    <li class="active">商家平台反馈统计</li>
	  </ol>
	</section>
	<!-- Main content -->
	<section class="content">

	<!-- 反馈信息 -->
	<div><h5>&emsp;商家平台反馈<h5></div>
	<div class="box">
		<div class="box-body">
			<div id="feedbackInfo" class="nav nav-tabs">
   				<#-- 时间选项卡 -->
				<form action="${basePath}/statistic/mechantFeedbacks" method="get" id="queryFeedback">
					<div id="timePicker1" name="timePicker" style="float:right;margin:10px">
   						<input class="submitTimeFrom" name="submitTimeFrom"
   							<#if ufqb.submitTimeFrom??> 
								value="${(ufqb.submitTimeFrom!!?string('yyyy-MM-dd'))!}"
							</#if>  required readonly="readonly" >
						至
						<input class="submitTimeEnd" name="submitTimeEnd" 
							<#if ufqb.submitTimeEnd??> 
								value="${(ufqb.submitTimeEnd!!?string('yyyy-MM-dd'))!}"
							</#if> required readonly="readonly" >
						
						<button type="button" class="btn btn-primary" id="search" style="margin-left:10px">搜索</button>
   					</div>
   				</form>

				<#-- table -->
   				<table id="feedback_list" class="table table-bordered table-hover">
        			<thead align="center">
        				<tr>
        					<th style="text-align:center">用户名</th>
        					<th style="text-align:center">ip</th>
        					<th style="text-align:center">提交时间</th>
        					<th style="text-align:center">反馈</th>
        				</tr>
        			</thead>
        		
                    <tbody>
                    <#--feedback data list-->
                    <#list tbData.list as feedback>
						<tr id="fb">
							<td style="text-align:center">${feedback.userName}</td>
							<td style="text-align:center">${feedback.userIp!""}</td>
							<td style="text-align:center">${feedback.createdAt!""}</td>
							<td style="text-align:center">${feedback.userFeedback!""}</td>
						</tr>
					</#list>
				</tbody>
			</table>
  		 </div><#--box-body end-->
                
  		 <div id = "feedbackpager" class="box-footer">
				<#include "/common/pager_bar.ftl">
		 </div>
  	</div><#--box end-->
</div>
        	
</body>
<script src="${basePath}/jslib/remodal.js"></script>
<script src="${basePath}/jslib/template.js"></script>
<!-- date-range-picker -->
<script src="${basePath}/jslib/moment.min.js"></script>
<script src="${basePath}/stylelib/adminlte/plugins/daterangepicker/daterangepicker.js"></script>
<!-- bootstrap color picker -->
<script src="${basePath}/stylelib/adminlte/plugins/colorpicker/bootstrap-colorpicker.min.js"></script>

</section>
</div>

<script>
<#--添加时间 Date range picker -->
	$('.submitTimeFrom').daterangepicker({
		format: 'YYYY-MM-DD',
      	singleDatePicker: true
	});
	$('.submitTimeEnd').daterangepicker({
		format: 'YYYY-MM-DD',
      	singleDatePicker: true
	});
	
	<#--根据时间查询 提交表单-->
	$('#search').click(function(){ 
		$('#queryFeedback').submit();
	});
	
</script>
</@layout.page>
</#escape>