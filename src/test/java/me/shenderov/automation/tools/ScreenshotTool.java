package me.shenderov.automation.tools;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Screenshot tool. Utilizes Selenium web driver methods
 *
 * @author Konstantin Shenderov
 *
 */
public class ScreenshotTool {

    /**
     * Screenshots directory
     */
    private static final String IMAGES_DIRECTORY = "build/images";

    /**
     * Take screenshot providing base filename.
     * @param driver WebDriver
     * @param filename base filename
     */
    public static void takeScreenshot(WebDriver driver, String filename){
        takeScreenshotAndSave(driver, getFilename(filename));
    }

    /**
     * Take screenshot providing ITestResult. Web driver and the base filename will be extracted from ITestResult
     * @param result ITestResult
     */
    public static void takeScreenshot(ITestResult result){
        WebDriver driver = (WebDriver) result.getTestContext().getAttribute("webDriver");
        takeScreenshotAndSave(driver, getFilename(result));
    }

    /**
     * Take screenshot providing class name. The base filename will be extracted from the class name
     * @param driver WebDriver
     * @param className class name
     */
    public static void takeScreenshot(WebDriver driver, Class<?> className){
        takeScreenshotAndSave(driver, getFilename(className));
    }

    /**
     * Internal(private) method that implements the screenshot and save file logic.
     * @param driver WebDriver
     * @param filename the base filename
     */
    private static void takeScreenshotAndSave(WebDriver driver, String filename){
        File directory = new File(IMAGES_DIRECTORY);
        if (! directory.exists()){
            boolean res = directory.mkdir();
            if(!res){
                throw new RuntimeException("Cannot create new directory");
            }
        }
        File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        try {
            Files.copy(scrFile.toPath(), new File(IMAGES_DIRECTORY + "/" + filename).toPath(), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ioe) {
            throw new RuntimeException(ioe);
        }
    }

    /**
     * Internal(private) helper method that extracts the class and method names from ITestResult and format it to the filename.
     * @param result ITestResult
     */
    private static String getFilename(ITestResult result) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd--HH.mm.ss");
        Date date = new Date();
        return String.format("%s.%s-%s.png", result.getTestClass().getRealClass().getSimpleName(), result.getName(), dateFormat.format(date));
    }

    /**
     * Internal(private) helper method that extracts the class name from className and format it to the filename.
     * @param className class name object
     */
    private static String getFilename(Class<?> className) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd--HH.mm.ss");
        Date date = new Date();
        return String.format("%s-%s.png", className.getSimpleName(), dateFormat.format(date));
    }

    /**
     * Internal(private) helper method that format the base filename it to the filename.
     * @param filename base filename as string
     */
    private static String getFilename(String filename) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd--HH.mm.ss");
        Date date = new Date();
        return String.format("%s-%s.png", filename, dateFormat.format(date));
    }
}
