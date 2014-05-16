package com.example.server.dao.imp;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.example.helper.Logger;
import com.example.server.dao.AdminDao;
import com.example.server.dbase.DBManager;
import com.example.server.entity.LoginEntity;


public class AdminDaoImp implements AdminDao {
	private DBManager dbManager;
	private PreparedStatement preparedStatement;
	public AdminDaoImp() {
		dbManager=new DBManager();
	}

	public boolean login(LoginEntity admin) {
		boolean flag=false;
		try {
			preparedStatement=dbManager.getConnection().prepareStatement(loginSql);
			Logger.println(this,"login","1");
			preparedStatement.setString(1,admin.getUserId());
			preparedStatement.setString(2,admin.getUserPwd());
			ResultSet result=preparedStatement.executeQuery();
			flag=true;
			if(result.next()){
				admin.setUserName(result.getString(1));
				Logger.println(this,"login true",admin.getUserName());
				flag=true;
			}
			result.close();
			preparedStatement.close();
		} catch (SQLException e) {
			return false;
		}
		return flag;
	}

	public boolean register(LoginEntity admin) {
		boolean flag=false;
		try {
			preparedStatement=dbManager.getConnection().prepareStatement(registerSql);
			preparedStatement.setString(1,admin.getUserId());
			preparedStatement.setString(2,admin.getUserName());
			preparedStatement.setString(3,admin.getUserPwd());
			flag=preparedStatement.execute();
			preparedStatement.close();
		} catch (SQLException e) {
			return false;
		}
		return flag;
	}

}
