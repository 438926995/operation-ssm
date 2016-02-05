<#import "/account/layout.ftl" as layout>
<#escape x as x?html>
<@layout.page title="省份编辑">

<div class="content-wrapper">
	<#-- Content Header (Page header) -->
	<section class="content-header">
	  <h1>
	    	省份编辑
	  </h1>
	  <ol class="breadcrumb">
	    <li class="active"><a href="${basePath}/account/index"><i class="fa fa-dashboard"></i> 首页</a></li>
	    <li><a href="${basePath}/city/mCityTree/list">城市基础表列表</a></li>
	    <li class="active">省份编辑</li>
	  </ol>
	</section>
	<#-- Main content -->
	<section class="content">
	<#-- 省份编辑页面 -->
		
	<div class="box box-primary">
        <div class="box-header with-border">
          <h3 class="box-title">省份编辑</h3>
        </div><!-- /.box-header -->
        <!-- form start -->
        
        <form id="provform" action="${basePath}/city/mCityTree/editSave" method="post">
          <div class="box-body"> 
			<input type="hidden" name="id" value="${cityTree.id}" >
          	<#if cityTree??>  
				<@spring.bind "cityTree.cityID" />  
			</#if> 
          	<div class="form-group">
          	  <div class="form-group <@spring.errorStyle /> " id="cityIDDiv">
              省份ID &nbsp;<label for="cityID" id="cityIDLabel">
              <#if cityTree??>  
			    <@spring.showErrors "<br>"/>  
			  </#if>  
              </label>
          		<input type="text" required class="form-control" id="cityID" name="cityID" value="${(cityTree.cityID)!}">
          	</div>
          	
          	<#if cityTree??>  
				<@spring.bind "cityTree.cityName" />  
			</#if> 
          	<div class="form-group">
          	  <div class="form-group <@spring.errorStyle /> " id="cityNameDiv">
              省份名 &nbsp;<label for="cityName" id="cityNameLabel">
              <#if cityTree??>  
			    <@spring.showErrors "<br>"/>  
			  </#if>  
              </label>
          		<input type="text" required class="form-control" id="cityName" name="cityName" value="${(cityTree.cityName)!}">
          	</div>
          	
          	<#if cityTree??>  
				<@spring.bind "cityTree.cityPinyin" />  
			</#if> 
          	<div class="form-group">
          		省份拼音&nbsp;
          		<input type="text" class="form-control" id="cityPinyin" name="cityPinyin" value="${(cityTree.cityPinyin)!}" >
          	</div>
          	
          	<div class="form-group">
          		城市类型&nbsp;
				<select name="cityType" id="cityType" >
					<option value="1"  <#if cityTree.cityType?? && "${cityTree.cityType}"=="1">selected="selected"</#if> >省或直辖市</option>
					<option value="2" <#if cityTree.cityType?? && "${cityTree.cityType}"=="2">selected="selected"</#if> >地级城市</option>
				  	<option value="3" <#if cityTree.cityType?? && "${cityTree.cityType}"=="3">selected="selected"</#if> >区</option>
				  	<option value="4" <#if cityTree.cityType?? && "${cityTree.cityType}"=="4">selected="selected"</#if> >县级市</option>
				  	<option value="5" <#if cityTree.cityType?? && "${cityTree.cityType}"=="5">selected="selected"</#if> >县</option>
				</select>
          	</div>
          	
          	<input type="hidden" id="parentName" name="parentName" value="">
          	<div class="form-group <@spring.errorStyle /> " id="parentIDDiv">
          		<label for="parentID" id="parentIDLabel"></label>
			</div>
          	<div class="form-group" style="" id="prov_list">
              省份&nbsp;
            	<select id="parentID" name="parentID" onchange="setParentName();">
            		<option value=""></option>
            		<#list provinceList as mCityTree>
            		<option value="${mCityTree.cityID!!}">${mCityTree.cityName!!}</option>
            		</#list>
            	</select>
            </div>
          	
          	<div class="box-footer">
          	<input type="hidden" name="token" value="${token}">
            <button type="submit" class="btn btn-primary">编辑</button>
            <button type="button" class="btn btn-primary" onclick="goback();">返回</button>
          	</div>
          </div>
         </form>   

     </div><!-- /.box -->
		
	<#-- 省份编辑页面结束 -->
	</section>
</div>
<script src="${basePath}/jslib/parsley/parsley.min.js"></script>
<script src="${basePath}/jslib/parsley/i18n/zh_cn.js"></script>
<link rel="stylesheet" href="${basePath}/stylelib/parsley.css">

<script src="${basePath}/jslib/remodal.js"></script>
<script src="${basePath}/jslib/template.js"></script>
<script>
	function goback(){
		window.location.href="${basePath}/city/mCityTree/list";
	}
	
	function setParentName(){
		var parentId = $('#parentID').val();
		if(parentId != ''){
			$("#parentIDDiv").removeClass("has-error");
		    $("#parentIDLabel").html("");
		}
		var parentName = $('#parentID').find("option:selected").text();
		$('#parentName').val(parentName);
 	}
 	function showProvinceList(){
 		var cityType = $('#cityType').val();
 		if(cityType == 1){
 			$("#parentIDDiv").removeClass("has-error");
		    $("#parentIDLabel").html("");
 			$('#prov_list').hide();
 			$('#parentID').val('');
 			$('#parentName').val('');
 		} else {
 			$('#prov_list').show();
 		}
 	}
 	// 表单提交前验证
	 $(function(){
	 	$('#provform').submit(function(){
	 		if($('#cityType').val() != '1' && $('#parentID').val() == ''){
	 			$("#parentIDDiv").addClass("has-error");
		        $("#parentIDLabel").html("请选择省份");
	 			return false;
	 		}
	 	});
	 });
</script>
</@layout.page>
</#escape>