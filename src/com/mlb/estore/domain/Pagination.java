package com.mlb.estore.domain;

import java.util.List;

public class Pagination<T> {
	// jsp输入变量
	private int pageNum = 1; // 查询页号
	private int pageSize = 3; // 每页显示数量
	// 从数据库获得变量
	private List<T> data; // 每页显示数据
	private long totalCount; // 数据总量
	// 计算变量
	private int pageCount; // 页数总量
	// private int beginCount; // 显示第一条数据在总数据中第几条
	private int[] pageBar; // 页码条页号数组
	// private int previousPage; // 上一页页号
	// private int nextPage; // 下一页页号
	// private int firstPage; // 第一页页号
	// private int lastPage; // 最后一页页号

	/**
	 * @return the pageNum
	 */
	public int getPageNum() {
		return pageNum;
	}

	/**
	 * @param pageNum
	 *            the pageNum to set
	 */
	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	/**
	 * @return the pageSize
	 */
	public int getPageSize() {
		return pageSize;
	}

	/**
	 * @param pageSize
	 *            the pageSize to set
	 */
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	/**
	 * @return the data
	 */
	public List<T> getData() {
		return data;
	}

	/**
	 * @param data
	 *            the data to set
	 */
	public void setData(List<T> data) {
		this.data = data;
	}

	/**
	 * @return the totalCount
	 */
	public long getTotalCount() {
		return totalCount;
	}

	/**
	 * @param totalCount
	 *            the totalCount to set
	 */
	public void setTotalCount(long totalCount) {
		this.totalCount = totalCount;
	}

	/**
	 * @return the pageCount
	 */
	public int getPageCount() {
		return (int) ((totalCount + pageSize - 1) / pageSize);
	}

	/**
	 * @return the beginCount
	 */
	public int getBeginCount() {
		return (pageNum - 1) * pageSize;
	}

	/**
	 * @return the pageBar
	 */
	public int[] getPageBar() {
		int size;// 页码条数字数量
		int beginPage;// 页码条第一数字
		int endPage;// 页码条最后一个数字
		pageCount = getPageCount();
		if (pageCount < 10) {
			size = pageCount;
			beginPage = 1;
			endPage = pageCount;
		} else {
			size = 10;
			beginPage = pageNum - 5;
			endPage = pageNum + 4;
			if (beginPage < 1) {
				beginPage = 1;
				endPage = 10;
			} else if (endPage > pageCount) {
				endPage = pageCount;
				beginPage = endPage - 9;
			}
		}
		pageBar = new int[size];
		for (int i = 0; i < pageBar.length; i++) {
			pageBar[i] = beginPage + i;
		}
		return pageBar;
	}

	/**
	 * @return the previousPage
	 */
	public int getPreviousPage() {
		return pageNum <= 1 ? pageNum : (pageNum - 1);
	}

	/**
	 * @return the nextPage
	 */
	public int getNextPage() {
		return pageNum >= getPageCount() ? pageNum : (pageNum + 1);
	}

	/**
	 * @return the firstPage
	 */
	public int getFirstPage() {
		return 1;
	}

	/**
	 * @return the lastPage
	 */
	public int getLastPage() {
		return getPageCount();
	}

}
