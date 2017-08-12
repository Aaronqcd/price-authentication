package com.pemng.serviceSystem.cms.dao;

import java.util.Map;

import com.pemng.serviceSystem.base.dao.Dao;

/**
 * @author eide
 *	委托书管理模块,包含以下两部分：
 *	1.价格鉴定/认定管理
 *	2.复核裁定管理
 */
public interface CmsDao extends Dao {
	
	/**
	 * 根据tmp_cms_id修改附件的cms_id
	 * @param params
	 */
	public void updateAttachments(Map<String, Object> params);
	
	/**
	 * 根据tmp_cms_id修改鉴定物品信息的cms_id
	 * @param params
	 */
	public void updateTAprislArtclsInfos(Map<String, Object> params);
	
}
