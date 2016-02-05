package com.eleme.controller.file;


import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.eleme.annotation.controller.UserMenu;
import com.eleme.bean.SessionBean;
import com.eleme.bean.file.FileQueryBean;
import com.eleme.controller.BaseController;
import com.eleme.service.file.IFileService;
import com.eleme.util.pager.TbData;

import me.ele.elog.Log;
import me.ele.elog.LogFactory;

/**
 * 文件Controller
 * 
 * @author sunwei
 * @since 2015年12月14日
 *
 */
@Controller
@RequestMapping("/file")
public class FileController extends BaseController {

  /**
   * elog
   */
  private static Log log = LogFactory.getLog(FileController.class);

  @Inject
  private IFileService fileService;

  /**
   * @param templateName
   */
  @RequestMapping(value = "/excelTemplate/{templateId:[\\d]+}", method = RequestMethod.GET)
  public String getExcelTempleById(@PathVariable("templateId") String templateId) {

    return "redirect:";
  }

  /**
   * [前台金融超市]附件列表
   * 
   * @param request
   * @param fqb 查询条件封装bean
   * @param currentPage 当前页
   * @return
   * @throws Exception
   */
  @RequestMapping(value = "/list/mart", method = RequestMethod.GET)
  @UserMenu
  public ModelAndView listmart(HttpServletRequest request, FileQueryBean fqb, Integer currentPage)
      throws Exception {
    // 记录日志
    SessionBean session = getSessionBean(request);
    log.info("{}查看附件列表", session.getUserName());
    // 查询数据并返回
    ModelAndView mav = new ModelAndView("system/filelist");
    TbData tbData = fileService.getUploadFileList(currentPage, fqb);
    tbData = tbData.fillTbData("file/list/mart", fqb, null);
    mav.addObject("tbData", tbData);
    mav.addObject("fqb", fqb);
    return mav;
  }

}
