/**    
 * description: 
 *      
 */
package com.pemng.serviceSystem.base.interceptor;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.regex.Pattern;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * @author luohanbin</a>
 * @version 1.0
 */

public class AntiSqlAttacksFilter implements Filter {

//	static String reg = "(?:')|(?:--)|(/\\*(?:.|[\\n\\r])*?\\*/)|(\\b(select|update|and|or|delete|insert|trancate|char|into|substr|ascii|declare|exec|count|master|into|drop|execute)\\b)";  
	
	
//	String OutStr = "<script>alert('XSS')</script>";  
//	OutStr = OutStr.replaceAll("&","&amp;");  
//	OutStr = OutStr.replaceAll("<","&lt;");  
//	OutStr = OutStr.replaceAll(">","&gt;");  
//	OutStr = OutStr.replaceAll("\"","&quot;");  
//	OutStr = OutStr.replaceAll("\'","&#39;");  
//	OutStr = OutStr.replaceAll("\\(","&#40;");  
//	OutStr = OutStr.replaceAll("\\)","&#41;");  
//	OutStr = OutStr.replaceAll("%","&#37;");  
//	OutStr = OutStr.replaceAll("\\+","&#43;");  
//	OutStr = OutStr.replaceAll("-","&#45;");  
//	out.println(OutStr);  
	
	private String sqlDefinedKeys;

	public void destroy() {
		// TODO Auto-generated method stub

	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain filterChain) throws IOException, ServletException {
		for (Enumeration e = ((HttpServletRequest) request).getParameterNames(); e.hasMoreElements();) {
			String paraName = (String) e.nextElement();
			String paraValue = "";
			String[] value = request.getParameterValues(paraName);
			for (int i = 0; i < value.length; i++) {
				paraValue = paraValue + value[i];
	        }
			

			if (filterContent(paraValue)) {
				try {
					String errorUrl = ((HttpServletRequest) request).getServletPath();
					PrintWriter out = response.getWriter();
					out.println("<script language=\"javascript\">");
					out.println("window.top.location.href='/sorry_filter.jsp';");
					out.println("</script>");
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				return;
			}
		}

		filterChain.doFilter(request, response);

	}

	public void init(FilterConfig config) throws ServletException {
		sqlDefinedKeys = config.getInitParameter("sqlDefinedKeys");
	}

	private boolean filterContent(String content) {
		content = content.toLowerCase();
		Pattern p = Pattern.compile(sqlDefinedKeys);
		return p.matcher(content).find();
	}

}