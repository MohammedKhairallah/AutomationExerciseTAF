package com.automationexercise.apis;

import com.automationexercise.utils.logs.LogsManager;
import com.automationexercise.validations.Verification;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.HashMap;
import java.util.Map;

public class UserManagementAPI {
    RequestSpecification requestSpecification;
    Response response;

    Verification verification;

    public UserManagementAPI() {
        // Constructor
        requestSpecification = RestAssured.given();
        verification = new Verification();
    }

    //endpoints
    private static final String CREATE_ACCOUNT_ENDPOINT = "/createAccount";
    private static final String DELETE_ACCOUNT_ENDPOINT = "/deleteAccount";

    //api methods
    //name, email, password, title (for example: Mr, Mrs, Miss), birth_date, birth_month, birth_year, firstname, lastname, company, address1, address2, country, zipcode, state, city, mobile_number
    @Step("Create a new user account with full details")
    public UserManagementAPI createUserAccount(String name, String email, String password, String title,
                                               String birthDate, String birthMonth, String birthYear,
                                               String firstName, String lastName, String company, String address1,
                                               String address2, String country, String zipcode, String state,
                                               String city, String mobileNumber)
    {
        Map<String, String> formParams = new HashMap<>();
        formParams.put("name", name);
        formParams.put("email", email);
        formParams.put("password", password);
        formParams.put("title", title);
        formParams.put("birth_date", birthDate);
        formParams.put("birth_month", birthMonth);
        formParams.put("birth_year", birthYear);
        formParams.put("firstname", firstName);
        formParams.put("lastname", lastName);
        formParams.put("company", company);
        formParams.put("address1", address1);
        formParams.put("address2", address2);
        formParams.put("country", country);
        formParams.put("zipcode", zipcode);
        formParams.put("state", state);
        formParams.put("city", city);
        formParams.put("mobile_number", mobileNumber);

        response = requestSpecification.spec(Builder.getUserManagementRequestSpecification(formParams))
                .post(CREATE_ACCOUNT_ENDPOINT);

        LogsManager.info(response.asPrettyString());
        return this;
    }

    //name, email, password, firstname, lastname
    @Step("Create a new user account with minimal details")
    public UserManagementAPI createUserAccount(String name, String email, String password, String firstName,
                                               String lastName)
    {
        Map<String, String> formParams = new HashMap<>();
        formParams.put("name", name);
        formParams.put("email", email);
        formParams.put("password", password);
        formParams.put("title", "Mr");
        formParams.put("birth_date", "23");
        formParams.put("birth_month", "August");
        formParams.put("birth_year", "2000");
        formParams.put("firstname", firstName);
        formParams.put("lastname", lastName);
        formParams.put("company", "Automation_Company");
        formParams.put("address1", "Automation_Address1");
        formParams.put("address2", "Automation_Address2");
        formParams.put("country", "United States");
        formParams.put("zipcode", "12345");
        formParams.put("state", "Automation_State");
        formParams.put("city", "Automation_City");
        formParams.put("mobile_number", "1234567890");

        response = requestSpecification.spec(Builder.getUserManagementRequestSpecification(formParams))
                .post(CREATE_ACCOUNT_ENDPOINT);

        LogsManager.info(response.asPrettyString());
        return this;
    }

    @Step("Delete user account by email")
    public UserManagementAPI deleteUserAccount(String email, String password) {
        Map<String, String> formParams = new HashMap<>();
        formParams.put("email", email);
        formParams.put("password", password);
        response = requestSpecification.spec(Builder.getUserManagementRequestSpecification(formParams))
                .delete(DELETE_ACCOUNT_ENDPOINT);
        LogsManager.info(response.asPrettyString());
        return this;
    }

    //validations
    @Step("Verify that user is created successfully")
    public UserManagementAPI verifyUserCreatedSuccessfully() {
        verification.Equals(response.jsonPath().getString("responseCode"), "201",
                "Response code is not 201");
        verification.Equals(response.jsonPath().get("message"), "User created!",
                "User is not created successfully");
        return this;
    }

    @Step("Verify that user is deleted successfully")
    public UserManagementAPI verifyUserDeletedSuccessfully() {
        verification.Equals(response.jsonPath().getString("responseCode"), "200",
                "Response code is not 200");
        verification.Equals(response.jsonPath().get("message"), "Account deleted!",
                "User is not deleted successfully");
        return this;
    }
}
