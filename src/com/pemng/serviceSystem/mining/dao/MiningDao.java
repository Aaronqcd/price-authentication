package com.pemng.serviceSystem.mining.dao;

import java.util.List;
import java.util.Map;

import com.pemng.serviceSystem.base.dao.Dao;

public interface MiningDao extends Dao {
	
	/**
	 * 获取标的物目录列表
	 * @return
	 */
	public List<Map<String, Object>> getMinNames(List<String> usersList);
	
	/**
	 * 获取采价人
	 * @return
	 */
	public List<Map<String, Object>> getMinUsers(List<String> usersList);
}
