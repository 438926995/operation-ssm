<#import "/account/layout.ftl" as layout>
<#escape x as x?html>
<@layout.page title="用户编辑">

<div class="content-wrapper">
	<#-- Content Header (Page header) -->
	<section class="content-header">
	  <h1>
	    	用户编辑
	  </h1>
	  <ol class="breadcrumb">
	    <li class="active"><a href="${basePath}/account/index"><i class="fa fa-dashboard"></i> 首页</a></li>
	    <li><a href="${basePath}/user/list">用户管理</a></li>
	    <li class="active">用户编辑</li>
	  </ol>
	</section>
	<#-- Main content -->
	<section class="content">
	<#-- 用户编辑页面 -->
		
	<div class="box box-primary">
        <div class="box-header with-border">
          <h3 class="box-title">用户编辑</h3>
        </div><!-- /.box-header -->
        <!-- form start -->
        
        <form id="user" action="${basePath}/user/editSave" method="post">
          <div class="box-body"> 
          	<div class="form-group">
          		<label>用户姓名</label>
          		<input readonly="true" type="text" class="form-control" id="userName" name="userName" value="${(user.userName)!}">
          	</div>
          	<div class="form-group">
          		<label>是否为管理员</label>
          		<label>
	              <input type="radio" name="isAdmin" id="isAdmin0" value="0" 
					<#if user??> 
					 <#if user.isAdmin?? && "${user.isAdmin}"=="0"> checked </#if> 
					<#else> checked </#if>
				  >否
	              <input type="radio" name="isAdmin" id="isAdmin1" value="1" <#if user?? && user.isAdmin?? && "${user.isAdmin}"=="1"> checked </#if> >是
	            </label>
          	</div>
          	<div class="form-group">
          	<label>用户角色</label>
          	<select name="roleId" id="rolesList" >
				<#list rolesList as role>
					<option value="${role.roleId?string}" <#if user.roleId?? && "${user.roleId}"=="${role.roleId?string}">selected="selected"</#if> >${role.roleName}</option>
				</#list>
			</select>
          	</div>
          	<div class="box-footer">
          	<input type="hidden" name="userId" value="${(user.userId)!}">
          	<input type="hidden" name="token" value="${token}">
            <button type="submit" class="btn btn-primary">编辑</button>
          	</div>
          </div>
         </form>   

     </div><!-- /.box -->
		
	<#-- 用户编辑页面结束 -->
	</section>
</div>
<script src="${basePath}/jslib/parsley/parsley.min.js"></script>
<script src="${basePath}/jslib/parsley/i18n/zh_cn.js"></script>
<link rel="stylesheet" href="${basePath}/stylelib/parsley.css">

<script src="${basePath}/jslib/remodal.js"></script>
<script src="${basePath}/jslib/template.js"></script>

</@layout.page>
</#escape>