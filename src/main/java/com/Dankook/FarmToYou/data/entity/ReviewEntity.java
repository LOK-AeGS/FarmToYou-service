package com.Dankook.FarmToYou.data.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Review")
@Getter
@Setter
@Builder
@AllArgsConstructor
public class ReviewEntity {
    @Id
    private String id;

    private String postId; // 게시물 Id
    private String content; // 리뷰 내용
    private String memberId; // 리뷰 남긴 사람
}
