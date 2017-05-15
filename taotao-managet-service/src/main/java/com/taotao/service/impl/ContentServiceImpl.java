/**
 * 
 */
package com.taotao.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.common.pojo.EUDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.HttpClientUtil;
import com.taotao.mapper.TbContentMapper;
import com.taotao.pojo.TbContent;
import com.taotao.pojo.TbContentExample;
import com.taotao.pojo.TbContentExample.Criteria;
import com.taotao.service.ContentService;

/**
 * <p> Description: 
 * 		内容管理
 * </p>
 * @author fengda
 * @date 2017年2月28日 下午2:28:00
 */
@Service
public class ContentServiceImpl implements ContentService {

	@Autowired
	private TbContentMapper tbContentMapper;
	
	@Value("${REST_BASE_URL}")
	private String REST_BASE_URL;
	
	@Value("${REST_CONTENT_SYNC_URL}")
	private String REST_CONTENT_SYNC_URL;
	/**
	 * 根据内容分类id查询内容
	 */
	@Override
	public EUDataGridResult getContentList(long page, long rows, long categoryId) {
		
		TbContentExample example = new TbContentExample();
		
		//在执行查询前设置分页信息
		PageHelper.startPage(new Integer((int)page), new Integer((int)rows));
				
		Criteria criteria = example.createCriteria();
		criteria.andCategoryIdEqualTo(categoryId);
		List<TbContent> list = tbContentMapper.selectByExample(example);
		
		
		PageInfo<TbContent> pageInfo = new PageInfo<TbContent>(list);
		
		EUDataGridResult dataGridResult = new EUDataGridResult();
		dataGridResult.setTotal(pageInfo.getTotal());
		dataGridResult.setRows(pageInfo.getList());
		
		return dataGridResult;
	}

	/**
	 * 添加内容
	 */
	@Override
	public TaotaoResult insertContent(TbContent content) {
		content.setCreated(new Date());
		content.setUpdated(new Date());
		tbContentMapper.insert(content);
		
		try {
			//添加缓存同步逻辑(将缓存中内容对应的分类id在缓存中的数据删除)
			HttpClientUtil.doGet(REST_BASE_URL + REST_CONTENT_SYNC_URL + content.getCategoryId());
		} catch (Exception e) {
			e.printStackTrace();
			//通知管理员某个缓存更新失败
		}
		return TaotaoResult.ok();
	}

	/**
	 * 编辑时，根据内容id查询要显示的内容 
	 */
	@Override
	public String queryContent(long id) {
		TbContent tbContent = tbContentMapper.selectByPrimaryKey(id);
		
		return tbContent.getContent();
	}

	/**
	 * 更新内容
	 */
	@Override
	public TaotaoResult updateContent(TbContent content) {
		tbContentMapper.updateByPrimaryKeySelective(content);
		
		try {
			//添加缓存同步逻辑(将缓存中内容对应的分类id在缓存中的数据删除)
			HttpClientUtil.doGet(REST_BASE_URL + REST_CONTENT_SYNC_URL + content.getCategoryId());
		} catch (Exception e) {
			e.printStackTrace();
			//通知管理员某个缓存更新失败
		}
		return TaotaoResult.ok();
	}

	/**
	 * 根据id删除内容（可多选）
	 */
	@Override
	public TaotaoResult deleteContent(String ids) {
		TbContentExample example = new TbContentExample();
		Criteria criteria = example.createCriteria();
		
		String[] strings = ids.split(",");
		List<Long> idList = new ArrayList<Long>();
		
		for(String string : strings){
			idList.add(Long.valueOf(string));
		}
		
		criteria.andIdIn(idList);
		//执行删除
		tbContentMapper.deleteByExample(example);
		
		
		try {
			//添加缓存同步逻辑(将缓存中内容对应的分类id在缓存中的数据删除)
			List<TbContent> list = tbContentMapper.selectByExample(example);
			if(!list.isEmpty() && list.size() >0){
				HttpClientUtil.doGet(REST_BASE_URL + REST_CONTENT_SYNC_URL + list.get(0).getCategoryId());
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			//通知管理员某个缓存更新失败
		}
		
		return TaotaoResult.ok();
	}

}
