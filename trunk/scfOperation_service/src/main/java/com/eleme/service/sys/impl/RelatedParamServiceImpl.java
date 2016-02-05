package com.eleme.service.sys.impl;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.eleme.domain.ops.sys.RelatedParamEntity;
import com.eleme.mapper.ops.sys.IRelatedParamMapper;
import com.eleme.service.sys.IRelatedParamService;

@Service
public class RelatedParamServiceImpl implements IRelatedParamService {

  @Inject
  private IRelatedParamMapper relatedParamMapper;

  @Override
  public String getValueByFlag(String flag) {
    RelatedParamEntity relatedParam = relatedParamMapper.selectByFlag(flag);
    if (relatedParam != null) {
      return relatedParam.getValue();
    }
    return null;
  }

  @Override
  public void alertValueByFlag(String flag, String value) {
    RelatedParamEntity relatedParam = new RelatedParamEntity();
    relatedParam.setFlag(flag);
    relatedParam.setValue(value);
    relatedParamMapper.updateValueByFlag(relatedParam);
  }

}
