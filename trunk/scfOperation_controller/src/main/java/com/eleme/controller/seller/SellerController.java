package com.eleme.controller.seller;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.eleme.annotation.controller.UserMenu;
import com.eleme.bean.JSONMessage;
import com.eleme.bean.SessionBean;
import com.eleme.bean.seller.PurposeSellerQueryBean;
import com.eleme.bean.seller.SellerQueryBean;
import com.eleme.controller.BaseController;
import com.eleme.domain.mart.PageResData;
import com.eleme.domain.mart.seller.ExportExlQueryBean;
import com.eleme.domain.mart.seller.MNaposSeller;
import com.eleme.domain.mart.seller.Seller;
import com.eleme.domain.mart.seller.TSdcTradeFlowStatistic;
import com.eleme.domain.ops.file.TSysExcelTemplate;
import com.eleme.service.file.IFileService;
import com.eleme.service.seller.ISellerService;
import com.eleme.service.seller.ITradeFlowService;
import com.eleme.soa.ers.ElemeRestaurantService;
import com.eleme.soa.ers.TRestaurant;
import com.eleme.util.CommonUtil;
import com.eleme.util.POIUtil;
import com.eleme.util.pager.TbData;

import me.ele.elog.Log;
import me.ele.elog.LogFactory;

@Controller
@RequestMapping(value = "/seller")
public class SellerController extends BaseController {

  /**
   * log
   */
  private static Log log = LogFactory.getLog(SellerController.class);

  @Inject
  private ISellerService sellerServiceImpl;

  @Inject
  private IFileService fileServiceImpl;

  @Inject
  private ElemeRestaurantService elemeRestaurantService;

  @Inject
  private ITradeFlowService tradeFlowService;

  /**
   * 商户列表.
   *
   * @param request
   * @param sqb
   * @param currentPage
   * @return
   * @throws Exception
   */
  @RequestMapping(value = "/list", method = RequestMethod.GET)
  @UserMenu
  public ModelAndView list(HttpServletRequest request, SellerQueryBean sqb, Integer currentPage)
      throws Exception {
    // 记录日志
    SessionBean session = getSessionBean(request);
    log.info("{}查看商户列表", session.getUserName());
    // 查询数据并返回
    ModelAndView mav = new ModelAndView("seller/list");

    // 页面中的数据通过ajax请求获取不再直接通过freemarker嵌入 下方代码因此废弃
    // TbData tbData = sellerServiceImpl.getSellerList(currentPage, sqb);
    // tbData = tbData.fillTbData("seller/list", sqb, null);
    //
    // mav.addObject("tbData", tbData);
    // mav.addObject("sqb", sqb);

    // 查询excel模板
    TSysExcelTemplate excelTemplate = fileServiceImpl.getExcelTemplateByTemplateId("1");
    mav.addObject("excelTemplate", excelTemplate);
    log.info(excelTemplate.getUrl());
    return mav;
  }

  /**
   * 获得分页数据
   *
   * @param request
   * @param queryBean
   * @return
   * @author sunwei
   * @since 2016年1月4日
   */
  @RequestMapping(value = "/list", method = RequestMethod.POST)
  @ResponseBody
  public JSONMessage listAjaxPage(HttpServletRequest request, SellerQueryBean queryBean) {
    // 记录日志
    SessionBean session = getSessionBean(request);
    log.info("{}获取商户列表数据", session.getUserName());

    boolean isSuccess = true;
    String message = "";
    PageResData<Seller> result = null;

    // 初始化请求参数
    if (queryBean == null) {
      queryBean = new SellerQueryBean();
    }
    try {
      result = sellerServiceImpl.getSellerPageData(queryBean);
    } catch (Exception ex) {
      log.error(CommonUtil.getErrorMessage(ex));
      isSuccess = false;
      message = "系统异常";
    }

    JSONMessage json = new JSONMessage();
    json.setIsSuccess(isSuccess);
    json.setDatas(result);
    json.setMessage(message);
    return json;
  }

  /**
   * 获取详细页面
   *
   * @param request
   * @param sellerId
   * @return
   * @author sunwei
   * @since 2016年1月6日
   */
  @RequestMapping(value = "/detail/{sellerId:[\\d]+}", method = RequestMethod.GET)
  @UserMenu
  public ModelAndView detailPage(HttpServletRequest request,
      @PathVariable("sellerId") Integer sellerId) {
    SessionBean session = getSessionBean(request);
    log.info("{}获取商户详细信息", session.getUserName());
    // 设置模板路由
    ModelAndView mav = new ModelAndView("seller/detail");
    // 查询商户信息
    Seller sellerInfo = sellerServiceImpl.getSellerInfoById(sellerId);
    // 为模板设置参数
    mav.addObject("sellerInfo", sellerInfo);

    return mav;
  }

  /**
   * 获取订单流水统计数据
   *
   * @param request
   * @param sellerId
   * @return
   * @author sunwei
   * @since 2016年1月6日
   */
  @RequestMapping(value = "/tradeFlow/statisticList/{sellerId:[\\d]+}", method = RequestMethod.GET)
  @ResponseBody
  public JSONMessage listSellerTradeFlowStatisticList(HttpServletRequest request,
      @PathVariable("sellerId") Integer sellerId) {
    boolean isSuccess = true;
    String message = "";
    List<TSdcTradeFlowStatistic> result = null;

    // 查询数据并返回
    try {
      result = tradeFlowService.getTradeFlowStatistic(sellerId);
    } catch (Exception ex) {
      ex.printStackTrace();
      log.error(CommonUtil.getErrorMessage(ex));
      isSuccess = false;
      message = "系统异常";
    }

    JSONMessage json = new JSONMessage();
    json.setIsSuccess(isSuccess);
    json.setDatas(result);
    json.setMessage(message);
    return json;
  }

  /**
   * 获取商户订单流水
   *
   * @param request
   * @param sellerId
   * @param response
   * @throws UnsupportedEncodingException
   * @author sunwei
   * @since 2016年1月6日
   */
  @RequestMapping(value = "/tradeFlow/detailList/export/{sellerId:[\\d]+}",
      method = RequestMethod.GET)
  public void exportSellerTradeFlow(HttpServletRequest request,
      @PathVariable("sellerId") Integer sellerId, HttpServletResponse response) throws Exception {
    // 日志
    SessionBean session = getSessionBean(request);
    log.info("{}导出商户基本信息表", session.getUserName());


    // 导出设置
    response.setContentType("application/vnd.ms-excel;charset=UTF-8");
    String codedFileName = new String("商户流水明细".getBytes("gb2312"), "ISO8859-1");
    response.setHeader("content-disposition", "attachment;filename=" + codedFileName + ".xls");

    // 查询数据并导出
    sellerServiceImpl.exportSellerTradeFlowDetail(sellerId, response.getOutputStream());

  }


  /**
   * 导出商户信息
   *
   * @param request
   * @param response
   * @param eeqb
   * @throws Exception
   * @author sunwei
   * @since 2016年1月5日
   */
  @RequestMapping(value = "/exportSellerInfo")
  @ResponseBody
  public void exportSellerInfo(HttpServletRequest request, HttpServletResponse response,
      ExportExlQueryBean eeqb) throws Exception {
    // 日志
    SessionBean session = getSessionBean(request);
    log.info("{}导出商户基本信息表", session.getUserName());

    // 查询数据并返回数据表对象
    XSSFWorkbook workbook = sellerServiceImpl.exportSellerAndFundsFlowBySellIds(eeqb);
    String codedFileName = new String("商户及流水信息".getBytes("gb2312"), "ISO8859-1");
    // 导出
    response.setContentType("application/vnd.ms-excel;charset=UTF-8");
    response.setHeader("content-disposition", "attachment;filename=" + codedFileName + ".xlsx");
    workbook.write(response.getOutputStream());
    log.info("返回导出Excel数据");
  }

  /**
   * 上传需要导出的商户id,导出之前对Excel进行解析以及过滤提醒
   *
   * @param request
   * @param response
   * @param needExportSellersExcel
   * @return
   * @throws Exception
   */
  @ResponseBody
  @RequestMapping(value = "/uploadNeedExportSellers", method = RequestMethod.POST,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public JSONMessage uploadNeedExportSeller(HttpServletRequest request,
      HttpServletResponse response, MultipartFile needExportSellersExcel) {
    boolean isSuccess = true;
    String message = "数据上传成功！";
    String resData = "";
    JSONMessage resultJson = new JSONMessage();
    XSSFWorkbook book = null;

    try {
      book = new XSSFWorkbook(needExportSellersExcel.getInputStream());
    } catch (Exception ex) {
      log.error(CommonUtil.getErrorMessage(ex));
      isSuccess = false;
      message = "上传的文件异常";
    }
    try {
      if (isSuccess) {
        // 读取第一个sheet
        XSSFSheet sheet = book.getSheetAt(0);
        int lastRowNum = sheet.getPhysicalNumberOfRows();
        int idColIndex = -1; // 默认所需的
        XSSFRow row0 = sheet.getRow(0);
        log.info("导入餐厅（或店铺）参数列表，行数为：" + lastRowNum);

        idColIndex = checkEmpInfoImportHead(row0);
        // 验证表头是否符合格式要求（根据内容判断即可）
        if (idColIndex == -1) {
          log.info("导入餐厅（或店铺）列表，表头不正确");
          isSuccess = false;
          message = "表头格式不正确";
        }

        // 解析Excel获得餐厅IDs 对应的M_NAPOS_SELLERS表中的OID字段
        if (isSuccess) {
          StringBuilder sellerIdsSb = new StringBuilder();

          for (int i = 1; i < lastRowNum; i++) {
            XSSFRow row = sheet.getRow(i);
            String sellerIdVal = POIUtil.getStringCellValue(row.getCell(idColIndex));
            if (StringUtils.isNotBlank(sellerIdVal)) { // 读取到非空值时候将其加入sellerIds字符串
              Double sellerIdNum = Double.valueOf(sellerIdVal);
              sellerIdsSb.append("," + sellerIdNum.longValue());
            }
          }

          if (sellerIdsSb.length() > 0) {
            resData = sellerIdsSb.substring(1);
          } else {
            isSuccess = false;
            message = "未解析到有用的数据，请检查后重新上传";
          }
        }
      }
    } catch (Exception ex) {
      log.error(CommonUtil.getErrorMessage(ex));
      isSuccess = false;
      message = "上传的文件解析出现异常";
    }
    resultJson.setIsSuccess(isSuccess);
    resultJson.setMessage(message);
    if (resData != null) {
      resultJson.setDatas(resData);
    }
    return resultJson;

  }

  /**
   * 验证导出商户信息时提交的Excel信息,并返回商户Id
   *
   * @param row 表格第一行
   * @return
   */
  private int checkEmpInfoImportHead(XSSFRow row) {
    int idColIndex = -1;
    int columnCount = row.getPhysicalNumberOfCells();
    for (int i = 0; i < columnCount; i++) {
      String columnName;
      columnName = StringUtils.deleteWhitespace(String.valueOf(row.getCell(i)));
      if (columnName.equalsIgnoreCase("店铺ID") || columnName.equalsIgnoreCase("餐厅ID")
          || columnName.equalsIgnoreCase("ID")) {
        idColIndex = i;
        break;
      }
    }
    return idColIndex;
  }

  /**
   * 意向商户列表. TODO 列表增加一列：是否已保存到napos商户表。
   *
   * @param request
   * @param psqb
   * @param currentPage
   * @return
   * @throws Exception
   */
  @RequestMapping(value = "/purposelist", method = RequestMethod.GET)
  @UserMenu
  public ModelAndView purposelist(HttpServletRequest request, PurposeSellerQueryBean psqb,
      Integer currentPage) throws Exception {
    // 记录日志
    SessionBean session = getSessionBean(request);
    log.info("{}查看意向商户列表", session.getUserName());
    // 查询数据并返回
    ModelAndView mav = new ModelAndView("seller/purposelist");
    TbData tbData = sellerServiceImpl.getPurposeSellerList(currentPage, psqb);
    tbData = tbData.fillTbData("seller/purposelist", psqb, null);
    mav.addObject("tbData", tbData);
    mav.addObject("psqb", psqb);

    // 查询excel模板
    TSysExcelTemplate excelTemplate = fileServiceImpl.getExcelTemplateByTemplateId("2");
    mav.addObject("excelTemplate", excelTemplate);
    return mav;
  }

  /**
   * 导入金融机构提供的意向商户名单，从数据接口中获取商户信息，保存到数据库中. ExcelFormat:商户名称、手机号、地址、napos商户id、商户id、批次、合作方
   *
   * @param request
   * @param response
   * @param purposeSellerExcel
   * @throws Exception
   */
  @RequestMapping(value = "/purposeSellerImport", method = RequestMethod.POST,
      produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  public JSONMessage purposeSellerImport(HttpServletRequest request, HttpServletResponse response,
      MultipartFile purposeSellerExcel) throws Exception {
    // 记录日志
    SessionBean session = getSessionBean(request);
    log.info("{}导入意向商户表格", session.getUserName());
    long currentUserId = session.getUserId();
    if (purposeSellerExcel.getSize() > 0) {
      JSONMessage jm = sellerServiceImpl.purposeSellerImport(purposeSellerExcel, currentUserId);
      return jm;
    } else {
      return new JSONMessage(false, "导入失败，请检查导入文件!");
    }
  }

  /**
   * 调用商户基本信息api，获取商户信息。
   *
   * @param request
   * @param response
   * @param res_name 商户名称
   * @param mobile 手机号
   * @param res_addr 商户地址
   * @param napos_resid napos商户id
   * @return map<String, Object>.put(list).put(isSuccess).put(failureMess)
   * @throws Exception
   */
  @RequestMapping(value = "/remoteGetSellerInfo", method = RequestMethod.GET,
      produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  public Map<String, Object> remoteGetSellerInfo(HttpServletRequest request,
      HttpServletResponse response, String res_name, String mobile, String res_addr,
      String napos_resid) throws Exception {
    // 记录日志
    SessionBean session = getSessionBean(request);
    log.info("{}调用api获取{}的信息", session.getUserName(), res_name);
    // 调用api查询
    Map<String, Object> map =
        sellerServiceImpl.remoteCallNaposResAPI(res_name, mobile, res_addr, napos_resid);
    return map;
  }

  @RequestMapping(value = "/saveSellerInfoByOid", method = RequestMethod.POST,
      produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  public JSONMessage saveSellerInfoByOid(HttpServletRequest request, HttpServletResponse response,
      String napos_resid) throws Exception {
    // 记录日志
    SessionBean session = getSessionBean(request);
    log.info("{}保存naposid为{}的信息", session.getUserName(), napos_resid);
    // 判断是否重复
    boolean existsFlag = sellerServiceImpl.judgeMNaposSellerExistsByNaposResId(napos_resid);
    if (existsFlag) {
      return new JSONMessage(false, "商户" + napos_resid + "已存在!");
    }
    // 调用api再次查询
    TRestaurant ts = elemeRestaurantService.get_by_oid(Integer.parseInt(napos_resid));
    // 保存并返回json
    MNaposSeller mns = new MNaposSeller();
    BeanUtils.copyProperties(mns,ts);
    JSONMessage jm = sellerServiceImpl.saveSellerInfo(mns);
    return jm;
  }

  /**
   * 获取商户流水
   *
   * @param request
   * @param response
   * @param id
   * @return
   * @throws Exception
   */
  @RequestMapping(value = "/naposSellersFlow", method = RequestMethod.POST,
      produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  public JSONMessage fundsFlow(HttpServletRequest request, HttpServletResponse response, Integer id,
      Integer month) throws Exception {
    // 记录日志
    SessionBean session = getSessionBean(request);
    log.info("{}获取交易流水,商户ID:{}", session.getUserName(), id);
    tradeFlowService.saveTradeFlowBatch(id, month);
    return new JSONMessage(true, "获取交易流水成功");
  }

}
