package basicFramework;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class Execution {
	
	public static void main(String[] args) throws Exception {
		HashMap<String, String> testExecutionMap = GetTestExecutionData.getData();
		
		for (String testCase : testExecutionMap.keySet()) {
			String executionStatus = testExecutionMap.get(testCase);
			if(executionStatus.contains("run")) {
				HashMap<Integer, List<String>> map  = GetDataFromExcel.getData(testCase);
				for (Integer eachvalue : map.keySet()) {
					List<String> list = map.get(eachvalue);
					
					System.out.println(list.get(0));
					
					try {
						Thread.sleep(1000);
						UpdateExcelResult.update(eachvalue, testCase, "Done");
						KeywordLibrary.controller(list.get(0), list.get(1), list.get(2), list.get(3), list.get(4), list.get(5));
					}
					catch(Exception e) {
						e.printStackTrace();
						System.out.println(e.toString());
						UpdateExcelResult.update(eachvalue, testCase, "Fail");
					}
				}
			} else {
				UpdateExcelResult.update(0, testCase, "Skipped");
			}
		}
}
}


class GetDataFromExcel{
	
	public static HashMap<Integer, List<String>> getData(String sheetName) throws Exception{
		HashMap<Integer, List<String>> map = new HashMap<>();
		String pathname = "C:\\Users\\FATEEMA\\Desktop\\Framework.xls";
		File file = new File(pathname);
		FileInputStream stream = new FileInputStream(file);
		HSSFWorkbook workbook = new HSSFWorkbook(stream);
		HSSFSheet sheet = workbook.getSheet(sheetName);
		int row_num = sheet.getLastRowNum();
		for (int i = 0; i <= row_num; i++) {
			ArrayList<String> excel_data = new ArrayList<>();
			HSSFRow row = sheet.getRow(i);
			int last_cell = row.getLastCellNum();
			for (int j = 0; j < last_cell; j++) {
				HSSFCell cell = row.getCell(j);
				if ( cell.getCellType() ==  cell.CELL_TYPE_NUMERIC) {
					excel_data.add(String.valueOf(cell.getNumericCellValue()));
				} else {
					excel_data.add(cell.getStringCellValue());
				}
			}
			map.put(i, excel_data);
		}
		System.out.println(map);
		return map;
	}
}

class UpdateExcelResult{
	
	public static void update(int rowNumber, String testCase, String result) throws Exception {
		String path_name = "C:\\Users\\FATEEMA\\Desktop\\Framework.xls";
		File file = new File(path_name);
		FileInputStream stream = new FileInputStream(file);
		HSSFWorkbook workbook = new HSSFWorkbook(stream);
		HSSFSheet sheet = workbook.getSheet(testCase);
		HSSFRow row;
		HSSFCell cell;
		if (! result.contains("Skipped")) {
			row = sheet.getRow(rowNumber);
			//int lastCell = row.getLastCellNum();
			cell = row.createCell(6);
			cell.setCellValue(result);
		} else {
			for(int i = 0; i <= sheet.getLastRowNum(); i++) {
				row = sheet.getRow(i);
				cell = row.createCell(6);
				cell.setCellValue(result);
			}
		}
		FileOutputStream outstream = new FileOutputStream(file);
		workbook.write(outstream);
	}
}

class GetTestExecutionData  {
	
	public static HashMap<String, String> getData() throws Exception{
		HashMap<String, String> map = new LinkedHashMap<>();
		String pathname = "C:\\Users\\FATEEMA\\Desktop\\Framework.xls";
		File file = new File(pathname);
		FileInputStream stream = new FileInputStream(file);
		HSSFWorkbook workbook = new HSSFWorkbook(stream);
		HSSFSheet sheet = workbook.getSheet("TestCaseDetails");
		HSSFRow row;
		HSSFCell cell;
		for(int i = 0; i <= sheet.getLastRowNum(); i++) {
			row = sheet.getRow(i);
			cell = row.getCell(0);
			String key = cell.getStringCellValue();
			cell = row.getCell(1);
			String value = cell.getStringCellValue();
			map.put(key, value);
		}
			System.out.println(map);
			return map;
		}
	
}