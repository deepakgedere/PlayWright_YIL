package com.procurement.requestforquotations.suspend;

import com.interfaces.*;
import com.microsoft.playwright.Page;

import java.util.Properties;

public class PocRfqSuspend implements RfqSuspend {

    LoginPageInterface loginPageInterface;
    LogoutPageInterface logoutPageInterface;
    Properties properties;
    Page page;
    RfqEdit rfqEdit;
    PrEdit prEdit;
    PrSendForApproval prSendForApproval;
    PrApprove prApprove;
    PrAssign prAssign;
    RfqCreate rfqCreate;

    private PocRfqSuspend(){
    }

//TODO Constructor
    public PocRfqSuspend(LoginPageInterface loginPageInterface, Properties properties, Page page, LogoutPageInterface logoutPageInterface, RfqEdit rfqEdit, PrEdit prEdit,
                         PrSendForApproval prSendForApproval, PrApprove prApprove, PrAssign prAssign, RfqCreate rfqCreate){
        this.loginPageInterface = loginPageInterface;
        this.properties = properties;
        this.page = page;
        this.logoutPageInterface = logoutPageInterface;
        this.rfqEdit = rfqEdit;
        this.prEdit = prEdit;
        this.prSendForApproval = prSendForApproval;
        this.prApprove = prApprove;
        this.prAssign = prAssign;
        this.rfqCreate = rfqCreate;
    }

    public void SuspendRfqEdit() throws InterruptedException {
        loginPageInterface.LoginMethod(properties.getProperty("Buyer"));
        page.locator("//*[contains(text(), 'Request For Quotations')]").click();
        String title = properties.getProperty("Title");
        page.locator("//span[contains(text(), '"+ title +"')]").first().click();
        page.locator("#btnToSuspendRfq").click();
        page.locator(".bootbox-input").fill("Suspended");
        page.locator(".bootbox-accept").click();
//        Thread.sleep(1000);
//        logoutPageInterface.LogoutMethod();
//        rfqEdit.RfqEditMethod();
        page.locator("#btnEditRfq").click();
        Thread.sleep(2000);
        page.locator("#btnUpdate").click();
        page.locator(".bootbox-input").fill("Updated");
        page.locator(".bootbox-accept").click();
        logoutPageInterface.LogoutMethod();
    }

    public void SuspendPREdit() throws InterruptedException {
        loginPageInterface.LoginMethod(properties.getProperty("Buyer"));
        page.locator("//*[contains(text(), 'Request For Quotations')]").click();
        String title = properties.getProperty("Title");
        page.locator("//span[contains(text(), '"+ title +"')]").first().click();
        page.locator("#btnToSuspendRfq").click();
        page.locator(".bootbox-input").fill("Suspended");
        page.locator(".bootbox-accept").click();
        Thread.sleep(2000);
        logoutPageInterface.LogoutMethod();
        prEdit.PrEditMethod();
        prSendForApproval.NonCatalogPrSendForApproval();
//        prApprove.Approve();
//        List<String> approvers = prSendForApproval.GetPRApprovers();
//        String PRReferenceNumber = prSendForApproval.GetPRReferenceNumber();
//        for (String approverMailId : approvers) {
//            prApprove.Approve(approverMailId , PRReferenceNumber);
//        }
        prAssign.BuyerManagerAssign();
        rfqCreate.BuyerRfqCreate();
        rfqCreate.RfQNotes();
        rfqCreate.RFQCreate();
    }
}