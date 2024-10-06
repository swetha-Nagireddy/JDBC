package com.learning.JDBC;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class BatchProcessing {

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
	
	public static void addProducts() throws ClassNotFoundException, SQLException {
		String q1 ="insert into Product(productName,ProductPrice,category)  values ('samsung',6800,'mobile')";
		String q2 ="insert into Product(productName,ProductPrice,category) values ('samsung',6800,'mobile')";
		String q3 ="insert into Product(productName,ProductPrice,category) values ('samsung',6800,'mobile')";
		String q4 ="insert into Product(productName,ProductPrice,category) values ('samsung',6800,'mobile')";
		
		Statement stmt = getDbConnection().createStatement();
		stmt.addBatch(q1);
		stmt.addBatch(q2);
		stmt.addBatch(q3);
		stmt.addBatch(q4);
		
		int[] executeBatch = stmt.executeBatch();
		System.out.println("Number of rows affected : "+executeBatch.length);
	}
	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		addProducts();

	}

}
