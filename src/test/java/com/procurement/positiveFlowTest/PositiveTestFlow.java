package com.procurement.positiveFlowTest;

import com.base.BaseTest;
import com.beust.jcommander.Parameter;
import jdk.jfr.Enabled;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.IListenersAnnotation;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

public class PositiveTestFlow extends BaseTest {
    @Test
    public void PositiveFlowTestMethod(){
        try {
            prCreateNonCatalog.RequesterLoginPRCreate();
            prCreateNonCatalog.CreateButton();
            prCreateNonCatalog.NonCatalog();
            prCreateNonCatalog.Title();
            prCreateNonCatalog.Project();
            prCreateNonCatalog.WBS();
            prCreateNonCatalog.Incoterm();
            prCreateNonCatalog.LiquidatedDamages();
            prCreateNonCatalog.WarrantyRequirements();
            prCreateNonCatalog.PriceValidity();
            prCreateNonCatalog.ShipToYokogawa();
            prCreateNonCatalog.ShippingAddress();
            prCreateNonCatalog.StorageLocation();
            prCreateNonCatalog.ShippingMode();
            prCreateNonCatalog.QuotationRequiredBy();
            prCreateNonCatalog.ExpectedPOIssue();
            prCreateNonCatalog.ExpectedDelivery();
            prCreateNonCatalog.BuyerGroup();
            prCreateNonCatalog.RoHS();
            prCreateNonCatalog.InspectionRequired();
            prCreateNonCatalog.Checker();
            prCreateNonCatalog.OrderIntake();
            prCreateNonCatalog.TargetPrice();
//            prCreateNonCatalog.Tcas();
//            prCreateNonCatalog.TcasCheck();
//            prCreateNonCatalog.TcasFileUpload();
            prCreateNonCatalog.TypeOfPurchase();
            prCreateNonCatalog.ImportItems();
            prCreateNonCatalog.AddLineRequisitionItems();
            prCreateNonCatalog.Notes();
            prCreateNonCatalog.NotesAttachments();
            prCreateNonCatalog.PRCreate();
            prSendForApproval.NonCatalogPrSendForApproval();
            int x = Integer.parseInt(properties.getProperty("ApproversCount"));
            List<String> approvers = new ArrayList<>();
            for(int i=1;i<=x;i++){
                approvers.add(properties.getProperty("Approver"+i));
            }
            String PRReferenceNumber = properties.getProperty("PrRefId");
            for (String approverMailId : approvers) {
                prApprove.Approve(approverMailId , PRReferenceNumber);
            }
            prAssign.BuyerManagerAssign();
            rfqCreate.BuyerRfqCreate();
            rfqCreate.RfQNotes();
            rfqCreate.RFQCreate();
            quotationSubmit.InviteRegisteredVendor();
            quotationSubmit.VendorLogin();
            quotationSubmit.Compliances();
            quotationSubmit.QuotationItems();
            quotationSubmit.QuotationAttachments();
            quotationSubmit.QuotationSubmitButton();
            readyForEvalutationInterface.ReadyForEvaluationButton();
            technicalEvaluationInterface.TechnicalEvaluationButton();
            commercialEvaluationInterface.CommercialEvaluationButton();
            porCreateNonCatalog.BuyerPORCreate();
            porCreateNonCatalog.Justification();
            porCreateNonCatalog.PORNotes();
            porCreateNonCatalog.PORCreate();
            porApproval.NonCatalogPORSendForApproval();
            porApprove.AllApprove();
            porInspectPoInterface.InspectCreatePO();
            purchaseOrderInterface.SendForVendor();
            orderScheduleInterface.OSCreate();
            orderScheduleApproveInterface.OSApprove();
            inspectionCreateInterface.VendorInspectionCreate();
            inspectionAssignInterface.RequesterInspectionAssign();
            dispatchNoteCreateInterface.DNCreate();
            dispatchNotesAssignInterface.DNAssign();
            workOrderCreateInterface.WOCreate();
            woSendForApprovalInterface.SendForApproval();
            woApproveInterface.WOApproveMethod();
            woTrackerStatusInterface.VendorTrackerStatus();
            woOkForInvoiceInterface.WOOkForInvoiceMethod();
            poInvoiceCreateInterface.VendorCreatePOInvoice();
            poSendForApprovalInterface.SendForApproval();
            poInvoiceApprovalInterface.POInvoiceAllApprove();
        } catch (Exception error) {
            System.out.println(error);
        }
    }
}
