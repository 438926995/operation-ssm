package com.eleme.service.product.impl;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.eleme.bean.file.SysUploadFileBean;
import com.eleme.constants.PagerConstants;
import com.eleme.constants.SymbolCode;
import com.eleme.domain.mart.product.MFinanceProduct;
import com.eleme.domain.mart.product.MFinanceProductVo;
import com.eleme.domain.mart.product.MProductRuleDetail;
import com.eleme.domain.mart.product.ProductQueryCnd;
import com.eleme.domain.mart.product.ProductRuleDetailCnd;
import com.eleme.domain.mart.product.ProductRuleQueryCnd;
import com.eleme.domain.mart.product.TApvFinanceProductHistory;
import com.eleme.domain.mart.product.TFcoRequestRule;
import com.eleme.domain.mart.product.TFcoRequestRuleVo;
import com.eleme.mapper.mart.product.MFinanceProductMapper;
import com.eleme.mapper.mart.product.MProductRuleDetailMapper;
import com.eleme.mapper.mart.product.TApvFinanceProductHistoryMapper;
import com.eleme.mapper.mart.product.TFcoRequestRuleMapper;
import com.eleme.service.BaseService;
import com.eleme.service.file.IFileService;
import com.eleme.service.product.IProductService;
import com.eleme.util.FileType;
import com.eleme.util.fuss.FussUtil;
import com.eleme.util.pager.TbData;

/**
 * 融资产品实现类。
 * 
 * @author yonglin.zhu
 *
 */
@Service
public class ProductServiceImpl extends BaseService implements IProductService {

  @Inject
  private MFinanceProductMapper mFinanceProductMapper;
  @Inject
  private TFcoRequestRuleMapper fcoRequestRuleMapper;
  @Inject
  private MProductRuleDetailMapper productRuleDetailMapper;
  @Inject
  private TApvFinanceProductHistoryMapper apvFinanceProductHistoryMapper;
  @Inject
  private FussUtil fussUtil;
  @Inject
  private IFileService fileService;

  @Transactional(propagation = Propagation.REQUIRED, rollbackFor = java.lang.Throwable.class)
  public int saveproductAdd(MFinanceProduct fp) throws Exception {
    // 设置添加产品条件
    fp.setCreateTime(new Date());
    fp.setReceivingDate(Integer.parseInt(fp.getPayLimit()));
    // 添加产品信息 得到fpId
    int result = mFinanceProductMapper.insert(fp);
    // 向t_sys_file_upload保存记录
    SysUploadFileBean sfb = fussUtil.getSysUploadFileBean(fp.getFpLogo(), fp.getFpId(),
        FileType.PRODUCT_BANNER.toString());
    int line = fileService.insertSysFileUpload(sfb);
    log.info("添加产品banner图, 得到影响行数：{}, 得到文件附件主键：{}", line, sfb.getUfId());
    // 更新产品表中文件主键
    MFinanceProduct product = new MFinanceProduct();
    product.setFpId(fp.getFpId());
    product.setUfId(sfb.getUfId());
    int lines = mFinanceProductMapper.updateById(product);
    log.info("更新产品表中文件主键 影响行数：{}，产品id：{}，文件主键id：{}", lines, product.getFpId(), product.getUfId());
    return result;
  }

  @Override
  public TbData selectProductList(ProductQueryCnd pqc, Integer currentPage) throws Exception {
    // MFinanceProduct record = new MFinanceProduct();
    // record.setFpId(3);
    // record.setProductStatus(1);
    // record.setFoId(3);
    // mFinanceProductMapper.updateById(record);
    // MFinanceProduct record1 = new MFinanceProduct();
    // record1.setFpId(4);
    // record1.setProductStatus(3);
    // record1.setFoId(3);
    // mFinanceProductMapper.updateById(record1);
    // 第一次进入时，currentPage为空
    if (currentPage == null) {
      currentPage = 1;
    }
    pqc.setStartRecord((currentPage - 1) * PagerConstants.PAGE_SIZE);
    pqc.setPageSize(PagerConstants.PAGE_SIZE);
    List<MFinanceProductVo> productList = mFinanceProductMapper.selectProductList(pqc);
    int totalCount = mFinanceProductMapper.selectProductListCount(pqc);
    return initTbData(totalCount, currentPage, PagerConstants.PAGE_SIZE, productList);
  }

  @Override
  public MFinanceProductVo selectProductById(Integer fpId) throws Exception {
    return mFinanceProductMapper.selectById(fpId);
  }

  @Override
  @Transactional(rollbackFor = java.lang.Throwable.class)
  public int updateProductById(MFinanceProductVo fp) throws Exception {
    MultipartFile mfile = fp.getFpLogo();
    if (mfile != null && !mfile.isEmpty()) {
      SysUploadFileBean sfb =
          fussUtil.getSysUploadFileBean(mfile, fp.getFpId(), FileType.PRODUCT_BANNER.toString());
      int line = fileService.insertSysFileUpload(sfb);
      log.info("更换产品banner图, 得到影响行数：{}, 得到文件附件主键：{}", line, sfb.getUfId());
      fp.setUfId(sfb.getUfId());
    }
    int result = mFinanceProductMapper.updateById(fp);
    return result;
  }

  @Override
  @Transactional(rollbackFor = java.lang.Throwable.class)
  public void approveProductById(TApvFinanceProductHistory fph) throws Exception {
    apvFinanceProductHistoryMapper.insert(fph);
    MFinanceProductVo fp = new MFinanceProductVo();
    fp.setFpId(fph.getFpId());
    fp.setProductStatus(Integer.valueOf(fph.getAppStatus()));
    updateProductById(fp);
  }


  @Override
  public int ruleAdd(TFcoRequestRule rule) throws SQLException {
    rule.setFromSymbol(SymbolCode.SymbolCode_2002.getIndex() + "");
    rule.setEndSymbol(SymbolCode.SymbolCode_2004.getIndex() + "");
    return fcoRequestRuleMapper.insert(rule);
  }

  @Override
  public TbData selectRuleList(ProductRuleQueryCnd cnd, Integer currentPage) throws Exception {
    // 第一次进入时，currentPage为空
    if (currentPage == null) {
      currentPage = 1;
    }
    cnd.setStartRecord((currentPage - 1) * PagerConstants.PAGE_SIZE);
    cnd.setPageSize(PagerConstants.PAGE_SIZE);
    List<TFcoRequestRuleVo> ruleList = fcoRequestRuleMapper.selectRuleListByFpId(cnd);
    int totalCount = fcoRequestRuleMapper.selectRuleListCountByFpId(cnd);
    return initTbData(totalCount, currentPage, PagerConstants.PAGE_SIZE, ruleList);
  }

  @Override
  public TFcoRequestRule selectProductRuleById(Integer ruleId) throws Exception {
    return fcoRequestRuleMapper.selectById(ruleId);
  }

  @Override
  public int updateProductRuleById(TFcoRequestRule rule) {
    return fcoRequestRuleMapper.updateById(rule);
  }

  @Override
  public boolean checkSingleRule(ProductRuleQueryCnd cnd) {
    Integer count = fcoRequestRuleMapper.selectRuleCountByCnd(cnd);
    if (count.intValue() > 0) {
      return true;
    }
    return false;
  }

  @Override
  public List<MFinanceProductVo> selectProductList(ProductQueryCnd pqc) throws Exception {
    return mFinanceProductMapper.selectProductList(pqc);
  }

  @Override
  public TbData selectProductRuleDetailList(ProductRuleDetailCnd cnd, Integer currentPage)
      throws Exception {
    // 第一次进入时，currentPage为空
    if (currentPage == null) {
      currentPage = 1;
    }
    cnd.setStartRecord((currentPage - 1) * PagerConstants.PAGE_SIZE);
    cnd.setPageSize(PagerConstants.PAGE_SIZE);
    List<MProductRuleDetail> list = productRuleDetailMapper.selectListByCnd(cnd);
    int totalCount = productRuleDetailMapper.selectListCountByCnd(cnd);
    return initTbData(totalCount, currentPage, PagerConstants.PAGE_SIZE, list);
  }

  @Override
  public List<MProductRuleDetail> selectProductRuleDetailList(ProductRuleDetailCnd cnd)
      throws Exception {
    return productRuleDetailMapper.selectListByCnd(cnd);
  }

  @Override
  public int applyAdd(MProductRuleDetail apply) throws SQLException {
    apply.setCreateTime(new Date());
    apply.setDelFlag(1);
    return productRuleDetailMapper.insert(apply);
  }

  @Override
  public MProductRuleDetail selectProductRuleDetailById(Integer ruleId) throws Exception {
    return productRuleDetailMapper.selectById(ruleId);
  }

  @Override
  public int updateProductRuleDetailById(MProductRuleDetail apply) {
    return productRuleDetailMapper.updateById(apply);
  }

  @Override
  public boolean checkSingleProduct(ProductQueryCnd pqc) {
    Integer count = mFinanceProductMapper.selectIsNotSelfCount(pqc);
    return count.intValue() == 0 ? true : false;
  }

  @Override
  public boolean checkSingleProductRule(ProductRuleDetailCnd cnd) {
    Integer count = productRuleDetailMapper.selectCountByName(cnd);
    return count.intValue() == 0 ? true : false;
  }

}
