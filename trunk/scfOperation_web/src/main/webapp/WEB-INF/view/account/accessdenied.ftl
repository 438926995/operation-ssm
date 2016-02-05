<#import "/account/layout.ftl" as layout>
<#escape x as x?html>
<@layout.page title="AccessDenied">

<div class="content-wrapper">
	<#-- Content Header (Page header) -->
	<section class="content-header">
	  <h1>
	    无权限
	  </h1>
	  <ol class="breadcrumb">
	    <li class="active"><a href="${basePath}/account/index"><i class="fa fa-dashboard"></i> 首页</a></li>
	  </ol>
	</section>
	<#-- Main content -->
	<section class="content">
		<div class="error-page">
            <h2 class="headline text-red">无权限</h2>
            <div class="error-content">
              <h3><i class="fa fa-warning text-red"></i> Oops! 无权限.</h3>
              <p>
                <a href="${basePath}/account/index">返回首页</a> .
              </p>
            </div>
          </div>
</section>
</div>

</@layout.page>
</#escape>