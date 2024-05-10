package core.coreAPI.listnener;

import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.annotations.Listeners;

import core.coreUI.AllListeners.TestMethodCapture;
import core.reports.extentReport_4.ExtentTestManager;
import core.testRail.ReadAnnotation;
import core.testRail.testRailMethods.Results;
import core.testRail.testRailMethods.TestCases;

import static core.testRail.DataTestRail.runId;


@Listeners({TestMethodCapture.class})
public class APIListener implements ITestListener, ISuiteListener, IInvokedMethodListener {

    private void resultAdd(ITestResult arg0) {
        if (arg0.getStatus() == 1)
            new TestCases().updateCase(new ReadAnnotation().readTC(arg0));
        if (new ReadAnnotation().readTCs(arg0) != null) {
            for (int tc : new ReadAnnotation().readTCs(arg0)) {
                new Results().addResultForCase(runId, tc, new Results().myData(arg0));
            }
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
    public void onTestStart(ITestResult arg0) {
        ExtentTestManager.getInstance().createTest(arg0.getName());
    }

    @Override
    public void onTestFailure(ITestResult arg0) {
        resultAdd(arg0);
        ExtentTestManager.getInstance().getLogger().fail(arg0.getThrowable());
        ExtentTestManager.getInstance().initialReport(arg0.getName()).flush();
    }

    @Override
    public void onTestSkipped(ITestResult arg0) {
        resultAdd(arg0);
        ExtentTestManager.getInstance().getLogger().skip(arg0.getThrowable());
        ExtentTestManager.getInstance().initialReport(arg0.getName()).flush();
    }

    @Override
    public void onTestSuccess(ITestResult arg0) {
        resultAdd(arg0);
        ExtentTestManager.getInstance().getLogger().pass(arg0.getName());
        ExtentTestManager.getInstance().initialReport(arg0.getName()).flush();
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult arg0) {
    }

    @Override
    public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {
    }

    @Override
    public void afterInvocation(IInvokedMethod method, ITestResult testResult) {
        ExtentTestManager.getInstance().initialReport(testResult.getName()).flush();
        System.out.println("Name: " + testResult.getName());
    }
}