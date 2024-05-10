package core.coreUI.utility;

import core.coreUI.ui.initialDriver.InitialDriver;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class UtilsMethods extends InitialDriver {
    private final WebDriver driverLocal = InitialDriver.getInstance().getDriver();
    private static UtilsMethods Instance;

    public static UtilsMethods getInstance() {
        synchronized (UtilsMethods.class) {
            if (Instance == null) {
                Instance = new UtilsMethods();
            }
        }
        return Instance;
    }

    public void deleteUtils() {
        Instance = null;
    }

    public HashMap<String, String> screenFullPage(ITestResult arg) {
        HashMap<String, String> myReport = new HashMap<>();
        try {
            final Screenshot screenshot = new AShot()
                    .shootingStrategy(ShootingStrategies.viewportPasting(100))
                    .takeScreenshot(driverLocal);
            final BufferedImage image = screenshot.getImage();
            String myProject = System.getProperty("user.dir");
            String path = myProject + File.separator + "target" + File.separator + "Screenshots";
            File file = new File(path);
            if (!file.exists()) {
                file.mkdir();
            }
            SimpleDateFormat format = new SimpleDateFormat("HH_mm_dd-MM-yyyy");
            String dateString = format.format(new Date());
            ImageIO.write(image, "PNG", new File(path + File.separator +
                    arg.getName() + "_" + dateString + ".png"));
            myReport.put("Base64", new String(Base64.encodeBase64(screenshot.toString().getBytes())));
            myReport.put("path", path + File.separator + arg.getName() + "_" + dateString + ".png");
            myReport.put("ShotPath", arg.getName() + "_" + dateString + ".png");
        } catch (IOException ex) {
            System.out.println("IOException : " + ex.getMessage());
        }
        return myReport;
    }

    public String TakeScreenshot(WebDriver driver, String screenshotName) throws IOException {
        String dateName = new SimpleDateFormat("yyyy_MM_dd-hh-mm-ss").format(new Date());
        TakesScreenshot ts = (TakesScreenshot) driver;
        File source = ts.getScreenshotAs(OutputType.FILE);
        String destination = System.getProperty("user.dir") + "/Screenshots/" + screenshotName + dateName + ".png";
        File finalDestination = new File(destination);
        FileUtils.copyFile(source, finalDestination);
        return destination;
    }
}