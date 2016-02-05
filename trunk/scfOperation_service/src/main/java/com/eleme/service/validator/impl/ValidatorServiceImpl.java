package com.eleme.service.validator.impl;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eleme.mapper.mart.validator.MartValidatorMapper;
import com.eleme.mapper.ops.validator.ValidatorMapper;
import com.eleme.service.BaseService;
import com.eleme.service.validator.IValidatorService;

/**
 * 验证唯一性实现类
 * 
 * @author yonglin.zhu
 *
 */
@Service
@Transactional(rollbackFor = java.lang.Throwable.class)
public class ValidatorServiceImpl extends BaseService implements IValidatorService {

	@Inject
	private ValidatorMapper validatorMapper;

	@Inject
	private MartValidatorMapper martValidatorMapper;

	@Override
	public boolean judgeSingle(String value, String field, String tableName, String status, String statusField,
			String dataSource) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("value", value);
		map.put("field", field);
		map.put("tableName", tableName);
		map.put("status", status);
		map.put("statusField", statusField);

		// 根据条件查询总件数
		Integer count = 0;
		if ("mart".equals(dataSource)) {
			count = martValidatorMapper.queryCount(map);
		} else {
			count = validatorMapper.queryCount(map);
		}
		return count.intValue() == 0 ? true : false;
	}

}
