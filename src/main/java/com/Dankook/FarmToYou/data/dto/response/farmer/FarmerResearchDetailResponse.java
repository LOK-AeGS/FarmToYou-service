package com.Dankook.FarmToYou.data.dto.response.farmer;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class FarmerResearchDetailResponse {
    private Integer seedId;
    private Integer seedManageId;
    private String seedUrl;

    private Integer seedLabId;
    private String seedLab;
    private String seedLabUrl;

    private String seedName;
    private Integer seedTestPay;
    private String seedCategory;

    private List<String> seedImage;

    private SeedDesc seedDesc;

    private Integer seedResearchStatus;

    private List<SeedResearchDiary> seedResearchDiarys;

    @Data
    @Builder
    public static class SeedDesc {
        private String title;
        private String content;
    }

    @Data
    @Builder
    public static class SeedResearchDiary {
        private Integer seedId;
        private Integer seedResearchFarmerId;
        private Integer seedResearchDiaryId;
        private Integer seedResearchDiaryWatering;
        private Integer seedResearchDiaryFertilizer;
        private Integer seedResearchDiarySunlight;
        private String seedResearchDiaryTitle;
        private String seedResearchDiaryContent;
        private List<String> seedResearchDiaryImage;
    }
} 