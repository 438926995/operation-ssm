package com.eleme.service.product;

import com.eleme.bean.loan.LoanQueryBean;
import com.eleme.bean.product.MFinanceProduct;
import com.eleme.bean.product.ProductQueryBean;
import com.eleme.bean.product.ProductRuleDetail;
import com.eleme.util.pager.TbData;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.OutputStream;
import java.util.List;

public interface IProductService {
  
  public TbData getProductList(Integer currentPage, ProductQueryBean pqb);
  
  public MFinanceProduct getProductInfoByFpId(Integer fpId);
  
  public int deleteProductByFpId(Integer fpId);
  
  public int editProduct(MFinanceProduct mfp);

  public int addProduct(MFinanceProduct mfp);

  public int addProductRule(ProductRuleDetail prd);

  public int updateProductRule(ProductRuleDetail prd);

  public int deleteProductRuleById(Integer id);

  public ProductRuleDetail getProductRuleDetailById(Integer prId);

  public List<MFinanceProduct> getAllProductList();

}
