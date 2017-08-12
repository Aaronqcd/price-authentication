package com.pemng.serviceSystem.common.office;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pemng.serviceSystem.common.office.pojo.obinfo;

public class test {

	/***************************************************************************
	 * 
	 * @param Dtype
	 *            打印类型 1:委托书,2：取件通知,3：送达回证，4：鉴定结论
	 * @param Ptype
	 *             鉴定类型：1：刑事: 2:行政执法，纪检监察，其他
	 */
	public static void main(String[] args) {

		int Ptype = 1;
		int Dtype = 1;
		
		Map dataMap = new HashMap();
		test ts= new test();
		
//		ts.getSendbackData(dataMap);//送达回证
//		ts.getnoteData(dataMap,2);//取件通知
//		ts.getMandateData(dataMap);//委托书
//		ts.getcriData(dataMap);//刑事结论书
//		ts.getnocriData(dataMap);
		ts.get11Data(dataMap);
		/** 委托书* */
		DataToDoc aa = new DataToDoc();
		aa.createDoc(dataMap, 0, 2, "112233");

	}
	
	// 11实例
	private void get11Data(Map dataMap) {

		dataMap.put("code", "123456");

		dataMap.put("name", "张三");
		

	}

	// 送达回执实例  3 
	private void getSendbackData(Map dataMap) {

		dataMap.put("writing", "123456");

		dataMap.put("consignor", "张三");

		dataMap.put("senddate", CM.formatDate("yyyy年MM月dd日"));

		dataMap.put("sysdate", CM.formatDate("yyyy年MM月dd日HH时mm分"));

	}

	// 取件通知实例
	private void getnoteData(Map dataMap, int Ptype) {

		dataMap.put("doc_code", "123456");

		dataMap.put("org_name", "海淀公安局");
		if (Ptype == 2) {
			dataMap.put("type_name", "价格复核裁定委托书");
		} else {
			dataMap.put("type_name", "价格鉴定委托书");
		}

		dataMap.put("getdate", CM.formatDate(7, "yyyy年MM月dd日"));

		dataMap.put("rec_name", "张三");
		dataMap.put("rec_tel", "13726516276");

		dataMap.put("sysdate", CM.formatDate("yyyy年MM月dd日"));
		Ptype = 2;

	}

	// 委托书数据 	1
	private void getMandateData(Map dataMap) {

		// 送达回执
		dataMap.put("doccode", "123456");
		dataMap.put("pcontent", "123456");
		dataMap.put("sysdate", CM.formatDate("yyyy年MM月dd日"));
		dataMap.put("wname", "还定公安局");
		dataMap.put("wperson", "张三");
		dataMap.put("wtel", "138123456");
		dataMap.put("wadd", "淮海路123号");
		dataMap.put("wzip", "201231");

		List obitems =  new  ArrayList();
		
		for (int i=0;i<5;i++){
			obinfo t = new obinfo();
			t.setBrm("测试1");
			t.setBuydate(CM.formatDate("yyyy年MM月dd日"));
			t.setIsuse("是");
			t.setObname("名字");
			t.setOtremark("其他备注");
			t.setParts("辅料");
			t.setStm("测试型号a");
			t.setYprice("30");
			obitems.add(t);
		}
		dataMap.put("items", obitems);

	}
	
	// 刑事结论书实例
	private void getcriData(Map dataMap) {

		dataMap.put("doc_code", "123456");//文案号
		dataMap.put("dsysdate", "二○一x年x月x日");//当前时间格式化成 二○一x年x月x日
		dataMap.put("p_name", "还定公安局");//单位名称
		dataMap.put("p_context", "张三");//标的物名称 	如类别多无法使用统称，则使用“XX（第一项物品）等一批物品”概括描述。
		dataMap.put("obj_name", "张三");//标的物名字， 标的过多则使用价格鉴定清单。格式为“详见价格鉴定清单“
		dataMap.put("basedate", "2013年10月12日");//价格基准日  格式2013年10月12日
		dataMap.put("gist", "爱上大声点撒旦");//价格鉴定依据
		dataMap.put("pmethod", "市场法");//价格鉴定方法
		dataMap.put("pprocess", "爱上大声大声道");//价格鉴定过程
		
		dataMap.put("mprice", "");
//		dataMap.put("mprice", "两万三仟四佰捌拾两元整（￥ 23482）");//格式 x万x仟x佰x拾x元整 （￥x,xxx）
		dataMap.put("sysdate", CM.formatDate("yyyy年MM月dd日"));//价格鉴定作业日期
		dataMap.put("person", "张三");
		dataMap.put("p_acce", "");
		dataMap.put("dsysdate", "二○一x年x月x");
		
		//构造标的物list数据
		List obitems =  new  ArrayList();
		
		for (int i=0;i<5;i++){
			obinfo t = new obinfo();
			t.setAmount(40);//数量
			t.setUnit("件");//单位
			t.setYprice("20");//单价
			t.setObname("红英公司牌针织衫");//名称		
			
			obitems.add(t);
		}
		
		
		if(obitems.size()<=1){
			dataMap.put("mprice", "两万三仟四佰捌拾两元整（￥ 23482）");//格式 x万x仟x佰x拾x元整 （￥x,xxx）
			dataMap.put("p_acce", "北京市涉案财产价格鉴定机构资质证书。");
			
		}else{
			StringBuffer bp = new StringBuffer();
			StringBuffer bf = new StringBuffer();
			bp.append("两万三仟四佰捌拾两元整（￥ 23482）  其中：");
			bf.append("1.价格鉴定清单 <w:br />");
			for(int i=0;i<obitems.size();i++){
				obinfo t = (obinfo)obitems.get(i);
				
				bp.append("<w:br />"+t.getObname()+" ¥ "+t.getAmount()*Double.parseDouble(t.getYprice()));
				bf.append(t.getAmount()+t.getUnit()+t.getObname()+":¥ "+t.getAmount()*Double.parseDouble(t.getYprice())+"<w:br />");
			}			
			bf.append("2.北京市涉案财产价格鉴定机构资质证书。");
			
			dataMap.put("mprice", bp.toString());
			dataMap.put("p_acce", bf.toString());
		}

	}
	
	// 非刑事结论书实例
	private void getnocriData(Map dataMap) {

		dataMap.put("doc_code", "123456");//文案号
		dataMap.put("dsysdate", "二○一x年x月x日");//当前时间格式化成 二○一x年x月x日
		dataMap.put("p_name", "还定公安局");//单位名称
		dataMap.put("p_context", "张三");//标的物名称 	如类别多无法使用统称，则使用“XX（第一项物品）等一批物品”概括描述。
		dataMap.put("obj_name", "张三");//标的物名字， 标的过多则使用价格鉴定清单。格式为“详见价格鉴定清单“
		dataMap.put("basedate", "2013年10月12日");//价格基准日  格式2013年10月12日
		dataMap.put("gist", "爱上大声点撒旦");//价格鉴定依据
		dataMap.put("pmethod", "市场法");//价格鉴定方法
		dataMap.put("pprocess", "爱上大声大声道");//价格鉴定过程
		
		dataMap.put("mprice", "");
//		dataMap.put("mprice", "两万三仟四佰捌拾两元整（￥ 23482）");//格式 x万x仟x佰x拾x元整 （￥x,xxx）
		dataMap.put("sysdate", CM.formatDate("yyyy年MM月dd日"));//价格鉴定作业日期
		dataMap.put("person", "张三");
//		dataMap.put("p_acce", "");
		dataMap.put("dsysdate", "二○一x年x月x");
		
		//构造标的物list数据
		List obitems =  new  ArrayList();
		
		for (int i=0;i<5;i++){
			obinfo t = new obinfo();
			t.setAmount(40);//数量
			t.setUnit("件");//单位
			t.setYprice("20");//单价
			t.setObname("红英公司牌针织衫");//名称		
			
			obitems.add(t);
		}
		
		
		if(obitems.size()<=1){
			dataMap.put("mprice", "两万三仟四佰捌拾两元整（￥ 23482）");//格式 x万x仟x佰x拾x元整 （￥x,xxx）
//			dataMap.put("p_acce", "北京市涉案财产价格鉴定机构资质证书。");
			
		}else{
			StringBuffer bp = new StringBuffer();
//			StringBuffer bf = new StringBuffer();
			bp.append("两万三仟四佰捌拾两元整（￥ 23482）  其中：");
//			bf.append("1.价格鉴定清单 <w:br />");
			for(int i=0;i<obitems.size();i++){
				obinfo t = (obinfo)obitems.get(i);
				
				bp.append("<w:br />"+t.getObname()+" ¥ "+t.getAmount()*Double.parseDouble(t.getYprice()));
//				bf.append(t.getAmount()+t.getUnit()+t.getObname()+":¥ "+t.getAmount()*Double.parseDouble(t.getYprice())+"<w:br />");
			}			
//			bf.append("2.北京市涉案财产价格鉴定机构资质证书。");
			
			dataMap.put("mprice", bp.toString());
//			dataMap.put("p_acce", bf.toString());
		}

	}

}
