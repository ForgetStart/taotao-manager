/**
 * 
 */
package com.taotao.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taotao.common.pojo.EUTreeNode;
import com.taotao.mapper.TbItemCatMapper;
import com.taotao.pojo.TbItemCat;
import com.taotao.pojo.TbItemCatExample;
import com.taotao.pojo.TbItemCatExample.Criteria;
import com.taotao.service.ItemCatService;

/**
 * <p> Description: 
 * 		商品类目的选择,新增商品时商品类目选择数据加载
 * </p>
 * @author fengda
 * @date 2017年2月17日 上午9:55:04
 */
@Service
public class ItemCatServiceImpl implements ItemCatService {

	@Autowired
	private TbItemCatMapper itemCatMapper;
	
	/* (non-Javadoc)
	 * @see com.taotao.service.ItemCatService#getCatList(long)
	 */
	@Override
	public List<EUTreeNode> getCatList(long parentId) {
		
		//创建查询条件
		TbItemCatExample catExample = new TbItemCatExample();
		Criteria criteria = catExample.createCriteria();
		criteria.andParentIdEqualTo(parentId);
		
		//执行查询
		List<TbItemCat> list = itemCatMapper.selectByExample(catExample);
		
		List<EUTreeNode> resultList = new ArrayList<EUTreeNode>();
		//把列表转换成treeNodelist
		for(TbItemCat itemCat:list){
			EUTreeNode node = new EUTreeNode();
			node.setId(itemCat.getId());
			node.setText(itemCat.getName());
			//不是叶子节点就是closed，叶子节点就是open
			node.setState(itemCat.getIsParent()?"closed":"open");
			resultList.add(node);
		}
		
		return resultList;
	}

}
