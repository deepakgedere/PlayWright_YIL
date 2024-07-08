package com.procurement.workorders.edit;

import com.base.BaseTest;
import org.testng.annotations.Test;

public class WorkOrderEditTest extends BaseTest {
    @Test
    public void WorkOrdersCreateTestMethod(){
        try {
            workOrderEditInterface.EditMethod();
        } catch (Exception error) {
            System.out.println(error);
        }
    }
}
