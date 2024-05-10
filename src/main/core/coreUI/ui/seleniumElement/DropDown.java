package core.coreUI.ui.seleniumElement;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.support.ui.Select;


public class DropDown extends Element{

    public String getSelected(By locator) {
        waitUntilClickable(locator);
        Select select = new Select(waitUntilClickable(locator));
        return select.getFirstSelectedOption().getText();
    }

    public int getLength(By locator) {
        Select select = new Select(waitUntilClickable(locator));
        return select.getOptions().size();
    }

    public void selectDropDownByIndex (By locator, int index) {
        Select select = new Select(waitUntilClickable(locator));
        select.selectByIndex(index);
    }

    public void selectDropDownByValue (By locator, String value) {
        try {
            Select select = new Select(waitUntilClickable(locator));
            waitUntilVisible(waitUntilVisible(locator).findElement(By.xpath(".//option[@value = '" + value + "']")));
            select.selectByValue(value);
        }
        catch (StaleElementReferenceException exception)
        {
            Select select = new Select(waitUntilClickable(locator));
            waitUntilVisible(waitUntilVisible(locator).findElement(By.xpath(".//option[@value = '" + value + "']")));
            select.selectByValue(value);
        }
    }
}