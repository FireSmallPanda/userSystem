package com.bdqn.entity;

import java.io.Serializable;

/**
 * 字典实体类
 * 
 * @author Administrator
 *
 */
public class Dictionarys implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer dictId;
	private String dictSystem;
	private String dictName;
	private String dictGroup;
	private String dictKey;
	private String dictContentCN;
	private String dictContentEN;
	private Integer dictStatus;

	public Integer getDictId() {
		return dictId;
	}

	public void setDictId(Integer dictId) {
		this.dictId = dictId;
	}

	public String getDictSystem() {
		return dictSystem;
	}

	public void setDictSystem(String dictSystem) {
		this.dictSystem = dictSystem;
	}

	public String getDictGroup() {
		return dictGroup;
	}

	public void setDictGroup(String dictGroup) {
		this.dictGroup = dictGroup;
	}

	public String getDictKey() {
		return dictKey;
	}

	public void setDictKey(String dictKey) {
		this.dictKey = dictKey;
	}

	public String getDictContentCN() {
		return dictContentCN;
	}

	public void setDictContentCN(String dictContentCN) {
		this.dictContentCN = dictContentCN;
	}

	public String getDictContentEN() {
		return dictContentEN;
	}

	public void setDictContentEN(String dictContentEN) {
		this.dictContentEN = dictContentEN;
	}

	public Integer getDictStatus() {
		return dictStatus;
	}

	public void setDictStatus(Integer dictStatus) {
		this.dictStatus = dictStatus;
	}

	public String getDictName() {
		return dictName;
	}

	public void setDictName(String dictName) {
		this.dictName = dictName;
	}

}
