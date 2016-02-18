package com.eleme.controller.user;

import java.text.SimpleDateFormat;
import java.util.HashMap;
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
import com.eleme.bean.user.ChangePwdBean;
import com.eleme.bean.user.UserAddBean;
import com.eleme.bean.user.UserNameBean;
import com.eleme.bean.user.UserQueryBean;
import com.eleme.controller.BaseController;
import com.eleme.domain.ops.UserRole;
import com.eleme.service.user.IRoleService;
import com.eleme.service.user.IUserService;
import com.eleme.util.TokenProcessor;
import com.eleme.util.pager.TbData;

import me.ele.elog.Log;
import me.ele.elog.LogFactory;

/**
 * 系统用户管理.
 * 
 * @author huwenwen
 *
 */
@Controller
@RequestMapping(value = "/user")
public class UserController extends BaseController {

  /**
   * log
   */
  private static Log log = LogFactory.getLog(UserController.class);

  @Inject
  private IUserService userService;

  @Inject
  private IRoleService roleService;

  /**
   * 用户列表.
   * 
   * @param request
   * @param uqb
   * @param currentPage
   * @return
   * @throws Exception
   */
  @RequestMapping(value = "/list", method = RequestMethod.GET)
  @UserMenu
  public ModelAndView list(HttpServletRequest request, UserQueryBean uqb, Integer currentPage)
      throws Exception {
    // 记录日志
    SessionBean session = getSessionBean(request);
    log.info("{}查看用户列表", session.getUserName());
    // 查询数据并返回
    ModelAndView mav = new ModelAndView("user/list");
    TbData tbData = userService.userList(currentPage, uqb);
    tbData = tbData.fillTbData("user/list", uqb, null);
    mav.addObject("tbData", tbData);
    mav.addObject("uqb", uqb);
    return mav;
  }

  /**
   * 跳转到添加用户的页面.
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
    ModelAndView mav = new ModelAndView("user/add");
    // 记录日志
    SessionBean session = getSessionBean(request);
    log.info("{}跳转到添加用户界面", session.getUserName());
    // 获得角色集合
    mav.addObject("rolesList", roleService.rolesList());
    // 返回
    return mav;
  }

  /**
   * 添加用户保存.
   * 
   * @param request
   * @param response
   * @param uab 添加用户的表单封装bean
   * @param result 保留校验结果
   * @return
   * @throws Exception
   */
  @RequestMapping(value = "/addSave", method = RequestMethod.POST)
  @AvoidDuplicateSubmission(needRemoveToken = true)
  @UserMenu
  public ModelAndView addSave(HttpServletRequest request, HttpServletResponse response,
      @Valid @ModelAttribute("uab") UserAddBean uab, BindingResult result) throws Exception {
    ModelAndView mav = new ModelAndView();
    // 验证
    if (result.hasErrors()) {
      mav.setViewName("user/add");
      // 获得角色集合
      mav.addObject("rolesList", roleService.rolesList());
      // 需要再次设置token
      request.getSession(false).setAttribute("token",
          TokenProcessor.getInstance().generateToken(request));
      return mav;
    }
    // 记录日志
    SessionBean session = getSessionBean(request);
    log.info("{}添加用户信息", session.getUserName());
    // clone UserAddBean 数据到 MUsers,field名称需相同
    UserRole ur = new UserRole();
    BeanUtils.copyProperties(ur, uab);
    // 调用service，得到影响行数
    int lines = userService.userAdd(ur);
    // 记录日志
    log.info("{}添加用户信息，影响行数{}", session.getUserName(), lines);
    // 返回
    mav.setViewName("redirect:/user/list");
    return mav;
  }


  /**
   * 异步返回用户名是否唯一.
   * 
   * @param request
   * @param response
   * @param unb 前台传来的json字符串，例如js：JSON.stringify(data).（如果是json对象，会报错）
   * @return JSONMessage封装类
   * @throws Exception
   */
  @RequestMapping(value = "/judgeNameSingle", method = RequestMethod.POST)
  @ResponseBody
  public JSONMessage judgeNameSingle(HttpServletRequest request, HttpServletResponse response,
      @RequestBody UserNameBean unb) throws Exception {
    SessionBean session = getSessionBean(request);
    boolean flag = userService.judgeIfUserNameSingle(unb.getUserName());
    log.info("{}添加用户，判断{}是否唯一,结果{}", session.getUserName(), unb.getUserName(), flag);
    if (flag) {
      return new JSONMessage(flag, "用户名唯一");
    } else {
      return new JSONMessage(flag, "该用户名已存在");
    }
  }

  /**
   * 跳转到用户修改密码页面
   * 
   * @param request
   * @return
   * @throws Exception
   */
  @RequestMapping(value = "/changePwd", method = RequestMethod.GET)
  @AvoidDuplicateSubmission(needSaveToken = true)
  @UserMenu
  public ModelAndView changePwd(HttpServletRequest request) throws Exception {
    // 记录日志
    SessionBean session = getSessionBean(request);
    log.info("{}进入修改密码页面", session.getUserName());
    // 查询数据并返回
    ModelAndView mav = new ModelAndView("user/changepwd");
    return mav;
  }

  /**
   * 修改用户密码
   * 
   * @param request
   * @param response
   * @param cpb
   * @param result
   * @return
   * @throws Exception
   */
  @RequestMapping(value = "/changePwdSave", method = RequestMethod.POST)
  @AvoidDuplicateSubmission(needRemoveToken = true)
  @UserMenu
  public ModelAndView changePwdSave(HttpServletRequest request, HttpServletResponse response,
      @Valid @ModelAttribute("cpb") ChangePwdBean cpb, BindingResult result) throws Exception {
    ModelAndView mav = new ModelAndView();
    // 验证
    if (result.hasErrors()) {
      mav.setViewName("user/changepwd");
      // 需要再次设置token
      request.getSession(false).setAttribute("token",
          TokenProcessor.getInstance().generateToken(request));
      return mav;
    }
    SessionBean session = getSessionBean(request);
    // 业务验证
    if (userService.judgePwd(session.getUserId(), cpb.getPswd())) {
      result.addError(new FieldError("cpb", "pswd", "旧密码输入不正确"));
      if (result.hasErrors()) {
        mav.setViewName("user/changepwd");
        // 需要再次设置token
        request.getSession(false).setAttribute("token",
            TokenProcessor.getInstance().generateToken(request));
        return mav;
      }
    }
    // 关联check
    // 新密码不能和旧密码相同
    if (cpb.getPswd().equals(cpb.getNewPswd())) {
      result.addError(new FieldError("cpb", "newPswd", "新密码不能和旧密码相同"));
      if (result.hasErrors()) {
        mav.setViewName("user/changepwd");
        // 需要再次设置token
        request.getSession(false).setAttribute("token",
            TokenProcessor.getInstance().generateToken(request));
        return mav;
      }
    }
    // 两次密码不相同
    if (!cpb.getNewPswdConfirm().equals(cpb.getNewPswd())) {
      result.addError(new FieldError("cpb", "newPswd", "新密码两次输入不一致"));
      if (result.hasErrors()) {
        mav.setViewName("user/changepwd");
        // 需要再次设置token
        request.getSession(false).setAttribute("token",
            TokenProcessor.getInstance().generateToken(request));
        return mav;
      }
    }
    log.info("{}用户修改密码", session.getUserName());
    com.eleme.domain.ops.MUsers mu = new com.eleme.domain.ops.MUsers();
    mu.setUserId(session.getUserId().intValue());
    mu.setUserName(session.getUserName());
    mu.setPswd(cpb.getNewPswd());
    // 调用service，得到影响行数
    int lines = userService.userEdit(mu);
    // 记录日志
    log.info("{}用户修改用户信息，影响行数{}", session.getUserName(), lines);
    // 返回
    mav.setViewName("redirect:/logout");
    return mav;
  }

  /**
   * 获取用户信息
   * 
   */
  @RequestMapping(value = "/getUserInfo", method = RequestMethod.GET,
      produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  public Map<String, Object> getUserInfo(HttpServletRequest request, HttpServletResponse response,
      Integer userId) throws Exception {
    // 记录日志
    SessionBean session = getSessionBean(request);
    log.info("{}根据用户ID{}获取的信息", session.getUserName(), userId);
    // 查询 用户信息
    UserRole userInfo = userService.getUserInfoById(userId);
    // 将得到的信息封装成Map集合
    Map<String, Object> map = new HashMap<String, Object>();
    map.put("userName", userInfo.getUserName());
    map.put("userId", userInfo.getUserId());
    map.put("roleName", userInfo.getRoleName());
    map.put("roleId", userInfo.getRoleId());
    //时间转换
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    map.put("createdAt", sdf.format(userInfo.getCreatedAt()));
    map.put("updatedAt", sdf.format(userInfo.getUpdatedAt()));
    return map;
  }
  /**
   * 编辑用户信息
   * @throws Exception 
   */
  @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
  @AvoidDuplicateSubmission(needSaveToken = true)
  @UserMenu
  public ModelAndView editView(HttpServletRequest request, HttpServletResponse response,
      @PathVariable Integer id) throws Exception{
    ModelAndView mav = new ModelAndView("user/edit");
    // 记录日志
    SessionBean session = getSessionBean(request);
    log.info("{}跳转到用户编辑界面", session.getUserName());
    UserRole userInfo = userService.getUserInfoById(id);
    userInfo.setUserId(id);
    mav.addObject("user", userInfo);
    //用户角色集合
    mav.addObject("rolesList", roleService.rolesList());
    
    return mav;
  }
  /**
   * 编辑信息保存
   */
  @RequestMapping(value="/editSave", method = RequestMethod.POST)
  @AvoidDuplicateSubmission(needRemoveToken = true)
  @UserMenu
  public ModelAndView editSave(HttpServletRequest request, HttpServletResponse response, UserRole user){
    ModelAndView mav = new ModelAndView();
    // 记录日志
    SessionBean session = getSessionBean(request);
    log.info("{}修改用户信息", session.getUserName());
    // 调用service，得到影响行数
    int lines = userService.updateUserAndRole(user);
    // 记录日志
    log.info("{}更行用户信息，影响行数{}", session.getUserName(), lines);
    // 返回
    mav.setViewName("redirect:/user/list");
    return mav;
  }
  /**
   * 删除用户
   */
  @RequestMapping(value="/del{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  public JSONMessage del(HttpServletRequest request, HttpServletResponse response, Integer userId){
    // 记录日志
    SessionBean session = getSessionBean(request);
    log.info("{}删除用户信息, 账户ID:{}", session.getUserName(),userId);
    // 调用service，得到影响行数
    int lines = userService.updateUser(userId);
    // 记录日志
    log.info("{}删除用户信息，影响行数{}", session.getUserName(), lines);
    return new JSONMessage(true, "删除成功");
  }
 
}
