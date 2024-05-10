package Ui;

import core.coreUI.AllListeners.Listener;
import core.coreUI.AllListeners.TestMethodCapture;
import core.coreUI.ui.seleniumElement.Windows;
import core.helper.Helper;
import core.testRail.TestRailId;
import io.qameta.allure.TmsLink;
import org.testng.annotations.*;
import uiPages.ForAllPages;
import uiPages.SignInPage;
import uiPages.WorkWithLists;
import uiPages.verifyEmails.BulkEmailVerification;

import static core.helper.ConstantPage.*;
import static core.helper.Files.*;
import static core.helper.LogIn.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.testng.Assert.*;

@Listeners({TestMethodCapture.class, Listener.class})

public class BEVTests {

    public static String EmailsInFile = "emails in file";
    public static String EmailsVerified = "emails verified";

    @BeforeClass
    public void login() {
        new SignInPage().login(urlBEV, email, password);
    }

    @BeforeMethod
    public void goToBulkEmailVerification() {
        new Windows().navigate(urlBEV);
    }

    @AfterClass
    public void quit() {
        new Windows().quit();
    }


    @TmsLink(value = "1981")
    @Test(priority = 0, description = "Add manually: enter valid email")
    @TestRailId(TestCaseId = 1981)
    public void addManuallyValidBEV() {
        String fileName = "Task1";
        BulkEmailVerification bev = new BulkEmailVerification()
                .addManually(fileName, "sm@snov.io  df.df");
        assertEquals(bev.getValidEmailsText(), "Emails detected: 1 Invalid entries: 1");
        bev.clickAddValidEmails()
                .waitVerify()
                .checkText(fileName, "1 " + EmailsVerified);
    }

    @TmsLink(value = "2291")
    @Test(priority = 0, description = "Add from txt file, check column info")
    @TestRailId(TestCaseId = 2291)
    public void addFromFileCheckColumnBEV() {
       final BulkEmailVerification bulkEmailVerify = new BulkEmailVerification()
                .uploadFromFile(FileURLLinksTxt, "2", false);
        String date = Helper.generateDate();
        String date1 = Helper.generateDate1();
        assertThat(bulkEmailVerify.getDateText().contains(date)||bulkEmailVerify.getDateText().contains(date1))
                .as("Is it date true?")
                .isTrue();
        new BulkEmailVerification()
                .checkText(FileURLLinksTxt, "2 " + EmailsInFile)
                .clickVerifyButton(FileURLLinksTxt, "2 " + EmailsVerified);
    }

    @TmsLink(value = "2292")
    @Test(priority = 0, description = "Add manually: check credits")
    @TestRailId(TestCaseId = 2292)
    public void addManuallyCheckCreditsBEV() {
        ForAllPages forAllPages = new ForAllPages();
        float creditsBefore = forAllPages.checkCredits();
        BulkEmailVerification bulkEmailVerify = new BulkEmailVerification();
        System.out.println("There are " + creditsBefore + " credits before searching");
        do {
            String emails = Helper.setRandomEmails(2, 10);
            new BulkEmailVerification()
                    .addManually("Name", emails)
                    .waitVerify();
            String status = bulkEmailVerify.getStatusColumnTextWarning();
            System.out.println("Verified 2 emails, " + status + " emails with warning status");
        } while (bulkEmailVerify.getStatusColumnTextWarning().contains("2"));
        new Windows().navigate(urlBEV);
        float creditsAfter = forAllPages.checkCredits();
        System.out.println("There are " + creditsAfter + " credits after searching");
        float result = (creditsAfter - creditsBefore);
        System.out.println("The difference is " + result + " credits");
        assertThat(result >= 0.5)
                .as("There are more than 0.5 credits used? ")
                .isTrue();
    }

    @TmsLink(value = "2294")
    @Test(priority = 0, description = "Add from file: check credits")
    @TestRailId(TestCaseId = 2294)
    public void addFromFileCheckCreditsBEV() {
        ForAllPages forAllPages = new ForAllPages();
        float creditsBefore = forAllPages.checkCredits();
        BulkEmailVerification bulkEmailVerify = new BulkEmailVerification()
                .clickAddFromFile();
        do {
            new Helper().setEmailInFile(2, 10);
            new BulkEmailVerification()
                    .uploadFromFile(DataBEV, "", false)
                    .clickVerifyButton(DataBEV, "2 " + EmailsVerified);
            System.out.println("Verified 2 emails, " + bulkEmailVerify.getStatusColumnTextWarning() + " emails with warning status");

        } while (bulkEmailVerify.getStatusColumnTextWarning().contains("2"));
        new Windows().navigate(urlBEV);
        float creditsAfter = forAllPages.checkCredits();
        System.out.println("There are " + creditsAfter + " credits after searching");
        float result = (creditsAfter - creditsBefore);
        System.out.println("The difference is " + result + " credits");
        assertThat(result >= 0.5)
                .as("There are more than 0.5 credits used? ")
                .isTrue();
    }

    @TmsLink(value = "2281")
    @Test(priority = 0, description = "Add from txt file: click 'Add to list' button, export (select valid status and xlsx format) export valid status, xlsx format, check file")
    @TestRailId(TestCaseId = 2281)
    public void exportXlsxBEV() throws Exception {
       BulkEmailVerification bulkEmailVerification = new BulkEmailVerification()
                .uploadFromFile(FileURLLinksTxt, "2", false)
                .clickVerifyButton(FileURLLinksTxt, "2 " + EmailsVerified);
        new WorkWithLists()
                .addToListScroll();
        new ForAllPages()
                .export("1", "2");
        String listName = bulkEmailVerification.getListName();
        String file = listName + ".xlsx";
        new Helper()
                .readFromXlsxFile(file, "Email,Email status", 3)
                .deleteFile(file);
    }

    @TmsLink(value = "2322")
    @Test(priority = 0, description = "Add manually: add email, verify, export xlsx, check file")
    @TestRailId(TestCaseId = 2322)
    public void exportXlsxWithoutAddToListBEV() throws Exception {
        String name = Helper.setSymbols(3).toLowerCase();
        new BulkEmailVerification()
                .addManually(name, Helper.randomEMail(3))
                .waitVerify();
        new ForAllPages()
                .export("1", "2");
        Helper helper = new Helper();
        String file = helper.getLastModifiedFile(name + ".xlsx");
        new Helper()
                .readFromXlsxFile(file, "Email,Email status", 2)
                .deleteFile(file);
    }
}