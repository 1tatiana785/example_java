package core.coreUI.ui.seleniumElement;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class Windows extends Element {

    public void navigate(String url) {
        driver.navigate().to(url);
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    public void fullScreen() {
        driver.manage().window().setSize(new Dimension(1280, 1024));
        driver.findElement(By.cssSelector("body")).sendKeys(Keys.F11);
    }

    public void maximize() {
        driver.manage().window().maximize();
    }

    public WebDriver.Timeouts implicitlyWait(long time, TimeUnit unit) {
        return driver.manage().timeouts().implicitlyWait(time, unit);
    }

    public void switchBetweenTabs(int tabIndex) {
        String parentHandle = new ArrayList<>(driver.getWindowHandles()).get(0);
        String anyTabName = new ArrayList<>(driver.getWindowHandles()).get(tabIndex);
        driver.switchTo().window(anyTabName);
        System.setProperty("current.window.handle", parentHandle);
    }

    public void switchToLastWindows() {
        String anyTabName = new ArrayList<>(driver.getWindowHandles()).get(1);
        driver.switchTo().window(anyTabName);
    }

    public int checkPopUpIsClosed() {
        return driver.getWindowHandles().size();
    }

    public void closeChild() {
        selectChild();
        driver.close();
    }

    public void selectChild() {
        String parent = driver.getWindowHandle();
        for (String childHandle : driver.getWindowHandles()) {
            if (!childHandle.equals(parent)) {
                driver.switchTo().window(childHandle);
            }
        }
    }

    public void alertDismiss() {
        driver.switchTo().alert().dismiss();
    }

    public void alertAccept() {
        driver.switchTo().alert().accept();
    }

    public void switchToFrame(By locator) {
        waitElement().until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(locator));
    }

    public void switchToFrame(WebElement element) {
        waitElement().until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(element));
    }

    public void switchToFrame(String element) {
        waitElement().until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(element));
    }

    public void refreshPage() {
        driver.navigate().refresh();
    }

    public void quit() {
        driver.quit();
    }

    public String alertGetText() {
        return driver.switchTo().alert().getText();
    }
}