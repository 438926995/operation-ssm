package com.eleme.service.loan.impl;

import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.util.CellRangeAddress;
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

import com.eleme.constants.FlowTeamPrefix;
import com.eleme.constants.LoanStatus;
import com.eleme.constants.PagerConstants;
import com.eleme.domain.mart.export.ExportExlColumnBean;
import com.eleme.domain.mart.export.ExportExlColumnTypeBean;
import com.eleme.domain.mart.loan.ExportPurposeLoanQueryCnd;
import com.eleme.domain.mart.loan.PurposeLoanCityStatistics;
import com.eleme.domain.mart.loan.PurposeLoanQueryCnd;
import com.eleme.domain.mart.loan.PurposeLoanStatisticsDetail;
import com.eleme.domain.mart.loan.PurposeLoanStatisticsExportVo;
import com.eleme.domain.mart.loan.PurposeLoanStatisticsVo;
import com.eleme.domain.mart.loan.TAppPurposeLoan;
import com.eleme.domain.mart.loan.TAppPurposeLoanVo;
import com.eleme.domain.mart.loan.TAppSellerLoan;
import com.eleme.domain.mart.loan.TApvFlowNode;
import com.eleme.domain.mart.loan.TApvFlowProcess;
import com.eleme.domain.mart.loan.TApvFlowTeam;
import com.eleme.domain.mart.loan.TApvHistory;
import com.eleme.domain.mart.product.MFinanceProduct;
import com.eleme.domain.mart.product.ProductRuleQueryCnd;
import com.eleme.domain.mart.product.TFcoRequestRuleVo;
import com.eleme.mapper.mart.loan.TAppPurposeLoanMapper;
import com.eleme.mapper.mart.loan.TAppSellerLoanMapper;
import com.eleme.mapper.mart.loan.TApvFlowNodeMapper;
import com.eleme.mapper.mart.loan.TApvFlowProcessMapper;
import com.eleme.mapper.mart.loan.TApvFlowTeamMapper;
import com.eleme.mapper.mart.loan.TApvHistoryMapper;
import com.eleme.mapper.mart.product.MFinanceProductMapper;
import com.eleme.mapper.mart.product.TFcoRequestRuleMapper;
import com.eleme.service.BaseService;
import com.eleme.service.loan.IPurposeLoanService;
import com.eleme.util.pager.TbData;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;


/**
 * 商户借贷现类。
 *
 * @author yonglin.zhu
 */
@Service
public class PurposeLoanServiceImpl extends BaseService implements IPurposeLoanService {

  @Inject
  private TAppPurposeLoanMapper appPurposeLoanMapper;

  @Inject
  private TApvFlowProcessMapper apvFlowProcessMapper;

  @Inject
  private TApvFlowNodeMapper apvFlowNodeMapper;

  @Inject
  private MFinanceProductMapper financeProductMapper;

  @Inject
  private TApvFlowTeamMapper apvFlowTeamMapper;

  @Inject
  private TApvHistoryMapper apvHistoryMapper;

  @Inject
  private TAppSellerLoanMapper appSellerLoanMapper;

  @Inject
  private TFcoRequestRuleMapper fcoRequestRuleMapper;


  @Override
  public TbData selectPurposeLoanList(PurposeLoanQueryCnd cnd, Integer currentPage)
      throws Exception {
    // 第一次进入时，currentPage为空
    if (currentPage == null) {
      currentPage = 1;
    }
    // 封装sql查询条件
    if (cnd.getSubmitTimeTo() != null) {
      cnd.setSubmitTimeTo(DateUtils.parseDate(
          DateFormatUtils.format(cnd.getSubmitTimeTo(), "yyyy/MM/dd") + " 23:59:59",
          "yyyy/MM/dd HH:mm:ss"));
    }
    cnd.setStartRecord((currentPage - 1) * PagerConstants.PAGE_SIZE);
    cnd.setPageSize(PagerConstants.PAGE_SIZE);
    List<TAppPurposeLoanVo> list = appPurposeLoanMapper.selectPurposeLoanListByCnd(cnd);
    int totalCount = appPurposeLoanMapper.selectSellerLoanListCount(cnd);
    return initTbData(totalCount, currentPage, PagerConstants.PAGE_SIZE, list);
  }


  @Override
  public XSSFWorkbook exportPurposeLoanList(ExportPurposeLoanQueryCnd cnd) throws Exception {
    // 封装sql查询条件
    if (cnd.getSubmitTimeTo() != null) {
      cnd.setSubmitTimeTo(DateUtils.parseDate(
          DateFormatUtils.format(cnd.getSubmitTimeTo(), "yyyy/MM/dd") + " 23:59:59",
          "yyyy/MM/dd HH:mm:ss"));
    }
    cnd.setStartRecord(null);
    cnd.setPageSize(null);

    List<TAppPurposeLoanVo> dataList = appPurposeLoanMapper.selectPurposeLoanListByCnd(cnd);
    // 解析导出字段设置
    ObjectMapper mapper = new ObjectMapper();
    List<ExportExlColumnTypeBean> exportExlColumnTypes = mapper.readValue(cnd.getExportExlColumn(),
        new TypeReference<List<ExportExlColumnTypeBean>>() {});

    long startTime = System.currentTimeMillis();
    XSSFWorkbook workbook = exportPurposeLoanListExcel(dataList, exportExlColumnTypes);
    log.info("导出{}条流水数据，耗时{}ms", dataList.size(), System.currentTimeMillis() - startTime);
    return workbook;
  }


  @Override
  public List<PurposeLoanStatisticsVo> selectSellerLoanStatistics() throws Exception {
    return appPurposeLoanMapper.selectSellerLoanStatistics();
  }

  @Override
  public Integer selectSellerLoanListCount(PurposeLoanQueryCnd cnd) throws Exception {
    return appPurposeLoanMapper.selectSellerLoanListCount(cnd);
  }

  @Override
  @Transactional(rollbackFor = Throwable.class)
  public int assignOrg(Integer fpId, Integer[] plIds) throws Exception {
    // 匹配流程
    TApvFlowProcess apvFlowProcess = apvFlowProcessMapper.selectLastestCommonProcess();
    int processId = apvFlowProcess == null ? 0 : apvFlowProcess.getProcsID();
    // 查询贷款贷款产品规则信息
    ProductRuleQueryCnd cnd = new ProductRuleQueryCnd();
    cnd.setFpId(fpId);
    cnd.setIsMobileUse("-1");
    List<TFcoRequestRuleVo> fcoRequestRuleVos = fcoRequestRuleMapper.selectRuleListByFpId(cnd);
    for (Integer plId : plIds) {
      TAppPurposeLoan appPurposeLoan = appPurposeLoanMapper.selectById(plId);
      TAppSellerLoan record = new TAppSellerLoan();
      BeanUtils.copyProperties(record, appPurposeLoan);
      record.setFpId(fpId);
      record.setAppStatus(
          processId == 0 ? LoanStatus.Status_N.getIndex() : LoanStatus.Status_P.getIndex());
      record.setProcsId(processId);
      // 新增贷款申请
      appSellerLoanMapper.insert(record);
      if (processId != 0) {
        // 查找节点审批人
        List<Map<String, Object>> nodeList = getFlowNode(processId, fpId);
        log.info("nodeList.size: " + nodeList.size());
        // 遍历nodeList，插入到apv_history表.
        for (int i = 0; i < nodeList.size(); i++) {
          // 添加审批历史记录
          TApvHistory apvHistory = new TApvHistory();
          apvHistory.setAppUserIds(nodeList.get(i).get("appUserIds").toString());
          apvHistory.setAppUserNames(nodeList.get(i).get("appUserNames").toString());
          apvHistory.setProcsId(processId);
          apvHistory.setNodeId(Integer.parseInt(nodeList.get(i).get("nodeId").toString()));
          apvHistory.setNodeName(nodeList.get(i).get("nodeName").toString());
          apvHistory.setSlId(record.getSlId());
          // 如果i=0，说明是第一个节点，审批状态为C
          if (i == 0) {
            // 产品有规则的情况，则不自动审核
            if (fcoRequestRuleVos != null && fcoRequestRuleVos.size() > 0) {
              apvHistory.setAppStatus(LoanStatus.Status_P.getIndex());
            } else {
              apvHistory.setAppDate(new Date());
              // 系统用户都用-1表示
              apvHistory.setReallyAppUserId(-1);
              apvHistory.setOpinions("系统自动审核");
              apvHistory.setAppStatus(LoanStatus.Status_C.getIndex());
            }
            apvHistoryMapper.insert(apvHistory);
          }
          // 如果i=1，说明是第一个节点，审批状态为P
          else if (i == 1) {
            // 产品有规则的情况，则不自动审核
            if (fcoRequestRuleVos != null && fcoRequestRuleVos.size() > 0) {
              apvHistory.setAppStatus(LoanStatus.Status_B.getIndex());
            } else {
              apvHistory.setAppStatus(LoanStatus.Status_P.getIndex());
            }
            apvHistoryMapper.insert(apvHistory);
          } else {
            apvHistory.setAppStatus(LoanStatus.Status_B.getIndex());
            apvHistoryMapper.insert(apvHistory);
          }
        }
      } else {
        // 给用户返回，保存成功，但提交失败。同时给管理员发送通知。
      }
    }

    return appPurposeLoanMapper.updateForAssignOrg(plIds);
  }

  @Override
  public TAppPurposeLoanVo selectPurposeLoanDetail(Integer plId) throws Exception {
    return appPurposeLoanMapper.selectPurposeLoanDetail(plId);
  }

  @Override
  public List<PurposeLoanStatisticsDetail> getPurposeStatisticsDetail(PurposeLoanQueryCnd cnd)
      throws Exception {
    List<PurposeLoanStatisticsDetail> details = new ArrayList<>();

    List<PurposeLoanStatisticsVo> statistics =
        appPurposeLoanMapper.selectSellerLoanStatisticsByCnd(cnd);
    if (statistics.size() == 0) {
      return details;
    }
    // 增加一个比较对象
    PurposeLoanStatisticsVo p = new PurposeLoanStatisticsVo();
    p.setProposerProvId(0);
    statistics.add(p);

    int sum = 0;
    BigDecimal amountTotal = new BigDecimal(0);

    if (statistics != null) {
      List<PurposeLoanCityStatistics> cityStatistics = new ArrayList<>();
      PurposeLoanCityStatistics cityStatistic = new PurposeLoanCityStatistics();

      PurposeLoanStatisticsDetail detail = null;
      // 初始化 省份名
      String provName = statistics.get(0).getProposerProvName();
      int provId = statistics.get(0).getProposerProvId();
      for (PurposeLoanStatisticsVo s : statistics) {
        if (s != null) {
          // 前一个与后一个比 不同则将数据 add
          if (provId != s.getProposerProvId()) {
            detail = new PurposeLoanStatisticsDetail();
            // 省份统计数据
            detail.setProvId(provId);
            detail.setProvName(provName);
            detail.setAccountTotal(sum);
            detail.setProvsAmount(amountTotal);
            // 省份对应城市的 统计数据
            detail.setCityStatistics(cityStatistics);

            details.add(detail);
            if (s.getTotalAccount() == null)
              break;

            // 重新初始化
            sum = s.getTotalAccount();
            amountTotal = s.getTotalLoanAmount();
            // 清空数据 重新初始化城市统计信息列表
            cityStatistics = new ArrayList<>();
          } else {
            sum += s.getTotalAccount();
            amountTotal = amountTotal.add(s.getTotalLoanAmount());
          }
          provName = s.getProposerProvName();
          provId = s.getProposerProvId();
          // 添加城市统计信息
          cityStatistic = new PurposeLoanCityStatistics();
          cityStatistic.setCityName(s.getProposerCityName());
          cityStatistic.setCityId(s.getProposerCityId());
          cityStatistic.setCityAccount(s.getTotalAccount());
          cityStatistic.setCityAmount(s.getTotalLoanAmount());
          cityStatistics.add(cityStatistic);
        }
      }
    }
    return details;
  }


  /**
   * 导出excel
   *
   * @param dataList 数据model
   * @param exportExlColumnTypes 具体导出字段的封装
   * @return
   */
  protected XSSFWorkbook exportPurposeLoanListExcel(List<TAppPurposeLoanVo> dataList,
      List<ExportExlColumnTypeBean> exportExlColumnTypes)
          throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
    // 创建POI工作薄
    XSSFWorkbook workbook = new XSSFWorkbook();
    // 定义 CellStyleMap
    // Map<String, XSSFCellStyle> cellStyleMap = new HashMap<String, XSSFCellStyle>();
    // 创建商户信息Sheet
    XSSFSheet sellerSheet = workbook.createSheet("商户基本信息");
    // sellerSheet.setDefaultRowHeight((short)25);
    // sellerSheet.setDefaultColumnWidth(100);
    // 表头字体
    XSSFFont font = workbook.createFont();
    font.setColor(IndexedColors.ROYAL_BLUE.getIndex());
    font.setFontHeight((short) 220);
    font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
    // 表头样式
    XSSFCellStyle cellStyle = workbook.createCellStyle();
    cellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
    cellStyle.setFont(font);
    cellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);

    // 创建第一行
    XSSFRow row0 = sellerSheet.createRow(0);
    int cellIndex = 0;
    List<Integer> groupCountList = new ArrayList<>();

    List<ExportExlColumnBean> totalColumnList = new ArrayList<>();

    // 遍历每一种类型
    for (ExportExlColumnTypeBean exlColumnType : exportExlColumnTypes) {
      List<ExportExlColumnBean> exportExlColumns = exlColumnType.getExportExlColumnBeans();
      if (exportExlColumns != null && exportExlColumns.size() > 0) {
        XSSFCell cell = row0.createCell(cellIndex);
        cell.setCellValue(exlColumnType.getText());
        cell.setCellStyle(cellStyle);
        cellIndex += exportExlColumns.size();
        groupCountList.add(exportExlColumns.size());
        totalColumnList.addAll(exportExlColumns);
      }
    }


    // 合并单元格
    if (groupCountList.size() > 0) {
      cellIndex = 0;
      for (Integer count : groupCountList) {
        if (count > 1) { // 大于1的时候进行合并操作
          sellerSheet.addMergedRegion(new CellRangeAddress(0, 0, cellIndex, cellIndex + count - 1));
          cellIndex += count;
        }
      }
    }

    // 遍历头部字段
    XSSFRow rowColumnNames = sellerSheet.createRow(1);
    for (int i = 0; i < totalColumnList.size(); i++) {
      ExportExlColumnBean columnBean = totalColumnList.get(i);
      XSSFCell cell = rowColumnNames.createCell(i);
      cell.setCellStyle(cellStyle);
      cell.setCellValue(columnBean.getText());
    }

    // 输入数据
    if (dataList != null && dataList.size() > 0) {
      for (int j = 0; j < dataList.size(); j++) {
        XSSFRow rowData = sellerSheet.createRow(j + 2);
        TAppPurposeLoanVo appPurposeLoan = dataList.get(j);
        for (int i = 0; i < totalColumnList.size(); i++) {
          XSSFCell rowDataCell = rowData.createCell(i);
          ExportExlColumnBean columnBean = totalColumnList.get(i);
          String value = org.apache.commons.beanutils.BeanUtils.getProperty(appPurposeLoan, columnBean.getName());
          rowDataCell.setCellValue(value);
        }
      }
    }
    // 宽度自适应
    for (int i = 0; i < totalColumnList.size(); i++) {
      sellerSheet.autoSizeColumn(i);
    }
    return workbook;
  }

  @Override
  public void exportPurposeLoanStatistics(OutputStream outputStream) throws Exception {
    List<PurposeLoanStatisticsDetail> statistics =
        getPurposeStatisticsDetail(new PurposeLoanQueryCnd());

    Collections.sort(statistics,
        (PurposeLoanStatisticsDetail o1, PurposeLoanStatisticsDetail o2) -> {
          if (o2.getAccountTotal() > o1.getAccountTotal()) {
            return 1;
          } else if (o2.getAccountTotal() == o1.getAccountTotal()) {
            return 0;
          } else {
            return -1;
          }
        });

    List<PurposeLoanStatisticsExportVo> exportLoans = new ArrayList<>();

    PurposeLoanStatisticsExportVo exportLoan = new PurposeLoanStatisticsExportVo();

    int index = 1;
    for (PurposeLoanStatisticsDetail statistic : statistics) {
      exportLoan.setIndex(index++);
      exportLoan.setProvName(statistic.getProvName());
      exportLoan.setAccount(statistic.getAccountTotal());
      exportLoan.setAmount(statistic.getProvsAmount().divide(new BigDecimal(100)));
      exportLoans.add(exportLoan);
      exportLoan = new PurposeLoanStatisticsExportVo();

      for (PurposeLoanCityStatistics cityInfo : statistic.getCityStatistics()) {
        exportLoan.setIndex(index++);
        exportLoan.setCityName(cityInfo.getCityName());
        exportLoan.setAccount(cityInfo.getCityAccount());
        exportLoan.setAmount(cityInfo.getCityAmount().divide(new BigDecimal(100)));
        exportLoans.add(exportLoan);
        exportLoan = new PurposeLoanStatisticsExportVo();
      }
    }

    InputStream is = getClass().getResourceAsStream("/tpl/excel/purpose_loan_statistics.xls");
    OutputStream os = outputStream;
    Context context = new Context();

    context.putVar("exportLoans", exportLoans);
    long startTime = new Date().getTime();
    JxlsHelper.getInstance().processTemplate(is, os, context);

    log.info("导出{}条统计数据，耗时{}ms", exportLoans.size(), new Date().getTime() - startTime);

  }

  /**
   * 根据审批流id，查找出各节点审批人。
   *
   * @param processId 审批流id
   * @param fpId 贷款产品id
   * @return
   * @throws Exception
   */
  private List<Map<String, Object>> getFlowNode(Integer processId, Integer fpId) throws Exception {
    // 定义返回结果
    List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
    // 根据审批流id，查找node表，返回有序的结果 (**有序：否则会影响审批**)。
    List<TApvFlowNode> list = apvFlowNodeMapper.selectApvNodesByProsID(processId);
    if (list != null) {
      // 遍历节点集合，根据节点类型查找审批人（1 按app_user_ids找审批人。2 按申请产品的金融机构找审批人 3.
      // 按申请产品找审批人）
      for (TApvFlowNode fn : list) {
        Map<String, Object> map = new HashMap<String, Object>();
        Integer nodetype = fn.getNodeType();
        // 1 按app_user_ids找审批人。2 按申请产品的金融机构找审批人 3. 按申请产品找审批人。
        if (1 == nodetype) {
          map.put("nodeId", fn.getNodeID());
          map.put("nodeName", fn.getNodeName());
          map.put("appUserIds", fn.getAppUserIDs());
          map.put("appUserNames", fn.getAppUserNames());
        } else if (2 == nodetype) {
          // 根据fpId查找所属金融机构
          MFinanceProduct mp = financeProductMapper.selectById(fpId);
          String teamName = FlowTeamPrefix.FO.getIndex() + mp.getFoId();
          // 根据标识查找flow_team表
          TApvFlowTeam ft = apvFlowTeamMapper.selectTeamByName(teamName);
          if (ft != null) {
            map.put("nodeId", fn.getNodeID());
            map.put("nodeName", fn.getNodeName());
            map.put("appUserIds", ft.getAppUserIds());
            map.put("appUserNames", ft.getAppUserNames());
          }
        } else if (3 == nodetype) {
          // 根据产品id，拼接出审批组名
          String teamName = FlowTeamPrefix.FP.toString() + fpId;
          // 根据标识查找flow_team表
          TApvFlowTeam ft = apvFlowTeamMapper.selectTeamByName(teamName);
          if (ft != null) {
            map.put("nodeId", fn.getNodeID());
            map.put("nodeName", fn.getNodeName());
            map.put("appUserIds", ft.getAppUserIds());
            map.put("appUserNames", ft.getAppUserNames());
          }
        } else {
          // 默认审批人(待定)
        }
        if (map.size() > 0) {
          resultList.add(map);
        }
      }
    }
    return resultList;
  }

  @Override
  @Transactional(readOnly = true)
  public List<TAppPurposeLoanVo> getTradeFlows(Integer[] plIds) throws Exception {
    return appPurposeLoanMapper.selectNaposResIdsByPlids(plIds);
  }

  @Override
  @Transactional(readOnly = true)
  public BigDecimal selectLoanAmountSum(PurposeLoanQueryCnd cnd) {
    return appPurposeLoanMapper.selectLoanAmountSum(cnd);
  }

  @Override
  @Transactional(readOnly = true)
  public TbData selectAllRepeatPurposeLoan(PurposeLoanQueryCnd cnd, Integer currentPage)
      throws Exception {
    // 第一次进入时，currentPage为空
    if (currentPage == null) {
      currentPage = 1;
    }
    cnd.setStartRecord((currentPage - 1) * PagerConstants.PAGE_SIZE);
    cnd.setPageSize(PagerConstants.PAGE_SIZE);
    List<TAppPurposeLoanVo> list = appPurposeLoanMapper.selectAllRepeatPurposeLoan(cnd);
    int totalCount = appPurposeLoanMapper.selectAllRepeatPurposeLoanCount(cnd);
    return initTbData(totalCount, currentPage, PagerConstants.PAGE_SIZE, list);
  }


  @Override
  public int updatePurposeLoan(TAppPurposeLoan appPurposeLoan) {
    return appPurposeLoanMapper.updateById(appPurposeLoan);
  }
}
