<#import "/account/layout.ftl" as layout>
<#escape x as x?html>
<@layout.page title="意向商户列表">
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
	    意向商户列表
	  </h1>
	  <ol class="breadcrumb">
	    <li><a href="${basePath}/account/index"><i class="fa fa-dashboard"></i>首页</a></li>
	    <li><a href="${basePath}/seller/list">商户管理</a></li>
	    <li class="active">意向商户列表</li>
	  </ol>
	</section>
	<!-- Main content -->
	<section class="content">
	<#-- 意向商户列表开始 -->

              <div class="box">
                <div class="box-header">
                  	<#-- 查询 -->
					<form action="${basePath}/seller/purposelist" method="get" id="queryPurposeSeller">
						<div id="purposeseller_search"  >
							<div class="form-group">
								<label>商户名称：</label>
								<input name="sellerName" value="${psqb.sellerName!""}" class="f-text" id="sellerName">
								
								<label>批次：</label>
								<input name="batch" value="${psqb.batch!""}" class="f-text" id="batch">
								
								<label>合作方：</label>
								<input name="partner" value="${psqb.partner!""}" class="f-text" id="partner">
								
								<input type="submit" value="搜索" >
							</div>
						</div>
					</form>
					<#-- 导入意向商户Excel -->
					<@layout.security.authorize url="seller/purposeSellerImport">
					<form action="${basePath}/seller/purposeSellerImport" method="post" id="purposeSellerImportForm" enctype="multipart/form-data"  >
						<div id="seller_search"  >
							<div class="form-group">
								<label for="purposeSellerExcel">导入意向商户（下载模板：<a href="${basePath}${excelTemplate.url!""}">${excelTemplate.excelTemplateName!""}</a>）</label>
			                    <input type="file" id="purposeSellerExcel" name="purposeSellerExcel" >
			                    <input type="button" value="导入" id="purposeSellerImportBtn" >
							</div>
						</div>
					</form>
					</@layout.security.authorize>
                </div>
                
                <div class="box-body">
                  <table id="purposeseller_list" class="table table-bordered table-hover">
                    <thead>
                      <tr>
                        <th id="seller_name">商户名称</th>
						<th id="mobile_phone">电话</th>
						<th id="res_addr">餐厅地址</th>
						<th id="napos_id">Napos餐厅id</th>
						<th id="res_id">餐厅id</th>
						<th id="batch">批次</th>
						<th id="partner">合作方</th>
						<th id="operate">操作</th>
                      </tr>
                    </thead>
                    <tbody>	
                    <#list tbData.list as seller>
						<tr>
							<td style="text-align:left">${seller.res_name!""}</td>
							<td style="text-align:left">${seller.mobile!!} </td>
							<td style="text-align:left">${seller.res_addr!!}</td>
							<td style="text-align:left">${seller.napos_resid!!}</td>
							<td style="text-align:left">${seller.res_id!!}</td>
							<td style="text-align:left">${seller.batch!!}</td>
							<td style="text-align:left">${seller.partner!!}</td>
							<td style="text-align:left">
							<@layout.security.authorize url="seller/remoteGetSellerInfo">
								<span onclick="javascript:remoteGetSellerInfo('${seller.res_name!!}','${seller.mobile!!}','${seller.res_addr!!}','${seller.napos_resid!!}')" style="cursor:pointer">获取餐厅信息</span>
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
              
<#-- 消息提示弹层 -->
<div class="remodal" data-remodal-id="messageBox" role="dialog" aria-labelledby="messageTitle" aria-describedby="messageBody">
  <button data-remodal-action="close" class="remodal-close" aria-label="Close"></button>
  <div>
    <h2 id="messageTitle"></h2>
    <p id="messageBody">
      
    </p>
  </div>
  <br>
  <button data-remodal-action="cancel" class="remodal-cancel">关闭</button>
</div>

<#-- 显示根据潜在信息查找出来的具体商户 -->
<script id="chooseSellerInfoForPurposeImport" type="text/html">
	<div class="box-body">
  		<table class="table table-bordered table-hover">
           <thead>
                      <tr>
                        <th>Napos餐厅id</th>
						<th>餐厅名称</th>
						<th>手机号码</th>
						<th>地址</th>
						<th>城市id</th>
						<th>操作</th>
                      </tr>
                    </thead>
                    <tbody>
                    {{each list as value}}
						<tr>
							<td style="text-align:left">{{value.oid}}</td>
							<td style="text-align:left">{{value.name}}</td>
							<td style="text-align:left">{{value.mobile}}</td>
							<td style="text-align:left">{{value.address_text}}</td>
							<td style="text-align:left">{{value.city_id}}</td>
							<td style="text-align:left">
							<@layout.security.authorize url="seller/saveSellerInfoByOid">
								<span onclick="javascript:saveSellerInfoByOid({{value.oid}})" style="cursor:pointer">保存到商户基础表</span>
							</@layout.security.authorize>
							</td>
						</tr>
					{{/each}}
                    </tbody>
                  </table>
                </div>
</script>


</body>
<script src="${basePath}/jslib/jquery.form.js"></script>
<script src="${basePath}/jslib/remodal.js"></script>
<script src="${basePath}/jslib/template.js"></script>
<!-- date-range-picker -->
<script src="${basePath}/jslib/moment.min.js"></script>
<script src="${basePath}/stylelib/adminlte/plugins/daterangepicker/daterangepicker.js"></script>
<!-- bootstrap color picker -->
<script src="${basePath}/stylelib/adminlte/plugins/colorpicker/bootstrap-colorpicker.min.js"></script>
<script type="text/javascript" >

  function remoteGetSellerInfo(res_name,mobile,res_addr,napos_resid){
		$.ajax({
		   	type: "GET",
		   	url: basePath + "seller/remoteGetSellerInfo",
		   	data: {  
               res_name : res_name,  
               mobile : mobile,  
               res_addr : res_addr,  
               napos_resid : napos_resid  
		    },
		   	success: function(resultObj){
		   		if(resultObj.isSuccess){
		   			var messBody = template('chooseSellerInfoForPurposeImport', resultObj);
					alertMessageBox("获取意向商户信息",messBody);
				}else{
					alertMessageBox("获取意向商户信息",resultObj.failureMess);
				}
		   	},
		   	error:function(resultObj){  
		   	 	alertMessageBox("获取意向商户信息","出现异常，请稍后重试！");
		   	}
		});
  }
  
  function saveSellerInfoByOid(napos_resid){
		$.ajax({
		   	type: "POST",
		   	url: basePath + "seller/saveSellerInfoByOid",
		   	data: {  
               napos_resid : napos_resid  
		    },
		   	success: function(resultObj){
				if(!resultObj.isSuccess){ 
					alertMessageBox("保存Napos商户",resultObj.failReason,function(){
					});
				}else {
					alertMessageBox("保存Napos商户",resultObj.message,function(){
					});
				}
		   	},
		   	error:function(resultObj){  
				alertMessageBox("保存Napos商户","出现异常，请稍后重试！");
		   	}
		});
  }

  function alertMessageBox(messTitle,messBody,callback){
  	$("#messageTitle").html(messTitle);
  	$("#messageBody").html(messBody);
    var messBox = $('[data-remodal-id=messageBox]').remodal();
    messBox.open();
    $(document).on('closed', '.remodal', function (e) {
    	callback();
	});
  }

  $(document).ready(function() {
  	<#-- 导入意向商户表格 -->
	var submitBtn = $("#purposeSellerImportBtn");
	var purposeSellerExcel = $("#purposeSellerExcel");
	var isSubmit=false;
	var options={
		dataType: "text",
		beforeSubmit:function(){
			isSubmit=true;
			submitBtn.val("提交中，请稍后");
		},
		success:function(result){	
			isSubmit=false;
			var resultObj = jQuery.parseJSON(result);
			if(!resultObj.isSuccess){ 
				alertMessageBox("导入意向商户",resultObj.failReason,function(){
					submitBtn.attr({"value":"导入"});
					window.parent.location.reload(); 
				});
			}else {
				alertMessageBox("导入意向商户",resultObj.message,function(){
					submitBtn.attr({"value":"导入"});
					window.parent.location.reload(); 
				});
				<#--此处不涉及部分成功的情况 -->
			}
		},
		error:function(){   
			isSubmit=false;
			alertMessageBox("导入意向商户","发生异常，请稍后重试！",function(){
				submitBtn.attr({"value":"导入"});
				window.parent.location.reload(); 
			});
		}
	};
	submitBtn.click(function(){
		if(isSubmit){
			return false;
		}
		var postfix = purposeSellerExcel.val().substring(purposeSellerExcel.val().lastIndexOf(".") + 1);
		if(purposeSellerExcel.length==0||!purposeSellerExcel.val()){
			alertMessageBox("导入意向商户","请选择导入的文件");
			return false;
		} else if (postfix != "xlsx") {
			alertMessageBox("导入意向商户","请使用Excel2007之后的版本！");
			return false;
		}
		$("#purposeSellerImportForm").ajaxSubmit(options);
	});
  
  });
  
</script>
  
  <#-- 意向商户列表页面结束 -->
  </section>
</div>


</@layout.page>
</#escape>