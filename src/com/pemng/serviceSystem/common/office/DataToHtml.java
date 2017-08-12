package com.pemng.serviceSystem.common.office;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Map;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class DataToHtml {

	private Configuration configuration = null;

	public DataToHtml() {

		configuration = new Configuration();

		configuration.setDefaultEncoding("utf-8");

	}

	/***************************************************************************
	 * 
	 * @param dataMap
	 *            java输出参数对象
	 * @param Dtype
	 *            打印类型 1:委托书,2：取件通知,3：送达回证，4：鉴定结论
	 * @param Ptype
	 *            鉴定类型：1：刑事: 2:行政执法，纪检监察，其他
	 * @param docid
	 *            文件名，主键命名
	 */

	public void createHtml(Map dataMap, int Dtype, int Ptype, String docid) {

		// 设置模本装置方法和路径,FreeMarker支持多种模板装载方法。可以重servlet，classpath，数据库装载，

		// 这里我们的模板是放在com.jen.core包下面

		configuration.setClassForTemplateLoading(this.getClass(),

		"/com/pemng/serviceSystem/common/office");

		Template t = null;

		try {

			// 装载模板

			if (Dtype == 1) {
				// 委托书
				t = configuration.getTemplate("mandate_html.ftl");
			} else if (Dtype == 2) {
				// 取件通知
				t = configuration.getTemplate("getnote_html.ftl");
			} else if (Dtype == 3) {
				// 送达回执
				t = configuration.getTemplate("sendback_html.ftl");
			} else if (Dtype == 4) {

				// 鉴定结论
				if (Ptype == 1) {
					// 1：刑事:
					t = configuration.getTemplate("criminal_html.ftl");
				} else {
					// 2:行政执法，纪检监察，其他
					t = configuration.getTemplate("nocriminal_html.ftl");
				}

			}else if (Dtype == 0) {
				// 送达回执
				t = configuration.getTemplate("11.ftl");
			}

			t.setEncoding("utf-8");

		} catch (IOException e) {

			e.printStackTrace();

		}

		File outFile = null;

		String docDir = null;
		
		// 输出文档路径及名称
		if (Dtype == 1) {
			// 委托书
			outFile = new File(GlobalData.aucate + docid + ".html");
			docDir = GlobalData.aucate ;

		} else if (Dtype == 2) {
			// 取件通知
			outFile = new File(GlobalData.getnote + docid + ".html");
			docDir = GlobalData.getnote ;
		} else if (Dtype == 3) {
			// 送达回执
			outFile = new File(GlobalData.sendback + docid + ".html");
			docDir = GlobalData.sendback ;
		} else if (Dtype == 4) {
			// 鉴定结论
			if (Ptype == 1) {
				// 1：刑事:
				outFile = new File(GlobalData.criminal + docid + ".html");
				docDir = GlobalData.criminal ;
			} else if (Ptype == 2) {
				// 2:行政执法，纪检监察，其他
				outFile = new File(GlobalData.nocriminal + docid + ".html");
				docDir = GlobalData.nocriminal ;
			}

		}else if(Dtype == 0) {
			// 委托书
			outFile = new File(GlobalData.nocriminal +"11.html");

		} 

		// File outFile = new File("D:/test.doc");

		Writer out = null;

		try {

			out = new BufferedWriter(new OutputStreamWriter(

			new FileOutputStream(outFile), "utf-8"));
			//System.out.println("-------------结束-----------");

		} catch (Exception e1) {

			e1.printStackTrace();

		}

		try {

			t.process(dataMap, out);

			out.close();

		} catch (TemplateException e) {

			e.printStackTrace();

		} catch (IOException e) {

			e.printStackTrace();

		}

	}

}
