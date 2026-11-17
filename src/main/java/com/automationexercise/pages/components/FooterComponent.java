package com.automationexercise.pages.components;

import com.automationexercise.drivers.GUIDriver;
import com.automationexercise.utils.logs.LogsManager;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class FooterComponent {
    private final GUIDriver driver;

    public FooterComponent(GUIDriver driver) {
        this.driver = driver;
    }

    //locators
    private final By scrollUpArrow = By.id("scrollUp");

    // --- SUBSCRIPTION LOCATORS ---
    private final By subscriptionTitle = By.xpath("//h2[text()='Subscription']");
    private final By subscriptionEmailInput = By.id("susbscribe_email");
    private final By subscribeButton = By.id("subscribe");
    private final By subscriptionSuccessMessage = By.cssSelector("#success-subscribe .alert-success");


    //actions
    @Step("Scroll to the Subscription section")
    public FooterComponent scrollToSubscription() {
        driver.element().scrollToElementJS(subscriptionTitle);
        return this;
    }

    @Step("Enter email in Subscription field: {email}")
    public FooterComponent enterSubscriptionEmail(String email) {
        driver.element().type(subscriptionEmailInput, email);
        return this;
    }

    @Step("Click on 'Subscribe' button")
    public FooterComponent clickOnSubscribeButton() {
        driver.element().click(subscribeButton);
        return this;
    }

    @Step("Scroll down to the bottom of the page")
    public FooterComponent scrollToBottom() {
        driver.element().scrollToBottom();
        return this;
    }

    @Step("Click on the arrow at the bottom right side to move upward")
    public FooterComponent clickOnScrollUpArrow() {
        driver.element().click(scrollUpArrow);
        return this;
    }

    //validations
    @Step("Verify 'SUBSCRIPTION' title is visible")
    public FooterComponent verifySubscriptionIsVisible() {
        driver.verification().isElementVisible(subscriptionTitle);
        return this;
    }

    @Step("Verify subscription success message is: {expectedMessage}")
    public FooterComponent verifySubscriptionSuccessMessage(String expectedMessage) {
        String actualTxt = driver.element().getText(subscriptionSuccessMessage);
        LogsManager.info("Validating subscription message: " + actualTxt);
        driver.verification().Equals(actualTxt, expectedMessage, "Subscription success message does not match");
        return this;
    }
}
