package com.eleme.mapper.mart.city;

import java.util.List;
import java.util.Map;

import com.eleme.domain.mart.city.MCityTree;
import com.eleme.domain.mart.city.TFcoCoveredCity;
import com.eleme.domain.mart.finance.MFinanceOrg;
import com.eleme.domain.mart.product.MFinanceProduct;

/**
 * 金融机构覆盖城市接口
 *
 * @author xudong.gu
 */
public interface TFcoCoveredCityMapper {

  /**
   * 根据foID 查询金融机构覆盖城市信息列表
   *
   * @param foID 金融机构覆盖省份名
   * @return 金融机构覆盖城市信息列表    不存在返回null
   */
  List<TFcoCoveredCity> selectCoveredCityInfosByFoID(int foID);

  /**
   * 插入金融机构覆盖城市信息表
   *
   * @param map 插入数据封装
   * @return 新增条数
   */
  int insertCoveredCityInfos(Map<String, Object> map);

  /**
   * 根据 foId 删除表中机构覆盖城市信息（以便后续更新）
   *
   * @param foID
   * @return 影响行数
   */
  int deleteCoveredCityInfosByFoID(int foID);

  /**
   * 根据覆盖省份名    查询覆盖城市列表
   *
   * @param provName
   * @return 覆盖城市列表  不存在返回null
   */
  List<String> selectCoveredCitiesByProvName(String provName);

  /**
   * 根据foID 和 覆盖省份名       查询覆盖城市列表
   *
   * @param map
   * @return 覆盖城市列表  不存在返回null
   */
  List<String> selectCoveredCitiesByFoIDProvName(Map<String, Object> map);

  /**
   * 根据城市ID 查询金融机构
   *
   * @param cityId
   * @return
   */
  List<MFinanceOrg> selectFoInfoByCityId(Integer cityId);

  /**
   * 根据城市Id 查询金融产品
   */
  List<MFinanceProduct> selectProductByCityId(Integer cityId);
}
