package com.eleme.mapper.ops.product;

import java.util.List;

import com.eleme.bean.product.MFinanceProduct;
import com.eleme.bean.product.ProductQueryBean;
import com.eleme.bean.product.ProductRuleDetail;

public interface ProductMapper {
  
  public List<MFinanceProduct> selectProductList(ProductQueryBean pqb);
  
  public int selectProductCount(ProductQueryBean pqb);
  
  public MFinanceProduct selectProductInfoByFpId(Integer fpId);
  
  public int updateProductToDelete(Integer fpId);
  
  public int updateProduct(MFinanceProduct mfp);

  public int insertProduct(MFinanceProduct mfp);

  public List<ProductRuleDetail> selectProductRuleDetailByFpId(Integer fpId);

  public int insertProductRule(ProductRuleDetail prd);

  public int updateProductRule(ProductRuleDetail prd);

  public int deleteProductRuleById(Integer id);

  public ProductRuleDetail selectProductRuleByPrId(Integer prId);

  public List<MFinanceProduct> selectAllProductList();

}
