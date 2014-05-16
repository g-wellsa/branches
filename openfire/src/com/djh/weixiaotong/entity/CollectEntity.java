package com.djh.weixiaotong.entity;

import java.io.Serializable;

/**
 * 收藏实体类
 * 
 * @author Administrator
 * 
 */
public class CollectEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	private String collId; 			// 收藏ID
	private String typeName; // 类型名
	private String typeId; 		// 收藏类型ID
	private String userId; 		// 收藏用户
	private String userName; // 收藏用户
	private String date; 			// 收藏时间

	public String getCollId() {
		return collId;
	}

	public void setCollId(String collId) {
		this.collId = collId;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getTypeId() {
		return typeId;
	}

	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	@Override
	public String toString() {
		return "CollectEntity [collId=" + collId + ", typeName=" + typeName
				+ ", typeId=" + typeId + ", userId=" + userId + ", userName="
				+ userName + ", date=" + date + "]";
	}

}