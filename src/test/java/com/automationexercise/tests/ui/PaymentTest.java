package com.automationexercise.tests.ui;

import com.automationexercise.apis.UserManagementAPI;
import com.automationexercise.drivers.GUIDriver;
import com.automationexercise.pages.HomePage;
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
public class PaymentTest extends BaseTest {

    private String uniqueEmail() {
        return testData.getJsonData("signup.email") + TimeManager.getSimpleTimestamp() + "@gmail.com";
    }

    @Severity(SeverityLevel.CRITICAL)
    @Epic("Checkout & Payments")
    @Feature("Order Placement")
    @Story("Register During Checkout")
    @Description("Verify that a guest can register during checkout, retain the cart contents, and place an order successfully")
    @Test(description = "TC14: Place Order: Register while Checkout")
    public void verifyUserCanPlaceOrderByRegisteringDuringCheckout(){
        // Product Data
        String productName = testData.getJsonData("product.name");
        String productPrice = testData.getJsonData("product.price");
        String productQuantity = testData.getJsonData("product.quantity");
        String productTotal = testData.getJsonData("product.total");
        String orderComment = testData.getJsonData("checkout.orderComment");
        String productTotalAmount = testData.getJsonData("checkout.expectedGrandTotal");
        String cartAddedMessage = testData.getJsonData("cartMessages.cartAdded");
        // Signup Data
        String signupEmail = uniqueEmail();
        String signupName = testData.getJsonData("signup.name");
        String title = testData.getJsonData("signup.titleMale");
        String password = testData.getJsonData("signup.password");
        String day = testData.getJsonData("signup.day");
        String month = testData.getJsonData("signup.month");
        String year = testData.getJsonData("signup.year");
        String firstName = testData.getJsonData("signup.firstName");
        String lastName = testData.getJsonData("signup.lastName");
        String company = testData.getJsonData("signup.companyName");
        String address1 = testData.getJsonData("signup.address1");
        String address2 = testData.getJsonData("signup.address2");
        String country = testData.getJsonData("signup.country");
        String state = testData.getJsonData("signup.state");
        String city = testData.getJsonData("signup.city");
        String zipCode = testData.getJsonData("signup.zipCode");
        String mobileNumber = testData.getJsonData("signup.mobileNumber");
        // Payment Data
        String nameOnCard = testData.getJsonData("payment.nameOnCard");
        String cardNumber = testData.getJsonData("payment.cardNumber");
        String cvc = testData.getJsonData("payment.cvc");
        String expirationMonth = testData.getJsonData("payment.expirationMonth");
        String expirationYear = testData.getJsonData("payment.expirationYear");
        // Messages Data
        String orderConfirmedMsg = testData.getJsonData("messages.orderConfirmedMsg");

        new HomePage(driver)
                .navigationBar()
                .clickOnProductsButton()
                .verifyUserIsNavigatedToAllProductsPage()
                .clickOnAddToCart(productName)
                .validateItemAddedMessage(cartAddedMessage)
                .clickOnContinueShopping()
                .navigationBar()
                .clickOnCartButton()
                .verifyUserIsNavigatedToShoppingCart()
                .verifyProductDetailsInCart(productName, productPrice, productQuantity, productTotal)
                .clickProceedToCheckoutAsGuest()
                .clickRegisterLoginInModal()
                .verifyNewUserSignupVisible()
                .enterSignupName(signupName)
                .enterSignupEmail(signupEmail)
                .clickOnSignupButton()
                .verifyEnterAccountInformationVisible()
                .enterAccountInformation(title, password, day, month, year)
                .selectSubscriptions()
                .enterAddressInformation(firstName, lastName, company, address1, address2, country, state, city, zipCode, mobileNumber)
                .clickOnCreateAccountButton()
                .verifyAccountCreated()
                .clickOnContinueButton()
                .verifyUserLabel(signupName)
                .clickOnCartButton()
                .verifyUserIsNavigatedToShoppingCart()
                .verifyProductDetailsInCart(productName, productPrice, productQuantity, productTotal)
                .clickProceedToCheckoutAsLoggedInUser()
                .verifyUserIsNavigatedToCheckoutPage()
                .verifyDeliveryAddress(title, firstName, lastName, company, address1, address2, city, state, zipCode, country, mobileNumber)
                .verifyBillingAddress(title, firstName, lastName, company, address1, address2, city, state, zipCode, country, mobileNumber)
                .verifyProductInReviewTable(productName, productPrice, productQuantity, productTotal)
                .verifyGrandTotalAmount(productTotalAmount)
                .enterOrderComment(orderComment)
                .clickPlaceOrder()
                .verifyPaymentPageIsDisplayed()
                .enterPaymentDetails(nameOnCard, cardNumber, cvc, expirationMonth, expirationYear)
                .clickPayAndConfirmOrder()
                .validateOrderConfirmedMessage(orderConfirmedMsg)
                .navigationBar()
                .clickOnDeleteAccountButton()
                .verifyAccountDeleted()
                .clickOnContinueButton();
    }

    @Severity(SeverityLevel.CRITICAL)
    @Epic("Checkout & Payments")
    @Feature("Order Placement")
    @Story("Register Before Checkout")
    @Description("Verify that a user can register before checkout, add a product to the cart, and place an order successfully")
    @Test(description = "TC15: Place Order: Register before Checkout")
    public void verifyUserCanPlaceOrderAfterRegisteringBeforeCheckout(){
        // Product Data
        String productName = testData.getJsonData("product.name");
        String productPrice = testData.getJsonData("product.price");
        String productQuantity = testData.getJsonData("product.quantity");
        String productTotal = testData.getJsonData("product.total");
        String orderComment = testData.getJsonData("checkout.orderComment");
        String productTotalAmount = testData.getJsonData("checkout.expectedGrandTotal");
        String cartAddedMessage = testData.getJsonData("cartMessages.cartAdded");
        // Signup Data
        String signupEmail = uniqueEmail();
        String signupName = testData.getJsonData("signup.name");
        String title = testData.getJsonData("signup.titleMale");
        String password = testData.getJsonData("signup.password");
        String day = testData.getJsonData("signup.day");
        String month = testData.getJsonData("signup.month");
        String year = testData.getJsonData("signup.year");
        String firstName = testData.getJsonData("signup.firstName");
        String lastName = testData.getJsonData("signup.lastName");
        String company = testData.getJsonData("signup.companyName");
        String address1 = testData.getJsonData("signup.address1");
        String address2 = testData.getJsonData("signup.address2");
        String country = testData.getJsonData("signup.country");
        String state = testData.getJsonData("signup.state");
        String city = testData.getJsonData("signup.city");
        String zipCode = testData.getJsonData("signup.zipCode");
        String mobileNumber = testData.getJsonData("signup.mobileNumber");

        // Payment Data
        String nameOnCard = testData.getJsonData("payment.nameOnCard");
        String cardNumber = testData.getJsonData("payment.cardNumber");
        String cvc = testData.getJsonData("payment.cvc");
        String expirationMonth = testData.getJsonData("payment.expirationMonth");
        String expirationYear = testData.getJsonData("payment.expirationYear");
        // Messages Data
        String paymentSuccessMsg = testData.getJsonData("messages.paymentSuccessMsg");
        String orderConfirmedMsg = testData.getJsonData("messages.orderConfirmedMsg");

        new HomePage(driver)
                .navigationBar()
                .clickOnSignupLoginButton()
                .verifyNewUserSignupVisible()
                .enterSignupName(signupName)
                .enterSignupEmail(signupEmail)
                .clickOnSignupButton()
                .verifyEnterAccountInformationVisible()
                .enterAccountInformation(title, password, day, month, year)
                .selectSubscriptions()
                .enterAddressInformation(firstName, lastName, company, address1, address2, country, state, city, zipCode, mobileNumber)
                .clickOnCreateAccountButton()
                .verifyAccountCreated()
                .clickOnContinueButton()
                .verifyUserLabel(signupName)
                .clickOnProductsButton()
                .verifyUserIsNavigatedToAllProductsPage()
                .clickOnAddToCart(productName)
                .validateItemAddedMessage(cartAddedMessage)
                .clickOnContinueShopping()
                .navigationBar()
                .clickOnCartButton()
                .verifyUserIsNavigatedToShoppingCart()
                .verifyProductDetailsInCart(productName, productPrice, productQuantity, productTotal)
                .clickProceedToCheckoutAsLoggedInUser()
                .verifyUserIsNavigatedToCheckoutPage()
                .verifyDeliveryAddress(title, firstName, lastName, company, address1, address2, city, state, zipCode, country, mobileNumber)
                .verifyBillingAddress(title, firstName, lastName, company, address1, address2, city, state, zipCode, country, mobileNumber)
                .verifyProductInReviewTable(productName, productPrice, productQuantity, productTotal)
                .verifyGrandTotalAmount(productTotalAmount)
                .enterOrderComment(orderComment)
                .clickPlaceOrder()
                .verifyPaymentPageIsDisplayed()
                .enterPaymentDetails(nameOnCard, cardNumber, cvc, expirationMonth, expirationYear)
                .clickPayAndConfirmOrder()
                .validateOrderConfirmedMessage(orderConfirmedMsg)
                .navigationBar()
                .clickOnDeleteAccountButton()
                .verifyAccountDeleted()
                .clickOnContinueButton();
    }

    @Severity(SeverityLevel.CRITICAL)
    @Epic("Checkout & Payments")
    @Feature("Order Placement")
    @Story("Login Before Checkout")
    @Description("Verify that a logged-in user can add a product to the cart, proceed through checkout, and place an order successfully")
    @Test(description = "TC16: Place Order: Login before Checkout")
    public void verifyUserCanPlaceOrderAfterLoggingInBeforeCheckout(){
        // Product Data
        String productName = testData.getJsonData("product.name");
        String productPrice = testData.getJsonData("product.price");
        String productQuantity = testData.getJsonData("product.quantity");
        String productTotal = testData.getJsonData("product.total");
        String orderComment = testData.getJsonData("checkout.orderComment");
        String productTotalAmount = testData.getJsonData("checkout.expectedGrandTotal");
        String cartAddedMessage = testData.getJsonData("cartMessages.cartAdded");
        // Signup Data
        String signupEmail = uniqueEmail();
        String signupName = testData.getJsonData("signup.name");
        String title = testData.getJsonData("signup.titleMale");
        String password = testData.getJsonData("signup.password");
        String day = testData.getJsonData("signup.day");
        String month = testData.getJsonData("signup.month");
        String year = testData.getJsonData("signup.year");
        String firstName = testData.getJsonData("signup.firstName");
        String lastName = testData.getJsonData("signup.lastName");
        String company = testData.getJsonData("signup.companyName");
        String address1 = testData.getJsonData("signup.address1");
        String address2 = testData.getJsonData("signup.address2");
        String country = testData.getJsonData("signup.country");
        String state = testData.getJsonData("signup.state");
        String city = testData.getJsonData("signup.city");
        String zipCode = testData.getJsonData("signup.zipCode");
        String mobileNumber = testData.getJsonData("signup.mobileNumber");
        // Payment Data
        String nameOnCard = testData.getJsonData("payment.nameOnCard");
        String cardNumber = testData.getJsonData("payment.cardNumber");
        String cvc = testData.getJsonData("payment.cvc");
        String expirationMonth = testData.getJsonData("payment.expirationMonth");
        String expirationYear = testData.getJsonData("payment.expirationYear");
        String orderConfirmedMsg = testData.getJsonData("messages.orderConfirmedMsg");

        // Precondition: create the user through the API.
        new UserManagementAPI()
                .createUserAccount(signupName, signupEmail, password,firstName, lastName)
                .verifyUserCreatedSuccessfully();

        new HomePage(driver)
                .navigationBar()
                .clickOnSignupLoginButton()
                .verifyLoginToYourAccountVisible()
                .enterLoginEmail(signupEmail)
                .enterLoginPassword(password)
                .clickOnLoginButton()
                .verifyUserLabel(signupName)
                .clickOnProductsButton()
                .verifyUserIsNavigatedToAllProductsPage()
                .clickOnAddToCart(productName)
                .validateItemAddedMessage(cartAddedMessage)
                .clickOnContinueShopping()
                .navigationBar()
                .clickOnCartButton()
                .verifyUserIsNavigatedToShoppingCart()
                .verifyProductDetailsInCart(productName, productPrice, productQuantity, productTotal)
                .clickProceedToCheckoutAsLoggedInUser()
                .verifyUserIsNavigatedToCheckoutPage()
                .verifyDeliveryAddress(title, firstName, lastName, company, address1, address2, city, state, zipCode, country, mobileNumber)
                .verifyBillingAddress(title, firstName, lastName, company, address1, address2, city, state, zipCode, country, mobileNumber)
                .verifyProductInReviewTable(productName, productPrice, productQuantity, productTotal)
                .verifyGrandTotalAmount(productTotalAmount)
                .enterOrderComment(orderComment)
                .clickPlaceOrder()
                .verifyPaymentPageIsDisplayed()
                .enterPaymentDetails(nameOnCard, cardNumber, cvc, expirationMonth, expirationYear)
                .clickPayAndConfirmOrder()
                .validateOrderConfirmedMessage(orderConfirmedMsg)
                .navigationBar()
                .clickOnDeleteAccountButton()
                .verifyAccountDeleted()
                .clickOnContinueButton();
    }

    @Severity(SeverityLevel.NORMAL)
    @Epic("Checkout & Payments")
    @Feature("Invoice Management")
    @Story("Download Order Invoice")
    @Description("Verify that a user can register during checkout, place an order, and download the invoice with the expected file name")
    @Test(description = "TC24: Download Invoice after purchase order")
    public void verifyUserCanDownloadInvoiceAfterPurchase(){
        // Product Data
        String productName = testData.getJsonData("product.name");
        String productPrice = testData.getJsonData("product.price");
        String productQuantity = testData.getJsonData("product.quantity");
        String productTotal = testData.getJsonData("product.total");
        String orderComment = testData.getJsonData("checkout.orderComment");
        String productTotalAmount = testData.getJsonData("checkout.expectedGrandTotal");
        String cartAddedMessage = testData.getJsonData("cartMessages.cartAdded");
        // Signup Data
        String signupEmail = uniqueEmail();
        String signupName = testData.getJsonData("signup.name");
        String title = testData.getJsonData("signup.titleMale");
        String password = testData.getJsonData("signup.password");
        String day = testData.getJsonData("signup.day");
        String month = testData.getJsonData("signup.month");
        String year = testData.getJsonData("signup.year");
        String firstName = testData.getJsonData("signup.firstName");
        String lastName = testData.getJsonData("signup.lastName");
        String company = testData.getJsonData("signup.companyName");
        String address1 = testData.getJsonData("signup.address1");
        String address2 = testData.getJsonData("signup.address2");
        String country = testData.getJsonData("signup.country");
        String state = testData.getJsonData("signup.state");
        String city = testData.getJsonData("signup.city");
        String zipCode = testData.getJsonData("signup.zipCode");
        String mobileNumber = testData.getJsonData("signup.mobileNumber");
        // Payment Data
        String nameOnCard = testData.getJsonData("payment.nameOnCard");
        String cardNumber = testData.getJsonData("payment.cardNumber");
        String cvc = testData.getJsonData("payment.cvc");
        String expirationMonth = testData.getJsonData("payment.expirationMonth");
        String expirationYear = testData.getJsonData("payment.expirationYear");
        // Messages Data
        String orderConfirmedMsg = testData.getJsonData("messages.orderConfirmedMsg");

        new HomePage(driver)
                .navigationBar()
                .clickOnProductsButton()
                .verifyUserIsNavigatedToAllProductsPage()
                .clickOnAddToCart(productName)
                .validateItemAddedMessage(cartAddedMessage)
                .clickOnContinueShopping()
                .navigationBar()
                .clickOnCartButton()
                .verifyUserIsNavigatedToShoppingCart()
                .verifyProductDetailsInCart(productName, productPrice, productQuantity, productTotal)
                .clickProceedToCheckoutAsGuest()
                .clickRegisterLoginInModal()
                .verifyNewUserSignupVisible()
                .enterSignupName(signupName)
                .enterSignupEmail(signupEmail)
                .clickOnSignupButton()
                .verifyEnterAccountInformationVisible()
                .enterAccountInformation(title, password, day, month, year)
                .selectSubscriptions()
                .enterAddressInformation(firstName, lastName, company, address1, address2, country, state, city, zipCode, mobileNumber)
                .clickOnCreateAccountButton()
                .verifyAccountCreated()
                .clickOnContinueButton()
                .verifyUserLabel(signupName)
                .clickOnCartButton()
                .verifyUserIsNavigatedToShoppingCart()
                .verifyProductDetailsInCart(productName, productPrice, productQuantity, productTotal)
                .clickProceedToCheckoutAsLoggedInUser()
                .verifyUserIsNavigatedToCheckoutPage()
                .verifyDeliveryAddress(title, firstName, lastName, company, address1, address2, city, state, zipCode, country, mobileNumber)
                .verifyBillingAddress(title, firstName, lastName, company, address1, address2, city, state, zipCode, country, mobileNumber)
                .verifyProductInReviewTable(productName, productPrice, productQuantity, productTotal)
                .verifyGrandTotalAmount(productTotalAmount)
                .enterOrderComment(orderComment)
                .clickPlaceOrder()
                .verifyPaymentPageIsDisplayed()
                .enterPaymentDetails(nameOnCard, cardNumber, cvc, expirationMonth, expirationYear)
                .clickPayAndConfirmOrder()
                .validateOrderConfirmedMessage(orderConfirmedMsg)
                .clickDownloadInvoice()
                .verifyDownloadedFile(testData.getJsonData("invoice.expectedFileName"))
                .clickOnContinueButton()
                .clickOnDeleteAccountButton()
                .verifyAccountDeleted()
                .clickOnContinueButton();
    }


    //Configurations
    @BeforeClass
    protected void loadTestData() {
        testData = new JsonReader("payment-data");
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
