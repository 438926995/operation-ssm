<#import "/account/layout.ftl" as layout>
<#escape x as x?html>
<@layout.page title="基础详情数据添加">

<div class="content-wrapper">
	<#-- Content Header (Page header) -->
	<section class="content-header">
	  <h1>
	    基础详情数据添加
	  </h1>
	  <ol class="breadcrumb">
	    <li class="active"><a href="${basePath}/account/index"><i class="fa fa-dashboard"></i> 首页</a></li>
	    <li><a href="${basePath}/basic/list">系统管理</a></li>
	    <li class="active">基础详情数据添加</li>
	  </ol>
	</section>
	<#-- Main content -->
	<section class="content">
	<#-- 用户添加页面 -->
		
	<div class="box box-primary">
        <div class="box-header with-border">
          <h3 class="box-title">基础详情数据添加</h3>
        </div><!-- /.box-header -->
        <!-- form start -->
        
        <form id="basicadd" action="${basePath}/basic/detailAddSave" method="post" >
          <div class="box-body">
          	
          	<#if mbd??>  
				<@spring.bind "mbd.typeId" />  
			</#if>   
          	<div class="form-group">
          		TYPE_ID&nbsp;
          		<input type="text" class="form-control" id="typeId" name="typeId" 
          		value="<#if mbd?? && mdb.typeId??>${mbd.typeId}<#else>${basic.typeCd!}</#if>" readonly="true" >
          	</div>
          	
          	<#if mbd??>  
				<@spring.bind "mbd.typeIdName" />  
			</#if> 
          	<div class="form-group">
          		分类ID名称&nbsp;
          		<input type="text" class="form-control" id="typeIdName" name="typeIdName" 
          		value="<#if mbd?? && mdb.typeIdName??>${mbd.typeIdName}<#else>${basic.typeCdName!}</#if>" readonly="true" >
          	</div>
          	
      		<#if mbd??>  
				<@spring.bind "mbd.typeCdName" />  
			</#if> 
          	<div class="form-group">
          	  <div class="form-group <@spring.errorStyle /> " id="typeCdNameDiv">
              类型CD名称 &nbsp;<label for="typeCdName" id="typeCdNameLabel">
              <#if mbd??>  
			    <@spring.showErrors "<br>"/>  
			  </#if>  
              </label>
          		<input type="text" required class="form-control" id="typeCdName" name="typeCdName" value="${(mbd.typeCdName)!}">
          	</div>
          	
          	<label>当前最大排序号：${maxIndex!}</label>
          	<#if mbd??>  
				<@spring.bind "mbd.sortIndex" />  
			</#if> 
          	<div class="form-group">
          	  <div class="form-group <@spring.errorStyle /> " id="sortIndexDiv">
              排序 &nbsp;<label for="sortIndex" id="sortIndexLabel">
              <#if mbd??>  
			    <@spring.showErrors "<br>"/>  
			  </#if>  
              </label>
          		<input type="text" required class="form-control" id="sortIndex" name="sortIndex" value="${(mbd.sortIndex)!}">
          	</div>
          	
          	<#if mbd??>  
				<@spring.bind "mbd.remarks" />  
			</#if> 
          	<div class="form-group">
          		备注&nbsp;
          		<input type="text" class="form-control" id="remarks" name="remarks" value="${(mbd.remarks)!}" >
          	</div>
          	
          	<#if mbd??>  
              <@spring.bind "mbd.isVisible" />  
            </#if> 
            <div class="form-group">
              <label>是否可见</label>
              <label>
                <input type="radio" name="isVisible" id="isVisible0" value="0" 
                <#if mbd??> 
                 <#if mbd.isVisible?? && "${mbd.isVisible}"=="0"> checked </#if> 
                <#else> checked </#if>
                >否
                <input type="radio" name="isVisible" id="isVisible1" value="1" <#if mbd?? && mbd.isVisible?? && "${mbd.isVisible}"=="1"> checked </#if> >是
              </label>
            </div>
          	
	        <input type="hidden" name="token" value="${token}">
          </div><!-- /.box-body -->
          <div class="box-footer">
            <button type="submit" class="btn btn-primary" id="add_user">添加</button>
          </div>
        </form>
      </div><!-- /.box -->
		
	<#-- 用户添加页面结束 -->
	</section>
</div>
<script src="${basePath}/jslib/parsley/parsley.min.js"></script>
<script src="${basePath}/jslib/parsley/i18n/zh_cn.js"></script>
<link rel="stylesheet" href="${basePath}/stylelib/parsley.css">
<script>
$(document).ready(function(){  
	<#-- 验证排序号 -->
	$("#sortIndex").blur(function(){
		if(isNaN($("#sortIndex").val())){
        	$("#sortIndexDiv").addClass("has-error");
            $("#sortIndexLabel").html("必须为数字");
         } else {
         	$("#sortIndexDiv").removeClass("has-error");
            $("#sortIndexLabel").html("");
         }
	});
	<#-- 添加表单校验 -->
	$('#basicadd').parsley();
	<#-- 光标移开后，验证用户名是否唯一 -->
	$("#typeCdName").blur(function(){  
		var typeCdName = {typeCdName:$("#typeCdName").val() }
		$.ajax({  
	            url : "${basePath}/basic/judgeTypeCdNameSingle",  
	            type : "POST",  
	            data : JSON.stringify(typeCdName), 
	            dataType: 'json',  
	            contentType:'application/json;charset=UTF-8', 
	            success : function(result) {
	            	if(!result.isSuccess){ 
	                	$("#typeCdNameDiv").addClass("has-error");
	                	$("#typeCdNameLabel").html(result.failReason);
	                }else {
	                	$("#typeCdNameDiv").removeClass("has-error");
	                	$("#typeCdNameLabel").html("");
	                }
	            },
	            error : function(result) {  
	                alert(result.statusText);
	            }  
	     });
		
     });
});
	$(function(){
		$('#basicadd').submit(function(){
			if($("#typeCdNameDiv").hasClass('has-error') || $("#sortIndexDiv").hasClass('has-error')){
            		return false;
            	}
		});
	});
</script>
</@layout.page>
</#escape>