package com.pemng.serviceSystem.agency.services;

import java.util.List;

import com.pemng.serviceSystem.base.services.BaseService;
import com.pemng.serviceSystem.base.util.Pager;
import com.pemng.serviceSystem.cms.dto.CmsDto;

public interface AgencyService extends BaseService {

	/**
	 * 代办事项
	 * @return 返回不同状态的委托书的统计和各种状态的列表
	 */
	public Object[] queryAgencyList();
	
	public List agencyList(Pager page,CmsDto cmsDto);
}
