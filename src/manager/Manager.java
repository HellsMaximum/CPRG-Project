package manager;

import java.sql.*;
import java.util.Scanner;

/**
 * Abstract class that serves as a template for the other manager classes (BookManager, MemberManager, CheckoutManager)
 * This class contains protected variables for the database connection and statement, as well as a scanner for user input
 * The constructor initializes the manager by setting the connection and statement variables and calling the displayMenu method
 * Contains abstract methods that must be implemented by the other manager classes
 * These methods do the following: 
 * display the menu, add data, remove data, update data, search data, and view all the data in the database.
 * they do each of these for their respective tables
 */
public abstract class Manager {

	
	// Protected variables that can be used by the other manager classes
	protected Connection conn;
	protected Statement stmt;
	
	protected Scanner keyboard;
	
	// Constructor to initialize the manager, set database connection variables, and display the menu
	public Manager(Connection conn, Statement stmt) {
			this.conn = conn;
			this.stmt = stmt;
			displayMenu();
	}
	
	// Abstract methods to be implemented by the other Manager classes
	public abstract void displayMenu();
	public abstract void add();
	public abstract void remove();
	public abstract void update();
	public abstract void search();
	public abstract void viewAll();
}
