package com.djh.weixiaotong.entity;

import java.io.Serializable;

/**
 * 培训课实体类
 * 
 * @author Administrator
 * 
 */
public class TrainCourseEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	private int grade; // 评分
	private int type; // 培训课类型
	private int willNum; // 报名人数
	private int willTotal; // 报名总数
	private String trainId; // 培训课ID
	private String school; // 开课学校
	private String teacher; // 任教老师
	private String subject; // 所属科目
	private String name; // 培训课名
	private String intro; // 培训课介绍
	private String beginDate; // 开课日期
	private String publishDate; // 发布日期
	private String area; // 所在地区
	private String phone; // 联系电话
	private boolean register; // 是否报名
	private boolean collect; // 是否收藏

	public int getGrade() {
		return grade;
	}

	public void setGrade(int grade) {
		this.grade = grade;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getWillNum() {
		return willNum;
	}

	public void setWillNum(int willNum) {
		this.willNum = willNum;
	}

	public int getWillTotal() {
		return willTotal;
	}

	public void setWillTotal(int willTotal) {
		this.willTotal = willTotal;
	}

	public String getTrainId() {
		return trainId;
	}

	public void setTrainId(String trainId) {
		this.trainId = trainId;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIntro() {
		return intro;
	}

	public void setIntro(String intro) {
		this.intro = intro;
	}

	public String getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}

	public String getPublishDate() {
		return publishDate;
	}

	public void setPublishDate(String publishDate) {
		this.publishDate = publishDate;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public boolean isRegister() {
		return register;
	}

	public void setRegister(boolean register) {
		this.register = register;
	}

	public boolean isCollect() {
		return collect;
	}

	public void setCollect(boolean collect) {
		this.collect = collect;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "TrainCourseEntity [grade=" + grade + ", type=" + type
				+ ", willNum=" + willNum + ", willTotal=" + willTotal
				+ ", trainId=" + trainId + ", school=" + school + ", teacher="
				+ teacher + ", subject=" + subject + ", name=" + name
				+ ", intro=" + intro + ", beginDate=" + beginDate
				+ ", publishDate=" + publishDate + ", area=" + area
				+ ", phone=" + phone + ", register=" + register + ", collect="
				+ collect + "]";
	}
	
}
