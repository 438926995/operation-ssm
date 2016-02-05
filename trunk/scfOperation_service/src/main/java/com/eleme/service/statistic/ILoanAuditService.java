package com.eleme.service.statistic;

import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.eleme.bean.statistic.LoanQueryBean;
import com.eleme.bean.statistic.OptionBean;
import com.eleme.bean.statistic.ProductName;
import com.eleme.domain.mart.statistic.LoanNumberBean;
import com.eleme.util.pager.TbData;

/**
 * 贷款审核统计接口
 * 
 * @author huwenwen
 *
 */
public interface ILoanAuditService {
  /**
   * 获取贷款审核统计列表
   * 
   * @param limitFlg 是否要分页
   * @param lqb 查询条件
   * @param currentPage 当前页
   * @return
   */
  public TbData getStatisticList(Integer currentPage, LoanQueryBean lqb, boolean limitFlg);

  /**
   * 获得贷款产品的名称
   * 
   */
  public List<ProductName> selectProductList();

  /**
   * 获得总共提交人数
   * @return
   */
  public LoanNumberBean getNumberTotal();

  /**
   * 导出用户提交人数的详细数据
   */
  public XSSFWorkbook exportMessage(LoanQueryBean lqb, boolean limitFlg);
  /**
   * 封装Echarts 的Option
   */
  public OptionBean getOption(LoanQueryBean lqb, boolean limitFlg);
  
}
