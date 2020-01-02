import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import test.pageobjects.IndexPage;
import tools.DriverManager;
import tools.ScreenshotOnFail;

public abstract class BaseTest {

    private static String hostname = System.getProperty("hostname");
    private static String driverType = System.getProperty("driver_type");
    static WebDriver driver;
    static IndexPage indexPage;

    public ScreenshotOnFail screenShootRule = new ScreenshotOnFail(driver);

    @BeforeClass(alwaysRun = true)
    public void setup() throws Exception {
        DriverManager driverManager = new DriverManager();
        driver = driverManager.getDriver();
        if(!driverType.equals("phantomjs")){
            driver.manage().window().fullscreen();
        }
        indexPage = initializeTest();
    }

    @AfterClass(alwaysRun = true)
    public static void cleanup() {
        driver.quit();
    }

    private static IndexPage initializeTest() {
        driver.get(hostname);
        indexPage = new IndexPage(driver);
        return indexPage;
    }

}
