package com.Dankook.FarmToYou.data.dto.response.experience;

import java.time.LocalDateTime;
import java.util.List;

import lombok.*;

@Getter
@Builder
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExpListResponse {
    private int seedId;
    private int seedManageId;
    private String seedUrl;
    private int seedLabId;
    private String seedLab;
    private String seedLabUrl;
    private String seedName;
    private int seedTestPay;
    private String seedCategory;
    private List<String> seedImage;
    private SeedDesc seedDesc;
    private int seedResearchStatus;
    private List<Diary> seedResearchDiarys;

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
    public static class Diary {
        private int seedId;
        private int seed_research_farmer_id;
        private int seed_research_diary_id;
        private int seed_research_diary_watering;
        private int seed_research_diary_fertilizer;
        private int seed_research_diary_sunlight;
        private String seed_research_diary_title;
        private String seed_research_diary_content;
        private List<String> seed_research_diary_image;
    }

} 