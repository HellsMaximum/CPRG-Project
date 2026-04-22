package manager;

import java.sql.*;
import java.util.Scanner;

public class CheckoutManager extends Manager {

	public CheckoutManager(Connection conn, Statement stmt) {
		super(conn, stmt);
	}

	@Override
	public void displayMenu() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void add() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void remove() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void search() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void viewAll() {
		String sqlStmt = "SELECT * FROM Checkout";
		try {
			PreparedStatement stmt = conn.prepareStatement(sqlStmt);
			ResultSet resultSet = stmt.executeQuery();
			while (resultSet.next()) {
				System.out.println("Checkout ID: " + resultSet.getInt("CheckoutID"));
				System.out.println("Checkout Date: " + resultSet.getString("CheckoutDate"));
				System.out.println("Return Date: " + resultSet.getString("ReturnDate"));
				System.out.println("Member ID: " + resultSet.getString("MemberID"));
				System.out.println("Book ISBN: " + resultSet.getString("BookISBN"));
				System.out.println("Book Returned: " + resultSet.getString("BookReturned"));
				System.out.println();
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

}
