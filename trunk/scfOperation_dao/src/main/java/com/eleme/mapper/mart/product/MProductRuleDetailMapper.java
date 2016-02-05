package com.eleme.mapper.mart.product;

import java.util.List;

import com.eleme.domain.mart.product.MProductRuleDetail;
import com.eleme.domain.mart.product.ProductRuleDetailCnd;

/**
 * 产品规则明细接口
 * 
 * @author yonglin.zhu
 *
 */
public interface MProductRuleDetailMapper {

	/**
	 * 新增规则明细
	 * 
	 * @param record
	 * @return
	 */
	int insert(MProductRuleDetail record);

	/**
	 * 根据ID查询产品规则明细
	 * 
	 * @param prId
	 * @return
	 */
	MProductRuleDetail selectById(Integer prId);

	/**
	 * 根据ID修改产品规则明细
	 * 
	 * @param record
	 * @return
	 */
	int updateById(MProductRuleDetail record);

	/**
	 * 根据条件查询产品规则明细.查询条件：规则类型;排序:默认
	 * 
	 * @param cnd
	 * @return
	 */
	List<MProductRuleDetail> selectListByCnd(ProductRuleDetailCnd cnd);

	/**
	 * 根据条件查询产品规则明细总件数
	 * 
	 * @param cnd
	 * @return
	 */
	Integer selectListCountByCnd(ProductRuleDetailCnd cnd);
	
	/**
	 * 根据名称查询产品规则明细总件数
	 * 
	 * @param cnd
	 * @return
	 */
	Integer selectCountByName(ProductRuleDetailCnd cnd);
}