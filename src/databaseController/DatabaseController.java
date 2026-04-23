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
	public void createTables() throws SQLException{
		String SQLStatement = "CREATE TABLE book (\r\n"
				+ "    isbn VARCHAR(13) PRIMARY KEY,\r\n"
				+ "    name VARCHAR(30),\r\n"
				+ "    author VARCHAR(30),\r\n"
				+ "    genre VARCHAR(30),\r\n"
				+ "    checked_out CHAR(1)\r\n"
				+ ");\r\n"
				+ "\r\n"
				+ "CREATE TABLE member (\r\n"
				+ "    id NUMBER(30) PRIMARY KEY,\r\n"
				+ "    name VARCHAR(30),\r\n"
				+ "    total_overdue_fees VARCHAR(30),\r\n"
				+ "    can_checkout CHAR(1)\r\n"
				+ ");\r\n"
				+ "CREATE TABLE checkout (\r\n"
				+ "    id VARCHAR2(30) PRIMARY KEY,\r\n"
				+ "    date_of_checkout DATE,\r\n"
				+ "    date_to_return DATE,\r\n"
				+ "    overdue_fees VARCHAR(30),\r\n"
				+ "    returned_status CHAR(1),\r\n"
				+ "    checked_out_isbn VARCHAR(13),\r\n"
				+ "    checked_out_id NUMBER(30),\r\n"
				+ "    CONSTRAINT fk_checkout_book FOREIGN KEY (checked_out_isbn) REFERENCES book(isbn),\r\n"
				+ "    CONSTRAINT fk_checkout_member FOREIGN KEY (checked_out_id) REFERENCES member(id)\r\n"
				+ ");\r\n"
				+ "CREATE TABLE book_checkout (\r\n"
				+ "    isbn VARCHAR(13),\r\n"
				+ "    checkout_id VARCHAR(30),\r\n"
				+ "    CONSTRAINT pk_book_checkout PRIMARY KEY (isbn, checkout_id),\r\n"
				+ "    CONSTRAINT fk_bc_book FOREIGN KEY (isbn) REFERENCES book(isbn),\r\n"
				+ "    CONSTRAINT fk_bc_checkout FOREIGN KEY (checkout_id) REFERENCES checkout(id)\r\n"
				+ ");\r\n"
				+ "\r\n"
				+ "CREATE TABLE member_checkout (\r\n"
				+ "    member_id NUMBER(30),\r\n"
				+ "    checkout_id VARCHAR(30),\r\n"
				+ "    CONSTRAINT pk_member_checkout PRIMARY KEY (member_id, checkout_id),\r\n"
				+ "    CONSTRAINT fk_mc_member FOREIGN KEY (member_id) REFERENCES member(id),\r\n"
				+ "    CONSTRAINT fk_mc_checkout FOREIGN KEY (checkout_id) REFERENCES checkout(id)\r\n"
				+ ");";
		stmt.execute(SQLStatement);
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
