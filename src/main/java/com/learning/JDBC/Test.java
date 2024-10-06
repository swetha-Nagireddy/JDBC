package com.learning.JDBC;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Test {
	
	//credential:
	
	//url of Db
	private static final String url = "jdbc:mysql://localhost:3306/jdbclearning"; /* 3306 is port number,
	port number is used to request from server */ 
	
	
	//userName
	
	private static final String username = "root";
	
	//password
	
	private static final String password = "swetha281";
	
	
    public static void getDbConnection() throws ClassNotFoundException, SQLException {
    	Class.forName("com.mysql.cj.jdbc.Driver"); //com.mysql.cj.jdbc.Driver
    	Connection connection = DriverManager.getConnection(url,username,password);
    	
    	if(connection ==null) {
    		System.out.println("connection failed");
    	}else {
    		System.out.println(connection);
    	}
    }
	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		getDbConnection();

	}

}
