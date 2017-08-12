package com.pemng.serviceSystem.cms.services;

import com.pemng.serviceSystem.base.services.BaseService;
import com.pemng.serviceSystem.pojo.TAttachment;

/**
 *	附件
 */
public interface AttachmentService extends BaseService{

	/**
	 * 价格鉴定查询列表
	 * @return
	 */
	public TAttachment getAttachment(Long id);
	
}
