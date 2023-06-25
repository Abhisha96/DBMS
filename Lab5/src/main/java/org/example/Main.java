package org.example;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) throws IOException {
        // Press Alt+Enter with your caret at the highlighted text to see how
        // IntelliJ IDEA suggests fixing it.
        String text = new String(Files.readAllBytes(Paths.get("C:\\Users\\AVuser\\IdeaProjects\\Lab5\\src\\main\\resources\\weather.json")), StandardCharsets.UTF_8);

        JSONObject obj = new JSONObject(text);
        JSONArray arr = obj.getJSONArray("daily");
        JSONArray filteredData = new JSONArray();

        for (int i = 3; i < arr.length(); i++) {
            JSONObject daily = arr.getJSONObject(i);
            JSONObject feelsLikeObj = daily.getJSONObject("feels_like");
            double feelsLikeDay = feelsLikeObj.getDouble("day");
            if(feelsLikeDay > 15) {
                long timestamp = daily.getLong("dt");
                LocalDateTime dateTime = LocalDateTime.ofInstant(Instant.ofEpochSecond(timestamp), ZoneOffset.UTC);
                System.out.println("Date: " + dateTime.toLocalDate() + ", Feels Like (Day): " + feelsLikeDay + "°C");
                JSONObject filteredObject = new JSONObject();

                filteredObject.put("date", dateTime.toLocalDate().toString());
                filteredObject.put("feels_like_day", feelsLikeDay);

                filteredData.put(filteredObject);
            }
        }
        try {
            FileWriter file = new FileWriter("C:\\Users\\AVuser\\IdeaProjects\\Lab5\\src\\main\\resources\\summer-weather.json");
            file.write(filteredData.toString(4));
            file.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}