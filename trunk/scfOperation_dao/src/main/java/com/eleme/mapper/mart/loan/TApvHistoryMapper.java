package com.eleme.mapper.mart.loan;

import java.util.List;

import com.eleme.domain.mart.loan.TApvHistory;
import com.eleme.domain.mart.loan.TApvHistoryVo;

/**
 * 审批历史记录接口
 * 
 * @author yonglin.zhu
 *
 */
public interface TApvHistoryMapper {

	/**
	 * 新增审批历史记录
	 * 
	 * @param record
	 * @return
	 */
	int insert(TApvHistory record);

	/**
	 * 根据ID查询审批历史记录
	 * 
	 * @param ahId
	 * @return
	 */
	TApvHistory selectById(Integer ahId);

	/**
	 * 修改审批历史记录
	 * 
	 * @param record
	 * @return
	 */
	int updateById(TApvHistory record);

	/**
	 * 根据借贷申请ID查询审批历史记录
	 * 
	 * @param slId
	 * @return
	 */
	List<TApvHistoryVo> selectBySlId(Integer slId);
}