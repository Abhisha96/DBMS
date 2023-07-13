package org.example.DBTest;

import org.example.DB.Query;
import org.example.DB.Queryfile;
import org.example.DB.RegexPatternMatcher;

import java.io.File;
import java.util.StringTokenizer;

import static org.example.DB.Query.getFolderPath;

class DatabaseManager {
    Query q = new Query();
    RegexPatternMatcher regexP = new RegexPatternMatcher();
    Queryfile qF = new Queryfile();
    public void insertValues(String query, String databaseName) {
        // Execute the INSERT query
        System.out.println(getFolderPath());
        System.out.println(databaseName);
        databaseName = databaseName;
        q.setFolderPath(new File("C://Users//AVuser//csci5408_s23_b00937694_abhisha_thaker//A2//"), databaseName);
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
        qF.saveToFileInsert1(getFolderPath() + "//" + tableName + ".txt", attributeName);
        qF.saveToFileInsert2(getFolderPath() + "//" + tableName + ".txt", valuesPart);
        // Print the values
        System.out.println(valuesPart);
    }
}
