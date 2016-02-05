<#import "/account/layout.ftl" as layout>
<#escape x as x?html>
<@layout.page title="融资产品规则编辑">
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
	    	融资产品规则编辑
	  </h1>
	  <ol class="breadcrumb">
	    <li class="active"><a href="${basePath}/account/index"><i class="fa fa-dashboard"></i> 首页</a></li>
	    <li><a href="${basePath}/product/rulelist/${(prab.fpId)!}">融资产品规则管理</a></li>
	    <li class="active">融资产品规则编辑</li>
	  </ol>
	</section>
	<#-- Main content -->
	<section class="content">
	<#-- 融资产品规则添加页面 -->
		
	<div class="box box-primary">
        <div class="box-header with-border">
          <h3 class="box-title">融资产品规则编辑</h3>
        </div><!-- /.box-header -->
        <!-- form start -->
        
        <form role="form" id="product" action="${basePath}/product/editRuleSave" method="post" data-parsley-validate="">
          <div class="box-body"> 
            <#if prab??>  
				<@spring.bind "prab.ruleName" />  
			</#if> 
            <div class="form-group <@spring.errorStyle /> " id="fpNameDiv">
            	字段名 &nbsp;
            	<label for="fpName" id="ruleNameLabel">
            		<#if prab??>  
					    <@spring.showErrors "<br>"/>  
					</#if>
              	</label>
              	
              	<select name="ruleName" id="ruleName" required onchange="rulechangge(this.options[this.options.selectedIndex].value)">
					<option value=""  <#if prab?? && "${prab.ruleName}"=="">selected="selected"</#if> >请选择</option>
					<#list list as basicData>
						<option value="${basicData.typeCd}" <#if prab?? && "${prab.ruleName}"=="${basicData.typeCd}">selected="selected"</#if>>${basicData.typeCdName}</option>
					</#list>
				</select>
              	
            </div>

            <#if prab??>
				<@spring.bind "prab.fromNum" /> 
				<@spring.bind "prab.endNum" />  
			</#if> 
            <div class="form-group <@spring.errorStyle /> ">
            	字段规则 &nbsp;
            	<label for="symbol" id="symbolLabel">
            		<#if prab??>  
					    <@spring.showErrors "<br>"/>  
					</#if>
              	</label>
              	<div>
              		<input type="text" id="fromNum" name="fromNum" value="${(prab.fromNum)!}" required data-parsley-trigger="change" data-parsley-type="number" data-parsley-minlength="2" data-parsley-maxlength="10">
              		<label id="fromNumLabel"></label>
              		~
              		<input type="text"  id="endNum" name="endNum" value="${(prab.endNum)!}" required data-parsley-trigger="change" data-parsley-type="number" data-parsley-minlength="2" data-parsley-maxlength="10">
            		<label id="endNumLabel"></label>
              	</div>
            </div>
            
            <#if prab??>
			    <@spring.bind "prab.isUse" />  
			</#if>
            <div class="form-group <@spring.errorStyle />">
             	状态 &nbsp;
             	<label for="isUse">
             		<#if prab??>  
					    <@spring.showErrors "<br>"/>  
					</#if>
             	</label>
              	<input name="isUse"  type="radio" value="1" required=""<#if prab??><#if "${prab.isUse?string}"=="1">checked</#if><#else>checked</#if>>开启
				<input name="isUse"  type="radio" value="2" <#if prab?? && "${prab.isUse?string}"=="2">checked</#if>>关闭
            </div>
            <input type="hidden" name="ruleId" id = "ruleId" value="${(prab.ruleId)!}">
            <input type="hidden" name="fpId" id = "fpId" value="${(prab.fpId)!}">
            <input type="hidden" name="token" value="${token}">
          </div><!-- /.box-body -->
          <div class="box-footer">
            <button type="submit" class="btn btn-primary" id="saveBtn" >编辑</button>
          </div>
        </form>
      </div><!-- /.box -->
		
	<#-- 融资产品规则添加页面结束 -->
	</section>
</div>
<script src="${basePath}/jslib/parsley/parsley.min.js"></script>
<script src="${basePath}/jslib/parsley/parsley.js"></script>
<script src="${basePath}/jslib/parsley/i18n/zh_cn.js"></script>
<link rel="stylesheet" href="${basePath}/stylelib/parsley.css">
<script src="${basePath}/jslib/remodal.js"></script>
<script src="${basePath}/jslib/template.js"></script>
<!-- date-range-picker -->
<script src="${basePath}/jslib/moment.min.js"></script>
<script src="${basePath}/stylelib/adminlte/plugins/daterangepicker/daterangepicker.js"></script>
<!-- bootstrap color picker -->
<script src="${basePath}/stylelib/adminlte/plugins/colorpicker/bootstrap-colorpicker.min.js"></script>
<script>

	$(function(){ 
		rulechangge("<#if prab??>${prab.ruleName}</#if>"); 
	}); 

	function rulechangge(ruleId){
		if(ruleId == ""){
			$("#fromNumLabel").html("");
			$("#endNumLabel").html(""); 
		}else{
			if(ruleId == "1001"){
				$("#fromNumLabel").html("月"); 
				$("#endNumLabel").html("月");
			}else if(ruleId == "1002"){
				$("#fromNumLabel").html("万元"); 
				$("#endNumLabel").html("万元");
			}else{
				$("#fromNumLabel").html("");
				$("#endNumLabel").html(""); 
			}
		}
	}
</script>
</@layout.page>
</#escape>