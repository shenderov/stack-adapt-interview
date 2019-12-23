package tools;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ScreenshotOnFail implements ITestListener {

    private WebDriver driver;

    public ScreenshotOnFail(WebDriver browser) {
        this.driver = browser;
    }

    @Override
    public void onTestStart(ITestResult result) {
    }

    @Override
    public void onTestSuccess(ITestResult result) {
    }

    @Override
    public void onTestFailure(ITestResult result) {
        File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        try {
            Files.copy(scrFile.toPath(), new File(getFilename(result)).toPath());
        } catch (IOException ioe) {
            throw new RuntimeException(ioe);
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
    }

    @Override
    public void onStart(ITestContext context) {
    }

    @Override
    public void onFinish(ITestContext context) {
    }

    private String getFilename(ITestResult result) {
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yy-HH-mm-ss");
        Date date = new Date();
        return String.format("images/%s-%s-%s.png", result.getName(), result.getMethod(), dateFormat.format(date));
    }
}
