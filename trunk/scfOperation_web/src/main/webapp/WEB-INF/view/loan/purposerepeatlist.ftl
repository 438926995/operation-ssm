<#import "/account/layout.ftl" as layout>
<#escape x as x?html>
    <@layout.page title="贷款管理">
    <head xmlns="http://www.w3.org/1999/html">
    <#-- 弹出框 -->
        <link rel="stylesheet" href="${basePath}/stylelib/remodal.css">
        <link rel="stylesheet" href="${basePath}/stylelib/remodal-default-theme.css">
        <link rel="stylesheet" href="${basePath}/stylelib/bootstrap/css/bootstrap-select.min.css">
    <#--<link rel="stylesheet" href="${basePath}/stylelib/select2.min.css">-->
    <#-- Bootstrap 颜色选择器 -->
        <link rel="stylesheet"
              href="${basePath}/stylelib/adminlte/plugins/colorpicker/bootstrap-colorpicker.min.css">
    <#-- Bootstrap 日期选择器 -->
        <link rel="stylesheet"
              href="${basePath}/stylelib/adminlte/plugins/daterangepicker/daterangepicker-bs3.css">
    </head>
    <body>
    <div class="content-wrapper">
        <!-- Content Header (Page header) -->
        <section class="content-header">
            <h1>
                未开通城市商户贷款申请重复数据
            </h1>
            <ol class="breadcrumb">
                <li><a href="${basePath}/account/index"><i class="fa fa-dashboard"></i>首页</a></li>
                <li><a href="${basePath}/purpose/loan/list">未开通城市商户贷款管理</a></li>
                <li class="active">未开通城市商户贷款管理</li>
            </ol>
        </section>
        <!-- Main content -->
        <section class="content">
        <#-- 未开通城市商户贷款管理页面开始 -->

            <div class="box">
                <div class="box-header">
                <#-- 查询 -->
                    <form action="${basePath}/purpose/loan/repeatList" method="get" id="queryPurposeLoan">
                        <div id="loan_list_search">
                            <div class="form-group">
                            	<label>地区：</label>&nbsp;
                                <select id="provinceId" name="provinceId"
                                        onchange="getCityList(this.value);">
                                    <option value="">省</option>
                                    <#list provinceList as mCityTree>
                                        <option value="${mCityTree.cityID}"<#if plq.provinceId?? && plq.provinceId == mCityTree.cityID>
                                                selected="selected"</#if>>${mCityTree.cityName}</option>
                                    </#list>
                                </select>
                                <select id="cityId" name="cityId">
                                    <option value="">市</option>
                                    <#if cityList?? && cityList?size gt 0>
                                        <#list cityList as mCityTree>
                                            <option value="${mCityTree.cityID}"<#if plq.cityId?? && plq.cityId == mCityTree.cityID>
                                                    selected="selected"</#if>>${mCityTree.cityName}</option>
                                        </#list>
                                    </#if>
                                </select>
                                &nbsp;&nbsp;&nbsp;&nbsp;
                                <label>时间：</label>&nbsp;
                                <input id="submitTimeFrom"
                                       name="submitTimeFrom" <#if plq.submitTimeFrom??>
                                       value="${(plq.submitTimeFrom!!?string('yyyy-MM-dd'))!}"</#if>
                                       required readonly="readonly">
                                至
                                <input id="submitTimeTo"
                                       name="submitTimeTo" <#if plq.submitTimeTo??>
                                       value="${(plq.submitTimeTo!!?string('yyyy-MM-dd'))!}"</#if>
                                       required readonly="readonly">
                                <input type="submit" value="搜索">
                            </div>
                        </div>
                    </form>
                </div>
                <div class="box-body">
                    <table id="loan_list" class="table table-bordered table-hover">
                        <thead>
                        <tr>
                            <th>序号</th>
                            <th>申请单号</th>
                            <th>申请人</th>
                            <th>餐厅名</th>
                            <th>地区</th>
                            <th>申请时间</th>
                            <th>申请金额</th>
                            <th>操作</th>
                        </tr>
                        </thead>
                        <#list tbData.list as loanSeller>
                        <tr>
                            <td style="text-align:center">${loanSeller_index+1}</td>
                            <td style="text-align:left">${loanSeller.docNo}</td>
                            <td style="text-align:left">${loanSeller.proposerName!""}</td>
                            <td style="text-align:left">${loanSeller.sellerName!""}</td>
                            <td style="text-align:left">${loanSeller.cityName!""}</td>
                            <td style="text-align:left"><#if loanSeller.submitTime??>${loanSeller.submitTime!!?string('yyyy-MM-dd HH:mm:ss')}</#if></td>
                            <td style="text-align:left"><#if loanSeller.loanAmount??>${loanSeller.loanAmountTenThousand!!?string} 万</#if></td>
                            <td style="text-align:center">
                            	<#if loanSeller.repeatFlag??>
									<#if loanSeller.repeatFlag?string=='1'> <a href="javascript:deletePurposeLoanInfo(${loanSeller.plId!!})">删除</a></#if>
								</#if>
                            </td>
                        </tr>
                        </#list>
                    </table>
                </div><!-- /.box-body -->
                <div id="productpager" class="box-footer">
		    <#include "/common/pager_bar.ftl">
	        </div>
            </div><!-- /.box -->
        <#-- 消息提示弹层 -->
            <div class="remodal" data-remodal-id="messageBox" role="dialog" aria-labelledby="messageTitle"
                 aria-describedby="messageBody">
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
    <script src="${basePath}/jslib/bootstrap-select.min.js"></script>
    <#--<script src="${basePath}/jslib/select2.min.js"></script>-->
    <!-- date-range-picker -->
    <script src="${basePath}/jslib/moment.min.js"></script>
    <script src="${basePath}/stylelib/adminlte/plugins/daterangepicker/daterangepicker.js"></script>
    <!-- bootstrap color picker -->
    <script src="${basePath}/stylelib/adminlte/plugins/colorpicker/bootstrap-colorpicker.min.js"></script>

    <#-- 未开通城市商户贷款管理页面结束 -->
    </section>
    </div>

    <script>
        $('#submitTimeFrom').daterangepicker({
            format: 'YYYY-MM-DD',
            singleDatePicker: true
        });
        $('#submitTimeTo').daterangepicker({
            format: 'YYYY-MM-DD',
            singleDatePicker: true
        });
        
        function alertMessageBox(messTitle, messBody, callback) {
            $("#messageTitle").html(messTitle);
            $("#messageBody").html(messBody);
            var messBox = $('[data-remodal-id=messageBox]').remodal();
            messBox.open();
            $(document).on('closed', '.remodal', function (e) {
                callback();
            });
        }
        
        function getCityList(provinceId) {
            $("#cityId").empty();
            $("#cityId").append('<option value="">市</option>');

            if (provinceId == '') {
                return;
            }

            $.ajax({
                url: '${basePath}/cityList/' + provinceId,
                type: 'get',
                dataType: 'json',
                success: function (result) {
                    $.each(result, function (idx, obj) {
                        $("#cityId").append('<option value="' + obj.cityID + '">' + obj.cityName + '</option>');
                    });
                },
                error: function (e) {
                    alertMessageBox("错误", "获取省市信息失败，请稍后重试！");
                }
            });
        }
        
        function deletePurposeLoanInfo(plId){
			if(confirm("确定要删除数据吗")){
				$.ajax({
				   	type: "GET",
				   	url: basePath + "purpose/loan/deletePurposeLoan",
				   	data: {  
				       plId : plId
				    },
				   	success: function(resultObj){
				   		if(resultObj.isSuccess){
				   			alertMessageBox("未开通城市贷款信息",resultObj.message,function(){
				   					window.parent.location.reload();
				   				});
						}else{
							alertMessageBox("未开通城市贷款信息",resultObj.message,function(){window.parent.location.reload();});
						}
				   	},
				   	error:function(resultObj){  
				   	 	alertMessageBox("未开通城市贷款信息","出现异常，请稍后重试！");
				   	}
				});
			}
        }
    </script>

  
    </@layout.page>
</#escape>
