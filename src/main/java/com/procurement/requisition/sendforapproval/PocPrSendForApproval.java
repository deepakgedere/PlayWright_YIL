package com.procurement.requisition.sendforapproval;

import com.factory.PlayWrightFactory;
import com.interfaces.LoginPageInterface;
import com.interfaces.LogoutPageInterface;
import com.interfaces.PrSendForApproval;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Response;
import com.microsoft.playwright.options.LoadState;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class PocPrSendForApproval implements PrSendForApproval {
    PlayWrightFactory playWrightFactory;
    Page page;
    Properties properties;
    LoginPageInterface loginPageInterface;
    LogoutPageInterface logoutPageInterface;



    //TODO Constructor
    public PocPrSendForApproval(LoginPageInterface loginPageInterface, Properties properties, Page page, LogoutPageInterface logoutPageInterface, PlayWrightFactory playWrightFactory) {
        this.properties = properties;
        this.page = page;
        this.loginPageInterface = loginPageInterface;
        this.logoutPageInterface = logoutPageInterface;
        this.playWrightFactory = playWrightFactory;
    }

    public void NonCatalogPrSendForApproval() throws InterruptedException {
        String title = properties.getProperty("Title");
        loginPageInterface.LoginMethod();
        Locator Requisitions = page.locator("//div[@id='ni-requisitions']");
        Requisitions.click();
        page.locator("//*[contains(text(), '" + title + "')]").first().click();
        page.waitForLoadState(LoadState.NETWORKIDLE);
        String prRefId = page.locator("#referenceId").textContent();
        playWrightFactory.savePrRefNo(prRefId);
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

    public List<String> GetPRApprovers() throws InterruptedException {
        String title = properties.getProperty("Title");
        loginPageInterface.LoginMethod();
        Locator Requisitions = page.locator("//div[@id='ni-requisitions']");
        Requisitions.click();
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
    public String GetPRReferenceNumber(){
        String title = properties.getProperty("Title");
        loginPageInterface.LoginMethod();
        Locator Requisitions = page.locator("//div[@id='ni-requisitions']");
        Requisitions.click();
        page.waitForLoadState();
        page.locator(String.format("//*[contains(text(), '%s')]", title)).first().click();
        String PRRefereneNumber = page.locator("#referenceId").textContent();
        logoutPageInterface.LogoutMethod();
        return PRRefereneNumber;
    }
}