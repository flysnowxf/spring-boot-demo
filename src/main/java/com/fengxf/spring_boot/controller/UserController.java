package com.fengxf.spring_boot.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fengxf.spring_boot.model.User;
import com.fengxf.spring_boot.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {
	@Autowired
	private UserService userService;
	
	@RequestMapping("/list")
	public List<User> getList(User user) {
		return userService.getList(user);
	}
	
	@RequestMapping("/get")
	public User get(Integer id) {
		return userService.get(id);
	}
}
