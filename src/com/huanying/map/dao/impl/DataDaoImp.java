package com.huanying.map.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.huanying.map.dao.BaseDao;
import com.huanying.map.dao.IDataDao;
import com.huanying.map.jdbc.DBClose;


public class DataDaoImp extends BaseDao implements IDataDao {


	@Override
	public List<String> getProvince(){
		List<String> result = new ArrayList<String>();
		String sql = "select name from chinadistrict where level=1 and (name!='占位符二号' AND name!='占位符')";
		ResultSet rs = executeQuery(sql,null);
		try {
			while (rs.next()) {
				result.add(rs.getString(1));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBClose.close(rs);
		}
		return result;
	}
	public List<String> getCity(String name){
		List<String> result = new ArrayList<String>();
		String sql = "select name FROM chinadistrict WHERE upid_three=(select id from chinadistrict WHERE name=?)";
		ResultSet rs = executeQuery(sql,name);
		try {
			while (rs.next()) {
				result.add(rs.getString(1));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBClose.close(rs);
		}
		return result;
	}
	public List<String> getwaterfactory(String name){
		List<String> result = new ArrayList<String>();
	 
		String sql = "select NodeName from nodeinfo where CityId=(select id from chinadistrict where name=?)";
		ResultSet rs = executeQuery(sql,name);
		try {
			while (rs.next()) {
				result.add(rs.getString(1));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBClose.close(rs);
		}
		return result;
	}

}
