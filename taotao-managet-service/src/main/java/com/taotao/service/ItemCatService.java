/**
 * 
 */
package com.taotao.service;

import java.util.List;

import com.taotao.common.pojo.EUTreeNode;

/**
 * <p> Description: 
 * 		商品类目的选择,新增商品时商品类目选择数据加载
 * </p>
 * @author fengda
 * @date 2017年2月17日 上午9:53:50
 */
public interface ItemCatService {
	
	List<EUTreeNode> getCatList(long parentId);
}
