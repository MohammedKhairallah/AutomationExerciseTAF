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

@Epic("Product Discovery")
@Owner("Mohammed Khairallah")
public class ProductsTest extends BaseTest {

    //Test
    @Severity(SeverityLevel.NORMAL)
    @Feature("Product Search")
    @Story("Search Products by Name")
    @Description("Verify that a user can search for a product by name and view relevant search results.")
    @Test(description = "TC09: Search Product")
    public void verifyUserCanSearchForProductByName(){
        String productName = testData.getJsonData("searchProduct.name");
        String productPrice = testData.getJsonData("searchProduct.price");

        new HomePage(driver)
                .navigationBar()
                .clickOnProductsButton()
                .verifyUserIsNavigatedToAllProductsPage()
                .searchProduct(productName)
                .verifySearchedProductsTitleIsVisible()
                .validateProductDetails(productName, productPrice);
    }


    @Severity(SeverityLevel.NORMAL)
    @Feature("Product Categories")
    @Story("Browse Products by Category")
    @Description("Verify that a user can browse products by selecting different categories from the sidebar.")
    @Test(description = "TC18: View Category Products")
    public void verifyUserCanViewProductsByCategory(){
        // Read Data
        String womenCat = testData.getJsonData("categories.womenCategory");
        String womenSubCat = testData.getJsonData("categories.womenSubCategory");
        String expectedWomenHeader = testData.getJsonData("categories.expectedWomenHeader");

        String menCat = testData.getJsonData("categories.menCategory");
        String menSubCat = testData.getJsonData("categories.menSubCategory");
        String expectedMenHeader = testData.getJsonData("categories.expectedMenHeader");

        new HomePage(driver)
                .sideBar()
                .verifySideBarIsVisible()
                .clickOnCategory(womenCat, womenSubCat)
                .verifyCategoryPageIsDisplayed(expectedWomenHeader)
                .sideBar()
                .clickOnCategory(menCat, menSubCat)
                .verifyCategoryPageIsDisplayed(expectedMenHeader);
    }

    @Severity(SeverityLevel.NORMAL)
    @Feature("Brand Filtering")
    @Story("Browse Products by Brand")
    @Description("Verify that a user can view products from different brands by selecting them from the sidebar.")
    @Test(description = "TC19: View & Cart Brand Products")
    public void verifyUserCanViewAndAddBrandProductsToCart(){
        String firstBrand = testData.getJsonData("brands.firstBrand");
        String secondBrand = testData.getJsonData("brands.secondBrand");
        String expectedFirstBrandHeader = testData.getJsonData("brands.expectedFirstBrandHeader");
        String expectedSecondBrandHeader = testData.getJsonData("brands.expectedSecondBrandHeader");
        new HomePage(driver)
                .navigationBar()
                .clickOnProductsButton()
                .verifyUserIsNavigatedToAllProductsPage()
                .sideBar()
                .verifySideBarIsVisible()
                .clickOnBrand(firstBrand)
                .verifyBrandPageIsDisplayed(expectedFirstBrandHeader)
                .clickOnBrand(secondBrand)
                .verifyBrandPageIsDisplayed(expectedSecondBrandHeader);
    }

    //Configurations
    @BeforeClass
    protected void loadTestData() {
        testData = new JsonReader("products-data");
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
