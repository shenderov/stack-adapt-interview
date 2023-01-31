package me.shenderov.automation.test.pageobjects;

import me.shenderov.automation.test.AbstractPage;
import me.shenderov.automation.test.pageobjects.components.NavBarPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ProductPage extends AbstractPage {

    /**
     * atLocator to be used in the constructor
     */
    private static final By atLocator = By.className("product-detail-container");

    private static final By productTitle = By.className("product-title");
    private static final By productShortDescription = By.className("product-short-description");
    private static final By productPrice = By.cssSelector(".product-detail-price .money");
    private static final By productColours = By.className("product-colours");
    private static final By addToBasketButton = By.cssSelector(".product-add-to-basket button");

    public ProductPage(WebDriver driver) {
        super(driver, atLocator);
    }

    public NavBarPage getNavBar(){
        return new NavBarPage(driver);
    }

    public String getProductTitle() {
        return byLocator(productTitle).getText();
    }

    public String getProductShortDescription() {
        return byLocator(productShortDescription).getText();
    }

    public String getProductPrice() {
        return byLocator(productPrice).getText();
    }

    public ProductPage selectProductColourByClass(String productClass) {
        byLocator(productColours).findElement(By.cssSelector(String.format("li .%s", productClass))).click();
        return this;
    }

    public ProductPage selectProductColourByIndex(int index) {
        byLocator(productColours).findElements(By.tagName("li")).get(index).click();
        return this;
    }

    public String getAddButtonText() {
        return byLocator(addToBasketButton).getText();
    }

    public BasketPage clickOnAddButton() {
        byLocator(addToBasketButton).click();
        return new BasketPage(driver);
    }
}
