package com.learning.JDBC;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;



public class InsertOperation {

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
		String query = "insert into product(productName,productPrice,category) values "
	+ "('HP123',6700,'Laptop')";
		Statement stmt = getDbConnection().createStatement();
		int rows =  stmt.executeUpdate(query);
		System.out.println("Number of rows affected : "+ rows);
		
	}
	
	public static void addCustomerProducts(Scanner sc) throws ClassNotFoundException, SQLException {
		System.out.println("Enter the productName");
		String productName = sc.next();
		System.out.println("Enter the productPrice");
		Double productPrice = sc.nextDouble();
		System.out.println("Enter the categoryName");
		String categoryName = sc.next();
		
		String query = "insert into product(productName,productPrice,category) values "
	    + "(?,?,?)";
		
		PreparedStatement ps = getDbConnection().prepareStatement(query);
		ps.setString(1, productName);
		ps.setDouble(2, productPrice);
		ps.setString(3, categoryName);
		
		int row = ps.executeUpdate();
		System.out.println("Number of rows affected " + row);
	}
		
		
		 
		
	
	public static void main(String[] args) {
		try {
			//addProducts()
			Scanner sc = new Scanner(System.in);
			addCustomerProducts(sc);
		}catch(ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}

	}

}
/* 
 1. Register the driver class
 2. create a connection object
 3. Get the statement object //we can execute queries
 4. Execute query
 5. Run the ResultSet
 */
