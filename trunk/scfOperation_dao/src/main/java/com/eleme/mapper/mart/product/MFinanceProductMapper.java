package com.eleme.mapper.mart.product;

import java.util.List;

import com.eleme.domain.mart.product.MFinanceProduct;
import com.eleme.domain.mart.product.MFinanceProductVo;
import com.eleme.domain.mart.product.ProductQueryCnd;

public interface MFinanceProductMapper {

	int insert(MFinanceProduct record);

	MFinanceProductVo selectById(Integer fpId);

	int updateById(MFinanceProduct record);

	/**
	 * 根据查询条件查询融资产品集合
	 * 
	 * @param pqc
	 * @return
	 */
	List<MFinanceProductVo> selectProductList(ProductQueryCnd pqc);

	/**
	 * 根据查询条件查询融资产品的总件数
	 * 
	 * @param pqc
	 * @return
	 */
	Integer selectProductListCount(ProductQueryCnd pqc);

	/**
	 * 验证是否重复，不包含本身
	 * 
	 * @param pqc
	 * @return
	 */
	Integer selectIsNotSelfCount(ProductQueryCnd pqc);
}
