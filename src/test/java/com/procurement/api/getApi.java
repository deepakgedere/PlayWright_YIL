package com.procurement.api;

import com.base.BaseAPI;
import com.microsoft.playwright.APIResponse;
import org.testng.annotations.Test;

import java.util.Arrays;

public class getApi extends BaseAPI {

    @Test
    public void getAPITestMethod(){
        APIResponse apiResponse = apiRequestContext.get("https://dprocure-uat.cormsquare.com/api/Reports/GetMyPendingApprovalCount\n");
        String x = Arrays.toString(apiResponse.body());
        String y = apiResponse.text();
        System.out.println(y);
    }


}
