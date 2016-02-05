package com.eleme.service.seller;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import com.eleme.bean.JSONMessage;
import com.eleme.bean.seller.PurposeSellerQueryBean;
import com.eleme.bean.seller.SellerQueryBean;
import com.eleme.domain.mart.PageResData;
import com.eleme.domain.mart.seller.ExportExlQueryBean;
import com.eleme.domain.mart.seller.MNaposSeller;
import com.eleme.domain.mart.seller.Seller;
import com.eleme.util.pager.TbData;

/**
 * 商户信息管理接口.
 * 
 * @author penglau
 *
 */
public interface ISellerService {

  /**
   * 获取商户列表. 取代方法 {@code getSellerPageData(SellerQueryBean queryBean)}
   * 
   * @param currentPage 当前页
   * @param sqb 商户列表查询封装类.
   * @return
   * @throws Exception
   */
  @Deprecated
  public TbData getSellerList(Integer currentPage, SellerQueryBean sqb) throws Exception;

  /**
   * 获取商户分页数据
   * 
   * @author sunwei
   * @since 2016年1月4日
   * @return
   */
  public PageResData<Seller> getSellerPageData(SellerQueryBean queryBean) throws Exception;

  /**
   * 导出商户及其流水数据
   * 
   * @param sqb
   * @return
   */
  public XSSFWorkbook exportSellerAndFundsFlowBySellIds(ExportExlQueryBean sqb);

  /**
   * 根据商户id获取其商户流水明细
   * 
   * @author sunwei
   * @since 2016年1月6日
   * @param sellerId
   * @param outputStream
   */
  public void exportSellerTradeFlowDetail(Integer sellerId, OutputStream outputStream);

  /**
   * 获取意向商户列表.
   * 
   * @param currentPage 当前页
   * @param psqb 商户列表查询封装类.
   * @return
   * @throws Exception
   */
  public TbData getPurposeSellerList(Integer currentPage, PurposeSellerQueryBean psqb)
      throws Exception;

  /**
   * 调用napos组提供的餐厅信息接口，并返回封装接口。
   * 
   * @param res_name 餐厅名称
   * @param mobile 手机号
   * @param res_addr 餐厅地址
   * @param napos_resid napos餐厅id
   * @return map<String, Object>.put(list).put(isSuccess).put(failureMess)
   * @throws Exception
   */
  public Map<String, Object> remoteCallNaposResAPI(String res_name, String mobile, String res_addr,
      String napos_resid) throws Exception;

  /**
   * 导入意向商户。
   * 
   * @param purposeSellerExcel 意向商户文件
   * @param curUserId 添加人
   * @return
   * @throws IOException
   */
  public JSONMessage purposeSellerImport(MultipartFile purposeSellerExcel, long curUserId)
      throws Exception;

  /**
   * 插入napos商户记录（暂无seller_id,user_id的关联）
   * 
   * @param mns napos商户基础表实体类.
   * @return
   * @throws IOException
   */
  public JSONMessage saveSellerInfo(MNaposSeller mns) throws Exception;

  /**
   * 根据napos餐厅id查找napos商户基础表
   * 
   * @param naposResId napos餐厅id
   * @return napos商户基础表对应的封装类.
   */
  public MNaposSeller findMNaposSellerByNaposResId(String naposResId) throws Exception;

  /**
   * 判断指定napos餐厅id的餐厅是否存在
   * 
   * @param naposResId napos餐厅id
   * @return 存在 true，不存在false.
   */
  public boolean judgeMNaposSellerExistsByNaposResId(String naposResId) throws Exception;

  /**
   * 获取商户基本信息
   * 
   * @author sunwei
   * @since 2016年1月5日
   * @param sellerId
   */
  public Seller getSellerInfoById(Integer sellerId);



}
