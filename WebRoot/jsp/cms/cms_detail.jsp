<!-- 委托明细 -->
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript">

		//删除 
		function delDetail(id) {
			if(confirm("确定删除委托明细吗？")){
			$.ajax({
			            url: "${pageContext.request.contextPath}/cms/delAprisl.action",
			            cache:false,
			            dataType:"html",
			            data:{"aprislDto.id":id},
			            success: function(){
			            	afterCreateAprisl();
			            }
			        });
			}
		}
</script>

<table id="detail_Tbl" border="0" cellSpacing="0" cellPadding="0">
	<tr>
		<th  width="5%">序号</th>
		<th  width="20%">标的名称</th>
		<th  width="5%">数量</th>
		<th  width="10%">价格鉴定基准日</th>
		<th  width="8%">市场价格</th>
		<th  width="10%">购买时间</th>
		<th  width="5%">成新率</th>
		<th  width="5%">配件</th>
		<th  width="8%">鉴定金额</th>
		<s:if test="null != aprislDto.TCommission.id">
		<th  width="10%">鉴定</th>
		</s:if >
		<th  width="10%">操作</th>
	</tr>
	<s:set name="totalMny" value="0"></s:set>
	<s:if test="null != aprislArtclsList &&  aprislArtclsList.size()>0 ">
		<s:iterator value="aprislArtclsList" status='st'>
			<tr <s:if test="%{#st.index%2==0}">class="td1"</s:if>>
			
				<td><s:property value="%{#st.index+1}"/></td>
				<td>
				<s:if test="artclNm != null && artclNm.toString().length() > 10">
					<span title="<s:property value="artclNm"/>" style="color:black">
						<s:property value="artclNm.toString().substring(0,10)"/>...
					</span>
				</s:if>
				<s:else>
					<s:property value="artclNm"/>
				</s:else>
				</td>
				<td style="text-align: center"><s:property value="qnty" /></td>
				<td><s:date format="yyyy-MM-dd" name="prcArtclRefDt" /></td>
				<td style="text-align: right"><s:property value="orgnlPrc" /><s:if test="null != orgnlPrc">元</s:if></td>
				<td><s:date format="yyyy-MM-dd" name="buyTm" /></td>
				<td><s:property value="newRate" /><s:if test="null != newRate">%</s:if></td>
				<td>
					<s:if test="null == TCmsArtclsAccesorses || TCmsArtclsAccesorses.size()<=0">无</s:if>
					<s:else>有</s:else>
				</td>
				<td style="text-align: right"><s:if test="null != aprislPrc && aprislPrc>0"><s:property value="aprislPrc" />元</s:if></td>
				<s:set name="totalMny" value="#totalMny + (aprislPrc==null?0:aprislPrc)"></s:set>
				<s:if test="null != aprislDto.TCommission.id">
					<td><input type="button" class="anniu2" value="价格鉴定" onclick="goAprisl(<s:property value='id'/>)"></td>
				</s:if>
				<td>
				<input type="button" class="anniu2" value="删除" onclick="delDetail(<s:property value="id"/>)">
				</td>
			</tr>
		</s:iterator>
		<s:hidden value="%{#totalMny}" name="saveDto.aprislMnySum" id="aprislMnySum"/>
	</s:if>
	<s:else>
	<tr class="td1" class="td1">
		<td colspan="12" style="text-align: left;">找不到相符的内容或信息。</td>
	</tr>
	</s:else>
	<!--<s:else>
		<s:iterator value="cmsDto.TAprislArtclsInfos" status='st_'>
			<tr>
				<td><s:property value="%{#st_.index+1}"/></td>
				<td><s:property value="artclNm" /></td>
				<td><s:property value="qnty" /></td>
				<td><s:property value="prcArtclRefDt" /></td>
				<td class="td_right"><s:property value="orgnlPrc" /></td>
				<td><s:property value="buyTm" /></td>
				<td><s:property value="newRate" /></td>
				<td>
					<s:if test="null == TCmsArtclsAccesorses || TCmsArtclsAccesorses.size()<=0">无</s:if>
					<s:else>有</s:else>
				</td>
				<td><s:property value="aprislPrc" /></td>
				<s:set name="totalMny" value="#totalMny + aprislPrc"></s:set>
				<td><input type="button" value="价格鉴定" onclick="goAprisl(<s:property value="id"/>)"></td>
				<td><a id="<s:property value="%{#st.index+1}"/>" style="text-decoration: underline;" onclick="delDetRow(<s:property value="id"/>)">删除</a></td>
			</tr>
		</s:iterator>
		<s:hidden value="%{#totalMny}" name="saveDto.aprislMnySum" id="aprislMnySum"/>
	</s:else>
--></table>