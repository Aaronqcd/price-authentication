package com.pemng.serviceSystem.frame.services;

import java.util.List;

import com.pemng.serviceSystem.base.services.BaseService;
import com.pemng.serviceSystem.common.Node;
import com.pemng.serviceSystem.pojo.TPartMenu;

public interface FrameService extends BaseService {

	/**
	 * 获得该用户所拥有的栏目菜单
	 * @param userId
	 * @return
	 */
	public List<Node> getNodes(Long userId);
	
}
