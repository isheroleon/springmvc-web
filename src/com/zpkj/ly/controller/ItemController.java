package com.zpkj.ly.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.zpkj.ly.pojo.Items;
import com.zpkj.ly.service.ItemService;

@Controller
public class ItemController {
	
	@Autowired
	private ItemService itemService;
	
	@RequestMapping("/itemList")
	public ModelAndView queryItemList(){
		ModelAndView modelAndView = new ModelAndView();
		//获取商品数据
		List<Items> queryItemList = itemService.queryItemList();
		modelAndView.addObject("itemList", queryItemList);
		modelAndView.setViewName("itemList");
		return modelAndView;	
	}

}
