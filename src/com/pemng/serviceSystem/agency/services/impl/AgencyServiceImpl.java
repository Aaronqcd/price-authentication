package com.pemng.serviceSystem.agency.services.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pemng.serviceSystem.agency.dao.AgencyDao;
import com.pemng.serviceSystem.agency.services.AgencyService;
import com.pemng.serviceSystem.base.services.BaseServiceImpl;
import com.pemng.serviceSystem.base.util.Pager;
import com.pemng.serviceSystem.cms.dto.CmsDto;

public class AgencyServiceImpl extends BaseServiceImpl implements AgencyService {

	private AgencyDao agencyDao;

	@SuppressWarnings("unchecked")
	@Override
	public Object[] queryAgencyList(){
		//Object[0]放统计数据,Object[1]放查询结果列表数据
		Object[] resultObj = {new HashMap<String, Object>(), new ArrayList<Object[]>()};
		Object agencyCount = agencyDao.cmsAgencyCount();
		if (null != agencyCount) {
			resultObj[0] = (Map<String,Object>)agencyCount;
		}
		return resultObj;
	}
	
	
	public List agencyList(Pager page,CmsDto cmsDto)
	{
		Map<String, Object> params = new HashMap<String, Object>(); 
		StringBuffer hql = new StringBuffer("select c from TCommission c left join fetch c.tunitsInfo ui left join fetch c.acptUsrId aui where 1=1 ");
		if(cmsDto.getFlag() == 1){
			hql.append(" and c.st='01' and c.achiv='0' ");
		}else if(cmsDto.getFlag() == 2){
			hql.append(" and (c.st='02' or c.st='03' or c.st='08' or c.st='10' or c.st='12') and c.achiv='0' ");
		}else if(cmsDto.getFlag() == 3){
		
			hql.append(" and c.st='07' and c.achiv='0' ");
		}else if(cmsDto.getFlag() ==4){
		
			hql.append(" and c.st='09' and c.achiv='0' ");
		}else if(cmsDto.getFlag() ==5){
			hql.append(" and c.st='11' and c.achiv='0'  ");
		}else if(cmsDto.getFlag() ==6){
		    hql.append(" and (c.st='06' or c.st='13') and c.achiv='0'   ");
		}
		hql.append(" and c.markForDel='V' ");
		hql.append(" order by c.id desc ");
		page = agencyDao.pagedQuery(hql.toString(), page, params);
		return page.getData();
	}
	
	public void setAgencyDao(AgencyDao agencyDao) {
		this.agencyDao = agencyDao;
	}
}
