<#import "/account/layout.ftl" as layout>
<#escape x as x?html>
<@layout.page title="用户密码修改">

<div class="content-wrapper">
	<#-- Content Header (Page header) -->
	<section class="content-header">
	  <h1>
	    用户密码修改
	  </h1>
	  <ol class="breadcrumb">
	    <li class="active"><a href="${basePath}/account/index"><i class="fa fa-dashboard"></i> 首页</a></li>
	    <li class="active">用户密码修改</li>
	  </ol>
	</section>
	<#-- Main content -->
	<section class="content">
	<#-- 用户密码修改页面 -->
		
	<div class="box box-primary">
        <div class="box-header with-border">
          <h3 class="box-title">用户密码修改</h3>
        </div><!-- /.box-header -->
        <!-- form start -->
        
        <form role="form" id="changepwd" action="${basePath}/user/changePwdSave" method="post" data-parsley-validate="">
          <div class="box-body">
          	<#if cpb??>  
			    <@spring.bind "cpb.pswd" />  
			</#if> 
            <div class="form-group <@spring.errorStyle />">
              当前密码 &nbsp;<label for="pswd">
              <#if cpb??>  
			    <@spring.showErrors "<br>"/>  
			  </#if>
              </label>
              <input type="password" class="form-control" id="pswd" name="pswd" placeholder="Password" value="${(cpb.pswd)!}"
              	required data-parsley-trigger="change"  data-parsley-length="[6, 20]">
            </div> 
            
          	<#if cpb??>  
			    <@spring.bind "cpb.newPswd" />  
			</#if> 
            <div class="form-group <@spring.errorStyle />">
              新密码 &nbsp;<label for="newPswd">
              <#if cpb??>  
			    <@spring.showErrors "<br>"/>  
			  </#if>
              </label>
              <input type="password" class="form-control" id="newPswd" name="newPswd" placeholder="Password" 
              	required data-parsley-trigger="change"  data-parsley-length="[6, 20]">
            </div>
            
            <#if cpb??>  
			    <@spring.bind "cpb.newPswdConfirm" />  
			</#if> 
            <div class="form-group <@spring.errorStyle />">
              确认新密码 &nbsp;<label for="newPswdConfirm">
              <#if cpb??>  
			    <@spring.showErrors "<br>"/>  
			  </#if>
              </label>
              <input type="password" class="form-control" id="newPswdConfirm" name="newPswdConfirm" placeholder="Password" 
              	required data-parsley-trigger="change"  data-parsley-length="[6, 20]">
            </div>  
          	
          	  
	         <input type="hidden" name="token" value="${token}">
          </div><!-- /.box-body -->
          <div class="box-footer">
            <button type="submit" class="btn btn-primary">修改密码</button>
          </div>
        </form>
      </div><!-- /.box -->
		
	<#-- 用户添加页面结束 -->
	</section>
</div>
<script src="${basePath}/jslib/parsley/parsley.min.js"></script>
<script src="${basePath}/jslib/parsley/i18n/zh_cn.js"></script>
<link rel="stylesheet" href="${basePath}/stylelib/parsley.css">

</@layout.page>
</#escape>