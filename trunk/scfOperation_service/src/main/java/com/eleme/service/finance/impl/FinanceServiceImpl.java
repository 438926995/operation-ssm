package com.eleme.service.finance.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.eleme.bean.file.SysUploadFileBean;
import com.eleme.constants.PagerConstants;
import com.eleme.domain.mart.city.MCityTree;
import com.eleme.domain.mart.city.TFcoCoveredCity;
import com.eleme.domain.mart.finance.FinanceQueryBean;
import com.eleme.domain.mart.finance.MFinanceOrg;
import com.eleme.domain.mart.finance.MFinanceOrgVo;
import com.eleme.domain.mart.loan.TApvFlowTeam;
import com.eleme.domain.mart.martusers.FinanceUserQueryCnd;
import com.eleme.domain.mart.martusers.MMartUsers;
import com.eleme.domain.mart.martusers.MMartUsersVo;
import com.eleme.mapper.mart.city.MCityTreeMapper;
import com.eleme.mapper.mart.city.TFcoCoveredCityMapper;
import com.eleme.mapper.mart.finance.MFinanceOrgMapper;
import com.eleme.mapper.mart.loan.TApvFlowTeamMapper;
import com.eleme.mapper.mart.martusers.MMartUsersMapper;
import com.eleme.service.BaseService;
import com.eleme.service.file.IFileService;
import com.eleme.service.finance.IFinanceService;
import com.eleme.util.FileType;
import com.eleme.util.fuss.FussUtil;
import com.eleme.util.pager.TbData;

import me.ele.elog.Log;
import me.ele.elog.LogFactory;

/**
 * 金融机构实现类。
 *
 * @author yonglin.zhu
 */
@Service
@Transactional(rollbackFor = java.lang.Throwable.class)
public class FinanceServiceImpl extends BaseService implements IFinanceService {

  /**
   * log
   */
  private static Log log = LogFactory.getLog(FinanceServiceImpl.class);

  @Inject
  private MFinanceOrgMapper mFinanceOrgMapper;

  @Inject
  private MMartUsersMapper mMartUsersMapper;

  @Inject
  private Md5PasswordEncoder passwordEncoder;

  @Inject
  private TApvFlowTeamMapper apvFlowTeamMapper;

  @Inject
  private MCityTreeMapper mCityMapper;

  @Inject
  private TFcoCoveredCityMapper tFcoCoveredCityMapper;

  @Inject
  private IFileService fileService;

  @Inject
  private FussUtil fussUtil;


  @Override
  @Transactional(propagation = Propagation.REQUIRED, rollbackFor = java.lang.Throwable.class)
  public int financeAdd(MFinanceOrg mf) throws Exception {
    mf.setCreateTime(new Date());
    int result = mFinanceOrgMapper.insert(mf);
    // 向t_sys_file_upload保存记录
    SysUploadFileBean sfb = fussUtil.getSysUploadFileBean(mf.getFoLogo(), mf.getFoId(),
        FileType.FINANCE_LOGO.toString());
    int line = fileService.insertSysFileUpload(sfb);
    log.info("添加金融机构logo, 得到影响行数：{}, 得到文件附件主键：{}", line, sfb.getUfId());
    // 更新机构表中文件主键
    MFinanceOrg org = new MFinanceOrg();
    org.setFoId(mf.getFoId());
    org.setUfId(sfb.getUfId());
    int lines = mFinanceOrgMapper.updateById(org);
    log.info("更新金融机构表中文件主键，影响行数：{}，金融机构id：{}，文件主键：{}", lines , org.getFoId(), org.getUfId());
    return result;
  }

  @Override
  @Transactional(readOnly = true)
  public TbData selectFinanceList(FinanceQueryBean fqb, Integer currentPage) throws Exception {
    // 第一次进入时，currentPage为空
    if (currentPage == null) {
      currentPage = 1;
    }
    // 封装sql查询条件
    if (!StringUtils.isBlank(fqb.getCreateDateRange())) {
      String startDateStr = fqb.getCreateDateRange().split(" - ")[0];
      String endDateStr = fqb.getCreateDateRange().split(" - ")[1] + " 23:59:59";
      fqb.setStartDate(DateUtils.parseDate(startDateStr, "MM/dd/yyyy"));
      fqb.setEndDate(DateUtils.parseDate(endDateStr, "MM/dd/yyyy HH:mm:ss"));
    }
    fqb.setStartRecord((currentPage - 1) * PagerConstants.PAGE_SIZE);
    fqb.setPageSize(PagerConstants.PAGE_SIZE);
    List<MFinanceOrg> financeList = mFinanceOrgMapper.selectFinanceList(fqb);
    int totalCount = mFinanceOrgMapper.selectFinanceListCount(fqb);
    return initTbData(totalCount, currentPage, PagerConstants.PAGE_SIZE, financeList);
  }

  @Override
  @Transactional(readOnly = true)
  public Map<String, Object> queryFinanceById(Integer foId) throws SQLException {
    boolean isSuccess = true;
    String failureMess = "";
    MFinanceOrg mFinanceOrg = mFinanceOrgMapper.selectById(foId);
    // 验证是否存在 金融机构
    if (mFinanceOrg == null) {
      isSuccess = false;
      failureMess = "金融机构不存在!";
    }
    // 查询覆盖城市
    String coverCities = "";
    List<TFcoCoveredCity> tfcs = tFcoCoveredCityMapper.selectCoveredCityInfosByFoID(foId);
    if (tfcs != null) {
      for (TFcoCoveredCity tfc : tfcs) {
        coverCities += "," + tfc.getCityName();
      }
    }
    if (coverCities.length() > 0) {
      coverCities = coverCities.substring(1, coverCities.length());
    }
    // 重新封装返回结果
    Map<String, Object> map = new HashMap<String, Object>();
    if (mFinanceOrg != null) {
      SysUploadFileBean file = fileService.getUploadFileById(mFinanceOrg.getUfId());
      map.put("url", file == null ? "" : file.getUrl());
    }
    map.put("coverCities", coverCities);
    map.put("financeInfo", mFinanceOrg);
    map.put("isSuccess", isSuccess);
    map.put("failureMess", failureMess);
    return map;
  }

  @Override
  @Transactional(readOnly = true)
  public List<MFinanceOrg> selectFinanceList(FinanceQueryBean fqb) throws Exception {
    return mFinanceOrgMapper.selectFinanceList(fqb);
  }

  @Override
  public int financeAddUsers(MMartUsers martUsers, String isApply) throws SQLException {
    martUsers.setUserStatus(1);
    // 加密密码.
    String encryptPswd =
        passwordEncoder.encodePassword(martUsers.getUserPswd(), martUsers.getUserName());
    martUsers.setUserPswd(encryptPswd);
    martUsers.setCreateTime(new Date());
    martUsers.setLoginMethod(1);
    int count = mMartUsersMapper.insert(martUsers);
    // 是默认审核人的情况，添加审批组信息
    if ("1".equals(isApply)) {
      // 判断审批组名称是否存在，如果存在，则不插入
      if (apvFlowTeamMapper.selectByName("FO" + martUsers.getFoId().intValue()).intValue() == 0) {
        TApvFlowTeam apvFlowTeam = new TApvFlowTeam();
        // 审批组名称
        apvFlowTeam.setTeamName("FO" + martUsers.getFoId().intValue());
        // 审批人ids，英文逗号分隔
        apvFlowTeam.setAppUserIds(martUsers.getUserId().toString());
        // 审批人names，英文逗号分隔
        apvFlowTeam.setAppUserNames(martUsers.getUserName());
        apvFlowTeamMapper.insert(apvFlowTeam);
      }

    }
    return count;
  }

  @Override
  public MFinanceOrg queryFinanceInfoById(Integer foId) throws SQLException {
    return mFinanceOrgMapper.selectById(foId);
  }

  @Override
  public int updateFinance(MFinanceOrg mFinanceOrg) throws Exception {
    MultipartFile foLogo = mFinanceOrg.getFoLogo();
    if (foLogo != null && !foLogo.isEmpty()) {
      SysUploadFileBean sfb = fussUtil.getSysUploadFileBean(foLogo, mFinanceOrg.getFoId(),
          FileType.FINANCE_LOGO.toString());
      int line = fileService.insertSysFileUpload(sfb);
      log.info("更换金融机构logo, 得到影响行数：{}, 得到文件附件主键：{}", line, sfb.getUfId());
      mFinanceOrg.setUfId(sfb.getUfId());
    }
    return mFinanceOrgMapper.updateById(mFinanceOrg);
  }

  @Override
  public TbData selectFinanceUserList(FinanceUserQueryCnd fuqc, Integer currentPage)
      throws Exception {
    // 第一次进入时，currentPage为空
    if (currentPage == null) {
      currentPage = 1;
    }
    fuqc.setStartRecord((currentPage - 1) * PagerConstants.PAGE_SIZE);
    fuqc.setPageSize(PagerConstants.PAGE_SIZE);
    List<MMartUsersVo> martUserList = mMartUsersMapper.selectFinanceUserList(fuqc);
    int totalCount = mMartUsersMapper.selectFinanceUserListCount(fuqc);
    return initTbData(totalCount, currentPage, PagerConstants.PAGE_SIZE, martUserList);
  }

  @Override
  public MMartUsersVo queryFinanceUserInfoById(Integer userId) throws SQLException {
    return mMartUsersMapper.selectById(userId);
  }

  @Override
  public int updateFinanceUser(MMartUsers mMartUsers) throws SQLException {
    MMartUsersVo mMartUsersVo = mMartUsersMapper.selectById(mMartUsers.getUserId());
    if (mMartUsersVo != null) {
      if (mMartUsersVo.getUserPswd().equals(mMartUsers.getUserPswd())) {
        mMartUsers.setUserPswd("");
      } else {
        if (StringUtils.isNotEmpty(mMartUsers.getUserPswd())) {
          // 加密密码.
          String encryptPswd =
              passwordEncoder.encodePassword(mMartUsers.getUserPswd(), mMartUsers.getUserName());
          mMartUsers.setUserPswd(encryptPswd);
        }
      }
    }
    return mMartUsersMapper.updateById(mMartUsers);
  }

  @Override
  @Transactional(readOnly = true)
  public Map<String, Object> queryFinanceByName(String foName) throws SQLException {
    boolean isSuccess = true;
    String failureMess = "";
    MFinanceOrg mFinanceOrg = mFinanceOrgMapper.selectByName(foName);
    // 验证是否存在 金融机构
    if (mFinanceOrg == null) {
      isSuccess = false;
      failureMess = "金融机构不存在!";
    }
    // 重新封装返回结果
    Map<String, Object> map = new HashMap<String, Object>();
    map.put("financeInfo", mFinanceOrg);
    map.put("isSuccess", isSuccess);
    map.put("failureMess", failureMess);
    return map;
  }

  @Override
  @Transactional(readOnly = true)
  public List<String> queryCitiesByProvName(String provName) throws SQLException {
    return mCityMapper.selectCityListByProvName(provName);
  }

  @Override
  public List<String> queryAllProvName() throws SQLException {
    return mCityMapper.selectAllProvName();
  }

  @Override
  public List<TFcoCoveredCity> queryTFcoCoverCityInfosByFoID(int foID) throws SQLException {
    return tFcoCoveredCityMapper.selectCoveredCityInfosByFoID(foID);
  }

  @Override
  public int addCoveredCityInfos(Map<String, Object> map) throws SQLException {
    int flag = 0;
    String[] cityNames = ((String) map.get("cityNames")).split(",");
    List<MCityTree> mCTs = queryMCityInfosByCityNames(cityNames);
    int foId = (int) map.get("foId");
    tFcoCoveredCityMapper.deleteCoveredCityInfosByFoID(foId);
    Map<String, Object> insertMap = new HashMap<>();
    insertMap.put("foId", foId);
    insertMap.put("cities", mCTs);
    flag = tFcoCoveredCityMapper.insertCoveredCityInfos(insertMap);
    return flag;
  }

  @Override
  public List<MCityTree> queryMCityInfosByCityNames(String[] cNames) throws SQLException {
    List<String> cityNames = Arrays.asList(cNames);
    Map<String, Object> map = new HashMap<>();
    map.put("cityNames", cityNames);
    return mCityMapper.selectMCityInfosByCityNames(map);
  }

  @Override
  public List<String> queryCoveredCitiesByProvName(String provName) throws SQLException {
    return tFcoCoveredCityMapper.selectCoveredCitiesByProvName(provName);
  }

  @Override
  public List<String> queryCoveredCitiesByProvName(int foID, String provName) throws SQLException {
    Map<String, Object> map = new HashMap<>();
    map.put("foID", foID);
    map.put("provName", provName);
    return tFcoCoveredCityMapper.selectCoveredCitiesByFoIDProvName(map);
  }

  @Override
  public int queryCityIdByCityName(String cityName) {
    return mCityMapper.selectCityIdByCityName(cityName);
  }

  @Override
  public MCityTree queryMCityInfoByCityId(int cityId) throws SQLException {
    return mCityMapper.selectCityTreeInfoByCityId(cityId);
  }

  @Override
  public boolean checkSingleMartUser(FinanceUserQueryCnd fuqc) {
    Integer count = mMartUsersMapper.selectIsNotSelfCount(fuqc);
    return count.intValue() == 0 ? true : false;
  }

  @Override
  public List<MCityTree> queryMCityInfosByCityIds(String cityIds) throws SQLException {
    String[] cIds = cityIds.split(",");
    List<Integer> cityIDs = new ArrayList<Integer>();
    for (String cId : cIds) {
      if (cId.length() > 0) {
        cityIDs.add(Integer.parseInt(cId));
      }
    }
    return mCityMapper.selectCityTreeInfosByCityIds(cityIDs);
  }

  @Override
  public boolean checkSingleProductOrg(FinanceQueryBean cnd) {
    Integer count = mFinanceOrgMapper.selectIsNotSelfCount(cnd);
    return count.intValue() == 0 ? true : false;
  }

  @Override
  public List<MFinanceOrg> queryAllFinanceOrgsForSelect() {
    return mFinanceOrgMapper.selectAllFinanceOrgs();
  }

  @Override
  public List<MFinanceOrgVo> selectOrgProductList() {
    return mFinanceOrgMapper.selectOrgProductList();
  }
}
