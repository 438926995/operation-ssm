package com.eleme.service.basic;

import java.util.List;

import com.eleme.domain.mart.basic.MBasicData;
import com.eleme.domain.mart.basic.MBasicDataKey;
import com.eleme.util.pager.TbData;

/**
 * 融资产品规则接口
 * 
 * @author yonglin.zhu
 *
 */
public interface IBasicService {

  /**
   * 根据Id查询基础数据信息集合
   * 
   * @param basicDataKey
   * @return
   */
  List<MBasicData> queryBasicDataList(MBasicDataKey basicDataKey);

  /**
   * 查询基础数据信息集合(分页用)
   * 
   * @param currentPage
   * @param mBasicData
   * @return
   */
  TbData getBasicDataList(Integer currentPage, MBasicData mBasicData);

  /**
   * 更行基本数据表里的基本数据(type_id==0)
   * 
   * @param mbd
   * @return
   */
  int updateBasicData(MBasicData mbd);

  /**
   * 更行基本数据表详情数据(type_id!=0)
   * 
   * @param mbd
   * @return
   */
  int updateBasicDataDetail(MBasicData mbd);

  /**
   * 插入数据
   * 
   * @param mbd
   * @return
   */
  int insertBasciData(MBasicData mbd);

  /**
   * 得到最大typeCd加1
   * 
   * @return
   */
  String getTypeCdAddOne(boolean flag);
  /**
   * 根据Id查最大的排序号
   * @param basicDataKey
   * @return
   */
  Integer selectMaxSortIndexById(MBasicDataKey basicDataKey);
  
  /**
   * 判断typeCdName 是否唯一
   * @param mbd
   * @return
   */
  boolean judgeIfTypeCdNameSingle(MBasicData mbd);

}
