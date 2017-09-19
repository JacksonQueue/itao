package com.leafnet.itao.commons.bean;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * 链状代码风格,方便构建参数
 * 
 * @author: Wangd
 */
public class DBParam extends HashMap<String, Object> {

    private static final long serialVersionUID = -2096140242546291945L;

    /**
     * 分页相关内容的常量定义
     */
    /**
     * 当前页面下标
     */
    public final static String PARAM_NAME_CUR_PAGE_IDX = "_curPageIdx";
    /**
     * 每页条数
     */
    public final static String PARAM_NAME_PAGESIZE = "_pageSize";

    /**
     * 开始分页的下标
     */
    public final static String PARAM_NAME_BEGIN_IDX = "_beginIdx";

    /**
     * 是否分页
     * 
     * @return
     */
    public boolean isPagination() {
	return (getBeginIdx() != null || getCurPage() != null) && getPageSize() != null;
    }

    /**
     * 获得开始分页的数据下标
     * 
     * @return
     */
    public Integer getBeginIdx() {
	Integer beginIdx = (Integer) val(PARAM_NAME_BEGIN_IDX);
	Integer curPage = (Integer) val(PARAM_NAME_CUR_PAGE_IDX);
	Integer pageSize = (Integer) val(PARAM_NAME_PAGESIZE);
	if (beginIdx == null && curPage != null && pageSize != null)
	    beginIdx = (curPage - 1) * pageSize;
	return beginIdx;
    }

    /**
     * 获得当前页数
     * 
     * @return
     */
    public Integer getCurPage() {
	Integer curPage = (Integer) val(PARAM_NAME_CUR_PAGE_IDX);
	Integer beginIdx = (Integer) val(PARAM_NAME_BEGIN_IDX);
	Integer pageSize = (Integer) val(PARAM_NAME_PAGESIZE);
	if (curPage == null && beginIdx != null && pageSize != null)
	    curPage = (beginIdx / pageSize) + 1;
	return curPage;
    }

    /**
     * 获得每页数据量
     * 
     * @return
     */
    public Integer getPageSize() {
	return (Integer) val(PARAM_NAME_PAGESIZE);
    }

    public DBParam buildPagination(int curPage, int pageSize) {
	if (pageSize > 0) {
	    append(PARAM_NAME_CUR_PAGE_IDX, curPage);
	    append(PARAM_NAME_PAGESIZE, pageSize);
	    append(PARAM_NAME_BEGIN_IDX, (curPage - 1) * pageSize);
	}
	return this;
    }

    public static DBParam build() {
	return new DBParam();
    }

    public static DBParam build(Map<String, Object> params) {
	return build().append(params);
    }

    public static DBParam build(int curPage, int pageSize) {
	return build().buildPagination(curPage, pageSize);
    }

    public Object val(String key) {
	return this.get(key);
    }

    public DBParam append(Map<String, Object> vals) {
	this.putAll(vals);
	return this;
    }

    public DBParam append(String key, Object val) {
	this.put(key, val);
	return this;
    }

    public Properties toProperties() {
	Properties properties = new Properties();
	properties.putAll(this);
	return properties;
    }

    @Override
    public Object get(Object key) {
	Object value = super.get(key);
	if (PARAM_NAME_BEGIN_IDX.equals(key.toString()) || PARAM_NAME_CUR_PAGE_IDX.equals(key.toString())
		|| PARAM_NAME_PAGESIZE.equals(key.toString())) {
	    if (value != null) {
		if (value instanceof Integer)
		    return (Integer) value;
		return Integer.valueOf(value.toString());
	    }
	}
	return value;
    }
}
