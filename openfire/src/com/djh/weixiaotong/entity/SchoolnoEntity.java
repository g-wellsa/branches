package com.djh.weixiaotong.entity;

/**
 * 学校号实体类
 * 
 * @author Administrator
 * 
 */
public class SchoolnoEntity {
	private String schoolNo; // 学校号
	private String schoolLogo; // 学校logo(超链接)
	private String schoolName; // 学校名称
	private String schoolIntro; // 学校简介
	private int attentions; // 学校关注数

	public String getSchoolNo() {
		return schoolNo;
	}

	public void setSchoolNo(String schoolNo) {
		this.schoolNo = schoolNo;
	}

	public String getSchoolLogo() {
		return schoolLogo;
	}

	public void setSchoolLogo(String schoolLogo) {
		this.schoolLogo = schoolLogo;
	}

	public String getSchoolName() {
		return schoolName;
	}

	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}

	public String getSchoolIntro() {
		return schoolIntro;
	}

	public void setSchoolIntro(String schoolIntro) {
		this.schoolIntro = schoolIntro;
	}

	public int getAttentions() {
		return attentions;
	}

	public void setAttentions(int attentions) {
		this.attentions = attentions;
	}

	@Override
	public String toString() {
		return "SchoolnoEntity [schoolNo=" + schoolNo + ", schoolLogo="
				+ schoolLogo + ", schoolName=" + schoolName + ", schoolIntro="
				+ schoolIntro + ", attentions=" + attentions + "]";
	}

}
