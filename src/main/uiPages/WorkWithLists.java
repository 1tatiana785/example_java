package uiPages;

import core.helper.Helper;
import core.coreUI.ui.seleniumElement.*;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import uiPages.verifyEmails.BulkEmailVerification;

import java.util.List;

import static uiPages.verifyEmails.BulkEmailVerification.addToListBtnBEV;

public class WorkWithLists {

    public static By addToListBtn = By.cssSelector(".found-prospect:nth-child(2) .lists__action-btn, .table-view tr:nth-child(1)  .lists__action-btn");
    private static By createListNameButton = By.cssSelector(".app-dropdown__drop-new button, .sensitive-dropdown__drop-new button");
    private static By inputListNameField = By.cssSelector(".app-dropdown__drop-new input, .sensitive-dropdown__drop-new input");
    private static By firstListName = By.cssSelector(".sensitive-dropdown__item:nth-child(1), .app-dropdown__drop-item:nth-child(2)");
    public static By ListsName = By.cssSelector(".app-dropdown__drop-item, .sensitive-dropdown__item");
    public static By listName = By.cssSelector(".table-view tr:nth-child(1) td:nth-child(4) a, .found-prospect:nth-child(2) .lists a");
    private By tasksCount = By.cssSelector("table th:nth-child(3) .first-row__border, .previous-found-prospects__check-all .app-dropdown+div");
    private static By tasksList = By.cssSelector(".prev .table tbody tr, .found-prospect");
    private By listOfProspects = By.cssSelector(".first-row__checkbox-box img, .previous-found-prospects__check-all img");
    private By selectAllTasks = By.cssSelector(".first-row__checkbox, .previous-found-prospects__check-block label");
    private static By firstCheckbox = By.cssSelector(".previous:nth-child(4) label");
    String existingListName = "//div[contains(text(),'%s')]";
    String select = "//div[@class='snovio-drop']/div[%s]";
    private By selectCheckboxes = By.cssSelector(".found-prospect__result-item input");
    private By selectCheckbox = By.cssSelector(".prev .row");

    Button button = new Button();
    Fields field = new Fields();
    JSExecutor jsExecutor = new JSExecutor();
    ElementProperties element = new ElementProperties();


    /**********************   Add to list  *****************/

    @Step("Click All add to list button, input created list name, click create list name, select and click first list name")
    public WorkWithLists clickAddToListBttn() {
        element.waitClickable(addToListBtn, 60);
        jsExecutor.JSClick(addToListBtn);
        return this;
    }

    @Step("Click 'Create list name' button, select first list name in drop-down")
    public WorkWithLists createListName(String listName) {
        field.sendValue(inputListNameField, listName);
        jsExecutor.JSClick(createListNameButton);
        return this;
    }

    @Step("Click 'Create list name' button, select first list name in drop-down")
    public WorkWithLists createList(String listName) {
        createListName(listName);
        element.waitTextToBePresentInElement(element.getElementsList(ListsName).get(0), listName);
        button.click(firstListName);
        return this;
    }

    @Step("Click 'Create list name' button, select first list name in drop-down")
    public WorkWithLists selectList(String listName) {
        try {
            button.clickString(existingListName, listName);
        } catch (Exception e) {
            createList(listName);
        }
        return this;
    }

    @Step("Add to list: clicking add to list button, input generate name, clicking Create List name button, selecting first list name in drop-down")
    public BulkEmailVerification addToListScroll(String listName) {
        BulkEmailVerification bulkEmailVerification = new BulkEmailVerification();
        jsExecutor.JSScrollTo();
        element.waitUntilInvisible(addToListBtnBEV, 3);
        button.click(addToListBtnBEV);
        selectList(listName);
        jsExecutor.JSScrollDown();
        return bulkEmailVerification;
    }

    @Step("Add to list: clicking add to list button, input generate name, clicking Create List name button, selecting first list name in drop-down")
    public BulkEmailVerification addToListScroll() {
        BulkEmailVerification bulkEmailVerification = new BulkEmailVerification();
        element.waitUntilInvisible(addToListBtnBEV, 3);
        button.click(addToListBtnBEV);
        String generateListName = Helper.generateStringDateTime();
        createList(generateListName);
        return bulkEmailVerification;
    }

    @Step("Click All add to list button, input created list name, click create list name, select and click first list name")
    public WorkWithLists addToList() {
        clickAddToListBttn();
        String generateListName = Helper.generateStringDateTime();
        createList(generateListName);
        return this;
    }

    @Step("Select several tasks, click add to list button, select existing list name and click")
    public WorkWithLists addToList(String listName) {
        clickAddToListBttn();
        selectList(listName);
        return this;
    }

    @Step("Check add to list display")
    public boolean addToListBttnDisplay() {
        return element.isDisplayed(addToListBtn);
    }

    @Step("Get list name first task")
    public String getListName() {
        element.waitingClickableLoc(listName);
        return field.getTxt(listName);
    }

    @Step("Get selected tasks count")
    public int checkCount() {
        element.waitVisibility(tasksCount, 15);
        String text = field.getTxt(tasksCount);
        String[] words = text.split(" ");
        int i = Integer.parseInt(words[1]);
        return i;
    }

    @Step("Click on the arrow to select multiple tasks, select one of the element (All on page, All in list, None)(parameter)")
    public WorkWithLists selectPreviousEmailSearch(String index) {
        List<WebElement> taskList = element.getElementsList(tasksList);
        if (taskList.size() < 20) {
            button.click(selectAllTasks);
        }
        if (taskList.size() >= 20) {
            button.click(listOfProspects);
            element.findElementByXpath(select, index).click();
        }
        return this;
    }

    @Step("Check selected tasks")
    public boolean isSelectedTask() {
        return element.isSelected(selectCheckboxes);
    }

    @Step("Check selected tasks")
    public boolean isSelected() {
        return element.findElement(selectCheckbox).getAttribute("class").contains("selected");
    }
}