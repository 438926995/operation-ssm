package com.eleme.service.loan;

import com.eleme.domain.ops.file.AppExcelTemplateFormVo;
import com.eleme.domain.ops.file.AppExcelTemplateVo;

import java.util.List;

/**
 * excel导出模板Service
 * <p>
 * Created by sunwei on 16/1/19.
 */
public interface IExcelTemplateService {


  /**
   * 获取贷款申请excel导出模板
   * @return
   */
  public List<AppExcelTemplateVo> getAppExcelTemplates();


  /**
   * 保存贷款申请excel导出模板
   * @param excelTemplate
   */
  public void saveAppExcelTemplate(AppExcelTemplateFormVo excelTemplate);

  /**
   * 通过模板id,删除excel导出模板
   * @param tempId
   */
  public void removeAppExcelTemplate(Integer tempId);


}
