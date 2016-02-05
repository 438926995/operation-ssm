<#import "/account/layout.ftl" as layout>
<#escape x as x?html>
<@layout.page title="融资产品所需材料管理">
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
	    融资产品所需材料管理
	  </h1>
	  <ol class="breadcrumb">
	    <li><a href="${basePath}/account/index"><i class="fa fa-dashboard"></i>首页</a></li>
	    <li><a href="${basePath}/product/list">融资产品管理</a></li>
	    <li class="active">  融资产品所需材料管理</li>
	  </ol>
	</section>
	<!-- Main content -->
	<section class="content">
	<#--   融资产品所需材料管理开始 -->

              <div class="box">
                <div class="box-header">
                  	<#-- 查询 -->
					<form action="" method="get" id="queryUser">
						<div id="user_list_search"  >
							<div class="form-group">
							<@layout.security.authorize url="product/addMaterialView">
								<input type="button" value="添加所需材料" onclick = "javascript:goMaterialAdd()"  >
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
                        <th>字段名</th>
						<th>操作</th>
                      </tr>
                    </thead>
                    <tbody>
                    <#list tbData.list as detail>
						<tr>
							<td style="text-align:center">${detail_index+1}</td>
							<td style="text-align:left">${detail.ruleName!""}</td>
							<td style="text-align:left">
							<@layout.security.authorize url="product/editMaterialView">
								<a href="${basePath}/product/editMaterialView/${detail.prId!!}" >编辑</a>
							</@layout.security.authorize>
							<@layout.security.authorize url="product/applyDel">
								<a href="javascript:deleteMaterialInfo('${detail.prId!!}')">删除</a>
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
  
<#-- 融资产品申请条件管理页面结束 -->
</section>
</div>

<script>
function deleteMaterialInfo(prId){
	if(confirm("确定要删除数据吗")){
		$.ajax({
		   	type: "GET",
		   	url: basePath + "product/applyDel",
		   	data: {  
		       prId : prId
		    },
		   	success: function(resultObj){
		   		if(resultObj.isSuccess){
		   			alertMessageBox("融资产品所需材料",resultObj.message,function(){
		   					window.parent.location.reload();
		   				});
				}else{
					alertMessageBox("融资产品所需材料",resultObj.message,function(){window.parent.location.reload();});
				}
		   	},
		   	error:function(resultObj){  
		   	 	alertMessageBox("融资产品所需材料","出现异常，请稍后重试！");
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

function goMaterialAdd(){
  	window.location.href="${basePath}/product/addMaterialView/${fpId}";
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