package com.taotao.service;

import com.taotao.common.pojo.EUDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbItemParam;

public interface ItemParamService {

	public TaotaoResult getItemParamByCid(long cid);
	
	public EUDataGridResult getItemParamList(long page,long rows);
	
	
	public TaotaoResult insertItemParam(TbItemParam itemParam);
}
