package com.learning.JDBC;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UpdateOperation {
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
	
	public static void updateProduct(int pId, double pPrice) throws ClassNotFoundException, SQLException {
		String query = " update product set productPrice =? where productId=?";
		PreparedStatement ps = getDbConnection().prepareStatement(query);
		ps.setDouble(1, pPrice);
		ps.setInt(2,pId);
		int i = ps.executeUpdate();
		System.out.println("product updated successfully");
	}
	
	
	public static void deleteProduct(int pId) throws ClassNotFoundException, SQLException {
		String query = "delete from product where productId =?";
		PreparedStatement ps = getDbConnection().prepareStatement(query);
		ps.setInt(1, pId);
		int i = ps.executeUpdate();
		System.out.println("Product deleted successfully");
	}
	public static void main(String[] args) {
		try {
			//updateProduct(1,56000);
			deleteProduct(2);
		}catch(ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}

	}

}
