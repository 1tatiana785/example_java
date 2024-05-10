package core.coreUI.AllListeners;

import core.helper.Base;
import core.coreUI.ui.initialDriver.InitialDriver;
import core.coreUI.utility.ExtentScreen;
import core.reports.extentReport_4.ExtentTestManager;
import core.testRail.ReadAnnotation;
import core.testRail.RetryListenerClass;
import core.testRail.testRailMethods.Results;
import core.testRail.testRailMethods.TestCases;
import org.testng.*;
import org.testng.annotations.Listeners;

import java.util.ArrayList;
import java.util.List;

import static core.testRail.DataTestRail.SEND_API_RESULT;
import static core.testRail.DataTestRail.runId;

@Listeners({TestMethodCapture.class, RetryListenerClass.class})
public class Listener extends InitialDriver implements ITestListener, ISuiteListener, IInvokedMethodListener {

    public static String getMethodName(ITestResult iTestResult) {
        return iTestResult.getMethod().getConstructorOrMethod().getName();
    }

    private void resultAdd(ITestResult arg0) {
        if (!SEND_API_RESULT)
            return;
        Base.getInstance().TC = new ReadAnnotation().readTC(arg0);
        List<String> textCases = new ArrayList<>();
        int[] value = new ReadAnnotation().readTCs(arg0);
        if (value != null) {
            for (int i = 0; i < value.length; i++) {
                int tc = new ReadAnnotation().readTCs(arg0)[i];
                new Results().addResultForCase(runId, tc, new Results().myData(arg0));
                new TestCases().updateCase(tc);
            }
        }
        new ReadAnnotation().readTCs(arg0);
        if (Base.getInstance().TC != 0) {
            System.out.println("TC: " + Base.getInstance().TC);
            new Results().addResultForCase(runId, Base.getInstance().TC, new Results().myData(arg0));
            new TestCases().updateCase(Base.getInstance().TC);
        }
    }

    @Override
    public void onStart(ISuite arg0) {
        ExtentTestManager.getInstance().initialReport(arg0.getName());
    }

    @Override
    public void onFinish(ISuite arg0) {
    }

    @Override
    public void onStart(ITestContext arg0) {
    }

    @Override
    public void onFinish(ITestContext arg0) {
    }

    @Override
    public void onTestSuccess(ITestResult arg0) {
        resultAdd(arg0);
        ExtentTestManager.getInstance().getLogger().getExtent().flush();
        System.out.println(arg0.getName() + " test is Pass");
    }

    @Override
    public void onTestFailure(ITestResult arg0) {
        System.out.println(arg0.getStatus() + " " + arg0.getTestName());
        ExtentTestManager.getInstance().getLogger().error(arg0.getThrowable().getMessage());
        new ExtentScreen().getScreenNewVersion(arg0);
        ExtentTestManager.getInstance().getLogger().fail(arg0.getThrowable());
        ExtentTestManager.getInstance().getLogger().getExtent().flush();
        resultAdd(arg0);
        System.out.println(arg0.getName() + " test is Fail");
    }

    @Override
    public void onTestStart(ITestResult arg0) {
        ExtentTestManager.getInstance().createTest(arg0.getName());
    }

    @Override
    public void onTestSkipped(ITestResult arg0) {
        ExtentTestManager.getInstance().getLogger().skip(arg0.getName());
        ExtentTestManager.getInstance().getLogger().getExtent().flush();
        resultAdd(arg0);
        System.out.println(arg0.getName() + " test is Skipped");
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult arg0) {
    }

    @Override
    public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {
    }

    @Override
    public void afterInvocation(IInvokedMethod method, ITestResult testResult) {
    }
}