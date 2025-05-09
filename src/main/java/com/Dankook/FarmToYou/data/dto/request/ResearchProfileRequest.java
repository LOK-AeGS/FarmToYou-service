package com.Dankook.FarmToYou.data.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResearchProfileRequest {
    private String researcherId;
    private String researcherName;
    private String researcherPhoneNumber;
    private String researcherAddress;
    private String researcherGrade;
    private String researcherLabPhoneNumber;
}
