package uiPages.verifyEmails;

import core.coreUI.ui.seleniumElement.*;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import uiPages.ForAllPages;
import uiPages.ModalPopUp;

import java.util.List;
import java.util.stream.Collectors;

import static core.helper.ConstantPage.loader;
import static core.helper.Files.getFilePath;
import static org.testng.Assert.assertTrue;
import static uiPages.ModalPopUp.deleteButton;

public class BulkEmailVerification {

    /**************       Add from file      **********************************/
    private By addFromFileTab = By.xpath("//div[@class='choice']/div[1]");
    private By chooseFile = By.xpath("//input[@type='file']");
    private By chooseFileBttn = By.cssSelector(".upload-btn");
    private By listColumn = By.cssSelector(".modal-table-box .modal-table tr:first-child th");
    private By useFirstRow = By.cssSelector(".modal-info__options .modal-info__check");

    /**************       Add manually      **********************************/
    private By addManuallyTab = By.xpath("//div[@class='choice']/div[2]");
    private By nameField = By.className("snovio-input");
    private By emailAddressesField = By.xpath("//label[@class='input-label']/textarea");
    private By VerifyEmailsButton = By.cssSelector(".manually button");
    private By addValidEmailsButton = By.cssSelector(".import__group-btn button:nth-child(1)");
    private By validEmailsText = By.cssSelector(".single-text");
    private By downloadInvalidEntriesButton = By.cssSelector(".import__group-btn button:nth-child(2)");
    private By resultText = By.cssSelector(".single-text");

    /**************       Import pop-up      **********************************/
    String column = "//div[@data-test='modal-rows']//table//tr[1]/th[%s]";
    private By uploadButton = By.cssSelector(".modal-table-box button:nth-child(2)");

    /**************       tasks      **********************************/
    public static By progressColumnText = By.xpath("//table[@class='table__box']//tr[1]/td[2]//span");
    public static By progressColumnsText = By.cssSelector("tr td:nth-child(2) span");
    public static By addToListBtnBEV = By.xpath("//tr[1]//div[contains(@class,'add-list')]/button");
    public By searchNameField = By.xpath("//label/input[@type='text']");
    public By taskDate = By.xpath("//tbody/tr[1]/td[4]");
    public By tasksList = By.xpath("//tbody");
    private By statusColumnWarning = By.cssSelector(".table__box tbody tr:nth-child(1) .cell__counters .status-warning + *");
    private By verifyButton = By.cssSelector("tbody tr:nth-child(1) td:nth-child(6) button.btn-snovio");
    private By progressColumnText1 = By.cssSelector(".table__cell--name span");
    private By progressIcon = By.cssSelector("tbody tr:nth-child(1) td:nth-child(2) div svg.cell__circle");
    private By completeIcon = By.cssSelector("tbody tr:nth-child(1) td:nth-child(2) div img.cell__img, tbody tr:nth-child(1) td:nth-child(2) .svg");
    private static By errorTitle = By.cssSelector(".drag-zone__title");
    private static By listName = By.xpath("//tbody/tr[1]/td[5]//a");


    Button button = new Button();
    Fields field = new Fields();
    Mouse mouse = new Mouse();
    Windows windows = new Windows();
    JSExecutor jsExecutor = new JSExecutor();
    ElementProperties element = new ElementProperties();

    /**************       Add from file      **********************************/

    public BulkEmailVerification() {
        element.waitUntilVisibleAndInvisible(loader);
    }

    public static BulkEmailVerification getNewInstance() {
        ElementProperties element = new ElementProperties();
        element.waitVisibility(loader, 2);
        element.waitUntilInvisible(loader, 5);
        return new BulkEmailVerification();
    }

    @Step("Click 'Add from file' tab")
    public BulkEmailVerification clickAddFromFile() {
        jsExecutor.JSClick(addFromFileTab);
        return this;
    }

    @Step("Upload from file: upload file with 3 columns, don't select column")
    public BulkEmailVerification selectFirstRow(boolean selectFirstRow) {
        if (selectFirstRow == true) {
            button.click(useFirstRow);
        }
        if (selectFirstRow == false) {
        }
        return this;
    }

    @Step("Check 'Choose file' button is clickable")
    public boolean checkChooseFileBttnInDisabled() {
        return field.findElement(chooseFileBttn).getAttribute("class").contains("disabled");
    }

    @Step("Upload from file")
    public BulkEmailVerification uploadFromFile(String fileName) {
        element.sendFile(chooseFile, getFilePath(fileName));
        return this;
    }

    @Step("Upload from file: upload file, select column (if need)(parameter), select first row (if need)(parameter), click 'Upload' button")
    public BulkEmailVerification uploadFromFile(String name, String numberColumn, boolean selectFirstRow) {
        uploadFromFile(name);
        List<WebElement> tableColumns = element.getElementsList(listColumn);
        if (tableColumns.size() > 1) {
            button.clickString(column, numberColumn);
        }
        selectFirstRow(selectFirstRow);
        button.click(uploadButton);
        element.waitUntilInvisible(completeIcon, 5);
        element.waitVisibility(completeIcon, 45);
        return this;
    }

    @Step("Upload from file: upload file, select column (if need)(parameter), select first row (if need)(parameter), click 'Upload' button")
    public BulkEmailVerification uploadFromErrorFile(String lang, String file, String text) {
        new ForAllPages().setLanguage(lang);
        uploadFromFile(file);
        ModalPopUp modalPopUp = new ModalPopUp();
        assertTrue(modalPopUp.checkModalText(text));
        modalPopUp.checkOkBttn();
        return this;
    }

    /**************       Check text      **********************************/

    @Step("Get error title text")
    public String getErrorTitleText() {
        return field.getTxt(errorTitle);
    }

    @Step("Get text in Status column (Risky)")
    public String getStatusColumnTextWarning() {
        return field.getTxt(statusColumnWarning);
    }

    @Step("Get text in Added column")
    public String getDateText() {
        return field.getTxt(taskDate);
    }

    /**************       Add manually tab      **************************************************************/

    @Step("click 'Add manually' tab")
    public BulkEmailVerification clickAddManually() {
        jsExecutor.JSClick(addManuallyTab);
        return this;
    }

    @Step("click 'Add manually' tab, input 'List name' field, input 'Email addresses' field, click 'Verify email' button")
    public BulkEmailVerification setValues(String name, String email) {
        field.sendValue(nameField, name);
        field.sendValue(emailAddressesField, email);
        return this;
    }

    @Step("Check 'Verify email' button is enable, input 'List name' field (parameter), input 'Email addresses' field (parameter)")
    public BulkEmailVerification addManuallyWithoutVerify(String name, String email) {
        clickAddManually();
        setValues(name, email);
        return this;
    }

    @Step("click 'Add manually' tab, input 'List name' field, input 'Email addresses' field, click 'Verify email' button")
    public BulkEmailVerification addManually(String name, String email) {
        clickAddManually();
        setValues(name, email);
        button.click(VerifyEmailsButton);
        return this;
    }

    @Step("Check 'Upload' button is enable")
    public boolean checkUploadButtonIsNotEnable() {
        return !field.findElementByXpath(VerifyEmailsButton).isEnabled();
    }

    @Step("Check 'Verify email' button is enable, input 'List name' (parameter), input 'Email addresses' field (parameter)")
    public BulkEmailVerification clearFields() {
        setValues("", "");
        windows.refreshPage();
        return this;
    }

    @Step("Check text after clicking 'Upload' button")
    public String getResultText() {
        element.waitVisibility(deleteButton, 1);
        return field.getTxt(resultText);
    }

    @Step("Click 'Download Invalid entries' button")
    public BulkEmailVerification clickDownloadInvalidEntriesBtn() {
        button.click(downloadInvalidEntriesButton);
        return this;
    }

    @Step("Check text 'Emails detected or Invalid entries'")
    public String getValidEmailsText() {
        element.waitVisibility(validEmailsText, 1);
        return field.getTxt(validEmailsText);
    }

    @Step("Click 'Add valid emails' tab")
    public BulkEmailVerification clickAddValidEmails() {
        jsExecutor.JSClick(addValidEmailsButton);
        return this;
    }

    @Step("Check 'Verify' button is enable")
    public boolean checkVerifyBttnIsShown() {
        boolean result;
        try {
            field.findElementByXpath(verifyButton).isDisplayed();
            result = true;
        } catch (Exception e) {
            result = false;
        }
        return result;
    }

    /**************       Task      **********************************/

    @Step("Verify emails: select first task in table, click 'Verify' button, check text in Progress column")
    public BulkEmailVerification checkText(String fileName, String text) {
        element.waitUntilInvisible(completeIcon, 5);
        element.waitVisibility(completeIcon, 15);
        String result = element.getElementsList(tasksList).stream().filter(e -> e.findElement(progressColumnText1).getText().contains(fileName)).map(e -> e.findElement(progressColumnsText)).map(WebElement::getText).collect(Collectors.toList()).toString();
        assertTrue(result.contains(text));
        return this;
    }

    public List<String> checkText() {
        element.waitVisibility(deleteButton, 5);
        return element.getElementsList(progressColumnText).stream().map(WebElement::getText).collect(Collectors.toList());
    }

    @Step("Verify emails: select first task in table, click 'Verify' button, check text in Progress column")
    public BulkEmailVerification waitVerify() {
        element.waitUntilVisibleAndInvisible(progressIcon, 100);
        element.waitVisibility(completeIcon, 50);
        element.waitVisibility(addToListBtnBEV, 50);
        element.waitVisibility(deleteButton, 50);
        return this;
    }

    @Step("Verify emails: select first task in table, click 'Verify' button")
    public BulkEmailVerification clickVerifyButton() {
        button.click(verifyButton);
        return this;
    }

    @Step("Verify emails: select first task in table, click 'Verify' button, check text in Progress column")
    public BulkEmailVerification clickVerifyButton(String fileName, String text) {
        clickVerifyButton();
        waitVerify();
        checkText(fileName, text);
        return this;
    }

    @Step("Search task: enter text in the search field, check result")
    public boolean searchTask(String name) {
        field.typeOnExist(searchNameField, name);
        field.type(searchNameField, Keys.ENTER);
        element.waitUntilVisibleAndInvisible(loader, 3);
        boolean result;
        boolean listTaskName = element.getElement(tasksList).getText().contains(name);
        if (listTaskName == true) {
            result = true;
        } else {
            result = false;
        }
        return result;
    }

    @Step("Get list name first task")
    public String getListName() {
        element.waitingClickableLoc(listName);
        return field.getTxt(listName);
    }
}