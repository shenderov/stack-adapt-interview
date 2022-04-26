package me.shenderov.automation.tools;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Objects;
import java.util.Properties;
import java.util.stream.Collectors;

/**
 * Driver manager. It will download and set up web driver according to OS and selected browser
 *
 * @author Konstantin Shenderov
 *
 */
public class DriverManager {

    /**
     * Web driver instance to be initialized
     */
    private WebDriver driver = null;

    /**
     * Browserstack url. Will be used for Browserstack driver type
     */
    private String BROWSERSTACK_URL;

    /**
     * Selenium Grid/Hub url. Will be used for hub driver type
     */
    private String HUB_URL;

    /**
     * The default constructor. Will call the setDriver method to initialize web driver
     */
    public DriverManager() throws Exception {
        setDriver();
    }

    /**
     * Will download the web driver and set up in the project.
     */
    private void setDriver() throws Exception {
        String driverType = System.getProperty("driverType"); //get web driver type from system parameter
        if (driver == null) {
            switch (driverType) {
                case "chrome":
                    WebDriverManager.chromedriver().setup();
                    ChromeOptions options = new ChromeOptions();
                    options.addArguments("--no-sandbox");
                    options.addArguments("--disable-dev-shm-usage");
                    options.addArguments("--incognito");
                    options.setPageLoadStrategy(PageLoadStrategy.NONE);
                    driver = new ChromeDriver(options);
                    break;
                case "headless":
                    WebDriverManager.chromedriver().setup();
                    ChromeOptions headlessOptions = new ChromeOptions();
                    headlessOptions.addArguments("--headless");
                    headlessOptions.addArguments("--disable-gpu");
                    driver = new ChromeDriver(headlessOptions);
                    break;
                case "firefox":
                    WebDriverManager.firefoxdriver().setup();
                    driver = new FirefoxDriver();
                    break;
                case "opera":
                    WebDriverManager.operadriver().setup();
                    driver = new OperaDriver();
                    break;
                case "edge":
                    WebDriverManager.edgedriver().setup();
                    driver = new EdgeDriver();
                    break;
                case "ie":
                    WebDriverManager.iedriver().setup();
                    driver = new InternetExplorerDriver();
                    break;
                case "safari":
                    if(!System.getProperty("os.name").equals("Mac OS X"))
                        throw new Exception("Safari is only supported on Mac OS X");
                    driver = new SafariDriver();
                    break;
                case "browserstack":
                    setBrowserstackCredentials();
                    RemoteWebDriver remoteWebDriver = new RemoteWebDriver(new URL(BROWSERSTACK_URL), getCapabilities(driverType));
                    System.setProperty("remote.session.id", remoteWebDriver.getSessionId().toString());
                    driver = remoteWebDriver;
                    break;
                case "hub":
                    setHubUrl();
                    driver = new RemoteWebDriver(new URL(HUB_URL), getCapabilities(driverType));
                    break;
                default:
                    throw new Exception("Unknown Driver type. Supported types: chrome, firefox, opera, phantomjs, ie, edge, safari, browserstack, hub");
            }
        }
    }

    /**
     * Getter for the web driver
     */
    public WebDriver getDriver() {
        return driver;
    }

    /**
     * Set Selenium grid/hub URL from system properties
     */
    private void setHubUrl() throws Exception {
        if(System.getProperty("hubUrl") == null)
            throw new Exception("Missing system property 'hubUrl' ");
        HUB_URL = System.getProperty("hubUrl");
    }

    /**
     * Set Browserstack credentials and build URL from system properties
     */
    private void setBrowserstackCredentials() throws Exception {
        if(System.getProperty("browserstackUsername") == null)
            throw new Exception("Missing system property 'browserstackUsername' ");
        if(System.getProperty("browserstackKey") == null)
            throw new Exception("Missing system property 'browserstackKey' ");
        BROWSERSTACK_URL = String.format("https://%s:%s@hub-cloud.browserstack.com/wd/hub", System.getProperty("browserstackUsername"), System.getProperty("browserstackKey"));
    }

    /**
     * Get desired capabilities (configuration) according to driver type
     * @param driverType driver type
     */
    private DesiredCapabilities getCapabilities(String driverType) throws Exception {
        Properties capabilities = getCapabilitiesFromFile(driverType);
        if(capabilities != null){
            HashMap<String, String> map = new HashMap<>(capabilities.entrySet()
                    .stream()
                    .collect(Collectors.toMap(e -> e.getKey().toString(),
                            e -> e.getValue().toString())));
            return new DesiredCapabilities(map);
        }
        return null;
    }

    /**
     * Get desired capabilities (configuration) filename according to driver type
     * @param driverType driver type
     */
    private String getCapabilitiesFilename(String driverType) throws Exception {
        String capabilityFilename = System.getProperty("capabilitiesFilename");
        if(capabilityFilename == null)
            throw new Exception("Missing system property 'capabilitiesFilename' ");
        switch (driverType){
            case "browserstack":
                return String.format("capabilities/browserstack/%s.capabilities", capabilityFilename);
            case "hub":
                return String.format("capabilities/hub/%s.capabilities", capabilityFilename);
            default:
                return null;

        }
    }

    /**
     * Convert capabilities filename into properties object according to driver type
     * @param driverType driver type
     */
    private Properties getCapabilitiesFromFile(String driverType) throws Exception {
        String capabilitiesPath = getCapabilitiesFilename(driverType);
        Properties properties = null;
        if(capabilitiesPath != null){
            try {
                properties = new Properties();
                ClassLoader classLoader = getClass().getClassLoader();
                properties.load(Objects.requireNonNull(classLoader.getResource(capabilitiesPath)).openStream());
            } catch (IOException e) {
                throw new Exception(String.format("Cannot open capabilities file '%s'", capabilitiesPath));
            }
        }
        return properties;
    }
}
