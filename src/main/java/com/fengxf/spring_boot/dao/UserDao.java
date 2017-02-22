package com.fengxf.spring_boot.dao;

import java.util.List;

import com.fengxf.spring_boot.model.User;

public interface UserDao {
	public List<User> getAll();
	public User get(Integer id);
}
