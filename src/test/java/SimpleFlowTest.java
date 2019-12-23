import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import test.pageobjects.SimpleFormDemoPage;
import test.pageobjects.WelcomePage;

import static org.testng.Assert.assertEquals;

public class SimpleFlowTest extends BaseTest {

    private WelcomePage welcomePage;
    private SimpleFormDemoPage simpleFormDemoPage;

    @BeforeClass
    public void setVariables() {
        welcomePage = indexPage.getWelcomePage();
    }

    @Test
    public void validateHeader() {
        String header = welcomePage.getWelcomeHeader();
        assertEquals(header, "WELCOME TO SELENIUM EASY DEMO");
    }

    @Test(dependsOnMethods = "validateHeader")
    public void clickOnGetPracticing() {
        welcomePage = welcomePage.clickStartPracticing();
        String header = welcomePage.getBasicHeader();
        assertEquals(header, "BASIC EXAMPLES TO START WITH SELENIUM");
    }

    @Test(dependsOnMethods = "clickOnGetPracticing")
    public void clickSimpleFormDemo() {
        simpleFormDemoPage = welcomePage.clickOnSimpleFormDemo();
        String header = simpleFormDemoPage.getHeader();
        assertEquals(header, "This would be your first example to start with Selenium.");
    }

    @Test(dependsOnMethods = "clickSimpleFormDemo")
    public void validateSingleInputField() {
        String message = "This is my message";
        simpleFormDemoPage.setMessage(message);
        simpleFormDemoPage.clickOnShowMessage();
        assertEquals(simpleFormDemoPage.getMessage(), message);
    }
}
