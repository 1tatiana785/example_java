package API;

import javax.ws.rs.core.Form;

import core.coreUI.utility.AllUtilsMethods;

public class APIDataMethods {

    public static Form registration() {
        AllUtilsMethods allUtilsMethods = new AllUtilsMethods();
        Form form = new Form();
        form.param("email", allUtilsMethods.generateRandomMail(5));
        form.param("password", "123456");
        form.param("name", "Registration Date" + AllUtilsMethods.currentDate());
        form.param("token", "5cd3f9d8bbbb180344770b24cb91406c");
        return form;
    }
}
