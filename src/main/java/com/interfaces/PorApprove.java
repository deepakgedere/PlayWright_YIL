package com.interfaces;

import java.util.List;

public interface PorApprove {

//    void ApproverLogin(List<String> matchingApprovers);
    List<String> GetPORApprovers() throws InterruptedException;
    String GetPORReferenceNumber();
    void AllApprove() throws InterruptedException;
    void ApproveMethod(String ApproverMailId , String PRReferenceNumber) throws InterruptedException;
}