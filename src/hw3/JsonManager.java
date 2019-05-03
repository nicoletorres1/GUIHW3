package hw3;

import jdk.nashorn.internal.parser.JSONParser;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
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

    public void readJSON(String file) throws FileNotFoundException {
        JSONTokener tokener = new JSONTokener(new FileInputStream(new File("src/file1.txt")));
        JSONArray result = new JSONArray(tokener);

        for (int i = 0; i < result.length(); i++) {
            Item jsonItem = new Item();
            jsonItem.setItemName(result.getJSONObject(i).getString("itemName"));
            jsonItem.setMinPrice(result.getJSONObject(i).getInt("minPrice"));
            jsonItem.setMaxPrice(result.getJSONObject(i).getInt("maxPrice"));
            jsonItem.setItemChange(result.getJSONObject(i).getInt("itemChange"));
            jsonItem.setItemPrice(result.getJSONObject(i).getInt("itemPrice"));
            jsonItem.setURL(result.getJSONObject(i).getString("URL"));
            add(jsonItem);
        }
    }

}
