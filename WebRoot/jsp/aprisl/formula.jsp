<%@ page language="java" import="java.util.*" pageEncoding="utf-8"
	contentType="text/html; charset=utf-8"%>
<!DOCTYPE HTML>
<html>
	<head>
		<title>附加公式</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<jsp:include page="/jsp/included/all_js.jsp"></jsp:include>
		<jsp:include page="/jsp/included/all_css.jsp"></jsp:include>
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/gsjscss/reset-min.css">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/gsjscss/ext-all.css">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/gsjscss/ext-helper.css">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/gsjscss/z-all.css">
        <script type="text/javascript" src="${pageContext.request.contextPath}/css/gsjscss/ext-base.js">
        </script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/css/gsjscss/ext-all.js">
        </script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/css/gsjscss/z-all.js">
        </script>
        <!--引入日期选择组件-->
        <script src="${pageContext.request.contextPath}/css/gsjscss/WdatePicker.js" type="text/javascript">
        </script><link href="${pageContext.request.contextPath}/css/gsjscss/WdatePicker.css" rel="stylesheet" type="text/css">
        <!--引入分页组件-->
        <script type="text/javascript" src="${pageContext.request.contextPath}/css/gsjscss/pagelist.js">
        </script>
        <!--业务CSS/JS-->
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/gsjscss/General.css">
        <script type="text/javascript" src="${pageContext.request.contextPath}/css/gsjscss/base.js">
        </script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/css/gsjscss/bg_gs1.js">
        </script>

    </head>

	<body class="  ext-webkit ext-chrome">
		<div
			style="position: absolute; z-index: 19700; top: -1970px; left: -1970px; display: none;">
			<iframe style="width: 186px; height: 198px;"
				src="/css/gsjscss/My97DatePicker.htm" frameborder="0" border="0"
				scrolling="no"></iframe>
		</div>
		<div class="Container">
			<table class="page">
				<tbody>
					<tr class="cont">
						<td valign="top">
							<!--cont区 开始-->
							<div class="Content_Area1">
								<div class="List_content">
									<div class="Section_title">
										<ul>
											<li>
												<b>机器设备</b>
											</li>
										</ul>
									</div>
									<table width="100%">
										<tbody>
											<tr>
												<td width="10%" rowspan="2" align="right" bgcolor="#2c5568">
													<span class="STYLE1"><font size="2"> <b>公式1</b>
													</font>： </span>
												</td>
												<td align="left" bgcolor="#ededed">
													机器设备价格 = 重置成本 - 实体性贬值 - 功能性贬值 - 经济性贬值
												</td>
											</tr>
											<tr>
												<td align="left" bgcolor="#ededed">
													<font color="red"> * </font> 重置成本：
													<input id="gs1_input1" type="text"
														style="border: 1px solid #B6D7ED; padding: 3px; width: 50px; color: #666666;"
														value="0">
													元 &nbsp;&nbsp;
													<font color="red"> * </font> 实体性贬值：
													<input id="gs1_input2" type="text"
														style="border: 1px solid #B6D7ED; padding: 3px; width: 50px; color: #666666;"
														value="0">
													元 &nbsp;&nbsp;
													<font color="red"> * </font> 功能性贬值：
													<input id="gs1_input3" type="text"
														style="border: 1px solid #B6D7ED; padding: 3px; width: 50px; color: #666666;"
														value="0">
													元 &nbsp;&nbsp;
													<font color="red"> * </font> 经济性贬值：
													<input id="gs1_input4" type="text"
														style="border: 1px solid #B6D7ED; padding: 3px; width: 50px; color: #666666;"
														value="0">
													元 &nbsp;&nbsp;
													<font style="font-weight: bold"> 鉴定金额： </font>
													<input id="gs1_result" type="text"
														style="border: 1px solid #B6D7ED; padding: 3px; width: 50px; color: red; text-align: center; font-weight: bold;"
														value="">
													元
													<input type="button" value="自动计算" style="margin-left: 15px"
														onclick="fnGs1(this)">
												</td>
											</tr>
											<tr>
												<td rowspan="2" align="right" bgcolor="#2c5568">
													<span class="STYLE1"><font size="2"> <b>公式2</b>
													</font>： </span>
												</td>
												<td align="left" bgcolor="#ededed">
													RC1 = RC2 * (A1/A2)^X &nbsp;&nbsp;&nbsp;&nbsp; (
													<b>RC1:</b>鉴证标的重置成本&nbsp;&nbsp;
													<b>RC2:</b>参照物重置成本&nbsp;&nbsp;
													<b>A1:</b>鉴证标的的生产能力&nbsp;&nbsp;
													<b>A2:</b>参照物的生产能力&nbsp;&nbsp;
													<b>X:</b>规模经济效益指数&nbsp;&nbsp; )
												</td>
											</tr>
											<tr>
												<td align="left" bgcolor="#ededed">
													<font color="red"> * </font> RC2：
													<input id="gs2_input1" type="text"
														style="border: 1px solid #B6D7ED; padding: 3px; width: 50px; color: #666666;"
														value="0">
													元 &nbsp;&nbsp;
													<font color="red"> * </font> A1：
													<input id="gs2_input2" type="text"
														style="border: 1px solid #B6D7ED; padding: 3px; width: 50px; color: #666666;"
														value="0">
													元 &nbsp;&nbsp;
													<font color="red"> * </font> A2：
													<input id="gs2_input3" type="text"
														style="border: 1px solid #B6D7ED; padding: 3px; width: 50px; color: #666666;"
														value="0">
													元 &nbsp;&nbsp;
													<font color="red"> * </font> X：
													<input id="gs2_input4" type="text"
														style="border: 1px solid #B6D7ED; padding: 3px; width: 50px; color: #666666;"
														value="0">
													(小数，例：0.65) &nbsp;&nbsp;
													<font style="font-weight: bold"> RC1： </font>
													<input id="gs2_result" type="text"
														style="border: 1px solid #B6D7ED; padding: 3px; width: 50px; color: red; text-align: center; font-weight: bold;"
														value="">
													元
													<input type="button" value="自动计算" style="margin-left: 15px"
														onclick="fnGs2(this)">
												</td>
											</tr>
											<tr>
												<td rowspan="2" align="right" bgcolor="#2c5568">
													<span class="STYLE1"><font size="2"> <b>公式3</b>
													</font>： </span>
												</td>
												<td align="left" bgcolor="#ededed">
													重置成本 = 历史成本 * (基准日定基价格指数 / 设备购买日定基价格指数) = 历史成本 *
													设备购买日到基准日环比价格指数
												</td>
											</tr>
											<tr>
												<td align="left" bgcolor="#ededed">
													<font color="red"> * </font> 历史成本：
													<input id="gs3_input1" type="text"
														style="border: 1px solid #B6D7ED; padding: 3px; width: 50px; color: #666666;"
														value="0">
													元 &nbsp;&nbsp;
													<font color="red"> * </font> 基准日定基价格指数：
													<input id="gs3_input2" type="text"
														style="border: 1px solid #B6D7ED; padding: 3px; width: 50px; color: #666666;"
														value="0">
													% &nbsp;&nbsp;
													<font color="red"> * </font> 设备购买日定基价格指数：
													<input id="gs3_input3" type="text"
														style="border: 1px solid #B6D7ED; padding: 3px; width: 50px; color: #666666;"
														value="0">
													% &nbsp;&nbsp;
													<font style="font-weight: bold"> 重置成本： </font>
													<input id="gs3_result" type="text"
														style="border: 1px solid #B6D7ED; padding: 3px; width: 50px; color: red; text-align: center; font-weight: bold;"
														value="">
													元
													<input type="button" value="自动计算" style="margin-left: 15px"
														onclick="fnGs3(this)">
												</td>
											</tr>
										</tbody>
									</table>
									<div class="Section_title">
										<ul>
											<li>
												<b>交通车损</b>
											</li>
										</ul>
									</div>
									<table width="100%">
										<tbody>
											<tr>
												<td width="10%" rowspan="2" align="right" bgcolor="#2c5568">
													<span class="STYLE1"><font size="2"> <b>公式4</b>
													</font>： </span>
												</td>
												<td width="93%" align="left" bgcolor="#ededed">
													鉴证金额 = 车辆重置成本 - 实体性贬值 - 功能性贬值 - 经济性贬值 - 残值
												</td>
											</tr>
											<tr>
												<td align="left" bgcolor="#ededed" style="line-height: 35px">
													<font color="red"> * </font> 车辆重置成本：
													<input id="gs4_input1" type="text"
														style="border: 1px solid #B6D7ED; padding: 3px; width: 50px; color: #666666;"
														value="0">
													元 &nbsp;&nbsp;
													<font color="red"> * </font> 实体性贬值：
													<input id="gs4_input2" type="text"
														style="border: 1px solid #B6D7ED; padding: 3px; width: 50px; color: #666666;"
														value="0">
													元 &nbsp;&nbsp;
													<font color="red"> * </font> 功能性贬值：
													<input id="gs4_input3" type="text"
														style="border: 1px solid #B6D7ED; padding: 3px; width: 50px; color: #666666;"
														value="0">
													元 &nbsp;&nbsp;
													<br>
													<font color="red"> * </font> 经济性贬值：
													<input id="gs4_input4" type="text"
														style="border: 1px solid #B6D7ED; padding: 3px; width: 50px; color: #666666;"
														value="0">
													元 &nbsp;&nbsp;
													<font color="red"> * </font> 残值：
													<input id="gs4_input5" type="text"
														style="border: 1px solid #B6D7ED; padding: 3px; width: 50px; color: #666666;"
														value="0">
													元 &nbsp;&nbsp;
													<font style="font-weight: bold"> 鉴定金额： </font>
													<input id="gs4_result" type="text"
														style="border: 1px solid #B6D7ED; padding: 3px; width: 50px; color: red; text-align: center; font-weight: bold;"
														value="">
													元
													<input type="button" value="自动计算" style="margin-left: 15px"
														onclick="fnGs4(this)">
												</td>
											</tr>
										</tbody>
									</table>

									<div class="Section_title">
										<ul>
											<li>
												<b>流动资产</b>
											</li>
										</ul>
									</div>
									<table width="100%">
										<tbody>
											<tr>
													<td width="10%" rowspan="2" align="right" bgcolor="#2c5568">
													<span class="STYLE1"><font size="2"> <b>公式5</b>
													</font>： </span>
												</td>
												</td>
												<td align="left" bgcolor="#ededed">
													在用低值易耗品鉴证值 = 全新成本价值 * 成新率
												</td>
											</tr>
											<tr>
												<td align="left" bgcolor="#ededed">
													<font color="red"> * </font> 全新成本价值：
													<input id="gs5_input1" type="text"
														style="border: 1px solid #B6D7ED; padding: 3px; width: 50px; color: #666666;"
														value="0">
													元 &nbsp;&nbsp;
													<font color="red"> * </font> 成新率：
													<input id="gs5_input2" type="text"
														style="border: 1px solid #B6D7ED; padding: 3px; width: 50px; color: #666666;"
														value="0">
													% &nbsp;&nbsp;
													<font style="font-weight: bold"> 鉴定金额： </font>
													<input id="gs5_result" type="text"
														style="border: 1px solid #B6D7ED; padding: 3px; width: 50px; color: red; text-align: center; font-weight: bold;"
														value="">
													元
													<input type="button" value="自动计算" style="margin-left: 15px"
														onclick="fnGs5(this)">
												</td>
											</tr>
											<tr>
												<td rowspan="2" align="right" bgcolor="#2c5568">
													<span class="STYLE1"><font size="2"> <b>公式6</b>
													</font>： </span>
												</td>
												<td align="left" bgcolor="#ededed">
													成新率 = (1 - 低值易耗品实际使用月数 / 低值易耗品可使用总月数) * 100%
												</td>
											</tr>
											<tr>
												<td align="left" bgcolor="#ededed">
													<font color="red"> * </font> 低值易耗品实际使用月数：
													<input id="gs6_input1" type="text"
														style="border: 1px solid #B6D7ED; padding: 3px; width: 50px; color: #666666;"
														value="0">
													月 &nbsp;&nbsp;
													<font color="red"> * </font> 低值易耗品可使用总月数：
													<input id="gs6_input2" type="text"
														style="border: 1px solid #B6D7ED; padding: 3px; width: 50px; color: #666666;"
														value="0">
													月 &nbsp;&nbsp;
													<font style="font-weight: bold"> 成新率： </font>
													<input id="gs6_result" type="text"
														style="border: 1px solid #B6D7ED; padding: 3px; width: 50px; color: red; text-align: center; font-weight: bold;"
														value="">
													%
													<input type="button" value="自动计算" style="margin-left: 15px"
														onclick="fnGs6(this)">
												</td>
											</tr>
											<tr>
												<td rowspan="2" align="right" bgcolor="#2c5568">
													<span class="STYLE1"><font size="2"> <b>公式7</b>
													</font>： </span>
												</td>
												<td align="left" bgcolor="#ededed">
													在制品价格鉴证值 = 产成品重置成本 * (在制品药当量 或 在制品完工率)
												</td>
											</tr>
											<tr>
												<td align="left" bgcolor="#ededed">
													<font color="red"> * </font> 产成品重置成本：
													<input id="gs7_input1" type="text"
														style="border: 1px solid #B6D7ED; padding: 3px; width: 50px; color: #666666;"
														value="0">
													元 &nbsp;&nbsp;
													<font color="red"> * </font> 在制品药当量 或 在制品完工率：
													<input id="gs7_input2" type="text"
														style="border: 1px solid #B6D7ED; padding: 3px; width: 50px; color: #666666;"
														value="0">
													% &nbsp;&nbsp;
													<font style="font-weight: bold"> 鉴证金额： </font>
													<input id="gs7_result" type="text"
														style="border: 1px solid #B6D7ED; padding: 3px; width: 50px; color: red; text-align: center; font-weight: bold;"
														value="">
													元
													<input type="button" value="自动计算" style="margin-left: 15px"
														onclick="fnGs7(this)">
												</td>
											</tr>
											<tr>
												<td rowspan="2" align="right" bgcolor="#2c5568">
													<span class="STYLE1"><font size="2"> <b>公式8</b>
													</font>： </span>
												</td>
												<td align="left" bgcolor="#ededed">
													报废在制品价格鉴证值 = 可回收废料重量 * 价格鉴证基准日单位重量的回收价格
												</td>
											</tr>
											<tr>
												<td align="left" bgcolor="#ededed">
													<font color="red"> * </font> 可回收废料重量：
													<input id="gs8_input1" type="text"
														style="border: 1px solid #B6D7ED; padding: 3px; width: 50px; color: #666666;"
														value="0">
													千克 &nbsp;&nbsp;
													<font color="red"> * </font> 价格鉴证基准日单位重量的回收价格：
													<input id="gs8_input2" type="text"
														style="border: 1px solid #B6D7ED; padding: 3px; width: 50px; color: #666666;"
														value="0">
													元/千克 &nbsp;&nbsp;
													<font style="font-weight: bold"> 鉴证金额： </font>
													<input id="gs8_result" type="text"
														style="border: 1px solid #B6D7ED; padding: 3px; width: 50px; color: red; text-align: center; font-weight: bold;"
														value="">
													元
													<input type="button" value="自动计算" style="margin-left: 15px"
														onclick="fnGs8(this)">
												</td>
											</tr>
											<tr>
												<td rowspan="2" align="right" bgcolor="#2c5568">
													<span class="STYLE1"><font size="2"> <b>公式9</b>
													</font>： </span>
												</td>
												<td align="left" bgcolor="#ededed">
													应收票据价格鉴证值 = 本金 * (1 + 利息率 * 时间)
												</td>
											</tr>
											<tr>
												<td align="left" bgcolor="#ededed">
													<font color="red"> * </font> 本金：
													<input id="gs9_input1" type="text"
														style="border: 1px solid #B6D7ED; padding: 3px; width: 50px; color: #666666;"
														value="0">
													元 &nbsp;&nbsp;
													<font color="red"> * </font> 利息率：
													<input id="gs9_input2" type="text"
														style="border: 1px solid #B6D7ED; padding: 3px; width: 50px; color: #666666;"
														value="0">
													% &nbsp;&nbsp;
													<font color="red"> * </font> 时间：
													<input id="gs9_input3" type="text"
														style="border: 1px solid #B6D7ED; padding: 3px; width: 50px; color: #666666;"
														value="0">
													个月
													<font style="font-weight: bold"> 鉴证金额： </font>
													<input id="gs9_result" type="text"
														style="border: 1px solid #B6D7ED; padding: 3px; width: 50px; color: red; text-align: center; font-weight: bold;"
														value="">
													元
													<input type="button" value="自动计算" style="margin-left: 15px"
														onclick="fnGs9(this)">
												</td>
											</tr>
										</tbody>
									</table>
									<div class="Section_title">
										<ul>
											<li>
												<b>长期投资</b>
											</li>
										</ul>
									</div>
									<table width="100%">
										<tbody>
											<tr>
												<td width="10%" rowspan="2" align="right" bgcolor="#2c5568">
													<span class="STYLE1"><font size="2"> <b>公式10</b>
													</font>： </span>
												</td>
												<td align="left" bgcolor="ededed">
													到期一次性还本付息债券的价格鉴证：
													<br>
													P = F * (1 + i) ^(-n) &nbsp;&nbsp;&nbsp;&nbsp; (
													<b>P:</b>债券的价格鉴证值&nbsp;&nbsp;
													<b>F:</b>债券到期时本利和&nbsp;&nbsp;
													<b>i:</b>折现率&nbsp;&nbsp;
													<b>n:</b>基准日到债券到期的间隔（以年或月为单位，与折现率含义相同）&nbsp;&nbsp; )
												</td>
											</tr>
											<tr>
												<td align="left" bgcolor="ededed">
													<font color="red"> * </font> F：
													<input id="gs10_input1" type="text"
														style="border: 1px solid #B6D7ED; padding: 3px; width: 50px; color: #666666;"
														value="0">
													元 &nbsp;&nbsp;
													<font color="red"> * </font> i：
													<input id="gs10_input2" type="text"
														style="border: 1px solid #B6D7ED; padding: 3px; width: 50px; color: #666666;"
														value="0">
													% &nbsp;&nbsp;
													<font color="red"> * </font> n：
													<input id="gs10_input3" type="text"
														style="border: 1px solid #B6D7ED; padding: 3px; width: 50px; color: #666666;"
														value="0">
													年 &nbsp;&nbsp;
													<font style="font-weight: bold"> 鉴定金额： </font>
													<input id="gs10_result" type="text"
														style="border: 1px solid #B6D7ED; padding: 3px; width: 50px; color: red; text-align: center; font-weight: bold;"
														value="">
													元
													<input type="button" value="自动计算" style="margin-left: 15px"
														onclick="fnGs10(this)">
												</td>
											</tr>
											<tr>
												<td rowspan="2" align="right" bgcolor="#2c5568">
													<span class="STYLE1"><font size="2"> <b>公式11</b>
													</font>： </span>
												</td>
												<td align="left" bgcolor="#ededed">
													本利和F计算时要看计息方式是但利息还是复利利息：
													<br>
													单利息:F = A * (1 + mr)&nbsp;&nbsp;&nbsp;&nbsp;复利息:F = A * (1
													+ r) ^ m &nbsp;&nbsp;&nbsp;&nbsp; (
													<b>A:</b>债券的面值&nbsp;&nbsp;
													<b>m:</b>计息期限&nbsp;&nbsp;
													<b>r:</b>债券利息率&nbsp;&nbsp; )
												</td>
											</tr>
											<tr>
												<td align="left" bgcolor="#ededed">
													单利息：
													<input type="radio" name="type" value="1" checked="">
													复利息：
													<input type="radio" name="type" value="2">
													&nbsp;&nbsp;
													<font color="red"> * </font> A：
													<input id="gs11_input1" type="text"
														style="border: 1px solid #B6D7ED; padding: 3px; width: 50px; color: #666666;"
														value="0">
													元 &nbsp;&nbsp;
													<font color="red"> * </font> m：
													<input id="gs11_input2" type="text"
														style="border: 1px solid #B6D7ED; padding: 3px; width: 50px; color: #666666;"
														value="0">
													年 &nbsp;&nbsp;
													<font color="red"> * </font> r：
													<input id="gs11_input3" type="text"
														style="border: 1px solid #B6D7ED; padding: 3px; width: 50px; color: #666666;"
														value="0">
													% &nbsp;&nbsp;
													<font style="font-weight: bold"> 鉴定金额： </font>
													<input id="gs11_result" type="text"
														style="border: 1px solid #B6D7ED; padding: 3px; width: 50px; color: red; text-align: center; font-weight: bold;"
														value="">
													元
													<input type="button" value="自动计算" style="margin-left: 15px"
														onclick="fnGs11(this)">
												</td>
											</tr>
											<tr>
												<td rowspan="2" align="right" bgcolor="#2c5568">
													<span class="STYLE1"><font size="2"> <b>公式12</b>
													</font>： </span>
												</td>
												<td align="left" bgcolor="#ededed">
													定期支付利息到期还本债券的价格鉴定:
													<br>
													P = ∑(t=1...n)[Rt(1+i)^(-t)] + A(1+i)^(-n)
													<br>
													(
													<b>P:</b>债券的价格鉴定值&nbsp;&nbsp;
													<b>Rt:</b>第t年的预期利息收益&nbsp;&nbsp;
													<b>i:</b>折现率&nbsp;&nbsp;
													<b>A:</b>债券面值&nbsp;&nbsp;
													<b>t:</b>价格鉴定基准日距收益利息日指数&nbsp;&nbsp;
													<b>n:</b>价格鉴定基准日距到期还本日的期限&nbsp;&nbsp; )
												</td>
											</tr>
											<tr>
												<td align="left" bgcolor="#ededed">
													<font color="red"> * </font> Rt：
													<input id="gs12_input2" type="text"
														style="border: 1px solid #B6D7ED; padding: 3px; width: 50px; color: #666666;"
														value="0">
													元 &nbsp;&nbsp;
													<font color="red"> * </font> i：
													<input id="gs12_input3" type="text"
														style="border: 1px solid #B6D7ED; padding: 3px; width: 50px; color: #666666;"
														value="0">
													% &nbsp;&nbsp;
													<font color="red"> * </font> A：
													<input id="gs12_input4" type="text"
														style="border: 1px solid #B6D7ED; padding: 3px; width: 50px; color: #666666;"
														value="0">
													元 &nbsp;&nbsp;
													<font color="red"> * </font> t：
													<input id="gs12_input5" type="text"
														style="border: 1px solid #B6D7ED; padding: 3px; width: 50px; color: #666666;"
														value="0">
													年 &nbsp;&nbsp;
													<font color="red"> * </font> n：
													<input id="gs12_input6" type="text"
														style="border: 1px solid #B6D7ED; padding: 3px; width: 50px; color: #666666;"
														value="0">
													年 &nbsp;&nbsp;
													<font style="font-weight: bold"> 鉴定金额： </font>
													<input id="gs12_result" type="text"
														style="border: 1px solid #B6D7ED; padding: 3px; width: 50px; color: red; text-align: center; font-weight: bold;"
														value="">
													元
													<input type="button" value="自动计算" style="margin-left: 15px"
														onclick="fnGs12(this)">
												</td>
											</tr>

											<tr>
												<td rowspan="2" align="right" bgcolor="#2c5568">
													<span class="STYLE1"><font size="2"> <b>公式13</b>
													</font>： </span>
												</td>
												<td align="left" bgcolor="#ededed">
													上市股票价格鉴证值 = 股票股数 * 价格鉴证基准日该股票市场收盘价
												</td>
											</tr>
											<tr>
												<td align="left" bgcolor="#ededed">
													<font color="red"> * </font> 股票股数：
													<input id="gs13_input1" type="text"
														style="border: 1px solid #B6D7ED; padding: 3px; width: 50px; color: #666666;"
														value="0">
													股 &nbsp;&nbsp;
													<font color="red"> * </font> 价格鉴证基准日该股票市场收盘价：
													<input id="gs13_input2" type="text"
														style="border: 1px solid #B6D7ED; padding: 3px; width: 50px; color: #666666;"
														value="0">
													元/股 &nbsp;&nbsp;
													<font style="font-weight: bold"> 鉴定金额： </font>
													<input id="gs13_result" type="text"
														style="border: 1px solid #B6D7ED; padding: 3px; width: 50px; color: red; text-align: center; font-weight: bold;"
														value="">
													元
													<input type="button" value="自动计算" style="margin-left: 15px"
														onclick="fnGs13(this)">
												</td>
											</tr>

											<tr>
												<td rowspan="2" align="right" bgcolor="#2c5568">
													<span class="STYLE1"><font size="2"> <b>公式14</b>
													</font>： </span>
												</td>
												<td align="left" bgcolor="#ededed">
													普通股票价格鉴证(固定红利模型)：P = R / i &nbsp;&nbsp;&nbsp;&nbsp; (
													<b>P:</b>该股票的价格鉴证值&nbsp;&nbsp;
													<b>R:</b>该股票下一年的红利额&nbsp;&nbsp;
													<b>r:</b>折现率&nbsp;&nbsp; )
												</td>
											</tr>
											<tr>
												<td align="left" bgcolor="#ededed">
													<font color="red"> * </font> R：
													<input id="gs14_input1" type="text"
														style="border: 1px solid #B6D7ED; padding: 3px; width: 50px; color: #666666;"
														value="0">
													元 &nbsp;&nbsp;
													<font color="red"> * </font> i：
													<input id="gs14_input2" type="text"
														style="border: 1px solid #B6D7ED; padding: 3px; width: 50px; color: #666666;"
														value="0">
													% &nbsp;&nbsp;
													<font style="font-weight: bold"> 鉴定金额： </font>
													<input id="gs14_result" type="text"
														style="border: 1px solid #B6D7ED; padding: 3px; width: 50px; color: red; text-align: center; font-weight: bold;"
														value="">
													元
													<input type="button" value="自动计算" style="margin-left: 15px"
														onclick="fnGs14(this)">
												</td>
											</tr>

											<tr>
												<td rowspan="2" align="right" bgcolor="#2c5568">
													<span class="STYLE1"><font size="2"> <b>公式15</b>
													</font>： </span>
												</td>
												<td align="left" bgcolor="#ededed">
													普通股的价格鉴证(红利增长模型)：P = R / (i - g) &nbsp;&nbsp;&nbsp;&nbsp; (
													<b>P:</b>该股票的价格鉴证值&nbsp;&nbsp;
													<b>R:</b>该股票下一年的红利额&nbsp;&nbsp;
													<b>i:</b>折现率&nbsp;&nbsp;
													<b>g:</b>股利增长率&nbsp;&nbsp; )
												</td>
											</tr>
											<tr>
												<td align="left" bgcolor="#ededed">
													<font color="red"> * </font> R：
													<input id="gs15_input1" type="text"
														style="border: 1px solid #B6D7ED; padding: 3px; width: 50px; color: #666666;"
														value="0">
													元 &nbsp;&nbsp;
													<font color="red"> * </font> i：
													<input id="gs15_input2" type="text"
														style="border: 1px solid #B6D7ED; padding: 3px; width: 50px; color: #666666;"
														value="0">
													% &nbsp;&nbsp; g：
													<input id="gs15_input3" type="text"
														style="border: 1px solid #B6D7ED; padding: 3px; width: 50px; color: #666666;"
														value="0">
													%
													<font style="font-weight: bold"> 鉴定金额： </font>
													<input id="gs15_result" type="text"
														style="border: 1px solid #B6D7ED; padding: 3px; width: 50px; color: red; text-align: center; font-weight: bold;"
														value="">
													元
													<input type="button" value="自动计算" style="margin-left: 15px"
														onclick="fnGs15(this)">
												</td>
											</tr>

											<tr>
												<td rowspan="2" align="right" bgcolor="#2c5568">
													<span class="STYLE1"><font size="2"> <b>公式16</b>
													</font>： </span>
												</td>
												<td align="left" bgcolor="#ededed">
													优先股的价格鉴证：P = ∑(t=1...)[Rt * (1 + i) ^ (-t)] = A / i
													&nbsp;&nbsp;&nbsp;&nbsp; (
													<b>P:</b>优先股的价格鉴证值&nbsp;&nbsp;
													<b>Rt:</b>第t年优先股的收益&nbsp;&nbsp;
													<b>i:</b>折现率&nbsp;&nbsp;
													<b>A:</b>优先股的年等额股息收益&nbsp;&nbsp; )
												</td>
											</tr>
											<tr>
												<td align="left" bgcolor="#ededed">
													<font color="red"> * </font> A：
													<input id="gs16_input1" type="text"
														style="border: 1px solid #B6D7ED; padding: 3px; width: 50px; color: #666666;"
														value="0">
													元 &nbsp;&nbsp;
													<font color="red"> * </font> i：
													<input id="gs16_input2" type="text"
														style="border: 1px solid #B6D7ED; padding: 3px; width: 50px; color: #666666;"
														value="0">
													%
													<font style="font-weight: bold"> 鉴定金额： </font>
													<input id="gs16_result" type="text"
														style="border: 1px solid #B6D7ED; padding: 3px; width: 50px; color: red; text-align: center; font-weight: bold;"
														value="">
													元
													<input type="button" value="自动计算" style="margin-left: 15px"
														onclick="fnGs16(this)">
												</td>
											</tr>
										</tbody>
									</table>
									<div class="Section_title">
										<ul>
											<li>
												<b>整体资产</b>
											</li>
										</ul>
									</div>
									<table width="100%">
										<tbody>

											<tr>
												<td width="10%" rowspan="2" align="right" bgcolor="#2c5568">
													<span class="STYLE1"><font size="2"> <b>公式17</b>
													</font>： </span>
												</td>
												<td align="left" bgcolor="#ededed">
													优先股的价格鉴证：P = ∑(i=1...n)Ai/(1 + r) ^ i
													&nbsp;&nbsp;&nbsp;&nbsp; (
													<b>P:</b>鉴证价值&nbsp;&nbsp;
													<b>n:</b>收益计算年限&nbsp;&nbsp;
													<b>Ai:</b>年收益额&nbsp;&nbsp;
													<b>r:</b>适当的折现率或资本化率&nbsp;&nbsp; )
												</td>
											</tr>
											<tr>
												<td align="left" bgcolor="#ededed">
													<font color="red"> * </font> n：
													<input id="gs17_input1" type="text"
														style="border: 1px solid #B6D7ED; padding: 3px; width: 50px; color: #666666;"
														value="0">
													年 &nbsp;&nbsp;
													<font color="red"> * </font> Ai(该计算要求必须每年都是固定的)：
													<input id="gs17_input2" type="text"
														style="border: 1px solid #B6D7ED; padding: 3px; width: 50px; color: #666666;"
														value="0">
													元 &nbsp;&nbsp;
													<font color="red"> * </font> r：
													<input id="gs17_input3" type="text"
														style="border: 1px solid #B6D7ED; padding: 3px; width: 50px; color: #666666;"
														value="0">
													%
													<font style="font-weight: bold"> 鉴定金额： </font>
													<input id="gs17_result" type="text"
														style="border: 1px solid #B6D7ED; padding: 3px; width: 50px; color: red; text-align: center; font-weight: bold;"
														value="">
													元
													<input type="button" value="自动计算" style="margin-left: 15px"
														onclick="fnGs17(this)">
												</td>
											</tr>
										</tbody>
									</table>
								</div>
							</div>
							<!--cont区 结束-->
						</td>
					</tr>
				</tbody>
			</table>
		</div>


		
			
				<div id="haloword-control-container">
					<a herf="#" id="haloword-add" class="haloword-button" title="加入单词表"></a><a
						herf="#" id="haloword-remove" class="haloword-button"
						title="移出单词表"></a><a
						href="http://jgjz.bjhd.gov.cn/JGJZ/jgjz/bg_gs1.htm#"
						id="haloword-open" class="haloword-button" title="查看单词详细释义"
						target="_blank"></a><a herf="#" id="haloword-close"
						class="haloword-button" title="关闭查询窗"></a>
				</div>
				<br style="clear: both;">
			</div>
			<div id="haloword-content"></div>
		</div>
	</body>
</html>