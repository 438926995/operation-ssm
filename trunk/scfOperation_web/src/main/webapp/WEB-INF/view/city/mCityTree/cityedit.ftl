<#import "/account/layout.ftl" as layout>
<#escape x as x?html>
<@layout.page title="城市编辑">

<div class="content-wrapper">
	<#-- Content Header (Page header) -->
	<section class="content-header">
	  <h1>
	    	城市编辑
	  </h1>
	  <ol class="breadcrumb">
	    <li class="active"><a href="${basePath}/account/index"><i class="fa fa-dashboard"></i> 首页</a></li>
	    <li><a href="${basePath}/city/mCityTree/list">城市基础表列表</a></li>
	    <li class="active">城市编辑</li>
	  </ol>
	</section>
	<#-- Main content -->
	<section class="content">
	<#-- 城市编辑页面 -->
		
	<div class="box box-primary">
        <div class="box-header with-border">
          <h3 class="box-title">城市编辑</h3>
        </div><!-- /.box-header -->
        <!-- form start -->
        
        <form id="cityform" action="${basePath}/city/mCityTree/cityeditSave" method="post">
          <div class="box-body"> 
			<input type="hidden" name="id" value="${cityTree.id}" >
          	<#if cityTree??>  
				<@spring.bind "cityTree.cityID" />  
			</#if> 
          	<div class="form-group">
          	  <div class="form-group <@spring.errorStyle /> " id="cityIDDiv">
              城市ID &nbsp;<label for="cityID" id="cityIDLabel">
              <#if cityTree??>  
			    <@spring.showErrors "<br>"/>  
			  </#if>  
              </label>
          		<input readonly type="text" required class="form-control" id="cityID" name="cityID" value="${(cityTree.cityID)!}">
          	</div>
          	
          	<#if cityTree??>  
				<@spring.bind "cityTree.cityName" />  
			</#if> 
          	<div class="form-group">
          	  <div class="form-group <@spring.errorStyle /> " id="cityNameDiv">
              城市名 &nbsp;<label for="cityName" id="cityNameLabel">
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
          		城市拼音&nbsp;
          		<input type="text" class="form-control" id="cityPinyin" name="cityPinyin" value="${(cityTree.cityPinyin)!}" >
          	</div>
          	
          	<div class="form-group">
          		城市类型&nbsp;
				<select name="cityType" id="cityType" onchange="showProvinceList();" >
					<option value="1"  <#if cityTree.cityType?? && "${cityTree.cityType}"=="1">selected="selected"</#if> >省</option>
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
          	<div class="form-group" style="display:block;" id="prov_list">
              省份&nbsp;
            	<select id="parentID" name="parentID" onchange="setParentName();">
            		<#list provinceList as mCityTree>
            		<option <#if cityTree.parentID?? && "${cityTree.parentID}"=="${mCityTree.cityID}">selected="selected"</#if> value="${mCityTree.cityID!!}">${mCityTree.cityName!!}</option>
            		</#list>
            	</select>
            </div>
            
            <div class="form-group">
	            是否有效&nbsp;
	            <label>
	              <input type="radio" name="isValid" id="isValid0" value="0" 
					<#if cityTree??> 
					 <#if cityTree.isValid?? && "${cityTree.isValid}"=="0"> checked </#if> 
					<#else> checked </#if>
				  >否
	              <input type="radio" name="isValid" id="isValid1" value="1" <#if cityTree?? && cityTree.isValid?? && "${cityTree.isValid}"=="1"> checked </#if> >是
	            </label>
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
		window.location.href="${basePath}/city/mCityTree/citylist/${cityTree.parentID}";
	}
	function setParentName(){
		var parentId = $('#parentID').val();
		var parentName = $('#parentID').find("option:selected").text();
		$('#parentName').val(parentName);
 	}
 	function showProvinceList(){
 		var cityType = $('#cityType').val();
 		if(cityType == 1){
 			$('#prov_list').hide();
 		} else {
 			$('#prov_list').show();
 		}
 	}
 	// 表单提交前验证
	 $(function(){
	 	$('#provform').submit(function(){
	 		if($('#parentID').val() == ''){
	 			$("#parentIDDiv").addClass("has-error");
		        $("#parentIDLabel").html("请选择省份");
	 			return false;
	 		} 
	 	});
	 });
</script>
</@layout.page>
</#escape>