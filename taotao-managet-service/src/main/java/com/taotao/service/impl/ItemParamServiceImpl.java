package com.taotao.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.taotao.common.pojo.EUDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.mapper.TbItemCatMapper;
import com.taotao.mapper.TbItemParamMapper;
import com.taotao.pojo.TbItemCat;
import com.taotao.pojo.TbItemParam;
import com.taotao.pojo.TbItemParamExample;
import com.taotao.pojo.TbItemParamExample.Criteria;
import com.taotao.pojo.TbItemParamListEnty;
import com.taotao.service.ItemParamService;

/**
 * 商品规格参数模板
 * @author fd
 *
 */
@Service
public class ItemParamServiceImpl implements ItemParamService {

	@Autowired
	private TbItemParamMapper tbItemParamMapper;
	@Autowired
	private TbItemCatMapper itemCatMapper;
	
	@Override
	public TaotaoResult getItemParamByCid(long cid) {
		TbItemParamExample example = new TbItemParamExample();
		Criteria criteria = example.createCriteria();
		criteria.andItemCatIdEqualTo(cid);
		List<TbItemParam> list = tbItemParamMapper.selectByExampleWithBLOBs(example);
		//判断是否查询到结果
		if(list != null && list.size() >0){
			return TaotaoResult.ok(list.get(0));
		}
		return TaotaoResult.ok();
	}
	
	
	/**
	 * 获取商品规格模板列表
	 */
	@Override
	public EUDataGridResult getItemParamList(long page, long rows) {
		TbItemParamExample example = new TbItemParamExample();
		
		//在执行查询前设置分页信息
		PageHelper.startPage(new Integer((int)page), new Integer((int)rows));
		List<TbItemParam> list = tbItemParamMapper.selectByExampleWithBLOBs(example);
		List<TbItemParamListEnty> itemParamList = new ArrayList<TbItemParamListEnty>();
		EUDataGridResult dataGridResult = new EUDataGridResult();
		
		for(TbItemParam tbItemParam : list){
			TbItemParamListEnty itemParamEntity = new TbItemParamListEnty();
			itemParamEntity.setId(tbItemParam.getId());
			itemParamEntity.setItemCatId(tbItemParam.getItemCatId());
			itemParamEntity.setCreated(tbItemParam.getCreated());
			itemParamEntity.setUpdated(tbItemParam.getUpdated());
			itemParamEntity.setParamData(tbItemParam.getParamData());
			
			TbItemCat tbItemCat = itemCatMapper.selectByPrimaryKey(tbItemParam.getItemCatId());
			itemParamEntity.setItemCatName(tbItemCat.getName());
			itemParamList.add(itemParamEntity);
		}
		
		dataGridResult.setRows(itemParamList);
		dataGridResult.setTotal(itemParamList.size());
		return dataGridResult;
	}


	/**
	 * 新增参数模板
	 */
	@Override
	public TaotaoResult insertItemParam(TbItemParam itemParam) {
		itemParam.setCreated(new Date());
		itemParam.setUpdated(new Date());
		tbItemParamMapper.insert(itemParam);
		return TaotaoResult.ok();
	}

}
