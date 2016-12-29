<#import "/account/layout.ftl" as layout>
<#escape x as x?html>
<@layout.page title="资源${(rab.resourceId)???string('编辑','添加')}">

<div class="content-wrapper">
	<#-- Content Header (Page header) -->
	<section class="content-header">
		<h1>
			资源${(rab.resourceId)???string('编辑','添加')}
		</h1>
		<ol class="breadcrumb">
			<li class="active"><a href="${basePath}/account/index"><i class="fa fa-dashboard"></i>首页</a></li>
			<li><a href="${basePath}/resource/list">资源管理</a></li>
			<li class="active">资源${(rab.resourceId)???string('编辑','添加')}</li>
		</ol>
	</section>
	<#-- Main content -->
	<section class="content">
		<#-- 资源编辑页面 -->
		<div class="box box-primary">
			<div class="box-header with-border">
				<h3 class="box-title">资源${(rab.resourceId)???string('编辑','添加')}</h3>
			</div><!-- /.box-header -->
		
			<!-- form start -->
			<form role="form" id="resourceClass" action="${basePath}/resource/save" method="post">
			<input type="hidden" name="resourceId" value="${(rab.resourceId)!}">
			<input type="hidden" name="token" value="${token}">
			<div class="box-body">
			
				<#if rab??>
				<@spring.bind "rab.resourceName" />
				</#if>
				<div class="form-group <@spring.errorStyle />" id="resourceNameDiv">
					资源名称&nbsp;
					<label for="resourceName" id="resourceNameLabel">
						<#if rab??>
						<@spring.showErrors "<br>"/>
						</#if>
					</label>
					<input type="text" class="form-control" id="resourceName" name="resourceName" value="${(rab.resourceName)!}" required>
				</div>
			
				<#if rab??>
				<@spring.bind "rab.resourceDesc" />
				</#if>
				<div class="form-group <@spring.errorStyle />">
					资源描述&nbsp;
					<label for="resourceDesc">
						<#if rab??>
						<@spring.showErrors "<br>"/>
						</#if>
					</label>
					<input type="text" class="form-control" id="resourceDesc" name="resourceDesc" value="${(rab.resourceDesc)!}" required>
				</div>
			
				<#if rab??>
				<@spring.bind "rab.resourceType" />
				</#if>
				<div class="form-group <@spring.errorStyle />">
					资源类型&nbsp;
					<label for="resourceType">
						<#if rab??>
						<@spring.showErrors "<br>"/>
						</#if>
					</label>
					<select id="resourceType" name="resourceType" class="form-control" required>
						<option value="URL" <#if (rab.resourceType)?? && (rab.resourceType == 'URL')>selected="selected"</#if>>URL</option>
						<#--<option value="METHOD" <#if (rab.resourceType)?? && (rab.resourceType == 'METHOD')>selected="selected"</#if>>METHOD</option>-->
						<option value="LIMB" <#if (rab.resourceType)?? && (rab.resourceType == 'LIMB')>selected="selected"</#if>>LIMB</option>
					</select>
				</div>
				
				<#if rab??>
				<@spring.bind "rab.grade" />
				</#if>
				<div class="form-group <@spring.errorStyle />">
					级别&nbsp;
					<label for="grade">
						<#if rab??>
						<@spring.showErrors "<br>"/>
						</#if>
					</label>
					<#if (rab.resourceId)?? && (rab.grade)??>
					<input type="hidden" id="grade" name="grade" value="${(rab.grade)!}">
					<input type="text" class="form-control" value="<#if (rab.grade == '1')>一级菜单<#elseif (rab.grade == '2')>二级菜单<#elseif (rab.grade == '3')>三级菜单</#if>" readonly="readonly">
					<#else>
					<select id="grade" name="grade" class="form-control" required>
						<option value="1" <#if (rab.grade)?? && (rab.grade == '1')>selected="selected"</#if>>一级(一级菜单)</option>
						<option value="2" <#if (rab.grade)?? && (rab.grade == '2')>selected="selected"</#if>>二级(二级菜单)</option>
						<option value="3" <#if (rab.grade)?? && (rab.grade == '3')>selected="selected"</#if>>三级(button)</option>
					</select>
					</#if>
				</div>
				
				<#if rab??>
				<@spring.bind "rab.parentId" />
				</#if>
				<div id="parentResourceDiv" class="form-group <@spring.errorStyle />" style="display: none;">
					所属父菜单&nbsp;
					<label for="parentId">
						<#if rab??>
						<@spring.showErrors "<br>"/>
						</#if>
					</label>
					<input type="hidden" id="parentId" name="parentId" value="${(rab.parentId)!}">
					<select id="firstResourceId" name="parentIds[0]" class="form-control">
						<option value="">请选择一级菜单</option>
						<#if firstMenus?? && firstMenus?size gt 0>
						<#list firstMenus as firstMenu>
						<option value="${firstMenu.resourceId}" <#if (rab.parentIds)?? && (rab.parentIds?size gt 0) && rab.parentIds[0]?? && (rab.parentIds[0] == firstMenu.resourceId)>selected="selected"</#if>>${firstMenu.resourceName}</option>
						</#list>
						</#if>
					</select>
					<select id="secondResourceId" name="parentIds[1]" class="form-control" style="display: none;">
						<option value="">请选择二级菜单</option>
						<#if secondMenus?? && secondMenus?size gt 0>
						<#list secondMenus as secondMenu>
						<option value="${secondMenu.resourceId}" <#if (rab.parentIds)?? && (rab.parentIds?size gt 1) && rab.parentIds[1]?? && (rab.parentIds[1] == secondMenu.resourceId)>selected="selected"</#if>>${secondMenu.resourceName}</option>
						</#list>
						</#if>
					</select>
				</div>
				
				<#if rab??>
				<@spring.bind "rab.resourceString" />
				</#if>
				<div class="form-group <@spring.errorStyle />">
					资源内容&nbsp;
					<label for="resourceString">
						<#if rab??>
						<@spring.showErrors "<br>"/>
						</#if>
					</label>
					<input type="text" class="form-control" id="resourceString" name="resourceString" value="${(rab.resourceString)!}">
				</div>
				
				<#if rab??>
				<@spring.bind "rab.showNav" />
				</#if>
				<div class="form-group <@spring.errorStyle />">
					左侧显示&nbsp;
					<label for="showNav">
						<#if rab??>
						<@spring.showErrors "<br>"/>
						</#if>
					</label>
					<input type="radio" name="showNav" value="0" <#if (rab.showNav)?? && (rab.showNav == '0')>checked="checked"</#if>>否
					<input type="radio" name="showNav" value="1" <#if (rab.showNav)?? && (rab.showNav == '1')>checked="checked"</#if>>是
				</div>
				
				<#if rab??>
				<@spring.bind "rab.isEnabled" />
				</#if>
				<div class="form-group <@spring.errorStyle />">
					是否可用&nbsp;
					<label for="isEnabled">
						<#if rab??>
						<@spring.showErrors "<br>"/>
						</#if>
					</label>
					<input type="radio" name="isEnabled" value="0" <#if (rab.isEnabled)?? && (rab.isEnabled == '0')>checked="checked"</#if>>否
					<input type="radio" name="isEnabled" value="1" <#if (rab.isEnabled)?? && (rab.isEnabled == '1')>checked="checked"</#if>>是
				</div>
				
				<#if rab??>
				<@spring.bind "rab.sortIndex" />
				</#if>
				<div class="form-group <@spring.errorStyle />">
					排序号&nbsp;
					<label for="sortIndex">
						<#if rab??>
						<@spring.showErrors "<br>"/>
						</#if>
					</label>
					<input type="text" class="form-control" id="sortIndex" name="sortIndex" value="${(rab.sortIndex)!}" required>
				</div>

			</div>
			<!-- /.box-body -->
			
			<div class="box-footer">
				<button type="button" class="btn btn-primary" onclick="listResource();">返回</button>
				<@layout.security.authorize url="resource/save">
                    <button type="submit" class="btn btn-primary" onclick="return onSubmit();">保存</button>
				</@layout.security.authorize>
			</div>
			</form>
		</div>
		<!-- /.box -->
		<#-- 资源编辑页面结束 -->
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

function listResource(){
	window.location.href="${basePath}/resource/list";
}

$(function() {
	<#if (rab.grade)?? && (rab.grade == '2')>
		$("#parentResourceDiv").show();
	<#elseif (rab.grade)?? && (rab.grade == '3')>
		$("#parentResourceDiv").show();
		$("#secondResourceId").show();
	</#if>
});

$("#grade").change(function() {
	var grade = $(this).val();
	if (grade == 1) {
		$("#parentResourceDiv").hide();
	} else if (grade == 2) {
		$("#parentResourceDiv").show();
		$("#secondResourceId").hide();
		$('#firstResourceId').attr("onchange", "");
	} else if (grade == 3) {
		$("#parentResourceDiv").show();
		$("#secondResourceId").show();
		$('#firstResourceId').attr("onchange", "getChildren();");
		$("#firstResourceId").trigger("onchange");
	}
});

function getChildren() {
	$("#secondResourceId").empty();
	$("#secondResourceId").append('<option value="">请选择二级菜单</option>');
	
	var firstResourceId = $("#firstResourceId").val();
	if (firstResourceId == '') {
		return;
	}
	
	$.ajax({
		url : '${basePath}/resource/children/' + firstResourceId,
		type : 'get',
		dataType : 'json',
		success : function(result) {
			$.each(result, function(idx, obj) {
				$("#secondResourceId").append('<option value="' + obj.resourceId + '">' + obj.resourceName + '</option>');
			});
		},
		error : function(data) {
			alert("获取菜单信息失败");
		}
	});
}

function onSubmit() {
	var grade = $("#grade").val();
	if (grade == 1) {
		$("#parentId").val(0);
	} else if (grade == 2) {
		$("#parentId").val($("#firstResourceId").val());
	} else if (grade == 3) {
		$("#parentId").val($("#secondResourceId").val());
	}
	return true;
}
</script>
</@layout.page>
</#escape>