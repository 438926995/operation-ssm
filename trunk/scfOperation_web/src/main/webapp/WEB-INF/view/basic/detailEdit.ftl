<#import "/account/layout.ftl" as layout>
<#escape x as x?html>
<@layout.page title="基础数据编辑">

<div class="content-wrapper">
	<#-- Content Header (Page header) -->
	<section class="content-header">
	  <h1>
	    	基础数据编辑
	  </h1>
	  <ol class="breadcrumb">
	    <li class="active"><a href="${basePath}/account/index"><i class="fa fa-dashboard"></i> 首页</a></li>
	    <li><a href="${basePath}/basic/list">基础数据列表</a></li>
	    <li class="active">基础数据编辑</li>
	  </ol>
	</section>
	<#-- Main content -->
	<section class="content">
	<#-- 角色编辑页面 -->
		
	<div class="box box-primary">
        <div class="box-header with-border">
          <h3 class="box-title">基础数据编辑</h3>
        </div><!-- /.box-header -->
        <!-- form start -->
        
        <form id="detailform" action="${basePath}/basic/detailEditSave" method="post">
          <div class="box-body"> 
          	<div class="form-group">
          		TYPE_ID&nbsp;:
          		<input type="text" class="form-control" id="typeId" name="typeId" value="${basic.typeId!!}" readonly="true" >
          	</div>
          	<div class="form-group">
          		TYPE_CD&nbsp;:
          		<input type="text" class="form-control" id="typeCd" name="typeCd" value="${basic.typeCd!!}" readonly="true" >
          	</div>
            <div class="form-group">
              分类名称&nbsp;:
              <input type="text" class="form-control" id="typeIdName" name="typeIdName" value="${basic.typeIdName!!}" readonly="true" >
            </div>

          	<#if basic??>  
				      <@spring.bind "basic.typeCdName" />  
			      </#if> 
          	<div class="form-group">
          	  <div class="form-group <@spring.errorStyle /> " id="typeCdNameDiv">
              类型名称 &nbsp;<label for="typeCdName" id="typeCdNameLabel">
              <#if basic??>  
			           <@spring.showErrors "<br>"/>  
			        </#if>  
              </label>
          		<input type="text" required class="form-control" id="typeCdName" name="typeCdName" value="${(basic.typeCdName)!}">
          	</div>

            <#if basic??>  
              <@spring.bind "basic.sortIndex" />  
            </#if> 
            <div class="form-group">
              <div class="form-group <@spring.errorStyle /> " id="sortIndexDiv">
              排序 &nbsp;<label for="sortIndex" id="sortIndexLabel">
              <#if basic??>  
                 <@spring.showErrors "<br>"/>  
              </#if>  
              </label>
              <input type="text" class="form-control" id="sortIndex" name="sortIndex" value="${(basic.sortIndex)!}">
            </div>

            <#if basic??>  
              <@spring.bind "basic.remarks" />  
            </#if> 
            <div class="form-group">
              <div class="form-group <@spring.errorStyle /> " id="remarksDiv">
              备注 &nbsp;<label for="remarks" id="remarksLabel">
              <#if basic??>  
                 <@spring.showErrors "<br>"/>  
              </#if>  
              </label>
              <input type="text" class="form-control" id="remarks" name="remarks" value="${(basic.remarks)!}">
            </div>

            <#if basic??>  
              <@spring.bind "basic.isVisible" />  
            </#if> 
            <div class="form-group">
              <label>是否可见</label>
              <label>
                <input type="radio" name="isVisible" id="isVisible0" value="0" 
                <#if basic??> 
                 <#if basic.isVisible?? && "${basic.isVisible}"=="0"> checked </#if> 
                <#else> checked </#if>
                >否
                <input type="radio" name="isVisible" id="isVisible1" value="1" <#if basic?? && basic.isVisible?? && "${basic.isVisible}"=="1"> checked </#if> >是
              </label>
            </div>

          	<div class="box-footer">
          	<input type="hidden" name="token" value="${token}">
            <button type="submit" class="btn btn-primary">编辑</button>
          	</div>
          </div>
         </form>   

     </div><!-- /.box -->
		
	<#-- 角色编辑页面结束 -->
	</section>
</div>
<script src="${basePath}/jslib/parsley/parsley.min.js"></script>
<script src="${basePath}/jslib/parsley/i18n/zh_cn.js"></script>
<link rel="stylesheet" href="${basePath}/stylelib/parsley.css">

<script src="${basePath}/jslib/remodal.js"></script>
<script src="${basePath}/jslib/template.js"></script>
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
	$('#detailform').parsley();
	<#-- 光标移开后，验证用户名是否唯一 -->
	$("#typeCdName").blur(function(){  
		var typeCdName = $("#typeCdName").val();
		var typeCd = $("#typeCd").val();
		$.ajax({  
	            url : "${basePath}/basic/judgeTypeCdNameSingle",  
	            type : "POST",  
	            data : JSON.stringify({
	            	typeCdName : typeCdName,
	            	typeCd : typeCd
	            }), 
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
		$('#detailform').submit(function(){
			if($("#typeCdNameDiv").hasClass('has-error') || $("#sortIndexDiv").hasClass('has-error')){
            		return false;
            	}
		});
	});
</script>
</@layout.page>
</#escape>