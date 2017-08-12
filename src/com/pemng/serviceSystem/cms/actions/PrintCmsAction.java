package com.pemng.serviceSystem.cms.actions;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.pemng.serviceSystem.base.actions.BaseAction;
import com.pemng.serviceSystem.cms.services.CmsService;
import com.pemng.serviceSystem.common.WebInfoMgmt;
import com.pemng.serviceSystem.common.WebUser;

/**
 * 打印委托书
 * @author liuyucheng
 *
 */
public class PrintCmsAction extends BaseAction {
	private CmsService cmsService;
	
	/**
	 * 委托书ID
	 */
	private Long printCmsId ;
	
	/**
	 * 打印的类型
	 */
	private int printCmsType;
	
	/**
	 * 附件类型
	 */
	private String attachmentType;
	
	/**
	 * 是否生成新的委托书
	 */
	private String isGenerateCms;
	
	/**
	 * 委托书的内容，以html流的方式展现
	 */
	private String cmsContent;
	
	private String cmsSt;	//委托书状态
	
	
	
	/**
	 * 获得委托书的html流，并展现
	 * @return
	 */
	public String toPrintCms()  throws Exception  {
		WebUser webUser = WebInfoMgmt.getWebInfo().getWebUser();
		if("true".equalsIgnoreCase(isGenerateCms)) {
			log.info("用户[" + webUser.getId() + " : " + webUser.getName() + "]重新生成打印 , 委托书ID : " + printCmsId + " , 打印类型 :" + printCmsType);
		} else {
			log.info("用户[" + webUser.getId() + " : " + webUser.getName() + "]执行打印 , 委托书ID : " + printCmsId + " , 打印类型 :" + printCmsType);
		}
		
		
		
		Object[] obj = cmsService.getPrintCms(printCmsId , printCmsType , attachmentType ,  isGenerateCms);
		String htmlSource = (String) obj[0];
		cmsSt = (String) obj[1];
		//if(printCmsType == 3) {
			//admin用户打印操作后,修改PRT_DT,所有人点击生成送达回证按钮后(printCmsType == 3),都能看到生成鉴定结论按钮,但管理员一直能显示
			cmsService.updateGnlSendBk(printCmsId, printCmsType);
		//}
		HttpServletRequest request  	= ServletActionContext.getRequest();
		String cxtPath = request.getContextPath();
		String scheme  = request.getScheme();
		String host = request.getHeader("Host");
		
		
		String imgActionPath = scheme + "://" + host + cxtPath + "/cms/getAttachmentRes.action";
		setCmsContent(htmlSource.replaceAll("#@#imgActionPath#@#", imgActionPath));
		request.getSession().removeAttribute("print_save_result");
		
		return "success";
	}
	
	
	/**
	 * 保存修改的委托书，只更新word和html，不保存到数据库
	 * @return
	 */
	public String savePrintCms() {
		String saveResult = "保存成功";
		WebUser webUser = WebInfoMgmt.getWebInfo().getWebUser();
		
		
		try {
			if("-1".equals(printCmsId)) {
				saveResult="附件不能保存";
				log.warn("系统不支持 打印附件，用户[" + webUser.getId() + " : " + webUser.getName() + "] , 委托书ID : " + printCmsId + " , 打印类型 :" + printCmsType);
			} else {
				cmsService.savePrintCms(printCmsId , printCmsType , cmsContent);
				log.info("用户[" + webUser.getId() + " : " + webUser.getName() + "]执行打印保存 , 委托书ID : " + printCmsId + " , 打印类型 :" + printCmsType);
			}
		} catch(Exception e) {
			log.error("保存打印失败 : " , e);
			saveResult = "保存失败";
		}
		
		ServletActionContext.getRequest().getSession().setAttribute("print_save_result", saveResult);
		return "success";
	}
	
	public CmsService getCmsService() {
		return cmsService;
	}

	public void setCmsService(CmsService cmsService) {
		this.cmsService = cmsService;
	}

	
	public Long getPrintCmsId() {
		return printCmsId;
	}


	public void setPrintCmsId(Long printCmsId) {
		this.printCmsId = printCmsId;
	}


	public int getPrintCmsType() {
		return printCmsType;
	}

	public void setPrintCmsType(int printCmsType) {
		this.printCmsType = printCmsType;
	}

	public String getIsGenerateCms() {
		return isGenerateCms;
	}

	public void setIsGenerateCms(String isGenerageCms) {
		this.isGenerateCms = isGenerageCms;
	}

	public String getCmsContent() {
		return cmsContent;
	}

	public void setCmsContent(String cmsContent) {
		this.cmsContent = cmsContent;
	}

	public String getAttachmentType() {
		return attachmentType;
	}

	public void setAttachmentType(String attachmentType) {
		this.attachmentType = attachmentType;
	}

	public String getCmsSt() {
		return cmsSt;
	}

	public void setCmsSt(String cmsSt) {
		this.cmsSt = cmsSt;
	}

	
}
