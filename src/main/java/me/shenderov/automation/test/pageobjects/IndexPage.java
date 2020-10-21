package me.shenderov.automation.test.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import me.shenderov.automation.test.AbstractPage;
import org.openqa.selenium.support.ui.ExpectedCondition;

public class IndexPage extends AbstractPage {

    /**
     * atLocator to be used in the constructor
     */
    private static final By atLocator = By.id("easycont");

    private static final String siteNameSelector = "site-name";
    private static final String siteSloganSelector = "site-slogan";

    public IndexPage(WebDriver driver) {
        super(driver, atLocator);
    }

    public IndexPage(WebDriver driver, ExpectedCondition<?> condition) {
        super(driver, condition);
    }

    public String getSiteName() {
        return byId(siteNameSelector).getText();
    }

    public String getSiteSlogan() {
        return byId(siteSloganSelector).getText();
    }

    public TopNavBarPage getTopNavBar() {
        return new TopNavBarPage(driver);
    }

    public MenuListPage getMenuList() {
        return new MenuListPage(driver);
    }

    public WelcomePage getWelcomePage() {
        return new WelcomePage(driver);
    }

}
