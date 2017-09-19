package com.leafnet.itao.mapper.basic;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Mapper注入
 * @author Wangd
 * @param <T>
 */
public interface BasicMapper<T> {

	/**
	 * 新增数据
	 * @param model 数据实体
	 * @return	受影响行数
	 */
	int insert(T model);

	/**
	 * 修改数据
	 * @param model	数据实体
	 * @return	受影响行数
	 */
	int update(T model);

	/**
	 * 根据指定参数删除数据
	 * @param param	查询条件
	 * @return	受影响行数
	 */
	int deleteByParam(Map<String, Object> param);

	/**
	 * 根据条件查询唯一一条数据
	 * @param param	查询条件
	 * @return	数据实体
	 */
	T queryUniqueById(Serializable id);

	/**
	 * 根据条件查询多条数据
	 * @param param	查询条件
	 * @return	数据实体
	 */
	List<T> queryListByParam(Map<String, Object> param);

	/**
	 * 根据条件查询数据条数
	 * @param param	查询条件
	 * @return	数据条数
	 */
	long queryCountByParam(Map<String, Object> param);
}
