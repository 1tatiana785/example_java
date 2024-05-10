package core.helper;

import core.testRail.TestRailId;
import core.utils.properties.PropertiesRead;

import java.lang.reflect.Method;


public class Base {

    public static final int RETRIES = Integer.parseInt(new PropertiesRead().getPropValues("retries", "config/config.properties"));
    public static int DELAY = Integer.parseInt(new PropertiesRead().getPropValues("element.poling", "config/config.properties"));
    public static int EXIST = Integer.parseInt(new PropertiesRead().getPropValues("element.exist", "config/config.properties"));

    /*** UI DRIVER ***/
    public static final String DRIVER_NAME = new PropertiesRead().getPropValues("browser.driver", "config/config.properties");
    public static final String DRIVER_VERSION = new PropertiesRead().getPropValues("driver.version", "config/config.properties");
    public static final String DRIVER_PATH = new PropertiesRead().getPropValues("driver.path", "config/config.properties");

    /****  Test Rail Config  */
    public static final String userTestRail = new PropertiesRead().getPropValues("TR.user", "config/config.properties");
    public static final String passTestRail = new PropertiesRead().getPropValues("TR.pass", "config/config.properties");
    public static final String urlTestRail = new PropertiesRead().getPropValues("TR.url", "config/config.properties");

    private static final ThreadLocal<Base> testCaseThread = new ThreadLocal<>();
    public static String BASE_URL = getParameter();

    /*** SELENIUM CONFIG        ***/
    public static int TIME_OUT = Integer.parseInt(new PropertiesRead().getPropValues("element.wait", "config/config.properties"));
    public int TC;

    static String getParameter() {
        String TEST_ONE_URL = new PropertiesRead().getPropValues("url.testAPI", "config/config.properties");
        String TEST_URL = new PropertiesRead().getPropValues("url.test", "config/config.properties");
        String STAGE_URL = new PropertiesRead().getPropValues("url.stage", "config/config.properties");
        String PROD_URL = new PropertiesRead().getPropValues("url.prod", "config/config.properties");
        return select(TEST_ONE_URL, TEST_URL, STAGE_URL, PROD_URL);
    }

    private static String select(String APItest, String test, String stage, String prod) {
        String environment = new PropertiesRead().getPropValues("environment", "config/config.properties");
        switch (environment) {
            case "APItest":
                return APItest;
            case "test":
                return test;
            case "stage":
                return stage;
            case "prod":
                return prod;
        }
        return stage;
    }

    private static String loginName() {
        final String userPreProdLogin = new PropertiesRead().getPropValues("user.PreProd", "config/siteSettings.properties");
        final String userProdLogin = new PropertiesRead().getPropValues("user.Prod", "config/siteSettings.properties");
        final String userTestLogin = new PropertiesRead().getPropValues("user.Test", "config/siteSettings.properties");
        return select("", userTestLogin, userPreProdLogin, userProdLogin);
    }

    private static String password() {
        final String userTestPassword = new PropertiesRead().getPropValues("password.Test", "config/siteSettings.properties");
        final String userStagePassword = new PropertiesRead().getPropValues("password.PreProd", "config/siteSettings.properties");
        final String userProdPassword = new PropertiesRead().getPropValues("password.Prod", "config/siteSettings.properties");
        return select("", userTestPassword, userStagePassword, userProdPassword);
    }

    public static Base getInstance() {
        if (testCaseThread.get() == null) {
            synchronized (Base.class) {
                testCaseThread.set(new Base());
            }
        }
        return testCaseThread.get();
    }

    protected String getCaseId(Method method, Class testClass) {
        //In case annotation will be placed on test method
        if (method != null) {
            if (method.isAnnotationPresent(TestRailId.class)) {
                return String.valueOf(method.getAnnotation(TestRailId.class).TestCaseId());
            }
        }
        //In case annotation will be placed on test class
        if (testClass != null) {
            if (testClass.isAnnotationPresent(TestRailId.class)) {
                return String.valueOf(((TestRailId) testClass.getAnnotation(TestRailId.class)).TestCaseId());
            }
        }
        return "0";
    }
}