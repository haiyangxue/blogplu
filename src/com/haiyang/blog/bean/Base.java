package com.haiyang.blog.bean;

import java.io.Serializable;


/**
 * 实体基类：实现序列化
 */
@SuppressWarnings("serial")
public abstract class Base implements Serializable {

	protected Notice notice;

	public Notice getNotice() {
		return notice;
	}

	public void setNotice(Notice notice) {
		this.notice = notice;
	}
}
