package com.Dankook.FarmToYou.controller;

import com.Dankook.FarmToYou.data.dto.request.TextReceive;
import com.Dankook.FarmToYou.service.GptService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpResponse;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/gpt")
public class GptController {

    @Autowired
    GptService gptService;

    @GetMapping("/test")
    public ResponseEntity<Object> test(){
        return ResponseEntity.ok("hello");
    }

    @PostMapping("/text")
    public ResponseEntity<Object> text(@RequestBody TextReceive textReceive){
        return gptService.textGenerate(textReceive);
    }

    @PostMapping("/image")
    public ResponseEntity<List<String>> image(@RequestBody TextReceive textReceive){
        return gptService.imageGenerate(textReceive);
    }

}
