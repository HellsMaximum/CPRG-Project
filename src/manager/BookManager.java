package manager;

import java.sql.*;
import java.util.Scanner;

import errors.CharacterLimitException;
import errors.ISBNException;

public class BookManager extends Manager {

	// Method to display the menu options for the book manager and handle user input
	@Override
	public void displayMenu() {
		// Create the book table if it does not already exist
		try {
			createTable();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		// Variable to hold the user's menu input and initalize the keyboard scanner created in the method abstract class
		int choice = 0;
		keyboard = new Scanner(System.in);
		// Loop menu options until the user chooses to exit the book manager
		while(choice != 5) {
			System.out.println("1) Add new book.");
			System.out.println("2) Search for a book.");
			System.out.println("3) Update existing book.");
			System.out.println("4) Remove existing book.");
			System.out.println("5) Exit book manager.");
			choice = Integer.parseInt(keyboard.nextLine());
			switch(choice) {
				case 1: 
				try {
					add();
				} catch (Throwable e) {
					e.printStackTrace();
				}
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

	// Method to add a new book to the database
	// Includes error handling for the ISBN, title, genre, and author inputs using the custom exceptions created in the errors package
	@Override
	public void add() throws Throwable {
		int isbn;
		System.out.println("Enter ISBN: ");
		String isbnString = keyboard.nextLine();
		if (isbnString.length() != 13 || !isbnString.matches("\\d+")) {
			throw new ISBNException();
		}
		else {
			isbn = Integer.parseInt(isbnString);
		}
		
		System.out.println("Enter Book Title: ");
		String title = keyboard.nextLine();
		if (title.length() > 75) {
			throw new CharacterLimitException("Book title cannot exceed 75 characters.");
		}
		
		System.out.println("Enter Genre: ");
		String genre = keyboard.nextLine();
		if (genre.length() > 75) {
			throw new CharacterLimitException("Genre cannot exceed 75 characters.");
		}
		
		System.out.println("Enter Author: ");
		String author = keyboard.nextLine();
		if (author.length() > 75) {
			throw new CharacterLimitException("Author name cannot exceed 75 characters.");
		}
		
		String sqlStmt = "INSERT INTO Book (ISBN, Title, Genre, Author) VALUES (?, ?, ?, ?)";
		try {
			PreparedStatement stmt = conn.prepareStatement(sqlStmt);
			stmt.setInt(1, isbn);
			stmt.setString(2, title);
			stmt.setString(3, genre);
			stmt.setString(4, author);
			// Execute the insert operation
	        int row = stmt.executeUpdate();

	        // Show how many rows were inserted
	        System.out.println(row + " record inserted.");
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
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
                		 "title VARCHAR(75) NOT NULL, " +
                		 "genre VARCHAR(75) NOT NULL, " +
                		 "author VARCHAR(75) NOT NULL)";
		System.out.println(sqlStmt);

		PreparedStatement stmt = conn.prepareStatement(sqlStmt);
		stmt.execute();
		
	}

}
