<#import "/account/layout.ftl" as layout>
<#escape x as x?html>
<@layout.page title="用户列表">
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
	<section class="content-header">
	  <h1>
	    用户列表
	  </h1>
	  <ol class="breadcrumb">
	    <li><a href="${basePath}/account/index"><i class="fa fa-dashboard"></i>首页</a></li>
	    <li><a href="${basePath}/user/list">用户管理</a></li>
	    <li class="active">用户列表</li>
	  </ol>
	</section>
	<!-- Main content -->
	<section class="content">
	<#-- 用户列表开始 -->

              <div class="box">
                <div class="box-header">
                  	<#-- 查询 -->
					<form action="${basePath}/user/list" method="get" id="queryUser">
						<div id="user_list_search"  >
							<div class="form-group">
								<label>姓名：</label>
								<input name="userName" value="${uqb.userName!""}" class="f-text" id="userName">
								
								<label>用户状态：</label>
								<select name="userStatus" id="userStatus" >
									<option value=""  <#if uqb.userStatus?? && "${uqb.userStatus}"=="">selected="selected"</#if> >全部</option>
									<option value="0" <#if uqb.userStatus?? && "${uqb.userStatus}"=="0">selected="selected"</#if> >正常</option>
								  	<option value="1" <#if uqb.userStatus?? && "${uqb.userStatus}"=="1">selected="selected"</#if> >冻结</option>
								</select>
								
								<label>是否管理员：</label>
								<select name="isAdmin" id="isAdmin" >
									<option value=""  <#if uqb.isAdmin?? && "${uqb.isAdmin}"=="">selected="selected"</#if> >全部</option>
									<option value="0" <#if uqb.isAdmin?? && "${uqb.isAdmin}"=="0">selected="selected"</#if> >否</option>
								  	<option value="1" <#if uqb.isAdmin?? && "${uqb.isAdmin}"=="1">selected="selected"</#if> >是</option>
								</select>
								
								<label>添加时间：</label>
								<div class="input-group">
									<div class="input-group-addon">
			                          <i class="fa fa-calendar"></i>
			                        </div>
									<input class="form-control" id="userCreateDateRange" name="userCreateDateRange" value="${uqb.userCreateDateRange!""}" >
								</div>
								<input type="submit" value="搜索"  >
								
								<@layout.security.authorize url="user/addView">
									<input type="button" value="添加用户" onclick="javascript:goUserAdd();" >
								</@layout.security.authorize>
								
							</div>
						</div>
					</form>
                </div>
                
                <div class="box-body">
                  <table id="user_list" class="table table-bordered table-hover">
                    <thead>
                      <tr>
                        <th id="user_name">用户姓名</th>
						<th id="create_date">添加时间</th>
						<th id="user_status">状态</th>
						<th id="is_admin">是否管理员</th>
						<th id="operate">操作</th>
                      </tr>
                    </thead>
                    <tbody>
                    <#list tbData.list as user>
						<tr id="${user.USER_ID}_${user.USER_NAME}">
							<td style="text-align:left">${user.USER_NAME!""}</td>
							<td style="text-align:left">${user.CREATED_AT!!} </td>
							<td style="text-align:left"><#if user.USER_STATUS??><#if user.USER_STATUS=='0'>正常<#else>冻结</#if></#if></td>
							<td style="text-align:left"><#if user.IS_ADMIN??><#if user.IS_ADMIN=='0'>否<#else>是</#if></#if></td>
							<td style="text-align:left">
							<@layout.security.authorize url="user/getUserInfo">
								<a href="javascript:getUserInfo('${user.USER_ID!!}')" >查看</a>
							</@layout.security.authorize>
							<@layout.security.authorize url="user/edit">
								<a href="${basePath}/user/edit/${user.USER_ID!!}">编辑</a>
							</@layout.security.authorize>
							<@layout.security.authorize url="user/del">
								<a href="javascript:deleteUserInfo('${user.USER_ID!!}')">删除</a>
							</@layout.security.authorize>
							</td>
						</tr>
					</#list>
                    </tbody>
                  </table>
                </div><!-- /.box-body -->
              	<div id = "userpager" class="box-footer">
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
<script>
  <#-- 删除用户信息 -->
  function deleteUserInfo(userId){
	if(confirm("确定要删除数据吗")){
		$.ajax({
		   	type: "GET",
		   	url: basePath + "user/del",
		   	data: {  
		       userId : userId
		    },
		   	success: function(resultObj){
		   		if(resultObj.isSuccess){
		   			alertMessageBox("用户信息",resultObj.message,function(){
		   					window.parent.location.reload();
		   				});
				}else{
					alertMessageBox("用户信息",resultObj.message,function(){window.parent.location.reload();});
				}
		   	},
		   	error:function(resultObj){  
		   	 	alertMessageBox("用户信息","出现异常，请稍后重试！");
		   	}
		});
	}
}
  <#-- 查看用户详情，弹出框 -->
  function getUserInfo(userId){
  		$.ajax({
		   	type: "GET",
		   	url: basePath + "user/getUserInfo",
		   	data: {  
               userId : userId
		    },
		   	success: function(resultObj){
		   		var messBody = template('chooseFinanceInfoForPurposeImport', resultObj);
				alertMessageBox("用户信息",messBody);
		   	},
		   	error:function(resultObj){  
		   	 	alertMessageBox("用户信息","出现异常，请稍后重试！");
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
  
  <#--添加时间 Date range picker -->
  $('#userCreateDateRange').daterangepicker({
    <#--  format: 'YYYY-MM-DD',
      separator: ' - '   -->
  });

  function goUserAdd(){
  	window.location.href= basePath + "user/addView";
  }
</script>
<#-- 显示根据id查看用户信息-->
<script id="chooseFinanceInfoForPurposeImport" type="text/html">
	<div class="box-body">
  		<table class="table table-bordered table-hover">
            <tbody>
				<tr>
					<td>用户ID</td>
					<td style="text-align:left">{{userId}}</td>
				</tr>
				<tr>
					<td>用户名</td>
					<td style="text-align:left">{{userName}}</td>
				</tr>
				<tr>
					<td>角色ID</td>
					<td style="text-align:left">{{roleId}}</td>
				</tr>
				<tr>
					<td>角色名</td>
					<td style="text-align:left">{{roleName}}</td>
				</tr>
				<tr>
					<td>创建时间</td>
					<td style="text-align:left">{{createdAt}}</td>
				</tr>
				<tr>
					<td>更行时间</td>
					<td style="text-align:left">{{updatedAt}}</td>
				</tr>
				</tr>
			</tbody>
		</table>
	</div>
</script> 
  <#-- 用户页面结束 -->
  </section>
</div>


</@layout.page>
</#escape>