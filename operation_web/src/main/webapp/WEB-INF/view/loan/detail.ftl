<#import "/account/layout.ftl" as layout>
<#escape x as x?html>
    <@layout.page title="贷款详情">
    <head>
    <#-- 弹出框 -->
        <link rel="stylesheet" href="${basePath}/stylelib/remodal.css">
        <link rel="stylesheet" href="${basePath}/stylelib/remodal-default-theme.css">
        <link rel="stylesheet" href="${basePath}/stylelib/bootstrap/css/bootstrap-select.min.css">
    <#-- Bootstrap 颜色选择器 -->
        <link rel="stylesheet"
              href="${basePath}/stylelib/adminlte/plugins/colorpicker/bootstrap-colorpicker.min.css">
    <#-- Bootstrap 日期选择器 -->
        <link rel="stylesheet"
              href="${basePath}/stylelib/adminlte/plugins/daterangepicker/daterangepicker-bs3.css">
        <style type="text/css">
            .count-position{
                position: absolute;
            }
            .fixed-table-header{
                display: none !important;
            }
            .fixed-table-body{
                margin-top: 50px;
                margin-bottom: -50px;
            }
        </style>
    </head>
    <body>
    <div class="content-wrapper">
    <#-- Content Header (Page header) -->
        <section class="content-header">
            <h1>
                贷款详情
            </h1>
            <ol class="breadcrumb">
                <li class="active"><a href="${basePath}/account/index"><i
                        class="fa fa-dashboard"></i> 首页</a></li>
                <li><a href="${basePath}/loan/list">贷款管理</a></li>
                <li class="active">贷款详情</li>
            </ol>
        </section>
    <#-- Main content -->
        <section class="content">
        <#-- 贷款详情页面 -->

            <div class="box box-primary">
                <div class="box-header with-border">
                    <h3 class="box-title">贷款详情</h3>
                </div><!-- /.box-header -->
                <!-- form start -->

                    <div class="box-body">
                        <div class="panel panel-default">
                            <div class="panel-heading">
                                <label>个人信息</label>
                            </div>
                            <div class="panel-body">
                                <table style="width:100%;">
                                    <tr>
                                        <td style="width:25%;"><label>贷款金额:</label>&emsp;
                                        ${loan.loanAmount!?string(',###')}元</td>
                                        <td style="width:25%;"><label>申请人姓名:</label>&emsp;
                                        ${loan.realName!""}</td>
                                        <td style="width:25%;"><label>性别:</label>&emsp;<#if loan.userSex??>
                                            <#if loan.userSex == 1>男<#else>女</#if>
                                        </#if></td>
                                        </tr>
                                    <tr>
                                        <td style="width:25%;"><label>年龄:</label>&emsp;${loan.userAge!}岁</td>
                                        <td><label>身份证号:</label>&emsp;${loan.userSid!}</td>
                                        <td><label>电话号码:</label>&emsp;${loan.mobilePhone!}</td>
                                    </tr>
                                    <tr>
                                        <td><label>申请单号:</label>&emsp;${loan.docNo!}</td>
                                        <td><label>申请产品:</label>&emsp;${loan.fpName!}</td>
                                        <td><label>申请时间:</label>&emsp;<#if loan.submitTime??>${loan.submitTime!!?string('yyyy-MM-dd HH:mm:ss')}</#if></td>
                                    </tr>
                                    <tr>
                                        <td><label>家庭地址:</label>&emsp;${loan.userAddr!}</td>
                                    </tr>
                                </table>
                            </div>
                        </div>

                        <div class="panel panel-default">
                            <div class="panel-heading">
                                <label>贷款产品信息</label>
                            </div>
                            <div class="panel-body">
                                <table style="width:100%;">
                                    <tr>
                                        <td style="width:25%; align:right;"><label>产品名称:</label>&emsp;
                                        ${loan.fpName!}</td>
                                        <td style="width:25%;"><label>贷款金额范围:</label>&emsp;
                                        ${loan.minLoanAmount!?string(',###')}元 ~ ${loan.maxLoanAmount!?string(',###')}元
                                        </td>
                                    </tr>
                                    <tr>
                                        <td style="width:25%;"><label>支持还款期限:</label>&emsp;${loan.payLimit!}个月</td>
                                        <td style="width:25%;"><label>利率:</label>&emsp;
                                        ${loan.minRaitRaito!}%/月</td>
                                    </tr>
                                </table>
                            </div>
                        </div>

                        <div class="panel panel-default">
                            <div class="panel-heading">
                                <label>产品规则</label>
                            </div>
                            <div class="panel-body">
                                <table style="width:100%;">
                                    <#list product.ruleList as rule>
                                        <tr>
                                            <td><label>${rule.ruleName}:</label>&emsp;${rule.ruleContent}</td>
                                        </tr>
                                    </#list>
                                </table>
                            </div>
                        </div>

                 <form role="form" id="loanDetail" action="" method="post" data-parsley-validate="">
                     <div class="panel panel-primary">
                         <div class="panel-heading">
                             <label><#if loan.appStatus == 'P'>审核贷款<#else>审批记录</#if></label>
                         </div>
                         <div class="panel-body">
                         <#if loan.appStatus == 'P'>
                             <label>审批意见</label>
                             <textarea class="form-control" name="remark" id="remark" required
                                    data-parsley-trigger="change" data-parsley-maxlength="100"></textarea>
                             <input type="hidden" name="slId" id="slId" value="${loan.slId!}">
                             <div class="box-footer">
                                <button type="button" class="btn btn-success" onclick="approve(1)">通过</button>
                                <button type="button" class="btn btn-danger" onclick="approve(0)">不通过</button>
                             </div>
                         <#else>
                            <table style="width:100%">
                                <thead
                                <tr>
                                    <th>审批人</th>
                                    <th>审批意见</th>
                                    <th>审批结果</th>
                                    <th>审批时间</th>
                                </tr>
                                </thead>
                                <tbody>
                                <tr>
                                    <td>${loan.apvUserName!}</td>
                                    <td>${loan.apvRemark!}</td>
                                    <td><#if loan.appStatus == 'C'>通过<#else>未通过</#if></td>
                                    <td>${loan.apvTime!?string('yyyy-MM-dd HH:mm:ss')}</td>
                                </tr>
                                </tbody>
                            </table>
                         </#if>
                        </div>
                     </div>
                </form>
            </div><!-- /.box -->

        <#-- 贷款详情页面结束 -->
        </section>
    </div>
    </body>
    <script src="${basePath}/jslib/parsley/parsley.min.js"></script>
    <script src="${basePath}/jslib/parsley/i18n/zh_cn.js"></script>
    <script src="${basePath}/jslib/remodal.js"></script>
    <script src="${basePath}/jslib/template.js"></script>
    <script src="${basePath}/jslib/bootstrap-table.min.js"></script>
    <script src="${basePath}/jslib/bootstrap-table-zh-CN.min.js"></script>
    <script src="${basePath}/jslib/bootstrap-select.min.js"></script>
    <link rel="stylesheet" href="${basePath}/stylelib/parsley.css">

    <script>
        var messBoxLoad;
        function approve(approveFlag){
            $.ajax({
                type: "POST",
                url: basePath + "loan/approve",
                data: {
                    approveFlag : approveFlag,
                    slId : $("#slId").val(),
                    remark : $.trim($("#remark").val())
                },
                success: function(resultObj){
                    if(resultObj.isSuccess){
                        alertMessageBox("贷款审核",resultObj.message,function(){
                            window.location.href="${basePath}/loan/list";
                        });
                    }else{
                        alertMessageBox("贷款审核",resultObj.message);
                    }
                },
                error:function(resultObj){
                    alertMessageBox("贷款审核","出现异常，请稍后重试！");
                }
            });
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



    <#-- 消息提示弹层 -->
    <div class="remodal" data-remodal-id="messageBox" role="dialog" aria-labelledby="messageTitle"
         aria-describedby="messageBody">
        <button data-remodal-action="close" class="remodal-close" aria-label="Close"></button>
        <div>
            <h2 id="messageTitle"></h2>
            <div id="messageBody">
            </div>
        </div>
        <br>
        <button data-remodal-action="cancel" class="remodal-cancel">关闭</button>
    </div>

    </@layout.page>
</#escape>
