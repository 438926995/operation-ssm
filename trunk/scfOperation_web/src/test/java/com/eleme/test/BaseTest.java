package com.eleme.test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.jxls.common.Context;
import org.jxls.util.JxlsHelper;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.eleme.domain.mart.loan.TAppSellerLoanVo;
import com.eleme.domain.mart.seller.TSdcTradeFlow;
import com.eleme.mapper.mart.seller.TSdcTradeFlowMapper;
import com.eleme.service.loan.ISellerLoanService;
import com.eleme.util.FileUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
    locations = {"classpath:applicationContext.xml", "classpath:applicationContext-pylon.xml",
        "classpath:applicationContext-rest.xml", "classpath:applicationContext-security.xml"})
public class BaseTest {

  @Inject
  private ISellerLoanService sellerLoanService;

  @Inject
  private TSdcTradeFlowMapper tradeFlowMapper;

//  @Test
//  public void TestPassword() {
//
//    try {
//      // 获取贷款信息
//      TAppSellerLoanVo appSellerLoan = sellerLoanService.selectSellerLoanById(9, null);
//      // 获取流水信息
//      List<TSdcTradeFlow> tradeFlows = tradeFlowMapper.selectBySellerId(16);
//      // 将贷款信息放入list中
//      List<TAppSellerLoanVo> sellerLoans = new ArrayList<TAppSellerLoanVo>();
//      sellerLoans.add(appSellerLoan);
//      // 读取导出Excel模板
//      InputStream is = getClass().getResourceAsStream("mail_seller_info.xlsx");
//      FileUtil.createDir("/Users/sunwei/temp/");
//      // 设置导出Excel路径
//      OutputStream os = new FileOutputStream("/Users/sunwei/temp/aa.xlsx");
//
//      // 创建导出时Jxls的上下文
//      Context context = new Context();
//      // 设置 sellerLoans 商户贷款变量
//      context.putVar("sellerLoans", sellerLoans);
//      // 设置 tradeFlows 商户流水变量
//      context.putVar("tradeFlows", tradeFlows);
//
//      // 开始导出
//      JxlsHelper.getInstance().processTemplate(is, os, context);
//
//      // 完成导出之后返回生成文件路径
//    } catch (Exception ex) {
//      ex.printStackTrace();
//    }
//  }
}
