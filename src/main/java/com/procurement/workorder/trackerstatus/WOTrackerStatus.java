package com.procurement.workorder.trackerstatus;

import com.interfaces.LoginPageInterface;
import com.interfaces.LogoutPageInterface;
import com.interfaces.WOTrackerStatusInterface;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Response;

import java.util.Properties;

public class WOTrackerStatus implements WOTrackerStatusInterface {

    Properties properties;
    Page page;
    LoginPageInterface loginPageInterface;
    LogoutPageInterface logoutPageInterface;

    private WOTrackerStatus(){
    }

//TODO Constructor
    public WOTrackerStatus(LoginPageInterface loginPageInterface, Properties properties, Page page, LogoutPageInterface logoutPageInterface){
        this.properties = properties;
        this.page = page;
        this.loginPageInterface = loginPageInterface;
        this.logoutPageInterface = logoutPageInterface;
    }

    public void VendorTrackerStatus() throws InterruptedException {
        Response response = page.waitForResponse(
                resp -> resp.url().equals("https://dprocure-uat.cormsquare.com/api/VP/RequestForQuotations/Listing") && resp.status() == 200,
                () -> {
                    // Click the login button, which triggers the request
                    loginPageInterface.LoginMethod(properties.getProperty("VendorMailId"));
                }
        );

        page.locator("//*[contains(text(), 'Work Orders')]").click();
        Thread.sleep(1000);
        String poReferenceId = properties.getProperty("PoReferenceId");

//        page.locator("//div[@id='pagination']//a[text()='4']").click();


//        List<String> containerList = page.locator("#listContainer tr td").allTextContents();
//        for(String tr : containerList){
//            if(tr.contains(poReferenceId)){
//                page.locator(".btn-link").first().click();
//            }
//        }
        Locator rows = page.locator("//tr");

        // Iterate through each row
        int rowCount = rows.count();
        for (int i = 0; i < rowCount; i++) {
            Locator row = rows.nth(i);

            // Locate all elements within the row (e.g., <td> elements)
            Locator cells = row.locator("//td");

            // Iterate through each cell in the row
            int cellCount = cells.count();
            for (int j = 0; j < cellCount; j++) {
                String cellText = cells.nth(j).innerText();

                // Your condition to check if the cell contains the desired text
                if (cellText.contains(poReferenceId)) {
                    // Perform the desired action within the current row
                    row.locator(".text-primary").first().click();
                    break;
                }
            }
        }
        Thread.sleep(1000);
        String[] status = {"Material_Pick_Up","ETD","Arrival_Notification","Import_Clearance","Out_for_Delivery", "Delivery_Completed"};
        for(int i = 0; i < status.length; i++) {
            page.waitForSelector(".form-control.flatpickr-custom.form-control.input").click();
            Locator today = page.locator("//span[@class='flatpickr-day today']").first();
            today.click();
            page.locator("#select2-statusId-container").last().click();
            page.waitForSelector("//li[contains(text(), '"+ status[i] +"')]").click();
            page.waitForSelector("#btnSubmit").click();
            page.waitForSelector(".bootbox-accept").click();
            Thread.sleep(2000);
        }
        logoutPageInterface.LogoutMethod();
    }
}