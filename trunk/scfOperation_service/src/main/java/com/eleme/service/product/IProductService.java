package com.eleme.service.product;

import java.sql.SQLException;
import java.util.List;

import com.eleme.domain.mart.product.MFinanceProduct;
import com.eleme.domain.mart.product.MFinanceProductVo;
import com.eleme.domain.mart.product.MProductRuleDetail;
import com.eleme.domain.mart.product.ProductQueryCnd;
import com.eleme.domain.mart.product.ProductRuleDetailCnd;
import com.eleme.domain.mart.product.ProductRuleQueryCnd;
import com.eleme.domain.mart.product.TApvFinanceProductHistory;
import com.eleme.domain.mart.product.TFcoRequestRule;
import com.eleme.util.pager.TbData;

/**
 * 融资产品接口
 * 
 * @author yonglin.zhu
 *
 */
public interface IProductService {

  /**
   * 新增融资产品信息
   * 
   * @param fp
   * @return
   * @throws SQLException
   */
  public int saveproductAdd(MFinanceProduct fp) throws Exception;

  /**
   * 根据条件分页查询融资产品信息。查询条件：产品名称,所属机构;排序:默认;
   * 
   * @param pqc 条件封装类
   * @return 融资产品集合
   */
  TbData selectProductList(ProductQueryCnd pqc, Integer currentPage) throws Exception;

  /**
   * 根据条件查询融资产品信息。查询条件：产品名称,所属机构;排序:默认;
   * 
   * @param pqc
   * @return
   * @throws Exception
   */
  List<MFinanceProductVo> selectProductList(ProductQueryCnd pqc) throws Exception;

  /**
   * 根据产品ID查询融资产品信息。查询条件：主键Key;排序:默认;
   * 
   * @param fpId
   * @return
   * @throws Exception
   */
  MFinanceProductVo selectProductById(Integer fpId) throws Exception;

  /**
   * 修改融资产品信息
   * 
   * @param fp
   * @return
   */
  int updateProductById(MFinanceProductVo fp) throws Exception;

  /**
   * 产品审核
   * 
   * @param fph
   */
  void approveProductById(TApvFinanceProductHistory fph) throws Exception;

  /**
   * 添加产品规则
   * 
   * @param rule
   * @return
   * @throws SQLException
   */
  public int ruleAdd(TFcoRequestRule rule) throws SQLException;

  /**
   * 根据产品ID查询融资产品规则信息.查询条件:产品ID;排序:默认
   * 
   * @param cnd
   * @param currentPage
   * @return
   * @throws Exception
   */
  TbData selectRuleList(ProductRuleQueryCnd cnd, Integer currentPage) throws Exception;

  /**
   * 根据产品ID查询融资产品规则信息。查询条件：主键Key;排序:默认;
   * 
   * @param ruleId
   * @return
   * @throws Exception
   */
  TFcoRequestRule selectProductRuleById(Integer ruleId) throws Exception;

  /**
   * 修改融资产品规则。
   * 
   * @param rule
   * @return 修改成功条数
   */
  int updateProductRuleById(TFcoRequestRule rule);

  /**
   * 验证融资产品规则的唯一性
   * 
   * @param cnd
   * @return
   */
  boolean checkSingleRule(ProductRuleQueryCnd cnd);

  /**
   * 根据产品ID查询融资产品规则信息.查询条件:产品ID;排序:默认
   * 
   * @param cnd
   * @param currentPage
   * @return
   * @throws Exception
   */
  TbData selectProductRuleDetailList(ProductRuleDetailCnd cnd, Integer currentPage)
      throws Exception;

  /**
   * 根据产品ID查询融资产品规则信息.查询条件:产品ID;排序:默认
   * 
   * @param cnd
   * @return
   * @throws Exception
   */
  List<MProductRuleDetail> selectProductRuleDetailList(ProductRuleDetailCnd cnd) throws Exception;

  /**
   * 
   * @param apply
   * @return
   * @throws SQLException
   */
  public int applyAdd(MProductRuleDetail apply) throws SQLException;

  /**
   * 根据主键查找产品规则明细
   * 
   * @param ruleId
   * @return
   * @throws Exception
   */
  MProductRuleDetail selectProductRuleDetailById(Integer ruleId) throws Exception;

  /**
   * 根据ID修改产品规则明细
   * 
   * @param fp
   * @return
   */
  int updateProductRuleDetailById(MProductRuleDetail apply);

  /**
   * 验证融资产品的唯一性
   * 
   * @param cnd
   * @return
   */
  boolean checkSingleProduct(ProductQueryCnd pqc);

  /**
   * 验证融资产品规则的唯一性
   * 
   * @param cnd
   * @return
   */
  boolean checkSingleProductRule(ProductRuleDetailCnd cnd);

}
