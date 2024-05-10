package core.json;

import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;

import static core.helper.Files.*;

public class JsonParser {

    JSONParser parser = new JSONParser();

    public String parse(String filename, String key, String value) {
        String keys = "";
        try {
            Object obj = parser.parse(new FileReader(getJsonFilePath(filename)));
            JSONObject jsonObject = (JSONObject) obj;
            JSONObject object = (JSONObject) jsonObject.get(key);
            String values = (String) object.get(value);
            keys = values;
        } catch (IOException | ParseException e) {
            System.out.println("Parsing error " + e.toString());
            e.printStackTrace();
        }
        return keys;
    }

    public String parse(String filename, String args, int value) {
        String keys = "";
        try {
            Object obj = parser.parse(new FileReader(getJsonFilePath(filename)));
            JSONObject jsonObject = (JSONObject) obj;
            JSONArray Array = (JSONArray) jsonObject.get(args);
            String values = (String) Array.get(value);
            System.out.println(values);
            keys = values;
        } catch (IOException | ParseException e) {
            System.out.println("Parsing error " + e.toString());
            e.printStackTrace();
        }
        return keys;
    }
}