package me.shenderov.automation.test.pageobjects;

import me.shenderov.automation.test.AbstractPage;
import me.shenderov.automation.test.Functionalities;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class SimpleFormDemoPage extends AbstractPage {

    /**
     * atLocator to be used in the constructor
     */
    private static final By atLocator = By.cssSelector("#easycont > div > div.col-md-6.text-left");

    private static final String headerSelector = "div.col-md-6.text-left > h3";
    private static final String messageFieldSelector = "user-message";
    private static final String showMessageButtonSelector = "#get-input > button";
    private static final String textDisplaySelector = "display";

    public SimpleFormDemoPage(WebDriver driver) {
        super(driver, atLocator);
        Functionalities.getTestFunctionality(driver).scrollToElement(byLocator(atLocator));
    }

    public String getHeader() {
        return byCss(headerSelector).getText();
    }

    public void setMessage(String message) {
        byId(messageFieldSelector).sendKeys(message);
    }

    public void clickOnShowMessage() {
        byCss(showMessageButtonSelector).click();
    }

    public String getMessage() {
        return byId(textDisplaySelector).getText();
    }
}
