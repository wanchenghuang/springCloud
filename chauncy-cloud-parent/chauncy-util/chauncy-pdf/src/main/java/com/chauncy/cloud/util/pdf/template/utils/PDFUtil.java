// package com.chauncy.cloud.util.pdf.utils;
//
// import com.itextpdf.text.*;
// import com.itextpdf.text.pdf.BaseFont;
// import com.itextpdf.text.pdf.PdfPCell;
// import com.itextpdf.text.pdf.PdfPTable;
// import com.itextpdf.text.pdf.PdfWriter;
// import lombok.extern.log4j.Log4j;
//
// import java.awt.Font;
// import java.io.FileOutputStream;
// import java.io.IOException;
// import java.util.Iterator;
// import java.util.List;
// import java.util.Map;
// import java.util.Set;
// import java.util.concurrent.atomic.AtomicInteger;
//
// /**
//  * @Author: wanchenghuang
//  * @DateTime: 2020/9/8 11:50 上午
//  * @Version: 2.0.0
//  * @description: PDF工具类
//  **/
// @Log4j
// public class PDFUtil {
//
// 	private static Document document = new Document();// 建立一个Document对象
// 	private static int maxWidth = 520;
//
// 	private static Font headfont;// 设置字体大小
// 	private static Font keyfont;// 设置字体大小
// 	private static Font textfont;// 设置字体大小
//
// 	static {
// 		BaseFont bfChinese;
// 		try {
// 			bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
// 			headfont = new Font(bfChinese, 10, Font.BOLD);// 设置字体大小
// 			keyfont = new Font(bfChinese, 8, Font.BOLD);// 设置字体大小
// 			textfont = new Font(bfChinese, 8, Font.NORMAL);// 设置字体大小
// 		} catch (Exception e) {
// 			e.printStackTrace();
// 		}
// 	}
//
// 	/**
// 	 * 初始化文档
// 	 *
// 	 * @param fileUrl
// 	 */
// 	private static void initDocument(String fileUrl) {
// 		document.setPageSize(PageSize.A4);// 设置页面大小
// 		try {
// 			FileOutputStream fileOutputStream = new FileOutputStream(fileUrl);
// 			PdfWriter.getInstance(document, fileOutputStream)
// 					.setViewerPreferences(PdfWriter.PageModeUseThumbs);
// 			document.open();
// 		} catch (Exception e) {
// 			e.printStackTrace();
// 		}
// 	}
//
// 	/**
// 	 * 创建table
// 	 *
// 	 * @param colNumber
// 	 * @return
// 	 */
// 	public static PdfPTable createTable(int colNumber) {
// 		PdfPTable table = new PdfPTable(colNumber);
// 		try {
// 			table.setTotalWidth(maxWidth);
// 			table.setLockedWidth(true);
// 			table.setHorizontalAlignment(Element.ALIGN_CENTER);
// 			table.getDefaultCell().setBorder(1);
// 		} catch (Exception e) {
// 			e.printStackTrace();
// 		}
// 		return table;
// 	}
//
// 	/**
// 	 * 创建table
// 	 *
// 	 * @param widths
// 	 * @return
// 	 */
// 	public PdfPTable createTable(float[] widths) {
// 		PdfPTable table = new PdfPTable(widths);
// 		try {
// 			table.setTotalWidth(maxWidth);
// 			table.setLockedWidth(true);
// 			table.setHorizontalAlignment(Element.ALIGN_CENTER);
// 			table.getDefaultCell().setBorder(1);
// 		} catch (Exception e) {
// 			e.printStackTrace();
// 		}
// 		return table;
// 	}
//
// 	/**
// 	 * 创建 空table
// 	 *
// 	 * @return
// 	 */
// 	public static PdfPCell createBlankTable() {
// 		PdfPCell cell = new PdfPCell();
// 		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
// 		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
// 		Paragraph paragraph = new Paragraph("", getPdfChineseFont());
// 		cell.setPhrase(paragraph);
// 		return cell;
// 	}
//
// 	/**
// 	 * 创建列
// 	 *
// 	 * @param value
// 	 * @param font
// 	 * @param align
// 	 * @return
// 	 */
// 	public PdfPCell createCell(String value, Font font, int align) {
// 		PdfPCell cell = new PdfPCell();
// 		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
// 		cell.setHorizontalAlignment(align);
// 		Paragraph paragraph = new Paragraph(String.valueOf(value), getPdfChineseFont());
// 		cell.setPhrase(paragraph);
// 		return cell;
// 	}
//
// 	/**
// 	 * 创建列
// 	 *
// 	 * @param value
// 	 * @param font
// 	 * @return
// 	 */
// 	public static PdfPCell createHeadCell(String value, Font font) {
// 		PdfPCell cell = new PdfPCell();
// 		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
// 		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
// 		cell.setBackgroundColor(new BaseColor(22022022));
// 		cell.setFixedHeight(25.0f);
//
// 		Font headFont = getPdfChineseFont();
// 		headFont.setColor(new BaseColor(0xff0000));
// 		headFont.setSize(14);
// 		headFont.setStyle("bold");
//
// 		Paragraph paragraph = new Paragraph(String.valueOf(value), headFont);
// 		cell.setPhrase(paragraph);
// 		return cell;
// 	}
//
// 	/**
// 	 * 创建列
// 	 *
// 	 * @param value
// 	 * @param font
// 	 * @param rowSpan 占多列
// 	 * @param colspan 占多行
// 	 * @return
// 	 */
// 	public static PdfPCell createCell(String value, Font font, int rowSpan, int colspan) {
// 		PdfPCell cell = new PdfPCell();
// 		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
// 		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
// 		cell.setRowspan(rowSpan);
// 		cell.setColspan(colspan);
// 		Paragraph paragraph = new Paragraph(String.valueOf(value), getPdfChineseFont());
// 		cell.setPhrase(paragraph);
// 		return cell;
// 	}
//
// 	/**
// 	 * 创建列
// 	 *
// 	 * @param value
// 	 * @param font
// 	 * @param align
// 	 * @param colspan
// 	 * @param boderFlag
// 	 * @return
// 	 */
// 	public static PdfPCell createCell(String value, Font font, int align, int colspan, boolean boderFlag) {
// 		PdfPCell cell = new PdfPCell();
// 		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
// 		cell.setHorizontalAlignment(align);
// 		cell.setColspan(colspan);
// 		Paragraph paragraph = new Paragraph(String.valueOf(value), getPdfChineseFont());
// 		cell.setPhrase(paragraph);
// 		cell.setPadding(3.0f);
// 		if (!boderFlag) {
// 			cell.setBorder(0);
// 			cell.setPaddingTop(15.0f);
// 			cell.setPaddingBottom(8.0f);
// 		}
// 		return cell;
// 	}
//
// 	/**
// 	 * 设置字体
// 	 *
// 	 * @return
// 	 */
// 	public static Font getPdfChineseFont() {
// 		BaseFont bfChinese = null;
// 		try {
// 			bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
// 		} catch (DocumentException e) {
// 			e.printStackTrace();
// 		} catch (IOException e) {
// 			e.printStackTrace();
// 		}
// 		Font fontChinese = new Font(bfChinese, 12, Font.NORMAL);
// 		return fontChinese;
// 	}
//
//
// 	//创建pdf
// 	public static String createPDF(Map<String, Object> tittle, List<Object> listObj, Map<String, Object> pojectMap, String language) throws IOException, DocumentException {
// 		try{
// 			String fileUrl = "D:/temp.pdf";
// 			initDocument(fileUrl);
//
// 			PdfPTable table = createTable(tittle, listObj, pojectMap, language);
// 			document.add(table);
// 			System.out.println("文件创建成功: " + fileUrl);
// 			return fileUrl;
// 		}finally {
// 			document.close();
// 		}
//
// 	}
//
// 	/**
// 	 * 组装table
// 	 *
// 	 * @param tittle
// 	 * @param listObj
// 	 * @param pojectMap
// 	 * @param language
// 	 * @return
// 	 */
// 	public static PdfPTable createTable(Map<String, Object> tittle, List<Object> listObj, Map<String, Object> pojectMap, String language) {
//
// 		System.out.println("tittle" + tittle);
// 		System.out.println("listObj" + listObj);
//
// 		AtomicInteger index = new AtomicInteger(1);//记录下表数
// 		AtomicInteger allColsSize = new AtomicInteger(0);//记录总列数
// 		float width = 100;
// 		float height = 40;
// 		int tittleSize = tittle.size();
//
// 		PdfPTable table = createTable(tittleSize);
//
// 		//1 设置标题
// 		tittle.forEach((key, value) -> {
// 			PdfPCell cell = null; //表格的单元格
// 			if ("zh".equals(language)) {
// 				cell = createHeadCell(String.valueOf(value), textfont);
// 			} else {
// 				cell = createHeadCell(key, textfont);
// 			}
// 			table.addCell(cell);
// 			allColsSize.getAndIncrement();
// 		});
//
//
// 		//2 设置 内容
// 		listObj.forEach(obj -> {
// 			if (obj instanceof List) {
// 				List<Map> comptents = (List<Map>) obj;
// 				comptents.forEach(comptent -> {
//
// 					Object obj1 = comptent.get("parts");
// 					AtomicInteger rowSpan1 = new AtomicInteger(0);
// 					List<Map> parts = null;
//
// 					if (obj1 instanceof List && ((List) obj1).size()>0) {
// 						//rowSpan1 需要嵌套多层
// 						parts = (List<Map>) obj1;
// 						parts.forEach(part->{
// 							Object obj2 = part.get("items");
// 							if(obj2 instanceof List && ((List) obj2).size()>0){
// 								rowSpan1.addAndGet(((List) obj2).size());
// 							}else{
// 								rowSpan1.addAndGet(1);
// 							}
// 						});
// 					}else{
// 						rowSpan1.addAndGet(1);
// 					}
//
// 					//添加编号
// 					PdfPCell cellNo = createCell(String.valueOf(index.getAndIncrement()), textfont, rowSpan1.get(), 1);
// 					table.addCell(cellNo);
//
// 					//组件
// 					String component_name = String.valueOf(comptent.get("component_name"));
// 					PdfPCell cell1 = createCell(component_name, textfont, rowSpan1.get(), 1);
// 					table.addCell(cell1);
//
//
// 					if (parts != null) {
// 						//部件
// 						parts.forEach(part -> {
// 							Object obj2 = part.get("items");
// 							List<Map> items = null;
// 							int rowSpan2 = 1;
//
// 							if (obj2 instanceof List && ((List) obj2).size()>0) {
// 								items = (List<Map>) obj2;
// 								rowSpan2 = items.size();
// 							}
//
// 							//部件Custmization
// 							String part_name = String.valueOf(part.get("part_name"));
// 							PdfPCell cell2 = createCell(part_name, textfont, rowSpan2, 1);
// 							table.addCell(cell2);
//
// 							if (items != null) {
// 								//item
// 								items.forEach(item -> {
//
// 									//从标题的第三个key开始遍历titile,获取数据
// 									Set<String> keys = tittle.keySet();
// 									Iterator<String> iterator = keys.iterator();
// 									iterator.next();//排除第一个NO
// 									iterator.next();//排除第二个Components
// 									iterator.next();//排除第三个Items
// 									while (iterator.hasNext()) {
// 										String key = iterator.next();
// 										Object o = item.get(key);
//
// 										if (o != null) {
// 											PdfPCell cell3 = createCell(String.valueOf(o), textfont, 1, 1);
// 											table.addCell(cell3);
// 										} else {
// 											table.addCell(createBlankTable());
// 										}
// 									}
// 								});
// 							} else {
// 								//剩下的列没有数据填空
// 								for (int i = 0; i < allColsSize.get() - 3; i++) {
// 									table.addCell(createBlankTable());
// 								}
// 							}
//
// 						});
// 					} else {
// 						//剩下的列没有数据填空
// 						for (int i = 0; i < allColsSize.get() - 2; i++) {
// 							table.addCell(createBlankTable());
// 						}
// 					}
// 				});
// 			}
// 		});
//
// 		//3 设置消费者信息
//
//
// 		return table;
// 	}
// }
//
