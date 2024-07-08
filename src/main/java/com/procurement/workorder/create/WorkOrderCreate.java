package com.procurement.workorder.create;

import com.interfaces.LoginPageInterface;
import com.interfaces.LogoutPageInterface;
import com.interfaces.WorkOrderCreateInterface;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

import java.util.Properties;

public class WorkOrderCreate implements WorkOrderCreateInterface {

    Properties properties;
    Page page;
    LoginPageInterface loginPageInterface;
    LogoutPageInterface logoutPageInterface;

    private WorkOrderCreate(){
    }

//TODO Constructor
    public WorkOrderCreate(LoginPageInterface loginPageInterface, Properties properties, Page page, LogoutPageInterface logoutPageInterface){
        this.properties = properties;
        this.page = page;
        this.logoutPageInterface = logoutPageInterface;
        this.loginPageInterface = loginPageInterface;
    }

    public void WOCreate() throws InterruptedException {
        loginPageInterface.LoginMethod(properties.getProperty("LogisticsManager"));
        Thread.sleep(1000);
        page.locator("//*[contains(text(), 'Dispatch Notes')]").click();
        String poReferenceId = properties.getProperty("PoReferenceId");
        Thread.sleep(1000);
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
        page.locator("#dnActionDropdown").click();
        page.locator("#btnToCreateWorkOrder").click();
        Thread.sleep(2000);
        page.locator("#select2-freightForwarderId-container").first().click();
        String vendorId = properties.getProperty("Vendor");
        page.locator(".select2-search__field").fill(vendorId);
        page.locator("//li[contains(text(), '" + vendorId + "')]").first().click();
        page.locator("#select2-priority-container").click();
        page.locator("//li[contains(text(), 'High')]").click();
        page.getByPlaceholder("Select Date").last().click();
        Locator today = page.locator("//span[@class='flatpickr-day today']").first();
        today.click();
        page.locator("#destinationTermLocationId").fill("India");
        page.locator("//*[contains(text(), 'Proceed')]").click();
        Thread.sleep(2000);
        page.locator("#vendorSendMailBtnId").click();
        page.locator(".bootbox-accept").click();
        Thread.sleep(1000);
        logoutPageInterface.LogoutMethod();
    }
}