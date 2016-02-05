package com.eleme.service.basic;

import java.util.List;

import com.eleme.domain.mart.city.CityAndProvBean;
import com.eleme.domain.mart.city.CityCompareBean;
import com.eleme.domain.mart.city.MCityTree;
import com.eleme.domain.mart.city.MCityTreeVo;
import com.eleme.domain.mart.finance.MFinanceOrg;
import com.eleme.domain.mart.product.MFinanceProduct;
import com.eleme.util.pager.TbData;

/**
 * 城市信息接口
 * 
 * @author yonglin.zhu
 *
 */
public interface ICityService {

  /**
   * 获得省份和省份对应的城市
   */
  public List<CityAndProvBean> getCityInfo();

  /**
   * 根据城市ID 获得金融机构信息
   * 
   * @param cityId
   * @return
   */
  public List<MFinanceOrg> getFoInfo(Integer cityId);

  /**
   * 根据城市ID 获得产品信息
   * 
   * @param cityId
   * @return
   */
  public List<MFinanceProduct> getProductInfo(Integer cityId);

  /**
   * 获取某个省下所有城市
   * 
   * @param provinceId
   * @return
   */
  List<MCityTree> getCityListByProvinceId(Integer provinceId);

  /**
   * 获取某个省下所有城市 (分页)
   * 
   * @param currentPage
   * @param cityTreeVo
   * @return
   */
  TbData getCityListInProvincePaging(Integer currentPage, MCityTreeVo cityTreeVo);

  /**
   * 获取所有的省
   * 
   * @return
   */
  List<MCityTree> getProvinceList();

  /**
   * 获取所有的省(分页)
   * 
   * @return
   */
  TbData getProvinceListPaging(Integer currentPage, MCityTreeVo cityTreeVo);

  /**
   * 得到所以城市信息(不含省份)
   * 
   * @return
   */
  List<CityCompareBean> getAllCityInfo();

  /**
   * 更新城市信息
   * 
   * @param cityTree
   * @return
   */
  int updateMCityTree(MCityTree cityTree);

  /**
   * 更行省份信息 和省份对应的城市
   * 
   * @param cityTree
   * @return
   */
  int updateMCityTreeProvince(MCityTree cityTree);

  /**
   * 插入napos城市信息
   * 
   * @param cityTree
   * @return
   */
  int insertMCityTree(MCityTree cityTree);

  /**
   * 根据城市id 得到城市信息
   * 
   * @param cityId
   * @return
   */
  MCityTree getCityInfoByCityId(Integer cityId);

  /**
   * 批量更行
   * 
   * @param updateCity
   * @return
   */
  int batchUpdateCity(List<CityCompareBean> updateCity);

}
