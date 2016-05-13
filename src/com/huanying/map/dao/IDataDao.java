package com.huanying.map.dao;

import java.util.List;
import java.util.Map;


public interface IDataDao {

	public List<String> getProvince();
	public List<String> getCity(String name);
	public List<String> getwaterfactory(String name);
}
