<#import "/account/layout.ftl" as layout>
<#escape x as x?html>
<@layout.page title="角色列表">
<head>
	<#-- 弹出框 -->
	<link rel="stylesheet" href="${basePath}/stylelib/remodal.css">
	<link rel="stylesheet" href="${basePath}/stylelib/remodal-default-theme.css">
	<#-- Bootstrap 颜色选择器 -->
    <link rel="stylesheet" href="${basePath}/stylelib/adminlte/plugins/colorpicker/bootstrap-colorpicker.min.css">
    <#-- Bootstrap 日期选择器 -->
    <link rel="stylesheet" href="${basePath}/stylelib/adminlte/plugins/daterangepicker/daterangepicker-bs3.css">
    <style>
    	.role-users-operation{
    		list-style:none;
    		text-align:left;
    		
    	}
    	.role-users-operation ul{
    		padding-left:2px;
    	}
    	.role-users-operation li{
    		padding-right:10%;
    		text-align:left;
    		display:block;
    		float:left;	
    		width:50%;
    	}
    	.role-users-operation li a{
    		padding-left:10px;
    	}
    </style>
</head>
<body>
<div class="content-wrapper">
	<!-- Content Header (Page header) -->
	<section class="content-header">
	  <h1>
	    角色列表
	  </h1>
	  <ol class="breadcrumb">
	    <li><a href="${basePath}/account/index"><i class="fa fa-dashboard"></i>首页</a></li>
	    <li><a href="${basePath}/user/list">用户管理</a></li>
	    <li class="active">角色列表</li>
	  </ol>
	</section>
	<!-- Main content -->
	<section class="content">
	<#-- 角色列表开始 -->
              <div class="box">
                <div class="box-body">
                  <table id="role_list" class="table table-bordered table-hover">
                    <thead>
                      <tr>
                        <th id="role_id">角色ID</th>
						<th id="role_name">角色名</th>
						<th id="operate">操作</th>
                      </tr>
                    </thead>
                    <tbody>
                    <#list tbData.list as role>
						<tr>
							<td>${role.roleId}</td>
							<td>${role.roleName}</td>
							<td>
							<@layout.security.authorize url="role/getRoleInfo">
								<a href="javascript:getRoleInfo('${role.roleId!!}')" >查看</a>
							</@layout.security.authorize>
							<@layout.security.authorize url="role/edit">
								<a href="${basePath}/role/edit/${role.roleId!!}">编辑</a>
							</@layout.security.authorize>
							<@layout.security.authorize url="role/del">
								<a href="javascript:deleteRoleInfo('${role.roleId!!}')">删除</a>
							</@layout.security.authorize>
							</td>
						</tr>
					</#list>
                    </tbody>
                  </table>
                </div><!-- /.box-body -->
              	<div id = "productpager" class="box-footer">
					<#include "/common/pager_bar.ftl">
				</div>
              </div><!-- /.box -->

<#-- 消息提示弹层 -->
<div class="remodal" data-remodal-id="messageBox" role="dialog" aria-labelledby="messageTitle" aria-describedby="messageBody">
  <button data-remodal-action="close" class="remodal-close" aria-label="Close"></button>
  <div>
    <h2 id="messageTitle"></h2>
    <p id="messageBody">
      
    </p>
  </div>
  <br>
  <button data-remodal-action="cancel" class="remodal-cancel">关闭</button>
</div>
</body>
<script src="${basePath}/jslib/remodal.js"></script>
<!-- date-range-picker -->
<script src="${basePath}/jslib/moment.min.js"></script>
<script src="${basePath}/stylelib/adminlte/plugins/daterangepicker/daterangepicker.js"></script>
<!-- bootstrap color picker -->
<script src="${basePath}/stylelib/adminlte/plugins/colorpicker/bootstrap-colorpicker.min.js"></script>
<script src="${basePath}/jslib/template.js"></script>

  <#-- 角色页面结束 -->
  </section>
</div>

<script>
	<#-- 删除角色信息 -->
  	function deleteRoleInfo(roleId){
		if(confirm("确定要删除数据吗")){
			$.ajax({
		   		type: "GET",
		   		url: basePath + "role/del",
		   		data: {  
		       	roleId : roleId
		    	},
		   		success: function(resultObj){
		   			if(resultObj.isSuccess){
		   				alertMessageBox("角色信息",resultObj.message,function(){
		   						window.parent.location.reload();
		   					});
					}else{
						alertMessageBox("角色信息",resultObj.message,function(){window.parent.location.reload();});
					}
		   		},
		   		error:function(resultObj){  
		   	 		alertMessageBox("角色信息","出现异常，请稍后重试！");
		   		}
			});
		}
	}

	<#-- 查看角色详情，弹出框 -->
  	function getRoleInfo(roleId){
  			$.ajax({
		   		type: "GET",
		   		url: basePath + "role/getRoleInfo",
		   		data: {  
               	roleId : roleId
		    	},
		   		success: function(resultObj){
		   			var messBody = template('chooseFinanceInfoForPurposeImport', resultObj);
					alertMessageBox("角色信息",messBody);
					$(".role-users-operation li a").click(function(){
						var _self = $(this);
						var userId = _self.data("userId");
						var roleId = _self.data("roleId");
						
						if(confirm("确定要删除该用户的角色吗")){
							$.ajax({
						   		type: "GET",
						   		url: basePath + "role/delUserAndRole",
						   		data: {
						   		userId : userId,  
						       	roleId : roleId
						    	},
						   		success: function(resultObj){
						   			_self.parent().remove();
						   		},
						   		error:function(resultObj){  
						   	 		alertMessageBox("用户和角色信息","出现异常，请稍后重试！");
						   		}
							});
						}
					});
		   		},
		   		error:function(resultObj){  
		   	 		alertMessageBox("角色信息","出现异常，请稍后重试！");
		   		}
			});
  	}
  
  	function alertMessageBox(messTitle,messBody,callback){
  		$("#messageTitle").html(messTitle);
  		$("#messageBody").html(messBody);
    	var messBox = $('[data-remodal-id=messageBox]').remodal();
    	messBox.open();
    	$(document).on('closed', '.remodal', function (e) {
    		callback();
		});
  }
 
</script>
<#-- 显示根据id查看角色信息-->
<script id="chooseFinanceInfoForPurposeImport" type="text/html">
	<div class="box-body">
  		<table class="table table-bordered table-hover">
            <tbody>
				<tr>
					<td style="width:30%">角色ID</td>
					<td style="text-align:left">{{roleInfo.roleId}}</td>
				</tr>
				<tr>
					<td>角色名</td>
					<td style="text-align:left">{{roleInfo.roleName}}</td>
				</tr>
				<tr>
					<td>权限</td>
					<td style="text-align:left">{{auth}}</td>
				</tr>
				<tr>
					<td style="vertical-align:middle">对应的用户</td>
					<td class="role-users-operation">
						<ul style="position:relative; width:100%">
						{{each user as value i}}
						<li>{{value.userName}}
					<a href="javascript:void(0)" data-userId="{{value.userId}}" data-roleId="{{value.roleId}}">删除</a>	</li>
						{{/each}}
						</ul>
					</td>
				</tr>
				
			</tbody>
		</table>
	</div>
</script> 
</@layout.page>
</#escape>