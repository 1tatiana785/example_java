package Ui;

import core.coreUI.AllListeners.Listener;
import core.coreUI.AllListeners.TestMethodCapture;
import core.coreUI.ui.seleniumElement.Windows;
import core.json.JsonParser;
import core.testRail.TestRailAnnotationList;
import core.testRail.TestRailId;
import io.qameta.allure.TmsLink;
import org.testng.annotations.*;
import uiPages.ForAllPages;
import uiPages.ModalPopUp;
import uiPages.SignInPage;
import uiPages.verifyEmails.BulkEmailVerification;

import static core.helper.ConstantPage.*;
import static core.helper.LogIn.*;
import static core.helper.Files.*;
import static org.testng.Assert.*;


@Listeners({TestMethodCapture.class, Listener.class})
public class Group {


    @BeforeGroups("FreePlanNoCredits")
    public void loginNoCredits() {
        new SignInPage().loginFree(urlBEV, emailFreePlanNoCredits, passwordFreePlanNoCredits);
    }

    @BeforeGroups("FreePlan")
    public void loginFreePlan() {
        new SignInPage().loginFree(urlBEV, emailFreePlan, passwordFreePlan);
    }

    @AfterGroups("FreePlan")
    public void logoutFreePlan() {
        new SignInPage().logout();
    }

    @AfterGroups("FreePlanNoCredits")
    public void logoutNoCredits() {
        new SignInPage().logout();
    }

    @BeforeMethod()
    public void goToBEV() {
        new Windows().navigate(urlBEV);
    }

    @AfterClass
    public void quit() {
        new Windows().quit();
    }

    /*************************************   Free Plan no Credits    **********************************/

    @TmsLink(value = "2991")
    @TmsLink(value = "3010")
    @TmsLink(value = "2996")
    @TmsLink(value = "3003")
    @Test(priority = 0, groups = "FreePlanNoCredits", description = "Free plan (no credits): click 'Verify' button, check 'You're out of credits' text")
    @TestRailAnnotationList(TestCaseIds = {2991, 3010, 2996, 3003})
    public void clickVerifyNoCreditsFreeBEV() {
        if (new BulkEmailVerification().checkVerifyBttnIsShown() == true) {
            for (int i = 0; i < LANG.length; i++) {
                new ForAllPages().setLanguage(LANG[i]);
                new BulkEmailVerification()
                        .clickVerifyButton();
                ModalPopUp modalPopUp = new ModalPopUp();
                assertEquals(modalPopUp.checkModalText(), new JsonParser().parse(BEV, "YouAreOutOfCredits", LANG[i]));
                modalPopUp.checkUpgradePlanBttn();
                new Windows().navigate(urlBEV);
            }
            new ForAllPages().setLanguage(LANG_EN);
        } else {
            System.out.println("There are no tasks");
        }
    }

    /*************************************   Free Plan   **********************************/

    @TmsLink(value = "2779")
    @Test(priority = 0, groups = "FreePlan", description = "Free plan: add manually, check text in modal pop-up")
    @TestRailId(TestCaseId = 2779)
    public void freePlanAdManuallyBEV() {
        new BulkEmailVerification().clickAddManually();
        ModalPopUp modalPopUp = new ModalPopUp();
        assertEquals(modalPopUp.checkModalText(), new JsonParser().parse(BEV, "premiumFeature", LANG_EN));
        modalPopUp.checkUpgradePlanBttn();
    }
}