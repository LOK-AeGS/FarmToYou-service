package com.Dankook.FarmToYou.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Dankook.FarmToYou.data.dto.request.UserProfileEditRequest;
import com.Dankook.FarmToYou.data.dto.request.UserRequest;
import com.Dankook.FarmToYou.data.dto.response.UserProfileResponse;
import com.Dankook.FarmToYou.service.GeneralUserService;

@RestController
@RequestMapping("/user")
public class GeneralUserController {
    @Autowired
    private GeneralUserService generalUserService;

    @PostMapping("/profile")
    public ResponseEntity<UserProfileResponse> userProfile(@RequestBody UserRequest request) {
        return generalUserService.userProfile(request);
    }

    @PostMapping("/purchased")
    public ResponseEntity<Object> userPurchased(@RequestBody UserRequest request) {
        return generalUserService.userPurchased(request);
    }

    @PostMapping("/participants")
    public ResponseEntity<Object> userParticipants(@RequestBody UserRequest request) {
        return generalUserService.userParticipants(request);
    }

    @PostMapping("/experiments")
    public ResponseEntity<Object> getUserExperiments(@RequestBody UserRequest request) {
        return generalUserService.getUserExperiments(request);
    }

    @GetMapping("/experiment/detail/{expId}")
    public ResponseEntity<Object> getUserExperimentDetail(@PathVariable String expId) {
        return generalUserService.getUserExperimentDetail(expId);
    }
    @GetMapping("/experiment/list/{expId}")
    public ResponseEntity<Object> getList(@PathVariable String expId) {
        return generalUserService.getList(expId);
    }

    @PostMapping("/profile/edit")
    public ResponseEntity<Object> getGeneralProfileForEdit(@RequestBody UserRequest request) {
        return generalUserService.getGeneralProfileForEdit(request);
    }

    @PostMapping("/profile/update/{userId}")
    public ResponseEntity<Object> updateGeneralProfile(@PathVariable String userId, @RequestBody UserProfileEditRequest request) {
        return generalUserService.updateGeneralProfile(userId, request);
    }
} 