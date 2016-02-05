<#import "/account/layout.ftl" as layout>
<#escape x as x?html>
<@layout.page title="贷款管理">
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
	    商户贷款管理
	  </h1>
	  <ol class="breadcrumb">
	    <li><a href="${basePath}/account/index"><i class="fa fa-dashboard"></i>首页</a></li>
	    <li><a href="${basePath}/loan/list">商户贷款管理</a></li>
	    <li class="active">商户贷款管理</li>
	  </ol>
	</section>
	<!-- Main content -->
	<section class="content">
	<#-- 商户贷款管理开始 -->

              <div class="box">
                <div class="box-header">
                  	<#-- 查询 -->
					<form action="${basePath}/loan/list" method="get" id="queryUser">
						<div id="loan_list_search"  >
							<div class="form-group">
								<label>地区：</label>&nbsp;
                                <select id="provinceId" name="provinceId"
                                        onchange="getCityList(this.value);">
                                    <option value="">省</option>
                                    <#list provinceList as mCityTree>
                                        <option value="${mCityTree.cityID}"<#if lqb.provinceId?? && lqb.provinceId == mCityTree.cityID>
                                                selected="selected"</#if>>${mCityTree.cityName}</option>
                                    </#list>
                                </select>
                                <select id="cityId" name="cityId">
                                    <option value="">市</option>
                                    <#if cityList?? && cityList?size gt 0>
                                        <#list cityList as mCityTree>
                                            <option value="${mCityTree.cityID}"<#if lqb.cityId?? && lqb.cityId == mCityTree.cityID>
                                                    selected="selected"</#if>>${mCityTree.cityName}</option>
                                        </#list>
                                    </#if>
                                </select>
                                &nbsp;&nbsp;&nbsp;&nbsp;
				            	<label>时间：</label>&nbsp;
				              	<input id="submitTimeFrom" name="submitTimeFrom" <#if lqb.submitTimeFrom??> value="${(lqb.submitTimeFrom!!?string('yyyy-MM-dd'))!}"</#if>  required readonly="readonly" >
				              	至
				              	<input id="submitTimeEnd" name="submitTimeEnd" <#if lqb.submitTimeEnd??> value="${(lqb.submitTimeEnd!!?string('yyyy-MM-dd'))!}"</#if> required readonly="readonly" >
								<label>订单来源：</label>
								<select name="orderSource" id="orderSource" >
									<option value=""  <#if lqb.orderSource?? && "${lqb.orderSource}"=="">selected="selected"</#if> >全部</option>
									<option value="0"  <#if lqb.orderSource?? && "${lqb.orderSource}"=="0">selected="selected"</#if> >用户直接提交</option>
									<option value="1"  <#if lqb.orderSource?? && "${lqb.orderSource}"=="1">selected="selected"</#if> >未开通城市分配</option>
								</select>
								
								<br>
								<label>申请人：</label>
								<input name="proposerName" value="${lqb.proposerName!""}" class="f-text" id="proposerName">
								
								<label>申请产品：</label>
								<select name="fpId" id="fpId" >
									<option value=""  <#if lqb.fpId?? && "${lqb.fpId}"=="">selected="selected"</#if> >全部</option>
									<#list productList as product>
										<option value="${product.fpId?string}" <#if lqb.fpId?? && "${lqb.fpId}"=="${product.fpId?string}">selected="selected"</#if> >${product.fpName}</option>
									</#list>
								</select>
								<label>审核状态：</label>
								<select name="appStatus" id="appStatus" >
									<option value=""  <#if lqb.appStatus?? && "${lqb.appStatus}"=="">selected="selected"</#if> >全部</option>
									<option value="P"  <#if lqb.appStatus?? && "${lqb.appStatus}"=="P">selected="selected"</#if> >审批中</option>
									<option value="C"  <#if lqb.appStatus?? && "${lqb.appStatus}"=="C">selected="selected"</#if> >通过</option>
									<option value="D"  <#if lqb.appStatus?? && "${lqb.appStatus}"=="D">selected="selected"</#if> >未通过</option>
								</select>
								<input type="submit" value="搜索"  >
							</div>
						</div>
					</form>
                </div>
                
                <div class="box-body">
                  <table id="loan_list" class="table table-bordered table-hover">
                    <thead>
                      <tr>
                      	<th>申请单号</th>
                        <th>申请人</th>
						<th>餐厅名</th>
						<th>申请产品</th>
						<th>地区</th>
						<th>申请时间</th>
						<th>申请金额</th>
						<th>审核进度</th>
						<th>审核状态</th>
						<th>操作</th>
                      </tr>
                    </thead>
                    <tbody>
                    <#list tbData.list as loanSeller>
						<tr>
							<td style="text-align:left">${loanSeller.docNo!""}</td>
							<td style="text-align:left">${loanSeller.proposerName!""}</td>
							<td style="text-align:left">${loanSeller.sellerName!""}</td>
							<td style="text-align:left">${loanSeller.fpName!""}</td>
							<td style="text-align:left">${loanSeller.cityName!""}</td>
							<td style="text-align:left"><#if loanSeller.submitTime??>${loanSeller.submitTime!!?string('yyyy-MM-dd HH:mm:ss')}</#if></td>
							<td style="text-align:left"><#if loanSeller.loanAmount??>${loanSeller.loanAmountTenThousand!!?string}万</#if></td>
							<td style="text-align:left"> <#if loanSeller.appStatus?? && "${loanSeller.appStatus}"!="C">${loanSeller.nodeName!!}</#if></td>
							<td style="text-align:left">${loanSeller.appStatusStr!!}</td>
							<td style="text-align:left">
							<@layout.security.authorize url="loan/detail">
								<a href="${basePath}/loan/detail/${loanSeller.slId!!}" >申请详情</a>
							</@layout.security.authorize>
								<a href="javascript:deleteLoanInfo('${loanSeller.slId!!}')">删除</a>
							</td>
						</tr>
					</#list>
                    </tbody>
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


function goProductAdd(){
  	window.location.href="${basePath}/product/addView";
}

function getCityList(provinceId) {
    $("#cityId").empty();
    $("#cityId").append('<option value="">市</option>');

    if (provinceId == '') {
        return;
    }

    $.ajax({
        url: '${basePath}/cityList/' + provinceId,
        type: 'get',
        dataType: 'json',
        success: function (result) {
            $.each(result, function (idx, obj) {
                $("#cityId").append('<option value="' + obj.cityID + '">' + obj.cityName + '</option>');
            });
        },
        error: function (data) {
            alertMessageBox("错误", "获取省市信息失败，请稍后重试！");
        }
    });
}

function deleteLoanInfo(slId){
	if(confirm("确定要删除数据吗")){
		$.ajax({
		   	type: "GET",
		   	url: basePath + "loan/del",
		   	data: {  
		       slId : slId
		    },
		   	success: function(resultObj){
		   		if(resultObj.isSuccess){
		   			alertMessageBox("贷款信息",resultObj.message,function(){
		   					window.parent.location.reload();
		   				});
				}else{
					alertMessageBox("贷款信息",resultObj.message,function(){window.parent.location.reload();});
				}
		   	},
		   	error:function(resultObj){  
		   	 	alertMessageBox("贷款信息","出现异常，请稍后重试！");
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

</@layout.page>
</#escape>