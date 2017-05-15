package com.taotao.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.pojo.EUDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbItemParam;
import com.taotao.service.ItemParamService;

/**
 * 商品规格参数模板管理
 * @author fd
 *
 */
@Controller
public class ItemParamController {

	@Autowired
	private ItemParamService itemParamService;
	
	/**
	 * 查询商品模板
	 * @param itemCatId
	 * @return
	 */
	@RequestMapping("/item/param/query/itemcatid/{itemCatId}")
	@ResponseBody
	public TaotaoResult getItemParamByCid(@PathVariable Long itemCatId){
		TaotaoResult result = itemParamService.getItemParamByCid(itemCatId);
		return result;
	}
	
	
	/**
	 * 获取商品模版列表
	 */
	@RequestMapping("/item/param/list")
	@ResponseBody
	public EUDataGridResult getItemParamList(HttpServletRequest request){
		String page = request.getParameter("page");
		String rows = request.getParameter("rows");
		EUDataGridResult dataGridResult = itemParamService.getItemParamList(Long.parseLong(page), Long.parseLong(rows));
		return dataGridResult;
	}
	
	/**
	 * 新增商品模板
	 */
	@RequestMapping("/item/param/save/{itemCatId}")
	@ResponseBody
	public TaotaoResult insertItemParam(HttpServletRequest request,@PathVariable long itemCatId){
		String paramData = request.getParameter("paramData");
		TbItemParam itemParam = new TbItemParam();
		itemParam.setItemCatId(itemCatId);
		itemParam.setParamData(paramData);
		TaotaoResult result = itemParamService.insertItemParam(itemParam);
		return result;
	}
}
