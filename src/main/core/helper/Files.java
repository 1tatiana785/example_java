package core.helper;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Files {

    public static final String FILE_PATH = "src/resources/dataFiles/";
    public static final String DOWNLOAD_PATH = "src/resources/downloadedPDF/";

    public static String getFilePath(String fileName) {
        String file = new File(FILE_PATH + fileName).getAbsolutePath();
        return file;
    }

    public static String getJsonFilePath(String fileName) {
        String file = new File("src/resources/jsonFiles/" + fileName).getAbsolutePath();
        return file;
    }

    public static String getDownloadPath(String fileName) {
        String file = new File(DOWNLOAD_PATH + fileName).getAbsolutePath();
        return file;
    }

    public static String getFileNameNotInLatin(int name) {
        ArrayList<String> list = new ArrayList<>();
        String[] elements = {"前景.csv", "Jeg ser på tv.txt"};
        for (String x : elements) {
            list.add(x);
        }
        return list.get(name);
    }

    public static String getErrorFile(String fileName) {
        Map<String, String> map = new HashMap<>();
        map.put("jpgFile", "Cat.jpg");
        map.put("bigFile", "15MB.csv");
        map.put("emptyTxt", "Empty.txt");
        map.put("emptyCsv", "Empty.csv");
        return map.get(fileName);
    }

    /*** All ***/
    public static final String All = "AllLines.csv";
    public static final String FileURLLinksTxt = "Singleurlsearch.txt";
    public static final String DataSES = "DataSES.csv";
    public static final String DataBEV = "DataBEV.txt";

    /***  DownloadedPDF  ***/
    public static final String BadDownloadsList = "bad_downloads_list.txt";

    /***  JSON files (texts)  ***/
    public static final String BEV = "BEV.json";
}