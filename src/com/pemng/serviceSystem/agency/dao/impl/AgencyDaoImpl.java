package com.pemng.serviceSystem.agency.dao.impl;

import com.pemng.serviceSystem.agency.dao.AgencyDao;
import com.pemng.serviceSystem.base.dao.BaseDaoHibernate;

public class AgencyDaoImpl extends BaseDaoHibernate implements AgencyDao {
	public AgencyDaoImpl() {
		super(null);
	}
	
	 /*	代办事项管理,含以下代办事项：
	 *	1.待受理委托书:st=01,achiv=0
	 *	2.待鉴定委托书:st=02,03,achiv=0 or c.st='08' or c.st='10' or c.st='12'
	 *	3.待初审委托书:st=07,achiv=0
	 * 	4.待复审委托书:st=09,achiv=0
	 * 	5.待终审委托书:st=11,achiv=0
	 *  6.待归档委托书:st=06,13,achiv=0
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Object cmsAgencyCount(){
		StringBuilder countHql =new StringBuilder("select new map( ")
			.append("coalesce(sum(case when c.st = '01' and c.achiv = '0' then 1 else 0 end),0) as con1, ")
			.append("coalesce(sum(case when (c.st = '02' or c.st = '03' or c.st='08' or c.st='10' or c.st='12') and c.achiv = '0' then 1 else 0 end),0) as con2, ")
			.append("coalesce(sum(case when c.st = '07' and c.achiv = '0' then 1 else 0 end),0) as con3,")
			.append("coalesce(sum(case when c.st = '09' and c.achiv = '0' then 1 else 0 end),0) as con4,")
			.append("coalesce(sum(case when c.st = '11' and c.achiv = '0' then 1 else 0 end),0) as con5,")
			.append("coalesce(sum(case when (c.st = '06' or c.st = '13') and achiv = '0' then 1 else 0 end),0) as con6)")
			.append("from TCommission c where c.markForDel = 'V' ");
		return this.uniqueResult(countHql.toString(), null);
	}

}
