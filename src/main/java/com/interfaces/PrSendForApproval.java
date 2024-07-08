package com.interfaces;

import java.util.List;

public interface PrSendForApproval {
    void NonCatalogPrSendForApproval() throws InterruptedException;
    List<String> GetPRApprovers() throws InterruptedException;
    String GetPRReferenceNumber();
}