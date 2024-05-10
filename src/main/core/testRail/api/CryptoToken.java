package core.testRail.api;

import core.helper.Base;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import static core.helper.Base.*;

public class CryptoToken extends ApiMethods {

    private static String getAuthorization() {
        return new String(Base64.getEncoder().encode((Base.userTestRail + ":" + Base.passTestRail).getBytes(StandardCharsets.UTF_8)));
    }

    public JSONObject getHeader(String myUrl) {
        JSONObject header = new JSONObject();
        header.put("url_name", urlTestRail + myUrl);
        header.put("header", "Authorization");
        header.put("headerValue", "Basic " + getAuthorization());
        return header;
    }
}