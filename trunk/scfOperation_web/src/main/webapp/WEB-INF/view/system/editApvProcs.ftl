<#import "/account/layout.ftl" as layout>
<#escape x as x?html>
<@layout.page title="审批流编辑">
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
	<!-- Main content -->
	<section class="content-header">
	  <h1>
	    	审批流编辑
	  </h1>
	  <ol class="breadcrumb">
	    <li class="active"><a href="${basePath}/account/index"><i class="fa fa-dashboard"></i> 首页</a></li>
	    <li><a href="${basePath}/finance/list">系统管理</a></li>
	    <li class="active">审批流编辑</li>
	  </ol>
	</section>
	<#-- Main content -->
	<section class="content">
	<#-- 审批流编辑页面 -->
	<div class="box box-primary">
        <div class="box-header with-border">
          <h3 class="box-title">审批流编辑</h3>
        </div><!-- /.box-header -->
        <!-- form start -->
        
        <form role="form" id="apvTeam" action="${basePath}/flowmanage/editProcsSave" method="post" data-parsley-validate="" enctype="multipart/form-data">
          <div class="box-body"> 
           
          <input type="hidden" id="procsID" name="procsID" value="${(afp.procsID)!}">
           
          <#if afp??>
				<@spring.bind "afp.procsName" />  
			</#if>
            <div class="form-group <@spring.errorStyle /> " id="procsNameDiv">
            	审批流名称：&nbsp;
            	<label for="procsName" id="procsNameLabel">
            		<#if afp??>  
					    <@spring.showErrors "<br>"/>  
					</#if>
            	</label>
				<input type="text" class="form-control" id="procsName" name="procsName" value="${(afp.procsName)!}" style="margin:5px;color:blue;">
            </div>
            
            <#if afp??>  
				<@spring.bind "afp.isValid" />  
			</#if>
            <div class="form-group <@spring.errorStyle /> " id="isValidDiv">
            	审批流有效性：&nbsp;
            	<label for="isValid" id="isValidLabel">
            		<#if afp??>  
					    <@spring.showErrors "<br>"/>  
					</#if>
            	</label>
	            <input type="radio" name="isValid" id="isValid0" value="0" 
					<#if afp??> 
					 	<#if afp.isValid?? && "${afp.isValid}"=="0"> checked </#if> 
					<#else>
					 	checked 
					</#if>>否  &nbsp;
	            <input type="radio" name="isValid" id="isValid1" value="1" 
					<#if afp?? && afp.isValid?? && "${afp.isValid}"=="1"> checked </#if> >是
            </div>
            
            <#if afp??>  
				<@spring.bind "afp.procsDesc" />  
			</#if>
            <div class="form-group <@spring.errorStyle /> " id="procsDescDiv">
            	审批流描述：&nbsp;
            	<label for="procsDesc" id="procsDescLabel">
            		<#if afp??>  
					    <@spring.showErrors "<br>"/>  
					</#if>
            	</label>
				<input type="text" class="form-control" id="procsDesc" name="procsDesc" value="${(afp.procsDesc)!}" style="margin:5px;color:blue;">
            </div>
            
            <input type="hidden" id="foID" name="foID" value="${(afp.foID)!}">
            
            <div id="foIDEmpty" style="display:none"></div>
            <div class="form-group/> " id="foIdDiv">
            	所属机构：&nbsp;
            	<label for="foId" id="foIdLabel">
            	</label>
            	<select name="foId" id="foId" onchange="foChoose()">
					<option value="" selected="selected">请选择</option>
					<#list financeList as finance>
						<option value="${finance.foId?string}">
								${finance.foName}
						</option>
					</#list>
				</select>
            </div>
            
            <#if afp??>  
				<@spring.bind "afp.fpID" />  
			</#if>
            <div class="form-group <@spring.errorStyle /> " id="fpIDDiv">
            	金融产品：&nbsp;
            	<label for="fpID" id="fpIDLabel">
            		<#if afp??>  
					    <@spring.showErrors "<br>"/>  
					</#if>
            	</label>
            	<button type="button" class="btn btn-primary" id="choose" style="margin:10px">选择金融产品</button>
            	<input type="text" class="form-control" id="fpID" name="fpID" value="${(afp.fpID)!}" style="margin:5px;color:blue;display:none" >
            	<input type="text" class="form-control" id="fpName" name="fpName" value="" readonly="readonly"  style="margin:5px;color:blue;display:none">
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
            
          </div><!-- /.box-body -->
          
          <div class="box-footer">
          	<input type="button" class="btn btn-primary" value="返回" onclick="javascript:goback()">
            <button type="submit" class="btn btn-primary">确定</button>
            <input type="hidden" name="token" value="${token}">
          </div>
        </form>
      </div><!-- /.box -->
		
	<#-- 页面结束 -->
	</section><!-- /.box -->
</body>
<script src="${basePath}/jslib/remodal.js"></script>
<script src="${basePath}/jslib/template.js"></script>
<!-- date-range-picker -->
<script src="${basePath}/jslib/moment.min.js"></script>
<script src="${basePath}/stylelib/adminlte/plugins/daterangepicker/daterangepicker.js"></script>
<!-- bootstrap color picker -->
<script src="${basePath}/stylelib/adminlte/plugins/colorpicker/bootstrap-colorpicker.min.js"></script>
<script>
	function foChoose(){
		$("#fpName").hide();
		var foId = $("#foId").val();
 		if(foId == ""){
 			$("#foIDEmpty").addClass("has-error");
 			$("#foIDEmpty").html('<font color="red"><strong>金融机构为空！</strong></font>');
 			$("#foIDEmpty").show();
	        return false;
 		}else{
 			$("#foIDEmpty").hide();
 			$("#foID").val(Number(foId));
 		}
	};
	 	
 	 function alertDivMsg(messTitle,messBody){
  		$("#messageTitle").html(messTitle);
  		$("#messageBody").html(messBody);
   		var messBox = $('[data-remodal-id=remodalDiv]').remodal();
   	 	messBox.open(); 
 	 };

	$('#choose').click(function(){
		var foId = $("#foId").val();
		if(foId==""){ 
			$("#foIDEmpty").addClass("has-error");
 			$("#foIDEmpty").html('<font color="red"><strong>选择金融产品时金融机构不能为空！</strong></font>');
 			$("#foIDEmpty").show();
			return false;
		}
		$("#foID").val(foId);
		$.ajax({
			type: "GET",
		   	url: basePath + "flowmanage/getProductsByFoId",
		   	data:{
		   		foId : foId
		   	},
		   	success: function(resultObj){
		   		fpIds = resultObj.fpIds;
		   		fpNames = resultObj.fpNames;
		   		
		 		var messBody = template('orgProducts', resultObj);
				alertDivMsg("选择金融机构产品",messBody);
					
				var name = $("#fpName").val();
				if(name != ""){
					ns = names.split(",");
					$.each(ns,function(index,value){
						$("input:radio[name='fpName']").each(function(){
				   			if($(this).val() == value){
				   				this.checked=true;
				   			}
				   		});
				   	});
				}
		   	},<#--success end-->
		   	error:function(){
		   		alert("网络问题，请稍后继续！");
		   	}	   	
		});
 	 });
 		 
 	function confirm(){
  		var ids = "";
  		var names = "";
    	$("input[name='fpName']:checked").each(function(i,name){  
      	 	names += ","+name.value;
      	 	var index = fpNames.indexOf(name.value);
      	 	var id = fpIds[index];
      	 	ids += ","+id;
   		 });
    	names = names.substring(1);
	 	ids = ids.substring(1);
	 	$("#fpID").val(ids);
    	$("#fpName").val(names);
   		$("#fpName").show();
  	}
 		 	
 	 function goback(){
 	 	window.location.href="${basePath}/flowmanage/apvFlowProcslist";
 	 }

</script>

<#-- 模版 -->
<script id="orgProducts" type="text/html">
	<div style="float:left;margin:5px;width:100%;text-align:left">金融产品列表：</div>
	<br>
	{{each fpNames as fpName i}}
		<div style="float: left;width:20%;text-align:left;">
			<input id="fpName_{{i}}" name="fpName" type="radio" value="{{fpName}}"
			 style="margin-left:20px"/>
			{{fpName}}
		</div>
	{{/each}}
</script>

<#-- 审批人员编辑页面结束 -->
</section>
</div>

</@layout.page>
</#escape>