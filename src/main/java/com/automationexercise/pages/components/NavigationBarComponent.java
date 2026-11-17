package com.automationexercise.pages.components;

import com.automationexercise.drivers.GUIDriver;
import com.automationexercise.pages.*;
import com.automationexercise.utils.dataReader.PropertyReader;
import com.automationexercise.utils.logs.LogsManager;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class NavigationBarComponent {
    private final GUIDriver driver;

    public NavigationBarComponent(GUIDriver driver) {
        this.driver = driver;
    }

    //locators
    private final By homeButton = By.xpath("//a[.=' Home ']");
    private final By productsButton = By.cssSelector("a[href=\"/products\"]");
    private final By cartButton = By.xpath("//a[.=' Cart']");
    private final By signupLoginButton = By.xpath("//a[.=' Signup / Login']");
    private final By logoutButton = By.xpath("//a[.=' Logout']");
    private final By testCasesButton = By.xpath("//a[.=' Test Cases']");
    private final By deleteAccountButton = By.xpath("//a[.=' Delete Account']");
    private final By apiButton = By.xpath("//a[.=' API Testing']");
    private final By videoTutorials = By.xpath("//a[.=' Video Tutorials']");
    private final By contactUsButton = By.xpath("//a[.=' Contact us']");
    private final By homePageLabel = By.cssSelector("h1 > span");
    private final By userLabel = By.xpath("//a[contains(., 'Logged in as')]//b");


    //actions
    @Step("Navigate to the Home Page")
    public NavigationBarComponent navigate(){
        driver.browser().navigateTo(PropertyReader.getProperty("baseUrlWeb"));
        return this;
    }

    @Step("Click on Home Button")
    public NavigationBarComponent clickOnHomeButton(){
        driver.element().click(homeButton);
        return this;
    }

    @Step("Click on Product Button")
    public ProductsPage clickOnProductsButton(){
        driver.element().click(productsButton);
        return new ProductsPage(driver);
    }

    @Step("Click on Cart Button")
    public CartPage clickOnCartButton(){
        driver.element().click(cartButton);
        return new CartPage(driver);
    }

    @Step("Click on Logout Button")
    public SignupLoginPage clickOnLogoutButton(){
        driver.element().click(logoutButton);
        return new SignupLoginPage(driver);
    }

    @Step("Click on Signup/Login Button")
    public SignupLoginPage clickOnSignupLoginButton(){
        driver.element().click(signupLoginButton);
        return new SignupLoginPage(driver);
    }

    @Step("Click on Test Cases Button")
    public TestCasesPage clickOnTestCasesButton(){
        driver.element().click(testCasesButton);
        return new TestCasesPage(driver);
    }

    @Step("Click on Delete Account Button")
    public DeleteAccountPage clickOnDeleteAccountButton(){
        driver.element().click(deleteAccountButton);
        return new DeleteAccountPage(driver);
    }

    @Step("Click on ContactUs Button")
    public ContactUsPage clickOnContactUsButton(){
        driver.element().click(contactUsButton);
        return new ContactUsPage(driver);
    }

    //validations
    @Step("Verify that home page is visible successfully")
    public NavigationBarComponent verifyHomePage(){
        driver.verification().isElementVisible(homePageLabel);
        return this;
    }

    @Step("Verify that 'Logged in as username' is visible\n")
    public NavigationBarComponent verifyUserLabel(String expectedName){
        String actualName = driver.element().getText(userLabel);
        LogsManager.info("Verify user Label: " + actualName);
        driver.verification().Equals(actualName, expectedName, "User label does not match. Expected: " + expectedName + ", Actual: " + actualName);
        return this;
    }

}
