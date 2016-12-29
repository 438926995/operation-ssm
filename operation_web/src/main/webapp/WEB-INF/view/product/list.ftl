<#import "/account/layout.ftl" as layout>
<#escape x as x?html>
<@layout.page title="产品列表">
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
	    产品列表
	  </h1>
	  <ol class="breadcrumb">
	    <li><a href="${basePath}/account/index"><i class="fa fa-dashboard"></i>首页</a></li>
	    <li><a href="${basePath}/user/list">产品管理</a></li>
	    <li class="active">产品列表</li>
	  </ol>
	</section>
	<!-- Main content -->
	<section class="content">
	<#-- 用户列表开始 -->

              <div class="box">
                <div class="box-header">
                  	<#-- 查询 -->
					<form action="${basePath}/product/list" method="get" id="queryUser">
						<div id="user_list_search"  >
							<div class="form-group">
								<label>产品名称：</label>
								<input name="fpName" value="${pqb.fpName!""}" class="f-text" id="fpName">
								
								<label>产品状态：</label>
								<select name="productStatus" id="productStatus" >
									<option value="">全部</option>
									<option value="0" <#if pqb.productStatus?? && pqb.productStatus == 0>selected="selected"</#if> >待发布</option>
								  	<option value="1" <#if pqb.productStatus?? && pqb.productStatus == 1>selected="selected"</#if> >已发布</option>
								  	<option value="2" <#if pqb.productStatus?? && pqb.productStatus == 2>selected="selected"</#if> >下架</option>
								</select>
								
								
								<input type="submit" value="搜索"  >
								
								<@layout.security.authorize url="product/add">
									<input type="button" value="添加产品" onclick="javascript:goUserAdd();" >
								</@layout.security.authorize>
								
							</div>
						</div>
					</form>
                </div>
                
                <div class="box-body">
                  <table id="user_list" class="table table-bordered table-hover">
                    <thead>
                      <tr>
                        <th>产品名称</th>
						<#--<th>产品覆盖地区</th>-->
						<th>产品状态</th>
						<th>其他描述</th>
						<th>操作</th>
                      </tr>
                    </thead>
                    <tbody>
                    <#list tbData.list as product>
						<tr>
							<td style="text-align:left">${product.fpName!""}</td>
							<#--<td style="text-align:left">${product.fpAreaName!} </td>-->
							<td style="text-align:left"><#if product.productStatus??><#if product.productStatus==0>待发布</#if>
							<#if product.productStatus == 1>已发布</#if>
							<#if product.productStatus == 2>下架</#if>
							</#if></td>
							<td style="text-align:left">${product.otherDesc!}</td>
							<td style="text-align:left">
							<@layout.security.authorize url="product/getProductInfo">
								<a href="${basePath}/product/ruleList/${product.fpId!}" >查看</a>
							</@layout.security.authorize>
							<@layout.security.authorize url="product/edit">
								<a href="${basePath}/product/edit/${product.fpId!}">编辑</a>
							</@layout.security.authorize>
							<@layout.security.authorize url="product/deleteProduct">
								<a href="javascript:deleteUserInfo('${product.fpId!}')">删除</a>
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
  function deleteUserInfo(fpId){
	if(confirm("确定要删除数据吗")){
		$.ajax({
		   	type: "POST",
		   	url: basePath + "product/deleteProduct",
		   	data: {
                fpId : fpId
		    },
		   	success: function(resultObj){
		   		if(resultObj.isSuccess){
		   			alertMessageBox("产品信息",resultObj.message,function(){
		   					window.parent.location.reload();
		   				});
				}else{
					alertMessageBox("产品信息",resultObj.message,function(){window.parent.location.reload();});
				}
		   	},
		   	error:function(resultObj){  
		   	 	alertMessageBox("产品信息","出现异常，请稍后重试！");
		   	}
		});
	}
}
  <#-- 查看用户详情，弹出框 -->
  function getProductInfo(fpId){
      window.location.href= basePath + "product/ruleList";
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
  	window.location.href= basePath + "product/addView";
  }
</script>
  <#-- 用户页面结束 -->
  </section>
</div>


</@layout.page>
</#escape>