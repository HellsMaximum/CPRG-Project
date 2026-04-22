package manager;

import java.sql.*;
import java.util.Scanner;
public abstract class Manager {
	// Private Variables that handle connecting the database
	private static final String SERVER = "localhost";
	private static final int PORT = 3306;
	private static final String DATABASE = "cprg211";
	private static final String USERNAME = "root";
	private static final String PASSWORD = "password";
	
	// Protected variables that can be used by the other manager classes
	protected Connection conn;
	protected Statement stmt;
	
	protected Scanner keyboard;
	
	// Constructor to initialize the manager, connect to the database, and display the menu
	public Manager() {
		try {
			connect();
			displayMenu();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}
	
	// Method to connect to the database using the connection parameters
	// Adds data to the connection and statement variables
	protected void connect() throws SQLException {
		final String DB_URL = String.format("jdbc:mariadb://%s:%d/%s?user=%s&password=%s", SERVER, PORT, DATABASE, USERNAME, PASSWORD);
		conn = DriverManager.getConnection(DB_URL);
		System.out.println("Connection to DB established.");
		stmt= conn.createStatement();	
	}
	
	// Method to disconnect from the database and close the keyboard scanner
	protected void disconnect() {
		try {
			conn.close();
			System.out.println("Connection closed!");
			keyboard.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	// Abstract methods to be implemented by the other Manager classes
	public abstract void displayMenu();
	public abstract void createTable() throws SQLException;
	public abstract void add() throws Throwable;
	public abstract void remove();
	public abstract void update();
	public abstract void search();
	public abstract void viewAll();
}
