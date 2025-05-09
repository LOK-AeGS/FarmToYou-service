package com.Dankook.FarmToYou.data.entity;

import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Document(collection = "company_seed")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SeedEntity {
    @Id
    private ObjectId id;

    private int seedId;

    private Integer seedManageId;
    private String seedUrl;
    private Integer seedCompanyId;
    private String seedCompany;
    private String seedCompanyUrl;
    private String seedName;
    private Integer seedPrice;
    private String seedCategory;
    private List<String> seedImage;
    private Integer seedRate;
    private SeedDesc seedDesc;
    private Integer seedCost;
    private Integer seedTotalSales;
    private List<SeedReview> seedReviews;

    @Data
    public static class SeedDesc {
        private String title;
        private String content;
    }

    @Data
    public static class SeedReview {
        private Integer seedReviewId;
        private Integer seedReviewerId;
        private String seedReviewerName;
        private String seedReviewContent;
        private Integer seedReviewRate;
        private List<String> seedReviewImage;
        private LocalDate seedReviewDate;
    }
}
