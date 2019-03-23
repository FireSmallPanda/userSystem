package com.bdqn.action.freemarker;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import com.bdqn.dao.DictionarysDaoImpl;
import com.bdqn.entity.DictBean;
import com.bdqn.entity.Dictionarys;

import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;

public class DictCache implements TemplateMethodModelEx {

	@Resource
	private DictionarysDaoImpl dictionarysDaoImpl;

	/*
	 * SuppressWarnings压制警告，即去除警告 rawtypes是说传参时也要传递带泛型的参数
	 */
	@Override
	public Object exec(@SuppressWarnings("rawtypes") List arg0) throws TemplateModelException {
		if (arg0.size() == 2) {// 工厂方法判断是取列表还是取值
			String dictSystem = arg0.get(0).toString();// 表明查询哪个系统的字典
			String dictGroup = arg0.get(1).toString();
			return getDict(dictSystem, dictGroup, null);
		} else if (arg0.size() == 3) {
			String dictSystem = arg0.get(0).toString();
			String dictGroup = arg0.get(1).toString();
			String dictKey = arg0.get(2).toString();
			return getDict(dictSystem, dictGroup, dictKey);
		}
		return null;
	}

	private Object getDict(String dictSystem, String dictGroup, String dictKey) {
		if (dictSystem == null || "".equals(dictSystem) || dictGroup == null && "".equals(dictGroup)) {// 系统、组不指定将返回空
			return null;
		}
		Dictionarys in = new Dictionarys();// 输入字典
		in.setDictSystem(dictSystem);// 所搜索系统
		in.setDictGroup(dictGroup);// 所搜索组
		List<Dictionarys> dictList = dictionarysDaoImpl.searchDictionarys(in);
		List<DictBean> dictBeanList = new ArrayList<>();
		if (dictList != null) {
			if (dictKey == null) {// 查询所有
				DictBean bean = new DictBean();
				for (Dictionarys dic : dictList) { // 将查询出来的表转换为dictbean表
					bean = new DictBean();
					bean.setDictGroup(dic.getDictGroup());
					bean.setDictKey(dic.getDictKey());
					bean.setDictContentCN(dic.getDictContentCN());
					bean.setDictContentEN(bean.getDictContentEN());
					dictBeanList.add(bean);
				}
			} else {// 字典查询
				DictBean bean = new DictBean();
				for (Dictionarys dic : dictList) { // 将查询出来的表转换为dictbean表
					if (dic.getDictKey().equals(dictKey)) {
						bean.setDictGroup(dic.getDictGroup());
						bean.setDictKey(dic.getDictKey());
						bean.setDictContentCN(dic.getDictContentCN());
						bean.setDictContentEN(bean.getDictContentEN());
						dictBeanList.add(bean);
						break;
					}
				}
			}
			return dictBeanList;
		}
		return null;
	}

}
