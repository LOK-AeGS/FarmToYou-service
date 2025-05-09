package com.Dankook.FarmToYou.controller;

import com.Dankook.FarmToYou.data.dto.request.ImageReceive;
import com.Dankook.FarmToYou.service.ImageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/image")
public class ImageController {
    @Autowired
    ImageService imageService;

    @PostMapping("/upload")
    public ResponseEntity<Object> uploadImage(@ModelAttribute("image")ImageReceive imageReceive) throws IOException {
        return imageService.imageUpload(imageReceive);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Map<String,String>>> getAllImage(){

        return imageService.getAllImage();

    }

}
