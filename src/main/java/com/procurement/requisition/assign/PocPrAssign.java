package com.procurement.requisition.assign;
import com.factory.PlayWrightFactory;
import com.interfaces.PrAssign;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.interfaces.LoginPageInterface;
import com.interfaces.LogoutPageInterface;
import java.util.Properties;

public class PocPrAssign implements PrAssign {
    PlayWrightFactory playWrightFactory;
    Page page;
    LoginPageInterface loginPageInterface;
    LogoutPageInterface logoutPageInterface;
    Properties properties;

    private PocPrAssign(){
    }

//TODO Constructor
    public PocPrAssign(LoginPageInterface loginPageInterface, Properties properties, Page page, LogoutPageInterface logoutPageInterface, PlayWrightFactory playWrightFactory){
        this.properties = properties;
        this.page = page;
        this.loginPageInterface = loginPageInterface;
        this.logoutPageInterface = logoutPageInterface;
        this.playWrightFactory = playWrightFactory;

    }
    public void BuyerManagerAssign() {
        loginPageInterface.LoginMethod(properties.getProperty("BuyerManager"));
        page.locator("#ni-requisitions").click();
        String title = properties.getProperty("Title");
        String buyerMailId = properties.getProperty("Buyer");
        page.locator("//*[contains(text(), '"+ title +"')]").first().click();
//TODO Assign Buyer
        String prReferenceId = page.waitForSelector("#referenceId").textContent();
        playWrightFactory.savePropertiesToFile(prReferenceId);
        page.locator("#btnAssignUser").click();
        page.locator("#select2-bgUser-container").click();
        page.getByRole(AriaRole.SEARCHBOX).fill(buyerMailId);
        page.locator("//li[contains(text(),'"+ buyerMailId +"')]").first().click();
        page.locator("#saveBGAssign").click();
        logoutPageInterface.LogoutMethod();
    }
}