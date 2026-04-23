package app;

import java.sql.SQLException;

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
	}

}
