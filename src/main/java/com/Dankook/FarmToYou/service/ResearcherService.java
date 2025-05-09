package com.Dankook.FarmToYou.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.Dankook.FarmToYou.data.dto.response.lab.lab_seed_detail_data;
import com.Dankook.FarmToYou.data.dto.response.lab.lab_seed_list_data;
import com.Dankook.FarmToYou.data.dto.response.lab.lab_status_dashboard_data;
import com.Dankook.FarmToYou.data.entity.SeedResearchEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Dankook.FarmToYou.data.dto.request.ResearchProfileRequest;
import com.Dankook.FarmToYou.data.entity.ResearcherEntity;
import com.Dankook.FarmToYou.data.entity.SeedEntity;
import com.Dankook.FarmToYou.data.dto.response.lab.LabResearchDetailResponse;
import com.Dankook.FarmToYou.data.entity.PostEntity;
import com.Dankook.FarmToYou.repository.PostRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ResearcherService {
    @Autowired
    MongoTemplate mongoTemplate;

    private final PostRepository postRepository;

    public ResponseEntity<Object> getResearcherProfile(ResearchProfileRequest request) {
        ResearcherEntity researcher = mongoTemplate.findOne(Query.query(Criteria.where("researcherId").is(request.getResearcherId())), ResearcherEntity.class, "Researcher");
        if (researcher == null) {
            return new ResponseEntity<>("연구소 정보를 찾을 수 없습니다.", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(researcher, HttpStatus.OK);
    }

    public ResponseEntity<Object> updateResearcherProfile(String labId, ResearchProfileRequest request) {
        ResearcherEntity researcher = mongoTemplate.findOne(Query.query(Criteria.where("researcherId").is(labId)), ResearcherEntity.class, "Researcher");
        if (researcher == null) {
            return new ResponseEntity<>("연구소 정보를 찾을 수 없습니다.", HttpStatus.NOT_FOUND);
        }
        researcher.setResearcherName(request.getResearcherName());
        researcher.setResearcherPhoneNumber(request.getResearcherPhoneNumber());
        researcher.setResearcherAddress(request.getResearcherAddress());
        researcher.setResearcherGrade(request.getResearcherGrade());
        researcher.setResearcherLabPhoneNumber(request.getResearcherLabPhoneNumber());
        mongoTemplate.save(researcher, "Researcher");
        return new ResponseEntity<>(researcher, HttpStatus.OK);
    }

    public ResponseEntity<Object> getResearcherSeedList(ResearchProfileRequest request) {
        List<SeedEntity> seedList = mongoTemplate.find(Query.query(Criteria.where("seedCompanyId").is(request.getResearcherId())), SeedEntity.class, "Seed");
        return new ResponseEntity<>(seedList, HttpStatus.OK);
    }
    public ResponseEntity<Object> getDashboard(String labId){
        int labIdInt = Integer.parseInt(labId);

        // 1. lab_seed 컬렉션에서 해당 연구소의 종자 목록 조회
        List<SeedResearchEntity> seeds = mongoTemplate.find(
                Query.query(Criteria.where("seedLabId").is(labIdInt)),
                SeedResearchEntity.class,
                "lab_seed"
        );

        if (seeds.isEmpty()) {
            return new ResponseEntity<>("등록된 연구 종자가 없습니다.", HttpStatus.NOT_FOUND);
        }

        // 2. 상태별 카운트 초기화 (0~4)
        Map<Integer, Integer> statusCountMap = new HashMap<>();
        for (int i = 0; i <= 4; i++) statusCountMap.put(i, 0);

        for (SeedResearchEntity seed : seeds) {
            int status = seed.getSeedResearchStatus();
            statusCountMap.put(status, statusCountMap.getOrDefault(status, 0) + 1);
        }

        // 3. 카테고리 라벨 정의
        List<String> categoryLabels = List.of("연구중", "모집 중", "연구완료", "연구실패", "연구중단");
        List<Integer> data = new ArrayList<>();
        for (int i = 0; i <= 4; i++) {
            data.add(statusCountMap.getOrDefault(i, 0));
        }

        // 4. DTO 구성
        lab_status_dashboard_data response = new lab_status_dashboard_data();
        response.setCategory(categoryLabels);
        response.setData(data);

        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    public ResponseEntity<Object> getResearcherSeedDetail(String labId) {
        int labIdInt = Integer.parseInt(labId);

        // 1. lab_seed에서 해당 연구소의 종자 목록 조회
        List<SeedResearchEntity> seeds = mongoTemplate.find(
                Query.query(Criteria.where("seedLabId").is(labIdInt)),
                SeedResearchEntity.class,
                "lab_seed"
        );

        if (seeds.isEmpty()) {
            return new ResponseEntity<>("해당 연구소의 연구 종자 정보가 없습니다.", HttpStatus.NOT_FOUND);
        }

        // 2. 각 Entity → DTO 매핑
        List<lab_seed_detail_data> responseList = seeds.stream().map(seed -> {
            lab_seed_detail_data dto = new lab_seed_detail_data();
            dto.setSeedId(seed.getSeedId());
            dto.setSeedManageId(seed.getSeedManageId());
            dto.setSeedUrl(seed.getSeedUrl());
            dto.setSeedLabId(seed.getSeedLabId());
            dto.setSeedLab(seed.getSeedLab());
            dto.setSeedLabUrl(seed.getSeedLabUrl());
            dto.setSeedName(seed.getSeedName());
            dto.setSeedTestPay(seed.getSeedTestPay());
            dto.setSeedCategory(seed.getSeedCategory());
            dto.setSeedImage(seed.getSeedImage());
            dto.setSeedResearchStatus(seed.getSeedResearchStatus());

            // SeedDesc
            lab_seed_detail_data.SeedDesc seedDesc = new lab_seed_detail_data().new SeedDesc();
            seedDesc.setTitle(seed.getSeedDesc().getTitle());
            seedDesc.setContent(seed.getSeedDesc().getContent());
            dto.setSeedDesc(seedDesc);

            // ResearchFarmer 리스트
            List<lab_seed_detail_data.ResearchFarmer> farmerList = seed.getSeedResearchFarmers().stream()
                    .map(f -> {
                        lab_seed_detail_data.ResearchFarmer farmer = new lab_seed_detail_data().new ResearchFarmer();
                        farmer.setFarmerId(f.getFarmerId());
                        farmer.setFarmerName(f.getFarmerName());
                        farmer.setFarmerPhone(f.getFarmerPhone());
                        farmer.setFarmerEmail(f.getFarmerEmail());
                        return farmer;
                    }).toList();
            dto.setSeedResearchFarmers(farmerList);

            // ResearchDiary 리스트
            List<lab_seed_detail_data.ResearchDiary> diaryList = seed.getSeedResearchDiarys().stream()
                    .map(d -> {
                        lab_seed_detail_data.ResearchDiary diary = new lab_seed_detail_data().new ResearchDiary();
                        diary.setSeedResearchFarmerId(d.getSeedResearchFarmerId());
                        diary.setSeedResearchDiaryId(d.getSeedResearchDiaryId());
                        diary.setSeedResearchDiaryWatering(d.getSeedResearchDiaryWatering());
                        diary.setSeedResearchDiaryFertilizer(d.getSeedResearchDiaryFertilizer());
                        diary.setSeedResearchDiarySunlight(d.getSeedResearchDiarySunlight());
                        diary.setSeedResearchDiaryTitle(d.getSeedResearchDiaryTitle());
                        diary.setSeedResearchDiaryContent(d.getSeedResearchDiaryContent());
                        diary.setSeedResearchDiaryImage(d.getSeedResearchDiaryImage());
                        return diary;
                    }).toList();
            dto.setSeedResearchDiarys(diaryList);

            return dto;
        }).toList();

        return new ResponseEntity<>(responseList, HttpStatus.OK);
    }

    public ResponseEntity<Object> updateResearcherSeed(String seedId, SeedEntity request) {
        SeedEntity seed = mongoTemplate.findOne(Query.query(Criteria.where("id").is(seedId)), SeedEntity.class, "Seed");
        if (seed == null) {
            return new ResponseEntity<>("종자 정보를 찾을 수 없습니다.", HttpStatus.NOT_FOUND);
        }
        seed.setSeedName(request.getSeedName());
        seed.setSeedCategory(request.getSeedCategory());
        seed.setSeedDesc(request.getSeedDesc());
        seed.setSeedPrice(request.getSeedPrice());
        mongoTemplate.save(seed, "Seed");
        return new ResponseEntity<>(seed, HttpStatus.OK);
    }
    public ResponseEntity<Object> getList(String labId){
        int labIdInt = Integer.parseInt(labId);

        // 1. lab_seed 컬렉션에서 해당 연구소의 종자 목록 조회
        List<SeedResearchEntity> seeds = mongoTemplate.find(
                Query.query(Criteria.where("seedLabId").is(labIdInt)),
                SeedResearchEntity.class,
                "lab_seed"
        );

        if (seeds.isEmpty()) {
            return new ResponseEntity<>("해당 연구소의 연구 종자 정보가 없습니다.", HttpStatus.NOT_FOUND);
        }

        // 2. Entity → DTO 변환
        List<lab_seed_list_data> responseList = seeds.stream().map(seed -> {
            lab_seed_list_data dto = new lab_seed_list_data();
            dto.setSeedId(seed.getSeedId());
            dto.setSeedManageId(seed.getSeedManageId());
            dto.setSeedUrl(seed.getSeedUrl());
            dto.setSeedLabId(seed.getSeedLabId());
            dto.setSeedLab(seed.getSeedLab());
            dto.setSeedLabUrl(seed.getSeedLabUrl());
            dto.setSeedName(seed.getSeedName());
            dto.setSeedTestPay(seed.getSeedTestPay());
            dto.setSeedCategory(seed.getSeedCategory());
            dto.setSeedImage(seed.getSeedImage());
            dto.setSeedResearchStatus(seed.getSeedResearchStatus());

            // SeedDesc
            lab_seed_list_data.SeedDesc desc = new lab_seed_list_data().new SeedDesc();
            desc.setTitle(seed.getSeedDesc().getTitle());
            desc.setContent(seed.getSeedDesc().getContent());
            dto.setSeedDesc(desc);

            // ResearchFarmers
            List<lab_seed_list_data.ResearchFarmer> farmerList = seed.getSeedResearchFarmers().stream().map(f -> {
                lab_seed_list_data.ResearchFarmer farmer = new lab_seed_list_data().new ResearchFarmer();
                farmer.setFarmerId(f.getFarmerId());
                farmer.setFarmerName(f.getFarmerName());
                farmer.setFarmerPhone(f.getFarmerPhone());
                farmer.setFarmerEmail(f.getFarmerEmail());
                return farmer;
            }).toList();
            dto.setSeedResearchFarmers(farmerList);

            // ResearchDiaries
            List<lab_seed_list_data.ResearchDiary> diaryList = seed.getSeedResearchDiarys().stream().map(d -> {
                lab_seed_list_data.ResearchDiary diary = new lab_seed_list_data().new ResearchDiary();
                diary.setSeedId(seed.getSeedId());
                diary.setSeedResearchFarmerId(d.getSeedResearchFarmerId());
                diary.setSeedResearchDiaryId(d.getSeedResearchDiaryId());
                diary.setSeedResearchDiaryWatering(d.getSeedResearchDiaryWatering());
                diary.setSeedResearchDiaryFertilizer(d.getSeedResearchDiaryFertilizer());
                diary.setSeedResearchDiarySunlight(d.getSeedResearchDiarySunlight());
                diary.setSeedResearchDiaryTitle(d.getSeedResearchDiaryTitle());
                diary.setSeedResearchDiaryContent(d.getSeedResearchDiaryContent());
                diary.setSeedResearchDiaryImage(d.getSeedResearchDiaryImage());
                return diary;
            }).toList();
            dto.setSeedResearchDiarys(diaryList);

            return dto;
        }).toList();

        return new ResponseEntity<>(responseList, HttpStatus.OK);

    }
    public LabResearchDetailResponse getResearcherResearchDetail(String postId) {
        PostEntity post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Research not found"));
        return convertToLabResearchDetailResponse(post);
    }

    private LabResearchDetailResponse convertToLabResearchDetailResponse(PostEntity post) {
        return LabResearchDetailResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .imageUrl(post.getImageUrl())
                .createdAt(post.getCreatedAt())
                .updatedAt(post.getUpdatedAt())
                .build();
    }
} 