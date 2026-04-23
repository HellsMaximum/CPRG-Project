package databaseController;

import java.sql.*;

public class DatabaseController {
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

	/**
	 * Method to create the tables in the database if they don't already exist
	 * the tables are as follows: BOOK, MEMBER, CHECKOUT
	 */
	public void createTables() throws SQLException{
		// stores the SQL statements to create the tables in an array
		String[] createTableStatements = {
				"CREATE TABLE IF NOT EXISTS BOOK ("
						+ "ISBN BIGINT PRIMARY KEY, "
						+ "TITLE VARCHAR(75) NOT NULL, "
						+ "GENRE VARCHAR(75) NOT NULL, "
						+ "AUTHOR VARCHAR(75) NOT NULL"
						+ ")",
				"CREATE TABLE IF NOT EXISTS MEMBER ("
						+ "MEMBERID BIGINT PRIMARY KEY AUTO_INCREMENT, "
						+ "FIRSTNAME VARCHAR(50) NOT NULL, "
						+ "LASTNAME VARCHAR(50) NOT NULL"
						+ ")",
				"CREATE TABLE IF NOT EXISTS CHECKOUT ("
						+ "CHECKOUTID INT PRIMARY KEY AUTO_INCREMENT, "
						+ "CHECKOUTDATE DATE NOT NULL, "
						+ "RETURNDATE DATE NOT NULL, "
						+ "MEMBERID BIGINT NOT NULL, "
						+ "BOOKISBN BIGINT NOT NULL, "
						+ "CONSTRAINT fk_checkout_member FOREIGN KEY (MEMBERID) REFERENCES MEMBER(MEMBERID), "
						+ "CONSTRAINT fk_checkout_book FOREIGN KEY (BOOKISBN) REFERENCES BOOK(ISBN)"
						+ ")"
		};

		// loops through the array and tries to create a new table for each SQL statement
		for (String createTableStatement : createTableStatements) {
			stmt.execute(createTableStatement);
		}
	}

	/**
	 * Method to drop the tables in the database if they exist
	 * the tables are as follows: CHECKOUT, MEMBER, BOOK
	 */
	public void dropTables() throws SQLException
	{
		// stores the SQL statements to drop the tables in an array
		String[] dropTableStatements = {
				"DROP TABLE IF EXISTS CHECKOUT",
				"DROP TABLE IF EXISTS MEMBER",
				"DROP TABLE IF EXISTS BOOK"
		};

		// loops through the array and tries to drop each table for each SQL statement
		for (String dropTableStatement : dropTableStatements) {
			stmt.execute(dropTableStatement);
		}
		
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
