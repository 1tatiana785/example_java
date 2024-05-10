package core.reports.extentReport_4;

import com.aventstack.extentreports.*;
import com.aventstack.extentreports.reporter.*;
import com.aventstack.extentreports.reporter.configuration.*;

import java.io.*;

public class ExtentManager {
    private static ExtentHtmlReporter htmlReporter;

    private static ExtentReports extent;


    public synchronized static ExtentReports getReporter(String name) {
        String path = System.getProperty("user.dir") + File.separator + "test-output" + File.separator + "" + name + ".html";
        if (extent == null) {
            extent = new ExtentReports();
        }
        htmlReporter = new ExtentHtmlReporter(path);
        extent.setSystemInfo("Environment", "Automation Testing");
        extent.setSystemInfo("User Name", "QA");
        extent.setAnalysisStrategy(AnalysisStrategy.TEST);
        htmlReporter.config().setProtocol(Protocol.HTTPS);
        htmlReporter.config().setDocumentTitle("SNOVIO: " + name);
        htmlReporter.config().setReportName("SNOVIO");
        htmlReporter.config().setTheme(Theme.DARK);
        htmlReporter.config().setEncoding("UTF-8");
        htmlReporter.config().setTimeStampFormat("EEEE, MMMM dd, yyyy, hh:mm a '('zzz')'");
        extent.getStats();
        extent.getStartedReporters();
        extent.attachReporter(htmlReporter);
        return extent;
    }
}
