package com.Dankook.FarmToYou.data.entity;

import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Document(collection = "Post")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostEntity {

    @Id
    private String id;

    private String title;
    private String content;
    private Map<LocalDateTime,LocalDateTime> startTimeEndTime; // 시작날짜 종료날짜
    private String imageUrl;
    private String farmerId;// Farmer FK
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
