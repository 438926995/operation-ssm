package com.eleme.mapper.mart.basic;

import java.util.List;

import com.eleme.domain.mart.basic.MBasicData;
import com.eleme.domain.mart.basic.MBasicDataKey;

/**
 * 基础数据接口
 * 
 * @author yonglin.zhu
 *
 */
public interface MBasicDataMapper {

  /**
   * 新增基础数据
   * 
   * @param record
   * @return
   */
  int insert(MBasicData record);

  /**
   * 根据主键查询基础数据
   * 
   * @param key
   * @return
   */
  List<MBasicData> selectById(MBasicDataKey key);

  /**
   * 修改基础数据
   * 
   * @param record
   * @return
   */
  int updateById(MBasicData record);

  /**
   * 查询基本数据总数
   * 
   * @param mBasicData
   * @return
   */
  int selectCount(MBasicData mBasicData);

  /**
   * 查询基本数据的集合，分页
   * 
   * @param mBasicData
   * @return
   */
  List<MBasicData> getMBasicDataList(MBasicData mBasicData);

  /**
   * 根据flag true查typeId=0的 false查typeID!=0 查询最大的TYPE_CD 加一
   * 
   * @return
   */
  String getMaxTypeCdAddOne(boolean flag);

  /**
   * 根据Id得到最大的排序号
   * 
   * @param key
   * @return
   */
  int getMaxSortIndexById(MBasicDataKey key);
  
  /**
   * 根据名字查找是否有该记录
   * @param mBasicData
   * @return
   */
  int findRecordByName(MBasicData mBasicData);

}
