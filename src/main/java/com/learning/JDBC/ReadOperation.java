package com.learning.JDBC;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ReadOperation {
	
private static final String url = "jdbc:mysql://localhost:3306/jdbclearning";
	
	private static final String username ="root";
	
	private static final String password ="swetha281";
	
	private static Connection con = null;
	public static Connection getDbConnection() throws ClassNotFoundException, SQLException {
		if(con == null) {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection(url,username,password);
		}
		return con;
	}
	
	public static void readAll() throws ClassNotFoundException, SQLException {
		String query = "select * from product";
		PreparedStatement ps = getDbConnection().prepareStatement(query);
		ResultSet rs = ps.executeQuery();
		
		while(rs.next()) {
			System.out.println("ProductId : "+rs.getInt(1));
			System.out.println("ProductName : "+ rs.getString(2));
			System.out.println("ProductPrice : "+rs.getDouble(3));
			System.out.println("CategoryName: "+rs.getString(4));
			System.out.println("---------------------------------");
		}
	}
	
	public static void getProductById(int ProductId) throws ClassNotFoundException, SQLException {
		String Query = "select * from product where productId = ?";
		PreparedStatement ps = getDbConnection().prepareStatement(Query);
		ps.setInt(1, ProductId);
		ResultSet rs= ps.executeQuery();
		while(rs.next()) {
			System.out.println("ProductId : "+rs.getInt(1));
			System.out.println("ProductName : "+ rs.getString(2));
			System.out.println("ProductPrice : "+rs.getDouble(3));
			System.out.println("CategoryName: "+rs.getString(4));
			System.out.println("---------------------------------");
		}
	}
	public static void main(String[] args) {
		try {
			//readAll();
			getProductById(1);
		}catch(ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}

	}

}
