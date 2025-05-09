package com.Dankook.FarmToYou.data.dto.response.experience;

import java.time.LocalDateTime;
import java.util.List;

import lombok.*;
import org.joda.time.DateTime;
import org.springframework.data.mongodb.core.aggregation.ArithmeticOperators;
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExpDetailResponse {

    private int expId;
    private String expName;
    private String expUrl;
    private String expManager;
    private String expManagerUrl;
    private int expPrice;
    private String expLocate;

    private LocalDateTime expApplyStartDate;
    private LocalDateTime expApplyEndDate;
    private LocalDateTime expStartDate;
    private LocalDateTime expEndDate;

    private List<String> expImage;
    private ExpDesc expDesc;
    private int expStatus;
    private List<ExpParticipant> expParticipants;
    private List<Review> reviews;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class ExpDesc {
        private String title;
        private String content;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class ExpParticipant {
        private int expParticipantId;
        private int expParticipantType;
        private String expParticipantName;
        private String expParticipantPhone;
        private String expParticipantEmail;
        private String expParticipantImage;
        private int expParticipantStatus;
        private LocalDateTime expParticipantApplyDate;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Review {
        private int expId;
        private int expReviewId;
        private int getExpReviewId; // 이름 중복 우려. 실제 필요 여부 확인 필요
        private String expReviewerName;
        private int expReviewRate;
        private List<String> expReviewImage;
        private LocalDateTime expReviewDate;
        private ExpReviewDesc expReviewDesc;

        @Getter
        @Setter
        @AllArgsConstructor
        @NoArgsConstructor
        @Builder
        public static class ExpReviewDesc {
            private String title;
            private String content;
        }
    }
}
