package com.bdqn.entity;

import java.io.Serializable;

public class DictBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String dictGroup;
	private String dictKey;
	private String dictContentCN;
	private String dictContentEN;

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

}
