package com.eleme.service.basic.impl;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eleme.constants.PagerConstants;
import com.eleme.domain.mart.basic.MBasicData;
import com.eleme.domain.mart.basic.MBasicDataKey;
import com.eleme.mapper.mart.basic.MBasicDataMapper;
import com.eleme.service.BaseService;
import com.eleme.service.basic.IBasicService;
import com.eleme.util.pager.TbData;

/**
 * 金融机构实现类。
 * 
 * @author yonglin.zhu
 *
 */
@Service
public class BasicServiceImpl extends BaseService implements IBasicService {

  @Inject
  private MBasicDataMapper basicDataMapper;


  @Override
  public List<MBasicData> queryBasicDataList(MBasicDataKey basicDataKey) {
    return basicDataMapper.selectById(basicDataKey);
  }


  @Override
  @Transactional(rollbackFor = Throwable.class)
  public int updateBasicData(MBasicData mbd) {
    // 更行基本数据
    basicDataMapper.updateById(mbd);
    // 更行
    // 封装第二次跟新的条件
    MBasicData mbd2 = new MBasicData();
    mbd2.setTypeId(Integer.parseInt(mbd.getTypeCd()));
    mbd2.setTypeIdName(mbd.getTypeCdName());
    mbd2.setIsVisible(mbd.getIsVisible());
    int lines = basicDataMapper.updateById(mbd2);
    return lines;
  }


  @Override
  public TbData getBasicDataList(Integer currentPage, MBasicData mBasicData) {
    // 第一次进入时，currentPage为空
    if (currentPage == null) {
      currentPage = 1;
    }
    // 封装sql查询条件
    mBasicData.setStartRecord((currentPage - 1) * PagerConstants.PAGE_SIZE);
    mBasicData.setPageSize(PagerConstants.PAGE_SIZE);
    List<MBasicData> mBasicDataList = basicDataMapper.getMBasicDataList(mBasicData);
    // 查询记录数
    int totalCount = basicDataMapper.selectCount(mBasicData);
    return initTbData(totalCount, currentPage, PagerConstants.PAGE_SIZE, mBasicDataList);
  }


  @Override
  @Transactional(rollbackFor = Throwable.class)
  public int updateBasicDataDetail(MBasicData mbd) {
    return basicDataMapper.updateById(mbd);
  }


  @Override
  public int insertBasciData(MBasicData mbd) {
    return basicDataMapper.insert(mbd);
  }


  @Override
  public String getTypeCdAddOne(boolean flag) {
    return basicDataMapper.getMaxTypeCdAddOne(flag);
  }


  @Override
  public Integer selectMaxSortIndexById(MBasicDataKey basicDataKey) {
    return basicDataMapper.getMaxSortIndexById(basicDataKey);
  }


  @Override
  public boolean judgeIfTypeCdNameSingle(MBasicData mbd) {
    return basicDataMapper.findRecordByName(mbd) == 0;
  }

}
