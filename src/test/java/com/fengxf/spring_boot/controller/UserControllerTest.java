package com.fengxf.spring_boot.controller;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;

import com.fengxf.spring_boot.test.BaseTest;

public class UserControllerTest extends BaseTest {
	@Autowired
	private TestRestTemplate restTemplate;
	
	@Test
	public void list() {
		ResponseEntity<String> forEntity = restTemplate.getForEntity("/user/list", String.class);
		System.out.println(forEntity.getBody());
	}
}
