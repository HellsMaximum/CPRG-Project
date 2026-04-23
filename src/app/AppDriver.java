package app;

import java.sql.SQLException;
import java.util.Scanner;

import databaseController.DatabaseController;
import manager.CheckoutManager;
import manager.MemberManager;
import manager.BookManager;

public class AppDriver {

	public static void main(String[] args) {
		// Establish connection to the database
		DatabaseController dbc = new DatabaseController();
		try {
			dbc.connect();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		//Main Menu
		int choiceMInt = 0;
		Scanner keyboardM = new Scanner(System.in);
		//loop for entering sub-menus/methods
		while(choiceMInt != 6) {
			System.out.println("1) Manage Books.");
			System.out.println("2) Manage Members.");
			System.out.println("3) Manage Checkouts.");
			System.out.println("4) Create Tables.");
			System.out.println("5) Delete Tables.");
			System.out.println("6) Exit program.");
			String choiceM = keyboardM.nextLine();
			if (!choiceM.matches("\\d+")) {
				System.out.println("Invalid input. Please enter 1 to 6.");
				continue;
			} else{
				choiceMInt = Integer.parseInt(choiceM);
				switch(choiceMInt) {
					case 1: 
						try { 
							new BookManager(dbc.getConn(), dbc.getStmt());
							//use initialized books class
						} catch (Throwable e) {
							System.out.println(e.getMessage());
						}
						break;
					case 2:
						try {
							System.out.println("Initializing Member Manager...");
							new MemberManager(dbc.getConn(), dbc.getStmt());
							//use initialized members class
						} catch (Throwable e) {
							System.out.println(e.getMessage());
						}
						break;
					case 3:
						try {
							new CheckoutManager(dbc.getConn(), dbc.getStmt());
							//use the initialized checkouts class
						} catch (Throwable e) {
							System.out.println(e.getMessage());
						}
						break;
					case 4:
						try {
							dbc.createTables();
							System.out.println("Tables created successfully.");
						} catch (SQLException e) {
							System.out.println(e.getMessage());
						}
						break;
					case 5:
						// Ask for confirmation from the user before deleting the tables
						System.out.println("Are you sure you want to delete all the tables? This action cannot be undone. (Y/N)");
						String confirmation = keyboardM.nextLine();
						while (!confirmation.equalsIgnoreCase("Y") && !confirmation.equalsIgnoreCase("N")) {
							System.out.println("Invalid input. Please enter Y or N.");
							confirmation = keyboardM.nextLine();
						}
						if (confirmation.equalsIgnoreCase("Y")) {
							try {
								dbc.dropTables();
								System.out.println("Tables deleted successfully.");
							} catch (SQLException e) {
								System.out.println(e.getMessage());
							}
						} else {
							System.out.println("Delete tables operation cancelled.");
						}
						break;
					case 6:
						keyboardM.close();
						System.out.println("Exiting program");
						break;
					default:
						System.out.println("Invalid input. Please enter 1 to 6.");	
				}
			}
		}
	}

}