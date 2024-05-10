package core.coreAPI.API;

import static core.helper.Base.BASE_URL;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import org.json.JSONObject;

import core.coreAPI.API.JSON.JSONObjectAPI;
import core.reports.extentReport_4.ExtentTestManager;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;

public class APIMethods {

    private static Cookie cookie;
    private final String url = BASE_URL;

    public APIMethods() {
        cookie = getCookie();
    }

    public JSONObjectAPI DataHeaders(String endpoint) {
        JSONObjectAPI apiHeaders = new JSONObjectAPI();
        apiHeaders.putAccept(MediaType.MULTIPART_FORM_DATA);
        apiHeaders.putRequest(MediaType.APPLICATION_JSON);
        apiHeaders.putEndpoint(endpoint);
        apiHeaders.put("Content-Type", "multipart/form-data");
        return apiHeaders;
    }

    /*** Create Response without methods
     * @param endpoint api endpoint
     * @param request  String request
     * @param accept   String accept
     * @param headers  headers of api
     * @return Invocation.Builder
     */

    private Invocation.Builder getResponse(String endpoint, String request, String accept, MultivaluedMap<String, Object> headers) {
        Client client = ClientBuilder.newClient();
        return client
                .target(url).path(endpoint)
                .request(request)
                .headers(headers)
                .accept(accept);
    }

    private Cookie getCookie() {
        try {
            OkHttpClient client = new OkHttpClient().newBuilder()
                    .build();
            RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
                    .addFormDataPart("password", "test")
                    .addFormDataPart("email", "test@gmail.com")
                    .build();
            okhttp3.Request request = new okhttp3.Request.Builder()
                    .url(url + "login")
                    .method("POST", body)
                    .addHeader("Content-Type", "application/x-www-form-urlencoded")
                    .build();
            okhttp3.Response response = client.newCall(request).execute();
            assert response.priorResponse() != null;
            List<String> trim4 = Arrays.asList(response.priorResponse().priorResponse()
                    != null ?
                    response.priorResponse().priorResponse().headers().toMultimap().get("set-cookie").get(0).split("=")
                    : new String[0]);
            return new Cookie(trim4.get(0), trim4.get(1), "path=/", "preprod...");
        } catch (IOException ex) {
            System.out.println("Exception " + ex.getMessage());
        }
        return null;
    }

    private Invocation.Builder getResponseREST(JSONObjectAPI headers) {
        Client client = ClientBuilder.newClient();
        if (headers.has("headers")) {
            return client
                    .target(url)
                    .path(headers.getEndpoint())
                    .request(headers.getRequest())
                    .headers(headers.getHeadersMultiMap());
        } else
            return client
                    .target(url)
                    .path(headers.getEndpoint())
                    .request(headers.getRequest());
    }

    private Invocation.Builder getResponse(JSONObjectAPI headers) {
        Client client = ClientBuilder.newClient();
        if (headers.has("headers")) {
            return client
                    .target(url)
                    .path(headers.getEndpoint())
                    .request(headers.getRequest())
                    .headers(headers.getHeadersMultiMap())
                    .header("X-Requested-With", "XMLHttpRequest")
                    .cookie(cookie);
        } else
            return client
                    .target(url)
                    .path(headers.getEndpoint())
                    .request(headers.getRequest())
                    .header("X-Requested-With", "XMLHttpRequest")
                    .cookie(cookie);
    }

    public Response get(JSONObjectAPI headers) {
        Response response = getResponse(headers)
                .get();
        return getResponseLog(response);
    }

    public Response getRestAPI(JSONObjectAPI headers) {
        Response response = getResponseREST(headers)
                .get();
        return getResponseLog(response);
    }

    private Response getAccessToken() {
        Form data = new Form();
        data.param("grant_type", "client_credentials");
        data.param("client_id", "92afd312b2d4714fe95c68eb33f678fd");
        data.param("client_secret", "de855313fb8fb15dd49dc510a32db848");
        JSONObjectAPI headers = DataHeaders("v1/oauth/access_token");
        return getResponseREST(headers)
                .post(Entity.entity(data, MediaType.APPLICATION_FORM_URLENCODED));
    }

    public Response postRestAPI(JSONObjectAPI headers, Form data) {
        Response response = getResponseREST(headers)
                .post(Entity.entity(data, MediaType.APPLICATION_FORM_URLENCODED));
        return getResponseLog(response);
    }

    public Response post(JSONObjectAPI headers, Form data) {
        Response response = getResponse(headers)
                .post(Entity.entity(data, MediaType.APPLICATION_FORM_URLENCODED));
        return getResponseLog(response);
    }

    public Response postWithoutCookie(JSONObjectAPI headers, Form data, String url) {
        Response response = ClientBuilder.newClient().target(url)
                .path(headers.getEndpoint())
                .request(headers.getRequest())
                .header("X-Requested-With", "XMLHttpRequest")
                .post(Entity.entity(data, MediaType.APPLICATION_FORM_URLENCODED));
        return getResponseLog(response);
    }

    private Response getResponseLog(Response response) {
        response.bufferEntity();
        ExtentTestManager.getInstance().getLogger().info("Response Status: " + response.getStatus());
        try {
            if (!response.readEntity(String.class).contains("<html>")) {
                ExtentTestManager.getInstance().getLogger().info("Response Entity: " + response.readEntity(String.class));
            }
        } catch (Exception ex) {
            System.out.println("Exception " + ex.getMessage());
        }
        return response;
    }

    /*** @param endpoint Endpoint
     * @param headers  MultivaluedMap<String,Object> headers
     * @param data     data  for post methods
     * @return Response
     */

    public Response put(String endpoint, String request, String accept, MultivaluedMap<String, Object> headers, JSONObject data) {
        Response response = getResponse(endpoint, request, accept, headers)
                .put(Entity.entity(data, MediaType.APPLICATION_JSON));
        return getResponseLog(response);
    }

    public Response get(String endpoint, String request, String accept, MultivaluedMap<String, Object> headers) {
        Response response = getResponse(endpoint, request, accept, headers).get();
        return getResponseLog(response);
    }
}