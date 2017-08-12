package com.pemng.serviceSystem.base.dao;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Filter;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projection;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.ObjectRetrievalFailureException;

import com.pemng.serviceSystem.base.pojo.BasePO;
import com.pemng.serviceSystem.base.util.Pager;



/**
 * 基础DAO接口，供实际使用接口基础。大多数情况下与BaseDaoHibernate合用。
 * 
 * @author shaojie
 * 
 * @param <T>
 */
public interface Dao<T extends BasePO> {

	/**
	 * 使用DaoFindPack作为查询条件，搜索数据
	 * 
	 * @param daoFindPack
	 *            搜索条件BO
	 * @return T对象列表
	 * @see DaoFindPack
	 */
	@SuppressWarnings("unchecked")
	public abstract List<T> findObjects(DaoFindPack<T> daoFindPack);

	/**
	 * 使用DaoFindPack作为查询条件，搜索数据.使用pagePack设置分页信息。
	 * 
	 * @param daoFindPack
	 *            搜索条件BO
	 * @param pagePack
	 *            分页信息
	 * @return T对象列表
	 * @see DaoFindPack
	 * @see PagePack
	 */
	@SuppressWarnings("unchecked")
	public abstract List<T> findObjects(DaoFindPack<T> daoFindPack,
			PagePack pagePack);

	/**
	 * 根据daoFindPack返回该搜索条件下的记录总数
	 * 
	 * @param daoFindPack
	 *            搜索条件BO
	 * @return 记录总数
	 */
	public abstract int getRowsCount(DaoFindPack<T> daoFindPack);

	/**
	 * 根据各种搜索条件的交集查找数据
	 * 
	 * @param o
	 *            Example查询
	 * @param exampleMap
	 *            关联对象的Example。key为属性名，值为作为example的对象实例
	 * @param cri
	 *            Criterion搜索条件
	 * @param order
	 *            排序顺序
	 * @param startRow
	 *            开始记录数
	 * @param pageSize
	 *            获取记录数
	 * @param likeSearch
	 *            是否模糊查找，会影响example查询的查找方式。
	 * @param projection
	 *            映射
	 * @param sortPack
	 *            排序对象BO
	 * @return T对象列表
	 */
	public abstract List<T> findObjects(final T o, final Map exampleMap,
			final Criterion cri[], final Order order[], final int startRow,
			final int pageSize, final boolean likeSearch,
			final Projection projection, final SortPack sortPack);

	/**
	 * 根据各种搜索条件的交集查找数据
	 * 
	 * @param o
	 *            Example查询
	 * @param exampleMap
	 *            关联对象的Example。key为属性名，值为作为example的对象实例
	 * @param cri
	 *            Criterion搜索条件
	 * @param order
	 *            排序顺序
	 * @param startRow
	 *            开始记录数
	 * @param pageSize
	 *            获取记录数
	 * @param likeSearch
	 *            是否模糊查找，会影响example查询的查找方式。
	 * @param projection
	 *            映射
	 * @param sortPack
	 *            排序对象BO
	 * @param fetchPacks
	 *            获取方式
	 * @return
	 */
	public abstract List<T> findObjects(final T o, final Map exampleMap,
			final Criterion cri[], final Order order[], final int startRow,
			final int pageSize, final boolean likeSearch,
			final Projection projection, final SortPack sortPack,
			final FetchPack[] fetchPacks);

	/**
	 * 结合分页获取记录及获取记录总数，一次性返回
	 * 
	 * @param example
	 *            Example查询
	 * @param exampleMap
	 *            关联对象的Example。key为属性名，值为作为example的对象实例
	 * @param cri
	 *            Criterion搜索条件
	 * @param likeSearch
	 *            是否模糊查找，会影响example查询的查找方式。
	 * @param daoOptionPack
	 *            获取结果的方式设置
	 * @return 根据DaoOptionPack的设置，返回记录列表、总数两者的各种组合
	 * @see DaoOptionPack
	 */
	public abstract ResultPack findObjectsWithCount(final T example,
			final Map exampleMap, final Criterion cri[],
			final boolean likeSearch, final DaoOptionPack daoOptionPack);

	/**
	 * 结合分页获取记录及获取记录总数，一次性返回
	 * 
	 * @param cri
	 *            Criterion搜索条件
	 * @param daoOptionPack
	 *            获取结果的方式设置
	 * @return 根据DaoOptionPack的设置，返回记录列表、总数两者的各种组合
	 * @see DaoOptionPack
	 */
	public abstract ResultPack findObjectsWithCount(final Criterion cri[],
			DaoOptionPack daoOptionPack);

	/**
	 * 结合分页获取记录及获取记录总数，一次性返回
	 * 
	 * @param example
	 *            Example查询
	 * @param cri
	 *            Criterion搜索条件
	 * @param daoOptionPack
	 *            获取结果的方式设置
	 * @return 根据DaoOptionPack的设置，返回记录列表、总数两者的各种组合
	 */
	public abstract ResultPack findObjectsWithCount(final T example,
			final Criterion cri, DaoOptionPack daoOptionPack);

	public abstract List<T> findObjects(final T o, final Map exampleMap,
			final Criterion cri[], final Order order[],
			final PagePack pagePack, final boolean likeSearch,
			final Projection projection, final SortPack sortPack);

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
	public abstract List<T> findObjects(final T o, final Map exampleMap,
			final Criterion cri[], final Order order[],
			final PagePack pagePack, final boolean likeSearch,
			final Projection projection, final SortPack sortPack,
			final FetchPack[] fetchPacks);

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
	public abstract List<T> findObjects(final T o, final Map exampleMap,
			final Criterion cri[], final Order order[], final int startRow,
			final int pageSize, final boolean likeSearch,
			final Projection projection);

	public abstract List<T> findObjects(final Criterion cri[],
			final PagePack pagePack, final SortPack sortPack);

	public abstract List<T> findObjects(final T o, final Map exampleMap,
			final Criterion cri[], final Order order[], final int startRow,
			final int pageSize);

	public abstract List<T> findObjects(final T o, final Criterion cri[],
			final Order order[], final int startRow, final int pageSize,
			final boolean likeSearch);

	/**
	 * 获取对象类型所有记录
	 * 
	 * @return T对象列表
	 */
	public abstract List<T> findObjects();

	/**
	 * 返回对象类型所有记录，但是通过Iterator返回
	 * 
	 * @return 对象记录的Iterator
	 */
	@SuppressWarnings("unchecked")
	public abstract Iterator<T> findObjectsByIterator();

	/**
	 * get ordered all rows of the class
	 */
	public abstract List<T> findObjects(final Order order[]);

	/**
	 * get data by examples
	 */
	public abstract List<T> findObjects(final T o);

	/**
	 * get data by examples with page
	 */
	public abstract List<T> findObjects(final T o, int startIndex, int pageSize);

	/**
	 * get data by example matchall
	 */
	public abstract List<T> findObjects(final T o, boolean likeSearch);

	/**
	 * get ordered example search
	 */
	public abstract List<T> findObjects(final T o, final Order order[]);

	/**
	 * 
	 * @param exampleMap
	 *            key : fieldName-比如条件在tpinfo.carType，则key为"carType" <p/> value :
	 *            条件对象- 已持久化(有id)的对象
	 * @return
	 */
	public abstract List<T> findObjects(final Map exampleMap);

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
	public abstract List<T> findObjects(final Map exampleMap,
			final Order order[]);

	/**
	 * 
	 * @param o
	 *            条件对象
	 * @param exampleMap
	 *            key : fieldName-比如条件在tpinfo.carType，则key为"carType" <p/> value :
	 *            条件对象- 已持久化(有id)的对象
	 * @return
	 */
	public abstract List<T> findObjects(final T o, final Map exampleMap);

	/**
	 * 
	 * @param o
	 *            条件对象
	 * @param exampleMap
	 *            key : fieldName-比如条件在tpinfo.carType，则key为"carType" <p/> value :
	 *            条件对象- 已持久化(有id)的对象
	 * @param order
	 *            an array of order <p/> Example: Order
	 *            order[]={Order.asc("name")}
	 * @return
	 */
	public abstract List<T> findObjects(final T o, final Map exampleMap,
			final Order order[]);

	/**
	 * 
	 * @param clazz
	 *            目标对象类型
	 * @param cri
	 *            Critertion 条件格式数组 <p/> Example: Criterion cri[] =
	 *            {Expression.in("belongDeptNum", departmentNums)};
	 * @return List
	 */
	public abstract List<T> findObjects(final Criterion cri[]);

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
	public abstract List<T> findObjects(final Criterion cri[],
			final Order order[]);

	/**
	 * 保存对象，如果没有ID则为新建，如果已有ID则为更新<br/>
	 * merge(o)
	 * @param o
	 *            需保存的对象

	 * @return 保存后的对象，包含更新的ID值等
	 * @throws DataAccessException
	 */
	@SuppressWarnings("unchecked")
	public abstract T saveObject(T o) throws DataAccessException;
	/**
	 * 保存对象，如果没有ID则为新建，如果已有ID则为更新<br/>
	 * merge(o)
	 * @param o
	 *            需保存的对象

	 * @return 保存后的对象，包含更新的ID值等
	 * @throws DataAccessException
	 */
	@SuppressWarnings("unchecked")
	public abstract Object saveObject(Object o) throws DataAccessException;
	
	/**
	 * 保存对象list,自动设置更新和创建信息<br/>
	 * saveOrUpdateAll(list)
	 * @param list
	 * @throws DataAccessException
	 */
	public abstract void saveObjectList(List<T> list) throws DataAccessException;

	/**
	 * use this instead of saveOrUpdate becoz it does not reattach the passed
	 * instance.
	 * 
	 * @param o
	 * @throws DataAccessException
	 */
	public abstract void updateObjectOnly(T o) throws DataAccessException;

	/**
	 * get read only object, and won't save this object, used for reference to
	 * save another object with same ID get an object according to the class and
	 * its id
	 */
	@SuppressWarnings("unchecked")
	public abstract T getReadOnlyObject(Serializable id)
			throws ObjectRetrievalFailureException;

	/**
	 * get an object according to the class and its id
	 */
	@SuppressWarnings("unchecked")
	public abstract T getObject(Serializable id)
			throws ObjectRetrievalFailureException;
	
	public Object getObject(Class clazz, Serializable id)
			throws ObjectRetrievalFailureException;
	/**
	 * remove the object with the specific class and id
	 */
	public abstract void removeObject(Long id)
			throws DataAccessException;

	/**
	 * remove the object
	 */
	public abstract void removeObject(T object) throws DataAccessException;

	/**
	 * 结合HQL获取对象映射
	 * 
	 * @param hqlQueryEntries
	 *            定义了需要获取的值及映射
	 * @param fromJoinSubClause
	 *            定义从那些对象表中获取，及相应的联合关系
	 * @param whereBodies
	 *            where子句片段，互相间为And关系
	 * @param params
	 *            动态参数表，key为参数名，在whereBodies中以":参数名"定义。值为查询条件值。<br>
	 *            如果值为null则会忽略whereBodies中该条搜索条件
	 * @param offsetIndex
	 *            首条获取记录位置
	 * @param pageSize
	 *            获取记录数量，0代表不分页
	 * @param orders
	 *            排序方式
	 * @return 根据HqlQueryEntry中的指定方式返回Map
	 */
	public abstract List<Map<String, ?>> findObjectsByHql(
			final HqlQueryEntry[] hqlQueryEntries,
			final String fromJoinSubClause, final String[] whereBodies,
			final Map<String, ?> params, final int offsetIndex,
			final int pageSize, final Order[] orders);

	/**
	 * 使用HQL获得对象
	 * 
	 * @param hql
	 *            查询用HQL
	 * @param params
	 *            查询参数，key为查询参数名，与hql中":参数名"对应
	 * @param offsetIndex
	 *            首条获取记录位置
	 * @param pageSize
	 *            获取记录数量，0代表不分页
	 * @param orders
	 *            排序方式
	 * @return 结果集列表，具体类型由HQL确定
	 */
	public abstract List<?> findObjectsByHql(final String hql,
			final Map<String, ?> params, final int offsetIndex,
			final int pageSize, final Order[] orders);

	/**
	 * 
	 * @param detachedCri
	 *            a DetachedCriteria
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public abstract List<T> findObjectsByCri(DetachedCriteria detachedCri);

	@SuppressWarnings("unchecked")
	public abstract List<T> findObjectsByCri(DetachedCriteria detachedCri,
			PagePack pagePack);

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
	public abstract List<T> findObjectsByCri(DetachedCriteria detachedCri,
			int firstResult, int maxResult);

	/**
	 * 根据条件获得rowcount数
	 * 
	 * @param clazz
	 * @param o
	 * @param exampleMap
	 * @param cri
	 * @param searchLike
	 *            true-anywhere false-exact
	 * @return int,the row count for the result set
	 */
	public abstract int getRowsCount(final T o, final Map exampleMap,
			final Criterion cri[], boolean searchLike);

	public abstract int getRowsCount(final Criterion cri[]);

	/**
	 * 将持久化对象从持久关系中释放
	 * 
	 * @param object
	 *            持久化对象
	 */
	public abstract void evict(T object);

	/**
	 * 刷新缓存，将数据层未执行的操作更新至SQL命令列表中
	 */
	public abstract void flush();
	/**
	 * clear
	 */
	public void clear();

	/**
	 * 用来配合相应的记录查询语句，获取记录总条数
	 * 
	 * @param fromJoinSubClause
	 *            定义从那些对象表中获取，及相应的联合关系
	 * @param whereBodies
	 *            where子句片段，互相间为And关系
	 * @param params
	 *            查询参数，key为查询参数名，与hql中":参数名"对应
	 * @return 记录总数
	 */
	public int getRowsCountByHql(final String fromJoinSubClause,
			final String[] whereBodies, final Map<String, ?> params);

	/**
	 * 用来配合相应的记录查询语句，获取记录总条数
	 * 
	 * @param hql
	 *            查询用HQL
	 * @param params *
	 *            查询参数，key为查询参数名，与hql中":参数名"对应
	 * @return 记录总数
	 */
	public int getRowsCountByHql(String hql, Map<String, ?> params);

	
	
	//add by shijiachen 20100706 begin
	public abstract List<Map<String, ?>> findObjectsBySql(
			final HqlQueryEntry[] hqlQueryEntries,
			final String fromJoinSubClause, final String[] whereBodies,
			final Map<String, ?> params, final int offsetIndex,
			final int pageSize, final Order[] orders);
	
	public int getRowsCountBySql(String fromJoinSubClause, final String[] whereBodies, Map<String, ?> params);
	//add by shijiachen 20100706 end
	
	/**
	 * 获取指定Sequence的新值。
	 * 
	 * @param sequenceName
	 *            数据库中序列名
	 * @return 新的序列值，即nextval
	 */
	public long getSequence(String sequenceName);

	/**
	 * 用来配合相应的记录查询语句，获取记录总条数。<br>
	 * 支持对某个字段distinct求得总数，相当于count(distinct xxx)
	 * 
	 * @param fromJoinSubClause
	 *            定义从那些对象表中获取，及相应的联合关系
	 * @param whereBodies
	 *            where子句片段，互相间为And关系
	 * @param params
	 *            查询参数，key为查询参数名，与hql中":参数名"对应
	 * @param distinctName
	 *            distinct的字段名，按照hql的格式。
	 * @return 记录总数
	 */
	public int getRowsCountByHql(final String fromJoinSubClause,
			final String[] whereBodies, final Map<String, ?> params,
			String distinctName);

	/**
	 * 结合HQL获取对象映射
	 * 
	 * @param hqlQueryEntries
	 *            定义了需要获取的值及映射
	 * @param fromJoinSubClause
	 *            定义从那些对象表中获取，及相应的联合关系
	 * @param whereBodies
	 *            where子句片段，互相间为And关系
	 * @param params
	 *            动态参数表，key为参数名，在whereBodies中以":参数名"定义。值为查询条件值。<br>
	 *            如果值为null则会忽略whereBodies中该条搜索条件
	 * @param offsetIndex
	 *            首条获取记录位置
	 * @param pageSize
	 *            获取记录数量，0代表不分页
	 * @param orders
	 *            排序方式
	 * @param isDistinct
	 *            是否需要对结果集进行distinct。先distinct再进行分页，不影响返回条数
	 * @return 根据HqlQueryEntry中的指定方式返回Map
	 */
	public List<Map<String, ?>> findObjectsByHql(
			final HqlQueryEntry[] hqlQueryEntries,
			final String fromJoinSubClause, final String[] whereBodies,
			final Map<String, ?> params, final int offsetIndex,
			final int pageSize, final Order[] orders, boolean isDistinct);

	/**
	 * 执行一条hql语句
	 * 
	 * @param hql
	 *            HQL语句
	 * @param params
	 *            动态参数表，key为参数名，在HQL中以":参数名"定义。值为查询条件值。<br>
	 * @return 根据HQL语句确定返回类型
	 */
	public Object executeHql(final String hql, final Map<String, ?> params);
	/**
	 * 执行一条sql语句
	 * @param Sql
	 * @return
	 */
	public Object executeSql(final String Sql);
	
	/**
	 * 
	 * @param Sql
	 * @return
	 */
	public List<?> findObjectBySql(final String Sql);
	/**
	 * 启用一条过滤条件。使用了Hibernate的Filter功能
	 * 
	 * @param name
	 *            过滤条件名
	 * @return 启用的过滤条件
	 */
	public Filter enableFilter(String name);

	/**
	 * save any PO.不自动设置更新和创建信息
	 */
	@SuppressWarnings("unchecked")
	public T saveObjectPure(T o) throws DataAccessException;

	/**
	 * 获得一个对象同时对其加乐观锁。<br>
	 * 其他线程如果需要获得同样的锁则需要等待当前线程结束
	 * 
	 * @param id
	 *            对象ID
	 * @return 持久化的对象
	 * @throws ObjectRetrievalFailureException
	 */
	@SuppressWarnings("unchecked")
	public T getObjectForUpdate(Serializable id)
			throws ObjectRetrievalFailureException;
	
	/**
	 * 
	 * @param sqlList
	 * @throws SQLException
	 */
	public void executeBatch(List<String> sqlList) throws SQLException;
	
	public List<?> findObjectsByHql(String hql, Map<String,?> params);
	
	public int getRowsCountByHql(String hql, Map<String, ?> params, String distinctName);
	
	/**
	 * Function Name               pagedQuery                                   
	 * @param                      pager 
	 * @param                      params
	 * @description                支持HQL的分页  			             
	 * Modify History:              Date             Programmer       Notes
	 *                            ---------        ---------------  ---------
	 *                            May 14, 2012             louzhixiong         Initial
	 **********************************************************************
	 */
	public Pager pagedQuery(String hql, Pager pager, Object[] params);
	
	public Pager pagedQuery(String hql, Pager pager, Map<String, ?> params);
	
	public Pager pagedQuery(Class clazz, Pager page, Criterion[] criterions);
	
	public Pager pagedQuery(Class clazz, Pager page, Criterion[] criterions,String orderBy,boolean isAsc);
	
	public Pager pagedQuery(Class clazz, Pager page, Criterion[] criterions,String orderBy,boolean isAsc,String fetchAttr,FetchMode fetchMode);
	
	public Pager pagedQuery(Criteria criteria, Pager page);
	
	/**根据给定HQL(有分页功能）*/	
	public List<T> queryList(String countHql, String hql, Pager pager);
	
	/**根据给定SQL及参数查询数据（有分页功能）*/	
	public List<T> queryListSql(String countSql, String sql, Pager pager);
	public List<?> queryListSql(String countSql, String sql, Pager pager,Class c);

	//add by yunxiangfu 20121112
	public List<?> findObjectsBySql(final String sql,
			final Map<String, ?> params, final int offsetIndex,
			final int pageSize, final Order[] orders);

	public List<Map<String, ?>> findObjectsBySql(final String Sql, final Map<String, ?> params);
	
	public Pager pagedQueryBySql(String sql, Pager pager, Map<String, ?> params);
	
	
	/**
	 * Function Name               saveTransientObject                                   
	 * @param                      o-保存对象
	 * @description                保存记录，并对原始对象ID设值
	 * Modify History:              Date             Programmer       Notes
	 *                            ---------        ---------------  ---------
	 *                            May 28, 2012             chenguanjun         Initial
	 **********************************************************************
	 */
	public void saveTransientObject(T o);
	
	/**
	 * Function Name               removeAllObjects                                   
	 * @param                      //删除对象集合
	 * @description                //删除集合内的所有对象  			             
	 * Modify History:              Date             Programmer       Notes
	 *                            ---------        ---------------  ---------
	 *                            Jun 7, 2012             shaojie         Initial
	 **********************************************************************
	 */
	public void removeAllObjects(Collection<T> objects) throws DataAccessException;
	
	
	public List<T> findByHql(String hql,Object[] values);
	
	/**
	 * get an object according to the class and its id
	 */
	@SuppressWarnings("unchecked")
	public abstract T getObjectByClass(Class clazz,Serializable id) throws ObjectRetrievalFailureException;
	
	
	/**
	 * sql 分页 SQL中的关键字 请用小写 如 select 和 order where 等
	 * @param sql
	 * @param pager
	 * @return 返回个List<Map<String,Object>> 的集合，
	 * 	Map 中key 对应数据库中的列名（小写），value 对应每列的值，列名无序
	 */
	public List<T> queryListMapSql(String sql, Pager pager);
	
	/**
	 * 查询一条数据
	 * @param hql
	 * @param params
	 * @return
	 */
	public Object uniqueResult(final String hql, final Map<String, ?> params);
	/**
	 * save 后 frush 并且返回该对象
	 * @param o
	 * @return
	 * @throws DataAccessException
	 */
	@SuppressWarnings("unchecked")
	public T saveObjectForTrans(T o) throws DataAccessException;

	void removeObject(Object object) throws DataAccessException;

	Pager pagedQuery(String hql, Pager pager, Map<String, ?> params,
			String distinctName);

	Pager pagedQueryBySql(String sql, Pager pager, Map<String, ?> params,
			String distinctName);
}
