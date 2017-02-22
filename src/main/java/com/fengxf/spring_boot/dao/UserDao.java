package com.fengxf.spring_boot.dao;

import com.fengxf.spring_boot.model.User;

import tk.mybatis.springboot.util.MyMapper;

public interface UserDao extends MyMapper<User> {
	
}
