package com.procurement.requisition.sendforapproval;
import com.base.BaseTest;
import org.testng.annotations.Test;

public class NonCatalogPrSendForApprovalTest extends BaseTest {

    @Test (priority = 3, groups = "requisition")
    public void NonCatalogPrAssignMethod() {
        try {
            prSendForApproval.NonCatalogPrSendForApproval();
        } catch (Exception error) {
            System.out.println(error);
        }
    }
}