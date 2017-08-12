package com.pemng.serviceSystem.base.util;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import com.pemng.serviceSystem.base.pojo.BasePO;



/**
 * Hibernate常用工具
 * 
 * @author 
 * 
 */
public class HibernateUtil {

	transient private static Log log = LogFactory.getLog(HibernateUtil.class);

	// /**
	// * @param oriList
	// * @param daoOptionPack
	// * @return
	// */
	// public static ResultPack getSubListToBePaged(List oriList,
	// DaoOptionPack daoOptionPack) {
	//
	// PagePack pagePack = daoOptionPack.getPagePack();
	// // if(pagePack==null)
	//
	// int oriListSize = oriList.size();
	// log.debug("daooption:" + daoOptionPack.getGetMode());
	//
	// switch (daoOptionPack.getGetMode()) {
	// case DaoOptionPack.GET_COUNT:
	//
	// return new ResultPack(new java.util.ArrayList(), oriListSize);
	//
	// case DaoOptionPack.GET_LIST:
	//
	// return new ResultPack(oriList, oriListSize);
	//
	// case DaoOptionPack.GET_ALL:
	//
	// int startIndex = pagePack.getStartIndex();
	// int pageSize = pagePack.getPageSize();
	// log.debug("start:" + startIndex);
	// log.debug("size:" + pageSize);
	//
	// if (startIndex >= oriListSize)
	// return ResultPack.createEmptyResultPack();
	//
	// int toIndex = startIndex + pageSize;
	// if (toIndex > oriListSize)
	// toIndex = oriListSize;
	//
	// return new ResultPack(oriList.subList(startIndex, toIndex),
	// oriListSize);
	//
	// default:
	//
	// return null;
	// }
	// }

	public static Criterion andCriterion(Criterion oriCri, Criterion andCri) {

		if (oriCri == null) {
			oriCri = andCri;
		} else if (andCri != null)

			oriCri = Restrictions.and(oriCri, andCri);

		return oriCri;
	}

	public static Criterion orCriterion(Criterion oriCri, Criterion andCri) {

		if (oriCri == null) {
			oriCri = andCri;
		} else if (andCri != null)

			oriCri = Restrictions.or(oriCri, andCri);

		return oriCri;
	}

	public static String fullLike(String searchStr) {

		if (searchStr == null)
			return null;
		else
			return "%" + searchStr + "%";

	}

	public static String fullILike(String searchStr) {

		if (searchStr == null)
			return null;
		else
			return "%" + searchStr.toLowerCase() + "%";

	}

	public static String iEq(String searchStr) {

		if (searchStr == null)
			return null;
		else
			return searchStr.toLowerCase();

	}

	public static Criterion dateRangeCriterion(Date startDate, Date endDate,
			String fieldName) {

		Criterion dateCriterion = null;

		if (startDate != null) {

			dateCriterion = Restrictions.ge(fieldName, startDate);
		}

		// should plus one day
		if (endDate != null) {

			Calendar calendar = new GregorianCalendar();
			calendar.setTime(endDate);
			calendar.add(Calendar.DAY_OF_MONTH, 1);

			dateCriterion = andCriterion(dateCriterion, Restrictions.lt(
					fieldName, calendar.getTime()));

		}

		return dateCriterion;
	}

	@SuppressWarnings("unchecked")
	public static <T extends BasePO> void removePoFromSet(
			Set<? extends BasePO> pos, T po) {

		for (Iterator<? extends BasePO> iter = pos.iterator(); iter.hasNext();) {

			BasePO boNow = (BasePO) iter.next();
			if (boNow.getId().equals(po.getId())) {

				iter.remove();
				break;

			}

		}

	}

	public static <T extends BasePO> void addPoToSet(Set<? super T> pos, T bo) {

		for (Iterator<? super T> iter = pos.iterator(); iter.hasNext();) {

			BasePO boNow = (BasePO) iter.next();
			if (boNow.getId().equals(bo.getId())) {

				return;

			}

		}

		pos.add(bo);

	}

	/**
	 * if want to get a unique object from a list, use this method
	 * 
	 * @param objects
	 * @return
	 * @throws BaseSysException
	 *             common.exception.notUnique
	 */
//	public static <T> T getUniqueFromCollection(Collection<T> objects)
//			throws BaseSysException {
//
//		if (objects == null || objects.size() == 0)
//			return null;
//
//		if (objects.size() > 1)
//			throw new BaseSysException("common.exception.notUnique");
//		else
//			return objects.iterator().next();
//	}

//	public static <T extends BasePO> SortedSet<? super T> sortSetById(
//			Collection<T> collection) {
//
//		if (collection == null)
//			return null;
//
//		 Comparator<T> comparator = new Comparator()<T> {
//		 public int compare(Object o1, Object o2) {
//		 int result = 0;
//		 BasePO baseBo1 = (BasePO) o1;
//		 BasePO baseBo2 = (BasePO) o2;
//		
//		 result = baseBo1.getId().compareTo(baseBo2.getId());
//		
//		 return result;
//		 }
//		 };
//
//		SortedSet<T> sortedSet = new TreeSet<T>(collection);
//		// sortedSet.addAll(collection);
//
//		return sortedSet;
//
//	}

	/**
	 * 从PO的集合中抽取出Id列表
	 * 
	 * @param pos
	 *            po集合
	 * @return Id列表，如果pos是有序的，则ids也按同样的顺序
	 */
	@SuppressWarnings("unchecked")
	public static <T extends BasePO<T>> List<Long> fetchIds(Collection<T> pos) {
		List<Long> ids = new ArrayList<Long>();
		for (BasePO po : pos) {
			ids.add(po.getId());
		}
		return ids;
	}
}
