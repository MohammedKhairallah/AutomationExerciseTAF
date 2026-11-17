package com.automationexercise.tests.ui;

import com.automationexercise.drivers.GUIDriver;
import com.automationexercise.pages.HomePage;
import com.automationexercise.tests.BaseTest;
import com.automationexercise.utils.TimeManager;
import com.automationexercise.utils.dataReader.JsonReader;
import io.qameta.allure.*;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Epic("Customer Support")
@Feature("Contact Us")
@Owner("Mohammed Khairallah")
public class ContactUsTest extends BaseTest {

    @Severity(SeverityLevel.NORMAL)
    @Story("Submit Customer Support Inquiry")
    @Description("Verify that a user can successfully submit the Contact Us form with a valid file attachment.")
    @Test(description = "TC6: Contact Us Form")
    public void verifyUserCanSubmitContactUsForm(){
        String name = testData.getJsonData("contactForm.name");
        String email = testData.getJsonData("contactForm.email") + TimeManager.getSimpleTimestamp() + "@gmail.com";
        String subject = testData.getJsonData("contactForm.subject");
        String message = testData.getJsonData("contactForm.message");
        String filePath = testData.getJsonData("contactForm.filePath");
        String expectedSuccessMsg = testData.getJsonData("messages.successMessage");

        new HomePage(driver)
                .navigationBar()
                .clickOnContactUsButton()
                .verifyContactUsPageIsVisible()
                .enterContactDetails(name, email, subject, message)
                .uploadFile(filePath)
                .clickSubmitButton()
                .clickOkOnAlert()
                .verifySuccessMessage(expectedSuccessMsg);;
    }


    //Configurations
    @BeforeClass
    protected void loadTestData() {
        testData = new JsonReader("contact-us-data");
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
