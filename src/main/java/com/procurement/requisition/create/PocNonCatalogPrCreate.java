package com.procurement.requisition.create;

import com.interfaces.LoginPageInterface;
import com.interfaces.LogoutPageInterface;
import com.interfaces.PrCreateNonCatalog;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Response;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.LoadState;

import java.nio.file.Paths;
import java.util.Properties;

public class PocNonCatalogPrCreate implements PrCreateNonCatalog {

    Page page;
    LoginPageInterface loginPageInterface;
    LogoutPageInterface logoutPageInterface;
    Properties properties;



    //TODO Constructor
    public PocNonCatalogPrCreate(LoginPageInterface loginPageInterface, Properties properties, Page page, LogoutPageInterface logoutPageInterface){
        this.page = page;
        this.properties = properties;
        this.loginPageInterface = loginPageInterface;
        this.logoutPageInterface = logoutPageInterface;
    }


    public void RequesterLoginPRCreate() throws InterruptedException {
        loginPageInterface.LoginMethod(properties.getProperty("EmailID"));
        Thread.sleep(2000);
        Locator Requisitions = page.locator("//div[@id='ni-requisitions']");
        Requisitions.click();
        page.waitForLoadState(LoadState.NETWORKIDLE);
    }

    public void CreateButton() {
        Locator prType = page.locator("//*[@id=\"content\"]/div[1]/div[2]/div/div[2]/button");
        prType.click();
    }

    public void NonCatalog(){
        Locator prType = page.locator("//*[@id=\"createPRModal\"]/div/div/div[2]/div/div/div/table/tbody/tr[2]/td[1]/a");
        prType.click();
    }

    public void Title() {
        Locator title = page.getByPlaceholder("Please Enter Title");
        title.fill(properties.getProperty("Title"));
    }

    public void ShipToYokogawa() {
        page.getByLabel("Ship To Yokogawa").check();
        page.waitForLoadState(LoadState.NETWORKIDLE);
    }

    public void Project() throws InterruptedException {
        page.locator("#select2-projectId-container").click();
        page.getByRole(AriaRole.SEARCHBOX).fill(properties.getProperty("Project"));
        Locator getProject = page.locator("//li[contains(text(),'" + properties.getProperty("Project") + "')]");
        Response response = page.waitForResponse(
                resp -> resp.url().startsWith("https://dprocure-uat.cormsquare.com/api/workBreakdownStructures/search") && resp.status() == 200,
                () -> {
                    getProject.click();
                }
        );
    }

    public void WBS() {
        page.locator("#select2-wbsId-container").click();
        page.getByRole(AriaRole.SEARCHBOX).fill(properties.getProperty("Wbs"));
        Locator getWbs = page.locator("//li[contains(text(),'" + properties.getProperty("Wbs") + "')][1]");
        getWbs.click();
    }

    public void Incoterm() {
        page.locator("#select2-incoterm-container").click();
        page.getByRole(AriaRole.SEARCHBOX).fill(properties.getProperty("Incoterm"));
        Locator getIncoterm = page.locator("//li[contains(text(),'" + properties.getProperty("Incoterm") + "')]");
        getIncoterm.click();
    }

    public void LiquidatedDamages() {
        page.locator("//input[@id='isLDStandardNoId']").click();
        page.locator("//input[@id='liquidatedamageTextId']").fill(properties.getProperty("LiquidatedDamages"));
    }
    public void ShippingAddress() {
        page.locator("//span[@aria-labelledby='select2-shippingaddressId-container']").click();
        page.getByRole(AriaRole.OPTION, new Page.GetByRoleOptions().setName(properties.getProperty("ShippingAddress"))).click();
    }
    public void StorageLocation() {
        page.locator("#select2-storageLocId-container").click();
        page.getByRole(AriaRole.OPTION, new Page.GetByRoleOptions().setName(properties.getProperty("StorageLocation"))).click();
    }

    public void ShippingMode() {
        page.getByText("-- Select Shipping Mode --").click();
        page.getByRole(AriaRole.SEARCHBOX).fill(properties.getProperty("ShippingMode"));
        Locator getShippingMode = page.locator("//li[contains(text(),'" + properties.getProperty("ShippingMode") + "')]");
        getShippingMode.click();
    }

    public void QuotationRequiredBy() {
        Locator quotationRequiredBy = page.locator("//*[@id=\"dates\"]/div[1]/input[2]");
        quotationRequiredBy.click();
        Locator today = page.locator("//span[@class='flatpickr-day today']").first();
        today.click();
    }

    public void ExpectedPOIssue() {
        Locator expectedPOIssue = page.locator("//*[@id=\"dates\"]/div[2]/input[2]");
        expectedPOIssue.click();
        Locator today = page.locator("//span[@class='flatpickr-day today']").first();
        today.click();
    }

    public void ExpectedDelivery() {
        Locator expectedDelivery = page.locator("//*[@id=\"dates\"]/div[3]/input[2]");
        expectedDelivery.click();
        Locator today = page.locator("//span[@class='flatpickr-day today']").first();
        today.click();
    }

    public void BuyerGroup(){
        page.locator("#select2-buyergroupid-container").click();
        page.getByRole(AriaRole.SEARCHBOX).fill(properties.getProperty("BuyerGroup"));
        Locator getBuyerGroup = page.locator("//li[contains(text(),'" + properties.getProperty("BuyerGroup") + "')]");
        getBuyerGroup.click();
    }
    public void RoHS(){
        page.locator("#rohsnotcomplianceid").check();
    }
    public void Checker(){
        page.locator("#select2-checkerId-container").click();
        page.getByRole(AriaRole.SEARCHBOX).fill(properties.getProperty("Checker"));
        Locator getBuyerGroup = page.locator("//li[contains(text(),'" + properties.getProperty("Checker") + "')]");
        getBuyerGroup.click();
    }
    public void BuyerManager(){
        page.locator("#select2-buyerManagerId-container").click();
        page.getByRole(AriaRole.SEARCHBOX).fill(properties.getProperty("BuyerManager"));
        Locator getBuyerManager = page.locator("//li[contains(text(),'" + properties.getProperty("BuyerManager") + "')]");
        getBuyerManager.click();
    }

    public void ProjectManager(){
        page.getByLabel("-- Select Project Manager --").click();
        page.getByRole(AriaRole.SEARCHBOX).fill(properties.getProperty("ProjectManager"));
        Locator getProjectManager = page.locator("//li[contains(text(),'" + properties.getProperty("ProjectManager") + "')]");
        getProjectManager.click();
    }

    public void OrderIntake(){
        page.getByPlaceholder("Enter Order Intake Cost").click();
        page.getByPlaceholder("Enter Order Intake Cost").fill(String.valueOf(properties.getProperty("OrderIntake")));
    }

    public void TargetPrice(){
        page.getByPlaceholder("Enter Target Price").click();
        page.getByPlaceholder("Enter Target Price").fill(String.valueOf(properties.getProperty("TargetPrice")));
    }

    public void WarrantyRequirements(){
        page.getByLabel("-- Select Warranty Requirements --").click();
        page.getByRole(AriaRole.SEARCHBOX).fill(properties.getProperty("WarrantyRequirement"));
        Locator getWarrantyRequirement = page.locator("//li[contains(text(),'" + properties.getProperty("WarrantyRequirement") + "')]");
        getWarrantyRequirement.click();
    }
    public void Tcas(){
        page.locator("#tcasApplicable").click();
    }
    public void TcasCheck(){
        page.locator("#q1").click();
        page.locator("#tcasSubmit").click();
    }
    public void TcasFileUpload(){
        Locator CommercialFile = page.locator("#tcasFilePreupload");
        CommercialFile.setInputFiles(Paths.get("Attachments-and-import-files/TCAS.xlsx"));
//        FileChooser fileChooser = page.waitForFileChooser(() -> page.click("#btnFileUpload"));
//        fileChooser.setFiles(Paths.get("C://Users//Cormsquare//Downloads//ImportRequisitionItems (4).xlsx"));
    }
    public void TypeOfPurchase(){
        page.locator("#select2-typeOfPurchaseId-container").click();
        Locator typeOfPurchase = page.locator("//li[contains(text(),'" + properties.getProperty("TypeOfPurchase") + "')]");
        typeOfPurchase.click();
    }
    public void PriceValidity(){
        page.getByLabel("-- Select Price Validity --").click();
        page.getByRole(AriaRole.SEARCHBOX).fill(properties.getProperty("PriceValidity"));
        Locator getPriceValidity = page.locator("//li[contains(text(),'" + properties.getProperty("PriceValidity") + "')]");
        getPriceValidity.click();
    }

    public void InspectionRequired() {
        page.locator("#inspectrequired").check();
    }

//    public void AddLineRequisitionItems() {
//        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Add line Requisition Items")).click();
////TODO Category
//        page.getByLabel("-- Select Categories --").click();
//        page.getByRole(AriaRole.SEARCHBOX).fill(properties.getProperty("Category"));
//        Locator getCategory = page.locator("//li[contains(text(),'" + properties.getProperty("Category") + "')]").first();
//        getCategory.click();
////TODO Items
//        page.getByLabel("-- Select Item --").click();
//        Locator getItem = page.locator("//li[contains(text(),'" + properties.getProperty("Item") + "')]").first();
//        getItem.click();
////TODO Quantity
//        page.getByRole(AriaRole.SPINBUTTON, new Page.GetByRoleOptions().setName("* Quantity :")).click();
//        page.getByRole(AriaRole.SPINBUTTON, new Page.GetByRoleOptions().setName("* Quantity :")).fill(String.valueOf(properties.getProperty("Quantity")));
////TODO Add Button
//        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Add").setExact(true)).click();
//    }
    public void AddLineRequisitionItems() {
        page.locator("#addLineRequisitionItems").click();
        //TODO Item Select
        page.locator("#select2-itemid-container").click();
        page.locator(".select2-search__field").fill(properties.getProperty("Item"));
        Locator getItem = page.locator("//li[contains(text(),'" + properties.getProperty("Item") + "')]").first();
        getItem.click();
        //TODO Quantity
        page.getByRole(AriaRole.SPINBUTTON, new Page.GetByRoleOptions().setName("* Quantity :")).click();
        page.getByRole(AriaRole.SPINBUTTON, new Page.GetByRoleOptions().setName("* Quantity :")).fill(String.valueOf(properties.getProperty("Quantity")));
        //TODO Add Button
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Add").setExact(true)).click();
    }
    public void ImportItems(){
        page.locator("#importItem").click();
        Locator CommercialFile = page.locator("#formFile");
        CommercialFile.setInputFiles(Paths.get("Attachments-and-import-files/ImportRequisitionItem.xlsx"));
        page.locator("#btnUpload").click();
    }

    public void Notes() {
        page.getByPlaceholder("Please Enter Notes").click();
        page.getByPlaceholder("Please Enter Notes").fill(properties.getProperty("Notes"));
    }
    public void NotesAttachments(){
        //Internal
        page.locator("#attachDocs").click();
        Locator CommercialFile = page.locator("#formFilePreupload");
        CommercialFile.setInputFiles(Paths.get("Attachments-and-import-files/Internal Attachment.xlsx"));
        page.locator("#radioActive").click();
        page.locator("#saveAttachments1").click();
        //External
        CommercialFile.setInputFiles(Paths.get("Attachments-and-import-files/External Attachment.xlsx"));
        page.locator("#radioInActive").click();
        page.locator("#saveAttachments1").click();
        //Internal Link
        page.locator("#formCheck12").check();
        page.locator("#sm_documentUrl").fill(properties.getProperty("InternalLink"));
        page.locator("#name").fill("Internal link");
        page.locator("#saveAttachments1").click();
        //External Link
        page.locator("#formCheck12").check();
        page.locator("#sm_documentUrl").fill(properties.getProperty("ExternalLink"));
        page.locator("#name").fill("External link");
        page.locator("#saveAttachments1").click();
        page.locator("#submitAttachmentsId").click();
    }

    public void PRCreate() throws InterruptedException {
//TODO Create Draft Button
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Create Draft")).click();
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Yes")).click();
        Thread.sleep(1000);
        logoutPageInterface.LogoutMethod();
    }
}