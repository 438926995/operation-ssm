<#import "/account/layout.ftl" as layout>
<#escape x as x?html>
    <@layout.page title="城市列表">
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
                城市列表
            </h1>
            <ol class="breadcrumb">
                <li><a href="${basePath}/account/index"><i class="fa fa-dashboard"></i>首页</a></li>
                <li><a href="${basePath}/city/list">城市信息查看</a></li>
                <li class="active">城市列表</li>
            </ol>
        </section>
        <!-- Main content -->
        <section class="content">
        <#-- 城市列表开始 -->
            <div class="box">
                <div class="box-body">
                    <table id="role_list" class="table table-bordered table-hover">
                        <thead>
                        <tr>
                            <th id="role_id">省份</th>
                            <th id="role_name">城市名</th>
                        </tr>
                        </thead>
                        <tbody>
                            <#list cityInfo as prov>
                            <tr>
                                <td width="8%"><a href="javascript:getCityInfo('${prov.cityID!!}')">${prov.cityName}</a>
                                </td>
                                <td>
                                    <#list prov.cityList as city>
                                        <#if city??>
                                            <a href="javascript:getCityInfo('${city.cityID!!}')">${city.cityName!!}</a>
                                        </#if>
                                    </#list>
                                </td>
                            </tr>
                            </#list>
                        </tbody>
                    </table>
                </div><!-- /.box-body -->

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
    <!-- date-range-picker -->
    <script src="${basePath}/jslib/moment.min.js"></script>
    <script src="${basePath}/stylelib/adminlte/plugins/daterangepicker/daterangepicker.js"></script>
    <!-- bootstrap color picker -->
    <script src="${basePath}/stylelib/adminlte/plugins/colorpicker/bootstrap-colorpicker.min.js"></script>
    <script src="${basePath}/jslib/template.js"></script>

    <#-- 城市页面结束 -->
    </section>
    </div>

    <script>
        <#-- 查看城市详情，弹出框 -->
        function getCityInfo(cityID) {
            $.ajax({
                type: "GET",
                url: basePath + "city/getCityInfo",
                data: {
                    cityID: cityID
                },
                success: function (resultObj) {
                    var messBody = template('chooseFinanceInfoForPurposeImport', resultObj);
                    alertMessageBox("城市信息", messBody);
                },
                error: function (resultObj) {
                    alertMessageBox("城市信息", "出现异常，请稍后重试！");
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
    <#-- 显示根据id查看城市信息-->
    <script id="chooseFinanceInfoForPurposeImport" type="text/html">
        <div class="box-body">
            <table class="table table-bordered table-hover">
                <tbody>
                <tr>
                    <td>金融机构</td>
                    <td style="text-align:left">{{foName}}</td>
                </tr>
                <tr>
                    <td>产品名称</td>
                    <td style="text-align:left">{{productName}}</td>
                </tr>
                </tbody>
            </table>
        </div>
    </script>
    </@layout.page>
</#escape>
