package com.procurement.workorders.approvals;

import com.base.BaseTest;
import org.testng.annotations.Test;

public class WORejectTest extends BaseTest {
    @Test
    public void WORejectTest(){
        try {
            woRejectInterface.WORejectMethod();
        } catch (Exception error) {
            System.out.println(error);
        }
    }
}
