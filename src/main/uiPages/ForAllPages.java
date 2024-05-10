package uiPages;

import core.coreUI.ui.seleniumElement.*;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.testng.Assert.assertTrue;
import static core.helper.ConstantPage.*;

public class ForAllPages {

    public static final By youtube = By.cssSelector(".tutorial-banner a:nth-child(3)");
    public static final By titlePage = By.cssSelector(".header__title");
    public static final By UpgradeBttn = By.cssSelector("#nav .snovAside__footer-block>a, .upgrade-modal__btn");
    String langLocator = "//div[@class='snovProfile__locales-drop']//div[contains(text(),'%s')]";
    private By showMoreButton = By.cssSelector(".bulk-email-search__content button, .table+div button, .social-url__list > button");
    public static By tasks = By.xpath("//table//tr");
    public static By deleteButton21 = By.xpath("//table//tr[21]//div[@class='table__cell table__cell--rules']/div[2]");
    public By status = By.cssSelector(".table-view .row:nth-child(1) .email-label");

    /*************** export      **********************************/
    public static By exportButton = By.cssSelector("tr:first-child .table__rules:first-child svg:not(.rules-cell__btn--red), tr:first-child .rules-cell__content:first-child svg:not(.rules-cell__btn--red)");
    String exportEmailsStatus = "//label[%s]/input[@type='checkbox']";
    public By exportEmails = By.xpath("//input[@type='checkbox']");
    String exportFormat = "//div[@class='export__extention']/label[%s]/input";
    public By downloadButton = By.cssSelector(".export button:nth-child(2)");
    public By exportSuccessfulText = By.cssSelector(".snovio-alert--success");
    Button button = new Button();
    Fields field = new Fields();
    Mouse mouse = new Mouse();
    Windows windows = new Windows();
    JSExecutor jsExecutor = new JSExecutor();
    ElementProperties element = new ElementProperties();

    @Step("Get credits: click on Account menu and get amount of credits")
    public float checkCredits() {
        element.waitClickable(ownerMenu, 20);
        button.click(ownerMenu);
        element.waitVisibility(credits, 15);
        String text = field.getTxt(credits);
        String result = text.split(" ")[2].split("/")[0];
        return Float.parseFloat(result);
    }

    @Step("Get title Page")
    public String getTitle() {
        return field.getTxt(titlePage);
    }

    @Step("Set Language")
    public ForAllPages setLanguage(String lang) {
        element.waitVisibility(loader, 3);
        button.click(ownerMenu);
        element.waitVisibility(languageLocator, 35);
        mouse.moveMouseTo(languageLocator);
        button.click(languageLocator);
        element.waitVisibility(loader, 1);
        button.clickString(langLocator, lang);
        element.waitVisibility(loader, 1);
        element.waitUntilInvisible(loader, 1);
        return this;
    }

    @Step("Click upgrade")
    public ForAllPages clickUpgrade() {
        element.waitClickable(UpgradeBttn, 36);
        jsExecutor.JSClick(UpgradeBttn);
        element.waitVisibility(loader);
        return this;
    }

    @Step("Get current url")
    public boolean getCurrentUrl(String url) {
        element.waitVisibility(loader, 1);
        return windows.getCurrentUrl().contains(url);
    }

    public static By elementId = By.cssSelector(".video");

    @Step("Check video")
    public boolean getCurrentId() {
        return element.isPresent(elementId);
    }

    @Step("Click 'Show more' button: get number of rows, click 'Show more' button, get number of rows, compare the difference")
    public ForAllPages clickShowMoreBtn() {
        List<WebElement> tasksList = element.getElementsList(tasks);
        jsExecutor.JSClick(showMoreButton);
        WebElement buttonSM = element.findElementByXpath(showMoreButton);
        jsExecutor.JSClick(buttonSM);
        element.waitVisibility(deleteButton21, 3);
        List<WebElement> tasksList2 = element.getElementsList(tasks);
        int Results = tasksList2.size() - tasksList.size();
        assertTrue(Results > 0);
        return this;
    }

    /**********************   Export *****************/
    @Step("Export: click export button first task, select export format (parameter), select checkbox (parameter), click 'Download' button")
    public ForAllPages selectFormat(String format) {
        jsExecutor.JSScrollTo();
        element.waitUntilInvisible(exportButton, 7);
        button.click(exportButton);
        element.waitUntilInvisible(exportEmails, 3);
        windows.implicitlyWait(1, TimeUnit.SECONDS);
        WebElement checkboxExportFormat = element.findElementByXpath(exportFormat, format);
        jsExecutor.JSClick(checkboxExportFormat);
        element.waitUntilInvisible(exportEmails, 2);
        windows.implicitlyWait(1, TimeUnit.SECONDS);
        return this;
    }

    @Step("Export: clicking export button first task, selecting export format (parameter), selecting checkbox (parameter), clicking Download button")
    public ForAllPages selectStatus(String status) {
        WebElement checkboxExportEmailsStatus = element.findElementByXpath(exportEmailsStatus, status);
        jsExecutor.JSClick(checkboxExportEmailsStatus);
        windows.implicitlyWait(1, TimeUnit.SECONDS);
        return this;
    }

    @Step("Export: clicking export button first task, selecting export format (parameter), selecting checkbox (parameter), clicking Download button")
    public ForAllPages clickDownload() {
        button.click(downloadButton);
        element.waitVisibility(exportSuccessfulText, 50);
        assertTrue(getExportText().contains(ExportSuccessful));
        return this;
    }

    @Step("Export: clicking export button first task, selecting export format (parameter), selecting checkbox (parameter), clicking Download button")
    public ForAllPages export(String status1, String status2, String status3, String format) {
        selectFormat(format);
        selectStatus(status1);
        selectStatus(status2);
        selectStatus(status3);
        clickDownload();
        return this;
    }

    @Step("Export: clicking export button first task, selecting export format (parameter), selecting checkbox (parameter), clicking Download button")
    public ForAllPages export(String status1, String status2, String format) {
        selectFormat(format);
        selectStatus(status1);
        selectStatus(status2);
        clickDownload();
        return this;
    }

    @Step("Export: clicking export button first task, selecting export format (parameter), selecting checkbox (parameter), clicking Download button")
    public ForAllPages export(String status1, String format) {
        selectFormat(format);
        selectStatus(status1);
        clickDownload();
        return this;
    }

    @Step("Get Export text after export result")
    public String getExportText() {
        element.waitUntilInvisible(exportSuccessfulText, 7);
        return field.getTxt(exportSuccessfulText);
    }
}