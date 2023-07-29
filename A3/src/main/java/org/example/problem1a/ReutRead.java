package org.example.problem1a;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ReutRead {
    public static void main(String[] args) {
        String reut009 = "C:/Users/AVuser/csci5408_s23_b00937694_abhisha_thaker/A3/src/main/resources/reut2-009.sgm";
        String reut014 = "C:/Users/AVuser/csci5408_s23_b00937694_abhisha_thaker/A3/src/main/resources/reut2-014.sgm";

        String reut009Content = readFiles(reut009);
        String reut014Content = readFiles(reut014);

        String combinedContent = reut009Content + reut014Content;

        String regexText = "<REUTERS(.*?)(.*?)>(.*?)<TEXT>(.*?)<TITLE>(.*?)<\\/TITLE>(.*?)<DATELINE>(.*?)<\\/DATELINE>(.*?)<BODY>(.*?)<\\/BODY>(.*?)<\\/TEXT>";

        Pattern regex = Pattern.compile(regexText, Pattern.DOTALL);
        Matcher matcher = regex.matcher(combinedContent);

        String mongoConnect = "mongodb://localhost:27017/";
        MongoClientURI connectURL = new MongoClientURI(mongoConnect);
        // connect to the mongodb server
        try(MongoClient connectClient = new MongoClient(connectURL)){
            MongoDatabase db = connectClient.getDatabase("ReuterDb");
            MongoCollection<Document> collection = db.getCollection("NewsArticle");
            Document textDocument = new Document();

            while (matcher.find()) {
                String textcontent = matcher.group(4);
                String titlecontent = matcher.group(5);
                String datecontent = matcher.group(7);
                String bodycontent = matcher.group(9);
                textDocument = new Document("text",textcontent).append("title", titlecontent).append("dateline", datecontent).append("body", bodycontent);
                Document document = new Document("title", titlecontent).append("text",textDocument);
                collection.insertOne(document);
            }
            System.out.println("connected to db success");

            }catch(Exception e){
            e.printStackTrace();
        }
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
}
