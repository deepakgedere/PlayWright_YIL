package com.procurement.workorder.approvals;

import com.interfaces.LoginPageInterface;
import com.interfaces.LogoutPageInterface;
import com.interfaces.WORejectInterface;
import com.microsoft.playwright.Page;

import java.util.List;
import java.util.Properties;

public class WOReject implements WORejectInterface {
    Properties properties;
    Page page;
    LoginPageInterface loginPageInterface;
    LogoutPageInterface logoutPageInterface;
    public WOReject(LoginPageInterface loginPageInterface, Properties properties, Page page, LogoutPageInterface logoutPageInterface){
        this.properties = properties;
        this.page = page;
        this.logoutPageInterface = logoutPageInterface;
        this.loginPageInterface = loginPageInterface;
    }
    public void WORejectMethod() throws InterruptedException {
        loginPageInterface.LoginMethod(properties.getProperty("LogisticsManager"));
        page.waitForSelector("//*[contains(text(), 'Work Orders')]").click();
        String poReferenceId = properties.getProperty("PoReferenceId");
        List<String> containerList = page.locator("#listContainer tr td").allTextContents();
        for(String tr : containerList){
            if(tr.contains(poReferenceId)){
                page.locator(".btn-link").first().click();
            }
        }
        page.locator("#btnReject").click();
        page.locator(".bootbox-input").fill("Rejected");
        page.locator(".bootbox-accept").click();
        page.wait(1000);
        logoutPageInterface.LogoutMethod();
    }
}
