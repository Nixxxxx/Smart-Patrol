package com.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class PoiExcel {

	/**
	 * 导出到ecxel表格
	 * @param names  姓名
	 * @param notCompleteds  未完成任务数
	 * @param notOnTimes  未按时完成任务数
	 * @param onTimes  已完成任务书
	 * @param fullFileName
	 * @param start
	 * @param end
	 */
	public static void expExcel(List<String> names, List<String> notCompleteds, List<String> notOnTimes,
			List<String> onTimes, String fullFileName, String start, String end){
		String[] title = {"姓名","未完成","未按时完成","已完成"};
		//创建Excel工作簿
		@SuppressWarnings("resource")
		XSSFWorkbook workbook = new XSSFWorkbook();
		//创建一个工作表sheet
		Sheet sheet = workbook.createSheet();
		//创建第一行
		Row row = sheet.createRow(0);
		Cell cell = row.createCell(0);
		cell.setCellValue(start+" - "+end+"出勤任务统计");
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 3));  //合并单元格
		
		//插入第一行数据 
        row = sheet.createRow(1);
		for (int i = 0; i < title.length; i++) {
			cell = row.createCell(i);
			cell.setCellValue(title[i]);
		}
		//追加数据
		for (int i = 0; i < names.size(); i++) {
			Row nextrow = sheet.createRow(i+2);
			Cell cell2 = nextrow.createCell(0);
			cell2.setCellValue(names.get(i));
			cell2 = nextrow.createCell(1);
			cell2.setCellValue(notCompleteds.get(i));
			cell2 = nextrow.createCell(2);
			cell2.setCellValue(notOnTimes.get(i));
			cell2 = nextrow.createCell(3);
			cell2.setCellValue(onTimes.get(i));
		}
		//创建一个文件
		File file = new File(fullFileName);
		try {
			file.createNewFile();
			//将Excel内容存盘
			FileOutputStream stream = FileUtils.openOutputStream(file);
			workbook.write(stream);
			stream.close();
		} catch (IOException e) {
		e.printStackTrace();
		}
	}
	
	
	/**
	 * 将巡更任务信息写入文件，以便python检测发送微信信息
	 * @param msg  文件内容
	 */
	public static void sendWeChat(String msg){
		File file = new File("C:/xungen/xungen.txt");
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(file));
			bw.write(msg);
			bw.flush();
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
