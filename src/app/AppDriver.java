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
		int choiceM = 0;
		Scanner keyboardM = new Scanner(System.in);
		//loop for entering sub-menus/methods
		while(choiceM != 4) {
			System.out.println("1) Manage Books.");
			System.out.println("2) Manage Members.");
			System.out.println("3) Manage Checkouts.");
			System.out.println("4) Exit program.");
			choiceM = Integer.parseInt(keyboardM.nextLine());
			switch(choiceM) {
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
					keyboardM.close();
					System.out.println("Exiting program");
					break;
				default:
					System.out.println("Invalid input. Please enter 1 to 4.");	
			}
		}
	}

}