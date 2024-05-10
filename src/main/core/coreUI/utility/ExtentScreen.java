package core.coreUI.utility;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestResult;

import com.aventstack.extentreports.MediaEntityBuilder;
import core.coreUI.ui.initialDriver.InitialDriver;
import core.reports.extentReport_4.ExtentTestManager;
import io.qameta.allure.Allure;

public class ExtentScreen extends InitialDriver {

    public void getScreenNewVersion(ITestResult arg0) {
        try {
            String base64 = ((TakesScreenshot) InitialDriver.getInstance().getDriver()).getScreenshotAs(OutputType.BASE64);
            ExtentTestManager.getInstance().getLogger().fail(arg0.getTestName(), MediaEntityBuilder.createScreenCaptureFromBase64String(base64).build());
            byte[] screenShot = ((TakesScreenshot) InitialDriver.getInstance().getDriver()).getScreenshotAs(OutputType.BYTES);
            Allure.getLifecycle().addAttachment(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MMM-yy_hh:mm:ss")), "image/png", "png", screenShot);
        } catch (Exception ex) {
            System.out.println("Exception " + ex.getMessage());
        }
    }
}