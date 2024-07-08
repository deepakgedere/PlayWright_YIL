package com.procurement.workorder.edit;

import com.interfaces.LoginPageInterface;
import com.interfaces.LogoutPageInterface;
import com.interfaces.WorkOrderEditInterface;
import com.microsoft.playwright.Page;

import java.util.List;
import java.util.Properties;

public class WorkOrderEdit implements WorkOrderEditInterface {
    Properties properties;
    Page page;
    LoginPageInterface loginPageInterface;
    LogoutPageInterface logoutPageInterface;
    public WorkOrderEdit(LoginPageInterface loginPageInterface, Properties properties, Page page, LogoutPageInterface logoutPageInterface){
        this.properties = properties;
        this.page = page;
        this.logoutPageInterface = logoutPageInterface;
        this.loginPageInterface = loginPageInterface;
    }
    public void EditMethod() {
        loginPageInterface.LoginMethod(properties.getProperty("LogisticsManager"));
        page.waitForSelector("//*[contains(text(), 'Work Orders')]").click();
        String poReferenceId = properties.getProperty("PoReferenceId");
        List<String> containerList = page.locator("#listContainer tr td").allTextContents();
        for(String tr : containerList){
            if(tr.contains(poReferenceId)){
                page.locator(".btn-link").first().click();
            }
        }
        page.locator("#btnEdit").click();
        page.locator("#btnToUpdateWorkOrder").click();
        page.locator(".bootbox-accept").click();
        logoutPageInterface.LogoutMethod();
    }
}
