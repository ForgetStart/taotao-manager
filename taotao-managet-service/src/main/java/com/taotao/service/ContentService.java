/**
 * 
 */
package com.taotao.service;

import com.taotao.common.pojo.EUDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbContent;

/**
 * <p> Description: </p>
 * @author fengda
 * @date 2017年2月28日 下午2:25:40
 */
public interface ContentService {

	public EUDataGridResult getContentList(long page,long rows,long categoryId);
	
	public TaotaoResult insertContent(TbContent content);
	
	public String queryContent(long id);
	
	public TaotaoResult updateContent(TbContent content);
	
	public TaotaoResult deleteContent(String ids);
}
