<#import "/account/layout.ftl" as layout>
<#escape x as x?html>
<@layout.page title="融资产品编辑">
<head>
	<#-- 弹出框 -->
	<link rel="stylesheet" href="${basePath}/stylelib/remodal.css">
	<link rel="stylesheet" href="${basePath}/stylelib/remodal-default-theme.css">
	<#-- Bootstrap 颜色选择器 -->
    <link rel="stylesheet" href="${basePath}/stylelib/adminlte/plugins/colorpicker/bootstrap-colorpicker.min.css">
    <#-- Bootstrap 日期选择器 -->
    <link rel="stylesheet" href="${basePath}/stylelib/adminlte/plugins/daterangepicker/daterangepicker-bs3.css">
    
</head>
<div class="content-wrapper">
	<#-- Content Header (Page header) -->
	<section class="content-header">
	  <h1>
	    	融资产品展示
	  </h1>
	  <ol class="breadcrumb">
	    <li class="active"><a href="${basePath}/account/index"><i class="fa fa-dashboard"></i>首页</a></li>
	    <li><a href="${basePath}/finance/list">融资产品管理</a></li>
	    <li class="active">融资产品展示</li>
	  </ol>
	</section>
	<#-- Main content -->
	<section class="content">
	<#-- 融资产品编辑页面 -->
		
	<div class="box box-primary">
        <div class="box-header with-border">
          <h3 class="box-title">融资产品编辑</h3>
        </div><!-- /.box-header -->
        <!-- form start -->
        
        <form role="form" id="productApprove" action="${basePath}/product/approveSave" method="post" data-parsley-validate="">
          <div class="box-body">
          
          	<div class="form-group" id="fpNameDiv">
            	产品状态：&nbsp;
            	<#if financeProduct.productStatus??>
					<#if financeProduct.productStatus?string=='0'>待上线
					<#elseif financeProduct.productStatus?string=='1'>上线
					<#else>下线</#if>
				</#if>
            </div>
          	<div class="form-group" id="fpNameDiv">
            	编辑时间：&nbsp;
            	${financeProduct.createAt!!?string('yyyy-MM-dd HH:mm:ss')}
            </div>
          
            <div class="form-group" id="fpNameDiv">
            	产品名称：&nbsp;
            	${(financeProduct.fpName)!}
            </div>
            
            <div class="form-group" id="foIdDiv">
            	所属机构：&nbsp;
				<#list financeList as finance>
					<#if ("${financeProduct.foId}"=="${finance.foId?string}")>${finance.foName}</#if>
				</#list>
            </div>
            
            <div class="form-group" id="foIdDiv">
            	投放城市：&nbsp;
            	<input type="hidden" id="fpArea" name="fpArea" value="${(financeProduct.fpArea)!}">
				<span id="fpAreaNames"></span>
            </div>

            <div class="form-group" id="loanAmountDiv">
            	额度范围：&nbsp;
            	${financeProduct.minLoanAmount}万-${financeProduct.maxLoanAmount}万
            </div>

            <div class="form-group" id="foUrlDiv">
            	期限范围：&nbsp;
				<#if "${financeProduct.payLimit}"=="1">3个月以下</#if>
			  	<#if "${financeProduct.payLimit}"=="3">3个月</#if>
			  	<#if "${financeProduct.payLimit}"=="6">6个月</#if>
			  	<#if "${financeProduct.payLimit}"=="12">12个月</#if>
			  	<#if "${financeProduct.payLimit}"=="24">24个月</#if>
			  	<#if "${financeProduct.payLimit}"=="36">36个月</#if>
            </div>
            
            <div class="form-group">
             	利&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;率：&nbsp;
             	${(financeProduct.minRaitRatio)!}-${(financeProduct.maxRaitRatio)!}%
             	/
				<#if "${financeProduct.raitUnit?string}"=="0">天</#if><#if "${financeProduct.raitUnit?string}"=="1">月</#if>
            </div>
            
            <div class="form-group" id="otherDescDiv">
            	banner图：&nbsp;
            	<#if bannerUrl??><img src="${bannerUrl}"/></#if>
            </div>
            
            <div class="form-group" id="otherDescDiv">
            	申请条件：&nbsp;
            	<#if applyList?? && applyList?size gt 0>
				<#list applyList as item>
					${item.ruleName!""}
				</#list>
				</#if>
            </div>
            
            <div class="form-group" id="otherDescDiv">
            	所需材料：&nbsp;
            	<#if materialList?? && materialList?size gt 0>
				<#list materialList as item>
					${item.ruleName!""}
				</#list>
				</#if>
            </div>
            
            <div class="form-group" id="otherDescDiv">
            	其他说明：&nbsp;
            	${(financeProduct.otherDesc)!}
            </div>
            <input type="hidden" name="fpId" id="fpId" value="${(financeProduct.fpId)!}">
            <input type="hidden" name="appStatus" id="appStatus" value="${(financeProduct.productStatus)!}">
            <input type="hidden" name="opinions" id="opinions">
            <input type="hidden" name="token" value="${token}">
          </div><!-- /.box-body -->
          <div class="box-footer">
          	<#if (financeProduct.productStatus==0)>
            <button type="button" class="btn btn-primary" id="onlineBtn" onclick="saveProductInfo(1);">上线</button>
            </#if>
          	<#if (financeProduct.productStatus!=2)>
            <button type="button" class="btn btn-primary" id="offlineBtn" onclick="alertMessageBox('下线原因', template('offlineBox'));">下线</button>
            </#if>
          </div>
        </form>
      </div><!-- /.box -->
	<#-- 融资产品编辑页面结束 -->
	</section>
<#-- 消息提示弹层 -->
<#-- 消息提示弹层 -->
<div class="remodal" data-remodal-id="messageBox" role="dialog" aria-labelledby="messageTitle" aria-describedby="messageBody">
  <button data-remodal-action="close" class="remodal-close" aria-label="Close"></button>
  <div>
    <h2 id="messageTitle"></h2>
    <p id="messageBody">
    </p>
  </div>
</div>
</div>
<script src="${basePath}/jslib/remodal.js"></script>
<script src="${basePath}/jslib/template.js"></script>
<!-- date-range-picker -->
<script src="${basePath}/jslib/moment.min.js"></script>
<script src="${basePath}/stylelib/adminlte/plugins/daterangepicker/daterangepicker.js"></script>
<!-- bootstrap color picker -->
<script src="${basePath}/stylelib/adminlte/plugins/colorpicker/bootstrap-colorpicker.min.js"></script>

<script>
	function saveProductInfo(productStatus) {
		$('#appStatus').val(productStatus);
		$('#productApprove').submit();
	}
  	
	function onOffline() {
		$('#opinions').val($('#failReason').val());
		saveProductInfo(2);
	}
	
	function alertMessageBox(messTitle, messBody, callback) {
	    $("#messageTitle").html(messTitle);
	    $("#messageBody").html(messBody);
	    var messBox = $('[data-remodal-id=messageBox]').remodal();
	    messBox.open();
	}
	
	$(function(){
  		$.ajax({
		   	type: "GET",
		   	url: basePath + "product/getCoverCityInfoByFoIDFpID",
		   	data:{
		   		foID : Number(${financeProduct.foId}),
		   		fpID : Number(${financeProduct.fpId})
		   	},
		   	success: function(resultObj){
				$("#fpAreaNames").text(resultObj.cityNames);
			},
			error:function(){
				alert("系统繁忙,请稍后再试！");
			}
		});
  	});
</script>
<#-- 模版-->
<script id="offlineBox" type="text/html">
    <div class="box-body">
        <table class="table table-bordered table-hover">
            <tr>
                <td>
                	<textarea id="failReason" rows="5" cols="90"></textarea>
                </td>
            </tr>
            <tr>
                <td>
	                <button data-remodal-action="confirm" class="remodal-confirm" onclick="onOffline();">确定</button>
	                <button data-remodal-action="cancel" class="remodal-cancel">取消</button>
                </td>
            </tr>
            </tbody>
        </table>
    </div>
</script>
</@layout.page>
</#escape>
