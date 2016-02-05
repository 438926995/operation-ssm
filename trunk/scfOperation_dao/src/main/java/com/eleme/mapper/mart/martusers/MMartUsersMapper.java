package com.eleme.mapper.mart.martusers;

import java.util.List;

import com.eleme.domain.mart.martusers.FinanceUserQueryCnd;
import com.eleme.domain.mart.martusers.MMartUsers;
import com.eleme.domain.mart.martusers.MMartUsersVo;

/**
 * 用户基础信息接口
 * 
 * @author yonglin.zhu
 *
 */
public interface MMartUsersMapper {

  /**
   * 添加用户基础信息
   * 
   * @param record 用户基础信息
   * @return 添加条数
   */
  int insert(MMartUsers record);

  /**
   * 根据ID查询用户基础信息
   * 
   * @param userId 用户ID
   * @return 用户基础信息
   */
  MMartUsersVo selectById(Integer userId);

  /**
   * 根据ID修改用户基础信息
   * 
   * @param record 用户基础信息
   * @return 修改条数
   */
  int updateById(MMartUsers record);

  /**
   * 根据条件分页查询金融机构 账户信息
   * 
   * @param fuqc
   * @return
   */
  List<MMartUsersVo> selectFinanceUserList(FinanceUserQueryCnd fuqc);

  /**
   * 根据条件查询金融机构账户总件数
   * 
   * @param fuqc 条件封装类
   * @return 件数
   */
  Integer selectFinanceUserListCount(FinanceUserQueryCnd fuqc);

  /**
   * 验证是否重复，不包含本身
   * 
   * @param fuqc
   * @return
   */
  Integer selectIsNotSelfCount(FinanceUserQueryCnd fuqc);

  /**
   * 根据金融机构ID 查询用户信息
   * 
   * @param foID
   * @return
   */
  List<MMartUsers> selectMartUsersByFoId(Integer foId);

}
