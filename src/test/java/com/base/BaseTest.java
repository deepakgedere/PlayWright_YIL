package com.base;

import com.factory.PlayWrightFactory;
import com.interfaces.*;
import com.microsoft.playwright.Page;
import com.procurement.currencyexchangerate.CurrencyExchangeRate;
import com.procurement.currencyexchangerate.CurrencyExchangeRateTest;
import com.procurement.dispatchnotes.assign.DispatchNotesAssign;
import com.procurement.dispatchnotes.assign.DispatchNotesAssignTest;
import com.procurement.dispatchnotes.create.DispatchNoteCreate;
import com.procurement.dispatchnotes.create.DispatchNotesCreateTest;
import com.procurement.inspections.assign.InspectionAssign;
import com.procurement.inspections.assign.InspectionAssignTest;
import com.procurement.inspections.create.InspectionCreate;
import com.procurement.inspections.create.InspectionCreateTest;
import com.procurement.inspections.fail.InspectionFail;
import com.procurement.inspections.fail.InspectionFailTest;
import com.procurement.invoice.approve.POInvoiceApproval;
import com.procurement.invoice.approve.POInvoiceApprovalTest;
import com.procurement.invoice.create.InvoiceCreateTest;
import com.procurement.invoice.create.POInvoiceCreate;
import com.procurement.invoice.sendforapproval.POInvoiceSendForApproval;
import com.procurement.invoice.sendforapproval.POInvoiceSendForApprovalTest;
import com.procurement.login.LoginPage;
import com.procurement.logout.LogoutPage;
import com.procurement.msa.InspectPOTest;
import com.procurement.msa.PorInspectPO;
import com.procurement.orderschedule.approval.OSApproveTest;
import com.procurement.orderschedule.approve.OrderScheduleApprove;
import com.procurement.orderschedule.create.OSCreateTest;
import com.procurement.orderschedule.create.OrderScheduleCreate;
import com.procurement.orderschedule.edit.OSEditTest;
import com.procurement.orderschedule.edit.OrderScheduleEdit;
import com.procurement.orderschedule.reject.OSRejectTest;
import com.procurement.orderschedule.reject.OrderScheduleReject;
import com.procurement.purchaseorder.BuyerPurchaseOrder;
import com.procurement.purchaseorder.POSendForVendorTest;
import com.procurement.purchaseorderrequest.approval.Approve;
import com.procurement.purchaseorderrequest.approval.PocPorSendForApproval;
import com.procurement.purchaseorderrequest.approvalandapprove.PorApprovalAndApprove;
import com.procurement.purchaseorderrequest.approvalandapprove.PorApprovalAndApproveTest;
import com.procurement.purchaseorderrequest.create.PocNonCatalogPorCreate;
import com.procurement.purchaseorderrequest.create.PorCreateTest;
import com.procurement.purchaseorderrequest.edit.PocPorEdit;
import com.procurement.purchaseorderrequest.edit.PorEditTest;
import com.procurement.purchaseorderrequest.reject.PocPorReject;
import com.procurement.purchaseorderrequest.reject.PocPorRejectTest;
import com.procurement.purchaseorderrequest.suspend.PocPorSuspend;
import com.procurement.purchaseorderrequest.suspend.PorSuspendPorEditTest;
import com.procurement.purchaseorderrequest.suspend.PorSuspendRfqEditTest;
import com.procurement.requestforquotation.commercialevaluation.CommercialEvaluationTest;
import com.procurement.requestforquotation.create.RFQCreateTest;
import com.procurement.requestforquotation.edit.RfqEditTest;
import com.procurement.requestforquotation.quotationregret.RegretBeforeRequoteTest;
import com.procurement.requestforquotation.quotationrequote.QuotationRequoteTest;
import com.procurement.requestforquotation.quotationsubmit.QuotationSubmitTest;
import com.procurement.requestforquotation.readyforevaluation.ReadyForEvaluationTest;
import com.procurement.requestforquotation.suspend.RfqSuspendPrEditTest;
import com.procurement.requestforquotation.suspend.RfqSuspendRfqEditTest;
import com.procurement.requestforquotation.technicalevaluation.TechnicalEvaluationRejectTest;
import com.procurement.requestforquotation.technicalevaluation.TechnicalEvaluationTest;
import com.procurement.requestforquotations.commercialevaluation.CommercialEvaluation;
import com.procurement.requestforquotations.create.PocRfqCreate;
import com.procurement.requestforquotations.edit.PocRfqEdit;
import com.procurement.requestforquotations.quotationregret.RegisteredVendorQuotationRegret;
import com.procurement.requestforquotations.quotationrequote.RegisteredVendorQuotationRequote;
import com.procurement.requestforquotations.quotationsubmit.RegisteredVendorQuotationSubmit;
import com.procurement.requestforquotations.readyforevaluation.ReadyForEvaluation;
import com.procurement.requestforquotations.suspend.PocRfqSuspend;
import com.procurement.requestforquotations.technicalevaluation.TechnicalEvaluation;
import com.procurement.requestforquotations.technicalevaluation.TechnicalEvaluationReject;
import com.procurement.requisition.approve.NonCatalogPrApproveTest;
import com.procurement.requisition.approve.PocPrApprove;
import com.procurement.requisition.assign.PocPrAssign;
import com.procurement.requisition.assign.RequisitionAssignTest;
import com.procurement.requisition.create.NonCatalogPrCreateTest;
import com.procurement.requisition.create.PocNonCatalogPrCreate;
import com.procurement.requisition.edit.NonCatalogPrEditTest;
import com.procurement.requisition.edit.PocPrEdit;
import com.procurement.requisition.reject.NonCatalogPrRejectTest;
import com.procurement.requisition.reject.PocPrReject;
import com.procurement.requisition.sendforapproval.NonCatalogPrSendForApprovalTest;
import com.procurement.requisition.sendforapproval.PocPrSendForApproval;
import com.procurement.requisition.suspend.BuyerManagerNonCatalogPrSuspendTest;
import com.procurement.requisition.suspend.BuyerNonCatalogPrSuspendTest;
import com.procurement.requisition.suspend.PocPrBuyerManagerSuspend;
import com.procurement.requisition.suspend.PocPrBuyerSuspend;
import com.procurement.workorder.approvals.WOApprove;
import com.procurement.workorder.approvals.WOOkForInvoice;
import com.procurement.workorder.approvals.WOReject;
import com.procurement.workorder.approvals.WOSendForApproval;
import com.procurement.workorder.create.WorkOrderCreate;
import com.procurement.workorder.edit.WorkOrderEdit;
import com.procurement.workorder.trackerstatus.WOTrackerStatus;
import com.procurement.workorders.approvals.WOApproveTest;
import com.procurement.workorders.approvals.WOOkForInvoiceTest;
import com.procurement.workorders.approvals.WORejectTest;
import com.procurement.workorders.approvals.WOSendForApprovalTest;
import com.procurement.workorders.create.WorkOrdersCreateTest;
import com.procurement.workorders.edit.WorkOrderEditTest;
import com.procurement.workorders.trackerstatus.WOTrackerStatusTest;
import com.reports.ExtendReportListener;
import org.testng.ITestListener;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

import java.util.Properties;

public class BaseTest {

    protected PlayWrightFactory playWrightFactory;
    protected Page page;
    protected Properties properties;
    protected CurrencyExchangeRate currencyExchangeRate;
    protected CurrencyExchangeRateTest currencyExchangeRateTest;
    protected LoginPageInterface loginPageInterface;
    protected LogoutPageInterface logoutPageInterface;
    protected NonCatalogPrCreateTest nonCatalogPrCreateTest;
    protected PrCreateNonCatalog prCreateNonCatalog;
    protected NonCatalogPrEditTest nonCatalogPrEditTest;
    protected PrEdit prEdit;
    protected NonCatalogPrSendForApprovalTest nonCatalogPrSendForApprovalTest;
    protected PrSendForApproval prSendForApproval;
    protected NonCatalogPrRejectTest NonCatalogPrRejectTest;
    protected PrApprove prApprove;
    protected NonCatalogPrApproveTest nonCatalogPrApproveTest;
    protected PrReject prReject;
    protected BuyerManagerNonCatalogPrSuspendTest buyerManagerNonCatalogPrSuspendTest;
    protected PrSuspend prSuspendBuyerManager;
    protected BuyerNonCatalogPrSuspendTest buyerNonCatalogPrSuspendTest;
    protected PrSuspend prSuspendBuyer;
    protected RequisitionAssignTest requisitionAssignTest;
    protected PrAssign prAssign;
    protected RFQCreateTest rfqCreateTest;
    protected RfqCreate rfqCreate;
    protected RfqEditTest rfqEditTest;
    protected RfqEdit rfqEdit;
    protected RfqSuspendRfqEditTest rfqSuspendRfqEditTest;
    protected RfqSuspendPrEditTest rfqSuspendPrEditTest;
    protected RfqSuspend rfqSuspend;
    protected QuotationSubmitTest quotationSubmitTest;
    protected QuotationSubmit quotationSubmit;
    protected RegretBeforeRequoteTest regretBeforeRequoteTest;
    protected QuotationRequote quotationRequote;
    protected QuotationRegret quotationRegret;
    protected QuotationRequoteTest quotationRequoteTest;
    protected ReadyForEvaluationTest readyForEvaluationTest;
    protected ReadyForEvalutationInterface readyForEvalutationInterface;
    protected TechnicalEvaluationRejectTest technicalEvaluationRejectTest;
    protected TEReject teReject;
    protected TechnicalEvaluationTest technicalEvaluationTest;
    protected TechnicalEvaluationInterface technicalEvaluationInterface;
    protected CommercialEvaluationTest commercialEvaluationTest;
    protected CommercialEvaluationInterface commercialEvaluationInterface;
    protected PorCreateTest porCreateTest;
    protected PorCreateNonCatalog porCreateNonCatalog;
    protected PorEditTest porEditTest;
    protected PorEdit porEdit;
    protected PorSuspendPorEditTest porSuspendPorEditTest;
    protected PorSuspendRfqEditTest porSuspendRfqEditTest;
    protected PorSuspend porSuspend;
    protected PorApproval porApproval;
    protected PorApprove porApprove;
    protected PorReject porReject;
    protected PocPorRejectTest pocPorRejectTest;
    protected ApprovalAndApprove approvalAndApprove;
    protected PorApprovalAndApproveTest porApprovalAndApproveTest;
    protected InspectPOTest inspectPOTest;
    protected PorInspectPoInterface porInspectPoInterface;
    protected POSendForVendorTest poSendForVendorTest;
    protected PurchaseOrderInterface purchaseOrderInterface;
    protected OSCreateTest osCreateTest;
    protected OrderScheduleInterface orderScheduleInterface;
    protected OSEditTest osEditTest;
    protected OSEdit osEdit;
    protected OSRejectTest osRejectTest;
    protected OSReject osReject;
    protected OSApproveTest osApproveTest;
    protected OrderScheduleApproveInterface orderScheduleApproveInterface;
    protected InspectionCreateTest inspectionCreateTest;
    protected InspectionCreateInterface inspectionCreateInterface;
    protected InspectionFailTest inspectionFailTest;
    protected InsFail insFail;
    protected InspectionAssignTest inspectionAssignTest;
    protected InspectionAssignInterface inspectionAssignInterface;
    protected DispatchNotesCreateTest dispatchNotesCreateTest;
    protected DispatchNoteCreateInterface dispatchNoteCreateInterface;
    protected DispatchNotesAssignTest dispatchNotesAssignTest;
    protected DispatchNotesAssignInterface dispatchNotesAssignInterface;
    protected WorkOrdersCreateTest workOrdersCreateTest;
    protected WorkOrderCreateInterface workOrderCreateInterface;
    protected WorkOrderEditInterface workOrderEditInterface;
    protected WorkOrderEditTest workOrderEditTest;
    protected WOSendForApprovalInterface woSendForApprovalInterface;
    protected WOSendForApprovalTest woSendForApprovalTest;
    protected WORejectInterface woRejectInterface;
    protected WORejectTest woRejectTest;
    protected WOApproveInterface woApproveInterface;
    protected WOApproveTest woApproveTest;
    protected WOOkForInvoiceInterface woOkForInvoiceInterface;
    protected WOOkForInvoiceTest woOkForInvoiceTest;
    protected WOTrackerStatusTest woTrackerStatusTest;
    protected WOTrackerStatusInterface woTrackerStatusInterface;
    protected InvoiceCreateTest invoiceCreateTest;
    protected POInvoiceCreateInterface poInvoiceCreateInterface;
    protected POInvoiceSendForApprovalTest poInvoiceSendForApprovalTest;
    protected POSendForApprovalInterface poSendForApprovalInterface;
    protected POInvoiceApprovalTest poInvoiceApprovalTest;
    protected POInvoiceApprovalInterface poInvoiceApprovalInterface;
    protected ITestListener iTestListener;

    @Parameters({"browser"})
    @BeforeTest
    public void Setup(String browserName) {
        try {
            playWrightFactory = new PlayWrightFactory();
            if(browserName == null){
                properties = playWrightFactory.initializeProperties("chrome");
                properties.setProperty("browser", browserName);
            }
            if(browserName != null){
                properties = playWrightFactory.initializeProperties(browserName);
                properties.setProperty("browser", browserName);
            }
            page = playWrightFactory.initializeBrowser(properties);
            loginPageInterface = new LoginPage(properties, page);
            logoutPageInterface = new LogoutPage(page);

//TODO Requisition
            prCreateNonCatalog = new PocNonCatalogPrCreate(loginPageInterface, properties, page, logoutPageInterface);
            nonCatalogPrCreateTest = new NonCatalogPrCreateTest();
            prSendForApproval = new PocPrSendForApproval(loginPageInterface, properties, page, logoutPageInterface,playWrightFactory);
            nonCatalogPrSendForApprovalTest = new NonCatalogPrSendForApprovalTest();
            prApprove = new PocPrApprove(loginPageInterface, properties, page, logoutPageInterface);
            nonCatalogPrApproveTest = new NonCatalogPrApproveTest();
            prAssign = new PocPrAssign(loginPageInterface, properties, page, logoutPageInterface, playWrightFactory);
            requisitionAssignTest = new RequisitionAssignTest();
            prEdit = new PocPrEdit(loginPageInterface, properties, page, logoutPageInterface, prSendForApproval, prApprove, prAssign);
            nonCatalogPrEditTest = new NonCatalogPrEditTest();
            prReject = new PocPrReject(loginPageInterface, properties, page, logoutPageInterface, prEdit);
            NonCatalogPrRejectTest = new NonCatalogPrRejectTest();
            prSuspendBuyerManager = new PocPrBuyerManagerSuspend(loginPageInterface, properties, page, logoutPageInterface, prEdit);
            buyerManagerNonCatalogPrSuspendTest = new BuyerManagerNonCatalogPrSuspendTest();
            prSuspendBuyer = new PocPrBuyerSuspend(loginPageInterface, properties, page, logoutPageInterface, prEdit);
            buyerNonCatalogPrSuspendTest = new BuyerNonCatalogPrSuspendTest();

//TODO Request For Quotation
            rfqCreate = new PocRfqCreate(loginPageInterface, properties, page, logoutPageInterface);
            rfqCreateTest = new RFQCreateTest();
            rfqEdit = new PocRfqEdit(loginPageInterface, properties, page, logoutPageInterface);
            rfqEditTest = new RfqEditTest();
            rfqSuspend = new PocRfqSuspend(loginPageInterface, properties, page, logoutPageInterface, rfqEdit, prEdit, prSendForApproval, prApprove, prAssign, rfqCreate);
            rfqSuspendRfqEditTest = new RfqSuspendRfqEditTest();
            rfqSuspendPrEditTest = new RfqSuspendPrEditTest();
            quotationSubmit = new RegisteredVendorQuotationSubmit(loginPageInterface, properties, page, logoutPageInterface);
            quotationSubmitTest = new QuotationSubmitTest();
            quotationRegret = new RegisteredVendorQuotationRegret(quotationSubmit, loginPageInterface, properties, page, logoutPageInterface);
            regretBeforeRequoteTest = new RegretBeforeRequoteTest();
            quotationRequote = new RegisteredVendorQuotationRequote(loginPageInterface, properties, page, logoutPageInterface);
            quotationRequoteTest = new QuotationRequoteTest();
            readyForEvalutationInterface = new ReadyForEvaluation(loginPageInterface, properties, page, logoutPageInterface);
            readyForEvaluationTest = new ReadyForEvaluationTest();
            teReject = new TechnicalEvaluationReject(loginPageInterface, properties, page, logoutPageInterface);
            technicalEvaluationRejectTest = new TechnicalEvaluationRejectTest();
            technicalEvaluationInterface = new TechnicalEvaluation(loginPageInterface, properties, page, logoutPageInterface);
            technicalEvaluationTest = new TechnicalEvaluationTest();
            commercialEvaluationInterface = new CommercialEvaluation(loginPageInterface, properties, page, logoutPageInterface);
            commercialEvaluationTest = new CommercialEvaluationTest();

//TODO Purchase Order Request
            porCreateNonCatalog = new PocNonCatalogPorCreate(loginPageInterface, properties, page, logoutPageInterface);
            porCreateTest = new PorCreateTest();
            porEdit = new PocPorEdit(loginPageInterface, properties, page, logoutPageInterface);
            porEditTest = new PorEditTest();
            porSuspend = new PocPorSuspend(loginPageInterface, properties, page, logoutPageInterface, porEdit, commercialEvaluationInterface, porCreateNonCatalog);
            porSuspendPorEditTest = new PorSuspendPorEditTest();
            porSuspendRfqEditTest = new PorSuspendRfqEditTest();
            porApproval = new PocPorSendForApproval(loginPageInterface, properties, page, logoutPageInterface,playWrightFactory);
            porApprove = new Approve(loginPageInterface, properties, page, logoutPageInterface, playWrightFactory);
            porReject = new PocPorReject(loginPageInterface, properties, page, logoutPageInterface, porEdit, porApproval, porApprove);
            pocPorRejectTest = new PocPorRejectTest();
            approvalAndApprove = new PorApprovalAndApprove(porApprove, porApproval);
            porApprovalAndApproveTest = new PorApprovalAndApproveTest();
            porInspectPoInterface = new PorInspectPO(loginPageInterface, properties, page, logoutPageInterface);
            inspectPOTest = new InspectPOTest();

//TODO Purchase Order
            purchaseOrderInterface = new BuyerPurchaseOrder(loginPageInterface, properties, page, logoutPageInterface);
            poSendForVendorTest = new POSendForVendorTest();
            orderScheduleInterface = new OrderScheduleCreate(loginPageInterface, properties, page, logoutPageInterface, playWrightFactory);
            osCreateTest = new OSCreateTest();
            osEdit = new OrderScheduleEdit(loginPageInterface, properties, page, logoutPageInterface);
            osEditTest = new OSEditTest();
            osReject = new OrderScheduleReject(loginPageInterface, properties, page, logoutPageInterface);
            osRejectTest = new OSRejectTest();
            orderScheduleApproveInterface = new OrderScheduleApprove(loginPageInterface, properties, page, logoutPageInterface);
            osApproveTest = new OSApproveTest();

//TODO Inspections
            inspectionCreateInterface = new InspectionCreate(loginPageInterface, properties, page, logoutPageInterface);
            inspectionCreateTest = new InspectionCreateTest();
            insFail = new InspectionFail(loginPageInterface, properties, page, logoutPageInterface, inspectionCreateInterface);
            inspectionFailTest = new InspectionFailTest();
            inspectionAssignInterface = new InspectionAssign(loginPageInterface, properties, page, logoutPageInterface);
            inspectionAssignTest = new InspectionAssignTest();
            dispatchNoteCreateInterface = new DispatchNoteCreate(loginPageInterface, properties, page, logoutPageInterface);
            dispatchNotesCreateTest = new DispatchNotesCreateTest();
            dispatchNotesAssignInterface = new DispatchNotesAssign(loginPageInterface, properties, page, logoutPageInterface);
            dispatchNotesAssignTest = new DispatchNotesAssignTest();
            workOrderCreateInterface = new WorkOrderCreate(loginPageInterface, properties, page, logoutPageInterface);
            workOrdersCreateTest = new WorkOrdersCreateTest();
            workOrderEditInterface = new WorkOrderEdit(loginPageInterface, properties, page, logoutPageInterface);
            workOrderEditTest = new WorkOrderEditTest();
            woSendForApprovalInterface = new WOSendForApproval(loginPageInterface, properties, page, logoutPageInterface);
            woSendForApprovalTest = new WOSendForApprovalTest();
            woRejectInterface = new WOReject(loginPageInterface, properties, page, logoutPageInterface);
            woRejectTest = new WORejectTest();
            woApproveInterface = new WOApprove(loginPageInterface, properties, page, logoutPageInterface);
            woApproveTest = new WOApproveTest();
            woTrackerStatusInterface = new WOTrackerStatus(loginPageInterface, properties, page, logoutPageInterface);
            woTrackerStatusTest = new WOTrackerStatusTest();
            woOkForInvoiceInterface = new WOOkForInvoice(loginPageInterface, properties, page, logoutPageInterface);
            woOkForInvoiceTest = new WOOkForInvoiceTest();
            currencyExchangeRate = new CurrencyExchangeRate(playWrightFactory, loginPageInterface, properties, logoutPageInterface);
            currencyExchangeRateTest = new CurrencyExchangeRateTest();
            poInvoiceCreateInterface = new POInvoiceCreate(playWrightFactory, loginPageInterface, properties, page, logoutPageInterface, currencyExchangeRate);
            invoiceCreateTest = new InvoiceCreateTest();
            poSendForApprovalInterface = new POInvoiceSendForApproval(loginPageInterface, properties, page, logoutPageInterface,playWrightFactory);
            poInvoiceSendForApprovalTest = new POInvoiceSendForApprovalTest();
            poInvoiceApprovalInterface = new POInvoiceApproval(loginPageInterface, properties, page, logoutPageInterface);
            poInvoiceApprovalTest = new POInvoiceApprovalTest();
            iTestListener = new ExtendReportListener();
        } catch (Exception error) {
            System.out.println("Error :" + error);
        }
    }

    @AfterTest
    public void TearDown() {
        try {
            page.context().browser().close();
        } catch (Exception error) {
            System.out.println("Error :" + error);
        }
    }

    @AfterTest
    public void TearDown(Page page) {
        try {
            page.context().browser().close();
        } catch (Exception error) {
            System.out.println("Error :" + error);
        }
    }
}