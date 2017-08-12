package com.pemng.serviceSystem.common;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.pemng.serviceSystem.pojo.TActnInfo;

/**
 * 保存用户角色列表类
 * @author sherlockq
 *
 */
public class WebActions implements Serializable{


	private static final long serialVersionUID = 4932052849663479650L;
	private List<TActnInfo> acitonlist = new ArrayList<TActnInfo>();//动作
	
	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public static WebActions fakeActions(){
		
		WebActions roles = new WebActions();
		return roles;
		
	}

	public List<TActnInfo> getAcitonlist() {
		return acitonlist;
	}

	public void setAcitonlist(List<TActnInfo> acitonlist) {
		this.acitonlist = acitonlist;
	}
}
