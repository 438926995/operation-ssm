<#import "/account/layout.ftl" as layout>
<#escape x as x?html>
<@layout.page title="角色添加">

<div class="content-wrapper">
	<#-- Content Header (Page header) -->
	<section class="content-header">
	  <h1>
	    角色添加
	  </h1>
	  <ol class="breadcrumb">
	    <li class="active"><a href="${basePath}/account/index"><i class="fa fa-dashboard"></i> 首页</a></li>
	    <li><a href="${basePath}/user/list">用户管理</a></li>
	    <li class="active">角色添加</li>
	  </ol>
	</section>
	<#-- Main content -->
	<section class="content">
	<#-- 角色添加页面 -->
		
	<div class="box box-primary">
        <div class="box-header with-border">
          <h3 class="box-title">角色添加</h3>
        </div><!-- /.box-header -->
        <!-- form start -->
        
        <form role="form" id="roleadd" action="${basePath}/role/addSave" method="post" >
          <input type="hidden" name="token" value="${token}">
          <div class="box-body">
            <#if rab??>  
				<@spring.bind "rab.roleName" />  
			</#if>
            <div class="form-group <@spring.errorStyle /> " id="roleNameDiv">
              角色名称 &nbsp;<label for="roleName" id="roleNameLabel">
              <#if rab??>  
			    <@spring.showErrors "<br>"/>  
			  </#if>  
              </label>
              <input type="text" class="form-control" id="roleName" name="roleName" value="${(rab.roleName)!}" required>
            </div>
           	<div class="form-group">
	            <label>默认角色：&nbsp;</label>
	            <label>
	              <input type="radio" name="isDefault" id="isDefault0" value="0" 
					<#if rab??> 
					 <#if rab.isDefault?? && "${rab.isDefault}"=="0"> checked </#if> 
					<#else> checked </#if>
				  >否
	              <input type="radio" name="isDefault" id="isDefault1" value="1" <#if rab?? && rab.isDefault?? && "${rab.isDefault}"=="1"> checked </#if> >是
	            </label>
	         </div>
	         <div class="form-group">
	         	<label>权限:&nbsp;</label>
	         	<div id="resourceTree" class="ztree" style="height: auto;width: auto;"></div>
	         </div>
          </div><!-- /.box-body -->
          <div class="box-footer">
          <input type="hidden" id="resourcesIdsStr" name="authStr" value="">
          <@layout.security.authorize url="role/addSave">
            <button type="submit" class="btn btn-primary">添加</button>
           </@layout.security.authorize>
          </div>
        </form>
      </div><!-- /.box -->
		
	<#-- 角色添加页面结束 -->
	</section>
</div>
<script src="${basePath}/jslib/parsley/parsley.min.js"></script>
<script src="${basePath}/jslib/parsley/i18n/zh_cn.js"></script>
<link rel="stylesheet" href="${basePath}/stylelib/parsley.css">
<!-- zTree -->
<link rel="stylesheet" href="${basePath}/stylelib/zTreeStyle/zTreeStyle.css" type="text/css">
<script type="text/javascript" src="${basePath}/jslib/jquery.ztree.all.js"></script>
<script>
	<#-- 表单提交前角色名空判断 -->
	$(function(){
		$('#roleadd').submit(function(){
			<#-- 角色名为空判断 -->
			var role = $('#roleName').val().trim();
            if(role == ''){
            	$("#roleNameDiv").addClass("has-error");
                $("#roleNameLabel").html('角色名不能为空');
                return false;
            } else {
            	if($("#roleNameDiv").hasClass('has-error')){
            		return false;
            	}
            }
            
            var resourcesIdsStr = '';
			var treeObj = $.fn.zTree.getZTreeObj("resourceTree");
			var nodes = treeObj.getCheckedNodes(true);
			for (var i = 0; i < nodes.length; i++) {
				if (i > 0) {
					resourcesIdsStr += ',';
				}
				resourcesIdsStr += nodes[i].nodeId;				
			}
			$("#resourcesIdsStr").val(resourcesIdsStr);
		});
	});

$(document).ready(function(){  
	<#-- 添加表单校验 -->
	$('#roleadd').parsley();
	<#-- 光标移开后，验证角色名是否唯一 -->
	$("#roleName").blur(function(){  
		var user = {roleName:$("#roleName").val() }
		$.ajax({  
	            url : "${basePath}/role/judgeNameSingle",  
	            type : "POST",  
	            data : JSON.stringify(user), 
	            dataType: 'json',  
	            contentType:'application/json;charset=UTF-8', 
	            success : function(result) {
	            	if(!result.isSuccess){ 
	                	$("#roleNameDiv").addClass("has-error");
	                	$("#roleNameLabel").html(result.failReason);
	                }else {
	                	$("#roleNameDiv").removeClass("has-error");
	                	$("#roleNameLabel").html("");
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
		}
	};

	var zNodes =[
		<#list authList as auth>
			<#if auth?is_first>
				{ id : ${auth.authModuleId}, pId : "root${auth_index}", name : "${auth.authName}", nodeId : ${auth.authId}
				<#if auth.checked>, checked:true</#if>},
			<#elseif auth.authModuleId != authList[auth_index-1].authModuleId >
			    { id : ${auth.authModuleId}, pId : "root${auth_index}", name : "${auth.authName}", nodeId : ${auth.authId}
			    <#if auth.checked>, checked:true</#if>},
			<#else>
				{ id : ${auth.authId}, pId : ${auth.authModuleId}, name : "${auth.authName}", nodeId : ${auth.authId}
				<#if auth.checked>, checked:true</#if>},
			</#if>
	    </#list>
	    		
	];
	$.fn.zTree.init($("#resourceTree"), setting, zNodes);
}
</script>
</@layout.page>
</#escape>