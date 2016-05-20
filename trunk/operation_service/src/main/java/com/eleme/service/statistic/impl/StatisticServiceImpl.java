package com.eleme.service.statistic.impl;

import com.eleme.bean.echart.OptionBean;
import com.eleme.bean.feedback.FeedbackQueryBean;
import com.eleme.bean.feedback.ReplyFeedback;
import com.eleme.bean.feedback.UserFeedback;
import com.eleme.bean.loan.LoanAudit;
import com.eleme.bean.loan.LoanQueryBean;
import com.eleme.mapper.ops.loan.LoanAuditMapper;
import com.eleme.mapper.ops.statistic.StatisticMapper;
import com.eleme.service.BaseService;
import com.eleme.service.statistic.IStatisticService;
import com.eleme.util.DateUtil;
import com.eleme.util.JsonUtil;
import com.eleme.util.PagingUtil;
import com.eleme.util.pager.TbData;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.*;

/**
 * Created by huwenwen on 16/5/14.
 */
@Service
public class StatisticServiceImpl extends BaseService implements IStatisticService {

    /**
     *
     */
    @Inject
    private LoanAuditMapper loanAuditMapper;

    @Inject
    private StatisticMapper statisticMapper;

    @Override
    public Map<String, Object> getLoanStatistic(LoanQueryBean lqb) {
        lqb.setAppStatus(null);
        List<LoanAudit> loanAuditList = loanAuditMapper.selectLoanAudit(lqb);
        lqb.setAppStatus("C");
        List<LoanAudit> passList = loanAuditMapper.selectLoanAudit(lqb);
        lqb.setAppStatus("D");
        List<LoanAudit> disPassList = loanAuditMapper.selectLoanAudit(lqb);
        lqb.setAppStatus("P");
        List<LoanAudit> auditList = loanAuditMapper.selectLoanAudit(lqb);
        for (int i = 0; i < loanAuditList.size(); i++) {
            LoanAudit loanAudit = loanAuditList.get(i);
            loanAudit.setPassCount(passList.get(i).getSumCount());
            loanAudit.setDisPassCount(disPassList.get(i).getSumCount());
            loanAudit.setAuditCount(auditList.get(i).getSumCount());
        }
        List<LoanAudit> result = PagingUtil.handPaging(loanAuditList, lqb.getCurrentPage(), lqb.getPageSize());
        TbData tbData = initTbData(loanAuditList.size(), lqb.getCurrentPage(), lqb.getPageSize(), result);
        Map<String, Object> map = new HashMap<>();
        map.put("tbData", tbData);
        map.put("opt", getLoanStatisticOpt(loanAuditList, lqb));
        map.put("loanTotal", getTotalLoanNumber(loanAuditList));
        return map;
    }

    @Override
    public XSSFWorkbook exportAppNumberInfo(LoanQueryBean lqb) {
        lqb.setAppStatus(null);
        List<LoanAudit> loanAuditList = loanAuditMapper.selectLoanAudit(lqb);
        lqb.setAppStatus("C");
        List<LoanAudit> passList = loanAuditMapper.selectLoanAudit(lqb);
        lqb.setAppStatus("D");
        List<LoanAudit> disPassList = loanAuditMapper.selectLoanAudit(lqb);
        lqb.setAppStatus("P");
        List<LoanAudit> auditList = loanAuditMapper.selectLoanAudit(lqb);
        for (int i = 0; i < loanAuditList.size(); i++) {
            LoanAudit loanAudit = loanAuditList.get(i);
            loanAudit.setPassCount(passList.get(i).getSumCount());
            loanAudit.setDisPassCount(disPassList.get(i).getSumCount());
            loanAudit.setAuditCount(auditList.get(i).getSumCount());
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
        String[] tableHead = {"时间", "提交申请人数", "通过人数", "未通过人数", "审批中人数"};
        XSSFRow row0 = sellerSheet.createRow(0);
        for (int i = 0; i < tableHead.length; i++) {
            XSSFCell cell = row0.createCell(i);
            cell.setCellStyle(cellStyle);
            cell.setCellValue(tableHead[i]);
        }
        // 生成excel数据
        for(int i = 0; i < loanAuditList.size(); i++){
            XSSFRow row = sellerSheet.createRow(i + 1);
            LoanAudit loanAudit = loanAuditList.get(i);
            List<Object> data = new ArrayList<>();
            data.add(DateUtil.dateFormatDateString(loanAudit.getSubmitTime()));
            data.add(loanAudit.getSumCount());
            data.add(loanAudit.getPassCount());
            data.add(loanAudit.getDisPassCount());
            data.add(loanAudit.getAuditCount());
            for (int j = 0; j < data.size(); j++) {
                XSSFCell cell = row.createCell(j);
                cell.setCellValue(data.get(j).toString());
            }
        }
        // 自动调整cell大小
        for (int i = 0; i < tableHead.length; i++) {
            sellerSheet.autoSizeColumn(i);
        }
        return workbook;
    }

    @Override
    public TbData getFeedbackInfo(FeedbackQueryBean fqb) {
        List<UserFeedback> userFeedbackList = statisticMapper.selectUserFeedbakList(fqb);
        int count = statisticMapper.selectUserFeedbackListCount(fqb);
        TbData tbData = initTbData(count, fqb.getCurrentPage(), fqb.getPageSize(), userFeedbackList);
        return tbData;
    }

    @Override
    public int replyFeedback(ReplyFeedback rf) {
        rf.setReplyTime(new Date());
        return statisticMapper.updateUserFeedbackToReply(rf);
    }

    private OptionBean getLoanStatisticOpt(List<LoanAudit> list, LoanQueryBean lqb) {
        OptionBean opt = new OptionBean();
        Collections.reverse(list);
        // 补0
        Date end = lqb.getSubmitTimeEnd();
        Date from = lqb.getSubmitTimeFrom();
        if (from == null && !list.isEmpty()) {
            from = list.get(0).getSubmitTime();
            end = new Date();
        } else if (list.isEmpty()) {
            from = end;
        }
        long interval = end.getTime() - from.getTime();
        int temp = (int) (interval / (24 * 3600 * 1000));
        for (int i = 0; i < temp + 1; i++) {
            Date time = null;
            if (i < list.size()) {
                time = list.get(i).getSubmitTime();
            }
            Calendar cal = Calendar.getInstance();
            cal.setTime(from);
            cal.add(Calendar.DATE, i);
            if (!cal.getTime().equals(time)) {
                LoanAudit la = new LoanAudit();
                la.setSubmitTime(cal.getTime());
                list.add(i, la);
            }
        }

        List<String> xList = new ArrayList<>();
        List<Integer> yList1 = new ArrayList<>();
        List<Integer> yList2 = new ArrayList<>();
        List<Integer> yList3 = new ArrayList<>();
        List<Integer> yList4 = new ArrayList<>();
        for (LoanAudit la : list) {
            xList.add(DateUtil.dateFormatDateString(la.getSubmitTime()));
            yList1.add(la.getSumCount());
            yList2.add(la.getPassCount());
            yList3.add(la.getDisPassCount());
            yList4.add(la.getAuditCount());
        }
        List<String> seriesData = new ArrayList<>();
        seriesData.add(JsonUtil.toJson(yList1));
        seriesData.add(JsonUtil.toJson(yList2));
        seriesData.add(JsonUtil.toJson(yList3));
        seriesData.add(JsonUtil.toJson(yList4));
        opt.setxAisData(JsonUtil.toJson(xList));
        opt.setSeriesData(seriesData);
        return opt;
    }

    private LoanAudit getTotalLoanNumber(List<LoanAudit> list) {
        LoanAudit loanAudit = new LoanAudit();
        int sumCount = 0;
        int passCount = 0;
        int disPassCount = 0;
        int auditCount = 0;
        for (LoanAudit la : list) {
            sumCount += la.getSumCount();
            passCount += la.getPassCount();
            disPassCount += la.getDisPassCount();
            auditCount += la.getAuditCount();
        }
        loanAudit.setSumCount(sumCount);
        loanAudit.setPassCount(passCount);
        loanAudit.setDisPassCount(disPassCount);
        loanAudit.setAuditCount(auditCount);
        return loanAudit;
    }
}
