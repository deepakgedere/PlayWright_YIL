package com.procurement.invoice.create;

import com.factory.PlayWrightFactory;
import com.interfaces.LoginPageInterface;
import com.interfaces.LogoutPageInterface;
import com.interfaces.POInvoiceCreateInterface;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Response;
import com.microsoft.playwright.options.AriaRole;
import com.procurement.currencyexchangerate.CurrencyExchangeRate;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.Paths;
import java.util.Properties;

public class POInvoiceCreate implements POInvoiceCreateInterface {

    PlayWrightFactory playWrightFactory;
    Properties properties;
    Page page;
    LoginPageInterface loginPageInterface;
    LogoutPageInterface logoutPageInterface;
    CurrencyExchangeRate currencyExchangeRate;

    int EUR = 4;
    int USD = 3;
    int INR = 2;

    private POInvoiceCreate() {
    }

//TODO Constructor
    public POInvoiceCreate(PlayWrightFactory playWrightFactory, LoginPageInterface loginPageInterface, Properties properties, Page page, LogoutPageInterface logoutPageInterface, CurrencyExchangeRate currencyExchangeRate) {
        this.loginPageInterface = loginPageInterface;
        this.properties = properties;
        this.page = page;
        this.logoutPageInterface = logoutPageInterface;
        this.currencyExchangeRate = currencyExchangeRate;
        this.playWrightFactory = playWrightFactory;
    }

    public void VendorCreatePOInvoice() throws InterruptedException {
        Response response = page.waitForResponse(
                resp -> resp.url().equals("https://dprocure-uat.cormsquare.com/api/VP/RequestForQuotations/Listing") && resp.status() == 200,
                () -> {
                    // Click the login button, which triggers the request
                    loginPageInterface.LoginMethod(properties.getProperty("VendorMailId"));
                }
        );
        page.locator("//*[contains(text(), 'Invoices')]").click();
        page.locator(".btn.btn-primary").first().click();
        String poReferenceId = properties.getProperty("PoReferenceId");
        page.locator("#select2-typeId-container").click();
        page.getByRole(AriaRole.SEARCHBOX).fill("Purchase Order");
        Locator InvoiceType = page.locator("//li[contains(text(),'Purchase Order')]");
        InvoiceType.click();
        page.locator("#invoiceNumber").fill(properties.getProperty("InvoiceNumber"));
        page.getByPlaceholder("Select Invoice Date").last().click();
        page.locator("//span[@class='flatpickr-day today']").last().click();
        page.locator("#select2-poId-container").click();
        page.getByRole(AriaRole.SEARCHBOX).fill(properties.getProperty("PoReferenceId"));
        Locator PoRefId = page.locator("//li[contains(text(),'" + properties.getProperty("PoReferenceId") + "')]");
        PoRefId.click();
        Locator AddDocuments1 = page.locator("#doc1");
        AddDocuments1.setInputFiles(Paths.get("Attachments-and-import-files/Invoice Attachment.xlsx"));
        Response x = page.waitForResponse(
                resp -> resp.url().equals("https://dprocure-uat.cormsquare.com/FileUpload") && resp.status() == 200,
                () -> {
                    page.locator("#btnCreate").click();
                }
        );
        page.locator(".bootbox-accept").click();
        logoutPageInterface.LogoutMethod();
    }

    public double VendorGST() {
//TODO Used JavaScript to get the value of the input field => page.evaluate("document.getElementById('USDgstId').value");
        double finalGSTPercentage = 0;
        try {
            String GSTPercentage = String.valueOf(page.locator("#USDgstId").evaluate("document.getElementById('USDgstId').value"));
            String getGSTPercentage = GSTPercentage.replaceAll("[^\\d.]", "");
            finalGSTPercentage = Double.parseDouble(getGSTPercentage);
        } catch (Exception error) {
            System.out.println("What is the Error :" + error.getMessage());
        }
        return finalGSTPercentage;
    }

    public void SGDEquivalentEnable(double finalGSTPercentage) {
        String poReferenceId = properties.getProperty("PoReferenceId");
        String currencyCode = properties.getProperty("InvoiceCurrencyCode");
        if (!currencyCode.equals("SGD") && finalGSTPercentage != 0 && (poReferenceId.startsWith("2400") || poReferenceId.startsWith("5K00") || poReferenceId.startsWith("2U00") || poReferenceId.startsWith("2W00"))) {
//TODO Foreign Sub-Total
            String getForeignSubTotal = page.locator("#USDsubtotal").getAttribute("value");
            String foreignSubTotal = getForeignSubTotal.replaceAll("[^\\d.]", "");
            double finalforeignSubTotal = Double.parseDouble(foreignSubTotal);
//TODO Input Sub-Total
            double getCurrencyExchangeRate = currencyExchangeRate.findCurrency();
            double sgdEquivalentSubTotal = finalforeignSubTotal * getCurrencyExchangeRate;
            page.locator("#SGDsubtotalInput").fill(String.valueOf(sgdEquivalentSubTotal));
//TODO Manually trigger the input and change events to ensure JavaScript logic executes
            page.locator("#SGDsubtotalInput").evaluate("el => { el.dispatchEvent(new Event('keyup', { bubbles: true })); }");
            //TODO Currency Exchange Rate
            double totalCurrencyExchangeRate = sgdEquivalentSubTotal / finalforeignSubTotal;
//TODO Currency Exchange Rate * Total GST
            String getForeignTotalGst = page.locator("#USDtotalGST").getAttribute("value");
            String foreignTotalGst = getForeignTotalGst.replaceAll("[^\\d.]", "");
            double finalForeignTotalGst = Double.parseDouble(foreignTotalGst);
            double inputTotalGst = totalCurrencyExchangeRate * finalForeignTotalGst;

            if (currencyCode.equals("EUR")) {
                String sgdTotalGST = String.valueOf(inputTotalGst);
//TODO Keep only digits and the decimal point
                String replaceSGDTotalGST = sgdTotalGST.replaceAll("[^\\d.]", "");
//TODO Convert to double for rounding
                double getSGDValue = Double.parseDouble(replaceSGDTotalGST);
//TODO Round off to 4 decimal places (adjust as needed)
                BigDecimal EURValue = new BigDecimal(getSGDValue).setScale(EUR, RoundingMode.HALF_UP);
                page.locator("#SGDtotalGSTInput").fill(String.valueOf(EURValue));
            }

            if (currencyCode.equals("USD")) {
                String sgdTotalGST = String.valueOf(inputTotalGst);
//TODO Keep only digits and the decimal point
                String replaceSGDTotalGST = sgdTotalGST.replaceAll("[^\\d.]", "");
//TODO Convert to double for rounding
                double getSGDValue = Double.parseDouble(replaceSGDTotalGST);
//TODO Round off to 3 decimal places (adjust as needed)
                BigDecimal USDValue = new BigDecimal(getSGDValue).setScale(USD, RoundingMode.HALF_UP);
                page.locator("#SGDtotalGSTInput").fill(String.valueOf(USDValue));
            }

            if (currencyCode.equals("INR")) {
                String sgdTotalGST = String.valueOf(inputTotalGst);
//TODO Keep only digits and the decimal point
                String replaceSGDTotalGST = sgdTotalGST.replaceAll("[^\\d.]", "");
//TODO Convert to double for rounding
                double getSGDValue = Double.parseDouble(replaceSGDTotalGST);
//TODO Round off to 2 decimal places (adjust as needed)
                BigDecimal INRValue = new BigDecimal(getSGDValue).setScale(INR, RoundingMode.HALF_UP);
                page.locator("#SGDtotalGSTInput").fill(String.valueOf(INRValue));
            }

//TODO Invoice Document
            Locator invoiceDocumentButton = page.locator("#doc1").first();
            invoiceDocumentButton.setInputFiles(Paths.get("D://YokogawaAsiaPrivateLimited//Downloads//Invoice Document.xlsx"));
            page.locator("#btnCreate").click();
            page.locator(".bootbox-accept").click();
        } else {
//TODO Invoice Document
            Locator invoiceDocumentButton2 = page.locator("#doc1").first();
            invoiceDocumentButton2.setInputFiles(Paths.get("D://YokogawaAsiaPrivateLimited//Downloads//Invoice Documents.xlsx"));
            page.locator("#btnCreate").click();
            page.locator(".bootbox-accept").click();
        }
    }
}