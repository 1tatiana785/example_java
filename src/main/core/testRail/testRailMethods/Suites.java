package core.testRail.testRailMethods;

import core.testRail.api.ApiMethods;
import core.testRail.api.CryptoToken;
import org.json.JSONObject;

public class Suites {

    final ApiMethods api = new ApiMethods();
    final CryptoToken token = new CryptoToken();

    /*** get_suite
     * Returns an existing test suite.
     * <p>
     * GET index.php?/api/v2/get_suite/:suite_id	 */

    public JSONObject getSuite(int suite_id) {
        return new JSONObject(api.apiGet(token.getHeader("/get_suite/" + suite_id)));
    }

    /*** get_suites
     * Returns a list of test suites for a project.
     * GET index.php?/api/v2/get_suites/:project_id	 */
    public JSONObject getSuites(int project_id) {
        return new JSONObject(api.apiGet(token.getHeader("/get_suites/" + project_id)));
    }

    /*** add_suite
     * Creates a new test suite.
     ** POST index.php?/api/v2/add_suite/:project_id  */

    public JSONObject addSuites(int project_id, JSONObject object) {
        return new JSONObject(api.apiPost(token.getHeader("/add_suite/" + project_id), object));
    }

    /*** delete_suite
     * Deletes an existing test suite.
     * <p>
     * POST index.php?/api/v2/delete_suite/:suite_id 	 */
    public JSONObject deleteSuite(int project_id) {
        return new JSONObject(api.apiPost(token.getHeader("/delete_suite/" + project_id), null));
    }
}