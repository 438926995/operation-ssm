package com.eleme.controller.basic;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.eleme.annotation.controller.UserMenu;
import com.eleme.annotation.form.AvoidDuplicateSubmission;
import com.eleme.bean.JSONMessage;
import com.eleme.bean.SessionBean;
import com.eleme.bean.basic.BasicAddBean;
import com.eleme.controller.BaseController;
import com.eleme.domain.mart.basic.MBasicData;
import com.eleme.domain.mart.basic.MBasicDataKey;
import com.eleme.service.basic.IBasicService;
import com.eleme.util.TokenProcessor;
import com.eleme.util.pager.TbData;

import me.ele.elog.Log;
import me.ele.elog.LogFactory;

/**
 * 基础数据相关控制器
 * 
 * @author huwenwen
 *
 */
@Controller
@RequestMapping(value = "/basic")
public class BasicController extends BaseController {

  /**
   * log
   */
  private static Log log = LogFactory.getLog(BasicController.class);

  @Inject
  private IBasicService basicService;

  /**
   * 基础数据列表
   * 
   * @param request
   * @param response
   * @return
   * @throws Exception
   */
  @RequestMapping(value = "/list")
  @UserMenu
  public ModelAndView list(HttpServletRequest request, HttpServletResponse response,
      MBasicData basicDataKey, Integer currentPage) throws Exception {
    ModelAndView mav = new ModelAndView("/basic/list");
    // 记录日志
    SessionBean session = getSessionBean(request);
    log.info("{}跳转到基础数据列表界面", session.getUserName());
    // 封装查询条件
    basicDataKey.setTypeId(0);
    TbData tbData = basicService.getBasicDataList(currentPage, basicDataKey);
    tbData.fillTbData("basic/list", basicDataKey, null);
    mav.addObject("tbData", tbData);
    mav.addObject("bdk", basicDataKey);
    return mav;
  }

  /**
   * 编辑基础表
   * 
   * @param request
   * @param response
   * @param typeCd
   * @return
   */
  @RequestMapping(value = "/edit/{typeCd}", method = RequestMethod.GET)
  @AvoidDuplicateSubmission(needSaveToken = true)
  @UserMenu
  public ModelAndView edit(HttpServletRequest request, HttpServletResponse response,
      @PathVariable String typeCd) {
    ModelAndView mav = new ModelAndView("/basic/edit");
    // 记录日志
    SessionBean session = getSessionBean(request);
    log.info("{}跳转到基础数据编辑界面", session.getUserName());
    // 封装查询条件
    MBasicDataKey basicDataKey = new MBasicDataKey();
    basicDataKey.setTypeId(0);
    basicDataKey.setTypeCd(typeCd);
    mav.addObject("basic", basicService.queryBasicDataList(basicDataKey).get(0));
    return mav;
  }

  /**
   * 编辑保存
   * 
   * @param request
   * @param response
   * @param mbd
   * @param result
   * @return
   */
  @RequestMapping(value = "/editSave", method = RequestMethod.POST)
  @AvoidDuplicateSubmission(needRemoveToken = true)
  @UserMenu
  public ModelAndView editSave(HttpServletRequest request, HttpServletResponse response,
      MBasicData mbd) {
    ModelAndView mav = new ModelAndView();
    // 记录日志
    SessionBean sb = getSessionBean(request);
    log.info("{}保存基本数据信息[{}]", sb.getUserName(), mbd.getTypeCdName());
    // 调用service，得到影响行数
    int lines = basicService.updateBasicData(mbd);
    // 记录日志
    log.info("{}保存基本数据信息，影响行数{}", sb.getUserName(), lines);
    mav.setViewName("redirect:/basic/list");
    return mav;
  }

  /**
   * 添加基础数据
   * 
   * @param request
   * @param response
   * @return
   */
  @RequestMapping(value = "/add", method = RequestMethod.GET)
  @AvoidDuplicateSubmission(needSaveToken = true)
  @UserMenu
  public ModelAndView add(HttpServletRequest request, HttpServletResponse response) {
    ModelAndView mav = new ModelAndView("/basic/add");
    // 记录日志
    SessionBean session = getSessionBean(request);
    log.info("{}跳转到添加基础数据界面", session.getUserName());
    // 查找排序
    MBasicDataKey basicDataKey = new MBasicDataKey();
    basicDataKey.setTypeId(0);
    mav.addObject("maxIndex", basicService.selectMaxSortIndexById(basicDataKey));
    return mav;
  }

  /**
   * 添加基础数据保存
   * 
   * @param request
   * @param response
   * @param mbd
   * @param result 保留校验结果
   * @return
   * @throws Exception
   */
  @RequestMapping(value = "/addSave", method = RequestMethod.POST)
  @AvoidDuplicateSubmission(needRemoveToken = true)
  @UserMenu
  public ModelAndView addSave(HttpServletRequest request, HttpServletResponse response,
      @Valid @ModelAttribute("mbd") BasicAddBean mbd, BindingResult result) throws Exception {
    ModelAndView mav = new ModelAndView();
    // 验证
    if (result.hasErrors()) {
      mav.setViewName("basic/add");
      // 需要再次设置token
      request.getSession(false).setAttribute("token",
          TokenProcessor.getInstance().generateToken(request));
      return mav;
    }
    // 记录日志
    SessionBean session = getSessionBean(request);
    log.info("{}添加基础数据", session.getUserName());
    // 得到type_cd
    mbd.setTypeCd(basicService.getTypeCdAddOne(true));
    // clone UserAddBean 数据到 MUsers,field名称需相同
    MBasicData data = new MBasicData();
    BeanUtils.copyProperties(data, mbd);
    // 调用service，得到影响行数
    int lines = basicService.insertBasciData(data);
    // 记录日志
    log.info("{}添加基础数据，影响行数{}", session.getUserName(), lines);
    mav.setViewName("redirect:/basic/list");
    return mav;
  }

  /**
   * 基本数据详情列表
   * 
   * @param request
   * @param response
   * @param bdk
   * @param currentPage
   * @return
   * @throws Exception
   */
  @RequestMapping(value = "/detailList/{typeCd}", method = RequestMethod.GET)
  @UserMenu
  public ModelAndView detailList(HttpServletRequest request, HttpServletResponse response,
      MBasicData bdk, Integer currentPage) throws Exception {
    ModelAndView mav = new ModelAndView("/basic/detailList");
    // 记录日志
    SessionBean session = getSessionBean(request);
    log.info("{}跳转到基础数据详细列表界面", session.getUserName());
    // 封装查询条件 TYPE_CD变TYPE_ID 删除原来的TYPE_CD
    bdk.setTypeId(Integer.parseInt(bdk.getTypeCd()));
    bdk.setTypeCd(null);
    TbData tbData = basicService.getBasicDataList(currentPage, bdk);
    tbData.fillTbData("basic/detailList", bdk, null);
    mav.addObject("tbData", tbData);
    mav.addObject("bdk", bdk);
    return mav;
  }

  /**
   * 编辑基础表详情数据
   * 
   * @param request
   * @param response
   * @param typeCd
   * @return
   */
  @RequestMapping(value = "/detailEdit", method = RequestMethod.GET)
  @AvoidDuplicateSubmission(needSaveToken = true)
  @UserMenu
  public ModelAndView detailEdit(HttpServletRequest request, HttpServletResponse response,
      MBasicDataKey basicDataKey) {
    ModelAndView mav = new ModelAndView("/basic/detailEdit");
    // 记录日志
    SessionBean session = getSessionBean(request);
    log.info("{}跳转到基础详情数据编辑界面", session.getUserName());

    mav.addObject("basic", basicService.queryBasicDataList(basicDataKey).get(0));
    return mav;
  }

  /**
   * 详细信息编辑保存
   * 
   * @param request
   * @param response
   * @param mbd
   * @param result
   * @return
   */
  @RequestMapping(value = "/detailEditSave", method = RequestMethod.POST)
  @AvoidDuplicateSubmission(needRemoveToken = true)
  @UserMenu
  public ModelAndView detailEditSave(HttpServletRequest request, HttpServletResponse response,
      MBasicData mbd) {
    ModelAndView mav = new ModelAndView();
    // 记录日志
    SessionBean sb = getSessionBean(request);
    log.info("{}保存基本数据详情信息[{}]", sb.getUserName(), mbd.getTypeCdName());
    // 调用service，得到影响行数
    int lines = basicService.updateBasicDataDetail(mbd);
    // 记录日志
    log.info("{}保存基本数据详情信息，影响行数{}", sb.getUserName(), lines);
    mav.setViewName("redirect:/basic/detailList/" + mbd.getTypeId());
    return mav;
  }

  /**
   * 添加基础详情数据
   * 
   * @param request
   * @param response
   * @return
   */
  @RequestMapping(value = "/detailAdd", method = RequestMethod.GET)
  @AvoidDuplicateSubmission(needSaveToken = true)
  @UserMenu
  public ModelAndView detailAdd(HttpServletRequest request, HttpServletResponse response,
      MBasicDataKey mbd) {
    ModelAndView mav = new ModelAndView("/basic/detailAdd");
    // 记录日志
    SessionBean session = getSessionBean(request);
    log.info("{}跳转到添加基础数据界面", session.getUserName());
    Integer maxIndex = basicService.selectMaxSortIndexById(mbd);
    // 封装查询条件
    mbd.setTypeCd(String.valueOf(mbd.getTypeId()));
    mbd.setTypeId(0);
    mav.addObject("basic", basicService.queryBasicDataList(mbd).get(0));
    mav.addObject("maxIndex", maxIndex);
    return mav;
  }

  /**
   * 添加基础详情数据保存
   * 
   * @param request
   * @param response
   * @param mbd
   * @return
   */
  @RequestMapping(value = "/detailAddSave", method = RequestMethod.POST)
  @AvoidDuplicateSubmission(needRemoveToken = true)
  @UserMenu
  public ModelAndView detailAddSave(HttpServletRequest request, HttpServletResponse response,
      @ModelAttribute("mbd") MBasicData mbd) {
    ModelAndView mav = new ModelAndView();
    // 记录日志
    SessionBean session = getSessionBean(request);
    log.info("{}添加基础数据", session.getUserName());
    // 设置typeCd
    mbd.setTypeCd(basicService.getTypeCdAddOne(false));
    // 调用service，得到影响行数
    int lines = basicService.insertBasciData(mbd);
    // 记录日志
    log.info("{}添加基础数据，影响行数{}", session.getUserName(), lines);
    mav.setViewName("redirect:/basic/detailList/" + mbd.getTypeId());
    return mav;
  }

  /**
   * 异步返回typeCdName名是否唯一.
   * 
   * @param request
   * @param response
   * @param rnb 前台传来的json字符串，例如js：JSON.stringify(data).（如果是json对象，会报错）
   * @return JSONMessage封装类
   * @throws Exception
   */
  @RequestMapping(value = "/judgeTypeCdNameSingle", method = RequestMethod.POST)
  @ResponseBody
  public JSONMessage judgeTypeCdNameSingle(HttpServletRequest request, HttpServletResponse response,
      @RequestBody MBasicData mbd) throws Exception {
    SessionBean session = getSessionBean(request);
    // 判断是否是原来的名字
    if (mbd.getTypeCd() != null) {
      MBasicDataKey basicDataKey = new MBasicDataKey();
      basicDataKey.setTypeCd(mbd.getTypeCd());
      String typeCdName = basicService.queryBasicDataList(basicDataKey).get(0).getTypeCdName();
      // 和原来名字相同不做下面判断
      if (mbd.getTypeCdName().equals(typeCdName)) {
        return new JSONMessage(true, "规则类型名唯一");
      }
    }
    boolean flag = basicService.judgeIfTypeCdNameSingle(mbd);
    log.info("{}添加基础数据，判断{}是否唯一,结果{}", session.getUserName(), mbd.getTypeCdName(), flag);
    if (flag) {
      return new JSONMessage(flag, "规则类型名唯一");
    } else {
      return new JSONMessage(flag, "该规则类型名已存在");
    }
  }

}
