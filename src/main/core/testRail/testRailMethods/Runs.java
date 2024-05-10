package core.testRail.testRailMethods;

import core.testRail.api.CryptoToken;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Runs extends CryptoToken {

    /*** @param projectId ID 1 =  ID=2 Automated
     * @return Arrays of all runs */
    private JSONArray getRuns(int projectId) {
        return new JSONArray(apiGet(getHeader("/get_runs/" + projectId)));
    }

    private String getRun(int runId) {
        return apiGet(getHeader("/get_run/" + runId));
    }

    private JSONObject addRun(int projectId, JSONObject bodyRun) {
        return new JSONObject(apiPost(getHeader("/add_run/" + projectId), bodyRun));
    }
    JSONObject updateRun(int runId, JSONObject bodyRun) {
        return new JSONObject(apiPost(getHeader("/update_run/" + runId), bodyRun));
    }

    private String deleteRun(int runId, JSONObject bodyRun) {
        try {
            return (apiPost(getHeader("/delete_run/" + runId), bodyRun));
        } catch (JSONException ex) {
            return null;
        }
    }
}