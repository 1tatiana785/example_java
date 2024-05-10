package core.coreUI.retryMethods;

import org.testng.*;
import org.testng.annotations.ITestAnnotation;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import static core.testRail.DataTestRail.runId;
import static core.testRail.DataTestRail.testCasesIds;


public class RetryListenerClass extends RetryFailedTestCases implements IAnnotationTransformer, IInvokedMethodListener {

    @Override
    public void transform(ITestAnnotation testAnnotation, Class testClass, Constructor testConstructor, Method testMethod) {
        IRetryAnalyzer retry = testAnnotation.getRetryAnalyzer();
        if (retry == null) {
            testAnnotation.setRetryAnalyzer(RetryFailedTestCases.class);
        }
        if (runId == 0)
            return;
        if (testCasesIds.get(0).equals("0")) {
            return;
        }
        testAnnotation.setEnabled(
                testCasesIds.stream().anyMatch(str -> str.equals(getCaseId(testMethod, testClass))));
    }

    @Override
    public String getCaseId(Method method, Class testClass) {
        return super.getCaseId(method, testClass);
    }

    @Override
    public void beforeInvocation(IInvokedMethod iInvokedMethod, ITestResult iTestResult) {
    }

    @Override
    public void afterInvocation(IInvokedMethod iInvokedMethod, ITestResult iTestResult) {
        retry(iTestResult);
    }
}