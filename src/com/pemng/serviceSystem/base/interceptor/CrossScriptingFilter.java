package com.pemng.serviceSystem.base.interceptor;

import java.io.IOException;
import java.util.Set;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.opensymphony.xwork2.util.TextParseUtil;

public class CrossScriptingFilter implements Filter {
	
	private Set<String> excludeActions; 
	
    public void init(FilterConfig filterConfig) throws ServletException {
    	this.excludeActions = TextParseUtil.commaDelimitedStringToSet(filterConfig.getInitParameter("excludeActions"));
    }
	
    public void destroy() {
       
    }
    
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
        throws IOException, ServletException {
    	
    	HttpServletRequest httpRequest = (HttpServletRequest)request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
    	String url = httpRequest.getRequestURI();
    	String contextPath = httpRequest.getContextPath();
    	url = url.substring(contextPath.length());
    	if(url.startsWith("/"))
    		url = url.substring(1);
    	
    	
    	if(excludeActions.contains(url)){
    		chain.doFilter(httpRequest, response);
    	}else{
//    		httpRequest.getRequestDispatcher("/AuthImg.servlet").forward(request,response);
    		
    		chain.doFilter(new RequestWrapper(httpRequest), response);
    	}
    }
}