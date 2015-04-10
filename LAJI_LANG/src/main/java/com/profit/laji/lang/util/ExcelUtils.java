package com.profit.laji.lang.util;

import org.apache.poi.ss.usermodel.Cell;

/**
 * execl辅助类
 * @author heyang
 * 2015-04-03
 */
public class ExcelUtils {
	
	/**
	 * xls格式的excel
	 */
	public static final String XLS = "xls";

	/**
	 * xlsx格式的excel
	 */
	public static final String XLSX = "xlsx";
	
	/**
	 * 判断文件是否为Excel
	 * 
	 * @param file
	 * @return
	 * @throws Exception
	 */
	public static boolean isExcel(String fileName) throws Exception {
		String extension = FileUtils.getExtension(fileName);
		if (XLS.equalsIgnoreCase(extension) || XLSX.equalsIgnoreCase(extension))
			return true;
		return false;
	}
	
	/**
	 * 获取单元格数据内容为字符串类型的数据
	 * @param cell
	 * @return
	 * @throws Exception
	 */
	public static String readCellVal(Cell cell) throws Exception{
		if (cell == null) return null;
		String result = null;
		switch (cell.getCellType()) {
		case Cell.CELL_TYPE_STRING:
			result = cell.getStringCellValue();
			break;
		case Cell.CELL_TYPE_NUMERIC:
			result = String.valueOf(cell.getNumericCellValue());
			break;
		case Cell.CELL_TYPE_BOOLEAN:
			result = String.valueOf(cell.getBooleanCellValue());
			break;
		case Cell.CELL_TYPE_BLANK:
			result = "";
			break;
		}
		return result;
	}
}
