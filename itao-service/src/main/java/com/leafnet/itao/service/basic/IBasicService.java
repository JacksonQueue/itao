package com.leafnet.itao.service.basic;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.leafnet.itao.commons.bean.DBParam;
import com.leafnet.itao.commons.bean.PageBean;

/**
 * 基础的Mapper操作
 * @author Wangd
 * @param <T>
 */
public interface IBasicService<T> {

	/**
	 * 根据条件查询唯一一条数据
	 * @param param	查询条件
	 * @return	数据实体
	 */
	T queryOne(Serializable id);

	/**
	 * 根据多个条件查询一条数据
	 * @param param	查询条件
	 * @return	数据实体
	 */
	T queryOne(Map<String, Object> params);

	/**
	 * 根据多个条件查询多条数据
	 * @param param	查询条件
	 * @return	数据实体
	 */
	List<T> queryList(Map<String, Object> params);

	/**
	 * 根据多个条件查询数据条数
	 * @param param	查询条件
	 * @return	数据条数
	 */
	long queryCount(Map<String, Object> params);

	/**
	 * 根据多个条件查询分页数据
	 * @param param
	 * @return
	 */
	PageBean<T> queryPageList(DBParam params);
}
