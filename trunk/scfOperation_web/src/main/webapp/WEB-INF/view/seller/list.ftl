<#import "/account/layout.ftl" as layout>
<#escape x as x?html>
<@layout.page title="商户列表">
<head>
	<#-- 弹出框 -->
	<link rel="stylesheet" href="${basePath}/stylelib/remodal.css">
	<link rel="stylesheet" href="${basePath}/stylelib/remodal-default-theme.css">
	<link rel="stylesheet" href="${basePath}/stylelib/jquery.fileupload.css">
	<link rel="stylesheet" href="${basePath}/stylelib/bootstrap/css/bootstrap-table.min.css">
	<link rel="stylesheet" href="${basePath}/stylelib/bootstrap/css/bootstrap-select.min.css">	
	<#-- Bootstrap 颜色选择器 -->
    <link rel="stylesheet" href="${basePath}/stylelib/adminlte/plugins/colorpicker/bootstrap-colorpicker.min.css">
    <#-- Bootstrap 日期选择器 -->
    <link rel="stylesheet" href="${basePath}/stylelib/adminlte/plugins/daterangepicker/daterangepicker-bs3.css">
    <style>
    	.search-from .form-group label{
    		width:33.33%;
    		float: none;
    		text-align: right;
    		margin-right: 1rem;
    	}
    	.search-from .form-group input[type='text']{
    		width:60%;
    		float: none;
    	}
    	.search-from .operation button{
    		margin-right: 1rem;
    		float: right;
    	}
    	.exl-column-ul{
    		text-align:left;
    		-webkit-padding-start: 2rem;
    	}
    	
    	.exl-column-ul li{
    		 list-style-type:none;
    		 display:inline-table;
    		 padding:0.7rem;
    	}
    	
    	.upload-alert-success-msg{
    	     padding:0.7rem;
    		 color: #3c763d;
             border-color: #d6e9c6;
    	}
    	
    	.upload-alert-info-msg{
    		padding:0.7rem;
    		color: #31708f;
    		border-color: #bce8f1;
    	}
    	
    	.upload-alert-error-msg{
    	     padding:0.7rem;
    		 color: #a94442;
             border-color: #ebccd1;
    	}
    	#tableSellers th,td input[type='checkbox']{
			vertical-align:middle;
    	}
    </style>
</head>
<body>
<div class="content-wrapper">
	<!-- Content Header (Page header) -->
	<section class="content-header">
	  <h1>
	    商户列表
	  </h1>
	  <ol class="breadcrumb">
	    <li><a href="${basePath}/account/index"><i class="fa fa-dashboard"></i>首页</a></li>
	    <li><a href="${basePath}/seller/list">商户管理</a></li>
	    <li class="active">商户列表</li>
	  </ol>
	</section>
	<!-- Main content -->
	<section class="content">
	<#-- 商户列表开始 -->
	  	<div class="box">
	        <div class="box-header">
	        	<form action="${basePath}/seller/exportSellerInfo" method="post" id="exportSellerInfo">
					<div class="row search-from">
	                  	<#-- 查询 -->
						<div class="form-group col-sm-4">
							<label for="inputSellerName">
								商户名称:
							</label>
							<input type="text" id="inputSellerName" name="sellerName" class="f-text">
						</div>
						<div class="form-group col-sm-4">
							<label for="inputKeeperName">
								店主:
							</label>
							<input type="text" id="inputKeeperName" name="keeperName" class="f-text">
						</div>
						<div class="form-group col-sm-4">
							<label for="inputKeeperPhone" >
								电话号码:
							</label>
							<input type="text" id="inputKeeperPhone" name="keeperPhone" class="f-text">
						</div>
					</div>
					<div class="row search-from">
						<div class="form-group col-sm-4">
							<label for="inputSellerId" >商户ID:</label>
							<input type="text" id="inputSellerId" name="sellerId" class="f-text">
						</div>
						<div class="form-group col-sm-4">
							<label for="inputNaposResOid" >NaposOID:</label>
							<input type="text" id="inputNaposResOid" name="naposResOid" class="f-text">
						</div>
						<div class="form-group col-sm-4">
							<label for="selOnlyNapos">仅Napos商户:</label>
							<select id="selOnlyNapos" class="selectpicker" data-width="60%" style="margin-left:5%">
								<option value="1">是</option>
								<option value="0">否</option>
							</select>
						</div>
					</div>
					<div class="row search-from">
						<div class="form-group col-sm-4">
							<label for="selIsImport" >是否是导入:</label>
							<select id="selIsImport" class="selectpicker" data-width="60%" >
								<option value="0">否</option>
								<option value="1">是</option>
							</select>
						</div>
						<div class="form-group col-sm-4">
							<label for="inputBatch" >期数:</label>
							<input type="text" id="inputBatch" name="batch" class="f-text">
						</div>
						<div class="form-group col-sm-4">
							<label for="selIsValid" >是否是有效:</label>
							<select id="selIsValid" class="selectpicker" data-width="60%" >
								<option value="1">是</option>
								<option value="0">否</option>
							</select>
						</div>
					</div>
					<div class="row search-from">
						<div class="form-group col-sm-4">
						</div>
						<div class="form-group col-sm-4">
						</div>
						<div class="form-group col-sm-4 operation">
						<@layout.security.authorize url="seller/exportSellerInfo">
							<button type="button" class="btn btn-default" id="exportSellerInfoBtn">导出数据</button>
						</@layout.security.authorize>
							<button type="button" class="btn btn-default" id="searchSellerInfoBtn">搜索</button>
						</div>
					</div>
					<input type="hidden" id="hidExportExlType" name="exportExlType" value="0">
					<input type="hidden" id="hidExportExlColumn" name="exportExlColumn">
					<input type="hidden" id="hidExportSellerIdsStr" name="exportSellerIdsStr">
				</form>
			</div>
	        <div class="box-body">
	      		<table id="tableSellers" class="table table-bordered table-hover" ></table>
	      	</div>
	    </div><!-- /.box -->
	  
	    <!-- modal dialog for select excel column -->
		<div class="remodal" data-remodal-id="modal">
		    <h1>导出Excel设置</h1>
		    <p>请选择需要导出的字段：</p>
		    <ul class="exl-column-ul">
		    	
		    </ul>
		    <div style="-webkit-padding-start:2.5rem;text-align:left">
		    	<div>
					导出方式：
					<input type="radio" name="exportType" value="0" checked>根据搜索条件导出
					<input type="radio" name="exportType" value="1" style="margin-left:1rem">根据Excel导出
				</div>
				<div id="excelExport" style="marign-top:1rem;">
				    <div style="margin-top:1rem">
					    <!-- The fileinput-button span is used to style the file input field as button -->
					    <span class="btn btn-success fileinput-button">
					        <i class="glyphicon glyphicon-paperclip"></i>
					        <span>选取Excel</span>
					        <!-- The file input field used as target for the file upload widget -->
					        <input id="excelFileUpload" type="file" name="needExportSellersExcel" data-url="${basePath}/seller/uploadNeedExportSellers" multiple>
					    </span>
					    <span id="uploadAlertMessage">请点击左侧按钮上传Excel作为导出商户的查询条件</span>
					</div>
					
					<div style="margin-top:1rem">
						<span>导入Excel模板下载：<a href="${basePath}${excelTemplate.url!}">${excelTemplate.excelTemplateName!}</a></span>
					</div>
				</div>
			</div>
		    <br>
		    <a class="remodal-cancel" href="javascript:void(0);">取消</a>
		    <a class="remodal-confirm" href="javascript:void(0)">导出</a>
		</div>
	</section>
	
</body>
<script src="${basePath}/jslib/remodal.js"></script>
<!-- date-range-picker -->
<script src="${basePath}/jslib/moment.min.js"></script>
<script src="${basePath}/jslib/fileupload/vendor/jquery.ui.widget.js"></script>
<script src="${basePath}/jslib/fileupload/jquery.iframe-transport.js"></script>
<script src="${basePath}/jslib/fileupload/jquery.fileupload.js"></script>
<script src="${basePath}/jslib/bootstrap-table.min.js"></script>
<script src="${basePath}/jslib/bootstrap-table-zh-CN.min.js"></script>
<script src="${basePath}/jslib/bootstrap-select.min.js"></script>
<script src="${basePath}/stylelib/adminlte/plugins/daterangepicker/daterangepicker.js"></script>
<!-- bootstrap color picker -->
<script src="${basePath}/stylelib/adminlte/plugins/colorpicker/bootstrap-colorpicker.min.js"></script>
<script type="text/javascript" >
	var messBoxLoad;

	$(function(){
		<!--添加时间 Date range picker -->
		var sellers_page_num = 0;
		var limit = 10;
		$('#tableSellers').bootstrapTable({
			columns : [{
				field : 'napos_res_oid',
				title : 'NaposOID',
				width : "150px"
			}, {
				field : "seller_account",
				title : "商户账号",
				width : "150px"
			}, {
				field : "seller_name",
				title : "商户名称",
				width : "150px"
			}, {
                field : "city_name",
                title : "城市名称",
                width : "100px"
			}, {
				field : "keeper_name",
				title : "店主",
				width : "120px"
			}, {
				field : "keeper_phone",
				title : "联系电话",
				width : "150px"
			}, {
				field : "seller_id",
				title : "餐厅详情",
				formatter : function(value, row, index){
				<@layout.security.authorize url="seller/tradeFlow/statisticList">
					return "<a href='javascript:void(0)' onClick='sellerDetails("+ value+");'>餐厅详情</a>";
				</@layout.security.authorize>
				}
			} ],
			method : 'POST',
			url : "${basePath}/seller/list",
			contentType : 'application/x-www-form-urlencoded; charset=UTF-8',
			responseHandler : function(res) {
				var data = {
					total : 0,
					rows : []
				};
				if (res.isSuccess) {
					data.total = res.datas.total;
					data.rows = res.datas.rows;
				}
				return data;
			},
			queryParams : function(params) {
				// 添加查询属性
				$("#inputSellerName").val()&&(params["sellerName"]=$("#inputSellerName").val())
				$("#inputKeeperName").val()&&(params["keeperName"]=$("#inputKeeperName").val())
				$("#inputKeeperPhone").val()&&(params["keeperPhone"]=$("#inputKeeperPhone").val())
				$("#inputSellerId").val()&&(params["sellerId"]=$("#inputSellerId").val())
				$("#inputNaposResOid").val()&&(params["naposResOid"]=$("#inputNaposResOid").val())
				$('#selOnlyNapos').selectpicker('val')&&(params["onlyNapos"]=$('#selOnlyNapos').selectpicker('val'))
				$("#selIsImport").selectpicker('val')&&(params["isImport"]=$("#selIsImport").selectpicker('val'))
				$("#inputBatch").val()&&(params["batch"]=$("#inputBatch").val())
				$("#selIsValid").selectpicker('val')&&(params["isValid"]=$("#selIsValid").selectpicker('val'))
				
				// 添加分页参数
				params["pageSize"]=params.limit;
				params["currentPage"]=params.offset / params.limit;
				// 分页没做完
				return params;
			},
			height : 430,
			pageNumber : 1,
			pageSize : limit,
			pagination : true,
			sidePagination : "server",
			paginationFirstText : "首页",
			paginationPreText : "上一页",
			paginationNextText : "下一页",
			paginationLastText : "末页",
			select : true,
			onLoadError : function(status) {
				console.log("onLoadError:" + status);
			}
		});

		$("#searchSellerInfoBtn").click(function(){
			$('#tableSellers').bootstrapTable('refresh');
		})

		$("input[name='exportType']").each(function(index,item){
		    if(this.checked){
		    	if(this.value == 1){
	    			$("#excelExport").show();
		    	}
		    	if(this.value == 0){
		    		$("#excelExport").hide();
		    	}
		    } 
		})

		$("input[name='exportType']").change(function(){
			$("#hidExportExlType").val(this.value);
			if(this.value == 1){
				$("#excelExport").show();
			}else{
				$("#excelExport").hide();
			}
		});

	   	var excelColumnMap=[//{name:"ID",text:"餐厅Id", is_default:true, is_force:true},
	   					   {name:"OID",text:"餐厅Id", is_default:true, is_force:true},
	   					   {name:"NAME",text:"餐厅名称", is_default:true, is_force:true},
	   					   {name:"BUSY_LEVEL",text:"忙碌情况"},
	   					   {name:"DESCRIPTION",text:"描述", is_default:true},
	   					   {name:"ADDRESS_TEXT",text:"地址", is_default:true},
	   					   {name:"NUM_RATING_1",text:"1星评价个数"},
	   					   {name:"NUM_RATING_2",text:"2星评价个数"},
	   					   {name:"NUM_RATING_3",text:"3星评价个数"},
	   					   {name:"NUM_RATING_4",text:"4星评价个数"},
	   					   {name:"NUM_RATING_5",text:"5星评价个数"},
	   					   {name:"PHONE",text:"联系电话"},
	   					   {name:"MOBILE",text:"手机号码"},
	   					   {name:"MIN_DELIVER_AMOUNT",text:"最低起送价"},
	   					   {name:"IS_BOOKABLE",text:"是否支持预定"},
	   					   {name:"FLAVORS",text:"口味"},
	   					   {name:"DINE_ENABLED",text:"是否支持堂吃"},
	   					   {name:"DELIVER_SPENT",text:"配送时间, eleme 计算得出 "},
	   					   {name:"IS_TIME_ENSURE",text:"是否确保准时送到"},
	   					   {name:"AVG_COMMENT_TIME",text:"催单时间"},
	   					   {name:"ONLINE_PAYMENT",text:"是否支持线上支付"},
	   					   {name:"INVOICE",text:"是否提供发票"},
	   					   {name:"INVOICE_MIN_AMOUNT",text:"发票最小金额"},
	   					   {name:"KEEPER_NAME",text:"所有者姓名"},
	   					   {name:"KEEPER_PHONE",text:"所有者电话"},
	   					   {name:"RECENT_FOOD_POPULARITY",text:"最近所有食物卖出份数(30天)"},
	   					   //{name:"COME_FROM",text:"渠道(0,1)，线上，线下"},
	   					   {name:"RECENT_ORDER_NUM",text:"最近订单数量"},
	   					   {name:"CERTIFICATION_TYPE",text:"认证方式"},
	   					   {name:"CERTIFICATION_STATUS",text:"认证状态"},
	   					   {name:"METHOD_OF_PAYMENT",text:"支付方式"}
	   					  ];
	   var i = 0;
	   var ul = $(".exl-column-ul");
	   for(; i<excelColumnMap.length; i++){
	      var columnSet = excelColumnMap[i];
	      var li = $("<li></li>");
	      var input = $("<input data-col-name='" + columnSet["name"] + "' type='checkbox' >" + columnSet["text"] + "</input>");
	      columnSet["is_default"]&&input.attr("checked","true");
	      columnSet["is_force"]&&input.attr("disabled","disabled");
	      li.append(input);
	      ul.append(li);
	   }
	   var inst = $('[data-remodal-id=modal]').remodal();
	   $("#exportSellerInfoBtn").click(function(){
	   	   $("#hidExportSellerIdsStr").val("");
	   	   $("#uploadAlertMessage").attr("class","").html("请点击左侧按钮上传Excel作为导出商户的查询条件");
	   	   inst.open();  //打开modal对话框
	   });
	   
	   $(".remodal-cancel").click(function(){
	       hidExportSellerIdsStr = null; // 将要导出的商户Id数组清零
	   	   inst.close();   //关闭modal对话框
	   });
	   
	   $(".remodal-confirm").click(function(){
	   		var exportType = 0;
	   		$("input[name='exportType']").each(function(index,item){
			    if(this.checked){
			    	exportType = this.value;
			    }
			})

	   		if(exportType == 1){
	   			// 验证表单是否有效
				var sellerIdsStr = $("#hidExportSellerIdsStr").val();
				if(sellerIdsStr.length == 0){
					alertMessage("upload-alert-error-msg","请点击左侧按钮上传Excel作为导出商户的查询条件");
					return;
				}
	   		}
	       
	   	   var excelExportColumnArr = [];
	   	   $(".exl-column-ul li input").each(function(i,item){
			   if(item.checked){
			   		var columnObj = {};
			   		columnObj.name=$(item).data("col-name");
			   		columnObj.text=$(item).parent().text();
			   		excelExportColumnArr.push(columnObj);
			   }
		   });
		   $("#hidExportExlColumn").val(JSON.stringify(excelExportColumnArr));
	   	   inst.close();
	       $("#exportSellerInfo").submit();
	   });
	   
	   $('#excelFileUpload').fileupload({
	   	   	dataType: 'json',
	   	   	add:function(e, data){
	   	   		$("#hidExportSellerIdsStr").val(""); // 选择附件时清空商户Id字符串input
	   	   		data.submit();
	   	   	},
	   	   	done:function(e, data){
	   	   		if(data.result.isSuccess){
	   	   		    alertMessage("upload-alert-success-msg","上传成功，请点击导出");
	   	   			$("#hidExportSellerIdsStr").val(data.result.datas);
	   	   		}else {
	   	   			alertMessage("upload-alert-error-msg",data.result.message);
	   	   			$("#hidExportSellerIdsStr").val("");
	   	   		}
	   	   	},
	   	   	fail: function(e, data) {
            	alertMessageBox("数据导出", "请求超时，请重新登录！",function(){window.parent.location.reload();});
        	}
       });
       
       function alertMessage(style, msg){
       	   var alertMsgSpan = $("#uploadAlertMessage"); 
           alertMsgSpan.hide().attr("class", style).text(msg).show();(500);
       }
	});
	
	function fundsFlow(id){
		if(confirm("确定要获取交易流水吗")){
			
			loadMessageBox("商户交易流水", "交易流水导入中！");
			$.ajax({
			   	type: "POST",
			   	url: basePath + "seller/naposSellersFlow",
			   	data: { 
			       id : id
			    },
			   	success: function(resultObj){
			   		if(messBoxLoad.state == "opening"){
			   			messBoxLoad.close();
			   		}
			   		if(resultObj.isSuccess){
			   			alertMessageBox("商户交易流水",resultObj.message,function(){
			   					window.parent.location.reload();
			   				});
					}else{
						alertMessageBox("商户交易流水",resultObj.message,function(){window.parent.location.reload();});
					}
			   	},
			   	error:function(resultObj){
			   		if(messBoxLoad.state == "opening"){
			   			messBoxLoad.close();
			   		}
			   	 	alertMessageBox("商户交易流水", "出现异常，请稍后重试！",function(){window.parent.location.reload();});
			   	}
			});
		}
	}
	
	function loadMessageBox(messTitle,messBody,callback){
	  	$("#loadTitle").html(messTitle);
	  	$("#loadBody").html(messBody);
	    messBoxLoad = $('[data-remodal-id=messageLoad]').remodal();
	    messBoxLoad.open();
	    $(document).on('', '.remodal', function (e) {
	    	callback();
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

	// 打开详细页面
	function sellerDetails(sellerId){
		window.location.href = basePath+"seller/detail/"+sellerId;

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

<#-- 悬浮层弹层 -->
<div class="remodal" data-remodal-id="messageLoad" role="dialog" aria-labelledby="loadTitle" aria-describedby="loadBody">
  <div>
    <h2 id="loadTitle"></h2>
    <p id="loadBody">
    </p>
    <div class="overlay">
	  <i class="fa fa-refresh fa-spin"></i>
	</div>
  </div>
  <br>
  
</div>
  
  <#-- 商户列表页面结束 -->
  </section>
</div>

</@layout.page>
</#escape>
