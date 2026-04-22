package app;

import java.sql.SQLException;

import databaseController.DatabaseControler;
import manager.CheckoutManager;
import manager.MemberManager;
import manager.BookManager;

public class AppDriver {

	public static void main(String[] args) {
		DatabaseControler dbc = new DatabaseControler();
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
