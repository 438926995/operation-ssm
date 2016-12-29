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
                产品规则列表
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
                    <input type="button" value="添加规则" onclick="addRule(${product.fpId});">
                </div>
                <div class="box-body">
                    <table id="user_list" class="table table-bordered table-hover">
                        <thead>
                        <tr>
                            <th>规则名称</th>
                            <th>规则内容</th>
                            <th>规则类型</th>
                            <th>操作</th>
                        </tr>
                        </thead>
                        <tbody>
                            <#list product.ruleList as rule>
                            <tr>
                                <td style="text-align:left">${rule.ruleName!""}</td>
                                <td style="text-align:left">${rule.ruleContent!""}</td>
                                <td style="text-align:left">
                                    <#if rule.ruleType == 1>申请条件</#if>
                                    <#if rule.ruleType == 2>所需材料</#if>
                                </td>
                                <td style="text-align:left">
                                    <a href="${basePath}/product/ruleEditView/${rule.prId!}">编辑</a>
                                    <a href="javascript:deleteUserInfo('${rule.prId!}')">删除</a>
                                </td>
                            </tr>
                            </#list>
                        </tbody>
                    </table>
                </div><!-- /.box-body -->
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
        function deleteUserInfo(prId){
            if(confirm("确定要删除数据吗")){
                $.ajax({
                    type: "POST",
                    url: basePath + "product/delRule",
                    data: {
                        prId : prId
                    },
                    success: function(resultObj){
                        if(resultObj.isSuccess){
                            alertMessageBox("产品规则信息",resultObj.message,function(){
                                window.parent.location.reload();
                            });
                        }else{
                            alertMessageBox("产品规则信息",resultObj.message,function(){window.parent.location.reload();});
                        }
                    },
                    error:function(resultObj){
                        alertMessageBox("产品规则信息","出现异常，请稍后重试！");
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

        <#--添加时间 Date range picker -->
        $('#userCreateDateRange').daterangepicker({
        <#--  format: 'YYYY-MM-DD',
          separator: ' - '   -->
        });

        function goUserAdd(){
            window.location.href= basePath + "product/addView";
        }

        function addRule (fpId) {
            window.location.href= basePath + "product/ruleAddView/" + fpId;
        }
    </script>
    <#-- 用户页面结束 -->
    </section>
    </div>


    </@layout.page>
</#escape>