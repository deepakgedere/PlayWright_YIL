package com.procurement.workorders.approvals;

import com.base.BaseTest;
import org.testng.annotations.Test;

public class WOSendForApprovalTest extends BaseTest {

    @Test
    public void WorkOrderSendForApprovalTestMethod(){
        try {
            woSendForApprovalInterface.SendForApproval();
        } catch (Exception error) {
            System.out.println(error);
        }
    }
}
