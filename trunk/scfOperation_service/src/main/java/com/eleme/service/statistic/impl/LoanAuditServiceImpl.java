package com.eleme.service.statistic.impl;

import static com.eleme.util.POIUtil.getDefaultStringStyle;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eleme.bean.statistic.LoanQueryBean;
import com.eleme.bean.statistic.OptionBean;
import com.eleme.bean.statistic.ProductName;
import com.eleme.constants.PagerConstants;
import com.eleme.domain.mart.statistic.LoanAudit;
import com.eleme.domain.mart.statistic.LoanNumberBean;
import com.eleme.mapper.mart.statistic.ILoanAuditMapper;
import com.eleme.service.BaseService;
import com.eleme.service.statistic.ILoanAuditService;
import com.eleme.util.JsonUtil;
import com.eleme.util.pager.TbData;

/**
 * 贷款审核统计实现类
 * 
 * @author huwenwen
 *
 */
@Service
public class LoanAuditServiceImpl extends BaseService implements ILoanAuditService {

  @Inject
  private ILoanAuditMapper loanAuditMapper;

  @Override
  @Transactional(readOnly = true)
  public TbData getStatisticList(Integer currentPage, LoanQueryBean lqb, boolean limitFlg) {
    // 第一次进入时，currentPage为空
    if (currentPage == null) {
      currentPage = 1;
    }
    // 封装sql查询条件
    Map<String, Object> map = new HashMap<String, Object>();
    // 封装之前判断时间段是否超出
    if (lqb.getPeriod() != null) {
      long endTime = lqb.getSubmitTimeEnd().getTime();
      long fromTime = lqb.getSubmitTimeFrom().getTime();
      long period = ((endTime - fromTime) / (24 * 60 * 60 * 1000));
      if (period > lqb.getPeriod()) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(lqb.getSubmitTimeEnd());
        calendar.add(Calendar.DAY_OF_MONTH, -(lqb.getPeriod() - 1));
        map.put("submitTimeFrom", calendar.getTime());
      } else {
        map.put("submitTimeFrom", lqb.getSubmitTimeFrom());
      }
    } else {
      map.put("submitTimeFrom", lqb.getSubmitTimeFrom());
    }
    map.put("submitTimeEnd", lqb.getSubmitTimeEnd());
    map.put("fpId", lqb.getFpId());
    map.put("startRecord", (currentPage - 1) * PagerConstants.PAGE_SIZE);
    map.put("pageSize", PagerConstants.PAGE_SIZE);
    map.put("appStatus", null);
    map.put("limitFlg", limitFlg);
    List<LoanAudit> loanAuditList = loanAuditMapper.loanAuditList(map);
    // 通过审核的人数
    map.put("appStatus", "C");
    List<LoanAudit> loanAuditList2 = loanAuditMapper.loanAuditList(map);
    // 未通过审核的人数
    map.put("appStatus", "D");
    List<LoanAudit> loanAuditList3 = loanAuditMapper.loanAuditList(map);
    // 审核中的人数
    map.put("appStatus", "P");
    List<LoanAudit> loanAuditList4 = loanAuditMapper.loanAuditList(map);

    // 将人数全部统计到loanAuditList中
    for (int i = 0; i < loanAuditList.size(); i++) {
      // 通过
      loanAuditList.get(i).setPassNumber(loanAuditList2.get(i).getCount());
      // 未通过
      loanAuditList.get(i).setDisPassNumber(loanAuditList3.get(i).getCount());
      // 审核中
      loanAuditList.get(i).setAuditNumber(loanAuditList4.get(i).getCount());
    }
    int totalCount = loanAuditMapper.LoanTotalNum(map);
    return initTbData(totalCount, currentPage, PagerConstants.PAGE_SIZE, loanAuditList);
  }

  @Override
  public OptionBean getOption(LoanQueryBean lqb, boolean limitFlg) {
    // 得到数据
    TbData tbData = getStatisticList(0, lqb, limitFlg);
    @SuppressWarnings("unchecked")
    List<LoanAudit> loanAuditList = (List<LoanAudit>) tbData.getList();
    // loanAuditList排序，按时间从小到大
    Collections.sort(loanAuditList, new Comparator<LoanAudit>() {
      @Override
      public int compare(LoanAudit o1, LoanAudit o2) {
        return o1.getSubmitTime().after(o2.getSubmitTime()) ? 1 : -1;
      }
    });
    // 得到x和y轴数据
    OptionBean opt = new OptionBean();
    List<String> list = new ArrayList<String>();
    // 获得x数据
    for (int i = 0; i < loanAuditList.size(); i++) {
      LoanAudit loanAudit = loanAuditList.get(i);
      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
      String submitTime = sdf.format(loanAudit.getSubmitTime());
      list.add(submitTime);
    }
    String xjson = JsonUtil.toJson(list);
    opt.setxAisData(xjson);
    // 获得y提交申请人数
    List<Integer> list1 = new ArrayList<Integer>();
    for (int i = 0; i < loanAuditList.size(); i++) {
      LoanAudit loanAudit = loanAuditList.get(i);
      list1.add(loanAudit.getCount());
    }
    String sjson1 = JsonUtil.toJson(list1);
    // 获得y通过人数
    List<Integer> list2 = new ArrayList<Integer>();
    for (int i = 0; i < loanAuditList.size(); i++) {
      LoanAudit loanAudit = loanAuditList.get(i);
      list2.add(loanAudit.getPassNumber());
    }
    String sjson2 = JsonUtil.toJson(list2);
    // 获得y未通过人数
    List<Integer> list3 = new ArrayList<Integer>();
    for (int i = 0; i < loanAuditList.size(); i++) {
      LoanAudit loanAudit = loanAuditList.get(i);
      list3.add(loanAudit.getDisPassNumber());
    }
    String sjson3 = JsonUtil.toJson(list3);
    // 获得y审核中人数
    List<Integer> list4 = new ArrayList<Integer>();
    for (int i = 0; i < loanAuditList.size(); i++) {
      LoanAudit loanAudit = loanAuditList.get(i);
      list4.add(loanAudit.getAuditNumber());
    }
    String sjson4 = JsonUtil.toJson(list4);
    List<String> seriesData = new ArrayList<String>();
    seriesData.add(sjson1);
    seriesData.add(sjson2);
    seriesData.add(sjson3);
    seriesData.add(sjson4);
    opt.setSeriesData(seriesData);
    return opt;
  }

  @Override
  public List<ProductName> selectProductList() {
    return loanAuditMapper.productList(null);
  }

  @Override
  public XSSFWorkbook exportMessage(LoanQueryBean lqb, boolean limitFlg) {
    // 查询所有符合条件数据
    TbData tbData = getStatisticList(0, lqb, limitFlg);
    @SuppressWarnings("unchecked")
    List<LoanAudit> loanAuditList = (List<LoanAudit>) tbData.getList();
    // 封装sql查询条件
    Map<String, Object> map = new HashMap<String, Object>();
    map.put("fpId", lqb.getFpId());
    List<ProductName> productNameList = loanAuditMapper.productList(map);
    // 设置贷款产品名称
    String productName;
    if (productNameList.size() == 1) {
      productName = productNameList.get(0).getFp_name();
    } else {
      productName = "全部";
    }
    // 创建POI工作薄
    XSSFWorkbook workbook = new XSSFWorkbook();
    // 创建关键指标详细数据Sheet
    XSSFSheet sellerSheet = workbook.createSheet("关键指标详细数据");
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
    // 生成表头
    String[] tableHead = {"贷款产品", "时间", "提交申请人数", "通过人数", "未通过人数", "审批中人数"};
    XSSFRow row0 = sellerSheet.createRow(0);
    for (int i = 0; i < tableHead.length; i++) {
      XSSFCell cell = row0.createCell(i);
      cell.setCellStyle(cellStyle);
      cell.setCellValue(tableHead[i]);
    }
    // 生成excel数据
    for (int i = 0; i < loanAuditList.size(); i++) {
      XSSFRow row = sellerSheet.createRow(i + 1);
      LoanAudit loanAudit = loanAuditList.get(i);
      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
      String submitTime = sdf.format(loanAudit.getSubmitTime());
      String[] data =
          {productName, submitTime, loanAudit.getCount() + "", loanAudit.getPassNumber() + "",
              loanAudit.getDisPassNumber() + "", loanAudit.getAuditNumber() + ""};
      for (int j = 0; j < data.length; j++) {
        XSSFCell cell = row.createCell(j);
        cell.setCellStyle(getDefaultStringStyle(workbook));
        cell.setCellValue(data[j]);
      }
    }
    // 自动调整cell大小
    for (int i = 0; i < tableHead.length; i++) {
      sellerSheet.autoSizeColumn(i);
    }
    return workbook;
  }

  @Override
  public LoanNumberBean getNumberTotal() {
    List<LoanAudit> selectTotalApplyInfo = loanAuditMapper.selectTotalApplyInfo();
    LoanNumberBean loanNumberBean = new LoanNumberBean();
    // 记录提交申请人数
    int sumNumber = 0;
    // 记录通过人数
    int passNumber = 0;
    // 记录未通过人数
    int disPassNumber = 0;
    // 记录审核中人数
    int auditNumber = 0;
    for (LoanAudit loanAudit : selectTotalApplyInfo) {
      if ("C".equals(loanAudit.getAppStatus())) {
        passNumber++;
        sumNumber++;
      } else if("D".equals(loanAudit.getAppStatus())){
        disPassNumber++;
        sumNumber++;
      } else if("P".equals(loanAudit.getAppStatus())){
        auditNumber++;
        sumNumber++;
      }
    }
    loanNumberBean.setPassNumber(passNumber);
    loanNumberBean.setDisPassNumber(disPassNumber);
    loanNumberBean.setAuditNumber(auditNumber);
    loanNumberBean.setSumNumber(sumNumber);
    return loanNumberBean;
  }

}
