package me.shenderov.automation.test.pageobjects;

import me.shenderov.automation.test.AbstractPage;
import me.shenderov.automation.test.pageobjects.components.NavBarPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class IndexPage extends AbstractPage {

    /**
     * atLocator to be used in the constructor
     */
    private static final By atLocator = By.id("main");

    private static final String mainHeader = ".learntocodecontent h1";
    private static final String siteSloganSelector = ".learntocodecontent .learntocodeh3";

    public IndexPage(WebDriver driver) {
        super(driver, atLocator);
    }

    public NavBarPage getNavBar(){
        return new NavBarPage(driver);
    }

    public String getSiteHeader() {
        return byCss(mainHeader).getText();
    }

    public String getSiteSubheader() {
        return byCss(siteSloganSelector).getText();
    }

}
