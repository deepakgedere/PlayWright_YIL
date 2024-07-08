package com.procurement.requestforquotation.quotationsubmit;

import com.base.BaseTest;
import org.testng.annotations.Test;
public class QuotationSubmitTest extends BaseTest {

    @Test
    public void QuotationSubmitMethod() throws InterruptedException{
        try {
        quotationSubmit.InviteRegisteredVendor();
        quotationSubmit.VendorLogin();
        quotationSubmit.Compliances();
        quotationSubmit.QuotationItems();
        quotationSubmit.QuotationAttachments();
        quotationSubmit.QuotationSubmitButton();
        } catch (Exception error) {
            System.out.println(error);
        }
    }
}