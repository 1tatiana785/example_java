package core.testRail.api;

import org.json.JSONObject;
import org.testng.ITestResult;

import java.util.ArrayList;
import java.util.List;

public class DataForAPI {

    /*** There is pre-initialization of the driver end his way that is it prior to calling object     */

    private static final ThreadLocal<DataForAPI> driverThread = new ThreadLocal<>();
    private static volatile DataForAPI instance;
    public final String exception = "";
    public List<JSONObject> statusIdList = new ArrayList<>();
    public ArrayList<Integer> idList = new ArrayList<>();
    private List<JSONObject> statusNameList = new ArrayList<>();

    public static DataForAPI getInstance() {
        if (driverThread.get() == null) {
            synchronized (DataForAPI.class) {
                driverThread.set(new DataForAPI());
            }
        }
        return driverThread.get();
    }

    public List<JSONObject> setDataForAPI(int status, int tcID) {
        if (tcID != 0) {
            JSONObject myArray = new JSONObject();
            myArray.put("status", status);
            myArray.put("id", tcID);
            if (status != 1) {
                myArray.put("exception", exception);
            } else {
                myArray.put("exception", "No Exception");
            }
            if (statusIdList == null) {
                statusIdList = new ArrayList<>();
                statusIdList.add(myArray);
                return statusIdList;
            }
            statusIdList.add(myArray);
        }
        return statusIdList;
    }

    public List<JSONObject> getDataForAPI() {
        return statusIdList;
    }

    public ArrayList<Integer> setIDList(int tc) {
        if (tc != 0) {
            if (idList == null) {
                idList = new ArrayList<>();
            }
            idList.add(tc);
        }
        return idList;
    }

    public ArrayList<Integer> getIDList() {
        return idList;
    }

    /*** It will set data  for test rail for body    */

    public List<JSONObject> setDataForAPIAuto(int status, String className, String tcData, ITestResult result) {
        JSONObject myArray = new JSONObject();
        myArray.put("class_name", className);
        myArray.put("status", status);
        myArray.put("name", tcData);
        if (result != null) {
            if (status != 1) {
                myArray.put("exception", result.getThrowable().getMessage());
            } else {
                myArray.put("exception", "No Exception");
            }
        }
        if (statusNameList == null) {
            statusNameList = new ArrayList<>();
            statusNameList.add(myArray);
            return statusNameList;
        }
        statusNameList.add(myArray);
        return statusNameList;
    }

    public List<JSONObject> getDataForAPIAuto() {
        return statusNameList;
    }
}
