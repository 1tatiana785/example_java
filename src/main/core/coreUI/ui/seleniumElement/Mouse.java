package core.coreUI.ui.seleniumElement;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptException;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.MoveTargetOutOfBoundsException;

public class Mouse extends Element {

    Actions action = new Actions(driver);
    ElementProperties element = new ElementProperties();

    public void moveMouseToAndClick(By locator, int x, int y) {
        WebElement element = waitUntilVisible(locator);
        action.moveToElement(element, x, y).click().build().perform();
    }

    public void moveMouseToAndClick(By locator) {
        WebElement element = waitUntilVisible(locator);
        try {
            action.moveToElement(element).click(element).build().perform();
        } catch (MoveTargetOutOfBoundsException ex) {
            System.out.println("Exception " + ex.getMessage());
        }
    }

    public void moveMouseToAndClick(String elementLocation, String number) {
        action.moveToElement(element.findElementByXpath(elementLocation, number)).build().perform();
        waitUntilClickable(By.xpath(String.format(elementLocation, number))).click();
    }

    public void useKeyboard(Keys keys) {
        action.sendKeys(keys).build().perform();
    }

    public void moveMouseTo(String elementLocation, String number) {
        action.moveToElement(element.findElementByXpath(elementLocation, number)).build().perform();
    }

    public void moveMouseTo(WebElement element) {
        try {
            action.moveToElement(element).perform();
        } catch (JavascriptException | MoveTargetOutOfBoundsException e) {
            e.getMessage();
        }
    }

    public void moveMouseTo(By locator) {
        try {
            WebElement element = waitUntilVisible(locator);
            action.moveToElement(element).perform();
        } catch (MoveTargetOutOfBoundsException ex) {
            System.out.println("Exception " + ex.getMessage());
        }
    }

    public void dragAndDropBy(By locator, int x, int y) {
        WebElement element = waitUntilVisible(locator);
        action.dragAndDropBy(element, -x, y).pause(100).build().perform();
    }

    public void dragAndDrop(By from, By to) {
        WebElement elementFrom = waitUntilVisible(from);
        WebElement elementTo = waitUntilVisible(to);
        action.dragAndDrop(elementFrom, elementTo).pause(100).build().perform();
    }

    public void mouseClickFromTo(By locator, By locatorTwo) {
        WebElement element = waitUntilVisible(locator);
        WebElement elementTwo = waitUntilVisible(locatorTwo);
        action.click(element).click(elementTwo).build().perform();
    }

    public void mouseClickFromTo(WebElement element, WebElement elementTwo) {
        waitUntilVisible(element);
        action.click(element).click(elementTwo).build().perform();
    }

    public void mouseClick(WebElement element) {
        waitUntilVisible(element);
        waitUntilClickable(element);
        action.click(element).build().perform();
    }

    public void mouseClick(By locator) {
        WebElement element = waitUntilVisible(locator);
        waitUntilClickable(element);
        mouseClick(element);
    }

    public void mouseHoverAndClick(WebElement element) {
        action.clickAndHold(element).build().perform();
    }

    public void moveDown() {
        Actions actions = new Actions(driver);
        int i = 0;
        while (i < 5) {
            actions.sendKeys(Keys.PAGE_DOWN).build().perform();
            i++;
        }
    }

    public void mouseHoverAndClick(By locator) {
        mouseHoverAndClick(waitUntilVisible(locator));
    }

    public void mouseDoubleClick(WebElement element) {
        action.doubleClick(element).build().perform();
    }

    public void mouseDoubleClickStr(String elementLocation, String text) {
        action.doubleClick(element.findElementByXpath(elementLocation, text)).build().perform();
    }

    public void mouseDoubleClick(By locator) {
        mouseDoubleClick(waitUntilVisible(locator));
    }

    public void mouseClickAndHold(By locator) {
        mouseClickAndHold(waitUntilVisible(locator));
    }

    public void mouseClickAndHold(WebElement element) {
        try {
            action.clickAndHold(element).perform();
        } catch (MoveTargetOutOfBoundsException ex) {
            ex.getMessage();
        }
    }

    public void mouseDragAndDrop(WebElement elementFrom, WebElement elementTo) {
        Mouse builder = new Mouse();
        builder.mouseDragAndDrop(elementFrom, elementTo);
    }

    public void dragAndDrop(By locator, int x, int y) {
        WebElement element = waitUntilVisible(locator);
        action.dragAndDropBy(element, x, y).perform();

    }
}