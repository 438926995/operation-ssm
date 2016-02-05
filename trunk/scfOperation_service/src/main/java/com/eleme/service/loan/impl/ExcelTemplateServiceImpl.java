package com.eleme.service.loan.impl;

import com.eleme.constants.ExportExcelTplType;
import com.eleme.domain.ops.file.AppExcelTemplateFormVo;
import com.eleme.domain.ops.file.AppExcelTemplateVo;
import com.eleme.domain.ops.file.TSysExcelTemplateRelation;
import com.eleme.mapper.ops.file.TSysExcelTemplateMapper;
import com.eleme.service.loan.IExcelTemplateService;
import me.ele.elog.Log;
import me.ele.elog.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;

/**
 * excel导出模板服务实现类型
 * <p>
 * Created by sunwei on 16/1/19.
 */
@Service
public class ExcelTemplateServiceImpl implements IExcelTemplateService {

  private static final Log elog = LogFactory.getLog(ExcelTemplateServiceImpl.class);

  @Inject
  TSysExcelTemplateMapper excelTemplateMapper;

  @Override
  @Transactional(readOnly = true)
  public List<AppExcelTemplateVo> getAppExcelTemplates() {
    //获取所有导出申请贷款ExcelTemplate
    return excelTemplateMapper.selectAppExcelTemplates(ExportExcelTplType.APP.getType());
  }

  @Override
  @Transactional(rollbackFor = Throwable.class)
  public void saveAppExcelTemplate(AppExcelTemplateFormVo excelTemplate) {
    if (excelTemplate.getExcelTemplateId() == null) { //  id 不存在时 新增
      elog.info("新增贷款excel导出模板");
      excelTemplate.setExcelTemplateType(ExportExcelTplType.APP.getType());
      excelTemplateMapper.insertExcelTemplate(excelTemplate);
    } else { // 更新操作
      elog.info("更新贷款excel导出模板");
      excelTemplate.setExcelTemplateType(ExportExcelTplType.APP.getType());
      excelTemplateMapper.updateExcelTemplate(excelTemplate);
      excelTemplateMapper.deleteExcelTemplateRelationsByTempId(excelTemplate.getExcelTemplateId());
    }
    if (excelTemplate.getExcelTemplateRelations() != null
        && excelTemplate.getExcelTemplateRelations().size() > 0) {
      for (TSysExcelTemplateRelation relation : excelTemplate.getExcelTemplateRelations()) {
        relation.setExcelTplId(excelTemplate.getExcelTemplateId());
      }
      excelTemplateMapper.insertExcelTemplateRelations(excelTemplate.getExcelTemplateRelations());
    }
    elog.info("保存excel模板完毕");
  }

  @Override
  @Transactional(rollbackFor = Throwable.class)
  public void removeAppExcelTemplate(Integer tempId) {
    elog.info("删除id:{},excel模板",tempId);
    excelTemplateMapper.deleteExcelTemplateById(tempId);
    excelTemplateMapper.deleteExcelTemplateRelationsByTempId(tempId);
    elog.info("删除id:{},excel模板,删除完成",tempId);
  }

}
