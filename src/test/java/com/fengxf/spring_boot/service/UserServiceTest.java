package com.fengxf.spring_boot.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.fengxf.spring_boot.model.User;
import com.fengxf.spring_boot.test.BaseTest;

public class UserServiceTest extends BaseTest {
	@Autowired
	private UserService userService;
	
	@Test
	public void get() {
		User param = new User();
		param.setId(1);
		User user = userService.get(param);
		System.out.println(user.getName());
	}
}
