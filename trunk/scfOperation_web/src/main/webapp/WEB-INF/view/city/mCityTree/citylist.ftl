<#import "/account/layout.ftl" as layout>
<#escape x as x?html>
<@layout.page title="城市基础表列表详情">
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
	    城市基础表列表详情
	  </h1>
	  <ol class="breadcrumb">
	    <li><a href="${basePath}/account/index"><i class="fa fa-dashboard"></i>首页</a></li>
	    <li><a href="${basePath}/city/mCityTree/list">城市基础表列表</a></li>
	    <li class="active">城市基础表列表详情</li>
	  </ol>
	</section>
	<!-- Main content -->
	<section class="content">
	<#-- 城市基础表列表详情开始 -->
		<div class="box">
			<div class="box-header">
			<#-- 查询 -->
			<form action="${basePath}/city/mCityTree/citylist/${cityTreeVo.parentID!!}" method="get">
			<div id="user_list_search" >
				<div class="form-group">
					<label>城市名称：</label>
					<input id="cityName" name="cityName" value="<#if cityTreeVo??>${cityTreeVo.cityName!}</#if>" class="f-text">
					<label>城市类型：</label>
						<select name="cityType" id="cityType" >
							<option value=""  <#if cityTreeVo.cityType?? && "${cityTreeVo.cityType}"=="">selected="selected"</#if> >全部</option>
							<option value="1"  <#if cityTreeVo.cityType?? && "${cityTreeVo.cityType}"=="1">selected="selected"</#if> >直辖市</option>
							<option value="2" <#if cityTreeVo.cityType?? && "${cityTreeVo.cityType}"=="2">selected="selected"</#if> >地级城市</option>
						  	<option value="3" <#if cityTreeVo.cityType?? && "${cityTreeVo.cityType}"=="3">selected="selected"</#if> >区</option>
						  	<option value="4" <#if cityTreeVo.cityType?? && "${cityTreeVo.cityType}"=="4">selected="selected"</#if> >县级市</option>
						  	<option value="5" <#if cityTreeVo.cityType?? && "${cityTreeVo.cityType}"=="5">selected="selected"</#if> >县</option>
						</select>
					<label>是否有效：</label>
						<select name="isValid">
							<option value="" <#if cityTreeVo.isValid?? && "${cityTreeVo.isValid}"=="">selected="selected"</#if> >全部</option>
							<option value="0" <#if cityTreeVo.isValid?? && "${cityTreeVo.isValid}"=="0">selected="selected"</#if> >否</option>
							<option value="1" <#if cityTreeVo.isValid?? && "${cityTreeVo.isValid}"=="1">selected="selected"</#if> >是</option>
						</select>
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
                        <th>城市ID</th>
						<th>城市名</th>
						<th>城市拼音</th>
						<th>城市类型</th>
						<th>是否有效</th>
						<th>操作</th>
                      </tr>
                    </thead>
                    <tbody>
                    <#list tbData.list as city>
						<tr>
							<td style="text-align:left">${city.parentID!""}</td>	
							<td style="text-align:left">${city.parentName!""}</td>	
							<td style="text-align:left">${city.cityID!""}</td>	
							<td style="text-align:left">${city.cityName!""}</td>	
							<td style="text-align:left">${city.cityPinyin!""}</td>	
							<td style="text-align:left">
							<#if city.cityType??>
								<#if city.cityType==1>直辖市</#if>
								<#if city.cityType==2>地级城市</#if>
								<#if city.cityType==3>区</#if>
								<#if city.cityType==4>县级市</#if>
								<#if city.cityType==5>县</#if>
							</#if>
							<td style="text-align:left">
							<#if city.isValid??>
								<#if city.isValid==0>否</#if>
								<#if city.isValid==1>是</#if>
							</#if>
							</td>	
							<td>
							<@layout.security.authorize url="city/mCityTree/cityedit">
								<a href="${basePath}/city/mCityTree/cityedit/${city.cityID!!}">编辑</a>
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