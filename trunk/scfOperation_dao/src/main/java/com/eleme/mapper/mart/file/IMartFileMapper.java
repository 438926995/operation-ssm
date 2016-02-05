package com.eleme.mapper.mart.file;

import java.util.List;
import java.util.Map;

import com.eleme.bean.file.SysUploadFileBean;

/**
 * 前台mart附件管理接口
 * 
 * @author penglau
 *
 */
public interface IMartFileMapper {

  /**
   * 根据业务类型及主键查找对应的记录
   * 
   * @param paramMap 参数对象
   * @return 附件表记录
   */
  List<SysUploadFileBean> getUploadFileByContainId(Map<String, Object> paramMap);

  /**
   * 根据业务类型及主键查找最新的记录
   * 
   * @param file
   * @return
   */
  SysUploadFileBean getUploadFileById(Integer id);

  /**
   * 根据页面的查询条件查找对应的记录
   * 
   * @param paramMap 参数对象
   * @return 附件表记录
   */
  List<SysUploadFileBean> getUploadFileList(Map<String, Object> paramMap);


  /**
   * 根据页面的查询条件查找符合条件的记录总数
   * 
   * @return 数量
   * @throws Exception
   */
  public int fileTotalNum(Map<String, Object> paramMap) throws Exception;

  /**
   * 插入附件表记录
   * 
   * @param sfb 参数对象
   * @return 影响行数
   */
  int insertInfoSysFileUpload(SysUploadFileBean sfb);


}
