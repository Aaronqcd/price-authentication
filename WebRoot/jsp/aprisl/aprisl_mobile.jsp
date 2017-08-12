<!-- 手机 001-->

<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript">
	jQuery(document).ready(
			function() {
				$("#unit").addClass("validate[maxUtf8Size[50]]");//单位
				$("#brndNm").addClass("validate[maxUtf8Size[50]]");//品牌名称
				$("#specTp").addClass("validate[maxUtf8Size[50]]");//型号规格
				$("#orgnlPrc").addClass("validate[custom[bill_amnt]]");//市场价格
				$("#newRate").addClass("validate[min[1],max[100],custom[onlyNumberSp]]");//成新率
				$("#othInfo").addClass("validate[maxUtf8Size[500]]");//其它信息
				$("#prcArtclRmk").addClass("validate[maxUtf8Size[4000]]");//价格鉴定备注
				$("#remark").addClass("validate[maxUtf8Size[500]]");//备注
				$("#mblImeiTp").addClass("validate[maxUtf8Size[100]]");//手机_IMEI型号
				$("#mblNtwkLcns").addClass("validate[maxUtf8Size[100]]");//手机_入网许可证
				$("#vcPlNm").addClass("validate[maxUtf8Size[100]]");//机动车_车牌号
				$("#vcFrmNm").addClass("validate[maxUtf8Size[100]]");//机动车_车架号码
				$("#vcEgnNm").addClass("validate[maxUtf8Size[100]]");//机动车_发动机号码
				$("#vcCarClr").addClass("validate[maxUtf8Size[100]]");//机动车_车身颜色
				$("#vcTotlTrip").addClass("validate[custom[onlyNumberSp]]");//机动车_累计行程
				$("#vcLosFuelKnd").addClass("validate[maxUtf8Size[20]]");//机动车灭失_燃料种类
				$("#vcLosPymntStatn").addClass("validate[maxUtf8Size[200]]");//机动车灭失_缴费情况
				$("#vcLosBuyAdrs").addClass("validate[maxUtf8Size[200]]");//机动车灭失_购置地点
				$("#vcLosArtclRefDtSt").addClass("validate[maxUtf8Size[200]]");//机动车灭失_标的在基准日状况
				$("#jjcSap").addClass("validate[maxUtf8Size[100]]");//珠宝玉工艺品_外形
				$("#jjcSize").addClass("validate[maxUtf8Size[100]]");//珠宝玉工艺品_尺寸
				$("#jjcWght").addClass("validate[maxUtf8Size[100]]");//珠宝玉工艺品_重量
				$("#jjcClr").addClass("validate[maxUtf8Size[100]]");//珠宝玉工艺品_颜色
				$("#jjcGrd").addClass("validate[maxUtf8Size[100]]");//珠宝玉工艺品_等级
				$("#jjcFnns").addClass("validate[maxUtf8Size[100]]");//珠宝玉工艺品_成色
				$("#jjcCertRprtNo").addClass("validate[maxUtf8Size[100]]");//珠宝玉工艺品_证书报告编号
				$("#mfArea").addClass("validate[maxUtf8Size[100]]");//市政设施_产地
				$("#mfBuyAdd").addClass("validate[maxUtf8Size[100]]");//市政设施_购置地点
				$("#blOwnrshpCertNo ").addClass("validate[maxUtf8Size[100]]");//建筑物土地_权属证号
				$("#blAdrs").addClass("validate[maxUtf8Size[100]]");//建筑物土地_详细地址
				//必填的*
				$("#qnty").addClass("validate[required],validate[custom[quty_rate]");//数量
				$("#artclNm").addClass("validate[required],maxUtf8Size[50]");//物品名称
				$("#prcAprislMtd").addClass("validate[funcCall[checkPrcAprislMtd]]");//鉴定方法
				$("#prcArtclRefDt").addClass("validate[required]");//价格基准日
				$("#isLos").addClass("validate[required]");//是否灭失
				$("#isMjrChng").addClass("validate[required]");//基准日形态是否发生重大改变
				$("#aprislPrc").addClass("validate[custom[bill_amnt]");//鉴定价格
				jQuery("#aprislform").validationEngine();
				addonfous();
			});
</script>

<!-- 手机-->
<table>
	<tr>
		<th><span style="color:red">*</span>鉴定方法：</th>
		<td colspan="3"><s:select cssClass="input1" id="prcAprislMtd" name="aprislDto.prcAprislMtd" 
		list="#{'001':'请选择','002':'市场法','003':'成本法','004':'专家咨询法','005':'收益法','006':'清算法'}">
		</s:select></td>
	</tr>
	<tr>
		<th>标的名称：</th>
		<td colspan="3">
			<s:textfield id="qnty" name="aprislDto.qnty" size="6" /><span style="color: red">*</span>
			<s:textfield id="unit" name="aprislDto.unit"   size="4" />
			<s:textfield id="brndNm" name="aprislDto.brndNm" />牌
			<s:textfield id="specTp" name="aprislDto.specTp" />型号
			<s:textfield id="artclNm" name="aprislDto.artclNm" /><span style="color: red">*</span>
		</td>
	</tr>
	<tr>
		<th>备注信息：</th>
		<td>
			<s:textfield id="remark" name="aprislDto.remark" cssClass="input3" />
		</td>
		<th>IMIE号：</th>
		<td>
			<s:textfield id="mblImeiTp" name="aprislDto.mblImeiTp" cssClass="input1" />
		</td>
	</tr>
	<tr>
		<th>其它信息：</th>
		<td>
			<s:textfield id="othInfo" name="aprislDto.othInfo" type="text" cssClass="input3" />
		</td>
		<th style="width: 150px;">市场价格(￥)：</th>
		<td>
			<s:textfield id="orgnlPrc" name="aprislDto.orgnlPrc" cssClass="input1" />元
		</td>
	</tr>
	<tr>
		<th>入网许可证：</th>
		<td>
			<s:textfield id="mblNtwkLcns" name="aprislDto.mblNtwkLcns"
				cssClass="input1" />
		</td>
		<th></th>
		<td></td>
	</tr>
	<tr>
		<th style="width: 150px;">是否能够正常使用：</th>
		<td>
		<s:if test="null==aprislDto.isNmlUse">
			<s:radio value='1' id="isNmlUse" name="aprislDto.isNmlUse"
				list="#{'1':'是','0':'否'}"></s:radio>
		</s:if>
		<s:else>
			<s:radio id="isNmlUse" name="aprislDto.isNmlUse"
				list="#{'1':'是','0':'否'}"></s:radio>
		</s:else>
		</td>
		<th style="width: 150px;"><span style="color: red">*</span>价格鉴定基准日期：</th>
		<td>
			<input type="text" name="aprislDto.prcArtclRefDt" id="prcArtclRefDt"
				value="<s:date name="aprislDto.prcArtclRefDt" format="yyyy-MM-dd"/>"
				readonly="readonly" class="input1" />

			<img onclick="WdatePicker({el:'prcArtclRefDt',readOnly:true});"
				src="${pageContext.request.contextPath}/images/ril.png" width="20px" height="18px" border="0" style="margin-bottom:-5px;margin-left:-25px;" />
		</td>
	</tr>
	<tr>
		<th>标的形态：</th>
		<td>
			1.是否灭失:
			<s:if test="null==aprislDto.isLos">
			<s:radio value='1'  id="isLos" name="aprislDto.isLos" list="#{'1':'是','0':'否'}"></s:radio>
			</s:if>
			<s:else>
			<s:radio  id="isLos" name="aprislDto.isLos" list="#{'1':'是','0':'否'}"></s:radio>
			</s:else>
			<br>
			2.基准日形态是否发生重大改变:
			<s:if test="null==aprislDto.isMjrChng">
			<s:radio value='1' id="isMjrChng" name="aprislDto.isMjrChng"
				list="#{'1':'是','0':'否'}"></s:radio>
			</s:if>
			<s:else>
			<s:radio id="isMjrChng" name="aprislDto.isMjrChng"
				list="#{'1':'是','0':'否'}"></s:radio>
			</s:else>
		</td>
		<th style="width: 150px;">成新率(1～100%)：</th>
		<td>
			<s:textfield id="newRate" name="aprislDto.newRate" cssClass="input1" />%
		</td>
	</tr>
	<tr>
		<s:if test="aprislDto.TCommission.acptDt != null">
		<th style="width: 150px;">鉴定价格(￥)：</th>
		<td>
			<s:if test="aprislDto.aprislPrc > 0"><s:textfield id="aprislPrc" name="aprislDto.aprislPrc" cssClass="input1"/></s:if>
			<s:else><s:textfield id="aprislPrc" name="aprislDto.aprislPrc" cssClass="input1" value=""/></s:else> 元
			<input type="button" name="round" class="anniu2" value="四舍五入" onclick="javascript:sswr();"/></td>
		</s:if>
		<th>购置时间：</th>
		<td>
			<input type="text" name="aprislDto.buyTm" id="buyTm"
				value="<s:date name="aprislDto.buyTm" format="yyyy-MM-dd"/>"
				readonly="readonly" class="input1"/>
			<img onclick="WdatePicker({el:'buyTm',readOnly:true});"
				src="${pageContext.request.contextPath}/images/ril.png" width="20px" height="18px" border="0" style="margin-bottom:-5px;margin-left:-25px;" />
		</td>
	</tr>
	<s:if test="aprislDto.TCommission.acptDt != null">
	<tr>
		<th colspan="1" style="width: 150px;">价格鉴定备注：</th>
		<td colspan="3">
			<s:textarea id="prcArtclRmk" name="aprislDto.prcArtclRmk" rows="5"
				cols="135"></s:textarea>
		</td>
	</tr>
	</s:if>
</table>
