<#import "/account/layout.ftl" as layout>
<#escape x as x?html>
<@layout.page title="权限模块${(amab.authModuleId)???string('编辑','添加')}">

<div class="content-wrapper">
	<#-- Content Header (Page header) -->
	<section class="content-header">
		<h1>
			权限模块${(amab.authModuleId)???string('编辑','添加')}
		</h1>
		<ol class="breadcrumb">
			<li class="active"><a href="${basePath}/account/index"><i class="fa fa-dashboard"></i>首页</a></li>
			<li><a href="${basePath}/authorities/module/list">权限模块管理</a></li>
			<li class="active">权限模块${(amab.authModuleId)???string('编辑','添加')}</li>
		</ol>
	</section>
	<#-- Main content -->
	<section class="content">
		<#-- 权限模块编辑页面 -->
		<div class="box box-primary">
			<div class="box-header with-border">
				<h3 class="box-title">权限模块${(amab.authModuleId)???string('编辑','添加')}</h3>
			</div><!-- /.box-header -->
		
			<!-- form start -->
			<form role="form" id="authoritiesModule" action="${basePath}/authorities/module/save" method="post">
			<input type="hidden" name="authModuleId" value="${(amab.authModuleId)!}">
			<input type="hidden" name="token" value="${token}">
			<div class="box-body"> 
			
				<#if amab??>
				<@spring.bind "amab.authModuleName" />
				</#if>
				<div class="form-group <@spring.errorStyle />" id="moduleNameDiv">
					权限模块名称&nbsp;
					<label for="authModuleName" id="moduleNameLabel">
						<#if amab??>
						<@spring.showErrors "<br>"/>
						</#if>
					</label>
					<input type="text" class="form-control" id="authModuleName" name="authModuleName" value="${(amab.authModuleName)!}" required>
				</div>

			</div>
			<!-- /.box-body -->
			
			<div class="box-footer">
			<@layout.security.authorize url="authorities/module/save">
				<button type="submit" class="btn btn-primary">保存</button>
			</@layout.security.authorize>
				<button type="button" class="btn btn-primary" onclick="javascript:listAuthoritiesModule();">返回</button>
			</div>
			</form>
		</div>
		<!-- /.box -->
		<#-- 权限模块编辑页面结束 -->
	</section>
</div>
<script src="${basePath}/jslib/parsley/parsley.min.js"></script>
<script src="${basePath}/jslib/parsley/i18n/zh_cn.js"></script>
<link rel="stylesheet" href="${basePath}/stylelib/parsley.css">

<script src="${basePath}/jslib/remodal.js"></script>
<script src="${basePath}/jslib/template.js"></script>
<#-- 弹出框 -->
<link rel="stylesheet" href="${basePath}/stylelib/remodal.css">
<link rel="stylesheet" href="${basePath}/stylelib/remodal-default-theme.css">
<!-- bootstrap css -->
<link rel="stylesheet" href="${basePath}/stylelib/bootstrap/css/bootstrap.min.css">
<script>
$(document).ready(function(){  
	<#-- 添加表单校验 -->
	$('#authoritiesModule').parsley();
	<#-- 光标移开后，验证权限模块名是否唯一 -->
	$("#authModuleName").keyup(function(){  
		var user = {authModuleName:$("#authModuleName").val() }
		$.ajax({  
	            url : "${basePath}/authorities/module/judgeNameSingle",  
	            type : "POST",  
	            data : JSON.stringify(user), 
	            dataType: 'json',  
	            contentType:'application/json;charset=UTF-8', 
	            success : function(result) {
	            	if(!result.isSuccess){ 
	                	$("#moduleNameDiv").addClass("has-error");
	                	$("#moduleNameLabel").html(result.failReason);
	                }else {
	                	$("#moduleNameDiv").removeClass("has-error");
	                	$("#moduleNameLabel").html("");
	                }
	            },
	            error : function(result) {  
	                alert(result.statusText);
	            }  
	     });
     });
});
$(function(){
		$('#authoritiesModule').submit(function(){
			if($("#moduleNameDiv").hasClass('has-error')){
				return false;
			}
		});
	});

function listAuthoritiesModule(){
	window.location.href="${basePath}/authorities/module/list";
}
</script>
</@layout.page>
</#escape>