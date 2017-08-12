package com.pemng.serviceSystem.units.services;

import java.util.List;

import com.pemng.serviceSystem.base.util.Pager;
import com.pemng.serviceSystem.pojo.TCommission;
import com.pemng.serviceSystem.pojo.TUnitsInfo;
import com.pemng.serviceSystem.pojo.TUnitsSort;
import com.pemng.serviceSystem.units.dto.UnitsInfoDto;
import com.pemng.serviceSystem.units.dto.UnitsSortDto;

public interface UnitsManagerService {
	/**
	 * 获取单位信息列表
	 * @param page
	 * @param unitsSortDto
	 * @return
	 */
	public List unitsList(Pager page,UnitsInfoDto unitsInfoDto);

	/**
	 * 获取单位列表
	 * @param page
	 * @param unitsSortDto
	 * @return
	 */
	public List<TUnitsSort> getSortList();
	
	/**
	 * 获取三级单位列表
	 * @param page
	 * @param unitsSortDto
	 * @return
	 */
	public List<TUnitsSort> getThreeList();
	
	/**
	 * 删除单位
	 * @param page
	 * @param unitsSortDto
	 * @return
	 */
	public void deleteUnit(UnitsInfoDto unitsInfoDto);

	/**
	 * 获取单位信息
	 * @param deptId
	 * @return 
	 */
	public TUnitsInfo getUnits(Long id);
	
	/**
	 * 修改单位信息
	 * @param deptDto
	 */
	public void modifyUnits(UnitsInfoDto unitsInfoDto);
	
	/**
	 * 新增单位信息
	 * @param deptDto
	 */
	public void addUnits(UnitsInfoDto unitsInfoDto);
	
	/**
	 * 获取单位信息和分类,并以json形式返回
	 * @return String
	 */
	public String getUnitSelectValue();
	
	/**
	 * 获取所有单位信息和分类,并以json形式返回
	 * @return String
	 */
	public String getUnitSelectValueAll();
	
	
	public int countCmsUnits(Long[] cmsUnitID);
	
	public int countUnitsInfo(Long[] sortsId);
	/**
	 * 查询单位类型列表
	 * @return
	 */
	public List<TUnitsSort> queryUnitsSorts(Long sortId);

	/**
	 * 保存单位类型
	 * @param id
	 * @param sortList
	 */
	public void saveUnitsSorts(Long id, List<TUnitsSort> sortList);
	
	/**
	 * 删除单位
	 * @param sortDto
	 * @return
	 */
	public int delUnitsSort(UnitsSortDto sortDto);
	
	
}
