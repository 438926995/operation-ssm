<#import "/account/layout.ftl" as layout>
<#escape x as x?html>
<@layout.page title="napos同步城市列表">
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
	    napos同步城市列表
	  </h1>
	  <ol class="breadcrumb">
	    <li><a href="${basePath}/account/index"><i class="fa fa-dashboard"></i>首页</a></li>
	    <li><a href="${basePath}/city/keepNaposCityInfo">napos同步城市列表</a></li>
	    <li class="active">napos同步城市列表</li>
	  </ol>
	</section>
	<!-- Main content -->
	<section class="content">
	<#-- 角色列表开始 -->
		<div><input type="button" class="btn btn-primary" value="同步napos最新状态" onclick="updateNaposStatus();" /></div>
		<#-- <#if insertCity?? && (insertCity?size>0) > -->
			<label>需要插入的城市</label>
              <div class="box">
                <div class="box-body">
                  <table id="insert_list" class="table table-bordered table-hover">
                    <thead>
                      <tr>
                        <th id="role_id">城市Id</th>
						<th id="role_name">城市名</th>
						<th id="operate">操作</th>
                      </tr>
                    </thead>
                    <tbody>
                    <#list insertCity as city>
						<tr>
							<td>${city.cityId}</td>
							<td>${city.cityName}</td>
							<td>
							<@layout.security.authorize url="city/insertCity">
								<a href="${basePath}/city/insertCity?cityID=${city.cityId}&cityName=${city.cityName}&cityPinyin=${city.cityPinyin}">插入</a>
							</@layout.security.authorize>
							</td>
						</tr>
					</#list>
                    </tbody>
                  </table>
                </div><!-- /.box-body -->
                </div><!-- /.box -->
           <#-- </#if> -->
           
           <#--<#if updateCity?? && (updateCity?size>0) >-->
			<label>需要更新的城市</label>
              <div class="box">
                <div class="box-body">
                  <table id="update_list" class="table table-bordered table-hover">
                    <thead>
                      <tr>
                        <th id="role_id">城市Id</th>
						<th id="role_name">城市名</th>
                      </tr>
                    </thead>
                    <tbody>
                    <#list updateCity as city>
						<tr>
							<td>
							napos城市Id：<label>${city.cityId}</label><br>
							E金融的城市Id：<label>${city.originalCityId}</label>
							</td>
							<td>${city.cityName}</td>
						</tr>
					</#list>
                    </tbody>
                  </table>
                </div><!-- /.box-body -->
              </div><!-- /.box -->
           <#-- </#if>-->
            
            <#if repeatCity?? && (repeatCity?size>0) >
              <label>需要更新的城市(napos有重名城市)</label>
              <div class="box">
                <div class="box-body">
                  <table id="repeat_list" class="table table-bordered table-hover">
                    <thead>
                      <tr>
                        <th id="role_id">城市Id</th>
						<th id="role_name">城市名</th>
                      </tr>
                    </thead>
                    <tbody>
                    <#list repeatCity?sort_by("cityName") as city>
						<tr>
							<td>
							napos城市Id：<label>${city.cityId}</label><br>
							E金融的城市Id：<label>${city.originalCityId!!}</label>
							</td>
							<td>${city.cityName}</td>
						</tr>
					</#list>
                    </tbody>
                  </table>
                </div><!-- /.box-body -->
              </div><!-- /.box -->
			</#if>
</body>
<script src="${basePath}/jslib/remodal.js"></script>
<!-- date-range-picker -->
<script src="${basePath}/jslib/moment.min.js"></script>
<script src="${basePath}/stylelib/adminlte/plugins/daterangepicker/daterangepicker.js"></script>
<!-- bootstrap color picker -->
<script src="${basePath}/stylelib/adminlte/plugins/colorpicker/bootstrap-colorpicker.min.js"></script>
<script src="${basePath}/jslib/template.js"></script>
<script>
	function updateNaposStatus(){
		<#if (updateCity?size >0) >
		if(confirm("确定要更行?")){
			console.log(basePath + "city/keepNaposCityInfo");
			location.href = basePath + "city/keepNaposCityInfo?falg=1";
		}
		<#else>
		alert("已经是最新状态");
		</#if>
	}
</script>
</@layout.page>
</#escape>