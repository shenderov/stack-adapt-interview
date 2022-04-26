package me.shenderov.automation.test.pageobjects.components;

import me.shenderov.automation.test.AbstractPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class NavBarPage extends AbstractPage {

    public NavBarPage(WebDriver driver) {
        super(driver);
    }

    public NavBarPage clickOnTutorialsDropdown(){
        byId("navbtn_tutorials").click();
        waitUntilElementIsVisibleByLocator(By.id("nav_tutorials"), 5);
        return this;
    }

    public void selectItemInTutorialDropdown(String itemTitle){
        byXpath(String.format("//*[@id='nav_tutorials']/div/div/div/a[text()='%s']", itemTitle)).click();
    }
}
