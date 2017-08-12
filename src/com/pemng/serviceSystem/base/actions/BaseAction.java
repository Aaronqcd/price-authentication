package com.pemng.serviceSystem.base.actions;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.pemng.serviceSystem.base.util.DateUtil;
import com.pemng.serviceSystem.base.util.JsonUtil;
import com.pemng.serviceSystem.common.Result;

public class BaseAction extends ActionSupport {

	protected Log log = LogFactory.getLog(this.getClass());
	
	protected static final String JSP = "jsp";
	
	protected String _returnJsp;

	private File _file;

	private String _fileFileName;

	private String _fileContentType;

	private String succMsg;

	private String isPop;
 
	private Result result ;
	
	//	protected Page page = new Page(); // 分页

	public BaseAction() {
//		System.out.println("in action base");
	}

	protected ServletContext getServletContext() {
		return ServletActionContext.getServletContext();
	}

	protected HttpServletRequest getRequest() {
		return ServletActionContext.getRequest();
	}

	/**
	 * 获得HttpServletResponse
	 * 
	 * @return
	 */
	protected HttpServletResponse getResponse() {
		return ServletActionContext.getResponse();
	}

	/**
	 * 获得Session
	 * 
	 * @return
	 */
	protected HttpSession getSession() {
		return getRequest().getSession();
	}

	//	 /**
	//	 *获得登录用户
	//	 */
	//	 public LoginUser getLoginUser(){
	//		 LoginUser user = (LoginUser)this.getSession().getAttribute(Constants.BRMS_SESSION_LOGIN_USER_KEY);
	//		 return user;
	//	 }

	// =========================================setter/getter================

	public File get_file() {
		return _file;
	}

	public void set_file(File _file) {
		this._file = _file;
	}

	public String get_fileFileName() {
		return _fileFileName;
	}

	public void set_fileFileName(String fileName) {
		_fileFileName = fileName;
	}

	public String get_fileContentType() {
		return _fileContentType;
	}

	public void set_fileContentType(String contentType) {
		_fileContentType = contentType;
	}
	
	public String get_returnJsp() {
		return _returnJsp;
	}

	public void set_returnJsp(String returnJsp) {
		_returnJsp = returnJsp;
	}
	
	//	public Page getPage() {
	//		return page;
	//	}
	//
	//	public void setPage(Page page) {
	//		this.page = page;
	//	}

	public String getSuccMsg() {
		return succMsg;
	}

	public void setSuccMsg(String succMsg) {
		this.succMsg = succMsg;
	}

	public String getIsPop() {
		return isPop;
	}

	public void setIsPop(String isPop) {
		this.isPop = isPop;
	}

	public void writeJsonToResponse(String json) throws IOException {
		ServletActionContext.getResponse().getWriter().write(json);
	}

	public int parseIntParameter(String name) // 解析为整数
	{
		int result = 0;
		String parameter = this.getRequest().getParameter(name);
		if (parameter != null && parameter.trim().length() > 0) {
			result = Integer.parseInt(parameter);
		}
		return result;
	}

	public String parseStringParameter(String name) // 解析为字符串
	{
		String temp = this.getRequest().getParameter(name);
		if (temp != null && temp.trim().length() > 0)
			return temp;
		return null;
	}

	public Long parseLongParameter(String name) // 解析为布尔类型
	{
		Long result = null;
		String parameter = this.getRequest().getParameter(name);
		if (parameter != null && parameter.trim().length() > 0) {
			result = Long.parseLong(parameter);
		}
		return result;
	}

	public List<Long> parseListParameter(String name, String prefix) {//解析为字符串列表，前台以prefix分割开
		List<Long> result = new ArrayList();

		String parameter = this.getRequest().getParameter(name);
		if (parameter != null && parameter.trim().length() > 0) {
			String[] str = parameter.split(prefix);
			for (String s : str) {
				result.add(Long.parseLong(s));

			}
		}

		return result;
	}

	public Date parseDateParameter(String name, String format)
			throws ParseException // 按指定格式解析为日期类型
	{
		Date result = null;
		String parameter = this.getRequest().getParameter(name);
		if (parameter != null && parameter.trim().length() > 0) {
			result = DateUtil.getDateFromString(parameter, format);
		}
		return result;
	}

	public void addFieldErrorToAjaxResponse(String fieldName,
			String errorMessage) {
		if (result == null)
			result = new Result("error");
		result.addFieldError(fieldName, errorMessage);

	}

	public void addActionErrorToAjaxResponse(String errorMessage) {
		if (result == null)
			result = new Result("error");
		result.addActionError(errorMessage);

	}

	public void writeAllErrorsToResponse() throws IOException {
		this.writeJsonToResponse(JsonUtil.object2json(result));
	}
	

	public boolean hasAjaxErrors() {
		if(result == null)
			return false;
		
		return !(result.getActionErrors().isEmpty()
				&& result.getFieldErrors().isEmpty());
	}
	
	public void writeSuccessToResponse() throws IOException{
		
		this.writeJsonToResponse(JsonUtil.object2json(new Result("success")));
		
	}
	
	public Result getErrorMsg(){
		return result;
	}
	
	/**
	 * 格式化小数
	 * @param s
	 * @return
	 */
	public String formatDouble(Double s){ 
		if(s == null)
			return "0.00";
		return new java.text.DecimalFormat("0.00").format(s.doubleValue());
	}
	
	/**
	 * 格式化小数带+-号
	 * @param s
	 * @return
	 */
	public String formatDoubleAndFuhao(Double s){ 
		if(s == null)
			return "0.00";
		String re = new java.text.DecimalFormat("0.00").format(s.doubleValue());
		if(re.indexOf("-")<0){
			re = "+"+re;
		}
		return re;
	}
	
	/**
	 * 
	 * @param json
	 * @throws IOException
	 */
	public void writeJsonData(String json) throws IOException {
		ServletActionContext.getResponse().setContentType("application/json");
		ServletActionContext.getResponse().setCharacterEncoding("UTF-8");
		ServletActionContext.getResponse().getWriter().write(json);
	}
	
	public Long getUnqCd(){
		return System.currentTimeMillis();
	}

}
