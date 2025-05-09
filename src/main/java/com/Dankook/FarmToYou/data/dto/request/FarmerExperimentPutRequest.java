package com.Dankook.FarmToYou.data.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FarmerExperimentPutRequest {
    private String expName;
    private String expApplyStt;
    private String expApplyEnd;
    private String expOperStt;
    private String expOperEnd;
    private String expDetail;
    private String expAddress;
    private String expPic;
} 