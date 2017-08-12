package com.pemng.serviceSystem.frame.actions;

import java.util.List;

import com.pemng.serviceSystem.base.actions.BaseAction;
import com.pemng.serviceSystem.common.Node;
import com.pemng.serviceSystem.common.WebInfoMgmt;
import com.pemng.serviceSystem.common.WebUser;
import com.pemng.serviceSystem.frame.services.FrameService;
import com.pemng.serviceSystem.pojo.TActnInfo;

public class FrameAction extends BaseAction{
	private FrameService frameService;
	private List<Node> nodes;
	private List<TActnInfo> result;
	private WebUser webUser;

	public String top() {
		webUser = WebInfoMgmt.getWebInfo().getWebUser();
		 if(webUser!=null){
			 nodes = frameService.getNodes(webUser.getId());
		 }
		return SUCCESS;
	}

	public String main() {
		webUser = WebInfoMgmt.getWebInfo().getWebUser();
		 if(webUser!=null){
			 nodes = frameService.getNodes(webUser.getId());
		 }
		return SUCCESS;
	}

	public String left() {
		return SUCCESS;
	}

	public String welcome() {
		return SUCCESS;
	}

	public String bottom() {
		return SUCCESS;
	}
	public List<TActnInfo> getResult() {
		return result;
	}

	public void setResult(List<TActnInfo> result) {
		this.result = result;
	}

	public FrameService getFrameService() {
		return frameService;
	}

	public void setFrameService(FrameService frameService) {
		this.frameService = frameService;
	}

	public List<Node> getNodes() {
		return nodes;
	}

	public void setNodes(List<Node> nodes) {
		this.nodes = nodes;
	}

	public WebUser getWebUser() {
		return webUser;
	}

	public void setWebUser(WebUser webUser) {
		this.webUser = webUser;
	}

}
