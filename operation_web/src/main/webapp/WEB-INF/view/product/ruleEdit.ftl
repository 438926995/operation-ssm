<#import "/account/layout.ftl" as layout>
<#escape x as x?html>
    <@layout.page title="融资产品规则编辑">
    <head>
    <#-- 弹出框 -->
        <link rel="stylesheet" href="${basePath}/stylelib/remodal.css">
        <link rel="stylesheet" href="${basePath}/stylelib/remodal-default-theme.css">
    <#-- Bootstrap 颜色选择器 -->
        <link rel="stylesheet" href="${basePath}/stylelib/adminlte/plugins/colorpicker/bootstrap-colorpicker.min.css">
    <#-- Bootstrap 日期选择器 -->
        <link rel="stylesheet" href="${basePath}/stylelib/adminlte/plugins/daterangepicker/daterangepicker-bs3.css">

    </head>
    <div class="content-wrapper">
    <#-- Content Header (Page header) -->
        <section class="content-header">
            <h1>
                融资产品规则编辑
            </h1>
            <ol class="breadcrumb">
                <li class="active"><a href="${basePath}/account/index"><i class="fa fa-dashboard"></i> 首页</a></li>
                <li><a href="${basePath}/product/list">融资产品管理</a></li>
                <li class="active">融资产品规则编辑</li>
            </ol>
        </section>
    <#-- Main content -->
        <section class="content">
        <#-- 融资产品添加页面 -->

            <div class="box box-primary">
                <div class="box-header with-border">
                    <h3 class="box-title">融资产品规则编辑</h3>
                </div><!-- /.box-header -->
                <!-- form start -->

                <form role="form" id="product" action="${basePath}/product/ruleEditSave" method="post" data-parsley-validate="" enctype="multipart/form-data">
                    <div class="box-body">
                        <div class="form-group id="fpNameDiv">
                        规则名称 &nbsp;
                        <input type="text" class="form-control" id="ruleName" name="ruleName" value="${productRule.ruleName!}" required data-parsley-trigger="change" data-parsley-length="[2, 100]" data-parsley-maxlength="100" >
                        </div>
                    <div class="form-group id="fpNameDiv">
                    规则内容 &nbsp;
                        <input type="text" class="form-control" id="ruleContent" name="ruleContent" value="${productRule.ruleContent!}" required data-parsley-trigger="change" data-parsley-length="[2, 100]" data-parsley-maxlength="100" >
                    </div>

            <input type="hidden" name="ruleType" id = "ruleType" value="${productRule.ruleType!}">
            <input type="hidden" name="prId" id="prId" value="${productRule.prId}">
    </div><!-- /.box-body -->
    <div class="box-footer">
        <button type="submit" class="btn btn-primary" >保存</button>
    </div>
    </form>
    </div><!-- /.box -->

    <#-- 融资产品添加页面结束 -->
    </section>
    </div>
    <script src="${basePath}/jslib/parsley/parsley.min.js"></script>
    <script src="${basePath}/jslib/parsley/parsley.js"></script>
    <script src="${basePath}/jslib/parsley/i18n/zh_cn.js"></script>
    <link rel="stylesheet" href="${basePath}/stylelib/parsley.css">
    <script src="${basePath}/jslib/remodal.js"></script>
    <script src="${basePath}/jslib/template.js"></script>
    <!-- date-range-picker -->
    <script src="${basePath}/jslib/moment.min.js"></script>
    <script src="${basePath}/stylelib/adminlte/plugins/daterangepicker/daterangepicker.js"></script>
    <!-- bootstrap color picker -->
    <script src="${basePath}/stylelib/adminlte/plugins/colorpicker/bootstrap-colorpicker.min.js"></script>
    <script>
        <#--添加时间 Date range picker -->
        $('#startDate').daterangepicker({
            format: 'YYYY-MM-DD',
            singleDatePicker: true
        });
        $('#endDate').daterangepicker({
            format: 'YYYY-MM-DD',
            singleDatePicker: true
        });

    </script>

    </@layout.page>
</#escape>
