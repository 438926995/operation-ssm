<#import "/account/layout.ftl" as layout>
<#escape x as x?html>
<@layout.page title="用户添加">

<div class="content-wrapper">
	<#-- Content Header (Page header) -->
	<section class="content-header">
	  <h1>
	    用户添加
	  </h1>
	  <ol class="breadcrumb">
	    <li class="active"><a href="${basePath}/account/index"><i class="fa fa-dashboard"></i> 首页</a></li>
	    <li><a href="${basePath}/user/list">用户管理</a></li>
	    <li class="active">用户添加</li>
	  </ol>
	</section>
	<#-- Main content -->
	<section class="content">
	<#-- 用户添加页面 -->
		
	<div class="box box-primary">
        <div class="box-header with-border">
          <h3 class="box-title">用户添加</h3>
        </div><!-- /.box-header -->
        <!-- form start -->
        
        <form role="form" id="useradd" action="${basePath}/user/addSave" method="post" >
          <div class="box-body">
          	  <#if uab??>  
			    <@spring.bind "uab.userName" />  
			  </#if>    
            <div class="form-group <@spring.errorStyle /> " id="usernameDiv">
              用户邮箱 &nbsp;<label for="userName" id="usernameLabel">
              <#if uab??>  
			    <@spring.showErrors "<br>"/>  
			  </#if>  
              </label>
              <input type="email" class="form-control" id="userName" name="userName" value="${(uab.userName)!}" placeholder="Enter email" 
              	required data-parsley-type="email" >
            </div>
            <#if uab??>  
			    <@spring.bind "uab.pswd" />  
			</#if> 
            <div class="form-group <@spring.errorStyle />">
              密码 &nbsp;<label for="pswd">
              <#if uab??>  
			    <@spring.showErrors "<br>"/>  
			  </#if>
              </label>
              <input type="password" class="form-control" id="pswd" name="pswd" placeholder="Password" 
              	required data-parsley-trigger="change"  >
            </div>
            <div class="form-group">
	            <label>是否为管理员：</label>
	            <label>
	              <input type="radio" name="isAdmin" id="isAdmin0" value="0" 
					<#if uab??> 
					 <#if uab.isAdmin?? && "${uab.isAdmin}"=="0"> checked </#if> 
					<#else> checked </#if>
				  >否
	              <input type="radio" name="isAdmin" id="isAdmin1" value="1" <#if uab?? && uab.isAdmin?? && "${uab.isAdmin}"=="1"> checked </#if> >是
	            </label>
	         </div>
	         <div class="form-group">
	         <div class="form-group <@spring.errorStyle /> " id="rolesListDiv">
	         角色：&nbsp;<label for="rolesList" id="rolesListLabel">
              <#if uab??>  
			    <@spring.showErrors "<br>"/>  
			  </#if>  
              </label>
	         	
	         	<select name="roleId" id="rolesList" >
						<option value=""></option>
						<#list rolesList as role>
							<option value="${role.roleId?string}">${role.roleName}</option>
						</#list>
				</select>
				</div>
	         </div>
	         <input type="hidden" name="token" value="${token}">
          </div><!-- /.box-body -->
          <div class="box-footer">
          <@layout.security.authorize url="user/addSave">
            <button type="submit" class="btn btn-primary" id="add_user">添加</button>
           </@layout.security.authorize>
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
	<#-- 添加表单校验 -->
	$('#useradd').parsley();
	<#-- 光标移开后，验证用户名是否唯一 -->
	$("#userName").blur(function(){  
		var user = {userName:$("#userName").val() }
		$.ajax({  
	            url : "${basePath}/user/judgeNameSingle",  
	            type : "POST",  
	            data : JSON.stringify(user), 
	            dataType: 'json',  
	            contentType:'application/json;charset=UTF-8', 
	            success : function(result) {
	            	if(!result.isSuccess){ 
	                	$("#usernameDiv").addClass("has-error");
	                	$("#usernameLabel").html(result.failReason);
	                }else {
	                	$("#usernameDiv").removeClass("has-error");
	                	$("#usernameLabel").html("");
	                }
	            },
	            error : function(result) {  
	                alert(result.statusText);
	            }  
	     });
		
     });
});
	$(function(){
		$('#useradd').submit(function(){
			var role = $('#rolesList').val();
			if(role == ''){
				$("#rolesListDiv").addClass("has-error");
	            $("#rolesListLabel").html("请选择角色");
				return false;
			}
		});
	});
</script>
</@layout.page>
</#escape>