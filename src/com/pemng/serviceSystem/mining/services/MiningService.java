package com.pemng.serviceSystem.mining.services;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.pemng.serviceSystem.base.services.BaseService;
import com.pemng.serviceSystem.base.util.Pager;
import com.pemng.serviceSystem.base.util.excelparser.NoSuchSheetException;
import com.pemng.serviceSystem.mining.dto.ZTreeObj;
import com.pemng.serviceSystem.pojo.TMiningPriceInfo;
import com.pemng.serviceSystem.pojo.TMiningPriceSort;
import com.pemng.serviceSystem.pojo.TUserInfo;

public interface MiningService extends BaseService {

	List<ZTreeObj> getMiningPriceSorts();

	TMiningPriceSort addMiningSort(TMiningPriceSort sort);

	void renameMiningSort(Long id, String newName);

	void removeMiningSort(Long id);

	List<TMiningPriceInfo> getMiningPriceList(Pager pager, Map<String, Object> params);

	void addMiningPrice(TMiningPriceInfo miningPriceInfo);

	void updateMiningPrice(TMiningPriceInfo miningPriceInfo);

	List<TUserInfo> getUserList();

	TMiningPriceInfo getMiningPrice(Long id);

	void removeMining(Long id);

	int getMiningCount(Map<String, Object> params);

	/**
	 * 判断标的物目录是否已经存在
	 * @param params
	 * @return
	 */
	public int getMiningSortNameCount(String sortName, Long id);
	
	TMiningPriceSort getTMiningPriceSort(Long id);
	
	/**
	 * 保存采价信息
	 * @param f
	 * @return
	 */
	public String impMinings(File f, String fileName) throws FileNotFoundException, IOException ,NoSuchSheetException;
	
}
