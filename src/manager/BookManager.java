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
					e.getMessage();
				}
					break;
				case 2:
					search();
					break;
				case 3:
				try {
					update();
				} catch (Throwable e) {
					e.getMessage();
				}
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
	public void update(){
		// get the user to input the ISBN of the book they want to update
		System.out.println("Enter ISBN of the book to update: ");
		int isbnToUpdate = Integer.parseInt(keyboard.nextLine());

		// Select the book with the matching ISBN
		String sqlStmt = "SELECT * FROM BOOK WHERE ISBN = ?";
		
		try {
			PreparedStatement stmt = conn.prepareStatement(sqlStmt);
			stmt.setInt(1, isbnToUpdate);
			ResultSet resultSet = stmt.executeQuery();
			boolean found = false;
			// display the books current information if it is found
			while (resultSet.next()) {
				found = true;
				System.out.println("Current ISBN: " + resultSet.getInt("ISBN"));
				System.out.println("Current Title: " + resultSet.getString("Title"));
				System.out.println("Current Genre: " + resultSet.getString("Genre"));
				System.out.println("Current Author: " + resultSet.getString("Author"));
			}
			// if the book is not found, break out of the sql search and return to the menu
			if(!found) {
				throw new ISBNException("Book with ISBN: " + isbnToUpdate + " was not found.");
			} 
			else {
				// Select the element of the book to edit
				System.out.println("Select element to update: \n1) Title \n2) Genre \n3) Author");
				int elementToUpdate = 0;
				while (elementToUpdate < 1 || elementToUpdate > 3) {
					elementToUpdate = Integer.parseInt(keyboard.nextLine());
					if (elementToUpdate < 1 || elementToUpdate > 3) {
						System.out.println("Invalid input. Please enter a number between 1 and 3.");
					}
				}
				// Switch statement to update selected element
				// Throws custom error if the new input is invalid
				switch(elementToUpdate){
					case 1:
						System.out.println("Enter new title: ");
						String newTitle = keyboard.nextLine();
						if (newTitle.length() > 75) {
							throw new CharacterLimitException("Book title cannot exceed 75 characters.");
						}
						else {
							PreparedStatement stmt = conn.prepareStatement(sqlStmt);
							stmt.setString(1, newTitle);
							stmt.setInt(2, isbnToUpdate);
							int row = stmt.executeUpdate();
							System.out.println(row + " record(s) updated.");
						}
						break;
					case 2:
						System.out.println("Enter new genre: ");
						String newGenre = keyboard.nextLine();
						if (newGenre.length() > 75) {
							throw new CharacterLimitException("Genre cannot exceed 75 characters.");
						}
						else {
							PreparedStatement stmt = conn.prepareStatement(sqlStmt);
							stmt.setString(1, newGenre);
							stmt.setInt(2, isbnToUpdate);
							int row = stmt.executeUpdate();
							System.out.println(row + " record(s) updated.");
						}
						break;
					case 3:
						System.out.println("Enter new author: ");
						String newAuthor = keyboard.nextLine();
						if (newAuthor.length() > 75) {
							throw new CharacterLimitException("Author name cannot exceed 75 characters.");
						}
						else {
							PreparedStatement stmt = conn.prepareStatement(sqlStmt);
							stmt.setString(1, newAuthor);
							stmt.setInt(2, isbnToUpdate);
							int row = stmt.executeUpdate();
							System.out.println(row + " record(s) updated.");
						}
						break;
					default:
						throw new IllegalArgumentException("Invalid input.");
				}
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} catch (ISBNException | CharacterLimitException | IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}
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
