package com.pemng.serviceSystem.base.util;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

public class HqlPageSupport implements HibernateCallback {

	private String hql = null;
	private String countHql = null;
	private Pager pager = null;

	public HqlPageSupport(String countHql, String hql, Pager pager) {
		this.countHql = countHql;
		this.hql = hql;
		this.pager = pager;
	}

	public Object doInHibernate(Session session) throws HibernateException, SQLException {
		List countList = session.createQuery(countHql).list();
		Long total = (Long) countList.get(0);
		pager.rebuild(total.intValue());
		Query query = session.createQuery(hql);
		query.setFirstResult((pager.getCurrentPage() - 1) * pager.getPageSize());
		query.setMaxResults(pager.getPageSize());
		return query.list();
	}

}
