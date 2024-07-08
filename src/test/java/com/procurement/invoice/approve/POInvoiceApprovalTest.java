package com.procurement.invoice.approve;
import com.base.BaseTest;
import org.testng.annotations.Test;

public class POInvoiceApprovalTest extends BaseTest {

    @Test
    public void POInvoiceApprovalTestMethod(){
        try {
        poInvoiceApprovalInterface.POInvoiceAllApprove();
        } catch (Exception error) {
            System.out.println(error);
        }
    }
}