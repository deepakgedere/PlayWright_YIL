package com.procurement.orderschedule.edit;
import com.base.BaseTest;
import org.testng.annotations.Test;

public class OSEditTest extends BaseTest {

    @Test
    public void OSEditTestMethod(){
        try {
        osEdit.OSEditMethod();
        } catch (Exception error) {
            System.out.println(error);
        }
    }
}