/**
 * 
 */
package com.taotao.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.pojo.EUTreeNode;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.service.ContentCategoryService;

/**
 * <p> Description: 
 * 		内容分类管理
 * </p>
 * @author fengda
 * @date 2017年2月28日 上午9:21:42
 */
@Controller
public class ContentCategoryController {

	@Autowired
	private ContentCategoryService categoryService;
	
	/**
	 * 展示内容分类列表
	 * @param parentId
	 * @return
	 */
	@RequestMapping("/content/category/list")
	@ResponseBody
	public List<EUTreeNode> getContenCatList(@RequestParam(value="id",defaultValue="0") Long parentId){
		List<EUTreeNode> list = categoryService.getCategoryList(parentId);
		return list;
	}
	
	/**
	 * 新增节点
	 * @param parentId
	 * @param name
	 * @return
	 */
	@RequestMapping("/content/category/create")
	@ResponseBody
	public TaotaoResult createCategory(@RequestParam(value="parentId") Long parentId,@RequestParam(value="name") String name){
		TaotaoResult result = categoryService.createCategory(parentId, name);
		return result;
	}
	
	/**
	 * 删除节点
	 * @param id
	 * @return
	 */
	@RequestMapping("/content/category/delete")
	@ResponseBody
	public TaotaoResult deleteCategory(Long id){
		TaotaoResult result = categoryService.deleteCategory(id);
		return result;
	}
	
	/**
	 * 节点重命名
	 * @param id
	 * @param name
	 * @return
	 */
	@RequestMapping("/content/category/update")
	@ResponseBody
	public TaotaoResult updataCategory(Long id, String name){
		TaotaoResult result = categoryService.updateCategory(id,name);
		return result;
	}
}
