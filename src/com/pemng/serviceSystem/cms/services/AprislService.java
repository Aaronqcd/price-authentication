package com.pemng.serviceSystem.cms.services;

import java.util.List;

import org.json.JSONArray;

import com.pemng.serviceSystem.base.services.BaseService;
import com.pemng.serviceSystem.base.util.Pager;
import com.pemng.serviceSystem.cms.dto.AprislDto;
import com.pemng.serviceSystem.pojo.TAprislArtclsInfo;

/**
 *	价格鉴定
 */
public interface AprislService extends BaseService{

	/**
	 * 价格鉴定查询列表
	 * @return
	 */
	public List<TAprislArtclsInfo> getAprislList(Pager page,AprislDto aprislDto);
	
	//添加价格鉴定
	public void saveAprisl(AprislDto aprislDto,JSONArray array);
	
	//查找单个价格鉴定物品
	public TAprislArtclsInfo getObjectById(Long id);

	//修改价格鉴定
	public void updateAprisl(AprislDto aprislDto, JSONArray expArray,Long[] ids);
	
}
