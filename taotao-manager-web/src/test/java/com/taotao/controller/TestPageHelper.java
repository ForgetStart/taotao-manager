/**
 * 
 */
package com.taotao.controller;

import java.util.List;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.mapper.TbItemMapper;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemExample;

/**
 * <p> Description: </p>
 * @author fengda
 * @date 2017年2月16日 下午3:04:12
 */
public class TestPageHelper {

	@Test
	public void testPageHelper(){
		//创建一个spring容器
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-*.xml");
		//从spring中获取mapper的代理对象
		TbItemMapper tbItemMapper = applicationContext.getBean(TbItemMapper.class);
		TbItemExample example = new TbItemExample();
		
		//设置分页信息
		PageHelper.startPage(2, 10);
		//执行查询
		List<TbItem> list = tbItemMapper.selectByExample(example);
		
		//取出商品列表
		for (TbItem tbItem : list) {
			System.out.println(tbItem.getTitle());
		}
		
		//取出分页信息
		PageInfo<TbItem> pageInfo = new PageInfo<TbItem>(list);
		long total = pageInfo.getTotal();
		System.out.println("共有商品 " + total);
	}
}
