<#import "/account/layout.ftl" as layout>
<#escape x as x?html>
<@layout.page title="融资产品规则管理">
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
	    融资产品规则管理
	  </h1>
	  <ol class="breadcrumb">
	    <li><a href="${basePath}/account/index"><i class="fa fa-dashboard"></i>首页</a></li>
	    <li><a href="${basePath}/product/rulelist/${fpId}">融资产品规则管理</a></li>
	    <li class="active">融资产品规则管理</li>
	  </ol>
	</section>
	<!-- Main content -->
	<section class="content">
	<#-- 融资产品规则管理开始 -->

              <div class="box">
                <div class="box-header">
                  	<#-- 查询 -->
					<form action="" method="get" id="queryUser">
						<div id="user_list_search"  >
							<div class="form-group">
							<@layout.security.authorize url="product/addRuleView">
								<input type="button" value="添加规则" onclick = "javascript:goRuleAdd()"  >
							</@layout.security.authorize>
							</div>
						</div>
					</form>
                </div>
                
                <div class="box-body">
                  <table id="user_list" class="table table-bordered table-hover">
                    <thead>
                      <tr>
                      	<th>序号</th>
                        <th>规则名称</th>
						<th>规则</th>
						<th>状态</th>
						<th>操作</th>
                      </tr>
                    </thead>
                    <tbody>
                    <#list tbData.list as rule>
						<tr>
							<td style="text-align:center">${rule_index+1}</td>
							<td style="text-align:left">${rule.typeCdName!""}</td>
							<td style="text-align:left">
								${rule.fromNum!""}
								<#if rule.ruleName?? && "${rule.ruleName?string}"=="1001">月</#if>
								<#if rule.ruleName?? && "${rule.ruleName?string}"=="1002">万元</#if>
								~
								${rule.endNum!""}
								<#if rule.ruleName?? && "${rule.ruleName?string}"=="1001">月</#if>
								<#if rule.ruleName?? && "${rule.ruleName?string}"=="1002">万元</#if>
							</td>
							<td style="text-align:left">
								<#if rule.isUse??>
									<#if rule.isUse?string=='1'>开启
									<#elseif rule.isUse?string=='2'>关闭
									</#if>
								</#if>
							</td>
							<td style="text-align:left">
							<@layout.security.authorize url="product/editRuleView">
								<a href="${basePath}/product/editRuleView/${rule.ruleId!!}" >编辑</a>
							</@layout.security.authorize>
							<@layout.security.authorize url="product/ruleDel">
								<a href="javascript:deleteProductRuleInfo('${rule.ruleId!!}')">删除</a>
							</@layout.security.authorize>
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
  
<#-- 融资产品规则页面结束 -->
</section>
</div>

<script>
function deleteProductRuleInfo(ruleId){
	if(confirm("确定要删除数据吗")){
		$.ajax({
		   	type: "GET",
		   	url: basePath + "product/ruleDel",
		   	data: {  
		       ruleId : ruleId
		    },
		   	success: function(resultObj){
		   		if(resultObj.isSuccess){
		   			alertMessageBox("融资产品规则信息",resultObj.message,function(){
		   					window.parent.location.reload();
		   				});
				}else{
					alertMessageBox("融资产品规则信息",resultObj.message,function(){window.parent.location.reload();});
				}
		   	},
		   	error:function(resultObj){  
		   	 	alertMessageBox("融资产品规则信息","出现异常，请稍后重试！");
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

function goRuleAdd(){
  	window.location.href="${basePath}/product/addRuleView/${fpId}";
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