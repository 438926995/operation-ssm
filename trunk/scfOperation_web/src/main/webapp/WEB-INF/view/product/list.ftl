<#import "/account/layout.ftl" as layout>
<#escape x as x?html>
<@layout.page title="融资产品管理">
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
	    融资产品管理
	  </h1>
	  <ol class="breadcrumb">
	    <li><a href="${basePath}/account/index"><i class="fa fa-dashboard"></i>首页</a></li>
	    <li><a href="${basePath}/product/list">融资产品管理</a></li>
	    <li class="active">融资产品管理</li>
	  </ol>
	</section>
	<!-- Main content -->
	<section class="content">
	<#-- 融资产品管理开始 -->

              <div class="box">
                <div class="box-header">
                  	<#-- 查询 -->
					<form action="${basePath}/product/list" method="get" id="queryUser">
						<div id="user_list_search"  >
							<div class="form-group">
								<label>产品名称：</label>
								<input name="ptName" value="${pqb.ptName!""}" class="f-text" id="ptName">
								
								<label>所属机构：</label>
								<select name="foId" id="foId" >
									<option value=""  <#if pqb.foId?? && "${pqb.foId}"=="">selected="selected"</#if> >全部</option>
									<#list financeList as finance>
										<option value="${finance.foId?string}" <#if pqb.foId?? && "${pqb.foId}"=="${finance.foId?string}">selected="selected"</#if> >${finance.foName}</option>
									</#list>
								</select>
								<input type="submit" value="搜索"  >
								<@layout.security.authorize url="product/addView">
									<input type="button" value="添加产品" onclick = "javascript:goProductAdd()"  >
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
                        <th>融资产品名称</th>
						<th>所属机构</th>
						<th>投放地区</th>
						<th>状态</th>
						<th>添加时间</th>
						<th>过滤规则</th>
						<th>操作</th>
                      </tr>
                    </thead>
                    <tbody>
                    <#list tbData.list as product>
						<tr>
							<td style="text-align:center">${product_index+1}</td>
							<td style="text-align:left">${product.fpName!""}</td>
							<td style="text-align:left">${product.foName!""}</td>
							<td style="text-align:left">
								${product.fpArea!""}
							</td>
							<td style="text-align:left">
								<#if product.productStatus??>
									<#if product.productStatus?string=='0'>待上线
									<#elseif product.productStatus?string=='1'>上线
									<#else>下线</#if>
								</#if>
							</td>
							<td>${product.createAt!!?string('yyyy-MM-dd HH:mm:ss')}</td>
							<td style="text-align:left">
							<@layout.security.authorize url="product/rulelist">
								<a href="${basePath}/product/rulelist/${product.fpId!!}">过滤规则</a>
							</@layout.security.authorize>

							<#if (product.isFinance)?? && (product.isFinance == 0)>
							<@layout.security.authorize url="product/applylist">
								<a href="${basePath}/product/applylist/${product.fpId!!}">申请条件</a>
							</@layout.security.authorize>
							<@layout.security.authorize url="product/materiallist">
								<a href="${basePath}/product/materiallist/${product.fpId!!}">所需材料</a>
							</@layout.security.authorize>
							</#if>

							</td>
							<td style="text-align:left">
							<#if (product.isFinance)?? && (product.isFinance == 1)>
							<#-- <@layout.security.authorize url="product/approveView"> -->
								<a href="${basePath}/product/approveView/${product.fpId!!}" >详情</a>
							<#-- </@layout.security.authorize> -->
							</#if>
							<#if (product.isFinance)?? && (product.isFinance == 0)>
							<@layout.security.authorize url="product/editView">
								<a href="${basePath}/product/editView/${product.fpId!!}" >编辑</a>
							</@layout.security.authorize>
							</#if>
							<@layout.security.authorize url="product/del">
								<a href="javascript:deleteProductInfo('${product.fpId!!}')">删除</a>
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
function deleteProductInfo(fpId){
	if(confirm("确定要删除数据吗")){
		$.ajax({
		   	type: "GET",
		   	url: basePath + "product/del",
		   	data: {  
		       fpId : fpId
		    },
		   	success: function(resultObj){
		   		if(resultObj.isSuccess){
		   			alertMessageBox("融资产品信息",resultObj.message,function(){
		   					window.parent.location.reload();
		   				});
				}else{
					alertMessageBox("融资产品信息",resultObj.message,function(){window.parent.location.reload();});
				}
		   	},
		   	error:function(resultObj){  
		   	 	alertMessageBox("融资产品信息","出现异常，请稍后重试！");
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

function goProductAdd(){
  	window.location.href="${basePath}/product/addView";
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