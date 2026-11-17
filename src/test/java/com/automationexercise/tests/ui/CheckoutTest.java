package com.automationexercise.tests.ui;

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
@Epic("Checkout & Payments")
@Owner("Mohammed Khairallah")
public class CheckoutTest extends BaseTest {

    private String uniqueEmail() {
        return testData.getJsonData("signup.email") + TimeManager.getSimpleTimestamp() + "@gmail.com";
    }

    @Severity(SeverityLevel.CRITICAL)
    @Feature("Checkout Address Verification")
    @Story("Delivery and Billing Addresses")
    @Description("Verify that the delivery and billing addresses displayed during checkout match the user's registration details.")
    @Test(description = "TC23: Verify address details in checkout page")
    public void verifyAddressDetailsAreCorrectInCheckoutPage(){
        // Product Data
        String productName = testData.getJsonData("product.name");
        String productPrice = testData.getJsonData("product.price");
        String productQuantity = testData.getJsonData("product.quantity");
        String productTotal = testData.getJsonData("product.total");
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
                .navigationBar()
                .clickOnDeleteAccountButton()
                .verifyAccountDeleted()
                .clickOnContinueButton();
    }

    //Configurations
    @BeforeClass
    protected void loadTestData() {
        testData = new JsonReader("checkout-data");
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
