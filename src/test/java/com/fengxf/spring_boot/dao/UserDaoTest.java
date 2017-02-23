package com.fengxf.spring_boot.dao;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.fengxf.spring_boot.model.User;
import com.fengxf.spring_boot.test.BaseTest;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;


public class UserDaoTest extends BaseTest {
	@Autowired
	private UserDao userDao;
	
//	@Test
	public void list() {
		List<User> selectAll = userDao.selectAll();
		selectAll.forEach(user -> {
			System.out.println(user.getName());
		});
	}
	
	@Test
	public void page() {
		User user = new User();
		user.setPage(4);
		user.setRows(2);
		
		PageHelper.startPage(user.getPage(), user.getRows());
		// 这里返回的 list，其实是 Page 类型
		List<User> list = userDao.select(user);
		list.forEach(u -> {
			System.out.println(u.getName());
		});
		
		PageInfo<User> pageInfo = new PageInfo<User>(list);
		System.out.println("总数：" + pageInfo.getTotal());
		System.out.println("总页数：" + pageInfo.getPages());
		System.out.println("当前第几页：" + pageInfo.getPageNum());
		System.out.println("当前页数量：" + pageInfo.getPageSize());
	}
	
//	@Test
	public void example() {
		// like
		Example example = new Example(User.class);
		example.createCriteria().andLike("name", "%小%");
		List<User> list = userDao.selectByExample(example);
		list.forEach(user -> {
			System.out.println(user.getName());
		});
		
		// list
		List<Integer> idList = new ArrayList<>();
		idList.add(2);
		idList.add(3);
		example = new Example(User.class);
		example.createCriteria().andIn("id", idList);
		list = userDao.selectByExample(example);
		list.forEach(user -> {
			System.out.println(user.getName());
		});
		
		// 大于小于
		example = new Example(User.class);
		Criteria criteria = example.createCriteria();
		criteria.andGreaterThan("id", "2");
		criteria.andEqualTo("name", "小熊");
		list = userDao.selectByExample(example);
		list.forEach(user -> {
			System.out.println(user.getName());
		});
		
		// 范围
		example = new Example(User.class);
		criteria = example.createCriteria();
		criteria.andBetween("createDate", "2017-02-20", "2017-02-22");
		list = userDao.selectByExample(example);
		System.out.println("date");
		list.forEach(user -> {
			System.out.println(user.getName());
		});
	}
	
//	@Test
	public void crud() {
		User user = new User();
		user.setName("test");
		// 不要使用 insert 方法，会插入 null 替换了默认值
		userDao.insertSelective(user);
		
		user.setName("test1");
		// 不要使用 update 方法，会把 null 覆盖掉原来的值
		userDao.updateByPrimaryKeySelective(user);
		
		userDao.deleteByPrimaryKey(user.getId());
	}
}
