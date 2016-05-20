package com.eleme.service.product.impl;

import java.io.OutputStream;
import java.util.List;

import javax.inject.Inject;

import com.eleme.bean.loan.LoanQueryBean;
import com.eleme.bean.product.ProductRuleDetail;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import com.eleme.bean.product.MFinanceProduct;
import com.eleme.bean.product.ProductQueryBean;
import com.eleme.constants.PagerConstants;
import com.eleme.mapper.ops.product.ProductMapper;
import com.eleme.service.BaseService;
import com.eleme.service.product.IProductService;
import com.eleme.util.pager.TbData;

@Service
public class ProductServiceImpl extends BaseService implements IProductService {

    @Inject
    private ProductMapper productMapper;

    @Override
    public TbData getProductList(Integer currentPage, ProductQueryBean pqb) {
        currentPage = (currentPage == null) ? 1 : currentPage;
        pqb.setOffset((currentPage - 1) * PagerConstants.PAGE_SIZE);
        pqb.setLimit(PagerConstants.PAGE_SIZE);
        List<MFinanceProduct> productList = productMapper.selectProductList(pqb);
        int totalCount = productMapper.selectProductCount(pqb);
        return initTbData(totalCount, currentPage, PagerConstants.PAGE_SIZE, productList);
    }

    @Override
    public MFinanceProduct getProductInfoByFpId(Integer fpId) {
        MFinanceProduct mFinanceProduct = productMapper.selectProductInfoByFpId(fpId);
        List<ProductRuleDetail> ruleDetails = productMapper.selectProductRuleDetailByFpId(fpId);
        mFinanceProduct.setRuleList(ruleDetails);
        return mFinanceProduct;
    }

    @Override
    public int deleteProductByFpId(Integer fpId) {
        return productMapper.updateProductToDelete(fpId);
    }

    @Override
    public int editProduct(MFinanceProduct mfp) {
        return productMapper.updateProduct(mfp);
    }

    @Override
    public int addProduct(MFinanceProduct mfp) {
        return productMapper.insertProduct(mfp);
    }

    @Override
    public int addProductRule(ProductRuleDetail prd) {
        return productMapper.insertProductRule(prd);
    }

    @Override
    public int updateProductRule(ProductRuleDetail prd) {
        return productMapper.updateProductRule(prd);
    }

    @Override
    public int deleteProductRuleById(Integer id) {
        return productMapper.deleteProductRuleById(id);
    }

    @Override
    public ProductRuleDetail getProductRuleDetailById(Integer prId) {
        return productMapper.selectProductRuleByPrId(prId);
    }

    @Override
    public List<MFinanceProduct> getAllProductList() {
        return productMapper.selectAllProductList();
    }

}
