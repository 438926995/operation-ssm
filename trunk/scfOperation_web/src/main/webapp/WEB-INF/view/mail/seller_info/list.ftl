<#import "/account/layout.ftl" as layout>
<#escape x as x?html>
    <@layout.page title="贷款管理">
    <head>
    <#-- 弹出框 -->
        <link rel="stylesheet" href="${basePath}/stylelib/remodal.css">
        <link rel="stylesheet" href="${basePath}/stylelib/remodal-default-theme.css">
        <link rel="stylesheet" href="${basePath}/stylelib/bootstrap/css/bootstrap-table.min.css">
        <link rel="stylesheet" href="${basePath}/stylelib/bootstrap/css/bootstrap-select.min.css">
    <#-- Bootstrap 颜色选择器 -->
        <link rel="stylesheet" href="${basePath}/stylelib/adminlte/plugins/colorpicker/bootstrap-colorpicker.min.css">
    <#-- Bootstrap 日期选择器 -->
        <link rel="stylesheet" href="${basePath}/stylelib/adminlte/plugins/daterangepicker/daterangepicker-bs3.css">
        <style>
            .table-with-checkbox tbody {
                vertical-align: middle;
            }
        </style>
    </head>
    <body>
    <div class="content-wrapper">
        <!-- Content Header (Page header) -->
        <section class="content-header">
            <h1>
                商户信息邮件管理
            </h1>
            <ol class="breadcrumb">
                <li><a href="${basePath}/account/index"><i class="fa fa-dashboard"></i>首页</a></li>
                <li><a href="${basePath}/mail/seller_info/list">邮件管理</a></li>
                <li class="active">商户信息邮件管理</li>
            </ol>
        </section>
        <!-- Main content -->
        <section class="content">
        <#-- 商户信息邮件管理 -->

            <div class="box">
                <div class="box-header">
                    <div class="form-inline">
                    <#-- 查询 -->
                        <div class="form-group">
                            <label for="selReceiver">接收方:</label>
                            <div class="input-group">
                                <select id="selReceiver" class="selectpicker" data-live-search="true" data-width="12rem"
                                        title="请选择接收方">
                                    <#list receivers as receiver>
                                        <option value="${receiver.foId}">${receiver.foName}</option>
                                    </#list>
                                </select>
								<span class="input-group-addon">
					                <i id="iRemoveSelReceiver" class="glyphicon glyphicon-remove fa fa-remove"
                                       style="cursor:pointer" title="清除选择"></i>
				                </span>
                            </div>
                        </div>
                        <div class="form-group" style="margin-left:1rem">
                            <label for="dtpRangeTime">时间范围:</label>
                            <div class="input-group">
						  	  	  <span class="input-group-addon">
				                  	<i class="glyphicon glyphicon-calendar fa fa-calendar"></i>
				                  </span>
                                <input type="text" class="form-control" style="width:180px" id='dtpRangeTime'/>
                            </div>
                        </div>
                        <div class="form-group" style="margin-left:1rem">
                            <label>状态:</label>
                            <div class="input-group">
                                <select id="selSendStatus" class="selectpicker" data-width="12rem" title="请选择发送状态">
                                    <option value="0">未发送</option>
                                    <option value="2">已发送</option>
                                    <option value="3">发送失败</option>
                                </select>
								<span class="input-group-addon">
						            <i id="iRemoveSelSendStatus" class="glyphicon glyphicon-remove fa fa-remove"
                                       style="cursor:pointer" title="清除选择"></i>
					            </span>
                            </div>
                        </div>
                        <button type="button" class="btn btn-default" style="margin-left:2rem"
                                id="mailSellerInfosSearch">搜索
                        </button>
                    </div>
                    <div class="form-inline" style="margin-top:2rem">
                        <@layout.security.authorize url="mail/seller_info/sendEmail">
                            <button type="button" class="btn btn-default" id="sendEmail">发送邮件</button>
                        </@layout.security.authorize>
                        <@layout.security.authorize url="mail/seller_info/setting">
                            <button type="button" class="btn btn-default" style="margin-left:2rem" id="sendSetting">
                                邮件设置
                            </button>
                        </@layout.security.authorize>
                    </div>
                </div>
                <div class="box-body">
                    <table id="tableMailSellerInfos"
                           class="table table-bordered table-hover table-with-checkbox"></table>
                </div>
            </div>

        </section>
    </div>
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

    <div class="remodal" data-remodal-id="settingSendSellerEmailMsgBox" role="dialog" aria-labelledby="messageTitle"
         aria-describedby="messageBody">
        <button data-remodal-action="close" class="remodal-close" aria-label="Close"></button>
        <div>
            <h2>邮箱设置</h2>
            <div class="form-inline">
                <label>发送模式：</label>
                <label>
                    <input type="radio" name="radioSendMode" value="0"
                           <#if sendMode="0">checked</#if>
                    >
                    手动发送
                </label>
                <label>
                    <input type="radio" name="radioSendMode" value="1"
                           <#if sendMode="1">checked</#if>
                    >
                    自动发送
                </label>
                <div>
                </div>
                <br>
                <button id="settingEmailSave" class="remodal-confirm">保存</button>
                <button data-remodal-action="cancel" class="remodal-cancel">取消</button>
            </div>
    </body>
    <script src="${basePath}/jslib/remodal.js"></script>
    <script src="${basePath}/jslib/template.js"></script>
    <script src="${basePath}/jslib/bootstrap-table.min.js"></script>
    <script src="${basePath}/jslib/bootstrap-table-zh-CN.min.js"></script>
    <script src="${basePath}/jslib/bootstrap-select.min.js"></script>
    <script src="${basePath}/jslib/jquery.dateFormat.min.js"></script>

    <!-- date-range-picker -->
    <script src="${basePath}/jslib/moment.min.js"></script>
    <script src="${basePath}/stylelib/adminlte/plugins/daterangepicker/daterangepicker.js"></script>
    <!-- bootstrap color picker -->
    <script src="${basePath}/stylelib/adminlte/plugins/colorpicker/bootstrap-colorpicker.min.js"></script>

    <script>
        $(function () {
        <#--添加时间 Date range picker -->
            $('#dtpRangeTime').daterangepicker({
                opens: "center",
                format: 'YYYY-MM-DD'
            });

            $('#iRemoveSelReceiver').click(function () {
                $('#selReceiver').selectpicker('deselectAll');
            });

            $('#iRemoveSelSendStatus').click(function () {
                $('#selSendStatus').selectpicker('deselectAll');
            });

            var mail_seller_infos_page_num = 0;
            var limit = 10;
            $('#tableMailSellerInfos').bootstrapTable({
                columns: [{
                    title: "选择",
                    checkbox: true
                }, {
                    title: '序号',
                    formatter: function (value, row, index) {
                        return (index + 1) + mail_seller_infos_page_num * limit;
                    },
                    align: "center",
                    width: "30px"
                }, {
                    field: 'user_email',
                    title: '接收方邮箱',
                    width: "150px"
                }, {
                    field: 'fo_name',
                    title: '接收方机构',
                    width: "120px"
                }, {
                    field: "seller_name",
                    title: "发送餐厅名",
                    width: "150px"
                }, {
                    field: "napos_oid",
                    title: "商户id（NaposOid）",
                    width: "120px"
                }, {
                    field: 'created_at',
                    title: '发送时间',
                    formatter: function (value, row, index) {
                        return $.format.date(value, "yyyy-MM-dd HH:mm:ss");
                    },
                    width: "150px"
                }, {
                    field: 'mail_send_status',
                    title: '发送状态',
                    formatter: function (value, row, index) {
                        if (value) {
                            switch (value) {
                                case 0:
                                    return "未发送";
                                case 1:
                                    return "正在发送";
                                case 2:
                                    return "已发送";
                                case 3:
                                    return "发送失败";
                            }
                        } else {
                            return "未发送";
                        }
                    }
                }],
                method: 'POST',
                url: "${basePath}/mail/seller_info/list",
                contentType: 'application/x-www-form-urlencoded; charset=UTF-8',
                responseHandler: function (res) {
                    var data = {
                        total: 0,
                        rows: []
                    };
                    if (res.isSuccess) {
                        data.total = res.datas.total;
                        data.rows = res.datas.rows;
                    }
                    return data;
                },
                queryParams: function (params) {
                    mail_seller_infos_page_num = params.offset / params.limit;
                    var receiverId = $('#selReceiver').selectpicker('val');
                    receiverId && (params["foId"] = receiverId);
                    var sendStatus = $('#selSendStatus').selectpicker('val');
                    sendStatus && (params["sendStatus"] = sendStatus)
                    var rangeTime = $('#dtpRangeTime').val();
                    if (rangeTime) {
                        var timeArr = rangeTime.split(" - ")
                        if (timeArr.length == 2) {
                            params["queryStartDate"] = timeArr[0] + " 00:00:00";
                            params["queryEndDate"] = timeArr[1] + " 23:59:59";
                        }
                    }
                    // 添加分页参数
                    params["pageSize"] = params.limit;
                    params["currentPage"] = params.offset / params.limit;
                    return params;
                },
                height: 430,
                pageNumber: 1,
                pageSize: limit,
                pagination: true,
                sidePagination: "server",
                paginationFirstText: "首页",
                paginationPreText: "上一页",
                paginationNextText: "下一页",
                paginationLastText: "末页",
                select: true,
                clickToSelect: true,
                onLoadError: function (status) {
                    console.log("onLoadError:" + status);
                }
            });
        })

        $("#mailSellerInfosSearch").click(function () {
            $('#tableMailSellerInfos').bootstrapTable('refresh');
        })

        //发送邮件
        $("#sendEmail").click(function () {

            var rows = $('#tableMailSellerInfos').bootstrapTable('getSelections');
            if (rows.length == 0) {
                alertMessageBox("发送商户信息邮件", "至少选择一条邮件数据进行发送", null);
                return;
            }
            var ids = [];
            for (var i = 0; i < rows.length; i++) {
                ids.push(rows[i].sim_id);
            }


            $.ajax({
                type: "POST",
                url: basePath + "mail/seller_info/sendEmail",
                data: {
                    mailSellerInfoIds: ids
                },
                dataType: "json",
                success: function (data) {
                    if (data.isSuccess) {
                        alertMessageBox("商户邮件请求发送邮件操作", data.message);
                    } else {
                        alertMessageBox("商户邮件请求发送邮件操作", "操作异常，请稍后重试或联系管理员");
                    }
                },
                error: function () {
                    // alertMessageBox("商户邮件请求发送邮件操作", "出现异常，请稍后重试！",function(){window.parent.location.reload();});
                }
            });
        });

        var settingMailBox = null;

        //邮件设置
        $("#sendSetting").click(function () {
            settingMailBox = $('[data-remodal-id=settingSendSellerEmailMsgBox]').remodal();
            settingMailBox.open();
        });

        // 设置邮件保存
        $("#settingEmailSave").click(function () {
            var sendMode = "0";
            var radioArr = $("input[name='radioSendMode']");
            for (var i = 0; i < radioArr.length; i++) {
                if (radioArr[i].checked) {
                    sendMode = radioArr[i].value;
                    break;
                }
            }
            $.ajax({
                type: "POST",
                url: basePath + "mail/seller_info/setting",
                data: {
                    sendMode: sendMode
                },
                dataType: "json",
                success: function (result) {
                    if (settingMailBox.state == "opening") {
                        settingMailBox.close();
                    }
                    if (result.isSuccess) {
                        alertMessageBox("商户邮件发送设置", "操作成功");
                    } else {
                        alertMessageBox("商户邮件发送设置", result.message);
                    }
                },
                error: function (resultObj) {
                    if (settingMailBox.state == "opening") {
                        settingMailBox.close();
                    }
                    alertMessageBox("商户邮件发送设置", "出现异常，请稍后重试！", function () {
                        window.parent.location.reload();
                    });
                }
            });
        });

        function alertMessageBox(messTitle, messBody, callback) {
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

    </script>



    </@layout.page>
</#escape>
