<%@ page language="java" pageEncoding="utf-8" %>
<%@ page import="com.opensymphony.xwork2.util.ValueStack"%>
<%@ page import="com.opensymphony.xwork2.ActionContext"%>
<%@ page import="java.util.*"%>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.7.2.js"></script>
<script type="text/javascript">


$(function(){

	var message = '';
	<%
		ValueStack vc = ActionContext.getContext().getValueStack();
		List<String> ferrors = (List<String>)vc.findValue("actionErrors");
	%>
		<%if(ferrors.size()>0){%>
			//message='<ul>';
		
		<%
		}%>
		<%	
		for(String message : ferrors){%>
			//message+= '<li>'+'<%= message%>'+'</li>';
			message+= message;
		<%	
		//break;
		}%>
	
		<%
			if(ferrors.size()>0){
		%>
			//message+='</ul>';
		<%}%>
	if(message!=''){
		alert(message);
		//messageError(message);
	}


});

</script>

