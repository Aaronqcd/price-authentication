package com.pemng.serviceSystem.mining.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pemng.serviceSystem.base.dao.BaseDaoHibernate;

public class MiningDaoImpl extends BaseDaoHibernate implements MiningDao {

	public MiningDaoImpl(){
		super(null);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> getMinNames(List<String> namesList) {
		
		StringBuilder sql = new StringBuilder("select new map(id as id, TMiningPriceSort.id as pId, sortName as sortName, TMiningPriceSort.sortName as pSortName) from TMiningPriceSort  ");
		sql.append("where sortName in(:names) and markForDel = 'V' ");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("names", namesList);
		return this.findObjectsByHql(sql.toString(), params);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> getMinUsers(List<String> usersList) {
		StringBuilder sql = new StringBuilder("select new map(id as id, name as name) from TUserInfo ");
		sql.append("where name in(:users) and markForDel = 'V' ");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("users", usersList);
		return this.findObjectsByHql(sql.toString(), params);
	}

}
