package com.pemng.serviceSystem.common.services;

import com.pemng.serviceSystem.base.services.BaseService;

public interface CityService extends BaseService {
	/**
	 * 根据城市id查出城市名称
	 * @param cityId 城市id
	 * @return
	 */
	public String getCityNm(Long cityId);
}
