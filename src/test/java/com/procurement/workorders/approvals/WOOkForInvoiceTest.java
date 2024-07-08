package com.procurement.workorders.approvals;

import com.base.BaseTest;
import org.testng.annotations.Test;

public class WOOkForInvoiceTest extends BaseTest {
    @Test
    public void WOOkForInvoiceTestMethod(){
        try {
            woOkForInvoiceInterface.WOOkForInvoiceMethod();
        } catch (Exception error) {
            System.out.println(error);
        }
    }
}
