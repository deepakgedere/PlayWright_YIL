package com.procurement.workorders.approvals;

import com.base.BaseTest;
import org.testng.annotations.Test;

public class WOApproveTest extends BaseTest {
    @Test
    public void WOApproveTestMethod(){
        try {
            woApproveInterface.WOApproveMethod();
        } catch (Exception error) {
            System.out.println(error);
        }
    }
}
