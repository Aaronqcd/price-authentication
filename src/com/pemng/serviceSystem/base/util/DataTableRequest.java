package com.pemng.serviceSystem.base.util;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.criterion.Order;

import com.pemng.serviceSystem.common.SortRule;

public class DataTableRequest {

	private HttpServletRequest request; // 内部使用的 Request 对象

	private final String sEchoParameter = "sEcho";

	// 起始索引和长度
	private final String iDisplayStartParameter = "iDisplayStart";
	private final String iDisplayLengthParameter = "iDisplayLength";

	// 列数
	private final String iColumnsParameter = "iColumns";
	private final String sColumnsParameter = "sColumns";

	// 参与排序列数
	private final String iSortingColsParameter = "iSortingCols";
	private final String iSortColPrefixParameter = "iSortCol_"; // 排序列的索引
	private final String sSortDirPrefixParameter = "sSortDir_"; // 排序的方向
	// asc,
	// desc

	// 每一列的可排序性
	private final String bSortablePrefixParameter = "bSortable_";

	// 全局搜索
	private final String sSearchParameter = "sSearch";
	private final String bRegexParameter = "bRegex";

	// 每一列的搜索
	private final String bSearchablePrefixParameter = "bSearchable_";
	private final String sSearchPrefixParameter = "sSearch_";
	private final String bEscapeRegexPrefixParameter = "bRegex_";

	/**
	 * 分页时每页start
	 */
	private int iDisplayStart;

	/**
	 * 分页时每页记录数量size
	 */
	private int iDisplayLength;

	/**
	 * DataTable请求服务器端次数
	 */
	private String sEcho;

	/**
	 * 排序列数
	 */
	private int iSortingCols;

	/**
	 * 搜索串
	 */
	private String sSearch;

	/**
	 * 是否正则
	 */
	private boolean bRegex;

	/**
	 * 排序列
	 */
	private SortColumn[] sortColumns;

	private int columnCount;

	private Column[] columns;

	public DataTableRequest(HttpServletRequest request) // 构造函数
	{
		this.request = request;
		this.sEcho = this.ParseStringParameter(sEchoParameter);
		this.iDisplayStart = this.ParseIntParameter(iDisplayStartParameter);
		this.iDisplayLength = this.ParseIntParameter(iDisplayLengthParameter);
		this.iSortingCols = this.ParseIntParameter(iSortingColsParameter);
		this.sSearch = this.ParseStringParameter(sSearchParameter);
		this.bRegex = "true".equals(this.ParseStringParameter(bRegexParameter));

		// 排序的列
		int count = this.iSortingCols;
		this.sortColumns = new SortColumn[count];

		// 封装排序列参数
		for (int i = 0; i < count; i++) {
			SortColumn col = new SortColumn();
			col.setIndex(this.ParseIntParameter(iSortColPrefixParameter + i));
			col.setDirection(SortDirection.ASC);
			if ("desc".equals(this.ParseStringParameter(sSortDirPrefixParameter
					+ i)))
				col.setDirection(SortDirection.DESC);
			this.sortColumns[i] = col;
		}

		// 一般列
		this.columnCount = this.ParseIntParameter(iColumnsParameter);

		count = this.columnCount;
		this.columns = new Column[count];

		// 目前列名并没有在参数中传过来
		String[] names = this.ParseStringParameter(sColumnsParameter)
				.split(",");

		for (int i = 0; i < count; i++) {
			Column col = new Column();
			if (i < names.length)
				col.setName(names[i]);
			col.setSortable(this.ParseBooleanParameter(bSortablePrefixParameter
					+ i));
			col.setSearchable(this
					.ParseBooleanParameter(bSearchablePrefixParameter + i));
			col
					.setSearch(this.ParseStringParameter(sSearchPrefixParameter
							+ i));
			col.setEscapeRegex(this
					.ParseBooleanParameter(bEscapeRegexPrefixParameter + i));
			columns[i] = col;
		}
	}

	private int ParseIntParameter(String name) // 解析为整数
	{
		int result = 0;
		String parameter = this.request.getParameter(name);
		if (parameter != null && parameter.trim().length() > 0) {
			result = Integer.parseInt(parameter);
		}
		return result;
	}

	private String ParseStringParameter(String name) // 解析为字符串
	{
		return this.request.getParameter(name);
	}

	private boolean ParseBooleanParameter(String name) // 解析为布尔类型
	{
		boolean result = false;
		String parameter = this.request.getParameter(name);
		if (parameter != null && parameter.trim().length() > 0) {
			result = Boolean.parseBoolean(parameter);
		}
		return result;
	}

	public boolean isBRegex() {
		return bRegex;
	}

	public void setBRegex(boolean regex) {
		bRegex = regex;
	}

	public int getIDisplayStart() {
		return iDisplayStart;
	}

	public int getIDisplayLength() {
		return iDisplayLength;
	}

	public String getSEcho() {
		return sEcho;
	}

	public int getISortingCols() {
		return iSortingCols;
	}

	public String getSSearch() {
		return sSearch;
	}

	public SortColumn[] getSortColumns() {
		return sortColumns;
	}

	public int getColumnCount() {
		return columnCount;
	}

	public Column[] getColumns() {
		return columns;
	}

	public String generateOrderBy() {
		StringBuilder str = new StringBuilder();
		str.append(" ");
		boolean first = true;
		for (SortColumn col : sortColumns) {
			String colName = columns[col.getIndex()].getName();
			if (colName.trim().length() > 0) {
				String dir = "";
				if (SortDirection.ASC == col.getDirection())
					dir = "asc";
				if (SortDirection.DESC == col.getDirection())
					dir = "desc";
				if (!first)
					str.append(",");
				else
					str.append("order by ");
				str.append(colName);
				str.append(" ");
				str.append(dir);
				first = false;
			}

		}
		return str.toString();
	}

	public Order[] generateOrders() {

		List<Order> orders = new ArrayList<Order>();
		for (int i = 0; i < sortColumns.length; i++) {
			if (SortDirection.ASC == sortColumns[i].getDirection()) {
				String field = columns[sortColumns[i].getIndex()].getName();
				if (field.trim().length() > 0)
					orders.add(Order.asc(field));
			}
			if (SortDirection.DESC == sortColumns[i].getDirection()) {
				String field = columns[sortColumns[i].getIndex()].getName();
				if (field.trim().length() > 0)
					orders.add(Order.desc(field));
			}
		}
		
		Order[] orderArray = new Order[orders.size()];

		return orders.toArray(orderArray);

	}

	public SortRule[] generateSortRuls() {

		List<SortRule> rules = new ArrayList<SortRule>();
		for (int i = 0; i < sortColumns.length; i++) {
			if (SortDirection.ASC == sortColumns[i].getDirection()) {
				String field = columns[sortColumns[i].getIndex()].getName();
				if (field.trim().length() > 0) {
					SortRule order = new SortRule();
					order.setParam(field);
					order.setDirection("ASC");
					rules.add(order);
				}
			}
			if (SortDirection.DESC == sortColumns[i].getDirection()) {
				String field = columns[sortColumns[i].getIndex()].getName();
				if (field.trim().length() > 0) {
					SortRule order = new SortRule();
					order.setParam(field);
					order.setDirection("DESC");
					rules.add(order);
				}
			}
		}
		
		SortRule[] ruleArray = new SortRule[rules.size()];
		return rules.toArray(ruleArray);

	}
}
