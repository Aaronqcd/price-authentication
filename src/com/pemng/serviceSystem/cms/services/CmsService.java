package com.pemng.serviceSystem.cms.services;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.pemng.serviceSystem.base.services.BaseService;
import com.pemng.serviceSystem.base.util.Pager;
import com.pemng.serviceSystem.cms.dto.CmsDto;
import com.pemng.serviceSystem.pojo.TAprislArtclsInfo;
import com.pemng.serviceSystem.pojo.TAttachment;
import com.pemng.serviceSystem.pojo.TCommission;
import com.pemng.serviceSystem.pojo.TRecUsr;
import com.pemng.serviceSystem.pojo.TUserInfo;

/**
 * @author eide
 *	委托书管理模块,包含以下两部分：
 *	1.价格鉴定/认定管理
 *	2.复核裁定管理
 */
public interface CmsService extends BaseService{
	/**
	 * 委托书查询列表
	 * @return
	 */
	public List<TCommission> getCmsList(Pager page,CmsDto cmsDto);
	
	/**
	 * 统计委托书数量和鉴定金额总和
	 * @param cmsDto
	 * @return
	 */
	public Map<String, Object> getCmsListCount(CmsDto cmsDto) ;
	
	/**
	 * 保存委托书
	 * @return
	 */
	public TCommission saveCms(CmsDto saveDto,TRecUsr recUsr);
	/**
	 * 删除委托书
	 * @return
	 */
	public void deleteCms(CmsDto cmsDto);
	
	/**
	 * 根据id得到委托书
	 * @return
	 */
	public TCommission getCms(Long id);
	
	/**
	 * 上传附件
	 * @param at
	 */
	public int uploadFiles(File file, String fileName, TAttachment at) throws Exception;
	
	/**
	 * 根据委托书ID/代委托书ID查询附件列表
	 * @param cmsId
	 * @param type(1:委托书ID 2:代委托书ID)
	 * @return
	 */
	public List<TAttachment> getAttachmentList(TAttachment at, int type);
	
	/**
	 * 根据委托书ID查询附件列表
	 * @param cmsId
	 * @return
	 */
	public List<TAttachment> getAttachmentList(TAttachment at);
	
	/**
	 * 根据ID删除附件(物理删除)
	 * @param cmsId
	 * @return
	 */
	public void deleteAttachment(Long atId);

	/**
	 * 获取附件并且增加下载次数
	 * @param id
	 * @return
	 */
	public TAttachment downloadAttachment(Long id);
	
	/**
	 * 根据委托书ID/代委托书ID委托明细列表
	 * @param cmsId
	 * @param type(1:委托书ID 2:代委托书ID)
	 * @return
	 */
	public List<TAprislArtclsInfo> getAprislList(TAprislArtclsInfo aprisl, int type);

	/**
	 * 根据委托书ID查询委托明细列表
	 * @param cmsId
	 * @return
	 */
	public List<TAprislArtclsInfo> getAprislList(TAprislArtclsInfo aprisl);
	
	/**
	 * 根据ID删除价格鉴定(物理删除)
	 * @param cmsId
	 * @return
	 */
	public void deleteAprisl(Long atId);
	
	/**
	 * 审核委托书
	 * @param cmsId
	 * @return
	 */
	public int auditCms(CmsDto saveDto);
	
	/**
	 * 根据类型和年份查找padCode
	 * @param tp
	 * @return
	 */
	public String selectPadCode(String tp, Long cmsId);

	
	/**
	 *批量归档委托书
	 */
	public int achiveCms(CmsDto cmsDto);

	/**
	 * 返回打印的CKEditor输入流
	 * @param printCmsId		:	委托书ID
	 * @param printCmsType		:	打印类型 	1:委托书,2:取件通知,3:送达回证，4:鉴定结论 , -1 : 打印附件
	 * @param attachmentType 	:	附件类型	只有打印类型为-1时生效,2:技术报告 , 3:勘验文件
	 * @param isGenerateCms 	:	是否重新生成文档
	 * @return
	 */
	public Object[] getPrintCms(Long printCmsId, int printCmsType,String attachmentType ,String isGenerateCms) throws Exception ;


	/**
	 * 
	 * @param printCmsId		:	委托书ID
	 * @param printCmsType		:	打印类型 	1:委托书,2:取件通知,3:送达回证，4:鉴定结论 , -1 : 打印附件
	 * @param cmsContent		: 	文档的内容
	 */
	public void savePrintCms(Long printCmsId, int printCmsType,	String cmsContent) throws IOException ;
	
	/**
	 * 保存生成送达回证
	 */
	public void updateGnlSendBk(Long cmsId, int printCmsType);
	
	/**
	 * 获取复审人员
	 */
	public List<TUserInfo> getUserRetrialUsers();

	/**
	 * 获取重复的鉴定物品委托单号
	 */
	public String getRepeatArtcls(Long cmsId);
}
