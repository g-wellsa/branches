package com.djh.weixiaotong.entity;

import java.io.Serializable;

/**
 * 评论实体类
 * 
 * @author Administrator
 * 
 */
public class CommentEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	private String comId; // 评论ID
	private String type; // 评论类型
	private String author; // 评论者
	private String authorId; // 评论ID
	private String content; // 评论内容
	private String date; // 评论日期
	private int grade; // 评论评分

	public String getComId() {
		return comId;
	}

	public void setComId(String comId) {
		this.comId = comId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getAuthorId() {
		return authorId;
	}

	public void setAuthorId(String authorId) {
		this.authorId = authorId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public int getGrade() {
		return grade;
	}

	public void setGrade(int grade) {
		this.grade = grade;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "CommentEntity [comId=" + comId + ", type=" + type + ", author="
				+ author + ", authorId=" + authorId + ", content=" + content
				+ ", date=" + date + ", grade=" + grade + "]";
	}

}
