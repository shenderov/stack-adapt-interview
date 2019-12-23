package test.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import test.AbstractPage;

public class IndexPage extends AbstractPage {

    private WebElement container;

    public IndexPage(WebDriver driver) {
        super(driver);
        container = byTagName("body");
    }

    public String getSiteName() {
        return container.findElement(By.id("site-name")).getText();
    }

    public String getSiteSlogan() {
        return container.findElement(By.id("site-slogan")).getText();
    }

    public TopNavBarPage getTopNavBar() {
        return new TopNavBarPage(driver);
    }

    public MenuListPage getMenuList() {
        return new MenuListPage(driver);
    }

    public WelcomePage getWelcomePage() {
        return new WelcomePage(driver, container);
    }

}
