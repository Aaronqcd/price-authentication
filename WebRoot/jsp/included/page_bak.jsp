<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<script>
function pageQuery(page){
	var re = /^[1-9]+[0-9]*]*$/;
	var total = document.getElementById("total").value;	
	if(typeof(page) == "undefined" || page == ''){
		page = document.getElementById("newPage").value;
	}
	if(typeof(page) == "undefined" || page == ''){
		alert("请输入要跳转的页面");
		return false;
	}
	if(!re.test(page)){
		alert("请输入正整数");
		return false;
	}
	if(parseInt(page)>parseInt(total)){
		alert("请输入正确的页码");
		return false;
	}
	var action = document.searchForm.action;
	document.searchForm.action=action+"?pager.currentPage="+page;
	document.searchForm.submit();
	return true;
}
function pageSizeQuery(){
	var action = document.searchForm.action;
	document.searchForm.action=action+"?pager.pageSize="+document.getElementById('pgSize').value;
	document.searchForm.submit();
}
</script>
<input id="total" type="hidden" value=<s:property value="#request.pager.totalPage" />>
	<div class="fengye">
	<s:if test="#request.pager.totalSize==0">
		找不到相符的内容或信息。
	</s:if>
	<s:else>
	<div class="fengyea">
	<s:if	test='%{#request.pager.currentPage > 1}'>
		<a href="javascript:pageQuery(1);" >首页</a><a href="javascript:pageQuery('<s:property value="#request.pager.currentPage - 1" />');">上一页</a>
	</s:if>
	<s:else><a>首页</a>  <a>上一页</a></s:else>
	<s:if	test='%{#request.pager.totalPage>1 && #request.pager.currentPage < #request.pager.totalPage}'>
		<a href="javascript:pageQuery('<s:property value="#request.pager.currentPage + 1" />');">下一页</a>  
 		<a href="javascript:pageQuery('<s:property value="#request.pager.totalPage" />');">末页&nbsp;</a>
	</s:if>
	<s:else><a>下一页</a>  <a>末页&nbsp;</a> </s:else>
	当前<s:property value="#request.pager.currentPage" />/<s:property value="#request.pager.totalPage" />&nbsp;
	每页显示<s:select onchange="pageSizeQuery();" id="pgSize" name="#request.pager.pageSize" list="#{5:5,10:10, 20:20, 30:30, 40:40, 50:50, 60:60, 70:70, 80:80, 90:90, 100:100}"></s:select>
	转到
	<input type="text" name="newPage" value="<s:property value="#request.pager.currentPage" />" size="4" id="newPage" onblur="return pageQuery('');"/>页
					
	</div>
	</s:else>
	
	</div>
