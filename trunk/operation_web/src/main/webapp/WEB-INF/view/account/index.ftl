<#import "/account/layout.ftl" as layout>
<#global basePath="${request.getContextPath()}">
<#escape x as x?html>
<@layout.page title="首页">
<div class="content-wrapper">
	<!-- Content Header (Page header) -->
	<section class="content-header">
	  <h1>
	    首页
	  </h1>
	  <ol class="breadcrumb">
	    <li class="active"><a href="${basePath}/account/index"><i class="fa fa-dashboard"></i> 首页</a></li>
	  </ol>
	</section>
	<!-- Main content -->
	<section class="content">
	<#-- Index 页面开始 -->
		
			Index
			
	<#-- Index 页面结束 -->
    </section>
</div>
</@layout.page>
</#escape>