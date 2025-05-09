package com.Dankook.FarmToYou.service;

import com.Dankook.FarmToYou.data.entity.SeedResearchEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Dankook.FarmToYou.data.dto.request.UserProfileEditRequest;
import com.Dankook.FarmToYou.data.dto.request.UserRequest;
import com.Dankook.FarmToYou.data.dto.response.UserParticipantsResponse;
import com.Dankook.FarmToYou.data.dto.response.UserProfileResponse;
import com.Dankook.FarmToYou.data.dto.response.UserPurchasedResponse;
import com.Dankook.FarmToYou.data.entity.MemberEntity;
import com.Dankook.FarmToYou.data.entity.PostEntity;
import com.Dankook.FarmToYou.data.dto.response.experience.ExpDetailResponse;
import com.Dankook.FarmToYou.data.dto.response.experience.ExpListResponse;
import com.Dankook.FarmToYou.data.entity.ExperienceEntity;
import com.Dankook.FarmToYou.repository.ExperienceRepository;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GeneralUserService {
    @Autowired
    MongoTemplate mongoTemplate;

    private final ExperienceRepository experienceRepository;

    public ResponseEntity<UserProfileResponse> userProfile(UserRequest userProfileRequest){
        MemberEntity memberEntity = mongoTemplate.findOne(Query.query(Criteria.where("id").is(userProfileRequest.getUserId())), MemberEntity.class,"Member");
        UserProfileResponse userProfileResponse = new UserProfileResponse();
        userProfileResponse.setMemberName(memberEntity.getMemberName());
        userProfileResponse.setMemberPhoneNumber(memberEntity.getMemberPhoneNumber());
        userProfileResponse.setMemberEmail(memberEntity.getMemberEmail());
        userProfileResponse.setMemberAddress(memberEntity.getMemberAddress());
        return new ResponseEntity<>(userProfileResponse,HttpStatus.OK);
    }

    public ResponseEntity<Object> userPurchased(UserRequest userProfileRequest){
        MemberEntity memberEntity = mongoTemplate.findOne(Query.query(Criteria.where("id").is(userProfileRequest.getUserId())), MemberEntity.class,"Member");
        UserPurchasedResponse userPurchasedResponse = new UserPurchasedResponse();
        userPurchasedResponse.setParchasedList(memberEntity.getParchasedList());
        return new ResponseEntity<>(userPurchasedResponse,HttpStatus.OK);
    }

    public ResponseEntity<Object> userParticipants(UserRequest userProfileRequest){
        MemberEntity memberEntity = mongoTemplate.findOne(Query.query(Criteria.where("id").is(userProfileRequest.getUserId())), MemberEntity.class,"Member");
        UserParticipantsResponse userParticipantsResponse = new UserParticipantsResponse();
        userParticipantsResponse.setMatchingList(memberEntity.getMatchingList());
        return new ResponseEntity<>(userParticipantsResponse,HttpStatus.OK);
    }

    public ResponseEntity<Object> getUserExperiments(UserRequest request) {
        MemberEntity member = mongoTemplate.findOne(Query.query(Criteria.where("memberId").is(request.getUserId())), MemberEntity.class, "Member");
        if (member == null) {
            return new ResponseEntity<>("사용자 정보를 찾을 수 없습니다.", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(member.getMatchingList(), HttpStatus.OK);
    }

    public ResponseEntity<Object> getUserExperimentDetail(String expId) {

        int id = Integer.parseInt(expId);

        ExperienceEntity entity = mongoTemplate.findOne(
                Query.query(Criteria.where("expId").is(id)),
                ExperienceEntity.class,
                "exp_booth"
        );

        if (entity == null) {
            return new ResponseEntity<>("체험 부스를 찾을 수 없습니다.", HttpStatus.NOT_FOUND);
        }

        ExpDetailResponse.ExpDesc desc = ExpDetailResponse.ExpDesc.builder()
                .title(entity.getExpDesc().getTitle())
                .content(entity.getExpDesc().getContent())
                .build();

        List<ExpDetailResponse.ExpParticipant> participantList = entity.getExpParticipants().stream()
                .map(p -> ExpDetailResponse.ExpParticipant.builder()
                        .expParticipantId(p.getExpParticipantId())
                        .expParticipantType(p.getExpParticipantType())
                        .expParticipantName(p.getExpParticipantName())
                        .expParticipantPhone(p.getExpParticipantPhone())
                        .expParticipantEmail(p.getExpParticipantEmail())
                        .expParticipantImage(p.getExpParticipantImage())
                        .expParticipantStatus(p.getExpParticipantStatus())
                        .expParticipantApplyDate(p.getExpParticipantApplyDate())
                        .build())
                .toList();

        List<ExpDetailResponse.Review> reviewList = entity.getExpReviews().stream()
                .map(r -> ExpDetailResponse.Review.builder()
                        .expId(entity.getExpId())
                        .expReviewId(r.getExpReviewId())
                        .getExpReviewId(r.getExpReviewId()) // 확인 필요: 이 필드는 중복일 가능성 있음
                        .expReviewerName(r.getExpReviewerName())
                        .expReviewRate(r.getExpReviewRate())
                        .expReviewImage(r.getExpReviewImage())
                        .expReviewDate(r.getExpReviewDate())
                        .expReviewDesc(ExpDetailResponse.Review.ExpReviewDesc.builder()
                                .title(r.getExpReviewDesc().getTitle())
                                .content(r.getExpReviewDesc().getContent())
                                .build())
                        .build())
                .toList();

        ExpDetailResponse response = ExpDetailResponse.builder()
                .expId(entity.getExpId())
                .expName(entity.getTitle())
                .expUrl(entity.getExpUrl()) // 또는 getExpUrl()?
                .expManager(entity.getExpManager())
                .expManagerUrl(entity.getExpManagerUrl())
                .expPrice(entity.getExpPrice())
                .expLocate(entity.getExpLocate())
                .expApplyStartDate(entity.getExpApplyStartDate())
                .expApplyEndDate(entity.getExpApplyEndDate())
                .expStartDate(entity.getExpStartDate())
                .expEndDate(entity.getExpEndDate())
                .expImage(entity.getExpImage())
                .expDesc(desc)
                .expStatus(entity.getExpStatus())
                .expParticipants(participantList)
                .reviews(reviewList)
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);

    }
    public ResponseEntity<Object> getList(String expId){
        int id = Integer.parseInt(expId);

        // 1. lab_seed에서 seedId == expId 조건으로 여러 건 조회 가능성
        List<SeedResearchEntity> seedEntities = mongoTemplate.find(
                Query.query(Criteria.where("seedId").is(id)),
                SeedResearchEntity.class,
                "lab_seed"
        );

        if (seedEntities.isEmpty()) {
            return new ResponseEntity<>("연구 데이터가 없습니다.", HttpStatus.NOT_FOUND);
        }

        // 2. exp_booth에서 expId = id인 부스 하나 조회
        ExperienceEntity expEntity = mongoTemplate.findOne(
                Query.query(Criteria.where("expId").is(id)),
                ExperienceEntity.class,
                "exp_booth"
        );

        if (expEntity == null) {
            return new ResponseEntity<>("체험 부스 정보가 없습니다.", HttpStatus.NOT_FOUND);
        }

        // 3. 결과 리스트 생성
        List<ExpListResponse> responseList = new ArrayList<>();

        for (SeedResearchEntity seedEntity : seedEntities) {

            ExpListResponse.SeedDesc seedDesc = ExpListResponse.SeedDesc.builder()
                    .title(seedEntity.getSeedDesc().getTitle())
                    .content(seedEntity.getSeedDesc().getContent())
                    .build();

            List<ExpListResponse.Diary> diaries = seedEntity.getSeedResearchDiarys().stream()
                    .map(d -> ExpListResponse.Diary.builder()
                            .seedId(seedEntity.getSeedId())
                            .seed_research_farmer_id(d.getSeedResearchFarmerId())
                            .seed_research_diary_id(d.getSeedResearchDiaryId())
                            .seed_research_diary_watering(d.getSeedResearchDiaryWatering())
                            .seed_research_diary_fertilizer(d.getSeedResearchDiaryFertilizer())
                            .seed_research_diary_sunlight(d.getSeedResearchDiarySunlight())
                            .seed_research_diary_title(d.getSeedResearchDiaryTitle())
                            .seed_research_diary_content(d.getSeedResearchDiaryContent())
                            .seed_research_diary_image(d.getSeedResearchDiaryImage())
                            .build())
                    .toList();

            ExpListResponse response = ExpListResponse.builder()
                    .seedId(seedEntity.getSeedId())
                    .seedManageId(seedEntity.getSeedManageId())
                    .seedUrl(expEntity.getExpUrl())
                    .seedLabId(seedEntity.getSeedLabId())
                    .seedLab(seedEntity.getSeedLab())
                    .seedLabUrl(seedEntity.getSeedLabUrl())
                    .seedName(seedEntity.getSeedName())
                    .seedTestPay(seedEntity.getSeedTestPay())
                    .seedCategory(seedEntity.getSeedCategory())
                    .seedImage(expEntity.getExpImage()) // 부스 이미지 재활용
                    .seedDesc(seedDesc)
                    .seedResearchStatus(seedEntity.getSeedResearchStatus())
                    .seedResearchDiarys(diaries)
                    .build();

            responseList.add(response);
        }

        return new ResponseEntity<>(responseList, HttpStatus.OK);

    }

    public ResponseEntity<Object> getGeneralProfileForEdit(UserRequest request) {
        MemberEntity member = mongoTemplate.findOne(Query.query(Criteria.where("memberId").is(request.getUserId())), MemberEntity.class, "Member");
        if (member == null) {
            return new ResponseEntity<>("사용자 정보를 찾을 수 없습니다.", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(member, HttpStatus.OK);
    }

    public ResponseEntity<Object> updateGeneralProfile(String userId, UserProfileEditRequest request) {
        MemberEntity member = mongoTemplate.findOne(Query.query(Criteria.where("memberId").is(userId)), MemberEntity.class, "Member");
        if (member == null) {
            return new ResponseEntity<>("사용자 정보를 찾을 수 없습니다.", HttpStatus.NOT_FOUND);
        }
        member.setMemberName(request.getUserName());
        member.setMemberPhoneNumber(request.getUserPhone());
        member.setMemberAddress(request.getUserAddress());
        mongoTemplate.save(member, "Member");
        return new ResponseEntity<>(member, HttpStatus.OK);
    }
/*
    public ExpDetailResponse getExperienceDetail(String expId) {
        ExperienceEntity experience = experienceRepository.findById(expId)
                .orElseThrow(() -> new RuntimeException("Experience not found"));
        return convertToExpDetailResponse(experience);
    }

    public List<ExpListResponse> getExperienceList() {
        List<ExperienceEntity> experiences = experienceRepository.findAll();
        return experiences.stream()
                .map(this::convertToExpListResponse)
                .collect(Collectors.toList());
    }
/*
    private ExpDetailResponse convertToExpDetailResponse(ExperienceEntity experience) {
        return ExpDetailResponse.builder()
                .id(experience.getExpId())
                .title(experience.getTitle())
                .content(experience.getContent())
                .imageUrl(experience.getImageUrl())
                .createdAt(experience.getCreatedAt())
                .updatedAt(experience.getUpdatedAt())
                .build();
    }
    private ExpListResponse convertToExpListResponse(ExperienceEntity experience) {
        return ExpListResponse.builder()
                .id(experience.getExpId())
                .title(experience.getTitle())
                .imageUrl(experience.getImageUrl())
                .createdAt(experience.getCreatedAt())
                .build();
    }*/
} 