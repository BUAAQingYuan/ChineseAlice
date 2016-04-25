package cn.buaaqingyuan.DataSorce;

import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import cn.buaaqingyuan.Entity.Organization;
import cn.buaaqingyuan.Entity.School;

//实体-属性
public class DbApproach {
	
	private static SqlSessionFactory sessionFactory = null;
	
	//读取mybatis配置文件
	static{
		Reader reader=null;
		try {
			reader = Resources.getResourceAsReader("mybatis_config.xml");
		} catch (IOException e) {
			e.printStackTrace();
		}
		sessionFactory=new SqlSessionFactoryBuilder().build(reader);
	}
	
	public static SqlSession getSession()
	{
		return sessionFactory.openSession();
	}
	
	//获取实体属性值
	public static String queryEntityProperty(String entity,String targetproperty,String queryproperty,String queryvalue) {

		Map<String,Object> map=new HashMap<String,Object>();
		
		map.put("tablename", entity);
		map.put("targetproperty", targetproperty);
		map.put("queryproperty", queryproperty);
		map.put("queryvalue", queryvalue);
		
		String state="SqlMapper.TemplateMapper.getPropertyByname";
		SqlSession session=getSession();
		String result=(String) session.selectOne(state, map);
		return result;
	}
	
	public static void main(String[] args)
	{
		
		System.out.println(DbApproach.queryEntityProperty("yuanshi","description","name","﻿陈永川"));
		//System.out.println(DbApproach.queryEntityProperty("yuanshi","description","name","﻿艾国祥"));
	}

}
