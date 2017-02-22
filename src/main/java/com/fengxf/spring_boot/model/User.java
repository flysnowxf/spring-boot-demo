package com.fengxf.spring_boot.model;

public class User extends BaseEntity {
	private String name;
	private String realName;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	
}
