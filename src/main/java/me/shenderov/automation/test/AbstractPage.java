package me.shenderov.automation.test;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.function.BooleanSupplier;

/**
 * This is the base class for all page objects and functionalities.
 *
 * @author Konstantin Shenderov
 *
 */
public abstract class AbstractPage {

    /**
     * Holds a reference to the {@code WebDriver} object to access the DOM from all page objects methods.
     * Required to be provided by all subclasses
     */
    protected WebDriver driver;

    /**
     * Uses by waitForAtLocator method to identify whether the web page loaded or not.
     * The {@code By} object should be a locator to the unique element on the web page.
     * Visibility of the element on the web page will indicate that the page loading is complete.
     * For more complex cases "condition" variable can be used instead.
     * This will be also used for "at" method to identify that the browser at the current page.
     */
    protected By atLocator;

    /**
     * Uses by waitForAtLocator method to identify whether the web page loaded or not.
     * The {@code ExpectedCondition} object should be a condition that satisfied when the page is loaded.
     * This variable can be used as an alternative to atLocator when all elements are loaded before
     * the page is loaded(e.g. page is loaded but a data table is being fetched).
     * This will be also used for "at" method to identify that the browser at the current page
     * if atLocator is not provided.
     */
    protected ExpectedCondition<?> condition;

    /**
     * Timeout for conditional checks
     */
    private static final Duration TIMEOUT = Duration.ofSeconds(20);

    /**
     * Most basic constructor for classes, where condition and atLocator is not needed.
     * @param driver WebDriver instance.
     */
    public AbstractPage(WebDriver driver) {
        this.driver = driver;
    }

    /**
     * Constructor with atLocator
     * @param driver WebDriver instance.
     * @param atLocator locator to a unique element on the web page.
     */
    public AbstractPage(WebDriver driver, By atLocator) {
        this.driver = driver;
        this.atLocator = atLocator;
        waitForAtLocator();
    }

    /**
     * Constructor with condition
     * @param driver WebDriver instance.
     * @param condition condition to be satisfied when the page is loaded.
     */
    public AbstractPage(WebDriver driver, ExpectedCondition<?> condition) {
        this.driver = driver;
        this.condition = condition;
        waitForAtCondition();
    }

    /**
     * Returns the WebElement when it is present on the web page.
     * @param id String with the element ID.
     * @return WebElement.
     */
    protected WebElement byId(String id) {
        return (new WebDriverWait(driver, TIMEOUT)).until(ExpectedConditions.presenceOfElementLocated(By.id(id)));
    }

    /**
     * Returns the WebElement when it is present on the web page.
     * @param className String with the element class name.
     * @return WebElement.
     */
    protected WebElement byClassName(String className) {
        return (new WebDriverWait(driver, TIMEOUT)).until(ExpectedConditions.presenceOfElementLocated(By.className(className)));
    }

    /**
     * Returns the WebElement when it is present on the web page.
     * @param tagName String with the element tag name.
     * @return WebElement.
     */
    protected WebElement byTagName(String tagName) {
        return (new WebDriverWait(driver, TIMEOUT)).until(ExpectedConditions.presenceOfElementLocated(By.tagName(tagName)));
    }

    /**
     * Returns the WebElement when it is present on the web page.
     * @param locator By locator of the element.
     * @return WebElement.
     */
    protected WebElement byLocator(By locator) {
        return (new WebDriverWait(driver, TIMEOUT)).until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    /**
     * Returns the WebElement when it is present on the web page.
     * @param cssName String with the element css selector.
     * @return WebElement.
     */
    protected WebElement byCss(String cssName) {
        return (new WebDriverWait(driver, TIMEOUT)).until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(cssName)));
    }

    /**
     * Returns the WebElement when it is present on the web page.
     * @param xpath String with the element xpath.
     * @return WebElement.
     */
    protected WebElement byXpath(String xpath) {
        return (new WebDriverWait(driver, TIMEOUT)).until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpath)));
    }

    /**
     * Wait until web element is visible and then return WebElement object.
     * @param element WebElement to wait for.
     * @param sec timeout in seconds
     * @return WebElement.
     */
    public WebElement waitUntilElementIsVisible(WebElement element, int sec) {
        return (new WebDriverWait(driver, Duration.ofSeconds(sec))).until(ExpectedConditions.visibilityOf(element));
    }

    /**
     * Wait until web element is visible and then return WebElement object.
     * @param locator By locator of the element to wait for.
     * @param sec timeout in seconds
     * @return WebElement.
     */
    public WebElement waitUntilElementIsVisibleByLocator(By locator, int sec) {
        return Functionalities.getTestFunctionality(driver).waitUntilElementIsVisible(locator, sec);
    }

    /**
     * Wait until condition is satisfied.
     * @param condition condition to wait for.
     * @param timeoutMs timeout in ms
     */
    public void waitUntil (BooleanSupplier condition, long timeoutMs) {
        long start = System.currentTimeMillis();
        while (!condition.getAsBoolean()){
            if (System.currentTimeMillis() - start > timeoutMs ){
                throw new TimeoutException(String.format("Condition not meet within %s ms",timeoutMs));
            }
        }
    }

    /**
     * Simple unconditional wait
     * @param seconds time in seconds
     */
    public void wait(int seconds) {
        try {
            Thread.sleep(Duration.ofSeconds(seconds).toMillis());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Simple unconditional wait
     * @param millis time in ms
     */
    public void waitMillis(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Checks that either the atLocator is present on the web page or the condition is satisfied.
     * If atLocator is not null it will be used for validation.
     * If atLocator is null but condition is not null, condition will be examined.
     * If both atLocator and condition are null, false will be returned.
     * @return WebElement.
     */
    public boolean at(){
        if(atLocator != null)
            return driver.findElements(atLocator).size() > 0;
        else if (condition != null){
            try {
                Functionalities.getTestFunctionality(driver).waitForCondition(condition, 10);
                return true;
            } catch (org.openqa.selenium.TimeoutException e) {
                e.printStackTrace();
                return false;
            }
        }
        return false;
    }

    /**
     * Get Element by locator and hover over it
     * @param locator By locator of the element to hover over
     */
    public void hoverOverElement(By locator) {
        Functionalities.getTestFunctionality(driver).hoverOverElement(byLocator(locator));
    }

    /**
     * Wait until web element of atLocator is visible on the web page. Used for constructor only so the method is private.
     */
    private void waitForAtLocator(){
        Functionalities.getTestFunctionality(driver).waitUntilElementIsVisible(atLocator, 60);
    }

    /**
     * Wait condition is satisfied on the web page. Used for constructor only so the method is private.
     */
    private void waitForAtCondition(){
        Functionalities.getTestFunctionality(driver).waitForCondition(condition, 60);
    }

}
