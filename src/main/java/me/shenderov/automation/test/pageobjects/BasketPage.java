package me.shenderov.automation.test.pageobjects;

import me.shenderov.automation.test.AbstractPage;
import me.shenderov.automation.test.pageobjects.components.NavBarPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.Objects;
import java.util.Optional;

public class BasketPage extends AbstractPage {

    /**
     * atLocator to be used in the constructor
     */
    private static final By atLocator = By.className("cart-page");

    private static final By cartTable = By.id("cart-table");
    private static final By productTitle = By.className("cart-product-title");
    private static final By productId = By.className("cart-product-sku");
    private static final By productPrice = By.cssSelector(".product-detail-price .money");
    private static final By removeItemLink = By.className("cart-item__remove");
    private static final By quantityDropdownSelect = By.className("cart-product-quantity-select");
    private static final By quantityInputField = By.className("cart-product-quantity-value");
    private static final By proceedToCheckoutButton = By.cssSelector(".cart-summary button");

    public BasketPage(WebDriver driver) {
        super(driver, atLocator);
    }

    public NavBarPage getNavBar(){
        return new NavBarPage(driver);
    }

    public String getProductTitle(int itemIndex) {
        return getTableRowByIndex(itemIndex).findElement(productTitle).getText();
    }

    public String getProductTitle(String itemTitle) {
        return getTableRowByTitle(itemTitle).findElement(productTitle).getText();
    }

    public String getProductSCU(int itemIndex) {
        String sku = getTableRowByIndex(itemIndex).findElement(productId).getText();
        return sku.substring(sku.indexOf(" "));
    }

    public String getProductSCU(String itemTitle) {
        String sku = getTableRowByTitle(itemTitle).findElement(productId).getText();
        return sku.substring(sku.indexOf(" "));
    }

    public String getProductPrice(int itemIndex) {
        return getTableRowByIndex(itemIndex).findElement(productPrice).getText();
    }

    public String getProductPrice(String itemTitle) {
        return getTableRowByTitle(itemTitle).findElement(productPrice).getText();
    }

    public BasketPage clickOnRemoveItem(int itemIndex) {
        getTableRowByIndex(itemIndex).findElement(removeItemLink).click();
        return this;
    }

    public BasketPage clickOnRemoveItem(String itemTitle) {
        getTableRowByTitle(itemTitle).findElement(removeItemLink).click();
        return this;
    }

    public int getQuantityValue(int itemIndex) {
        return Integer.getInteger(getTableRowByIndex(itemIndex).findElement(quantityInputField).getAttribute("value"));
    }

    public int getQuantityValue(String itemTitle) {
        return Integer.getInteger(getTableRowByTitle(itemTitle).findElement(quantityInputField).getAttribute("value"));
    }

    public BasketPage setQuantityValue(int itemIndex, int value) {
        if(value < 1 || value > 10){
            throw new RuntimeException("Quantity value should be in range between 1 and 10");
        }
        WebElement element = getTableRowByIndex(itemIndex).findElement(quantityDropdownSelect);
        element.click();
        Objects.requireNonNull(element.findElements(By.tagName("option")).stream().filter(el -> el.getText().equals(String.valueOf(value))).findFirst().orElse(null)).click();
        return this;
    }

    public BasketPage setQuantityValue(String itemTitle, int value) {
        if(value < 1 || value > 10){
            throw new RuntimeException("Quantity value should be in range between 1 and 10");
        }
        WebElement element = getTableRowByTitle(itemTitle).findElement(quantityDropdownSelect);
        element.click();
        Objects.requireNonNull(element.findElements(By.tagName("option")).stream().filter(el -> el.getText().equals(String.valueOf(value))).findFirst().orElse(null)).click();
        return this;
    }

    //void should be changed to CheckoutPage when the page object is created
    public void clickOnProceedButton() {
        byLocator(proceedToCheckoutButton).click();
    }

    private WebElement getTableRowByIndex(int index) {
        return byLocator(cartTable).findElements(By.cssSelector("tbody tr")).get(index);
    }

    private WebElement getTableRowByTitle(String title) {
        Optional<WebElement> element = byLocator(cartTable).findElements(By.cssSelector("tbody tr")).stream().filter(el -> el.findElement(productTitle).getText().equals(title)).findFirst();
        return element.orElse(null);
    }
}
