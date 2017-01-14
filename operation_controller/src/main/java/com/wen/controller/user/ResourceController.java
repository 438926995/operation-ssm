package com.wen.controller.user;

import com.wen.annontation.InjectResource;
import com.wen.annotation.controller.UserMenu;
import com.wen.annotation.form.AvoidDuplicateSubmission;
import com.wen.bean.SessionBean;
import com.wen.bean.user.ResourceAddBean;
import com.wen.controller.BaseController;
import com.wen.domain.user.Resource;
import com.wen.domain.user.ResourceQueryBean;
import com.wen.service.user.IResourceService;
import com.wen.util.CommonUtil;
import com.wen.util.TokenProcessor;
import com.wen.util.pager.TbData;
import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

/**
 * 资源相关信息管理
 * 
 * @author huwenwen
 */
@Controller
@RequestMapping(value = "/resource")
public class ResourceController extends BaseController {

  /**
   * log
   */
  private static Logger log = LoggerFactory.getLogger(ResourceController.class);

  @Inject
  private IResourceService resourceService;

  @RequestMapping(value = "/list", method = RequestMethod.GET)
  @InjectResource(name = "菜单列表", url = "resource/list", grade = 2, parentName = "用户管理", parentOtherProps = {"PRJ_TYPE:0"},
      customProps = {"RESOURCE_TYPE:URL", "SHOW_NAV:1", "RESOURCE_DESC:菜单列表"})
  @UserMenu
  public ModelAndView list(HttpServletRequest request, ResourceQueryBean rqb, Integer currentPage)
      throws Exception {
    // 记录日志
    SessionBean session = getSessionBean(request);
    log.info("{}查看资源列表", session.getUserName());
    // 查询数据并返回
    ModelAndView mav = new ModelAndView("resource/list");
    TbData tbData = resourceService.queryResourceTbData(currentPage, rqb);
    tbData = tbData.fillTbData("resource/list", rqb, null);
    mav.addObject("tbData", tbData);
    mav.addObject("rqb", rqb);
    return mav;
  }

  /**
   * 资源添加页面跳转
   * 
   * @param request
   * @param response
   * @return
   * @throws Exception
   */
  @RequestMapping(value = "/add", method = RequestMethod.GET)
  @AvoidDuplicateSubmission(needSaveToken = true)
  @UserMenu
  @InjectResource(name = "资源添加", url = "resource/add", grade = 3, parentName = "菜单列表", parentOtherProps = {"PRJ_TYPE:0"},
      customProps = {"RESOURCE_TYPE:URL", "SHOW_NAV:0", "RESOURCE_DESC:资源添加"})
  public ModelAndView add(HttpServletRequest request) throws Exception {
    ModelAndView mav = new ModelAndView("resource/add");
    // 记录日志
    SessionBean session = getSessionBean(request);
    log.info("{}跳转到添加资源界面", session.getUserName());

    // 获取一级菜单列表，用于页面初始化
    mav.addObject("firstMenus", resourceService.queryResourceListByParentId(0));

    ResourceAddBean rab = new ResourceAddBean();
    mav.addObject("rab", rab);
    // 返回
    return mav;
  }

  /**
   * 资源组编辑页面跳转
   * 
   * @param request
   * @param response
   * @return
   * @throws Exception
   */
  @RequestMapping(value = "/edit/{resourceId}", method = RequestMethod.GET)
  @AvoidDuplicateSubmission(needSaveToken = true)
  @UserMenu
  @InjectResource(name = "资源编辑", url = "resource/edit", grade = 3, parentName = "菜单列表", parentOtherProps = {"PRJ_TYPE:0"},
      customProps = {"RESOURCE_TYPE:URL", "SHOW_NAV:0", "RESOURCE_DESC:资源编辑"})
  public ModelAndView edit(HttpServletRequest request, @PathVariable Integer resourceId)
      throws Exception {
    ModelAndView mav = new ModelAndView("resource/add");
    // 记录日志
    SessionBean session = getSessionBean(request);
    log.info("{}跳转到添加资源组界面", session.getUserName());

    Resource rab = resourceService.queryResourceById(resourceId);
    mav.addObject("rab", rab);

    // 获取父级菜单信息，用于页面初始化
    mav.addObject("firstMenus", resourceService.queryResourceListByParentId(0));
    if (rab.getParentIds().length > 1) {
      mav.addObject("secondMenus",
          resourceService.queryResourceListByParentId(rab.getParentIds()[0]));
    }

    // 返回
    return mav;
  }

  /**
   * 根据parentId获取其子菜单
   * 
   * @param pid
   * @return
   * @throws Exception
   */
  @RequestMapping(value = "/children/{parentId}", method = RequestMethod.GET,
      produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  @InjectResource(name = "根据parentId获取其子菜单", url = "resource/children", grade = 3, parentName = "菜单列表", parentOtherProps = {"PRJ_TYPE:0"},
      customProps = {"RESOURCE_TYPE:URL", "SHOW_NAV:0"})
  public List<Resource> children(@PathVariable Integer parentId) throws Exception {
    return resourceService.queryResourceListByParentId(parentId);
  }

  /**
   * 保存一条资源信息
   * 
   * @param request
   * @param response
   * @param rab
   * @param result
   * @return
   * @throws Exception
   */
  @RequestMapping(value = "/save", method = RequestMethod.POST)
  @AvoidDuplicateSubmission(needRemoveToken = true)
  @UserMenu
  @InjectResource(name = "保存资源", url = "resource/save", grade = 3, parentName = "菜单列表", parentOtherProps = {"PRJ_TYPE:0"},
      customProps = {"RESOURCE_TYPE:URL", "SHOW_NAV:0"})
  public ModelAndView save(HttpServletRequest request,
      @Valid @ModelAttribute("rab") ResourceAddBean rab, BindingResult result) throws Exception {
    ModelAndView mav = new ModelAndView();
    // 验证
    if (result.hasErrors()) {
      mav.setViewName("resource/add");
      // 需要再次设置token
      request.getSession(false).setAttribute("token",
          TokenProcessor.getInstance().generateToken(request));

      // 获取父级菜单信息，用于页面初始化
      mav.addObject("firstMenus", resourceService.queryResourceListByParentId(0));
      if (rab.getParentIds().length > 1 && rab.getParentIds()[0] != null) {
        mav.addObject("secondMenus",
            resourceService.queryResourceListByParentId(rab.getParentIds()[0]));
      }
      return mav;
    }
    // 记录日志
    SessionBean sb = getSessionBean(request);
    log.info("{}保存资源信息[{}]", sb.getUserName(), rab.getResourceName());

    Resource resource = new Resource();
    BeanUtils.copyProperties(resource, rab);
    resource.setCreateUser(sb.getUserId().intValue());
    // 调用service，得到影响行数
    int lines = 0;
    try {
      lines = resourceService.saveResource(resource);
    } catch (Exception e) {
      log.error(CommonUtil.getErrorMessage(e));
    }
    // 记录日志
    log.info("{}保存资源信息[{}:{}]，影响行数{}", sb.getUserName(), resource.getResourceId(),
        resource.getResourceName(), lines);
    // 返回
    mav.setViewName("redirect:/resource/list");
    return mav;
  }

}
