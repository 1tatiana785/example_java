package uiPages;

import core.coreUI.ui.seleniumElement.*;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import uiPages.verifyEmails.BulkEmailVerification;

import static core.helper.ConstantPage.*;

public class SignInPage {

    public static By emailField = By.xpath("//*[text()='Email']/following-sibling::input");
    public static By passField = By.xpath("//*[text()='Password']/following-sibling::input");
    public static By logInBtn = By.cssSelector(".btn-primary");
    public static By continueBttn = By.cssSelector(".modal__actions button:nth-child(1)");
    public static By closedBttn = By.cssSelector(".modal-snovio:not([style*='none']) div.btn-modal-close");

    Button button = new Button();
    Fields fields = new Fields();
    Mouse mouse = new Mouse();
    Windows windows = new Windows();
    ElementProperties element = new ElementProperties();

    JSExecutor jsExecutor = new JSExecutor();

    @Step("Input email and password, click 'Log In' button, check main page Logo")
    public SignInPage loginStart(String url, String user, String pass) {
        windows.navigate(url);
        try {
            if (!element.findElementByXpath(emailField).isDisplayed()) {
                windows.refreshPage();
            }
        } catch (Exception e) {
        }
        fields.sendValue(emailField, user);
        fields.sendValue(passField, pass);
        jsExecutor.JSClick(logInBtn);
        return this;
    }

    @Step("Input email and password, click 'Log In' button, check main page Logo")
    public SignInPage login(String url, String user, String pass) {
        loginStart(url, user, pass);
        try {
            if (element.findElementByXpath(ownerMenu).isEnabled()) {
            } else {
                System.out.println("Owner menu is enabled");
                button.click(closedBttn);
            }
        } catch (Exception e) {
        }
        return this;
    }

    @Step("input email and password, click 'Log In' button, check main page Logo")
    public SignInPage loginFree(String url, String user, String pass) {
        loginStart(url, user, pass);
        try {
            element.waitVisibility(planMenu, 5);
        } catch (Exception e) {
            element.waitVisibility(continueBttn, 5);
            windows.selectChild();
            mouse.mouseDoubleClick(continueBttn);
        }
        return this;
    }

    @Step("Open owner menu, click 'Logout' button")
    public SignInPage logout() {
        button.click(ownerMenu);
        element.waitVisibility(languageLocator, 2);
        element.waitVisibility(logoutBtn, 3);
        jsExecutor.JSClick(logoutBtn);
        element.waitVisibility(loader, 5);
        windows.refreshPage();
        return this;
    }

    public BulkEmailVerification goToBulkEmailVerification() {
        mouse.moveMouseTo(goToMenuVerify);
        button.click(goToBEV);
        return BulkEmailVerification.getNewInstance();
    }
}