package com.Dankook.FarmToYou.data.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FarmerProfileEditRequest {
    private String farmerName;
    private String farmerPhone;
    private String farmerAddress;
    private String farmerFarmAddress;
} 