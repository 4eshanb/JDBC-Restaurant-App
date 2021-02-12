import java.sql.*;
import java.io.*;
import java.util.*;

/**
 * A class that connects to PostgreSQL and disconnects. You will need to change
 * your credentials below, to match the usename and password of your account in
 * the PostgreSQL server. The name of your database in the server is the same as
 * your username. You are asked to include code that tests the methods of the
 * RestaurantApplication class in a similar manner to the sample
 * RunFilmsApplication.java program.
 */

public class RunRestaurantApplication {
	public static void main(String[] args) {

		Connection connection = null;
		try {
			// Register the driver
			Class.forName("org.postgresql.Driver");
			// Make the connection.
			// You will need to fill in your real username (twice) and password for your
			// Postgres account in the arguments of the getConnection method below.

			// String url =
			// "jdbc:postgresql://cse180-db.lt.ucsc.edu/esbharga?user=esbharga&password=structure60unique";
			// connection = DriverManager.getConnection(url);

			// removed password, but not username
			connection = DriverManager.getConnection("jdbc:postgresql://localhost", "eshan",
					"Rando");
			//random password above


			if (connection != null)
				System.out.println("Connected to the database!");

			/*
			 * Include your code below to test the methods of the RestaurantApplication
			 * class The sample code in RunFilmsApplication.java should be useful. That code
			 * tests other methods for a different database schema. Your code below:
			 */

			RestaurantApplication app = new RestaurantApplication(connection);

			//TEST 1 - getFrequentlyOrderedMenuItems

			System.out.println("\nOutput of getFrequentlyOrderedMenuItems\n" +
			"when the parameter numMenuItemsOrdered is 65:\n" + app.getFrequentlyOrderedMenuItems(65).toString() + "\n");
		
			// EXTRA TESTS
			/*if( app.getFrequentlyOrderedMenuItems(-1) == null ) {
				System.out.println("ERROR: Negative returned null list");
			}
			
			System.out.printf("Update Server number 3 to Fogg\n");
			if ( app.updateServerName(3, "Phileas Fogg") == 0 ) {
				System.out.println("ERROR: should get one got zero\n");
			}
			System.out.printf("Updating non existtent server id\n");*/
			

			//TEST 2 - updateServerName

			System.out.println("\nOutput of updateServerName when theServerID is 3\n" +
			"and newServerName is ‘Phileas Fogg':\n" + Integer.toString(app.updateServerName(3, "Phileas Fogg")) + "\n");

			//TEST 3 - updateServerName

			System.out.println("\nOutput of updateServerName when theServerID is 10\n" +
			"and newServerName is ‘John Smith':\n" + Integer.toString(app.updateServerName(10, "John Smith")) + "\n");

			//TEST 4 - reduceSomeVisitCosts

			System.out.println("\nOutput of reduceSomeVisitCosts when maxVisitCount is 10:\n" +
			Integer.toString(app.reduceSomeVisitCosts(10)) + "\n");

			//TEST 5 - reduceSomeVisitCosts

			System.out.println("\nOutput of reduceSomeVisitCosts when maxVisitCount is 95:\n" +
			Integer.toString(app.reduceSomeVisitCosts(95)) + "\n");


			// EXTRA TESTS
			//app.reduceSomeVisitCosts(-1);
			//app.reduceSomeVisitCosts(800);

			/*******************
			 * Your code ends here
			 */

		} catch (SQLException | ClassNotFoundException e) {
			System.out.println("Error while connecting to database: " + e);
			e.printStackTrace();
		} finally {
			if (connection != null) {
				// Closing Connection
				try {
					connection.close();
				} catch (SQLException e) {
					System.out.println("Failed to close connection: " + e);
					e.printStackTrace();
				}
			}
		}
	}
}
