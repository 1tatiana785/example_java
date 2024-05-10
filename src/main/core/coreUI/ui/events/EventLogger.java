package core.coreUI.ui.events;

import com.aventstack.extentreports.Status;
import core.reports.extentReport_4.ExtentTestManager;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.WebDriverEventListener;

public class EventLogger implements WebDriverEventListener {

    @Override
    public void beforeAlertAccept(WebDriver webDriver) {
    }

    @Override
    public void afterAlertAccept(WebDriver webDriver) {
    }

    @Override
    public void afterAlertDismiss(WebDriver webDriver) {
    }

    @Override
    public void beforeAlertDismiss(WebDriver webDriver) {
    }

    @Override
    public void beforeNavigateTo(String s, WebDriver webDriver) {
        try {
            ExtentTestManager.getInstance().getLogger().createNode(s);
            ExtentTestManager.getInstance().getLogger().log(Status.INFO, s);
        } catch (NullPointerException ex) {
            ex.getMessage();
        }
    }

    @Override
    public void afterNavigateTo(String s, WebDriver webDriver) {
        System.out.println("Navigate to: " + s);
    }

    @Override
    public void beforeNavigateBack(WebDriver webDriver) {
    }

    @Override
    public void afterNavigateBack(WebDriver webDriver) {
    }

    @Override
    public void beforeNavigateForward(WebDriver webDriver) {
    }

    @Override
    public void afterNavigateForward(WebDriver webDriver) {
    }

    @Override
    public void beforeNavigateRefresh(WebDriver webDriver) {
    }

    @Override
    public void afterNavigateRefresh(WebDriver webDriver) {
    }

    @Override
    public void beforeFindBy(By by, WebElement webElement, WebDriver webDriver) {
    }

    @Override
    public void afterFindBy(By by, WebElement webElement, WebDriver webDriver) {
    }

    @Override
    public void beforeClickOn(WebElement webElement, WebDriver webDriver) {
    }

    @Override
    public void afterClickOn(WebElement webElement, WebDriver webDriver) {
        try {
            System.out.println("Click on element: " + webElement);
            ExtentTestManager.getInstance().getLogger().createNode("Click on element: " + webElement);
            ExtentTestManager.getInstance().getLogger().log(Status.INFO, "Click on element: " + webElement);
        } catch (NullPointerException ex) {
            //System.out.println("Exception: "+	ex.getMessage());
        }
    }

    @Override
    public void beforeChangeValueOf(WebElement webElement, WebDriver webDriver, CharSequence[] charSequences) {
        try {
            if (charSequences == null)
                return;
            System.out.println("Change value on : " + charSequences[0].toString() + " in element" + webElement);
            ExtentTestManager.getInstance().getLogger().log(Status.INFO, "Change value on : " + charSequences[0].toString() + " in element" + webElement);
            ExtentTestManager.getInstance().getLogger().createNode("Change value on : " + charSequences[0].toString() + " in element" + webElement);
        } catch (NullPointerException ex) {
        }
    }

    @Override
    public void afterChangeValueOf(WebElement webElement, WebDriver webDriver, CharSequence[] charSequences) {
    }

    @Override
    public void beforeScript(String s, WebDriver webDriver) {
    }

    @Override
    public void afterScript(String s, WebDriver webDriver) {
    }

    @Override
    public void beforeSwitchToWindow(String s, WebDriver webDriver) {
    }

    @Override
    public void afterSwitchToWindow(String s, WebDriver webDriver) {
    }

    @Override
    public void onException(Throwable throwable, WebDriver webDriver) {
    }

    @Override
    public <X> void beforeGetScreenshotAs(OutputType<X> outputType) {
    }

    @Override
    public <X> void afterGetScreenshotAs(OutputType<X> outputType, X x) {
    }

    @Override
    public void beforeGetText(WebElement webElement, WebDriver webDriver) {
    }

    @Override
    public void afterGetText(WebElement webElement, WebDriver webDriver, String s) {
    }
}
