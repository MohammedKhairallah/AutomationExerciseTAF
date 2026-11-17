package com.automationexercise.tests.ui;

import com.automationexercise.apis.UserManagementAPI;
import com.automationexercise.drivers.GUIDriver;
import com.automationexercise.pages.HomePage;
import com.automationexercise.pages.ProductDetailsPage;
import com.automationexercise.pages.ProductsPage;
import com.automationexercise.pages.components.NavigationBarComponent;
import com.automationexercise.tests.BaseTest;
import com.automationexercise.utils.TimeManager;
import com.automationexercise.utils.dataReader.JsonReader;
import io.qameta.allure.*;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Owner("Mohammed Khairallah")
public class CartTest extends BaseTest {

    private String uniqueEmail() {
        return testData.getJsonData("signup.email") + TimeManager.getSimpleTimestamp() + "@gmail.com";
    }

    @Severity(SeverityLevel.NORMAL)
    @Epic("Customer Engagement")
    @Feature("Newsletter Subscription")
    @Story("Subscribe to Newsletter")
    @Description("Verify that a user can successfully subscribe to the newsletter from the Cart page")
    @Test(description = "TC11: Verify Subscription in Cart page")
    public void verifyUserCanSubscribeFromCartPage(){
        String subscriptionEmail = testData.getJsonData("subscription.email") + TimeManager.getSimpleTimestamp() + "@gmail.com";
        new HomePage(driver)
                .navigationBar()
                .clickOnCartButton()
                .verifyUserIsNavigatedToShoppingCart()
                .footer()
                .scrollToSubscription()
                .verifySubscriptionIsVisible()
                .enterSubscriptionEmail(subscriptionEmail)
                .clickOnSubscribeButton()
                .verifySubscriptionSuccessMessage(testData.getJsonData("subscription.successMessage"));
    }

    @Severity(SeverityLevel.CRITICAL)
    @Epic("Shopping Experience")
    @Feature("Shopping Cart Management")
    @Story("Add Products to Cart")
    @Description("Verify that a user can add multiple products to the shopping cart and that their prices, quantities, and totals are displayed correctly.")
    @Test(description = "TC12: Add Products in Cart")
    public void verifyUserCanAddProductsToCart() {
        // Product 1 data
        String product1Name = testData.getJsonData("product1.name");
        String product1Price = testData.getJsonData("product1.price");
        String product1Quantity = testData.getJsonData("product1.quantity");
        String product1Total = testData.getJsonData("product1.total");

        // Product 2 data
        String product2Name = testData.getJsonData("product2.name");
        String product2Price = testData.getJsonData("product2.price");
        String product2Quantity = testData.getJsonData("product2.quantity");
        String product2Total = testData.getJsonData("product2.total");

        // Message data
        String cartAddedMessage = testData.getJsonData("messages.cartAdded");

        new HomePage(driver)
                .navigationBar()
                .clickOnProductsButton()
                .verifyUserIsNavigatedToAllProductsPage()
                .clickOnAddToCart(product1Name)
                .validateItemAddedMessage(cartAddedMessage)
                .clickOnContinueShopping()
                .clickOnAddToCart(product2Name)
                .validateItemAddedMessage(cartAddedMessage)
                .clickOnViewCart()
                .verifyProductDetailsInCart(product1Name, product1Price, product1Quantity, product1Total)
                .verifyProductDetailsInCart(product2Name, product2Price, product2Quantity,product2Total);
    }

    @Severity(SeverityLevel.CRITICAL)
    @Epic("Shopping Experience")
    @Feature("Shopping Cart Management")
    @Story("Update Product Quantity")
    @Description("Verify that the selected product quantity is correctly reflected in the shopping cart and the total price is calculated accurately.")
    @Test(description = "TC13: Verify Product quantity in Cart")
    public void verifyProductQuantityInCartMatchesSelection(){
        new ProductsPage(driver)
                .clickOnViewProduct(testData.getJsonData("quantityTest.productName"))
                .verifyProductName(testData.getJsonData("quantityTest.productName"))
                .verifyProductPrice(testData.getJsonData("quantityTest.unitPrice"))
                .increaseQuantity(testData.getJsonData("quantityTest.newQuantity"))
                .clickAddToCart()
                .verifyItemAddedMessage(testData.getJsonData("messages.cartAdded"))
                .clickViewCartInModal()
                .verifyProductDetailsInCart(testData.getJsonData("quantityTest.productName"),
                        testData.getJsonData("quantityTest.unitPrice"),
                        testData.getJsonData("quantityTest.newQuantity"),
                        testData.getJsonData("quantityTest.expectedTotal"));
    }

    @Severity(SeverityLevel.CRITICAL)
    @Epic("Shopping Experience")
    @Feature("Shopping Cart Management")
    @Story("Remove Products from Cart")
    @Description("Verify that a user can remove a product from the shopping cart and that the cart displays the empty state.")
    @Test(description = "TC17: Remove Products From Cart")
    public void verifyUserCanRemoveProductFromCart(){
        new HomePage(driver)
                .navigationBar()
                .clickOnProductsButton()
                .verifyUserIsNavigatedToAllProductsPage()
                .clickOnAddToCart(testData.getJsonData("product1.name"))
                .validateItemAddedMessage(testData.getJsonData("messages.cartAdded"))
                .clickOnContinueShopping()
                .navigationBar()
                .clickOnCartButton()
                .verifyUserIsNavigatedToShoppingCart()
                .clickRemoveProduct(testData.getJsonData("product1.name"))
                .verifyProductIsRemovedFromCart(testData.getJsonData("product1.name"))
                .verifyCartIsEmpty();
    }

    @Severity(SeverityLevel.NORMAL)
    @Epic("Shopping Experience")
    @Feature("Cart Persistence")
    @Story("Preserve Cart Items After Login")
    @Description("Verify that products added to the shopping cart as a guest remain available with the correct details after logging in.")
    @Test(description = "TC20: Search Products and Verify Cart After Login")
    public void verifySearchedProductsRemainInCartAfterLogin(){
        String productName = testData.getJsonData("searchProduct.name");
        String productPrice = testData.getJsonData("searchProduct.price");
        String productQuantity = testData.getJsonData("searchProduct.quantity");
        String productTotal = testData.getJsonData("searchProduct.total");
        String cartAddedMessage = testData.getJsonData("messages.cartAdded");

        // Signup Data
        String signupEmail = uniqueEmail();
        String signupName = testData.getJsonData("signup.name");
        String password = testData.getJsonData("signup.password");
        String firstName = testData.getJsonData("signup.firstName");
        String lastName = testData.getJsonData("signup.lastName");


        // Precondition: create the user through the API.
        new UserManagementAPI()
                .createUserAccount(signupName, signupEmail, password,firstName, lastName)
                .verifyUserCreatedSuccessfully();

        new HomePage(driver)
                .navigationBar()
                .clickOnProductsButton()
                .verifyUserIsNavigatedToAllProductsPage()
                .searchProduct(productName)
                .verifySearchedProductsTitleIsVisible()
                .validateProductDetails(productName, productPrice)
                .clickOnAddToCart(productName)
                .validateItemAddedMessage(cartAddedMessage)
                .clickOnContinueShopping()
                .navigationBar()
                .clickOnCartButton()
                .verifyUserIsNavigatedToShoppingCart()
                .verifyProductDetailsInCart(productName, productPrice, productQuantity, productTotal)
                .navigationBar()
                .clickOnSignupLoginButton()
                .verifyLoginToYourAccountVisible()
                .enterLoginEmail(signupEmail)
                .enterLoginPassword(password)
                .clickOnLoginButton()
                .verifyUserLabel(signupName)
                .clickOnCartButton()
                .verifyUserIsNavigatedToShoppingCart()
                .verifyProductDetailsInCart(productName, productPrice, productQuantity, productTotal);
    }

    @Severity(SeverityLevel.NORMAL)
    @Epic("Shopping Experience")
    @Feature("Recommended Products")
    @Story("Add Recommended Product to Cart")
    @Description("Verify that a user can add a product to the shopping cart from the Recommended Items section.")
    @Test(description = "TC22: Add to cart from Recommended items")
    public void verifyUserCanAddProductToCartFromRecommendedItems(){
        String productName = testData.getJsonData("recommendedProduct.name");
        String productPrice =  testData.getJsonData("recommendedProduct.price");
        String productQuantity =  testData.getJsonData("recommendedProduct.quantity");
        String productTotal =  testData.getJsonData("recommendedProduct.total");
        // Message data
        String cartAddedMessage = testData.getJsonData("messages.cartAdded");

        new HomePage(driver)
                .scrollToRecommendedItems()
                .verifyRecommendedItemsVisible()
                .clickOnAddToCartOfRecommendedProduct(productName)
                .validateItemAddedMessage(cartAddedMessage)
                .clickOnViewCart()
                .verifyUserIsNavigatedToShoppingCart()
                .verifyProductDetailsInCart(productName, productPrice, productQuantity, productTotal);
    }

    //Configurations
    @BeforeClass
    protected void loadTestData() {
        testData = new JsonReader("cart-data");
    }

    @BeforeMethod
    public void setUp() {
        driver = new GUIDriver();
        new HomePage(driver)
                .navigate()
                .verifyHomePageIsVisible();
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        if (driver != null) {
            driver.quitDriver();
        }
    }
}
