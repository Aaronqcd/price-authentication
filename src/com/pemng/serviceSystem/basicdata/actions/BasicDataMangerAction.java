package com.pemng.serviceSystem.basicdata.actions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.pemng.serviceSystem.base.actions.BaseAction;
import com.pemng.serviceSystem.base.util.CopyBeanUtils;
import com.pemng.serviceSystem.base.util.JsonUtil;
import com.pemng.serviceSystem.base.util.Pager;
import com.pemng.serviceSystem.base.util.PojoUtils;
import com.pemng.serviceSystem.base.util.WSUtils;
import com.pemng.serviceSystem.basicdata.dto.BasicDataDto;
import com.pemng.serviceSystem.basicdata.services.BasicDataMangerService;
import com.pemng.serviceSystem.common.Constants;
import com.pemng.serviceSystem.common.Result;
import com.pemng.serviceSystem.pojo.TBasicData;
import com.pemng.serviceSystem.pojo.TBasicDataCnt;

public class BasicDataMangerAction extends BaseAction {
	private static final long serialVersionUID = 6075692462205082827L;
	
	private BasicDataMangerService basicDataMangerService;
	private BasicDataDto saveDto = new BasicDataDto();
	private BasicDataDto viewDto = new BasicDataDto();
	private List<TBasicDataCnt> basicDataList;
	private List<TBasicData> dataList;
	private Pager pager = new Pager();
	private Long[] delIds;
	private String msg;
	
	public String viewCodeList() {
		if (null != viewDto) {
			dataList = basicDataMangerService.basicDataList(pager, viewDto);
		}
		return SUCCESS;
	}

	/**
	 * 保存列表
	 * @return
	 */
	public String saveBasicData(){
		basicDataMangerService.saveBasicData(viewDto.getId(),basicDataList);
		return SUCCESS;
	}
	public String viewCodeContent() {
		basicDataList = basicDataMangerService.basicDataCntList(pager, viewDto);
		return SUCCESS;
	}

	public String insertBasicData() {
		try {
			if (checkKey()) {
				TBasicDataCnt content = (TBasicDataCnt) PojoUtils.convert2Pojo(saveDto);
				content.setMarkForDel(Constants.MARK_FOR_DELETE_NO);
				basicDataMangerService.addBasicData(content);
				this.writeJsonToResponse(JsonUtil.object2json((new Result("success"))));
				//重新set数据字典
				WSUtils.resetDataMap(basicDataMangerService.getAllDataMap());
			} else
				this.writeJsonToResponse(JsonUtil.object2json((new Result("error"))));
		} catch (Exception e) {
		}

		return null;
	}

	// 列表查询
	public String queryBasicData() {
		basicDataList = basicDataMangerService.basicDataCntList(pager, viewDto);
		return SUCCESS;
	}

	// 修改查询
	public String queryBasic() {
		TBasicDataCnt TBasicData = basicDataMangerService.queryBasic(Long.parseLong(viewDto.getIds()));
		viewDto = (BasicDataDto) PojoUtils.convert2Dto(BasicDataDto.class, TBasicData);
		viewDto.setIds(TBasicData.getTBasicData().getId().toString());
		return SUCCESS;
	}

	//	 修改
	public String modifyBasicData() {
		try {
			if (checkKey()) {
				TBasicDataCnt TBasicData = basicDataMangerService.queryBasic(saveDto.getId());
				CopyBeanUtils.copyPropsWithoutNull(TBasicData, saveDto);
				basicDataMangerService.modifyBasicData(TBasicData);
				this.writeJsonToResponse(JsonUtil.object2json((new Result("success"))));
				//重新set数据字典
				WSUtils.resetDataMap(basicDataMangerService.getAllDataMap());
			} else
				this.writeJsonToResponse(JsonUtil.object2json((new Result("error"))));
		} catch (IOException e) {
		}
		return null;
	}

	// 删除
	public String deleteBasic() {
		String newString = viewDto.getDelIds().replace(" ", "");
		String basicIds[] = newString.split(",");
		List basicList = new ArrayList();
		TBasicDataCnt basic = new TBasicDataCnt();
		for (int i = 0; i < basicIds.length; i++) {
			// userInfo.setId(Long.parseLong(userIds[i]));
			if(StringUtils.isEmpty(basicIds[i]))
				continue;
			basic = basicDataMangerService.queryBasic(Long.parseLong(basicIds[i]));
			basic.setMarkForDel(Constants.MARK_FOR_DELETE_YES);
			basicList.add(basic);
		}

		basicDataMangerService.deleteBasicData(basicList);
		//重新set数据字典
		WSUtils.resetDataMap(basicDataMangerService.getAllDataMap());
		return SUCCESS;
	}

	private boolean checkKey() {
		saveDto.setTheKey(this.parseStringParameter("theKey"));
		TBasicData TBasicData = new TBasicData();
		TBasicData.setId(this.parseLongParameter("dataId"));
		saveDto.setTBasicData(TBasicData);
		saveDto.setId(this.parseLongParameter("basicId"));
		saveDto.setValue(this.parseStringParameter("value1"));
		saveDto.setValue2(this.parseStringParameter("value2"));
		saveDto.setValue3(this.parseStringParameter("value3"));
		// 验证唯一性
		List keyInfo = basicDataMangerService.checkTheKey(saveDto.getTheKey(), saveDto.getTBasicData().getId(), saveDto.getId());
		if (keyInfo.isEmpty()) {
			return true;
		} else {
			return false;
		}
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Long[] getDelIds() {
		return delIds;
	}

	public void setDelIds(Long[] delIds) {
		this.delIds = delIds;
	}

	public Pager getPager() {
		return pager;
	}

	public void setPager(Pager pager) {
		this.pager = pager;
	}

	public BasicDataDto getSaveDto() {
		return saveDto;
	}

	public void setSaveDto(BasicDataDto saveDto) {
		this.saveDto = saveDto;
	}

	public BasicDataMangerService getbasicDataMangerService() {
		return basicDataMangerService;
	}

	public void setbasicDataMangerService(BasicDataMangerService basicDataMangerService) {
		this.basicDataMangerService = basicDataMangerService;
	}

	public List<TBasicDataCnt> getBasicDataList() {
		return basicDataList;
	}

	public void setBasicDataList(List<TBasicDataCnt> basicDataList) {
		this.basicDataList = basicDataList;
	}

	public BasicDataDto getViewDto() {
		return viewDto;
	}

	public void setViewDto(BasicDataDto viewDto) {
		this.viewDto = viewDto;
	}

	public List getDataList() {
		return dataList;
	}

	public void setDataList(List dataList) {
		this.dataList = dataList;
	}

}
