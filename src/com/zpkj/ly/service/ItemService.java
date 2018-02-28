package com.zpkj.ly.service;

import java.util.List;

import com.zpkj.ly.pojo.Items;

public interface ItemService {
	
	List<Items> queryItemList();
	
	Items queryItemById(Integer id);
	
	void updateItemById(Items items);

}
