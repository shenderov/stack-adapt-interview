package me.shenderov.automation.test;

import me.shenderov.automation.test.functionality.TestFunctionality;
import org.openqa.selenium.WebDriver;

/**
 * This class with allow one line access to functionalities. All new functionality classes should be added here
 *
 * @author Konstantin Shenderov
 *
 */
public class Functionalities {

    public static TestFunctionality getTestFunctionality(WebDriver driver){
        return new TestFunctionality(driver);
    }

}
