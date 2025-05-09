package com.Dankook.FarmToYou.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Dankook.FarmToYou.data.dto.request.FarmerExperimentPutRequest;
import com.Dankook.FarmToYou.data.dto.request.FarmerProfileEditRequest;
import com.Dankook.FarmToYou.data.dto.request.FarmerProfileRequest;
import com.Dankook.FarmToYou.service.FarmerService;

@RestController
@RequestMapping("/farmer")
public class FarmerController {
    @Autowired
    private FarmerService farmerService;

    @PostMapping("/profile")
    public ResponseEntity<Object> getFarmerProfile(@RequestBody FarmerProfileRequest request) {
        return farmerService.getFarmerProfileForEdit(request);
    }

    @PostMapping("/profile/update/{farmerId}")
    public ResponseEntity<Object> updateFarmerProfile(@PathVariable String farmerId, @RequestBody FarmerProfileEditRequest request) {
        return farmerService.updateFarmerProfile(farmerId, request);
    }

    @PostMapping("/experiments")
    public ResponseEntity<Object> getFarmerExperiments(@RequestBody FarmerProfileRequest request) {
        return farmerService.getFarmerExperiments(request);
    }

    @GetMapping("/experiment/detail/{farmerId}")
    public ResponseEntity<Object> getFarmerExperimentDetail(@PathVariable String farmerId) {
        return farmerService.getFarmerResearchDetailByFarmerId(farmerId);
    }

    @GetMapping("/experiment/list/{farmerId}")
    public ResponseEntity<Object> getList(@PathVariable String farmerId) {
        return farmerService. getList(farmerId);
    }

    @PostMapping("/experiment/register")
    public ResponseEntity<Object> putFarmerExperiment(@RequestBody FarmerExperimentPutRequest request) {
        return farmerService.putFarmerExperiment(request);
    }

    @PostMapping("/labs")
    public ResponseEntity<Object> getFarmerLabs(@RequestBody FarmerProfileRequest request) {
        return farmerService.getFarmerLabs(request);
    }

    @GetMapping("/lab/detail/{labexId}")
    public ResponseEntity<Object> getFarmerLabexDetail(@PathVariable String labexId) {
        return farmerService.getFarmerLabexDetail(labexId);
    }

    @GetMapping("/lab/diary/{labexId}")
    public ResponseEntity<Object> getFarmerLabexAddDiary(@PathVariable String labexId) {
        return farmerService.getFarmerLabexAddDiary(labexId);
    }
} 