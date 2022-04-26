package me.shenderov.automation.test.pageobjects.tutorials;

import org.openqa.selenium.WebDriver;

/**
 * React Tutorial specific methods can be defined here
 *
 * @author Konstantin Shenderov
 *
 */
public class ReactTutorialPage extends AbstractTutorialPage {

    private static final String pageHeading = "React Tutorial";

    public ReactTutorialPage(WebDriver driver) {
        super(driver, pageHeading);
    }

}
