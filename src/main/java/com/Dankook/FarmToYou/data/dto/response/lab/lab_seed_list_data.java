package com.Dankook.FarmToYou.data.dto.response.lab;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class lab_seed_list_data {
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
    private List<ResearchFarmer> seedResearchFarmers;
    private List<ResearchDiary> seedResearchDiarys;
    @Getter
    @Setter
    public class SeedDesc {
        private String title;
        private String content;
    }
    @Getter
    @Setter
    public class ResearchFarmer {
        private int farmerId;
        private String farmerName;
        private String farmerPhone;
        private String farmerEmail;
    }
    @Getter
    @Setter
    public class ResearchDiary {
        private int seedId;
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
