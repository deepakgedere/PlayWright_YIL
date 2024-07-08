package com.procurement.inspections.assign;

import com.interfaces.InspectionAssignInterface;
import com.interfaces.LoginPageInterface;
import com.interfaces.LogoutPageInterface;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

import java.util.Properties;

public class InspectionAssign implements InspectionAssignInterface {

    Properties properties;
    Page page;
    LoginPageInterface loginPageInterface;
    LogoutPageInterface logoutPageInterface;

    private InspectionAssign() {
    }

    //TODO Constructor
    public InspectionAssign(LoginPageInterface loginPageInterface, Properties properties, Page page, LogoutPageInterface logoutPageInterface) {
        this.loginPageInterface = loginPageInterface;
        this.properties = properties;
        this.page = page;
        this.logoutPageInterface = logoutPageInterface;
    }

    public void RequesterInspectionAssign() throws InterruptedException {
        String mailId = properties.getProperty("EmailID");
        loginPageInterface.LoginMethod(mailId);
        Thread.sleep(1000);
        page.locator("//*[contains(text(), 'Order Schedules')]").click();
        String poReferenceId = properties.getProperty("PoReferenceId");
        Thread.sleep(1000);
//        List<String> containerList = page.locator("#listContainer tr td").allTextContents();
//        for (String tr : containerList) {
//            if (tr.contains(poReferenceId)) {
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
        page.locator("#btnAssignInspector").click();
        page.locator("#select2-InspectionId-container").click();
        page.locator(".select2-search__field").fill(mailId);
        page.locator("//li[contains(text(), '" + mailId + "')]").first().click();
        page.locator("#saveInspector").click();
        page.locator("#btnForCreateInspection").click();
        Thread.sleep(1000);
        page.locator("#physicalInspectionReq").click();
        page.locator("#btnCreateInspection").click();
        page.locator(".bootbox-accept").click();
        Thread.sleep(1000);
        logoutPageInterface.LogoutMethod();
    }
}

