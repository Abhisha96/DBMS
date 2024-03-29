package org.example.DB;

public class Helper {
    /**
     * Finds the index of a column name from an array of attribute names.
     * @param columnName
     * @param attributeName
     * @return int - 0,1,2,3,4
     */
     static int getColumnIndex(String columnName,String[] attributeName) {
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

    /**
     * Removes whitespace or single quotes characters from a value.
     * @param value
     * @return
     */
    static String getTrimmedValue(String value) {
        return value.trim().replaceAll("'", "");
    }

}
