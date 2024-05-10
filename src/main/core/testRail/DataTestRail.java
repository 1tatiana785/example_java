package core.testRail;

import core.utils.properties.PropertiesRead;
import org.apache.commons.lang3.ArrayUtils;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class DataTestRail {

    public static final int runId = runId();
    public static final boolean SEND_API_RESULT = sendAPIResult();
    public static final List<String> testCasesIds = testCasesIds();
    public static final boolean HEADLESS = headlessConfig();

    private static boolean headlessConfig() {
        String value = System.getProperty("headless");
        if (value == null)
            return Boolean.parseBoolean(new PropertiesRead().getPropValues("headless", "config/config.properties"));
        return Boolean.parseBoolean(value);
    }

    private static int runId() {
        String value = System.getProperty("runId");
        if (value == null)
            return Integer.parseInt(new PropertiesRead().getPropValues("RunId.default", "config/config.properties"));
        return Integer.parseInt(value);
    }

    private static boolean sendAPIResult() {
        String value = System.getProperty("sendApi");
        if (value == null)
            return Boolean.parseBoolean(new PropertiesRead().getPropValues("send.API.Result", "config/config.properties"));
        return Boolean.parseBoolean(value);
    }

    private static List<String> testCasesIds() {
        String value = System.getProperty("testCaseIds");
        if (value == null)
            return Collections.singletonList("0");
        String[] stringArray = value.split("_");
        if (stringArray[0].equals("_") || stringArray[0].length() == 0) {
            stringArray = ArrayUtils.remove(stringArray, 0);
        }
        return Arrays.asList(stringArray);
    }
}