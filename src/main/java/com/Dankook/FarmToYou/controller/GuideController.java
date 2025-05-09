package com.Dankook.FarmToYou.controller;

import com.Dankook.FarmToYou.data.request.GrowGuideRequest;
import com.Dankook.FarmToYou.service.GrowGuideService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/guide")
public class GuideController {
    @Autowired
    GrowGuideService growGuideService;

    @PostMapping("/grow")
    public ResponseEntity<Object> makeGrowGuid(@RequestBody GrowGuideRequest growGuideRequest){

        return growGuideService.makeGrowGuide(growGuideRequest.getPersonId(),growGuideRequest.getSeedNumber());
    }
}
