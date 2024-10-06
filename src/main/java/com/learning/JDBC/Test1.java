package com.learning.JDBC;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

public class Test1 {

	private static final String url ="jdbc:mysql://localhost:3306/jdbclearning";
	//"jdbc:mysql://localhost:3306/jdbclearning";
	private static final String username="root";
	private static final String password="swetha281";
	
	private static Connection con=null;
	public static Connection getDbConnection() throws ClassNotFoundException,SQLException{
		if(con==null) {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con=DriverManager.getConnection(url,username,password);
			}
		return con;
	}
	
	public static void getMetaData() throws ClassNotFoundException, SQLException {
		String query="select * from product";
		Statement stmt=getDbConnection().createStatement();
		ResultSet rs=stmt.executeQuery(query);
		ResultSetMetaData metaData = rs.getMetaData();
		System.out.println(metaData.getColumnCount());
		System.out.println(metaData.getColumnName(1)+" "+metaData.getColumnTypeName(1));
	}
	
	public static void getDBMetaData() throws ClassNotFoundException, SQLException {
		DatabaseMetaData dbMetaData =getDbConnection().getMetaData();
		System.out.println(dbMetaData.getURL());
		System.out.println(dbMetaData.getUserName());
		System.out.println(dbMetaData.getDriverName());
		System.out.println(dbMetaData.getDriverVersion());
		System.out.println(dbMetaData.getStringFunctions());
		System.out.println(dbMetaData.getNumericFunctions());
	}
	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		getMetaData();
		//getDBMetaData();
	}

}
