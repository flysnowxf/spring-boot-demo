package com.fengxf.spring_boot.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fengxf.spring_boot.model.User;
import com.fengxf.spring_boot.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	@Autowired
	private RedisTemplate<String, Object> redisTemplate;
	
	@RequestMapping("/list")
	@ResponseBody
	public List<User> getList(User user) {
		return userService.getList(user);
	}
	
	@RequestMapping("/get")
	@ResponseBody
	public User get(Integer id) {
		return userService.get(id);
	}
	
	@RequestMapping("/view")
	public String view(User user, Model model) {
		User userModel = null;
		// 手动设置缓存
		String key = "user_" + user.getId();
		if (!redisTemplate.hasKey(key)) {
			userModel = userService.get(user.getId());
			redisTemplate.opsForValue().set(key, userModel);
		}
		else {
			userModel = (User) redisTemplate.opsForValue().get(key);
		}
		model.addAttribute("user", userModel);
		
		return "user/view";
	}
	
	@RequestMapping("/cacheView")
	@Cacheable(cacheNames = "cacheView")
	public ModelAndView cacheView(User user) {
		ModelAndView modelAndView = new ModelAndView("user/view");
		User userModel = userService.get(user.getId());
		modelAndView.addObject("user", userModel);
		
		return modelAndView;
	}
}
