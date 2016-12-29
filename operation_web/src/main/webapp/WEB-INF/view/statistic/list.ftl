<#import "/account/layout.ftl" as layout>
<#escape x as x?html>
    <@layout.page title="统计数据">
    <head>
    <#-- 弹出框 -->
        <link rel="stylesheet" href="${basePath}/stylelib/remodal.css">
        <link rel="stylesheet" href="${basePath}/stylelib/remodal-default-theme.css">
        <link rel="stylesheet" href="${basePath}/stylelib/bootstrap/css/bootstrap-select.min.css">
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
                贷款申请统计
            </h1>
            <ol class="breadcrumb">
                <li><a href="${basePath}/account/index"><i class="fa fa-dashboard"></i>首页</a></li>
                <li><a href="${basePath}/statistic/list">贷款申请统计</a></li>
                <li class="active">贷款申请统计</li>
            </ol>
        </section>
        <!-- Main content -->
        <section class="content">
            <!-- 关键指标 -->
            <div class="box">
                <div class="box-body">
                    <table class="table table-bordered table-hover">
                        <thead>
                        <tr>
                            <th colspan="4">关键指标（总计）</td>
                        </tr>
                        <thead>

                        <tbody>
                        <tr>
                            <td align="center">提交申请人数</td>
                            <td align="center">通过人数</td>
                            <td align="center">未通过人数</td>
                            <td align="center">审核中人数</td>
                        </tr>
                        <tr>
                            <td align="center">${loanTotal.sumCount?string(',###')}</td>
                            <td align="center">${loanTotal.passCount?string(',###')}</td>
                            <td align="center">${loanTotal.disPassCount?string(',###')}</td>
                            <td align="center">${loanTotal.auditCount?string(',###')}</td>
                        </tr>
                        </tbody>

                    </table>
                </div>
                <!-- 趋势图 -->
                <div class="box">

                    <div class="box-header">
                    <#-- 查询 -->
                        <form class="form-inline" action="${basePath}/statistic/list" method="get" id="queryLoan">

                            <br/>
                            <div class="form-group" style="margin-left:1rem;">
                                <lable style="font-weight:bold;">关键指标详解</lable>
                            </div>
                            <br/>
                            <br/>
                            <div class="row search-from">
                                <div class="btn-group" role="group" style="margin-left:1rem;">
                                <#-- <div class="form-group" style="margin-left:2rem;">		 -->
                                    <input type="button" class="btn btn-default" id="seven" name="seven" onclick="getData();" style="width:50px;" value="7日">
                                <#-- </div> -->

                                <#-- <div class="form-group" style="margin-left:1rem;"> -->
                                    <input type="button" class="btn btn-default" id="month" name="month" onclick="getDataByDay();" style="width:50px;" value="月">
                                <#-- </div> -->

                                <#-- <div class="form-group" style="margin-left:1rem;"> -->
                                    <input type="button" class="btn btn-default" id="year" name="year" onclick="getDataByMonth();" style="width:50px;" value="年">
                                <#-- </div> -->
                                </div>

                                <input type="hidden" id="submitTimeFrom" name="submitTimeFrom" <#if lqb.submitTimeFrom??>value="${(lqb.submitTimeFrom!!?string('yyyy-MM-dd'))!}"</#if>>
                                <input type="hidden" id="submitTimeEnd" name="submitTimeEnd" <#if lqb.submitTimeEnd??> value="${(lqb.submitTimeEnd!!?string('yyyy-MM-dd'))!}"</#if>>

                                <div class="form-group" style="margin-left:1rem;">
                                    <label for="submitTimeRange" style="margin-left:1rem;">时间范围:</label>
                                    <input id="submitTimeRange" class="form-control" name="submitTimeRange" readonly="readonly" style="width:200px; margin-left:5px;">
                                </div>


                                <div id="fpIdDiv" class="form-group" style="margin-left:1rem;">
                                    <label>贷款产品: </label>&nbsp;
                                    <select class="selectpicker" data-width="10rem" name="fpId" id="fpId" data-dropup-auto="false" style="width:80px;">
                                        <option value="">全部</option>
                                        <#list productList as product>
                                            <option value="${product.fpId?string}" <#if lqb.fpId?? && "${lqb.fpId?string}" == "${product.fpId?string}" >selected="selected"</#if> >${product.fpName!}</option>
                                        </#list>
                                    </select>
                                </div>

                                <div class="form-group" style="margin-left:1rem;">
                                    <input type="button" class="btn btn-default" value="搜索" onclick="search()">
                                    <input type="hidden" name="queryAll" id="queryAll" value="${lqb.queryAll?string('true','false')}" >
                                </div>
                            </div>
                            </from>
                    </div>
                <#-- 趋势图 -->
                    <div>
                        <lable id="chartLable"><h5>&emsp;趋势图</h5></lable>
                        <!-- 为ECharts准备一个具备大小（宽高）的Dom -->
                        <div id="echarts" style="height: 400px"></div>
                    </div>
                </div>
                <!-- 详细数据 -->
                <div><h5>&emsp;详细数据<h5></div>
                <div align="right">
                    <@layout.security.authorize url="statistic/exportExcel">
                        <input type="button" class="btn btn-primary" value="导出Excel" onclick="exportInfo();" />&emsp;
                    </@layout.security.authorize>
                </div>
                <div id="messageList" class="box-body">
                        <table id="audit_list" class="table table-bordered table-hover">
                            <thead>
                            <tr>
                                <th>时间</th>
                                <th>提交申请人数</th>
                                <th>通过人数</th>
                                <th>未通过人数</th>
                                <th>审核中人数</th>
                            </tr>
                            </thead>
                            <tbody>
                                <#list tbData.list as audit>
                                <tr>
                                    <td align="center">
                                        ${audit.submitTime!!?string("yyyy-MM-dd")}
                                    </td>
                                    <td align="center">${audit.sumCount?string(',###')}</td>
                                    <td align="center">${audit.passCount?string(',###')}</td>
                                    <td align="center">${audit.disPassCount?string(',###')}</td>
                                    <td align="center">${audit.auditCount?string(',###')}</td>
                                </tr>
                                </#list>
                        </table>
                </div><!-- /.box-body -->
                <div id = "productpager" class="box-footer">
                    <#include "/common/pager_bar.ftl">
                </div>
            </div><!-- /.box -->

        <#-- 消息提示弹层 -->
            <div class="remodal" data-remodal-id="messageBox" role="dialog"
                 aria-labelledby="messageTitle" aria-describedby="messageBody">
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

    </body>
    <script src="${basePath}/jslib/remodal.js"></script>
    <script src="${basePath}/jslib/template.js"></script>
    <script src="${basePath}/jslib/bootstrap-select.min.js"></script>
    <!-- date-range-picker -->
    <script src="${basePath}/jslib/moment.min.js"></script>
    <script src="${basePath}/stylelib/adminlte/plugins/daterangepicker/daterangepicker.js"></script>
    <!-- bootstrap color picker -->
    <script src="${basePath}/stylelib/adminlte/plugins/colorpicker/bootstrap-colorpicker.min.js"></script>

    <#-- 商户贷款管理页面结束 -->
    </section>
    </div>
    <#-- Echarts  趋势图 -->
    <script src="${basePath}/jslib/echarts/echarts.common.min.js"></script>
        <script type="text/javascript">
            var dom = document.getElementById("echarts");
            var myChart = echarts.init(dom);
            option = {
                tooltip : {
                    trigger: 'axis'
                },
                legend: {
                    data:['提交申请人数','通过人数','未通过人数','审核中人数']
                },

                calculable : true,
                xAxis : [
                    {
                        type : 'category',
                        boundaryGap : false,
                        data : <#noescape>${opt.xAisData}</#noescape>
                    }],
                yAxis : [
                    {
                        type : 'value'
                    }],
                series : [
                    {
                        name:'提交申请人数',
                        type:'line',
                        data:${opt.seriesData[0]}
                    },
                    {
                        name:'通过人数',
                        type:'line',
                        data:${opt.seriesData[1]}
                    },
                    {
                        name:'未通过人数',
                        type:'line',
                        data:${opt.seriesData[2]}
                    },
                    {
                        name:'审核中人数',
                        type:'line',
                        data:${opt.seriesData[3]}
                    }
                ]
            };
            myChart.setOption(option);
        </script>

    <script>

        <#--添加时间 Date range picker -->
        $('#submitTimeFrom').daterangepicker({
            format: 'YYYY-MM-DD',
            singleDatePicker: true
        });
        $('#submitTimeEnd').daterangepicker({
            format: 'YYYY-MM-DD',
            singleDatePicker: true
        });
        $('input[name="submitTimeRange"]').daterangepicker({
            autoUpdateInput: true,
            timePicker: true,
            format: 'YYYY-MM-DD',
            ranges: {
                "今天": [
                    moment(),
                    moment()
                ],
                "昨天": [
                    moment().subtract(1,'days'),
                    moment().subtract(1,'days')
                ],
                "最近7天": [
                    moment().subtract(6,'days'),
                    moment()
                ],
                "最近1个月": [
                    moment().subtract(1,'months'),
                    moment()
                ],
                "最近半年": [
                    moment().subtract(6,'months'),
                    moment()
                ],
                "最近一年": [
                    moment().subtract(1,'years'),
                    moment()
                ]
            },
            // alwaysShowCalendars: true,
            locale: {
                customRangeLabel: '自定义',
                cancelLabel: 'Clear'
            }
            // }
            // , function (start, end, label) {
            // 	$('#submitTimeFrom').val(start.format('YYYY-MM-DD'));
            // 	$('#submitTimeEnd').val(end.format('YYYY-MM-DD'));
        });

        $('input[name="submitTimeRange"]').on('apply.daterangepicker', function(ev, picker) {
            $('#submitTimeFrom').val(picker.startDate.format('YYYY-MM-DD'));
            $('#submitTimeEnd').val(picker.endDate.format('YYYY-MM-DD'));
        });

        $('input[name="submitTimeRange"]').on('cancel.daterangepicker', function(ev, picker) {
            $('#submitTimeRange').val('');
            $('#submitTimeFrom').val('');
            $('#submitTimeEnd').val('');
        });
        $(function () {
            var from = $('#submitTimeFrom').val();
            var end = $('#submitTimeEnd').val();
            console.log(from);
            console.log('end');
            if(from == ''){
                $('#submitTimeRange').val('');
            } else {
                $('#submitTimeRange').data('daterangepicker').setStartDate(moment(from));
                // console.log(moment(from));
                $('#submitTimeRange').data('daterangepicker').setEndDate(moment(end));
                // $('#submitTimeRange').val(from+" - "+end);
            }
        });
        <#-- 点击时间段按钮查询数据 -->
        function getData(){
            var startDate = moment().subtract(6,'days');
            $('#submitTimeRange').data('daterangepicker').setStartDate(moment().subtract(6,'days'));
            $('#submitTimeRange').data('daterangepicker').setEndDate(moment());
            $('#submitTimeFrom').data('daterangepicker').setEndDate(moment().subtract(6,'days'));
            $('#submitTimeEnd').data('daterangepicker').setEndDate(moment());
            var formObject = document.getElementById("queryLoan");
            formObject.submit();
        };
        function getDataByMonth(){
            $('#submitTimeRange').data('daterangepicker').setStartDate(moment().subtract(1,'years'));
            $('#submitTimeRange').data('daterangepicker').setEndDate(moment());
            $('#submitTimeFrom').data('daterangepicker').setEndDate(moment().subtract(1,'years'));
            $('#submitTimeEnd').data('daterangepicker').setEndDate(moment());
            var formObject = document.getElementById("queryLoan");
            formObject.submit();
        }
        function getDataByDay(){
            $('#submitTimeRange').data('daterangepicker').setStartDate(moment().subtract(1,'months'));
            $('#submitTimeRange').data('daterangepicker').setEndDate(moment());
            $('#submitTimeFrom').data('daterangepicker').setEndDate(moment().subtract(1,'months'));
            $('#submitTimeEnd').data('daterangepicker').setEndDate(moment());
            var formObject = document.getElementById("queryLoan");
            formObject.submit();
        }
        <#-- Excel导出关键指标的详细数据 -->
        function exportInfo(){
            var formObject = document.getElementById("queryLoan");
            formObject.action = basePath + "statistic/exportExcel";
            formObject.submit();
            formObject.action = basePath + "statistic/list";
        }
        <#-- 搜索按钮触发事件 -->
        function search(){
            var formObject = document.getElementById("queryLoan");
            var startTime = $('#submitTimeFrom').val();
            var endTime = $('#submitTimeEnd').val();

            if(startTime=="" && endTime ==""){
                $("#queryAll").val(true);
            } else {
                $("#queryAll").val(false);
            }
            formObject.submit();
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
    </@layout.page>
</#escape>