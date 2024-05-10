package core.coreUI.retryMethods;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import core.helper.Base;


public class RetryFailedTestCases extends Base implements IRetryAnalyzer {

    private static final Map<String, Integer> retries = Collections.synchronizedMap(new HashMap<>());

    public boolean retry(ITestResult result) {
        if (result.getStatus() != ITestResult.FAILURE) {
            return false;
        }
        String key = result.getTestContext().getName() + "/" + result.getMethod().getMethodName();
        retries.putIfAbsent(key, 0);
        int curRetries = retries.get(key);
        if (curRetries < RETRIES) {
            retries.put(key, curRetries + 1);
            return true;
        }
        return false;

    }
}