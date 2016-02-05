<#import "/account/layout.ftl" as layout>
<#escape x as x?html>
<@layout.page title="插入napos城市">

<div class="content-wrapper">
	<#-- Content Header (Page header) -->
	<section class="content-header">
	  <h1>
	    	插入napos城市
	  </h1>
	  <ol class="breadcrumb">
	    <li class="active"><a href="${basePath}/account/index"><i class="fa fa-dashboard"></i> 首页</a></li>
	    <li><a href="${basePath}/basic/keepNaposCityInfo">napos同步城市列表</a></li>
	    <li class="active">插入napos城市</li>
	  </ol>
	</section>
	<#-- Main content -->
	<section class="content">
	<#-- 角色编辑页面 -->
		
	<div class="box box-primary">
        <div class="box-header with-border">
          <h3 class="box-title">插入napos城市</h3>
        </div><!-- /.box-header -->
        <!-- form start -->
        
        <form id="insertCityInfo" action="${basePath}/city/insert" method="post">
          <div class="box-body"> 
          	<div class="form-group">
          		城市ID&nbsp;
          		<input type="text" class="form-control" id="cityID" name="cityID" value="${city.cityID!!}" readonly="true" >
          	</div>
          	<div class="form-group">
          		城市名&nbsp;
          		<input type="text" class="form-control" id="cityName" name="cityName" value="${city.cityName!!}" readonly="true" >
          	</div>
          	<div class="form-group">
          		城市拼音&nbsp;
          		<input type="text" class="form-control" id="cityPinyin" name="cityPinyin" value="${city.cityPinyin!!}" readonly="true" >
          	</div>
          	<div class="form-group <@spring.errorStyle /> " id="parentIDDiv">
          		<label for="parentID" id="parentIDLabel"></label>
			</div>
            <div class="form-group">
              省份&nbsp;
	            	<select id="parentID" name="parentID" onchange="setParentName();">
	            		<option value=""></option>
	            		<#list provinceList as mCityTree>
	            		<option value="${mCityTree.cityID!!}">${mCityTree.cityName!!}</option>
	            		</#list>
	            	</select>
            </div>
            <input type="hidden" id="parentName" name="parentName" value="">
          	<div class="form-group <@spring.errorStyle /> " id="cityTypeDiv">
          		<label for="cityType" id="cityTypeLabel"></label>
			</div>
          	<div class="form-group">
          		
              城市类型 &nbsp;
              	<select id="cityType" name="cityType" onchange="removeError();">
	            		<option value=""></option>
	            		<option value="1">省份或直辖市</option>
	            		<option value="2">地级城市</option>
	            		<option value="3">区</option>
	            		<option value="4">县级市</option>
	            		<option value="5">县</option>
	           </select>
          	</div>

          	<input type="hidden" name="token" value="${token}">
            <button type="submit" class="btn btn-primary">插入</button>
          	</div>
          </div>
         </form>   

     </div><!-- /.box -->
		
	<#-- 角色编辑页面结束 -->
	</section>
</div>
<script src="${basePath}/jslib/parsley/parsley.min.js"></script>
<script src="${basePath}/jslib/parsley/i18n/zh_cn.js"></script>
<link rel="stylesheet" href="${basePath}/stylelib/parsley.css">

<script src="${basePath}/jslib/remodal.js"></script>
<script src="${basePath}/jslib/template.js"></script>
<script>
 function setParentName(){
 	var parentId = $('#parentID').val();
 	if(parentId != ''){
 		$("#parentIDDiv").removeClass("has-error");
	    $("#parentIDLabel").html("");
 	}
 	var parentName = $('#parentID').find("option:selected").text();
 	$('#parentName').val(parentName);
 }
 function removeError(){
 	if($('#cityType').val() != ''){
		$("#cityTypeDiv").removeClass("has-error");
        $("#cityTypeLabel").html("");
 	}
 }
 // 表单提交前验证
 $(function(){
 	$('#insertCityInfo').submit(function(){
 		if($('#parentID').val() == ''){
 			$("#parentIDDiv").addClass("has-error");
	        $("#parentIDLabel").html("请选择省份");
 			return false;
 		}
 		if($('#cityType').val() == ''){
 			$("#cityTypeDiv").addClass("has-error");
	        $("#cityTypeLabel").html("请选择城市类型");
 			return false;
 		}
 	});
 });
</script>
</@layout.page>
</#escape>