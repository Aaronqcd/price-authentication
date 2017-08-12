package com.pemng.serviceSystem.agency.actions;

import java.util.List;
import java.util.Map;

import com.pemng.serviceSystem.agency.services.AgencyService;
import com.pemng.serviceSystem.base.actions.BaseAction;
import com.pemng.serviceSystem.base.util.Pager;
import com.pemng.serviceSystem.cms.dto.CmsDto;

/**
 * @author eide
 *	代办事项管理,含以下代办事项：
 *	1.待受理委托书:st=01,achiv=0
 *	2.待鉴定委托书:st=02,03,achiv=0
 *	3.待初审委托书:st=07,achiv=0
 * 	4.待复审委托书:st=09,achiv=0
 * 	5.待终审委托书:st=11,achiv=0
 *  6.待归档委托书:st=06,13,achiv=0
 */
public class AgencyAction extends BaseAction {

	private static final long serialVersionUID = -5089194531001996348L;

	private AgencyService agencyService;
	private CmsDto cmsDto = new CmsDto();
	private Map<String, Object> agencyMap;	//代办统计
	private List agencyCmsList;
	private Pager pager = new Pager();
	
	/**
	 * 代办事项列表
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String agencyList(){
		Object[] obj = agencyService.queryAgencyList();
		//不同状态委托书统计数据
		agencyMap =  (Map<String, Object>) obj[0];
		return SUCCESS;
	}

	public String agencyCmsList(){
		if(cmsDto != null && cmsDto.getFlag() != 0) {
			agencyCmsList = agencyService.agencyList(pager, cmsDto);
		}
		return SUCCESS;
	}
	
	public AgencyService getAgencyService() {
		return agencyService;
	}
	
	public void setAgencyService(AgencyService agencyService) {
		this.agencyService = agencyService;
	}

	public Pager getPager() {
		return pager;
	}

	public void setPager(Pager pager) {
		this.pager = pager;
	}

	public Map<String, Object> getAgencyMap() {
		return agencyMap;
	}

	public void setAgencyMap(Map<String, Object> agencyMap) {
		this.agencyMap = agencyMap;
	}

	public List getAgencyCmsList() {
		return agencyCmsList;
	}

	public void setAgencyCmsList(List agencyCmsList) {
		this.agencyCmsList = agencyCmsList;
	}

	public CmsDto getCmsDto() {
		return cmsDto;
	}

	public void setCmsDto(CmsDto cmsDto) {
		this.cmsDto = cmsDto;
	}
}
