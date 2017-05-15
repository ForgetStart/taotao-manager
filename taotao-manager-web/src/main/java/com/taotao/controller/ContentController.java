/**
 * 
 */
package com.taotao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.pojo.EUDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbContent;
import com.taotao.service.ContentService;

/**
 * <p> Description: 
 * 		内容管理
 * </p>
 * @author fengda
 * @date 2017年2月28日 下午2:34:19
 */
@Controller
public class ContentController {

	@Autowired
	private ContentService contentService;
	
	/**
	 * 根据内容分类展示内容
	 * @param page
	 * @param rows
	 * @param categoryId
	 * @return
	 */
	@RequestMapping("/content/query/list")
	@ResponseBody
	public EUDataGridResult getContentList(long page, long rows, long categoryId){
		EUDataGridResult result = contentService.getContentList(page, rows, categoryId);
		return result;
	}
	
	/**
	 * 添加内容
	 * @param content
	 * @return
	 */
	@RequestMapping(value="/content/save",method=RequestMethod.POST)
	@ResponseBody
	public TaotaoResult insertContent(TbContent content){
		contentService.insertContent(content);
		return TaotaoResult.ok();
	}
	
	/**
	 * 更新内容
	 * @param content
	 * @return
	 */
	@RequestMapping("/rest/content/edit")
	@ResponseBody
	public TaotaoResult updateContent(TbContent content){
		contentService.updateContent(content);
		return TaotaoResult.ok();
	}
	
	
	/**
	 * 编辑时，根据内容id查询要显示的内容
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/content/query/content",produces=MediaType.APPLICATION_JSON_VALUE+";charset=utf-8")
	@ResponseBody
	public String queryContent(long id){
		String content = contentService.queryContent(id);
		return content;
	}
	
	/**
	 * 根据id删除内容(可多选)
	 * @param ids
	 * @return
	 */
	@RequestMapping("/content/delete")
	@ResponseBody
	public TaotaoResult deleteContent(String ids){
		TaotaoResult result = contentService.deleteContent(ids);
		return result;
	}
	
}
