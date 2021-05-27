package com.rom.util.excel;


import java.io.FileInputStream;  
import java.io.FileNotFoundException;  
import java.io.FileOutputStream;  
import java.io.IOException;  
import java.io.InputStream;  
  
import org.apache.poi.hssf.usermodel.HSSFWorkbook;  
import org.apache.poi.ss.usermodel.Cell;  
import org.apache.poi.ss.usermodel.CellStyle;  
import org.apache.poi.ss.usermodel.Row;  
import org.apache.poi.ss.usermodel.Sheet;  
import org.apache.poi.ss.usermodel.Workbook;  
import org.apache.poi.ss.util.CellRangeAddress;  

public class PoiTestExcel {

	/** 
     * @param args 
     */  
    public static void main(String[] args) {  
        try {  
            InputStream in = new FileInputStream("D:\\1111.xls");  
            Workbook work = new HSSFWorkbook(in);  
            // 得到excel的第0张表  
            Sheet sheet = work.getSheetAt(0);  
            // 得到第1行的第一个单元格的样式  
            Row rowCellStyle = sheet.getRow(1);  
            CellStyle columnOne = rowCellStyle.getCell(0).getCellStyle();  
            // 这里面的行和列的数法与计算机里的一样，从0开始是第一  
            // 填充title数据  
            Row row = sheet.getRow(0);  
            Cell cell = row.getCell(0);  
            cell.setCellValue("2010年花名测");  
            int i = 2;//计数器  
            int number = 0;  
            // 得到行，并填充数据和表格样式  
            for (;i < 10; i++) {  
                row = sheet.createRow(i);// 得到行  
                cell = row.createCell(0);// 得到第0个单元格  
                cell.setCellValue("琳"+i);// 填充值  
                cell.setCellStyle(columnOne);// 填充样式  
                cell = row.createCell(1);  
                cell.setCellValue("女");  
                cell.setCellStyle(columnOne);// 填充样式  
                cell = row.createCell(2);  
                cell.setCellValue(i+20);  
                cell.setCellStyle(columnOne);// 填充样式  
                // .....给每个单元格填充数据和样式  
                number++;  
            }  
            //创建每个单元格，添加样式，最后合并  
            row = sheet.createRow(i);  
            cell = row.createCell(0);  
            cell.setCellValue("总计：" + number + "个学生");// 填充值  
            cell.setCellStyle(columnOne);  
            cell = row.createCell(1);  
            cell.setCellStyle(columnOne);  
            cell = row.createCell(2);  
            cell.setCellStyle(columnOne);  
            // 合并单元格  
            sheet.addMergedRegion(new CellRangeAddress(i,i,0,2));  
            FileOutputStream os = new FileOutputStream("D:\\report\\workbook.xls");  
            work.write(os);  
            os.close();  
        } catch (FileNotFoundException e) {  
            System.out.println("文件路径错误");  
            e.printStackTrace();  
        } catch (IOException e) {  
            System.out.println("文件输入流错误");  
            e.printStackTrace();  
        }  
  
    }  
  
}
