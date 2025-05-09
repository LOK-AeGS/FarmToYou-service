package com.Dankook.FarmToYou.data.dto.response.farmer;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FarmerProfileResponse {
    private String id;
    private String name;
    private String email;
    private String phone;
    private String address;
} 