package org.example.DB;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Query {
    private String query; // Holds the query value
    private Queryfile queryFile; // Link to the QueryFile - where all operations of file are performed
    private String databaseName; //Stores the databaseName
    private static File folderPath; // Stores the folderPath of the database
    public String getDatabaseName() {
        return databaseName;
    }
    private RegexPatternMatcher regexP;
    public void setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
    }
    public Query(){
        queryFile = new Queryfile();
        folderPath = new File("C://Users//AVuser//csci5408_s23_b00937694_abhisha_thaker//A2//");
        regexP = new RegexPatternMatcher();
    }
    public static File getFolderPath() {
        return folderPath;
    }

    public void setFolderPath(File folderPath, String databaseName) {
        this.folderPath = new File(folderPath+"//"+databaseName);
    }

    /**
     * CREATES THE DATABASE FOLDER BASED ON THE DATABASENAME PROVIDED IN THE QUERY
     * @param query Inputtype > String - e.g. CREATE DATABASE db;
     * @return databaseName Inputtype > String - e.g. db
     */
    public String createDB(String query) {
        // Separates the query into Tokens and extracts the desired token
        query = query.trim();
        // Create a StringTokenizer object
        StringTokenizer tokenizer = new StringTokenizer(query);
        // Skips the CREATE and DATABASE Token
        tokenizer.nextToken();
        tokenizer.nextToken();
        // Extract the nameofthedatabase
        databaseName = tokenizer.nextToken();
        // replaces ; at the end to empty string
        databaseName = databaseName.replace(";", "");


        // calls the setDatabaseName and updates the name of the databaseName
        setDatabaseName(databaseName);
        // sets the value of the folderpath
        folderPath = new File("C://Users//AVuser//csci5408_s23_b00937694_abhisha_thaker//A2");
        // calls the setFolderPath and updates the value of the folderPath and databaseName
        setFolderPath(folderPath,databaseName);


        // checks if the folderPath already exists, if it doesn't exist then creates the folder
        // Also returns databaseName
        if (!folderPath.exists()) { // Check if the folder already exists
            if (folderPath.mkdir()) { // Create the folder
                System.out.println("Folder created successfully.");
                return databaseName;
            } else {
                System.out.println("Failed to create the folder.");
            }
        } else {
            System.out.println("Folder already exists.");
        }
        return null;
    }

    /**
     * CREATES TWO FILES - One filename is an empty one and matches the tablename and
     * second file name stores all the tables information, thus created.
     * @param query String - e.g. CREATE TABLE users ( id INT, name VARCHAR(50), email VARCHAR(100));
     * @param dbName String - e.g. Name of the database in which you want to create this table.
     * No return value
     */
    public void createTables(String query, String dbName) {
        // todolater -  check whether the database exists or not. if no database exists, tell it to create a
        //database first
        // Execute the CREATE TABLES query
        System.out.println(databaseName);
        databaseName = dbName;
        setFolderPath(folderPath,databaseName);
        System.out.println(getFolderPath());

        query = query.trim();
        // Create a StringTokenizer object
        StringTokenizer tokenizer = new StringTokenizer(query);
        // Skips the CREATE and TABLE Token
        tokenizer.nextToken();
        tokenizer.nextToken();
        // Store the tablename value and pass this value.
        String tableName = tokenizer.nextToken();


        // if already created ignore
        // creates an empty file - with the name as the tablename
        queryFile.createFileTableName(tableName);
        // writes the tablenames in the alltableinfo file
        queryFile.writeTableNamesToAllTableInfo(tableName);

        // retrieves the value between ( an ) and stores it in a columnName
        int openBracketsIndex = query.indexOf("(");
        int closeBracketsIndex = query.lastIndexOf(")");
        String columnNameTable = query.substring(openBracketsIndex + 1, closeBracketsIndex);

        tokenizer = new StringTokenizer(columnNameTable, ",");

        while (tokenizer.hasMoreTokens()) {
              String columnName = tokenizer.nextToken().trim();
              System.out.println(columnName);
            // writes the columnames on the basis of the token in the alltableinfo file
              queryFile.writeAttributeNamesToAllTableInfo(columnName);
        }
    }
    // INSERT OPERATION

    /**
     *
     * @param query String - e.g. INSERT INTO Krishna (EmployeeID, FirstName, LastName, Age, DepartmentID) VALUES (8, 'Mayur', 'Krishna', 89, 90);
     * @param databaseName String - e.g. Name of the database in which you want to create this table.
     */
    public void insertValues(String query,String databaseName) {
        // Execute the INSERT query
        System.out.println(getFolderPath());
        System.out.println(databaseName);
        databaseName = databaseName;
        setFolderPath(folderPath,databaseName);
        System.out.println(getFolderPath());

        query = query.trim();
        // Create a StringTokenizer object
        StringTokenizer tokenizer = new StringTokenizer(query);
        // Skips the CREATE and TABLE Token
        tokenizer.nextToken();
        tokenizer.nextToken();
        // Store the tablename value and pass this value.
        String tableName = tokenizer.nextToken();

        // extract the columnames from the query
        String attributeName = regexP.extractColumnInsertQuery(query);
        System.out.println(attributeName);
        // extract the values from the query
        String valuesPart = regexP.extractValuesInsertQuery(query);
        System.out.println(valuesPart);

       // extractRows(token+".txt",valuesPart);
        queryFile.saveToFileInsert1(getFolderPath()+"//"+tableName+".txt",attributeName);
        queryFile.saveToFileInsert2(getFolderPath()+"//"+tableName+".txt",valuesPart);
        // Print the values
        System.out.println(valuesPart);
    }
    // SELECT OPERATION
    public void selectValues(String query,String databaseName) {

        System.out.println(getFolderPath());
        System.out.println(databaseName);
        databaseName = databaseName;
        setFolderPath(folderPath,databaseName);
        System.out.println(getFolderPath());

        // Execute the INSERT query
        System.out.println(query);
        // extracts the table name on whom operation is to be performed
        String tableName = regexP.extractSelectTableName(query);

        // extracts the filter
        String columnName = regexP.extractSelectColumnName(query);
        System.out.println(columnName);

        // extracts the condition
        String targetRow = regexP.extractSelectCondition(query);
        System.out.println(targetRow);

        Pattern pattern = Pattern.compile("(\\w+)\\s*(>=|<=|==|=|<|>)\\s*(\\w+)");
        Matcher matcher = pattern.matcher(targetRow);

        String targetRowName = new String();
        String operator;
        String targetValue = new String();

        if (matcher.find()) {
            targetRowName = matcher.group(1);
            operator = matcher.group(2);
            targetValue = matcher.group(3);

            System.out.println("EmployeeId: " + targetRowName);
            System.out.println("Operator: " + operator);
            System.out.println("Value: " + targetValue);
        } else {
            System.out.println("No match found");
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(getFolderPath()+"//"+tableName.trim()+".txt"))) {
            String line;
            String firstLine = reader.readLine();
            String[] attributeNames;
            int columnValueNo;
            attributeNames = firstLine.split(",");

            int lineNumber = 1;
            // Read the lines from the file
            while ((line = reader.readLine()) != null) {
                // Remove parentheses and trim leading/trailing whitespace
                line = line.replaceAll("[()]", "").trim();
                // Split the line into values (assuming comma-separated values)
                String[] values = line.split(",");
                // Retrieve the value for the specified column
                String columnValue = getTrimmedValue(attributeNames[getColumnIndex(targetRowName,attributeNames)]);
                System.out.println(columnValue);
                // Check if the column value matches the target value
                String lineNumberValue = getTrimmedValue(String.valueOf(lineNumber));
                System.out.println(lineNumberValue);
                if (columnValue.equals(targetRowName) && targetValue.equals(values[getColumnIndex(columnValue,attributeNames)])) {
                    System.out.println(targetRowName);
                    // Retrieve the value of the target column
                    String value = getTrimmedValue(values[getColumnIndex(columnName,attributeNames)]);
                    System.out.println(columnName + "" + value);
                }
                lineNumber++;
            }
        } catch (IOException e) {
            System.out.println("An error occurred while reading the file: " + e.getMessage());
        }
    }

    private static int getColumnIndex(String columnName,String[] attributeName) {
           if (columnName.equalsIgnoreCase(attributeName[0].trim())) {
               return 0;
           } else if (columnName.equalsIgnoreCase(attributeName[1].trim())) {
               return 1;
           } else if (columnName.equalsIgnoreCase(attributeName[2].trim())) {
               return 2;
           } else if (columnName.equalsIgnoreCase(attributeName[3].trim())) {
               return 3;
           } else if (columnName.equalsIgnoreCase(attributeName[4].trim())) {
               return 4;
           }
        return -1;
    }

    // Helper method to remove leading/trailing whitespace and single quotes from a value
    private static String getTrimmedValue(String value) {
        return value.trim().replaceAll("'", "");
    }

    // UPDATE queries

    public void updateValues(String query,String databaseName) {
        // Execute the UPDATE query
        System.out.println(getFolderPath());
        System.out.println(databaseName);
        databaseName = databaseName;
        setFolderPath(folderPath,databaseName);
        System.out.println(getFolderPath());

        System.out.println(query);
        // extracts the table name on whom operation is to be performed
        String tableName = regexP.extractUpdateTableName(query);
        System.out.println(tableName);
        // extracts the filter
        String columnNameValue = regexP.extractUpdateColumnNameValue(query);

        String[] parts = columnNameValue.split("'");
        String columnName = parts[0];
        String updateToValue = "'"+parts[1]+"'";

        System.out.println(columnName);
        System.out.println(updateToValue);

        // extracts the condition
        String targetRow = regexP.extractUpdateCondition(query);
        System.out.println(targetRow);

        //word,whitespace,operator,whitespace,word
        Pattern pattern = Pattern.compile("(\\w+)\\s*(>=|<=|==|=|<|>)\\s*(\\w+)");
        Matcher matcher = pattern.matcher(targetRow);

        String targetRowName = new String();
        String operator;
        String targetValue = new String();
        if (matcher.find()) {
            targetRowName = matcher.group(1);
            operator = matcher.group(2);
            targetValue = matcher.group(3);

            System.out.println("EmployeeId: " + targetRowName);
            System.out.println("Operator: " + operator);
            System.out.println("Value: " + targetValue);
        } else {
            System.out.println("No match found");
        }
        List<String> linesFinal = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(getFolderPath()+"//"+tableName.trim()+".txt"))) {
            String line;
            String firstLine = reader.readLine();
            String[] attributeNames;
            int columnValueNo;
            attributeNames = firstLine.split(",");
            int lineNumber = 1;
            linesFinal.add(firstLine);
            // Read the lines from the file
            while ((line = reader.readLine()) != null) {
                // Remove parentheses and trim leading/trailing whitespace
                line = line.replaceAll("[()]", "").trim();
                // Split the line into values (assuming comma-separated values)
                String[] values = line.split(",");
                // Retrieve the value for the specified column
                String columnValue = getTrimmedValue(attributeNames[getColumnIndex(targetRowName,attributeNames)]);
                System.out.println(columnValue);
                // Check if the column value matches the target value
                String lineNumberValue = getTrimmedValue(String.valueOf(lineNumber));
                System.out.println(lineNumberValue);
                linesFinal.add(line);
                if (columnValue.equals(targetRowName) && targetValue.equals(values[getColumnIndex(columnValue,attributeNames)])) {
                    System.out.println(targetRowName);
                    // Retrieve the value of the target column
                    values[getColumnIndex(columnName,attributeNames)] = updateToValue;
                    String value = values[getColumnIndex(columnName,attributeNames)];
                    System.out.println(columnName + "" + value);
                    String updatedLine = String.join(", ", values);
                    linesFinal.remove(Integer.parseInt(lineNumberValue));
                    linesFinal.add(Integer.parseInt(lineNumberValue),updatedLine);
                }
                lineNumber++;
            }
        } catch (IOException e) {
            System.out.println("An error occurred while reading the file: " + e.getMessage());
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(getFolderPath()+"//"+tableName.trim()+".txt"))) {
            for(String line: linesFinal){
                writer.write(line);
                writer.newLine();
            }
            System.out.println("File updated successfully.");
        } catch (IOException e) {
            System.out.println("An error occurred while writing to the file: " + e.getMessage());
        }
    }

    public void deleteValues(String query,String databaseName) {
        // example query - DELETE FROM Krishna WHERE FirstName='Mayur';
        // Execute the DELETE query
        System.out.println(getFolderPath());
        System.out.println(databaseName);
        databaseName = databaseName;
        setFolderPath(folderPath,databaseName);
        System.out.println(getFolderPath());

        System.out.println(query);
        // extracts the table name on whom operation is to be performed
        String tableName = regexP.extractDeleteTableInfo(query);
        System.out.println(tableName);
        // extracts the filter

        // extracts the condition
        String targetRow = regexP.extractDeleteCondition(query);
        System.out.println(targetRow);
        // word,whitespace,operator,whitespace,word
        Pattern pattern = Pattern.compile("(\\w+)\\s*(>=|<=|==|=|<|>)\\s*(\\w+)");
        Matcher matcher = pattern.matcher(targetRow);

        String targetRowName = new String();
        String operator;
        String targetValue = new String();
        if (matcher.find()) {
            targetRowName = matcher.group(1);
            operator = matcher.group(2);
            targetValue = matcher.group(3);

            System.out.println("EmployeeId: " + targetRowName);
            System.out.println("Operator: " + operator);
            System.out.println("Value: " + targetValue);
        } else {
            System.out.println("No match found");
        }
        List<String> linesFinal = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(getFolderPath()+"//"+tableName.trim()+".txt"))) {
            String line;
            String firstLine = reader.readLine();
            String[] attributeNames;
            int columnValueNo;
            attributeNames = firstLine.split(",");
            int lineNumber = 1;
            linesFinal.add(firstLine);
            // Read the lines from the file
            while ((line = reader.readLine()) != null) {
                // Remove parentheses and trim leading/trailing whitespace
                line = line.replaceAll("[()]", "").trim();
                // Split the line into values (assuming comma-separated values)
                String[] values = line.split(",");
                System.out.println(values);
                System.out.println(values[getColumnIndex(targetRowName,attributeNames)]);
                // Retrieve the value for the specified column
                String columnValue = getTrimmedValue(attributeNames[getColumnIndex(targetRowName,attributeNames)]);
                System.out.println(columnValue);
                // Check if the column value matches the target value
                String lineNumberValue = getTrimmedValue(String.valueOf(lineNumber));
                System.out.println(lineNumberValue);
                linesFinal.add(line);
             //   System.out.println(values[getColumnIndex(columnValue,attributeNames)]);
               // System.out.println(values[line.indexOf(targetValue)]);
                if (columnValue.equals(targetRowName) && targetValue.equals(values[getColumnIndex(columnValue,attributeNames)])) {
                    System.out.println(targetValue);
                    System.out.println(lineNumberValue);
                    System.out.println(targetRowName);
                    // Retrieve the value of the target column
                   // values[getColumnIndex(columnName,attributeNames)] = updateToValue;
                    String value = values[getColumnIndex(columnValue,attributeNames)];
                    System.out.println(columnValue + "" + value);
                  //  String updatedLine = String.join(", ", values);
                    linesFinal.remove(Integer.parseInt(lineNumberValue));
                }
                lineNumber++;
            }
        } catch (IOException e) {
            System.out.println("An error occurred while reading the file: " + e.getMessage());
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(getFolderPath()+"//"+tableName.trim()+".txt"))) {
            for(String line: linesFinal){
                writer.write(line);
                writer.newLine();
            }
            System.out.println("File updated successfully.");
        } catch (IOException e) {
            System.out.println("An error occurred while writing to the file: " + e.getMessage());
        }
    }
}
