package org.example.DBTest;
import org.example.DB.Query;
import org.example.DB.Queryfile;
import org.example.DB.RegexPatternMatcher;

import java.io.File;
import java.util.StringTokenizer;

import static org.example.DB.Query.getFolderPath;

public class ManualTesting {

    public static void main(String[] args) {
        DatabaseManager databaseManager = new DatabaseManager();

        String query = "INSERT INTO tableName2 (column1, column2, column3) VALUES (value1, value2, value3);";
        String databaseName = "dbName3";

            // Manual Test Case 1: Valid Insert Query
            System.out.println("Manual Test Case 1 - Valid Insert Query");
            databaseManager.insertValues(query, databaseName);
            // Perform manual validation by checking the database file or output.
            System.out.println();

            // Manual Test Case 2: Invalid Insert Query
            //System.out.println(invalidTestQuery(query,databaseName));

            // Manual Test Case 3: Empty Values in Insert Query
            //System.out.println(emptyValues(query,databaseName));
    }

    private static boolean emptyValues(String query, String databaseName) {
        DatabaseManager databaseManager = new DatabaseManager();
        System.out.println(emptyValues(query,databaseName));
        System.out.println("Manual Test Case 3 - Empty Values in Insert Query");
        query = "INSERT INTO table_name (column1, column2, column3) VALUES ();";
        databaseManager.insertValues(query, databaseName);
        // Perform manual validation by checking the error message or database file.
        return false;
    }

    private static Boolean invalidTestQuery(String query, String databaseName) {
        DatabaseManager databaseManager = new DatabaseManager();
        System.out.println("Manual Test Case 2 - Invalid Insert Query");
        query = "INSERT INTO table_name VALUES (value1, value2, value3);";
        databaseManager.insertValues(query, databaseName);
        return false;
        // Perform manual validation by checking the error message or database file.
    }
}




