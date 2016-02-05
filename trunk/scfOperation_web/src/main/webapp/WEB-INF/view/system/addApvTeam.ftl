<#import "/account/layout.ftl" as layout>
<#escape x as x?html>
<@layout.page title="添加审批组">
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
	    	审批组添加
	  </h1>
	  <ol class="breadcrumb">
	    <li class="active"><a href="${basePath}/account/index"><i class="fa fa-dashboard"></i> 首页</a></li>
	    <li><a href="${basePath}/finance/list">系统管理</a></li>
	    <li class="active">添加审批组</li>
	  </ol>
	</section>
	<#-- Main content -->
	<section class="content">
	<#-- 审批组添加页面 -->
	<div class="box box-primary">
        <div class="box-header with-border">
          <h3 class="box-title">审批组添加</h3>
        </div><!-- /.box-header -->
        <!-- form start -->
        
        <form role="form" id="apvTeam" action="${basePath}/flowmanage/addTeamSave" method="post" data-parsley-validate="" enctype="multipart/form-data">
          <div class="box-body"> 
           
          <div id="foIDEmpty" style="display:none"></div>
           
          <#if aftb??>  
				<@spring.bind "aftb.teamName" />  
			</#if>
            <div class="form-group <@spring.errorStyle /> " id="teamNameDiv">
            	所属机构：&nbsp;
            	<label for="teamName" id="teamNameLabel">
            		<#if aftb??>  
					    <@spring.showErrors "<br>"/>  
					</#if>
            	</label>
            	<select name="teamName" id="teamName" onchange="foChoose()" required>
					<option value=""  <#if aftb?? && "${aftb.teamName}"=="">selected="selected"</#if> >请选择</option>
					
					<#list financeList as finance>
						<option value="${finance.foId?string}"
							<#if aftb?? && "${aftb.teamName}"=="${finance.foId?string}">selected="selected"</#if> >
								${finance.foName}
						</option>
					</#list>
				</select>
            </div>
            
            <#if aftb??>  
				<@spring.bind "aftb.appUserNames" />  
			</#if>
            <div class="form-group <@spring.errorStyle /> " id="apvUserNamesDiv">
            	信贷员：&nbsp;
            	<label for="appUserNames" id="appUserNamesLabel">
            		<#if aftb??>  
					    <@spring.showErrors "<br>"/>  
					</#if>
            	</label>
            	<button type="button" class="btn btn-primary" id="choose">选择审批人员</button>
            	<input type="text" class="form-control" id="appUserIds" name="appUserIds" value="${(aftb.appUserIds)!}" style="margin:5px;color:blue;display:none" >
            	<input type="text" class="form-control" id="appUserNames" name="appUserNames" value="${(aftb.appUserNames)!}" readonly="readonly"  style="margin:5px;color:blue;display:none" >
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
	
	var isError = false;
	function foChoose(){
		var teamName = $("#teamName").val();
 		if(teamName == ""){
 			$("#foIDEmpty").addClass("has-error");
 			$("#foIDEmpty").html('<font color="red"><strong>金融机构不能为空！</strong></font>');
 			$("#foIDEmpty").show();
 			isError = true;
	        return false;
 		}else{
 			isError = false;
 		}
	
	 	teamName = "FO"+teamName;
		$.ajax({
			type: "GET",
		   	url: basePath + "flowmanage/getTeamByTeamname",
		   	data:{
		   		teamName : teamName
		   	},
		   	success: function(resultObj){
		   		if(resultObj.isExist){
		   			$("#foIDEmpty").addClass("has-error");
 					$("#foIDEmpty").html('<font color="red"><strong>金融机构审批组已存在！请重新选择</strong></font>');
 					$("#foIDEmpty").show();
 					isError = true;
		   		}else{
		   			$("#foIDEmpty").hide();
		   			isError = false;
		   		}
		   	},
		   	error:function(){
		   		alert("网络问题，请稍后继续！");
		   	}	   	
		});
	};

	$('#choose').click(function(){
 		var teamName = $("#teamName").val();
		if(isError){
			return false;
		}
		 		
		$.ajax({
		   	type: "GET",
		   	url: basePath + "flowmanage/getUsersByFoID",
		   	data:{
		   		foId : Number(teamName)
		   	},
		   	success: function(resultObj){
		   		userIds = resultObj.userIds;
		   		userNames = resultObj.userNames;
		   		
		 		var messBody = template('orgUsers', resultObj);
				alertDivMsg("选择金融机构信贷员",messBody);
					
				var names = $("#appUserNames").val();
				if(names != ""){
					ns = names.split(",");
					$.each(ns,function(index,value){
						$("input:checkbox[name='user']").each(function(){
				   			if($(this).val() == value){
				   				this.checked=true;
				   			}
				   		});
				   	});
				}
		   	},<#--success end-->
		 });
 	 });
 		 	
 	 function alertDivMsg(messTitle,messBody){
  		$("#messageTitle").html(messTitle);
  		$("#messageBody").html(messBody);
   		var messBox = $('[data-remodal-id=remodalDiv]').remodal();
   	 	messBox.open(); 
 	 };
 	 
 	 function confirm(){
  		var users = "";
  		var uIds = "";
    	$("input[name='user']:checked").each(function(i,userName){  
      	 	users += ","+userName.value;
      	 	var index = userNames.indexOf(userName.value);
      	 	var userId = userIds[index];
      	 	uIds += ","+userId;
   		 });
    	users = users.substring(1);
	 	uIds = uIds.substring(1);
	 	$("#appUserIds").val(uIds);
    	$("#appUserNames").val(users);
   		$("#appUserNames").show();
  	}
 	 
 	 function goback(){
 	 	window.location.href="${basePath}/flowmanage/apvFlowTeamlist";
 	 }
 	 
</script>

<#-- 模版 -->
<script id="orgUsers" type="text/html">
	<div style="float:left;margin:5px;width:100%;text-align:left">信贷员列表：</div>
	<br>
	{{each userNames as user i}}
		<div style="float: left;width:20%;text-align:left;">
			<input id="user_{{i}}" name="user" type="checkbox" value="{{user}}"
			 style="margin-left:20px"/>
			{{user}}
		</div>
	{{/each}}
</script>
<#-- 审批组添加页面结束 -->
</section>
</div>


</@layout.page>
</#escape>