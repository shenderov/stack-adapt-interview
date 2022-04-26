package me.shenderov.automation.test.pageobjects.tutorials;

import org.openqa.selenium.WebDriver;

/**
 * Java Tutorial specific methods can be defined here
 *
 * @author Konstantin Shenderov
 *
 */
public class JavaTutorialPage extends AbstractTutorialPage {

    private static final String pageHeading = "Java Tutorial";

    public JavaTutorialPage(WebDriver driver) {
        super(driver, pageHeading);
    }

}
