<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE HTML>
<html>
	<head>
		
		<title>鉴定物品</title>
		<jsp:include page="/jsp/included/all_js.jsp"></jsp:include>
		<jsp:include page="/jsp/included/all_css.jsp"></jsp:include>
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/lrtk.css" />
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/right.css" />
	
		<script type="text/javascript">
		
	//添加
	var xuhao = 1;
	function addRow() {
		var trText2 = '<tr>';
		trText2 += '<td><input type="checkbox" id="" value="" name="boxes"/>';
		trText2 += '<td>' + document.getElementById("popname").value + '</td>';
		trText2 += '<td>' + document.getElementById("popdesic").value + '</td>';
		trText2 += '<td><a id="'
				+ xuhao
				+ '" style="text-decoration: underline;cursor: pointer;" onclick="readyupdate(this);">修改</a></td>';
		trText2 += '</tr>';
		$("#testTbl tbody").append(trText2);
		xuhao++;
		document.getElementById("popname").value="";
		document.getElementById("popdesic").value="";
	}
	//删除 
	function delRow() {

		var checkboxs = $("input[name='boxes']:checked");
		if (checkboxs == null || checkboxs.length == 0) {
			alert("请选择删除项！");
			return;
		}
		if (confirm("确认是否要删除！")) {

			$(checkboxs).each(function() {
				var id = $(this).val();//行号，为删除
				$(this).parent().parent().remove();
			});
		}
	}

	//修改
	function readyupdate(obj) {
		document.getElementById("popname").value = obj.parentElement.parentElement.cells[1].innerHTML;
		document.getElementById("popdesic").value = obj.parentElement.parentElement.cells[2].innerHTML;
		document.getElementById("updataButton").value = obj.id;
	}

	//确认修改
	function updateRow() {
		id = document.getElementById("updataButton").value;
		if(id==''){
			alert('请点击修改');
			return;
		};
		
		var obj = document.getElementById(id);
		obj.parentElement.parentElement.cells[1].innerHTML = document
				.getElementById("popname").value;
		obj.parentElement.parentElement.cells[2].innerHTML = document
				.getElementById("popdesic").value;
				
		document.getElementById("updataButton").value="";
		document.getElementById("popname").value="";
		document.getElementById("popdesic").value="";
	}
	
	//选择物品
	function selectcert(obj){
		//将每个物品都隐藏
		$("div[id^=cert_]").each(
			function(){
				document.getElementById($(this).attr('id')).style.display="none";
			}
		);
		//将指定物品显示
		var id="cert_"+obj.value;
		document.getElementById(id).style.display="";
		$("#testTbl tbody tr").remove();
	}
	
	//确定按钮
	function addCert(){
	//document.submitform.submit();
	alert('确定');
	}
	
</script>
	</head>

	<body>

<form id="submitform" name="submitform" action="${pageContext.request.contextPath}/login/toLogin.action" method="post">
		<div class="ykuang01">
			<h3>添加鉴定物品</h3>
			<div class="buttonk2">
				<input name="button2" type="button"" class="anniu" value="确定" onclick="addCert();" />
				<input name="button3" type="button" class="anniu" value="取消" />
			</div>
			<div class="buttonk2">
				鉴定物品：
				<s:select id="artclTp" name="artclTp" onchange="selectcert(this)"
				list="#{'001':'手机','002':'钱包','003':'自行车','004':'电子产品','005':'机动车','006':'机动车(灭失)','007':'首饰（黄金|铂金）','008':'珠|宝|玉|工艺','009':'日用百货','010':'工地建材','011':'市政设施','012':'假冒产品','013':'机器设备','014':'建筑物、土地','015':'其它'}" 
				></s:select>	
			</div>
		
			
			<!-- 手机-->
			<div id="cert_001" class="box3" >
				<p>手机</p>
				<table>
					<tr>
						<th>标的名称：</th>
						<td>
							<s:textfield id="qnty" name="aprislArtclsDto.qnty"  value="数量" size="6" />
							<s:textfield id="unit" name="aprislArtclsDto.unit"  value="单位" size="4" />
							<s:textfield id="brndNm" name="aprislArtclsDto.brndNm"  value="品牌" />牌
							<s:textfield id="specTp" name="aprislArtclsDto.specTp"  value="型号规格" />型号
							<s:textfield id="artclNm" name="aprislArtclsDto.artclNm"  value="物品名称" />
						</td>
						<th>IMIE型号：</th>
						<td><s:textfield id="mblImeiTp" name="aprislArtclsDto.mblImeiTp"  class="input1" /></td>
					</tr>
					<tr>
						<th>备注信息：</th>
						<td><s:textfield id="remark" name="aprislArtclsDto.remark"  class="input1" /></td>
						<th>市场价格(￥)：</th>
						<td><s:textfield id="orgnlPrc" name="aprislArtclsDto.orgnlPrc" class="input1" />元</td>
					</tr>
					<tr>
						<th>其它信息：</th>
						<td><s:textfield id="othInfo" name="aprislArtclsDto.othInfo"  class="input1" /></td>
						<th></th>
						<td></td>
					</tr>
					<tr>
						<th>入网许可证：</th>
						<td><s:textfield id="mblNtwkLcns" name="aprislArtclsDto.mblNtwkLcns" class="input1" /></td>
						<th><span style="color:red">*</span>价格鉴定基准日期：</th>
						<td>
							<s:textfield id="prcArtclRefDt" name="aprislArtclsDto.prcArtclRefDt"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd' , maxDate:'%yyyy-%MM-{%d}'})" cssClass="Wdate" />
						</td>
					</tr>
					<tr>
						<th>是否能够正常使用：</th>
						<td>
							<s:radio id="isNmlUse" name="aprislArtclsDto.isNmlUse" list="#{'1':'是','0':'否'}"></s:radio>
						</td>
						<th>成新率(1～100%)：</th>
						<td><s:textfield id="newRate" name="aprislArtclsDto.newRate" class="input1" />%</td>
					</tr>
					<tr>
						<th><span style="color:red">*</span>标的形态：</th>
						<td>
							1.是否灭失:
							<s:radio id="isLos" name="aprislArtclsDto.isLos" list="#{'1':'是','0':'否'}"></s:radio>
							<br>
							2.基准日形态是否发生重大改变:
							<s:radio id="isMjrChng" name="aprislArtclsDto.isMjrChng" list="#{'1':'是','0':'否'}"></s:radio>
						</td>
						<th>购置时间：</th>
						<td>
							<s:textfield id="buyTm" name="aprislArtclsDto.buyTm" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd' , maxDate:'%yyyy-%MM-{%d}'})"  cssClass="Wdate" />
						</td>
					</tr>
					<tr>
						<th colspan="1">鉴定价格(￥)：</th>
						<td colspan="3"><s:textfield id="aprislPrc" name="aprislArtclsDto.aprislPrc" class="input1" />元</td>
					</tr>
					<tr>
						<th colspan="1">价格鉴定备注：</th>
						<td colspan="3"><s:textarea id="prcArtclRmk" name="aprislArtclsDto.prcArtclRmk" rows="5" cols="50"></s:textarea></td>
					</tr>
				</table>
			</div>
	
	

	
	
	
			<!-- 钱包 其它 -->
			<div id="cert_002" class="box3" style="display: none;">
				<p>钱包 </p>
				<table>
					<tr>
						<th>标的名称：</th>
						<td>
							<s:textfield id="qnty" name="qnty"  value="数量" size="6" />
							<s:textfield id="unit" name="unit"  value="单位" size="4" />
							<s:textfield id="brndNm" name="brndNm"  value="品牌" />牌
							<s:textfield id="specTp" name="specTp"  value="型号规格" />型号
							<s:textfield id="artclNm" name="artclNm"  value="物品名称" />
						</td>
						<th>市场价格(￥)：</th>
						<td><s:textfield id="orgnlPrc" name="orgnlPrc" class="input1" />元</td>
					</tr>
					<tr>
						<th>备注信息：</th>
						<td><s:textfield id="remark" name="remark"  class="input1" /></td>
					</tr>
					<tr>
						<th>其它信息：</th>
						<td><s:textfield id="othInfo" name="othInfo"  class="input1" /></td>
						<th><span style="color:red">*</span>价格鉴定基准日期：</th>
						<td>
							<s:textfield id="prcArtclRefDt" name="prcArtclRefDt"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd' , maxDate:'%yyyy-%MM-{%d}'})"  cssClass="Wdate"/>
						</td>
					</tr>
					<tr>
						<th>是否能够正常使用：</th>
						<td>
						<s:radio id="isNmlUse" name="isNmlUse" list="#{'1':'是','0':'否'}"></s:radio>
						</td>
						<th>成新率(1～100%)：</th>
						<td><s:textfield id="newRate" name="newRate" class="input1" />%</td>
					</tr>
					<tr>
						<th><span style="color:red">*</span>标的形态：</th>
						<td>
							1.是否灭失:
							<s:radio id="isLos" name="isLos" list="#{'1':'是','0':'否'}"></s:radio>
							<br>
							2.基准日形态是否发生重大改变:
							<s:radio id="isMjrChng" name="isMjrChng" list="#{'1':'是','0':'否'}"></s:radio>
						</td>
						<th>购置时间：</th>
						<td>
							<s:textfield id="buyTm" name="buyTm" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd' , maxDate:'%yyyy-%MM-{%d}'})"  cssClass="Wdate" />
						</td>
					</tr>
					<tr>
						<th colspan="1">鉴定价格(￥)：</th>
						<td colspan="3"><s:textfield id="aprislPrc" name="aprislPrc" class="input1" />元</td>
					</tr>
					<tr>
						<th colspan="1">价格鉴定备注：</th>
						<td colspan="3"><s:textarea id="prcArtclRmk" name="prcArtclRmk" rows="5" cols="50"></s:textarea></td>
					</tr>
				</table>
			</div>
	
	

			
			<!-- 自行车 其它-->
			<div id="cert_003" class="box3" style="display: none;">
				<p>自行车</p>
				<table>
					<tr>
						<th>标的名称：</th>
						<td>
							<s:textfield id="qnty" name="qnty"  value="数量" size="6" />
							<s:textfield id="unit" name="unit"  value="单位" size="4" />
							<s:textfield id="brndNm" name="brndNm"  value="品牌" />牌
							<s:textfield id="specTp" name="specTp"  value="型号规格" />型号
							<s:textfield id="artclNm" name="artclNm"  value="物品名称" />
						</td>
						<th>市场价格(￥)：</th>
						<td><s:textfield id="orgnlPrc" name="orgnlPrc" class="input1" />元</td>
					</tr>
					<tr>
						<th>备注信息：</th>
						<td><s:textfield id="remark" name="remark"  class="input1" /></td>
					</tr>
					<tr>
						<th>其它信息：</th>
						<td><s:textfield id="othInfo" name="othInfo"  class="input1" /></td>
						<th><span style="color:red">*</span>价格鉴定基准日期：</th>
						<td>
							<s:textfield id="prcArtclRefDt" name="prcArtclRefDt"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd' , maxDate:'%yyyy-%MM-{%d}'})"  cssClass="Wdate"/>
						</td>
					</tr>
					<tr>
						<th>是否能够正常使用：</th>
						<td>
						<s:radio id="isNmlUse" name="isNmlUse" list="#{'1':'是','0':'否'}"></s:radio>
						</td>
						<th>成新率(1～100%)：</th>
						<td><s:textfield id="newRate" name="newRate" class="input1" />%</td>
					</tr>
					<tr>
						<th><span style="color:red">*</span>标的形态：</th>
						<td>
							1.是否灭失:
							<s:radio id="isLos" name="isLos" list="#{'1':'是','0':'否'}"></s:radio>
							<br>
							2.基准日形态是否发生重大改变:
							<s:radio id="isMjrChng" name="isMjrChng" list="#{'1':'是','0':'否'}"></s:radio>
						</td>
						<th>购置时间：</th>
						<td>
							<s:textfield id="buyTm" name="buyTm" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd' , maxDate:'%yyyy-%MM-{%d}'})"  cssClass="Wdate" />
						</td>
					</tr>
					<tr>
						<th colspan="1">鉴定价格(￥)：</th>
						<td colspan="3"><s:textfield id="aprislPrc" name="aprislPrc" class="input1" />元</td>
					</tr>
					<tr>
						<th colspan="1">价格鉴定备注：</th>
						<td colspan="3"><s:textarea id="prcArtclRmk" name="prcArtclRmk" rows="5" cols="50"></s:textarea></td>
					</tr>
				</table>
			</div>
			
			<!-- 电子产品 其它 -->
			<div id="cert_004" class="box3" style="display: none;">
				<p>电子产品</p>
				<table>
					<tr>
						<th>标的名称：</th>
						<td>
							<s:textfield id="qnty" name="qnty"  value="数量" size="6" />
							<s:textfield id="unit" name="unit"  value="单位" size="4" />
							<s:textfield id="brndNm" name="brndNm"  value="品牌" />牌
							<s:textfield id="specTp" name="specTp"  value="型号规格" />型号
							<s:textfield id="artclNm" name="artclNm"  value="物品名称" />
						</td>
						<th>市场价格(￥)：</th>
						<td><s:textfield id="orgnlPrc" name="orgnlPrc" class="input1" />元</td>
					</tr>
					<tr>
						<th>备注信息：</th>
						<td><s:textfield id="remark" name="remark"  class="input1" /></td>
						<th></th>
						<td></td>
					</tr>
					<tr>
						<th>其它信息：</th>
						<td><s:textfield id="othInfo" name="othInfo"  class="input1" /></td>
						<th><span style="color:red">*</span>价格鉴定基准日期：</th>
						<td>
							<s:textfield id="prcArtclRefDt" name="prcArtclRefDt"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd' , maxDate:'%yyyy-%MM-{%d}'})"  cssClass="Wdate"/>
						</td>
					</tr>
					<tr>
						<th>是否能够正常使用：</th>
						<td>
						<s:radio id="isNmlUse" name="isNmlUse" list="#{'1':'是','0':'否'}"></s:radio>
						</td>
						<th>成新率(1～100%)：</th>
						<td><s:textfield id="newRate" name="newRate" class="input1" />%</td>
					</tr>
					<tr>
						<th><span style="color:red">*</span>标的形态：</th>
						<td>
							1.是否灭失:
							<s:radio id="isLos" name="isLos" list="#{'1':'是','0':'否'}"></s:radio>
							<br>
							2.基准日形态是否发生重大改变:
							<s:radio id="isMjrChng" name="isMjrChng" list="#{'1':'是','0':'否'}"></s:radio>
						</td>
						<th>购置时间：</th>
						<td>
							<s:textfield id="buyTm" name="buyTm" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd' , maxDate:'%yyyy-%MM-{%d}'})"  cssClass="Wdate" />
						</td>
					</tr>
					<tr>
						<th colspan="1">鉴定价格(￥)：</th>
						<td colspan="3"><s:textfield id="aprislPrc" name="aprislPrc" class="input1" />元</td>
					</tr>
					<tr>
						<th colspan="1">价格鉴定备注：</th>
						<td colspan="3"><s:textarea id="prcArtclRmk" name="prcArtclRmk" rows="5" cols="50"></s:textarea></td>
					</tr>
				</table>
			</div>	
	
			<!-- 机动车 -->
			<div id="cert_005" class="box3" style="display: none;">
				<p>机动车</p>
				<table>
					<tr>
						<th>标的名称：</th>
						<td>
							<s:textfield id="qnty" name="qnty"  value="数量" size="6" />
							<s:textfield id="unit" name="unit"  value="单位" size="4" />
							<s:textfield id="brndNm" name="brndNm"  value="品牌" />牌
							<s:textfield id="specTp" name="specTp"  value="型号规格" />型号
							<s:textfield id="artclNm" name="artclNm"  value="物品名称" />
						</td>
						<th>车架号码：</th>
						<td><s:textfield id="vcFrmNm" name="vcFrmNm" class="input1" /></td>
					</tr>
					<tr>
						<th>车牌号码：</th>
						<td><s:textfield id="vcPlNm" name="vcPlNm" class="input1" /></td>
						<th>车身颜色：</th>
						<td><s:textfield id="vcCarClr" name="vcCarClr" class="input1" /></td>
					</tr>
					<tr>
						<th>发动机号码：</th>
						<td><s:textfield id="vcEgnNm" name="vcEgnNm" class="input1" /></td>
						<th>市场价格(￥)：</th>
						<td><s:textfield id="orgnlPrc" name="orgnlPrc" class="input1" />元</td>
					</tr>
					<tr>
						<th>初次登记日期：</th>
						<td><s:textfield id="vcInitRegDt" name="vcInitRegDt" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd' , maxDate:'%yyyy-%MM-{%d}'})"  cssClass="Wdate" />
						</td>
						<th></th>
						<td></td>
					</tr>
					<tr>
						<th>累计行程(表显)：</th>
						<td><s:textfield id="vcTotlTrip" name="vcTotlTrip" class="input1" />公里</td>
						<th>其它信息：</th>
						<td><s:textfield id="othInfo" name="othInfo"  class="input1" /></td>
					</tr>
					<tr>
						<th>使用性质：</th>
						<td>
							<s:radio id="vcUseNtr" name="vcUseNtr" list="#{'1':'营运','0':'非营运'}"></s:radio>
						</td>
						<th><span style="color:red">*</span>价格鉴定基准日期：</th>
						<td>
							<s:textfield id="prcArtclRefDt" name="prcArtclRefDt" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd' , maxDate:'%yyyy-%MM-{%d}'})"  cssClass="Wdate"/>
						</td>
					</tr>
					<tr>
						<th>购置时间：</th>
						<td>
							<s:textfield id="buyTm" name="buyTm" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd' , maxDate:'%yyyy-%MM-{%d}'})"  cssClass="Wdate" />
						</td>
						<th>成新率(1～100%)：</th>
						<td><s:textfield id="newRate" name="newRate" class="input1" />%</td>
					</tr>
					<tr>
						<th colspan="1">鉴定价格(￥)：</th>
						<td colspan="3"><s:textfield id="aprislPrc" name="aprislPrc" class="input1" />元</td>
					</tr>
					<tr>
						<th colspan="1">价格鉴定备注：</th>
						<td colspan="3"><s:textarea id="prcArtclRmk" name="prcArtclRmk" rows="5" cols="50"></s:textarea></td>
					</tr>
				</table>
			</div>	
			
			<!-- 机动车(灭失) -->
			<div id="cert_006" class="box3" style="display: none;">
				<p>机动车(灭失)</p>
				<table>
					<tr>
						<th>标的名称：</th>
						<td>
							<s:textfield id="qnty" name="qnty"  value="数量" size="6" />
							<s:textfield id="unit" name="unit"  value="单位" size="4" />
							<s:textfield id="brndNm" name="brndNm"  value="品牌" />牌
							<s:textfield id="specTp" name="specTp"  value="型号规格" />型号
							<s:textfield id="artclNm" name="artclNm"  value="物品名称" />
						</td>
						<th>车架号码：</th>
						<td><s:textfield id="vcFrmNm" name="vcFrmNm" class="input1" /></td>
					</tr>
					<tr>
						<th>车牌号码：</th>
						<td><s:textfield id="vcPlNm" name="vcPlNm" class="input1" /></td>
						<th>车身颜色：</th>
						<td><s:textfield id="vcCarClr" name="vcCarClr" class="input1" /></td>
					</tr>
					<tr>
						<th>发动机号码：</th>
						<td><s:textfield id="vcEgnNm" name="vcEgnNm" class="input1" /></td>
						<th>结构特征：</th>
						<td>
							<s:radio id="vcLosStruct" name="vcLosStruct" list="#{'1':'电喷','0':'化油器'}"></s:radio>
						</td>
					</tr>
					<tr>
						<th>燃料种类：</th>
						<td><s:textfield id="vcLosFuelKnd" name="vcLosFuelKnd" class="input1" /></td>
						<th>购置地点：</th>
						<td><s:textfield id="vcLosBuyAdrs" name="vcLosBuyAdrs" class="input1" /></td>
					</tr>
					<tr>
						<th>初次登记日期：</th>
						<td><s:textfield id="vcLosInitRegDt" name="vcLosInitRegDt" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd' , maxDate:'%yyyy-%MM-{%d}'})"  cssClass="Wdate" />
						</td>
						<th>缴费情况：</th>
						<td><s:textfield id="vcLosPymntStatn" name="vcLosPymntStatn" class="input1" /></td>
					</tr>
					<tr>
						<th>年检合格日期：</th>
						<td><s:textfield id="vcLosAnnPassDt" name="vcLosAnnPassDt" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd' , maxDate:'%yyyy-%MM-{%d}'})"  cssClass="Wdate" />
						</td>
						<th>市场价格(￥)：</th>
						<td><s:textfield id="orgnlPrc" name="orgnlPrc" class="input1" />元</td>
					</tr>
					<tr>
						<th>表的基准日状况：</th>
						<td><s:textfield id="vcLosArtclRefDtSt" name="vcLosArtclRefDtSt" class="input1" /></td>
					</tr>
					<tr>
						<th>累计行程(表显)：</th>
						<td><s:textfield id="vcTotlTrip" name="vcTotlTrip" class="input1" />公里</td>
						<th>其它信息：</th>
						<td><s:textfield id="othInfo" name="othInfo"  class="input1" /></td>
					</tr>
					<tr>
						<th>使用性质：</th>
						<td>
							<s:radio id="vcUseNtr" name="vcUseNtr" list="#{'1':'营运','0':'非营运'}"></s:radio>
						</td>
						<th><span style="color:red">*</span>价格鉴定基准日期：</th>
						<td>
							<s:textfield id="prcArtclRefDt" name="prcArtclRefDt" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd' , maxDate:'%yyyy-%MM-{%d}'})"  cssClass="Wdate"/>
						</td>
					</tr>
					<tr>
						<th>购置时间：</th>
						<td>
							<s:textfield id="buyTm" name="buyTm" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd' , maxDate:'%yyyy-%MM-{%d}'})"  cssClass="Wdate" />
						</td>
						<th>成新率(1～100%)：</th>
						<td><s:textfield id="newRate" name="newRate" class="input1" />%</td>
					</tr>
					<tr>
						<th colspan="1">鉴定价格(￥)：</th>
						<td colspan="3"><s:textfield id="aprislPrc" name="aprislPrc" class="input1" />元</td>
					</tr>
					<tr>
						<th colspan="1">价格鉴定备注：</th>
						<td colspan="3"><s:textarea id="prcArtclRmk" name="prcArtclRmk" rows="5" cols="50"></s:textarea></td>
					</tr>
				</table>
			</div>			
		
			
			<!-- 首饰（黄金|铂金）其它 -->
			<div id="cert_007" class="box3" style="display: none;">
				<p>首饰(黄金|铂金)</p>
				<table>
					<tr>
						<th>标的名称：</th>
						<td>
							<s:textfield id="qnty" name="qnty"  value="数量" size="6" />
							<s:textfield id="unit" name="unit"  value="单位" size="4" />
							<s:textfield id="brndNm" name="brndNm"  value="品牌" />牌
							<s:textfield id="specTp" name="specTp"  value="型号规格" />型号
							<s:textfield id="artclNm" name="artclNm"  value="物品名称" />
						</td>
						<th>市场价格(￥)：</th>
						<td><s:textfield id="orgnlPrc" name="orgnlPrc" class="input1" />元</td>
					</tr>
					<tr>
						<th>备注信息：</th>
						<td><s:textfield id="remark" name="remark"  class="input1" /></td>
					</tr>
					<tr>
						<th>其它信息：</th>
						<td><s:textfield id="othInfo" name="othInfo"  class="input1" /></td>
						<th><span style="color:red">*</span>价格鉴定基准日期：</th>
						<td>
							<s:textfield id="prcArtclRefDt" name="prcArtclRefDt"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd' , maxDate:'%yyyy-%MM-{%d}'})"  cssClass="Wdate"/>
						</td>
					</tr>
					<tr>
						<th>是否能够正常使用：</th>
						<td>
						<s:radio id="isNmlUse" name="isNmlUse" list="#{'1':'是','0':'否'}"></s:radio>
						</td>
						<th>成新率(1～100%)：</th>
						<td><s:textfield id="newRate" name="newRate" class="input1" />%</td>
					</tr>
					<tr>
						<th><span style="color:red">*</span>标的形态：</th>
						<td>
							1.是否灭失:
							<s:radio id="isLos" name="isLos" list="#{'1':'是','0':'否'}"></s:radio>
							<br>
							2.基准日形态是否发生重大改变:
							<s:radio id="isMjrChng" name="isMjrChng" list="#{'1':'是','0':'否'}"></s:radio>
						</td>
						<th>购置时间：</th>
						<td>
							<s:textfield id="buyTm" name="buyTm" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd' , maxDate:'%yyyy-%MM-{%d}'})"  cssClass="Wdate" />
						</td>
					</tr>
					<tr>
						<th colspan="1">鉴定价格(￥)：</th>
						<td colspan="3"><s:textfield id="aprislPrc" name="aprislPrc" class="input1" />元</td>
					</tr>
					<tr>
						<th colspan="1">价格鉴定备注：</th>
						<td colspan="3"><s:textarea id="prcArtclRmk" name="prcArtclRmk" rows="5" cols="50"></s:textarea></td>
					</tr>
				</table>
			</div>	
			
			
			<!-- 珠|宝|玉|工艺 -->
			<div id="cert_008" class="box3" style="display: none;" >
				<p>珠|宝|玉|工艺</p>
				<table>
					<tr>
						<th>标的名称：</th>
						<td>
							<s:textfield id="qnty" name="qnty"  value="数量" size="6" />
							<s:textfield id="unit" name="unit"  value="单位" size="4" />
							<s:textfield id="brndNm" name="brndNm"  value="品牌" />牌
							<s:textfield id="specTp" name="specTp"  value="型号规格" />型号
							<s:textfield id="artclNm" name="artclNm"  value="物品名称" />
						</td>
						<th>市场价格(￥)：</th>
						<td><s:textfield id="orgnlPrc" name="orgnlPrc" class="input1" />元</td>
					</tr>
					<tr>
						<th>外形：</th>
						<td><s:textfield id="jjcSap" name="jjcSap" class="input1" /></td>
						<th></th>
						<td></td>
					</tr>
					<tr>
						<th>尺寸：</th>
						<td><s:textfield id="jjcSize" name="jjcSize" class="input1" /></td>
						<th>重量：</th>
						<td><s:textfield id="jjcWght" name="jjcWght" class="input1" /></td>
					</tr>
					<tr>
						<th>颜色：</th>
						<td><s:textfield id="jjcClr" name="jjcClr" class="input1" /></td>
						<th>品质等级：</th>
						<td><s:textfield id="jjcGrd" name="jjcGrd" class="input1" /></td>
					</tr>
					<tr>
						<th>成色：</th>
						<td><s:textfield id="jjcFnns" name="jjcFnns" class="input1" /></td>
						<th>备注消息：</th>
						<td><s:textfield id="remark" name="remark"  class="input1" /></td>
					</tr>
					<tr>
						<th>证书/报告编号：</th>
						<td>
							<s:textfield id="jjcCertRprtNo" name="jjcCertRprtNo" class="input1" />
						</td>
						<th>其它信息：</th>
						<td><s:textfield id="othInfo" name="othInfo"  class="input1" /></td>
					</tr>
					<tr>
						<th>是否有质量、技术等检测、<br>鉴定证书（报告）：</th>
						<td>
							<s:radio id="jjcIsQltArtclCert" name="jjcIsQltArtclCert" list="#{'1':'是','0':'否'}"></s:radio>
						</td>
						<th><span style="color:red;">*</span>价格鉴定基准日期：</th>
						<td>
							<s:textfield id="prcArtclRefDt" name="prcArtclRefDt"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd' , maxDate:'%yyyy-%MM-{%d}'})"  cssClass="Wdate"/>
						</td>
					</tr>
					<tr>
						<th>成新率(1～100%)：</th>
						<td><s:textfield id="newRate" name="newRate" class="input1" />%</td>
						<th>购置时间：</th>
						<td>
							<s:textfield id="buyTm" name="buyTm" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd' , maxDate:'%yyyy-%MM-{%d}'})"  cssClass="Wdate" />
						</td>
					</tr>
					<tr>
						<th colspan="1"><span style="color:red;">*</span>标的形态：</th>
						<td colspan="3">
							1.是否灭失:
							<s:radio id="isLos" name="isLos" list="#{'1':'是','0':'否'}"></s:radio>
							<br>
							2.基准日形态是否发生重大改变:
							<s:radio id="isMjrChng" name="isMjrChng" list="#{'1':'是','0':'否'}"></s:radio>
						</td>
					</tr>
					<tr>
						<th colspan="1">鉴定价格(￥)：</th>
						<td colspan="3"><s:textfield id="aprislPrc" name="aprislPrc" class="input1" />元</td>
					</tr>
					<tr>
						<th colspan="1">价格鉴定备注：</th>
						<td colspan="3"><s:textarea id="prcArtclRmk" name="prcArtclRmk" rows="5" cols="50"></s:textarea></td>
					</tr>
				</table>
			</div>	
			
			<!-- 日用百货 其它 -->
			<div id="cert_009"  class="box3" style="display: none;">
				<p>日用百货</p>
				<table>
					<tr>
						<th>标的名称：</th>
						<td>
							<s:textfield id="qnty" name="qnty"  value="数量" size="6" />
							<s:textfield id="unit" name="unit"  value="单位" size="4" />
							<s:textfield id="brndNm" name="brndNm"  value="品牌" />牌
							<s:textfield id="specTp" name="specTp"  value="型号规格" />型号
							<s:textfield id="artclNm" name="artclNm"  value="物品名称" />
						</td>
						<th>市场价格(￥)：</th>
						<td><s:textfield id="orgnlPrc" name="orgnlPrc" class="input1" />元</td>
					</tr>
					<tr>
						<th>备注信息：</th>
						<td><s:textfield id="remark" name="remark"  class="input1" /></td>
					</tr>
					<tr>
						<th>其它信息：</th>
						<td><s:textfield id="othInfo" name="othInfo"  class="input1" /></td>
						<th><span style="color:red">*</span>价格鉴定基准日期：</th>
						<td>
							<s:textfield id="prcArtclRefDt" name="prcArtclRefDt"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd' , maxDate:'%yyyy-%MM-{%d}'})"  cssClass="Wdate"/>
						</td>
					</tr>
					<tr>
						<th>是否能够正常使用：</th>
						<td>
						<s:radio id="isNmlUse" name="isNmlUse" list="#{'1':'是','0':'否'}"></s:radio>
						</td>
						<th>成新率(1～100%)：</th>
						<td><s:textfield id="newRate" name="newRate" class="input1" />%</td>
					</tr>
					<tr>
						<th><span style="color:red">*</span>标的形态：</th>
						<td>
							1.是否灭失:
							<s:radio id="isLos" name="isLos" list="#{'1':'是','0':'否'}"></s:radio>
							<br>
							2.基准日形态是否发生重大改变:
							<s:radio id="isMjrChng" name="isMjrChng" list="#{'1':'是','0':'否'}"></s:radio>
						</td>
						<th>购置时间：</th>
						<td>
							<s:textfield id="buyTm" name="buyTm" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd' , maxDate:'%yyyy-%MM-{%d}'})"  cssClass="Wdate" />
						</td>
					</tr>
					<tr>
						<th colspan="1">鉴定价格(￥)：</th>
						<td colspan="3"><s:textfield id="aprislPrc" name="aprislPrc" class="input1" />元</td>
					</tr>
					<tr>
						<th colspan="1">价格鉴定备注：</th>
						<td colspan="3"><s:textarea id="prcArtclRmk" name="prcArtclRmk" rows="5" cols="50"></s:textarea></td>
					</tr>
				</table>
			</div>	
			
			<!-- 工地建材 其它 -->
			<div id="cert_010"  class="box3" style="display: none;">
				<p>工地建材</p>
				<table>
					<tr>
						<th>标的名称：</th>
						<td>
							<s:textfield id="qnty" name="qnty"  value="数量" size="6" />
							<s:textfield id="unit" name="unit"  value="单位" size="4" />
							<s:textfield id="brndNm" name="brndNm"  value="品牌" />牌
							<s:textfield id="specTp" name="specTp"  value="型号规格" />型号
							<s:textfield id="artclNm" name="artclNm"  value="物品名称" />
						</td>
						<th>市场价格(￥)：</th>
						<td><s:textfield id="orgnlPrc" name="orgnlPrc" class="input1" />元</td>
					</tr>
					<tr>
						<th>备注信息：</th>
						<td><s:textfield id="remark" name="remark"  class="input1" /></td>
					</tr>
					<tr>
						<th>其它信息：</th>
						<td><s:textfield id="othInfo" name="othInfo"  class="input1" /></td>
						<th><span style="color:red">*</span>价格鉴定基准日期：</th>
						<td>
							<s:textfield id="prcArtclRefDt" name="prcArtclRefDt"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd' , maxDate:'%yyyy-%MM-{%d}'})"  cssClass="Wdate"/>
						</td>
					</tr>
					<tr>
						<th>是否能够正常使用：</th>
						<td>
						<s:radio id="isNmlUse" name="isNmlUse" list="#{'1':'是','0':'否'}"></s:radio>
						</td>
						<th>成新率(1～100%)：</th>
						<td><s:textfield id="newRate" name="newRate" class="input1" />%</td>
					</tr>
					<tr>
						<th><span style="color:red">*</span>标的形态：</th>
						<td>
							1.是否灭失:
							<s:radio id="isLos" name="isLos" list="#{'1':'是','0':'否'}"></s:radio>
							<br>
							2.基准日形态是否发生重大改变:
							<s:radio id="isMjrChng" name="isMjrChng" list="#{'1':'是','0':'否'}"></s:radio>
						</td>
						<th>购置时间：</th>
						<td>
							<s:textfield id="buyTm" name="buyTm" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd' , maxDate:'%yyyy-%MM-{%d}'})"  cssClass="Wdate" />
						</td>
					</tr>
					<tr>
						<th colspan="1">鉴定价格(￥)：</th>
						<td colspan="3"><s:textfield id="aprislPrc" name="aprislPrc" class="input1" />元</td>
					</tr>
					<tr>
						<th colspan="1">价格鉴定备注：</th>
						<td colspan="3"><s:textarea id="prcArtclRmk" name="prcArtclRmk" rows="5" cols="50"></s:textarea></td>
					</tr>
				</table>
			</div>	
		
			
			<!-- 市政设施 其它 -->
			<div id="cert_011" class="box3" style="display: none;">
				<p>市政设施</p>
				<table>
					<tr>
						<th>标的名称：</th>
						<td>
							<s:textfield id="qnty" name="qnty"  value="数量" size="6" />
							<s:textfield id="unit" name="unit"  value="单位" size="4" />
							<s:textfield id="brndNm" name="brndNm"  value="品牌" />牌
							<s:textfield id="specTp" name="specTp"  value="型号规格" />型号
							<s:textfield id="artclNm" name="artclNm"  value="物品名称" />
						</td>
						<th>市场价格(￥)：</th>
						<td><s:textfield id="orgnlPrc" name="orgnlPrc" class="input1" />元</td>
					</tr>
					<tr>
						<th>备注信息：</th>
						<td><s:textfield id="remark" name="remark"  class="input1" /></td>
						<th></th>
						<td></td>
					</tr>
					<tr>
						<th>其它信息：</th>
						<td><s:textfield id="othInfo" name="othInfo"  class="input1" /></td>
						<th><span style="color:red">*</span>价格鉴定基准日期：</th>
						<td>
							<s:textfield id="prcArtclRefDt" name="prcArtclRefDt"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd' , maxDate:'%yyyy-%MM-{%d}'})"  cssClass="Wdate"/>
						</td>
					</tr>
					<tr>
						<th>是否能够正常使用：</th>
						<td>
						<s:radio id="isNmlUse" name="isNmlUse" list="#{'1':'是','0':'否'}"></s:radio>
						</td>
						<th>成新率(1～100%)：</th>
						<td><s:textfield id="newRate" name="newRate" class="input1" />%</td>
					</tr>
					<tr>
						<th><span style="color:red">*</span>标的形态：</th>
						<td>
							1.是否灭失:
							<s:radio id="isLos" name="isLos" list="#{'1':'是','0':'否'}"></s:radio>
							<br>
							2.基准日形态是否发生重大改变:
							<s:radio id="isMjrChng" name="isMjrChng" list="#{'1':'是','0':'否'}"></s:radio>
						</td>
						<th>购置时间：</th>
						<td>
							<s:textfield id="buyTm" name="buyTm" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd' , maxDate:'%yyyy-%MM-{%d}'})"  cssClass="Wdate" />
						</td>
					</tr>
					<tr>
						<th colspan="1">鉴定价格(￥)：</th>
						<td colspan="3"><s:textfield id="aprislPrc" name="aprislPrc" class="input1" />元</td>
					</tr>
					<tr>
						<th colspan="1">价格鉴定备注：</th>
						<td colspan="3"><s:textarea id="prcArtclRmk" name="prcArtclRmk" rows="5" cols="50"></s:textarea></td>
					</tr>
				</table>
			</div>	

			
			
			<!-- 假冒产品 其它 -->
			<div id="cert_012" class="box3" style="display: none;">
				<p>假冒产品</p>
				<table>
					<tr>
						<th>标的名称：</th>
						<td>
							<s:textfield id="qnty" name="qnty"  value="数量" size="6" />
							<s:textfield id="unit" name="unit"  value="单位" size="4" />
							<s:textfield id="brndNm" name="brndNm"  value="品牌" />牌
							<s:textfield id="specTp" name="specTp"  value="型号规格" />型号
							<s:textfield id="artclNm" name="artclNm"  value="物品名称" />
						</td>
						<th>市场价格(￥)：</th>
						<td><s:textfield id="orgnlPrc" name="orgnlPrc" class="input1" />元</td>
					</tr>
					<tr>
						<th>备注信息：</th>
						<td><s:textfield id="remark" name="remark"  class="input1" /></td>
						<th></th>
						<td></td>
					</tr>
					<tr>
						<th>其它信息：</th>
						<td><s:textfield id="othInfo" name="othInfo"  class="input1" /></td>
						<th><span style="color:red">*</span>价格鉴定基准日期：</th>
						<td>
							<s:textfield id="prcArtclRefDt" name="prcArtclRefDt"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd' , maxDate:'%yyyy-%MM-{%d}'})"  cssClass="Wdate"/>
						</td>
					</tr>
					<tr>
						<th>是否能够正常使用：</th>
						<td>
						<s:radio id="isNmlUse" name="isNmlUse" list="#{'1':'是','0':'否'}"></s:radio>
						</td>
						<th>成新率(1～100%)：</th>
						<td><s:textfield id="newRate" name="newRate" class="input1" />%</td>
					</tr>
					<tr>
						<th><span style="color:red">*</span>标的形态：</th>
						<td>
							1.是否灭失:
							<s:radio id="isLos" name="isLos" list="#{'1':'是','0':'否'}"></s:radio>
							<br>
							2.基准日形态是否发生重大改变:
							<s:radio id="isMjrChng" name="isMjrChng" list="#{'1':'是','0':'否'}"></s:radio>
						</td>
						<th>购置时间：</th>
						<td>
							<s:textfield id="buyTm" name="buyTm" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd' , maxDate:'%yyyy-%MM-{%d}'})"  cssClass="Wdate" />
						</td>
					</tr>
					<tr>
						<th colspan="1">鉴定价格(￥)：</th>
						<td colspan="3"><s:textfield id="aprislPrc" name="aprislPrc" class="input1" />元</td>
					</tr>
					<tr>
						<th colspan="1">价格鉴定备注：</th>
						<td colspan="3"><s:textarea id="prcArtclRmk" name="prcArtclRmk" rows="5" cols="50"></s:textarea></td>
					</tr>
				</table>
			</div>	


	
			<!-- 机器设备  -->
			<div id="cert_013" class="box3" style="display: none;">
				<p>机器设备</p>
				<table>
					<tr>
						<th>标的名称：</th>
						<td>
							<s:textfield id="qnty" name="qnty"  value="数量" size="6" />
							<s:textfield id="unit" name="unit"  value="单位" size="4" />
							<s:textfield id="brndNm" name="brndNm"  value="品牌" />牌
							<s:textfield id="specTp" name="specTp"  value="型号规格" />型号
							<s:textfield id="artclNm" name="artclNm"  value="物品名称" />
						</td>
						<th>市场价格(￥)：</th>
						<td><s:textfield id="orgnlPrc" name="orgnlPrc" class="input1" />元</td>
					</tr>
					<tr>
						<th>产地：</th>
						<td><s:textfield id="mfArea" name="mfArea" class="input1" /></td>
					</tr>
					<tr>
						<th>购置地点：</th>
						<td><s:textfield id="mfBuyAdd" name="mfBuyAdd" class="input1" /></td>
						<th>其它信息：</th>
						<td><s:textfield id="othInfo" name="othInfo" class="input1" /></td>
					</tr>
					<tr>
						<th>是否正在使用：</th>
						<td>
							<s:radio id="isNmlUse" name="isNmlUse" list="#{'1':'是','0':'否'}"></s:radio>
						</td>
						<th><span style="color:red">*</span>价格鉴定基准日期：</th>
						<td>
							<s:textfield id="prcArtclRefDt" name="prcArtclRefDt"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd' , maxDate:'%yyyy-%MM-{%d}'})"  cssClass="Wdate"/>
						</td>
					</tr>
					<tr>
						<th>成新率(1～100%)：</th>
						<td><s:textfield id="newRate" name="newRate" class="input1" />%</td>
						<th>购置时间：</th>
						<td>
							<s:textfield id="buyTm" name="buyTm" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd' , maxDate:'%yyyy-%MM-{%d}'})"  cssClass="Wdate" />
						</td>
					</tr>
					<tr>
						<th colspan="1"><span style="color:red">*</span>标的形态：</th>
						<td colspan="3">
							1.是否灭失:
							<s:radio id="isLos" name="isLos" list="#{'1':'是','0':'否'}"></s:radio>
							<br>
							2.基准日形态是否发生重大改变:
							<s:radio id="isMjrChng" name="isMjrChng" list="#{'1':'是','0':'否'}"></s:radio>
						</td>
					</tr>
					<tr>
						<th colspan="1">鉴定价格(￥)：</th>
						<td colspan="3"><s:textfield id="aprislPrc" name="aprislPrc" class="input1" />元</td>
					</tr>
					<tr>
						<th colspan="1">价格鉴定备注：</th>
						<td colspan="3"><s:textarea id="prcArtclRmk" name="prcArtclRmk" rows="5" cols="50"></s:textarea></td>
					</tr>
				</table>
			</div>	



			
			<!-- 建筑物、土地 -->
			<div id="cert_014" class="box3" style="display: none;">
				<p>建筑物、土地</p>
				<table>
					<tr>
						<th>标的名称：</th>
						<td>
							<s:textfield id="qnty" name="qnty"  value="数量" size="6" />
							<s:textfield id="unit" name="unit"  value="单位" size="4" />
							<s:textfield id="artclNm" name="artclNm"  value="物品名称" />
						</td>
						<th>权属证号：</th>
						<td><s:textfield id="blOwnrshpCertNo" name="blOwnrshpCertNo" class="input1" /></td>
					</tr>
					<tr>
						<th><span style="color:red">*</span>价格鉴定基准日期：</th>
						<td>
							<s:textfield id="prcArtclRefDt" name="prcArtclRefDt"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd' , maxDate:'%yyyy-%MM-{%d}'})"  cssClass="Wdate"/>
						</td>
						<th>购置时间：</th>
						<td>
							<s:textfield id="buyTm" name="buyTm" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd' , maxDate:'%yyyy-%MM-{%d}'})"  cssClass="Wdate" />
						</td>
					</tr>
					<tr>
						<th>其它信息：</th>
						<td><s:textfield id="othInfo" name="othInfo"  class="input1" /></td>
						<th>详细地址：</th>
						<td><s:textfield id="blAdrs" name="blAdrs" class="input1" /></td>
					</tr>
					<tr>
						<th colspan="1">鉴定价格(￥)：</th>
						<td colspan="3"><s:textfield id="aprislPrc" name="aprislPrc" class="input1" />元</td>
					</tr>
					<tr>
						<th colspan="1">价格鉴定备注：</th>
						<td colspan="3"><s:textarea id="prcArtclRmk" name="prcArtclRmk" rows="5" cols="50"></s:textarea></td>
					</tr>
				</table>
			</div>	
			
	
			
			<!-- 其它-->
			<div id="cert_015" class="box3" style="display: none;">
				<p>其它</p>
				<table>
					<tr>
						<th>标的名称：</th>
						<td>
							<s:textfield id="qnty" name="qnty"  value="数量" size="6" />
							<s:textfield id="unit" name="unit"  value="单位" size="4" />
							<s:textfield id="brndNm" name="brndNm"  value="品牌" />牌
							<s:textfield id="specTp" name="specTp"  value="型号规格" />型号
							<s:textfield id="artclNm" name="artclNm"  value="物品名称" />
						</td>
						<th>市场价格(￥)：</th>
						<td><s:textfield id="orgnlPrc" name="orgnlPrc" class="input1" />元</td>
					</tr>
					<tr>
						<th>备注信息：</th>
						<td><s:textfield id="remark" name="remark"  class="input1" /></td>
					</tr>
					<tr>
						<th>其它信息：</th>
						<td><s:textfield id="othInfo" name="othInfo"  class="input1" /></td>
						<th><span style="color:red">*</span>价格鉴定基准日期：</th>
						<td>
							<s:textfield id="prcArtclRefDt" name="prcArtclRefDt"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd' , maxDate:'%yyyy-%MM-{%d}'})"  cssClass="Wdate"/>
						</td>
					</tr>
					<tr>
						<th>是否能够正常使用：</th>
						<td>
						<s:radio id="isNmlUse" name="isNmlUse" list="#{'1':'是','0':'否'}"></s:radio>
						</td>
						<th>成新率(1～100%)：</th>
						<td><s:textfield id="newRate" name="newRate" class="input1" />%</td>
					</tr>
					<tr>
						<th><span style="color:red">*</span>标的形态：</th>
						<td>
							1.是否灭失:
							<s:radio id="isLos" name="isLos" list="#{'1':'是','0':'否'}"></s:radio>
							<br>
							2.基准日形态是否发生重大改变:
							<s:radio id="isMjrChng" name="isMjrChng" list="#{'1':'是','0':'否'}"></s:radio>
						</td>
						<th>购置时间：</th>
						<td>
							<s:textfield id="buyTm" name="buyTm" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd' , maxDate:'%yyyy-%MM-{%d}'})"  cssClass="Wdate" />
						</td>
					</tr>
					<tr>
						<th colspan="1">鉴定价格(￥)：</th>
						<td colspan="3"><s:textfield id="aprislPrc" name="aprislPrc" class="input1" />元</td>
					</tr>
					<tr>
						<th colspan="1">价格鉴定备注：</th>
						<td colspan="3"><s:textarea id="prcArtclRmk" name="prcArtclRmk" rows="5" cols="50"></s:textarea></td>
					</tr>
				</table>
			</div>
				
		
			
			<!-- 扣除费用 -->
			<div class="box3">
				<p>扣除费用</p>
				名称：<input type="text" id="popname" name="nm" class="input1" />
				描述：<input type="text" id="popdesic" name="remark" class="input1" />
				<input name="button1" class="anniu" type="button" value="添加" onclick="addRow()" />
				<input name="button2" class="anniu" type="button" value="修改"
					onclick="updateRow()" />
				<input type="hidden" id='updataButton' />
				<input name="button3" class="anniu" type="button" value="删除" onclick="delRow()" />
				<table id="testTbl">
					<thead><tr>
						<th width="5%">选择</th>
						<th width="15%">名称</th>
						<th width="65%">扣除费用描述</th>
						<th width="15%">操作</th>
						</tr>
					</thead>
					<tbody>
					</tbody>
				</table>
			</div>
		</div>

</form>

	</body>
</html>
