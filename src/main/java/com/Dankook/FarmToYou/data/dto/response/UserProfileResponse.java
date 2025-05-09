package com.Dankook.FarmToYou.data.dto.response;


import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class UserProfileResponse {

    private String memberName;
    private String memberPhoneNumber;
    private String memberEmail;
    private String memberAddress;
//    private LocalDateTime matchedTime;
}
