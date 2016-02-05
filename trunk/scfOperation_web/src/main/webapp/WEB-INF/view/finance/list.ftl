<#import "/account/layout.ftl" as layout>
<#escape x as x?html>
<@layout.page title="金融机构列表">
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
	    金融机构列表
	  </h1>
	  <ol class="breadcrumb">
	    <li><a href="${basePath}/account/index"><i class="fa fa-dashboard"></i>首页</a></li>
	    <li><a href="${basePath}/finance/list">金融机构管理</a></li>
	    <li class="active">金融机构列表</li>
	  </ol>
	</section>
	<!-- Main content -->
	<section class="content">
	<#-- 金融机构列表开始 -->

              <div class="box">
                <div class="box-header">
                  	<#-- 查询 -->
					<form action="${basePath}/finance/list" method="get" id="queryUser">
						<div id="user_list_search"  >
							<div class="form-group">
								<label>机构名称：</label>
								<input name="foName" value="${fqb.foName!""}" class="f-text" id="foName">
								
								<label>状态：</label>
								<select name="foStatus" id="foStatus" >
									<option value=""  <#if fqb.foStatus?? && "${fqb.foStatus}"=="">selected="selected"</#if> >全部</option>
									<option value="0" <#if fqb.foStatus?? && "${fqb.foStatus}"=="0">selected="selected"</#if> >洽谈中</option>
								  	<option value="1" <#if fqb.foStatus?? && "${fqb.foStatus}"=="1">selected="selected"</#if> >合作中</option>
								  	<option value="2" <#if fqb.foStatus?? && "${fqb.foStatus}"=="2">selected="selected"</#if> >取消合作</option>
								</select>
								<label>添加时间：</label>
								<div class="input-group">
									<div class="input-group-addon">
			                          <i class="fa fa-calendar"></i>
			                        </div>
									<input class="form-control" id="createDateRange" name="createDateRange" value="${fqb.createDateRange!""}" >
								</div>
								<input type="submit" value="搜索">
								<@layout.security.authorize url="finance/addView">
									<input type="button" value="添加机构" onclick = "javascript:goFinanceAdd()"  >
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
                        <th id="foName">机构名称</th>
						<th id="create_date">机构地址</th>
						<th id="foPhone">机构电话</th>
						<th id="createDate">添加时间</th>
						<th id="userStatus">状态</th>
						<th>账号管理</th>
						<th id="operate">操作</th>
                      </tr>
                    </thead>
                    <tbody>
                    <#list tbData.list as finance>
						<tr id="${finance.foId}_${finance.foName}">
							<td style="text-align:center">${finance_index+1}</td>
							<td style="text-align:left">${finance.foName!""}</td>
							<td style="text-align:left">${finance.foAddr!""}</td>
							<td style="text-align:left">${finance.foPhone!""}</td>
							<td>${finance.createTime!!?string('yyyy-MM-dd HH:mm:ss')} </td>
							<td style="text-align:left">
								<#if finance.foStatus??>
									<#if finance.foStatus?string=='0'>洽谈中<#elseif finance.foStatus?string=='1'>合作中<#else>取消合作</#if>
								</#if>
							</td>
							<td style="text-align:left">
							<@layout.security.authorize url="finance/userlist">
								<a href="${basePath}/finance/userlist/${finance.foId!!}">账号管理</a>
							</@layout.security.authorize>
							</td>
							<td style="text-align:left">
							<@layout.security.authorize url="file/getFinanceOrgLogo">
								<a href="javascript:getFinanceInfo('${finance.foId!!}')" >查看</a>
							</@layout.security.authorize>
							<@layout.security.authorize url="finance/edit">
								<a href="${basePath}/finance/edit/${finance.foId!!}">编辑</a>
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
<script src="${basePath}/jslib/template.js"></script>
<!-- date-range-picker -->
<script src="${basePath}/jslib/moment.min.js"></script>
<script src="${basePath}/stylelib/adminlte/plugins/daterangepicker/daterangepicker.js"></script>
<!-- bootstrap color picker -->
<script src="${basePath}/stylelib/adminlte/plugins/colorpicker/bootstrap-colorpicker.min.js"></script>
<script>
  <#--添加时间 Date range picker -->
  $('#createDateRange').daterangepicker({
    <#--  format: 'YYYY-MM-DD',
      separator: ' - '   -->
  });
  
  function getFinanceInfo(foId){
		$.ajax({
		   	type: "GET",
		   	url: basePath + "finance/getFinanceInfo",
		   	data: {  
               foId : foId
		    },
		   	success: function(resultObj){
		   		if(resultObj.isSuccess){
		   			var messBody = template('chooseFinanceInfoForPurposeImport', resultObj);
					alertMessageBox("金融机构信息",messBody);
				}else{
					alertMessageBox("金融机构信息",resultObj.failureMess);
				}
		   	},
		   	error:function(resultObj){  
		   	 	alertMessageBox("金融机构信息","出现异常，请稍后重试！");
		   	}
		});
  }
  
  function goFinanceAdd(){
  	window.location.href="${basePath}/finance/addView";
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

<#-- 显示根据id查询金融机构信息-->
<script id="chooseFinanceInfoForPurposeImport" type="text/html">
	<div class="box-body">
  		<table class="table table-bordered table-hover">
            <tbody>
				<tr>
					<td>机构名称</td>
					<td style="text-align:left">{{financeInfo.foName}}</td>
				</tr>
				<tr>
            		<td>公司Logo</td>
              		<td><img src="{{url}}" height="100"  /></td>
            	</tr>
				<tr>
					<td>机构地址</td>
					<td style="text-align:left">{{financeInfo.foAddr}}</td>
				</tr
				<tr>
					<td>公司网址</td>
					<td style="text-align:left">{{financeInfo.foUrl}}</td>
				</tr>
				<tr>
					<td>公司电话</td>
					<td style="text-align:left">{{financeInfo.foPhone}}</td>
				</tr>
				<tr>
					<td>公司简介</td>
					<td style="text-align:left">{{financeInfo.foDesc}}</td>
				</tr>
				<tr>
					<td>覆盖城市</td>
					<td style="text-align:left">{{coverCities}}</td>
				</tr>
				<tr>
					<td>添加时间</td>
					<td style="text-align:left">{{financeInfo.createTimeStr}}</td>
				</tr>
				<tr>
					<td>状态</td>
					<td style="text-align:left">{{financeInfo.foStatusStr}}</td>
				</tr>
				
			</tbody>
		</table>
	</div>
</script> 
  
<#-- 金融机构列表结束 -->
</section>
</div>


</@layout.page>
</#escape>