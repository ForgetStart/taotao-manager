/**
 * 
 */
package com.taotao.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taotao.common.pojo.EUTreeNode;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.mapper.TbContentCategoryMapper;
import com.taotao.pojo.TbContentCategory;
import com.taotao.pojo.TbContentCategoryExample;
import com.taotao.pojo.TbContentCategoryExample.Criteria;
import com.taotao.service.ContentCategoryService;

/**
 * <p> Description: 
 * 		内容分类管理
 * </p>
 * @author fengda
 * @date 2017年2月28日 上午9:15:57
 */
@Service
public class ContentCategoryServiceImpl implements ContentCategoryService {

	@Autowired
	private TbContentCategoryMapper tbContentCategoryMapper;
	
	/**
	 * 内容分类列表树的展示
	 */
	@Override
	public List<EUTreeNode> getCategoryList(long parentId) {
		TbContentCategoryExample example = new TbContentCategoryExample();
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(parentId);
		List<TbContentCategory> list = tbContentCategoryMapper.selectByExample(example);
		List<EUTreeNode> resultList = new ArrayList<EUTreeNode>();
		for(TbContentCategory contentCategory : list){
			EUTreeNode node = new EUTreeNode();
			node.setId(contentCategory.getId());
			node.setText(contentCategory.getName());
			node.setState(contentCategory.getIsParent()?"closed":"open");
			resultList.add(node);
		}
		return resultList;
	}

	/**
	 * 新增节点
	 */
	@Override
	public TaotaoResult createCategory(Long parentId, String name) {
		TbContentCategory tbCategory = new TbContentCategory();
		tbCategory.setParentId(parentId);
		tbCategory.setName(name);
		tbCategory.setSortOrder(1);
		tbCategory.setStatus(1);
		tbCategory.setIsParent(false);
		tbCategory.setCreated(new Date());
		tbCategory.setUpdated(new Date());
		//添加记录
		tbContentCategoryMapper.insert(tbCategory);			//由于mapper添加了selectKey，所以完成添加后，实体中就包含了主键id的值
		
		//查看父节点的isParent列是否为true，如果不是true改为true
		TbContentCategory parentCat = tbContentCategoryMapper.selectByPrimaryKey(parentId);
		//如果不是true改为true
		if(!parentCat.getIsParent()){
			parentCat.setIsParent(true);
			tbContentCategoryMapper.updateByPrimaryKeySelective(parentCat);
		}
		return TaotaoResult.ok(tbCategory);
	}

	/**
	 * 删除节点
	 */
	@Override
	public TaotaoResult deleteCategory(Long id) {
		//根据id查询当前节点
		TbContentCategory cateGory = tbContentCategoryMapper.selectByPrimaryKey(id);

		//删除当前节点
		tbContentCategoryMapper.deleteByPrimaryKey(id);
		
		//判断当前节点的父节点下是否还有子节点，若没有了，父节点更改isParent为false
		TbContentCategoryExample example = new TbContentCategoryExample();
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(cateGory.getParentId());
		
		List<TbContentCategory> list = tbContentCategoryMapper.selectByExample(example);
		if(list.isEmpty() && list.size() == 0){
			TbContentCategory parentCat = tbContentCategoryMapper.selectByPrimaryKey(cateGory.getParentId());
			parentCat.setIsParent(false);
			tbContentCategoryMapper.updateByPrimaryKeySelective(parentCat);
		}
		return TaotaoResult.ok();
	}

	/**
	 * 更新节点
	 */
	@Override
	public TaotaoResult updateCategory(Long id, String name) {
		//根据id查询当前节点
		TbContentCategory cateGory = tbContentCategoryMapper.selectByPrimaryKey(id);
		cateGory.setName(name);
		tbContentCategoryMapper.updateByPrimaryKeySelective(cateGory);
		return TaotaoResult.ok();
	}

	
	

}
