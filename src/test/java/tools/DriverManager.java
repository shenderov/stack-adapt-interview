package tools;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;

public class DriverManager {
    private WebDriver driver = null;

    public DriverManager() throws Exception {
        setDriver();
    }

    private void setDriver() throws Exception {
        String driverType = System.getProperty("driverType");
        if (driver == null) {
            switch (driverType) {
                case "chrome":
                    WebDriverManager.chromedriver().setup();
                    driver = new ChromeDriver();
                    break;
                case "firefox":
                    WebDriverManager.firefoxdriver().setup();
                    driver = new FirefoxDriver();
                    break;
                case "opera":
                    WebDriverManager.operadriver().setup();
                    driver = new OperaDriver();
                    break;
                case "phantomjs":
                    WebDriverManager.phantomjs().setup();
                    driver = new PhantomJSDriver();
                    break;
                case "edge":
                    WebDriverManager.edgedriver().setup();
                    driver = new EdgeDriver();
                    break;
                case "ie":
                    WebDriverManager.iedriver().setup();
                    driver = new InternetExplorerDriver();
                    break;
                default:
                    throw new Exception("Unknown Driver type. Supported types: chrome, firefox, opera, phantomjs, ie, edge");
            }
        }
    }

    public WebDriver getDriver() {
        return driver;
    }
}
