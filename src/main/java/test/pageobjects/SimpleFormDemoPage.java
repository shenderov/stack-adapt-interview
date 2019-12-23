package test.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import test.AbstractPage;

public class SimpleFormDemoPage extends AbstractPage {

    private By containerLocator = By.cssSelector("#easycont > div > div.col-md-6.text-left");
    private WebElement container;

    public SimpleFormDemoPage(WebDriver driver) {
        super(driver);
        container = byLocator(containerLocator);
        scrollToElement(container);
    }

    public String getHeader() {
        return container.findElement(By.cssSelector("div.col-md-6.text-left > h3")).getText();
    }

    public void setMessage(String message) {
        container.findElement(By.id("user-message")).sendKeys(message);
    }

    public void clickOnShowMessage() {
        container.findElement(By.id("get-input")).findElement(By.className("btn-default")).click();
    }

    public String getMessage() {
        return container.findElement(By.xpath("//div[@id='user-message']")).findElement(By.id("display")).getText();
    }
}
