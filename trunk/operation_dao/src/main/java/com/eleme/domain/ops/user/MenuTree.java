package com.eleme.domain.ops.user;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 菜单树
 * 
 * @author huwenwen
 */
public class MenuTree implements Serializable {
  private static final long serialVersionUID = 5154140857758046959L;

  // 用于生成菜单树，临时变量
  private transient Map<Integer, MenuNode> menuNodeMap = new HashMap<Integer, MenuNode>();

  // 存放菜单根目录信息
  private List<MenuNode> root = new ArrayList<MenuNode>(0);
  // 面包屑导航，按照菜单层级迭代
  private List<String> breadcrumbNavigationList = new ArrayList<String>(0);
  // 当前菜单（可获取当前菜单的子菜单或兄弟菜单，用于右侧tab页）
  private MenuNode curMenu;

  public MenuTree() {}

  public MenuTree(List<Resource> resources) {
    setResources(resources);
  }

  /**
   * 根据查询所得resource生成菜单树
   * 
   * @param resources
   */
  public void setResources(List<Resource> resources) {
    MenuNode thisMenuNode = null, parentMenuNode = null;
    for (Resource resource : resources) {

      thisMenuNode = new MenuNode(resource);
      menuNodeMap.put(thisMenuNode.getResourceId(), thisMenuNode);

      if ((parentMenuNode = menuNodeMap.get(resource.getParentId())) != null) {
        thisMenuNode.setParent(parentMenuNode);
        parentMenuNode.addChild(thisMenuNode);
      }

      if (resource.getParentId() == 0) {
        root.add(thisMenuNode);
      }
    }
    menuNodeMap.clear();
  }

  /**
   * 根据当前url匹配对应菜单以及导航栏信息
   * 
   * @param curUrl
   */
  public void setCurMenu(String curUrl) {
    
    // 循环一级菜单
    for (MenuNode level1 : root) {
      level1.setSelected(false);
      
      // 循环二级菜单
      for (MenuNode level2 : level1.getChildren()) {
        level2.setSelected(false);
        
        if (curUrl.indexOf(level2.getResourceString().startsWith("/") ? level2.getResourceString() : "/".concat(level2.getResourceString())) == 0) {
          curMenu = level2;
        }
        
        // 循环三级菜单
        for (MenuNode level3 : level2.getChildren()) {
          level3.setSelected(false);
          if (curUrl.contains(level3.getResourceString())) {
            curMenu = level3;
          }
        }
      }
    }

    breadcrumbNavigationList.clear();
    if (curMenu != null) {
      processSelectedMenu(curMenu);
      Collections.reverse(breadcrumbNavigationList);
    }
  }

  private void processSelectedMenu(MenuNode menuNode) {
    breadcrumbNavigationList.add(menuNode.getResourceName());
    menuNode.setSelected(true);
    MenuNode parent = menuNode.getParent();
    if (parent != null) {
      processSelectedMenu(parent);
    }
  }

  public List<MenuNode> getRoot() {
    return root;
  }

  public MenuNode getCurMenu() {
    return curMenu;
  }

  public List<String> getBreadcrumbNavigationList() {
    return breadcrumbNavigationList;
  }

  /**
   * 菜单节点
   * 
   * @author zhangqiongbiao
   */
  public class MenuNode implements Serializable {
    private static final long serialVersionUID = -959374682329150971L;

    private Integer resourceId;
    private String resourceName;
    private String resourceString;
    private MenuNode parent;
    private List<MenuNode> children = new ArrayList<MenuNode>(0);
    private String grade;
    private boolean selected = false;

    public MenuNode() {}

    public MenuNode(Resource resource) {
      this(resource.getResourceId(), resource.getResourceName(),
          resource.getResourceString(), resource.getGrade());
    }

    public MenuNode(Integer resourceId, String resourceName, String resourceString, String grade) {
      this.resourceId = resourceId;
      this.resourceName = resourceName;
      this.resourceString = resourceString;
      this.grade = grade;
    }

    public Integer getResourceId() {
      return resourceId;
    }

    public String getResourceName() {
      return resourceName;
    }

    public String getResourceString() {
      return resourceString;
    }

    public MenuNode getParent() {
      return parent;
    }

    public List<MenuNode> getChildren() {
      return children;
    }

    public void addChild(MenuNode child) {
      this.children.add(child);
    }

    public void setParent(MenuNode parent) {
      this.parent = parent;
    }

    public boolean isSelected() {
      return selected;
    }

    public void setSelected(boolean selected) {
      this.selected = selected;
    }

    public String getGrade() {
      return grade;
    }

    public void setGrade(String grade) {
      this.grade = grade;
    }
  }
}