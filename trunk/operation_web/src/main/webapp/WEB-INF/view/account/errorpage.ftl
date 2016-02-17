<#assign basePath="${request.getContextPath()}"> 
<#--<#import "/account/layout.ftl" as layout>-->
<#escape x as x?html>
<#--<@layout.page title="500">-->
<base id="basePath" href="${basePath}">
<div class="content-wrapper">
	<#-- Content Header (Page header) -->
	<section class="content-header">
	  <h1>
	    500
	  </h1>
	  <ol class="breadcrumb">
	    <li class="active"><a href="${basePath}/account/index"><i class="fa fa-dashboard"></i> 首页</a></li>
	  </ol>
	</section>
	<#-- Main content -->
	<section class="content">
	<#-- 500页面 -->
		<div class="error-page">
            <h2 class="headline text-red">500</h2>
            <div class="error-content">
              <h3><i class="fa fa-warning text-red"></i> Oops! Something went wrong.</h3>
              <p>
                We will work on fixing that right away.
                Meanwhile, you may <a href="${basePath}/account/index">返回首页</a> .
              </p>
            </div>
          </div>
<#-- 500页面结束 -->
</section>
</div>
<#--</@layout.page>-->
</#escape>