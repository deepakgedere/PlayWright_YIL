package com.procurement.purchaseorder;
import com.interfaces.PurchaseOrderInterface;
import com.microsoft.playwright.Page;
import com.interfaces.LoginPageInterface;
import com.interfaces.LogoutPageInterface;
import java.util.Properties;

public class BuyerPurchaseOrder implements PurchaseOrderInterface {

    Properties properties;
    Page page;
    LoginPageInterface loginPageInterface;
    LogoutPageInterface logoutPageInterface;

    private BuyerPurchaseOrder(){
    }

//TODO Constructor
    public BuyerPurchaseOrder(LoginPageInterface loginPageInterface, Properties properties, Page page, LogoutPageInterface logoutPageInterface){
        this.loginPageInterface = loginPageInterface;
        this.properties = properties;
        this.page = page;
        this.logoutPageInterface = logoutPageInterface;
    }

    public void SendForVendor() throws InterruptedException {
        loginPageInterface.LoginMethod(properties.getProperty("Buyer"));
        page.locator("//*[contains(text(), 'Purchase Orders')]").click();
        String title = properties.getProperty("Title");
        page.locator("//span[contains(text(), '"+ title +"')]").first().click();
        page.locator("#btnSendPO").click();
        page.locator("#vendorSendMailBtnId").click();
        Thread.sleep(2000);
        logoutPageInterface.LogoutMethod();
    }
}