<#import "/account/layout.ftl" as layout>
<#escape x as x?html>
<@layout.page title="产品编辑">
<head>
	<#-- 弹出框 -->
	<link rel="stylesheet" href="${basePath}/stylelib/remodal.css">
	<link rel="stylesheet" href="${basePath}/stylelib/remodal-default-theme.css">
	<#-- Bootstrap 颜色选择器 -->
    <link rel="stylesheet" href="${basePath}/stylelib/adminlte/plugins/colorpicker/bootstrap-colorpicker.min.css">
    <#-- Bootstrap 日期选择器 -->
    <link rel="stylesheet" href="${basePath}/stylelib/adminlte/plugins/daterangepicker/daterangepicker-bs3.css">
    
</head>
<div class="content-wrapper">
	<#-- Content Header (Page header) -->
	<section class="content-header">
	  <h1>
	    	产品编辑
	  </h1>
	  <ol class="breadcrumb">
	    <li class="active"><a href="${basePath}/account/index"><i class="fa fa-dashboard"></i> 首页</a></li>
	    <li><a href="${basePath}/product/list">产品管理</a></li>
	    <li class="active">产品编辑</li>
	  </ol>
	</section>
	<#-- Main content -->
	<section class="content">
	<#-- 融资产品编辑页面 -->
		
	<div class="box box-primary">
        <div class="box-header with-border">
          <h3 class="box-title">产品编辑</h3>
        </div><!-- /.box-header -->
        <!-- form start -->
        
        <form role="form" id="product" action="${basePath}/product/editSave" method="post" data-parsley-validate="" enctype="multipart/form-data">
          <div class="box-body"> 
            <#if pab??>  
				<@spring.bind "pab.fpName" />  
			</#if> 
            <div class="form-group <@spring.errorStyle /> " id="fpNameDiv">
            	产品名称 &nbsp;
            	<label for="fpName" id="fpNameLabel">
            		<#if pab??>  
					    <@spring.showErrors "<br>"/>  
					</#if>
              	</label>
              	<input type="text" class="form-control" id="fpName" name="fpName" value="${(pab.fpName)!}" required data-parsley-trigger="change" data-parsley-length="[2, 100]" data-parsley-maxlength="100">
            </div>
            
			<#if pab??>  
				<@spring.bind "pab.minLoanAmount" />
			</#if>
			<#if pab??>  
				<@spring.bind "pab.maxLoanAmount" />
			</#if>
            <div class="form-group <@spring.errorStyle /> " id="loanAmountDiv">
            	额度范围&nbsp;
            	<label for="minLoanAmount" id="minLoanAmountLabel">
            		<#if pab??>  
					    <@spring.showErrors "<br>"/>  
					</#if>
              	</label>
              	<input type="number" class="form-control" id ="minLoanAmount" name="minLoanAmount" value="${(pab.minLoanAmount)!}"
              	required data-parsley-trigger="change" data-parsley-type="integer" data-parsley-min="1">
				~
				<label for="maxLoanAmount" id="maxLoanAmountLabel">
            		<#if pab??>  
					    <@spring.showErrors "<br>"/>  
					</#if>
              	</label>
				<input type="number" class="form-control" id ="maxLoanAmount" name="maxLoanAmount" value="${(pab.maxLoanAmount)!}"
              	required data-parsley-trigger="change" data-parsley-type="integer" data-parsley-min="1">
            </div>
            

			<#if pab??>  
				<@spring.bind "pab.payLimit" />  
			</#if>
            <div class="form-group <@spring.errorStyle /> " id="foUrlDiv">
            	支持还款期限&nbsp;
            	<label for="payLimit" id="payLimitLabel">
            		<#if pab??>  
					    <@spring.showErrors "<br>"/>  
					</#if>
              	</label>
              	<select name="payLimit" id="payLimit" required>
					<option value="">请选择</option>
					<option value="1" <#if pab?? && "${pab.payLimit}"=="1">selected="selected"</#if> >1个月</option>
				  	<option value="3" <#if pab?? && "${pab.payLimit}"=="3">selected="selected"</#if> >3个月</option>
				  	<option value="6" <#if pab?? && "${pab.payLimit}"=="6">selected="selected"</#if> >6个月</option>
				  	<option value="12" <#if pab?? && "${pab.payLimit}"=="12">selected="selected"</#if> >12个月</option>
				  	<option value="24" <#if pab?? && "${pab.payLimit}"=="24">selected="selected"</#if> >24个月</option>
				  	<option value="36" <#if pab?? && "${pab.payLimit}"=="36">selected="selected"</#if> >36个月</option>
				</select>
				<#--~-->
				<#--<select name="payLimitTo" id="payLimitTo" required>-->
					<#--<option value="">请选择</option>-->
					<#--<option value="1" <#if pab.payLimitTo?? && "${pab.payLimitTo}"=="1">selected="selected"</#if> >1个月</option>-->
				  	<#--<option value="3" <#if pab.payLimitTo?? && "${pab.payLimitTo}"=="3">selected="selected"</#if> >3个月</option>-->
				  	<#--<option value="6" <#if pab.payLimitTo?? && "${pab.payLimitTo}"=="6">selected="selected"</#if> >6个月</option>-->
				  	<#--<option value="12" <#if pab.payLimitTo?? && "${pab.payLimitTo}"=="12">selected="selected"</#if> >12个月</option>-->
				  	<#--<option value="24" <#if pab.payLimitTo?? && "${pab.payLimitTo}"=="24">selected="selected"</#if> >24个月</option>-->
				  	<#--<option value="36" <#if pab.payLimitTo?? && "${pab.payLimitTo}"=="36">selected="selected"</#if> >36个月</option>-->
				<#--</select>-->
            </div>
            
            <#if pab??>  
				<@spring.bind "pab.minRaitRatio" /> 
				<@spring.bind "pab.maxRaitRatio" />  
			</#if> 
            <div class="form-group <@spring.errorStyle /> " id="raitRatioDiv">
            	利率 &nbsp;
            	<label for="raitRatio" id="raitRatioLabel">
            		<#if pab??>  
					    <@spring.showErrors "<br>"/>  
					</#if>
              	</label>
              	<input type="text" class="form-control" id="minRaitRatio" name="minRaitRatio" value="${(pab.minRaitRatio)!}" required data-parsley-trigger="change" data-parsley-type="number" data-parsley-maxlength="4">
              	<#--~-->
              	<#--<input type="text" class="form-control" id="maxRaitRatio" name="maxRaitRatio" value="${(pab.maxRaitRatio)!}" required data-parsley-trigger="change" data-parsley-type="number" data-parsley-maxlength="4">-->
            </div>

            <#-- <#if pab??>  
				<@spring.bind "pab.fpArea" />  
			</#if>
            <div class="form-group <@spring.errorStyle /> " id="fpAreaDiv">
            	贷款投放地区：&nbsp;
            	<label for="fpArea" id="fpAreaLabel">
            		<#if pab??>  
					    <@spring.showErrors "<br>"/>  
					</#if>
            	</label>
            	<button type="button" class="btn btn-primary" id="choose">添加投放地区</button>
            	<input type="text" class="form-control" id="fpArea" name="fpArea" value="${(pab.fpArea)!}" style="margin:5px;color:blue;display:none">
            	<input type="text" class="form-control" id="fpAreaNames" value="" readonly="readonly" style="margin:5px;color:blue;" >
            </div> -->
            
            <#-- 消息提示弹层 -->
			<div class="remodal" data-remodal-id="remodalDiv" role="dialog">
			  <button data-remodal-action="close" class="remodal-close" aria-label="Close"></button>
			  <div>
			    <h2 id="messageTitle"></h2>
			    <p id="messageBody">
			      
			    </p>
			  </div>
			  <br>
			  <button data-remodal-action="cancel" class="remodal-cancel" onclick="confirm()">确定</button>
			</div>
            
            <#if pab??>  
				<@spring.bind "pab.otherDesc" />  
			</#if> 
            <div class="form-group <@spring.errorStyle /> " id="otherDescDiv">
            	其他说明 &nbsp;
            	<label for="otherDesc" id="otherDescLabel">
            		<#if pab??>  
					    <@spring.showErrors "<br>"/>  
					</#if>
              	</label>
              	<input type="text" class="form-control" id="otherDesc" name="otherDesc" value="${(pab.otherDesc)!}"  data-parsley-trigger="change" data-parsley-maxlength="500">
            </div>

            <input type="hidden" name="fpId" id = "fpId" value="${(pab.fpId)!}">
            <input type="hidden" name="productStatus" id = "productStatus" value="${(pab.productStatus)!}">
          </div><!-- /.box-body -->
          <div class="box-footer">
            <button type="submit" class="btn btn-primary" id="saveBtn" onclick="javascript:saveProductInfo(0);" >确认</button>
            <button type="submit" class="btn btn-primary" id="onlineBtn" onclick="javascript:saveProductInfo(1);" >上线</button>
            <button type="submit" class="btn btn-primary" id="offlineBtn" onclick="javascript:saveProductInfo(2);" >下线</button>
          </div>
        </form>
      </div><!-- /.box -->
		
	<#-- 融资产品编辑页面结束 -->
	</section>
</div>
<script src="${basePath}/jslib/parsley/parsley.min.js"></script>
<script src="${basePath}/jslib/parsley/parsley.js"></script>
<script src="${basePath}/jslib/parsley/i18n/zh_cn.js"></script>
<link rel="stylesheet" href="${basePath}/stylelib/parsley.css">
<script src="${basePath}/jslib/remodal.js"></script>
<script src="${basePath}/jslib/template.js"></script>
<!-- date-range-picker -->
<script src="${basePath}/jslib/moment.min.js"></script>
<script src="${basePath}/stylelib/adminlte/plugins/daterangepicker/daterangepicker.js"></script>
<!-- bootstrap color picker -->
<script src="${basePath}/stylelib/adminlte/plugins/colorpicker/bootstrap-colorpicker.min.js"></script>
<script>
	<#--添加时间 Date range picker -->
	$('#startDate').daterangepicker({
		format: 'YYYY-MM-DD',
      	singleDatePicker: true
	});
	$('#endDate').daterangepicker({
		format: 'YYYY-MM-DD',
      	singleDatePicker: true
	});
	
	function saveProductInfo(productStatus){
		$('#productStatus').val(productStatus);
	}
	
	$(function(){
		$('#product').submit(function(){
	  	if($("#fpLogoDiv").hasClass('has-error')){
		        return false;
		    }
	  	});
	  	
  // 		$.ajax({
		//    	type: "GET",
		//    	url: basePath + "product/getCoverCityInfoByFoIDFpID",
		//    	data:{
		//    		foID : Number($("#foId").val()),
		//    		fpID : Number($("#fpId").val())
		//    	},
		//    	success: function(resultObj){
		// 		$("#fpAreaNames").val(resultObj.cityNames);
		// 	},
		// 	error:function(){
		// 		alert("系统繁忙,请稍后再试！");
		// 	}
		// });
		
		
  	});
	
	$('#choose').click(function(){ 
		$.ajax({
		   	type: "GET",
		   	url: basePath + "product/getCoverCityInfoByFoIDFpID",
		   	data:{
		   		foID : Number($("#foId").val()),
		   		fpID : Number($("#fpId").val())
		   	},
		   	success: function(resultObj){
		   			var messBody = template('chooseArea', resultObj);
					alertDivMsg("编辑投放地区",messBody);
					
					if($("#fpAreaNames").val()==""){
						$("#fpAreaNames").val(resultObj.cityNames);
					}
					var fCity = $("#fpAreaNames").val();
					var fCitys;
					if(fCity.length>0){
						$("#choosed").empty();
						fCitys = fCity.split(",");
						$.each(fCitys,function(index,value){
							appendDiv(value);
						});
					}
					
					$("input:checkbox[name='City']").each(function(){
		   				var compVar = $(this).val();
		   				$("input:checkbox[name='provCities']").each(function(){
		   					if($(this).val() == compVar){
		   						this.checked=true;
		   					}
		   				});
		   			});
		   		},
		 });
 	 });
 		 	
 	 function alertDivMsg(messTitle,messBody){
  		$("#messageTitle").html(messTitle);
  		$("#messageBody").html(messBody);
   		var messBox = $('[data-remodal-id=remodalDiv]').remodal();
   	 	messBox.open(); 
 	 };
 	 
 	 function chooseAllCity(provId){
  		var provName = $(provId).next().attr("value");
 		$.ajax({
		   	type: "GET",
		   	url: basePath + "product/getCoverCitiesByProvName",
		   	data: {  
		   	  foID: Number($("#foId").val()),
              provName : provName
		    },
		   	success: function(resultObj){
		   	
 				var divBody = template('pCitites', resultObj);
 				$(".unchoosed").empty();	
		   		$(".unchoosed").html(divBody);
		   		
		   		var c = $(provId).prop("checked");
		   		if(c){
    				$('input[name="provCities"]').prop("checked", true);
		   			$("input:checkbox[name='provCities']").each(function(){
		   				var compVar = $(this).val();
		   				var flag = true;
		   				$("input:checkbox[name='City']").each(function(){
		   					if($(this).val() == compVar){
		   						flag = false;
		   					}
		   				});
		   				if(flag){
		   					appendDiv(compVar);
		   				}
		   			});
		   		}else{
		   			$('input[name="provCities"]').prop("checked", false);
		   			$("input:checkbox[name='provCities']").each(function(){
		   				var compVar = $(this).val();
		   				$("input:checkbox[name='City']").each(function(){
		   					if($(this).val() == compVar){
		   						deleteChoose(this);
		   					}
		   				});
		   			});
		   		}
		   	},
	 	});
  	 };
  
  	function refreshCity(input,index){
  		var value = input.value;
  		var flag = true;
  		var index ;
  		$("input:checkbox[name='City']").each(function(i){  
       		if($(this).val()==value){
       			flag=false;
       			index = i;
       			return;
      		 }  
    	}); 
  	
    	 if(input.checked){
     		if(flag){
				appendDiv(value);
     		}
      	}else{
      		$("input:checkbox[name='City']")[index].parentElement.remove();
      	}
  	};
  
  	function appendDiv(value,index){
 	 	$('#choosed').append('<div name="City" style="float:left;text-align:left;margin-left:10px">'+
     	 	'<input name="City" type="checkbox" checked="checked" value="'+
     	 	value+'" onclick= "deleteChoose(this)"/>'+value+'</div>');
  	}
  
  	function selectCities(id){
  		var provName = $(id).attr("value");
  		$.ajax({
		   	type: "GET",
		   	url: basePath + "product/getCoverCitiesByProvName",
		   	data: {
		   		foID: Number($("#foId").val()),
               provName : provName
		    },
		   	success: function(resultObj){
 				var divBody = template('pCitites', resultObj);
 				$(".unchoosed").empty();	
		   		$(".unchoosed").html(divBody);
		   		
		   		$("input:checkbox[name='City']").each(function(){
		   			var compVar = $(this).val();
		   			$("input:checkbox[name='provCities']").each(function(){
		   				if($(this).val() == compVar){
		   					this.checked=true;
		   				}
		   			});
		   		});
		   	},
	 	});
  	};
  
  	function deleteChoose(id){
  		id.parentElement.remove();
  		var v = id.value;
  		$("input:checkbox[name='provCities']").each(function(){
  			if(this.value == v){
  				this.checked=false;
  			}
  		});
  	}
  
   	function confirm(){
  		var cityList = "";
    	$("input:checkbox[name='City']").each(function(){  
      	 	cityList += (","+$(this).val());  
   		 });  
    	cityList = cityList.substring(1);
    	
    	$.ajax({
		   	type: "GET",
		   	url: basePath + "product/getCityIdsByCityNames",
		   	data: {
               cityList : cityList
		    },
		   	success: function(resultObj){
		   		cIds = resultObj.cityIds;
		   		$("#fpArea").val(cIds);
		   	},
		   	error:function(cityIds){
		   		alert("出现异常！");
		   	}
	 	});
   		
    	$("#fpAreaNames").val(cityList);
   		$("#fpAreaNames").show();
  	}
  	
</script>

<#-- 模版-->
<script id="chooseArea" type="text/html">
	<div class="choosedCity" style="float:left;width:100%;height:auto;border:1px solid gray;margin-bottom:10px;padding:10px" >
	 		<div style="float:left;margin:5px;width:100%;text-align:left">已选地区：</div>
 			<br>
	 		<div id="choosed" style="float:left;">
	 		
			</div>
	</div>
	
	<div class="choosedProvs" style="float:left;width:100%;height:auto;border:1px solid gray;margin-bottom:10px;padding:10px;padding-left:20px;" >
	    <div style="float:left;margin:5px;width:100%;text-align:left">机构覆盖省份：</div>
	    <br>
	    {{each provs as value i}}
	     <div style="float: left;width: 20%;text-align:left;">
					<input id="prov_{{i}}" name="prov" type="checkbox" value="{{value}}"style="margin-left:20px"
					 onclick="chooseAllCity(prov_{{i}})"/>
					
					<label id="prov_l_{{i}}" value="{{value}}"
					 onclick="selectCities(prov_l_{{i}})">{{value}}</label>					
				</div>
	    {{/each}}
	</div>

	<div class="unchoosed" style="float:left;width:100%;height:auto;border:1px solid gray;margin-bottom:10px;padding:10px" >
	    <div style="float:left;margin:5px;width:100%;text-align:left">地区列表：</div>
	    <br>
	    {{each defaultCityList as value i}}
	      <div style="float: left;width: 20%;text-align:left;">
	       		<input name="provCities" type="checkbox" style="margin-left:20px" value="{{value}}"
	       		 	onclick="refreshCity(this,{{i}})"/>
	       		{{value}}
	      </div>
	    {{/each}}
	</div>
</script> 

<#-- 模版2 -->
<script id="pCitites" type="text/html">
	<div style="float:left;margin:5px;width:100%;text-align:left">地区列表：</div>
	<br>
	{{each cityList as city i}}
		<div style="float: left;width:20%;text-align:left;">
			<input id="provCities_{{i}}" name="provCities" type="checkbox" value="{{city}}"
			 style="margin-left:20px" onclick="refreshCity(this)"/>
			{{city}}
		</div>
	{{/each}}
</script>
</@layout.page>
</#escape>