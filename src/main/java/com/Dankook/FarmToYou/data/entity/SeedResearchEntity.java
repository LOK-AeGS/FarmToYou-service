package com.Dankook.FarmToYou.data.entity;

import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
@Getter
@Setter
@Builder
@AllArgsConstructor
@Document(collection = "lab_seed")
public class SeedResearchEntity {

    @Id
    private ObjectId id;

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

    private Integer seedResearchStatus; // 0: 진행 중, 1: 완료, 등등

    private List<SeedResearchFarmer> seedResearchFarmers;
    private List<SeedResearchDiary> seedResearchDiarys;

    @Data
    public static class SeedDesc {
        private String title;
        private String content;
    }

    @Data
    public static class SeedResearchFarmer {
        private Integer farmerId;
        private String farmerName;
        private String farmerPhone;
        private String farmerEmail;
    }

    @Data
    public static class SeedResearchDiary {
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