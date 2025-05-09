package com.Dankook.FarmToYou.data.entity;

import org.joda.time.LocalDateTime;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Document(collection = "Diary")
@Getter
@Setter
@Builder
@AllArgsConstructor
public class DiaryEntity {

    @Id
    private String id;
    private String seedNumber;
    private String seedName;
    private String content;
    private LocalDateTime localDateTime;
    private String imageUrl;
    private String personId;
    private int infusionOfNutritionalSupplements; //1은 물 준거 0은 물 안준거

}
