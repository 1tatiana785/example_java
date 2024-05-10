package core.testRail.testRailMethods;

import core.testRail.api.ApiMethods;
import core.testRail.api.CryptoToken;
import org.json.JSONArray;

public class Tests extends CryptoToken {

    private final ApiMethods api = new ApiMethods();
    private final CryptoToken token = new CryptoToken();

    public JSONArray getTests(int run_id) {
        return new JSONArray(api.apiGet(token.getHeader("/get_tests/" + run_id)));
    }
}
