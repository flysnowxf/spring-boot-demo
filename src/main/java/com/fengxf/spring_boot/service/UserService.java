package com.fengxf.spring_boot.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fengxf.spring_boot.dao.UserDao;
import com.fengxf.spring_boot.model.User;
import com.github.pagehelper.PageHelper;

@Service
public class UserService {
	@Autowired
	private UserDao userDao;
	
	public List<User> getList(User user) {
		PageHelper.startPage(user.getPage(), user.getRows());
		return userDao.select(user);
	}
	
	public User get(Integer id) {
		return userDao.selectByPrimaryKey(id);
	}
}
