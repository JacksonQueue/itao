package com.leafnet.itao.service.basic;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import com.leafnet.htw.commons.bean.DBParam;
import com.leafnet.htw.commons.bean.PageBean;
import com.leafnet.itao.mapper.basic.BasicMapper;

/**
 * 提供基础单表查查询分页功能
 * @author Wangd
 * @param <T>
 */
public abstract class AbsBasicService<T> implements IBasicService<T> {

	protected Logger LOG = LoggerFactory.getLogger(getClass());

	@Autowired
	protected BasicMapper<T> mapper;

	@Override
	public T queryOne(Serializable id) {
		Assert.notNull(id, "主键不能为空");
		return mapper.queryUniqueById(id);
	}

	@Override
	public T queryOne(Map<String, Object> params) {
		List<T> list = queryList(params);
		return list == null || list.isEmpty() ? null : list.get(0);
	}

	@Override
	public List<T> queryList(Map<String, Object> params) {
		return mapper.queryListByParam(params);
	}

	@Override
	public long queryCount(Map<String, Object> params) {
		return mapper.queryCountByParam(params);
	}

	@Override
	public PageBean<T> queryPageList(DBParam params) {
		Assert.isTrue(params.isPagination(), "未找到分页参数.");
		PageBean<T> pageBean = new PageBean<T>();
		List<T> resultList = queryList(params);
		long totalCount = queryCount(params);
		pageBean.setCurPage(params.getCurPage());
		pageBean.setPageSize(params.getPageSize());
		pageBean.buildPage(resultList, totalCount);
		return pageBean;
	}
}
