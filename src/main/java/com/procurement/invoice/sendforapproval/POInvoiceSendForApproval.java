package com.procurement.invoice.sendforapproval;

import com.factory.PlayWrightFactory;
import com.interfaces.LoginPageInterface;
import com.interfaces.LogoutPageInterface;
import com.interfaces.POSendForApprovalInterface;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Response;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class POInvoiceSendForApproval implements POSendForApprovalInterface {
    PlayWrightFactory playWrightFactory;
    Page page;
    Properties properties;
    LoginPageInterface loginPageInterface;
    LogoutPageInterface logoutPageInterface;

    private POInvoiceSendForApproval(){
    }

//TODO Constructor
    public POInvoiceSendForApproval(LoginPageInterface loginPageInterface, Properties properties, Page page, LogoutPageInterface logoutPageInterface, PlayWrightFactory playWrightFactory){
        this.page = page;
        this.properties = properties;
        this.loginPageInterface = loginPageInterface;
        this.logoutPageInterface = logoutPageInterface;
        this.playWrightFactory = playWrightFactory;
    }

    public void SendForApproval() throws InterruptedException {
        loginPageInterface.LoginMethod(properties.getProperty("FinanceChecker"));
        Response response = page.waitForResponse(
                resp -> resp.url().startsWith("https://dprocure-uat.cormsquare.com/api/Invoices/Listing") && resp.status() == 200,
                () -> {
                    page.locator("#ni-invoices").click();
                }
        );
        String poReferenceId = properties.getProperty("PoReferenceId");
//        List<String> invoiceTable = page.locator("#listContainer tr td").allTextContents();
//        for (String tr : invoiceTable){
//            if (tr.contains(poReferenceId)) {
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

        Thread.sleep(3000);

        List<Locator> CheckList = page.locator("//button[contains(text(), 'Check List')]").all();
        for(Locator x : CheckList)
        {
            x.click();
            page.locator("#selctAllId").first().click();
            page.locator("#acceptCheckListId").click();
            Thread.sleep(1000);
        }

        page.locator("#btnAccept").click();
        page.locator(".bootbox-accept").click();
        page.locator("#btnSendApproval").click();
        Response response1 = page.waitForResponse(
                resp -> resp.url().startsWith("https://dprocure-uat.cormsquare.com/api/Approvals") && resp.status() == 200,
                () -> {
                    page.locator(".bootbox-accept").click();
                }
        );
        // Filtering out the same stage approvers starts here

        List<List<String>> matchingApprovers = new ArrayList<>();
        List<String> approvalTable = page.locator("#approvalData tbody tr td").allTextContents();
        String approverMailId = "@cormsquare.com";
        String approverMailId2 = "@sharklasers.com";
        String approverMailId3 = "@yokogawa.com";
        //get all approvers
        List<String> Stage = new ArrayList<>();
        for (int i=0;i<approvalTable.size();i++) {
            if (approvalTable.get(i).endsWith(approverMailId)) {
                List<String> x = new ArrayList<>();
                x.add(approvalTable.get(i-1));
                x.add(approvalTable.get(i));
                matchingApprovers.add(x);
            }
            if (approvalTable.get(i).endsWith(approverMailId2)) {
                List<String> x = new ArrayList<>();
                x.add(approvalTable.get(i-1));
                x.add(approvalTable.get(i));
                matchingApprovers.add(x);
            }
            if (approvalTable.get(i).endsWith(approverMailId3)) {
                List<String> x = new ArrayList<>();
                x.add(approvalTable.get(i-1));
                x.add(approvalTable.get(i));
                matchingApprovers.add(x);
            }
        }
        //filter approvers with same stage
        for (int i = 0; i < matchingApprovers.size(); i++) {
            List<String> x = matchingApprovers.get(i);
            String a = x.get(0);

            // Check next element
            if (i < matchingApprovers.size() - 1) {
                List<String> y = matchingApprovers.get(i + 1);
                String b = y.get(0);
                if (a.equals(b)) {
                    matchingApprovers.remove(i);
                    i--; // Adjust the index after removal
                    continue; // Skip to the next iteration
                }
            }

            // Check previous element
            if (i > 0) {
                List<String> z = matchingApprovers.get(i - 1);
                String c = z.get(0);
                if (a.equals(c)) {
                    matchingApprovers.remove(i);
                    i--; // Adjust the index after removal
                }
            }
        }
        //store new list of approvers
        List<String> Approvers = new ArrayList<>();
        for(int i=0; i<matchingApprovers.size();i++){
            List<String> x = matchingApprovers.get(i);
            Approvers.add(x.get(1));
        }

        //Filtering ends here

        //write approvers emailid to file
        for (int i = 1; i <= Approvers.size();i++){
            String approverEmail = Approvers.get(i-1);
            playWrightFactory.saveApproversToFile(approverEmail,i);
            playWrightFactory.saveApproversCount(i);
        }

        logoutPageInterface.LogoutMethod();
    }
}