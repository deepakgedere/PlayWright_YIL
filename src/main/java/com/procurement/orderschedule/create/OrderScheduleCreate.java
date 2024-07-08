package com.procurement.orderschedule.create;
import com.interfaces.OrderScheduleInterface;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.factory.PlayWrightFactory;
import com.interfaces.LoginPageInterface;
import com.interfaces.LogoutPageInterface;
import com.microsoft.playwright.Response;

import java.util.Properties;

public class OrderScheduleCreate implements OrderScheduleInterface {

    PlayWrightFactory playWrightFactory;
    Properties properties;
    Page page;
    LoginPageInterface loginPageInterface;
    LogoutPageInterface logoutPageInterface;

    private OrderScheduleCreate() {
    }

    public OrderScheduleCreate(LoginPageInterface loginPageInterface, Properties properties, Page page, LogoutPageInterface logoutPageInterface, PlayWrightFactory playWrightFactory) {
        this.loginPageInterface = loginPageInterface;
        this.properties = properties;
        this.page = page;
        this.logoutPageInterface = logoutPageInterface;
        this.playWrightFactory = playWrightFactory;
    }

    public void OSCreate() throws InterruptedException {
        Response response = page.waitForResponse(
                resp -> resp.url().equals("https://dprocure-uat.cormsquare.com/api/VP/RequestForQuotations/Listing") && resp.status() == 200,
                () -> {
                    // Click the login button, which triggers the request
                    loginPageInterface.LoginMethod(properties.getProperty("VendorMailId"));
                }
        );
        page.locator("//*[contains(text(), 'Purchase Orders')]").click();
        String title = properties.getProperty("Title");
        page.locator("//*[contains(text(), '" + title + "')]").first().click();
        String poReferenceId = page.waitForSelector("#referenceId").textContent();
        playWrightFactory.savePropertiesToFile(poReferenceId);
        page.locator("#btnCreateOR").click();
        Locator orderScheduleDate = page.locator(".scheduleDate-1").last();
        orderScheduleDate.click();
        Locator today = page.locator("//span[@class='flatpickr-day today']").first();
        today.click();
        page.locator("#btnCreate").click();
        page.waitForSelector(".bootbox-accept").click();
        Thread.sleep(1000);
        logoutPageInterface.LogoutMethod();
    }
}