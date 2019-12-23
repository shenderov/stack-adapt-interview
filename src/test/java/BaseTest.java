import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import test.pageobjects.IndexPage;
import tools.DriverManager;
import tools.ScreenshotOnFail;

public abstract class BaseTest {

    private static String hostname = System.getProperty("hostname");
    static WebDriver driver;
    static IndexPage indexPage;

    public ScreenshotOnFail screenShootRule = new ScreenshotOnFail(driver);

    @BeforeClass
    public void setup() throws Exception {
        DriverManager driverManager = new DriverManager();
        driver = driverManager.getDriver();
        driver.manage().window().fullscreen();
        indexPage = initializeTest();
    }

    @AfterClass
    public static void cleanup() {
        driver.quit();
    }

    private static IndexPage initializeTest() {
        driver.get(hostname);
        indexPage = new IndexPage(driver);
        return indexPage;
    }

}
