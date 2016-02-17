package com.eleme.controller.user;

import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.http.MediaType;
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
import com.eleme.bean.user.ResourceAddBean;
import com.eleme.controller.BaseController;
import com.eleme.domain.ops.user.Resource;
import com.eleme.domain.ops.user.ResourceQueryBean;
import com.eleme.service.user.IResourceService;
import com.eleme.util.CommonUtil;
import com.eleme.util.TokenProcessor;
import com.eleme.util.pager.TbData;

import me.ele.elog.Log;
import me.ele.elog.LogFactory;

/**
 * 资源相关信息管理
 * 
 * @author zhangqiongbiao
 */
@Controller
@RequestMapping(value = "/resource")
public class ResourceController extends BaseController {

  /**
   * log
   */
  private static Log log = LogFactory.getLog(ResourceController.class);

  @Inject
  private IResourceService resourceService;

  @RequestMapping(value = "/list", method = RequestMethod.GET)
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
