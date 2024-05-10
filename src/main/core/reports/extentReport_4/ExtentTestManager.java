package core.reports.extentReport_4;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;


public class ExtentTestManager {

    private static ExtentTestManager instance;
    private static ThreadLocal<ExtentTest> testThread = new ThreadLocal<>();
    public ExtentReports report;
    public ExtentTest logger;

    private ExtentTestManager() {
    }

    public static synchronized ExtentTestManager getInstance() {
        if (instance == null) {
            instance = new ExtentTestManager();
        }
        return instance;
    }

    public synchronized ExtentReports initialReport(String name) {
        if (report == null) {
            report = ExtentManager.getReporter(name);
            return report;
        } else {
            return report;
        }
    }

    public ExtentTest getLogger() {
        return testThread.get();
    }

    public ExtentTest createTest(String testName) {
        logger = report.createTest(testName);
        if (testThread.get() == null || !testThread.get().getModel().getName().equals(testName)) {
            testThread.set(logger);
        }
        return logger;
    }
}