/**
 * 
 */
package com.taotao.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.common.pojo.EUDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.IDUtils;
import com.taotao.mapper.TbItemDescMapper;
import com.taotao.mapper.TbItemMapper;
import com.taotao.mapper.TbItemParamItemMapper;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemDesc;
import com.taotao.pojo.TbItemExample;
import com.taotao.pojo.TbItemExample.Criteria;
import com.taotao.pojo.TbItemParamItem;
import com.taotao.pojo.TbItemParamItemExample;
import com.taotao.service.ItemService;

/**
 * <p> Description:商品管理service </p>
 * @author fengda
 * @date 2017年2月15日 下午12:12:29
 */
@Service
public class ItemServiceImpl implements ItemService {
	
	@Autowired
	private TbItemMapper itemMapper;
	@Autowired
	private TbItemDescMapper itemDescMapper;
	@Autowired
	private TbItemParamItemMapper itemParamItemMapper;

	/* (non-Javadoc)
	 * @see com.taotao.service.ItemService#getItemById(long)
	 */
	@Override
	public TbItem getItemById(long itemId) {
//		TbItem item = itemMapper.selectByPrimaryKey(itemId);
		
		//通过example查询
		TbItemExample example = new TbItemExample();
		Criteria criteria = example.createCriteria();
		criteria.andIdEqualTo(itemId);									//添加查询条件
		criteria.andTitleLike("%联通3G手机%");
		
		List<TbItem> list = itemMapper.selectByExample(example);		//默认查询所有
		if(list != null && list.size() >0){
			TbItem tbItem = list.get(0);
			return tbItem;
		}
		return null;
	}

	
	/**
	 * 查询商品列表
	 * page		显示第几页
	 * rows		每页显示多少条
	 */
	@Override
	public EUDataGridResult getItemList(long page, long rows) {
		TbItemExample example = new TbItemExample();
		
		//在执行查询前设置分页信息
		PageHelper.startPage(new Integer((int)page), new Integer((int)rows));
		
		//执行查询
		List<TbItem> list = itemMapper.selectByExample(example);
		EUDataGridResult dataGridResult = new EUDataGridResult();
		
		PageInfo pageInfo = new PageInfo<TbItem>(list);
		dataGridResult.setRows(pageInfo.getList());
		dataGridResult.setTotal(pageInfo.getTotal());
		return dataGridResult;
	}


	/**
	 * 添加商品
	 */
	@Override
	public TaotaoResult createItem(TbItem item , String desc, String itemParams) throws Exception {
		// item补全
		//生成商品id
		long itemId = IDUtils.genItemId();
		item.setId(itemId);
		//商品状态，1-正常，2-下架，3-删除
		item.setStatus((byte) 1);
		item.setCreated(new Date());
		item.setUpdated(new Date());
		//插入到数据库
		itemMapper.insert(item);
		
		//添加商品描述
		TaotaoResult result = insertItemDesc(itemId, desc);
		
		//为了控制事务的回滚，所以此处抛出一个异常
		if(result.getStatus() != 200){
			throw new Exception();
		}
		
		//保存商品的规格参数
		TaotaoResult temParamResult = insertItemParamItem(itemId,itemParams);
		if(temParamResult.getStatus() != 200){
			throw new Exception();
		}
		
		return TaotaoResult.ok();
	}

	
	
	/**
	 * 添加商品描述
	 */
	private TaotaoResult insertItemDesc(Long itemId,String desc){
		TbItemDesc itemDesc = new TbItemDesc();
		itemDesc.setItemId(itemId);
		itemDesc.setItemDesc(desc);
		itemDesc.setCreated(new Date());
		itemDesc.setUpdated(new Date());
		//插入到数据库
		itemDescMapper.insert(itemDesc);
		return TaotaoResult.ok();
	}

	/**
	 * 查询商品描述
	 * @return
	 */
	public TaotaoResult getItemDescByItemId(Long itemId){
		TbItemDesc itemDesc = itemDescMapper.selectByPrimaryKey(itemId);
		return TaotaoResult.ok(itemDesc);
	}
	/**
	 * 添加商品规格参数
	 */
	private TaotaoResult insertItemParamItem(long itemId,String itemParams){
		TbItemParamItem paramItem = new TbItemParamItem();
		paramItem.setItemId(itemId);
		paramItem.setParamData(itemParams);
		paramItem.setCreated(new Date());
		paramItem.setUpdated(new Date());
		itemParamItemMapper.insert(paramItem);
		
		return TaotaoResult.ok();
	}
	
	/**
	 * 根据商品id删除商品
	 */
	@Override
	public TaotaoResult deleteItem(String ids) {
		TbItemExample itemExample = new TbItemExample();
		Criteria criteria = itemExample.createCriteria();
		
		String[] strings = ids.split(",");
		List<Long> idList = new ArrayList<Long>();
		
		for(String string : strings){
			idList.add(Long.valueOf(string));
		}
		criteria.andIdIn(idList);
		itemMapper.deleteByExample(itemExample);
		
		return TaotaoResult.ok();
	}


	/**
	 * 下架商品，将商品状态改为2
	 * 商品状态，1-正常，2-下架，3-删除
	 */
	@Override
	public TaotaoResult instockItem(String ids) {
		TbItem item = new TbItem();
		item.setStatus((byte) 2);								//设置要改哪个字段，要改成什么值
		
		TbItemExample itemExample = new TbItemExample();		//创建添加条件的实体类
		Criteria criteria = itemExample.createCriteria();
		
		String[] strings = ids.split(",");
		List<Long> idList = new ArrayList<Long>();
		
		for(String string : strings){
			idList.add(Long.valueOf(string));
		}
		
		criteria.andIdIn(idList);								//添加条件
		itemMapper.updateByExampleSelective(item,itemExample);
		
		return TaotaoResult.ok();
	}


	/**
	 * 上架商品,将商品状态改为1
	 * 商品状态，1-正常，2-下架，3-删除
	 */
	@Override
	public TaotaoResult reshelfItem(String ids) {
		
		//设置要修改哪个字段，要修改成什么值，以实体形式传入
		TbItem item = new TbItem();
		item.setStatus((byte) 1);
		
		TbItemExample tbItemExample = new TbItemExample();			//创建添加条件的实体类
		Criteria criteria = tbItemExample.createCriteria();
		
		//封装ids成List
		List<Long> idList = new ArrayList<Long>();
		String[] strings = ids.split(",");
		for(String id : strings){
			idList.add(Long.valueOf(id));
		}
		
		criteria.andIdIn(idList);									//添加条件
		itemMapper.updateByExampleSelective(item, tbItemExample);
		
		return TaotaoResult.ok();
	}


	/**
	 * 展示商品规格参数
	 */
	@Override
	public TaotaoResult getItemParamItemByItemId(Long itemId) {
		TbItemParamItemExample example = new TbItemParamItemExample();
		com.taotao.pojo.TbItemParamItemExample.Criteria criteria = example.createCriteria();
		criteria.andItemIdEqualTo(itemId);
		List<TbItemParamItem> list = itemParamItemMapper.selectByExampleWithBLOBs(example);
		if(list != null && list.size() > 0){
			return TaotaoResult.ok(list.get(0));
		}
		return TaotaoResult.ok();
	}


	/**
	 * 更新商品信息
	 */
	@Override
	public TaotaoResult updateItem(TbItem item, String desc, String itemParams)
			throws Exception {
		
		itemMapper.updateByPrimaryKeySelective(item);
		
		
		TaotaoResult taotaoResult = updateItemDesc(item, desc);
		if(taotaoResult.getStatus() != 200){
			throw new Exception();
		}
		
		TaotaoResult result = updateItemParam(item, itemParams);
		if(result.getStatus() != 200){
			throw new Exception();
		}
		
		return TaotaoResult.ok();
	}
	
	/**
	 * 更新商品描述信息
	 * @param item
	 * @param desc
	 * @return
	 */
	private TaotaoResult updateItemDesc(TbItem item, String desc){
		TbItemDesc itemDesc = new TbItemDesc();
		itemDesc.setItemDesc(desc);
		itemDesc.setItemId(item.getId());
		itemDescMapper.updateByPrimaryKeyWithBLOBs(itemDesc);
		return TaotaoResult.ok();
	}
	
	/**
	 * 更新商品规格参数信息
	 * @param item
	 * @param itemParams
	 * @return
	 */
	private TaotaoResult updateItemParam(TbItem item, String itemParams){
		TbItemParamItemExample example = new TbItemParamItemExample();
		com.taotao.pojo.TbItemParamItemExample.Criteria criteria = example.createCriteria();
		criteria.andItemIdEqualTo(item.getId());
		List<TbItemParamItem> list = itemParamItemMapper.selectByExampleWithBLOBs(example);
		if(list != null && list.size() > 0){
			TbItemParamItem itemParamItem = list.get(0);
			itemParamItem.setParamData(itemParams);
			itemParamItemMapper.updateByPrimaryKeyWithBLOBs(itemParamItem);
		}
		return TaotaoResult.ok();
	}
	
}
