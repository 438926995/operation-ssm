<#import "/account/layout.ftl" as layout>
<#escape x as x?html>
<@layout.page title="权限${(aab.authoritiesId)???string('编辑','添加')}">

<div class="content-wrapper">
	<#-- Content Header (Page header) -->
	<section class="content-header">
		<h1>
			权限${(aab.authoritiesId)???string('编辑','添加')}
		</h1>
		<ol class="breadcrumb">
			<li class="active"><a href="${basePath}/account/index"><i class="fa fa-dashboard"></i>首页</a></li>
			<li><a href="${basePath}/authorities/list">权限管理</a></li>
			<li class="active">权限${(aab.authoritiesId)???string('编辑','添加')}</li>
		</ol>
	</section>
	<#-- Main content -->
	<section class="content">
		<#-- 权限编辑页面 -->
		<div class="box box-primary">
			<div class="box-header with-border">
				<h3 class="box-title">权限${(aab.authoritiesId)???string('编辑','添加')}</h3>
			</div><!-- /.box-header -->
		
			<!-- form start -->
			<form role="form" id="authoritiesModule" action="${basePath}/authorities/save" method="post">
			<input type="hidden" id="authoritiesId" name="authoritiesId" value="${(aab.authoritiesId)!}">
			<input type="hidden" id="resourcesIdsStr" name="resourcesIdsStr" value="${(aab.resourcesIdsStr)!}">
			<input type="hidden" name="token" value="${token}">
			<div class="box-body">
			
				<#if aab??>
				<@spring.bind "aab.authName" />
				</#if>
				<div class="form-group <@spring.errorStyle />" id="authNameDiv">
					权限名称&nbsp;
					<label for="authName" id="authNameLabel">
						<#if aab??>
						<@spring.showErrors "<br>"/>
						</#if>
					</label>
					<input type="text" class="form-control" id="authName" name="authName" value="${(aab.authName)!}" required>
				</div>
			
				<#if aab??>
				<@spring.bind "aab.authDesc" />
				</#if>
				<div class="form-group <@spring.errorStyle />">
					权限描述&nbsp;
					<label for="authDesc">
						<#if aab??>
						<@spring.showErrors "<br>"/>
						</#if>
					</label>
					<input type="text" class="form-control" id="authDesc" name="authDesc" value="${(aab.authDesc)!}" required>
				</div>
				
				<#if aab??>
				<@spring.bind "aab.authModuleId" />
				</#if>
				<div class="form-group <@spring.errorStyle />">
					所属权限模块&nbsp;
					<label for="authModuleId">
						<#if aab??>
						<@spring.showErrors "<br>"/>
						</#if>
					</label>
					<select id="authModuleId" name="authModuleId" class="form-control" required>
						<#if authoritiesModuleList?? && authoritiesModuleList?size gt 0>
						<#list authoritiesModuleList as authoritiesModule>
						<option value="${authoritiesModule.authModuleId}" <#if (aab.authModuleId)?? && (aab.authModuleId == authoritiesModule.authModuleId)>selected="selected"</#if>>${authoritiesModule.authModuleName}</option>
						</#list>
						</#if>
					</select>
				</div>
				
				<#if aab??>
				<@spring.bind "aab.resourcesIds" />
				</#if>
				<div class="form-group <@spring.errorStyle />">
					资源&nbsp;
					<label for="resourcesIds">
						<#if aab??>
						<@spring.showErrors "<br>"/>
						</#if>
					</label>
					<div id="resourceTree" class="ztree" style="height: auto;width: auto;"></div>
				</div>
			
				<#if aab??>
				<@spring.bind "aab.isEnabled" />
				</#if>
				<div class="form-group <@spring.errorStyle />">
					是否可用&nbsp;
					<label for="isEnabled">
						<#if aab??>
						<@spring.showErrors "<br>"/>
						</#if>
					</label>
					<input type="radio" name="isEnabled" value="0" <#if (aab.isEnabled)?? && (aab.isEnabled == '0')>checked="checked"</#if>>否
					<input type="radio" name="isEnabled" value="1" <#if (aab.isEnabled)?? && (aab.isEnabled == '1')>checked="checked"</#if>>是
				</div>
				
				<div class="form-group <@spring.errorStyle />">
					是否默认&nbsp;
					<label for="isDefault">
						<#if aab??>
						<@spring.showErrors "<br>"/>
						</#if>
					</label>
					<input type="radio" name="isDefault" value="0" <#if (aab.isDefault)?? && (aab.isDefault == '0')>checked="checked"</#if>>否
					<input type="radio" name="isDefault" value="1" <#if (aab.isDefault)?? && (aab.isDefault == '1')>checked="checked"</#if>>是
				</div>
				
			</div>
			<!-- /.box-body -->
			
			<div class="box-footer">
				<button type="button" class="btn btn-primary" onclick="listAuthorities();">返回</button>
				<@layout.security.authorize url="authorities/save">
                    <button type="submit" class="btn btn-primary" onclick="return onSubmit();">保存</button>
				</@layout.security.authorize>
			</div>
			</form>
		</div>
		<!-- /.box -->
		<#-- 权限编辑页面结束 -->
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
<!-- zTree -->
<link rel="stylesheet" href="${basePath}/stylelib/zTreeStyle/zTreeStyle.css" type="text/css">
<script type="text/javascript" src="${basePath}/jslib/jquery.ztree.all.js"></script>
<script>
$(document).ready(function(){  
	<#-- 添加表单校验 -->
	$('#authoritiesModule').parsley();
	<#-- 光标移开后，验证角色名是否唯一 -->
	$("#authName").blur(function(){  
		var user = {authName:$("#authName").val(), authId:$('#authoritiesId').val()}
		console.log($('#authoritiesId').val());
		$.ajax({  
	            url : "${basePath}/authorities/judgeNameSingle",  
	            type : "POST",  
	            data : JSON.stringify(user), 
	            dataType: 'json',  
	            contentType:'application/json;charset=UTF-8', 
	            success : function(result) {
	            	if(!result.isSuccess){ 
	                	$("#authNameDiv").addClass("has-error");
	                	$("#authNameLabel").html(result.failReason);
	                }else {
	                	$("#authNameDiv").removeClass("has-error");
	                	$("#authNameLabel").html("");
	                }
	            },
	            error : function(result) {  
	                alert(result.statusText);
	            }  
	     });
     });
});

$(function() {
	createTree();
});

var zNodes = [];

function createTree() {
	var setting = {
		view: {
			selectedMulti: false
		},
		check: {
			enable: true
		},
		data: {
			simpleData: {
				enable: true
			}
		},
		callback: {
			onCheck: zTreeOnclick
		}
		
	};

	zNodes =[
		<#if menuTree?? && menuTree?size gt 0>
		<#list menuTree.root as resource1>
		{ url:"${resource1.resourceString}", id:${resource1.resourceId}, pId:0, name:"${resource1.resourceName}"<#if aab.resourcesIds?? && aab.resourcesIds?seq_contains(resource1.resourceId)>, checked:true</#if>},
			<#if resource1.children?? && resource1.children?size gt 0>
			<#list resource1.children as resource2>
			{ url:"${resource2.resourceString}", id:${resource2.resourceId}, pId:${resource1.resourceId}, name:"${resource2.resourceName}"<#if aab.resourcesIds?? && aab.resourcesIds?seq_contains(resource2.resourceId)>, checked:true</#if>},
				<#if resource2.children?? && resource2.children?size gt 0>
				<#list resource2.children as resource3>
				{ url:"${resource3.resourceString}", id:${resource3.resourceId}, pId:${resource2.resourceId}, name:"${resource3.resourceName}"<#if aab.resourcesIds?? && aab.resourcesIds?seq_contains(resource3.resourceId)>, checked:true</#if>},
				</#list>
				</#if>
			</#list>
			</#if>
		</#list>
		</#if>
	];
	$.fn.zTree.init($("#resourceTree"), setting, zNodes);
}

function listAuthorities(){
	window.location.href="${basePath}/authorities/list";
}

function onSubmit() {
	if($("#authNameDiv").hasClass('has-error')){
            return false;
    }
	var resourcesIdsStr = '';
	var treeObj = $.fn.zTree.getZTreeObj("resourceTree");
	var nodes = treeObj.getCheckedNodes(true);
	for (var i = 0; i < nodes.length; i++) {
		if (i > 0) {
			resourcesIdsStr += ',';
		}
		resourcesIdsStr += nodes[i].id;
	}
	$("#resourcesIdsStr").val(resourcesIdsStr);
	return true;
}
<#-- 树的点击事件 -->
function zTreeOnclick(event, treeId, treeNode) {
			var treeObj = $.fn.zTree.getZTreeObj("resourceTree");
			
			var childrenNodes = treeObj.getNodeByParam("id",treeNode.id,null).children;
			if(childrenNodes != null){
				var childrenUrlArray = new Array();
				var childrenIdArray = new Array();
				for(var i = 0; i < childrenNodes.length; i++){
					childrenUrlArray[i] = childrenNodes[i].url;
					childrenIdArray[i] = childrenNodes[i].id;
				}
			} else {
				var childrenUrlArray = [treeObj.getNodeByParam("id",treeNode.id,null).url];
				var childrenIdArray = [treeNode.id];
			}
			for(var i = 0; i < childrenUrlArray.length; i++){
    			var nodeTemp = treeObj.getNodeByParam("id",childrenIdArray[i],null);
				var nodeObj = treeObj.getNodesByParam("url",childrenUrlArray[i],null);		
    			for(var m = 0; m < nodeObj.length; m++){
    				if(nodeTemp != nodeObj[m]){
    					treeObj.checkNode(nodeObj[m],null,true);	
    				}
    			}		
		}
}
</script>
</@layout.page>
</#escape>