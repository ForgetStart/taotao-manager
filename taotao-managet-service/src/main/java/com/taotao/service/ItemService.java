/**
 * 
 */
package com.taotao.service;

import com.taotao.common.pojo.EUDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbItem;

/**
 * <p> Description: </p>
 * @author fengda
 * @date 2017年2月15日 下午12:11:11
 */
public interface ItemService {

	public TbItem getItemById(long itemId);
	
	/**
	 * 查询商品
	 * @param page
	 * @param rows
	 * @return
	 */
	public EUDataGridResult getItemList(long page,long rows);
	
	/**
	 * 添加商品,添加商品的同时保存商品描述，商品表和商品描述是分表的
	 * 一个service中操作两个表，也保证了事务一致性
	 * @param itemParams 
	 * @throws Exception 
	 */
	public TaotaoResult createItem(TbItem item,String desc, String itemParams) throws Exception;
	
	/**
	 * 删除商品
	 */
	public TaotaoResult deleteItem(String ids);
	
	/**
	 * 下架商品
	 */
	public TaotaoResult instockItem(String ids);
	
	/**
	 * 上架商品
	 */
	public TaotaoResult reshelfItem(String ids);
	
	/**
	 * 查询商品描述
	 * @param itemId
	 * @return
	 */
	public TaotaoResult getItemDescByItemId(Long itemId);
	
	/**
	 * 查询商品规格参数
	 * @param itemId
	 * @return
	 */
	public TaotaoResult getItemParamItemByItemId(Long itemId);
	
	public TaotaoResult updateItem(TbItem item,String desc, String itemParams)throws Exception;
}
