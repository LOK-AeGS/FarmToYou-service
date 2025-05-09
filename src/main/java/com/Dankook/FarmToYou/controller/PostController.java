package com.Dankook.FarmToYou.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.Dankook.FarmToYou.data.dto.response.GetAllPost;
import com.Dankook.FarmToYou.data.dto.response.GetAllSeed;
import com.Dankook.FarmToYou.data.entity.PostEntity;
import com.Dankook.FarmToYou.service.PostService;

/*
예약 게시물, 종자 판매 게시물
 */

@RequestMapping("/post")
@Controller
public class PostController {
    @Autowired
    PostService postService;

    //예약 페이지 보여줌
    @GetMapping("/reserve")
    public ResponseEntity<List<GetAllPost>> reservePost(PostEntity post) {

        return postService.getAllPost();

    }
    //종자 판매 페이지 보여줌
    @GetMapping("/sell")
    public ResponseEntity<List<GetAllSeed>> seedSellPost(PostEntity post) {

        return postService.getAllSeed();
    }

}