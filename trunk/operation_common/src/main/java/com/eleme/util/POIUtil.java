package com.eleme.util;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.*;

import java.util.Date;
import java.util.Map;

/**
 * POI类库的工具类
 *
 * @author huwenwen
 */
public class POIUtil {

  public static final String DEFAULT_DAY_FORMAT = "yy-m-d";
  public static final String DEFAULT_TIME_FORMAT = "h:mm";
  public static final String DEFAULT_DAY_AND_TIME_FORMAT =
      DEFAULT_DAY_FORMAT + " " + DEFAULT_TIME_FORMAT;

  public static final String CELLSTYLE_TYPE_NUMERICAL = "cellStyle_numerical";
  public static final String CELLSTYLE_TYPE_INTEGER = "cellStyle_integer";
  public static final String CELLSTYLE_TYPE_STRING = "cellStyle_string";
  public static final String CELLSTYLE_TYPE_DAY_AND_TIME = "cellStyle_day_and_time";
  private static final short TWIPS_PER_PIEXL = 15; //1 Pixel = 1440 TPI / 96 DPI = 15 Twips

  /**
   * 生成Excel的一行数据
   *
   * @param rowNum      行号
   * @param sheet       输出的具体sheet
   * @param style       样式
   * @param columnNames 具体有哪些字段
   */
  public static final void createSimpleRow(int rowNum, XSSFSheet sheet, XSSFCellStyle style,
      String... columnNames) {
    XSSFRow row = sheet.createRow(rowNum);
    if (columnNames != null && columnNames.length > 0) {
      for (int i = 0; i < columnNames.length; i++) {
        XSSFCell cell = row.createCell(i);
        cell.setCellStyle(style);
        cell.setCellValue(columnNames[i]);
      }
    }
  }

  /**
   * 从cell中获取值
   *
   * @param cell
   * @return
   * @author huwenwen
   * @since 2015年12月21日
   */
  public static final String getStringCellValue(XSSFCell cell) {
    switch (cell.getCellType()) {
      case XSSFCell.CELL_TYPE_STRING:
        return cell.getStringCellValue();
      case XSSFCell.CELL_TYPE_NUMERIC:
        return cell.getNumericCellValue() + "";
      case XSSFCell.CELL_TYPE_BOOLEAN:
        return cell.getBooleanCellValue() + "";
      case XSSFCell.CELL_TYPE_FORMULA:
        return cell.getCellFormula() + "";
      default:
    }
    return cell.getStringCellValue();
  }

  /**
   * 创建Excel默认style行
   *
   * @param rowNum
   * @param workbook
   * @param sheet
   * @param cells
   */
  public static final void createDefaultRow(int rowNum, XSSFWorkbook workbook, XSSFSheet sheet,
      Map<String, XSSFCellStyle> cellStyleMap, POICellBean... cells) {
    XSSFRow row = sheet.createRow(rowNum);
    if (cells != null && cells.length > 0) {
      for (int i = 0; i < cells.length; i++) {
        XSSFCell cell = row.createCell(i);
        Object value = cells[i].getValue();
        String cellStyleStr = CELLSTYLE_TYPE_STRING; // 默认为字符串类型
        if (value instanceof Boolean) {
          cell.setCellValue((boolean) value);
        } else if (value instanceof Double) {
          cell.setCellValue((double) value);
          cellStyleStr = CELLSTYLE_TYPE_NUMERICAL;
        } else if (value instanceof Float) {
          cell.setCellValue((float) value);
          cellStyleStr = CELLSTYLE_TYPE_NUMERICAL;
        } else if (value instanceof Integer) {
          cell.setCellValue((int) value);
          cellStyleStr = CELLSTYLE_TYPE_INTEGER;
        } else if (value instanceof Long) {
          cell.setCellValue((long) value);
          cellStyleStr = CELLSTYLE_TYPE_INTEGER;
        } else if (value instanceof Date) {
          cell.setCellValue((Date) value);
          cellStyleStr = CELLSTYLE_TYPE_DAY_AND_TIME;
        } else {
          cell.setCellValue((String) value);
        }
        XSSFCellStyle cellStyle = cellStyleMap.get(cellStyleStr);
        if (cellStyle == null) {
          cellStyle = getDefaultCellStyle(workbook, cellStyleStr);
          cellStyleMap.put(cellStyleStr, cellStyle);
        }
        cell.setCellStyle(cellStyle);
      }
    }
  }

  public static final XSSFCellStyle getDefaultCellStyle(XSSFWorkbook workbook,
      String cellStyleStr) {
    if (cellStyleStr.equals(CELLSTYLE_TYPE_INTEGER)) {
      return getDefaultIntegerStyle(workbook);
    } else if (cellStyleStr.equals(CELLSTYLE_TYPE_NUMERICAL)) {
      return getDefaultNumericalStyle(workbook);
    } else if (cellStyleStr.equals(CELLSTYLE_TYPE_DAY_AND_TIME)) {
      return getDefaultDayAndTimeStyle(workbook);
    } else {
      return getDefaultStringStyle(workbook);
    }

  }

  /**
   * 获得默认时间格式（不带日期）
   *
   * @param workbook
   * @return
   */
  public static final XSSFCellStyle getDefaultTimeStyle(XSSFWorkbook workbook) {
    XSSFCellStyle timeStyle = workbook.createCellStyle();
    XSSFDataFormat df = workbook.createDataFormat();
    timeStyle.setDataFormat(df.getFormat(DEFAULT_TIME_FORMAT));
    timeStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);
    return timeStyle;
  }

  /**
   * 获得默认日期格式（不带日期）
   *
   * @param workbook
   * @return
   */
  public static final XSSFCellStyle getDefaultDayStyle(XSSFWorkbook workbook) {
    XSSFCellStyle dayStyle = workbook.createCellStyle();
    XSSFDataFormat df = workbook.createDataFormat();
    dayStyle.setDataFormat(df.getFormat(DEFAULT_TIME_FORMAT));
    dayStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);
    return dayStyle;
  }

  /**
   * 获得默认日期+时间格式
   *
   * @param workbook
   * @return
   */
  public static final XSSFCellStyle getDefaultDayAndTimeStyle(XSSFWorkbook workbook) {
    XSSFCellStyle dayAndTimeStyle = workbook.createCellStyle();
    XSSFDataFormat df = workbook.createDataFormat();
    dayAndTimeStyle.setDataFormat(df.getFormat(DEFAULT_DAY_AND_TIME_FORMAT));
    dayAndTimeStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);
    return dayAndTimeStyle;
  }

  /**
   * 默认整数格式
   *
   * @param workbook
   * @return
   */
  public static final XSSFCellStyle getDefaultIntegerStyle(XSSFWorkbook workbook) {
    XSSFCellStyle dayAndTimeStyle = workbook.createCellStyle();
    XSSFDataFormat df = workbook.createDataFormat();
    dayAndTimeStyle.setDataFormat(df.getFormat("0"));
    dayAndTimeStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);
    return dayAndTimeStyle;
  }

  /**
   * 默认数字类型 默认保留两位小数
   *
   * @param workbook
   * @return
   */
  public static final XSSFCellStyle getDefaultNumericalStyle(XSSFWorkbook workbook) {
    XSSFCellStyle dayAndTimeStyle = workbook.createCellStyle();
    XSSFDataFormat df = workbook.createDataFormat();
    dayAndTimeStyle.setDataFormat(df.getFormat("0.00"));
    dayAndTimeStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);
    return dayAndTimeStyle;
  }

  /**
   * 默认字符串格式
   *
   * @param workbook
   * @return
   */
  public static final XSSFCellStyle getDefaultStringStyle(XSSFWorkbook workbook) {
    XSSFCellStyle stringStyle = workbook.createCellStyle();
    XSSFDataFormat df = workbook.createDataFormat();
    stringStyle.setDataFormat(df.getFormat("@"));
    stringStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);
    return stringStyle;
  }

  /**
   * Given a sheet, this method deletes a column from a sheet and moves
   * all the columns to the right of it to the left one cell.
   * <p>
   * Note, this method will not update any formula references.
   *
   * @param sheet
   * @param columnToDelete
   */
  public static void deleteColumn(Sheet sheet, int columnToDelete) {
    int maxColumn = 0;
    for (int r = 0; r < sheet.getLastRowNum() + 1; r++) {
      Row row = sheet.getRow(r);

      // if no row exists here; then nothing to do; next!
      if (row == null)
        continue;

      // if the row doesn't have this many columns then we are good; next!
      int lastColumn = row.getLastCellNum();
      if (lastColumn > maxColumn)
        maxColumn = lastColumn;

      if (lastColumn < columnToDelete)
        continue;

      for (int x = columnToDelete + 1; x < lastColumn + 1; x++) {
        Cell oldCell = row.getCell(x - 1);
        if (oldCell != null)
          row.removeCell(oldCell);

        Cell nextCell = row.getCell(x);
        if (nextCell != null) {
          Cell newCell = row.createCell(x - 1, nextCell.getCellType());
          cloneCell(newCell, nextCell);
        }
      }
    }


    // Adjust the column widths
    for (int c = 0; c < maxColumn; c++) {
      sheet.setColumnWidth(c, sheet.getColumnWidth(c + 1));
    }
  }

  /*
   * Takes an existing Cell and merges all the styles and forumla
   * into the new one
   */
  private static void cloneCell(Cell cNew, Cell cOld) {
    cNew.setCellComment(cOld.getCellComment());
    cNew.setCellStyle(cOld.getCellStyle());

    switch (cNew.getCellType()) {
      case Cell.CELL_TYPE_BOOLEAN: {
        cNew.setCellValue(cOld.getBooleanCellValue());
        break;
      }
      case Cell.CELL_TYPE_NUMERIC: {
        cNew.setCellValue(cOld.getNumericCellValue());
        break;
      }
      case Cell.CELL_TYPE_STRING: {
        cNew.setCellValue(cOld.getStringCellValue());
        break;
      }
      case Cell.CELL_TYPE_ERROR: {
        cNew.setCellValue(cOld.getErrorCellValue());
        break;
      }
      case Cell.CELL_TYPE_FORMULA: {
        cNew.setCellFormula(cOld.getCellFormula());
        break;
      }
    }

  }

  /**
   * 对sheet设置默认的列宽和行高
   *
   * @param sheet
   */
  public static final void setDefaultSheetRHCW(XSSFSheet sheet) {
    sheet.setDefaultRowHeight((short) 400);
    sheet.setDefaultColumnWidth(20);
  }

  /**
   * POI cell辅助内部类
   *
   * @author huwenwen
   */
  public static class POICellBean {

    private XSSFCellStyle cellStyle = null;
    private Object value;

    public POICellBean(Object value) {
      super();
      this.value = value;
    }

    public POICellBean(XSSFCellStyle cellStyle, Object value) {
      super();
      this.cellStyle = cellStyle;
      this.value = value;
    }

    public XSSFCellStyle getCellStyle() {
      return cellStyle;
    }

    public void setCellStyle(XSSFCellStyle cellStyle) {
      this.cellStyle = cellStyle;
    }

    public Object getValue() {
      return value;
    }

    public void setValue(Object value) {
      this.value = value;
    }

  }

}
