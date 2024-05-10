package core.coreUI.ui.seleniumElement;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

public class JSExecutor extends Element {
    ElementProperties element = new ElementProperties();

    public void JSClick(By locator) {
        WebElement element = waitUntilExist(locator);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", element);
    }

    public void JSClick(WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", element);
    }

    public void JSClickStr(String elementLocation, String text) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", element.findElementByXpath(elementLocation, text));
    }

    public void JSMove(By locator) {
        WebElement myElement = waitUntilExist(locator);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView();", myElement);
    }

    public void JSMove(WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView();", element);
    }

    public void JSScrollTo() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollTo(0, 250)");
    }

    public void JSScrollDown() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollTo(0, 250)");
    }

    public boolean isElementSelected(String cssLocator) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        return (boolean) js.executeScript("return document.querySelectorAll(" + cssLocator + ":checked).length>0");
    }

    public boolean isElementSelected(WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        return (boolean) js.executeScript("return arguments[0]:checked.length > 0;", element);
    }

    public void scrollToNecessaryElement(WebElement element) {
        int y = element.getLocation().getY();
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollTo(0," + y + ")");
    }

    public void scrollToNecessaryElement(By locator) {
        WebElement element = waitUntilClickable(locator);
        int y = element.getLocation().getY();
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollTo(0," + y + ")");
    }

    public String getInputValue(By locator, String field) {
        waitUntilVisible(locator);
        try {
            String value;
            JavascriptExecutor js = (JavascriptExecutor) driver;
            value = js.executeScript("return document.querySelector('" + field + "').value;").toString();
            return value;
        } catch (Exception e) {
            return null;
        }
    }
}