package com.eleme.security;

import com.eleme.bean.security.AuthoritiesBean;
import com.eleme.bean.security.ResourcesBean;
import com.eleme.service.security.ISecurityService;
import me.ele.elog.Log;
import me.ele.elog.LogFactory;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * 安全元数据源.
 *
 * @author penglau
 */
public class CustomSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {
  /**
   * 日志对象
   */
  private static final Log log = LogFactory.getLog(CustomSecurityMetadataSource.class);
  /**
   * 所有权限资源封装的Map
   */
  private static Map<String, Collection<ConfigAttribute>> resourceMap = null;
  /**
   * 用户权限业务逻辑
   */
  @Inject
  private ISecurityService securityService;

  /**
   * 初始化
   *
   * @param securityService 权限业务逻辑对象
   */
  //
  public CustomSecurityMetadataSource(ISecurityService securityService) {
    log.info("初始化资源数据信息");
    this.securityService = securityService;
    loadResourceDefine();
  }

  /**
   * 加载资源
   */
  public void loadResourceDefine() {
    log.info("若资源信息为空，则加载资源信息,封装到resourceMap中");
    // 若map为空 实例化一个以url为建 ConfigAttribute集合为值的map集合
    resourceMap = new HashMap<String, Collection<ConfigAttribute>>();
    // 查找出所有权限集合
    List<AuthoritiesBean> allAuth = securityService.findAllAuthorities();
    // 遍历权限集合
    for (AuthoritiesBean auth : allAuth) {
      // 得到该权限下的所有资源集合
      List<ResourcesBean> resourceList = auth.getResourceList();
      // 遍历资源集合
      for (ResourcesBean resource : resourceList) {
        // 得到资源对象的url
        String url = resource.getResourceString();
        // 判断map中是否存在当前url键
        if (!resourceMap.containsKey(url)) {
          // 若不存在则创建一个新的ConfigAttribute对象集合
          Collection<ConfigAttribute> value = new ArrayList<ConfigAttribute>();
          // 得到权限名称 传入实例化的spring security上下文对象中
          ConfigAttribute ca = new SecurityConfig(resource.getResourceString());
          // 将spring security上下文对象封装到ConfigAttribute集合当中
          value.add(ca);
          // 再将url所对应的ConfigAttribute集合覆盖掉
          resourceMap.put(url, value);
        }
      }
    }
  }

  /**
   * 得到用户请求所对应的所有spring security权限对象集合
   */
  public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
    // 若resourceMap集合为空，则重新加载资源集合
    if (resourceMap == null) {
      loadResourceDefine();
    }
    // 获取请求对象，并获取参数
    HttpServletRequest request = ((FilterInvocation) object).getRequest();
    // 得到传入的FilterInvocation对象的请求路径
    String requestUrl = ((FilterInvocation) object).getRequestUrl();
    requestUrl = trimLeft(requestUrl, "/");
    log.info("用户访问url：" + requestUrl);
    // requestUrl与数据库中的resource_string匹配，匹配成功后替换成数据库中的值\
    if ("GET".equals(request.getMethod()) || "POST".equals(request.getMethod())) {
      for (Iterator<String> keyIter = resourceMap.keySet().iterator(); keyIter.hasNext(); ) {
        String key = keyIter.next();
        if (requestUrl.startsWith(key)) {
          requestUrl = key;
//          log.info("key:" + key + ",break");
          break;
        }
      }
    } else {
      log.error("未知请求");
    }

    //    int firstXGIndex = requestUrl.indexOf('/');
    // 根据请求路径得到resourceMap集合中所对应的ConfigAttribute的值集合并返回
    return resourceMap.get(requestUrl);
  }

  public Collection<ConfigAttribute> getAllConfigAttributes() {
    return null;
  }

  public boolean supports(Class<?> clazz) {
    return true;
  }

  /**
   * 删除字符串左侧指定字符串
   *
   * @param org
   * @param deleteS
   * @return
   */
  private String trimLeft(String org, String deleteS) {
    if (StringUtils.isNotBlank(deleteS)) {
      if (org.startsWith(deleteS)) {
        org = org.substring(deleteS.length());
        return trimLeft(org, deleteS);
      } else {
        return org;
      }
    } else {
      return org.trim();
    }


  }

}
