<#import "/account/layout.ftl" as layout>
<#escape x as x?html>
<@layout.page title="城市基础表列表">
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
	    城市基础表列表
	  </h1>
	  <ol class="breadcrumb">
	    <li><a href="${basePath}/account/index"><i class="fa fa-dashboard"></i>首页</a></li>
	    <li><a href="${basePath}/city/mCityTree/list">城市基础表列表</a></li>
	    <li class="active">城市基础表列表</li>
	  </ol>
	</section>
	<!-- Main content -->
	<section class="content">
	<#-- 城市基础表列表开始 -->
		<div class="box">
			<div class="box-header">
			<#-- 查询 -->
			<form action="${basePath}/city/mCityTree/list" method="get">
			<div id="user_list_search" >
				<div class="form-group">
					<label>省份名称：</label>
					<input id="cityName" name="cityName" value="<#if cityTreeVo??>${cityTreeVo.cityName!}</#if>" class="f-text">
					<input type="submit" value="搜索" >
				</div>
			</div>
			</form>
			</div>

                <div class="box-body">
                  <table id="basicdata_list" class="table table-bordered table-hover">
                    <thead>
                      <tr>
                        <th>省份ID</th>
						<th>省份名</th>
						<th>省份拼音</th>
						<th>操作</th>
                      </tr>
                    </thead>
                    <tbody>
                    <#list tbData.list as city>
						<tr>
							<td style="text-align:left">${city.cityID!""}</td>	
							<td style="text-align:left">${city.cityName!""}</td>	
							<td style="text-align:left">${city.cityPinyin!""}</td>	
							<td>
							<@layout.security.authorize url="city/mCityTree/citylist">
								<a href="${basePath}/city/mCityTree/citylist/${city.cityID!!}" >查看</a>
							</@layout.security.authorize>
							<@layout.security.authorize url="city/mCityTree/edit">
								<a href="${basePath}/city/mCityTree/edit?id=${city.id}&cityID=${city.cityID!!}&cityName=${city.cityName!!}&cityPinyin=${city.cityPinyin!!}">编辑</a>
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

</@layout.page>
</#escape>