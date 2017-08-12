package com.pemng.serviceSystem.units.services.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.pemng.common.util.SelectComponentUtil;
import com.pemng.serviceSystem.base.util.JsonUtil;
import com.pemng.serviceSystem.base.util.Pager;
import com.pemng.serviceSystem.base.util.PojoUtils;
import com.pemng.serviceSystem.cms.dao.CmsDao;
import com.pemng.serviceSystem.common.Constants;
import com.pemng.serviceSystem.pojo.TCommission;
import com.pemng.serviceSystem.pojo.TUnitsInfo;
import com.pemng.serviceSystem.pojo.TUnitsSort;
import com.pemng.serviceSystem.pojo.TUserInfo;
import com.pemng.serviceSystem.units.dao.UnitsInfoDao;
import com.pemng.serviceSystem.units.dao.UnitsSortDao;
import com.pemng.serviceSystem.units.dto.UnitsInfoDto;
import com.pemng.serviceSystem.units.dto.UnitsSortDto;
import com.pemng.serviceSystem.units.services.UnitsManagerService;


public class UnitsManagerServiceImpl implements UnitsManagerService {
	private UnitsInfoDao unitsInfoDao;
	private UnitsSortDao unitsSortDao;
	private CmsDao cmsDao;
	

	@Override
	public void addUnits(UnitsInfoDto saveDto) {
		TUnitsInfo unitsInfo=(TUnitsInfo) PojoUtils.convert2Pojo(saveDto);
		unitsInfo.setMarkForDel(Constants.MARK_FOR_DELETE_NO);
		TUnitsSort us = null;
		if (null != saveDto.getTUnitsSort() && null != saveDto.getTUnitsSort().getId()) {
			us = unitsSortDao.getObject(saveDto.getTUnitsSort().getId());
		}
		unitsInfo.setTUnitsSort(us);
		this.unitsInfoDao.saveObject(unitsInfo);
	}

	// 逻辑删除
	public void deleteUnit(UnitsInfoDto unitsInfoDto) {
		List unitList=new ArrayList();
		TUnitsInfo unitsInfo=new TUnitsInfo();
		for(long id:unitsInfoDto.getIds()){
			unitsInfo=getUnits(id);
			unitsInfo.setMarkForDel(Constants.MARK_FOR_DELETE_YES);
			unitList.add(unitsInfo);
		}  
		unitsInfoDao.saveObjectList(unitList);
	}

	
	@Override
	public TUnitsInfo getUnits(Long id) {
		return (TUnitsInfo) unitsInfoDao.getObject(id);
	}

	@Override
	public void modifyUnits(UnitsInfoDto saveDto) {
		TUnitsInfo unitsInfo=(TUnitsInfo) PojoUtils.convert2Pojo(saveDto);
		unitsInfo.setMarkForDel(Constants.MARK_FOR_DELETE_NO);
		TUnitsSort us = unitsSortDao.getObject(saveDto.getTUnitsSort().getId());
		unitsInfo.setTUnitsSort(us);
		this.unitsInfoDao.updateObjectOnly(unitsInfo);
	}


	@Override
	public List unitsList(Pager page, UnitsInfoDto unitsInfoDto) {
		// TODO Auto-generated method stub
		StringBuffer hql = new StringBuffer("select ui from TUnitsInfo ui  where 1=1 ");
		Map<String, Object> params = new HashMap<String, Object>(); 
		
		//单位名称
		if (StringUtils.isNotBlank(unitsInfoDto.getName())) {
			params.put("name", "%" + unitsInfoDto.getName().trim() + "%");
			hql.append(" and ui.name like :name ");
			
		}
		//单位代码
		if (StringUtils.isNotBlank(unitsInfoDto.getNo())) {
			params.put("no", "%" + unitsInfoDto.getNo().trim() + "%");
			hql.append(" and ui.no like :no ");
		}
		
		//单位简称
		if (StringUtils.isNotBlank(unitsInfoDto.getShortName())) {
			params.put("shortName", "%" + unitsInfoDto.getShortName().trim() + "%");
			hql.append(" and ui.shortName like :shortName "); 
		}

		//单位类型
		if (unitsInfoDto!=null && unitsInfoDto.getTUnitsSort()!= null 
				&& null != unitsInfoDto.getTUnitsSort().getTUnitsSort() 
				&& unitsInfoDto.getTUnitsSort().getTUnitsSort().getId()!= null
				&& unitsInfoDto.getTUnitsSort().getTUnitsSort().getId()!= -1) {
			params.put("id", unitsInfoDto.getTUnitsSort().getTUnitsSort().getId());
			hql.append(" and ui.TUnitsSort.TUnitsSort.id = :id"); 
		
		}
		
		if (unitsInfoDto!=null && unitsInfoDto.getTUnitsSort()!= null 
				&& unitsInfoDto.getTUnitsSort().getId()!= null
				&& unitsInfoDto.getTUnitsSort().getId()!= -1) {
			params.put("pid", unitsInfoDto.getTUnitsSort().getId());
			hql.append(" and ui.TUnitsSort.id = :pid"); 
		}
		//鉴定类别
		if (StringUtils.isNotBlank(unitsInfoDto.getType())
				&& !unitsInfoDto.getType().equals("-1")) {
			params.put("type",  unitsInfoDto.getType().trim() );
			hql.append(" and ui.type = :type "); 
		}
		
		//单位地址
		if (StringUtils.isNotBlank(unitsInfoDto.getAddress())) {
			params.put("address", "%" + unitsInfoDto.getAddress().trim() + "%");
			hql.append(" and ui.address like :address "); 
		}
		
		hql.append(" and ui.markForDel='V' ");
		hql.append(" order by ui.id desc ");
		
		page = unitsInfoDao.pagedQuery(hql.toString(), page, params);
		return page.getData();
	}

	

	public UnitsSortDao getUnitsSortDao() {
		return unitsSortDao;
	}

	public void setUnitsSortDao(UnitsSortDao unitsSortDao) {
		this.unitsSortDao = unitsSortDao;
	}

	@Override
	public List<TUnitsSort> getSortList() {
		Map<String, Object> params = new HashMap<String, Object>();
		StringBuilder hql = new StringBuilder();
		hql.append("select us from TUnitsSort us where us.markForDel = :markForDel");//
		params.put("markForDel", Constants.MARK_FOR_DELETE_NO);
		hql.append(" and us.sortLevel <> 3 ");
		return (List<TUnitsSort>) unitsSortDao.findObjectsByHql(hql.toString(), params);
	}

	@Override
	public List<TUnitsSort> getThreeList() {
		Map<String, Object> params = new HashMap<String, Object>();
		StringBuilder hql = new StringBuilder();
		hql.append("select us from TUnitsSort us where us.markForDel = :markForDel");//
		params.put("markForDel", Constants.MARK_FOR_DELETE_NO);
		hql.append(" and us.sortLevel = 3 ");
		return (List<TUnitsSort>) unitsSortDao.findObjectsByHql(hql.toString(), params);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public String getUnitSelectValue(){
		StringBuilder hql = new StringBuilder("select ui from TCommission c join c.tunitsInfo ui ")
		.append("left join fetch ui.TUnitsSort us left join fetch us.TUnitsSort where ui.markForDel='V'");
		List<TUnitsInfo> unitsList = (List<TUnitsInfo>) unitsSortDao.findObjectsByHql(hql.toString(), null);
		Map<Object, Object> retMap = new HashMap<Object, Object>();
		if (null != unitsList) {
			TUnitsSort lv1 = null;
			TUnitsSort lv2 = null;
			Object[] ret = null;
			for (TUnitsInfo lv3 : unitsList) {
				ret = new Object[6];
				lv2 = lv3.getTUnitsSort();
				if (null != lv2) {
					lv1 = lv2.getTUnitsSort();
					if (null != lv1) {
						ret[0] = lv1.getId();
						ret[1] = lv1.getSortName();	
					}
					ret[2] = lv2.getId();
					ret[3] = lv2.getSortName();	
				}
				ret[4] = lv3.getId();
				ret[5] = lv3.getShortName();	
				SelectComponentUtil.buildSelectOptionValues(ret, retMap);
			}
		}
		String jsonValue = JsonUtil.map2json(retMap);
		return jsonValue;
	}
	
	@Override
	public String getUnitSelectValueAll(){
		StringBuilder hql = new StringBuilder("select ui from TUnitsInfo ui ")
		.append("left join fetch ui.TUnitsSort us left join fetch us.TUnitsSort where ui.markForDel='V'");
		List<TUnitsInfo> unitsList = (List<TUnitsInfo>) unitsSortDao.findObjectsByHql(hql.toString(), null);
		Map<Object, Object> retMap = new HashMap<Object, Object>();
		if (null != unitsList) {
			TUnitsSort lv1 = null;
			TUnitsSort lv2 = null;
			Object[] ret = null;
			for (TUnitsInfo lv3 : unitsList) {
				ret = new Object[6];
				lv2 = lv3.getTUnitsSort();
				if (null != lv2) {
					lv1 = lv2.getTUnitsSort();
					if (null != lv1) {
						ret[0] = lv1.getId();
						ret[1] = lv1.getSortName();	
					}
					ret[2] = lv2.getId();
					ret[3] = lv2.getSortName();	
				}else{
					ret[1] = "";
					ret[3] = "";	
				}
				ret[4] = lv3.getId();
				ret[5] = lv3.getShortName();	
				SelectComponentUtil.buildSelectOptionValues(ret, retMap);
			}
		}
		String jsonValue = JsonUtil.map2json(retMap);
		return jsonValue;
	}

	@SuppressWarnings("unchecked")
	@Override
	public int countCmsUnits(Long[] cmsUnitID) {
		String hql = "select count(cms) from TCommission cms  where cms.tunitsInfo.id in(:id) and cms.markForDel='V' ";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", cmsUnitID);
        return this.cmsDao.getRowsCountByHql(hql, params);
	}
	
	@Override
	public int countUnitsInfo(Long[] sortsId) {
		String hql = "select count(ui) from TUnitsInfo ui  where ui.TUnitsSort.id in(:id) and ui.markForDel='V' ";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", sortsId);
        return this.unitsSortDao.getRowsCountByHql(hql, params);
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<TUnitsSort> queryUnitsSorts(Long sortId) {
		Map<String, Object> params = null;
		StringBuffer hqlbf = new StringBuffer();
		hqlbf.append("select us from TUnitsSort us where 1=1 ");
		if (null != sortId && sortId > 0) {
			hqlbf.append("and us.TUnitsSort.id = :sortId ");
			params = new HashMap<String, Object>();
			params.put("sortId", sortId);
		}else{
			hqlbf.append("and us.TUnitsSort is null ");
		}
		hqlbf.append("and us.markForDel='V'");
		return (List<TUnitsSort>) this.unitsSortDao.findObjectsByHql(hqlbf.toString(), params);
	}
	
	@Override
	public void saveUnitsSorts(Long id, List<TUnitsSort> sortList) {
		for (TUnitsSort sort : sortList) {
			if (null == sort.getTUnitsSort() || null ==sort.getTUnitsSort().getId()) {
				sort.setTUnitsSort(null);
				sort.setSortLevel(1l);
			}else{
				sort.setSortLevel(2l);
			}
		}
		this.unitsSortDao.saveObjectList(sortList);
	}
	
	@Override
	public int delUnitsSort(UnitsSortDto sortDto) {
		String hql = "update TUnitsSort set markForDel='D' where id in(:id) ";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", sortDto.getIds());
		unitsSortDao.executeHql(hql, params);
		return 1;
	}
	
	public CmsDao getCmsDao() {
		return cmsDao;
	}

	public void setCmsDao(CmsDao cmsDao) {
		this.cmsDao = cmsDao;
	}

	public UnitsInfoDao getUnitsInfoDao() {
		return unitsInfoDao;
	}

	public void setUnitsInfoDao(UnitsInfoDao unitsInfoDao) {
		this.unitsInfoDao = unitsInfoDao;
	}

	
}
