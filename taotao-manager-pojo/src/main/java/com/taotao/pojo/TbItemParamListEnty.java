/**
 * 
 */
package com.taotao.pojo;

import java.util.Date;

/**
 * <p> Description: </p>
 * @author fengda
 * @date 2017年2月22日 上午10:17:04
 */
public class TbItemParamListEnty {
 	private Long id;

    private Long itemCatId;

    private Date created;

    private Date updated;

    private String paramData;
    
    private String itemCatName;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getItemCatId() {
		return itemCatId;
	}

	public void setItemCatId(Long itemCatId) {
		this.itemCatId = itemCatId;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Date getUpdated() {
		return updated;
	}

	public void setUpdated(Date updated) {
		this.updated = updated;
	}

	public String getParamData() {
		return paramData;
	}

	public void setParamData(String paramData) {
		this.paramData = paramData;
	}

	public String getItemCatName() {
		return itemCatName;
	}

	public void setItemCatName(String itemCatName) {
		this.itemCatName = itemCatName;
	}
}
