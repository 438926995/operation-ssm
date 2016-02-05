<#import "/account/layout.ftl" as layout>
<#escape x as x?html>
<@layout.page title="金融机构添加">

<div class="content-wrapper">
	<#-- Content Header (Page header) -->
	<section class="content-header">
	  <h1>
	    	金融机构添加
	  </h1>
	  <ol class="breadcrumb">
	    <li class="active"><a href="${basePath}/account/index"><i class="fa fa-dashboard"></i> 首页</a></li>
	    <li><a href="${basePath}/finance/list">金融机构 管理</a></li>
	    <li class="active">金融机构添加</li>
	  </ol>
	</section>
	<#-- Main content -->
	<section class="content">
	<#-- 金融机构添加页面 -->
		
	<div class="box box-primary">
        <div class="box-header with-border">
          <h3 class="box-title">金融机构添加</h3>
        </div><!-- /.box-header -->
        <!-- form start -->
        
        <form role="form" id="finance" action="${basePath}/finance/addSave" method="post" data-parsley-validate="" enctype="multipart/form-data">
          <div class="box-body"> 
            <#if fab??>  
				<@spring.bind "fab.foName" />  
			</#if> 
            <div class="form-group <@spring.errorStyle /> " id="fonameDiv">
            	机构名称 &nbsp;
            	<label for="foName" id="fonameLabel">
            		<#if fab??>  
					    <@spring.showErrors "<br>"/>  
					</#if>
              	</label>
              	<input type="text" class="form-control" id="foName" name="foName" value="${(fab.foName)!}" required data-parsley-trigger="change" data-parsley-length="[2, 100]" data-parsley-maxlength="100">
            </div>

			<#if fab??>  
				<@spring.bind "fab.foAddr" />  
			</#if>
            <div class="form-group <@spring.errorStyle /> " id="foAddrDiv">
            	机构地址&nbsp;
            	<label for="foAddr" id="foAddrLabel">
            		<#if fab??>  
					    <@spring.showErrors "<br>"/>  
					</#if>
              	</label>
              <input type="text" class="form-control" id="foAddr" name="foAddr" value="${(fab.foAddr)!}" required data-parsley-trigger="change" data-parsley-length="[2, 200]" data-parsley-maxlength="200">
            </div>
            

			<#if fab??>  
				<@spring.bind "fab.foUrl" />  
			</#if>
            <div class="form-group <@spring.errorStyle /> " id="foUrlDiv">
            	公司网址&nbsp;
            	<label for="foUrl" id="foUrlLabel">
            		<#if fab??>  
					    <@spring.showErrors "<br>"/>  
					</#if>
              	</label>
              <input type="url" class="form-control" id="foUrl" name="foUrl" value="${(fab.foUrl)!}" required data-parsley-length="[0,100]"  data-parsley-trigger="change" data-parsley-maxlength="200">
            </div>
            

			<#if fab??>  
				<@spring.bind "fab.foPhone" />  
			</#if>
            <div class="form-group <@spring.errorStyle /> " id="foPhoneDiv">
            	公司电话&nbsp;
            	<label for="foPhone" id="foPhoneLabel">
            		<#if fab??>  
					    <@spring.showErrors "<br>"/>  
					</#if>
              	</label>
              <input type="text" class="form-control" id="foPhone" name="foPhone" value="${(fab.foPhone)!}" data-parsley-trigger="change" data-parsley-maxlength="20" data-parsley-pattern="^([0-9]{3,4}-)?[0-9]{7,8}$" >
            </div>
            
            <#if fab??>  
				<@spring.bind "fab.foDesc" />  
			</#if>
            <div class="form-group <@spring.errorStyle /> " id="foDescDiv">
            	公司简介&nbsp;
            	<label for="foDesc" id="foDescLabel">
            		<#if fab??>  
					    <@spring.showErrors "<br>"/>  
					</#if>
              	</label>
              <textarea class="form-control" id="foDesc" name="foDesc" rows="8" data-parsley-trigger="change" data-parsley-maxlength="2000">${(fab.foDesc)!}</textarea>
            </div>
            
            <#if fab??>  
				<@spring.bind "fab.foLogo" />  
			</#if>
            <div class="form-group <@spring.errorStyle /> " id="foLogoDiv">
            	公司Logo&nbsp;
            	<label for="foLogo" id="foLogoLabel">
            		<#if fab??>  
					    <@spring.showErrors "<br>"/>  
					</#if>
              	</label>
              <input type="file" class="form-control" id="foLogo" name="foLogo" onchange="checkUpload();" value="${(fab.foLogo)!}" required>
            </div>
            
            <#if fab??>  
				<@spring.bind "fab.foCoverCityNames" />  
			</#if>
            <div class="form-group <@spring.errorStyle /> " id="foCoverCityNamesDiv">
            	机构的覆盖城市&nbsp;
            	<label for="foCity" id="foCityLabel">
            		<#if fab??>  
					    <@spring.showErrors "<br>"/>  
					</#if>
              	</label>
              	<button type="button" class="btn btn-primary" id="choose">添加城市</button>
              	<input type="text" class="form-control" id="foCity" name="foCoverCityNames" value="${(fab.foCoverCityNames)!}" readonly="readonly" style="margin:5px;color:blue;display:none">
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

			<#if fab??>  
				<@spring.bind "fab.foStatus" />  
			</#if>
            <div class="form-group <@spring.errorStyle /> " id="foStatusDiv">
            	状态&nbsp;
            	<label for="foStatus" id="foStatusLabel">
            		<#if fab??>  
					    <@spring.showErrors "<br>"/>  
					</#if>
            	</label>
                <select name="foStatus" id="foStatus" required >
					<option value="">全部</option>
					<option value="0">洽谈中</option>
				  	<option value="1" selected >合作中</option>
				  	<option value="2">取消合作</option>
				</select>
            </div>
            
            
            <input type="hidden" name="token" value="${token}">
          </div><!-- /.box-body -->
          <div class="box-footer">
            <button type="submit" class="btn btn-primary">添加</button>
          </div>
        </form>
      </div><!-- /.box -->
		
	<#-- 用户添加页面结束 -->
	</section>
</div>
<script src="${basePath}/jslib/remodal.js"></script>
<script src="${basePath}/jslib/template.js"></script>
<#-- 弹出框 -->
<link rel="stylesheet" href="${basePath}/stylelib/remodal.css">
<link rel="stylesheet" href="${basePath}/stylelib/remodal-default-theme.css">
<script src="${basePath}/jslib/parsley/parsley.min.js"></script>
<script src="${basePath}/jslib/parsley/i18n/zh_cn.js"></script>
<link rel="stylesheet" href="${basePath}/stylelib/parsley.css">
<!-- bootstrap css -->
<link rel="stylesheet" href="${basePath}/stylelib/bootstrap/css/bootstrap.min.css">

<script  type="text/javascript">
  var basePath = "${basePath}/";
  
  $(function(){
  	var value = $("#foCity").val();
  	if(value!=""){
  		$("#foCity").show();
  	}
  });
  
  var cities="";
  $('#choose').click(function(){ 
	$.ajax({
		   	type: "GET",
		   	url: basePath + "finance/getdefaultProvAndCity",
		   	success: function(resultObj){
		   			var messBody = template('chooseCity', resultObj);
					alertDivMsg("选择城市",messBody);
					
					var fCity = $("#foCity").val();
					var fCitys;
					if(fCity.length>0){
						fCitys = fCity.split(",");
					}
					
					$.each(fCitys,function(index,value){
						appendDiv(value);
					});
					
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
  }
  
  function chooseAllCity(provId){
  	var provName = $(provId).next().attr("value");
 		$.ajax({
		   	type: "GET",
		   	url: basePath + "finance/getCitiesByProvName",
		   	data: {  
              provName : provName
		    },
		   	success: function(cityList){
		   		var data = {  
                	list : cityList
            	}; 
 				var divBody = template('pCitites', data);
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
  	$("input:checkbox[name='City']").each(function(){  
       if($(this).val()==value){
       		flag=false;
       		return;
       }  
    }); 

     if(input.checked){
     	if(flag){
     	 	appendDiv(value,index);
     	}
      }else{
      	$("input:checkbox[name='City']")[index].parentElement.remove();
      }
  };
  
  function appendDiv(value){
 	 $('#choosed').append('<div name="City" style="float:left;text-align:left;margin-left:10px">'+
     	 	'<input name="City" type="checkbox" checked="checked" value="'+
     	 	value+'" onclick= "deleteChoose(this)"/>'+value+'</div>');
  }
  
  function selectCities(id){
  		var provName = $(id).attr("value");
  		$.ajax({
		   	type: "GET",
		   	url: basePath + "finance/getCitiesByProvName",
		   	data: {  
               provName : provName
		    },
		   	success: function(cityList){
		   		var data = {  
                	list : cityList
            	}; 
 				var divBody = template('pCitites', data);
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
    
    $("#foCity").val(cityList);
   	$("#foCity").show();
  }
  
  function checkUpload(){
  	var pic = $('#foLogo').val();
  	var type = pic.substr(pic.lastIndexOf('.'));
  	if(type == '.jpg' || type == '.jpeg' || type == '.png' ||
  	type == '.JPG' || type == '.JPEG' || type == '.PNG'
  	){
  		$("#foLogoDiv").removeClass("has-error");
  		$("#foLogoLabel").html("");
  	} else {
  		$("#foLogoDiv").addClass("has-error");
	    $("#foLogoLabel").html("上传类型只能是jpg,jpeg,png");
  	}
  }
  $(function(){
  	$('#finance').submit(function(){
  	if($("#foLogoDiv").hasClass('has-error')){
	        return false;
	    }
  	});
  });
 </script>

<#-- 模版-->
<script id="chooseCity" type="text/html">
	<div class="choosedCity" style="float:left;width:100%;height:auto;border:1px solid gray;margin-bottom:10px;" >
	 		<div style="float:left;margin:10px;width:100%;text-align:left">已选城市：</div>
 			<br>
	 		<div id="choosed" style="float:left;">
			</div>
	</div>
	
	<div class="choosedProvs" style="float:left;width:100%;height:auto;border:1px solid gray;margin-bottom:10px;" >
	    <div style="float:left;margin:10px;width:100%;text-align:left;">覆盖省份：</div>
	    <br>
	    {{each provs as value i}}
	     <div style="float: left;width: 25%;text-align:left;">
					<input id="prov_{{i}}" name="prov" type="checkbox" value="{{value}}"style="margin-left:10px"
					 onclick="chooseAllCity(prov_{{i}})"/>
					
					<label id="prov_l_{{i}}" value="{{value}}"
					 onclick="selectCities(prov_l_{{i}})">{{value}}</label>					
				</div>
	    {{/each}}
	</div>

	<div class="unchoosed" style="float:left;width:100%;height:auto;border:1px solid gray;margin-bottom:10px;" >
	    <div style="float:left;margin:10px;width:100%;text-align:left">城市列表：</div>
	    <br>
	    {{each defaultCityList as value i}}
	      <div style="float: left;width: 25%;text-align:left;">
	       		<input name="provCities" type="checkbox" style="margin-left:10px" value="{{value}}"
	       		 	onclick="refreshCity(this,{{i}})"/>
	       		{{value}}
	      </div>
	    {{/each}}
	</div>
</script>

<#-- 模版2 -->
<script id="pCitites" type="text/html">
	<div style="float:left;margin:10px;width:100%;text-align:left">城市列表：</div>
	<br>
	{{each list as city i}}
		<div style="float: left;width:25%;text-align:left;">
			<input id="provCities_{{i}}" name="provCities" type="checkbox" value="{{city}}"
			 style="margin-left:10px" onclick="refreshCity(this,{{i}})"/>
			{{city}}
		</div>
	{{/each}}
</script>
</@layout.page>
</#escape>
