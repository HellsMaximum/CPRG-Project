package manager;

import java.sql.*;
import java.util.Scanner;

public class BookManager extends Manager {

	@Override
	public void displayMenu() {
		try {
			createTable();
		} catch (SQLException e) {
			e.getMessage();
		}
		int choice = 0;
		keyboard = new Scanner(System.in);
		while(choice != 5) {
			System.out.println("1) Add new book.");
			System.out.println("2) Search for a book.");
			System.out.println("3) Update existing book.");
			System.out.println("4) Remove existing book.");
			System.out.println("5) Exit book manager.");
			choice = Integer.parseInt(keyboard.nextLine());
			switch(choice) {
				case 1: 
					add();
					break;
				case 2:
					search();
					break;
				case 3:
					update();
					break;
				case 4:
					remove();
					break;
				case 5:
					disconnect();
					break;
				default:
					System.out.println("Invalid input. Please Enter 1 to 5.");
					
			}
		}
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
	public void createTable() throws SQLException {
		String sqlStmt = "CREATE TABLE IF NOT EXISTS Book (" +
                		 "isbn INT(13) PRIMARY KEY, " +
                		 "name VARCHAR(75) NOT NULL, " +
                		 "genre VARCHAR(75) NOT NULL, " +
                		 "author VARCHAR(75) NOT NULL)";
		System.out.println(sqlStmt);

		PreparedStatement stmt = conn.prepareStatement(sqlStmt);
		stmt.execute();
		
	}

}
