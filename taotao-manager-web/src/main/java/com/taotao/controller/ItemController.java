/**
 * 
 */
package com.taotao.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.pojo.EUDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.ExceptionUtil;
import com.taotao.pojo.TbItem;
import com.taotao.service.ItemService;

/**
 * <p> Description: 商品管理</p>
 * @author fengda
 * @date 2017年2月15日 下午12:25:56
 */
@Controller
public class ItemController {
	
	@Autowired
	private ItemService itemService;
	
	/**
	 * 首页展示
	 * @param itemId
	 * @return
	 */
	
	@RequestMapping("/item/{itemId}")
	@ResponseBody
	public TbItem getItemById(@PathVariable Long itemId){		//通过路径获取参数
		TbItem item = itemService.getItemById(itemId);
		return item;
	}
	
	/**
	 * 查询商品列表
	 * @param request
	 * @return
	 */
	@RequestMapping("/item/list")
	@ResponseBody
	public EUDataGridResult getItemList(HttpServletRequest request){
		String page = request.getParameter("page");
		String rows = request.getParameter("rows");
		
		EUDataGridResult euDataGridResult = itemService.getItemList(Long.parseLong(page), Long.parseLong(rows));
		return euDataGridResult;
	}
	
	/**
	 * 商品添加
	 *（数据库中商品信息和商品描述是分开存储的,提高查询效率，商品描述是大文本，不需要时这样就可以不需要取出来）
	 * @param item			商品实体
	 * @param desc			商品描述
	 * @param itemParams	商品规格	
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/item/save",method=RequestMethod.POST)
	@ResponseBody
	public TaotaoResult createItem(TbItem item , String desc  , String itemParams) throws Exception{
		TaotaoResult result = itemService.createItem(item,desc,itemParams);
		return result;
	}
	
	/**
	 * 商品删除
	 */
	@RequestMapping(value="/rest/item/delete",method=RequestMethod.POST)
	@ResponseBody
	public TaotaoResult deleteItem(String ids){
		TaotaoResult result = itemService.deleteItem(ids);
		return result;
	}
	
	/**
	 * 商品下架
	 */
	@RequestMapping(value="/rest/item/instock",method=RequestMethod.POST)
	@ResponseBody
	public TaotaoResult xiaJiaItem(String ids){
		TaotaoResult result = itemService.instockItem(ids);
		return result;
	}
	
	/**
	 * 商品上架
	 */
	@RequestMapping(value="/rest/item/reshelf",method=RequestMethod.POST)
	@ResponseBody
	public TaotaoResult reshelfItem(String ids){
		TaotaoResult result = itemService.reshelfItem(ids);
		return result;
	}
	
	/**
	 * 查询商品描述
	 * @param itemId
	 * @return
	 */
	@RequestMapping("/rest/item/query/item/desc/{itemId}")
	@ResponseBody
	public TaotaoResult getItemDescByItemId(@PathVariable Long itemId){
		TaotaoResult result = itemService.getItemDescByItemId(itemId);
		return result;
	}
	
	/**
	 * 查询商品规格参数
	 * @param itemId
	 * @return
	 */
	@RequestMapping("/rest/item/param/item/query/{itemId}")
	@ResponseBody
	public TaotaoResult getItemParamItemByItemId(@PathVariable Long itemId){
		TaotaoResult result = itemService.getItemParamItemByItemId(itemId);
		return result;
	}
	
	/**
	 * 跳转商品编辑页面
	 * @return
	 */
	@RequestMapping("/rest/page/item-edit")
	public String redictModel(){
		return "item-edit";
	}
	
	
	@RequestMapping("/rest/item/update")
	@ResponseBody
	public TaotaoResult updateItem(TbItem item, String desc, String itemParams){
		TaotaoResult result;
		try {
			result = itemService.updateItem(item, desc, itemParams);
		} catch (Exception e) {
			e.printStackTrace();
			return TaotaoResult.build(500, ExceptionUtil.getStackTrace(e));
		}
		return result;
	}
}
