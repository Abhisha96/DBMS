package org.example.DB;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexPatternMatcher {
    /**
     *
     * @param query : insert query is passed
     * @return : regex matcher columnames
     */
    static String extractColumnInsertQuery(String query) {
        Pattern pattern = Pattern.compile("INSERT INTO \\w+\\s*\\(([^)]+)\\)",Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(query);

        if (matcher.find()) {
            String attributeList = matcher.group(1);
            System.out.println(attributeList);
            return attributeList;
        }
        return null;
    }

    /**
     *
     * @param query insert query is passed
     * @return returns extracted matching values as per the regex passsed
     */
    static String extractValuesInsertQuery(String query) {
        Pattern pattern = Pattern.compile("VALUES\\s*([\\s\\S]+);", Pattern.CASE_INSENSITIVE);
       // Pattern pattern = Pattern.compile("VALUES(.+?)");
        Matcher matcher = pattern.matcher(query);
        if (matcher.find()) {
            System.out.println(matcher.group(1));
            return matcher.group(1);
        }
        return "";
    }

    /**
     *
     * @param query takes in the select query
     * @return the condition after where
     */
     String extractSelectCondition(String query) {
        Pattern conditionPattern = Pattern.compile("WHERE\\s*(.+)",Pattern.CASE_INSENSITIVE);
        Matcher conditionMatcher = conditionPattern.matcher(query);
        if (conditionMatcher.find()) {
            String conditionSection = conditionMatcher.group(1).trim();
            String[] conditions = conditionSection.split("AND");
            for (String condition : conditions) {
                System.out.println("Condition: "+condition.trim());
                return condition.trim();
            }
        }
        return null;
    }

    /**
     *
     * @param query takes in the select query
     * @return return tablename in between select and from
     */
     String extractSelectColumnName(String query) {

        Pattern columnPattern = Pattern.compile("SELECT(.+?)FROM", Pattern.CASE_INSENSITIVE);
        Matcher columnMatcher = columnPattern.matcher(query);
        if (columnMatcher.find()) {
            String columnSection = columnMatcher.group(1).trim();
            String[] columnNames = columnSection.split(",");
            for (String columnName : columnNames) {
                System.out.println(columnName.trim());
                return columnName.trim();
            }
        }
        return null;
    }

    /**
     *
     * @param query takes in the select query
     * @return returns tablename between from and where
     */
     static String extractSelectTableName(String query) {
        String tableName = "";

        String pattern = "FROM(.+?)WHERE";
        Pattern regexPattern = Pattern.compile(pattern,Pattern.CASE_INSENSITIVE);

        // Create a matcher object
        Matcher matcher = regexPattern.matcher(query);

        // Find the table name in the query
        if (matcher.find()) {
            tableName = matcher.group(1);
        }
        return tableName;
    }

    /**
     *
     * @param query takes in update condition
     * @return returns the condition after where
     */
    String extractUpdateCondition(String query) {
        Pattern conditionPattern = Pattern.compile("WHERE(.+)",Pattern.CASE_INSENSITIVE);
        Matcher conditionMatcher = conditionPattern.matcher(query);
        if (conditionMatcher.find()) {
            String condition = conditionMatcher.group(1).trim();
            System.out.println("Condition: " + condition);
            return condition;
        }
        return null;
    }

    /**
     *
     * @param query takes in the update query
     * @return returns the column to be updated and value to be set to
     */
    String extractUpdateColumnNameValue(String query) {
        Pattern updatePattern = Pattern.compile("SET(.+?)WHERE", Pattern.CASE_INSENSITIVE);
        Matcher updateMatcher = updatePattern.matcher(query);
        if (updateMatcher.find()) {
            String updateSection = updateMatcher.group(1).trim();
            String[] updates = updateSection.split(",");
            for (String update : updates) {
                String[] parts = update.trim().split("=");
                String columnName = parts[0].trim();
                String updateValue = parts[1].trim();
                System.out.println("Column name: " + columnName);
                System.out.println("Update value: " + updateValue);
                return columnName+""+updateValue;
            }
        }
        return null;
    }

    /**
     *
     * @param query takes in the update query
     * @return returns the tablename beteween update and set
     */
    String extractUpdateTableName(String query) {
        Pattern tablePattern = Pattern.compile("UPDATE\\s*(.+?)\\s*SET", Pattern.CASE_INSENSITIVE);
        Matcher tableMatcher = tablePattern.matcher(query);
        if (tableMatcher.find()) {
            String tableName = tableMatcher.group(1).trim();
            System.out.println("Table name: " + tableName);
            return tableName;
        }
        return null;
    }

    /**
     *
     * @param query takes in the delete query
     * @return condition that is passed in the delete query after where
     */
    String extractDeleteCondition(String query) {
        Pattern conditionPattern = Pattern.compile("WHERE(.+)",Pattern.CASE_INSENSITIVE);
        Matcher conditionMatcher = conditionPattern.matcher(query);
        if (conditionMatcher.find()) {
            String condition = conditionMatcher.group(1).trim();
            System.out.println("Condition: " + condition);
            return condition;
        }
        return null;
    }

    /**
     *
     * @param query takes in the delete query
     * @return returns the table name that is passed in the query between from and where
     */
    String extractDeleteTableInfo(String query) {
        Pattern tablePattern = Pattern.compile("FROM(.+?)WHERE", Pattern.CASE_INSENSITIVE);
        Matcher tableMatcher = tablePattern.matcher(query);
        if (tableMatcher.find()) {
            String tableName = tableMatcher.group(1).trim();
            System.out.println("Table name: " + tableName);
            return tableName;
        }
        return null;
    }
}
