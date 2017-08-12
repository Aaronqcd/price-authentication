<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<script>
var currentPage;
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
	currentPage = page;
	var action = document.searchForm.action;
	var params=$('#searchForm').serialize();
	$.ajax({
        url: action+"?pager.currentPage="+page+"&pager.pageSize="+document.getElementById('pgSize').value,
		type:"POST",
        cache:false,
        dataType:"html",
        data:params,
        success: function(html){
        	$("#tagContent0").html('').html(html);
        }
    });
}
function pageSizeQuery(){
	var action = document.searchForm.action;
	var params=$('#searchForm').serialize();
	$.ajax({
        url: action+"?pager.pageSize="+document.getElementById('pgSize').value,
		type:"POST",
        cache:false,
        dataType:"html",
        data:params,
        success: function(html){
        	$("#tagContent0").html('').html(html);
        }
    });
}
</script>

<input id="total" type="hidden" value=<s:property value="#request.pager.totalPage" />>
<s:hidden name="pager.currentPage" id="currentPage"/>

	<s:if test="#request.pager.totalSize==0">
		找不到相符的内容或信息。
	</s:if>
	<s:else>
	<div class="fanye">
	<s:if	test='%{#request.pager.currentPage > 1}'>
		<a href="javascript:pageQuery(1);" >首页</a><a href="javascript:pageQuery('<s:property value="#request.pager.currentPage - 1" />');">上一页</a>
	</s:if>
	<s:else><a>首页</a>  <a>上一页</a></s:else>
	<s:if	test='%{#request.pager.totalPage>1 && #request.pager.currentPage < #request.pager.totalPage}'>
		<a href="javascript:pageQuery('<s:property value="#request.pager.currentPage + 1" />');">下一页</a>  
 		<a href="javascript:pageQuery('<s:property value="#request.pager.totalPage" />');">末页</a>
	</s:if>
	<s:else><a>下一页</a>  <a>末页</a> </s:else>
	展示<s:select onchange="pageSizeQuery();" id="pgSize" name="pager.pageSize" list="#{5:5,10:10,15:15,20:20}"></s:select>
	条 共<s:property value="#request.pager.totalSize"/>条
	
	</div>
	</s:else>

