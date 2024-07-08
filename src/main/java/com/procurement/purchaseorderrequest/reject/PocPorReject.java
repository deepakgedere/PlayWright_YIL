package com.procurement.purchaseorderrequest.reject;

import com.interfaces.*;
import com.microsoft.playwright.Page;

import java.util.List;
import java.util.Properties;

public class PocPorReject implements PorReject{

    LoginPageInterface loginPageInterface;
    LogoutPageInterface logoutPageInterface;
    Properties properties;
    Page page;
    PorEdit porEdit;
    PorApproval porApproval;
    PorApprove porApprove;


//TODO Constructor
    public PocPorReject(LoginPageInterface loginPageInterface, Properties properties, Page page, LogoutPageInterface logoutPageInterface, PorEdit porEdit, PorApproval porApproval,PorApprove porApprove){
        this.loginPageInterface = loginPageInterface;
        this.properties = properties;
        this.page = page;
        this.logoutPageInterface = logoutPageInterface;
        this.porEdit = porEdit;
        this.porApproval = porApproval;
        this.porApprove = porApprove;
    }




    public void Reject(String ApproverMailId , String PORReferenceNumber) {
        loginPageInterface.LoginMethod(ApproverMailId);
        page.locator("#ni-my-approvals").click();
        String title = "PurchaseOrderRequest "+PORReferenceNumber;
        page.locator(String.format("//*[contains(text(), '%s')]", title)).first().click();
        page.locator("#btnReject").click();
        page.locator(".bootbox-input").fill("Rejected");
        page.locator(".bootbox-accept").click();
        logoutPageInterface.LogoutMethod();
    }

    public void AllRejectAndEditMethod() throws InterruptedException {
        List<String> approvers = porApprove.GetPORApprovers();
        String PRReferenceNumber = porApprove.GetPORReferenceNumber();
        for (int i = 0; i < approvers.size(); i++) {
            // Current approver rejects
            Reject(approvers.get(i), PRReferenceNumber);

            // Edit and send for approval
            porEdit.PorEditMethod();
            porApproval.NonCatalogPORSendForApproval();

            // All previous approvers approve up to the current one
            for (int j = 0; j < i; j++) {
                porApprove.ApproveMethod(approvers.get(j), PRReferenceNumber);
            }
        }
        // Final approval by all approvers
        for (String approver : approvers) {
            porApprove.ApproveMethod(approver, PRReferenceNumber);
        }
    }

}