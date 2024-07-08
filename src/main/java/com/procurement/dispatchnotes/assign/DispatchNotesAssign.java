package com.procurement.dispatchnotes.assign;

import com.interfaces.DispatchNotesAssignInterface;
import com.interfaces.LoginPageInterface;
import com.interfaces.LogoutPageInterface;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

import java.util.Properties;

public class DispatchNotesAssign implements DispatchNotesAssignInterface {

    Properties properties;
    Page page;
    LoginPageInterface loginPageInterface;
    LogoutPageInterface logoutPageInterface;

    private DispatchNotesAssign(){
    }

//TODO Constructor
    public DispatchNotesAssign(LoginPageInterface loginPageInterface, Properties properties, Page page, LogoutPageInterface logoutPageInterface){
        this.properties = properties;
        this.page = page;
        this.loginPageInterface = loginPageInterface;
        this.logoutPageInterface = logoutPageInterface;
    }

    public void DNAssign() throws InterruptedException {
        String logisticsManager = properties.getProperty("LogisticsManager");
        loginPageInterface.LoginMethod(logisticsManager);
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
        page.locator("#btnToAssign").click();
        page.locator("#select2-assignerId-container").click();
        page.locator(".select2-search__field").fill(logisticsManager);
        page.locator("//li[contains(text(), '" + logisticsManager + "')]").click();
        page.locator("#saveAssine").click();
        Thread.sleep(1000);
        logoutPageInterface.LogoutMethod();
    }
}