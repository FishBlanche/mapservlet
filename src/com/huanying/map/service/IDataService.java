package com.huanying.map.service;

import java.util.List;


public interface IDataService {

	public List<String> getProvince();
	public List<String> getCity(String name);
	public List<String> getwaterfactory(String name);
}
