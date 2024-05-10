package core.coreUI.ui.events;

import core.coreUI.ui.initialDriver.InitialDriver;
import org.openqa.selenium.JavascriptException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import static core.helper.Base.EXIST;

abstract class JSWaiter extends InitialDriver {


    private final WebDriver driver = InitialDriver.getInstance().getDriver();

    private final WebDriverWait jsWait = new WebDriverWait(driver, EXIST);
    private final JavascriptExecutor jsExec = (JavascriptExecutor) driver;

    void waitForJQueryLoad() {
        try {
            ExpectedCondition<Boolean> jQueryLoad = driver -> {
                assert driver != null;
                return ((Long) ((JavascriptExecutor) driver)
                        .executeScript("return jQuery.active") == 0);
            };
            JavascriptExecutor js = (JavascriptExecutor) driver;
            boolean jqueryReady = (Boolean) js.executeScript("return !window.jQuery");
            if (jqueryReady) {
                jsWait.until(jQueryLoad);
            }
        } catch (JavascriptException ex) {
        }
    }

    //Wait for Angular Load
    private void waitForAngularLoad() {
        String angularReadyScript = "return window.angular.element('body').injector().get('$http').pendingRequests.length === 0";
        ExpectedCondition<Boolean> angularLoad = driver -> {
            assert driver != null;
            return Boolean.valueOf(((JavascriptExecutor) driver)
                    .executeScript(angularReadyScript).toString());
        };
        boolean angularReady = Boolean.parseBoolean(jsExec.executeScript(angularReadyScript).toString());
        if (!angularReady) {
            jsWait.until(angularLoad);
        }
    }

    //Wait Until JS Ready
    void waitUntilJSReady() {
        try {
            ExpectedCondition<Boolean> jsLoad = driver -> {
                assert driver != null;
                return ((JavascriptExecutor) driver)
                        .executeScript("return document.readyState").toString().equals("complete");
            };
            boolean jsReady = jsExec.executeScript("return document.readyState").toString().equals("complete");
            if (!jsReady) {
                jsWait.until(jsLoad);
            }
        } catch (TimeoutException ex) {
        }
    }

    //Wait Until JQuery and JS Ready
    private void waitUntilJQueryReady() {
        Boolean jQueryDefined = (Boolean) jsExec.executeScript("return typeof jQuery != 'undefined'");
        if (jQueryDefined) {
            waitForJQueryLoad();
            waitUntilJSReady();
        }
    }

    //Wait Until Angular and JS Ready
    private void waitUntilAngularReady() {
        Boolean angularUnDefined = (Boolean) jsExec.executeScript("return window.angular === undefined");
        if (!angularUnDefined) {
            Boolean angularInjectorUnDefined = (Boolean) jsExec.executeScript("return angular.element(document).injector() === undefined");
            if (!angularInjectorUnDefined) {
                waitForAngularLoad();
                waitUntilJSReady();
            }
        }
    }

    //Wait Until JQuery Angular and JS is ready
    void waitJQueryAngular() {
        try {
            waitUntilJQueryReady();
            waitUntilAngularReady();
        } catch (Exception ex) {
        }
    }
}