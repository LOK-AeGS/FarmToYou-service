package com.Dankook.FarmToYou.data.dto.response.farmer;

import java.time.LocalDateTime;
import java.util.List;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class FarmerResearchListResponse {

    private int seedId;
    private int seed_ManageId;
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
    private List<SeedResearchDiary> seedResearchDiaries;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public class SeedDesc {
        private String title;
        private String content;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public class SeedResearchDiary {
        private int seedId;
        private int seedResearchFarmerId;
        private int seedResearchDiary_watering;
        private int seedResearchDiaryFertilizer;
        private int seedResearch_diarySunlight;
        private String seedResearchDiaryTitle;
        private String seedResearchDiaryContent;
        private List<String> seedResearchDiary_image;
    }
}
