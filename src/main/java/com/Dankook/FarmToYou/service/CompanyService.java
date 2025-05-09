package com.Dankook.FarmToYou.service;

import java.time.LocalDate;
import java.time.Year;
import java.util.*;
import java.util.stream.Collectors;

import com.Dankook.FarmToYou.data.dto.SeedEditInfo;
import com.Dankook.FarmToYou.data.dto.response.company.*;
import com.Dankook.FarmToYou.data.entity.ExperienceEntity;
import com.Dankook.FarmToYou.data.entity.SeedResearchEntity;
import com.Dankook.FarmToYou.data.entity.SeedTransactionEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Dankook.FarmToYou.data.dto.request.CompanyAllSeedRequest;
import com.Dankook.FarmToYou.data.dto.request.CompanyProfileRequest;
import com.Dankook.FarmToYou.data.dto.request.CompanySeedRegister;
import com.Dankook.FarmToYou.data.dto.response.CompanyAllSeedResponse;
import com.Dankook.FarmToYou.data.dto.response.CompanyProfileResponse;
import com.Dankook.FarmToYou.data.dto.response.CompanySeedResponse;
import com.Dankook.FarmToYou.data.entity.SeedEntity;

@Service
public class CompanyService {
    @Autowired
    MongoTemplate mongoTemplate;

    public ResponseEntity<Object> companyProfile(CompanyProfileRequest companyProfileRequest){
        List<SeedEntity> seedList = mongoTemplate.find(Query.query(Criteria.where("seedCompanyId").is(companyProfileRequest.getCompanyId())),SeedEntity.class,"Seed");
        CompanyProfileResponse companyProfileResponse = new CompanyProfileResponse();
        Map<String,Integer> category = new HashMap();
        for(SeedEntity seedEntity : seedList) {
            String seedCategory = seedEntity.getSeedCategory();
            if (category.containsKey(seedCategory)) {
                category.put(seedCategory, category.get(seedCategory) + 1);
            } else {
                category.put(seedCategory, 1);
            }
        }
        for(SeedEntity seedEntity : seedList){
            companyProfileResponse.setSeedByCategory(category);
        }
        return new ResponseEntity<>(companyProfileResponse,HttpStatus.OK);
    }

    public ResponseEntity<Object> companySeedRegister(CompanySeedRegister companySeedRegister){
        CompanySeedResponse companySeedResponse = new CompanySeedResponse();
        SeedEntity seedEntity = SeedEntity.builder()
                .seedCompanyId(companySeedRegister.getCompanyId() != null ? Integer.parseInt(companySeedRegister.getCompanyId()) : 0)
                .seedPrice(companySeedRegister.getPrice())
                .seedName(companySeedRegister.getName())
                .seedCategory(companySeedRegister.getCategory())
                .seedDesc(null)
                .build();
        mongoTemplate.insert(seedEntity);
        companySeedResponse.setPrice(seedEntity.getSeedPrice());
        companySeedResponse.setName(seedEntity.getSeedName());
        companySeedResponse.setCategory(seedEntity.getSeedCategory());
        return new ResponseEntity<>(companySeedResponse,HttpStatus.OK);
    }

    public ResponseEntity<Object> companyAllSeed(CompanyAllSeedRequest companyAllSeedRequest){
        List<CompanyAllSeedResponse> companyAllSeedResponseList = new ArrayList<>();
        List<SeedEntity> seedEntityList = mongoTemplate.find(Query.query(Criteria.where("seedCompanyId").is(companyAllSeedRequest.getCompanyId())),SeedEntity.class,"Seed");
        for(SeedEntity seedEntity : seedEntityList){
            CompanyAllSeedResponse companyAllSeedResponse = new CompanyAllSeedResponse();
            companyAllSeedResponse.setPrice(seedEntity.getSeedPrice());
            companyAllSeedResponse.setName(seedEntity.getSeedName());
            companyAllSeedResponse.setCategory(seedEntity.getSeedCategory());
            companyAllSeedResponse.setExplain(seedEntity.getSeedDesc());
            companyAllSeedResponse.setNumberOfSeed(null);
            companyAllSeedResponseList.add(companyAllSeedResponse);
        }
        return new ResponseEntity<>(companyAllSeedResponseList,HttpStatus.OK);
    }

    public ResponseEntity<Object> getList(String companyId) {
        int cid = Integer.parseInt(companyId);

        // ✅ companyId에 해당하는 모든 종자 조회
        List<SeedEntity> entities = mongoTemplate.find(
                Query.query(Criteria.where("seedCompanyId").is(cid)),
                SeedEntity.class,
                "company_seed"
        );

        if (entities.isEmpty()) {
            return new ResponseEntity<>("해당 회사의 종자를 찾을 수 없습니다.", HttpStatus.NOT_FOUND);
        }

        // ✅ 각 SeedEntity → DTO 변환
        List<company_seed_list_data> resultList = entities.stream().map(entity -> {
            company_seed_list_data.SeedDesc seedDesc = company_seed_list_data.SeedDesc.builder()
                    .title(entity.getSeedDesc().getTitle())
                    .content(entity.getSeedDesc().getContent())
                    .build();

            List<company_seed_list_data.SeedReview> reviews = entity.getSeedReviews().stream()
                    .map(r -> company_seed_list_data.SeedReview.builder()
                            .seedId(entity.getSeedId())
                            .seedReviewId(r.getSeedReviewId())
                            .seedReviewerId(r.getSeedReviewerId())
                            .seedReviewerName(r.getSeedReviewerName())
                            .seedReviewRate(r.getSeedReviewRate())
                            .seedReviewDate(r.getSeedReviewDate().toString())
                            .seedReviewImage(r.getSeedReviewImage())
                            .seedReviewDesc(company_seed_list_data.SeedReviewDesc.builder()
                                    .title("리뷰")
                                    .content(r.getSeedReviewContent())
                                    .build())
                            .build())
                    .toList();

            return company_seed_list_data.builder()
                    .seed_id(entity.getSeedId())
                    .seed_manage_id(entity.getSeedManageId())
                    .seed_url(entity.getSeedUrl())
                    .seed_company_id(entity.getSeedCompanyId())
                    .seed_company(entity.getSeedCompany())
                    .seed_company_url(entity.getSeedCompanyUrl())
                    .seed_name(entity.getSeedName())
                    .seed_price(entity.getSeedPrice())
                    .seed_category(entity.getSeedCategory())
                    .seed_image(entity.getSeedImage())
                    .seed_rate(entity.getSeedRate())
                    .seedDesc(seedDesc)
                    .seed_cost(entity.getSeedCost())
                    .seed_total_sales(entity.getSeedTotalSales())
                    .seedReviews(reviews)
                    .build();
        }).toList();

        return new ResponseEntity<>(resultList, HttpStatus.OK);
    }

    private company_seed_transaction_detail_data.UserData mapToUserData(SeedTransactionEntity.UserData source) {
        if (source == null) return null;

        return company_seed_transaction_detail_data.UserData.builder()
                .user_id(source.getUserId())
                .user_type(source.getUserType())
                .user_name(source.getUserName())
                .user_phone(source.getUserPhone())
                .user_email(source.getUserEmail())
                .user_address(source.getUserAddress())
                .user_address_detail(source.getUserAddressDetail())
                .build();
    }


    public ResponseEntity<Object> getTransaction(String companyId){
        int companyIdInt = Integer.parseInt(companyId);

        // 1. 해당 회사의 종자들 조회
        List<SeedEntity> seeds = mongoTemplate.find(
                Query.query(Criteria.where("seedCompanyId").is(companyIdInt)),
                SeedEntity.class,
                "company_seed"
        );

        if (seeds.isEmpty()) {
            return new ResponseEntity<>("해당 회사의 종자가 없습니다.", HttpStatus.NOT_FOUND);
        }

        Set<Integer> seedIds = seeds.stream()
                .map(SeedEntity::getSeedId)
                .collect(Collectors.toSet());

        // 2. 해당 seedId들을 기반으로 거래 목록 조회
        List<SeedTransactionEntity> transactions = mongoTemplate.find(
                Query.query(Criteria.where("seedId").in(seedIds)),
                SeedTransactionEntity.class,
                "company_seed_transaction"
        );

        if (transactions.isEmpty()) {
            return new ResponseEntity<>("거래 내역이 없습니다.", HttpStatus.NOT_FOUND);
        }

        // 3. 거래 → DTO 매핑
        List<company_seed_transaction_detail_data> responseList = transactions.stream()
                .map(tx -> {
                    company_seed_transaction_detail_data.UserData mappedUserData = mapToUserData(tx.getUserData());

                    return company_seed_transaction_detail_data.builder()
                            .seed_transaction_id(tx.getSeedTransactionId())
                            .seed_id(tx.getSeedId())
                            .seed_sales(tx.getSeedSales())
                            .seed_sales_date(tx.getSeedSalesDate())
                            .seed_total_pay(0) // seed 가격 계산 시 seed 리스트에서 꺼내 계산 가능
                            .getSeed_transaction_status(tx.getSeedTransactionStatus())
                            .seed_transaction_payment("카드") // 실제 결제 방식 있으면 반영
                            .userData(mappedUserData)
                            .build();
                })
                .toList();

        return new ResponseEntity<>(responseList, HttpStatus.OK);
    }
    public ResponseEntity<Object> getTransactionList(String companyId){
        int cid = Integer.parseInt(companyId);

        // 1. 해당 회사의 종자 목록 조회
        List<SeedEntity> seeds = mongoTemplate.find(
                Query.query(Criteria.where("seedCompanyId").is(cid)),
                SeedEntity.class,
                "company_seed"
        );

        if (seeds.isEmpty()) {
            return new ResponseEntity<>("해당 회사에 등록된 종자가 없습니다.", HttpStatus.NOT_FOUND);
        }

        Set<Integer> seedIds = seeds.stream()
                .map(SeedEntity::getSeedId)
                .collect(Collectors.toSet());

        // 2. seedId 목록을 기준으로 거래 목록 조회
        List<SeedTransactionEntity> transactions = mongoTemplate.find(
                Query.query(Criteria.where("seedId").in(seedIds)),
                SeedTransactionEntity.class,
                "company_seed_transaction"
        );

        if (transactions.isEmpty()) {
            return new ResponseEntity<>("거래 내역이 없습니다.", HttpStatus.NOT_FOUND);
        }

        // 3. 거래 → DTO 매핑
        List<company_seed_transaction_list_data> responseList = transactions.stream()
                .map(tx -> {
                    SeedTransactionEntity.UserData source = tx.getUserData();
                    company_seed_transaction_list_data.UserData userData = null;

                    if (source != null) {
                        userData = company_seed_transaction_list_data.UserData.builder()
                                .user_id(source.getUserId())
                                .user_type(source.getUserType())
                                .user_name(source.getUserName())
                                .user_phone(source.getUserPhone())
                                .user_email(source.getUserEmail())
                                .user_address(source.getUserAddress())
                                .user_address_detail(source.getUserAddressDetail())
                                .build();
                    }

                    return company_seed_transaction_list_data.builder()
                            .seed_transaction_id(tx.getSeedTransactionId())
                            .seed_id(tx.getSeedId())
                            .seed_sales(tx.getSeedSales())
                            .seed_sales_date(tx.getSeedSalesDate())
                            .seed_transaction_status(tx.getSeedTransactionStatus())
                            .seed_transaction_payment("카드")
                            .userData(userData)
                            .build();
                })
                .toList();

        return new ResponseEntity<>(responseList, HttpStatus.OK);

    }
    public ResponseEntity<Object> getSaleDashBoard(String companyId){
        int cid = Integer.parseInt(companyId);
        int currentYear = Year.now().getValue();

        // 1. companyId가 일치하는 종자들 조회
        List<SeedEntity> seeds = mongoTemplate.find(
                Query.query(Criteria.where("seedCompanyId").is(cid)),
                SeedEntity.class,
                "company_seed"
        );

        if (seeds.isEmpty()) {
            return new ResponseEntity<>("등록된 종자가 없습니다.", HttpStatus.NOT_FOUND);
        }

        Set<Integer> seedIds = seeds.stream().map(SeedEntity::getSeedId).collect(Collectors.toSet());

        // 2. seedId 리스트로 거래 목록 조회
        List<SeedTransactionEntity> transactions = mongoTemplate.find(
                Query.query(Criteria.where("seedId").in(seedIds)),
                SeedTransactionEntity.class,
                "company_seed_transaction"
        );

        // 3. 월별 합계 계산
        Map<Integer, Integer> salesPerMonth = new HashMap<>();
        for (int i = 1; i <= 12; i++) salesPerMonth.put(i, 0);

        for (SeedTransactionEntity tx : transactions) {
            LocalDate date = tx.getSeedSalesDate();
            if (date.getYear() != currentYear) continue; // 현재 연도만

            int month = date.getMonthValue();
            int seedPrice = seeds.stream()
                    .filter(s -> s.getSeedId() == tx.getSeedId())
                    .map(SeedEntity::getSeedPrice)
                    .findFirst().orElse(0);

            int total = salesPerMonth.getOrDefault(month, 0);
            salesPerMonth.put(month, total + seedPrice * tx.getSeedSales());
        }

        // 4. 응답 DTO 구성
        List<Integer> data = new ArrayList<>();
        List<String> months = new ArrayList<>();
        for (int i = 1; i <= 12; i++) {
            data.add(salesPerMonth.getOrDefault(i, 0));
            months.add(i + "월");
        }

        company_sales_dashboard_data response = new company_sales_dashboard_data();
        response.setData(data);
        response.setMonth(months);

        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    public ResponseEntity<Object> editSeed(String seedId){
        int id = Integer.parseInt(seedId);

        // MongoDB 조회
        SeedEntity seed = mongoTemplate.findOne(
                Query.query(Criteria.where("seedId").is(id)),
                SeedEntity.class,
                "company_seed"
        );

        if (seed == null) {
            return new ResponseEntity<>("해당 종자를 찾을 수 없습니다.", HttpStatus.NOT_FOUND);
        }

        // DTO 변환
        SeedEditInfo info = SeedEditInfo.builder()
                .seedName(seed.getSeedName())
                .category(seed.getSeedCategory())
                .title(seed.getSeedDesc() != null ? seed.getSeedDesc().getTitle() : "")
                .seedNumber(String.valueOf(seed.getSeedId())) // seedNumber는 따로 없으면 seedId를 문자열로
                .price(seed.getSeedPrice() != null ? seed.getSeedPrice() : 0)
                .content(seed.getSeedDesc() != null ? seed.getSeedDesc().getContent() : "")
                .build();

        return new ResponseEntity<>(info, HttpStatus.OK);

    }

    public ResponseEntity<Object> getCategoryDashBoard(String companyId){
        int cid = Integer.parseInt(companyId);

        // 1. 해당 회사의 종자들 조회
        List<SeedEntity> seeds = mongoTemplate.find(
                Query.query(Criteria.where("seedCompanyId").is(cid)),
                SeedEntity.class,
                "company_seed"
        );

        if (seeds.isEmpty()) {
            return new ResponseEntity<>("등록된 종자가 없습니다.", HttpStatus.NOT_FOUND);
        }

        // 2. 카테고리별 개수 집계
        Map<String, Integer> categoryCountMap = new HashMap<>();
        for (SeedEntity seed : seeds) {
            String category = seed.getSeedCategory();
            categoryCountMap.put(category, categoryCountMap.getOrDefault(category, 0) + 1);
        }

        // 3. DTO 구성
        company_category_dashboard_data response = new company_category_dashboard_data();
        response.setCategory(new ArrayList<>(categoryCountMap.keySet()));
        response.setData(new ArrayList<>(categoryCountMap.values()));

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    public ResponseEntity<Object> addNewSeed(CompanySeedRegister request) {
        return new ResponseEntity<>("신규 종자 등록 (구현 필요)", HttpStatus.OK);
    }
    public ResponseEntity<Object> getCompanyPrevRegSeedList(CompanyProfileRequest request) {
        return new ResponseEntity<>("이전 등록 종자 리스트 (구현 필요)", HttpStatus.OK);
    }
    private company_seed_detail_data convertToCompanySeedDetailData(SeedEntity seed) {
        company_seed_detail_data.SeedDesc seedDesc = null;
        if (seed.getSeedDesc() != null) {
            seedDesc = company_seed_detail_data.SeedDesc.builder()
                    .title(seed.getSeedDesc().getTitle())
                    .content(seed.getSeedDesc().getContent())
                    .build();
        }

        List<company_seed_detail_data.SeedReview> seedReviews = null;
        if (seed.getSeedReviews() != null) {
            seedReviews = seed.getSeedReviews().stream()
                    .map(r -> company_seed_detail_data.SeedReview.builder()
                            .seedId(seed.getSeedId())
                            .seedReviewId(r.getSeedReviewId())
                            .seedReviewerId(r.getSeedReviewerId())
                            .seedReviewerName(r.getSeedReviewerName())
                            .seedReviewContent(r.getSeedReviewContent())
                            .seedReviewRate(r.getSeedReviewRate())
                            .seedReviewImage(r.getSeedReviewImage())
                            .seedReviewDate(
                                    r.getSeedReviewDate() != null ?
                                            r.getSeedReviewDate().toString() :
                                            null
                            )
                            .build())
                    .toList();
        }

        return company_seed_detail_data.builder()
                .seed_id(seed.getSeedId())
                .seed_manage_id(seed.getSeedManageId() != null ? seed.getSeedManageId() : 0)
                .seed_url(seed.getSeedUrl())
                .seed_company_id(seed.getSeedCompanyId() != null ? seed.getSeedCompanyId() : 0)
                .seed_company(seed.getSeedCompany())
                .seed_company_url(seed.getSeedCompanyUrl())
                .seed_name(seed.getSeedName())
                .seed_price(seed.getSeedPrice() != null ? seed.getSeedPrice() : 0)
                .seed_category(seed.getSeedCategory())
                .seed_image(seed.getSeedImage())
                .seed_rate(seed.getSeedRate() != null ? seed.getSeedRate() : 0)
                .seedDesc(seedDesc)
                .seed_cost(seed.getSeedCost() != null ? seed.getSeedCost() : 0)
                .seed_total_sales(seed.getSeedTotalSales() != null ? seed.getSeedTotalSales() : 0)
                .seedReviews(seedReviews)
                .build();
    }
    public ResponseEntity<Object> editDetailSeedInfo(String seedId, SeedEditInfo seedEditInfo){
        int id = Integer.parseInt(seedId);

        // 1. 해당 종자 데이터 조회
        SeedEntity seed = mongoTemplate.findOne(
                Query.query(Criteria.where("seedId").is(id)),
                SeedEntity.class,
                "company_seed"
        );

        if (seed == null) {
            return new ResponseEntity<>("해당 종자 정보를 찾을 수 없습니다.", HttpStatus.NOT_FOUND);
        }

        // 2. 필드 수정
        seed.setSeedName(seedEditInfo.getSeedName());
        seed.setSeedCategory(seedEditInfo.getCategory());
        seed.setSeedPrice(seedEditInfo.getPrice());

        // seedDesc가 null일 경우 새로 생성
        if (seed.getSeedDesc() == null) {
            seed.setSeedDesc(new SeedEntity.SeedDesc());
        }
        seed.getSeedDesc().setContent(seedEditInfo.getContent());

        // 3. 저장 (업데이트)
        mongoTemplate.save(seed, "company_seed");

        return new ResponseEntity<>(seedEditInfo, HttpStatus.OK);


    }

    public ResponseEntity<Object> getCompanyPrevRegSeedDetail(String seedId) {
        int seed_id = Integer.parseInt(seedId);

        // seedId로 종자 정보 조회
        SeedEntity seed = mongoTemplate.findOne(
                Query.query(Criteria.where("seedId").is(seed_id)),
                SeedEntity.class,
                "company_seed"
        );

        if (seed == null) {
            return new ResponseEntity<>("해당 종자 정보를 찾을 수 없습니다.", HttpStatus.NOT_FOUND);
        }

        // DTO 변환
        company_seed_detail_data dto = convertToCompanySeedDetailData(seed);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

/*
    private company_seed_detail_data convertToCompanySeedDetailData(SeedEntity seed) {
        company_seed_detail_data dto = new company_seed_detail_data();
        // 필드 매핑 (예시, 실제 setter/getter 필요)
        // dto.setSeed_id(seed.getSeedId());
        // ... 나머지 필드 매핑
        return dto;
    }*/
}