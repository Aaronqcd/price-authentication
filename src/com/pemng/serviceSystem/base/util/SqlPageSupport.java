package com.pemng.serviceSystem.base.util;

import java.math.BigDecimal;
import java.sql.SQLException;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

public class SqlPageSupport implements HibernateCallback {

	private String sql = null;
	private String countSql = null;
	private Pager pager = null;
	private Class resultClass = null;

	public SqlPageSupport(String countSql, String sql, Class resultClass, Pager pager) {
		this.countSql = countSql;
		this.sql = sql;
		this.resultClass = resultClass;
		this.pager = pager;
	}

	public SqlPageSupport(String sql, Class resultClass) {
		this.sql = sql;
		this.resultClass = resultClass;
	}

	public Object doInHibernate(Session session) throws HibernateException, SQLException {
		SQLQuery query = null;
		if (pager == null) {
			query = session.createSQLQuery(sql);
		} else {

			query = session.createSQLQuery(countSql);
			query.addScalar("count(*)", Hibernate.BIG_DECIMAL);
			int totalSize = ((BigDecimal) query.uniqueResult()).intValue();
			pager.rebuild(totalSize);
			query = session.createSQLQuery(sql);
			query.setFirstResult((pager.getCurrentPage() - 1) * pager.getPageSize());
			query.setMaxResults(pager.getPageSize());
		}
		if (resultClass != null)
			query.addEntity(resultClass);
		return query.list();
	}

}
