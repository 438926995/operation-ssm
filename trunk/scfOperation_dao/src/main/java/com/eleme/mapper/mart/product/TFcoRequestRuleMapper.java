package com.eleme.mapper.mart.product;

import java.util.List;

import com.eleme.domain.mart.product.ProductRuleQueryCnd;
import com.eleme.domain.mart.product.TFcoRequestRule;
import com.eleme.domain.mart.product.TFcoRequestRuleVo;

/**
 * 融资产品规则接口
 * 
 * @author yonglin.zhu
 *
 */
public interface TFcoRequestRuleMapper {

  /**
   * 新增融资产品规则
   * 
   * @param record
   * @return
   */
  int insert(TFcoRequestRule record);

  /**
   * 根据ID查询融资产品规则
   * 
   * @param ruleId
   * @return
   */
  TFcoRequestRule selectById(Integer ruleId);

  /**
   * 修改融资产品规则
   * 
   * @param record
   * @return
   */
  int updateById(TFcoRequestRule record);

  /**
   * 根据产品ID分页查询规则集合，查询条件:产品ID;排序:默认
   * 
   * @param cnd
   * @return
   */
  List<TFcoRequestRuleVo> selectRuleListByFpId(ProductRuleQueryCnd cnd);

  /**
   * 根据产品ID查询规则条数
   * 
   * @param cnd
   * @return
   */
  Integer selectRuleListCountByFpId(ProductRuleQueryCnd cnd);

  /**
   * 根据条件查询融资产品规则的件数
   * 
   * @param cnd
   * @return
   */
  Integer selectRuleCountByCnd(ProductRuleQueryCnd cnd);
}
