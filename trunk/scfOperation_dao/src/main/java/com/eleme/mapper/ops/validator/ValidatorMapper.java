package com.eleme.mapper.ops.validator;

import java.util.Map;

/**
 * 验证唯一性Mapper接口
 * 
 * @author yonglin.zhu
 *
 */
public interface ValidatorMapper {

	/**
	 * 根据条件查询件数
	 * 
	 * @param map
	 * @return
	 */
	Integer queryCount(Map<String, Object> map);
}
