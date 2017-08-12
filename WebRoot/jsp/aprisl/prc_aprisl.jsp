<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript">
	function lookFormula(){
		window.open("${pageContext.request.contextPath}/jsp/aprisl/formula.jsp", "lookFormula");
	}

</script>
<h4>价格鉴定</h4>
<table>
	<tr>
		<th>扣除费用值（￥）：</th>
		<td>3500元</td>
	</tr>
	<tr>
		<th>市场平均价格：</th>
		<td><s:textfield cssClass="input1" id="" name="" />元</td>
	</tr>
	<tr>
		<th>基本公式：</th>
		<td><s:select cssClass="input1" id="aprislMthd" name="aprislDto.TAprislArtclsPrcAprisl.aprislMthd" 
				list="#{'001':'请选择',
				'002':'鉴定价格=重置完全价格×综合成新率×（1-变现折扣率）',
				'003':'鉴定价格=A/r×[1-1/(1+r)^n]',
				'004':'选择2名以上的专家或有一定代表性的名望的专业技术人员或市场销售人员',
				'005':'鉴定价格＝重置成本×综合成新率',
				'006':'鉴定价格＝重置成本×（1－实体性贬值率－功能性贬值率－经济性贬值率）',
				'007':'鉴定价格＝重置成本－实体性贬值－功能性贬值－经济性贬值',
				'008':'鉴定价格＝重置成本×（1－功能性贬值率）×（1－经济性贬值率）×成新率',
				'009':'鉴定价格=可比实例市场价格×交易情况修正系数×交易日期修正系数×区域因素修正系数×个别因素修正系数'}">
				</s:select>&nbsp;&nbsp;&nbsp;<input type="button" class="anniu" onclick="lookFormula()" value="查看附加公式"/>
		</td>
	</tr>
	<tr>
		<th>鉴定金额：</th>
		<td><s:textfield cssClass="input1" id="" name="" />元&nbsp;&nbsp;<input type="button" class="anniu" value="自动计算"/></td>
	</tr>
	<tr>
		<th colspan="1">备注：</th>
		<td colspan="3">
			<s:textarea id="" name="" cols="100" rows="5"  />
		</td>
	</tr>
	<tr>
		<th colspan="1">价格鉴定过程：</th>
		<td colspan="3">
			<s:textarea id="" name="" rows="5"cols="100" />
		</td>
	</tr>
</table>
<div align="center"><input type="button" class="anniu" value="自动生成鉴定过程"/></div>

	
