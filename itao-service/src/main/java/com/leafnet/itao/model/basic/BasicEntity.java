package com.leafnet.itao.model.basic;

import java.io.Serializable;
import java.util.Date;

public class BasicEntity implements Serializable {

	private static final long serialVersionUID = -3548859223914020954L;

	/**
	 * 数据有效性
	 */
	public enum Validity {
		VALID, INVALID;
	}

	/**
	 * 创建人
	 */
	protected String creator;

	/**
	 * 创建时间
	 */
	protected Date createTime;

	/**
	 * 最后编辑人
	 */
	protected String lastModifier;

	/**
	 * 最后编辑时间
	 */
	protected Date lastModifyTime;

	protected Validity validity = Validity.VALID;

	public Validity getValidity() {
		return validity;
	}

	public String getCreator() {
		if (this.creator == null)
			this.creator = getLastModifier();
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public String getLastModifier() {
		return lastModifier;
	}

	public void setLastModifier(String lastModifier) {
		this.lastModifier = lastModifier;
	}

	public Date getCreateTime() {
		if (this.createTime == null)
			this.createTime = getLastModifyTime();
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getLastModifyTime() {
		return lastModifyTime;
	}

	public void setLastModifyTime(Date lastModifyTime) {
		this.lastModifyTime = lastModifyTime;
	}

	public void setValidity(Validity validity) {
		this.validity = validity;
	}

}
