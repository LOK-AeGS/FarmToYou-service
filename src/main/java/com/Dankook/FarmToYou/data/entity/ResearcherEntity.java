package com.Dankook.FarmToYou.data.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Researcher")
@Getter
@Setter
@Builder
@AllArgsConstructor
/*
- 연구소 이름 (LAB_NAME)
- 연구소 연락처 (LAB_PHONE)
- 연구소 소재지 (LAB_ADDRESS)
- 담당자명 (LAB_ADMIN_NAME)
- 담당자 직급 (LAB_ADMIN_GRADE)
- 담당자 연락처 (LAB_ADMIN_PHONE)
 */
public class ResearcherEntity {
    @Id
    private String researcherId;
    private String researcherName;
    private String researcherPhoneNumber;
    private String researcherEmail;
    private String researcherAddress;
    private String researcherGrade;
    private String researcherLabPhoneNumber;
}
