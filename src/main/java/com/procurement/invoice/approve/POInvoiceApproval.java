package com.procurement.invoice.approve;

import com.interfaces.LoginPageInterface;
import com.interfaces.LogoutPageInterface;
import com.interfaces.POInvoiceApprovalInterface;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Response;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class POInvoiceApproval implements POInvoiceApprovalInterface {

    Page page;
    Properties properties;
    LoginPageInterface loginPageInterface;
    LogoutPageInterface logoutPageInterface;

    private POInvoiceApproval(){
    }

//TODO Constructor
    public POInvoiceApproval(LoginPageInterface loginPageInterface, Properties properties, Page page, LogoutPageInterface logoutPageInterface){
        this.loginPageInterface = loginPageInterface;
        this.properties = properties;
        this.page = page;
        this.logoutPageInterface = logoutPageInterface;
    }

    public void POInvoiceAllApprove(){
        try {
            int x = Integer.parseInt(properties.getProperty("ApproversCount"));
            List<String> approvers = new ArrayList<>();
            for(int i=1;i<=x;i++){
                approvers.add(properties.getProperty("Approver"+i));
            }
            for (int i = 0; i < approvers.size(); i++) {
                String approverMailId = approvers.get(i);
                POInvoiceApprovalMethod(approverMailId, i);
            }
        } catch (Exception error) {
            System.out.println(error);
        }
    }
    public void POInvoiceApprovalMethod(String approver, int stage) throws InterruptedException {
        loginPageInterface.LoginMethod(approver);
        Thread.sleep(1000);
        Response response = page.waitForResponse(
                resp -> resp.url().startsWith("https://dprocure-uat.cormsquare.com/api/Invoices/Listing") && resp.status() == 200,
                () -> {
                    page.locator("#ni-invoices").click();
                }
        );
        String poReferenceId = properties.getProperty("PoReferenceId");
//        List<String> invoiceContainer = page.locator("#listContainer tr td").allTextContents();
//        for(String tr : invoiceContainer){
//            if (tr.contains(poReferenceId)){
//                page.locator(".btn btn-sm btn-link p-0 text-primary").first().click();
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
        if(stage == 0){
            page.locator("#headerText").fill(properties.getProperty("HeaderText"));
            page.locator(("#billOfLadding")).fill(properties.getProperty("BillOfLading"));
            Thread.sleep(1000);
            page.locator("#updateBillHeaderDetails").click();
        }
        page.locator("#btnApprove").click();
        page.locator(".bootbox-accept").click();
        Thread.sleep(1000);
        logoutPageInterface.LogoutMethod();
    }
}