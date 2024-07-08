package com.procurement.requisition.suspend;
import com.interfaces.PrSuspend;
import com.microsoft.playwright.Page;
import com.interfaces.LoginPageInterface;
import com.interfaces.LogoutPageInterface;
import com.interfaces.PrEdit;
import java.util.Properties;

public class PocPrBuyerSuspend implements PrSuspend {

    LoginPageInterface loginPageInterface;
    LogoutPageInterface logoutPageInterface;
    Properties properties;
    Page page;
    PrEdit prEdit;

    private PocPrBuyerSuspend(){
    }

//TODO Constructor
    public PocPrBuyerSuspend(LoginPageInterface loginPageInterface, Properties properties, Page page, LogoutPageInterface logoutPageInterface, PrEdit prEdit){
        this.loginPageInterface = loginPageInterface;
        this.properties = properties;
        this.page = page;
        this.logoutPageInterface = logoutPageInterface;
        this.prEdit = prEdit;
    }

    public void Suspend() throws InterruptedException {
        loginPageInterface.LoginMethod(properties.getProperty("Buyer"));
        page.locator("#ni-requisitions").click();
        String title = properties.getProperty("Title");
        page.locator("//*[contains(text(), '" + title + "')]").first().click();
        page.locator("#btnSuspend").click();
        page.locator(".bootbox-input").fill("Buyer Suspended");
        page.locator(".bootbox-accept").click();
        logoutPageInterface.LogoutMethod();
        prEdit.PrBuyerSuspendEdit();
    }
}