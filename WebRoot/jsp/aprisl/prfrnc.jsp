<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript">

		jQuery(document).ready(function(){
				
		}); 
	
		//添加参考价格
		function toAddPrfrmc() {
			var ck = document.getElementsByName("ids");
			var i;
			var ids = "";
   			for(i=0;i<ck.length;i++){
				ids = ids + ck[i].value +",";
			}
			//location.href="${pageContext.request.contextPath}/agency/mining.action?mid="+ids; 
   			window.open("${pageContext.request.contextPath}/aprisl/mining.action?mid="+ids, "addPrfrmc");
		}
		
		//参考技术卷
		function referTech() {
			window.open("${pageContext.request.contextPath}/aprisl/referTech.action","_blank");
		}
		
		//
		//删除 
		function delPrfRow(obj) {
			$(obj).parent().parent().remove();
			var b = $("#prf_Tbl>tbody>tr").length;
			var tableall = $("#prf_Tbl");
				$(tableall).find("tr").each(function(trindex,tritem){
				//trindex   行数
				if(trindex != 0 && this.style.display != 'none'){
					$("#prf_Tbl tr:eq("+trindex+")").find("td").each(function(tdindex,tditem){
						//列内容
						if(tdindex==0){
							$(tditem).text(trindex);
						}
					});
				}
			});
		}
</script>
			

	<h4>参考价格库</h4>
	&nbsp;&nbsp;<input id="add" name="button1" class="anniu" type="button" value="参考技术卷" onclick="referTech()" />
	&nbsp;&nbsp;<input id="add" name="button1" class="anniu" type="button" value="添加参考价格" onclick="toAddPrfrmc()" />
	<input type="hidden" id='hid' />
	<table id="prf_Tbl" border="0" cellSpacing="0" cellPadding="0">
		<tbody>
		<tr>
			<th width="5%" style="text-align: center;">序号</th>
			<th width="15%" style="text-align: center;">参考物名称</th>
			<th width="15%" style="text-align: center;">采价日期</th>
			<th width="15%" style="text-align: center;"> 分类（品名）</th>
			<th width="15%" style="text-align: center;">型号（类型）</th>
			<th width="15%" style="text-align: center;">经销商价格</th>
			<th width="15%" style="text-align: center;">操作</th>
		</tr>
			<s:if test="(null == misList || misList.size()<=0) && (null == aprislDto.TPrfrncPrcLib || aprislDto.TPrfrncPrcLib.size()<=0)" >
				<tr><td colspan="7" style="text-align: left;">请添加参考价格</td></tr>
			</s:if>
			<s:iterator value="misList" status='st' var="ml">
			<tr>
				<td align="center">
					${st.index+1}
				</td>
				<td align="center">
					<input type="hidden" value="<s:property value="id" />" name="ids" />
					<s:property value="TMiningPriceSort.sortName"/>
				</td>
				<td align="center"><!-- 采价日期 -->
					<s:date name="miningDate" format="yyyy-MM-dd"/>
				</td>
				<td align="center"><!-- 分类（品名） -->
					<s:property value="name" />
				</td>
				<td align="center"><!--  型号（类型）-->
					<s:property value="typeName" />
				</td>
				<td align="center"><!--  经销商价格 -->
					<s:property value="miningPrice" />
				</td>
				<td align="center">
					<a id="<s:property value="%{#st.index+1}"/>" style="text-decoration: underline;" onclick="delPrfRow(this)">删除</a>
				</td>
			</tr>
			</s:iterator>
			<s:iterator value="aprislDto.TPrfrncPrcLib" status='st_' var="pl">
			<tr>
				<td align="center">
					${st_.index+1}
				</td>
				<td align="center">
					<input type="hidden" value="<s:property value="TMiningPriceInfo.id" />" name="ids" />
					<s:property value="TMiningPriceInfo.TMiningPriceSort.sortName"/>
				</td>
				<td align="center">
					<s:date name="TMiningPriceInfo.miningDate" format="yyyy-MM-dd"/>
				</td>
				<td align="center">
					<s:property value="TMiningPriceInfo.name" />
				</td>
				<td align="center">
					<s:property value="TMiningPriceInfo.typeName" />
				</td>
				<td align="center">
					<s:property value="TMiningPriceInfo.miningPrice" />
				</td>
				<td align="center">
					<a id="<s:property value="%{#st.index+1}"/>" style="text-decoration: underline;" onclick="delPrfRow(this)">删除</a>
				</td>
			</tr>
			</s:iterator>
		</tbody>
	</table>
