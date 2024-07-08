package com.interfaces;

public interface POInvoiceCreateInterface {

    void VendorCreatePOInvoice() throws InterruptedException;
    double VendorGST();
    void SGDEquivalentEnable(double finalGSTPercentage);
}