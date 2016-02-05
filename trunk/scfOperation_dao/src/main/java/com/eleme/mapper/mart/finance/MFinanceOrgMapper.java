package com.eleme.mapper.mart.finance;

import java.util.List;

import com.eleme.domain.mart.finance.FinanceQueryBean;
import com.eleme.domain.mart.finance.MFinanceOrg;
import com.eleme.domain.mart.finance.MFinanceOrgVo;

/**
 * 金融机构管理接口
 * 
 * @author yonglin.zhu
 *
 */
public interface MFinanceOrgMapper {

  /**
   * 添加金融机构
   * 
   * @param 金融机构信息
   * @return 插入条数
   */
  int insert(MFinanceOrg record);

  /**
   * 根据主键查询金融机构信息
   * 
   * @param foId 主键ID
   * @return 金融机构信息
   */
  MFinanceOrg selectById(Integer foId);

  /**
   * 根据主键修改金融机构信息
   * 
   * @param 金融机构信息
   * @return 修改条数
   */
  int updateById(MFinanceOrg record);

  /**
   * 根据条件查询金融机构信息
   * 
   * @param fqb 条件封装类
   * @return 金融机构集合
   */
  List<MFinanceOrg> selectFinanceList(FinanceQueryBean fqb);

  /**
   * 根据条件查询金融机构总件数
   * 
   * @param fqb 条件封装类
   * @return 件数
   */
  Integer selectFinanceListCount(FinanceQueryBean fqb);

  /**
   * 根据金融机构名称 查询金融机构信息
   * 
   * @param foName 金融机构名称
   * @return 金融机构信息 不存在返回null
   */
  MFinanceOrg selectByName(String foName);

  /**
   * 验证是否重复，不包含本身
   * 
   * @param fqb
   * @return
   */
  Integer selectIsNotSelfCount(FinanceQueryBean fqb);

  /**
   * 选择所有金融机构用户
   * 
   * @author sunwei
   * @since 2015年12月28日
   * @return
   */
  List<MFinanceOrg> selectAllFinanceOrgs();
  
  /**
   * 查询机构-产品对象模型
   * @return
   */
  List<MFinanceOrgVo> selectOrgProductList();
}
