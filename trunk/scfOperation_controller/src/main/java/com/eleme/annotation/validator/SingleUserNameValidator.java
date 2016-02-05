package com.eleme.annotation.validator;

import javax.inject.Inject;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.stereotype.Service;

import com.eleme.service.user.IUserService;
import com.eleme.util.CommonUtil;

import me.ele.elog.Log;
import me.ele.elog.LogFactory;

/**
 * 自定义注解验证器，用户名唯一.
 * 
 * @author penglau
 *
 */
@Service
public class SingleUserNameValidator implements ConstraintValidator<SingleUserName, String> {

	/**
	 * log
	 */
	private static Log log = LogFactory.getLog(SingleUserNameValidator.class);

	@Inject
	private IUserService userService;

	@Override
	public void initialize(SingleUserName constraintAnnotation) {
	}

	@Override
	public boolean isValid(String username, ConstraintValidatorContext context) {
		boolean flag = true;
		try {
			flag = userService.judgeIfUserNameSingle(username);
		} catch (Exception e) {
			log.error(CommonUtil.getErrorMessage(e));
		}
		return flag;
	}

}
