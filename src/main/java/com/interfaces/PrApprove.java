package com.interfaces;

public interface PrApprove {
    void Approve();
    void Approve(String approverMailId);
    void Approve(String approverMailId, String PRReferenceNumber) throws InterruptedException;
}