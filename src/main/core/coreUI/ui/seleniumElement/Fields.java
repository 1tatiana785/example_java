package core.coreUI.ui.seleniumElement;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

public class Fields extends Element {

    /***************Get All Elements Settings*******************/

    public String getTxt(By locator) {
        return waitUntilVisible(locator).getText();
    }

    public String getTxt(WebElement element) {
        return waitUntilVisible(element).getText();
    }

    /*****************************/

    public void typeOnExist(By locator, String text) {
        waitUntilExist(locator).clear();
        waitUntilExist(locator).sendKeys(text);
    }

    public void typeWithoutClean(By locator, String text) {
        waitUntilClickable(locator).sendKeys(text);
    }

    public void type(By locator, String text) {
        waitUntilVisible(locator).clear();
        waitUntilVisible(locator).sendKeys(text);
    }

    public void type(WebElement element, String text) {
        waitUntilClickable(element).clear();
        waitUntilClickable(element).sendKeys(text);
    }

    public void type(By locator, Keys key) {
        waitUntilClickable(locator).sendKeys(key);
    }

    public void typeWithCheck(By locator, String text) {
        do {
            waitUntilVisible(locator).sendKeys(text);
            if (waitUntilVisible(locator).getAttribute("value").contains(text))
                break;
        }
        while (!waitUntilVisible(locator).getText().contains(text));
    }

    public String getValueFromField(By locator) {
        return waitUntilExist(locator).getAttribute("value");
    }

    public String getValueFromField(WebElement element) {
        return waitUntilClickable(element).getAttribute("value");
    }

    public void sendValue(By locator, String text) {
        waitUntilClickable(locator).sendKeys(text);
    }

    public void keysEnter(By elementLocation) {
        waitUntilClickable(elementLocation).sendKeys(Keys.ENTER);
    }

    public void keysCtrlV(By elementLocation) {
        waitUntilClickable(elementLocation).sendKeys(Keys.CONTROL + "v");
    }

    public void cleanField(WebElement element) {
        waitUntilClickable(element).clear();
    }

    public void cleanField(By locator) {
        waitUntilClickable(locator).clear();
    }

    public void cleanByBackSpace(By locator) {
        waitUntilClickable(locator).sendKeys(Keys.chord(Keys.CONTROL, "a"));
        waitUntilClickable(locator).sendKeys(Keys.DELETE);
    }

    public void sendKeys(Keys keys) {
        Actions builder = new Actions(driver);
        builder.sendKeys(keys).perform();
    }

    public void cleanByBackSpace(WebElement element) {
        for (int i = 0; i < 5; i++) {
            waitUntilClickable(element).sendKeys(Keys.BACK_SPACE);
        }
    }
}