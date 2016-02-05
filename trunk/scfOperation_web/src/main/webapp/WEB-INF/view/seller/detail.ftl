<#import "/account/layout.ftl" as layout>
<#escape x as x?html>
<@layout.page title="商户列表">
<head>
	<#-- 弹出框 -->
	<link rel="stylesheet" href="${basePath}/stylelib/remodal.css" >
	<link rel="stylesheet" href="${basePath}/stylelib/remodal-default-theme.css">
	<link rel="stylesheet" href="${basePath}/stylelib/jquery.fileupload.css">
	<link rel="stylesheet" href="${basePath}/stylelib/bootstrap/css/bootstrap-table.min.css">
	<link rel="stylesheet" href="${basePath}/stylelib/bootstrap/css/bootstrap-select.min.css">	
	<#-- Bootstrap 颜色选择器 -->
	<link rel="stylesheet" href="${basePath}/stylelib/adminlte/plugins/colorpicker/bootstrap-colorpicker.min.css">
	<#-- Bootstrap 日期选择器 -->
	<link rel="stylesheet" href="${basePath}/stylelib/adminlte/plugins/daterangepicker/daterangepicker-bs3.css">
	<style>
		.search-from .form-group label{
			width:33.33%;
			float: none;
			text-align: right;
			margin-right: 1rem;
		}
		.search-from .form-group input[type='text']{
			width:60%;
			float: none;
		}
		.search-from .operation button{
			margin-right: 1rem;
			float: right;
		}
		.exl-column-ul{
			text-align:left;
			-webkit-padding-start: 2rem;
		}
		
		.exl-column-ul li{
			 list-style-type:none;
			 display:inline-table;
			 padding:0.7rem;
		}
		
		.upload-alert-success-msg{
		     padding:0.7rem;
			 color: #3c763d;
	         border-color: #d6e9c6;
		}
		
		.upload-alert-info-msg{
			padding:0.7rem;
			color: #31708f;
			border-color: #bce8f1;
		}
		
		.upload-alert-error-msg{
		     padding:0.7rem;
			 color: #a94442;
	         border-color: #ebccd1;
		}

		#sellerInfoTab{
			padding: 2rem;
		}
		#sellerInfoTab .row{
			padding: 2rem 0rem 0rem 0rem;
			vertical-align: middle
		}
		#sellerInfoTab .row label:first-child{
			text-align: right;
			padding-right:1rem;
			font-size: 2rem;
		}
		#sellerInfoTab .row label:nth-child(2){
			padding-top: 0.3rem;
		}
		#tradeFlowTab{
			padding:1rem;
		}
		.operateDiv{
			width: 100%;
			text-align: right;
			padding:2rem 0rem;
		}
	</style>
</head>
<body>
	<div class="content-wrapper">
	<!-- Content Header (Page header) -->
		<section class="content-header">
			<h1>
			    商户详细信息
			</h1>
			<ol class="breadcrumb">
			    <li>
				    <a href="${basePath}/account/index"><i class="fa fa-dashboard"></i>首页</a>
			    </li>
			    <li>
				    <a href="${basePath}/seller/list">商户列表</a>
			    </li>
			    <li class="active">商户详细</li>
			</ol>
		</section>
		<!-- Main content -->
		<section class="content">
		<!-- 商户列表开始 -->
		  	<div class="box">
				<ul class="nav nav-tabs" id="myTab">
					<li class="active">
						<a data-target="#sellerInfoTab" data-toggle="tab">餐厅信息</a>
					</li>
					<li>
						<a data-target="#tradeFlowTab" data-toggle="tab">经营流水</a>
					</li>
				</ul>
				<div class="tab-content">
					<div class="tab-pane active" id="sellerInfoTab">
						<div class="row">
							<label class="col-xs-3 text">商家ID：</label>
							<label class="col-xs-8">${sellerInfo.seller_id!}</label>
						</div>
						<div class="row">
							<label class="col-xs-3">商家全称：</label>
							<label class="col-xs-8">${sellerInfo.seller_name!}</label>
						</div>
						<div class="row">
							<label class="col-xs-3">创建时间：</label>
							<label class="col-xs-8">${sellerInfo.created_at!}</label>
						</div>
						<div class="row">
							<label class="col-xs-3">店主：</label>
							<label class="col-xs-8">${sellerInfo.keeper_name!}</label>
						</div>
						<div class="row">
							<label class="col-xs-3">店主电话：</label>
							<label class="col-xs-8">${sellerInfo.keeper_phone!}</label>
						</div>
						<!-- <div class="row">
							<label class="col-xs-4">企业所有者身份证：</label>
							<label class="col-xs-8"></label>
						</div> -->
						<div class="row">
							<label class="col-xs-3">地址：</label>
							<label class="col-xs-8">${sellerInfo.address_text!}</label>
						</div>
						<!-- <div class="row">
							<label class="col-xs-4">法人：</label>
							<label class="col-xs-8"></label>
						</div> -->
						<div class="row">
							<label class="col-xs-3">描述：</label>
							<label class="col-xs-8">${sellerInfo.description!}</label>
						</div>
						<!-- <div class="row">
							<label class="col-xs-4">服务等级：</label>
							<label class="col-xs-8"></label>
						</div> -->
					</div>
					<div class="tab-pane" id="tradeFlowTab">
						<div class="operateDiv">
						<@layout.security.authorize url="seller/tradeFlow/detailList/export">
							<button type="button" class="btn btn-default" id="exportTradeFlowBtn">导出流水明细</button>
						</@layout.security.authorize>
						</div>
						<table id="tableTradeFlow" class="table table-bordered table-hover"></table>
					</div>
					<form id="exportTFExcel" action="${basePath}/seller/tradeFlow/detailList/export/${sellerInfo.seller_id!}" method="GET">
					</form>
				</div>
			</div>
		</section>
	</div>
</body>
<script src="${basePath}/jslib/remodal.js"></script>
<!-- date-range-picker -->
<script src="${basePath}/jslib/moment.min.js"></script>
<script src="${basePath}/jslib/fileupload/vendor/jquery.ui.widget.js"></script>
<script src="${basePath}/jslib/fileupload/jquery.iframe-transport.js"></script>
<script src="${basePath}/jslib/fileupload/jquery.fileupload.js"></script>
<script src="${basePath}/jslib/bootstrap-table.min.js"></script>
<script src="${basePath}/jslib/bootstrap-table-zh-CN.min.js"></script>
<script src="${basePath}/jslib/bootstrap-select.min.js"></script>
<script src="${basePath}/stylelib/adminlte/plugins/daterangepicker/daterangepicker.js"></script>
<!-- bootstrap color picker -->
<script src="${basePath}/stylelib/adminlte/plugins/colorpicker/bootstrap-colorpicker.min.js"></script>
<script type="text/javascript" >
	$(function(){
		<!--添加时间 Date range picker -->
		var sellers_page_num = 0;
		var limit = 10;
		$('#tableTradeFlow').bootstrapTable({
			columns : [{
				title : '日期',
				width : "150px",
				formatter : function(value, row, index){
					if(row.type == 1){
						return "合计";
					}else {
						return row.year + "-" + row.month;
					}
				}
			}, {
				field : "orderCount",
				title : "订单总数",
				width : "150px"
			}, {
				field : "sumTotal",
				title : "交易总额（元）",
				width : "120px"
			}, {
				field : "perDayTotal",
				title : "日均交易额（元）",
				width : "150px",
				formatter:function (value,row,index){
					if(row.type==1){
						return "";
					}else {
						return value;
					}
				}
			}],
			method : 'GET',
			url : "${basePath}/seller/tradeFlow/statisticList/${sellerInfo.seller_id}",
			contentType : 'application/x-www-form-urlencoded; charset=UTF-8',
			responseHandler : function(res) {
				return res.datas;
			},
			queryParams : function(params) {
				return params;
			},
			height : 430,
			onLoadError : function(status) {
				console.log("onLoadError:" + status);
			}
		});
		$("#exportTradeFlowBtn").click(function(){
			$("#exportTFExcel").submit();
		});
	})
</script>

</@layout.page>
</#escape>