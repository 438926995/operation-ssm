package com.eleme.service.basic.impl;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eleme.constants.PagerConstants;
import com.eleme.domain.mart.city.CityAndProvBean;
import com.eleme.domain.mart.city.CityCompareBean;
import com.eleme.domain.mart.city.MCityTree;
import com.eleme.domain.mart.city.MCityTreeVo;
import com.eleme.domain.mart.finance.MFinanceOrg;
import com.eleme.domain.mart.product.MFinanceProduct;
import com.eleme.mapper.mart.city.MCityTreeMapper;
import com.eleme.mapper.mart.city.TFcoCoveredCityMapper;
import com.eleme.service.BaseService;
import com.eleme.service.basic.ICityService;
import com.eleme.util.pager.TbData;

/**
 * 城市信息实现类。
 * 
 * @author yonglin.zhu
 *
 */
@Service
@Transactional(rollbackFor = java.lang.Throwable.class)
public class CityServiceImpl extends BaseService implements ICityService {

  @Inject
  private MCityTreeMapper cityTreeMapper;

  @Inject
  private TFcoCoveredCityMapper foMapper;

  @Override
  @Transactional(readOnly = true)
  public List<CityAndProvBean> getCityInfo() {
    List<CityAndProvBean> cityAndProvList = new ArrayList<CityAndProvBean>();
    for (MCityTree city : cityTreeMapper.selectAllProvInfo()) {
      CityAndProvBean cityAndProv = new CityAndProvBean();
      cityAndProv.setCityID(city.getCityID());
      cityAndProv.setCityName(city.getCityName());
      cityAndProv.setCityList(cityTreeMapper.selectCityListByprovId(city.getCityID()));
      cityAndProvList.add(cityAndProv);
    }
    return cityAndProvList;
  }

  @Override
  @Transactional(readOnly = true)
  public List<MFinanceOrg> getFoInfo(Integer cityId) {
    return foMapper.selectFoInfoByCityId(cityId);
  }

  @Override
  @Transactional(readOnly = true)
  public List<MFinanceProduct> getProductInfo(Integer cityId) {
    return foMapper.selectProductByCityId(cityId);
  }


  @Override
  // @Cacheable(value = "cityTree", key = "'getCityListByParentId:' + #provinceId")
  @Transactional(readOnly = true)
  public List<MCityTree> getCityListByProvinceId(Integer provinceId) {
    return cityTreeMapper.selectCityListByprovId(provinceId);
  }

  @Override
  // @Cacheable(value = "cityTree", key = "'getProvinceList'")
  @Transactional(readOnly = true)
  public List<MCityTree> getProvinceList() {
    return cityTreeMapper.selectAllProvInfo();
  }

  @Override
  @Transactional(readOnly = true)
  public List<CityCompareBean> getAllCityInfo() {
    return cityTreeMapper.getMatchNaopsCityInfo();
  }

  @Override
  @Transactional(rollbackFor = Throwable.class)
  public int updateMCityTree(MCityTree cityTree) {
    return cityTreeMapper.updateMCityById(cityTree);
  }

  @Override
  @Transactional(rollbackFor = Throwable.class)
  public int insertMCityTree(MCityTree cityTree) {
    return cityTreeMapper.insertMCity(cityTree);
  }

  @Override
  @Transactional(readOnly = true)
  public TbData getProvinceListPaging(Integer currentPage, MCityTreeVo cityTreeVo) {
    // 第一次进入时，currentPage为空
    if (currentPage == null) {
      currentPage = 1;
    }
    // 封装sql查询条件
    cityTreeVo.setPageSize(PagerConstants.PAGE_SIZE);
    cityTreeVo.setStartRecord((currentPage - 1) * PagerConstants.PAGE_SIZE);
    List<MCityTree> selectAllProInfoPaging = cityTreeMapper.selectAllProInfoPaging(cityTreeVo);
    // 查询记录数
    int totalCount = cityTreeMapper.getTotalCount(cityTreeVo);
    return initTbData(totalCount, currentPage, PagerConstants.PAGE_SIZE, selectAllProInfoPaging);
  }

  @Override
  @Transactional(readOnly = true)
  public TbData getCityListInProvincePaging(Integer currentPage, MCityTreeVo cityTreeVo) {
    // 第一次进入时，currentPage为空
    if (currentPage == null) {
      currentPage = 1;
    }
    // 封装sql查询条件
    cityTreeVo.setPageSize(PagerConstants.PAGE_SIZE);
    cityTreeVo.setStartRecord((currentPage - 1) * PagerConstants.PAGE_SIZE);
    List<MCityTree> selectCityListInProvPaging =
        cityTreeMapper.selectCityListInProvPaging(cityTreeVo);
    // 查询记录数
    int totalCount = cityTreeMapper.getTotalCountCity(cityTreeVo);
    return initTbData(totalCount, currentPage, PagerConstants.PAGE_SIZE,
        selectCityListInProvPaging);
  }

  @Override
  @Transactional(rollbackFor = Throwable.class)
  public int updateMCityTreeProvince(MCityTree cityTree) {
    // 数据库里的省份对应的城市Id
    int provId = cityTreeMapper.getCityIdById(cityTree.getId());
    // 更行省份信息
    int lines = cityTreeMapper.updateMCityById(cityTree);
    // 得到省份对应的城市
    List<MCityTree> cityList = cityTreeMapper.selectCityListByprovId(provId);
    // 省份中有城市才更行
    if (cityList.size() > 0) {
      // 封装更行条件
      CityAndProvBean cap = new CityAndProvBean();
      cap.setCityID(cityTree.getCityID());
      cap.setCityName(cityTree.getCityName());
      cap.setCityList(cityList);
      // 更行省份对应的城市中的省份
      lines = cityTreeMapper.updateMCityProvince(cap);
    }
    return lines;
  }

  @Override
  public MCityTree getCityInfoByCityId(Integer cityId) {
    return cityTreeMapper.selectCityTreeInfoByCityId(cityId);
  }

  @Override
  public int batchUpdateCity(List<CityCompareBean> updateCity) {
    return cityTreeMapper.updateBatchCityTree(updateCity);
  }

}
