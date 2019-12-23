import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class IndexPageTest extends BaseTest {

    @Test
    public void validateSiteName() {
        String siteName = indexPage.getSiteName();
        assertEquals(siteName, "Selenium Easy");
    }

    @Test
    public void validateSiteSlogan() {
        String slogan = indexPage.getSiteSlogan();
        assertEquals(slogan, "Complete Automation Testing Tutorials");
    }

}
