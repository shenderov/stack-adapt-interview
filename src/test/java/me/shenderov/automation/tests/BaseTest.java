package me.shenderov.automation.tests;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import me.shenderov.automation.test.pageobjects.IndexPage;
import me.shenderov.automation.tools.DriverManager;
import me.shenderov.automation.tools.TestListener;

/**
 * This is the base class for all test classes. In initialized and stores web driver instance
 * and have common methods and variables
 *
 * @author Konstantin Shenderov
 *
 */
@Listeners(TestListener.class)
public abstract class BaseTest {

    /**
     * Base URL. Can be accessed from this class only for test initialization
     */
    private static final String baseUrl = System.getProperty("baseUrl");

    /**
     * Driver type. Values: chrome, phantomjs, firefox, opera, ie, edge, browserstack, hub
     */
    protected static String driverType = System.getProperty("driverType");

    /**
     * Web driver instance to be used for all tests and page objects(will be propagated by tests).
     */
    protected static WebDriver driver;

    /**
     * Index Page object, this object will be initiated by default when a browser opened.
     */
    protected static IndexPage indexPage;

    /**
     * This method runs before every test class. It will initialize web driver and navigate to the index page.
     * @param context this object will be automatically provided by TestNG and needed for the test listener(me.shenderov.automation.tools.TestListener).
     */
    @BeforeClass(alwaysRun = true)
    public void setup(ITestContext context) throws Exception {
        try {
            DriverManager driverManager = new DriverManager();
            driver = driverManager.getDriver(); // Initialize web driver using driver manager
            if(driver != null) { // if the web driver initialized, set it to ITestContext and initialize test
                context.setAttribute("webDriver", driver);
                indexPage = initializeTest();
            }
        } catch (Exception e) { // if something went wrong, cleanup and throw exception
            e.printStackTrace();
            cleanup();
            throw e;
        }
    }

    /**
     * This method runs after every test class. It will properly close the driver.
     */
    @AfterClass(alwaysRun = true)
    public static void cleanup() {
        try {
            driver.close();
            driver.quit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * This method extends the setup method to set up the browser parameters and get open the base URL.
     */
    private IndexPage initializeTest() {
        Dimension dimension = new Dimension(1920, 1080);
        driver.manage().window().setSize(dimension);
        driver.get(baseUrl);
        indexPage = new IndexPage(driver);
        return indexPage;
    }
}
