package databaseController;

import java.sql.*;

public class DatabaseControler {
	// Private Variables that handle connecting the database
	private static final String SERVER = "localhost";
	private static final int PORT = 3306;
	private static final String DATABASE = "cprg211";
	private static final String USERNAME = "root";
	private static final String PASSWORD = "password";
	
	private Connection conn;
	private Statement stmt;

    // Method to connect to the database using the connection parameters
	// Adds data to the connection and statement variables
	public void connect() throws SQLException {
		final String DB_URL = String.format("jdbc:mariadb://%s:%d/%s?user=%s&password=%s", SERVER, PORT, DATABASE, USERNAME, PASSWORD);
		conn = DriverManager.getConnection(DB_URL);
		System.out.println("Connection to DB established.");
		stmt= conn.createStatement();	
	}
	
	// Method to disconnect from the database and close the keyboard scanner
	public void disconnect() {
		try {
			if (stmt != null) {
				stmt.close();
			}
			if (conn != null) {
				conn.close();
			}
			System.out.println("Database connection closed!");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

    public Connection getConn() {
        return conn;
    }

    public Statement getStmt() {
        return stmt;
    }
}
