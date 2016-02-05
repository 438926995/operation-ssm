package com.eleme.mapper.mart.city;


import java.util.List;
import java.util.Map;

import com.eleme.domain.mart.city.CityAndProvBean;
import com.eleme.domain.mart.city.CityCompareBean;
import com.eleme.domain.mart.city.MCityTree;
import com.eleme.domain.mart.city.MCityTreeVo;


/**
 * 城市层级关系 接口
 * 
 * @author xudong.gu
 *
 */
public interface MCityTreeMapper {

  /**
   * 根据金融机构覆盖省份名称 省份包含城市列表
   * 
   * @param provName 金融机构覆盖省份名
   * @return 省份包含城市列表 不存在返回null
   */
  List<String> selectCityListByProvName(String provName);

  /**
   * 根据省份id 查省份中所以城市信息
   * 
   * @param provId
   * @return
   */
  List<MCityTree> selectCityListByprovId(Integer provId);

  /**
   * 查询 所有省份名
   * 
   * @return 省份列表 不存在返回null
   */
  List<String> selectAllProvName();

  /**
   * 查询 所以省份信息
   * 
   * @return
   */
  List<MCityTree> selectAllProvInfo();

  /**
   * 根据城市名列表 查询城市信息列表
   * 
   * @param cityNames
   * @return 查询城市信息列表 不存在返回null
   */
  List<MCityTree> selectMCityInfosByCityNames(Map<String, Object> cityNames);

  /**
   * 根据 城市名 查询城市id
   * 
   * @param cityName
   * @return 城市id 不存在返回0
   */
  int selectCityIdByCityName(String cityName);

  /**
   * 根据 城市id 查询城市信息
   * 
   * @param cityId
   * @return 城市信息 不存在返回null
   */
  MCityTree selectCityTreeInfoByCityId(int cityId);

  /**
   * 根据 城市id列表 查询城市信息
   * 
   * @param cityIds
   * @return 城市信息 不存在返回null
   */
  List<MCityTree> selectCityTreeInfosByCityIds(List<Integer> cityIds);


  /**
   * 获得和napos 对应的城市信息(不含省份，有直辖市)
   * 
   * @return
   */
  List<CityCompareBean> getMatchNaopsCityInfo();

  /**
   * 根据主键更行城市信息
   * 
   * @param cityTree
   * @return
   */
  int updateMCityById(MCityTree cityTree);

  /**
   * 根据更行的省份 更行对应的城市中对应的省份
   * 
   * @param cap
   * @return
   */
  int updateMCityProvince(CityAndProvBean cap);

  /**
   * 插入城市信息
   * 
   * @param cityTree
   * @return
   */
  int insertMCity(MCityTree cityTree);

  /**
   * 查询 所以省份信息(分页)
   * 
   * @return
   */
  List<MCityTree> selectAllProInfoPaging(MCityTreeVo cityTreeVo);

  /**
   * 查询省份的总数
   * 
   * @param cityTreeVo
   * @return
   */
  int getTotalCount(MCityTreeVo cityTreeVo);

  /**
   * 获得某个省份下的城市 (分页)
   * 
   * @param cityTreeVo
   * @return
   */
  List<MCityTree> selectCityListInProvPaging(MCityTreeVo cityTreeVo);

  /**
   * 查询某个省份下城市数量
   * 
   * @param cityTreeVo
   * @return
   */
  int getTotalCountCity(MCityTreeVo cityTreeVo);

  /**
   * 根据主键id 得到城市Id
   * 
   * @param id
   * @return
   */
  int getCityIdById(Integer id);

  /**
   * 批量更行城市信息
   * 
   * @param updateCity
   * @return
   */
  int updateBatchCityTree(List<CityCompareBean> updateCity);
}
