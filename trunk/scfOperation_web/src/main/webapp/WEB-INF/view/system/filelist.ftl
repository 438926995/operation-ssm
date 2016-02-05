<#import "/account/layout.ftl" as layout>
<#escape x as x?html>
<@layout.page title="金融超市附件列表">
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
	    金融超市附件列表
	  </h1>
	  <ol class="breadcrumb">
	    <li><a href="${basePath}/account/index"><i class="fa fa-dashboard"></i>首页</a></li>
	    <li><a href="${basePath}/finance/list">系统管理</a></li>
	    <li class="active">金融超市附件列表</li>
	  </ol>
	</section>
	<!-- Main content -->
	<section class="content">
	<#-- 金融超市附件列表开始 -->
              <div class="box">
                <div class="box-header">
                  	<#-- 查询 -->
					<form action="${basePath}/file/list/mart" method="get" id="queryFile">
						<div id="file_list_search"  >
							<div class="form-group">
								<label>附件名称：</label>
								<input name="source_name" value="${fqb.source_name!""}" class="f-text" id="source_name">
								<input type="submit" value="搜索">
							</div>
						</div>
					</form>
                </div>
                
                <div class="box-body">
                  <table id="file_list" class="table table-bordered table-hover">
                    <thead>
                      <tr>
                      	<th>序号</th>
                        <th id="source_name">原名称</th>
						<th id="file_edit_name">上传后文件名</th>
						<th id="mime_type">类型</th>
						<th id="file_size">大小</th>
						<th id="create_at">添加时间</th>
						<th id="contain_type">所属类别</th>
						<th id="operate">操作</th>
                      </tr>
                    </thead>
                    <tbody>
                    <#list tbData.list as file>
						<tr id="file_${file.ufId}">
							<td style="text-align:center">${file_index+1}</td>
							<td style="text-align:left">${file.sourceName!""}</td>
							<td style="text-align:left">${file.fileEditName!""}</td>
							<td style="text-align:left">${file.mimeType!""}</td>
							<td style="text-align:left">${file.fileSize!""}</td>
							<td>${file.createdAt!!?string('yyyy-MM-dd HH:mm:ss')} </td>
							<td style="text-align:left">
								${file.containType!""}
							</td>
							<td style="text-align:left">
							<@layout.security.authorize url="file/getFinanceOrgLogo">
								<a href="javascript:getFileInfo('${file.url!!}')" >查看</a>
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
  
  function getFileInfo(url){
	var messBody = "<img src='" + url + "' height='100'  />";
	alertMessageBox("附件查看",messBody);
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
<#-- 附件列表页面结束 -->
</section>
</div>


</@layout.page>
</#escape>