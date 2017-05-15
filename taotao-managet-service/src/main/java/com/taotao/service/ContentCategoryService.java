/**
 * 
 */
package com.taotao.service;

import java.util.List;

import com.taotao.common.pojo.EUTreeNode;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbContentCategory;

/**
 * <p> Description: </p>
 * @author fengda
 * @date 2017年2月28日 上午9:15:17
 */
public interface ContentCategoryService {

	public List<EUTreeNode> getCategoryList(long parentId);
	
	public TaotaoResult createCategory(Long parentId,String name);
	
	public TaotaoResult deleteCategory(Long id);
	
	public TaotaoResult updateCategory(Long id,String name);
}
