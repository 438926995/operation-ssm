<#import "/account/layout.ftl" as layout>
<#escape x as x?html>
    <@layout.page title="融资产品添加">
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
                融资产品添加
            </h1>
            <ol class="breadcrumb">
                <li class="active"><a href="${basePath}/account/index"><i class="fa fa-dashboard"></i> 首页</a></li>
                <li><a href="${basePath}/product/list">融资产品管理</a></li>
                <li class="active">融资产品添加</li>
            </ol>
        </section>
    <#-- Main content -->
        <section class="content">
        <#-- 融资产品添加页面 -->

            <div class="box box-primary">
                <div class="box-header with-border">
                    <h3 class="box-title">融资产品添加</h3>
                </div><!-- /.box-header -->
                <!-- form start -->

                <form role="form" id="product" action="${basePath}/product/addSave" method="post" data-parsley-validate="" enctype="multipart/form-data">
                    <div class="box-body">
                        <#if pab??>
                            <@spring.bind "pab.fpName" />
                        </#if>
                        <div class="form-group <@spring.errorStyle /> " id="fpNameDiv">
                            产品名称 &nbsp;
                            <label for="fpName" id="fpNameLabel">
                                <#if pab??>
					    <@spring.showErrors "<br>"/>
					</#if>
                            </label>
                            <input type="text" class="form-control" id="fpName" name="fpName" value="${(pab.fpName)!}" required data-parsley-trigger="change" data-parsley-length="[2, 100]" data-parsley-maxlength="100" >
                        </div>

                        <#if pab??>
                            <@spring.bind "pab.minLoanAmount" />
                        </#if>
                        <#if pab??>
                            <@spring.bind "pab.maxLoanAmount" />
                        </#if>
                        <div class="form-group <@spring.errorStyle /> " id="loanAmountDiv">
                            额度范围&nbsp;
                            <label for="minLoanAmount" id="minLoanAmountLabel">
                                <#if pab??>
					    <@spring.showErrors "<br>"/>
					</#if>
                            </label>
                            <input type="number" class="form-control" id ="minLoanAmount" name="minLoanAmount" value="${(pab.minLoanAmount)!}"
                                   required data-parsley-trigger="change" data-parsley-type="integer" data-parsley-min="1">
                        <#-- <select name="minLoanAmount" id="minLoanAmount" required >
                          <option value="">请选择</option>
                          <option value="1" <#if pab?? && "${pab.minLoanAmount}"=="1">selected="selected"</#if> >1万</option>
                            <option value="3" <#if pab?? && "${pab.minLoanAmount}"=="3">selected="selected"</#if> >3万</option>
                            <option value="5" <#if pab?? && "${pab.minLoanAmount}"=="5">selected="selected"</#if> >5万</option>
                            <option value="10" <#if pab?? && "${pab.minLoanAmount}"=="10">selected="selected"</#if> >10万</option>
                            <option value="30" <#if pab?? && "${pab.minLoanAmount}"=="30">selected="selected"</#if> >30万</option>
                            <option value="50" <#if pab?? && "${pab.minLoanAmount}"=="50">selected="selected"</#if> >50万</option>
                            <option value="100" <#if pab?? && "${pab.minLoanAmount}"=="100">selected="selected"</#if> >100万</option>
                            <option value="150" <#if pab?? && "${pab.minLoanAmount}"=="150">selected="selected"</#if> >150万</option>
                            <option value="200" <#if pab?? && "${pab.minLoanAmount}"=="200">selected="selected"</#if> >200万</option>
                      </select> -->
                            ~
                            <label for="maxLoanAmount" id="maxLoanAmountLabel">
                                <#if pab??>
					    <@spring.showErrors "<br>"/>
					</#if>
                            </label>
                            <input type="number" class="form-control" id ="maxLoanAmount" name="maxLoanAmount" value="${(pab.maxLoanAmount)!}"
                                   required data-parsley-trigger="change" data-parsley-type="integer" data-parsley-min="1" >
                        <#-- <select name="maxLoanAmount" id="maxLoanAmount" required >
                            <option value="">请选择</option>
                            <option value="1" <#if pab?? && "${pab.maxLoanAmount}"=="1">selected="selected"</#if> >1万</option>
                              <option value="3" <#if pab?? && "${pab.maxLoanAmount}"=="3">selected="selected"</#if> >3万</option>
                              <option value="5" <#if pab?? && "${pab.maxLoanAmount}"=="5">selected="selected"</#if> >5万</option>
                              <option value="10" <#if pab?? && "${pab.maxLoanAmount}"=="10">selected="selected"</#if> >10万</option>
                              <option value="30" <#if pab?? && "${pab.maxLoanAmount}"=="30">selected="selected"</#if> >30万</option>
                              <option value="50" <#if pab?? && "${pab.maxLoanAmount}"=="50">selected="selected"</#if> >50万</option>
                              <option value="100" <#if pab?? && "${pab.maxLoanAmount}"=="100">selected="selected"</#if> >100万</option>
                              <option value="150" <#if pab?? && "${pab.maxLoanAmount}"=="150">selected="selected"</#if> >150万</option>
                              <option value="200" <#if pab?? && "${pab.maxLoanAmount}"=="200">selected="selected"</#if> >200万</option>
                        </select> -->
                        </div>


                        <#if pab??>
                            <@spring.bind "pab.payLimit" />

                        </#if>
                        <div class="form-group <@spring.errorStyle /> " id="foUrlDiv">
                            支持还款期限&nbsp;
                            <label for="payLimit" id="payLimitLabel">
                                <#if pab??>
					    <@spring.showErrors "<br>"/>
					</#if>
                            </label>
                            <select name="payLimit" id="payLimit" required>
                                <option value="">请选择</option>
                                <option value="1" <#if pab?? && "${pab.payLimit}"=="1">selected="selected"</#if> >1个月</option>
                                <option value="3" <#if pab?? && "${pab.payLimit}"=="3">selected="selected"</#if> >3个月</option>
                                <option value="6" <#if pab?? && "${pab.payLimit}"=="6">selected="selected"</#if> >6个月</option>
                                <option value="12" <#if pab?? && "${pab.payLimit}"=="12">selected="selected"</#if> >12个月</option>
                                <option value="24" <#if pab?? && "${pab.payLimit}"=="24">selected="selected"</#if> >24个月</option>
                                <option value="36" <#if pab?? && "${pab.payLimit}"=="36">selected="selected"</#if> >36个月</option>
                            </select>
                            <#--~-->
                            <#--<select name="payLimitTo" id="payLimitTo" required>-->
                                <#--<option value="">请选择</option>-->
                                <#--<option value="1" <#if pab?? && "${pab.payLimitTo}"=="1">selected="selected"</#if> >1个月</option>-->
                                <#--<option value="3" <#if pab?? && "${pab.payLimitTo}"=="3">selected="selected"</#if> >3个月</option>-->
                                <#--<option value="6" <#if pab?? && "${pab.payLimitTo}"=="6">selected="selected"</#if> >6个月</option>-->
                                <#--<option value="12" <#if pab?? && "${pab.payLimitTo}"=="12">selected="selected"</#if> >12个月</option>-->
                                <#--<option value="24" <#if pab?? && "${pab.payLimitTo}"=="24">selected="selected"</#if> >24个月</option>-->
                                <#--<option value="36" <#if pab?? && "${pab.payLimitTo}"=="36">selected="selected"</#if> >36个月</option>-->
                            <#--</select>-->
                        </div>

                        <#if pab??>
                            <@spring.bind "pab.minRaitRatio" />
                            <@spring.bind "pab.maxRaitRatio" />
                        </#if>
                        <div class="form-group <@spring.errorStyle /> " id="raitRatioDiv">
                            利率&emsp;<span style="color: mediumvioletred">(单位: %/月)</span> &nbsp;
                            <label for="raitRatio" id="raitRatioLabel">
                                <#if pab??>
					    <@spring.showErrors "<br>"/>
					</#if>
                            </label>
                            <input type="text" class="form-control" id="minRaitRatio" name="minRaitRatio" value="${(pab.minRaitRatio)!}" required data-parsley-trigger="change" data-parsley-type="number" data-parsley-maxlength="4">
                        </div>

                        <div class="form-group">
                            图片url&nbsp;
                            <input type="text" class="form-control" name="imageUrl">
                        </div>

                        <#if pab??>
                            <@spring.bind "pab.otherDesc" />
                        </#if>
                        <div class="form-group <@spring.errorStyle /> " id="otherDescDiv">
                            其他说明 &nbsp;
                            <label for="otherDesc" id="otherDescLabel">
                                <#if pab??>
					    <@spring.showErrors "<br>"/>
					</#if>
                            </label>
                            <input type="text" class="form-control" id="otherDesc" name="otherDesc" value="${(pab.otherDesc)!}" data-parsley-trigger="change" data-parsley-minlength="2"  data-parsley-maxlength="500" >
                        </div>

                        <input type="hidden" name="productStatus" id = "productStatus" value="${(pab.productStatus)!}">
                    </div><!-- /.box-body -->
                    <div class="box-footer">
                        <button type="submit" class="btn btn-primary" id="saveBtn" onclick="javascript:saveProductInfo(0);" >确认</button>
                        <button type="submit" class="btn btn-primary" id="onlineBtn" onclick="javascript:saveProductInfo(1);" >上线</button>
                        <button type="submit" class="btn btn-primary" id="offlineBtn" onclick="javascript:saveProductInfo(2);" >下线</button>
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

        function saveProductInfo(productStatus){
            $('#productStatus').val(productStatus);
        }

    </script>

    </@layout.page>
</#escape>
