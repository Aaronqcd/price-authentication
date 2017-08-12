package com.pemng.serviceSystem.base.dao;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Filter;
import org.hibernate.HibernateException;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.StatelessSession;
import org.hibernate.Transaction;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.impl.CriteriaImpl;
import org.hibernate.sql.JoinFragment;
import org.hibernate.transform.Transformers;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.pemng.serviceSystem.base.pojo.BasePO;
import com.pemng.serviceSystem.base.util.BeanUtils;
import com.pemng.serviceSystem.base.util.HqlPageSupport;
import com.pemng.serviceSystem.base.util.Pager;
import com.pemng.serviceSystem.base.util.PojoUtils;
import com.pemng.serviceSystem.base.util.SqlPageSupport;
import com.pemng.serviceSystem.base.util.SqlPagerMapSupport;
import com.pemng.serviceSystem.common.Constants;
import com.pemng.serviceSystem.common.WebInfo;
import com.pemng.serviceSystem.common.WebInfoMgmt;

/**
 * DAO通用实现，共大多数情况下DAO继承使用
 * 
 * @author shaojie
 * 
 * @param <T>
 *            泛型类型
 */
public class BaseDaoHibernate<T extends BasePO> extends HibernateDaoSupport
		implements Dao<T> {
	transient private static Log log = LogFactory
			.getLog(BaseDaoHibernate.class);

	private Class<T> persistentClass;

	public BaseDaoHibernate(){
	}
	
	public BaseDaoHibernate(Class<T> persistentClass) {
		this.persistentClass = persistentClass;
	}

	@SuppressWarnings("unchecked")
	public List<T> findObjects(DaoFindPack<T> daoFindPack) {

		return findObjects((T) daoFindPack.getExample(), daoFindPack
				.getExampleMap(),
				new Criterion[] { daoFindPack.getCriterion() }, daoFindPack
						.getOrders(), 0, 0, daoFindPack.isLikeSearch(),
				daoFindPack.getProjection(), null, daoFindPack.getFetchPacks());

	}

	@SuppressWarnings("unchecked")
	public List<T> findObjects(DaoFindPack<T> daoFindPack, PagePack pagePack) {

		return findObjects((T) daoFindPack.getExample(), daoFindPack
				.getExampleMap(),
				new Criterion[] { daoFindPack.getCriterion() }, daoFindPack
						.getOrders(), pagePack.getStartIndex(), pagePack
						.getPageSize(), daoFindPack.isLikeSearch(), daoFindPack
						.getProjection(), null, daoFindPack.getFetchPacks());

	}

	public int getRowsCount(DaoFindPack<T> daoFindPack) {

		return getRowsCount(daoFindPack.getExample(), daoFindPack
				.getExampleMap(),
				new Criterion[] { daoFindPack.getCriterion() }, daoFindPack
						.isLikeSearch());
	}

	/**
	 * 
	 * 
	 * @param o
	 * @param exampleMap
	 * @param cri
	 * @param order
	 * @param startRow
	 * @param pageSize
	 * @param likeSearch
	 *            true: anywhere, false: exact
	 * @param projection
	 * @param sortFieldNames
	 *            field names to be sort by, as
	 *            "partProj.csfPrincipal.staffName"
	 * @parm ascending sort order true-ascend false-descend
	 * @return
	 */
	public List<T> findObjects(final T o, final Map exampleMap,
			final Criterion cri[], final Order order[], final int startRow,
			final int pageSize, final boolean likeSearch,
			final Projection projection, final SortPack sortPack) {

		return findObjects(o, exampleMap, cri, order, PagePack.create(startRow,
				pageSize), likeSearch, projection, sortPack);
	}

	public List<T> findObjects(final T o, final Map exampleMap,
			final Criterion cri[], final Order order[], final int startRow,
			final int pageSize, final boolean likeSearch,
			final Projection projection, final SortPack sortPack,
			final FetchPack[] fetchPacks) {
		return findObjects(o, exampleMap, cri, order, PagePack.create(startRow,
				pageSize), likeSearch, projection, sortPack, fetchPacks);
	}

	public ResultPack findObjectsWithCount(final T example,
			final Map exampleMap, final Criterion cri[],
			final boolean likeSearch, final DaoOptionPack daoOptionPack) {

		switch (daoOptionPack.getGetMode()) {
		case DaoOptionPack.GET_ALL:
			return new ResultPack(findObjects(example, exampleMap, cri,
					daoOptionPack.getOrders(), daoOptionPack.getOffsetIndex(),
					daoOptionPack.getPageSize(), likeSearch, null),
					getRowsCount(example, exampleMap, cri, likeSearch));

		case DaoOptionPack.GET_COUNT:
			return new ResultPack(new ArrayList(), getRowsCount(example,
					exampleMap, cri, likeSearch));

		case DaoOptionPack.GET_LIST:
			return new ResultPack(findObjects(example, exampleMap, cri,
					daoOptionPack.getOrders(), daoOptionPack.getOffsetIndex(),
					daoOptionPack.getPageSize(), likeSearch, null), -1);
		default:
			return null;
		}

	}

	public ResultPack findObjectsWithCount(final Criterion cri[],
			DaoOptionPack daoOptionPack) {

		return findObjectsWithCount(null, null, cri, false, daoOptionPack);

	}

	public ResultPack findObjectsWithCount(final T example,
			final Criterion cri, DaoOptionPack daoOptionPack) {

		return findObjectsWithCount(example, null, new Criterion[] { cri },
				true, daoOptionPack);

	}

	public List<T> findObjects(final T o, final Map exampleMap,
			final Criterion cri[], final Order order[],
			final PagePack pagePack, final boolean likeSearch,
			final Projection projection, final SortPack sortPack) {
		return findObjects(o, exampleMap, cri, order, pagePack, likeSearch,
				projection, sortPack, null);
	}

	/**
	 * 
	 * 
	 * @param o
	 * @param exampleMap
	 * @param cri
	 * @param order
	 * @param pagePack
	 * @param likeSearch
	 *            true: anywhere, false: exact
	 * @param projection
	 * @param sortFieldNames
	 *            field names to be sort by, as
	 *            "partProj.csfPrincipal.staffName"
	 * @parm ascending sort order true-ascend false-descend
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<T> findObjects(final T o, final Map exampleMap,
			final Criterion cri[], final Order order[],
			final PagePack pagePack, final boolean likeSearch,
			final Projection projection, final SortPack sortPack,
			final FetchPack[] fetchPacks) {

		// filter on properties set in the component
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {

				Criteria cr = session.createCriteria(persistentClass);

				if (pagePack != null) {

					if (pagePack.getPageSize() != 0)
						cr.setMaxResults(pagePack.getPageSize());

					cr.setFirstResult(pagePack.getStartIndex());

				}
				// Add Criterions
				if (cri != null) {
					for (int i = 0; i < cri.length; i++)
						if (cri[i] != null)
							cr.add(cri[i]);
				}

				// Add Orders
				if (order != null) {
					for (int i = 0; i < order.length; i++) {
						if (order[i] != null) {
							cr.addOrder(order[i]);
						}
					}
				}
				cr.addOrder(Order.desc("id"));

				Criteria crFirst = cr;
				// Add fieldname Orders as "a.b.c.d" May "duplicate path here"
				if (sortPack != null) {
					log.debug("field name:'" + sortPack.getFieldNames() + "'");

					String nameArray[] = sortPack.getFieldNames().split("\\.");
					Criteria sortCr = cr;
					for (int i = 0; i < nameArray.length - 1; i++) {

						// sortCr = sortCr.createCriteria(nameArray[i]);
						sortCr = sortCr.createCriteria(nameArray[i],
								JoinFragment.LEFT_OUTER_JOIN);
						if (i == 0)
							crFirst = sortCr; // 获取第一层的实例,以免在example中使用

					}

					sortCr.addOrder(sortPack.isAscending() ? Order
							.asc(nameArray[nameArray.length - 1]) : Order
							.desc(nameArray[nameArray.length - 1]));

				}

				// Add Example
				if (o != null) {
					Example ex = Example.create(o);
					if (likeSearch) {
						ex.ignoreCase().enableLike(MatchMode.ANYWHERE);
					}
					cr.add(ex);

				}

				// Add Associated-Object Examples
				if (exampleMap != null) {
					Iterator iter = exampleMap.entrySet().iterator();
					int mapSize = exampleMap.size();

					for (int i = 0; i < mapSize; i++) {
						Map.Entry entry = (Map.Entry) iter.next();
						String fieldName = (String) entry.getKey();
						Example example = Example.create(entry.getValue());

						if (likeSearch) {
							example.ignoreCase().enableLike(MatchMode.ANYWHERE);
						}

						if (sortPack != null
								&& sortPack.getFieldNames().startsWith(
										fieldName))
							crFirst.add(example);
						else
							cr.createCriteria(fieldName).add(example);

					}
				}

				if (projection != null)
					cr.setProjection(projection);

				// if (alias != null) {
				// for (int i = 0; i < alias.length; i++) {
				// AliasPack aliasPack = alias[i];
				// cr.createAlias(aliasPack.getProperty(), aliasPack
				// .getAlias(), aliasPack.getJoinType());
				// }
				// }

				if (fetchPacks != null) {
					for (FetchPack fetchPack : fetchPacks) {
						cr.setFetchMode(fetchPack.getProperty(), fetchPack
								.getFetchMode());
					}
				}

				return cr.list();
			}
		};

		return (List<T>) getHibernateTemplate().execute(callback);
	}

	/**
	 * 
	 * @param clazz
	 * @param o
	 * @param exampleMap
	 * @param cri
	 * @param order
	 * @param startRow
	 * @param pageSize
	 * @param likeSearch
	 *            true: anywhere, false: exact
	 * @param projection
	 * @return
	 */
	public List<T> findObjects(final T o, final Map exampleMap,
			final Criterion cri[], final Order order[], final int startRow,
			final int pageSize, final boolean likeSearch,
			final Projection projection) {

		return findObjects(o, exampleMap, cri, order, startRow, pageSize,
				likeSearch, projection, null);

	}

	public List<T> findObjects(final Criterion cri[], final PagePack pagePack,
			final SortPack sortPack) {

		return findObjects(null, null, cri, null, pagePack, false, null,
				sortPack);

	}

	public List<T> findObjects(final T o, final Map exampleMap,
			final Criterion cri[], final Order order[], final int startRow,
			final int pageSize) {
		return findObjects(o, exampleMap, cri, order, startRow, pageSize,
				false, null);
	}

	public List<T> findObjects(final T o, final Criterion cri[],
			final Order order[], final int startRow, final int pageSize,
			final boolean likeSearch) {
		return findObjects(o, null, cri, order, startRow, pageSize, likeSearch,
				null);
	}

	/**
	 * get all
	 */
	public List<T> findObjects() {

		return findObjects(null, null, null, null, 0, 0);

	}

	@SuppressWarnings("unchecked")
	public Iterator<T> findObjectsByIterator() {

		return getHibernateTemplate().iterate(
				"from " + persistentClass.getName());

	}

	/**
	 * get ordered all rows of the class
	 */
	public List<T> findObjects(final Order order[]) {

		return findObjects(null, null, null, order, 0, 0);
	}

	/**
	 * get data by examples
	 */
	public List<T> findObjects(final T o) {

		return findObjects(o, null, null, null, 0, 0);
	}

	/**
	 * get data by examples with page
	 */
	public List<T> findObjects(final T o, int startIndex, int pageSize) {

		return findObjects(o, null, null, null, startIndex, pageSize);
	}

	/**
	 * get data by example matchall
	 */
	public List<T> findObjects(final T o, boolean likeSearch) {

		return findObjects(o, null, null, null, 0, 0, true, null);
	}

	/**
	 * get ordered example search
	 */
	public List<T> findObjects(final T o, final Order order[]) {

		return findObjects(o, null, null, order, 0, 0);
	}

	/**
	 * 
	 * @param exampleMap
	 *            key : fieldName-比如条件在tpinfo.carType，则key为"carType" <p/> value :
	 *            条件对象- 已持久化(有id)的对象
	 * 
	 * 
	 * @return
	 */
	public List<T> findObjects(final Map exampleMap) {

		return findObjects(null, exampleMap, null, null, 0, 0);
	}

	/**
	 * 
	 * @param exampleMap
	 *            key : fieldName-比如条件在tpinfo.carType，则key为"carType" <p/> value :
	 *            条件对象- 已持久化(有id)的对象 *
	 * @param order
	 *            an array of order <p/> Example: Order
	 *            order[]={Order.asc("name")}
	 * @return
	 */
	public List<T> findObjects(final Map exampleMap, final Order order[]) {

		return findObjects(null, exampleMap, null, null, 0, 0);
	}

	/**
	 * 
	 * @param o
	 *            条件对象
	 * @param exampleMap
	 *            key : fieldName-比如条件在tpinfo.carType，则key为"carType" <p/> value :
	 *            条件对象- 已持久化(有id)的对象
	 * 
	 * 
	 * @return
	 */
	public List<T> findObjects(final T o, final Map exampleMap) {
		return findObjects(o, exampleMap, null, null, 0, 0);

	}

	/**
	 * 
	 * @param o
	 *            条件对象
	 * @param exampleMap
	 *            key : fieldName-比如条件在tpinfo.carType，则key为"carType" <p/> value :
	 *            条件对象- 已持久化(有id)的对象
	 * 
	 * 
	 * @param order
	 *            an array of order <p/> Example: Order
	 *            order[]={Order.asc("name")}
	 * @return
	 */
	public List<T> findObjects(final T o, final Map exampleMap,
			final Order order[]) {
		return findObjects(o, exampleMap, null, order, 0, 0);

	}

	/**
	 * 
	 * @param clazz
	 *            目标对象类型
	 * @param cri
	 *            Critertion 条件格式数组 <p/> Example: Criterion cri[] =
	 *            {Expression.in("belongDeptNum", departmentNums)};
	 * @return List
	 */
	public List<T> findObjects(final Criterion cri[]) {

		return findObjects(null, null, cri, null, 0, 0);
	}

	/**
	 * 
	 * @param clazz
	 *            目标对象类型
	 * @param cri
	 *            Critertion 条件格式数组 <p/> Example: Criterion cri[] =
	 *            {Expression.in("belongDeptNum", departmentNums)}; *
	 * @param order
	 *            an array of order <p/> Example: Order
	 *            order[]={Order.asc("name")}
	 * @return List
	 */
	public List<T> findObjects(final Criterion cri[], final Order order[]) {

		return findObjects(null, null, cri, order, 0, 0);
	}

	/**
	 * save any PO
	 */
	@SuppressWarnings("unchecked")
	public T saveObject(T o) throws DataAccessException {

		setSystemFieldsForSaveOrUpdate(o);
		T t = (T) getHibernateTemplate().merge(o);
//		o = t;
		return t;

	}
	/**
	 * save any PO
	 */
	@SuppressWarnings("unchecked")
	public Object saveObject(Object o) throws DataAccessException {

		setSystemFieldsForSaveOrUpdate(o);
		Object t = getHibernateTemplate().merge(o);
		return t;

	}

	private void setSystemFieldsForCreate(Object obj) {
		WebInfo webInfo = WebInfoMgmt.getWebInfo();
		if (webInfo != null) {
			try {
				if (webInfo.getWebUser() != null
						&& webInfo.getWebUser().getId() != null) {
					Method setCreateBy = obj.getClass().getMethod(
							"setCrtUsrId", new Class[] { Long.class });

					setCreateBy.invoke(obj, new Object[] { webInfo.getWebUser()
							.getId() });
				}

			} catch (NoSuchMethodException e) {
			} catch (SecurityException e) {
			} catch (IllegalArgumentException e) {
			} catch (IllegalAccessException e) {
			} catch (InvocationTargetException e) {
			}
		}
		try {
			Method setCreateDate = obj.getClass().getMethod("setCrtTime",
					new Class[] { Date.class });
			setCreateDate.invoke(obj, new Object[] { Calendar.getInstance()
					.getTime() });
		} catch (NoSuchMethodException e) {
		} catch (SecurityException e) {
		} catch (IllegalArgumentException e) {
		} catch (IllegalAccessException e) {
		} catch (InvocationTargetException e) {
		}
		//默认MarkForDel为"V"
		try {
			Method getMarkForDel = obj.getClass().getMethod("getMarkForDel",
					null);
			String markForDel = (String)getMarkForDel.invoke(obj, null);
			if(markForDel==null){
				Method setMarkForDel = obj.getClass().getMethod("setMarkForDel",
					String.class);
				setMarkForDel.invoke(obj, new Object[] {Constants.MARK_FOR_DELETE_NO });
			}
		} catch (NoSuchMethodException e) {
		} catch (SecurityException e) {
		} catch (IllegalArgumentException e) {
		} catch (IllegalAccessException e) {
		} catch (InvocationTargetException e) {
		}
	}

	private void setSystemFieldsForUpdate(Object obj) {
		WebInfo webInfo = WebInfoMgmt.getWebInfo();
		Object id = PojoUtils.getBean(obj, "getId");
		Object crtTime = PojoUtils.getBean(obj, "getCrtTime");
		Object crtUsrId = PojoUtils.getBean(obj, "getCrtUsrId");
		if(id!=null && (crtTime==null || crtUsrId==null)){//原创建时间
			try{
				Object o = getObject((Long)id);
				//清楚缓存
				flush();
				clear();
				
				if(o!=null){
					Object originalCrtTime = PojoUtils.getBean(o, "getCrtTime");
					Object originalCrtUsrId = PojoUtils.getBean(o, "getCrtUsrId");
					if(crtTime==null && originalCrtTime!=null){
						try {
							Method setCreateDate = obj.getClass().getMethod("setCrtTime",
									new Class[] { Date.class });
							setCreateDate.invoke(obj, new Object[] { originalCrtTime });
						} catch (NoSuchMethodException e) {
						} catch (SecurityException e) {
						} catch (IllegalArgumentException e) {
						} catch (IllegalAccessException e) {
						} catch (InvocationTargetException e) {
						}
					}
					if(crtUsrId==null && originalCrtUsrId!=null){//原创建人
						try {
							Method setCreateDate = obj.getClass().getMethod("setCrtUsrId",
									new Class[] { Long.class });
							setCreateDate.invoke(obj, new Object[] { originalCrtUsrId });
						} catch (NoSuchMethodException e) {
						} catch (SecurityException e) {
						} catch (IllegalArgumentException e) {
						} catch (IllegalAccessException e) {
						} catch (InvocationTargetException e) {
						}
					}
				}
			}catch(Exception e){
				
			}
		}
		if (webInfo != null) {
			try {
				if (webInfo.getWebUser() != null
						&& webInfo.getWebUser().getId() != null) {
					Method setCreateBy = obj.getClass().getMethod(
							"setUpdUsrId", new Class[] { Long.class });
					setCreateBy.invoke(obj, new Object[] { webInfo.getWebUser()
							.getId() });
				}
			} catch (NoSuchMethodException e) {
			} catch (SecurityException e) {
			} catch (IllegalArgumentException e) {
			} catch (IllegalAccessException e) {
			} catch (InvocationTargetException e) {
			}
		}
		try {
			Method setCreateDate = obj.getClass().getMethod("setUpdTime",
					new Class[] { Date.class });
			setCreateDate.invoke(obj, new Object[] { Calendar.getInstance()
					.getTime() });
		} catch (NoSuchMethodException e) {
		} catch (SecurityException e) {
		} catch (IllegalArgumentException e) {
		} catch (IllegalAccessException e) {
		} catch (InvocationTargetException e) {
		}
	}

	private void setSystemFieldsForSaveOrUpdate(Object obj) {
		WebInfo webInfo = WebInfoMgmt.getWebInfo();
		if (webInfo != null) {
			try {
				Method getId = obj.getClass()
						.getMethod("getId", new Class[] {});
				Long id = (Long) getId.invoke(obj, new Object[] {});
				if (id != null) {
					setSystemFieldsForUpdate(obj);
				} else {
					setSystemFieldsForCreate(obj);
				}
			} catch (NoSuchMethodException e) {
			} catch (SecurityException e) {
			} catch (IllegalArgumentException e) {
			} catch (IllegalAccessException e) {
			} catch (InvocationTargetException e) {
			}
		}
	}

	public void saveObjectList(List<T> list) throws DataAccessException {
		for (T o : list) {
			setSystemFieldsForSaveOrUpdate(o);
//			setSystemFieldsForCreate(o);
		}
		this.getHibernateTemplate().saveOrUpdateAll(list);
		// this.flush();
	}

	/**
	 * save any PO.不自动设置更新和创建信息
	 */
	@SuppressWarnings("unchecked")
	public T saveObjectPure(T o) throws DataAccessException {
//		T t = (T) getHibernateTemplate().merge(o);
		// this.flush();
		T t = getHibernateTemplate().merge(o);
		return t;

	}

	/**
	 * use this instead of saveOrUpdate becoz it does not reattach the passed
	 * instance.
	 * 
	 * @param o
	 * @throws DataAccessException
	 */
	public void updateObjectOnly(T o) throws DataAccessException {
		setSystemFieldsForUpdate(o);
		getHibernateTemplate().update(o);
		// this.flush();
	}

	/**
	 * get read only object, and won't save this object, used for reference to
	 * save another object with same ID get an object according to the class and
	 * its id
	 */
	@SuppressWarnings("unchecked")
	public T getReadOnlyObject(Serializable id)
			throws ObjectRetrievalFailureException {
		StatelessSession statelessSession = getHibernateTemplate()
				.getSessionFactory().openStatelessSession();
		Transaction tx = statelessSession.beginTransaction();
		T o = (T) statelessSession.get(persistentClass, id, LockMode.READ);
		tx.commit();
		statelessSession.close();
		return o;
	}

	/**
	 * get an object according to the class and its id
	 */
	@SuppressWarnings("unchecked")
	public T getObject(Serializable id) throws ObjectRetrievalFailureException {
		Object o = getHibernateTemplate().get(persistentClass, id);

		if (o == null) {
			throw new ObjectRetrievalFailureException(persistentClass, id);
		}

		return (T) o;
	}
	@Override
	public Object getObject(Class clazz, Serializable id) throws ObjectRetrievalFailureException {
		Object o = getHibernateTemplate().get(clazz, id);
		if (o == null) {
			throw new ObjectRetrievalFailureException(clazz, id);
		}
		return o;
	}

	/**
	 * get an object according to the class and its id
	 */
	@SuppressWarnings("unchecked")
	public T getObjectForUpdate(Serializable id)
			throws ObjectRetrievalFailureException {
		Object o = getHibernateTemplate().get(persistentClass, id,
				LockMode.UPGRADE_NOWAIT);

		if (o == null) {
			throw new ObjectRetrievalFailureException(persistentClass, id);
		}

		return (T) o;
	}

	/**
	 * remove the object with the specific class and id
	 */
	public void removeObject(Long id) throws DataAccessException {
		getHibernateTemplate().delete(getObject(id));
		// this.flush();
	}

	/**
	 * remove the object
	 */
	public void removeObject(T object) throws DataAccessException {
		getHibernateTemplate().delete(object);
		// this.flush();
	}

	/**
	 * remove the object
	 */
	public void removeObject(Object object) throws DataAccessException {
		getHibernateTemplate().delete(object);
		// this.flush();
	}
	
	public void removeAllObjects(Collection<T> objects) throws DataAccessException {
		getHibernateTemplate().deleteAll(objects);
		// this.flush();
	}
	
	

	/**
	 * 
	 * @param hql
	 *            a HQL String
	 * @return List contains the result
	 */
	public List<?> findObjectsByHql(String hql, Map<String, ?> params) {
		return this.findObjectsByHql(hql, params, 0, 0, null);
	}

	// public int getRowsCountByHql(String hql, Object[] params) {
	//
	// String hqlForCount = parseHqlCount(hql);
	// List countList = findObjectsByHql(hqlForCount, params);
	// return ((Long) countList.get(0)).intValue();
	//
	// }
	//	
	// public ResultPack findObjectsWithCountByHql(final String hql,
	// final Object[] params, final DaoOptionPack daoOptionPack) {
	//
	// switch (daoOptionPack.getGetMode()) {
	// case DaoOptionPack.GET_ALL:
	// return new ResultPack(findObjectsByHql(hql, params, daoOptionPack
	// .getOffsetIndex(), daoOptionPack.getPageSize(),
	// daoOptionPack.getOrders()), getRowsCountByHql(hql, params));
	//
	// case DaoOptionPack.GET_COUNT:
	// return new ResultPack(new ArrayList(), getRowsCountByHql(hql,
	// params));
	//
	// case DaoOptionPack.GET_LIST:
	// return new ResultPack(findObjectsByHql(hql, params, daoOptionPack
	// .getOffsetIndex(), daoOptionPack.getPageSize(),
	// daoOptionPack.getOrders()), -1);
	// default:
	// return null;
	// }
	//
	// }

	// public List<?> findObjectsByHql(final String hql, final Object[] params,
	// final int offsetIndex, final int pageSize, final Order[] orders) {
	//
	// List list = getHibernateTemplate().executeFind(new HibernateCallback() {
	// public Object doInHibernate(Session session) throws SQLException {
	//
	// String finalHql = hql;
	//
	// if (orders != null) {
	// finalHql += generateHqlOrderClause(orders);
	//
	// }
	//
	// Query query = session.createQuery(finalHql);
	//
	// if (params != null) {
	// for (int i = 0; i < params.length; i++) {
	// query.setParameter(i, params[i]);
	// }
	// }
	//
	// if (pageSize != 0) {
	// query.setFirstResult(offsetIndex);
	// query.setMaxResults(pageSize);
	// }
	//
	// List result = query.list();
	// return result;
	// }
	// });
	//
	// return list;
	//
	// }

	public int getRowsCountByHql(final String fromJoinSubClause,
			final String[] whereBodies, final Map<String, ?> params) {
		List countList = findObjectsByHql(null, fromJoinSubClause, whereBodies,
				params, 0, 0, null, true, null);
		return ((Long) countList.get(0)).intValue();

	}

	public int getRowsCountByHql(final String fromJoinSubClause,
			final String[] whereBodies, final Map<String, ?> params,
			String distinctName) {
		List countList = findObjectsByHql(null, fromJoinSubClause, whereBodies,
				params, 0, 0, null, true, distinctName);
		return ((Long) countList.get(0)).intValue();

	}

	/**
	 * get the param name from a where body
	 * 
	 * @param ori
	 *            like "id = :idinput"
	 * @return "idinput"
	 */
	private String getWhereBodyParamName(String ori) {
		if (!ori.contains(":")) {
			return null;
		}

		String[] oris = ori.split("[:()]");
		if (oris.length == 1) {
			return null;
		} else {
			return oris[oris.length - 1].trim();
		}
	}

	public List<Map<String, ?>> findObjectsByHql(
			final HqlQueryEntry[] hqlQueryEntries,
			final String fromJoinSubClause, final String[] whereBodies,
			final Map<String, ?> params, final int offsetIndex,
			final int pageSize, final Order[] orders, boolean isDistinct) {
		return findObjectsByHql(hqlQueryEntries, fromJoinSubClause,
				whereBodies, params, offsetIndex, pageSize, orders, false,
				isDistinct ? "1" : null);
	}

	public List<Map<String, ?>> findObjectsByHql(
			final HqlQueryEntry[] hqlQueryEntries,
			final String fromJoinSubClause, final String[] whereBodies,
			final Map<String, ?> params, final int offsetIndex,
			final int pageSize, final Order[] orders) {
		return findObjectsByHql(hqlQueryEntries, fromJoinSubClause,
				whereBodies, params, offsetIndex, pageSize, orders, false, null);
	}

	private List<Map<String, ?>> findObjectsByHql(
			final HqlQueryEntry[] hqlQueryEntries,
			final String fromJoinSubClause, final String[] whereBodies,
			final Map<String, ?> params, final int offsetIndex,
			final int pageSize, final Order[] orders, final boolean isGetCount,
			final String distinctName) {

		List list = getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session) throws SQLException {

				StringBuffer sb = new StringBuffer();
				if (hqlQueryEntries != null) {
					sb.append("select ");
					if (distinctName != null) {
						sb.append("distinct ");
					}

					// ->"select new map( ...)"
					sb.append(HqlQueryEntry.generateQueryMap(hqlQueryEntries));
				}

				// ->"select new map( ...) from .. join ...
				sb.append(" ").append(fromJoinSubClause);

				if (whereBodies != null) {
					boolean isHaveWhere = false;

					for (String whereBody : whereBodies) {
						String paramName = getWhereBodyParamName(whereBody);
						if (paramName == null
								|| (params != null && params.get(paramName) != null)) {
							if (!isHaveWhere) {
								isHaveWhere = true;
								sb.append(" where ");
							} else {
								sb.append(" and ");
							}
							sb.append("(").append(whereBody).append(")");

						}
					}
				}

				if (!isGetCount && orders != null) {
					sb.append(generateHqlOrderClause(orders));

				}

				String finalHql = sb.toString();

				if (isGetCount) {
					finalHql = parseHqlCount(finalHql, distinctName);
				}

				log.debug("hql:" + finalHql);
				Query query = session.createQuery(finalHql);

				if (params != null) {
					for (Entry<String, ?> entry : params.entrySet()) {
						Object value = entry.getValue();
						if (value != null) {
							if (value instanceof Object[]) {
								query.setParameterList(entry.getKey(),
										(Object[]) value);

							} else if (value instanceof Collection<?>) {
								query.setParameterList(entry.getKey(),
										(Collection<?>) value);

							} else {
								query.setParameter(entry.getKey(), value);
							}

						}
					}
				}

				if (pageSize != 0) {
					query.setFirstResult(offsetIndex);
					query.setMaxResults(pageSize);
				}

				List result = query.list();
				return result;
			}
		});

		return list;

	}

	@Override
	public Object uniqueResult(final String hql, final Map<String, ?> params){
		return getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws SQLException {
				String finalHql = hql;
				log.debug("hql:" + finalHql);
				Query query = session.createQuery(finalHql);

				if (params != null) {
					for (Entry<String, ?> entry : params.entrySet()) {
						Object value = entry.getValue();
						if (value != null) {
							if (value instanceof Object[]) {
								query.setParameterList(entry.getKey(),
										(Object[]) value);
							} else if (value instanceof Collection<?>) {
								query.setParameterList(entry.getKey(),
										(Collection<?>) value);

							} else {
								query.setParameter(entry.getKey(), value);
							}

						}
					}
				}
				return query.uniqueResult();
			}
		});
	}
	
	public Object executeHql(final String hql, final Map<String, ?> params) {

		return getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws SQLException {

				String finalHql = hql;

				log.debug("hql:" + finalHql);
				Query query = session.createQuery(finalHql);

				if (params != null) {
					for (Entry<String, ?> entry : params.entrySet()) {
						Object value = entry.getValue();
						if (value != null) {
							if (value instanceof Object[]) {
								query.setParameterList(entry.getKey(),
										(Object[]) value);

							} else if (value instanceof Collection<?>) {
								query.setParameterList(entry.getKey(),
										(Collection<?>) value);

							} else {
								query.setParameter(entry.getKey(), value);
							}

						}
					}
				}
				return query.executeUpdate();
			}
		});

	}

	public Object executeSql(final String Sql) {

		return getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws SQLException {

				String finalSql = Sql;

				log.debug("sql:" + finalSql);
				Query query = session.createSQLQuery(finalSql);
				return query.executeUpdate();
			}
		});

	}

	public List<?> findObjectBySql(final String Sql) {

		return (List<?>) getHibernateTemplate().execute(
				new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws SQLException {

						String finalSql = Sql;

						log.debug("sql:" + finalSql);
						Query query = session.createSQLQuery(finalSql);
						return query.list();
					}
				});

	}

	// add by shijiachen 20100705 begin
	public List<Map<String, ?>> findObjectsBySql(
			final HqlQueryEntry[] hqlQueryEntries,
			final String fromJoinSubClause, final String[] whereBodies,
			final Map<String, ?> params, final int offsetIndex,
			final int pageSize, final Order[] orders) {

		List list = getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session) throws SQLException {

				StringBuffer sb = new StringBuffer();
				sb.append("select ");

				if (hqlQueryEntries != null) {

					// ->"select new map( ...)"
					sb.append(StringUtils.join(hqlQueryEntries, ", "));
				} else {
					sb.append(" count(*) ");
				}

				sb.append(fromJoinSubClause);

				if (whereBodies != null) {
					boolean isHaveWhere = false;

					for (String whereBody : whereBodies) {
						String paramName = getWhereBodyParamName(whereBody);
						if (paramName == null
								|| (params != null && params.get(paramName) != null)) {
							if (!isHaveWhere) {
								isHaveWhere = true;
								sb.append(" where ");
							} else {
								sb.append(" and ");
							}
							sb.append("(").append(whereBody).append(")");

						}
					}
				}

				String finalSql = sb.toString();

				log.debug("sql:" + finalSql);
				Query query = session.createSQLQuery(sb.toString())
						.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);// 返回一个map,KEY:为DB中名称一致

				if (params != null) {
					for (Entry<String, ?> entry : params.entrySet()) {
						Object value = entry.getValue();
						if (value != null) {
							if (value instanceof Object[]) {
								query.setParameterList(entry.getKey(),
										(Object[]) value);

							} else if (value instanceof Collection<?>) {
								query.setParameterList(entry.getKey(),
										(Collection<?>) value);

							} else {
								query.setParameter(entry.getKey(), value);
							}

						}
					}
				}

				if (pageSize != 0) {
					query.setFirstResult(offsetIndex);
					query.setMaxResults(pageSize);
				}

				return query.list();
			}
		});

		return list;

	}
	
	// add by shijiachen 20100705 begin
	public List<Map<String, ?>> findObjectsBySql(final String Sql, final Map<String, ?> params) {

		List list = getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session) throws SQLException {
				String finalSql = Sql;
				log.debug("sql:" + finalSql);
				Query query = session.createSQLQuery(finalSql.toString())
						.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);// 返回一个map,KEY:为DB中名称一致
				if (params != null) {
					for (Entry<String, ?> entry : params.entrySet()) {
						Object value = entry.getValue();
						if (value != null) {
							if (value instanceof Object[]) {
								query.setParameterList(entry.getKey(),
										(Object[]) value);

							} else if (value instanceof Collection<?>) {
								query.setParameterList(entry.getKey(),
										(Collection<?>) value);

							} else {
								query.setParameter(entry.getKey(), value);
							}
						}
					}
				}
				return query.list();
			}
		});
		return list;
	}

	public List<?> findObjectsBySql(final String sql,
			final Map<String, ?> params, final int offsetIndex,
			final int pageSize, final Order[] orders) {

		List list = getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session) throws SQLException {
					
				String finalSql = sql;

				if (orders != null) {
					finalSql += generateHqlOrderClause(orders);

				}
				log.debug("sql:" + finalSql);
				Query query = session.createSQLQuery(finalSql);

				if (params != null) {
					for (Entry<String, ?> entry : params.entrySet()) {
						Object value = entry.getValue();
						if (value != null) {
							if (value instanceof Object[]) {
								query.setParameterList(entry.getKey(),
										(Object[]) value);

							} else if (value instanceof Collection<?>) {
								query.setParameterList(entry.getKey(),
										(Collection<?>) value);

							} else {
								query.setParameter(entry.getKey(), value);
							}

						}
					}
				}

				if (pageSize != 0) {
					query.setFirstResult(offsetIndex);
					query.setMaxResults(pageSize);
				}

				List result = query.list();
				return result;
			}
		});

		return list;

	}
	// 

	public List<?> findObjectsByHql(final String hql,
			final Map<String, ?> params, final int offsetIndex,
			final int pageSize, final Order[] orders) {

		List list = getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session) throws SQLException {

				String finalHql = hql;

				if (orders != null) {
					finalHql += generateHqlOrderClause(orders);

				}
				log.debug("hql:" + finalHql);
				Query query = session.createQuery(finalHql);

				if (params != null) {
					for (Entry<String, ?> entry : params.entrySet()) {
						Object value = entry.getValue();
						if (value != null) {
							if (value instanceof Object[]) {
								query.setParameterList(entry.getKey(),
										(Object[]) value);

							} else if (value instanceof Collection<?>) {
								query.setParameterList(entry.getKey(),
										(Collection<?>) value);

							} else {
								query.setParameter(entry.getKey(), value);
							}

						}
					}
				}

				if (pageSize != 0) {
					query.setFirstResult(offsetIndex);
					query.setMaxResults(pageSize);
				}

				List result = query.list();
				return result;
			}
		});

		return list;

	}

	public int getRowsCountByHql(String hql, Map<String, ?> params) {

		String hqlForCount = parseHqlCount(hql, null);
		List countList = findObjectsByHql(hqlForCount, params, 0, 0, null);
		if(countList.isEmpty()) //分组函数时会为空
		    return 0;
		return ((Long) countList.get(0)).intValue();

	}

	public int getRowsCountByHql(String hql, Map<String, ?> params,
			String distinctName) {

		String hqlForCount = parseHqlCount(hql, distinctName);
		List countList = findObjectsByHql(hqlForCount, params, 0, 0, null);
	     if(countList.isEmpty()) //分组函数时会为空
	            return 0;
		return ((Long) countList.get(0)).intValue();

	}

	// add by shijiachen 20100706 begin
	public int getRowsCountBySql(String sql, final String[] whereBodies,
			Map<String, ?> params) {
		List<Map<String, ?>> countList = findObjectsBySql(null, sql,
				whereBodies, params, 0, 0, null);
		return ((BigDecimal) countList.get(0).values().iterator().next())
				.intValue();
	}

	// add by shijiachen 20100706 end

	private String generateHqlOrderClause(Order[] orders) {

		boolean isFirst = true;
		StringBuffer stringBuffer = new StringBuffer();
		for (Order order : orders) {
			if (order != null) {
				if (isFirst) {
					stringBuffer.append(" ORDER BY ");
					isFirst = false;

				} else {
					stringBuffer.append(", ");
				}

				stringBuffer.append(order.toString());

			}

		}

		return stringBuffer.toString();
	}

	/**
	 * Parse a original select item hql to a select count hql
	 * 
	 * @param originalHql
	 *            Have a form of "select ... from ... ..." or "from ..."
	 * @return transform to "select count(*) from ... ..."
	 */
	private String parseHqlCount(String originalHql, String distinctName) {

		String loweredOriginalHql = originalHql.toLowerCase();
		int beginPos = loweredOriginalHql.indexOf(" from ");
		if (beginPos == -1) {
			// should have a " from "
			throw new IllegalArgumentException("not a valid hql string");
		}
		String countField = null;
		if (distinctName != null) {
			countField = "distinct " + distinctName;
		} else {
			countField = "*";
		}
		//去除排序
		if(loweredOriginalHql.indexOf(" order ") != -1){
			originalHql = originalHql.substring(0,loweredOriginalHql.indexOf(" order "));
		}
		
		return "select count("
				+ countField
				+ ")"
				+ originalHql.substring(beginPos).replaceAll("join fetch ",
						"join ");

	}

	/**
	 * 
	 * @param detachedCri
	 *            a DetachedCriteria
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<T> findObjectsByCri(DetachedCriteria detachedCri) {

		return getHibernateTemplate().findByCriteria(detachedCri);
	}

	@SuppressWarnings("unchecked")
	public List<T> findObjectsByCri(DetachedCriteria detachedCri,
			PagePack pagePack) {

		if (pagePack == null)
			return findObjectsByCri(detachedCri);
		else
			return getHibernateTemplate().findByCriteria(detachedCri,
					pagePack.getStartIndex(), pagePack.getPageSize());
	}

	/**
	 * 
	 * @param detachedCri
	 *            a DetachedCriteria
	 * @param firstResult
	 *            can use to pagination
	 * @param maxResult
	 *            can use to pagenation
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<T> findObjectsByCri(DetachedCriteria detachedCri,
			int firstResult, int maxResult) {

		return getHibernateTemplate().findByCriteria(detachedCri, firstResult,
				maxResult);
	}

	/**
	 * 根据条件获得rowcount数
	 * 
	 * 
	 * 
	 * @param clazz
	 * @param o
	 * @param exampleMap
	 * @param cri
	 * @param searchLike
	 *            true-anywhere false-exact
	 * @return int,the row count for the result set
	 */
	public int getRowsCount(final T o, final Map exampleMap,
			final Criterion cri[], boolean searchLike) {
		List<T> results = findObjects(o, exampleMap, cri, null, 0, 0,
				searchLike, Projections.rowCount());
		Object result = results.get(0);

		return ((Long) result).intValue();
	}

	public int getRowsCount(final Criterion cri[]) {

		return getRowsCount(null, null, cri, false);
	}

	/*
	 * private String getSubString(String[] stringArray, int no) { StringBuffer
	 * out = new StringBuffer(); for (int i = 0; i < no; i++) {
	 * out.append(stringArray[i]); } return new String(out); }
	 */

	public void evict(T object) {

		getHibernateTemplate().evict(object);

	}

	public void flush() {

		getHibernateTemplate().flush();
	}
	public void clear(){
		getHibernateTemplate().clear();
	}

	public static void main(String[] args) {
		BaseDaoHibernate<BasePO> dao = new BaseDaoHibernate<BasePO>(
				BasePO.class);
		Order[] orders = new Order[] { Order.asc("asc1"), Order.desc("des1"),
				Order.desc("des2") };

		System.out.println(dao.generateHqlOrderClause(orders));
		System.out.println(dao.getWhereBodyParamName("asdf in (:sdf)"));
	}

	/**
	 * 2013.10.22修改
	 * getHibernateTemplate().getSessionFactory().openSession() 替换getHibernateTemplate().getSessionFactory().getCurrentSession();
	 * 
	 * function: 获取指定Sequence的新值。
	 * 
	 * 
	 * @param sequenceName
	 * @return
	 */
	public long getSequence(String sequenceName) {
		long newSeq = 0;
		Session session = getHibernateTemplate().getSessionFactory().openSession();
		getHibernateTemplate().getSessionFactory().close();
				//.getCurrentSession();
		String sql = "select " + sequenceName + ".Nextval from dual";
		try {
			Connection con = session.connection();
			
			ResultSet rs = con.createStatement().executeQuery(sql);
			while (rs.next()) {
				newSeq = rs.getLong(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return newSeq;
	}

	public Filter enableFilter(String name) {
		return getHibernateTemplate().enableFilter(name);
	}

	public void executeBatch(List<String> sqlList) throws SQLException {
		Session session = getHibernateTemplate().getSessionFactory()
				.getCurrentSession();

		Connection con = session.connection();
		Statement stmt = con.createStatement();
		for (String strSql : sqlList) {
			stmt.addBatch(strSql);
		}
		stmt.executeBatch();
	}
	
	protected Criteria createCriteria(Class clazz, Criterion[] criterions) {
		Criteria criteria = getSession().createCriteria(clazz);
		if (criterions != null) {
			for (int i = 0, len = criterions.length; i < len; i++) {
				criteria.add(criterions[i]);
			}
		}
		return criteria;
	}

	protected Criteria createCriteria(Class clazz, Criterion[] criterions,
			LockMode lockMode) {
		Criteria criteria = getSession().createCriteria(clazz);
		if (criterions != null) {
			for (int i = 0, len = criterions.length; i < len; i++) {
				criteria.add(criterions[i]);
			}
		}
		criteria.setLockMode(lockMode);
		return criteria;
	}

	protected Criteria createCriteria(Class clazz, String orderBy,
			boolean isAsc, Criterion[] criterions) {
		Criteria criteria = createCriteria(clazz, criterions);

		if (isAsc)
			criteria.addOrder(Order.asc(orderBy));
		else
			criteria.addOrder(Order.desc(orderBy));

		return criteria;
	}

	protected Criteria createCriteria(Class clazz, String orderBy,
			boolean isAsc, Criterion[] criterions, String fetchAttr,
			FetchMode fetchMode) {
		Criteria criteria = createCriteria(clazz, orderBy, isAsc, criterions);

		criteria.setFetchMode(fetchAttr, fetchMode);

		return criteria;
	}
	
	public List<T> queryList(String countHql, String hql, Pager pager) {
		return getHibernateTemplate().executeFind(
				new HqlPageSupport(countHql, hql, pager));
	}

	public List<T> queryListSql(String countSql, String sql, Pager pager) {
		return getHibernateTemplate().executeFind(
				new SqlPageSupport(countSql, sql, persistentClass, pager));
	}
	public List<?> queryListSql(String countSql, String sql, Pager pager,Class c) {
		return getHibernateTemplate().executeFind(
				new SqlPageSupport(countSql, sql, c, pager));
	}

	public Pager pagedQuery(String hql, Pager pager, Object[] values) {
		String countQueryString = " select count (*) "
				+ checkLeftJoinFetch(removeSelect(removeOrders(hql)));
		List countlist = getHibernateTemplate().find(countQueryString,
				(values == null || values.length == 0) ? null : values);
		long totalCount = Long.valueOf(countlist.get(0).toString()).longValue();

		if (totalCount < 1)
			return pager;

		int startIndex = pager.getStartOfPage();
		Query query = createQuery(hql, values);
		List list = query.setFirstResult(startIndex).setMaxResults(
				pager.getPageSize()).list();

		pager.setData(list);
		pager.setTotalSize(totalCount);

		return pager;
	}

	public Pager pagedQuery(String hql, Pager pager, Map<String, ?> params) {
		long totalCount = this.getRowsCountByHql(hql, params);

		if (totalCount < 1)
			return pager;

		int startIndex = pager.getStartOfPage();
		List list = findObjectsByHql(hql, params, startIndex, pager.getPageSize(), null);

		pager.setData(list);
		pager.setTotalSize(totalCount);
		return pager;
	}
	public Pager pagedQuery(String hql, Pager pager, Map<String, ?> params,String distinctName) {
		long totalCount = this.getRowsCountByHql(hql, params,distinctName);

		if (totalCount < 1)
			return pager;

		int startIndex = pager.getStartOfPage();
		List list = findObjectsByHql(hql, params, startIndex, pager.getPageSize(), null);

		pager.setData(list);
		pager.setTotalSize(totalCount);

		return pager;
	}
	public Pager pagedQueryBySql(String sql, Pager pager, Map<String, ?> params) {
		String sqlCount = parseHqlCount(sql, null);
		List totalCountList = this.findObjectsBySql(sqlCount, params,0,0,null);
		int totalCount=0;
		if(totalCountList!=null){
			if(totalCountList.get(0) instanceof BigDecimal){
				totalCount = ((BigDecimal) totalCountList.get(0)).intValue();
			}else{
				totalCount = ((Integer) totalCountList.get(0)).intValue();
			}
		}

		if (totalCount < 1)
			return pager;

		int startIndex = pager.getStartOfPage();
		List list = findObjectsBySql(sql, params, startIndex, pager.getPageSize(), null);

		pager.setData(list);
		pager.setTotalSize(totalCount);

		return pager;
	}
	public Pager pagedQueryBySql(String sql, Pager pager, Map<String, ?> params,String distinctName) {
		String sqlCount = parseHqlCount(sql, distinctName);
		List totalCountList = this.findObjectsBySql(sqlCount, params,0,0,null);
		int totalCount=0;
		if(totalCountList!=null){
			if(totalCountList.get(0) instanceof BigDecimal){
				totalCount = ((BigDecimal) totalCountList.get(0)).intValue();
			}else{
				totalCount = ((Integer) totalCountList.get(0)).intValue();
			}
		}

		if (totalCount < 1)
			return pager;

		int startIndex = pager.getStartOfPage();
		List list = findObjectsBySql(sql, params, startIndex, pager.getPageSize(), null);

		pager.setData(list);
		pager.setTotalSize(totalCount);

		return pager;
	}
	
	public Pager pagedQuery(Class clazz, Pager page, Criterion[] criterions) {
		Criteria criteria = createCriteria(clazz, criterions);
		return pagedQuery(criteria, page);
	}

	public Pager pagedQuery(Class clazz, Pager page, Criterion[] criterions,
			String orderBy, boolean isAsc) {
		Criteria criteria = createCriteria(clazz, orderBy, isAsc, criterions);
		return pagedQuery(criteria, page);
	}

	public Pager pagedQuery(Class clazz, Pager page, Criterion[] criterions,
			String orderBy, boolean isAsc, String fetchAttr, FetchMode fetchMode) {
		Criteria criteria = createCriteria(clazz, orderBy, isAsc, criterions,
				fetchAttr, fetchMode);
		return pagedQuery(criteria, page);
	}

	public Pager pagedQuery(Criteria criteria, Pager page) {
		CriteriaImpl impl = (CriteriaImpl) criteria;
		Projection projection = impl.getProjection();
		List orderEntries;
		try {
			orderEntries = (List) BeanUtils.forceGetProperty(impl,
					"orderEntries");
			BeanUtils.forceSetProperty(impl, "orderEntries", new ArrayList());
		} catch (Exception e) {
			throw new InternalError(" Runtime Exception impossibility throw ");
		}

		long totalCount = Long.valueOf(
				criteria.setProjection(Projections.rowCount()).uniqueResult()
						.toString()).longValue();

		criteria.setProjection(projection);
		if (projection == null) {
			criteria.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);
		}

		try {
			BeanUtils.forceSetProperty(impl, "orderEntries", orderEntries);
		} catch (Exception e) {
			throw new InternalError(" Runtime Exception impossibility throw ");
		}

		if (totalCount < 1)
			return page;

		int startIndex = page.getStartOfPage();
		List list = criteria.setFirstResult(startIndex).setMaxResults(
				page.getPageSize()).list();

		page.setData(list);
		page.setTotalSize(totalCount);

		return page;
	}

	protected Query createQuery(String hql, Object[] values) {
		Query query = getSession().createQuery(hql);
		int len = values == null ? 0 : values.length;
		for (int i = 0; i < len; i++) {
			query.setParameter(i, values[i]);
		}
		return query;

	}

	private String checkLeftJoinFetch(String hql) {
		final String regex = "left join fetch";
		final String replacement = "left join";
		String temp = hql;
		while (temp.indexOf(regex) > 0) {
			temp = temp.replaceAll(regex, replacement);
		}
		return temp;
	}

	private static String removeSelect(String hql) {
		int beginPos = hql.toLowerCase().indexOf("from");
		return hql.substring(beginPos);
	}

	private static String removeOrders(String hql) {
		Pattern p = Pattern.compile("order\\s*by[\\w|\\W|\\s|\\S]*",
				Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(hql);
		StringBuffer sb = new StringBuffer();
		while (m.find()) {
			m.appendReplacement(sb, "");
		}
		m.appendTail(sb);
		return sb.toString();
	}
	
	
	public void saveTransientObject(T o){
		setSystemFieldsForCreate(o);
		this.getHibernateTemplate().save(o);
	}

	@Override
	public List<T> findByHql(String hql, Object[] values) {
		// TODO Auto-generated method stub
		return this.getHibernateTemplate().find(hql,values);
	}

	@Override
	public T getObjectByClass(Class clazz, Serializable id) throws ObjectRetrievalFailureException {
		Object o = getHibernateTemplate().get(clazz, id);
		if (o == null) {
			throw new ObjectRetrievalFailureException(clazz, id);
		}
		return (T) o;
	}
	

	public List<T> queryListMapSql(String sql, Pager pager){
		if(!StringUtils.isNotEmpty(sql)) return null;
		//sql = sql.toLowerCase();
		StringBuffer countSql = new StringBuffer();
		try{
			countSql.append("select count(*) ");
			if(sql.contains("order")){
				countSql.append(sql.substring(sql.indexOf("from"), sql.lastIndexOf("order")));
			}else{
				countSql.append(sql.substring(sql.indexOf("from"), sql.length()));
			}
			
		}catch(Exception e){
			e.printStackTrace();
			throw new RuntimeException("解析sql异常:"+sql);
			
		}
		return this.getHibernateTemplate().executeFind(new SqlPagerMapSupport(countSql.toString(),sql,null,pager));
	}
	
	@SuppressWarnings("unchecked")
	public T saveObjectForTrans(T o) throws DataAccessException {
		setSystemFieldsForSaveOrUpdate(o);
		T t = (T) getHibernateTemplate().merge(o);
		 this.flush();
		return t;

	}
	
}
