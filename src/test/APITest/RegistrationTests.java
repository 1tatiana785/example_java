package APITest;

import API.APIDataMethods;
import core.coreAPI.API.APIMethods;
import core.coreAPI.listnener.APIListener;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import javax.ws.rs.core.Response;

import static org.assertj.core.api.Assertions.assertThat;

import core.coreUI.AllListeners.TestMethodCapture;

@Listeners({APIListener.class, TestMethodCapture.class})
public class RegistrationTests {

    @Test
    public void apiRegistrationTest() {
        APIMethods api = new APIMethods();
        Response response = api.post(api.DataHeaders("api/test/register"), APIDataMethods.registration());
        assertThat(response.getStatus())
                .as("Is api/test/register succeed")
                .isEqualTo(200);
        assertThat(response.readEntity(String.class))
                .contains("\"success\":true");
    }

    @Test
    public void apiRegistrationGetTest() {
        APIMethods api = new APIMethods();
        Response response = api.get(api.DataHeaders("register"));
        assertThat(response.getStatus())
                .as("Is register succeed")
                .isEqualTo(200);
        assertThat(response.readEntity(String.class))
                .isNotNull();
    }
}