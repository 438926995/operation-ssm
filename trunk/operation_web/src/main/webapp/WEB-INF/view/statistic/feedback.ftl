<#import "/account/layout.ftl" as layout>
<#escape x as x?html>
    <@layout.page title="用户反馈列表">
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
                用户反馈列表
            </h1>
            <ol class="breadcrumb">
                <li><a href="${basePath}/account/index"><i class="fa fa-dashboard"></i>首页</a></li>
                <li><a href="${basePath}/statistic/getUserFeedbackData">用户反馈列表</a></li>
                <li class="active">用户反馈列表</li>
            </ol>
        </section>
        <!-- Main content -->
        <section class="content">
        <#-- 用户列表开始 -->

            <div class="box">
                <div class="box-header">
                </div>
            <#-- 查询 -->
                <form class="form-inline" action="${basePath}/statistic/getUserFeedbackData" method="get" id="queryUser">

                    <div class="row search-from">

                        <input type="hidden" id="submitTimeFrom" name="submitTimeFrom" <#if fqb.submitTimeFrom??>value="${(fqb.submitTimeFrom!!?string('yyyy-MM-dd'))!}"</#if>>
                        <input type="hidden" id="submitTimeEnd" name="submitTimeEnd" <#if fqb.submitTimeEnd??> value="${(fqb.submitTimeEnd!!?string('yyyy-MM-dd'))!}"</#if>>

                        <div class="form-group" style="margin-left:1rem;">
                            <label for="submitTimeRange" style="margin-left:1rem;">时间范围:</label>
                            <input id="submitTimeRange" class="form-control" name="submitTimeRange" readonly="readonly" style="width:200px; margin-left:5px;">
                        </div>


                        <div class="form-group" style="margin-left:1rem;">
                            <input type="submit" value="搜索" class="btn btn-default" >
                        </div>
                    </div>
                </form>


                <div class="box-body">
                    <table id="user_list" class="table table-bordered table-hover">
                        <thead>
                        <tr>
                            <th>反馈人</th>
                            <th>反馈内容</th>
                            <th>反馈时间</th>
                            <th>回复人</th>
                            <th>回复内容</th>
                            <th>回复时间</th>
                        </tr>
                        </thead>
                        <tbody>
                            <#list tbData.list as feedback>
                            <tr>
                                <td style="text-align:left">${feedback.userName!}</td>
                                <td style="text-align:left">${feedback.feedbackContent!}</td>
                                <td style="text-align:left">${feedback.feedbackTime!?string('yyyy-MM-dd HH:mm:ss')}</td>
                                <td style="text-align:left">${feedback.replyUserName!}</td>
                                <td style="text-align:left;">${feedback.replyContent!}</td>
                                <td style="text-align:left">
                                    <#if feedback.replyTime??>${feedback.replyTime!?string('yyyy-MM-dd HH:mm:ss')}
                                    <#else>
                                        <a  href="javascript:void(0);" onclick="replyUserFeedbak('${feedback.id!}');">回复</a>
                                    </#if>
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
        function saveReply(id){
            var replyContent = $('#replyContent').val();
            $.ajax({
                type: "POST",
                url: basePath + "statistic/replyFeedback",
                data: {
                    id: id,
                    replyContent: replyContent
                },
                success: function(resultObj){
                    if(resultObj.isSuccess){
                        alertMessageBox("回复反馈",resultObj.message, function(){
                            window.location.reload();
                        });
                    }else{
                        alertMessageBox("回复反馈",resultObj.failReason,function(){window.location.reload();});
                    }
                },
                error:function(resultObj){
                    alertMessageBox("回复反馈","出现异常，请稍后重试！");
                }
            });
        }
        <#-- 查看用户详情，弹出框 -->
        function replyUserFeedbak(id){
            var messBody = template('replyInfo', {id: id});
            alertMessageBox("", messBody);
        }

        function alertMessageBox(messTitle,messBody,callback){
            $("#messageTitle").html(messTitle);
            $("#messageBody").html(messBody);
            var messBox = $('[data-remodal-id=messageBox]').remodal();
            messBox.open();
            $(document).on('closed', '.remodal', function (e) {
                if (typeof callback == "function") {
                    callback();
                }
            });
        }

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
    </script>

    <script id="replyInfo" type="text/html">
        <div class="form-group">
            <textarea id="replyContent" class="form-control"></textarea>
        </div>
        <div class="form-group">
            <input type="button" class="btn btn-success" value="回复" onclick="saveReply({{id}})">
        </div>
    </script>
    <#-- 用户页面结束 -->
    </section>
    </div>


    </@layout.page>
</#escape>
