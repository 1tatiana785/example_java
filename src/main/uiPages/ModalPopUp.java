package uiPages;

import core.coreUI.ui.seleniumElement.*;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

import static org.testng.Assert.*;
import static core.helper.ConstantPage.*;

public class ModalPopUp {

    public static By popUpTitle = By.cssSelector(".modal-info__title, .swal2-title:is([style*='display: block']), .modal-snovio__title");
    public static By getSomeCredits = By.cssSelector(".modal-snovio:not([style*='none']) button, .modal-snovio:not([style*='none']) a, .upgrade-modal__right a");
    private By modalText = By.cssSelector(".modal-snovio:not([style*='none']) *[class*=text], .modal-snovio:not([style*='display: none']) *[class*=text], .swal2-html-container");
    public static By confirm = By.cssSelector(".modal-snovio:not([style*='none']) *[data-test*=modal-confirm], .swal2-confirm, .modal-snovio:not([style*='display: none']) button.confirm");
    public static By deleteButton = By.cssSelector(".table__box tbody tr:first-child .table__rules>svg:is(.table__rules-btn--red), tr:first-child .rules-cell__content svg:is(.rules-cell__btn--red)");

    Button button = new Button();
    Fields field = new Fields();
    ElementProperties element = new ElementProperties();

    @Step("Get warning text in modal pop-up")
    public boolean checkModalText(String text) {
        return field.getTxt(modalText).contains(text);
    }

    @Step("Get warning text in modal pop-up")
    public String checkModalText() {
        return field.getTxt(modalText);
    }

    @Step("Get warning text in modal pop-up")
    public ModalPopUp checkModalTextSURLS(String text) {
        element.waitVisibility(popUpTitle, 15);
        assertTrue(checkModalText(text));
        checkOkBttn();
        return this;
    }

    @Step("Check text in modal pop-up")
    public ModalPopUp checkOkBttn() {
        element.waitUntilInvisible(confirm, 1);
        element.waitVisibility(confirm, 5);
        new JSExecutor().JSClick(confirm);
        element.waitVisibility(loader, 1);
        element.waitUntilInvisible(loader, 1);
        return this;
    }

    @Step("Click button in the modal pop-up")
    public ModalPopUp checkUpgradePlanBttn() {
        element.waitVisibility(getSomeCredits, 25);
        button.click(getSomeCredits);
        element.waitVisibility(loader);
        return this;
    }
}