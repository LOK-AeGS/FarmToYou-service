package com.Dankook.FarmToYou.data.dto.response.company;

import lombok.*;

import java.util.List;

/*

export const company_seed_detail_data = { // 품종판매 상세 데이터
        seed_id: 1, // 품종 번호
        seed_manage_id: 0, // -1, 0, 1
        seed_url: 'https://www.google.com',
        seed_company_id: 94, // 회사 번호 (고유값)
        seed_company: '씨앤씨 컴퍼니',
        seed_company_url: 'https://www.google.com',
        seed_name: '[혼합종자] 머시기1',
        seed_price: 1900,
        seed_category: '곡류',
        seed_image: ['', '', ''], // 이미지 경로
        seed_rate: 3, // 1 ~ 5 점 척도
        seed_desc: {
            title: '[혼합종자] 1kg 팝니다.',
            content: '씨앤씨 컴퍼니는 씨앤씨 컴퍼니의 씨앤씨 컴퍼니입니다.',
        },
        seed_cost: 1000, // 종자 원가
        seed_reviews: [
            {
                seed_id: 1,
                seed_reviewer_id: 1,
                seed_reviewer_name: '홍길동',
                seed_review_id: 1,
                seed_review_content: '좋아요',
                seed_review_rate: 5,
                seed_review_image: ['', '', ''],
                seed_review_date: '2025-01-01',
            },
            {
                seed_id: 1,
                seed_reviewer_id: 1,
                seed_reviewer_name: '홍길동',
                seed_review_id: 1,
                seed_review_content: '좋아요',
                seed_review_rate: 5,
                seed_review_image: ['', '', ''],
                seed_review_date: '2025-01-01',
            }
        ],
        seed_total_sales: 943,
    }
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class company_seed_detail_data {
    private int seed_id; // 품종 번호
    private int seed_manage_id; // -1, 0, 1
    private String seed_url;
    private int seed_company_id; // 회사 번호 (고유값)
    private String seed_company;
    private String seed_company_url;
    private String seed_name ;
    private int seed_price;
    private String seed_category;
    List<String> seed_image; // 이미지 경로
    private int seed_rate; // 1 ~ 5 점 척도

    private SeedDesc seedDesc;
    private int seed_cost; // 종자 원가
    private List<SeedReview> seedReviews;
    private int seed_total_sales;
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
    public static class SeedReview {
        private int seedId;
        private int seedReviewerId;
        private String seedReviewerName;
        private int seedReviewId;
        private String seedReviewContent;
        private int seedReviewRate;
        private List<String> seedReviewImage;
        private String seedReviewDate; // 또는 LocalDate
    }
}
