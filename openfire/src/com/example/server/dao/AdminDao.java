package com.example.server.dao;

import com.example.server.entity.LoginEntity;

public interface AdminDao {
	static final String loginSql="select userName from admin where userId=? and userPwd = ?";
	static final String registerSql="insert into admin values(?,?,?)";
	boolean login(LoginEntity admin);
	boolean register(LoginEntity admin);
}
