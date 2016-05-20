<#import "/account/layout.ftl" as layout>
<#escape x as x?html>
    <@layout.page title="贷款审核列表">
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
                贷款审核列表
            </h1>
            <ol class="breadcrumb">
                <li><a href="${basePath}/account/index"><i class="fa fa-dashboard"></i>首页</a></li>
                <li><a href="${basePath}/user/list">贷款管理</a></li>
                <li class="active">贷款审核列表</li>
            </ol>
        </section>
        <!-- Main content -->
        <section class="content">
        <#-- 用户列表开始 -->

            <div class="box">
                <div class="box-header">
                </div>
                <#-- 查询 -->
                    <form class="form-inline" action="${basePath}/loan/list" method="get" id="queryUser">

                            <div class="row search-from">

                                <input type="hidden" id="submitTimeFrom" name="submitTimeFrom" <#if lqb.submitTimeFrom??>value="${(lqb.submitTimeFrom!!?string('yyyy-MM-dd'))!}"</#if>>
                                <input type="hidden" id="submitTimeEnd" name="submitTimeEnd" <#if lqb.submitTimeEnd??> value="${(lqb.submitTimeEnd!!?string('yyyy-MM-dd'))!}"</#if>>

                                <div class="form-group" style="margin-left:1rem;">
                                    <label for="submitTimeRange" style="margin-left:1rem;">时间范围:</label>
                                    <input id="submitTimeRange" class="form-control" name="submitTimeRange" readonly="readonly" style="width:200px; margin-left:5px;">
                                </div>
                                <div class="form-group" style="margin-left:1rem;">
                                <label>贷款产品：</label>
                                <select name="fpId" class="selectpicker" data-width="10rem">
                                    <option value="">全部</option>
                                    <#list productList as pro>
                                        <option value="${pro.fpId}">${pro.fpName}</option>
                                    </#list>
                                </select>
                                </div>
                                <div class="form-group" style="margin-left:1rem;">
                                <label>审核状态：</label>
                                <select name="appStatus" id="appStatus" class="selectpicker" data-width="10rem">
                                    <option value="">全部</option>
                                    <option value="P" <#if lqb.appStatus?? && lqb.appStatus == 'P'>selected="selected"</#if> >审核中</option>
                                    <option value="D" <#if lqb.appStatus?? && lqb.appStatus == 'D'>selected="selected"</#if> >未通过</option>
                                    <option value="C" <#if lqb.appStatus?? && lqb.appStatus == 'C'>selected="selected"</#if> >通过</option>
                                </select>
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
                            <th>申请人</th>
                            <th>申请单号</th>
                            <th>申请金额</th>
                            <th>申请时间</th>
                            <th>申请产品</th>
                            <th>审核状态</th>
                            <th>操作</th>
                        </tr>
                        </thead>
                        <tbody>
                            <#list tbData.list as loan>
                            <tr>
                                <td style="text-align:left">${loan.realName!}</td>
                                <td style="text-align:left">${loan.docNo!}</td>
                                <td style="text-align:left">${loan.loanAmount!?string(',###')}元</td>
                                <td style="text-align:left">${loan.submitTime!?string('yyyy-MM-dd HH:mm:ss')}</td>
                                <td style="text-align:left;">${loan.fpName!}</td>
                                <td style="text-align:left">
                                    <#if loan.appStatus == 'P'>
                                        审核中
                                    <#elseif loan.appStatus == 'C'>
                                        通过
                                    <#else>
                                        未通过
                                    </#if>
                                </td>
                                <td style="text-align:left">
                                    <@layout.security.authorize url="product/edit">
                                        <a href="${basePath}/loan/detail/${loan.slId!}">申请详情</a>
                                    </@layout.security.authorize>
                                    <@layout.security.authorize url="product/deleteProduct">
                                        <a href="javascript:deleteUserInfo('${loan.slId!}')">删除</a>
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
        function deleteUserInfo(slId){
            if(confirm("确定要删除数据吗")){
                $.ajax({
                    type: "POST",
                    url: basePath + "loan/deleteLoan/" + slId,
                    data: {},
                    success: function(resultObj){
                        if(resultObj.isSuccess){
                            alertMessageBox("贷款信息",resultObj.message,function(){
                                window.parent.location.reload();
                            });
                        }else{
                            alertMessageBox("贷款信息",resultObj.message,function(){window.parent.location.reload();});
                        }
                    },
                    error:function(resultObj){
                        alertMessageBox("贷款信息","出现异常，请稍后重试！");
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

        function goUserAdd(){
            window.location.href= basePath + "product/addView";
        }
    </script>
    <#-- 用户页面结束 -->
    </section>
    </div>


    </@layout.page>
</#escape>