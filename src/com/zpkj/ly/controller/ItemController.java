package com.zpkj.ly.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.zpkj.ly.expection.MyException;
import com.zpkj.ly.pojo.Items;
import com.zpkj.ly.pojo.QueryVo;
import com.zpkj.ly.service.ItemService;

@Controller
public class ItemController {
	
	@Autowired
	private ItemService itemService;
	
	@RequestMapping("/test/itemList")
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
	 * @throws IOException 
	 * @throws IllegalStateException 
	 */
	@RequestMapping("/updateitem")
	public String updateItem(Items items,@RequestParam("pictureFile") MultipartFile multipartFile) throws IllegalStateException, IOException{
		if(multipartFile.getSize()!=0){
			//图片上传
			//设置图片名称,不能重复,可以使用uuid
			String picName = UUID.randomUUID().toString();
			//获取文件名
			String oriName = multipartFile.getOriginalFilename();
			//获取图片后缀
			String extName = oriName.substring(oriName.lastIndexOf("."));
			//开始上传
			multipartFile.transferTo(new File("C:/development/upload/image/" + picName + extName ));
			items.setPic(picName + extName);
		}
		this.itemService.updateItemById(items);
		//返回逻辑视图
		/*return "forward:/item/RequestParam.action";*/
		return "redirect:/RequestParam.action?id="+items.getId();
	}
	/**
	 * 使用pojo作为查询条件
	 * 绑定数组类型参数
	 * @param queryVo
	 * @return
	 * @throws MyException 
	 */
	@RequestMapping(method=RequestMethod.POST,value={"/item/queryitem"})
	public String queryItem(QueryVo queryVo,Integer[] ids) throws MyException{
		System.out.println(queryVo.getItems().getName());
		System.out.println(queryVo.getItems().getId());
		//自定义异常
		if(queryVo.getItems() == null){
			throw new MyException("自定义异常出现了");
		}
		//运行时异常
		//int a=1/0;
		
		System.out.println(queryVo.getIds().length);
		System.out.println(ids.length);
		return "querySuccess";
	}
	@RequestMapping(method=RequestMethod.POST,value={"/base/queryitem"})
	public void ItemList(HttpServletRequest request , HttpServletResponse response) throws ServletException, IOException{
		//请求转发相当于一次请求无需加服务地址
		request.getRequestDispatcher("").forward(request, response);
		//重定向前缀要加服务地址
		response.sendRedirect("");
	}
	
	//json交互
	@RequestMapping("/testjson")
	@ResponseBody
	public Items testJson(@RequestBody Items items){
		System.out.println(items);
		return items;
	}
	
	//RestFul风格的开发
	@RequestMapping(method=RequestMethod.GET,value={"/restful/query/{id}"})
	@ResponseBody
	public Items restfulQuery(@PathVariable Integer id){
		Items items = itemService.queryItemById(id);
		return items;
	}
	
}
