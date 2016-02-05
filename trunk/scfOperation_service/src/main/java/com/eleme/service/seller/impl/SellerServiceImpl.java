package com.eleme.service.seller.impl;

import static com.eleme.util.POIUtil.createDefaultRow;
import static com.eleme.util.POIUtil.createSimpleRow;
import static com.eleme.util.POIUtil.getDefaultStringStyle;
import static com.eleme.util.POIUtil.setDefaultSheetRHCW;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jxls.common.Context;
import org.jxls.util.JxlsHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.eleme.bean.JSONMessage;
import com.eleme.bean.seller.NaposRestaurant;
import com.eleme.bean.seller.PurposeSellerQueryBean;
import com.eleme.bean.seller.SellerQueryBean;
import com.eleme.constants.PagerConstants;
import com.eleme.domain.mart.PageResData;
import com.eleme.domain.mart.export.ExportExlColumnBean;
import com.eleme.domain.mart.seller.ExportExlQueryBean;
import com.eleme.domain.mart.seller.MNaposSeller;
import com.eleme.domain.mart.seller.PurposeSeller;
import com.eleme.domain.mart.seller.Seller;
import com.eleme.domain.mart.seller.TSdcTradeFlow;
import com.eleme.mapper.mart.seller.ISellerMapper;
import com.eleme.mapper.mart.seller.TSdcTradeFlowMapper;
import com.eleme.service.BaseService;
import com.eleme.service.seller.ISellerService;
import com.eleme.soa.ers.ElemeRestaurantService;
import com.eleme.soa.ers.TRestaurant;
import com.eleme.soa.ers.TRestaurantQuery;
import com.eleme.util.CommonUtil;
import com.eleme.util.JsonUtil;
import com.eleme.util.POIUtil;
import com.eleme.util.POIUtil.POICellBean;
import com.eleme.util.pager.TbData;

import me.ele.elog.Log;
import me.ele.elog.LogFactory;

/**
 * 商户信息管理实现类。
 *
 * @author penglau
 */
@Service
public class SellerServiceImpl extends BaseService implements ISellerService {

  /**
   * log
   */
  private static Log log = LogFactory.getLog(SellerServiceImpl.class);
  @Inject
  private ISellerMapper sellerMapper;
  @Inject
  private TSdcTradeFlowMapper tradeFlowMapper;
  @Inject
  private ElemeRestaurantService elemeRestaurantService;

  @Override
  @Transactional(readOnly = true)
  @Deprecated
  public TbData getSellerList(Integer currentPage, SellerQueryBean sqb) throws Exception {
    // 第一次进入时，currentPage为空
    if (currentPage == null) {
      currentPage = 1;
    }
    // 封装sql查询条件
    Map<String, Object> map = new HashMap<String, Object>();
    map.put("startRecord", (currentPage - 1) * PagerConstants.PAGE_SIZE);
    map.put("pageSize", PagerConstants.PAGE_SIZE);
    map.put("sellerName", sqb.getSellerName());
    map.put("sellerId", sqb.getSellerId());
    map.put("naposResOid", sqb.getNaposResOid());
    map.put("onlyNapos", sqb.getOnlyNapos());
    map.put("isImport", sqb.getIsImport());
    map.put("batch", sqb.getBatch());
    List<Seller> sellerList = sellerMapper.sellerList(map);
    int totalCount = sellerMapper.sellerTotalNum(map);

    return initTbData(totalCount, currentPage, PagerConstants.PAGE_SIZE, sellerList);
  }

  @Override
  @Transactional(readOnly = true)
  public PageResData<Seller> getSellerPageData(SellerQueryBean queryBean) throws Exception {
    // 封装sql查询条件
    Map<String, Object> map = new HashMap<String, Object>();
    map.put("startRecord", queryBean.getCurrentPage() * PagerConstants.PAGE_SIZE);
    map.put("pageSize", queryBean.getPageSize());
    map.put("sellerName", queryBean.getSellerName());
    map.put("keeperName", queryBean.getKeeperName());
    map.put("keeperPhone", queryBean.getKeeperPhone());
    map.put("sellerId", queryBean.getSellerId());
    map.put("naposResOid", queryBean.getNaposResOid());
    map.put("onlyNapos", queryBean.getOnlyNapos());
    map.put("isImport", queryBean.getIsImport());
    map.put("batch", queryBean.getBatch());
    map.put("isValid", queryBean.getIsValid());
    PageResData<Seller> sellerPageData = new PageResData<Seller>();
    // 获得数据总量
    sellerPageData.setTotal(sellerMapper.sellerTotalNum(map));
    // 获得分页数据
    sellerPageData.setRows(sellerMapper.sellerList(map));
    return sellerPageData;
  }

  @Override
  public XSSFWorkbook exportSellerAndFundsFlowBySellIds(ExportExlQueryBean sqb) {
    try {
      log.info("导出商户流水开始");
      // 查询数据
      Map<String, Object> parmMap = new HashMap<String, Object>();
      parmMap.put("sellerName", sqb.getSellerName());
      parmMap.put("keeperName", sqb.getKeeperName());
      parmMap.put("keeperPhone", sqb.getKeeperPhone());
      parmMap.put("sellerId", sqb.getSellerId());
      parmMap.put("naposResOid", sqb.getNaposResOid());
      parmMap.put("onlyNapos", sqb.getOnlyNapos());
      parmMap.put("isImport", sqb.getIsImport());
      parmMap.put("batch", sqb.getBatch());
      parmMap.put("isValid", sqb.getIsValid());
      parmMap.put("exportExlType", sqb.getExportExlType());
      log.info("导出类型:{},(0：根据搜索条件查询 1：根据导入的excel查询)", sqb.getExportExlType());
      parmMap.put("exportSellerIds", sqb.getExportSellerIdsStr().split(","));

      // 导出Excel 字段字符串
      String exportExlColumnStr = sqb.getExportExlColumn();
      log.info("需要导出的excel字段:{}", exportExlColumnStr);

      // 定义导出字段的list
      List<ExportExlColumnBean> columnList = new ArrayList<ExportExlColumnBean>();
      if (StringUtils.isNotBlank(exportExlColumnStr)) {
        // 解析要导出的字段
        columnList = JsonUtil.toModelArr(exportExlColumnStr, ExportExlColumnBean.class);
      }

      if (columnList.size() > 0) {
        StringBuilder exportColumns = new StringBuilder();
        for (ExportExlColumnBean exlColumnBean : columnList) {
          exportColumns.append("," + exlColumnBean.getName());
        }
        if (exportColumns.length() > 0) {
          parmMap.put("exportColumns", exportColumns.substring(1));
        }
      }

      // 查询所有商户信息
      // 根据excel中的餐厅id匹配 M_NAPOS_SELLER.OID，并将符合条件的商户所有者手机号进行二次匹配获得其下所有的商户
      List<Map<String, Object>> siList = sellerMapper.selectSellersForExport(parmMap);
      log.info("查询导出商户list,获得商户数量{}", siList.size());

      // 创建POI工作薄
      XSSFWorkbook workbook = new XSSFWorkbook();
      // 定义 CellStyleMap
      Map<String, XSSFCellStyle> cellStyleMap = new HashMap<>();

      // 创建商户信息Sheet
      XSSFSheet sellerSheet = workbook.createSheet("商户基本信息");
      setDefaultSheetRHCW(sellerSheet);

      // 表头字体
      XSSFFont font = workbook.createFont();
      font.setColor(IndexedColors.ROYAL_BLUE.getIndex());
      font.setFontHeight((short) 220);
      font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
      // 表头样式
      XSSFCellStyle cellHeadStyle = workbook.createCellStyle();
      cellHeadStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
      cellHeadStyle.setFont(font);
      cellHeadStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);

      // 生成表头(TODO 导出列有待确定)
      XSSFRow row0 = sellerSheet.createRow(0);

      for (int i = 0; i < columnList.size(); i++) {
        ExportExlColumnBean columnBean = columnList.get(i);
        XSSFCell cell = row0.createCell(i);

        cell.setCellStyle(cellHeadStyle);
        cell.setCellValue(columnBean.getText());
      }

      Set<String> needExportOIds = new HashSet<>(); // 需要导出的所有餐厅Id集合

      XSSFCellStyle defaultStringStyle = cellStyleMap.get(POIUtil.CELLSTYLE_TYPE_STRING);
      if (defaultStringStyle == null) {
        defaultStringStyle = getDefaultStringStyle(workbook);
        cellStyleMap.put(POIUtil.CELLSTYLE_TYPE_STRING, defaultStringStyle);
      }
      // 生成excel数据
      for (int i = 0; i < siList.size(); i++) {
        Map<String, Object> siMap = siList.get(i);
        String oidStr = String.valueOf(siMap.get("OID"));
        if (StringUtils.isBlank(oidStr) || oidStr.equals("null")) {
          continue;
        }
        needExportOIds.add(oidStr);
        XSSFRow row = sellerSheet.createRow(i + 1);

        for (int j = 0; j < columnList.size(); j++) {
          ExportExlColumnBean columnBean = columnList.get(j);
          XSSFCell cell = row.createCell(j);
          cell.setCellStyle(defaultStringStyle);
          cell.setCellValue(
              StringUtils.deleteWhitespace(String.valueOf(siMap.get(columnBean.getName()))));
        }
      }
      log.info("导出商户基本信息完成,成功导出{}条数据", siList.size());

      // 设置列宽根据宽度自适应
//      for (int j = 0; j < columnList.size(); j++) {
//        sellerSheet.autoSizeColumn(j);
//      }

      for (String oid : needExportOIds) {
        List<TSdcTradeFlow> tradeFlowList = tradeFlowMapper.selectByOid(Long.valueOf(oid));
        log.info("导出商户id:{}流水{}条,开始", oid, tradeFlowList.size());

        // 创建商户流水Sheet
        XSSFSheet sheetFundsFlow = workbook.createSheet("餐厅(ID_" + oid + ")流水");
        setDefaultSheetRHCW(sheetFundsFlow);

        int rowNum = 0;
        // 生成表头(TODO 导出列有待确定)
        createSimpleRow(rowNum++, sheetFundsFlow, cellHeadStyle, "餐厅id", "餐厅名", "订单id", "下单时间", "金额");

        // 生成表格数据
        for (int i = 0; i < tradeFlowList.size(); i++) {
          TSdcTradeFlow fundsFlow = tradeFlowList.get(i);
          Float total = fundsFlow.getTotal() != null ? (float) fundsFlow.getTotal() / 100 : null;
          createDefaultRow(rowNum++, workbook, sheetFundsFlow, cellStyleMap, new POICellBean(oid),
              new POICellBean(fundsFlow.getRestaurantName()),
              new POICellBean(fundsFlow.getOrderId()), new POICellBean(fundsFlow.getCreatedAt()),
              new POICellBean(total));
        }
        log.info("导出商户id:{}流水{}条,成功", oid, tradeFlowList.size());
//        sheetFundsFlow.autoSizeColumn(0);
//        sheetFundsFlow.autoSizeColumn(1);
//        sheetFundsFlow.autoSizeColumn(2);
//        sheetFundsFlow.autoSizeColumn(3);
//        sheetFundsFlow.autoSizeColumn(4);
      }
      log.info("导出商户流水完成");
      return workbook;
    } catch (Exception ex) {
      log.error(CommonUtil.getErrorMessage(ex));
      return null;
    }
  }

  @Override
  public void exportSellerTradeFlowDetail(Integer sellerId, OutputStream outputStream) {
    try {
      Seller seller = sellerMapper.findSellerBySellerId(sellerId);
      List<TSdcTradeFlow> tradeFlowList = tradeFlowMapper.selectBySellerId(sellerId);
      InputStream is = getClass().getResourceAsStream("/tpl/excel/trade_flow_detail.xls");
      OutputStream os = outputStream;
      Context context = new Context();
      context.putVar("naposResOid", seller.getNapos_res_oid());
      context.putVar("tradeFlows", tradeFlowList);
      long startTime = new Date().getTime();
      JxlsHelper.getInstance().processTemplate(is, os, context);
      log.info("导出{}条流水数据，耗时{}ms", tradeFlowList.size(), new Date().getTime() - startTime);
    } catch (Exception ex) {
      log.error(CommonUtil.getErrorMessage(ex));
    }

  }

  @Override
  public TbData getPurposeSellerList(Integer currentPage, PurposeSellerQueryBean psqb)
      throws Exception {
    // 第一次进入时，currentPage为空
    if (currentPage == null) {
      currentPage = 1;
    }
    // 封装sql查询条件
    Map<String, Object> map = new HashMap<String, Object>();
    map.put("startRecord", (currentPage - 1) * PagerConstants.PAGE_SIZE);
    map.put("pageSize", PagerConstants.PAGE_SIZE);
    map.put("sellerName", psqb.getSellerName());
    map.put("batch", psqb.getBatch());
    map.put("partner", psqb.getPartner());
    List<PurposeSeller> sellerList = sellerMapper.purposeSellerList(map);
    int totalCount = sellerMapper.purposeSellerTotalNum(map);

    return initTbData(totalCount, currentPage, PagerConstants.PAGE_SIZE, sellerList);
  }

  @Override
  public JSONMessage purposeSellerImport(MultipartFile purposeSellerExcel, long curUserId)
      throws Exception {
    XSSFWorkbook book = new XSSFWorkbook(purposeSellerExcel.getInputStream());
    // 读取第一个sheet
    XSSFSheet sheet = book.getSheetAt(0);
    int lastRowNum = sheet.getPhysicalNumberOfRows();
    XSSFRow row0 = sheet.getRow(0);
    log.info("导入意向商户列表，行数为：" + lastRowNum);
    // 验证表头是否符合格式要求（根据内容判断即可）
    if (!checkEmpInfoImportHead(row0)) {
      log.info("导入意向商户列表，表头不正确");
      return new JSONMessage(false, "表头格式不正确");
    }
    // TODO
    // 判断是否重复（不保存，提示用户xx餐厅已存在）以及部分字断是否为int(返回到前台)，另还需判断某行记录全部为空的情况（不返回，自行过滤）
    // 待批量插入的意向商户集合
    List<PurposeSeller> psList = new ArrayList<PurposeSeller>();
    // 迭代表格行
    for (int rn = 1; rn < lastRowNum; rn++) {
      XSSFRow row = sheet.getRow(rn);
      if (row == null) {
        continue;
      }
      // 获取数据
      PurposeSeller ps = new PurposeSeller();
      ps.setRes_name(getPOICellValue(row.getCell(0)));
      ps.setMobile(getPOICellValue(row.getCell(1)));
      ps.setRes_addr(getPOICellValue(row.getCell(2)));
      String naposId = getPOICellValue(row.getCell(3));

      // 空数据判断
      if (StringUtils.isBlank(naposId) && StringUtils.isBlank(ps.getRes_name())) {
        continue;
      }
      if (StringUtils.isBlank(naposId)) {
        ps.setNapos_resid(0);
      } else {
        ps.setNapos_resid(Integer.parseInt(naposId));
      }
      String resId = getPOICellValue(row.getCell(4));
      if (StringUtils.isBlank(resId)) {
        ps.setRes_id(0);
      } else {
        ps.setRes_id(Integer.parseInt(resId));
      }
      String batch = getPOICellValue(row.getCell(5));
      if (StringUtils.isBlank(batch)) {
        ps.setBatch(0);
      } else {
        ps.setBatch(Integer.parseInt(batch));
      }
      ps.setPartner(getPOICellValue(row.getCell(6)));
      psList.add(ps);
    }

    // 定义成功插入数量
    int successNum = 0;
    if (psList.size() > 0) {
      // 插入，返回成功添加意向商户的数量
      successNum = sellerMapper.batchAddPS(psList);
    }

    // 返回结果
    return new JSONMessage(true, "已成功添加" + successNum + "条意向餐厅!");
  }

  /**
   * 导入意向商户表头验证
   *
   * @param row 表格第一行
   * @return
   */
  private boolean checkEmpInfoImportHead(XSSFRow row) {
    int columnCount = row.getPhysicalNumberOfCells();
    if (columnCount != 7) {
      return false;
    }
    for (int i = 0; i < columnCount; i++) {
      String columnName;
      switch (i) {
        case 0:
          columnName = StringUtils.deleteWhitespace(String.valueOf(row.getCell(i)));
          if (!columnName.equals("餐厅名称"))
            return false;
          break;
        case 1:
          columnName = StringUtils.deleteWhitespace(String.valueOf(row.getCell(i)));
          if (!columnName.equals("手机号"))
            return false;
          break;
        case 2:
          columnName = StringUtils.deleteWhitespace(String.valueOf(row.getCell(i)));
          if (!columnName.equals("地址"))
            return false;
          break;
        case 3:
          columnName = StringUtils.deleteWhitespace(String.valueOf(row.getCell(i)));
          if (!columnName.equals("napos餐厅id"))
            return false;
          break;
        case 4:
          columnName = StringUtils.deleteWhitespace(String.valueOf(row.getCell(i)));
          if (!columnName.equals("商户id"))
            return false;
          break;
        case 5:
          columnName = StringUtils.deleteWhitespace(String.valueOf(row.getCell(i)));
          if (!columnName.equals("批次"))
            return false;
          break;
        case 6:
          columnName = StringUtils.deleteWhitespace(String.valueOf(row.getCell(i)));
          if (!columnName.equals("合作方"))
            return false;
          break;
      }
    }
    return true;
  }

  @Override
  public JSONMessage saveSellerInfo(MNaposSeller mns) throws Exception {
    // 插入，返回成功添加意向商户的数量
    int successNum = sellerMapper.saveSellerInfo(mns);
    return new JSONMessage(true, "已成功添加" + successNum + "条商户!");
  }

  @Override
  public Map<String, Object> remoteCallNaposResAPI(String res_name, String mobile, String res_addr,
      String napos_resid) throws Exception {
    TRestaurantQuery tq = new TRestaurantQuery();
    List<TRestaurant> list = new ArrayList<TRestaurant>();
    boolean isSuccess = true;
    String failureMess = "";
    if (!StringUtils.isBlank(napos_resid) && StringUtils.isNumeric(napos_resid)) {
      TRestaurant ts = null;
      try {
        ts = elemeRestaurantService.get_by_oid(Integer.parseInt(napos_resid));
      } catch (Exception ex) {
        isSuccess = false;
        failureMess = ex.getMessage();
        log.error(ex.getMessage() + ",napos_resid:" + napos_resid);
      }
      list.add(ts);
    } else {
      tq.setAddress(res_addr);
      // tq.setMobile(mobile);
      tq.setPhone(mobile);
      tq.setKeyword(res_name);
      try {
        list.addAll(elemeRestaurantService.query_restaurant(tq));
      } catch (Exception ex) {
        isSuccess = false;
        failureMess = ex.getMessage();
        log.error(ex.getMessage() + ",napos_resid:" + napos_resid);
      }
    }
    // 重新封装返回结果
    List<NaposRestaurant> resultList = new ArrayList<NaposRestaurant>();
    for (TRestaurant ts : list) {
      NaposRestaurant nr = new NaposRestaurant();
      BeanUtils.copyProperties(nr, ts);
      resultList.add(nr);
    }
    Map<String, Object> map = new HashMap<String, Object>();
    map.put("list", resultList);
    map.put("isSuccess", isSuccess);
    map.put("failureMess", failureMess);
    return map;
  }

  @Override
  public MNaposSeller findMNaposSellerByNaposResId(String naposResId) throws Exception {
    MNaposSeller mns = sellerMapper.findMNaposSellerByNaposResId(naposResId);
    return mns;
  }

  @Override
  public boolean judgeMNaposSellerExistsByNaposResId(String naposResId) throws Exception {
    boolean flag = false;
    MNaposSeller mns = findMNaposSellerByNaposResId(naposResId);
    if (mns != null) {
      flag = true;
    }
    return flag;
  }

  @Override
  public Seller getSellerInfoById(Integer sellerId) {
    return sellerMapper.findSellerBySellerId(sellerId);
  }

}
