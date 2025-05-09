package com.Dankook.FarmToYou.data.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserProfileEditRequest {
    private String userName;
    private String userPhone;
    private String userAddress;
} 