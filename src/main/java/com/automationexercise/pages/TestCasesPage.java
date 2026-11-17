package com.automationexercise.pages;

import com.automationexercise.drivers.GUIDriver;
import com.automationexercise.pages.components.FooterComponent;
import com.automationexercise.pages.components.NavigationBarComponent;
import com.automationexercise.utils.dataReader.PropertyReader;
import com.automationexercise.utils.logs.LogsManager;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class TestCasesPage {
    private final GUIDriver driver;
    private final NavigationBarComponent navigationBar;
    private final FooterComponent footer;

    public TestCasesPage(GUIDriver driver) {
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
    private final String TEST_CASES_ENDPOINT = "/test_cases";

    //locators
    private final By testCasesBanner = By.xpath("//h2[@class='title text-center']//b[text()='Test Cases']");

    //actions

    //validations
    @Step("Verify user is navigated to the Test Cases page")
    public TestCasesPage verifyUserIsNavigatedToTestCasesPage(){
        driver.verification().assertPageUrl(PropertyReader.getProperty("baseUrlWeb") + TEST_CASES_ENDPOINT);
        driver.verification().isElementVisible(testCasesBanner);

        LogsManager.info("Test Cases page loaded successfully.");

        return this;
    }
}
