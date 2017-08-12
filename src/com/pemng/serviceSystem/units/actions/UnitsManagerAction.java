package com.pemng.serviceSystem.units.actions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.pemng.serviceSystem.base.actions.BaseAction;
import com.pemng.serviceSystem.base.util.JsonUtil;
import com.pemng.serviceSystem.base.util.Pager;
import com.pemng.serviceSystem.base.util.PojoUtils;
import com.pemng.serviceSystem.common.Result;
import com.pemng.serviceSystem.pojo.TCommission;
import com.pemng.serviceSystem.pojo.TUnitsInfo;
import com.pemng.serviceSystem.pojo.TUnitsSort;
import com.pemng.serviceSystem.units.dto.UnitsInfoDto;
import com.pemng.serviceSystem.units.dto.UnitsSortDto;
import com.pemng.serviceSystem.units.services.UnitsManagerService;

public class UnitsManagerAction extends BaseAction {
	private UnitsManagerService unitsManagerService;
	private UnitsInfoDto unitsDto = new UnitsInfoDto();
	private UnitsSortDto sortDto = new UnitsSortDto();
	private UnitsInfoDto saveDto = new UnitsInfoDto();
	private TUnitsInfo dto = new UnitsInfoDto();
	private List<TUnitsSort> sonList;
	private List<TUnitsSort> sortList;
	private List<TUnitsSort> threeList;
	private List unitsInfoList;
	private Pager pager = new Pager();
	private int flag;

	/**
	 * 根据条件查询单位列表
	 * 
	 * @return
	 */
	public String unitsList() {
		List<TUnitsSort> sortJsonList = unitsManagerService.getSortList();
		sortList = new ArrayList<TUnitsSort>();
		for (int i = 0; i < sortJsonList.size(); i++) {
			TUnitsSort sort = sortJsonList.get(i);
				if (null == sort.getTUnitsSort()) {
					// 放第一级
					sortList.add(sort);
				}
			
		}
		sortJsonList.removeAll(sortList);
		sonList = sortJsonList;
		unitsInfoList = unitsManagerService.unitsList(pager, unitsDto);
		return SUCCESS;
	}

	/**
	 * 修改单位信息
	 * 
	 * @return
	 * @throws IOException
	 */
	public String modifyUnit() {
		if (null != saveDto) {
			unitsManagerService.modifyUnits(saveDto);
		}
		return SUCCESS;
	}

	/**
	 * 进入新增页面
	 * 
	 * @return
	 * @throws IOException
	 */
	public String preAdd() {
		List<TUnitsSort> sortJsonList = unitsManagerService.getSortList();
		sortList = new ArrayList<TUnitsSort>();
		for (int i = 0; i < sortJsonList.size(); i++) {
			TUnitsSort sort = sortJsonList.get(i);
				if (sort.getTUnitsSort() == null) {
					// 放第一级
					sortList.add(sort);
				}
			
		}
		sortJsonList.removeAll(sortList);
		sonList = sortJsonList;
		return SUCCESS;
	}

	/**
	 * 新增单位信息
	 * 
	 * @return
	 * @throws IOException
	 */
	public String addUnit() {
		if (null != saveDto) {
			unitsManagerService.addUnits(saveDto);
		}
		return SUCCESS;
	}

	/**
	 * 得到单位信息
	 * 
	 * @return
	 * @throws IOException
	 */

	public String getUnit() {
		List<TUnitsSort> sortJsonList = unitsManagerService.getSortList();
		sortList = new ArrayList<TUnitsSort>();
		for (int i = 0; i < sortJsonList.size(); i++) {
			TUnitsSort sort = sortJsonList.get(i);
				if (null == sort.getTUnitsSort()) {
					// 放第一级
					sortList.add(sort);
				}
		}
		sortJsonList.removeAll(sortList);
		sonList = sortJsonList;
		Long id = Long.parseLong(this.getRequest().getParameter("id"));
		dto = unitsManagerService.getUnits(id);
		saveDto = (UnitsInfoDto) PojoUtils.convert2Dto(UnitsInfoDto.class, dto);
		return SUCCESS;
	}

	/**
	 * 删除选中的单位
	 * 
	 * @return
	 * @throws IOException
	 */
	public void deleteUnit() {
		int count = 0;
		if (null != unitsDto && null != unitsDto.getIds()) {
			count = unitsManagerService.countCmsUnits(unitsDto.getIds());
		}else{
			count = -1;
		}
		try{
			if(count > 0){
				this.writeJsonToResponse(JsonUtil.object2json((new Result("1"))));
			}else if(count == -1){
				this.writeJsonToResponse(JsonUtil.object2json((new Result("-1"))));
			}else{
				unitsManagerService.deleteUnit(unitsDto);
				this.writeJsonToResponse(JsonUtil.object2json((new Result("0"))));
			}
		} catch (IOException e) {
			this.log.error(e, e);
		}
	}
	
	/**
	 * 查询单位类型
	 * @return
	 */
	public String queryUnitsSorts() {
		if (null != sortDto) {
			sortList = this.unitsManagerService.queryUnitsSorts(sortDto.getId());
		}
		return SUCCESS;
	}
	
	/**
	 * 保存单位类型
	 * @return
	 */
	public void saveUnitsSort(){
		try {
			if (null != sortList) {
				this.unitsManagerService.saveUnitsSorts(sortDto.getId(),sortList);
			}
			this.writeJsonToResponse(JsonUtil.object2json((new Result("1"))));
		} catch (IOException e) {
			this.log.error(e, e);
		}
	}
	
	/**
	 * 删除选中的单位
	 * 
	 * @return
	 * @throws IOException
	 */
	public void deleteUnitSort() {
		int count = 0;
		if (null != sortDto && null != sortDto.getIds()) {
			count = unitsManagerService.countUnitsInfo(sortDto.getIds());
		}else{
			count = -1;
		}
		try{
			if(count > 0){
				this.writeJsonToResponse(JsonUtil.object2json((new Result("1"))));
			}else if(count == -1){
				this.writeJsonToResponse(JsonUtil.object2json((new Result("-1"))));
			}else{
				unitsManagerService.delUnitsSort(sortDto);
				this.writeJsonToResponse(JsonUtil.object2json((new Result("0"))));
			}
		} catch (IOException e) {
			this.log.error(e, e);
		}
	}

	public UnitsSortDto getSortDto() {
		return sortDto;
	}

	public void setSortDto(UnitsSortDto sortDto) {
		this.sortDto = sortDto;
	}

	public TUnitsInfo getDto() {
		return dto;
	}

	public void setDto(TUnitsInfo dto) {
		this.dto = dto;
	}

	public List<TUnitsSort> getSortList() {
		return sortList;
	}

	public void setSortList(List<TUnitsSort> sortList) {
		this.sortList = sortList;
	}

	public List<TUnitsSort> getSonList() {
		return sonList;
	}

	public void setSonList(List<TUnitsSort> sonList) {
		this.sonList = sonList;
	}

	public UnitsManagerService getUnitsManagerService() {
		return unitsManagerService;
	}

	public void setUnitsManagerService(UnitsManagerService unitsManagerService) {
		this.unitsManagerService = unitsManagerService;
	}

	public UnitsInfoDto getUnitsDto() {
		return unitsDto;
	}

	public void setUnitsDto(UnitsInfoDto unitsDto) {
		this.unitsDto = unitsDto;
	}

	public UnitsInfoDto getSaveDto() {
		return saveDto;
	}

	public void setSaveDto(UnitsInfoDto saveDto) {
		this.saveDto = saveDto;
	}

	public List getUnitsInfoList() {
		return unitsInfoList;
	}

	public void setUnitsInfoList(List unitsInfoList) {
		this.unitsInfoList = unitsInfoList;
	}

	public Pager getPager() {
		return pager;
	}

	public void setPager(Pager pager) {
		this.pager = pager;
	}

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

	public List<TUnitsSort> getThreeList() {
		return threeList;
	}

	public void setThreeList(List<TUnitsSort> threeList) {
		this.threeList = threeList;
	}

}
