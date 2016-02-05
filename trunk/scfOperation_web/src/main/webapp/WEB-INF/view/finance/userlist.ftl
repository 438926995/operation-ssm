<#import "/account/layout.ftl" as layout>
<#escape x as x?html>
<@layout.page title=" 金融机构账号管理">
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
	    金融机构账号管理
	  </h1>
	  <ol class="breadcrumb">
	    <li><a href="${basePath}/account/index"><i class="fa fa-dashboard"></i>首页</a></li>
	    <li><a href="${basePath}/finance/list">金融机构管理</a></li>
	    <li class="active"> 金融机构账号管理</li>
	  </ol>
	</section>
	<!-- Main content -->
	<section class="content">
	<#-- 金融机构列表开始 -->

              <div class="box">
                <div class="box-header">
                  	<#-- 查询 -->
					<form action="${basePath}/finance/userlist/${foId!!}" method="get" id="queryUser">
						<div id="user_list_search"  >
							<div class="form-group">
								<label>账号名：</label>
								<input name="userName" value="${fqb.userName!""}" class="f-text" id="userName">
								<input type="submit" value="搜索">
								<@layout.security.authorize url="finance/addUserView">
									<input type="button" value="添加账号" onclick = "javascript:goFinanceAdd()"  >
								</@layout.security.authorize>
							</div>
						</div>
					</form>
                </div>
                
                <div class="box-body">
                  <table id="user_list" class="table table-bordered table-hover">
                    <thead>
                      <tr>
                      	<th>序号</th>
                        <th>账户名</th>
						<th>添加时间</th>
						<th>状态</th>
						<th>所属机构</th>
						<th>操作</th>
                      </tr>
                    </thead>
                    <tbody>
                    <#list tbData.list as martuser>
						<tr>
							<td style="text-align:center">${martuser_index+1}</td>
							<td style="text-align:left">${martuser.userName!""}</td>
							<td>${martuser.createTime!!?string('yyyy-MM-dd HH:mm:ss')} </td>
							<td style="text-align:left">
								<#if martuser.userStatus??>
									<#if martuser.userStatus?string=='1'>开启<#else>关闭</#if>
								</#if>
							</td>
							<td style="text-align:left">
								${martuser.foName!""}
							</td>
							<td style="text-align:left">
							<@layout.security.authorize url="finance/editUserView">
								<a href="${basePath}/finance/editUserView/${martuser.userId!!}" >编辑</a>
							</@layout.security.authorize>
							<@layout.security.authorize url="finance/deleteUser">
								<a href="javascript:deleteFinanceUserInfo('${martuser.userId!!}')">删除</a>
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

</body>
<script src="${basePath}/jslib/remodal.js"></script>
<script src="${basePath}/jslib/template.js"></script>
<!-- date-range-picker -->
<script src="${basePath}/jslib/moment.min.js"></script>
<script src="${basePath}/stylelib/adminlte/plugins/daterangepicker/daterangepicker.js"></script>
<!-- bootstrap color picker -->
<script src="${basePath}/stylelib/adminlte/plugins/colorpicker/bootstrap-colorpicker.min.js"></script>
  
<#-- 金融机构账户页面结束 -->
</section>
</div>

<script>
function deleteFinanceUserInfo(userId){
	if(confirm("确定要删除数据吗")){
		$.ajax({
		   	type: "GET",
		   	url: basePath + "finance/deleteUser",
		   	data: {  
		       userId : userId
		    },
		   	success: function(resultObj){
		   		if(resultObj.isSuccess){
		   			alertMessageBox("金融机构账户信息",resultObj.message,function(){
		   					window.parent.location.reload();
		   				});
				}else{
					alertMessageBox("金融机构账户信息",resultObj.message,function(){window.parent.location.reload();});
				}
		   	},
		   	error:function(resultObj){  
		   	 	alertMessageBox("金融机构账户信息","出现异常，请稍后重试！");
		   	}
		});
	}
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

function goFinanceAdd(){
	window.location.href="${basePath}/finance/addUserView/${foId}";
}


</script>

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

</@layout.page>
</#escape>