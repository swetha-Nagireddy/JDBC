package com.learning.JDBC;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Project {
	
	private static final String url = "jdbc:mysql://localhost:3306/flightbooking";
	
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

	public class UserService{
		
		
		public static void registerUser(String name,String password) throws ClassNotFoundException, SQLException {
			String query = "Insert into users(userName,userpassword) values(?,?)";
			PreparedStatement stmt = getDbConnection().prepareStatement(query);
			
			stmt.setString(1, name);
			stmt.setString(2, password);
			int i = stmt.executeUpdate();
			System.out.println("user details updated successfully");
		}
		
		
		public static int loginUser(String name, String password) throws ClassNotFoundException, SQLException {
			String query = " select userid from users where username=? and userpassword=?";
			PreparedStatement stmt= getDbConnection().prepareStatement(query);
			
			stmt.setString(1, name);
			stmt.setString(2, password);
			ResultSet rs= stmt.executeQuery();
			
			if(rs.next()) {
				int userId = rs.getInt("userId");
				return userId;
			}else {
				return -1;
			}
			
	}
	
	}	
		public class FlightService{
			public static void displayAvailableSeats(String source, String destination) throws ClassNotFoundException, SQLException {
				String query = "select * from flights where source=? and destination=? and seats >0";
				PreparedStatement stmt = getDbConnection().prepareStatement(query);
				stmt.setString(1, source);
				stmt.setString(2, destination);
				
				ResultSet rs =stmt.executeQuery();
				
				while(rs.next()) {
					System.out.println("FlightId: "+rs.getInt("flightId"));
					System.out.println("FlightName: "+rs.getString("flightName"));
					System.out.println("seats_available: "+rs.getInt("seats"));
					System.out.println("..................................");
				}
			}
		}
		
		
	
	public class BookingService{
		public static boolean bookFlight(int userId,int flightId) throws ClassNotFoundException, SQLException {
			
		    String q="select seats from flights where flightId=?";
			String query1 = "Update flights SET seats = seats-1 where flightId=?";
			String query = "insert into booking(userId,flightId) values(?,?)";
			
			PreparedStatement stmt = getDbConnection().prepareStatement(q);
			stmt.setInt(1, flightId);
			ResultSet rs= stmt.executeQuery();
			
			if(rs.next()) {
				int seats_available = rs.getInt("seats");
				
				if(seats_available>0) {
					PreparedStatement stmt1= getDbConnection().prepareStatement(query);
					stmt1.setInt(1, userId);
					stmt1.setInt(2, flightId);
					stmt1.executeUpdate();
					
					
					PreparedStatement stmt2= getDbConnection().prepareStatement(query1);
					stmt2.setInt(1, flightId);
					stmt2.executeUpdate();
				}
			}
			
			return true;
		}
	}
	
	
	public class BoardingPass{
		public static void getBoardingPass( int userId,int flightId) throws ClassNotFoundException, SQLException {
			String que="SELECT u.userId, u.userName, f.flightId, f.flightName "
					+ "FROM users u "
					+ "JOIN booking b on u.userId = b.userId "
					+ "JOIN flights f ON b.flightId = f.flightId "
					+ "wHERE u.userId = ? AND f.flightId = ?";
			PreparedStatement stmt = getDbConnection().prepareStatement(que);
			
			stmt.setInt(1, userId);
//			stmt.setString(2,flightName );
			stmt.setInt(2, flightId);
			
			ResultSet rs= stmt.executeQuery();
			
			if(rs.next()) {
				String user = rs.getString("userName");
				int user_id=rs.getInt("userId");
				String flight = rs.getString("flightName");
				int flightID = rs.getInt("flightId");
				
				
				System.out.println("\nBoarding pass: ");
				System.out.println("userName: "+user);
				System.out.println("FlightName: "+flight);
				System.out.println("FlightId: "+flightID);
				
				System.out.println("Enjoy your flight......");
				
			}
			
		}
	}
	
	public static void main(String[] args) throws ClassNotFoundException, SQLException {

		 int logginId=-1;
		 
		 while(logginId == -1) {
			 System.out.println("welcome to flight Booking sytsem");
			 System.out.println("Do you have an accaount? yes/no");
			 
			 Scanner sc = new Scanner(System.in);
			 String hasaccount = sc.next();
			 
			 if(hasaccount.equalsIgnoreCase("no")) {
				 System.out.println("Register new user");
				 System.out.println("Enter your name");
				 Scanner sc1 = new Scanner(System.in);
				 String name = sc1.next();
				 
				 System.out.println("Enter password");
				 Scanner sc2 = new Scanner(System.in);
				 String password = sc2.next();
				 
				 UserService.registerUser(name,password);
			 }
			 
			 System.out.println("Please login");
			 System.out.println("enter name");
			 Scanner sc1= new Scanner(System.in);
			 String name = sc1.next();
			 
			 System.out.println("Enter password");
			 Scanner sc2 = new Scanner(System.in);
			 String password = sc2.next();
			 
			 logginId = UserService.loginUser(name,password);
			 if(logginId == -1) {
				 System.out.println("Login failed. incorrect name or password");
			 }
		 }
		 System.out.println("Login Successfull");
		 
		 
		 //source and destination
		 
		 System.out.println("enter source");
		 Scanner sc3 = new Scanner(System.in);
		 String source = sc3.next();
		 
		 System.out.println("Enter destination");
		 Scanner sc4 = new Scanner(System.in);
		 String destination = sc4.next();
		 
		 System.out.println("Available flights from"+source+" "+ destination+" is : ");
		 FlightService.displayAvailableSeats(source,destination);
		 
		 
		 System.out.println("Enter userId");
		 Scanner sc6 = new Scanner(System.in);
		 int userId=sc6.nextInt();
		 
		 System.out.println("Enter flightId to book Ticket");
		 Scanner sc5 = new Scanner(System.in);
		 int flightId= sc5.nextInt();
		 
		 
		 
		 boolean bookingsucess= BookingService.bookFlight(userId,flightId);
		 
		 if(bookingsucess) {
			 System.out.println("your flight ticket has been booked.....Happy Journey");
		 }else {
			 System.out.println("your flight booking has failed....sorry for Inconvience");
		 }
	
		
         BoardingPass.getBoardingPass(userId,flightId);

	}
	
}

