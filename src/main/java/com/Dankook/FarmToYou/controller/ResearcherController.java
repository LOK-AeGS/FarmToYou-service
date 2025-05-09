package com.Dankook.FarmToYou.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Dankook.FarmToYou.data.dto.request.ResearchProfileRequest;
import com.Dankook.FarmToYou.data.entity.SeedEntity;
import com.Dankook.FarmToYou.service.ResearcherService;

@RestController
@RequestMapping("/researcher")
public class ResearcherController {
    @Autowired
    private ResearcherService researcherService;

    @PostMapping("/profile")
    public ResponseEntity<Object> getResearcherProfile(@RequestBody ResearchProfileRequest request) {
        return researcherService.getResearcherProfile(request);
    }

    @PostMapping("/profile/update/{labId}")
    public ResponseEntity<Object> updateResearcherProfile(@PathVariable String labId, @RequestBody ResearchProfileRequest request) {
        return researcherService.updateResearcherProfile(labId, request);
    }

    @PostMapping("/seed/list")
    public ResponseEntity<Object> getResearcherSeedList(@RequestBody ResearchProfileRequest request) {
        return researcherService.getResearcherSeedList(request);
    }

    @GetMapping("/seed/detail/{labId}")
    public ResponseEntity<Object> getResearcherSeedDetail(@PathVariable String labId) {
        return researcherService.getResearcherSeedDetail(labId);
    }

//    getDashboard(String labId)
    @GetMapping("/seed/dashboard/{labId}")
    public ResponseEntity<Object> getDashboard(@PathVariable String labId) {
        return researcherService.getDashboard(labId);
    }
    @GetMapping("/seed/list/{labId}")
    public ResponseEntity<Object> getList(@PathVariable String labId) {
        return researcherService.getList(labId);
    }
    @PostMapping("/seed/update/{seedId}")
    public ResponseEntity<Object> updateResearcherSeed(@PathVariable String seedId, @RequestBody SeedEntity request) {
        return researcherService.updateResearcherSeed(seedId, request);
    }
} 