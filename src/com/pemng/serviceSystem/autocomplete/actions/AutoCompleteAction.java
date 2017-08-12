package com.pemng.serviceSystem.autocomplete.actions;

import com.pemng.serviceSystem.autocomplete.services.AutoCompleteService;
import com.pemng.serviceSystem.base.actions.BaseAction;

/**
 * Created on Apr 17, 2013
 * <p>Description: [自动提示]</p>
 * @version        1.0
 */
public class AutoCompleteAction extends BaseAction {

	private static final long serialVersionUID = -8720632684406757227L;
	
	private String term;
	private AutoCompleteService autoCompleteService;
	/*
	*//**
	 * Function Name               getDataValue                                   
	 * @return                     //函数返回值的说明
	 * @description                //合同名称自动匹配			             
	 *//*
	public String getDataValue() throws IOException {
		List<BasicDataCnt> dcList = WSUtils.getBasicDataList("002");
		List<String> list = null;
		if (null != dcList && dcList.size()>0 && StringUtils.isNotBlank(term)) {
			list = new ArrayList<String>();
			for (BasicDataCnt bdc : dcList) {
				if (bdc.getValue().matches(".*("+term.toLowerCase()+"|" +term.toUpperCase()+").*")) {
					list.add(bdc.getValue());
					if (list.size() > Constants.AUTO_COMPLETE_SIZE_10) {
						break;
					}
				}
			}
		}
		writeJsonToResponse(JsonUtil.list2json(list));
		return null;
	}*/
	
	public void setTerm(String term) {
		this.term = term;
	}
	
	public void setAutoCompleteService(AutoCompleteService autoCompleteService) {
		this.autoCompleteService = autoCompleteService;
	}
}