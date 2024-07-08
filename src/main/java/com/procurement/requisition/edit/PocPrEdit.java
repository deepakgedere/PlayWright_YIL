package com.procurement.requisition.edit;
import com.interfaces.*;
import com.microsoft.playwright.Page;
import com.interfaces.PrSendForApproval;

import java.util.List;
import java.util.Properties;

public class PocPrEdit implements PrEdit {

    LoginPageInterface loginPageInterface;
    LogoutPageInterface logoutPageInterface;
    Properties properties;
    Page page;
    PrSendForApproval prSendForApproval;
    PrApprove prApprove;
    PrAssign prAssign;

    private PocPrEdit(){
    }

//TODO Constructor
    public PocPrEdit(LoginPageInterface loginPageInterface, Properties properties, Page page, LogoutPageInterface logoutPageInterface, PrSendForApproval prSendForApproval, PrApprove prApprove, PrAssign prAssign){
        this.loginPageInterface = loginPageInterface;
        this.properties = properties;
        this.page = page;
        this.logoutPageInterface = logoutPageInterface;
        this.prSendForApproval = prSendForApproval;
        this.prApprove = prApprove;
        this.prAssign = prAssign;
    }

    public void PrEditMethod() throws InterruptedException {
        loginPageInterface.LoginMethod();
        page.locator("#ni-requisitions").click();
        String title = properties.getProperty("Title");
        page.locator("//*[contains(text(), '" + title + "')]").first().click();
        page.locator("#editContainer").click();
        Thread.sleep(2000);
        page.locator("#btnUpdate").click();
        page.locator(".bootbox-accept").click();
        Thread.sleep(2000);
        logoutPageInterface.LogoutMethod();
    }

    public void PrRejectEdit() throws InterruptedException {
        PrEditMethod();
        prSendForApproval.NonCatalogPrSendForApproval();
    }
    public void PrBuyerManagerSuspendEdit() throws InterruptedException {
        PrEditMethod();
        prSendForApproval.NonCatalogPrSendForApproval();
        List<String> approvers = prSendForApproval.GetPRApprovers();
        String PRReferenceNumber = prSendForApproval.GetPRReferenceNumber();
        for (String approverMailId : approvers) {
            prApprove.Approve(approverMailId , PRReferenceNumber);
        }
        prAssign.BuyerManagerAssign();
    }
    public void PrBuyerSuspendEdit() throws InterruptedException {
        PrEditMethod();
        prSendForApproval.NonCatalogPrSendForApproval();
        List<String> approvers = prSendForApproval.GetPRApprovers();
        for (String approverMailId : approvers) {
            prApprove.Approve(approverMailId);
        }

        prAssign.BuyerManagerAssign();
    }
//    public void PrBuyerManagerSuspendEdit() throws InterruptedException {
//        PrEditMethod();
//        prSendForApproval.NonCatalogPrSendForApproval();
//        prApprove.Approve(properties.getProperty("BuyerManager"));
//    }
//
//    public void PrBuyerSuspendEdit() throws InterruptedException {
//        PrEditMethod();
//        prSendForApproval.NonCatalogPrSendForApproval();
//        prApprove.Approve(properties.getProperty("Buyer"));
//        prAssign.BuyerManagerLogin();
//        prAssign.BuyerManagerAssign();
//    }
}
