package org.example;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
import java.sql.*;
import java.util.Scanner;

public class DatabaseProgram {
    public static void main(String[] args) {
        try {
            // Connect to the local database
            Scanner sc = new Scanner(System.in);
            System.out.println("Enter the local username");
            String localUsername = sc.next();
            System.out.println("Enter the local password");
            String localPassword = sc.next();
            Connection localConnect = DriverManager.getConnection("jdbc:mysql://localhost:3306/ecommerce", localUsername, localPassword);

            // Connect to the remote database in GCP
            System.out.println("Enter the remote username");
            String remoteUsername = sc.next();
            System.out.println("Enter the remote password");
            String remotePassword = sc.next();
            Connection remoteConnect = DriverManager.getConnection("jdbc:mysql://35.184.144.54:3306/INVENTORY", remoteUsername, remotePassword);

            /*
            Fetch Item details from the Inventory table in the remote database
            and display the time it took to carry out this operation
             */
            // Fetch item details from the remote database
            Statement remoteStatement = remoteConnect.createStatement();

            // set the start time before running the first query
            long startTime = System.currentTimeMillis();
            ResultSet remoteResultSet = remoteStatement.executeQuery("SELECT item_id, item_name, available_quantity FROM Inventory");
            // log the end time after running the query
            long endTime = System.currentTimeMillis();

            long timediff = endTime - startTime;
            System.out.println("Time difference to fetch order details from remote database is"+""+timediff);


            /*
            Insert Order_Info details in the Order_Info table in the local database
            and display the time it took to carry out this operation
             */
            // Create an order in the local database
            Statement localStatement = localConnect.createStatement();

            String orderQuery = "INSERT INTO Order_info (order_id, user_id, item_name, quantity, order_date) VALUES (4, 1, 'Product A', 2, '2023-06-23')";

            startTime = System.currentTimeMillis();
            localStatement.executeUpdate(orderQuery);
            endTime = System.currentTimeMillis();
            timediff = endTime - startTime;
            System.out.println("Time difference to insert in order_info table is"+""+timediff);


            // Write the updated quantity back to the remote database

            String updateQuery = "UPDATE Inventory SET available_quantity = 4 WHERE item_id = 1";
            Statement updateStatement = remoteConnect.createStatement();

            startTime = System.currentTimeMillis();
            updateStatement.executeUpdate(updateQuery);
            endTime = System.currentTimeMillis();
            timediff = endTime - startTime;
            System.out.println("Time difference to update inventory in inventory table is"+""+timediff);

            // Close the database connections
            remoteResultSet.close();
            remoteStatement.close();
            updateStatement.close();

            localStatement.close();
            remoteConnect.close();
            localConnect.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
