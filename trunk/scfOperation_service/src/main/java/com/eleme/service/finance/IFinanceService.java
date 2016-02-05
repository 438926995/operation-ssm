package com.eleme.service.finance;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.eleme.domain.mart.city.MCityTree;
import com.eleme.domain.mart.city.TFcoCoveredCity;
import com.eleme.domain.mart.finance.FinanceQueryBean;
import com.eleme.domain.mart.finance.MFinanceOrg;
import com.eleme.domain.mart.finance.MFinanceOrgVo;
import com.eleme.domain.mart.martusers.FinanceUserQueryCnd;
import com.eleme.domain.mart.martusers.MMartUsers;
import com.eleme.domain.mart.martusers.MMartUsersVo;
import com.eleme.util.pager.TbData;

/**
 * 金融机构接口
 * 
 * @author yonglin.zhu
 *
 */
public interface IFinanceService {

  /**
   * 新增金融机构信息
   * 
   * @param mf
   * @return 新增条数
   * @throws SQLException
   */
  public int financeAdd(MFinanceOrg mf) throws Exception;

  /**
   * 根据条件分页查询金融机构信息。查询条件:机构名称,状态,创建时间;排序：默认;
   * 
   * @param fqb 条件封装类
   * @return 金融机构集合
   */
  TbData selectFinanceList(FinanceQueryBean fqb, Integer currentPage) throws Exception;

  /**
   * 根据ID查询金融机构信息。查询条件:主键key
   * 
   * @param foId
   * @return Map
   * @throws SQLException
   */
  public Map<String, Object> queryFinanceById(Integer foId) throws SQLException;

  /**
   * 根据条件查询金融机构信息。查询条件:机构名称,状态,创建时间;排序：默认;
   * 
   * @param fqb 条件封装类
   * @return 金融机构集合
   */
  List<MFinanceOrg> selectFinanceList(FinanceQueryBean fqb) throws Exception;

  /**
   * 新增金融机构账户
   * 
   * @param martUsers
   * @return 新增条数
   * @throws SQLException
   */
  public int financeAddUsers(MMartUsers martUsers, String isApply) throws SQLException;

  /**
   * 根据ID查询金融机构信息。查询条件：主键key
   * 
   * @param foId
   * @return 金融机构信息;没有查询到则返回null。
   * @throws SQLException
   */
  public MFinanceOrg queryFinanceInfoById(Integer foId) throws SQLException;

  /**
   * 根据ID修改金融机构
   * 
   * @param mFinanceOrg
   * @return 修改条数
   * @throws SQLException
   */
  public int updateFinance(MFinanceOrg mFinanceOrg) throws Exception;

  /**
   * 根据条件分页查询金融机构账户信息。查询条件：账号名称,所属机构;排序:默认;
   * 
   * @param fuqc 条件封装类
   * @return 金融机构集合
   */
  TbData selectFinanceUserList(FinanceUserQueryCnd fuqc, Integer currentPage) throws Exception;

  /**
   * 根据ID查询 金融机构账户信息。查询条件：主键key
   * 
   * @param userId
   * @return 金融机构账户信息;如果没有查询到则返回null.
   * @throws SQLException
   */
  public MMartUsersVo queryFinanceUserInfoById(Integer userId) throws SQLException;

  /**
   * 修改金融机构账户信息
   * 
   * @param mMartUsers
   * @return 修改条数
   * @throws SQLException
   */
  public int updateFinanceUser(MMartUsers mMartUsers) throws SQLException;

  /**
   * 根据 金融机构名称 查询金融机构信息
   * 
   * @param foName 查询条件：金融机构名称
   * @return 金融机构信息 不存在则返回null。
   * @throws SQLException
   */
  public Map<String, Object> queryFinanceByName(String foName) throws SQLException;

  /**
   * 根据 金融机构覆盖省份名称 省份包含城市列表
   * 
   * @param provName 查询条件：省份名
   * @return 省份包含城市列表 不存在则返回null
   * @throws SQLException
   */
  public List<String> queryCitiesByProvName(String provName) throws SQLException;

  /**
   * 查询所有省份名
   * 
   * @return
   * @throws SQLException
   */
  public List<String> queryAllProvName() throws SQLException;

  /**
   * 根据金融机构ID 查询金融机构覆盖城市信息列表
   * 
   * @param foID 金融机构ID
   * @return 金融机构覆盖城市信息列表 不存在返回null
   * @throws SQLException
   */
  List<TFcoCoveredCity> queryTFcoCoverCityInfosByFoID(int foID) throws SQLException;

  /**
   * 新增 机构覆盖城市表信息
   * 
   * @param map
   * @return 新增条数
   * @throws SQLException
   */
  int addCoveredCityInfos(Map<String, Object> map) throws SQLException;

  /**
   * 根据cityName列表 查找 cityTree 列表
   * 
   * @param cityNameList
   * @return 查找 cityTree 列表 不存在返回null
   * @throws SQLException
   */
  List<MCityTree> queryMCityInfosByCityNames(String[] cityNames) throws SQLException;

  /**
   * 根据 金融机构覆盖省份名称 查询包含城市列表
   * 
   * @param provName
   * @return 城市列表 不存在返回null
   * @throws SQLException
   */
  public List<String> queryCoveredCitiesByProvName(String provName) throws SQLException;

  /**
   * 根据 金融机构 foID 和 覆盖省份名称 查询包含城市列表
   * 
   * @param foID
   * @param provName
   * @return
   * @throws SQLException
   */
  public List<String> queryCoveredCitiesByProvName(int foID, String provName) throws SQLException;

  /**
   * 根据cityName 查询 cityId
   * 
   * @param cityName
   * @return cityId
   */
  public int queryCityIdByCityName(String cityName);

  /**
   * 根据cityId 查询城市信息
   * 
   * @param cityId
   * @return 城市信息 不存在返回null
   * @throws SQLException
   */
  public MCityTree queryMCityInfoByCityId(int cityId) throws SQLException;

  /**
   * 验证金融机构账户的唯一性
   * 
   * @param fuqc
   * @return
   */
  boolean checkSingleMartUser(FinanceUserQueryCnd fuqc);

  /**
   * 根据 cityId字符串 查询城市信息
   * 
   * @param cityIds
   * @return 查询城市信息 不存在null
   * @throws SQLException
   */
  List<MCityTree> queryMCityInfosByCityIds(String cityIds) throws SQLException;

  /**
   * 验证金融机构名称的唯一性
   * 
   * @param cnd
   * @return
   */
  boolean checkSingleProductOrg(FinanceQueryBean cnd);

  /**
   * 查询出所有金融机构，用于绑定前端Select控件的金融机构列表
   * 
   * @author sunwei
   * @since 2015年12月28日
   * @return
   */
  List<MFinanceOrg> queryAllFinanceOrgsForSelect();

  /**
   * 查询机构-产品对象模型
   * @return
   */
  List<MFinanceOrgVo> selectOrgProductList();
}
