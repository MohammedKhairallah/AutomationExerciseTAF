package com.automationexercise.pages;

import com.automationexercise.drivers.GUIDriver;
import com.automationexercise.pages.components.NavigationBarComponent;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class DeleteAccountPage {
    private final GUIDriver driver;

    public DeleteAccountPage(GUIDriver driver) {
        this.driver = driver;
    }

    //locators
    private final By accountDeletedLabel = By.cssSelector("h2[data-qa='account-deleted'] b");
    private final By continueButton = By.cssSelector("[data-qa=\"continue-button\"]");

    //actions
    @Step("Click on 'Continue' button after account deletion")
    public NavigationBarComponent clickOnContinueButton(){
        driver.element().click(continueButton);
        return new NavigationBarComponent(driver);
    }

    //validations
    @Step("Verify that 'ACCOUNT DELETED!' is visible")
    public DeleteAccountPage verifyAccountDeleted(){
        driver.verification().isElementVisible(accountDeletedLabel);
        return this;
    }
}
