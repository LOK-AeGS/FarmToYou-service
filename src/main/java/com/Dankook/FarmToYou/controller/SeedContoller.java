package com.Dankook.FarmToYou.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Dankook.FarmToYou.data.entity.PostEntity;
import com.Dankook.FarmToYou.service.PostService;

@RestController
@RequestMapping("/seed")
public class SeedContoller {
    @Autowired
    private PostService postService;

    //종자 등록
    @PostMapping("/information")
    public ResponseEntity<Object> seedPost(PostEntity post) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/mainpage")
    public ResponseEntity<Object> getLatestSeeds() {
        return postService.getLatestSeeds();
    }

    @GetMapping("/product/{seed_id}")
    public ResponseEntity<Object> getSeedDetail(@PathVariable("seed_id") String seedId) {
        return postService.getSeedDetail(seedId);
    }

    @GetMapping("/search/{query_string}")
    public ResponseEntity<Object> searchSeeds(@PathVariable("query_string") String query) {
        return postService.searchSeeds(query);
    }

    @GetMapping("/experience/{exp_id}")
    public ResponseEntity<Object> getExperienceDetail(@PathVariable("exp_id") String expId) {
        return postService.getExperienceDetail(expId);
    }

    @GetMapping("/search/experience/{query_string}")
    public ResponseEntity<Object> searchExperiences(@PathVariable("query_string") String query) {
        return postService.searchExperiences(query);
    }

    @GetMapping("/experience/mainpage")
    public ResponseEntity<Object> getExperienceMainpage() {
        return postService.getLatestExperiences();
    }

    @GetMapping("/prev/detail/{seedId}")
    public ResponseEntity<Object> getSeedPrevDetail(@PathVariable("seedId") String seedId) {
        return postService.getSeedDetail(seedId);
    }
}
