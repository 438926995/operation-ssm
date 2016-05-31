package com.eleme.monitor;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 定义一个简单的数据库查询，用于监控系统是否可用 如果查询正常，返回alive；如果查询失败，返回error.
 * 
 * @author huwenwen
 */
public class SystemHealthyServlet extends HttpServlet implements Servlet {
  private static final long serialVersionUID = 1L;

  /**
   * @see HttpServlet#HttpServlet().
   */
  public SystemHealthyServlet() {
    super();
  }

  /**
   * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response).
   */
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    this.doPost(request, response);
  }

  /**
   * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response).
   */
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    PrintWriter out = response.getWriter();
    String result = "alive";
    try {
      // CommonDao commonDao = BeanValidateUtil.getSpringBean(CommonDao.class);
      // commonDao.getRowCountBySpringJDBC("SELECT COUNT(*) FROM M_ORGANIZATION");
    } catch (Exception e) {
      result = "error";
    }
    out.println(result);
  }

}
