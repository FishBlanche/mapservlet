package com.huanying.map.domain;

import java.io.Serializable;
import java.util.Date;

public class Data implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int dpower;
	private Date dtime;
	private int utag;
	private String rid;
	public Data(){
		
	}
	public Data(String rid, int utag, int dpower, Date dtime) {
		super();
		this.rid = rid;
		this.utag = utag;
		this.dpower = dpower;
		this.dtime = dtime;
	}

	public int getDpower() {
		return dpower;
	}

	public void setDpower(int dpower) {
		this.dpower = dpower;
	}

	public Date getDtime() {
		return dtime;
	}

	public void setDtime(Date dtime) {
		this.dtime = dtime;
	}

	public int getUtag() {
		return utag;
	}

	public void setUtag(int utag) {
		this.utag = utag;
	}

	public String getRid() {
		return rid;
	}

	public void setRid(String rid) {
		this.rid = rid;
	}

}
