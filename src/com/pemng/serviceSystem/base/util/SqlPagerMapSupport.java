package com.pemng.serviceSystem.base.util;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.springframework.orm.hibernate3.HibernateCallback;

public class SqlPagerMapSupport implements HibernateCallback {

	private String sql = null;
	private String countSql = null;
	private Pager pager = null;
	private Class resultClass = null;

	public SqlPagerMapSupport(String countSql, String sql, Class resultClass, Pager pager) {
		this.countSql = countSql;
		this.sql = sql;
		this.resultClass = resultClass;
		this.pager = pager;
	}

	public SqlPagerMapSupport(String sql, Class resultClass) {
		this.sql = sql;
		this.resultClass = resultClass;
	}

	public List doInHibernate(Session session) throws HibernateException, SQLException {
		
		SQLQuery query = null;
		if (pager == null) {
			query = session.createSQLQuery(sql);
			//query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		} else {

			query = session.createSQLQuery(countSql);
			query.addScalar("count(*)", Hibernate.BIG_DECIMAL);
			int totalSize = ((BigDecimal) query.uniqueResult()).intValue();
			pager.rebuild(totalSize);
			query = session.createSQLQuery(sql);
			query.setFirstResult((pager.getCurrentPage() - 1) * pager.getPageSize());
			query.setMaxResults(pager.getPageSize());
		}
		if (resultClass != null){
			return query.addEntity(resultClass).list();
		}else{
			query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			List <Map<String,Object>> returnList = new ArrayList<Map<String,Object>>();
			List<Map<String,Object>> list = query.list();
			if(null == list || list.size()== 0) return null;
			for(Map<String,Object> map : list){
				Map<String, Object> dataMap = new HashMap<String, Object>();
				for(Map.Entry<String,Object> entity : map.entrySet()){
					dataMap.put(entity.getKey().toLowerCase(), entity.getValue());
				}
				returnList.add(dataMap);
			}
			return returnList;
		}
		
	}

}
