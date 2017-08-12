package com.pemng.serviceSystem.common.services;

import com.pemng.serviceSystem.base.services.BaseService;

public interface PrvncService extends BaseService{

	/**
	 * 根据省份id查出省份名称
	 * @param prvId 省份id
	 * @return
	 */
	public String getPrvncName(Long prvId);
}
