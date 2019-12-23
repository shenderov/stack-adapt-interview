package test.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class WelcomePage extends IndexPage {

    private By containerLocator = By.cssSelector("#easycont > div > div.col-md-6.text-left > div > div");
    private WebElement container;

    public WelcomePage(WebDriver driver) {
        super(driver);
        container = byLocator(containerLocator);
        scrollToElement(container);
    }

    public WelcomePage(WebDriver driver, WebElement parentContainer) {
        super(driver);
        container = parentContainer.findElement(containerLocator);
        scrollToElement(container);
    }

    public String getWelcomeHeader() {
        return container.findElement(By.cssSelector("#home > h3")).getText();
    }

    public String getBasicHeader() {
        return container.findElement(By.cssSelector("#basic > h3")).getText();
    }

    public WelcomePage clickStartPracticing() {
        container.findElement(By.id("btn_basic_example")).click();
        waitUntilElementIsVisible(container.findElement(By.cssSelector("#basic > h3")), 10);
        return this;
    }

    public SimpleFormDemoPage clickOnSimpleFormDemo() {
        container.findElement(By.cssSelector("#basic > div > a:nth-child(1)")).click();
        waitUntilElementIsVisible(byCss("#easycont > div > div.col-md-6.text-left > h3"), 10);
        return new SimpleFormDemoPage(driver);
    }

}
