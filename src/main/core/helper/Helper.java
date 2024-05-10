package core.helper;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import core.coreUI.ui.seleniumElement.Windows;
import io.qameta.allure.Step;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static core.helper.Files.*;
import static org.testng.Assert.assertEquals;

public class Helper {

    public static final String SYMBOLS = "ABCDEFGHIJKLNOPRSTQUYWXYZabcdefghijklmnopqrstuvwxyz1234567890";
    public static final String[] NAMES = {"Noah", "James", "Ann", "Anna", "Alex", "William", "Benjamin", "Jacob",
            "Michael", "Sophia", "Emma", "Emily", "Isabella", "Olivia", "Henry", "Sam", "Stella"};
    public static final String[] DOMAINS_DOT = {".com", ".ua", ".net", ".io", ".co", ".bn", ".uk", ".org", ".com.au"};
    public static final String[] DOMAINS = {"@gmail.com", "@i.ua", "@mail.com", "@yandex.com", "@yandex.ru",
            "@gov.uk", "@delltechnologies.com", "@yopmail.com", "@weswap.com", "@viavisolutions.com"};


    private XSSFWorkbook book;
    Windows windows = new Windows();

    public static String generateString() {
        return UUID.randomUUID().toString();
    }

    public static Date Date() {
        return new Date();
    }

    // current time
    @Step("Generate current date format 'HHmmss-ddMMyyyy'")
    public static String generateStringDateTime() {
        return new SimpleDateFormat("HHmmss-ddMMyyyy").format(Calendar.getInstance().getTime());
    }

    @Step("Generate current date format 'dd-MM-yyyy'")
    public static String generateDate() {
        return new SimpleDateFormat("dd MMM yyyy").format(Date());
    }

    public static String generateDate1() {
        return new SimpleDateFormat("d MMM yyyy").format(Date());
    }

    public static int getRandomNumberInRange(int min, int max) {
        if (min >= max) {
            throw new IllegalArgumentException("max must be greater than min");
        }
        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }

    public static String setSymbols(int nameLength) {
        String name = "";
        int i;
        for (i = 0; i < nameLength; i++) {
            int number1 = getRandomNumberInRange(0, 35);
            name = name + SYMBOLS.charAt(number1);
        }
        return name;
    }

    public static String setNumber() {
        String s = "1234";
        String b = String.valueOf(Integer.parseInt(s));
        int i;
        for (i = 0; i < 6; i++) {
            b = String.valueOf(Integer.parseInt(s) + i);
            System.out.println(b);
        }
        return b;
    }

    public static String random(String[] data) {
        String name = "";
        ArrayList<String> emailIndex = new ArrayList<>();
        for (String x : data) {
            emailIndex.add(x);
        }
        return name + emailIndex.get(getRandomNumberInRange(0, emailIndex.size() - 1));
    }

    public static String randomEMail(int nameLength) {
        String email = setSymbols(nameLength) + random(DOMAINS);
        return email;
    }

    public static String randomDomain(int nameLength) {
        String domain = setSymbols(nameLength) + random(DOMAINS_DOT);
        return domain;
    }

    public static String randomReallyDomain() {
        String domain = random(DOMAINS);
        return domain;
    }

    public static String randomReallyName() {
        String name = random(NAMES);
        return name;
    }

    @Step("Set first, last name and domain in file")
    public static String setRandomEmails(int countEmails, int nameLength) {
        String emails = "";
        int i;
        for (i = 0; i < countEmails; i++) {
            emails = emails + "\n" + Helper.randomEMail(nameLength);
        }
        return emails;
    }

    @Step("Set first, last name and domain in file")
    public static String setRandomFirstNameLastNameDomain(int countEmails, int nameLength) {
        String data = "";
        int i;
        for (i = 0; i < countEmails; i++) {
            data = data + "\n" + Helper.randomReallyName() + "," + Helper.setSymbols(nameLength) + "," + Helper.randomReallyDomain();
        }
        return data;
    }

    public Helper setEmailInFile(int countEmails, int nameLength) {
        try (FileWriter writer = new FileWriter(getFilePath(DataBEV), false)) {
            String email = setRandomEmails(countEmails, nameLength);
            writer.write(email);
        } catch (IOException ex) {
        }
        return this;
    }

    public Helper setDataBESInFile(int countEmails, int nameLength) {
        try (FileWriter writer = new FileWriter(getFilePath(DataSES), false)) {
            String data = setRandomFirstNameLastNameDomain(countEmails, nameLength);
            writer.write(data);
        } catch (IOException ex) {
        }
        return this;
    }

    public Helper readFromXlsxFile(String fileName, String columnName, int rows) throws Exception {
        File j = new File(getDownloadPath(fileName));
        while (!j.exists() && !j.isFile()) {
            windows.implicitlyWait(30, TimeUnit.MILLISECONDS);
        }
        File file = new File(getDownloadPath(fileName));
        try {
            OPCPackage pkg = OPCPackage.open(file);
            book = new XSSFWorkbook(pkg);
            pkg.close();
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String sheetName = book.getSheetAt(0).getSheetName().toString();
        XSSFSheet sheet = book.getSheet(sheetName);
        int f = sheet.getPhysicalNumberOfRows();
        assertEquals(f, rows);
        String cell1 = sheet.getRow(0).getCell(0).getStringCellValue();
        String cell2 = sheet.getRow(0).getCell(1).getStringCellValue();
        String value = cell1 + "," + cell2;
        assertEquals(value, columnName);
        return this;
    }

    @Step("Read from file: column info, number rows")
    public Helper readFromFile(String filename, String columnName, int rows) throws Exception {
        File j = new File(getDownloadPath(filename));
        while (!j.exists() && !j.isFile()) {
            windows.implicitlyWait(80, TimeUnit.MILLISECONDS);
        }
        FileReader fr = new FileReader(getDownloadPath(filename));
        LineNumberReader reader = new LineNumberReader(new FileReader(getDownloadPath(filename)));
        int cnt = 0;
        String lineRead = "";
        while ((lineRead = reader.readLine()) != null) {
        }
        cnt = reader.getLineNumber();
        assertEquals(cnt, rows);
        fr.close();
        return this;
    }

    @Step("Get last modified file in directory")
    public String getLastModifiedFile(String filename) {
        File f = new File(getDownloadPath(filename));
        while (!f.exists()) {
            windows.implicitlyWait(30, TimeUnit.MILLISECONDS);
        }
        File[] files = new File(DOWNLOAD_PATH).listFiles();
        if (files.length == 0) return null;
        Arrays.sort(files, new Comparator<File>() {
            public int compare(File o1, File o2) {
                return new Long(o2.lastModified()).compareTo(o1.lastModified());
            }
        });
        String[] fileName = files[0].toString().split("/");
        return fileName[3];
    }

    @Step("Check export file")
    public Helper deleteFile(String path) {
        File file = new File(getDownloadPath(path));
        file.delete();
        return this;
    }

    @Step("Get all files in directory")
    public String getAllFilesInDirectory(String filename) {
        String files = "";
        File originalFile = new File(getFilePath(filename));
        File folder = originalFile.getParentFile();
        for (File file : folder.listFiles()) {
            System.out.println(file.getName());
            files = file.getName();
        }
        return files;
    }

    @Step("Check export file")
    public static String checkFile(String filename) {
        int i;
        String file = "";
        for (i = 1; i < 50; i++) {
            File f = new File(getDownloadPath(filename) + " (" + i + ").csv");
            if (f.exists() && f.isFile()) {
                file = f.getParent();
                System.out.println(file);
            } else {
                break;
            }
        }
        return file;
    }
}