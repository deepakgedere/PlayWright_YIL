package com.procurement.purchaseorderrequest.approval;

import com.factory.PlayWrightFactory;
import com.interfaces.LoginPageInterface;
import com.interfaces.LogoutPageInterface;
import com.interfaces.PorApproval;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Response;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class PocPorSendForApproval implements PorApproval {
    PlayWrightFactory playWrightFactory;

    Properties properties;
    Page page;
    LoginPageInterface loginPageInterface;
    LogoutPageInterface logoutPageInterface;

    private PocPorSendForApproval() {
    }

//TODO Constructor
    public PocPorSendForApproval(LoginPageInterface loginPageInterface, Properties properties, Page page, LogoutPageInterface logoutPageInterface, PlayWrightFactory playWrightFactory)
    {
        this.loginPageInterface = loginPageInterface;
        this.properties = properties;
        this.page = page;
        this.logoutPageInterface = logoutPageInterface;
        this.playWrightFactory = playWrightFactory;
    }

public void NonCatalogPORSendForApproval() throws InterruptedException {
    String title = properties.getProperty("Title");
    loginPageInterface.LoginMethod(properties.getProperty("Buyer"));
    Locator Requisitions = page.locator("#ni-purchase-order-requests");
    Requisitions.click();
    page.locator("//*[contains(text(), '" + title + "')]").first().click();
    String porRefId = page.locator("#referenceId").textContent();
    playWrightFactory.savePORRefNo(porRefId);
    page.locator("#btnSendApproval").click();
    Response response = page.waitForResponse(
            resp -> resp.url().startsWith("https://dprocure-uat.cormsquare.com/api/Approvals") && resp.status() == 200,
            () -> {
                page.locator("//button[contains(text(), 'Yes')]").click();
            }
    );
    List<String> matchingApprovers = new ArrayList<>();
    List<String> approvalTable = page.locator("#approvalData tbody tr td").allTextContents();
    String approverMailId = "@cormsquare.com";
    String approverMailId2 = "@sharklasers.com";
    String approverMailId3 = "@yokogawa.com";
    for (String approver : approvalTable) {
        if (approver.endsWith(approverMailId)) {
            matchingApprovers.add(approver);
        }
        if (approver.endsWith(approverMailId2)) {
            matchingApprovers.add(approver);
        }
        if (approver.endsWith(approverMailId3)) {
            matchingApprovers.add(approver);
        }
    }
    for (int i = 1; i <= matchingApprovers.size();i++){
        String approverEmail = matchingApprovers.get(i-1);
        playWrightFactory.saveApproversToFile(approverEmail,i);
        playWrightFactory.saveApproversCount(i);
    }
    logoutPageInterface.LogoutMethod();
}



//    public List<String> SendForApproval() {
//        loginPageInterface.LoginMethod(properties.getProperty("Buyer"));
//        page.locator("//*[contains(text(), 'Purchase Order Requests')]").click();
//        String title = properties.getProperty("Title");
//        page.locator("//span[contains(text(), '"+ title +"')]").first().click();
//        page.locator("#btnSendApproval").click();
//        Locator approvalPopup = page.locator("//h3[contains(text(), 'Purchase Order Request Send For Approval')]").first();
//        List<String> matchingApprovers = new ArrayList<>();
//        if (approvalPopup.isEnabled() && approvalPopup.isVisible() || !approvalPopup.isHidden()) {
////TODO CFO
////            Locator cfoPopup = page.locator("#role-7");
////            if (cfoPopup.isVisible()){
////                cfoPopup.click();
////                page.waitForSelector("//li[contains(text(), '" + properties.getProperty("cfo") + "')]").click();
////            }
////TODO President/Director (Corporate)
//            Locator presidentPopup = page.locator("#select2-role-8-container");
//            if (presidentPopup.isVisible()) {
//                presidentPopup.click();
//                page.waitForSelector("//li[contains(text(), '" + properties.getProperty("PresidentDirectorCorporate") + "')]").click();
//            }
////TODO Submit
//            page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Submit")).click();
//            List<String> approvalTable = page.locator("#approvalData tbody tr td").allTextContents();
//            approvalTable.removeIf(text -> text.contains("PR Approver Group A"));
//            String approverMailId = "@cormsquare.com";
//            String approverMailId2 = "@sharklasers.com";
//            String approverMailId3 = "@yokogawa.com";
//            String approverDesignation = "PR Approver Group";
//            for (String approver : approvalTable) {
//                if (approver.endsWith(approverMailId)) {
//                    matchingApprovers.add(approver);
//                }
//                if (approver.startsWith(approverDesignation) && !approver.contains("PR Approver Group A")) {
//                    matchingApprovers.add(approver);
//                }
//                if (approver.endsWith(approverMailId2)) {
//                    matchingApprovers.add(approver);
//                }
//                if (approver.endsWith(approverMailId3)) {
//                    matchingApprovers.add(approver);
//                }
//            }
//            logoutPageInterface.LogoutMethod();
//            return matchingApprovers;
//        } else {
//            List<String> approvalTable = page.locator("#approvalData tbody tr td").allTextContents();
//            String approverMailId = "@cormsquare.com";
//            String approverMailId2 = "@sharklasers.com";
//            String approverMailId3 = "@yokogawa.com";
//            String approverDesignation = "PR Approver Group";
//            for (String approver : approvalTable) {
//                if (approver.endsWith(approverMailId)) {
//                    matchingApprovers.add(approver);
//                }
//                if (approver.startsWith(approverDesignation) && !approver.contains("PR Approver Group A")) {
//                    matchingApprovers.add(approver);
//                }
//                if (approver.endsWith(approverMailId2)) {
//                    matchingApprovers.add(approver);
//                }
//                if (approver.endsWith(approverMailId3)) {
//                    matchingApprovers.add(approver);
//                }
//            }
//            logoutPageInterface.LogoutMethod();
//        }
//        return matchingApprovers;
//    }

}