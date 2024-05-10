package core.coreUI.ui.events;

import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.WebDriverEventListener;

import static io.qameta.allure.Allure.step;

public class EventHandler extends JSWaiter implements WebDriverEventListener {

    int beforeClickX, beforeClickY;

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
    public void beforeSwitchToWindow(String s, WebDriver webDriver) {
    }

    @Override
    public void afterSwitchToWindow(String s, WebDriver webDriver) {
    }

    @Override
    public void beforeNavigateRefresh(WebDriver webDriver) {
        waitUntilJSReady();
    }

    @Override
    public void afterNavigateRefresh(WebDriver webDriver) {
        waitJQueryAngular();
        waitUntilJSReady();
        waitForJQueryLoad();
    }

    @Override
    public void beforeChangeValueOf(WebElement webElement, WebDriver webDriver, CharSequence[] charSequences) {
        if (charSequences != null) {
            step("Send value: " + charSequences[0] + "To field: " + webElement.getAttribute("class"));
        }
        waitJQueryAngular();
        waitUntilJSReady();
        waitForJQueryLoad();
    }

    @Override
    public void afterChangeValueOf(WebElement webElement, WebDriver webDriver, CharSequence[] charSequences) {
        waitJQueryAngular();
        waitUntilJSReady();
        waitForJQueryLoad();
    }

    @Override
    public void beforeClickOn(WebElement arg0, WebDriver arg1) {
        step("Click on Element: " + arg0.getAttribute("class"));
        beforeClickX = arg0.getLocation().x;
        beforeClickY = arg0.getLocation().y;
        waitJQueryAngular();
        waitUntilJSReady();
        waitForJQueryLoad();
    }

    @Override
    public void afterClickOn(WebElement arg0, WebDriver arg1) {
    }

    @Override
    public void afterNavigateForward(WebDriver arg0) {
        waitJQueryAngular();
        waitForJQueryLoad();
    }

    @Override
    public void afterNavigateTo(String arg0, WebDriver arg1) {
        waitJQueryAngular();
        waitForJQueryLoad();
    }

    @Override
    public void beforeNavigateTo(String arg0, WebDriver arg1) {
    }

    @Override
    public void beforeFindBy(By arg0, WebElement arg1, WebDriver arg2) {
        waitJQueryAngular();
        waitUntilJSReady();
        waitForJQueryLoad();
    }

    @Override
    public void afterFindBy(By arg0, WebElement arg1, WebDriver arg2) {
    }

    @Override
    public void afterNavigateBack(WebDriver arg0) {
    }

    @Override
    public void beforeNavigateBack(WebDriver arg0) {
    }

    @Override
    public void beforeNavigateForward(WebDriver arg0) {
    }

    /***
     * Events for JS script
     * @param arg0 JS script
     * @param arg1 selenium driver
     */
    @Override
    public void afterScript(String arg0, WebDriver arg1) {
    }

    @Override
    public void beforeScript(String arg0, WebDriver arg1) {
    }

    @Override
    public void onException(Throwable arg0, WebDriver arg1) {
        System.out.println("Get exception: " + arg0.getMessage());
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