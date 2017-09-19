package com.leafnet.itao.commons.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 分页组件
 * @Description : curPage 从0开始,首页传入 0
 * @author: Wangd
 * @param <T>
 */
public class PageBean<T> implements Serializable {

    private static final long serialVersionUID = -5732544552919784657L;

    /** 总记录数 */
    private long totalRecCount = 0;

    /** 开始页码 */
    private int curPage = 0;

    /** 每页多少 */
    private int pageSize = 0;

    /** 分页结果 */
    private List<T> resultList;

    /**
     * 拓展数据
     */
    private Map<String, Object> extData = new HashMap<>();

    public PageBean<T> buildPage(List<T> resultList, long totalRecCount) {
	this.setResultList(resultList);
	this.setTotalRecCount(totalRecCount);
	return this;
    }

    /**
     * 添加拓展数据
     */
    public void addExtData(String name, String value) {
	getExtData().put(name, value);
    }

    public void setExtData(Map<String, Object> extData) {
	this.extData = extData;
    }

    public Map<String, Object> getExtData() {
	return extData;
    }

    /**
     * 获取总页数
     * 
     * @return
     */
    public long getTotalPageCount() {
	if (getTotalRecCount() > 0) {
	    long offset = getTotalRecCount() % getPageSize();
	    long pageCount = getTotalRecCount() / getPageSize();
	    return offset == 0 ? pageCount : pageCount + 1;
	}
	return 0;
    }

    /**
     * 获取开始下标
     * 
     * @return
     */
    public int getStartIndex() {
	return this.getCurPage() * this.getPageSize();
    }

    /**
     * 获取结束下标
     * 
     * @return
     */
    public int getEndIndex() {
	return getStartIndex() + this.getPageSize();
    }

    /**
     * 是否有上页
     * 
     * @return
     */
    public boolean isHasPrePage() {
	return this.getPrePage() < this.getCurPage();
    }

    /**
     * 是否有下页
     * 
     * @return
     */
    public boolean isHasNextPage() {
	return this.getTotalPageCount() > this.getCurPage();
    }

    /**
     * 获取 上页 页码
     * 
     * @return
     */
    public int getPrePage() {
	return this.getCurPage() - 1 < 1 ? 0 : this.getCurPage() - 1;
    }

    /**
     * 获取下页页码
     * 
     * @return
     */
    public int getNextPage() {
	int nextPage = this.getCurPage() + 1;
	return nextPage > this.getTotalPageCount() ? (int) this.getTotalPageCount() : nextPage;
    }

    /**
     * 获取总条数
     * 
     * @return
     */
    public long getTotalRecCount() {
	return totalRecCount;
    }

    public void setTotalRecCount(long totalRecCount) {
	this.totalRecCount = totalRecCount;
    }

    /**
     * 获取当前页码
     * 
     * @author: Xiongmw
     * @Date: 2014年12月12日
     * @return
     */
    public int getCurPage() {
	if (curPage < 0) {
	    curPage = 0;
	}
	return curPage;
    }

    public void setCurPage(int curPage) {
	this.curPage = curPage;
    }

    /**
     * 获得 每页条数
     * 
     * @return
     */
    public int getPageSize() {
	if (pageSize < 0) {
	    setPageSize(0);
	}
	return pageSize;
    }

    public void setPageSize(int pageSize) {
	this.pageSize = pageSize;
    }

    /**
     * 获取分页内容
     * 
     * @return
     */
    public List<T> getResultList() {
	if (resultList == null) {
	    resultList = new ArrayList<T>();
	}
	return resultList;
    }

    public void setResultList(List<T> resultList) {
	this.resultList = resultList;
    }
}
