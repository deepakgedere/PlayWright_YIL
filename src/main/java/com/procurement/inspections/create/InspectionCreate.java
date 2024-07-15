package com.procurement.inspections.create;

import com.interfaces.InspectionCreateInterface;
import com.interfaces.LoginPageInterface;
import com.interfaces.LogoutPageInterface;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Response;
import com.microsoft.playwright.options.LoadState;

import java.util.Properties;

public class InspectionCreate implements InspectionCreateInterface {

    Properties properties;
    Page page;
    LoginPageInterface loginPageInterface;
    LogoutPageInterface logoutPageInterface;

    private InspectionCreate(){
    }

//TODO Constructor
    public InspectionCreate(LoginPageInterface loginPageInterface, Properties properties, Page page, LogoutPageInterface logoutPageInterface){
        this.loginPageInterface = loginPageInterface;
        this.properties = properties;
        this.page = page;
        this.logoutPageInterface = logoutPageInterface;
    }

    public void VendorInspectionCreate() throws InterruptedException {
        Response response = page.waitForResponse(
                resp -> resp.url().equals("https://dprocure-uat.cormsquare.com/api/VP/RequestForQuotations/Listing") && resp.status() == 200,
                () -> {
                    // Click the login button, which triggers the request
                    loginPageInterface.LoginMethod(properties.getProperty("VendorMailId"));
                }
        );

        String poReferenceId = properties.getProperty("PoReferenceId");

        Response x = page.waitForResponse(
                resp -> resp.url().equals("https://dprocure-uat.cormsquare.com/api/VP/OrderSchedules/Listing") && resp.status() == 200,
                () -> {
                    page.locator("//*[contains(text(), 'Order Schedules')]").click();
                }
        );
       // https://dprocure-uat.cormsquare.com/api/VP/ActivityLogs

// Click the login button, which triggers the request
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

//        https://dprocure-uat.cormsquare.com/api/VP/OrderSchedules/Listing

//        List<String> containerList = page.locator("#listContainer tr td").allTextContents();
//        for(String tr : containerList){
//            if(tr.contains(poReferenceId)){
//                page.locator(".btn-link").first().click();s
//            }
//        }

//
//        page.waitForLoadState(LoadState.DOMCONTENTLOADED);
//        Thread.sleep(1000);
        page.locator("#btnSendForInspection").click();
        Thread.sleep(1000);

        page.locator(".bootbox-accept").click();
        Thread.sleep(1000);

        logoutPageInterface.LogoutMethod();
    }
}
