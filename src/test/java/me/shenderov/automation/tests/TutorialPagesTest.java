package me.shenderov.automation.tests;

import me.shenderov.automation.test.pageobjects.tutorials.JavaTutorialPage;
import me.shenderov.automation.test.pageobjects.tutorials.ReactTutorialPage;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

@Test(groups = "tutorials")
public class TutorialPagesTest extends BaseTest {

    //private IndexPageTest indexPageTest;
    private JavaTutorialPage javaTutorialPage;
    private ReactTutorialPage reactTutorialPage;

    @BeforeClass
    public void setVariables() {
        //indexPageTest = new
    }

    @Test
    public void validateJavaTutorialPage() {
        //navigate to Java Tutorial page
        indexPage.getNavBar().clickOnTutorialsDropdown().selectItemInTutorialDropdown("Learn Java");
        javaTutorialPage = new JavaTutorialPage(driver);
        assertEquals(javaTutorialPage.getPageHeading(), "Java Tutorial");
    }

    @Test(dependsOnMethods = "validateJavaTutorialPage")
    public void validateReactTutorialPage() {
        //navigate to React Tutorial page
        javaTutorialPage.getNavBar().clickOnTutorialsDropdown().selectItemInTutorialDropdown("Learn React");
        reactTutorialPage = new ReactTutorialPage(driver);
        assertEquals(reactTutorialPage.getPageHeading(), "React Tutorial");
    }
}
