<#import "/account/layout.ftl" as layout>
<#escape x as x?html>
<@layout.page title="审批节点编辑">
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
	    	审批节点编辑
	  </h1>
	  <ol class="breadcrumb">
	    <li class="active"><a href="${basePath}/account/index"><i class="fa fa-dashboard"></i> 首页</a></li>
	    <li><a href="${basePath}/finance/list">系统管理</a></li>
	    <li class="active">审批节点编辑</li>
	  </ol>
	</section>
	<#-- Main content -->
	<section class="content">
	<#-- 审批节点b编辑页面 -->
	<div class="box box-primary">
        <div class="box-header with-border">
          <h3 class="box-title">审批节点编辑</h3>
        </div><!-- /.box-header -->
        <!-- form start -->
        <form role="form" id="apvTeam" action="${basePath}/flowmanage/editNodeSave" method="post" data-parsley-validate="" enctype="multipart/form-data">
          <div class="box-body"> 
          
          <input type="hidden" class="form-control" id="nodeID" name="nodeID" value="${(afn.nodeID)!}">
          <input type="hidden" class="form-control" id="procsID" name="procsID" value="${(afn.procsID)!}">
          
			<#if afn??>  
          		<@spring.bind "afn.nodeName" />  
			</#if>
            <div class="form-group <@spring.errorStyle />" id="nodeNameDiv">
            	审批节点名称：&nbsp;
            	<label for="nodeName" id="nodeNameLabel">
            		<#if afn??>  
					    <@spring.showErrors "<br>"/>  
					</#if>
            	</label>
            	<input type="text" class="form-control" id="nodeName" name="nodeName" value="${(afn.nodeName)!}" required data-parsley-trigger="change" data-parsley-length="[2, 100]" data-parsley-maxlength="100">
			</div>
           
           <#-- 错误提醒 -->
           <div id="typeEmpty" style="display:none"></div>
            <div class="form-group" id="typeDiv">
            	节点类型：&nbsp;
            	<select name="type" id="type" onchange="typeChoose()" required>
					<option value="1">按id找审批人</option>
					<option value="2">按金融机构找审批人</option>
					<option value="3">按产品找审批人</option>
				</select>
			</div>
           <input type="hidden" class="form-control" id="nodeType" name="nodeType" value="${(afn.nodeType)!}">
           
           <input type="hidden" class="form-control" id="isOps" name="isOps" value="${(afn.isOps)!}">
           
           	<#-- ops -->
            <div id = "ops">
            <div class="form-group" id="opsUserDiv">
            	后台运营人员：&nbsp;
            	<button type="button" class="btn btn-primary" id="opsChoose" style="margin:10px;" onclick="chooseOpsUsers()">选择运营人员</button>
            </div>
		  	</div>
			<#-- ops end -->

           <#-- org -->
           <div id = "org" style="display:none" > 
           <#-- 错误提醒 -->
           <div id="foIDEmpty" style="display:none"></div>
           <div class="form-group" id="foIdDiv">
            	所属机构：&nbsp;
            	<select name="foId" id="foId" onchange="foChoose()">
					<option value="" selected="selected">请选择</option>
					<#list financeList as finance>
						<option value="${finance.foId?string}">
							${finance.foName}
						</option>
					</#list>
				</select>
            </div>
            
            <div class="form-group" id="orgUsersDiv">
            	审批人员：&nbsp;
            	<button type="button" class="btn btn-primary" id="orgChoose" style="margin:10px;" onclick="chooseOrgUser()">选择审批人员</button>
            </div>
         	</div>
         	<#--org end-->

			<input type="hidden" class="form-control" id="appUserIDs" name="appUserIDs" value="${(afn.appUserIDs)!}">
            <input type="text" class="form-control" id="appUserNames" name="appUserNames" value="${(afn.appUserNames)!}" readonly="readonly"  style="margin:5px;color:blue;">
           

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

	$(function(){
		var type = $("#nodeType").val();
		$("#type").val(""+type);
		$("#type").change();
	});

	<#-- 是运营人员时查找 userid -->
	var isOps = true;
	function typeChoose(){
		var type = $("#type").val();
		
		if(type=="1"){
			$("#teamID").val(null);
			var isOps = true;
			$("#isOps").val(1);
			$("#ops").show();
			$("#org").hide();
		}else{
			isOps = false;
			$("#isOps").val(0);
			$("#ops").hide();
			$("#org").show();
		}
		$("#nodeType").val(Number(type));
	}
	
	<#-- 选择后台运营人员 -->
	function chooseOpsUsers(){
		$.ajax({
			type: "GET",
		   	url: basePath + "flowmanage/getOpsUsers",
		   	success: function(resultObj){
		   		appUserIds = resultObj.opsUserIds;
		   		appUserNames = resultObj.opsUserNames;
		   		
		   		var data = {appUserNames:appUserNames};
		   		var messBody = template('orgUsers',data);
				alertDivMsg("选择审批人员",messBody);
				
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
		   	},
		   	error:function(){
		   		alert("网络问题，请稍后继续！");
		   	}	   	
		});
	};
	
	var appUserIds;
	var appUserNames;
	var isTeamNull = true;
	<#-- 没选择金融机构 不能选择机构相关数据 -->
	function foChoose(){
		var foId = $("#foId").val();
 		if(foId == ""){
 			$("#foIDEmpty").addClass("has-error");
 			$("#foIDEmpty").html('<font color="red"><strong>金融机构不能为空！</strong></font>');
 			$("#foIDEmpty").show();
	        return false;
 		}else{
 			$("#foIDEmpty").hide();
 		}
	
		$("#apvUserNamesDiv").show();
	
	 	var teamName = "FO"+foId;
		$.ajax({
			type: "GET",
		   	url: basePath + "flowmanage/getTeamByTeamname",
		   	data:{
		   		teamName : teamName
		   	},
		   	success: function(resultObj){
		   		if(resultObj.team != null){
		   			appUserIds = resultObj.team.appUserIds.split(",");
		   			appUserNames = resultObj.team.appUserNames.split(",");
		   			isTeamNull = false;
		   		}
		   	},
		   	error:function(){
		   		alert("网络问题，请稍后继续！");
		   	}	   	
		});
	};

	function chooseOrgUser(){
 		var foId = $("#foId").val();
		if(foId==""){
			$("#foIDEmpty").addClass("has-error");
 			$("#foIDEmpty").html('<font color="red"><strong>金融机构不能为空！</strong></font>');
 			$("#foIDEmpty").show();
			return false;
		}
		
		if(isTeamNull){
			$("#foIDEmpty").addClass("has-error");
 			$("#foIDEmpty").html('<font color="red"><strong>金融机构暂时没有审核人员，请先添加！</strong></font>');
 			$("#foIDEmpty").show();
			return false;
		}
		
		var data = {appUserNames:appUserNames}
		var messBody = template('orgUsers', data);
		alertDivMsg("选择审批人员",messBody);
					
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
 	 };
 		 	
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
      	 	var index = appUserNames.indexOf(userName.value);
      	 	var userId = appUserIds[index];
      	 	uIds += ","+userId;
   		 });
    	users = users.substring(1);
	 	uIds = uIds.substring(1);
	 	
	 	$("#appUserIDs").val(uIds);
	 	
    	$("#appUserNames").val(users);
   		$("#appUserNames").show();
  	}
 	 
 	 function goback(){
 	 	var procsID=$("#procsID").val();
 	 	window.location.href="${basePath}/flowmanage/apvFlowNodeList/"+procsID;
 	 }
 	 
</script>

<#-- 模版 -->
<script id="orgUsers" type="text/html">
	<div style="float:left;margin:5px;width:100%;text-align:left">审批人列表：</div>
	<br>
	{{each appUserNames as user i}}
		<div style="float: left;width:25%;text-align:left;">
			<input id="user_{{i}}" name="user" type="checkbox" value="{{user}}" style="margin-left:20px"/>
			{{user}}
		</div>
	{{/each}}
</script>

<#-- 审批节点编辑页面结束 -->
</section>
</div>


</@layout.page>
</#escape>