package com.pemng.serviceSystem.base.util;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
/**
 * HQL查询常用工具
 * @author shaojie
 *
 */
public class HqlQueryUtil {

	transient private static Log log = LogFactory.getLog(HqlQueryUtil.class);

	/**
	 * join some where by or using the same param
	 * 
	 * @param queries
	 *            something like {"lower(partNameZh) like","lower(partNameEn)
	 *            like"}
	 * @param paramToken
	 *            for param like ":partName"
	 * @return
	 */
	public static String joinedOrWhere(String[] queries, String paramToken) {
		for (int i = 0; i < queries.length; i++) {
			queries[i] = queries[i] + " " + paramToken;
		}
		return StringUtils.join(queries, " or ");
	}

	/**
	 * 
	 * @param fieldNames
	 *            2-dimension arrays in format of
	 *            {{"csf","name_zh"},{"csf","name_en"}}
	 * @param paramToken
	 * @return
	 */
	public static String joinedOrIlikeWhere(String[][] fieldNames,
			String paramToken) {
		String[] queries = new String[fieldNames.length];

		for (int i = 0; i < fieldNames.length; i++) {
			queries[i] = icaseLike(fieldNames[i], "");
		}
		return joinedOrWhere(queries, paramToken);

	}

	//
	// /**
	// * get a query where for the split string like "polo|santana"
	// *
	// * @param fieldName
	// * the fieldName to query
	// * @param paramName
	// * the paramName to be
	// * @return a complete where entry
	// */
	// public static String querySplitWhere(String fieldName, String paramName)
	// {
	// String[] queries = new String[3];
	// queries[0] = "lower(" + fieldName +") like "
	// }
	public static String splitILikeWhere(String[] fieldNames,
			String paramNamePrefix) {

		String fieldName = dotJoin(fieldNames);

		String queries[] = new String[4];
		for (int i = 0; i < 4; i++) {
			queries[i] = fieldName + " like " + paramNamePrefix + i;
		}
		return StringUtils.join(queries, " or ");

	}

	/**
	 * To supply three params for split query
	 * 
	 * @param ori
	 * @param i
	 *            corresponding to the splitILikeWhere's params
	 * @return %|ori ori|% %|ori|%
	 */
	public static String getSplitParam(String ori, int i) {
		
		if(ori==null)
			return null;
		
		switch (i) {
		case 0:
			return "%|" + ori;
		case 1:
			return ori + "|%";
		case 2:
			return "%|" + ori + "|%";
		case 3:
			return ori;
			
		default:
			return null;
		}
	}

	/**
	 * 
	 */
	public static String icaseLike(String[] fieldNames, String paramToken) {
		String fieldName = dotJoin(fieldNames);
		return "lower(" + fieldName + ") like " + paramToken;

	}

	public static String icaseEq(String[] fieldNames, String paramToken) {
		String fieldName = dotJoin(fieldNames);
		return "lower(" + fieldName + ") = " + paramToken;

	}
	
	public static String operator(String[] fieldNames, String operator,
			String paramToken) {
		return dotJoin(fieldNames) + " " + operator + " " + paramToken;
	}

	public static String eq(String[] fieldNames, String paramToken) {
		return dotJoin(fieldNames) + " = " + paramToken;
	}

	public static String in(String[] fieldNames, String paramToken) {
		return dotJoin(fieldNames) + " in (" + paramToken + ")";
	}

	public static String notIn(String[] fieldNames, String paramToken) {
		return dotJoin(fieldNames) + " not in (" + paramToken + ")";
	}
	
	public static String eq(String[] fieldNames, String[] rightFieldNames) {
		return dotJoin(fieldNames) + " = " + dotJoin(rightFieldNames);
	}

	public static String leftJoin(String[] fieldNames, String aliasName) {
		return " left join " + dotJoin(fieldNames) + " " + aliasName;
	}
	
	public static String leftJoinFetch(String[] fieldNames, String aliasName) {
		return " left join fetch " + dotJoin(fieldNames) + " " + aliasName;
	}
	
	public static String join(String[] fieldNames, String aliasName) {
		return " join " + dotJoin(fieldNames) + " " + aliasName;
	}
	
	public static String joinFetch(String[] fieldNames, String aliasName) {
		return " join fetch " + dotJoin(fieldNames) + " " + aliasName;
	}
	
	public static String from(String className, String aliasName) {
		return "from " + className + " " + aliasName;
	}

	public static String dotJoin(String[] fieldNames) {
		return StringUtils.join(fieldNames, ".");
	}
}
