package com.fengxf.spring_boot.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fengxf.spring_boot.dao.UserDao;
import com.fengxf.spring_boot.model.User;

@Service
public class UserService {
	@Autowired
	private UserDao userDao;
	
	public List<User> getAll() {
		return userDao.getAll();
	}
	
	public User get(Integer id) {
		return userDao.get(id);
	}
}
