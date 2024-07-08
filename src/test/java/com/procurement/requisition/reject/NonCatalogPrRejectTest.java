package com.procurement.requisition.reject;

import com.base.BaseTest;
import org.testng.annotations.Test;

import java.util.List;

public class NonCatalogPrRejectTest extends BaseTest {

    @Test (groups = "requisition")
    public void NonCatalogPrRejectTestMethod() throws InterruptedException {
        try {
            List<String> approvers = prSendForApproval.GetPRApprovers();
            String PRReferenceNumber = prSendForApproval.GetPRReferenceNumber();
//                prReject.Reject(approvers.get(0), PRReferenceNumber);
//                prEdit.PrEditMethod();
//                prSendForApproval.NonCatalogPrSendForApproval();
//                prApprove.Approve(approvers.get(0), PRReferenceNumber);
//                prReject.Reject(approvers.get(1), PRReferenceNumber);
            for (int i = 0; i < approvers.size(); i++) {
                // Current approver rejects
                prReject.Reject(approvers.get(i), PRReferenceNumber);

                // Edit and send for approval
                prEdit.PrEditMethod();
                prSendForApproval.NonCatalogPrSendForApproval();

                // All previous approvers approve up to the current one
                for (int j = 0; j <= i; j++) {
                    prApprove.Approve(approvers.get(j), PRReferenceNumber);
                }
            }
            // Final approval by all approvers
//            for (String approver : approvers) {
//                prApprove.Approve(approver, PRReferenceNumber);
//            }
        } catch (Exception error) {
            System.out.println(error);
        }
    }
}