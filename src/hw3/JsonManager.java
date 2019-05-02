package hw3;

import org.json.JSONArray;
import org.json.JSONObject;
import java.io.FileWriter;
import java.io.*;



public class JsonManager extends WatchList {



    public JSONArray createJSON(){

        return new JSONArray(getHolder());
    }


    public void writeJSON(JSONArray obj) {

        try (FileWriter file = new FileWriter("file1.txt")) {
            file.write(obj.toString());
            System.out.println("Successfully Copied JSON Object to File...");
            System.out.println("\nJSON Array: " + obj.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}






