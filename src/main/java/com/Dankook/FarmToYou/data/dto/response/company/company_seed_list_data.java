package com.Dankook.FarmToYou.data.dto.response.company;

import lombok.*;

import java.util.List;
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class company_seed_list_data {
    private int seed_id;
    private int seed_manage_id;
    private String seed_url;
    private int seed_company_id;
    private String seed_company;
    private String seed_company_url;
    private String seed_name;
    private int seed_price;
    private String seed_category;
    private List<String> seed_image;
    private int seed_rate;
    private SeedDesc seedDesc;
    private int seed_cost;
    private int seed_total_sales;
    private List<SeedReview> seedReviews;

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SeedDesc {
        private String title;
        private String content;
    }

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SeedReviewDesc {
        private String title;
        private String content;
    }

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SeedReview {
        private int seedId;
        private int seedReviewId;
        private int seedReviewerId;
        private String seedReviewerName;
        private SeedReviewDesc seedReviewDesc;
        private int seedReviewRate;
        private List<String> seedReviewImage;
        private String seedReviewDate;
    }
}
