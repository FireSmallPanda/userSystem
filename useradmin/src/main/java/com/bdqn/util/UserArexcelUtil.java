package com.bdqn.util;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.bdqn.entity.User;

import jxl.Workbook;
import jxl.format.Alignment;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

/**
 * 根据传入的集合将其转化成excel的工具类
 * 
 * @author 火焰小熊猫
 *
 */
public class UserArexcelUtil {
	public UserArexcelUtil(String pathStr) {
		this.pathStr = pathStr;
	}

	// 字典类
	private DictUtil dictUtil = new DictUtil();
	private String separator = File.separator; // 文件分隔符
	private String tableTitle = "员工信息";// 表格标题
	private String tableLab = "usertable";// 表格选项卡名
	private String[] tableClosName = { "用户ID", "用户名字", "用户账号", "用户邮箱", "用户状态" };// 列名(该列名必须与实体类参数相对应)
	private static WritableCellFormat lwh_value; // 表格数据样式
	private static WritableCellFormat lwh_value_left;
	private static WritableCellFormat lwh_key; // 表头样式
	private static WritableCellFormat lwh_name_left; // 表名样式
	private static WritableCellFormat lwh_name_right; // 表名样式
	private static WritableCellFormat lwh_name_center; // 表名样式
	private static WritableCellFormat lwh_title; // 页名称样式
	private static WritableCellFormat lwh_percent_float;

	private String pathStr; // 下载到的文件夹

	/**
	 * 生成excel文件并返回其路径
	 * 
	 * @param list
	 * @return
	 * @throws Exception
	 */
	public String genarateExcel(@SuppressWarnings("rawtypes") List list) throws Exception {
		/****** 定义表格格式start *****/
		WritableFont xm_key = new jxl.write.WritableFont(// 表格列名字体
				WritableFont.createFont("微软雅黑"), 12, WritableFont.BOLD);
		WritableFont xm_value = new jxl.write.WritableFont(WritableFont.createFont("微软雅黑"), 10, WritableFont.NO_BOLD);
		// 设置单元格样式
		lwh_value = new WritableCellFormat(xm_value); // 单元格字体样式
		lwh_value.setAlignment(jxl.format.Alignment.CENTRE); // 单元格水平对齐样式
		lwh_value.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE); // 单元格垂直对齐样式
		lwh_value.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN); // 单元格边框样式

		lwh_value_left = new WritableCellFormat(xm_value);
		lwh_value_left.setAlignment(jxl.format.Alignment.LEFT);
		lwh_value_left.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);
		lwh_value_left.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN);
		lwh_value_left.setWrap(true);

		lwh_key = new WritableCellFormat(xm_key);
		lwh_key.setAlignment(jxl.format.Alignment.CENTRE);
		lwh_key.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN);

		lwh_name_left = new WritableCellFormat(xm_key);
		lwh_name_left.setAlignment(Alignment.LEFT);
		lwh_name_left.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);

		lwh_name_right = new WritableCellFormat(xm_key);
		lwh_name_right.setAlignment(Alignment.LEFT);

		lwh_name_center = new WritableCellFormat(xm_key);
		lwh_name_center.setAlignment(Alignment.CENTRE);

		jxl.write.NumberFormat wf_percent_float = new jxl.write.NumberFormat("0.00"); // 定义单元浮点数据类型
		lwh_percent_float = new jxl.write.WritableCellFormat(xm_value, wf_percent_float);
		lwh_percent_float.setAlignment(jxl.format.Alignment.CENTRE);
		lwh_percent_float.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);
		lwh_percent_float.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN);

		WritableFont wf_title = new jxl.write.WritableFont(WritableFont.createFont("微软雅黑"), 24, WritableFont.NO_BOLD); // 定义标题样式
		lwh_title = new WritableCellFormat(wf_title);
		lwh_title.setAlignment(Alignment.CENTRE);
		lwh_title.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN);
		/****** 定义表格格式end *****/
		// 在指定的路径生成空白的xls文件
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
		File downFileDir = new File(pathStr);// 下载路径
		if (!downFileDir.exists() && !downFileDir.isDirectory()) {// 检测是下载路径是否存在
			// 若不存在则创建
			downFileDir.mkdir();
		}
		String filename = pathStr + separator + df.format(new Date()) + ".xls";
		File file = new File(filename);
		file.createNewFile();
		// 根据传输过来的list，定义excel表格的列数
		@SuppressWarnings("rawtypes")
		Iterator it = list.iterator();
		try {
			WritableWorkbook wb = Workbook.createWorkbook(file);
			// 设置Excel工作簿名称
			WritableSheet ws = wb.createSheet(tableLab, 0);
			int startRowNum = 0; // 起始行
			int startColNum = 0; // 起始列
			int maxColSize = tableClosName.length; // 最大列数
			for (int i = 0; i < tableClosName.length; i++) {

				ws.setColumnView(i, 30);// 添加列和设置列宽
			}
			ws.addCell(new Label(startColNum, startRowNum, tableTitle, lwh_title));
			ws.mergeCells(startColNum, startRowNum, startColNum + maxColSize - 1, startRowNum); // 合并单元格，合并(1,0)到(1,9)
			startColNum = 0;
			startRowNum++;
			// 第1行，绘制表头
			for (int i = 0; i < tableClosName.length; i++) {
				ws.addCell(new Label(startColNum, startRowNum, tableClosName[i], lwh_key));
				startColNum++;
			}
			// 将行数加1，列数重置为0
			startRowNum++;
			startColNum = 0;
			// 添加记录(此处需要手动添加)
			while (it.hasNext()) {
				User ar = (User) it.next();// 集合类型
				ws.addCell(new Label(startColNum, startRowNum, String.valueOf(ar.getUserId()), lwh_value));
				startColNum++;
				ws.addCell(new Label(startColNum, startRowNum, ar.getUserName(), lwh_value));
				startColNum++;
				ws.addCell(new Label(startColNum, startRowNum, ar.getUserAccount(), lwh_value));
				startColNum++;
				ws.addCell(new Label(startColNum, startRowNum, ar.getUserEmail(), lwh_value));
				startColNum++;
				ws.addCell(new Label(startColNum, startRowNum, String.valueOf(dictUtil.getDictByKey("100001", "100", ar.getUserStatus().toString()).getDictContentCN()), lwh_value));
				startColNum++;
				// 将行数加1，列数重置为0
				startRowNum++;
				startColNum = 0;
			}
			wb.write(); // 生成Excel工作簿
			wb.close();
			// 成功并返回文件路径
			return filename;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
