package com.bdqn.util;

import java.util.List;

import org.springframework.stereotype.Component;

import com.bdqn.dao.DictionarysDaoImpl;
import com.bdqn.entity.DictBean;
import com.bdqn.entity.Dictionarys;
@Component("dictUtil")
public class DictUtil {
	/**
	 * 查询字典方法
	 * 
	 * @param dictSystem
	 * @param dictGroup
	 * @param dictKey
	 * @return
	 */
	public DictBean getDictByKey(String dictSystem, String dictGroup, String dictKey) {
		Dictionarys dictionarys = new Dictionarys();
		dictionarys.setDictStatus(1);
		dictionarys.setDictSystem(dictSystem);
		dictionarys.setDictGroup(dictGroup);
		DictBean retn = new DictBean();
		// 获取字典
		DictionarysDaoImpl dao = new DictionarysDaoImpl();
		List<Dictionarys> dictList = dao.searchDictionarys(dictionarys);
		for (Dictionarys dict : dictList) {
			if (dict.getDictKey().equals(dictKey)) {
				retn.setDictContentCN(dict.getDictContentCN());
				retn.setDictContentEN(dict.getDictContentEN());
				return retn;
			}

		}
		return null;

	};
}
