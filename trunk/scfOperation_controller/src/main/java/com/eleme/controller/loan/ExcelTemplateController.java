package com.eleme.controller.loan;

import com.eleme.bean.JSONMessage;
import com.eleme.bean.SessionBean;
import com.eleme.constants.ExportConstants;
import com.eleme.controller.BaseController;
import com.eleme.domain.mart.export.ExportExlColumnTypeBean;
import com.eleme.domain.ops.file.AppExcelTemplateFormVo;
import com.eleme.domain.ops.file.AppExcelTemplateVo;
import com.eleme.domain.ops.file.TSysExcelTemplateRelation;
import com.eleme.service.loan.IExcelTemplateService;
import com.eleme.util.CommonUtil;
import me.ele.arch.etrace.common.util.JSONUtil;
import me.ele.elog.Log;
import me.ele.elog.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * excel导出模板管理Controller
 * Created by sunwei on 16/1/18.
 */
@Controller
@RequestMapping(value = "/loan/exceltemplate")
public class ExcelTemplateController extends BaseController {

  private final Log elog = LogFactory.getLog(ExcelTemplateController.class);

  @Inject
  private IExcelTemplateService excelTemplateService;

  /**
   * 获取excel导出模板数据
   *
   * @param request
   * @return
   */
  @RequestMapping(value = "/list")
  @ResponseBody
  public JSONMessage getExcelTemplateData(HttpServletRequest request) {
    SessionBean session = getSessionBean(request);
    elog.info("用户{}获取excel导出模板数据", session.getUserName());
    // 初始化
    boolean isSuccess = true;
    String msg = "操作成功12";
    List<AppExcelTemplateVo> excelTemplates = null;
    try {
      excelTemplates = excelTemplateService.getAppExcelTemplates();
    } catch (Exception ex) {
      elog.error(CommonUtil.getErrorMessage(ex));
      isSuccess = false;
      msg = "系统错误";
    }
    JSONMessage json = new JSONMessage();
    json.setMessage(msg);
    json.setIsSuccess(isSuccess);
    json.setDatas(excelTemplates);
    return json;
  }

  /**
   * 保存excel导出模板
   *
   * @param request
   * @param excelTemplate
   * @return
   */
  @RequestMapping(value = "/save")
  @ResponseBody
  public JSONMessage saveExcelTemplate(HttpServletRequest request, AppExcelTemplateFormVo excelTemplate) {
    SessionBean sessionBean = getSessionBean(request);
    elog.info("用户{}保存excel导出模板", sessionBean.getUserName());
    // 初始化
    boolean isSuccess = true;
    String message = "操作成功";
    try {
      elog.info(excelTemplate.getExcelTemplateName());
      // 将 excelRelations json str解析 放入 excelTemplateRelations
      excelTemplate.setExcelTemplateRelations(JSONUtil.toArray(excelTemplate.getExcelTemplateRelationsJsonStr(), TSysExcelTemplateRelation.class));
      excelTemplateService.saveAppExcelTemplate(excelTemplate);
    } catch (Exception ex) {
      elog.error(CommonUtil.getErrorMessage(ex));
      isSuccess = false;
      message = "系统异常";
    }
    JSONMessage json = new JSONMessage();
    json.setMessage(message);
    json.setIsSuccess(isSuccess);
    return json;
  }

  /**
   * 删除模板
   *
   * @param request
   * @param tempId
   * @return
   */
  @RequestMapping("/remove/{tempId:[\\d]+}")
  @ResponseBody
  public JSONMessage removeExcelTemplate(HttpServletRequest request, @PathVariable("tempId") Integer tempId) {
    SessionBean sessionBean = getSessionBean(request);
    elog.info("用户{}删除excel导出模板", sessionBean.getUserName());
    boolean isSuccess = true;
    String message = "操作成功";
    try {
      excelTemplateService.removeAppExcelTemplate(tempId);
    } catch (Exception ex) {
      elog.error(CommonUtil.getErrorMessage(ex));
      isSuccess = false;
      message = "系统异常";
    }
    JSONMessage json = new JSONMessage();
    json.setMessage(message);
    json.setIsSuccess(isSuccess);
    return json;
  }


  /**
   * 获取贷款审批列表导出Columns
   *
   * @return
   */
  @RequestMapping(value = "/approveLoanColumns")
  @ResponseBody
  public JSONMessage getExcelTemplateTotalColumns(HttpServletRequest request) {
    SessionBean session = getSessionBean(request);
    elog.info("用户{}获取excel可以导出的所有字段", session.getUserName());
    boolean isSuccess = true;
    String message = "";
    List<ExportExlColumnTypeBean> exportExlColumnTypeBeanList = new ArrayList<>();
    try {
      exportExlColumnTypeBeanList = ExportConstants.approveLoanList;
    } catch (Exception ex) {
      elog.error(CommonUtil.getErrorMessage(ex));
      isSuccess = false;
      message = "系统异常";

    }
    JSONMessage json = new JSONMessage();
    json.setIsSuccess(isSuccess);
    json.setMessage(message);
    json.setDatas(exportExlColumnTypeBeanList);
    return json;
  }

}
