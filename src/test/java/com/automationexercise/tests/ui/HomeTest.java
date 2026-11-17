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

@Owner("Mohammed Khairallah")
public class HomeTest extends BaseTest {

    @Severity(SeverityLevel.NORMAL)
    @Epic("Site Navigation")
    @Feature("Test Cases Page")
    @Story("Navigate to Test Cases Page")
    @Description("Verify that a user can navigate to the Test Cases page using the navigation bar.")
    @Test(description = "TC7: Verify Test Cases Page")
    public void verifyTestCasesPageIsDisplayed(){
        new HomePage(driver)
                .navigationBar()
                .clickOnTestCasesButton()
                .verifyUserIsNavigatedToTestCasesPage();
    }

    @Severity(SeverityLevel.NORMAL)
    @Epic("Customer Engagement")
    @Feature("Newsletter Subscription")
    @Story("Subscribe to Newsletter")
    @Description("Verify that a user can successfully subscribe to the newsletter from the Home page footer.")
    @Test(description = "TC10: Verify Subscription in home page")
    public void verifyUserCanSubscribeFromHomePage(){
        String subscriptionEmail = testData.getJsonData("subscription.email") + TimeManager.getSimpleTimestamp() + "@gmail.com";
        new HomePage(driver)
                .footer()
                .scrollToSubscription()
                .verifySubscriptionIsVisible()
                .enterSubscriptionEmail(subscriptionEmail)
                .clickOnSubscribeButton()
                .verifySubscriptionSuccessMessage(testData.getJsonData("subscription.successMessage"));
    }

    @Severity(SeverityLevel.NORMAL)
    @Epic("Site Navigation")
    @Feature("Page Scrolling")
    @Story("Scroll to Top Using Arrow Button")
    @Description("Verify that a user can scroll to the bottom of the page and return to the top using the scroll-up arrow.")
    @Test(description = "TC25: Verify Scroll Up using 'Arrow' button and Scroll Down functionality")
    public void verifyUserCanScrollUpUsingArrowButton(){
        new HomePage(driver)
                .footer()
                .scrollToBottom()
                .verifySubscriptionIsVisible()
                .clickOnScrollUpArrow();

        new HomePage(driver)
                .verifyPageIsScrolledUp();
    }

    @Severity(SeverityLevel.NORMAL)
    @Epic("Site Navigation")
    @Feature("Page Scrolling")
    @Story("Scroll to Top Without Arrow Button")
    @Description("Verify that a user can scroll to the bottom of the page and return to the top without using the scroll-up arrow.")
    @Test(description = "TC26: Verify Scroll Up without 'Arrow' button and Scroll Down functionality")
    public void verifyUserCanScrollUpWithoutArrowButton(){
        new HomePage(driver)
                .footer()
                .scrollToBottom()
                .verifySubscriptionIsVisible();

        new HomePage(driver)
                .scrollUpToTop();
    }

    //Configurations
    @BeforeClass
    protected void loadTestData() {
        testData = new JsonReader("home-data");
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
