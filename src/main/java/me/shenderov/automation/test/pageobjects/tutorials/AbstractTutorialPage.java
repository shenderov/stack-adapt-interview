package me.shenderov.automation.test.pageobjects.tutorials;

import me.shenderov.automation.test.AbstractPage;
import me.shenderov.automation.test.pageobjects.components.NavBarPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

/**
 * This is the base class for all tutorial pages.
 * All methods that can be used by all tutorial pages can be defined here
 *
 * @author Konstantin Shenderov
 *
 */
public abstract class AbstractTutorialPage extends AbstractPage {

    private static final By pageHeadingElement = By.cssSelector("#main h1");

    public AbstractTutorialPage(WebDriver driver, String pageHeading) {
        super(driver, ExpectedConditions.and(ExpectedConditions.visibilityOfElementLocated(pageHeadingElement),
                ExpectedConditions.textToBe(pageHeadingElement, pageHeading)));
    }

    public NavBarPage getNavBar(){
        return new NavBarPage(driver);
    }

    public String getPageHeading(){
        return byLocator(pageHeadingElement).getText();
    }
}
