package com.Dankook.FarmToYou.data.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/*
    private int price;
    private String name; //원가
    private List<String> explain; //경작 방법, 유의사항
    private String researcherId; // researcher FK
    private String companyId; // researcher FK

    //LocalDateTime을 월로 수정해줘야할듯 함.
    private Map<LocalDateTime,Integer> profitByMonth; //월별 이익

    private String category;
    private int numberOfSell; // 판매개수
    private String numberOfSeed;
 */
@Getter
@Setter
public class CompanySeedRegister {
    private String companyId;
    private int price;
    private String name; //원가
    private List<String> explain; //경작 방법, 유의사항
//    private String researcherId; // researcher FK

    //LocalDateTime을 월로 수정해줘야할듯 함.
//    private Map<LocalDateTime,Integer> profitByMonth; //월별 이익

    private String category;
//    private int numberOfSell; // 판매개수
    private String numberOfSeed;//종자번호
}
