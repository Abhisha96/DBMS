package problem2;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class problem2 {
    public static void main(String[] args) {
        // Create 4 strings that stores the file location
        String positivePath = "C:/Users/AVuser/csci5408_s23_b00937694_abhisha_thaker/A3/src/main/resources/positive-words.txt";
        String negativePath = "C:/Users/AVuser/csci5408_s23_b00937694_abhisha_thaker/A3/src/main/resources/negative-words.txt";

        String reut009Title = "C:/Users/AVuser/csci5408_s23_b00937694_abhisha_thaker/A3/src/main/resources/reut2-009.sgm";
        String reut014Title = "C:/Users/AVuser/csci5408_s23_b00937694_abhisha_thaker/A3/src/main/resources/reut2-014.sgm";

        // Read those 4 files
        String positiveList = readFiles(positivePath);
        String negativeList = readFiles(negativePath);

        String reut009Content = readFiles(reut009Title);
        String reut014Content = readFiles(reut014Title);
        //concatenate the two files
        String combinedContent = reut009Content + reut014Content;

        // extract the title tags
        String regexScript = "<TITLE>(.*?)<\\/TITLE>";

        Pattern regex = Pattern.compile(regexScript, Pattern.DOTALL);
        Matcher matcher = regex.matcher(combinedContent);

        String titlecontent;
        List<String> titleList = new ArrayList<>();
        while (matcher.find()) {
             titlecontent = matcher.group(1);
             titleList.add(titlecontent);
        }

        String[] wordSplit = titleList.toArray(new String[0]);

        for (String word : wordSplit) {
            System.out.println("wordsplit" + word);
        }

        // splits the files whenever a new line is encountered and stores it in the string array
        String[] positive = positiveList.split("\n");
        String[] negative = negativeList.split("\n");

        int counterPos = 0;
        int counterNeg = 0;

        String[] wordsBag;

        String[] words;
        int[] frequencies;

        // Initialize the model for the table
        DefaultTableModel model = new DefaultTableModel(
                new Object[]{"News", "Title", "Match", "Polarity"},
                0
        );

        for(String line: wordSplit){
            words = line.toLowerCase().split("\\s+");
            wordsBag = line.toLowerCase().split("\\s+");

            frequencies = new int[words.length];

            counterPos = 0;
            counterNeg = 0;

            for (String word : wordsBag) {
                for (String pos : positive) {
                    if (word.equals(pos)) {
                        counterPos++;
                        // System.out.println(word);
                    }
                }
                for (String neg : negative) {
                    if (word.equals(neg)) {
                        counterNeg++;
                        // System.out.println(word);
                    }
                }
            }
            for (int i = 0; i < words.length; i++) {
                    int counter = 1;
                    for (int j = i + 1; j < words.length; j++) {
                        if (words[i].equalsIgnoreCase(words[j])) {
                            counter++;
                            words[j] = "";
                        }
                    }
                    frequencies[i] = counter;
                    //  System.out.println("match found"+wordSplit[i]+counter);
            }
            List<String> matches = new ArrayList<>();
            List<Integer> frequency = new ArrayList<>();
            Map<String, Integer> wordFrequencyMap = new HashMap<>();
            Map<String,Integer> finalFrequencyMap = new HashMap<>();
            for (int i = 0; i < words.length; i++) {
                if (!words[i].isEmpty() && frequencies[i] > 0) {
                    matches.add(words[i]);
                    frequency.add(frequencies[i]);
                    wordFrequencyMap.put(words[i],frequencies[i]);
                    if(frequencies[i] > 1){
                        finalFrequencyMap.put(words[i],frequencies[i]);
                    }
                }
            }
            matchFrequency(line,wordFrequencyMap);
            String polarity = displayTag(line,counterPos,counterNeg);
            System.out.println("Polarity is"+polarity);
            model.addRow(new Object[]{
                    model.getRowCount() + 1, // News
                    line, // Title
                    finalFrequencyMap.toString(), // Match
                    polarity // Polarity
            });
        }
        JTable table = new JTable(model);

        // Show the JTable in a JScrollPane
        JScrollPane scrollPane = new JScrollPane(table);

        // Create a JFrame to hold the JScrollPane
        JFrame frame = new JFrame("Table");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(scrollPane);
        frame.pack();
        frame.setVisible(true);
    }

    private static void matchFrequency(String line, Map<String, Integer> wordFrequencyMap) {
        System.out.println("For line 1"+line);
        System.out.print("bow1 = {");

        for (Map.Entry<String, Integer> entry : wordFrequencyMap.entrySet()) {
            String word = entry.getKey();
            int freq = entry.getValue();
            System.out.print("\"" + word + "\":" + freq);
            if (entry.getKey() != wordFrequencyMap.keySet().toArray()[wordFrequencyMap.size() - 1]) {
                System.out.print(", ");
            }
        }
        System.out.println("}");
    }
    static String readFiles(String filePath)  {
        BufferedReader readFile = null;
        try {
            readFile = new BufferedReader(new FileReader(filePath));
            StringBuilder content = new StringBuilder();
            String line;

            while ((line = readFile.readLine()) != null) {
                content.append(line).append("\n");
            }

            String sgmContent = content.toString();
            return sgmContent;
        } catch(FileNotFoundException e){
            e.printStackTrace();
        } catch(IOException e){
            e.printStackTrace();
        } finally{
            try {
                readFile.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }

    static String displayTag(String line, int npos, int nneg){
     //   System.out.println("Line is"+line);
      //   System.out.println("No of positive words from the line is"+npos);
        // System.out.println("No of negative words from the line is"+nneg);
        int overallScore = npos - nneg;
      //  System.out.println(overallScore);
        String polarity = null;
        if(overallScore > 0){
            polarity = "positive";
        } else if(overallScore < 0) {
            polarity = "negative";
        }else if(overallScore == 0) {
            polarity = "neutral";
        }
        return polarity;
    }
}
