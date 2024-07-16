package com.procurement.requisition.approve;
import com.interfaces.PrApprove;
import com.microsoft.playwright.Page;
import com.interfaces.LoginPageInterface;
import com.interfaces.LogoutPageInterface;

import java.util.List;
import java.util.Properties;

public class PocPrApprove implements PrApprove {

    LoginPageInterface loginPageInterface;
    LogoutPageInterface logoutPageInterface;
    Properties properties;
    Page page;

    private PocPrApprove(){
    }

//TODO Constructor
    public PocPrApprove(LoginPageInterface loginPageInterface, Properties properties, Page page, LogoutPageInterface logoutPageInterface){
        this.loginPageInterface = loginPageInterface;
        this.properties = properties;
        this.page = page;
        this.logoutPageInterface = logoutPageInterface;
    }
    public void Approve() {
        loginPageInterface.LoginMethod();
        String title = properties.getProperty("Title");
        page.locator("//*[contains(text(), '" + title + "')]").first().click();
        page.locator("#btnApprove").click();
        page.locator(".bootbox-accept").click();
        logoutPageInterface.LogoutMethod();
    }
    public void Approve(String ApproverMailId) {
        loginPageInterface.LoginMethod(ApproverMailId);
        page.locator("#ni-requisitions").click();
        String title = properties.getProperty("Title");
        page.locator("//*[contains(text(), '" + title + "')]").first().click();
        page.locator("#btnApprove").click();
        page.locator(".bootbox-accept").click();
        logoutPageInterface.LogoutMethod();
    }
    public void Approve(String ApproverMailId , String PRReferenceNumber) throws InterruptedException {
        loginPageInterface.LoginMethod(ApproverMailId);
        page.locator("#ni-my-approvals").click();
        String title = "Requisition "+PRReferenceNumber;
        page.locator(String.format("//*[contains(text(), '%s')]", title)).first().click();
        page.locator("#btnApprove").click();
        Thread.sleep(1000);
        page.locator(".bootbox-accept").click();
        Thread.sleep(1000);
        logoutPageInterface.LogoutMethod();
    }
}
