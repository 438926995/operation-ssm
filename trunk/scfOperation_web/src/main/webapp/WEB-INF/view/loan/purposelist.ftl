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
        <style>
            #selTemplate span {
                padding: 0px;
            }

            .exl-column-ul li {
                list-style: none;
                display: inline-table;
                width: 25%;
            }

            .excelExportSetting-body {
                text-align: left;

            }

            .excelExportSetting-body h3 {
                text-align: center;
            }

            #select2-selTemplate-container {
                line-height: 24px;
            }
        </style>
    </head>
    <body>
    <div class="content-wrapper">
        <!-- Content Header (Page header) -->
        <section class="content-header">
            <h1>
                未开通城市商户贷款管理
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
                    <form action="${basePath}/purpose/loan/list" method="get" id="queryPurposeLoan">
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
                                <@layout.security.authorize url="purpose/loan/exportList">
                                    <input type="button" value="导出" onclick="goExportList()">
                                </@layout.security.authorize>
                                <@layout.security.authorize url="purpose/loan/getStatisticsInfo">
                                    <input type="button" value="查看统计" onclick="getStatisticsInfo()">
                                </@layout.security.authorize>
								<input type="button" value="删除重复数据" onclick = "javascript:goRepeatData()">
								
                            <#-- <input type="button" value="查看统计" onclick = "getStatisticsInfo()" > -->
                                <input type="button" value="分配" onclick="getOrgProductList()">
                                <input id="flowChk" name="flowChk" value="1" type="checkbox"
                                       checked>分配拉取流水</input>
                            </div>
                        </div>
                        <input type="hidden" name="exportExlColumn" id="hidExportExlColumn"/>
                    </form>
                </div>
                <div class="box-body">
                    <label>申请金额总额：</label>${loanAmountSum}万元
                    <table id="loan_list" class="table table-bordered table-hover">
                        <thead>
                        <tr>
                            <th>全选&nbsp;&nbsp;&nbsp;&nbsp;<input id="checkAll" type="checkbox"/>
                            </th>
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
                        <form id="assignOrgFrm" action="${basePath}/purpose/loan/assignOrg"
                              method="post">
                            <input type="hidden" name="token" value="${token}">
                            <input id="fpId" name="fpId" type="hidden"/>
                            <input id="isGetFlow" name="isGetFlow" type="hidden"/>
                            <tbody>
                                <#list tbData.list as loanSeller>
                                <tr>
                                    <td style="text-align:center"><input name="plIds"
                                                                         type="checkbox"
                                                                         value="${loanSeller.plId}"/>
                                    </td>
                                    <td style="text-align:center">${loanSeller_index+1}</td>
                                    <td style="text-align:left">${loanSeller.docNo}</td>
                                    <td style="text-align:left">${loanSeller.proposerName!""}</td>
                                    <td style="text-align:left">${loanSeller.sellerName!""}</td>
                                    <td style="text-align:left">${loanSeller.cityName!""}</td>
                                    <td style="text-align:left"><#if loanSeller.submitTime??>${loanSeller.submitTime!!?string('yyyy-MM-dd HH:mm:ss')}</#if></td>
                                    <td style="text-align:left"><#if loanSeller.loanAmount??>${loanSeller.loanAmountTenThousand!!?string}
                                        万</#if></td>
                                    <td style="text-align:center"><a
                                            href="${basePath}/purpose/loan/detail/${loanSeller.plId!!}">申请详情</a>
                                    </td>
                                </tr>
                                </#list>
                            </tbody>
                        </form>
                    </table>
                </div><!-- /.box-body -->
                <div id="productpager" class="box-footer">
		    <#include "/common/pager_bar.ftl">
	        </div>
            </div><!-- /.box -->
        <#-- 消息提示弹层 -->
            <div class="remodal" data-remodal-id="messageBox" role="dialog"
                 aria-labelledby="messageTitle"
                 aria-describedby="messageBody">
                <button data-remodal-action="close" class="remodal-close"
                        aria-label="Close"></button>
                <div>
                    <h2 id="messageTitle"></h2>
                    <p id="messageBody">

                    </p>
                </div>
                <br>
                <button data-remodal-action="cancel" class="remodal-cancel">关闭</button>
            </div>
            <!-- modal dialog for select excel column -->
            <div class="remodal excelExportSetting" data-remodal-id="exportDialogBox">
                <button data-remodal-action="close" class="remodal-close"
                        aria-label="Close"></button>
                <div class="excelExportSetting-body">
                    <h3><strong>Excel导出设置</strong></h3>
                    <div class="row" style="margin-top: 2rem;">
                        <div class="col-sm-8">
                            <strong style="padding-top: 5px">模板名称</strong>
                        <#--<select id="selTemplate">-->
                        <#--</select>-->
                            <div class="input-group" style="padding-top: 5px;">
                                <select id="selTemplate" class="selectpicker" data-width="200px"
                                        title="点击此处选择导出的Excel模板">
                                </select>
								<span class="input-group-addon">
						            <i id="selTemplateClear"
                                       class="glyphicon glyphicon-remove fa fa-remove"
                                       style="cursor:pointer" title="清除选择"></i>
					            </span>
                            </div>
                        </div>
                        <div class="col-sm-4" style="text-align: right;bottom: 0px">
                            <@layout.security.authorize url="loan/exceltemplate/save">
                                <a id="goSaveExcelTemp" class="btn btn-sm btn-success"
                                   href="javascript:void(0)">保存模板</a>
                            </@layout.security.authorize>
                            <@layout.security.authorize url="loan/exceltemplate/remove">
                                <a id="removeExcelTemp" class="btn btn-sm btn-danger"
                                   href="javascript:void(0)">删除模板</a>
                            </@layout.security.authorize>
                            <p id="excelTempOperationMsg" class="help-block"></p>
                        </div>
                    </div>
                    <div style="padding-top: 1rem;" id="checkColumnsBox">
                    </div>
                </div>
                <div style="padding-top: 1rem;">
                    <button class="remodal-cancel" data-remodal-action="close">取消</button>
                    <button class="remodal-confirm" onclick="exportList()">导出</button>
                </div>
            </div>
            <@layout.security.authorize url="loan/exceltemplate/save">
                <div class="remodal" data-remodal-id="inputExcelNameBox" role="dialog"
                     aria-labelledby="messageTitle"
                     aria-describedby="messageBody">
                    <button data-remodal-action="close" class="remodal-close"
                            aria-label="Close"></button>
                    <h3>信息导出模板编辑</h3>
                    <div class="form-group text-left">
                        <input class="form-control" type="text" name="txtExcelTempName"
                               id="txtExcelTempName"
                               placeholder="请输入模板名称"/>
                        <p class="help-block"></p>
                    </div>
                    <br>
                    <button class="remodal-confirm" id="saveExcelTemp">保存</button>
                    <button data-remodal-action="close" class="remodal-cancel">关闭</button>
                </div>
            </@layout.security.authorize>
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
        // 全局变量,下拉框绑定数据
        var excelTempArr = [];

        // 导出字段初始数据定义
        var excelColumnArr = [];

        // 初始化
        $(function () {
        <#--添加时间 Date range picker -->
            $('#submitTimeFrom').daterangepicker({
                format: 'YYYY-MM-DD',
                singleDatePicker: true
            });
            $('#submitTimeTo').daterangepicker({
                format: 'YYYY-MM-DD',
                singleDatePicker: true
            });

            $("#selTemplate").on("changed.bs.select", function (event, clickedIndex, newValue, oldValue) {
                if (excelTempArr[clickedIndex - 1].excelTemplateRelations) {
                    loadExcelTemplateChecked(excelTempArr[clickedIndex - 1].excelTemplateRelations)
                }
            });
            loadSelectData();

            initExcelExportColumns();

            $("[data-remodal-id='inputExcelNameBox']>button[data-remodal-action='close']").click(function () {
                var exportBox = $('[data-remodal-id=exportDialogBox]').remodal();
                exportBox.open();
            });
            <@layout.security.authorize url="loan/exceltemplate/save">
                $("#goSaveExcelTemp").click(function () {
                    var tempId = $("#selTemplate").selectpicker("val");
                    var tempName = "";
                    if (tempId) {
                        tempName = $("#selTemplate").prevAll("button")[0].title;
                    }
                    initInputExcelTemplateDialog(tempName);
                });
            </@layout.security.authorize>

            $("#saveExcelTemp").click(function () {//保存excel模板
                saveExcelTemplate()
            });

            $("#removeExcelTemp").click(function () { // 删除excel模板
                removeExcelTemlate();
            })

            $("#selTemplateClear").parent().click(function () { // 清除select
                $("#selTemplate").selectpicker("deselectAll");
            })


        });

        // 加载模板选择框数据
        function loadSelectData() {
            var selTempCon = $("#selTemplate");

            $.ajax({
                url: "${basePath}/loan/exceltemplate/list",
                dataType: 'json',
                success: function (data, params) {
                    selTempCon.empty();
                    if (data.isSuccess && data.datas) {
                        excelTempArr = data.datas;
                        for (var i = 0; i < excelTempArr.length; i++) {
                            var optionCon = $("<option value='" + excelTempArr[i].excelTemplateId + "'>" + excelTempArr[i].excelTemplateName + "</option>")
                            selTempCon.append(optionCon);
                        }
                    }
                    selTempCon.selectpicker('refresh');
                },
                error: function (e) {
                    console.log(e);
                }
            })
        }

        // 根据excel模板 动态家宅Checked状态
        function loadExcelTemplateChecked(relations) {
            $("#checkColumnsBox li input").each(function (i, item) {
                item.checked = false;
                for (var j = 0; j < relations.length; j++) {
                    if (relations[j].columnId == $(item).data("col-id")) {
                        item.checked = true;
                        break;
                    }
                }
            });
        }

        // 初始化excel导出字段
        function initExcelExportColumns() {
            $.ajax({
                url: "${basePath}/loan/exceltemplate/approveLoanColumns",
                dataType: "json",
                success: function (data) {
                    if (!data.isSuccess) { // 异常情况
                        console.log(data.message);
                        return;
                    }
                    excelColumnArr = data.datas;
                    var container = $("#checkColumnsBox");
                    for (var i = 0; i < excelColumnArr.length; i++) { // 遍历有几种类型的checkout
                        if (excelColumnArr[i].columns != null && excelColumnArr[i].columns.length > 0) {
                            var groupTitle = $("<strong>" + excelColumnArr[i].text + "</strong>");
                            var checkUl = $("<ul class='exl-column-ul' data-group-id='" + excelColumnArr[i].id + "'></ul>")
                            for (var j = 0; j < excelColumnArr[i].columns.length; j++) {
                                var columnSet = excelColumnArr[i].columns[j];
                                var li = $("<li></li>");
                                var input = $("<input data-col-id='" + columnSet["id"] + "' data-col-name='" + columnSet["name"] + "' type='checkbox' >" + columnSet["text"] + "</input>");
                                columnSet["is_default"] && input.attr("checked", "true");
                                columnSet["is_force"] && input.attr("disabled", "disabled");
                                li.append(input);
                                checkUl.append(li);
                            }
                            container.append(groupTitle);
                            container.append(checkUl);
                        }
                    }
                },
                error: function (e) {
                    console.log(e);
                }
            })


        }

        // 保存excel模板
        function saveExcelTemplate() {
            var tempNameCol = $("#txtExcelTempName");
            var tempName = $.trim(tempNameCol.val());
            if (!tempName) {
                tempNameCol.parent().removeClass("has-success has-error").addClass("has-error");
                tempNameCol.next().html("请填写模板名称").hide().fadeIn(500);
                tempNameCol.val("");
                tempNameCol.focus();
                return;
            }
            var tempId = $("#selTemplate").selectpicker("val");
            var columnArr = [];
            $("#checkColumnsBox li input").each(function (i, item) {
                if (item.checked) {
                    var columnObj = {};
                    columnObj.columnId = $(item).data("col-id");
                    columnObj.columnName = $(item).data("col-name");
                    columnObj.columnText = $(item).parent().text();
                    columnArr.push(columnObj);
                }
            });

            $.ajax({
                url: "${basePath}/loan/exceltemplate/save",
                method: "POST",
                dataType: "json",
                data: {
                    excelTemplateId: tempId,
                    excelTemplateName: tempName,
                    excelTemplateRelationsJsonStr: JSON.stringify(columnArr)
                },
                success: function (data) {
                    if (data.isSuccess) {
                        var exportBox = $('[data-remodal-id=exportDialogBox]').remodal();
                        exportBox.open();
                        loadSelectData();
                    } else {
                        tempNameCol.parent().removeClass("has-success has-error").addClass("has-error");
                        tempNameCol.next().html("请填写模板名称").hide().fadeIn(500);
                    }
                },
                error: function (e) {
                    console.log(e);
                }
            })
        }
        // 删除excel 模板
        function removeExcelTemlate() {
            var tempId = $("#selTemplate").selectpicker("val");
            if (!tempId) {
                $("#excelTempOperationMsg").text("请选择要删除的模板");
                $("#excelTempOperationMsg").parent().removeClass("has-success has-error").addClass("has-error");
                $("#excelTempOperationMsg").hide().fadeIn(500).delay(2000).fadeOut(500);
                return;
            }
            $.ajax({
                url: "${basePath}/loan/exceltemplate/remove/" + tempId,
                method: "POST",
                dataType: "json",
                success: function (data) {
                    if (data.isSuccess) {
                        loadSelectData();
                    } else {

                    }
                },
                error: function (e) {
                    console.log(e);
                }
            })
        }

        <@layout.security.authorize url="loan/exceltemplate/save">
        // 初始化 excel模板 编辑名称弹出框
        function initInputExcelTemplateDialog(initExcelName) {
            var messBox = $('[data-remodal-id=inputExcelNameBox]').remodal();
            $("#txtExcelTempName").val(initExcelName);
            messBox.open();
        }
        </@layout.security.authorize>

        // 打开导出对话框
        function goExportList() {
            var exportBox = $('[data-remodal-id=exportDialogBox]').remodal();
            exportBox.open();
        <#--$("#queryPurposeLoan").attr("action", "${basePath}/purpose/loan/exportList");-->
        <#--$("#queryPurposeLoan").submit();-->
        <#--$("#queryPurposeLoan").attr("action", "${basePath}/purpose/loan/list");-->
        }

        // 导出数据
        function exportList() {
            var columnGroupArr = [];
            $("#checkColumnsBox ul").each(function () {
                var groupId = $(this).data("group-id");
                var groupText = $($(this)).prev().text();
                var columnArr = [];
                $(this).find("li input").each(function (i, item) {
                    if (item.checked) {
                        var columnObj = {};
                        columnObj.name = $(item).data("col-name");
                        columnObj.text = $(item).parent().text();
                        columnArr.push(columnObj);
                    }
                })
                var group = {
                    id: groupId,
                    text: groupText,
                    columns: columnArr
                }
                columnGroupArr.push(group);
            });

            $("#hidExportExlColumn").val(JSON.stringify(columnGroupArr));
            $("#queryPurposeLoan").attr("action", "${basePath}/purpose/loan/exportList");
            $("#queryPurposeLoan").submit();
            $("#queryPurposeLoan").attr("action", "${basePath}/purpose/loan/list");
        }

        <#--查看统计数据-->
        function getStatisticsInfo() {
            $.ajax({
                type: "GET",
                url: basePath + "purpose/loan/getStatisticsInfo",
                data: {
                    startTime: $("#submitTimeFrom").val(),
                    endTime: $("#submitTimeTo").val()
                },
                success: function (resultObj) {
                    var messBody = template('chooseStatisticsPurposeImport', resultObj);
                    alertMessageBox("未开通城市餐厅统计信息", messBody);
                },
                error: function (resultObj) {
                    alertMessageBox("未开通城市餐厅统计信息", "出现异常，请稍后重试！");
                }
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

        $("#checkAll").click(function () {
            $("input[name='plIds']").prop("checked", $(this).prop("checked"));
        });

        function getOrgProductList() {
            if ($("input[name='plIds']:checked").length == 0) {
                alertMessageBox("", "至少选择一条申请记录！");
                return;
            }
            var messBody = template('OrgProductList');
            alertMessageBox("选择贷款机构", messBody);
        }

        function assignOrg() {
            if ($("input:radio[name='fpIdRdo']:checked").length == 0) {
                alert("请选择机构！");
            }

            var pfId = $("input:radio[name='fpIdRdo']:checked").val();
            var pfName = $("#pfName_" + pfId).text();
            var isGetFlow;
            if ($("#flowChk").is(':checked')) {
                isGetFlow = '1';
            } else {
                isGetFlow = '0';
            }
            if (confirm("是否确定将选择的贷款申请单指定给" + pfName + ",一旦指定将不能再修改？")) {
                $("#fpId").val(pfId);
                $("#isGetFlow").val(isGetFlow);
                $("#assignOrgFrm").submit();
            }
        }

        <#--点击省份返回-->
        function backToDetailP(td) {
            var provId = $(td).data("provid");
            $("#provinceId").val(provId);
            $("#queryPurposeLoan").submit();
        }

        <#--点击城市返回-->
        function backToDetailC(label) {
            var provId = $(label.parentElement).data("provid");
            var cityId = $(label).data("cityid");
            var cityname = $(label).attr("value");
            $("#provinceId").val(provId);
            $("#cityId").empty();
            $("#cityId").append('<option value="' + cityId + '" selected="selected">cityname</option>');
            $("#queryPurposeLoan").submit();
        }

        <#--导出统计结果 -->
        function exportStatistics() {
            $("#queryPurposeLoan").attr("action", "${basePath}/purpose/loan/exportStatistics");
            $("#queryPurposeLoan").submit();
            $("#queryPurposeLoan").attr("action", "${basePath}/purpose/loan/list");
        }
        
        function goRepeatData(){
		  window.location.href="${basePath}/purpose/loan/repeatList";
		}

        function alertMessageBox(messTitle, messBody, callback) {
            $("#messageTitle").html(messTitle);
            $("#messageBody").html(messBody);
            var messBox = $('[data-remodal-id=messageBox]').remodal();
            messBox.open();
            $(document).on('closed', '.remodal', function (e) {
                callback();
            });
        }
    </script>

    <#-- 模版 -->
    <#-- 显示统计信息信息-->
    <script id="chooseStatisticsPurposeImport" type="text/html">
        <div>
            时间：&nbsp;
            <label id="timeFrom">{{startTime}}</label>
            —
            <label id="timeTo">{{endTime}}</label>
        </div>
        <div class="box-body">
            <table class="table table-bordered table-hover">
                <tbody>
                <tr>
                    <th style="text-align:center">省</th>
                    <th style="text-align:center">市</th>
                </tr>

                {{each details as detail i}}
                <tr>
                    <td id="detail_{{i}}" onclick="backToDetailP(this)" style="cursor:pointer;"
                        data-provid="{{detail.provId}}">
                        {{detail.provName}}({{detail.accountTotal}})
                    </td>
                    <td id="c_{{i}}" style="text-align:left" data-provid="{{detail.provId}}">
                        {{each detail.cityStatistics as cityStatistic j}}
                        <label style="cursor:pointer;" data-cityid="{{cityStatistic.cityId}}"
                               onclick="backToDetailC(this)" value="{{cityStatistic.cityName}}">
                            {{cityStatistic.cityName}}({{cityStatistic.cityAccount}})
                            &nbsp;</label>
                        {{/each}}
                    </td>
                <tr>
                    {{/each}}

                    <td>合计</td>
                    <td style="text-align:left">{{statisticsCount}}</td>
                </tr
                </tbody>
            </table>
            <input type="button" value="导出" style="float:right" onclick="exportStatistics()">
        </div>
    </script>

    <#-- 机构列表 -->
    <script id="OrgProductList" type="text/html">
        <div class="box-body">
            <table class="table table-bordered table-hover">
                <tbody>
                    <#list orgProductList as org>
                        <#list org.financeProducts as pro>
                        <tr>
                            <#if (pro_index == 0)>
                                <td rowspan="${org.financeProducts?size}">${org.foName}</td>
                            </#if>
                            <td style="text-align:left"><input type="radio" name="fpIdRdo"
                                                               value="${pro.fpId}"
                                                               <#if (org_index == 0 && pro_index == 0)>checked="checked"</#if>/><span
                                    id="pfName_${pro.fpId}">${pro.fpName}</span></td>
                        </tr>
                        </#list>
                    </#list>
                <tr>
                    <td colspan="2"><input type="button" value="确定" onclick="assignOrg();"/></td>
                </tr>
                </tbody>
            </table>
        </div>
    </script>
    </@layout.page>
</#escape>
