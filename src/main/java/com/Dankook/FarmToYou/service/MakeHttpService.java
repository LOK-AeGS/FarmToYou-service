package com.Dankook.FarmToYou.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.server.reactive.HttpHandler;
import org.springframework.stereotype.Service;

import org.springframework.http.*;

import java.util.HashMap;
import java.util.Map;

@Service
public class MakeHttpService {
    HttpHeaders httpHeaders;
    @Autowired
    Environment env;

    public HttpHeaders makeHeaderRequest(){
        httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setBearerAuth(env.getProperty("gpt.api.key"));

        return httpHeaders;
    }
    //텍스트 만들기
    public Map<String,Object> makeRequestBodyService(String text, String model){
        Map<String,Object> requestBody = new HashMap<>();
        requestBody.put("model",model);
        requestBody.put("input",text);
        return requestBody;
    }

    public Map<String, Object> makeTextToImageRequestBodyService(String text, String model){
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model",model);
        requestBody.put("prompt",text);
        requestBody.put("n",1);
        requestBody.put("size","1024x1024");
        return requestBody;
    }

}
