package com.procurement.requisition.approve;

import com.base.BaseTest;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

public class NonCatalogPrApproveTest extends BaseTest {
    @Test (priority = 4, groups = "requisition")
    public void NonCatalogPrApproveTestMethod(){
        try {
            int x = Integer.parseInt(properties.getProperty("ApproversCount"));
            List<String> approvers = new ArrayList<>();
            for(int i=1;i<=x;i++){
                approvers.add(properties.getProperty("Approver"+i));
            }
            String PRReferenceNumber = properties.getProperty("PrRefId");
            for (String approverMailId : approvers) {
                prApprove.Approve(approverMailId , PRReferenceNumber);
            }
        } catch (Exception error) {
            System.out.println(error);
        }
    }
}