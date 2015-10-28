package com.ljy.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.ljy.domain.User;
import com.ljy.util.SqlHelper;

public class UsersService {
	
	//Obtain pageCount
	public int getPageCount(int pageSize){
		String sql = "SELECT COUNT(*) FROM users";
		ResultSet rs = SqlHelper.executeQuery(sql, null);
		int rowCount = 0;
		try {
			rs.next();
			rowCount = rs.getInt(1);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			SqlHelper.close(rs, SqlHelper.getPs(), SqlHelper.getCt());
		}
		return (rowCount-1) / pageSize + 1;
	}
	
	
	//Page Division to obtain users
	//ResultSet -> User Object -> ArrayList(set)
	//Benefits: 1. Encapsulation User object : OOP
	//          2. ArrayList has nothing to do with ResultSet, so we can close recourses in time
	public ArrayList<User> getUsersByPage(int pageNow, int pageSize){
		ArrayList<User> al = new ArrayList<User>();
		String sql = "SELECT * FROM users LIMIT "+(pageSize*(pageNow-1))+", "+pageSize;
		ResultSet rs = SqlHelper.executeQuery(sql, null);
		//encapsulate into arraylist
		try {
			while(rs.next()){
				User u = new User();
				u.setId(rs.getInt(1));
				u.setName(rs.getString(2));
				u.setEmail(rs.getString(4));
				u.setGrade(rs.getInt(6));
				al.add(u);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			SqlHelper.close(rs, SqlHelper.getPs(), SqlHelper.getCt());
		}
		return al;
	}
	
	Connection ct = null;
	ResultSet rs = null;
	PreparedStatement ps = null;
	//Obtain user info
	public User getUserById(String id){
		String sql = "SELECT * FROM users WHERE id = ?";
		String[] parameters = {id};
		ResultSet rs = SqlHelper.executeQuery(sql, parameters);
		User user = new User();
		try {
			if(rs.next()){
				user.setId(rs.getInt(1));
				user.setName(rs.getString(2));
				user.setPwd(rs.getString(3));
				user.setEmail(rs.getString(4));
				user.setTel(rs.getString(5));
				user.setGrade(rs.getInt(6));				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return user;
	}
	//Add User
	public boolean addUser(User user){
		boolean b = true;
		String sql = "INSERT INTO users(name,pwd,email,tel,grade) VALUES(?,?,?,?,?)";
		String[] parameters = {user.getName(), user.getPwd(), user.getEmail(), user.getTel(), user.getGrade()+""};
		try{
			SqlHelper.executeUpdate(sql, parameters);
		} catch (Exception e){
			b = false;
		}
		return b;
	}
	//Update User
	public boolean updUser(User user){
		boolean b = true;
		String sql = "update users set name=?, pwd=?, email=?, tel=?, grade=? where id=?";
		String[] parameters = {user.getName(), user.getPwd(), user.getEmail(), user.getTel(), user.getGrade()+"", user.getId()+""};
		try{
			SqlHelper.executeUpdate(sql, parameters);
		} catch (Exception e) {
			b = false;
		}
		return b;
		
	}
	//Delete User
	public boolean delUser(String id){
		boolean b = true;
		String sql = "DELETE FROM users WHERE id = ?";
		String[] parameters = {id};
		try{
			SqlHelper.executeUpdate(sql, parameters);			
		} catch (Exception e) {
			b = false;
		}
		return b;
	}
	//function to validate if a User is legal
	public boolean checkUser(User user){
		
		//Connect to MySQL
		boolean b = false;
		
		String sql = "select * from users where id = ? and pwd = ?";
		String[] parameters = {user.getId()+"", user.getPwd()};
		ResultSet rs = SqlHelper.executeQuery(sql, parameters);
		try {
			if(rs.next()){
				b = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			SqlHelper.close(rs, SqlHelper.getPs(), SqlHelper.getCt());
		}
		return b;
	}
	
}
