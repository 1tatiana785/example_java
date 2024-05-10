package core.helper;

import org.openqa.selenium.By;

import java.util.HashMap;
import java.util.Map;

public class ConstantPage {

    public static By loader = By.cssSelector(".app-preloader");
    public static By planMenu = By.cssSelector(".header__upgrade");
    public static By ownerMenu = By.cssSelector(".snovHeader__profile");
    public static By credits = By.cssSelector(".snovProfile__menu .snovProfile__limit:first-child>span");
    public static By logoutBtn = By.cssSelector(".snovProfile__menu .snovProfile__link:last-child");
    public static By languageLocator = By.cssSelector(".snovProfile--open .snovProfile__locales");
    public static By goToMenuVerify = By.cssSelector(".snovHeader a[href$='verify/individual-emails']");
    public static By goToBEV = By.xpath("//div[@class='snovAside__submenu']//*[contains(text(),'Bulk Email Verification')]");


    public static final String LANG_EN = "En";
    public static final String LANG_PT = "Br";
    public static final String LANG_ZH = "Zh";
    public static final String LANG_UA = "Ua";
    public static final String LANG_ES = "Es";

    public static final String[] LANG = {LANG_EN, LANG_PT, LANG_ZH, LANG_UA, LANG_ES};

    public static String ExportSuccessful = "Export successful";

    public static Map<String, String> FileEmpty = new HashMap<>();
    {
        FileEmpty.put(LANG_EN, "It looks like you're trying to upload an empty file. Please pick another file to continue.");
        FileEmpty.put(LANG_PT, "Parece que você está tentando fazer upload de um arquivo vazio. Escolha outro arquivo para continuar.");
        FileEmpty.put(LANG_ZH, "您似乎正在尝试上传一个空文件。 请选择另一个文件继续上传。");
        FileEmpty.put(LANG_UA, "Файл, який ви намагаєтеся завантажити, пустий. Будь ласка, виберіть інший файл.");
    }
}