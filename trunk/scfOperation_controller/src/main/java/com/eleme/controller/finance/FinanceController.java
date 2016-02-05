package com.eleme.controller.finance;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.eleme.annotation.controller.UserMenu;
import com.eleme.annotation.form.AvoidDuplicateSubmission;
import com.eleme.bean.JSONMessage;
import com.eleme.bean.SessionBean;
import com.eleme.bean.file.SysUploadFileBean;
import com.eleme.bean.finance.FinanceAddBean;
import com.eleme.bean.finance.FinanceAddUserBean;
import com.eleme.bean.finance.FinanceEditBean;
import com.eleme.bean.finance.FinanceEditUserBean;
import com.eleme.bean.finance.FinanceUserQueryBean;
import com.eleme.controller.BaseController;
import com.eleme.domain.mart.city.TFcoCoveredCity;
import com.eleme.domain.mart.finance.FinanceQueryBean;
import com.eleme.domain.mart.finance.MFinanceOrg;
import com.eleme.domain.mart.martusers.FinanceUserQueryCnd;
import com.eleme.domain.mart.martusers.MMartUsers;
import com.eleme.domain.mart.martusers.MMartUsersVo;
import com.eleme.service.file.IFileService;
import com.eleme.service.finance.IFinanceService;
import com.eleme.util.TokenProcessor;
import com.eleme.util.pager.TbData;

import me.ele.elog.Log;
import me.ele.elog.LogFactory;

/**
 * 金融机构方法控制器。
 * 
 * @author yonglin.zhu
 *
 */
@Controller
@RequestMapping(value = "/finance")
public class FinanceController extends BaseController {
  /**
   * log
   */
  private static Log log = LogFactory.getLog(FinanceController.class);

  @Inject
  private IFinanceService financeService;

  @Inject
  private IFileService fileService;

  /**
   * 跳转到添加金融机构的页面.
   * 
   * @param request
   * @param response
   * @return
   * @throws Exception
   */
  @RequestMapping(value = "/addView", method = RequestMethod.GET)
  @AvoidDuplicateSubmission(needSaveToken = true)
  @UserMenu
  public ModelAndView addView(HttpServletRequest request, HttpServletResponse response)
      throws Exception {
    ModelAndView mav = new ModelAndView("finance/add");
    // 记录日志
    SessionBean session = getSessionBean(request);
    log.info("{}跳转到添加金融机构界面", session.getUserName());
    // 返回
    return mav;
  }



  /**
   * 添加金融机构.
   * 
   * @param request
   * @param response
   * @param uab 添加金融机构 的表单封装bean
   * @param result 保留校验结果
   * @return
   * @throws Exception
   */
  @RequestMapping(value = "/addSave", method = RequestMethod.POST)
  @AvoidDuplicateSubmission(needRemoveToken = true)
  @UserMenu
  public ModelAndView addSave(HttpServletRequest request, HttpServletResponse response,
      @Valid @ModelAttribute("fab") FinanceAddBean fab, BindingResult result) throws Exception {
    // @RequestParam(value = "file", required = false) MultipartFile file,
    ModelAndView mav = new ModelAndView();

    // 验证
    if (result.hasErrors()) {   
      mav.setViewName("finance/add");
      // 需要再次设置token
      request.getSession(false).setAttribute("token",
          TokenProcessor.getInstance().generateToken(request));
      return mav;
    }
    // 记录日志
    SessionBean session = getSessionBean(request);
    log.info("{}添加金融机构信息", session.getUserName());
    // clone数据到MFinanceOrg
    MFinanceOrg mf = new MFinanceOrg();
    BeanUtils.copyProperties(mf, fab);
    // 调用service，得到影响行数
    int lines = financeService.financeAdd(mf);
    // 记录日志
    log.info("{}添加金融机构信息，影响行数{}", session.getUserName(), lines);
    // 将覆盖城市数据进行封装
    Map<String, Object> map = new HashMap<String, Object>();
    map.put("cityNames", fab.getFoCoverCityNames());
    map.put("foId", mf.getFoId());
    // 调用service，得到影响行数
    int tFlines = financeService.addCoveredCityInfos(map);
    // 记录日志
    log.info("{}添加金融机构覆盖城市信息，影响行数{}", session.getUserName(), tFlines);
    // 返回
    mav.setViewName("redirect:/finance/list");
    return mav;
  }

  /**
   * 查询金融机构信息
   * 
   * @param request
   * @param fqb
   * @param currentPage
   * @return
   */
  @RequestMapping(value = "/list", method = RequestMethod.GET)
  @UserMenu
  public ModelAndView list(HttpServletRequest request, FinanceQueryBean fqb, Integer currentPage)
      throws Exception {
    // 记录日志
    SessionBean session = getSessionBean(request);
    log.info("{}查询金融机构列表", session.getUserName());
    // 查询数据并返回
    ModelAndView mav = new ModelAndView("finance/list");
    TbData tbData = financeService.selectFinanceList(fqb, currentPage);
    tbData = tbData.fillTbData("finance/list", fqb, null);
    mav.addObject("tbData", tbData);
    mav.addObject("fqb", fqb);
    return mav;
  }

  /**
   * 获取商户金融机构信息
   * 
   * @param request
   * @param response
   * @param res_name
   * @param mobile
   * @param res_addr
   * @param napos_resid
   * @return
   * @throws Exception
   */
  @RequestMapping(value = "/getFinanceInfo", method = RequestMethod.GET,
      produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  public Map<String, Object> getFinanceInfo(HttpServletRequest request,
      HttpServletResponse response, Integer foId) throws Exception {
    // 记录日志
    SessionBean session = getSessionBean(request);
    log.info("{}根据金融机构ID{}获取的信息", session.getUserName(), foId);
    // 查询 金融机构信息
    Map<String, Object> map = financeService.queryFinanceById(foId);
    return map;
  }

  /**
   * 跳转到添加金融机构账号的页面.
   * 
   * @param request
   * @param response
   * @return
   * @throws Exception
   */
  @RequestMapping(value = "/addUserView/{foId}", method = RequestMethod.GET)
  @AvoidDuplicateSubmission(needSaveToken = true)
  @UserMenu
  public ModelAndView addUserView(HttpServletRequest request, HttpServletResponse response,
      @PathVariable Integer foId) throws Exception {
    ModelAndView mav = new ModelAndView("finance/useradd");
    // 记录日志
    SessionBean session = getSessionBean(request);
    log.info("{}跳转到添加账户界面", session.getUserName());
    MFinanceOrg financeOrg = financeService.queryFinanceInfoById(foId);
    mav.addObject("financeOrg", financeOrg);
    // 返回
    return mav;
  }

  /**
   * 添加金融机构账号.
   * 
   * @param request
   * @param response
   * @param uab 添加金融机构 的表单封装bean
   * @param result 保留校验结果
   * @return
   * @throws Exception
   */
  @RequestMapping(value = "/addUserSave", method = RequestMethod.POST)
  @AvoidDuplicateSubmission(needRemoveToken = true)
  @UserMenu
  public ModelAndView addUserSave(HttpServletRequest request, HttpServletResponse response,
      @Valid @ModelAttribute("fab") FinanceAddUserBean fab, BindingResult result) throws Exception {
    ModelAndView mav = new ModelAndView();
    // 验证
    if (result.hasErrors()) {
      mav.setViewName("finance/useradd");
      // 需要再次设置token
      request.getSession(false).setAttribute("token",
          TokenProcessor.getInstance().generateToken(request));
      MFinanceOrg financeOrg = financeService.queryFinanceInfoById(Integer.valueOf(fab.getFoId()));
      mav.addObject("financeOrg", financeOrg);
      return mav;
    }
    // 记录日志
    SessionBean session = getSessionBean(request);
    log.info("{}添加金融机构账户信息", session.getUserName());
    // clone数据到MFinanceOrg
    MMartUsers martUsers = new MMartUsers();
    BeanUtils.copyProperties(martUsers, fab);
    martUsers.setCreateUser(session.getUserId().intValue());
    martUsers.setCreateUserName(session.getUserName());
    // 调用service，得到影响行数
    int lines = financeService.financeAddUsers(martUsers, fab.getIsApply());
    // 记录日志
    log.info("{}添加金融机构账户信息，影响行数{}", session.getUserName(), lines);
    // 返回
    mav.setViewName("redirect:/finance/userlist/" + fab.getFoId());
    return mav;
  }

  /**
   * 跳转到添加金融机构的页面.
   * 
   * @param request
   * @param response
   * @return
   * @throws Exception
   */
  @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
  @AvoidDuplicateSubmission(needSaveToken = true)
  @UserMenu
  public ModelAndView editView(HttpServletRequest request, HttpServletResponse response,
      @PathVariable Integer id) throws Exception {
    ModelAndView mav = new ModelAndView("finance/edit");
    // 记录日志
    SessionBean session = getSessionBean(request);
    MFinanceOrg mFinanceOrg = financeService.queryFinanceInfoById(id);
    if (mFinanceOrg == null) {
      log.info("未找到对应的金融机构");
      mav.setViewName("account/notfound");
    } else {
      log.info("{}跳转到编辑金融机构界面", session.getUserName());
      FinanceAddBean fab = new FinanceAddBean();
      BeanUtils.copyProperties(fab, mFinanceOrg);
      List<TFcoCoveredCity> tFcoCoveredCitys = financeService.queryTFcoCoverCityInfosByFoID(id);
      String cityNames = "";
      if (tFcoCoveredCitys.size() != 0) {
        for (TFcoCoveredCity tFC : tFcoCoveredCitys) {
          cityNames += "," + tFC.getCityName();
        }
        cityNames = cityNames.substring(1, cityNames.length());
      }
      fab.setFoCoverCityNames(cityNames);

      // 根据金融机构id，到t_sys_upload_file中查询最新记录
      if(mFinanceOrg != null){
        SysUploadFileBean file = fileService.getUploadFileById(mFinanceOrg.getUfId());
        mav.addObject("url", file == null ? "" : file.getUrl());
      }
//      SysUploadFileBean file = fileService.getLatestUploadFileByContainId(
//          Integer.parseInt(fab.getFoId()), FileType.FINANCE_LOGO.toString());
//      if (file != null) {
//        mav.addObject("url", file.getUrl());
//      }
      mav.addObject("fab", fab);
    }
    // 返回
    return mav;
  }

  /**
   * 添加金融机构.
   * 
   * @param request
   * @param response
   * @param uab 添加金融机构 的表单封装bean
   * @param result 保留校验结果
   * @return
   * @throws Exception
   */
  @RequestMapping(value = "/editSave", method = RequestMethod.POST)
  @AvoidDuplicateSubmission(needRemoveToken = true)
  @UserMenu
  public ModelAndView editSave(HttpServletRequest request, HttpServletResponse response,
      @Valid @ModelAttribute("fab") FinanceEditBean fab, BindingResult result) throws Exception {
    ModelAndView mav = new ModelAndView();
    // 验证
    if (result.hasErrors()) {
      mav.setViewName("finance/edit");
      // 需要再次设置token
      request.getSession(false).setAttribute("token",
          TokenProcessor.getInstance().generateToken(request));
      return mav;
    }
    // 业务验证
    FinanceQueryBean cnd = new FinanceQueryBean();
    cnd.setFoId(Integer.parseInt(fab.getFoId()));
    cnd.setFoName(fab.getFoName());
    if (!financeService.checkSingleProductOrg(cnd)) {
      result.addError(new FieldError("fab", "foName", "机构名称已存在"));
      // 验证
      if (result.hasErrors()) {
        mav.setViewName("finance/edit");
        // 需要再次设置token
        request.getSession(false).setAttribute("token",
            TokenProcessor.getInstance().generateToken(request));
        return mav;
      }
    }
    // 记录日志
    SessionBean session = getSessionBean(request);
    log.info("{}修改金融机构信息", session.getUserName());
    // clone数据到MFinanceOrg
    MFinanceOrg mf = new MFinanceOrg();
    BeanUtils.copyProperties(mf, fab);
    // 调用service，得到影响行数
    int lines = financeService.updateFinance(mf);
    // 记录日志
    log.info("{}修改金融机构信息，影响行数{}", session.getUserName(), lines);

    Map<String, Object> resultMap = financeService.queryFinanceByName(fab.getFoName());
    MFinanceOrg mFinanceOrg = (MFinanceOrg) resultMap.get("financeInfo");
    // 将覆盖城市数据进行封装
    Map<String, Object> map = new HashMap<String, Object>();
    map.put("cityNames", fab.getFoCoverCityNames());
    map.put("foId", mFinanceOrg.getFoId());
    // 调用service，得到影响行数
    int tFlines = financeService.addCoveredCityInfos(map);
    // 记录日志
    log.info("{}添加金融机构覆盖城市信息，影响行数{}", session.getUserName(), tFlines);
    // 返回
    mav.setViewName("redirect:/finance/list");
    return mav;
  }

  /**
   * 查询金融机构账户信息
   * 
   * @param request
   * @param fqb
   * @param currentPage
   * @return
   * @throws Exception
   */
  @RequestMapping(value = "/userlist/{foId}", method = RequestMethod.GET)
  @UserMenu
  public ModelAndView userlist(HttpServletRequest request, @PathVariable Integer foId,
      FinanceUserQueryBean fqb, Integer currentPage) throws Exception {
    // 记录日志
    SessionBean session = getSessionBean(request);
    log.info("{}查询金融机构账户列表", session.getUserName());
    // 查询数据并返回
    ModelAndView mav = new ModelAndView("finance/userlist");
    FinanceUserQueryCnd fqbc = new FinanceUserQueryCnd();
    if (foId != null) {
      fqbc.setFoId(foId);
    }
    fqbc.setUserName(fqb.getUserName());
    TbData tbData = financeService.selectFinanceUserList(fqbc, currentPage);
    tbData = tbData.fillTbData("finance/userlist/" + foId.intValue(), fqb, null);
    mav.addObject("tbData", tbData);
    mav.addObject("fqb", fqb);
    mav.addObject("foId", foId);
    return mav;
  }

  /**
   * 跳转到编辑金融机构账户的页面.
   * 
   * @param request
   * @param response
   * @return
   * @throws Exception
   */
  @RequestMapping(value = "/editUserView/{id}", method = RequestMethod.GET)
  @AvoidDuplicateSubmission(needSaveToken = true)
  @UserMenu
  public ModelAndView editUserView(HttpServletRequest request, HttpServletResponse response,
      @PathVariable Integer id) throws Exception {
    ModelAndView mav = new ModelAndView("finance/useredit");
    // 记录日志
    SessionBean session = getSessionBean(request);
    MMartUsersVo mMartUsersVo = financeService.queryFinanceUserInfoById(id);
    if (mMartUsersVo == null) {
      log.info("未找到对应的金融机构账户");
      mav.setViewName("account/notfound");
    } else {
      log.info("{}跳转到编辑金融机构界面", session.getUserName());
      FinanceAddUserBean fab = new FinanceAddUserBean();
      BeanUtils.copyProperties(fab, mMartUsersVo);
      mav.addObject("fab", fab);
    }
    // 返回
    return mav;
  }

  /**
   * 编辑金融机构.
   * 
   * @param request
   * @param response
   * @param uab 添加金融机构 的表单封装bean
   * @param result 保留校验结果
   * @return
   * @throws Exception
   */
  @RequestMapping(value = "/editUserSave", method = RequestMethod.POST)
  @AvoidDuplicateSubmission(needRemoveToken = true)
  @UserMenu
  public ModelAndView editUserSave(HttpServletRequest request, HttpServletResponse response,
      @Valid @ModelAttribute("fab") FinanceEditUserBean fab, BindingResult result)
          throws Exception {
    ModelAndView mav = new ModelAndView();
    // 验证
    if (result.hasErrors()) {
      mav.setViewName("finance/useredit");
      // 需要再次设置token
      request.getSession(false).setAttribute("token",
          TokenProcessor.getInstance().generateToken(request));
      return mav;
    }
    // 业务验证
    FinanceUserQueryCnd fuqc = new FinanceUserQueryCnd();
    fuqc.setUserId(Integer.parseInt(fab.getUserId()));
    fuqc.setUserName(fab.getUserName());
    if (!financeService.checkSingleMartUser(fuqc)) {
      result.addError(new FieldError("fab", "userName", "账号名称已存在"));
      // 验证
      if (result.hasErrors()) {
        mav.setViewName("finance/useredit");
        // 需要再次设置token
        request.getSession(false).setAttribute("token",
            TokenProcessor.getInstance().generateToken(request));
        return mav;
      }
    }

    // 记录日志
    SessionBean session = getSessionBean(request);
    log.info("{}修改金融机构账户信息", session.getUserName());
    // clone数据到MFinanceOrg
    MMartUsers mMartUsers = new MMartUsers();
    BeanUtils.copyProperties(mMartUsers, fab);
    // 调用service，得到影响行数
    int lines = financeService.updateFinanceUser(mMartUsers);
    // 记录日志
    log.info("{}修改金融机构账户信息，影响行数{}", session.getUserName(), lines);
    // 返回
    mav.setViewName("redirect:/finance/userlist/" + fab.getFoId());
    return mav;
  }

  /**
   * 删除商户金融机构信息
   * 
   * @param request
   * @param response
   * @param res_name
   * @param mobile
   * @param res_addr
   * @param napos_resid
   * @return
   * @throws Exception
   */
  @RequestMapping(value = "/deleteUser", method = RequestMethod.GET,
      produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  public JSONMessage deleteUser(HttpServletRequest request, HttpServletResponse response,
      Integer userId) throws Exception {
    // 记录日志
    SessionBean session = getSessionBean(request);
    log.info("{}删除金融机构账户的信息,账户ID:{}", session.getUserName(), userId);
    // 修改金融机构账户信息
    MMartUsers mMartUsers = new MMartUsers();
    mMartUsers.setUserId(userId);
    mMartUsers.setUserStatus(3);
    // 调用service
    int lines = financeService.updateFinanceUser(mMartUsers);
    // 记录日志
    log.info("{}修改金融机构账户信息，影响行数{}", session.getUserName(), lines);
    return new JSONMessage(true, "删除成功");
  }

  /**
   * 根据机构foID 查询金融机构信息 查询参数金融机构名
   * 
   * @param request
   * @param response
   * @param foID 金融机构id
   * @return 返回金融机构信息 不存在返回null
   * @throws Exception
   */
  @RequestMapping(value = "/getCoverCityInfoByFoID", method = RequestMethod.GET,
      produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  public Map<String, Object> getCoverCityInfoByFoID(HttpServletRequest request,
      HttpServletResponse response, int foID) throws Exception {
    // 记录日志
    SessionBean session = getSessionBean(request);
    log.info("{}跳转到选择城市界面", session.getUserName());

    // 查询 金融机构信息
    List<TFcoCoveredCity> tFcoCoveredCityInfos = financeService.queryTFcoCoverCityInfosByFoID(foID);

    List<String> cities = new ArrayList<String>();
    HashSet<String> provSet = new HashSet<String>();
    List<String> provs = new ArrayList<String>();
    List<String> defaultCityList = new ArrayList<String>();
    for (TFcoCoveredCity tFcoCoveredCityInfo : tFcoCoveredCityInfos) {
      cities.add(tFcoCoveredCityInfo.getCityName());
      if (tFcoCoveredCityInfo.getProvID() == 0) {
        provSet.add(tFcoCoveredCityInfo.getCityName());
      } else {
        provSet.add(tFcoCoveredCityInfo.getProvName());
      }
    }

    for (String prov : provSet) {
      provs.add(prov);
    }
    if (provs.size() > 0) {
      defaultCityList = financeService.queryCitiesByProvName(provs.get(0));
      if (defaultCityList.size() == 0) {
        defaultCityList.add(provs.get(0));
      }
    }
    Map<String, Object> resultMap = new HashMap<String, Object>();
    resultMap.put("cities", cities);
    resultMap.put("provs", provs);
    resultMap.put("defaultCityList", defaultCityList);

    return resultMap;
  }

  /**
   * 根据省份名查询城市列表
   * 
   * @param request
   * @param response
   * @param provName 省份名
   * @return 城市列表 不存在返回bull
   * @throws SQLException
   */
  @RequestMapping(value = "/getCitiesByProvName", method = RequestMethod.GET,
      produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  public List<String> getCitiesByProvName(HttpServletRequest request, HttpServletResponse response,
      String provName) throws Exception {
    // 记录日志
    SessionBean session = getSessionBean(request);
    log.info("{}根据金融机构NAME{}获取的信息", session.getUserName(), provName);
    // 查询城市列表
    List<String> cityList = new ArrayList<String>();
    cityList = financeService.queryCitiesByProvName(provName);
    if (cityList.size() == 0) {
      cityList.add(provName);
    }
    return cityList;
  }

  /**
   * 获取所有省份名
   * 
   * @param request
   * @param response
   * @return 省份名列表 不存在返回null
   * @throws SQLException
   */
  @RequestMapping(value = "/getdefaultProvAndCity", method = RequestMethod.GET,
      produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  public Map<String, Object> getdefaultProvAndCity(HttpServletRequest request,
      HttpServletResponse response) throws Exception {
    // 记录日志
    SessionBean session = getSessionBean(request);
    log.info("{}根据金融机构NAME{}获取的信息", session.getUserName());
    Map<String, Object> resultMap = new HashMap<String, Object>();
    // 查询省份列表
    List<String> provList = financeService.queryAllProvName();
    List<String> defaultCityList = new ArrayList<String>();
    if (provList.size() > 0) {
      defaultCityList = financeService.queryCitiesByProvName(provList.get(0));
      if (defaultCityList.size() == 0) {
        defaultCityList.add(provList.get(0));
      }
    }
    resultMap.put("provs", provList);
    resultMap.put("defaultCityList", defaultCityList);

    return resultMap;
  }
}
