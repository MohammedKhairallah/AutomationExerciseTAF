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

@Epic("User Management")
@Feature("Authentication")
@Owner("Mohammed Khairallah")
public class LoginTest extends BaseTest {
    private String uniqueEmail() {
        return testData.getJsonData("email") + TimeManager.getSimpleTimestamp() + "@gmail.com";
    }

    //Tests
    @Story("Login with valid credentials")
    @Severity(SeverityLevel.CRITICAL)
    @Description("As a registered user, I want to log in to access my account. "
            + "Verify user can log in with a valid email and password")
    @Test(description = "TC02: Login User with correct email and password")
    public void verifyUserCanLoginWithValidCredentials(){
        // Precondition: create the user through the API.
        String email = uniqueEmail();

        new UserManagementAPI().createUserAccount(testData.getJsonData("name"), email,
                testData.getJsonData("password"),testData.getJsonData("firstName"),
                testData.getJsonData("lastName"))
                .verifyUserCreatedSuccessfully();

        // try to login with the same email and password
        new HomePage(driver)
                .navigationBar()
                .clickOnSignupLoginButton()
                .verifyLoginToYourAccountVisible()
                .enterLoginEmail(email)
                .enterLoginPassword(testData.getJsonData("password"))
                .clickOnLoginButton()
                .verifyUserLabel(testData.getJsonData("name"))
                .clickOnDeleteAccountButton()
                .verifyAccountDeleted()
                .clickOnContinueButton();
    }


    @Story("Login with invalid credentials")
    @Severity(SeverityLevel.NORMAL)
    @Description("As a user, I want to see an error message if I enter wrong credentials. "
            + "Verify user cannot log in with an invalid email and password")
    @Test(description = "TC03: Login User with incorrect email and password")
    public void verifyUserCannotLoginWithInvalidCredentials(){
        // No user is needed: the credentials do not exist on the site

        new HomePage(driver)
                .navigationBar()
                .clickOnSignupLoginButton()
                .verifyLoginToYourAccountVisible()
                .enterLoginEmail(testData.getJsonData("invalidEmail"))
                .enterLoginPassword(testData.getJsonData("invalidPassword"))
                .clickOnLoginButtonExpectingError()
                .verifyLoginErrorMessage(testData.getJsonData("errorMessages.incorrectLogin"));
    }

    @Story("Logout")
    @Severity(SeverityLevel.NORMAL)
    @Description("As a logged-in user, I want to log out to secure my account. "
            + "Verify a logged-in user can log out")
    @Test(description = "TC04: Logout User")
    public void verifyUserCanLogout(){
        // Precondition: create the user through the API.
        String email = uniqueEmail();

        new UserManagementAPI().createUserAccount(testData.getJsonData("name"), email,
                        testData.getJsonData("password"),testData.getJsonData("firstName"),
                        testData.getJsonData("lastName"))
                .verifyUserCreatedSuccessfully();

        // try to login with the same email and password
        new HomePage(driver)
                .navigationBar()
                .clickOnSignupLoginButton()
                .verifyLoginToYourAccountVisible()
                .enterLoginEmail(email)
                .enterLoginPassword(testData.getJsonData("password"))
                .clickOnLoginButton()
                .verifyUserLabel(testData.getJsonData("name"))
                .clickOnLogoutButton()
                .verifyLoginToYourAccountVisible();

        new UserManagementAPI()
                .deleteUserAccount(email, testData.getJsonData("password"))
                .verifyUserDeletedSuccessfully();
    }

    //Configurations
    @BeforeClass
    protected void loadTestData() {
        testData = new JsonReader("login-data");
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
        if (driver != null){
            driver.quitDriver();
        }
    }
}
