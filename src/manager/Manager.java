package manager;

import java.sql.*;
import java.util.Scanner;
public abstract class Manager {

	
	// Protected variables that can be used by the other manager classes
	protected Connection conn;
	protected Statement stmt;
	
	protected Scanner keyboard;
	
	// Constructor to initialize the manager, connect to the database, and display the menu
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
