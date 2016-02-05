package com.eleme.mapper.mart.seller;

import java.util.List;
import java.util.Map;

import com.eleme.domain.mart.seller.MNaposSeller;
import com.eleme.domain.mart.seller.PurposeSeller;
import com.eleme.domain.mart.seller.Seller;

/**
 * 商户管理接口.
 * 
 * @author penglau
 *
 */
public interface ISellerMapper {

  /**
   * 查询所有符合条件的商户集合（暂只查有效）.
   * 
   * @return 商户对象集合
   * @throws Exception 异常
   */
  public List<Seller> sellerList(Map<String, Object> map) throws Exception;

  /**
   * 查询符合条件的商户总数（暂只查有效）.
   * 
   * @return 数量
   * @throws Exception 异常
   */
  public int sellerTotalNum(Map<String, Object> map) throws Exception;

  /**
   * 查询所有导入的意向商户.
   * 
   * @return 已导入的意向商户集合
   * @throws Exception 异常
   */
  public List<PurposeSeller> purposeSellerList(Map<String, Object> map) throws Exception;

  /**
   * 查询已导入的意向商户总数.
   * 
   * @return 数量
   * @throws Exception 异常
   */
  public int purposeSellerTotalNum(Map<String, Object> map) throws Exception;

  /**
   * 批量添加导入的意向商户.
   * 
   * @param psList
   * @return 成功添加的数量.
   */
  public int batchAddPS(List<PurposeSeller> psList) throws Exception;

  /**
   * 保存napos商户数据.
   * 
   * @param mns
   * @return
   */
  public int saveSellerInfo(MNaposSeller mns) throws Exception;

  /**
   * 根据napos餐厅id查找napos商户基础表.
   * 
   * @param naposResId napos餐厅id.
   * @return
   * @throws Exception
   */
  public MNaposSeller findMNaposSellerByNaposResId(String naposResId) throws Exception;

  /**
   * 根据餐厅id（参数exportSellerIdsStr字段提供）的字符串查找拥有人联系号码，再通过联系号码查询出其下的所有餐厅
   * 
   * @param sqb
   */
  public List<Map<String, Object>> selectSellersForExport(Map<String, Object> map);

  /**
   * 根据ID查询napos商户
   * 
   * @param sellerId
   * @return
   */
  public MNaposSeller findMNaposSellerBySellerId(Integer id);

  /**
   * 通过sellerId来查询商户信息
   * @author sunwei
   * @since 2016年1月5日
   * @param sellerId
   * @return
   */
  public Seller findSellerBySellerId(Integer sellerId);

}
