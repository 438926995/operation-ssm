package com.eleme.controller.flow;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.eleme.annotation.controller.UserMenu;
import com.eleme.annotation.form.AvoidDuplicateSubmission;
import com.eleme.bean.SessionBean;
import com.eleme.bean.apv.ApvFlowProcessQueryBean;
import com.eleme.bean.apv.ApvFlowTeamQueryBean;
import com.eleme.controller.BaseController;
import com.eleme.domain.mart.finance.FinanceQueryBean;
import com.eleme.domain.mart.finance.MFinanceOrg;
import com.eleme.domain.mart.loan.TApvFlowNode;
import com.eleme.domain.mart.loan.TApvFlowProcess;
import com.eleme.domain.mart.loan.TApvFlowTeam;
import com.eleme.domain.mart.martusers.MMartUsers;
import com.eleme.domain.mart.product.MFinanceProductVo;
import com.eleme.domain.mart.product.ProductQueryCnd;
import com.eleme.domain.ops.UserRole;
import com.eleme.service.finance.IFinanceService;
import com.eleme.service.flow.IFlowManageService;
import com.eleme.service.product.IProductService;
import com.eleme.service.user.IUserService;
import com.eleme.util.TokenProcessor;
import com.eleme.util.pager.TbData;

import me.ele.elog.Log;
import me.ele.elog.LogFactory;

/**
 * 流程管理.
 * 
 * @author penglau
 *
 */
@Controller
@RequestMapping(value = "/flowmanage")
public class FlowManageController extends BaseController {

  /**
   * log
   */
  private static Log log = LogFactory.getLog(FlowManageController.class);

  @Inject
  private IFlowManageService flowManageServiceImpl;

  @Inject
  private IFinanceService financeService;

  @Inject
  private IProductService productService;

  @Inject
  private IUserService userService;

  /**
   * 查询 审批组信息
   * 
   * @param request
   * @param response
   * @param currentPage
   * @param aftqb
   * @return
   * @throws Exception
   */
  @RequestMapping(value = "/apvFlowTeamlist", method = RequestMethod.GET)
  @AvoidDuplicateSubmission()
  @UserMenu

  public ModelAndView apvFlowTeamList(HttpServletRequest request, HttpServletResponse response,
      Integer currentPage, ApvFlowTeamQueryBean aftqb) throws Exception {
    // 记录日志
    SessionBean session = getSessionBean(request);
    log.info("{}查看审批组列表", session.getUserName());

    // 查询数据并返回
    ModelAndView mav = new ModelAndView("system/apvTeamlist");
    TbData tbData = flowManageServiceImpl.getApvFlowTeamList(currentPage, aftqb);
    tbData.fillTbData("flowmanage/apvFlowTeamlist", aftqb, null);
    mav.addObject("aftqb", aftqb);
    mav.addObject("tbData", tbData);
    return mav;
  }

  /**
   * 跳转到添加审批组
   * 
   * @param request
   * @param response
   * @return
   * @throws Exception
   */
  @RequestMapping(value = "/addTeamView", method = RequestMethod.GET)
  @AvoidDuplicateSubmission(needSaveToken = true)
  @UserMenu
  public ModelAndView addTeamView(HttpServletRequest request, HttpServletResponse response)
      throws Exception {
    ModelAndView mav = new ModelAndView("system/addApvTeam");
    // 记录日志
    SessionBean session = getSessionBean(request);
    log.info("{}跳转到添加审批组界面", session.getUserName());
    // 查询金融机构信息
    FinanceQueryBean financeQueryBean = new FinanceQueryBean();
    financeQueryBean.setFoStatus("1");
    List<MFinanceOrg> financeList = financeService.selectFinanceList(financeQueryBean);
    mav.addObject("financeList", financeList);
    // 返回
    return mav;
  }

  /**
   * 审批组添加 保存
   * 
   * @param request
   * @param response
   * @param aftb
   * @param result
   * @return
   * @throws Exception
   */
  @RequestMapping(value = "/addTeamSave", method = RequestMethod.POST)
  @AvoidDuplicateSubmission(needRemoveToken = true)
  @UserMenu
  public ModelAndView addTeamSave(HttpServletRequest request, HttpServletResponse response,
      @Valid @ModelAttribute("aftb") TApvFlowTeam aftb, BindingResult result) throws Exception {
    ModelAndView mav = new ModelAndView();
    aftb.setTeamName("FO" + aftb.getTeamName());
    // 验证
    if (result.hasErrors()) {
      mav.setViewName("system/addApvTeam");
      // 需要再次设置token
      request.getSession(false).setAttribute("token",
          TokenProcessor.getInstance().generateToken(request));
      return mav;
    }
    // 记录日志
    SessionBean session = getSessionBean(request);
    // 调用service，得到影响行数
    int lines = flowManageServiceImpl.addApvTeam(aftb);
    // 记录日志
    log.info("{}添加审批组信息", session.getUserName(), lines);
    // 返回
    mav.setViewName("redirect:/flowmanage/apvFlowTeamlist");
    return mav;
  }

  /**
   * 根据审批组名查询 审批组是否存在
   * 
   * @param request
   * @param response
   * @param teamName
   * @return
   * @throws Exception
   */
  @RequestMapping(value = "/getTeamByTeamname", method = RequestMethod.GET)
  @AvoidDuplicateSubmission(needSaveToken = true)
  @ResponseBody
  public Map<String, Object> getTeamByTeamname(HttpServletRequest request,
      HttpServletResponse response, String teamName) throws Exception {
    // 记录日志
    SessionBean session = getSessionBean(request);
    log.info("{}查询审批组是否存在", session.getUserName());
    Map<String, Object> map = new HashMap<>();
    // 查询金融机构 信贷员信息
    TApvFlowTeam team = flowManageServiceImpl.getTeamByTeamname(teamName);
    boolean isExist = false;
    if (team != null) {
      isExist = true;
    }
    map.put("isExist", isExist);
    map.put("team", team);
    // 返回
    return map;
  }

  /**
   * 根据金融机构ID 查询金融机构信贷员信息
   * 
   * @param request
   * @param response
   * @param foId
   * @return
   * @throws Exception
   */
  @RequestMapping(value = "/getUsersByFoID", method = RequestMethod.GET)
  @AvoidDuplicateSubmission(needSaveToken = true)
  @ResponseBody
  public Map<String, Object> getUsersByFoID(HttpServletRequest request,
      HttpServletResponse response, Integer foId) throws Exception {
    // 记录日志
    SessionBean session = getSessionBean(request);
    log.info("{}查询金融机构信贷员信息", session.getUserName());
    Map<String, Object> map = new HashMap<>();
    // 查询金融机构 信贷员信息
    List<MMartUsers> users = flowManageServiceImpl.getMartUsersByFoId(foId);
    List<Integer> userIds = new ArrayList<>();
    List<String> userNames = new ArrayList<>();
    for (MMartUsers user : users) {
      userIds.add(user.getUserId());
      userNames.add(user.getUserName());
    }
    map.put("userIds", userIds);
    map.put("userNames", userNames);
    // 返回
    return map;
  }

  /**
   * 根据审批组Id 进入编辑审批组界面
   * 
   * @param request
   * @param response
   * @param ftId
   * @return
   * @throws Exception
   */
  @RequestMapping(value = "/editTeamView/{ftId}", method = RequestMethod.GET)
  @AvoidDuplicateSubmission(needSaveToken = true)
  @UserMenu
  public ModelAndView editTeamView(HttpServletRequest request, HttpServletResponse response,
      @PathVariable Integer ftId) throws Exception {
    ModelAndView mav = new ModelAndView("system/editApvTeam");
    // 记录日志
    SessionBean session = getSessionBean(request);
    TApvFlowTeam aftb = flowManageServiceImpl.getApvFlowTeamById(ftId);
    log.info("{}跳转到编辑审批组界面", session.getUserName());
    mav.addObject("aftb", aftb);
    // 返回
    return mav;
  }

  /**
   * 审批组编辑保存
   * 
   * @param request
   * @param response
   * @param aftb
   * @param result
   * @return
   * @throws Exception
   */
  @RequestMapping(value = "/editTeamSave", method = RequestMethod.POST)
  @AvoidDuplicateSubmission(needRemoveToken = true)
  @UserMenu
  public ModelAndView editTeamSave(HttpServletRequest request, HttpServletResponse response,
      @Valid @ModelAttribute("aftb") TApvFlowTeam aftb, BindingResult result) throws Exception {
    ModelAndView mav = new ModelAndView();
    // 验证
    if (result.hasErrors()) {
      mav.setViewName("system/editApvTeam");
      // 需要再次设置token
      request.getSession(false).setAttribute("token",
          TokenProcessor.getInstance().generateToken(request));
      return mav;
    }
    // 记录日志
    SessionBean session = getSessionBean(request);
    // 调用service，得到影响行数
    int lines = flowManageServiceImpl.updateApvTeam(aftb);
    // 记录日志
    log.info("{}修改审批组信息保存", session.getUserName(), lines);
    // 返回
    mav.setViewName("redirect:/flowmanage/apvFlowTeamlist");
    return mav;
  }

  /**
   * 查询审批流信息
   * 
   * @param request
   * @param response
   * @param currentPage
   * @param afpqb
   * @return
   * @throws Exception
   */
  @RequestMapping(value = "/apvFlowProcslist", method = RequestMethod.GET)
  @AvoidDuplicateSubmission()
  @UserMenu
  public ModelAndView apvFlowProcslist(HttpServletRequest request, HttpServletResponse response,
      Integer currentPage, ApvFlowProcessQueryBean afpqb) throws Exception {
    // 记录日志
    SessionBean session = getSessionBean(request);
    log.info("{}查看审批流列表", session.getUserName());

    // 查询数据并返回
    ModelAndView mav = new ModelAndView("system/apvProcslist");
    TbData tbData = flowManageServiceImpl.getApvFlowProcsList(currentPage, afpqb);
    tbData.fillTbData("flowmanage/apvProcslist", afpqb, null);
    mav.addObject("afpqb", afpqb);
    mav.addObject("tbData", tbData);
    return mav;
  }

  /**
   * 跳转到添加审批流界面
   * 
   * @param request
   * @param response
   * @return
   * @throws Exception
   */
  @RequestMapping(value = "/addProcsView", method = RequestMethod.GET)
  @AvoidDuplicateSubmission(needSaveToken = true)
  @UserMenu
  public ModelAndView addProcsView(HttpServletRequest request, HttpServletResponse response)
      throws Exception {
    ModelAndView mav = new ModelAndView("system/addApvProcs");
    SessionBean session = getSessionBean(request);
    log.info("{}跳转到审批流添加界面", session.getUserName());

    FinanceQueryBean financeQueryBean = new FinanceQueryBean();
    financeQueryBean.setFoStatus("1");
    List<MFinanceOrg> financeList = financeService.selectFinanceList(financeQueryBean);

    mav.addObject("financeList", financeList);
    // 返回
    return mav;
  }

  /**
   * 审批流添加 保存
   * 
   * @param request
   * @param response
   * @param afp
   * @param result
   * @return
   * @throws Exception
   */
  @RequestMapping(value = "/addProcsSave", method = RequestMethod.POST)
  @AvoidDuplicateSubmission(needRemoveToken = true)
  @UserMenu
  public ModelAndView addProcsSave(HttpServletRequest request, HttpServletResponse response,
      @Valid @ModelAttribute("afp") TApvFlowProcess afp, BindingResult result) throws Exception {
    ModelAndView mav = new ModelAndView();
    // 验证
    if (result.hasErrors()) {
      mav.setViewName("system/editApvProcs");
      // 需要再次设置token
      request.getSession(false).setAttribute("token",
          TokenProcessor.getInstance().generateToken(request));
      return mav;
    }
    // 记录日志
    SessionBean session = getSessionBean(request);
    // 调用service，得到影响行数
    int lines = flowManageServiceImpl.addApvProcs(afp);
    // 记录日志
    log.info("{}修改审批组信息保存", session.getUserName(), lines);
    // 返回
    mav.setViewName("redirect:/flowmanage/apvFlowProcslist");
    return mav;
  }

  /**
   * 跳转到编辑审批流界面
   * 
   * @param request
   * @param response
   * @param procsID
   * @return
   * @throws Exception
   */
  @RequestMapping(value = "/editProcsView/{procsID}", method = RequestMethod.GET)
  @AvoidDuplicateSubmission(needSaveToken = true)
  @UserMenu
  public ModelAndView editProcsView(HttpServletRequest request, HttpServletResponse response,
      @PathVariable Integer procsID) throws Exception {
    ModelAndView mav = new ModelAndView("system/editApvProcs");
    // 记录日志
    SessionBean session = getSessionBean(request);
    log.info("{}跳转到编辑审批组界面", session.getUserName());
    FinanceQueryBean financeQueryBean = new FinanceQueryBean();
    financeQueryBean.setFoStatus("1");
    List<MFinanceOrg> financeList = financeService.selectFinanceList(financeQueryBean);

    TApvFlowProcess afp = flowManageServiceImpl.getApvFlowProcsById(procsID);

    mav.addObject("financeList", financeList);
    mav.addObject("afp", afp);
    // 返回
    return mav;
  }

  /**
   * 根据金融机构Id 查询金融机构产品信息
   * 
   * @param request
   * @param response
   * @param foId
   * @return
   * @throws Exception
   */
  @RequestMapping(value = "/getProductsByFoId", method = RequestMethod.GET)
  @AvoidDuplicateSubmission(needSaveToken = true)
  @ResponseBody
  public Map<String, Object> getProductsByFoId(HttpServletRequest request,
      HttpServletResponse response, Integer foId) throws Exception {
    // 记录日志
    SessionBean session = getSessionBean(request);
    log.info("{}查询审批组是否存在", session.getUserName());
    Map<String, Object> map = new HashMap<>();
    // 查询金融机构产品信息
    ProductQueryCnd pqc = new ProductQueryCnd();
    pqc.setFoId(foId);
    List<MFinanceProductVo> products = productService.selectProductList(pqc);
    List<Integer> fpIds = new ArrayList<>();
    List<String> fpNames = new ArrayList<>();
    for (MFinanceProductVo p : products) {
      if (p != null) {
        fpIds.add(p.getFpId());
        fpNames.add(p.getFpName());
      }
    }
    map.put("fpIds", fpIds);
    map.put("fpNames", fpNames);
    // 返回
    return map;
  }

  /**
   * 审批流编辑保存
   * 
   * @param request
   * @param response
   * @param aftb
   * @param result
   * @return
   * @throws Exception
   */
  @RequestMapping(value = "/editProcsSave", method = RequestMethod.POST)
  @AvoidDuplicateSubmission(needRemoveToken = true)
  @UserMenu
  public ModelAndView editProcsSave(HttpServletRequest request, HttpServletResponse response,
      @Valid @ModelAttribute("afp") TApvFlowProcess afp, BindingResult result) throws Exception {
    ModelAndView mav = new ModelAndView();
    // 验证
    if (result.hasErrors()) {
      mav.setViewName("system/editApvProcs");
      // 需要再次设置token
      request.getSession(false).setAttribute("token",
          TokenProcessor.getInstance().generateToken(request));
      return mav;
    }
    // 记录日志
    SessionBean session = getSessionBean(request);
    // 调用service，得到影响行数
    int lines = flowManageServiceImpl.updateApvProcs(afp);
    // 记录日志
    log.info("{}修改审批组信息保存", session.getUserName(), lines);
    // 返回
    mav.setViewName("redirect:/flowmanage/apvFlowProcslist");
    return mav;
  }

  /**
   * 查询 审批节点信息
   * 
   * @param request
   * @param response
   * @param procsID
   * @return
   * @throws Exception
   */
  @RequestMapping(value = "/apvFlowNodeList/{procsID}", method = RequestMethod.GET)
  @AvoidDuplicateSubmission(needSaveToken = true)
  @UserMenu
  public ModelAndView apvFlowNodeList(HttpServletRequest request, HttpServletResponse response,
      @PathVariable Integer procsID) throws Exception {
    ModelAndView mav = new ModelAndView("system/apvNodelist");
    // 记录日志
    SessionBean session = getSessionBean(request);
    log.info("{}查看审批节点列表", session.getUserName());
    List<TApvFlowNode> afns = flowManageServiceImpl.getApvFlowNodes(procsID);
    mav.addObject("procsID", procsID);
    mav.addObject("afns", afns);
    return mav;
  }

  /**
   * 跳转到添加审批节点界面
   * 
   * @param request
   * @param response
   * @return
   * @throws Exception
   */
  @RequestMapping(value = "/addNodeView/{procsID}", method = RequestMethod.GET)
  @AvoidDuplicateSubmission(needSaveToken = true)
  @UserMenu
  public ModelAndView addNodeView(HttpServletRequest request, HttpServletResponse response,
      @PathVariable Integer procsID) throws Exception {
    ModelAndView mav = new ModelAndView("system/addApvNode");
    SessionBean session = getSessionBean(request);
    log.info("{}跳转到审批节点添加界面", session.getUserName());

    // 查询审批流信息
    TApvFlowProcess afp = flowManageServiceImpl.getApvFlowProcsById(procsID);
    // 查找金融机构信息
    FinanceQueryBean financeQueryBean = new FinanceQueryBean();
    if (afp != null) {
      if (afp.getFoID() != null) {
        MFinanceOrg org = financeService.queryFinanceInfoById(afp.getFoID());
        if (org != null) {
          financeQueryBean.setFoName(org.getFoName());
        }
      }
    }
    financeQueryBean.setFoStatus("1");
    List<MFinanceOrg> financeList = financeService.selectFinanceList(financeQueryBean);

    mav.addObject("afp", afp);
    mav.addObject("financeList", financeList);
    // 返回
    return mav;
  }

  /**
   * 获得运营人员
   * 
   * @param request
   * @param response
   * @return
   * @throws Exception
   */
  @RequestMapping(value = "/getOpsUsers", method = RequestMethod.GET)
  @ResponseBody
  public Map<String, Object> getOpsUsers(HttpServletRequest request, HttpServletResponse response)
      throws Exception {
    SessionBean session = getSessionBean(request);
    log.info("{}获得运营人员", session.getUserName());

    Map<String, Object> map = new HashMap<>();
    // 获取后台运营人员(1,2)
    List<UserRole> userRoles = userService.getUserInfoByRoleId(1);
    List<UserRole> userRoles2 = userService.getUserInfoByRoleId(2);
    userRoles.addAll(userRoles2);

    List<Integer> opsUserIds = new ArrayList<>();
    List<String> opsUserNames = new ArrayList<>();
    for (UserRole user : userRoles) {
      opsUserIds.add(user.getUserId());
      opsUserNames.add(user.getUserName());
    }

    map.put("opsUserIds", opsUserIds);
    map.put("opsUserNames", opsUserNames);
    // 返回
    return map;
  }

  /**
   * 审批节点添加 保存
   * 
   * @param request
   * @param response
   * @param afp
   * @param result
   * @return
   * @throws Exception
   */
  @RequestMapping(value = "/addNodeSave", method = RequestMethod.POST)
  @AvoidDuplicateSubmission(needRemoveToken = true)
  @UserMenu
  public ModelAndView addNodeSave(HttpServletRequest request, HttpServletResponse response,
      @Valid @ModelAttribute("afn") TApvFlowNode afn, BindingResult result) throws Exception {
    ModelAndView mav = new ModelAndView();
    // 验证
    if (result.hasErrors()) {
      mav.setViewName("system/addApvNode");
      // 需要再次设置token
      request.getSession(false).setAttribute("token",
          TokenProcessor.getInstance().generateToken(request));
      return mav;
    }

    if (afn.getTeamID() != null) {
      TApvFlowTeam team = flowManageServiceImpl.getTeamByTeamname("FO" + afn.getTeamID());
      if (team != null) {
        afn.setTeamID(team.getFtId());
      } else {
        afn.setTeamID(null);
      }
    }

    List<TApvFlowNode> afns = flowManageServiceImpl.getApvFlowNodes(afn.getProcsID());
    if (afns.size() > 0) {
      TApvFlowNode node = afns.get(afns.size() - 1);
      afn.setParentID(node.getNodeID());
    } else {
      afn.setParentID(0);
    }
    // 记录日志
    SessionBean session = getSessionBean(request);
    // 调用service，得到影响行数
    int lines = flowManageServiceImpl.addApvNode(afn);
    // 记录日志
    log.info("{}修改审批节点信息保存", session.getUserName(), lines);
    // 返回
    String url = "redirect:/flowmanage/apvFlowNodeList/" + afn.getProcsID();
    mav.setViewName(url);
    return mav;
  }

  /**
   * 跳转到编辑审批节点界面
   * 
   * @param request
   * @param response
   * @param procsID
   * @return
   * @throws Exception
   */
  @RequestMapping(value = "/editNodeView/{nodeID}", method = RequestMethod.GET)
  @AvoidDuplicateSubmission(needSaveToken = true)
  @UserMenu
  public ModelAndView editNodeView(HttpServletRequest request, HttpServletResponse response,
      @PathVariable Integer nodeID) throws Exception {
    ModelAndView mav = new ModelAndView("system/editApvNode");
    // 记录日志
    SessionBean session = getSessionBean(request);
    log.info("{}跳转到编辑审批节点界面", session.getUserName());
    // 查找条件封装
    FinanceQueryBean financeQueryBean = new FinanceQueryBean();

    // 查询节点数据
    TApvFlowNode afn = flowManageServiceImpl.getApvFlowNodeById(nodeID);
    if (afn.getProcsID() != null) {
      TApvFlowProcess afp = flowManageServiceImpl.getApvFlowProcsById(afn.getProcsID());
      MFinanceOrg org = financeService.queryFinanceInfoById(afp.getFoID());
      if (org != null) {
        financeQueryBean.setFoName(org.getFoName());
      }
    }
    financeQueryBean.setFoStatus("1");
    List<MFinanceOrg> financeList = financeService.selectFinanceList(financeQueryBean);

    mav.addObject("financeList", financeList);
    mav.addObject("afn", afn);
    // 返回
    return mav;
  }

  /**
   * 审批流编辑保存
   * 
   * @param request
   * @param response
   * @param aftb
   * @param result
   * @return
   * @throws Exception
   */
  @RequestMapping(value = "/editNodeSave", method = RequestMethod.POST)
  @AvoidDuplicateSubmission(needRemoveToken = true)
  @UserMenu
  public ModelAndView editNodeSave(HttpServletRequest request, HttpServletResponse response,
      @Valid @ModelAttribute("afn") TApvFlowNode afn, BindingResult result) throws Exception {
    ModelAndView mav = new ModelAndView();
    // 验证
    if (result.hasErrors()) {
      mav.setViewName("system/editApvNode");
      // 需要再次设置token
      request.getSession(false).setAttribute("token",
          TokenProcessor.getInstance().generateToken(request));
      return mav;
    }
    // 记录日志
    SessionBean session = getSessionBean(request);
    // 调用service，得到影响行数
    int lines = flowManageServiceImpl.updateApvNode(afn);
    // 记录日志
    log.info("{}编辑审批节点信息保存", session.getUserName(), lines);
    // 返回
    String url = "redirect:/flowmanage/apvFlowNodeList/" + afn.getProcsID();
    mav.setViewName(url);
    return mav;
  }


}
