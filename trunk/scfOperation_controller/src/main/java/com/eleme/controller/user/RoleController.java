package com.eleme.controller.user;

import java.util.HashMap;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.eleme.annotation.controller.UserMenu;
import com.eleme.annotation.form.AvoidDuplicateSubmission;
import com.eleme.bean.JSONMessage;
import com.eleme.bean.SessionBean;
import com.eleme.bean.user.RoleAddBean;
import com.eleme.bean.user.RoleBean;
import com.eleme.bean.user.RoleNameBean;
import com.eleme.controller.BaseController;
import com.eleme.domain.ops.UserRole;
import com.eleme.domain.ops.user.AuthoritieBean;
import com.eleme.service.user.IAuthoritiesService;
import com.eleme.service.user.IRoleService;
import com.eleme.service.user.IUserService;
import com.eleme.util.TokenProcessor;
import com.eleme.util.pager.TbData;

import me.ele.elog.Log;
import me.ele.elog.LogFactory;

/**
 * 角色管理
 * 
 * @author zhangqiongbiao
 */
@Controller
@RequestMapping(value = "/role")
public class RoleController extends BaseController {

  /**
   * log
   */
  private static Log log = LogFactory.getLog(RoleController.class);

  @Inject
  private IRoleService roleService;
  
  @Inject
  private IUserService userService;

  @Inject
  private IAuthoritiesService authService;

  /**
   * 角色列表
   * @throws Exception 
   */
  @RequestMapping(value = "/list")
  @UserMenu
  public ModelAndView list(HttpServletRequest request, HttpServletResponse response, Integer currentPage) throws Exception {
    ModelAndView mav = new ModelAndView("/role/list");
    // 记录日志
    SessionBean session = getSessionBean(request);
    log.info("{}跳转到角色列表界面", session.getUserName());
    TbData tbData = roleService.rolesPageList(currentPage);
    tbData.fillTbData("role/list", null, null);
    mav.addObject("tbData", tbData);
    return mav;
  }

  /**
   * 角色添加
   */
  @RequestMapping(value = "/addView", method = RequestMethod.GET)
  @AvoidDuplicateSubmission(needSaveToken = true)
  @UserMenu
  public ModelAndView addView(HttpServletRequest request, HttpServletResponse response) {
    ModelAndView mav = new ModelAndView("role/add");
    // 记录日志
    SessionBean session = getSessionBean(request);
    log.info("{}跳转到角色添加界面", session.getUserName());
    // 获得权限的集合
    List<AuthoritieBean> authList = authService.authList();
    mav.addObject("authList", authList);
    return mav;
  }

  /**
   * 添加角色保存
   * 
   * @throws Exception
   */
  @RequestMapping(value = "/addSave", method = RequestMethod.POST)
  @AvoidDuplicateSubmission(needRemoveToken = true)
  @UserMenu
  public ModelAndView addSave(HttpServletRequest request, HttpServletResponse response,
      @Valid @ModelAttribute("rab") RoleAddBean rab, BindingResult result) throws Exception {
    ModelAndView mav = new ModelAndView();
    // 验证
    if (result.hasErrors()) {
      mav.setViewName("role/add");
      // 获得权限的集合
      List<AuthoritieBean> authList = authService.authList();
      mav.addObject("authList", authList);
      // 需要再次设置token
      request.getSession(false).setAttribute("token",
          TokenProcessor.getInstance().generateToken(request));
      return mav;
    }
    // 记录日志
    SessionBean session = getSessionBean(request);
    log.info("{}添加角色信息", session.getUserName());
    // clone RoleAddBean 数据到 RoleBean,field名称需相同
    RoleBean ur = new RoleBean();
    // 将authStr 转成 auth数据
    rab.setAuthList(rab.getAuthStr().split(","));
    BeanUtils.copyProperties(ur, rab); 
    int lines = roleService.insertRole(ur);
    // 记录日志
    log.info("{}添加角色信息，影响行数{}", session.getUserName(), lines);
    mav.setViewName("redirect:/role/list");
    return mav;
  }

  /**
   * 异步返回角色名是否唯一.
   * 
   * @param request
   * @param response
   * @param rnb 前台传来的json字符串，例如js：JSON.stringify(data).（如果是json对象，会报错）
   * @return JSONMessage封装类
   * @throws Exception
   */
  @RequestMapping(value = "/judgeNameSingle", method = RequestMethod.POST)
  @ResponseBody
  public JSONMessage judgeNameSingle(HttpServletRequest request, HttpServletResponse response,
      @RequestBody RoleNameBean rnb) throws Exception {
    SessionBean session = getSessionBean(request);
    boolean flag = roleService.judgeIfRoleNameSingle(rnb.getRoleName());
    log.info("{}添加角色，判断{}是否唯一,结果{}", session.getUserName(), rnb.getRoleName(), flag);
    if (flag) {
      return new JSONMessage(flag, "角色名唯一");
    } else {
      return new JSONMessage(flag, "该角色名已存在");
    }
  }

  /**
   * 编辑角色信息
   */
  @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
  @AvoidDuplicateSubmission(needSaveToken = true)
  @UserMenu
  public ModelAndView editView(HttpServletRequest request, HttpServletResponse response,
      @PathVariable Integer id) throws Exception {
    ModelAndView mav = new ModelAndView("/role/edit");
    // 记录日志
    SessionBean session = getSessionBean(request);
    log.info("{}跳转到角色编辑界面", session.getUserName());
    List<RoleBean> roleInfoList = roleService.getRoleInfoById(id);
    // 得到权限集合
    List<AuthoritieBean> authList = authService.authList();
    // 判断该角色现有的权限
    for (int i = 0; i < authList.size(); i++) {
      for (int j = 0; j < roleInfoList.size(); j++) {
        if (roleInfoList.get(j) == null) {
          continue;
        } else if (authList.get(i).getAuthId().equals(roleInfoList.get(j).getAuthId())) {
          authList.get(i).setChecked(true);
          break;
        }
      }
    }
    // 角色信息
    RoleBean roleInfo = roleInfoList.get(0);
    // 返回角色信息
    mav.addObject("roleInfo", roleInfo);
    // 返回权限集合
    mav.addObject("authList", authList);
    return mav;
  }

  /**
   * 编辑信息保存
   * 
   * @throws Exception
   */
  @RequestMapping(value = "/editSave", method = RequestMethod.POST)
  @AvoidDuplicateSubmission(needRemoveToken = true)
  @UserMenu
  public ModelAndView editSave(HttpServletRequest request, HttpServletResponse response,
      @Valid @ModelAttribute("rab") RoleAddBean rab, BindingResult result) throws Exception {
    ModelAndView mav = new ModelAndView();
    // 验证
    if (result.hasErrors()) {
      editSaveError(request, rab, mav);
      return mav;
    }
    // 业务验证 角色名是否唯一(不包括自己本身)
    String roleName = roleService.getRoleInfoById(rab.getRoleId()).get(0).getRoleName();
    if (!rab.getRoleName().equals(roleName)) {
      if (!roleService.judgeIfRoleNameSingle(rab.getRoleName())) {
        result.addError(new FieldError("rab", "roleName", "角色名已存在"));
        // 验证
        if (result.hasErrors()) {
          editSaveError(request, rab, mav);
          return mav;
        }
      }
    }
    // 记录日志
    SessionBean session = getSessionBean(request);
    log.info("{}添加角色信息", session.getUserName());
    // clone RoleAddBean 数据到 RoleBean,field名称需相同
    RoleBean ur = new RoleBean();
    // 将authStr 装换成 auth数组
    rab.setAuthList(rab.getAuthStr().split(","));
    BeanUtils.copyProperties(ur, rab);  
    int lines = roleService.updateRole(ur);
    // 记录日志
    log.info("{}更行角色信息，影响行数{}", session.getUserName(), lines);
    // 返回
    mav.setViewName("redirect:/role/list");
    return mav;
  }
/**
 * 编译保存出错 封装mav.addObject()
 * @param request
 * @param rab
 * @param mav
 */
  private void editSaveError(HttpServletRequest request, RoleAddBean rab, ModelAndView mav) {
    mav.setViewName("role/edit");
    // 得到权限集合
    List<AuthoritieBean> authList = authService.authList();
    // 判断该角色现有的权限
    if (rab.getAuthList() != null) {
      for (int i = 0; i < authList.size(); i++) {
        for (int j = 0; j < rab.getAuthList().length; j++) {
          if (authList.get(i).getAuthId().equals(Integer.parseInt(rab.getAuthList()[j]))) {
            authList.get(i).setChecked(true);
            break;
          }
        }
      }
    }
    // 返回角色信息
    mav.addObject("roleInfo", rab);
    // 返回权限集合
    mav.addObject("authList", authList);
    // 需要再次设置token
    request.getSession(false).setAttribute("token",
        TokenProcessor.getInstance().generateToken(request));
  }

  /**
   * 获得角色信息
   */
  @RequestMapping(value = "/getRoleInfo", method = RequestMethod.GET,
      produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  public Map<String, Object> getRoleInfo(HttpServletRequest request, HttpServletResponse response,
      Integer roleId) throws Exception {
    // 记录日志
    SessionBean session = getSessionBean(request);
    log.info("{}根据角色ID{}获取的信息", session.getUserName(), roleId);
    // 查询 角色信息
    List<RoleBean> roleInfoById = roleService.getRoleInfoById(roleId);
    // 查询 该角色对应的用户
    List<UserRole> userInfoByRoleId = userService.getUserInfoByRoleId(roleId);
    // UserRole userInfo = userService.getUserInfoById(rol);
    // 将得到的信息封装成Map集合
    Map<String, Object> map = new HashMap<String, Object>();
    map.put("roleInfo", roleInfoById.get(0));
    // 将权限名称封装
    StringBuilder auth = new StringBuilder();
    for (RoleBean role : roleInfoById) {
      if (role.getAuthName() == null) {
        auth.append(", ");
      } else {
        auth.append(role.getAuthName() + ", ");
      }
    }
    auth.delete(auth.length() - 2, auth.length());
    map.put("auth", auth);
    map.put("user", userInfoByRoleId);
    return map;
  }

  /**
   * 删除角色
   */
  @RequestMapping(value = "/del{id}", method = RequestMethod.GET,
      produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  public JSONMessage del(HttpServletRequest request, HttpServletResponse response, Integer roleId) {
    // 记录日志
    SessionBean session = getSessionBean(request);
    log.info("{}删除角色信息, 角色ID:{}", session.getUserName(), roleId);
    // 调用service，得到影响行数
    int lines = roleService.deleteRole(roleId);
    // 记录日志
    log.info("{}删除角色信息，影响行数{}", session.getUserName(), lines);
    return new JSONMessage(true, "删除成功");
  }
  /**
   * 删除该用户对应的该角色
   * @param request
   * @param response
   * @param roleId
   * @return
   */
  @RequestMapping(value = "/delUserAndRole", method = RequestMethod.GET,
      produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  public JSONMessage delUserAndRole(HttpServletRequest request, HttpServletResponse response, UserRole ur) {
    // 记录日志
    SessionBean session = getSessionBean(request);
    log.info("{}删除用户和角色信息, 用户ID:{},角色ID:{}", session.getUserName(), ur.getUserId(), ur.getRoleId());
    // 调用service，得到影响行数
    int lines = userService.delUserAndRole(ur);
    // 记录日志
    log.info("{}删除用户和角色信息，影响行数{}", session.getUserName(), lines);
    return new JSONMessage(true, "删除成功");
  }
  
}
