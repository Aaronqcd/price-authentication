package com.pemng.serviceSystem.common.actions;

import java.util.Locale;

import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.pemng.serviceSystem.base.actions.BaseAction;

public class ChangeLocaleAction extends BaseAction {
	String local;

	public String getLocal() {
		return local;
	}

	public void setLocal(String local) {
		this.local = local;
	}

	@Override
	public String execute() throws Exception {
		Locale loc = null;
		if ("zh_CN".equals(local)) {
			loc = Locale.CHINA;
		} else if ("en_US".equals(local)) {
			loc = Locale.US;
		} else if ("ko_KR".equals(local)) {
			loc = Locale.KOREA;
		}
		System.out.println("设置前getLocale==="+getLocale());
//		System.out.println("loc==="+loc);
//		ActionContext.getContext().setLocale(loc);
//		HttpSession session = ServletActionContext.getRequest().getSession();
//		session.setAttribute("WW_TRANS_I18N_LOCALE", loc);
//		ActionContext.getContext().getSession().put("WW_TRANS_I18N_LOCALE", Locale.US);
//		Locale locale=new Locale("en","US");
//		ActionContext.getContext().getSession().put("WW_TRANS_I18N_LOCALE", locale);
//		ActionContext.getContext().getSession().put(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME,locale);
//		
//		System.out.println(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME);
//		
//		
		//更改语言
		LocaleResolver localeResolver = RequestContextUtils.getLocaleResolver(getRequest());
		localeResolver.setLocale(getRequest(), getResponse(), loc);
		System.out.println("设置后getLocale==="+getLocale());
		
		//Action中处理
//		ApplicationContext ctx = AppContainerUtils.getWebApplicationContext(this.getServletContext());
//		String str = ctx.getMessage("helloworld", null, getLocale());
//
//        System.out.println(str);//输出“陈刚”
		
		return SUCCESS;

	}

}
