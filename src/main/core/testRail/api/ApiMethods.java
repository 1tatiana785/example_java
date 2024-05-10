package core.testRail.api;

import org.json.JSONObject;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Map;

public class ApiMethods {

    /*** @param apiHeader parameter to header for GET request     */
    public String apiGet(JSONObject apiHeader) {
        Client client = ClientBuilder.newClient();
        WebTarget webResource = client.target(apiHeader.getString("url_name"));
        Response response = webResource.request()
                .header(
                        apiHeader.getString("header"),
                        apiHeader.getString("headerValue")
                )
                .header("Content-Type", "application/json")
                .get();
        response.bufferEntity();
        if (response.getStatus() > 299) {
            System.err.println("Status: " + response.getStatus());
            System.err.println("Body: " + response.readEntity(String.class));
            if (response.readEntity(String.class) != null)
                throw new RuntimeException(response.readEntity(String.class));
        }
        return response.readEntity(String.class);
    }

    /*** @param apiHeader    parameter to header for POST request
     * @param apiParameter parameter for POST request     */
    public String apiPost(JSONObject apiHeader, JSONObject apiParameter) {
        Map test = null;
        if (apiParameter != null) {
            test = apiParameter.toMap();
        }
        Client client = ClientBuilder.newClient();
        WebTarget webResource = client.target(apiHeader.getString("url_name"));
        Response response = webResource.request()
                .accept(MediaType.APPLICATION_JSON)
                .header(
                        apiHeader.getString("header"),
                        apiHeader.getString("headerValue")
                )
                .accept(MediaType.APPLICATION_JSON)
                .post(Entity.entity(test, MediaType.APPLICATION_JSON));
        response.bufferEntity();
        return response.readEntity(String.class);
    }
}