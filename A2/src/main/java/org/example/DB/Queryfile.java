package org.example.DB;

import java.io.*;

public class Queryfile {
    Query q;
    Queryfile(){

    }
    Queryfile(Query query){
        this.q = query;
    }

    /**
     *
     * @param tablename passes the tablename and creates the file with name as tablename inside the folderpath
     */
    public void createFileTableName(String tablename){
        try {
            File file = new File(q.getFolderPath()+"//"+tablename+".txt");

            if (file.createNewFile()) {
                System.out.println("File created successfully.");
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred while creating the file: " + e.getMessage());
        }
    }

    /**
     * stores the tableinfo in the alltableinfo file
     * @param tableName takes in the name as the tablename
     */
    public void writeTableNamesToAllTableInfo(String tableName){
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(q.getFolderPath()+"//"+"alltableinfo.txt",true))) {
            writer.newLine();
            writer.write("Table_Name"+":"+tableName);
            writer.newLine();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    /**
     *
     *  stores the tableinfo in the alltableinfo file
     *      * @param tableName takes in the name as the tablename
     */
    public void writeAttributeNamesToAllTableInfo(String columnName){
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(q.getFolderPath()+"//"+"alltableinfo.txt",true))) {
            writer.write("ColumnName"+":"+columnName);// Write the content to the file
            writer.newLine();
            System.out.println("File created and content written successfully.");
        } catch (IOException e) {
            System.out.println("An error occurred while creating the file: " + e.getMessage());
        }
    }

    /**
     *
     * @param fileName takes in the filename and
     * @param value
     */
     public void saveToFileInsert1(String fileName, String value) {
        Boolean checkLineExists = checkLineExists(fileName,value);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName,true))) {
            //  writer.write(attribute);
            if(!checkLineExists){
                writer.write(value);
                writer.newLine();
            }else{
                System.out.println("attribute already exists");
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    // checks whether the line exists or not
    static boolean checkLineExists(String fileName, String value) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.equals(value)) {
                    return true;
                }else {
                    return false;
                }
            }
        } catch (IOException e) {
            System.out.println("An error occurred while reading the file: " + e.getMessage());
        }
        return false;
    }
    // saves values from insert query to file2
     static void saveToFileInsert2(String fileName, String value) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName,true))) {
            //  writer.write(attribute);
            writer.write(value+" ");
            writer.newLine();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
