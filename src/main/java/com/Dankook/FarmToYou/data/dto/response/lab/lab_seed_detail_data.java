package com.Dankook.FarmToYou.data.dto.response.lab;

import lombok.*;

import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class lab_seed_detail_data {
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
    private int seedResearchStatus; // 0~4
    private List<ResearchFarmer> seedResearchFarmers;
    private List<ResearchDiary> seedResearchDiarys;
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
    public class ResearchFarmer {
        private int farmerId;
        private String farmerName;
        private String farmerPhone;
        private String farmerEmail;
    }
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public class ResearchDiary {
        private int seedResearchFarmerId;
        private int seedResearchDiaryId;
        private int seedResearchDiaryWatering;
        private int seedResearchDiaryFertilizer;
        private int seedResearchDiarySunlight;
        private String seedResearchDiaryTitle;
        private String seedResearchDiaryContent;
        private List<String> seedResearchDiaryImage;
    }
}
