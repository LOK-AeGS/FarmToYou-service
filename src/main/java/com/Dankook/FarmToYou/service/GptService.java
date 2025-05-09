package com.Dankook.FarmToYou.service;

import com.Dankook.FarmToYou.data.dto.gptapi.TextGenerationResponse;
import com.Dankook.FarmToYou.data.dto.request.TextReceive;
import com.Dankook.FarmToYou.data.dto.response.TextResponse;
import com.Dankook.FarmToYou.data.entity.GptImageCollection;
import com.Dankook.FarmToYou.data.entity.GptTextCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.http.HttpResponse;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
Http 요청을 보내야 함.
 */

@Service
public class GptService {
        @Autowired
        Environment env;
        //헤더 만들어주는 역할
        @Autowired
        private MakeHttpService httpHeaders;
        @Autowired
        private MongoTemplate mongoTemplate;

        private RestTemplate restTemplate;
        private HttpHeaders headers;
        //텍스트
        public ResponseEntity<Object> textGenerate(TextReceive textReceive){
                restTemplate = new RestTemplate();
                Map<String,Object> body;
                TextResponse returnResponse = new TextResponse();
                LocalDateTime dateTime = LocalDateTime.now().plusHours(9L);

                headers = httpHeaders.makeHeaderRequest();
                body = httpHeaders.makeRequestBodyService(textReceive.getText(),"gpt-4o");

                HttpEntity<Map<String,Object>> requestEntity = new HttpEntity<>(body, headers);
                ResponseEntity<Map> response = restTemplate.exchange(env.getProperty("gpt.api.text.generation"), HttpMethod.POST,requestEntity,Map.class);

                List<Map<String,Object>> outputList = (List<Map<String, Object>>)response.getBody().get("output");
                List<Map<String,Object>> contentList = (List<Map<String, Object>>) outputList.get(0).get("content");

                returnResponse.setText(contentList.get(0).get("text").toString());

                GptTextCollection gptTextCollection = GptTextCollection.builder()
                        .createdAt(dateTime)
                        .text(textReceive.getText())
                        .response(returnResponse.getText())
                        .build();


                mongoTemplate.insert(gptTextCollection);
                return new ResponseEntity<>(returnResponse.getText(), HttpStatus.OK);

        }

        public ResponseEntity<List<String>> imageGenerate(TextReceive textReceive){
                restTemplate = new RestTemplate();
                Map<String, Object> body;
                LocalDateTime dateTime = LocalDateTime.now().plusHours(9L);

                headers = httpHeaders.makeHeaderRequest();
                body = httpHeaders.makeTextToImageRequestBodyService(textReceive.getText(),"dall-e-3");

                HttpEntity<Map<String,Object>> requestEntity = new HttpEntity<>(body,headers);
                ResponseEntity<Map> response = restTemplate.exchange(env.getProperty("gpt.api.text.to.image"),HttpMethod.POST,requestEntity,Map.class);

                List<Map<String,String>> outputList = (List<Map<String,String>>)response.getBody().get("data");
                List<String> url = new ArrayList<>();
                for(int i = 0; i < outputList.size(); i++){
                        url.add(outputList.get(i).get("url"));
                }

                GptImageCollection gptImageCollection = GptImageCollection.builder()
                        .createdAt(dateTime)
                        .text(textReceive.getText())
                        .url(url)
                        .build();

                mongoTemplate.insert(gptImageCollection);

                return new ResponseEntity<>(url,HttpStatus.OK);
        }

}
