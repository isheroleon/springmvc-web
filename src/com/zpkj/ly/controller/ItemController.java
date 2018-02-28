package com.zpkj.ly.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.zpkj.ly.pojo.Items;
import com.zpkj.ly.pojo.QueryVo;
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
	
	/**使用ModelandView向页面传递数据并且返回视图
	 * @param request
	 * @return
	 */
	@RequestMapping("/itemEdit")
	public ModelAndView queryItemById(HttpServletRequest request){
		ModelAndView modelAndView = new ModelAndView();
		String strId = request.getParameter("id");
		Integer id = Integer.valueOf(strId);
		Items item = itemService.queryItemById(id);
		modelAndView.addObject("item", item);
		modelAndView.setViewName("editItem");
		return modelAndView;
	}
	
	/**使用Model向页面传递对象
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/itemEditStr")
	public String queryItemByIdStr(HttpServletRequest request,Model model){
		String strId = request.getParameter("id");
		Integer id = Integer.valueOf(strId);
		Items items = itemService.queryItemById(id);
		model.addAttribute("item", items);
		return "editItem";
	}
	/**绑定简单 参数
	 * @return
	 */
	@RequestMapping("/itemEditParams")
	public String queryItemByIdParams(Integer id,ModelMap modelMap){
		Items items = itemService.queryItemById(id);
		//将商品数据放在模型中
		modelMap.addAttribute("item", items);
		return "editItem";
	}
	
	/**
	 * RequestParam
	 */
	@RequestMapping("/RequestParam")
	public String queryItemByIdRequestParams(@RequestParam(value="id",required=true,defaultValue="1") Integer id,
			ModelMap modelMap){
		Items items = itemService.queryItemById(id);
		modelMap.addAttribute("item", items);
		return "editItem";
	}
	/**
	 * 跟新商品,绑定pojo类型
	 */
	@RequestMapping("/updateitem")
	public String updateItem(Items items){
		this.itemService.updateItemById(items);
		//返回逻辑视图
		return "success";
	}
	/**
	 * 使用pojo作为查询条件
	 * @param queryVo
	 * @return
	 */
	@RequestMapping("/item/queryitem")
	public String queryItem(QueryVo queryVo){
		System.out.println(queryVo.getItems().getName());
		System.out.println(queryVo.getItems().getId());
		return "querySuccess";
	}
}
