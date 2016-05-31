package com.eleme.service;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.springframework.stereotype.Service;

import com.eleme.util.pager.PgInfo;
import com.eleme.util.pager.TbData;

import me.ele.elog.Log;
import me.ele.elog.LogFactory;

/**
 * 业务逻辑基础类.
 * 
 * @author huwenwen
 *
 */
@Service
public class BaseService {

	/**
	 * 日志对象
	 */
	protected static final Log log = LogFactory.getLog(BaseService.class);

	/**
	 * 根据条件到TbData的封装类.
	 * 
	 * @param totalCount
	 *            总数量
	 * @param currentPage
	 *            当前页
	 * @param pageSize
	 *            每页数量
	 * @param list
	 *            列表
	 * @return TbData封装类
	 */
	public TbData initTbData(int totalCount, Integer currentPage, Integer pageSize, List<?> list) {
		PgInfo pageInfo = new PgInfo();
		pageInfo.setPageNo(totalCount == 0 ? 0 : currentPage);
		pageInfo.setPageSize(pageSize);
		pageInfo.setTotalCount(totalCount);
		pageInfo.setSumPage(totalCount % pageSize == 0 ? totalCount / pageSize : (totalCount / pageSize + 1));
		// 封装tbData
		TbData tbData = new TbData();
		tbData.setPageInfo(pageInfo);
		tbData.setList(list);
		return tbData;
	}

	/**
	 * 根据表格cell得到具体内容
	 * 
	 * @param cell
	 * @return
	 */
	public String getPOICellValue(Cell cell) {
		if (cell == null) {
			return "";
		}
		String str = null;
		if (cell instanceof XSSFCell) {
			XSSFCell xssfCell = (XSSFCell) cell;
			if (xssfCell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
				if (HSSFDateUtil.isCellDateFormatted(xssfCell)) {
					double d = xssfCell.getNumericCellValue();
					Date date = HSSFDateUtil.getJavaDate(d);
					SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
					str = dateFormat.format(date);
				} else {
					// 当数字比较大的时候会变为科学计数法
					DecimalFormat df = new DecimalFormat("0");
					str = df.format(xssfCell.getNumericCellValue());
					// double val = xssfCell.getNumericCellValue();
					// int intVal = new Double(val).intValue();
					// str = Integer.toString(intVal);
				}
			} else if (xssfCell.getCellType() == Cell.CELL_TYPE_BOOLEAN) {
				boolean bl = xssfCell.getBooleanCellValue();
				str = Boolean.toString(bl);
			} else if (xssfCell.getCellType() == Cell.CELL_TYPE_STRING) {
				str = xssfCell.getStringCellValue();
			} else if (xssfCell.getCellType() == Cell.CELL_TYPE_FORMULA) {
				double formula = xssfCell.getNumericCellValue();
				if (HSSFDateUtil.isValidExcelDate(formula)) {
					Date date = HSSFDateUtil.getJavaDate(formula);
					SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
					str = dateFormat.format(date);
				} else {
					int intVal = new Double(formula).intValue();
					str = Integer.toString(intVal);
				}
			} else if (xssfCell.getCellType() == Cell.CELL_TYPE_BLANK) {
				str = "";
			}
		} else if (cell instanceof HSSFCell) {
			HSSFCell hssfCell = (HSSFCell) cell;
			if (hssfCell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
				if (HSSFDateUtil.isCellDateFormatted(hssfCell)) {
					double d = hssfCell.getNumericCellValue();
					Date date = HSSFDateUtil.getJavaDate(d);
					SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
					str = dateFormat.format(date);
				} else {
					double val = hssfCell.getNumericCellValue();
					int intVal = new Double(val).intValue();
					str = Integer.toString(intVal);
				}
			} else if (hssfCell.getCellType() == Cell.CELL_TYPE_BOOLEAN) {
				boolean bl = hssfCell.getBooleanCellValue();
				str = Boolean.toString(bl);
			} else if (hssfCell.getCellType() == Cell.CELL_TYPE_STRING) {
				str = hssfCell.getStringCellValue();
			} else if (hssfCell.getCellType() == Cell.CELL_TYPE_FORMULA) {
				double formula = hssfCell.getNumericCellValue();
				if (HSSFDateUtil.isValidExcelDate(formula)) {
					Date date = HSSFDateUtil.getJavaDate(formula);
					SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
					str = dateFormat.format(date);
				} else {
					int intVal = new Double(formula).intValue();
					str = Integer.toString(intVal);
				}
			} else if (hssfCell.getCellType() == Cell.CELL_TYPE_BLANK) {
				str = "";
			}
		}
		return str;
	}

}
