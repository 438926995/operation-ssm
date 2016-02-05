package com.eleme.service.file.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.eleme.bean.file.FileQueryBean;
import com.eleme.bean.file.SysUploadFileBean;
import com.eleme.constants.PagerConstants;
import com.eleme.domain.ops.file.TSysExcelTemplate;
import com.eleme.mapper.mart.file.IMartFileMapper;
import com.eleme.mapper.ops.file.IFileMapper;
import com.eleme.service.BaseService;
import com.eleme.service.file.IFileService;
import com.eleme.util.FileType;
import com.eleme.util.pager.TbData;

import me.ele.elog.Log;
import me.ele.elog.LogFactory;

/**
 * 文件 Service 实现类
 * 
 * @author sunwei
 * @since 2015年12月14日
 *
 */
@Service
public class FileServiceImpl extends BaseService implements IFileService {

  /**
   * 日志
   */
  private static final Log log = LogFactory.getLog(FileServiceImpl.class);

  @Inject
  private IFileMapper fileMapper;

  @Inject
  private IMartFileMapper martFileMapper;

  @Override
  public TSysExcelTemplate getExcelTemplateByTemplateId(String templateId) throws Exception {
    Map<String, Object> paramsMap = new HashMap<String, Object>();
    paramsMap.put("containType", FileType.EXCEL_TEMPLATE);
    paramsMap.put("templateId", templateId);
    return fileMapper.selectExcelTemplateByTemplateId(paramsMap);
  }

  @Override
  public TbData getUploadFileList(Integer currentPage, FileQueryBean fqb) throws Exception {
    // 第一次进入时，currentPage为空
    if (currentPage == null) {
      currentPage = 1;
    }
    // 封装sql查询条件
    Map<String, Object> paramsMap = new HashMap<String, Object>();
    paramsMap.put("startRecord", (currentPage - 1) * PagerConstants.PAGE_SIZE);
    paramsMap.put("pageSize", PagerConstants.PAGE_SIZE);
    paramsMap.put("sourceName", fqb.getSource_name());
    paramsMap.put("mimeType", fqb.getMime_type());
    List<SysUploadFileBean> uploadFileList = martFileMapper.getUploadFileList(paramsMap);
    int totalCount = martFileMapper.fileTotalNum(paramsMap);

    return initTbData(totalCount, currentPage, PagerConstants.PAGE_SIZE, uploadFileList);
  }

  @Override
  public SysUploadFileBean getUploadFileById(Integer id) {
    return martFileMapper.getUploadFileById(id);
  }

  @Override
  public int insertSysFileUpload(SysUploadFileBean sfb) {
    return martFileMapper.insertInfoSysFileUpload(sfb);
  }

}
