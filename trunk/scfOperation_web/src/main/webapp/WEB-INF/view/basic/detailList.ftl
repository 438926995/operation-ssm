<#import "/account/layout.ftl" as layout>
<#escape x as x?html>
<@layout.page title="基础数据详情列表">
<head>
	<#-- 弹出框 -->
	<link rel="stylesheet" href="${basePath}/stylelib/remodal.css">
	<link rel="stylesheet" href="${basePath}/stylelib/remodal-default-theme.css">
	<#-- Bootstrap 颜色选择器 -->
    <link rel="stylesheet" href="${basePath}/stylelib/adminlte/plugins/colorpicker/bootstrap-colorpicker.min.css">
    <#-- Bootstrap 日期选择器 -->
    <link rel="stylesheet" href="${basePath}/stylelib/adminlte/plugins/daterangepicker/daterangepicker-bs3.css">
</head>
<body>
<div class="content-wrapper">
	<!-- Content Header (Page header) -->
	<section class="content-header">
	  <h1>
	    基础数据详情列表
	  </h1>
	  <ol class="breadcrumb">
	    <li><a href="${basePath}/account/index"><i class="fa fa-dashboard"></i>首页</a></li>
	    <li><a href="${basePath}/basic/list">基础数据列表</a></li>
	    <li class="active">基础数据详情列表</li>
	  </ol>
	</section>
	<!-- Main content -->
	<section class="content">
	<#-- 基础数据列表开始 -->
		<div class="box">
			<div class="box-header">
			<#-- 查询 -->
			<form action="${basePath}/basic/detailList/${bdk.typeId!}" method="get">
			<div id="user_list_search" >
				<div class="form-group">
					<label>类型名称：</label>
					<input id="typeCdName" name="typeCdName" value="<#if bdk??>${bdk.typeCdName!}</#if>" class="f-text">
					<input type="submit" value="搜索" >
					<@layout.security.authorize url="basic/detailAdd">
						<input type="button" value="添加" onclick="javascript:addDetailBasic(${bdk.typeId!});" >
					</@layout.security.authorize>
				</div>
			</div>
			</form>
			</div>
                <div class="box-body">
                  <table id="basicdata_list" class="table table-bordered table-hover">
                    <thead>
                      <tr>
                        <th>TYPE_ID</th>
						<th>分类名称</th>
						<th>TYPE_CD</th>
						<th>类型名称</th>
						<th>排序号</th>
						<th>状态</th>
						<th>操作</th>
                      </tr>
                    </thead>
                    <tbody>
                    <#list tbData.list as basic>
						<tr>
							<td style="text-align:left">${basic.typeId!""}</td>	
							<td style="text-align:left">${basic.typeIdName!""}</td>	
							<td style="text-align:left">${basic.typeCd!""}</td>	
							<td style="text-align:left">${basic.typeCdName!""}</td>	
							<td style="text-align:left">${basic.sortIndex!""}</td>	
							<td style="text-align:left"><#if basic.isVisible??><#if basic.isVisible==1>可见<#else>不可见</#if></#if></td>
							<td>
							<#--<a href="${basePath}/basic/detailList/${basic.typeCd!!}" >查看</a>-->
							<@layout.security.authorize url="basic/detailEdit">
								<a href="${basePath}/basic/detailEdit?typeId=${basic.typeId!!}&typeCd=${basic.typeCd!!}">编辑</a>
							</@layout.security.authorize>
							</td>
						</tr>
					</#list>
                    </tbody>
                  </table>
                </div><!-- /.box-body -->
              	<div id = "productpager" class="box-footer">
					<#include "/common/pager_bar.ftl">
				</div>
              </div><!-- /.box -->



  <#-- 用户页面结束 -->
  </section>
</div>

<script>
	function addDetailBasic(typeId){
		window.location.href = basePath + "basic/detailAdd?typeId="+typeId;
	}
</script>

</@layout.page>
</#escape>