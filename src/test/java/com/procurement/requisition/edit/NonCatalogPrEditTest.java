package com.procurement.requisition.edit;
import com.base.BaseTest;
import org.testng.annotations.Test;

public class NonCatalogPrEditTest extends BaseTest {

    @Test (priority = 2, groups = "requisition")
    public void NonCatalogPrEditTestMethod(){
        try {
            prEdit.PrEditMethod();
        } catch (Exception error) {
            System.out.println(error);
        }
    }
}