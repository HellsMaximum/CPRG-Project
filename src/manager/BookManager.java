package manager;

import java.sql.*;
import java.util.Scanner;

import errors.CharacterLimitException;
import errors.ISBNException;
import errors.NotFoundException;

public class BookManager extends Manager {

	/**
	 * Constructor to initialize the book manager by setting the super class variables for the database connection
	 */
	public BookManager(Connection conn, Statement stmt) {
		super(conn, stmt);
	}

	/** 
	 * Method to display the menu options for the book manager and handle user input
	 */
	@Override
	public void displayMenu() {
		// Variable to hold the user's menu input and initialize the keyboard scanner created in the method abstract class
		int choice = 0;
		keyboard = new Scanner(System.in);
		// Loop menu options until the user chooses to exit the book manager
		while(choice != 6) {
			System.out.println("Book Manager Menu:");
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
					System.out.println("Returning to main menu.");
					break;
				default:
					System.out.println("Invalid input. Please Enter 1 to 6.");	
			}
		}
	}

	/** 
	 * Method to add a new book to the database based on user input
	 * Includes error handling for the ISBN, title, genre, and author inputs 
	 * does the error handling using the custom exceptions created in the errors package
	 */
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

	/** 
	 * Method to remove a book from the database based on the ISBN input from the user
	 * if the book is not found, error out and return to the menu
	 * if the book is found, delete it from the database and return to the menu
	 * Includes error handling for the ISBN input using the custom exceptions created in the errors package 
	 * if the book is being borrowed by a member and has not been returned, error out and return to the menu
	 */ 
	@Override
	public void remove() {
		System.out.println("Enter ISBN of book to remove: ");
		long isbn = Long.parseLong(keyboard.nextLine());
		
		// validate the ISBN input
		while (String.valueOf(isbn).length() != 13 || !String.valueOf(isbn).matches("\\d+")) {
			System.out.println("Invalid input. ISBN must be 13 characters long and only contain numeric characters. \nPlease enter a valid ISBN: ");
			isbn = Long.parseLong(keyboard.nextLine());
		}

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
			// if the book is found, check to see if it is currently checked out
			else {
				sqlStmt = "SELECT * FROM CHECKOUT WHERE BookISBN = ?";
				stmt = conn.prepareStatement(sqlStmt);
				stmt.setLong(1, isbn);
				resultSet = stmt.executeQuery();
				if (resultSet.next()) {
					throw new ISBNException("Book with ISBN: " + isbn + " cannot be deleted because it is currently checked out.");
				} 
				else {
					sqlStmt = "DELETE FROM BOOK WHERE ISBN = ?";
					stmt = conn.prepareStatement(sqlStmt);
					stmt.setLong(1, isbn);
					int row = stmt.executeUpdate();
					System.out.println(row + " record(s) deleted.");
				}
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} catch (NotFoundException e) {
			System.out.println(e.getMessage());
		} catch (ISBNException e) {
			System.out.println(e.getMessage());
		}
		
	}

	/**
	 * Method to update an existing book in the database based on the ISBN of that book
	 * user inputs the ISBN of the book they want to update the element of
	 * if the book is not found, error out and return to the menu
	 * if the book is found display the current information for that book
	 * user selects the element of the book they want to update (title, genre, author)
	 * user inputs the new data for the selected element
	 * if the new data is in an invalid format, error out and return to the menu
	 * if the new data is valid, update the book in the database with the new data and return to the menu
	 * Checks to see if the data being updated is currently checked out by a member
	 * if the book is currently checked out, error out and return to the menu without updating the book
	 */
	@Override
	public void update(){
		// get the user to input the ISBN of the book they want to update
		System.out.println("Enter ISBN of the book to update: ");
		long isbnToUpdate = Long.parseLong(keyboard.nextLine());
		
		// validate the ISBN input
		while (String.valueOf(isbnToUpdate).length() != 13 || !String.valueOf(isbnToUpdate).matches("\\d+")) {
			System.out.println("Invalid input. ISBN must be 13 characters long and only contain numeric characters. \nPlease enter a valid ISBN: ");
			isbnToUpdate = Long.parseLong(keyboard.nextLine());
		}

		// Select the book with the matching ISBN
		String sqlStmt = "SELECT * FROM BOOK WHERE ISBN = ?";
		
		try {
			PreparedStatement stmt = conn.prepareStatement(sqlStmt);
			stmt.setLong(1, isbnToUpdate);
			ResultSet resultSet = stmt.executeQuery();
			boolean found = false;
			long isbn = 0;
			String title = "";
			String genre = "";
			String author = "";
			// check to see if book is found and store the book data
			while (resultSet.next()) {
				found = true;
				isbn = resultSet.getLong("ISBN");
				title = resultSet.getString("Title");
				genre = resultSet.getString("Genre");
				author = resultSet.getString("Author");
			}
			// if the book is not found, break out of the sql search and return to the menu
			if(!found) {
				throw new NotFoundException("Book with ISBN: " + isbnToUpdate + " was not found.");
			} 
			else {
				// check to see if the book is currently checked out by a member
				sqlStmt = "SELECT * FROM CHECKOUT WHERE BookISBN = ?";
				stmt = conn.prepareStatement(sqlStmt);
				stmt.setLong(1, isbnToUpdate);
				ResultSet resultSet2 = stmt.executeQuery();
				if (resultSet2.next()) {
					throw new ISBNException("Book with ISBN: " + isbnToUpdate + " cannot be updated because it is currently checked out.");
				} else {
					// print out elements of the book that was found
					System.out.println(String.format("ISBN: %d Title: %s Genre: %s Author: %s\n", isbn, title, genre, author));
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
							} else {
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
							} else {
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
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} catch (NotFoundException | CharacterLimitException | IllegalArgumentException e) {
			System.out.println(e.getMessage());
		} catch (ISBNException e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * Method to search through the database for any books that match the user's search criteria
	 * User can search by any of the books attributes (ISBN, title, genre, author)
	 * if the user inputs data in an invalid format loop until they input valid data
	 * Returns all the data for the matching books and displays it in a readable format
	 * If there are no matches for the search criteria, display a message saying no books were found
	 */
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
						System.out.println(String.format("ISBN: %d Title: %s Genre: %s Author: %s\n", resultSet.getLong("ISBN"), resultSet.getString("Title"), resultSet.getString("Genre"), resultSet.getString("Author")));
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
						System.out.println(String.format("ISBN: %d Title: %s Genre: %s Author: %s\n", resultSet.getLong("ISBN"), resultSet.getString("Title"), resultSet.getString("Genre"), resultSet.getString("Author")));
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
						System.out.println(String.format("ISBN: %d Title: %s Genre: %s Author: %s\n", resultSet.getLong("ISBN"), resultSet.getString("Title"), resultSet.getString("Genre"), resultSet.getString("Author")));
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
						System.out.println(String.format("ISBN: %d Title: %s Genre: %s Author: %s\n", resultSet.getLong("ISBN"), resultSet.getString("Title"), resultSet.getString("Genre"), resultSet.getString("Author")));
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
	
	/**
	 * Method to view all the books in the database
	 * Returns all the data for all the books in the database and displays it in a readable format
	 * If there are no books in the database, display a message saying there are no books to display
	 */
	@Override
	public void viewAll() {
		String sqlStmt = "SELECT * FROM BOOK";
		try {
			PreparedStatement stmt = conn.prepareStatement(sqlStmt);
			ResultSet resultSet = stmt.executeQuery();
			boolean found = false;
			while (resultSet.next()) {
				found = true;
				System.out.println(String.format("ISBN: %d Title: %s Genre: %s Author: %s\n", resultSet.getLong("ISBN"), resultSet.getString("Title"), resultSet.getString("Genre"), resultSet.getString("Author")));
			}
			if (!found) {
				System.out.println("No books to display.");
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
}