package com.bdqn.dao;

import java.io.Reader;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.springframework.stereotype.Repository;

import com.bdqn.entity.Dictionarys;
@Repository(value="dictionarysDaoImpl")
public class DictionarysDaoImpl implements IDictionarysDao {

	@Override
	public List<Dictionarys> searchDictionarys(Dictionarys dictionarys) {
		SqlSession session=null;
		dictionarys.setDictStatus(1);// 查询可用
		try {
			Reader reader=Resources.getResourceAsReader("mybatis-config.xml");
			SqlSessionFactoryBuilder builder=new SqlSessionFactoryBuilder();
			SqlSessionFactory factory= builder.build(reader);
			session=factory.openSession();
			return session.selectList("com.bdqn.dao.IDictionarysDao.serachDictionarys",dictionarys);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			session.close();
		}
		return null;
	}

}
