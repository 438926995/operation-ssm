<#import "/account/layout.ftl" as layout>
<#escape x as x?html>
<@layout.page title="金融机构添加">

<div class="content-wrapper">
	<#-- Content Header (Page header) -->
	<section class="content-header">
	  <h1>
	    	账号编辑 
	  </h1>
	  <ol class="breadcrumb">
	    <li class="active"><a href="${basePath}/account/index"><i class="fa fa-dashboard"></i> 首页</a></li>
	    <li><a href="${basePath}/finance/userlist">金融机构 管理</a></li>
	    <li class="active">账号编辑/li>
	  </ol>
	</section>
	<#-- Main content -->
	<section class="coent">
	<#-- 账号编辑页面 -->
		
	<div class="box box-primary">
        <div class="box-header with-border">
          <h3 class="box-title">账号编辑</h3>
        </div><!-- /.box-header -->
        <!-- form start -->
        
        <form role="form" id="martUsers" action="${basePath}/finance/editUserSave" method="post" data-parsley-validate="">
          <div class="box-body"> 
          
          	<#if fab??>  
			    <@spring.bind "fab.userName" />  
			</#if> 
            <div class="form-group <@spring.errorStyle /> " id="userNameDiv">
            	账号名 &nbsp;
            	<label for="userName" id="userNameLabel">
            	  <#if fab??>  
				    <@spring.showErrors "<br>"/>  
				  </#if>  
            	</label>
            	<input type="text" class="form-control" id="userName" name="userName" value="${(fab.userName)!}" data-parsley-trigger="change" required data-parsley-length="[2, 50]" data-parsley-maxlength="50" >
            </div>
            
            <#if fab??>  
			    <@spring.bind "fab.userPswd" />  
			</#if>
            <div class="form-group <@spring.errorStyle />">
             	 密码 &nbsp;
             	 <label for="userPswd">
             	 	<#if fab??>  
					    <@spring.showErrors "<br>"/>  
					</#if> 
             	 </label>
              	<input type="password" class="form-control" id="userPswd" name="userPswd" placeholder="Password" value="${(fab.userPswd)!}" required data-parsley-trigger="change" data-parsley-length="[2, 100]" data-parsley-maxlength="100" >
            </div>
            
            <#if fab??>  
			    <@spring.bind "fab.userFlag" />  
			</#if>
            <div class="form-group <@spring.errorStyle />">
             	类型&nbsp;
             	<label for="userFlag">
             		<#if fab??>  
					    <@spring.showErrors "<br>"/>  
					</#if> 
             	</label>
              	<input name="userFlag"  type="radio" value="1" <#if fab.userFlag?? && "${fab.userFlag}"=="1">checked</#if> required="" disabled="disabled">商家
				<input name="userFlag"  type="radio" value="2" <#if fab.userFlag?? && "${fab.userFlag}"=="2">checked</#if> >金融机构
            </div>
            
            <#if fab??>  
			    <@spring.bind "fab.foId" />  
			</#if>
            <div class="form-group <@spring.errorStyle />">
             	金融机构&nbsp;
             	<label for="foId">
             		<#if fab??>  
					    <@spring.showErrors "<br>"/>  
					</#if> 
             	</label>
              	${fab.foName!""}
            </div>
            
            <#if fab??>  
			    <@spring.bind "fab.nickName" />  
			</#if>
            <div class="form-group <@spring.errorStyle /> " id="nickNameDiv">
            	昵称 &nbsp;
            	<label for="nickName" id="nickNameLabel">
            		<#if fab??>  
					    <@spring.showErrors "<br>"/>  
					</#if>
            	</label>
            	<input type="text" class="form-control" id="nickName" name="nickName" value="${(fab.nickName)!}" data-parsley-trigger="change" data-parsley-length="[5, 100]" data-parsley-maxlength="200">
            </div>
            
            <#if fab??>  
			    <@spring.bind "fab.mobilePhone" />  
			</#if>
            <div class="form-group <@spring.errorStyle /> " id="mobilePhoneDiv">
            	手机号码&nbsp;
            	<label for="mobilePhone" id="mobilePhoneLabel">
            		<#if fab??>  
					    <@spring.showErrors "<br>"/>  
					</#if>
                </label>
              <input type="text" class="form-control" id="mobilePhone" name="mobilePhone" value="${(fab.mobilePhone)!}" required data-parsley-trigger="change" data-parsley-maxlength="20" >
            </div>
            
            <#if fab??>  
			    <@spring.bind "fab.userEmail" />  
			</#if>
            <div class="form-group <@spring.errorStyle /> " id="userEmailDiv">
             	 邮箱 &nbsp;
             	 <label for="userEmail" id="userEmailLabel">
             	 	<#if fab??>  
					    <@spring.showErrors "<br>"/>  
					</#if>
             	 </label>
              <input type="email" class="form-control" id="userEmail" name="userEmail" value="${(fab.userEmail)!}" placeholder="Enter email" required data-parsley-trigger="change" data-parsley-maxlength="200" >
            </div>
            <#if fab??>  
			    <@spring.bind "fab.userStatus" />  
			</#if>
            <div class="form-group <@spring.errorStyle />">
             	状态 &nbsp;
             	<label for="userStatus">
             		<#if fab??>  
					    <@spring.showErrors "<br>"/>  
					</#if>
             	</label>
             	
              	<input name="userStatus"  type="radio" value="1" <#if fab.userStatus?? && "${fab.userStatus}"=="1">checked</#if> required="">开启
				<input name="userStatus"  type="radio" value="2" <#if fab.userStatus?? && "${fab.userStatus}"=="2">checked</#if> >关闭
            </div>
            
            <#if fab??>
			    <@spring.bind "fab.userSex" />  
			</#if>
            <div class="form-group <@spring.errorStyle />">
             	性别 &nbsp;
             	<label for="userSex">
             		<#if fab??>  
					    <@spring.showErrors "<br>"/>  
					</#if>
             	</label>
              	<input name="userSex"  type="radio" value="1" required="" <#if fab.userSex?? && "${fab.userSex}"=="1">checked</#if> >男
				<input name="userSex"  type="radio" value="2" <#if fab.userSex?? && "${fab.userSex}"=="2">checked</#if> >女
            </div>
            
            <#if fab??>  
			    <@spring.bind "fab.userAge" />  
			</#if>
            <div class="form-group <@spring.errorStyle /> " id="userAgeDiv">
            	年龄&nbsp;
            	<label for="userAge" id="userAgeLabel">
            		<#if fab??>  
					    <@spring.showErrors "<br>"/>  
					</#if>
                </label>
              <input type="text" class="form-control" id="userAge" name="userAge" value="${(fab.userAge)!}" data-parsley-trigger="change" data-parsley-maxlength="9" data-parsley-type="integer">
            </div>
            
            <#if fab??>  
			    <@spring.bind "fab.userSid" />  
			</#if>
            <div class="form-group <@spring.errorStyle /> " id="userSidDiv">
            	身份证号&nbsp;
            	<label for="userSid" id="userSidLabel">
            		<#if fab??>  
					    <@spring.showErrors "<br>"/>  
					</#if>
                </label>
              <input type="text" class="form-control" id="userSid" name="userSid" value="${(fab.userSid)!}" required data-parsley-trigger="change" data-parsley-maxlength="20">
            </div>
            
            <#if fab??>  
			    <@spring.bind "fab.userAddr" />  
			</#if>
            <div class="form-group <@spring.errorStyle /> " id="userAddrDiv">
            	住址&nbsp;
            	<label for="userSid" id="userAddrLabel">
            		<#if fab??>  
					    <@spring.showErrors "<br>"/>  
					</#if>
                </label>
              <input type="text" class="form-control" id="userAddr" name="userAddr" value="${(fab.userAddr)!}" data-parsley-trigger="change" data-parsley-maxlength="100">
            </div>
            
            <input type="hidden" name="userId" value="${fab.userId}">
            <input type="hidden" name="foId" value="${fab.foId}">
            <input type="hidden" name="foName" value="${fab.foName}">
            <input type="hidden" name="token" value="${token}">
          </div><!-- /.box-body -->
          <div class="box-footer">
            <button type="submit" class="btn btn-primary">编辑</button>
          </div>
        </form>
      </div><!-- /.box -->
		
	<#-- 账号编辑页面结束 -->
	</section>
</div>
<script src="${basePath}/jslib/parsley/parsley.min.js"></script>
<script src="${basePath}/jslib/parsley/i18n/zh_cn.js"></script>
<link rel="stylesheet" href="${basePath}/stylelib/parsley.css">

</@layout.page>
</#escape>