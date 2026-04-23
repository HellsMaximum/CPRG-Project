package app;

import java.sql.SQLException;
import java.util.Scanner;

import databaseController.DatabaseController;
import manager.CheckoutManager;
import manager.MemberManager;
import manager.BookManager;

public class AppDriver {

	public static void main(String[] args) {
		DatabaseController dbc = new DatabaseController();
		try {
			dbc.connect();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		
		new BookManager(dbc.getConn(), dbc.getStmt());
		// new MemberManager(DatabaseConnection.getConn(), DatabaseConnection.getStmt());
		// new CheckoutManager(DatabaseConnection.getConn(), DatabaseConnection.getStmt());
		
		
		//Main Menu Method
		int choiceM = 0;
		//loop for entering sub-menus/methods
		while(choiceM != 4) {
			Scanner keyboardM = new Scanner(System.in);
			System.out.println("1) Manage Books.");
			System.out.println("2) Manage Members.");
			System.out.println("3) Manage Checkouts.");
			System.out.println("4) Exit program.");
			choiceM = Integer.parseInt(keyboardM.nextLine());
			switch(choiceM) {
				case 1: 
				try { 
					keyboardM.close();
					//use initialized books class
				} catch (Throwable e) {
					e.getMessage();
				}
					break;
				case 2:
				try {
					keyboardM.close();
					//use initialized members class
				} catch (Throwable e) {
					e.getMessage();
				}
					break;
				case 3:
				try {
					keyboardM.close();
					//use the initialized checkouts class
				} catch (Throwable e) {
					e.getMessage();
				}
					break;
				case 4:
					keyboardM.close();
					System.out.println("Exiting program");
					break;
				default:
					System.out.println("Invalid input. Please enter 1 to 4.");	
			}
		}
	}

}
