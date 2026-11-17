package com.automationexercise.pages;

import com.automationexercise.drivers.GUIDriver;
import com.automationexercise.pages.components.FooterComponent;
import com.automationexercise.pages.components.NavigationBarComponent;
import com.automationexercise.pages.components.SideBarComponent;
import com.automationexercise.utils.dataReader.PropertyReader;
import com.automationexercise.utils.logs.LogsManager;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class PaymentPage {
    private final GUIDriver driver;
    private final NavigationBarComponent navigationBar;
    private final FooterComponent footer;


    public PaymentPage(GUIDriver driver) {
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
    private final String PAYMENT_ENDPOINT = "/payment";

    //locators
    private final By paymentPageTitle = By.xpath("//h2[text()='Payment']");
    private final By nameOnCardInput = By.cssSelector("input[data-qa='name-on-card']");
    private final By cardNumberInput = By.cssSelector("input[data-qa='card-number']");
    private final By cvcInput = By.cssSelector("input[data-qa='cvc']");
    private final By expiryMonthInput = By.cssSelector("input[data-qa='expiry-month']");
    private final By expiryYearInput = By.cssSelector("input[data-qa='expiry-year']");
    private final By payAndConfirmButton = By.id("submit");
    private final By paymentSuccessMessage = By.cssSelector("#success_message .alert-success");

    private final By orderPlacedTitle = By.xpath("//h2[@data-qa='order-placed']");
    private final By downloadInvoiceButton = By.xpath("//a[contains(text(), 'Download Invoice')]");
    private final By continueButton = By.cssSelector("[data-qa='continue-button']");
    private final By orderConfirmedParagraph = By.xpath("//p[contains(text(), 'Congratulations!')]");


    //dynamic locators

    //actions
    @Step("Enter payment details: Name: {name}, Card: {cardNumber}")
    public PaymentPage enterPaymentDetails(String name, String cardNumber, String cvc, String expMonth, String expYear) {
        driver.element().type(nameOnCardInput, name)
                .type(cardNumberInput, cardNumber)
                .type(cvcInput, cvc)
                .type(expiryMonthInput, expMonth)
                .type(expiryYearInput, expYear);

        LogsManager.info("Entered payment details for: " + name);
        return this;
    }

    @Step("Click on 'Pay and Confirm Order' button")
    public PaymentPage clickPayAndConfirmOrder() {
        driver.element().click(payAndConfirmButton);

        LogsManager.info("Clicked 'Pay and Confirm Order' button.");
        return this;
    }

    @Step("Click 'Download Invoice' button")
    public PaymentPage clickDownloadInvoice() {
        driver.element().click(downloadInvoiceButton);

        LogsManager.info("Clicked 'Download Invoice' button.");
        return this;
    }

    @Step("Click 'Continue' button to finish order flow")
    public NavigationBarComponent clickOnContinueButton() {
        driver.element().click(continueButton);

        LogsManager.info("Clicked 'Continue' button and redirected to Home.");
        return new NavigationBarComponent(driver);
    }

    //validations
    @Step("Verify user is navigated to Payment page")
    public PaymentPage verifyPaymentPageIsDisplayed() {
        driver.verification().assertPageUrl(PropertyReader.getProperty("baseUrlWeb") + PAYMENT_ENDPOINT);
        driver.verification().isElementVisible(paymentPageTitle);

        LogsManager.info("Verified that the Payment page is displayed.");
        return this;
    }

    @Step("Validate payment success message is: {expectedMessage}")
    public PaymentPage validatePaymentSuccessMessage(String expectedMessage) {
        String actualMessage = driver.element().getText(paymentSuccessMessage);
        LogsManager.info("Validating payment success message: " + actualMessage);

        driver.verification().Equals(actualMessage, expectedMessage, "Payment success message does not match expected text");

        return this;
    }


    @Step("Validate Order Confirmed message is: {expectedMessage}")
    public PaymentPage validateOrderConfirmedMessage(String expectedMessage) {
        driver.verification().isElementVisible(orderPlacedTitle);
        String actualMessage = driver.element().getText(orderConfirmedParagraph);
        LogsManager.info("Validating Order Confirmed message: " + actualMessage);
        driver.verification().Equals(actualMessage, expectedMessage, "Order Confirmed message does not match expected text");
        return this;
    }

    @Step("Verify that the invoice file '{invoiceName}' is downloaded successfully")
    public PaymentPage verifyDownloadedFile(String invoiceName) {
        driver.verification().assertFileExists(invoiceName, "File is not existed");
        LogsManager.info("Successfully verified that invoice file exists");
        return this;
    }

}
