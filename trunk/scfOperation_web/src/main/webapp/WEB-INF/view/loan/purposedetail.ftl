<#import "/account/layout.ftl" as layout>
<#escape x as x?html>
<@layout.page title="贷款详情">
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
	<#-- Content Header (Page header) -->
	<section class="content-header">
	  <h1>
	    	未开通城市审核贷款详情
	  </h1>
	  <ol class="breadcrumb">
	    <li class="active"><a href="${basePath}/account/index"><i class="fa fa-dashboard"></i> 首页</a></li>
	    <li><a href="${basePath}/purpose/loan/list">未开通城市商户贷款管理</a></li>
	    <li class="active">贷款详情</li>
	  </ol>
	</section>
	<#-- Main content -->
	<section class="content">
	<#-- 贷款详情页面 -->
		
	<div class="box box-primary">
        <div class="box-header with-border">
          <h3 class="box-title">贷款详情</h3>
        </div><!-- /.box-header -->
        <!-- form start -->
        
        <form role="form" id="loanDetail" action="" method="post" data-parsley-validate="">
        	<div class="box-body"> 
        	<@layout.security.authorize url="seller/naposSellersFlow">
        		<button type="button" class="btn btn-primary" onclick="fundsFlow(${purposeLoan.naposResId!""})">获取流水</button>
    			<select name="month" id="month" >
					<option value=""></option>
					<option value="6">6个月</option>
					<option value="9">9个月</option>
					<option value="12">12个月</option>
				</select>    	
	
			</@layout.security.authorize>
            	<table class="box-header">
            		<tr>
                      	<th style="width:100px;"></th>
                        <th style="width:100px;"></th>
						<th style="width:100px;"></th>
						<th style="width:200px;"></th>
                    </tr>
            		<tr>
            			<td>申请人：&nbsp;</td>
            			<td>${purposeLoan.proposerName!""}</td>
            			<td>申请单号</td>
            			<td>${purposeLoan.docNo!""}</td>
            		</tr>
            		<tr>
            			<td>餐厅：&nbsp;</td>
            			<td>${purposeLoan.sellerName!""}</td>
            			<td>申请金额</td>
            			<td>${purposeLoan.loanAmountTenThousand!""}万元</td>
            		</tr>
            		<tr>
            			<td>申请地区：&nbsp;</td>
            			<td>${purposeLoan.cityName!""}</td>
            			<td>申请时间</td>
            			<td><#if purposeLoan.submitTime??>${purposeLoan.submitTime!!?string('yyyy-MM-dd HH:mm:ss')}</#if></td>
            		</tr>
            	</table>
            	<table class="box-header">
            		<tr>
                      	<th style="width:200px;"></th>
                        <th style="width:200px;"></th>
                    </tr>
            		<tr>
            			<td colspan = 2>1.个人信息</td>
            		</tr>
            		<tr>
            			<td>姓名：&nbsp;</td>
            			<td>${purposeLoan.proposerName!""}</td>
            			
            		</tr>
            		<tr>
            			<td>性别</td>
            			<td>
							<#if purposeLoan.proposerSex??>
								<#if purposeLoan.proposerSex?string=='1'>男<#else>女</#if>
							</#if>
						</td>
            		</tr>
            		<tr>
            			<td>年龄</td>
            			<td>${purposeLoan.proposerAge!""}</td>
            		</tr>
            		<tr>
            			<td>身份证号</td>
            			<td>${purposeLoan.proposerSidStr!""}</td>
            		</tr>
            		<tr>
            			<td>手机号码</td>
            			<td>${purposeLoan.proposerPhoneStr!""}</td>
            		</tr>
            	</table>
            	<table class="box-header">
            		<tr>
                      	<th style="width:200px;"></th>
                        <th style="width:200px;"></th>
                    </tr>
            		<tr>
            			<td colspan = 2>2.餐厅信息</td>
            		</tr>
            		<tr>
            			<td>餐厅名称：&nbsp;</td>
            			<td>${purposeLoan.sellerName!""}</td>
            			
            		</tr>
            		<tr>
            			<td>餐厅号</td>
            			<td>${purposeLoan.naposResId!""}</td>
            		</tr>
            		<tr>
            			<td>法人</td>
            			<td>${purposeLoan.proposerName!""}</td>
            		</tr>
            		<tr>
            			<td>店主</td>
            			<td>${purposeLoan.proposerName!""}</td>
            		</tr>
            		<tr>
            			<td>电话</td>
            			<td>${purposeLoan.proposerPhone!""}</td>
            		</tr>
            		<tr>
            			<td>饿了么日均流水</td>
            			<td><#if purposeLoan.perDayTotal?? >${purposeLoan.perDayTotal?string(",##0.0#")!""}元</#if></td>
            		</tr>
            		<tr>
            			<td>加入饿了么时间</td>
            			<td>${purposeLoan.createdDate!""}</td>
            		</tr>
            		<tr>
            			<td>餐厅地址</td>
            			<td>${purposeLoan.addressText!""}</td>
            		</tr>
            	</table>
            	
              <input type="hidden" name="slId" id = "slId" value="${purposeLoan.slId!""}">
          </div><!-- /.box-body -->
        </form>
      </div><!-- /.box -->
		
	<#-- 贷款详情页面结束 -->
	</section>
</div>
</body>
<script src="${basePath}/jslib/parsley/parsley.min.js"></script>
<script src="${basePath}/jslib/parsley/i18n/zh_cn.js"></script>
<script src="${basePath}/jslib/remodal.js"></script>
<script src="${basePath}/jslib/template.js"></script>
<link rel="stylesheet" href="${basePath}/stylelib/parsley.css">
<script>
var messBoxLoad;
function approve(approveFlag){
	$.ajax({
	   	type: "GET",
	   	url: basePath + "loan/approve",
	   	data: {  
	   	   approveFlag : approveFlag,
	       slId : $("#slId").val(),
	       remark : $.trim($("#remark").val())
	    },
	   	success: function(resultObj){
	   		if(resultObj.isSuccess){
	   			alertMessageBox("贷款审核",resultObj.message,function(){
	   					window.location.href="${basePath}/loan/list";
	   				});
			}else{
				alertMessageBox("贷款审核",resultObj.message);
			}
	   	},
	   	error:function(resultObj){  
	   	 	alertMessageBox("贷款审核","出现异常，请稍后重试！");
	   	}
	});
}

function fundsFlow(id){
	if(confirm("确定要获取交易流水吗")){
		
		loadMessageBox("商户交易流水", "交易流水导入中！");
		$.ajax({
		   	type: "POST",
		   	url: basePath + "seller/naposSellersFlow",
		   	data: { 
		       id : id,
		       month:$("#month").val()
		    },
		   	success: function(resultObj){
		   		if(messBoxLoad.state == "opening"){
		   			messBoxLoad.close();
		   		}
		   		if(resultObj.isSuccess){
		   			alertMessageBox("商户交易流水",resultObj.message,function(){
		   					window.parent.location.reload();
		   				});
				}else{
					alertMessageBox("商户交易流水",resultObj.message,function(){window.parent.location.reload();});
				}
		   	},
		   	error:function(resultObj){
		   		if(messBoxLoad.state == "opening"){
		   			messBoxLoad.close();
		   		}
		   	 	alertMessageBox("商户交易流水", "出现异常，请稍后重试！",function(){window.parent.location.reload();});
		   	}
		});
	}
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

function loadMessageBox(messTitle,messBody,callback){
  	$("#loadTitle").html(messTitle);
  	$("#loadBody").html(messBody);
    messBoxLoad = $('[data-remodal-id=messageLoad]').remodal();
    messBoxLoad.open();
    $(document).on('', '.remodal', function (e) {
    	callback();
	});
}

</script>

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

<#-- 悬浮层弹层 -->
<div class="remodal" data-remodal-id="messageLoad" role="dialog" aria-labelledby="loadTitle" aria-describedby="loadBody">
  <div>
    <h2 id="loadTitle"></h2>
    <p id="loadBody">
    </p>
    <div class="overlay">
	  <i class="fa fa-refresh fa-spin"></i>
	</div>
  </div>
  <br>
</div>

</@layout.page>
</#escape>