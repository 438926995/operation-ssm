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
	    贷款审核统计
	  </h1>
	  <ol class="breadcrumb">
	    <li><a href="${basePath}/account/index"><i class="fa fa-dashboard"></i>首页</a></li>
	    <li><a href="${basePath}/statistic/list">贷款审核统计</a></li>
	    <li class="active">贷款审核统计</li>
	  </ol>
	</section>
	<!-- Main content -->
	<section class="content">
	<!-- 关键指标 -->
		<div class="box">
		<div class="box-body">
		<table class="table table-bordered table-hover">
		<thead>
			<tr>
				<th colspan="4">关键指标（总计）</td>
			</tr>
		<thead>
		
		<tbody>
			<tr>
				<td align="center">提交申请人数</td>
				<td align="center">通过人数</td>
				<td align="center">未通过人数</td>
				<td align="center">审核中人数</td>
			</tr>
			<tr>
				<td align="center">${loanNumber.sumNumber?string(',###')}</td>
				<td align="center">${loanNumber.passNumber?string(',###')}</td>
				<td align="center">${loanNumber.disPassNumber?string(',###')}</td>
				<td align="center">${loanNumber.auditNumber?string(',###')}</td>		
			</tr>
		</tbody>
		
    	</table>
    	</div>
	<!-- 趋势图 -->
		<div class="box">
        	<div class="box-header">
        		<#-- 查询 -->
				<form action="${basePath}/statistic/list" method="get" id="queryLoan">
				<table width="100%" class="table">
					<tr>
						<td width="30%"><lable style="font-weight:bold;">关键指标详解</lable></td>
						<td><input id="seven" name="seven" type="button" style="width:80px;" value="7日" onclick="getData(7);">
						<input id="fourteen" name="fourteen" type="button" style="width:80px;" value="14日" onclick="getData(14);">
						<input id="thirty" name="thirty" type="button" style="width:80px;" value="30日" onclick="getData(30);"></td>
						<td>
						<input type="hidden" name="period" id="period" value="" >
						<label>时间：</label>&nbsp;
						<input id="submitTimeFrom" name="submitTimeFrom" <#if lqb.submitTimeFrom??>value="${(lqb.submitTimeFrom!!?string('yyyy-MM-dd'))!}"</#if>  required readonly="readonly" >
				至
				<input id="submitTimeEnd" name="submitTimeEnd" <#if lqb.submitTimeEnd??> value="${(lqb.submitTimeEnd!!?string('yyyy-MM-dd'))!}"</#if> required readonly="readonly" >
				<br></td>
					</tr>
				</table>
				
				<div>
				<table>
					<tr>
					<td width="60%"></td>
					<td>
				<lable>贷款产品: </lable>&nbsp;
				<select name="fpId" id="fpId" >
					<option value=""  <#if lqb.fpId?? && "${lqb.fpId}"=="">selected="selected"</#if> >全部</option>
						<#list productList as product>
							<option value="${product.fp_id?string}" <#if lqb.fpId?? && "${lqb.fpId}"=="${product.fp_id?string}">selected="selected"</#if> >${product.fp_name}</option>
						</#list>
				</select>
					</td>
					<td width="3%"></td>
					<td><input type="submit" style="width:80px;" value="搜索"></td>
					</tr>
				</table>
				</div>
				</from>
			</div>
			<#-- 趋势图 -->
			<div>
				<lable id="chartLable"><h5>&emsp;趋势图</h5></lable>
				<!-- 为ECharts准备一个具备大小（宽高）的Dom -->
				<div id="echarts" style="height: 400px"></div>
			</div>
		</div>
	<!-- 详细数据 -->
		<div><h5>&emsp;详细数据<h5></div>
		<div align="right">
		<@layout.security.authorize url="statistic/exportExcel">
			<input type="button" class="btn btn-primary" value="导出Excel" onclick="exportInfo();" />&emsp;
		</@layout.security.authorize>
		</div>
		<div id="messageList" class="box-body">
        	<table id="audit_list" class="table table-bordered table-hover">
        		<thead align="center">
        			<tr>
        				<th>时间</th>
        				<th>提交申请人数</th>
        				<th>通过人数</th>
        				<th>未通过人数</th>
        				<th>审核中人数</th>
        			</tr>
        		</thead>
        		<tbody>
                    <#list tbData.list as audit>
                    	<tr>
                    		<td align="center">${audit.submitTime!!?string("yyyy-MM-dd")}</td>
                    		<td align="center">${audit.count?string(',###')}</td>
                    		<td align="center">${audit.passNumber?string(',###')}</td>
                    		<td align="center">${audit.disPassNumber?string(',###')}</td>
                    		<td align="center">${audit.auditNumber?string(',###')}</td>
                    	</tr>
                    </#list>
        	</table>
        </div><!-- /.box-body -->
        <div id = "productpager" class="box-footer">
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

<#-- 商户贷款管理页面结束 -->
</section>
</div>
<#-- Echarts  趋势图 -->
<script src="http://echarts.baidu.com/build/dist/echarts.js"></script>
<script type="text/javascript">
		// 路径配置
		require.config({
			paths : {
				echarts : 'http://echarts.baidu.com/build/dist'
			}
		});
		// 使用
		require([ 'echarts', 'echarts/chart/line'], function(ec) {
			// 基于准备好的dom，初始化echarts图表
			var myChart = ec.init(document.getElementById('echarts'));
			option = {
   	 			tooltip : {
        			trigger: 'axis'
    				},
    			legend: {
        			data:['提交申请人数','通过人数','未通过人数','审核中人数']
    				},
    			
    			calculable : true,
    			xAxis : [
       			 {
            	type : 'category',
           	 	boundaryGap : false,
            	data : <#noescape>${opt.xAisData}</#noescape>
        		}],
    			yAxis : [
       			{
           		 type : 'value'
        		}],
    			series : [
        		{
            		name:'提交申请人数',
            		type:'line',
            		data:${opt.seriesData[0]}
        		},
        		{
            		name:'通过人数',
            		type:'line',
            		data:${opt.seriesData[1]}
        		},
        		{
            		name:'未通过人数',
            		type:'line',
            		data:${opt.seriesData[2]}
        		},
        		{
            		name:'审核中人数',
            		type:'line',
            		data:${opt.seriesData[3]}
        		}
    		]
		};
			// 为echarts对象加载数据 
			myChart.setOption(option);
		});
</script>

<script>
<#--添加时间 Date range picker -->
	$('#submitTimeFrom').daterangepicker({
		format: 'YYYY-MM-DD',
      	singleDatePicker: true
	});
	$('#submitTimeEnd').daterangepicker({
		format: 'YYYY-MM-DD',
      	singleDatePicker: true
	});
<#-- 点击时间段按钮查询数据 -->	
	function getData(period){
	  	var formObject = document.getElementById("queryLoan");
		$('#period').val(period);
		formObject.submit();
	}
<#-- Excel导出关键指标的详细数据 -->
	function exportInfo(){
		var formObject = document.getElementById("queryLoan");
		formObject.action = basePath + "statistic/exportExcel";
		formObject.submit();
		formObject.action = basePath + "statistic/list";
	}
</script>
</@layout.page>
</#escape>