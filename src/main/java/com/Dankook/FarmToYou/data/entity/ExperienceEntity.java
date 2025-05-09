package com.Dankook.FarmToYou.data.entity;

import java.time.LocalDateTime;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "exp_booth")
public class ExperienceEntity {

    @Id
    private ObjectId id;

    private int expId;

    private String title;
    private String content;
    private String imageUrl;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private String expManager;
    private String expManagerUrl;

    private Integer expPrice;
    private String expLocate;
    private String expUrl;

    private LocalDateTime expApplyStartDate;
    private LocalDateTime expApplyEndDate;
    private LocalDateTime expStartDate;
    private LocalDateTime expEndDate;

    private List<String> expImage;

    private ExpDesc expDesc;

    private Integer expStatus; // 0: 모집중, 1: 종료 등

    private List<ExpParticipant> expParticipants;
    private List<ExpReview> expReviews;

    @Data
    public static class ExpDesc {
        private String title;
        private String content;
    }

    @Data
    public static class ExpParticipant {
        private Integer expParticipantId;
        private Integer expParticipantType; // 0: 일반, 1: 농부 등
        private String expParticipantName;
        private String expParticipantPhone;
        private String expParticipantEmail;
        private String expParticipantImage;
        private Integer expParticipantStatus; // 0: 승인, 1: 대기 등
        private LocalDateTime expParticipantApplyDate;
    }

    @Data
    public static class ExpReview {
        private Integer expReviewId;
        private Integer expReviewerId;
        private String expReviewerName;
        private ExpReviewDesc expReviewDesc;
        private Integer expReviewRate;
        private List<String> expReviewImage;
        private LocalDateTime expReviewDate;
    }

    @Data
    public static class ExpReviewDesc {
        private String title;
        private String content;
    }
}