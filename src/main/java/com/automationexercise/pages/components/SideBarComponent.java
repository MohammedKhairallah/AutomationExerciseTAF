package com.automationexercise.pages.components;

import com.automationexercise.drivers.GUIDriver;
import com.automationexercise.pages.ProductsPage;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class SideBarComponent {
    private final GUIDriver driver;

    public SideBarComponent(GUIDriver driver) {
        this.driver = driver;
    }

    //locators
    private final By categoryTitle = By.xpath("//h2[text()='Category']");
    private final By brandsTitle = By.xpath("//h2[text()='Brands']");

    //dynamic locators
    private By brandLink(String brandName) {
        return By.cssSelector(".brands_products a[href='/brand_products/" + brandName + "']");
    }

    private By categoryAccordionButton(String categoryName) {
        return By.xpath("//a[@href='#" + categoryName + "']");
    }

    private By categoryLink(String category, String subCategory) {
        return By.xpath("//div[@id='" + category + "']//a[contains(text(), '" + subCategory + "')]");
    }


    // Actions
    @Step("Click on brand: {brandName}")
    public ProductsPage clickOnBrand(String brandName) {
        driver.element().click(brandLink(brandName));
        return new ProductsPage(driver);
    }

    @Step("Click on category: {category} -> {subCategory}")
    public ProductsPage clickOnCategory(String category, String subCategory) {
        driver.element().click(categoryAccordionButton(category)); // Expand accordion
        driver.element().click(categoryLink(category, subCategory)); // Click subcategory
        return new ProductsPage(driver);
    }

    // Validations
    @Step("Verify Sidebar is visible")
    public SideBarComponent verifySideBarIsVisible() {
        driver.verification().isElementVisible(categoryTitle);
        driver.verification().isElementVisible(brandsTitle);
        return this;
    }
}
