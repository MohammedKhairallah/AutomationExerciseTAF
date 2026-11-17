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
@Feature("Registration")
@Owner("Mohammed Khairallah")
public class SignupTest extends BaseTest {

    private String uniqueEmail() {
        return testData.getJsonData("email") + TimeManager.getSimpleTimestamp() + "@gmail.com";
    }

    //Tests
    @Story("Signup with valid data")
    @Severity(SeverityLevel.CRITICAL)
    @Description("As a new user, I want to create an account so I can track orders. "
            + "Verify user can sign up with complete details, see 'ACCOUNT CREATED!', and then delete the account")
    @Test(description = "TC01: Register User")
    public void verifyUserCanSignupWithValidDataAndDeleteAccount(){
        String email = uniqueEmail();

        new HomePage(driver)
                .navigationBar()
                .clickOnSignupLoginButton()
                .verifyNewUserSignupVisible()
                .enterSignupName(testData.getJsonData("name"))
                .enterSignupEmail(email)
                .clickOnSignupButton()
                .verifyEnterAccountInformationVisible()
                .enterAccountInformation(testData.getJsonData("titleMale"), testData.getJsonData("password"),
                        testData.getJsonData("day"), testData.getJsonData("month"),
                        testData.getJsonData("year"))
                .selectSubscriptions()
                .enterAddressInformation(testData.getJsonData("firstName"), testData.getJsonData("lastName"),
                        testData.getJsonData("companyName"), testData.getJsonData("address1"),
                        testData.getJsonData("address2"), testData.getJsonData("country"),
                        testData.getJsonData("state"), testData.getJsonData("city"),
                        testData.getJsonData("zipCode"), testData.getJsonData("mobileNumber"))
                .clickOnCreateAccountButton()
                .verifyAccountCreated()
                .clickOnContinueButton()
                .verifyUserLabel(testData.getJsonData("name"))
                .clickOnDeleteAccountButton()
                .verifyAccountDeleted()
                .clickOnContinueButton();
    }

    @Story("Signup with existing email")
    @Severity(SeverityLevel.NORMAL)
    @Description("As a user, I should not be allowed to register with an email already in use. "
            + "Verify the error message is shown when signing up with an existing email")
    @Test(description = "TC05: Register User with existing email")
    public void verifyUserCannotSignupWithExistingEmail(){
        // Precondition: create the user through the API.
        String email = uniqueEmail();

        new UserManagementAPI().createUserAccount(
                        testData.getJsonData("name"),  email,
                        testData.getJsonData("password"), testData.getJsonData("titleMale"),
                        testData.getJsonData("day"), testData.getJsonData("month"),
                        testData.getJsonData("year"), testData.getJsonData("firstName"),
                        testData.getJsonData("lastName"), testData.getJsonData("companyName"),
                        testData.getJsonData("address1"), testData.getJsonData("address2"),
                        testData.getJsonData("country"), testData.getJsonData("zipCode"),
                        testData.getJsonData("state"), testData.getJsonData("city"),
                        testData.getJsonData("mobileNumber")
                )
                .verifyUserCreatedSuccessfully();

        // try to sign up again with the same email
        new HomePage(driver)
                .navigationBar()
                .clickOnSignupLoginButton()
                .verifyNewUserSignupVisible()
                .enterSignupName(testData.getJsonData("name"))
                .enterSignupEmail(email)
                .clickOnSignupButtonExpectingError()
                .verifySignupErrorMessage(testData.getJsonData("errorMessages.emailAlreadyExists"));

        new UserManagementAPI()
                .deleteUserAccount(email, testData.getJsonData("password"))
                .verifyUserDeletedSuccessfully();
    }


    //Configurations
    @BeforeClass
    protected void loadTestData() {
        testData = new JsonReader("signup-data");
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
