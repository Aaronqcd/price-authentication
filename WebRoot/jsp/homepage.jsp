<%
	String path = request.getContextPath();
	String url = path + "/login/toLogin.action";
	out.println(url);
	response.sendRedirect(url);
 %>
