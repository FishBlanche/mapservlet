package com.huanying.map.service.impl;

import java.util.List;
import java.util.Map;

import com.huanying.map.dao.IDataDao;
import com.huanying.map.dao.impl.DataDaoImp;
import com.huanying.map.service.IDataService;


public class DataServiceImpl implements IDataService {

	private IDataDao dataDao;
	
	public DataServiceImpl() {
		dataDao=new DataDaoImp();
	}
	
	@Override
	public List<String> getProvince(){
		return dataDao.getProvince();
	}
	
	public List<String> getCity(String name){
		return dataDao.getCity(name);
	}
	public List<String> getwaterfactory(String name){
		return dataDao.getwaterfactory(name);
	}
}
