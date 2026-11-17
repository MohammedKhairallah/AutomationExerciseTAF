package com.automationexercise.pages;

import com.automationexercise.drivers.GUIDriver;
import com.automationexercise.pages.components.FooterComponent;
import com.automationexercise.pages.components.NavigationBarComponent;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class SignupPage {
    private final GUIDriver driver;
    private final NavigationBarComponent navigationBar;
    private final FooterComponent footer;

    public SignupPage(GUIDriver driver) {
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
    private final By nameField = By.name("name");
    private final By emailField = By.id("email");
    private final By passwordField = By.id("password");
    private final By dayDropdown = By.id("days");
    private final By monthDropdown = By.id("months");
    private final By yearDropdown = By.id("years");
    private final By newsletterCheckbox = By.id("newsletter");
    private final By specialOffersCheckbox = By.id("optin");
    private final By firstNameField = By.id("first_name");
    private final By lastNameField = By.id("last_name");
    private final By companyField = By.id("company");
    private final By address1Field = By.id("address1");
    private final By address2Field = By.id("address2");
    private final By countryDropdown = By.id("country");
    private final By stateField = By.id("state");
    private final By cityField = By.id("city");
    private final By zipcodeField = By.id("zipcode");
    private final By mobileNumberField = By.id("mobile_number");
    private final By createAccountButton = By.cssSelector("[data-qa=\"create-account\"]");
    private final By accountCreatedLabel = By.cssSelector("h2[data-qa='account-created'] b");
    private final By continueButton = By.cssSelector("[data-qa=\"continue-button\"]");
    private final By enterAccountInformationLabel = By.xpath("//b[text()='Enter Account Information']");

    //actions
    @Step("Choose title {title}") //Mr - Mrs
    private SignupPage chooseTitle(String title){
        By titleLocator = By.xpath("//input [@value='" + title + "']");
        driver.element().click(titleLocator);
        return this;
    }

    @Step("Enter account information")
    public SignupPage enterAccountInformation(String title,
                                              String passwordText,
                                              String dayText,
                                              String monthText,
                                              String yearText){
        chooseTitle(title);
        driver.element().type(passwordField, passwordText);
        driver.element().selectFromDropdown(dayDropdown, dayText);
        driver.element().selectFromDropdown(monthDropdown, monthText);
        driver.element().selectFromDropdown(yearDropdown, yearText);
        return this;
    }

    @Step("Select newsletter and special offers checkboxes")
    public SignupPage selectSubscriptions(){
        driver.element().click(newsletterCheckbox);
        driver.element().click(specialOffersCheckbox);
        return this;
    }

    @Step("Enter address information")
    public SignupPage enterAddressInformation(String firstNameText,
                                              String lastNameText,
                                              String companyText,
                                              String address1Text,
                                              String address2Text,
                                              String countryText,
                                              String stateText,
                                              String cityText,
                                              String zipcodeText,
                                              String mobileNumberText){
        driver.element().type(firstNameField, firstNameText);
        driver.element().type(lastNameField, lastNameText);
        driver.element().type(companyField, companyText);
        driver.element().type(address1Field, address1Text);
        driver.element().type(address2Field, address2Text);
        driver.element().selectFromDropdown(countryDropdown, countryText);
        driver.element().type(stateField, stateText);
        driver.element().type(cityField, cityText);
        driver.element().type(zipcodeField, zipcodeText);
        driver.element().type(mobileNumberField, mobileNumberText);
        return this;
    }

    @Step("Click on 'Create Account button'")
    public SignupPage clickOnCreateAccountButton(){
        driver.element().click(createAccountButton);
        return this;
    }

    @Step("Click on 'Continue' button")
    public NavigationBarComponent clickOnContinueButton(){
        driver.element().click(continueButton);
        return new NavigationBarComponent(driver);
    }

    //validations
    @Step("Verify that 'ENTER ACCOUNT INFORMATION' is visible")
    public SignupPage verifyEnterAccountInformationVisible(){
        driver.verification().isElementVisible(enterAccountInformationLabel);
        return this;
    }

    @Step("Verify that 'ACCOUNT CREATED!' is visible")
    public SignupPage verifyAccountCreated(){
        driver.verification().isElementVisible(accountCreatedLabel);
        return this;
    }
}
