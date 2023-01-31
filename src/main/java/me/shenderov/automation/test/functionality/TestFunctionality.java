package me.shenderov.automation.test.functionality;

import me.shenderov.automation.test.AbstractPage;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * This is test functionality - set of helpers to use in test flow or when needed in page objects
 *
 * @author Konstantin Shenderov
 *
 */
public class TestFunctionality extends AbstractPage {

    /**
     * The basic constructor. As functionalities operated on fully loaded elements there is no reason to use any wait
     * @param driver WebDriver instance.
     */
    public TestFunctionality(WebDriver driver) {
        super(driver);
    }

    /**
     * Hover over element and optional click
     * @param element web element to hover
     * @param click click on element after hovering if true
     */
    public void hoverOverElement(WebElement element, boolean click) {
        Actions actions = new Actions(driver);
        actions.moveToElement(element);
        if(click){
            actions.click();
        }
        actions.build().perform();
    }

    /**
     * Hover over element
     * @param element web element to hover
     */
    public void hoverOverElement(WebElement element) {
        hoverOverElement(element, false);
    }

    /**
     * Scroll to make an element visible on the screen
     * @param element web element to scroll to
     */
    public void scrollToElement(WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView(true);", element);
    }

    /**
     * Scroll to make an element visible on the screen
     * @param locator locator of web element to scroll to
     */
    public void scrollToElement(By locator) {
        scrollToElement(driver.findElement(locator));
    }

    /**
     * Wait until an element is present on the web page and return it
     * @param locator locator of web element to wait for
     * @param sec timeout in seconds
     * @return WebElement when it is present
     */
    public WebElement waitUntilElementIsLocated(By locator, int sec) {
        return (new WebDriverWait(driver, Duration.ofSeconds(sec))).until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    /**
     * Wait until an element is visible on the web page and return it
     * @param locator locator of web element to wait for
     * @param sec timeout in seconds
     * @return WebElement when it is visible
     */
    public WebElement waitUntilElementIsVisible(By locator, int sec) {
        return (new WebDriverWait(driver, Duration.ofSeconds(sec))).until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    /**
     * Wait until an element is visible on the web page and return it
     * @param el element to wait for
     * @param sec timeout in seconds
     * @return WebElement when it is visible
     */
    public WebElement waitUntilElementIsVisible(WebElement el, int sec) {
        return (new WebDriverWait(driver, Duration.ofSeconds(sec))).until(ExpectedConditions.visibilityOf(el));
    }

    /**
     * Wait until an element is invisible on the web page
     * @param locator locator of web element to wait for
     * @param sec timeout in seconds
     */
    public boolean waitUntilElementIsInvisible(By locator, int sec) {
        return (new WebDriverWait(driver, Duration.ofSeconds(sec))).until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }

    /**
     * Wait until an element is invisible on the web page
     * @param el element to wait for
     * @param sec timeout in seconds
     */
    public boolean waitUntilElementIsInvisible(WebElement el, int sec) {
        return (new WebDriverWait(driver, Duration.ofSeconds(sec))).until(ExpectedConditions.invisibilityOf(el));
    }

    /**
     * Wait until an element is clickable on the web page and return it
     * @param locator locator of web element to wait for
     * @param sec timeout in seconds
     * @return WebElement when it is clickable
     */
    public WebElement waitUntilElementIsClickable(By locator, int sec) {
        return (new WebDriverWait(driver, Duration.ofSeconds(sec))).until(ExpectedConditions.elementToBeClickable(locator));
    }

    /**
     * Wait until an element is clickable on the web page and return it
     * @param el web element to wait for
     * @param sec timeout in seconds
     * @return WebElement when it is clickable
     */
    public WebElement waitUntilElementIsClickable(WebElement el, int sec) {
        return (new WebDriverWait(driver, Duration.ofSeconds(sec))).until(ExpectedConditions.elementToBeClickable(el));
    }

    /**
     * Wait until an element is selected on the web page and return true when it's selected
     * @param locator locator of web element to be selected
     * @param sec timeout in seconds
     * @return WebElement when it is clickable
     */
    public boolean waitUntilElementIsSelected(By locator, int sec) {
        return (new WebDriverWait(driver, Duration.ofSeconds(sec))).until(ExpectedConditions.elementToBeSelected(locator));
    }

    /**
     * Wait until an element is selected on the web page and return true when it's selected
     * @param el web element to be selected
     * @param sec timeout in seconds
     * @return WebElement when it is clickable
     */
    public boolean waitUntilElementIsSelected(WebElement el, int sec) {
        return (new WebDriverWait(driver, Duration.ofSeconds(sec))).until(ExpectedConditions.elementToBeSelected(el));
    }

    /**
     * Wait until condition is satisfied
     * @param condition to be satisfied
     * @param sec timeout in seconds
     */
    public void waitForCondition(ExpectedCondition<?> condition, long sec) {
        new WebDriverWait(driver, Duration.ofSeconds(sec)).until(condition);
    }

    /**
     * Refresh current web page
     */
    public void refreshWebPage() {
        driver.navigate().refresh();
    }

    /**
     * Get item from local storage
     * @param key key to get from storage
     * @return String item
     */
    public String getItemFromLocalStorage(String key){
        JavascriptExecutor js = (JavascriptExecutor) driver;
        return (String) js.executeScript(String.format("return window.localStorage.getItem('%s');", key));
    }

}
