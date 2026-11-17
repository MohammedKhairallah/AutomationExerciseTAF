package com.automationexercise.pages;

import com.automationexercise.drivers.GUIDriver;
import com.automationexercise.pages.components.FooterComponent;
import com.automationexercise.pages.components.NavigationBarComponent;
import com.automationexercise.utils.dataReader.PropertyReader;
import com.automationexercise.utils.logs.LogsManager;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class CartPage {
    private final GUIDriver driver;
    private final NavigationBarComponent navigationBar;
    private final FooterComponent footer;

    public CartPage(GUIDriver driver) {
        this.driver = driver;
        this.navigationBar = new NavigationBarComponent(driver);
        this.footer = new FooterComponent(driver);
    }

    // The navigation bar is a component shared by every page
    public NavigationBarComponent navigationBar() {
        return navigationBar;
    }

    // The footer is a component shared by every page
    public FooterComponent footer() {
        return footer;
    }

    //variables
    private final String VIEW_CART_ENDPOINT = "/view_cart";

    //locators
    private final By proceedToCheckoutButton = By.cssSelector(".btn.btn-default.check_out");
    private final By shoppingCartBreadcrumb = By.xpath("//li[@class='active' and contains(text(), 'Shopping Cart')]");
    private final By emptyCartMessage = By.xpath("//b[text()='Cart is empty!']");
    private final By registerLoginLinkInModal = By.cssSelector("#checkoutModal .modal-body a[href='/login']");
    private final By continueOnCartButton = By.cssSelector("#checkoutModal .modal-footer button");
    private final By checkoutModalTitle = By.cssSelector("#checkoutModal .modal-title");

    //dynamic locators
    private By cartProductName(String productName) {
        return By.xpath("//td[@class='cart_description']//a[text()='" + productName + "']");
    }

    private By cartProductPrice(String productName) {
        return By.xpath("//tr[.//a[text()='" + productName + "']]//td[@class='cart_price']//p");
    }

    private By cartProductQuantityButton(String productName) {
        return By.xpath("//tr[.//a[text()='" + productName + "']]//td[@class='cart_quantity']//button");
    }

    private By cartProductTotal(String productName) {
        return By.xpath("//tr[.//a[text()='" + productName + "']]//td[@class='cart_total']//p");
    }

    private By removeProductButton(String productName) {
        return By.xpath("//tr[.//a[text()='" + productName + "']]//a[@class='cart_quantity_delete']");
    }

    //actions
    @Step("Navigate to the Cart Page")
    public CartPage navigate(){
        driver.browser().navigateTo(PropertyReader.getProperty("baseUrlWeb") + VIEW_CART_ENDPOINT);
        return this;
    }

    @Step("Click 'Proceed To Checkout' (Expecting Checkout Page)")
    public CheckoutPage clickProceedToCheckoutAsLoggedInUser() {
        driver.element().click(proceedToCheckoutButton);
        return new CheckoutPage(driver);
    }

    @Step("Click on 'Proceed To Checkout' button")
    public CartPage clickProceedToCheckoutAsGuest() {
        driver.element().click(proceedToCheckoutButton);
        return this;
    }

    @Step("Click remove button for product name: {productName}")
    public CartPage clickRemoveProduct(String productName) {
        driver.element().click(removeProductButton(productName));
        return this;
    }

    @Step("Click 'Register / Login' link in Checkout modal")
    public SignupLoginPage clickRegisterLoginInModal() {
        driver.element().click(registerLoginLinkInModal);
        LogsManager.info("Clicked 'Register / Login' in Checkout modal.");
        return new SignupLoginPage(driver);
    }

    @Step("Click 'Continue On Cart' button in Checkout modal")
    public CartPage clickContinueOnCartInModal() {
        driver.element().click(continueOnCartButton);
        LogsManager.info("Clicked 'Continue On Cart' in Checkout modal.");
        driver.element().waitForInvisibility(checkoutModalTitle);
        return this;
    }

    //validations
    @Step("Verify user is navigated to the Shopping Cart page")
    public CartPage verifyUserIsNavigatedToShoppingCart(){
        // 1. Verify the URL is correct
        driver.verification().assertPageUrl(PropertyReader.getProperty("baseUrlWeb") + VIEW_CART_ENDPOINT);

        // 2. Verify the 'Shopping Cart' text in the breadcrumb is visible
        driver.verification().isElementVisible(shoppingCartBreadcrumb);

        // Optional: Get the text and log it to prove it worked
        String breadcrumbText = driver.element().getText(shoppingCartBreadcrumb);
        LogsManager.info("Verified breadcrumb text: " + breadcrumbText);

        return this;
    }

    @Step("Verify Product Details in Cart (Name: {expectedName}, Price: {expectedPrice}, Qty: {expectedQuantity}, Total: {expectedTotal})")
    public CartPage verifyProductDetailsInCart(String expectedName, String expectedPrice, String expectedQuantity, String expectedTotal) {

        //Get actual values
        String actualName = driver.element().getText(cartProductName(expectedName));
        String actualPrice = driver.element().getText(cartProductPrice(expectedName));
        String actualQuantity = driver.element().getText(cartProductQuantityButton(expectedName));
        String actualTotal = driver.element().getText(cartProductTotal(expectedName));

        LogsManager.info("Verifying cart details for: " + expectedName);

        driver.validation().Equals(actualName, expectedName, "Product Name does not match");
        driver.validation().Equals(actualPrice, expectedPrice, "Product Price does not match");
        driver.validation().Equals(actualQuantity, expectedQuantity, "Product Quantity does not match");
        driver.validation().Equals(actualTotal, expectedTotal, "Product Total does not match");

        return this;
    }

    @Step("Verify product '{productName}' is removed from the cart")
    public CartPage verifyProductIsRemovedFromCart(String productName) {
        // Wait up to 10 seconds for the specific product row to disappear
        driver.element().waitForInvisibility(cartProductName(productName));

        LogsManager.info("Verified that product '" + productName + "' was removed from the cart.");
        return this;
    }

    @Step("Verify that the cart is empty")
    public CartPage verifyCartIsEmpty() {
        driver.verification().isElementVisible(emptyCartMessage);

        LogsManager.info("Verified that the cart is now empty.");
        return this;
    }

}
