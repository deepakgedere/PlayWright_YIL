package com.procurement.workorder.approvals;

import com.interfaces.LoginPageInterface;
import com.interfaces.LogoutPageInterface;
import com.interfaces.WOSendForApprovalInterface;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

import java.util.Properties;

public class WOSendForApproval implements WOSendForApprovalInterface {
    Properties properties;
    Page page;
    LoginPageInterface loginPageInterface;
    LogoutPageInterface logoutPageInterface;
    public WOSendForApproval(LoginPageInterface loginPageInterface, Properties properties, Page page, LogoutPageInterface logoutPageInterface){
        this.properties = properties;
        this.page = page;
        this.logoutPageInterface = logoutPageInterface;
        this.loginPageInterface = loginPageInterface;
    }
    public void SendForApproval() throws InterruptedException {
        loginPageInterface.LoginMethod(properties.getProperty("LogisticsManager"));
        Thread.sleep(1000);
        page.waitForSelector("//*[contains(text(), 'Work Orders')]").click();
        String poReferenceId = properties.getProperty("PoReferenceId");
        Thread.sleep(1000);
//        List<String> containerList = page.locator("#listContainer tr td").allTextContents();
//        for(String tr : containerList){
//            if(tr.contains(poReferenceId)){
//                page.locator(".btn-link").first().click();
//            }
//        }

        // Locate all <tr> elements
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
        page.waitForSelector("#btnSendApproval").click();
        page.locator(".bootbox-accept").click();
        Thread.sleep(1000);
        logoutPageInterface.LogoutMethod();
    }
}
