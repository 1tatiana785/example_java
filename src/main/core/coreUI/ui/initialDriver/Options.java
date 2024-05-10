package core.coreUI.ui.initialDriver;

import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerOptions;

import java.io.File;
import java.util.Collections;
import java.util.HashMap;

import static core.testRail.DataTestRail.HEADLESS;

class Options {

    ChromeOptions chromeOptions() {
        ChromeOptions options = new ChromeOptions();
        options.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));
        options.addArguments("window-size=1936,1056");
        options.addArguments("--disable-infobars");
        options.addArguments("--no-sandbox");
        options.addArguments("enable-features=NetworkServiceInProcess");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--disable-browser-side-navigation");
        options.addArguments("--disable-gpu");
        options.setPageLoadStrategy(PageLoadStrategy.NORMAL);
        if (HEADLESS) {
            options.addArguments("headless");
        }
        HashMap<String, Object> prefs = new HashMap<>();
        prefs.put("plugins.always_open_pdf_externally", true);
        prefs.put("download.default_directory", System.getProperty("user.dir") + File.separator + "src" + File.separator + "resources" + File.separator + "downloadedPDF");
        options.setExperimentalOption("prefs", prefs);
        return options;
    }

    FirefoxOptions firefoxOptions() {
        FirefoxOptions options = new FirefoxOptions();
        if (HEADLESS) {
            options.addArguments("headless");
        }
        options.addArguments("enable-automation");
        return options;
    }

    InternetExplorerOptions internetExplorerOptions() {
        return new InternetExplorerOptions();
    }
}