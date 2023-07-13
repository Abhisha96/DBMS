package org.example.DBTest;

import org.example.DB.Query;
import org.example.DB.Queryfile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.File;
import java.util.StringTokenizer;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class QueryTest {
    @Mock
    private File folderPath;

    private Query databaseManager;

    @BeforeEach
    void setUp() {
        databaseManager = new Query();
    }

    @Test
    void testCreateDB() {
        String query = "CREATE DATABASE dbName4;";
        String result = databaseManager.createDB(query);
        assertEquals("dbName4",result);
    }
    @Test
    void testCreateTables(){
        Queryfile qF = new Queryfile();
        String query = "CREATE TABLE table_name1 (column1, column2, column3);";
        String dbName = "dbName3";
        StringTokenizer tokenizer = mock(StringTokenizer.class);
        when(tokenizer.nextToken()).thenReturn("CREATE");
        when(tokenizer.nextToken()).thenReturn("TABLE");
        when(tokenizer.nextToken()).thenReturn("table_name1");

        String table = "table_name";
        String FolderPath = "C://Users//AVuser//csci5408_s23_b00937694_abhisha_thaker//A2//"+dbName;
        qF.createFileTableName(table);
    }


}
