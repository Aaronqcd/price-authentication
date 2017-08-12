package com.pemng.serviceSystem.agency.dao;

import com.pemng.serviceSystem.base.dao.Dao;

public interface AgencyDao extends Dao {
	
	/**
	 * 统计
	 * @return 各类型代办统计结果
	 */
	public Object cmsAgencyCount();


}
