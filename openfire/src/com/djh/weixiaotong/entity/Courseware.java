package com.djh.weixiaotong.entity;

import java.io.Serializable;

/**
 * 课件实体类
 * 
 * @author Administrator
 * 
 */
public class Courseware implements Serializable {

	private static final long serialVersionUID = 1L;
	private String courseId; // 课件ID
	private String title; // 课件标题
	private String school; // 所属学校
	private String teacher; // 所属老师
	private String subject; // 所属科目
	private String intro; // 课件简介
	private String author; // 课件作者
	private String company; // 作者单位
	private String publishDate; // 发布日期
	private boolean collect; // 是否收藏
	private double price; // 交易价格
	private int buyCounts; // 购买次数
	private int type; // 课件类型
	private int grade; // 评分

	public String getCourseId() {
		return courseId;
	}

	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSchool() {
		return school;
	}

	public void setSchool(String school) {
		this.school = school;
	}

	public String getTeacher() {
		return teacher;
	}

	public void setTeacher(String teacher) {
		this.teacher = teacher;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getIntro() {
		return intro;
	}

	public void setIntro(String intro) {
		this.intro = intro;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getPublishDate() {
		return publishDate;
	}

	public void setPublishDate(String publishDate) {
		this.publishDate = publishDate;
	}

	public int getBuyCounts() {
		return buyCounts;
	}

	public void setBuyCounts(int buyCounts) {
		this.buyCounts = buyCounts;
	}

	public boolean isCollect() {
		return collect;
	}

	public void setCollect(boolean collect) {
		this.collect = collect;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
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
		return "Courseware [courseId=" + courseId + ", title=" + title
				+ ", school=" + school + ", teacher=" + teacher + ", subject="
				+ subject + ", intro=" + intro + ", author=" + author
				+ ", company=" + company + ", publishDate=" + publishDate
				+ ", buyCounts=" + buyCounts + ", collect=" + collect
				+ ", price=" + price + ", type=" + type + ", grade=" + grade
				+ "]";
	}

}
