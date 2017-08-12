package com.pemng.serviceSystem.autocomplete.services.impl;

import com.pemng.serviceSystem.autocomplete.dao.AutoCompleteDao;
import com.pemng.serviceSystem.autocomplete.services.AutoCompleteService;
import com.pemng.serviceSystem.base.services.BaseServiceImpl;

/**
 * Created on Apr 18, 2013
 * <p>Description: [自动提示]</p>
 * @version        1.0
 */
public class AutoCompleteServiceImpl extends BaseServiceImpl implements AutoCompleteService{
	private AutoCompleteDao autoCompleteDao;

	
	public void setAutoCompleteDao(AutoCompleteDao autoCompleteDao) {
		this.autoCompleteDao = autoCompleteDao;
	}
}