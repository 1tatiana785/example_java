package core.coreUI.ui.seleniumElement;

import static core.helper.Base.DELAY;
import static core.helper.Base.TIME_OUT;
import static org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable;

import java.time.Duration;
import java.util.List;
import java.util.function.Function;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import core.coreUI.ui.initialDriver.InitialDriver;

abstract class Element extends InitialDriver {


    protected WebDriver driver = InitialDriver.getInstance().getDriver();

    protected void waitForProgressToDisappear(final WebElement progress) {
        final Function<WebDriver, Boolean> webDriverBooleanFunction = (WebDriver driver) ->
                ExpectedConditions.or(
                        ExpectedConditions.invisibilityOf(progress),
                        ExpectedConditions.attributeToBe(progress, "display", "none")
                ).apply(driver);
        this.waitElement(DELAY).ignoring(NoSuchElementException.class)
                .ignoring(StaleElementReferenceException.class).until(webDriverBooleanFunction);
    }

    /*** Status of element
     *  * Get status of element
     * @return getWebElement(ExpectedConditions.elementToBeClickable ( element));    */

    public WebElement getWebElement(ExpectedCondition<WebElement> webElementExpectedCondition) {
        return waitElement().until(webElementExpectedCondition);
    }

    /**
     * Status of element
     * Get status of element
     *
     * @param stateElementExpectedCondition WebElement element,By locator
     * @return getWebStateOfElement(ExpectedConditions.elementToBeSelected ( element));
     */

    public boolean getWebStateOfElement(ExpectedCondition<Boolean> stateElementExpectedCondition) {
        return waitElement().ignoring(NoSuchElementException.class)
                .ignoring(StaleElementReferenceException.class)
                .ignoring(TimeoutException.class)
                .until(stateElementExpectedCondition);
    }

    /*** It webDriver wait for all element     ** @return WebDriverWait     */

    protected WebDriverWait waitElement() {
        WebDriverWait wait = new WebDriverWait(driver, TIME_OUT);
        wait.pollingEvery(Duration.ofMillis(DELAY));
        wait.ignoring(NoSuchElementException.class);
        wait.ignoring(StaleElementReferenceException.class);
        return wait;
    }

    public WebDriverWait waitElement(int time) {
        WebDriverWait wait = new WebDriverWait(driver, time);
        wait.pollingEvery(Duration.ofMillis(DELAY));
        wait.ignoring(NoSuchElementException.class);
        wait.ignoring(StaleElementReferenceException.class);
        return wait;
    }

    protected WebElement
    waitUntilClickable(By locator) {
        return getWebElement(elementToBeClickable(locator));
    }

    protected WebElement waitUntilClickable(WebElement element) {
        return getWebElement(elementToBeClickable(element));
    }

    protected WebElement waitUntilVisible(WebElement element) {
        return getWebElement(ExpectedConditions.visibilityOf(element));
    }

    protected WebElement waitUntilVisible(By locator) {
        return getWebElement(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public WebElement waitUntilExist(By locator) {
        return getWebElement(ExpectedConditions.presenceOfElementLocated(locator));
    }

    protected boolean waitUntilInvisible(By locator) {
        return getWebStateOfElement(ExpectedConditions.invisibilityOfElementLocated(locator));
    }

    protected boolean waitUntilInvisible(WebElement element) {
        return getWebStateOfElement(ExpectedConditions.invisibilityOf(element));
    }

    protected boolean waitUntilTextPresent(By locator, String text) {
        return getWebStateOfElement(ExpectedConditions.textToBe(locator, text));
    }

    protected boolean waitUntilTextPresent(WebElement element, String text) {
        return getWebStateOfElement(ExpectedConditions.textToBePresentInElement(element, text));
    }

    public List<WebElement> selectFromList(By locator) {
        return driver.findElements(locator);
    }


    public WebElement findElementByXpath(By by) {
        waitUntilExist(by);
        return driver.findElement(by);
    }

    public WebElement findElementByXpath(String elementLocation, String number) {
        return driver.findElement(By.xpath(String.format(elementLocation, number)));
    }

    public WebElement findElement(String elementLocation, String number) {
        return driver.findElement(By.cssSelector(String.format(elementLocation, number)));
    }

    public WebElement findElement(By locator) {
        waitUntilExist(locator);
        return driver.findElement(locator);
    }
}