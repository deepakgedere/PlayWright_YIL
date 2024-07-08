package com.procurement.requisition.reject;
import com.interfaces.PrReject;
import com.microsoft.playwright.Page;
import com.interfaces.LoginPageInterface;
import com.interfaces.LogoutPageInterface;
import com.interfaces.PrEdit;
import java.util.Properties;

public class PocPrReject implements PrReject {

    LoginPageInterface loginPageInterface;
    LogoutPageInterface logoutPageInterface;
    Properties properties;
    Page page;
    PrEdit prEdit;

    private PocPrReject(){
    }

//TODO Constructor
    public PocPrReject(LoginPageInterface loginPageInterface, Properties properties, Page page, LogoutPageInterface logoutPageInterface, PrEdit prEdit){
        this.loginPageInterface = loginPageInterface;
        this.properties = properties;
        this.page = page;
        this.logoutPageInterface = logoutPageInterface;
        this.prEdit = prEdit;
    }

    public void Reject(String approverEmail, String PRReferenceNumber) throws InterruptedException {
        loginPageInterface.LoginMethod(approverEmail);
        page.locator("#ni-my-approvals").click();
        String title = "Requisition "+PRReferenceNumber;
        page.locator(String.format("//*[contains(text(), '%s')]", title)).first().click();
        page.locator("#btnReject").click();
        page.locator(".bootbox-input").fill("Rejected");
        page.locator(".bootbox-accept").click();
        Thread.sleep(1000);
        logoutPageInterface.LogoutMethod();
    }
}