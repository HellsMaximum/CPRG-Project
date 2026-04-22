package manager;

import java.sql.*;
import java.util.Scanner;

import errors.CharacterLimitException;
import errors.ISBNException;
import errors.NotFoundException;

public class BookManager extends Manager {

	public BookManager(Connection conn, Statement stmt) {
		super(conn, stmt);
		// TODO Auto-generated constructor stub
	}

	// Method to display the menu options for the book manager and handle user input
	@Override
	public void displayMenu() {
		// Variable to hold the user's menu input and initialize the keyboard scanner created in the method abstract class
		int choice = 0;
		keyboard = new Scanner(System.in);
		// Loop menu options until the user chooses to exit the book manager
		while(choice != 6) {
			System.out.println("1) Add new book.");
			System.out.println("2) Search for a book.");
			System.out.println("3) Update existing book.");
			System.out.println("4) Remove existing book.");
			System.out.println("5) View all books.");
			System.out.println("6) Exit book manager.");
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
					viewAll();
					break;
				case 6:
					keyboard.close();
					System.out.println("Returning to main menu.");
					break;
				default:
					System.out.println("Invalid input. Please Enter 1 to 6.");	
			}
		}
	}

	// Method to add a new book to the database
	// Includes error handling for the ISBN, title, genre, and author inputs using the custom exceptions created in the errors package
	@Override
	public void add() {
		// get the isbn the user wants to add
		long isbn = 0;
		try {
		System.out.println("Enter ISBN: ");
		String isbnString = keyboard.nextLine();
		// Check if the ISBN is valid
		if (isbnString.length() != 13 || !isbnString.matches("\\d+")) {
			throw new ISBNException();
		}
		isbn = Long.parseLong(isbnString);

		// Select the book with the matching ISBN
		String sqlStmt = "SELECT * FROM BOOK WHERE ISBN = ?";

			// Check if the ISBN already exists in the database
			PreparedStatement stmt = conn.prepareStatement(sqlStmt);
			stmt.setLong(1, isbn);
			ResultSet resultSet = stmt.executeQuery();
			boolean found = false;
			// Check if the ISBN is valid
			if (resultSet.next()) {
				found = true;
			}
			// if the book is found, error out and return to menu
			if(found) {
				throw new ISBNException("Book with ISBN: " + isbn + " already exists.");
			} 
			else{
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
				
				sqlStmt = "INSERT INTO Book (ISBN, Title, Genre, Author) VALUES (?, ?, ?, ?)";
				
				stmt = conn.prepareStatement(sqlStmt);
				stmt.setLong(1, isbn);
				stmt.setString(2, title);
				stmt.setString(3, genre);
				stmt.setString(4, author);
				// Execute the insert operation
				int row = stmt.executeUpdate();
	
				// Show how many rows were inserted
				System.out.println(row + " record inserted.");	
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} catch (ISBNException | CharacterLimitException e) {
			System.out.println(e.getMessage());
		}
	}

	// method to remove a book from the database based on the ISBN input from the user
	@Override
	public void remove() {
		System.out.println("Enter ISBN of book to remove: ");
		long isbn = Long.parseLong(keyboard.nextLine());

		String sqlStmt = "SELECT * FROM BOOK WHERE ISBN = ?";
		// Verify that the book exists before attempting to delete it
		try {
			PreparedStatement stmt = conn.prepareStatement(sqlStmt);
			stmt.setLong(1, isbn);
			ResultSet resultSet = stmt.executeQuery();
			boolean found = false;
			// Check if the ISBN is valid
			if (resultSet.next()) {
				found = true;
			}
			// if the book is not found error out and return to menu
			if (!found){
				throw new NotFoundException("Book with ISBN: " + isbn + " was not found.");
			}
			// if the book is found, delete it from the database and return to the menu
			else {
				sqlStmt = "DELETE FROM BOOK WHERE ISBN = ?";
				PreparedStatement deleteStmt = conn.prepareStatement(sqlStmt);
				deleteStmt.setLong(1, isbn);
				int rowsDeleted = deleteStmt.executeUpdate();
				System.out.println(rowsDeleted + " record(s) deleted.");
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} catch (NotFoundException e) {
			System.out.println(e.getMessage());
		}
		
	}

	@Override
	public void update(){
		// get the user to input the ISBN of the book they want to update
		System.out.println("Enter ISBN of the book to update: ");
		long isbnToUpdate = Long.parseLong(keyboard.nextLine());

		// Select the book with the matching ISBN
		String sqlStmt = "SELECT * FROM BOOK WHERE ISBN = ?";
		
		try {
			PreparedStatement stmt = conn.prepareStatement(sqlStmt);
			stmt.setLong(1, isbnToUpdate);
			ResultSet resultSet = stmt.executeQuery();
			boolean found = false;
			// display the books current information if it is found
			while (resultSet.next()) {
				found = true;
				System.out.println(String.format("ISBN: %d Title: %s Genre: %s Author: %s", resultSet.getLong("ISBN"), resultSet.getString("Title"), resultSet.getString("Genre"), resultSet.getString("Author")));
			}
			// if the book is not found, break out of the sql search and return to the menu
			if(!found) {
				throw new NotFoundException("Book with ISBN: " + isbnToUpdate + " was not found.");
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
							sqlStmt = "UPDATE BOOK SET TITLE = ? WHERE ISBN = ?";
							stmt = conn.prepareStatement(sqlStmt);
							stmt.setString(1, newTitle);
							stmt.setLong(2, isbnToUpdate);
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
							sqlStmt = "UPDATE BOOK SET GENRE = ? WHERE ISBN = ?";
							stmt = conn.prepareStatement(sqlStmt);
							stmt.setString(1, newGenre);
							stmt.setLong(2, isbnToUpdate);
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
							sqlStmt = "UPDATE BOOK SET AUTHOR = ? WHERE ISBN = ?";
							stmt = conn.prepareStatement(sqlStmt);
							stmt.setString(1, newAuthor);
							stmt.setLong(2, isbnToUpdate);
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
		} catch (NotFoundException | CharacterLimitException | IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}
	}

	@Override
	public void search() {
		System.out.println("Enter how you want to search for a book: \n1) ISBN \n2) Title \n3) Genre \n4) Author");
		int searchTypeInt = 0;
		while (searchTypeInt < 1 || searchTypeInt > 4) {
			String SearchType = keyboard.nextLine();
			if (!SearchType.matches("[1-4]")) {
				System.out.println("Invalid input. Please enter a number between 1 and 4.");
				continue;
			} else {
				searchTypeInt = Integer.parseInt(SearchType);
			}

		}
		switch(searchTypeInt){
			case 1:
				System.out.println("Enter ISBN: ");
				long isbn = Long.parseLong(keyboard.nextLine());
				while (String.valueOf(isbn).length() != 13 || !String.valueOf(isbn).matches("\\d+")) {
					System.out.println("Invalid input. ISBN must be 13 characters long and only contain numeric characters. \nPlease enter a valid ISBN: ");
					isbn = Long.parseLong(keyboard.nextLine());
				}
				// Select the book with the matching ISBN
				String sqlStmtISBN = "SELECT * FROM BOOK WHERE ISBN = ?";
				try {
					PreparedStatement stmt = conn.prepareStatement(sqlStmtISBN);
					stmt.setLong(1, isbn);
					ResultSet resultSet = stmt.executeQuery();
					boolean found = false;
					// Check if the ISBN is valid
					while (resultSet.next()) {
						found = true;
						System.out.println(String.format("ISBN: %d Title: %s Genre: %s Author: %s", resultSet.getLong("ISBN"), resultSet.getString("Title"), resultSet.getString("Genre"), resultSet.getString("Author")));
					}
					// if the book is not found, break out of the sql search and return to the menu
					if (!found){
						throw new NotFoundException("Book with ISBN: " + isbn + " was not found.");
					} 
				} catch (SQLException e) {
					System.out.println(e.getMessage());
				} catch (NotFoundException e) {
					System.out.println(e.getMessage());
				}
				break;
			
			case 2:
				System.out.println("Enter Title: ");
				String title = keyboard.nextLine();
				while(title.length() > 75) {
					System.out.println("Invalid input. Title cannot exceed 75 characters. \nPlease enter a valid title: ");
					title = keyboard.nextLine();
				}
				// Select the book with the matching title
				String sqlStmtTitle = "SELECT * FROM BOOK WHERE TITLE = ?";
				try {
					PreparedStatement stmt = conn.prepareStatement(sqlStmtTitle);
					stmt.setString(1, title);
					ResultSet resultSet = stmt.executeQuery();
					boolean found = false;
					while (resultSet.next()) {
						found = true;
						System.out.println(String.format("ISBN: %d Title: %s Genre: %s Author: %s", resultSet.getLong("ISBN"), resultSet.getString("Title"), resultSet.getString("Genre"), resultSet.getString("Author")));
					}
					if (!found){
						throw new NotFoundException("Book with Title: " + title + " was not found.");
					} 
				} catch (SQLException e) {
					System.out.println(e.getMessage());
				} catch (NotFoundException e) {
					System.out.println(e.getMessage());
				}
				break;

			case 3:
				System.out.println("Enter Genre: ");
				String genre = keyboard.nextLine();
				while (genre.length() > 75) {
					System.out.println("Invalid input. Genre cannot exceed 75 characters. \nPlease enter a valid genre: ");
					genre = keyboard.nextLine();
				}

				// Select the book with the matching genre
				String sqlStmtGenre = "SELECT * FROM BOOK WHERE GENRE = ?";
				try {
					PreparedStatement stmt = conn.prepareStatement(sqlStmtGenre);
					stmt.setString(1, genre);
					ResultSet resultSet = stmt.executeQuery();
					boolean found = false;
					while (resultSet.next()) {
						found = true;
						System.out.println(String.format("ISBN: %d Title: %s Genre: %s Author: %s", resultSet.getLong("ISBN"), resultSet.getString("Title"), resultSet.getString("Genre"), resultSet.getString("Author")));
					}
					if (!found){
						throw new NotFoundException("Book with Genre: " + genre + " was not found.");
					} 
				} catch (SQLException e) {
					System.out.println(e.getMessage());
				} catch (NotFoundException e) {
					System.out.println(e.getMessage());
				}
				break;
			case 4:
				System.out.println("Enter Author: ");
				String author = keyboard.nextLine();
				while (author.length() > 75) {
					System.out.println("Invalid input. Author cannot exceed 75 characters. \nPlease enter a valid author: ");
					author = keyboard.nextLine();
				}

				// Select the book with the matching author
				String sqlStmtAuthor = "SELECT * FROM BOOK WHERE AUTHOR = ?";
				try {
					PreparedStatement stmt = conn.prepareStatement(sqlStmtAuthor);
					stmt.setString(1, author);
					ResultSet resultSet = stmt.executeQuery();
					boolean found = false;
					while (resultSet.next()) {
						found = true;
						System.out.println(String.format("ISBN: %d Title: %s Genre: %s Author: %s", resultSet.getLong("ISBN"), resultSet.getString("Title"), resultSet.getString("Genre"), resultSet.getString("Author")));
					}
					if (!found){
						throw new NotFoundException("Book with Author: " + author + " was not found.");
					} 
				} catch (SQLException e) {
					System.out.println(e.getMessage());
				} catch (NotFoundException e) {
					System.out.println(e.getMessage());
				}
				break;
			default:
				throw new IllegalArgumentException("Invalid input.");
		}
		
	}
	
	@Override
	public void viewAll() {
		String sqlStmt = "SELECT * FROM BOOK";
		try {
			PreparedStatement stmt = conn.prepareStatement(sqlStmt);
			ResultSet resultSet = stmt.executeQuery();
			while (resultSet.next()) {
				System.out.println(String.format("ISBN: %d Title: %s Genre: %s Author: %s", resultSet.getLong("ISBN"), resultSet.getString("Title"), resultSet.getString("Genre"), resultSet.getString("Author")));
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
}