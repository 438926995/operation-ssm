<#import "/account/layout.ftl" as layout>
<#escape x as x?html>
<@layout.page title="404">
<div class="content-wrapper">
	<#-- Content Header (Page header) -->
	<section class="content-header">
	  <h1>
	    404
	  </h1>
	  <ol class="breadcrumb">
	    <li class="active"><a href="${basePath}/account/index"><i class="fa fa-dashboard"></i> 首页</a></li>
	  </ol>
	</section>
	<#-- Main content -->
	<section class="content">
	<#-- 404页面 -->
		<div class="error-page">
			<h2 class="headline text-yellow">404</h2>
			<div class="error-content">
				<h3>
					<i class="fa fa-warning text-yellow"></i> Oops! Page not found.
				</h3>
				<p>
					We could not find the page you were looking for. Meanwhile, you may
					<a href="${basePath}/account/index">返回首页</a> .
				</p>
			</div>
		</div>
<#-- 404页面结束 -->
</section>
</div>
</@layout.page>
</#escape>