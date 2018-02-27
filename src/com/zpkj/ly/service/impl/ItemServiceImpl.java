package com.zpkj.ly.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zpkj.ly.mapper.ItemsMapper;
import com.zpkj.ly.pojo.Items;
import com.zpkj.ly.service.ItemService;
@Service
public class ItemServiceImpl implements ItemService {
	
	@Autowired
	private ItemsMapper itemsMapper;
	
	@Override
	public List<Items> queryItemList() {
		List<Items> list = this.itemsMapper.selectByExample(null);
		return list;
	}
}
