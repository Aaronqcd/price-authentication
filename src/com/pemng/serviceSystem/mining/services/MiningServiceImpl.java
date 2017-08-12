package com.pemng.serviceSystem.mining.services;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.pemng.serviceSystem.base.services.BaseServiceImpl;
import com.pemng.serviceSystem.base.util.DateUtil;
import com.pemng.serviceSystem.base.util.Pager;
import com.pemng.serviceSystem.base.util.PojoUtils;
import com.pemng.serviceSystem.base.util.excelparser.ExcelReaderUtil;
import com.pemng.serviceSystem.base.util.excelparser.NoSuchSheetException;
import com.pemng.serviceSystem.common.Constants;
import com.pemng.serviceSystem.mining.dao.MiningDao;
import com.pemng.serviceSystem.mining.dto.MinDto;
import com.pemng.serviceSystem.mining.dto.ZTreeObj;
import com.pemng.serviceSystem.pojo.TMiningPriceInfo;
import com.pemng.serviceSystem.pojo.TMiningPriceSort;
import com.pemng.serviceSystem.pojo.TUserInfo;

public class MiningServiceImpl extends BaseServiceImpl implements MiningService {

	private MiningDao miningDao;
	
	@SuppressWarnings("unchecked")
	public List<ZTreeObj> getMiningPriceSorts(){
		StringBuffer hql = new StringBuffer()
		.append("select new com.pemng.serviceSystem.mining.dto.ZTreeObj(")
		.append("ps.id||'' as id, coalesce(pps.id, 0)||'' as pid, ps.sortName as name")
		.append(") from TMiningPriceSort ps left join ps.TMiningPriceSort pps ")
		.append("where ps.markForDel = :markForDel ")
		.append("order by ps.id asc ");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("markForDel", Constants.MARK_FOR_DELETE_NO);
		return (List<ZTreeObj>)miningDao.findObjectsByHql(hql.toString(), params); 
	}
	
	@SuppressWarnings("unchecked")
	public TMiningPriceSort addMiningSort(TMiningPriceSort sort){
		if (sort.getTMiningPriceSort()!=null && sort.getTMiningPriceSort().getId()!=null && sort.getTMiningPriceSort().getId()<=0) {
			sort.setTMiningPriceSort(null);
		}
		sort.setMarkForDel(Constants.MARK_FOR_DELETE_NO);
		return (TMiningPriceSort) miningDao.saveObject(sort);
	}
	
	@SuppressWarnings("unchecked")
	public void renameMiningSort(Long id,String newName){
		TMiningPriceSort sort = (TMiningPriceSort) miningDao.getObject(TMiningPriceSort.class, id);
		sort.setSortName(newName);
		miningDao.saveObject(sort);
	}
	
	@SuppressWarnings("unchecked")
	public void removeMiningSort(Long id){
		TMiningPriceSort sort = (TMiningPriceSort) miningDao.getObject(TMiningPriceSort.class, id);
		sort.setMarkForDel(Constants.MARK_FOR_DELETE_YES);
		int i=0;
		Long[] ids = new Long[sort.getTMiningPriceSorts().size()];
		for(TMiningPriceSort s : sort.getTMiningPriceSorts()){//获得字节点ID
			ids[i]=s.getId();
			i++;
		}
		miningDao.saveObject(sort);
		for(Long sortId : ids){//删除子几点
			removeMiningSort(sortId);
		}
	}
	
	@SuppressWarnings("unchecked")
	public void removeMining(Long id){
		TMiningPriceInfo sort = (TMiningPriceInfo) miningDao.getObject(TMiningPriceInfo.class, id);
		sort.setMarkForDel(Constants.MARK_FOR_DELETE_YES);
		miningDao.saveObject(sort);
	}
	
	@SuppressWarnings("unchecked")
	public List<TMiningPriceInfo> getMiningPriceList(Pager pager, Map<String, Object> params){
		StringBuilder hql = new StringBuilder(" from TMiningPriceInfo where markForDel = :markForDel ");
		params.put("markForDel", Constants.MARK_FOR_DELETE_NO);
		if(params.get("sortName")!=null){
			hql.append(" and TMiningPriceSort.sortName = :sortName");
		}
		if(params.get("sortId")!=null && !params.get("sortId").toString().equals("0")){
			hql.append(" and TMiningPriceSort.id = :sortId");
		}
		if(params.get("beginTime")!=null){
			hql.append(" and miningDate >= :beginTime");
		}
		if(params.get("endTime")!=null){
			hql.append(" and miningDate <= :endTime");
		}
		if(params.get("userId")!=null){
			hql.append(" and TUserInfo.id = :userId");
		}
		if(params.get("address")!=null){
			hql.append(" and miningAddress like :address");
		}
		if(params.get("name")!=null){
			hql.append(" and name like :name");
		}
		hql.append(" order by miningDate desc,crtTime desc ");
		pager = miningDao.pagedQuery(hql.toString(), pager, params);
		return pager.getData();
		//限制200条
		//return (List<TMiningPriceInfo>) miningDao.findObjectsByHql(hql.toString(), params, 0, 200, null);
	}
	
	@SuppressWarnings("unchecked")
	public int getMiningCount(Map<String, Object> params){
		StringBuilder hql = new StringBuilder("select count(*) from TMiningPriceInfo where markForDel = :markForDel");
		params.put("markForDel", Constants.MARK_FOR_DELETE_NO);
		if(params.get("sortId")!=null){
			hql.append(" and TMiningPriceSort.id = :sortId");
		}
		return ((Number)miningDao.uniqueResult(hql.toString(), params)).intValue();
	}
	
	@SuppressWarnings("unchecked")
	public int getMiningSortNameCount(String sortName, Long id){
		Map<String, Object> params = new HashMap<String, Object>();
		StringBuilder hql = new StringBuilder("select count(*) from TMiningPriceSort mps where 1 = 1 ")
		.append("and mps.sortName = :sortName and mps.markForDel = :markForDel and sortName != :bakSortName ");
		if (null != id) {
			hql.append("and mps.id != :mid ");
			params.put("mid", id);
		}
		params.put("bakSortName", "备注");	//除了备注其他的都不可以重复
		params.put("sortName", sortName);
		params.put("markForDel", "V");
		return ((Number)miningDao.uniqueResult(hql.toString(), params)).intValue();
	}
	
	@SuppressWarnings("unchecked")
	public void addMiningPrice(TMiningPriceInfo miningPriceInfo){
		miningDao.saveObject(miningPriceInfo);
	}
	
	@SuppressWarnings("unchecked")
	public void updateMiningPrice(TMiningPriceInfo miningPriceInfo){
		TMiningPriceInfo obj = (TMiningPriceInfo) miningDao.getObject(TMiningPriceInfo.class, miningPriceInfo.getId());
		
		obj.setMiningAddress(miningPriceInfo.getMiningAddress());
		obj.setMiningDate(miningPriceInfo.getMiningDate());
		obj.setMiningPrice(miningPriceInfo.getMiningPrice());
		obj.setReferenceDate(miningPriceInfo.getReferenceDate());
		obj.setRemark(miningPriceInfo.getRemark());
		obj.setSource(miningPriceInfo.getSource());
		obj.setStatus(miningPriceInfo.getStatus());
		obj.setSortName(miningPriceInfo.getSortName());
		obj.setName(miningPriceInfo.getName());
		obj.setTypeName(miningPriceInfo.getTypeName());
		obj.setGuidePrice(miningPriceInfo.getGuidePrice());
//		obj.setTMiningPriceSort(TMiningPriceSort);
		obj.setTUserInfo(miningPriceInfo.getTUserInfo());
		miningDao.saveObject(obj);
	}
	
	public TMiningPriceInfo getMiningPrice(Long id){
		return (TMiningPriceInfo) miningDao.getObject(TMiningPriceInfo.class, id);
	}
	
	@SuppressWarnings("unchecked")
	public List<TUserInfo> getUserList(){
		Map<String, Object> params=new HashMap<String, Object>();
		String hql=" select t from TUserInfo t where t.markForDel=:markForDel order by t.id desc";
		params.put("markForDel", Constants.MARK_FOR_DELETE_NO);
		return (List<TUserInfo>) miningDao.findObjectsByHql(hql, params);
	}
	
	@Override
	public TMiningPriceSort getTMiningPriceSort(Long id) {
		if (null != id && id > 0) {
			return (TMiningPriceSort) miningDao.getObjectByClass(TMiningPriceSort.class,id);
		}else {
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public String impMinings(File f, String fileName) throws FileNotFoundException, IOException, NoSuchSheetException {
		String message = new String();
		StringBuilder result = new StringBuilder();
		TMiningPriceInfo mpi = null;
		ExcelReaderUtil excelUtil = new ExcelReaderUtil();
		List<MinDto> mprList = new ArrayList<MinDto>();
		List<TMiningPriceInfo> mprInfoList = new ArrayList<TMiningPriceInfo>();
		Workbook book =null;
		excelUtil.createWorkBook(fileName, new FileInputStream(f));
		book = excelUtil.getWorkbook();
		int sheetNum = 1;//book.getNumberOfSheets();
		
		StringBuilder minNames = new StringBuilder();	//采价目录名称
		StringBuilder minUsers = new StringBuilder();	//采价人
		for (int i = 0; i < sheetNum; i++) {
			excelUtil.locateSheet(i);
			Sheet sheet=excelUtil.getSheet();
			int rowNum = excelUtil.getLastRowNum();					//获取行数
			boolean isValidate;
			boolean isEnd = false;
			if(sheet != null){
				if(rowNum>0){
					int header=sheet.getRow(0).getLastCellNum();	//获取头标题的列数
					if(header != 9){
						throw new NoSuchSheetException("列数不对，请使用采价模板");
					}
					for(int j = 1; j < rowNum+1; j++){				//从第一行开始  
						isValidate = true;
						MinDto minDto = new MinDto();				//采价信息
						for (int m=0;m<header;m++) {
							String obj=excelUtil.getOrigianlValue(j, m);
							if (null != obj && obj.trim().equals("end")) {
								rowNum = j;
								isEnd = true;
								break;
							}
							if (null == obj || StringUtils.isBlank(obj.replaceAll("\\s+", ""))) {
								obj = null;
								if (m == 0 || m == 4 || m == 5 || m == 8) {
									isValidate = false;
									break;
								}
							}else{
								obj = obj.replaceAll("\\s+", "");
							}
							if (m == 0) {		//目录名称(*)
								obj = obj.replaceAll("/", ",");
								minDto.setMinNames(obj);
								minNames.append(obj).append(",");
							}else if(m ==1){	//采价地点
								minDto.setMiningAddress(obj);
							}else if(m ==2){	//采价人
								minDto.setMinUsers(obj);
								minUsers.append(obj).append(",");
							}else if(m ==3){	//价格准日
								try {
									if (obj.matches("\\d{4}/\\d{1,2}/\\d{1,2}")) {
										minDto.setReferenceDate(DateUtil.getDateFromString(obj, "yyyy/MM/dd"));
									}
								} catch (Exception e) {
								}
							}else if(m ==4){	//采价日期(*)
								try {
									if (obj.matches("\\d{4}/\\d{1,2}/\\d{1,2}")) {
										minDto.setMiningDate(DateUtil.getDateFromString(obj, "yyyy/MM/dd"));
									}
								} catch (Exception e) {
								}
							}else if(m ==5){	//分类（品名）(*)
								minDto.setName(obj);
							}else if(m ==6){	//型号类型(*)
								minDto.setTypeName(obj);
							}else if(m ==7){	//厂家指导价
								minDto.setGuidePrice(obj);
							}else if(m ==8){	//经销商价格(*)
								minDto.setMiningPrice(obj);
							}
							minDto.setMarkForDel("V");
						}
						if (!isEnd) {
							if (isValidate) {
								mprList.add(minDto);
							}else{
								result.append("第").append(j+1).append("行").append("，");
							}
						}
					}
				}
			}
		}
		if (result.length() > 0) {
			if (mprList.size() <= 0) {
				message = "导入失败,原因为目录名称、采价日期、分类（品名）、经销商价格为必填,请确认！";
			}else{
				message = "导入失败,原因为" + result + "目录名称、采价日期、分类（品名）、经销商价格为必填,请确认！";
			}
			return message;
		}
		//查询目录名称
		Map<String, Long> minNameMap = new HashMap<String, Long>();
		if (minNames.length() > 0) {
			List<String> minNamesList = Arrays.asList(minNames.toString().split(","));  
			List<Map<String, Object>> listMap = this.miningDao.getMinNames(minNamesList);
			for (Map<String, Object> map : listMap) {
				minNameMap.put(MapUtils.getString(map, "pSortName", "") + MapUtils.getString(map, "sortName", ""), MapUtils.getLongValue(map, "id"));
			}
		}
		
		//查询采价人
		Map<String, Long> minUserMap = new HashMap<String, Long>();
		if (minUsers.length() > 0) {
			List<String> minUsersList = Arrays.asList(minUsers.toString().split(","));  
			List<Map<String, Object>> listMap = this.miningDao.getMinUsers(minUsersList);
			for (Map<String, Object> map : listMap) {
				minUserMap.put(MapUtils.getString(map, "name"), MapUtils.getLongValue(map, "id"));
			}
		}
		//赋值并保存导入信息
		Long tSortId = null;
		Long tUserId = null;
		String[] minFl = null;
		String minName = null;
		for (MinDto mdto : mprList) {
			//标的物目录
			minFl = mdto.getMinNames().split(",");
			if (minFl.length == 1) {
				minName = minFl[0];
			}else{
				minName = minFl[minFl.length-2] + minFl[minFl.length-1];
			}
			tSortId = MapUtils.getLongValue(minNameMap, minName);
			if (tSortId > 0) {
				TMiningPriceSort ps = new TMiningPriceSort();
				ps.setId(tSortId);
				mdto.setTMiningPriceSort(ps);
			}else{
				continue;
			}
			//采价人
			tUserId = MapUtils.getLongValue(minUserMap, mdto.getMinUsers());
			if (tUserId > 0) {
				TUserInfo ui = new TUserInfo();
				ui.setId(tUserId);
				mdto.setTUserInfo(ui);
			}
			mpi=(TMiningPriceInfo) PojoUtils.convert2Pojo(mdto);
			mpi.setStatus("1");	//状态
			mprInfoList.add(mpi);
		}
		this.miningDao.saveObjectList(mprInfoList);
		return message;
	}
	
	public MiningDao getMiningDao() {
		return miningDao;
	}

	public void setMiningDao(MiningDao miningDao) {
		this.miningDao = miningDao;
	}
	
}
