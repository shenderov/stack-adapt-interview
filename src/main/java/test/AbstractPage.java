package test;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AbstractPage {

    protected WebDriver driver;

    public AbstractPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    protected WebElement byId(String id) {
        return (new WebDriverWait(driver, 20)).until(ExpectedConditions.presenceOfElementLocated(By.id(id)));
    }

    protected WebElement byClassName(String className) {
        return (new WebDriverWait(driver, 20)).until(ExpectedConditions.presenceOfElementLocated(By.className(className)));
    }

    protected WebElement byTagName(String tagName) {
        return (new WebDriverWait(driver, 20)).until(ExpectedConditions.presenceOfElementLocated(By.tagName(tagName)));
    }

    protected WebElement byLocator(By locator) {
        return (new WebDriverWait(driver, 20)).until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    protected WebElement byCss(String cssName) {
        return (new WebDriverWait(driver, 20)).until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(cssName)));
    }

    protected void scrollToElement(WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView(true);", element);
    }

    public WebElement waitUntilElementIsVisible(WebElement element, int sec) {
        return (new WebDriverWait(driver, sec)).until(ExpectedConditions.visibilityOf(element));
    }

    public void wait(int seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
