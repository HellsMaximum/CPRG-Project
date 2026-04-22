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
				System.out.println("ISBN: " + resultSet.getInt("ISBN"));
				System.out.println("Title: " + resultSet.getString("Title"));
				System.out.println("Genre: " + resultSet.getString("Genre"));
				System.out.println("Author: " + resultSet.getString("Author"));
				System.out.println();
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

}
