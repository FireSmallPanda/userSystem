package com.bdqn.dao;

import java.io.Reader;
import java.util.Date;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.springframework.stereotype.Repository;

import com.bdqn.entity.ConsumerView;
@Repository(value="consumerViewDaoImpl")
public class ConsumerViewDaoImpl implements IConsumerViewDao {

	@Override
	public boolean addConsumerView(ConsumerView consumerView) {
		int result=-1;
		SqlSession session=null;
		// 初始意见状态
		consumerView.setConsumerViewStatus(1);
		consumerView.setConsumerViewDate(new Date());
		try {
			Reader reader=Resources.getResourceAsReader("mybatis-config.xml");
			SqlSessionFactoryBuilder builder=new SqlSessionFactoryBuilder();
			SqlSessionFactory factory= builder.build(reader);
			session=factory.openSession();
			result=session.update("com.bdqn.dao.IConsumerViewDao.addConsumerView",consumerView);
			session.commit();
		} catch (Exception e) {
			session.rollback();
			e.printStackTrace();
		}finally{
			session.close();
		}
		if(result>0){
			return true;
		}else{
			return false;
		}
	}

}
