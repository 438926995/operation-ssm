package com.eleme.service.validator;

/**
 * 唯一性验证接口
 * 
 * @author yonglin.zhu
 *
 */
public interface IValidatorService {

	/**
	 * 验证字段唯一性
	 * 
	 * @param value
	 * @param field
	 * @param tableName
	 * @param status
	 * @param statusField
	 * @param dataSource
	 * @return
	 * @throws Exception
	 */
	public boolean judgeSingle(String value, String field, String tableName, String status, String statusField,
			String dataSource) throws Exception;

}
