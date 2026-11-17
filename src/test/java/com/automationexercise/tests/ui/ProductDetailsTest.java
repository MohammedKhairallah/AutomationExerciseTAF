package com.automationexercise.tests.ui;

import com.automationexercise.drivers.GUIDriver;
import com.automationexercise.pages.HomePage;
import com.automationexercise.pages.components.NavigationBarComponent;
import com.automationexercise.tests.BaseTest;
import com.automationexercise.utils.dataReader.JsonReader;
import io.qameta.allure.*;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Owner("Mohammed Khairallah")
public class ProductDetailsTest extends BaseTest {


    @Severity(SeverityLevel.NORMAL)
    @Epic("Product Discovery")
    @Feature("Product Catalog")
    @Story("View Product Details")
    @Description("Verify that a user can access the Products page, view the product catalog, and navigate to a product details page.")
    @Test(description = "TC08:  Verify All Products and product detail page")
    public void verifyAllProductsAndProductDetailsAreDisplayed(){
        String name = testData.getJsonData("productDetails.name");
        String category = testData.getJsonData("productDetails.category");
        String price =testData.getJsonData("productDetails.price");
        String availability = testData.getJsonData("productDetails.availability");
        String condition = testData.getJsonData("productDetails.condition");
        String brand = testData.getJsonData("productDetails.brand");

        new HomePage(driver)
                .navigationBar()
                .clickOnProductsButton()
                .verifyUserIsNavigatedToAllProductsPage()
                .verifyProductsListIsVisible()
                .clickOnViewProduct(testData.getJsonData("productDetails.name"))
                .verifyUserIsNavigatedToProductDetailsPage()
                .verifyProductDetails(name, category, price, availability, condition, brand);
    }

    @Severity(SeverityLevel.NORMAL)
    @Epic("Customer Engagement")
    @Feature("Product Reviews")
    @Story("Submit Product Review")
    @Description("Verify that a user can successfully submit a review for a product.")
    @Test(description = "TC21: Add review on product")
    public void verifyUserCanAddReviewOnProduct() {
        new HomePage(driver)
                .navigationBar()
                .clickOnProductsButton()
                .verifyUserIsNavigatedToAllProductsPage()
                .clickOnViewProduct(testData.getJsonData("productDetails.name"))
                .verifyUserIsNavigatedToProductDetailsPage()
                .verifyWriteYourReviewFormIsVisible()
                .fillReviewForm(testData.getJsonData("reviewData.name"),
                        testData.getJsonData("reviewData.email"),
                        testData.getJsonData("reviewData.review"))
                .clickSubmitReview()
                .verifyReviewSuccessMessage(testData.getJsonData("messages.reviewSuccess"));
    }

    //Configurations
    @BeforeClass
    protected void loadTestData() {
        testData = new JsonReader("productsDetails-data");
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
