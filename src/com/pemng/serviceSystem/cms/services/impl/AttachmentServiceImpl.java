package com.pemng.serviceSystem.cms.services.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pemng.serviceSystem.base.services.BaseServiceImpl;
import com.pemng.serviceSystem.cms.dao.AttachmentDao;
import com.pemng.serviceSystem.cms.services.AttachmentService;
import com.pemng.serviceSystem.pojo.TAttachment;

public class AttachmentServiceImpl extends BaseServiceImpl implements
		AttachmentService {

	private AttachmentDao attachmentDao ;
	
	@Override
	public TAttachment getAttachment(Long id) {
		return (TAttachment) attachmentDao.getObject(TAttachment.class, id);
	}

	public AttachmentDao getAttachmentDao() {
		return attachmentDao;
	}

	public void setAttachmentDao(AttachmentDao attachmentDao) {
		this.attachmentDao = attachmentDao;
	}

	
}
