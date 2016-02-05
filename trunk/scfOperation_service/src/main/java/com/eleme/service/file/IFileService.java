package com.eleme.service.file;

import org.springframework.transaction.annotation.Transactional;

import com.eleme.bean.file.FileQueryBean;
import com.eleme.bean.file.SysUploadFileBean;
import com.eleme.domain.ops.file.TSysExcelTemplate;
import com.eleme.util.pager.TbData;

/**
 * 文件 Service
 * 
 * @author sunwei
 * @since 2015年12月14日
 *
 */
public interface IFileService {

  TSysExcelTemplate getExcelTemplateByTemplateId(String templateId) throws Exception;

  /**
   * 根据条件查询附件
   * 
   * @param currentPage 当前页
   * @param fqb 查询条件
   * @return
   * @throws Exception
   */
  @Transactional(readOnly = true)
  public TbData getUploadFileList(Integer currentPage, FileQueryBean fqb) throws Exception;

  /**
   * 根据主键Id取图片
   * 
   * @param id
   * @return
   */
  SysUploadFileBean getUploadFileById(Integer id);

  /**
   * 向上传附件表插入数据
   * 
   * @param sfb
   * @return
   */
  int insertSysFileUpload(SysUploadFileBean sfb);
}
