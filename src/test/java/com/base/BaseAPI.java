package com.base;

import com.microsoft.playwright.APIRequest;
import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.Playwright;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

public class BaseAPI extends BaseTest{

    Playwright playwright;
    APIRequest apiRequest;
    public APIRequestContext apiRequestContext;


    @BeforeClass
    public void setUp(){
        playwright = Playwright.create();
        apiRequest = playwright.request();
        apiRequestContext = apiRequest.newContext();
        loginPageInterface.LoginMethod();

    }

    @AfterClass
    public void tearDown(){
        playwright.close();
    }

}
