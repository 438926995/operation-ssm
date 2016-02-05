<#import "/account/layout.ftl" as layout>
<#escape x as x?html>
<@layout.page title="融资产品申请条件添加">
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
	    	融资产品申请条件添加
	  </h1>
	  <ol class="breadcrumb">
	    <li class="active"><a href="${basePath}/account/index"><i class="fa fa-dashboard"></i> 首页</a></li>
	    <li><a href="${basePath}/product/applylist/${(fpId)!}">融资产品申请条件管理</a></li>
	    <li class="active">融资产品申请条件添加</li>
	  </ol>
	</section>
	<#-- Main content -->
	<section class="content">
	<#-- 融资产品申请条件添加页面 -->
		
	<div class="box box-primary">
        <div class="box-header with-border">
          <h3 class="box-title">融资产品规则添加</h3>
        </div><!-- /.box-header -->
        <!-- form start -->
        
        <form role="form" id="product" action="${basePath}/product/addApplySave" method="post" data-parsley-validate="">
          <div class="box-body"> 
            <#if paab??>  
				<@spring.bind "paab.ruleName" />  
			</#if> 
            <div class="form-group <@spring.errorStyle /> " id="ruleNameDiv">
            	字段名 &nbsp;
            	<label for="ruleName" id="ruleNameLabel">
            		<#if paab??>  
					    <@spring.showErrors "<br>"/>  
					</#if>
              	</label>
              	<input type="text" class="form-control" id="ruleName" name="ruleName" value="${(paab.ruleName)!}" required data-parsley-trigger="change" data-parsley-length="[2, 100]" data-parsley-maxlength="100" >
            </div>
            
            <#if paab??>  
				<@spring.bind "paab.ruleContent" />  
			</#if> 
            <div class="form-group <@spring.errorStyle /> " id="ruleContentDiv">
            	字段规则 &nbsp;
            	<label for="ruleContent" id="ruleContentLabel">
            		<#if paab??>  
					    <@spring.showErrors "<br>"/>  
					</#if>
              	</label>
              	<input type="text" class="form-control" id="ruleContent" name="ruleContent" value="${(paab.ruleContent)!}" required data-parsley-trigger="change" data-parsley-length="[2, 100]" data-parsley-maxlength="100" >
            </div>
           
            <input type="hidden" name="ruleType" id = "ruleType" value="1">
            <input type="hidden" name="fpId" id = "fpId" value="${(fpId)!}">
            <input type="hidden" name="token" value="${token}">
          </div><!-- /.box-body -->
          <div class="box-footer">
            <button type="submit" class="btn btn-primary" id="saveBtn" >添加</button>
          </div>
        </form>
      </div><!-- /.box -->
		
	<#-- 融资产品申请条件添加页面结束 -->
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

</@layout.page>
</#escape>