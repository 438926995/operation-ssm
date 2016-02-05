<#import "/account/layout.ftl" as layout>
<#escape x as x?html>
<@layout.page title="融资产品添加">
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
	    	融资产品添加
	  </h1>
	  <ol class="breadcrumb">
	    <li class="active"><a href="${basePath}/account/index"><i class="fa fa-dashboard"></i> 首页</a></li>
	    <li><a href="${basePath}/finance/list">融资产品管理</a></li>
	    <li class="active">融资产品添加</li>
	  </ol>
	</section>
	<#-- Main content -->
	<section class="content">
	<#-- 融资产品添加页面 -->
		
	<div class="box box-primary">
        <div class="box-header with-border">
          <h3 class="box-title">融资产品添加</h3>
        </div><!-- /.box-header -->
        <!-- form start -->
        
        <form role="form" id="product" action="${basePath}/product/addSave" method="post" data-parsley-validate="" enctype="multipart/form-data">
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
              	<input type="text" class="form-control" id="fpName" name="fpName" value="${(pab.fpName)!}" required data-parsley-trigger="change" data-parsley-length="[2, 100]" data-parsley-maxlength="100" >
            </div>
            
            <#if pab??>  
				<@spring.bind "pab.fpLogo" />  
			</#if>
            <div class="form-group <@spring.errorStyle /> " id="fpLogoDiv">
            	产品banner&nbsp;
            	<label for="fpLogo" id="fpLogoLabel">
            		<#if pab??>  
					    <@spring.showErrors "<br>"/>  
					</#if>
              	</label>
              <input type="file" class="form-control" id="fpLogo" name="fpLogo" onchange="checkUpload();" value="${(pab.fpLogo)!}" required>
            </div>

			<#if pab??>  
				<@spring.bind "pab.minLoanAmount" />
			</#if>
            <div class="form-group <@spring.errorStyle /> " id="loanAmountDiv">
            	额度范围&nbsp;
            	<label for="minLoanAmount" id="minLoanAmountLabel">
            		<#if pab??>  
					    <@spring.showErrors "<br>"/>  
					</#if>
              	</label>
              	<select name="minLoanAmount" id="minLoanAmount" required >
					<option value="">请选择</option>
					<option value="1" <#if pab?? && "${pab.minLoanAmount}"=="1">selected="selected"</#if> >1万</option>
				  	<option value="3" <#if pab?? && "${pab.minLoanAmount}"=="3">selected="selected"</#if> >3万</option>
				  	<option value="5" <#if pab?? && "${pab.minLoanAmount}"=="5">selected="selected"</#if> >5万</option>
				  	<option value="10" <#if pab?? && "${pab.minLoanAmount}"=="10">selected="selected"</#if> >10万</option>
				  	<option value="30" <#if pab?? && "${pab.minLoanAmount}"=="30">selected="selected"</#if> >30万</option>
				  	<option value="50" <#if pab?? && "${pab.minLoanAmount}"=="50">selected="selected"</#if> >50万</option>
				  	<option value="100" <#if pab?? && "${pab.minLoanAmount}"=="100">selected="selected"</#if> >100万</option>
				  	<option value="150" <#if pab?? && "${pab.minLoanAmount}"=="150">selected="selected"</#if> >150万</option>
				  	<option value="200" <#if pab?? && "${pab.minLoanAmount}"=="200">selected="selected"</#if> >200万</option>
				</select>
				~
				<select name="maxLoanAmount" id="maxLoanAmount" required >
					<option value="">请选择</option>
					<option value="1" <#if pab?? && "${pab.maxLoanAmount}"=="1">selected="selected"</#if> >1万</option>
				  	<option value="3" <#if pab?? && "${pab.maxLoanAmount}"=="3">selected="selected"</#if> >3万</option>
				  	<option value="5" <#if pab?? && "${pab.maxLoanAmount}"=="5">selected="selected"</#if> >5万</option>
				  	<option value="10" <#if pab?? && "${pab.maxLoanAmount}"=="10">selected="selected"</#if> >10万</option>
				  	<option value="30" <#if pab?? && "${pab.maxLoanAmount}"=="30">selected="selected"</#if> >30万</option>
				  	<option value="50" <#if pab?? && "${pab.maxLoanAmount}"=="50">selected="selected"</#if> >50万</option>
				  	<option value="100" <#if pab?? && "${pab.maxLoanAmount}"=="100">selected="selected"</#if> >100万</option>
				  	<option value="150" <#if pab?? && "${pab.maxLoanAmount}"=="150">selected="selected"</#if> >150万</option>
				  	<option value="200" <#if pab?? && "${pab.maxLoanAmount}"=="200">selected="selected"</#if> >200万</option>
				</select>
            </div>
            

			<#if pab??>  
				<@spring.bind "pab.payLimit" />  
			</#if>
            <div class="form-group <@spring.errorStyle /> " id="foUrlDiv">
            	期限范围&nbsp;
            	<label for="payLimit" id="payLimitLabel">
            		<#if pab??>  
					    <@spring.showErrors "<br>"/>  
					</#if>
              	</label>
              	<select name="payLimit" id="payLimit" required>
					<option value="">请选择</option>
					<option value="1" <#if pab?? && "${pab.payLimit}"=="1">selected="selected"</#if> >3个月以下</option>
				  	<option value="3" <#if pab?? && "${pab.payLimit}"=="3">selected="selected"</#if> >3个月</option>
				  	<option value="6" <#if pab?? && "${pab.payLimit}"=="6">selected="selected"</#if> >6个月</option>
				  	<option value="12" <#if pab?? && "${pab.payLimit}"=="12">selected="selected"</#if> >12个月</option>
				  	<option value="24" <#if pab?? && "${pab.payLimit}"=="24">selected="selected"</#if> >24个月</option>
				  	<option value="36" <#if pab?? && "${pab.payLimit}"=="36">selected="selected"</#if> >36个月</option>
				</select>
            </div>
            
            <#if pab??>  
			    <@spring.bind "pab.raitUnit" />  
			</#if>
            <div class="form-group <@spring.errorStyle />">
             	费率单位&nbsp;
             	<label for="raitUnit">
             		<#if pab??>  
					    <@spring.showErrors "<br>"/>  
					</#if>
             	</label>
              	<input name="raitUnit"  type="radio" value="0" required>天
				<input name="raitUnit"  type="radio" value="1" checked>月
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
              	~
              	<input type="text" class="form-control" id="maxRaitRatio" name="maxRaitRatio" value="${(pab.maxRaitRatio)!}" required data-parsley-trigger="change" data-parsley-type="number" data-parsley-maxlength="4">
            </div>

			<#if pab??>  
				<@spring.bind "pab.startDate" /> 
			</#if>
            <div class="form-group <@spring.errorStyle /> " id="productDateRangeDiv">
            	产品上线时间&nbsp;
            	<label for="productDateRange" id="productDateRangeLabel">
            		<#if pab??>  
					    <@spring.showErrors "<br>"/>  
					</#if>
              	</label>
              	<input id="startDate" name="startDate" value="${(pab.startDate!!?string('yyyy-MM-dd'))!}" required readonly="readonly" >
              	~
              	<input id="endDate" name="endDate" value="${(pab.endDate!!?string('yyyy-MM-dd'))!}" required readonly="readonly" >
            </div>
            
            <div id="foIDEmpty" style="display:none"></div>
            <#if pab??>  
				<@spring.bind "pab.foId" />  
			</#if>
            <div class="form-group <@spring.errorStyle /> " id="foIdDiv">
            	所属机构：&nbsp;
            	<label for="foId" id="foIdLabel">
            		<#if pab??>  
					    <@spring.showErrors "<br>"/>  
					</#if>
            	</label>
            	<select name="foId" id="foId" required >
					<option value=""  <#if pab?? && "${pab.foId}"=="">selected="selected"</#if> >请选择</option>
					<#list financeList as finance>
						<option value="${finance.foId?string}" <#if pab?? && "${pab.foId}"=="${finance.foId?string}">selected="selected"</#if> >${finance.foName}</option>
					</#list>
				</select>
            </div>
            

			<#if pab??>  
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
            	<input type="text" class="form-control" id="fpArea" name="fpArea" value="${(pab.fpArea)!}" style="margin:5px;color:blue;display:none" >
            	<input type="text" class="form-control" id="fpAreaNames" value="" readonly="readonly"  style="margin:5px;color:blue;display:none" >
            </div>
            
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
				<@spring.bind "pab.otherRait" />  
			</#if> 
            <div class="form-group <@spring.errorStyle /> " id="otherRaitDiv">
            	其他费率 &nbsp;
            	<label for="otherRait" id="otherRaitLabel">
            		<#if pab??>  
					    <@spring.showErrors "<br>"/>
					</#if>
              	</label>
              	<input type="text" class="form-control" id="otherRait" name="otherRait" value="${(pab.maxRaitRatio)!}" required data-parsley-trigger="change" data-parsley-type="number" data-parsley-maxlength="4">
            </div>
            
            <#if pab??>  
				<@spring.bind "pab.credentialsRequire" />  
			</#if> 
            <div class="form-group <@spring.errorStyle /> " id="credentialsRequireDiv">
            	证件要求 &nbsp;
            	<label for="credentialsRequire" id="credentialsRequireLabel">
            		<#if pab??>  
					    <@spring.showErrors "<br>"/>  
					</#if>
              	</label>
              	<input type="text" class="form-control" id="credentialsRequire" name="credentialsRequire" value="${(pab.credentialsRequire)!}" data-parsley-trigger="change" data-parsley-minlength="2"  data-parsley-maxlength="500" >
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
              	<input type="text" class="form-control" id="otherDesc" name="otherDesc" value="${(pab.otherDesc)!}" data-parsley-trigger="change" data-parsley-minlength="2"  data-parsley-maxlength="500" >
            </div>
            <input type="hidden" name="productStatus" id = "productStatus" value="${(pab.productStatus)!}">
            <input type="hidden" name="token" value="${token}">
          </div><!-- /.box-body -->
          <div class="box-footer">
            <button type="submit" class="btn btn-primary" id="saveBtn" onclick="javascript:saveProductInfo(0);" >确认</button>
            <button type="submit" class="btn btn-primary" id="onlineBtn" onclick="javascript:saveProductInfo(1);" >上线</button>
            <button type="submit" class="btn btn-primary" id="offlineBtn" onclick="javascript:saveProductInfo(2);" >下线</button>
          </div>
        </form>
      </div><!-- /.box -->
		
	<#-- 融资产品添加页面结束 -->
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
	var basePath = "${basePath}/";
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
  		var value = $("#fpAreaNames").val();
  		if(value!=""){
  			$("#fpAreaNames").show();
  		}
  		$('#product').submit(function(){
	  	if($("#fpLogoDiv").hasClass('has-error')){
		        return false;
		    }
	  	});
  	});
  	
  	function checkUpload(){
		var pic = $('#fpLogo').val();
	  	var type = pic.substr(pic.lastIndexOf('.'));
	  	if(type == '.jpg' || type == '.jpeg' || type == '.png' ||
	  	type == '.JPG' || type == '.JPEG' || type == '.PNG'
	  	){
	  		$("#fpLogoDiv").removeClass("has-error");
	  		$("#fpLogoLabel").html("");
	  	} else {
	  		$("#fpLogoDiv").addClass("has-error");
		    $("#fpLogoLabel").html("上传类型只能是jpg,jpeg,png");
	  	}
	}
	
 	$('#choose').click(function(){
 		var foId = $("#foId").val();
 		<#-- 判断是否选择金融机构（错误提示2秒）-->
 		if(foId == ""){
 			$("#foIDEmpty").addClass("has-error");
 			$("#foIDEmpty").html('<font color="red"><strong>金融机构不能为空！</strong></font>');
 			$("#foIDEmpty").show();
  			setTimeout(function(){$("#foIDEmpty").hide()},2000);
	        return false;
 		}

		$.ajax({
		   	type: "GET",
		   	url: basePath + "product/getCoverCityInfoByFoID",
		   	data:{
		   		foID : Number(foId)
		   	},
		   	success: function(resultObj){
		   			var messBody = template('chooseArea', resultObj);
					alertDivMsg("添加投放地区",messBody);
					
					if($("#fpAreaNames").val()==""){
						$("#fpAreaNames").val(resultObj.cityNames);
					}
					var fCity = $("#fpAreaNames").val();
					var fCitys;
					if(fCity.length>0){
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
  		var cIds = "";
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
		   	error:function(resultObj){
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
