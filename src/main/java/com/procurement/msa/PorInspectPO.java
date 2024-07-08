package com.procurement.msa;
import com.interfaces.PorInspectPoInterface;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.interfaces.LoginPageInterface;
import com.interfaces.LogoutPageInterface;
import java.util.Properties;

public class PorInspectPO implements PorInspectPoInterface {

    Properties properties;
    LoginPageInterface loginPageInterface;
    Page page;
    LogoutPageInterface logoutPageInterface;

    private PorInspectPO(){
    }

//TODO Constructor
    public PorInspectPO(LoginPageInterface loginPageInterface, Properties properties, Page page, LogoutPageInterface logoutPageInterface){
        this.loginPageInterface = loginPageInterface;
        this.properties = properties;
        this.page = page;
        this.logoutPageInterface = logoutPageInterface;
    }

    public void InspectCreatePO() throws InterruptedException {
        loginPageInterface.LoginMethod(properties.getProperty("AdminId"));
        page.locator("//*[contains(text(), 'Purchase Order Requests')]").click();
        String title = properties.getProperty("Title");
        page.locator("//span[contains(text(),'" + title + "')]").first().click();
        Locator createPOButton = page.locator("#createPOContainer");
        createPOButton.evaluate("(element) => { element.style.display = 'block'; }");
        Thread.sleep(1000);
        createPOButton.click();
        page.waitForSelector(".bootbox-accept").click();
        Thread.sleep(1000);
        logoutPageInterface.LogoutMethod();
    }
}