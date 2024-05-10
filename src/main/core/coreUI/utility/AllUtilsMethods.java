package core.coreUI.utility;

import net.bytebuddy.utility.RandomString;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import static java.lang.Math.abs;

public class AllUtilsMethods {

    public static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_FORMAT_NEW = "yyyy-MM-dd";

    private static final Random rnd = new Random(System.nanoTime());

    public static int getRandomNumberInRange(int min, int max) {
        if (min >= max) {
            throw new IllegalArgumentException("max must be greater than min");
        }
        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }

    public static String getRandomEMail() {
        ArrayList<String> firstName = new ArrayList<>();
        firstName.add("tesla");
        firstName.add("zeeman");
        firstName.add("curie");
        firstName.add("rayleigh");
        firstName.add("lenard");
        firstName.add("wien");
        firstName.add("dalen");
        firstName.add("compton");
        firstName.add("heisenberg");
        ArrayList<String> emailIndex = new ArrayList<>();
        emailIndex.add("@mymail.com");
        emailIndex.add("@sn.io");
        emailIndex.add("@yandex.com");
        emailIndex.add("@outlook.com");
        emailIndex.add("@email.com");
        return firstName.get(getRandomNumberInRange(0, firstName.size() - 1)) + getRandomNumberInRange(0, 50000) + emailIndex.get(getRandomNumberInRange(0, emailIndex.size() - 1));
    }

    public static String currentDate() {
        Date now = Calendar.getInstance().getTime();
        Calendar c = Calendar.getInstance();
        c.setTime(now);
        return format(c.getTime(), DATE_FORMAT);
    }

    public static String currentDatePlusDay(int day) {
        Date now = Calendar.getInstance().getTime();
        Calendar c = Calendar.getInstance();
        c.setTime(now);
        c.add(Calendar.DAY_OF_MONTH, +day);
        return format(c.getTime(), DATE_FORMAT_NEW);
    }

    public static Date parseDate(String date, String format) {
        if (date == null) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        try {
            return sdf.parse(date);
        } catch (ParseException e) {
            return null;
        }
    }

    public static String format(Date date, String format) {
        if (date == null) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }

    public String generateRandomMail(int length) {
        return "TEST_Email" + uniqueNumbers(length) + "@mail.com";
    }

    public String generateString(int length) {
        return RandomString.make(length);
    }

    public String uniqueIp() {
        return getRandomNumberInRange(1, 255) + "." + getRandomNumberInRange(1, 255) + "." + getRandomNumberInRange(1, 255) + "." + getRandomNumberInRange(1, 255);
    }

    public String randomPhone() {
        return "+37" + uniqueNumbers(9);
    }

    public String uniqueNumbers(int size) {
        StringBuilder sb = new StringBuilder(size);
        while (sb.length() < size) {
            long l = abs(rnd.nextLong());
            sb.append(l);
        }
        String s = sb.toString();
        return s.substring(0, size);
    }

    public static void renameFileUseJavaNewIO(String srcFilePath, String destFilePath) {
        try {
            if (srcFilePath != null && srcFilePath.trim().length() > 0 && destFilePath != null && destFilePath.trim().length() > 0) {
                /* Create the source Path instance. */
                Path srcPathObj = Paths.get(srcFilePath).toAbsolutePath();
                /* Create the target Path instance. */
                Path destPathObj = Paths.get(destFilePath + "\\allure-results").toAbsolutePath();
                /* Rename source to target, replace it if target exist. */
                Path targetPathObj = Files.move(srcPathObj, destPathObj, StandardCopyOption.COPY_ATTRIBUTES);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}