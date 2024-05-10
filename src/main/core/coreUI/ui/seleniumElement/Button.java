package core.coreUI.ui.seleniumElement;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.Calendar;
import java.util.Date;

import static core.helper.Base.TIME_OUT;

public class Button extends Element {

    ElementProperties elementProperties = new ElementProperties();

    public void click(By locator) {
        waitUntilClickable(locator).click();
    }

    public void clickEnabledBtn(By locator) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.SECOND, TIME_OUT);
        long endTime = calendar.getTimeInMillis();
        calendar.clear();
        WebElement myElement;
        do {
            myElement = selectFromList(locator).stream()
                    .filter(WebElement::isDisplayed)
                    .filter(WebElement::isEnabled)
                    .findAny()
                    .orElse(null);
            if (new Date().getTime() < endTime)
                break;
        }
        while (myElement == null);
        myElement.click();
    }

    public void click(WebElement element) {
        waitUntilClickable(element).click();
    }

    public void clickRepeat(By locator) {
        int i = 0;
        while (elementProperties.isEnabled(locator) || i < 5) {
            waitUntilClickable(locator).click();
            i++;
        }
    }

    public void clickString(String elementLocation, String name) {
        waitUntilClickable(By.xpath(String.format(elementLocation, name))).click();
    }

    public void clickStringCSS(String elementLocation, String name) {
        waitUntilClickable(By.cssSelector(String.format(elementLocation, name))).click();
    }

    public void clickString(String elementLocation, String name, String name1) {
        waitUntilClickable(By.xpath(String.format(elementLocation, name, name1))).click();
    }
}
