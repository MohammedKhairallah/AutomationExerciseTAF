package com.automationexercise.pages;

import com.automationexercise.drivers.GUIDriver;
import com.automationexercise.pages.components.FooterComponent;
import com.automationexercise.pages.components.NavigationBarComponent;
import com.automationexercise.utils.dataReader.PropertyReader;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class SignupLoginPage {
    private final GUIDriver driver;
    private final NavigationBarComponent navigationBar;
    private final FooterComponent footer;

    public SignupLoginPage(GUIDriver driver) {
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


    private final String SIGNUP_LOGIN_ENDPOINT = "/login";

    //locators
    private final By loginEmail = By.cssSelector("[data-qa=\"login-email\"]");
    private final By loginPassword = By.cssSelector("[data-qa=\"login-password\"]");
    private final By loginButton = By.cssSelector("[data-qa=\"login-button\"]");
    private final By signupName = By.cssSelector("[data-qa=\"signup-name\"]");
    private final By signupEmail = By.cssSelector("[data-qa=\"signup-email\"]");
    private final By signupButton = By.cssSelector("[data-qa=\"signup-button\"]");
    private final By newUserSignupLabel = By.cssSelector(".signup-form > h2");
    private final By loginToYourAccountLabel = By.cssSelector(".login-form > h2");
    private final By loginError = By.cssSelector(".login-form p");
    private final By signupError = By.cssSelector(".signup-form p");

    //actions
    @Step("Navigate to the Signup/Login Page")
    public SignupLoginPage navigate(){
        driver.browser().navigateTo(PropertyReader.getProperty("baseUrlWeb") + SIGNUP_LOGIN_ENDPOINT);
        return this;
    }

    @Step("Enter email {email} in login field")
    public SignupLoginPage enterLoginEmail(String email){
        driver.element().type(loginEmail, email);
        return this;
    }

    @Step("Enter password {password} in login field")
    public SignupLoginPage enterLoginPassword(String password){
        driver.element().type(loginPassword, password);
        return this;
    }
    //make edit
    @Step("Click on 'Login' Button")
    public NavigationBarComponent clickOnLoginButton(){
        driver.element().click(loginButton);
        return new NavigationBarComponent(driver);
    }

    @Step("Click on 'Login' Button expecting an error")
    public SignupLoginPage clickOnLoginButtonExpectingError(){
        driver.element().click(loginButton);
        return this;
    }

    @Step("Enter name {name} in signup field")
    public SignupLoginPage enterSignupName(String name){
        driver.element().type(signupName, name);
        return this;
    }

    @Step("Enter email {email} in signup field")
    public SignupLoginPage enterSignupEmail(String email){
        driver.element().type(signupEmail, email);
        return this;
    }

    @Step("Click on 'Signup' Button")
    public SignupPage clickOnSignupButton(){
        driver.element().click(signupButton);
        return new SignupPage(driver);
    }

    @Step("Click on 'Signup' Button expecting an error")
    public SignupLoginPage clickOnSignupButtonExpectingError() {
        driver.element().click(signupButton);
        return this;
    }

    //validations
    @Step("Verify 'New User Signup!' is visible")
    public SignupLoginPage verifyNewUserSignupVisible(){
        driver.verification().isElementVisible(newUserSignupLabel);
        return this;
    }

    @Step("Verify 'Login to your account' is visible")
    public SignupLoginPage verifyLoginToYourAccountVisible(){
        driver.verification().isElementVisible(loginToYourAccountLabel);
        return this;
    }

    @Step("Verify login error message is '{errorExpected}'")
    public SignupLoginPage verifyLoginErrorMessage(String errorExpected){
        String errorActual = driver.element().getText(loginError);
        driver.verification().Equals(errorActual, errorExpected, "Login error message is not as expected");
        return this;
    }

    @Step("Verify signup error message is '{errorExpected}'")
    public SignupLoginPage verifySignupErrorMessage(String errorExpected){
        String errorActual = driver.element().getText(signupError);
        driver.verification().Equals(errorActual, errorExpected, "Signup error message is not as expected");
        return this;
    }
}
