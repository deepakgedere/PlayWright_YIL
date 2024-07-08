package com.procurement.requestforquotations.quotationsubmit;

import com.interfaces.LoginPageInterface;
import com.interfaces.LogoutPageInterface;
import com.interfaces.QuotationSubmit;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
public class RegisteredVendorQuotationSubmit implements QuotationSubmit {

    Properties properties;
    Page page;
    LoginPageInterface loginPageInterface;
    LogoutPageInterface logoutPageInterface;

    private RegisteredVendorQuotationSubmit(){
    }

//TODO Constructor
    public RegisteredVendorQuotationSubmit(LoginPageInterface loginPageInterface, Properties properties, Page page, LogoutPageInterface logoutPageInterface){
        this.loginPageInterface = loginPageInterface;
        this.properties = properties;
        this.page = page;
        this.logoutPageInterface = logoutPageInterface;
    }

    public void InviteRegisteredVendor(){
        loginPageInterface.LoginMethod(properties.getProperty("Buyer"));
        page.locator("//*[contains(text(), 'Request For Quotations')]").click();
        String title = properties.getProperty("Title");
        page.locator("//span[contains(text(), '"+ title +"')]").first().click();
        page.locator("#addRequestForQuotationVendors").click();
        page.locator("#select2-vendorId-container").click();
        String vendorId = properties.getProperty("Vendor");
        page.locator(".select2-search__field").fill(vendorId);
        page.locator("//li[contains(text(), '"+ vendorId +"')]").first().click();
        page.locator("#saveRequestForQuotationVendor").click();
        page.locator("#vendorSendMailBtnId").click();
        logoutPageInterface.LogoutMethod();
    }

    public void VendorLogin() throws InterruptedException {
        loginPageInterface.LoginMethod(properties.getProperty("VendorMailId"));
        String title = properties.getProperty("Title");
        page.locator("//span[contains(text(), '" + title + "')]").first().click();
        page.locator("#btnSendQuote").click();
        page.locator("#incotermLocation").fill(properties.getProperty("Incoterm"));
        page.locator("#quotationReferenceNumber").fill(properties.getProperty("QuotationReferenceNumber"));
        Thread.sleep(2000);
        Locator validityDate = page.locator("#dates");
        validityDate.click();
        Locator today = page.locator("//span[@class='flatpickr-day today']");
        int getTodayDayNumber = Integer.parseInt(today.textContent());
        int getTomorrowDayNumber = getTodayDayNumber + 1;
        int nextDayAfterThirty = 31;
        if (getTodayDayNumber == 30) {
            Locator day = page.locator("//span[contains(text(), '" + nextDayAfterThirty + "')]");
            if (day.isVisible() || !day.isHidden()) {
                day.click();
            } else {
                page.locator(".flatpickr-day.nextMonthDay").first().click();
            }
        }
        if (getTodayDayNumber == 31) {
            page.locator(".flatpickr-day.nextMonthDay").first().click();
        }
        else {
            page.locator("//span[contains(text(), '" + getTomorrowDayNumber + "')]").last().click();
        }
    }

    public void Compliances(){
        page.locator("#liquidatedComplyId").click();
        page.locator("#rohsComplyId").click();
        page.locator("#warrantyRequirementsComplyId").click();
        page.locator("#packingForwardingComplyId").click();
        page.locator("#freightComplyId").click();
        page.locator("#insuranceComplyId").click();
        page.locator("#bankGuaranteeComplyId").click();
    }
    public void QuotationItems() {

        class ExcelReader {
            public static List<List<String>> readExcel(String filePath) {
                List<List<String>> data = new ArrayList<>();

                try (FileInputStream fis = new FileInputStream(filePath);
                     Workbook workbook = new XSSFWorkbook(fis)) {

                    Sheet sheet = workbook.getSheetAt(0);

                    // Start from the second row (index 1)
                    for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                        Row row = sheet.getRow(i);
                        List<String> rowData = new ArrayList<>();
                        for (Cell cell : row) {
                            rowData.add(getCellStringValue(cell));
                        }
                        data.add(rowData);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }

                return data;
            }

            private static String getCellStringValue(Cell cell) {
                DataFormatter dataFormatter = new DataFormatter();
                return dataFormatter.formatCellValue(cell);
            }
        }

        // Read data from the Excel file
        String excelFilePath = "Attachments-and-import-files/Quote.xlsx";
        List<List<String>> excelData = ExcelReader.readExcel(excelFilePath);

        int itemIndex = 1; // Start with the first item
        // Iterate through each row of data
        for (List<String> rowData : excelData) {
            if (rowData.size() < 11) {
                continue; // Skip rows that don't have enough data
            }
            // Assuming the input fields have specific selectors
            page.fill("#hsnsac-" + itemIndex, rowData.get(0));
            page.fill("#make-" + itemIndex, rowData.get(1));
            page.fill("#model-" + itemIndex, rowData.get(2));
            page.fill("#partNumber-" + itemIndex, rowData.get(3));
            page.fill("#countryOfOrigin-" + itemIndex, rowData.get(4));
            Locator Rate = page.locator("#rate-" + itemIndex);
            Rate.fill(rowData.get(5));
            Rate.evaluate("el => { el.dispatchEvent(new Event('keyup', { bubbles: true })); }");
            page.fill("#discount-" + itemIndex, rowData.get(6));
            page.fill("#cgst-" + itemIndex, rowData.get(7));
            page.fill("#sgst-" + itemIndex, rowData.get(8));
            page.fill("#leadTime-" + itemIndex, rowData.get(9));
            page.fill("#notes-" + itemIndex, rowData.get(10));
            itemIndex++; // Increment the item index for the next row

        }

//        for (int i=0; i<excelData.size(); i++){
//            Locator ViewItemSpecification = page.locator("#viewitemspecification");
//            if (ViewItemSpecification!=null) {
//                ViewItemSpecification.nth(i).click();
//                page.locator("#complyAll").click();
//                page.locator("#itemSpecificationSave").click();
//            }
//        }
        List<Locator> ViewSpecification = page.locator("#viewitemspecification").all();
        for(Locator x : ViewSpecification){
            x.click();
            page.locator("#complyAll").click();
            page.locator("#itemSpecificationSave").click();
        }
    }
    public void QuotationAttachments() {
//TODO Technical Attachment
        page.locator("#attachFile").click();
        Locator TechnicalFile = page.locator("#formFilePreupload");
        TechnicalFile.setInputFiles(Paths.get("Attachments-and-import-files/Technical Attachment.xlsx"));
        page.locator("#select2-attachmentTypeId-container").click();
        page.locator("//li[contains(text(), 'Technical')]").click();
        page.locator("#attachmentSaveId").click();
//TODO Commercial Attachment
        page.locator("#attachFile").click();
        Locator CommercialFile = page.locator("#formFilePreupload");
        CommercialFile.setInputFiles(Paths.get("Attachments-and-import-files/Commercial Attachment.xlsx"));
        page.locator("#select2-attachmentTypeId-container").click();
        page.locator("//li[contains(text(), 'Commercial')]").click();
        page.locator("#attachmentSaveId").click();
    }

    public void QuotationSubmitButton(){
        page.locator("#btnCreate").click();
        page.locator(".bootbox-accept").click();
        logoutPageInterface.LogoutMethod();
    }
}