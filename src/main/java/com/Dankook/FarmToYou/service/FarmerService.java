package com.Dankook.FarmToYou.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.Dankook.FarmToYou.data.entity.ExperienceEntity;
import com.Dankook.FarmToYou.data.entity.SeedResearchEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Dankook.FarmToYou.data.dto.request.FarmerExperimentPutRequest;
import com.Dankook.FarmToYou.data.dto.request.FarmerProfileEditRequest;
import com.Dankook.FarmToYou.data.dto.request.FarmerProfileRequest;
import com.Dankook.FarmToYou.data.dto.response.farmer.FarmerProfileResponse;
import com.Dankook.FarmToYou.data.dto.response.farmer.FarmerResearchDetailResponse;
import com.Dankook.FarmToYou.data.dto.response.farmer.FarmerResearchListResponse;
import com.Dankook.FarmToYou.data.entity.FarmerEntity;
import com.Dankook.FarmToYou.data.entity.PostEntity;
import com.Dankook.FarmToYou.repository.FarmerRepository;
import com.Dankook.FarmToYou.repository.PostRepository;

@Service
public class FarmerService {
    @Autowired
    MongoTemplate mongoTemplate;

    private final FarmerRepository farmerRepository;
    private final PostRepository postRepository;

    public FarmerService(FarmerRepository farmerRepository, PostRepository postRepository) {
        this.farmerRepository = farmerRepository;
        this.postRepository = postRepository;
    }

    public ResponseEntity<Object> getFarmerProfileForEdit(FarmerProfileRequest request) {
        FarmerEntity farmer = mongoTemplate.findOne(Query.query(Criteria.where("farmerId").is(request.getFarmerId())), FarmerEntity.class, "Farmer");
        if (farmer == null) {
            return new ResponseEntity<>("농부 정보를 찾을 수 없습니다.", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(farmer, HttpStatus.OK);
    }

    public ResponseEntity<Object> updateFarmerProfile(String farmerId, FarmerProfileEditRequest request) {
        FarmerEntity farmer = mongoTemplate.findOne(Query.query(Criteria.where("farmerId").is(farmerId)), FarmerEntity.class, "Farmer");
        if (farmer == null) {
            return new ResponseEntity<>("농부 정보를 찾을 수 없습니다.", HttpStatus.NOT_FOUND);
        }
        farmer.setName(request.getFarmerName());
        farmer.setPhone(request.getFarmerPhone());
        farmer.setAddress(request.getFarmerAddress());
        mongoTemplate.save(farmer, "Farmer");
        return new ResponseEntity<>(farmer, HttpStatus.OK);
    }

    public ResponseEntity<Object> getFarmerExperiments(FarmerProfileRequest request) {
        List<PostEntity> posts = mongoTemplate.find(Query.query(Criteria.where("writerId").is(request.getFarmerId())), PostEntity.class, "Post");
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    public ResponseEntity<Object> getFarmerResearchDetailByFarmerId(String farmerId) {
        int farmer_id = Integer.parseInt(farmerId);

        // lab_seed 전체 탐색 후 farmerId가 포함된 diary만 필터링
        List<SeedResearchEntity> allSeeds = mongoTemplate.findAll(SeedResearchEntity.class, "lab_seed");

        List<FarmerResearchDetailResponse> responseList = new ArrayList<>();

        for (SeedResearchEntity entity : allSeeds) {
            List<SeedResearchEntity.SeedResearchDiary> matchedDiaries = entity.getSeedResearchDiarys().stream()
                    .filter(d -> d.getSeedResearchFarmerId().equals(farmer_id))
                    .toList();

            if (matchedDiaries.isEmpty()) continue; // 참여 안한 연구는 제외

            List<FarmerResearchDetailResponse.SeedResearchDiary> dtoDiaries = matchedDiaries.stream()
                    .map(d -> FarmerResearchDetailResponse.SeedResearchDiary.builder()
                            .seedId(entity.getSeedId())
                            .seedResearchFarmerId(d.getSeedResearchFarmerId())
                            .seedResearchDiaryId(d.getSeedResearchDiaryId())
                            .seedResearchDiaryWatering(d.getSeedResearchDiaryWatering())
                            .seedResearchDiaryFertilizer(d.getSeedResearchDiaryFertilizer())
                            .seedResearchDiarySunlight(d.getSeedResearchDiarySunlight())
                            .seedResearchDiaryTitle(d.getSeedResearchDiaryTitle())
                            .seedResearchDiaryContent(d.getSeedResearchDiaryContent())
                            .seedResearchDiaryImage(d.getSeedResearchDiaryImage())
                            .build())
                    .toList();

            FarmerResearchDetailResponse res = FarmerResearchDetailResponse.builder()
                    .seedId(entity.getSeedId())
                    .seedManageId(entity.getSeedManageId())
                    .seedUrl(entity.getSeedUrl())
                    .seedLabId(entity.getSeedLabId())
                    .seedLab(entity.getSeedLab())
                    .seedLabUrl(entity.getSeedLabUrl())
                    .seedName(entity.getSeedName())
                    .seedTestPay(entity.getSeedTestPay())
                    .seedCategory(entity.getSeedCategory())
                    .seedImage(entity.getSeedImage())
                    .seedDesc(FarmerResearchDetailResponse.SeedDesc.builder()
                            .title(entity.getSeedDesc().getTitle())
                            .content(entity.getSeedDesc().getContent())
                            .build())
                    .seedResearchStatus(entity.getSeedResearchStatus())
                    .seedResearchDiarys(dtoDiaries)
                    .build();

            responseList.add(res);
        }

        if (responseList.isEmpty()) {
            return new ResponseEntity<>("해당 농부가 참여한 연구가 없습니다.", HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(responseList, HttpStatus.OK);
    }

    public ResponseEntity<Object> getList(String farmerId){
        int farmer_id = Integer.parseInt(farmerId);

        // lab_seed 컬렉션 전체에서 해당 농부가 작성한 일지 필터링
        List<SeedResearchEntity> allSeeds = mongoTemplate.findAll(SeedResearchEntity.class, "lab_seed");

        List<FarmerResearchListResponse> result = new ArrayList<>();

        for (SeedResearchEntity entity : allSeeds) {
            List<SeedResearchEntity.SeedResearchDiary> matchedDiaries = entity.getSeedResearchDiarys().stream()
                    .filter(d -> d.getSeedResearchFarmerId().equals(farmer_id))
                    .toList();

            if (matchedDiaries.isEmpty()) continue;

            // DTO 변환
            List<FarmerResearchListResponse.SeedResearchDiary> diaryList = matchedDiaries.stream()
                    .map(diary -> {
                        FarmerResearchListResponse.SeedResearchDiary dto = new FarmerResearchListResponse().new SeedResearchDiary();
                        dto.setSeedId(entity.getSeedId());
                        dto.setSeedResearchFarmerId(diary.getSeedResearchFarmerId());
                        dto.setSeedResearchDiary_watering(diary.getSeedResearchDiaryWatering());
                        dto.setSeedResearchDiaryFertilizer(diary.getSeedResearchDiaryFertilizer());
                        dto.setSeedResearch_diarySunlight(diary.getSeedResearchDiarySunlight());
                        dto.setSeedResearchDiaryTitle(diary.getSeedResearchDiaryTitle());
                        dto.setSeedResearchDiaryContent(diary.getSeedResearchDiaryContent());
                        dto.setSeedResearchDiary_image(diary.getSeedResearchDiaryImage());
                        return dto;
                    }).toList();

            FarmerResearchListResponse.SeedDesc desc = new FarmerResearchListResponse().new SeedDesc();
            desc.setTitle(entity.getSeedDesc().getTitle());
            desc.setContent(entity.getSeedDesc().getContent());

            FarmerResearchListResponse response = FarmerResearchListResponse.builder()
                    .seedId(entity.getSeedId())
                    .seed_ManageId(entity.getSeedManageId())
                    .seedUrl(entity.getSeedUrl())
                    .seedLabId(entity.getSeedLabId())
                    .seedLab(entity.getSeedLab())
                    .seedLabUrl(entity.getSeedLabUrl())
                    .seedName(entity.getSeedName())
                    .seedTestPay(entity.getSeedTestPay())
                    .seedCategory(entity.getSeedCategory())
                    .seedImage(entity.getSeedImage())
                    .seedDesc(desc)
                    .seedResearchStatus(entity.getSeedResearchStatus())
                    .seedResearchDiaries(diaryList)
                    .build();

            result.add(response);
        }

        if (result.isEmpty()) {
            return new ResponseEntity<>("해당 농부의 연구 데이터가 없습니다.", HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(result, HttpStatus.OK);

    }



    public ResponseEntity<Object> putFarmerExperiment(FarmerExperimentPutRequest request) {
        PostEntity post = PostEntity.builder()
            .title(request.getExpName())
            .content(request.getExpDetail())
            .imageUrl(request.getExpPic())
            .farmerId(null) // 실제 farmerId를 할당하려면 request에 추가 필요
            .build();
        mongoTemplate.insert(post, "Post");
        return new ResponseEntity<>(post, HttpStatus.OK);
    }

    public ResponseEntity<Object> getFarmerLabs(FarmerProfileRequest request) {
        return new ResponseEntity<>("참여 연구 리스트 (구현 필요)", HttpStatus.OK);
    }

    public ResponseEntity<Object> getFarmerLabexDetail(String labexId) {
        return new ResponseEntity<>("연구 상세 (구현 필요)", HttpStatus.OK);
    }

    public ResponseEntity<Object> getFarmerLabexAddDiary(String labexId) {
        return new ResponseEntity<>("일지 작성 화면 (구현 필요)", HttpStatus.OK);
    }

    public FarmerProfileResponse getFarmerProfile(String farmerId) {
        FarmerEntity farmer = farmerRepository.findById(farmerId)
                .orElseThrow(() -> new RuntimeException("Farmer not found"));
        return convertToFarmerProfileResponse(farmer);
    }

/*
    public List<FarmerResearchListResponse> getFarmerResearchList(String farmerId) {
        List<PostEntity> posts = postRepository.findByFarmerId(farmerId);
        return posts.stream()
                .map(this::convertToFarmerResearchListResponse)
                .collect(Collectors.toList());
    }*/

    private FarmerProfileResponse convertToFarmerProfileResponse(FarmerEntity farmer) {
        return FarmerProfileResponse.builder()
                .id(farmer.getId())
                .name(farmer.getName())
                .email(farmer.getEmail())
                .phone(farmer.getPhone())
                .address(farmer.getAddress())
                .build();
    }
/*
    private FarmerResearchDetailResponse convertToFarmerResearchDetailResponse(PostEntity post) {
        return FarmerResearchDetailResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .imageUrl(post.getImageUrl())
                .createdAt(post.getCreatedAt())
                .updatedAt(post.getUpdatedAt())
                .build();
    }*/
/*
    private FarmerResearchListResponse convertToFarmerResearchListResponse(PostEntity post) {
        return FarmerResearchListResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .imageUrl(post.getImageUrl())
                .createdAt(post.getCreatedAt())
                .build();
    }*/
} 