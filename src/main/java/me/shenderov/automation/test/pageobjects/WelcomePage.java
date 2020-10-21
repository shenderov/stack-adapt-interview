package me.shenderov.automation.test.pageobjects;

import me.shenderov.automation.test.Functionalities;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class WelcomePage extends IndexPage {

    private static final String welcomePageContainerSelector = "#easycont > div > div.col-md-6.text-left > div > div";
    private static final String welcomeHeaderSelector = "#home > h3";
    private static final String basicHeaderSelector = "#basic > h3";
    private static final String startPracticingButtonSelector = "btn_basic_example";
    private static final String simpleFormDemoLinkSelector = "#basic > div > a:nth-child(1)";

    public WelcomePage(WebDriver driver) {
        super(driver, ExpectedConditions.and(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(welcomePageContainerSelector)),
                                             ExpectedConditions.textToBe(By.cssSelector(welcomeHeaderSelector), "WELCOME TO SELENIUM EASY DEMO")));
        Functionalities.getTestFunctionality(driver).scrollToElement(byCss(welcomePageContainerSelector));
    }

    public String getWelcomeHeader() {
        return byCss(welcomeHeaderSelector).getText();
    }

    public String getBasicHeader() {
        return byCss(basicHeaderSelector).getAttribute("innerText");
    }

    public WelcomePage clickStartPracticing() {
        byId(startPracticingButtonSelector).click();
        Functionalities.getTestFunctionality(driver).waitMillis(500); //Wait due to animated transition
        return this;
    }

    public SimpleFormDemoPage clickOnSimpleFormDemo() {
        System.out.println(byCss(simpleFormDemoLinkSelector).getAttribute("innerHTML"));
        byCss(simpleFormDemoLinkSelector).click();
        return new SimpleFormDemoPage(driver);
    }

}
