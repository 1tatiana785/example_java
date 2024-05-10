package Ui;

import core.coreUI.AllListeners.Listener;
import core.coreUI.AllListeners.TestMethodCapture;
import core.coreUI.ui.seleniumElement.Windows;
import core.helper.ConstantPage;
import core.helper.Helper;
import core.json.JsonParser;
import core.testRail.TestRailAnnotationList;
import core.testRail.TestRailId;
import io.qameta.allure.TmsLink;
import org.testng.annotations.*;
import uiPages.ForAllPages;
import uiPages.SignInPage;
import uiPages.verifyEmails.BulkEmailVerification;

import java.util.Arrays;

import static core.helper.ConstantPage.*;
import static core.helper.Files.*;

import static core.helper.LogIn.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.testng.Assert.assertEquals;


@Listeners({TestMethodCapture.class, Listener.class})

public class BEVUploadTests {

    public static String[] fileEmpty = {getErrorFile("emptyTxt"), getErrorFile("emptyCsv")};

    @BeforeClass
    public void login() {
        new SignInPage().login(urlBEV, email, password);
    }

    @BeforeMethod
    public void goToBulkEmailVerification() {
        new Windows().navigate(urlBEV);
        new ForAllPages().setLanguage(LANG_EN);
    }

    @AfterClass
    public void quit() {
        new Windows().quit();
    }

    @TmsLink(value = "2892")
    @Test(priority = 0, description = "Check BEV in menu panel")
    @TestRailId(TestCaseId = 2892)
    public void checkMenu() {
        new SignInPage().goToBulkEmailVerification();
    }

    @TmsLink(value = "2275")
    @TmsLink(value = "3095")
    @TmsLink(value = "3096")
    @TmsLink(value = "3097")
    @Test(priority = 0, description = "Upload big file, check 'Maximum allowed size for uploaded files 10 mb' text (EN, PT, ZH, UA)")
    @TestRailAnnotationList(TestCaseIds = {2275, 3095, 3096, 3097})
    public void bigFileBEV() {
        for (int i = 0; i < LANG.length; i++) {
            new ForAllPages().setLanguage(LANG[i]);
            BulkEmailVerification bulkEmailVerification = new BulkEmailVerification()
                    .uploadFromFile(getErrorFile("bigFile"));
            assertEquals(bulkEmailVerification.getErrorTitleText(),new JsonParser().parse(BEV, "fileIsTooLarge", LANG[i]));
        }
    }

    @TmsLink(value = "2284")
    @TmsLink(value = "2431")
    @TmsLink(value = "3105")
    @TmsLink(value = "3106")
    @TmsLink(value = "3107")
    @Test(priority = 0, description = "Choose empty txt/csv file, check error text (EN, PT, ZH, UA)")
    @TestRailAnnotationList(TestCaseIds = {2284, 2431, 3105, 3106, 3107})
    public void uploadEmptyFileBEV() {
        Arrays.stream(fileEmpty).forEach((file) -> {
                new BulkEmailVerification()
                        .uploadFromErrorFile(LANG_EN, file, new ConstantPage().FileEmpty.get(LANG_EN));
            });
    }

    @TmsLink(value = "1985")
    @Test(priority = 0, description = "Search for a task by the name 'Task' (present in the list), search for a task by the name'!!!' (not in the list)")
    @TestRailId(TestCaseId = 1985)
    public void searchTaskNameBEV() {
        BulkEmailVerification bulkEmailVerification = new BulkEmailVerification()
                .addManually("Task", "tm@snov.io")
                .waitVerify();
        assertThat(bulkEmailVerification.searchTask("Task"))
                .as("Is there text  with this name in the list?")
                .isTrue();
    }

    @TmsLink(value = "2285")
    @Test(priority = 0, description = "Add manually: check 'Upload' button is enable, name is not filled")
    @TestRailId(TestCaseId = 2285)
    public void checkUploadButtonNameBEV() {
        BulkEmailVerification bev = new BulkEmailVerification()
                .addManuallyWithoutVerify("Name", "");
        assertThat(bev.checkUploadButtonIsNotEnable())
                .as("Is it 'Upload' button is not enable?")
                .isTrue();
    }

    @DataProvider(name = "data-providerTxt")
    public Object[][] dataProviderTxt() {
        return new Object[][]{
                {getFileNameNotInLatin(0)}, {getFileNameNotInLatin(1)},
        };
    }

    @TmsLink(value = "2616")
    @Test(dataProvider = "data-providerTxt", priority = 0, description = "Upload txt file (name not in Latin), check file info")
    @TestRailId(TestCaseId = 2616)
    public void txtNameNotInLatinBEV(String data) {
        new BulkEmailVerification()
                .uploadFromFile(data, "3", false)
                .clickVerifyButton(data, "1 " + BEVTests.EmailsVerified);
    }

    @TmsLink(value = "2283")
    @Test(priority = 0, description = "Add manually: enter invalid email")
    @TestRailId(TestCaseId = 2283)
    public void addSingleInvalidBEV() throws Exception {
        BulkEmailVerification bulkEmailVerify = new BulkEmailVerification()
                .addManually("Task", "test@email.io");
        assertEquals(bulkEmailVerify.getResultText(), "Invalid entries: 1");
        new BulkEmailVerification()
                .clickDownloadInvalidEntriesBtn();
       new Helper()
                .readFromFile(BadDownloadsList, "tmsnov.io", 1)
                .deleteFile(BadDownloadsList);
    }
}