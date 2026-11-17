package com.automationexercise.pages;

import com.automationexercise.drivers.GUIDriver;
import com.automationexercise.pages.components.FooterComponent;
import com.automationexercise.pages.components.NavigationBarComponent;
import com.automationexercise.utils.logs.LogsManager;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class ContactUsPage {
    private final GUIDriver driver;
    private final NavigationBarComponent navigationBar;
    private final FooterComponent footer;

    public ContactUsPage(GUIDriver driver) {
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

    //locators
    private final By getInTouchTitle = By.xpath("//h2[text()='Get In Touch']");
    private final By nameInput = By.cssSelector("input[data-qa='name']");
    private final By emailInput = By.cssSelector("input[data-qa='email']");
    private final By subjectInput = By.cssSelector("input[data-qa='subject']");
    private final By messageInput = By.id("message");
    private final By uploadFileInput = By.name("upload_file");
    private final By submitButton = By.cssSelector("input[data-qa='submit-button']");

    private final By successMessage = By.cssSelector(".status.alert.alert-success");

    //actions
    @Step("Enter contact details: Name: {name}, Email: {email}, Subject: {subject}, Message: {message}")
    public ContactUsPage enterContactDetails(String name, String email, String subject, String message) {
        driver.element().type(nameInput, name);
        driver.element().type(emailInput, email);
        driver.element().type(subjectInput, subject);
        driver.element().type(messageInput, message);
        return this;
    }

    @Step("Upload file: {filePath}")
    public ContactUsPage uploadFile(String filePath) {
        driver.element().uploadFile(uploadFileInput, filePath);
        return this;
    }

    @Step("Click 'Submit' button")
    public ContactUsPage clickSubmitButton() {
        driver.element().click(submitButton);
        return this;
    }

    @Step("Click 'OK' on the alert popup")
    public ContactUsPage clickOkOnAlert() {
        driver.element().acceptAlert();
        return this;
    }

    //validations
    @Step("Verify that Contact us page is visible successfully")
    public ContactUsPage verifyContactUsPageIsVisible() {
        driver.verification().isElementVisible(getInTouchTitle);
        return this;
    }

    @Step("Verify success message is: {expectedMessage}")
    public ContactUsPage verifySuccessMessage(String expectedMessage) {
        String actualTxt = driver.element().getText(successMessage);
        LogsManager.info("Validating contact success message: " + actualTxt);
        driver.verification().Equals(actualTxt, expectedMessage, "Success message does not match");
        return this;
    }
}
