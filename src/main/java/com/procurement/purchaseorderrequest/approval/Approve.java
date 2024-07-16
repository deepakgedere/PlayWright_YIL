package com.procurement.purchaseorderrequest.approval;

import com.factory.PlayWrightFactory;
import com.interfaces.LoginPageInterface;
import com.interfaces.LogoutPageInterface;
import com.interfaces.PorApprove;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Approve implements PorApprove {
    PlayWrightFactory playWrightFactory;
    Properties properties;
    Page page;
    LoginPageInterface loginPageInterface;
    LogoutPageInterface logoutPageInterface;

    //TODO Constructor
    public Approve(LoginPageInterface loginPageInterface, Properties properties, Page page, LogoutPageInterface logoutPageInterface, PlayWrightFactory playWrightFactory) throws InterruptedException {
        this.loginPageInterface = loginPageInterface;
        this.properties = properties;
        this.page = page;
        this.logoutPageInterface = logoutPageInterface;
        this.playWrightFactory = playWrightFactory;
    }
    public void AllApprove() throws InterruptedException {
//        List<String> approvers = GetPORApprovers();
////        String PORReferenceNumber = GetPORReferenceNumber();
////        for (String approverMailId : approvers) {
////           ApproveMethod(approverMailId , PORReferenceNumber);
            try {
                int x = Integer.parseInt(properties.getProperty("ApproversCount"));
                List<String> approvers = new ArrayList<>();
                for(int i=1;i<=x;i++){
                    approvers.add(properties.getProperty("Approver"+i));
                }
                String PORReferenceNumber = properties.getProperty("PORRefId");
                for (String approverMailId : approvers) {
                    ApproveMethod(approverMailId , PORReferenceNumber);
                }
            } catch (Exception error) {
                System.out.println(error);
            }
        }
    public void ApproveMethod(String ApproverMailId , String PORReferenceNumber) throws InterruptedException {
        loginPageInterface.LoginMethod(ApproverMailId);
        page.locator("#ni-my-approvals").click();
        String title = "PurchaseOrderRequest "+PORReferenceNumber;
        page.locator(String.format("//*[contains(text(), '%s')]", title)).first().click();
        String poReferenceId = page.waitForSelector("#referenceId").textContent();
        playWrightFactory.savePropertiesToFile(poReferenceId);
        page.locator("#btnApprove").click();
        Thread.sleep(1000);
        page.locator(".bootbox-accept").click();
        Thread.sleep(2000);
        logoutPageInterface.LogoutMethod();
    }
    public List<String> GetPORApprovers() throws InterruptedException {
        String title = properties.getProperty("Title");
        loginPageInterface.LoginMethod(properties.getProperty("Buyer"));
        page.locator("#ni-purchase-order-requests").click();
        page.locator(String.format("//*[contains(text(), '%s')]", title)).first().click();
        Thread.sleep(2000);
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
        logoutPageInterface.LogoutMethod();
        return matchingApprovers;
    }
    public String GetPORReferenceNumber(){
        String title = properties.getProperty("Title");
        loginPageInterface.LoginMethod(properties.getProperty("Buyer"));
        Locator Requisitions = page.locator("#ni-purchase-order-requests");
        Requisitions.click();
        page.waitForLoadState();
        page.locator(String.format("//*[contains(text(), '%s')]", title)).first().click();
        String PRRefereneNumber = page.locator("#referenceId").textContent();
        logoutPageInterface.LogoutMethod();
        return PRRefereneNumber;
    }
//    public void ApproverLogin(List<String> matchingApprovers) {
//        List<String> groupIds = new ArrayList<>();
//        for (int i = 0; i < matchingApprovers.size(); i++) {
//            String approverMailId = matchingApprovers.get(i);
//            if (approverMailId.endsWith("@cormsquare.com") || approverMailId.endsWith("@sharklasers.com") || approverMailId.endsWith("@yokogawa.com")) {
//                loginPageInterface.LoginMethod(approverMailId, page);
////TODO Approver Approves POR
//                page.waitForSelector("//span[contains(text(), 'My Approvals')]").click();
//                String title = properties.getProperty("Title");
//                page.locator("//span[contains(text(), '" + title + "')]").first().click();
//                Locator addApprovers = page.locator("#btnAddApprovers");
//                Locator projectManagerPopUp = page.locator("//h3[contains(text(), 'Purchase Order Request Send For Approval')]").last();
//                if (i == 0 && addApprovers.isEnabled()) {
//                    addApprovers.click();
//                    if (projectManagerPopUp.isEnabled() && projectManagerPopUp.isVisible()) {
////TODO PR Approver Group B
//                        Locator projectManagerDropDown = page.locator("#select2-PMBId-container");
//                        if (projectManagerDropDown.isEnabled() && projectManagerDropDown.isVisible()) {
//                            projectManagerDropDown.click();
//                            String prApproverGroupB = properties.getProperty("PRApproverGroupB");
//                            page.getByRole(AriaRole.SEARCHBOX).fill(prApproverGroupB);
//                            Locator getGroupB = page.locator("//li[contains(text(), '" + prApproverGroupB + "')]");
//                            String groupBId = getGroupB.textContent();
//                            getGroupB.first().click();
//                            groupIds.add(groupBId);
//                        }
////TODO PR Approver Group C
//                        Locator departmentManagerDropDown = page.locator("#select2-departmentManagerId-container");
//                        if (departmentManagerDropDown.isEnabled() && departmentManagerDropDown.isVisible()) {
//                            departmentManagerDropDown.click();
//                            String prApproverGroupC = properties.getProperty("PRApproverGroupC");
//                            page.getByRole(AriaRole.SEARCHBOX).fill(prApproverGroupC);
//                            Locator getGroupC = page.locator("//li[contains(text(), '" + prApproverGroupC + "')]");
//                            String groupCId = getGroupC.textContent();
//                            getGroupC.first().click();
//                            groupIds.add(groupCId);
//                        }
////TODO PR Approver Group D
//                        Locator divisionManagerDropDown = page.locator("#select2-divisionManagerId-container");
//                        if (divisionManagerDropDown.isEnabled() && divisionManagerDropDown.isVisible()) {
//                            divisionManagerDropDown.click();
//                            String prApproverGroupD = properties.getProperty("PRApproverGroupD");
//                            page.getByRole(AriaRole.SEARCHBOX).fill(prApproverGroupD);
//                            Locator getGroupD = page.locator("//li[contains(text(), '" + prApproverGroupD + "')]");
//                            String groupDId = getGroupD.textContent();
//                            getGroupD.first().click();
//                            groupIds.add(groupDId);
//                        }
//                        page.locator("#btnSendUserFromPM").click();
//                        page.locator("#btnApprove").click();
//                        page.locator(".bootbox-accept").click();
//                        logoutPageInterface.LogoutMethod();
//                    } else if (!projectManagerPopUp.isVisible()) {
//                        page.locator("#btnApprove").click();
//                        page.locator(".bootbox-accept").click();
//                        logoutPageInterface.LogoutMethod();
//                    }
//                } else {
//                    page.locator("#btnApprove").click();
//                    page.locator(".bootbox-accept").click();
//                    logoutPageInterface.LogoutMethod();
//                }
//            }
//            int size = groupIds.size() - 1;
//            if (approverMailId.startsWith("PR Approver Group")) {
//                for (int j = 0; j <= size; j++) {
//                    loginPageInterface.LoginMethod(groupIds.get(j), page);
//                    page.waitForSelector("//span[contains(text(), 'My Approvals')]").click();
//                    String title = properties.getProperty("Title");
//                    page.locator("//span[contains(text(), '" + title + "')]").first().click();
//                    page.waitForSelector("#btnApprove").click();
//                    page.waitForSelector(".bootbox-accept").click();
//                    logoutPageInterface.LogoutMethod();
//                }
//                i += size;
//            }
//        }
//    }
}