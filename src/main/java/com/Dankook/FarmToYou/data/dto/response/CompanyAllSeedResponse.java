package com.Dankook.FarmToYou.data.dto.response;

import com.Dankook.FarmToYou.data.entity.SeedEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CompanyAllSeedResponse {

//    private String id;
    private int price;
    private String name; //원가
    private SeedEntity.SeedDesc explain; //경작 방법, 유의사항
//    private String researcherId; // researcher FK
//    private String companyId; // researcher FK

    //LocalDateTime을 월로 수정해줘야할듯 함.
//    private Map<LocalDateTime,Integer> profitByMonth; //월별 이익

    private String category;
    private int numberOfSell; // 판매개수
    private String numberOfSeed;

}
