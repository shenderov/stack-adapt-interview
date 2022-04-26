package me.shenderov.automation.tests;

import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

@Test(groups = "index")
public class IndexPageTest extends BaseTest {

    @Test
    public void validateSiteHeader() {
        String siteName = indexPage.getSiteHeader();
        assertEquals(siteName, "Learn to Code");
    }

    @Test
    public void validateSiteSubheader() {
        String subheader = indexPage.getSiteSubheader();
        assertEquals(subheader, "With the world's largest web developer site.");
    }

}
