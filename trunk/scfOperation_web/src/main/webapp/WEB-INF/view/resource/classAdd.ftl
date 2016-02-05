<#import "/account/layout.ftl" as layout>
<#escape x as x?html>
<@layout.page title="资源组${(rcab.classId)???string('编辑','添加')}">

<div class="content-wrapper">
	<#-- Content Header (Page header) -->
	<section class="content-header">
		<h1>
			资源组${(rcab.classId)???string('编辑','添加')}
		</h1>
		<ol class="breadcrumb">
			<li class="active"><a href="${basePath}/account/index"><i class="fa fa-dashboard"></i>首页</a></li>
			<li><a href="${basePath}/resource/class/list">资源组管理</a></li>
			<li class="active">资源组${(rcab.classId)???string('编辑','添加')}</li>
		</ol>
	</section>
	<#-- Main content -->
	<section class="content">
		<#-- 资源组编辑页面 -->
		<div class="box box-primary">
			<div class="box-header with-border">
				<h3 class="box-title">资源组${(rcab.classId)???string('编辑','添加')}</h3>
			</div><!-- /.box-header -->
		
			<!-- form start -->
			<form role="form" id="resourceClass" action="${basePath}/resource/class/save" method="post">
			<input type="hidden" name="classId" value="${(rcab.classId)!}">
			<input type="hidden" name="token" value="${token}">
			<div class="box-body"> 
			
				<#if rcab??>
				<@spring.bind "rcab.className" />
				</#if>
				<div class="form-group <@spring.errorStyle />" id="classNameDiv">
					资源组名称&nbsp;
					<label for="className" id="classNameLabel">
						<#if rcab??>
						<@spring.showErrors "<br>"/>
						</#if>
					</label>
					<input type="text" class="form-control" id="className" name="className" value="${(rcab.className)!}" required>
				</div>

			</div>
			<!-- /.box-body -->
			
			<div class="box-footer">
			<@layout.security.authorize url="resource/class/save">
				<button type="submit" class="btn btn-primary">保存</button>
			</@layout.security.authorize>
				<button type="button" class="btn btn-primary" onclick="javascript:listResourceClass();">返回</button>
			</div>
			</form>
		</div>
		<!-- /.box -->
		<#-- 资源组编辑页面结束 -->
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
	$('#resourceClass').parsley();
	<#-- 光标移开后，验证名是否唯一 -->
	$("#className").keyup(function(){  
		var user = {className:$("#className").val() }
		$.ajax({  
	            url : "${basePath}/resource/judgeNameSingle",  
	            type : "POST",  
	            data : JSON.stringify(user), 
	            dataType: 'json',  
	            contentType:'application/json;charset=UTF-8', 
	            success : function(result) {
	            	if(!result.isSuccess){ 
	                	$("#classNameDiv").addClass("has-error");
	                	$("#classNameLabel").html(result.failReason);
	                }else {
	                	$("#classNameDiv").removeClass("has-error");
	                	$("#classNameLabel").html("");
	                }
	            },
	            error : function(result) {  
	                alert(result.statusText);
	            }  
	     });
		
     });
});

$(function(){
		$('#resourceClass').submit(function(){
			if($("#classNameDiv").hasClass('has-error')){
				return false;
			}
		});
	});
	
function listResourceClass(){
	window.location.href="${basePath}/resource/class/list";
}
</script>
</@layout.page>
</#escape>