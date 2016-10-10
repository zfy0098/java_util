package com.rom.util.pdf;

import java.io.FileNotFoundException;  
import java.io.FileOutputStream;
import java.io.IOException;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfWriter;


/**
 *    pdf 工具类
 * @author zfy
 *   需要jar 
 *   itext-2.1.2u.jar
 *   iTextAsian.jar
 *
 */


public class PDFUtil {

	public static void main(String[] args) {

		Document document = new Document(PageSize.A4);
		
		try {
			PdfWriter.getInstance(document, new FileOutputStream("d:/itext.pdf"));
			
			// 添加作者
			document.addAuthor("zfy");
			// 添加标题
			document.addTitle("java创建pdf");
			//  添加主题
			document.addSubject("第一个pdf程序");
			// 添加关键字
			document.addKeywords("itext");
			
			document.open();
			
			// 创建中文字体
			BaseFont bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H",   
		            BaseFont.NOT_EMBEDDED); 
			Font FontChinese = new Font(bfChinese, 12, Font.NORMAL);  
			
		
			
			
			
			document.add(new Paragraph("java创建pdf文件",FontChinese));
			document.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
