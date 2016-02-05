package com.eleme.annotation.validator;

import javax.inject.Inject;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.stereotype.Service;

import com.eleme.service.validator.IValidatorService;
import com.eleme.util.CommonUtil;

import me.ele.elog.Log;
import me.ele.elog.LogFactory;

/**
 * 自定义注解验证器，字段名唯一.
 * 
 * @author yonglin.zhu
 *
 */
@Service
public class SingleValidator implements ConstraintValidator<Single, String> {

	// 唯一性字段
	private String field;

	// 唯一性表名
	private String tableName;

	// 唯一性状态值
	private String status;

	// 唯一性状态字段
	private String statusField;

	// 数据源选择；默认是mart,另外提供opt数据源
	private String dataSource;

	/**
	 * log
	 */
	private static Log log = LogFactory.getLog(SingleValidator.class);

	@Inject
	private IValidatorService validatorService;

	@Override
	public void initialize(Single constraintAnnotation) {
		this.field = constraintAnnotation.field();
		this.tableName = constraintAnnotation.tableName();
		this.status = constraintAnnotation.status();
		this.statusField = constraintAnnotation.statusField();
		this.dataSource = constraintAnnotation.dataSource();
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		boolean flag = true;
		try {
			flag = validatorService.judgeSingle(value, field, tableName, status, statusField,dataSource);
		} catch (Exception e) {
			log.error(CommonUtil.getErrorMessage(e));
		}
		return flag;
	}

}
