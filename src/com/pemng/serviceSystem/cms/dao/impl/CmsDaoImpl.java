package com.pemng.serviceSystem.cms.dao.impl;

import java.util.Map;

import com.pemng.serviceSystem.base.dao.BaseDaoHibernate;
import com.pemng.serviceSystem.cms.dao.CmsDao;

public class CmsDaoImpl extends BaseDaoHibernate implements CmsDao {
	public CmsDaoImpl() {
		super();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void updateAttachments(Map<String, Object> params){
		StringBuilder hql = new StringBuilder()
		.append("update TAttachment a set a.TCommission.id = :cmsId, a.tmpCmsId = null  where a.tmpCmsId = :tmpCmsId ");
		this.executeHql(hql.toString(), params);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void updateTAprislArtclsInfos(Map<String, Object> params){
		StringBuilder hql = new StringBuilder()
		.append("update TAprislArtclsInfo a set a.TCommission.id = :cmsId, a.tmpCmsId = null where a.tmpCmsId = :tmpCmsId ");
		this.executeHql(hql.toString(), params);
	}
	
}
