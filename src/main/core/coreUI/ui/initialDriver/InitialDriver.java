package core.coreUI.ui.initialDriver;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

import static core.helper.Base.*;

public class InitialDriver {

    /*** There is pre-initialization of the driver and his way that is it prior to calling object  ****/
    private static ThreadLocal<InitialDriver> driverThread = new ThreadLocal<>();
    protected WebDriver driver;

    public static InitialDriver getInstance() {
        if (driverThread.get() == null) {
            synchronized (InitialDriver.class) {
                driverThread.set(new InitialDriver());
            }
        }
        return driverThread.get();
    }

    public WebDriver getDriver() {
        if (driver == null) {
            driver = initialDriver();
        }
        return driver;
    }

    private synchronized WebDriver initialDriver() {
        switch (DRIVER_NAME) {
            case "CHROME": {
                if (!DRIVER_VERSION.equals("0")) {
                    WebDriverManager.chromedriver().clearDriverCache().driverVersion(DRIVER_VERSION).setup();
                } else {
                    System.setProperty("webdriver.chrome.driver", DRIVER_PATH);
                }
                driver = new ChromeDriver(new Options().chromeOptions());
                break;
            }
            case "FIREFOX": {
                WebDriverManager.firefoxdriver().setup();
                driver = new FirefoxDriver(new Options().firefoxOptions());
                break;
            }
            case "IE": {
                driver = new InternetExplorerDriver(new Options().internetExplorerOptions());
                break;
            }
            default: {
                driver = new ChromeDriver(new Options().chromeOptions());
            }
        }
        return driver;
    }
}