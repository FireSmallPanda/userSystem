package com.bdqn.dao;

import java.util.List;

import com.bdqn.entity.Dictionarys;

public interface IDictionarysDao {

	/**
	 * 查询字典
	 * @param dictionarys
	 * @return
	 */
	List<Dictionarys> searchDictionarys(Dictionarys dictionarys);
}
