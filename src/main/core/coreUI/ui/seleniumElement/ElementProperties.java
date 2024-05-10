package core.coreUI.ui.seleniumElement;

import core.coreUI.ui.initialDriver.InitialDriver;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.time.Duration;
import java.util.*;

import static core.helper.Base.*;

public class ElementProperties extends Element {

    public static void sleep() {
        try {
            Thread.sleep(DELAY);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public WebElement expandRootElement(By locator) {
        WebElement ele = (WebElement) ((JavascriptExecutor) driver)
                .executeScript("return arguments[0].shadowRoot", waitUntilExist(locator));
        return ele;
    }

    public WebElement expandRootElement(WebElement element) {
        WebElement ele = (WebElement) ((JavascriptExecutor) driver)
                .executeScript("return arguments[0].shadowRoot", element);
        return ele;
    }

    private WebElement getWebElementExist(ExpectedCondition<WebElement> webElementExpectedCondition) {
        WebDriverWait wait = new WebDriverWait(driver, EXIST);
        wait.pollingEvery(Duration.ofMillis(DELAY));
        wait.ignoring(StaleElementReferenceException.class);
        wait.ignoring(NoSuchElementException.class);
        wait.until(webElementExpectedCondition);
        return wait.until(webElementExpectedCondition);
    }

    public String getAttribute(By locator, String attribute) {
        return waitUntilVisible(locator).getAttribute(attribute);
    }

    public String getAttributeValue(By locator) {
        return waitUntilClickable(locator).getAttribute("value");
    }

    public boolean getAttributeDisabled(By locator) {
        return Boolean.parseBoolean(waitUntilExist(locator).getAttribute("disabled"));
    }

    public boolean isPresent(By locator) {
        try {
            return getWebElementExist(ExpectedConditions.presenceOfElementLocated(locator)) != null;
        } catch (TimeoutException ex) {
            return false;
        }
    }

    public boolean isSelected(By locator) {
        return getWebElementExist(ExpectedConditions.presenceOfElementLocated(locator)).isSelected();
    }

    public boolean isDisplayed(By locator) {
        try {
            return getWebElementExist(ExpectedConditions.visibilityOfElementLocated(locator)) != null;
        } catch (Exception ex) {
            return false;
        }
    }

    public boolean isDisplayed(WebElement element) {
        try {
            return getWebElementExist(ExpectedConditions.visibilityOf(element)) != null;
        } catch (Exception ex) {
            return false;
        }
    }

    public int sizeWidth(By locator) {
        return waitUntilVisible(locator).getSize().width;
    }

    public int sizeHeight(By locator) {
        return waitUntilVisible(locator).getSize().height;
    }

    public boolean isEnabled(WebElement element) {
        return getWebElementExist(ExpectedConditions.elementToBeClickable(element)) != null;
    }

    public boolean isEnabled(By locator) {
        return getWebElementExist(ExpectedConditions.elementToBeClickable(locator)) != null;
    }

    public boolean isClickable(By locator) {
        return waitUntilVisible(locator).isEnabled();
    }

    public boolean isClickable(WebElement element) {
        return getWebElement(ExpectedConditions.elementToBeClickable(element)).isEnabled();
    }

    public void waitClickable(By locator, int time) {
        try {
            waitElement(time).until(ExpectedConditions.elementToBeClickable(locator));
        } catch (NoSuchElementException | TimeoutException | StaleElementReferenceException ex) {
            ex.getMessage();
        }
    }

    public void waitVisibility(By locator) {
        try {
            waitElement(EXIST).until(ExpectedConditions.visibilityOfElementLocated(locator));
        } catch (NoSuchElementException | TimeoutException ex) {
            ex.getMessage();
        }
    }

    public void waitVisibility(By locator, String name) {
        try {
            waitElement(EXIST).until(ExpectedConditions.invisibilityOfElementWithText(locator, name));
        } catch (NoSuchElementException | TimeoutException ex) {
            ex.getMessage();
        }
    }

    public void waitVisibility(WebElement locator) {
        try {
            waitElement(EXIST).until(ExpectedConditions.visibilityOf(locator));
        } catch (NoSuchElementException | TimeoutException ex) {
            ex.getMessage();
        }
    }

    public void waitVisibility(By locator, int time) {
        try {
            waitElement(time).until(ExpectedConditions.visibilityOfElementLocated(locator));
        } catch (NoSuchElementException | TimeoutException ex) {
            ex.getMessage();
        }
    }

    public void waitVisibility(WebElement element, int time) {
        try {

            waitElement(time).until(ExpectedConditions.visibilityOf(element));
        } catch (NoSuchElementException | TimeoutException ex) {
            ex.getMessage();
        }
    }

    public void waitUntilForTextVisible(By locator, int time, String text) {
        try {
            waitElement(time).until(ExpectedConditions.textToBe(locator, text));
        } catch (NoSuchElementException | TimeoutException ex) {
            ex.getMessage();
        }
    }

    @Override
    protected WebDriverWait waitElement() {
        DELAY = 250;
        return super.waitElement();
    }

    public void waitUntilVisibleAndInvisible(By locator, int time) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.SECOND, time);
        long endTime = calendar.getTimeInMillis();
        calendar.clear();
        do {
            try {
                waitElement(TIME_OUT).until(ExpectedConditions.invisibilityOfElementLocated(locator));
            } catch (TimeoutException | StaleElementReferenceException | NoSuchElementException |
                     ElementClickInterceptedException var4) {
                System.err.println("MSG Exception : " + var4.getMessage());
                waitElement(TIME_OUT).until(ExpectedConditions.invisibilityOfElementLocated(locator));
                break;
            }
            if (new Date().getTime() < endTime)
                break;
        }
        while (new Date().getTime() < endTime);
    }

    public void waitUntilVisibleAndInvisible(By locator) {
        try {
            waitElement(EXIST / 5).until(ExpectedConditions.visibilityOfElementLocated(locator));
        } catch (WebDriverException var4) {
            waitElement(TIME_OUT * 2).until(ExpectedConditions.invisibilityOfElementLocated(locator));
        } finally {
            waitElement(TIME_OUT).until(ExpectedConditions.invisibilityOfElementLocated(locator));
        }
    }

    public boolean waitUntilInvisible(By locator, int disappear) {
        try {
            waitElement(disappear).until(ExpectedConditions.invisibilityOfElementLocated(locator));
        } catch (NoSuchElementException | TimeoutException ex) {
            ex.getMessage();
        }
        return false;
    }

    public boolean waitTextToBePresentInElement(WebElement element, String text) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, 3);
            wait.until(ExpectedConditions.textToBePresentInElement(element, text));
        } catch (NoSuchElementException | TimeoutException ex) {
            ex.getMessage();
        }
        return false;
    }

    public boolean waitUntilInvisible(By locator) {
        int disappear = 30;
        try {
            waitElement(disappear).until(ExpectedConditions.invisibilityOfElementLocated(locator));
        } catch (NoSuchElementException | TimeoutException ex) {
            ex.getMessage();
        }
        return false;
    }

    public void sendFile(By locator, String filePath) {
        waitUntilExist(locator).sendKeys(filePath);
    }

    public List<WebElement> getElementsList(By locator) {
        waitUntilExist(locator);
        return driver.findElements(locator);
    }

    public List<String> getTextOfElements(By locator) {
        waitUntilExist(locator);
        ArrayList<String> allText = new ArrayList<>();
        List<WebElement> elements = driver.findElements(locator);
        for (WebElement myElement : elements) {
            waitVisibility(myElement);
            allText.add(myElement.getText());
        }
        return allText;
    }

    public WebElement getElement(By locator) {
        return waitUntilExist(locator);
    }

    public WebElement getElementFromList(By locator, int index) {
        waitUntilExist(locator);
        List<WebElement> elements = driver.findElements(locator);
        return elements.get(index);
    }

    public void uploadFile(By locator, String pathToFile) {
        WebElement uploadElement = waitUntilClickable(locator);
        uploadElement.sendKeys(System.getProperty("user.dir") + File.separator + "src" + File.separator + "resources" + File.separator + pathToFile);
        waitUntilClickable(locator);
    }

    public WebElement waitingClickableLoc(By locator) {
        waitEl().ignoreAll(ignored_exeptions);
        return waitEl().until(ExpectedConditions.elementToBeClickable(locator));
    }

    public WebDriverWait waitEl() {
        WebDriverWait wait = new WebDriverWait(driver, 30);
        wait.ignoring(NoSuchElementException.class);
        return wait;
    }

    protected final WebDriver driver = InitialDriver.getInstance().getDriver();

    public WebElement presenceElLocation(By locator) {
        waitEl().ignoreAll(ignored_exeptions);
        return waitEl().until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    public Collection ignored_exeptions = Arrays.asList(
            NoSuchElementException.class,
            ElementNotVisibleException.class,
            ElementNotSelectableException.class);
}