package me.shenderov.automation.test.pageobjects.components;

import me.shenderov.automation.test.AbstractPage;
import me.shenderov.automation.test.pageobjects.BasketPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class NavBarPage extends AbstractPage {

    private static final By basketIcon = By.className("header-basket");
    private static final By basketDropdownMenu = By.className("cart-mini-contents");
    private static final By basketDropdownSubtotal = By.className("mini-cart-total");
    private static final By basketDropdownCheckoutButton = By.xpath("//*[text()='Checkout']");
    private static final By basketDropdownEditButton = By.xpath("//*[text()='Edit Basket']");

    public NavBarPage(WebDriver driver) {
        super(driver);
    }

    public BasketPage clickOnBasketEdit(){
        hoverOverElement(basketIcon);
        byLocator(basketDropdownMenu).findElement(basketDropdownEditButton).click();
        return new BasketPage(driver);
    }

    //void should be changed to CheckoutPage when the page object is created
    public void clickOnBasketCheckout(){
        hoverOverElement(basketIcon);
        byLocator(basketDropdownMenu).findElement(basketDropdownCheckoutButton).click();
    }

    public String getSubtotalValue(){
        hoverOverElement(basketIcon);
        return byLocator(basketDropdownMenu).findElement(basketDropdownSubtotal).getText();
    }
}
