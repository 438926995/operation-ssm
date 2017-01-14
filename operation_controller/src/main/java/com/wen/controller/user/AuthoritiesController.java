package com.wen.controller.user;

import com.wen.annontation.InjectResource;
import com.wen.annotation.controller.UserMenu;
import com.wen.annotation.form.AvoidDuplicateSubmission;
import com.wen.bean.JSONMessage;
import com.wen.bean.SessionBean;
import com.wen.bean.user.AuthModuleNameBean;
import com.wen.bean.user.AuthNameBean;
import com.wen.bean.user.AuthoritiesAddBean;
import com.wen.bean.user.AuthoritiesModuleAddBean;
import com.wen.controller.BaseController;
import com.wen.domain.user.Authorities;
import com.wen.domain.user.AuthoritiesModule;
import com.wen.domain.user.AuthoritiesQueryBean;
import com.wen.domain.user.Resource;
import com.wen.security.CustomSecurityMetadataSource;
import com.wen.service.user.IAuthoritiesService;
import com.wen.service.user.IResourceService;
import com.wen.util.TokenProcessor;
import com.wen.util.pager.TbData;
import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;
/**
 * 权限相关信息管理
 * 
 * @author huwenwen
 */
@Controller
@RequestMapping(value = "/authorities")
public class AuthoritiesController extends BaseController {

  /**
   * log
   */
  private static Logger log = LoggerFactory.getLogger(AuthoritiesController.class);

  @Inject
  private IAuthoritiesService authoritiesService;
  @Inject
  private IResourceService resourceService;
  @Inject
  private CustomSecurityMetadataSource dataSource;


  @RequestMapping(value = "/list", method = RequestMethod.GET)
  @UserMenu
  @InjectResource(name = "权限列表", url = "authorities/list", grade = 2, parentName = "用户管理", parentOtherProps = {"PRJ_TYPE:0"},
      customProps = {"RESOURCE_TYPE:URL", "SHOW_NAV:1"})
  public ModelAndView list(HttpServletRequest request, AuthoritiesQueryBean aqb,
      Integer currentPage) throws Exception {
    // 记录日志
    SessionBean session = getSessionBean(request);
    log.info("{}查看权限列表", session.getUserName());
    // 查询数据并返回
    ModelAndView mav = new ModelAndView("authorities/list");
    TbData tbData = authoritiesService.queryAuthoritiesTbData(currentPage, aqb);
    tbData = tbData.fillTbData("authorities/list", aqb, null);
    mav.addObject("tbData", tbData);
    mav.addObject("aqb", aqb);
    return mav;
  }

  /**
   * 权限添加页面跳转
   * 
   * @param request
   * @param response
   * @return
   * @throws Exception
   */
  @RequestMapping(value = "/add", method = RequestMethod.GET)
  @AvoidDuplicateSubmission(needSaveToken = true)
  @UserMenu
  @InjectResource(name = "添加权限", url = "authorities/add", grade = 2, parentName = "用户管理", parentOtherProps = {"PRJ_TYPE:0"},
      customProps = {"RESOURCE_TYPE:URL", "SHOW_NAV:1"})
  public ModelAndView add(HttpServletRequest request) throws Exception {
    ModelAndView mav = new ModelAndView("authorities/add");
    // 记录日志
    SessionBean session = getSessionBean(request);
    log.info("{}跳转到添加权限界面", session.getUserName());

    // 获取所有权限模块信息，用于页面初始化
    mav.addObject("authoritiesModuleList", authoritiesService.queryAuthoritiesModuleListAll());
    // 获取所有资源信息，用于页面初始化
    mav.addObject("menuTree", resourceService.queryMenuTree());

    AuthoritiesAddBean aab = new AuthoritiesAddBean();
    mav.addObject("aab", aab);
    // 返回
    return mav;
  }

  /**
   * 权限模块编辑页面跳转
   * 
   * @param request
   * @param response
   * @return
   * @throws Exception
   */
  @RequestMapping(value = "/edit/{authoritiesId}", method = RequestMethod.GET)
  @AvoidDuplicateSubmission(needSaveToken = true)
  @UserMenu
  @InjectResource(name = "编辑权限", url = "authorities/edit", grade = 3, parentName = "权限列表", parentOtherProps = {"PRJ_TYPE:0"},
      customProps = {"RESOURCE_TYPE:URL", "SHOW_NAV:0"})
  public ModelAndView edit(HttpServletRequest request, @PathVariable Integer authoritiesId)
      throws Exception {
    ModelAndView mav = new ModelAndView("authorities/add");
    // 记录日志
    SessionBean session = getSessionBean(request);
    log.info("{}跳转到添加权限模块界面", session.getUserName());

    Authorities aab = authoritiesService.queryAuthoritiesById(authoritiesId);
    mav.addObject("aab", aab);

    // 获取所有权限模块信息，用于页面初始化
    mav.addObject("authoritiesModuleList", authoritiesService.queryAuthoritiesModuleListAll());
    // 获取所有资源信息，用于页面初始化
    mav.addObject("menuTree", resourceService.queryMenuTree());

    // 返回
    return mav;
  }

  /**
   * 保存一条权限信息
   * 
   * @param request
   * @param response
   * @param aab
   * @param result
   * @return
   * @throws Exception
   */
  @RequestMapping(value = "/save", method = RequestMethod.POST)
  @AvoidDuplicateSubmission(needRemoveToken = true)
  @UserMenu
  @InjectResource(name = "保存权限信息", url = "authorities/save", grade = 3, parentName = "权限列表", parentOtherProps = {"PRJ_TYPE:0"},
      customProps = {"RESOURCE_TYPE:URL", "SHOW_NAV:0"})
  public ModelAndView save(HttpServletRequest request,
      @Valid @ModelAttribute("aab") AuthoritiesAddBean aab, BindingResult result) throws Exception {
    ModelAndView mav = new ModelAndView();

    // 将接受到的resourcesIdsStr转换成resourcesIds
    aab.convertResourcesIdsStr2ResourcesIds();

    // 验证
    if (result.hasErrors()) {
      mav.setViewName("authorities/add");
      // 需要再次设置token
      request.getSession(false).setAttribute("token",
          TokenProcessor.getInstance().generateToken(request));

      // 获取所有权限模块信息，用于页面初始化
      mav.addObject("authoritiesModuleList", authoritiesService.queryAuthoritiesModuleListAll());
      // 获取所有资源信息，用于页面初始化
      mav.addObject("menuTree", resourceService.queryMenuTree());
      return mav;
    }
    // 记录日志
    SessionBean sb = getSessionBean(request);
    log.info("{}保存权限信息[{}]", sb.getUserName(), aab.getAuthName());

    Authorities authorities = new Authorities();
    BeanUtils.copyProperties(authorities, aab);
    // 调用service，得到影响行数
    int lines = authoritiesService.saveAuthorities(authorities);
    // 新加资源后重新加载
    if (lines > 0) {
      dataSource.loadResourceDefine();
    }
    // 记录日志
    log.info("{}保存权限信息[{}:{}]，影响行数{}", sb.getUserName(), authorities.getAuthoritiesId(),
        authorities.getAuthName(), lines);
    // 返回
    mav.setViewName("redirect:/authorities/list");
    return mav;
  }


  /**
   * 权限模块列表
   * 
   * @param request
   * @param aqb
   * @param currentPage
   * @return
   * @throws Exception
   */
  @RequestMapping(value = "/module/list", method = RequestMethod.GET)
  @UserMenu
  @InjectResource(name = "权限模块列表", url = "authorities/module/list", grade = 2, parentName = "用户管理", parentOtherProps = {"PRJ_TYPE:0"},
      customProps = {"RESOURCE_TYPE:URL", "SHOW_NAV:1"})
  public ModelAndView moduleList(HttpServletRequest request, AuthoritiesQueryBean aqb,
      Integer currentPage) throws Exception {
    // 记录日志
    SessionBean session = getSessionBean(request);
    log.info("{}查看权限模块列表", session.getUserName());
    // 查询数据并返回
    ModelAndView mav = new ModelAndView("authorities/moduleList");
    TbData tbData = authoritiesService.queryAuthoritiesModuleTbData(currentPage, aqb);
    tbData = tbData.fillTbData("authorities/module/list", aqb, null);
    mav.addObject("tbData", tbData);
    mav.addObject("aqb", aqb);
    return mav;
  }

  /**
   * 权限模块添加页面跳转
   * 
   * @param request
   * @param response
   * @return
   * @throws Exception
   */
  @RequestMapping(value = "/module/add", method = RequestMethod.GET)
  @AvoidDuplicateSubmission(needSaveToken = true)
  @UserMenu
  @InjectResource(name = "权限模块添加", url = "authorities/module/add", grade = 2, parentName = "用户管理", parentOtherProps = {"PRJ_TYPE:0"},
      customProps = {"RESOURCE_TYPE:URL", "SHOW_NAV:1"})
  public ModelAndView addModule(HttpServletRequest request) throws Exception {
    ModelAndView mav = new ModelAndView("authorities/moduleAdd");
    // 记录日志
    SessionBean session = getSessionBean(request);
    log.info("{}跳转到添加权限模块界面", session.getUserName());
    // 返回
    return mav;
  }

  /**
   * 权限模块编辑页面跳转
   * 
   * @param request
   * @param response
   * @return
   * @throws Exception
   */
  @RequestMapping(value = "/module/edit/{authModuleId}", method = RequestMethod.GET)
  @AvoidDuplicateSubmission(needSaveToken = true)
  @UserMenu
  @InjectResource(name = "权限模块编辑", url = "authorities/module/edit", grade = 3, parentName = "权限模块列表", parentOtherProps = {"PRJ_TYPE:0"},
      customProps = {"RESOURCE_TYPE:URL", "SHOW_NAV:0"})
  public ModelAndView editModule(HttpServletRequest request, @PathVariable Integer authModuleId)
      throws Exception {
    ModelAndView mav = new ModelAndView("authorities/moduleAdd");
    // 记录日志
    SessionBean session = getSessionBean(request);
    log.info("{}跳转到添加权限模块界面", session.getUserName());

    AuthoritiesModule amab = authoritiesService.queryAuthoritiesModuleById(authModuleId);
    mav.addObject("amab", amab);

    // 返回
    return mav;
  }

  /**
   * 保存一条权限模块信息
   * 
   * @param request
   * @param response
   * @param amab
   * @param result
   * @return
   * @throws Exception
   */
  @RequestMapping(value = "/module/save", method = RequestMethod.POST)
  @AvoidDuplicateSubmission(needRemoveToken = true)
  @UserMenu
  @InjectResource(name = "权限模块保存", url = "authorities/module/save", grade = 3, parentName = "权限模块列表", parentOtherProps = {"PRJ_TYPE:0"},
      customProps = {"RESOURCE_TYPE:URL", "SHOW_NAV:0"})
  public ModelAndView saveModule(HttpServletRequest request,
      @Valid @ModelAttribute("amab") AuthoritiesModuleAddBean amab, BindingResult result)
          throws Exception {
    ModelAndView mav = new ModelAndView();
    // 验证
    if (result.hasErrors()) {
      mav.setViewName("authorities/moduleAdd");
      // 需要再次设置token
      request.getSession(false).setAttribute("token",
          TokenProcessor.getInstance().generateToken(request));
      return mav;
    }
    // 记录日志
    SessionBean session = getSessionBean(request);
    log.info("{}保存权限模块信息[{}]", session.getUserName(), amab.getAuthModuleName());

    AuthoritiesModule authoritiesModule = new AuthoritiesModule();
    BeanUtils.copyProperties(authoritiesModule, amab);
    // 调用service，得到影响行数
    int lines = authoritiesService.saveAuthoritiesModule(authoritiesModule);
    // 记录日志
    log.info("{}保存权限模块信息[{}:{}]，影响行数{}", session.getUserName(), authoritiesModule.getAuthModuleId(),
        authoritiesModule.getAuthModuleName(), lines);
    // 返回
    mav.setViewName("redirect:/authorities/module/list");
    return mav;
  }

  /**
   * 验证权限名称是否唯一
   * 
   * @param request
   * @param response
   * @param rnb
   * @return
   * @throws Exception
   */
  @RequestMapping(value = "/judgeNameSingle", method = RequestMethod.POST)
  @ResponseBody
  @InjectResource(name = "验证权限名称是否唯一", url = "authorities/judgeNameSingle", grade = 3, parentName = "添加权限", parentOtherProps = {"PRJ_TYPE:0"},
      customProps = {"RESOURCE_TYPE:URL", "SHOW_NAV:0"})
  public JSONMessage judgeNameSingle(HttpServletRequest request, HttpServletResponse response,
      @RequestBody AuthNameBean anb) throws Exception {
    SessionBean session = getSessionBean(request);
    if (anb.getAuthId() == null) {
      boolean flag = authoritiesService.judgeIfAuthNameSingle(anb.getAuthName());
      log.info("{}添加权限，判断{}是否唯一,结果{}", session.getUserName(), anb.getAuthName(), flag);
      if (flag) {
        return new JSONMessage(flag, "权限名唯一");
      } else {
        return new JSONMessage(flag, "该权限名已存在");
      }
    }
    return new JSONMessage(true);
  }

  /**
   * 验证权限模块名称是否唯一
   * 
   * @param request
   * @param response
   * @param rnb
   * @return
   * @throws Exception
   */
  @RequestMapping(value = "/module/judgeNameSingle", method = RequestMethod.POST)
  @ResponseBody
  @InjectResource(name = "验证权限模块名称是否唯一", url = "authorities/module/judgeNameSingle", grade = 3, parentName = "权限模块添加", parentOtherProps = {"PRJ_TYPE:0"},
      customProps = {"RESOURCE_TYPE:URL", "SHOW_NAV:0"})
  public JSONMessage judgeNameSingle2(HttpServletRequest request, HttpServletResponse response,
      @RequestBody AuthModuleNameBean anb) throws Exception {
    SessionBean session = getSessionBean(request);
    boolean flag = authoritiesService.judgeIfAuthNameSingle2(anb.getAuthModuleName());
    log.info("{}添加权限模块，判断{}是否唯一,结果{}", session.getUserName(), anb.getAuthModuleName(), flag);
    if (flag) {
      return new JSONMessage(flag, "权限模块名唯一");
    } else {
      return new JSONMessage(flag, "该权限模块名已存在");
    }
  }

  /**
   * 判断url是否唯一
   * 
   * @param request
   * @param response
   * @param resourceId
   * @return
   */
  @RequestMapping(value = "/judgeUrlSingle", method = RequestMethod.GET)
  @ResponseBody
  @InjectResource(name = "判断url是否唯一", url = "authorities/judgeUrlSingle", grade = 3, parentName = "添加权限", parentOtherProps = {"PRJ_TYPE:0"},
      customProps = {"RESOURCE_TYPE:URL", "SHOW_NAV:0"})
  public JSONMessage judgeUrlSingle(HttpServletRequest request, HttpServletResponse response,
      Integer resourceId) {
    SessionBean session = getSessionBean(request);
    log.info("{}选择资源，判断URL是否唯一", session.getUserName());
    List<Resource> resourceList = authoritiesService.judgeUrlSingle(resourceId);
    // 得到的结果不大于一个，唯一
    if (resourceList.size() <= 1) {
      return null;
    }
    StringBuilder resourceIdStr = new StringBuilder();
    for (int i = 0; i < resourceList.size(); i++) {
      if (!resourceId.equals(resourceList.get(i).getResourceId())) {
        resourceIdStr.append(resourceList.get(i).getResourceId());
        if (i != resourceList.size() - 1) {
          resourceIdStr.append(",");
        }
      }
    }
    JSONMessage jsonMessage = new JSONMessage();
    jsonMessage.setDatas(resourceIdStr);
    return jsonMessage;
  }
}
