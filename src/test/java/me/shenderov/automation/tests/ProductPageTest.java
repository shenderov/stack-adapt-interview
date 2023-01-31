package me.shenderov.automation.tests;

import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

@Test(groups = "product")
public class ProductPageTest extends BaseTest {

    @Test
    public void shouldDisplayProductTitle() {
        String productTitle = productPage.getProductTitle();
        assertEquals(productTitle, "CHRYSLER OPTICAL A D-FRAME IN TORTOISESHELL");
    }

    @Test
    public void shouldDisplayProductPrice() {
        String price = productPage.getProductPrice();
        assertEquals(price, "$375.00");
    }

}
