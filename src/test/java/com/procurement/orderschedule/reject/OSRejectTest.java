package com.procurement.orderschedule.reject;
import com.base.BaseTest;
import org.testng.annotations.Test;

public class OSRejectTest extends BaseTest {

    @Test
    public void OSRejectTestMethod(){
        try {
            osReject.OSRejectMethod();
        } catch (Exception error) {
            System.out.println(error);
        }
    }
}