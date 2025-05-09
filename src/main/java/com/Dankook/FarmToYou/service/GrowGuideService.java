package com.Dankook.FarmToYou.service;

import com.Dankook.FarmToYou.data.entity.DiaryEntity;
import com.Dankook.FarmToYou.data.entity.SeedResearchEntity;
import com.Dankook.FarmToYou.data.request.GptUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GrowGuideService {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private MakeHttpHeader makeHttpHeader;

    @Autowired
    private Environment env;

    private RestTemplate restTemplate;

    public ResponseEntity<Object> makeGrowGuide(String farmerId, String seedId) {
        restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = makeHttpHeader.makeHeaderRequest();

        int seedIdInt = Integer.parseInt(seedId);
        int farmerIdInt = Integer.parseInt(farmerId);

        // 1. lab_seed 컬렉션에서 seedId로 찾기
        SeedResearchEntity entity = mongoTemplate.findOne(
                Query.query(Criteria.where("seedId").is(seedIdInt)),
                SeedResearchEntity.class,
                "lab_seed"
        );

        if (entity == null) {
            return new ResponseEntity<>("해당 종자 연구 정보를 찾을 수 없습니다.", HttpStatus.NOT_FOUND);
        }

        // 2. farmerId에 해당하는 다이어리만 필터링
        List<SeedResearchEntity.SeedResearchDiary> diaryList = entity.getSeedResearchDiarys().stream()
                .filter(d -> d.getSeedResearchFarmerId().equals(farmerIdInt))
                .toList();

        if (diaryList.isEmpty()) {
            return new ResponseEntity<>("해당 농부의 연구 일지가 존재하지 않습니다.", HttpStatus.NOT_FOUND);
        }

        // 3. 프롬프트 구성
        StringBuilder promptBuilder = new StringBuilder();
        promptBuilder.append(GptUtils.info);

        for (SeedResearchEntity.SeedResearchDiary diary : diaryList) {
            promptBuilder.append(GptUtils.seedName).append(entity.getSeedName()).append("\n");
            promptBuilder.append(GptUtils.researchDate).append(diary.getSeedResearchDiaryTitle()).append("\n");
            promptBuilder.append(GptUtils.content).append(diary.getSeedResearchDiaryContent()).append("\n");
            promptBuilder.append(GptUtils.imageUrl)
                    .append(String.join(", ", diary.getSeedResearchDiaryImage()))
                    .append("\n");
        }

        String prompt = promptBuilder.toString();
        Map<String, Object> requestBody = makeHttpHeader.makeRequestBodyService(prompt);
        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestBody, httpHeaders);

        // 4. GPT API 요청
        ResponseEntity<Map> response = restTemplate.exchange(
                env.getProperty("gpt.api.text.generation"),
                HttpMethod.POST,
                requestEntity,
                Map.class
        );

        List<Map<String, Object>> outputList = (List<Map<String, Object>>) response.getBody().get("output");
        List<Map<String, Object>> contentList = (List<Map<String, Object>>) outputList.get(0).get("content");

        String resultText = contentList.get(0).get("text").toString();

        return new ResponseEntity<>(resultText, HttpStatus.OK);
    }

}
