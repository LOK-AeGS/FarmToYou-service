package com.Dankook.FarmToYou.service;

import com.Dankook.FarmToYou.data.dto.request.ImageReceive;
import com.Dankook.FarmToYou.data.dto.response.GetAllImage;
import com.Dankook.FarmToYou.data.entity.ImageCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;

import org.bson.Document;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ImageService {
    @Autowired
    S3Service s3Service;
    //uploadMultipartFile(MultipartFile multipartFile, String keyName)

    @Autowired
    MongoTemplate mongoTemplate;

    public ResponseEntity<Object> imageUpload(ImageReceive imageReceive) throws IOException {
        //이미지 url
        System.out.println(imageReceive.getImageName());
        String fileName = imageReceive.getImageName() ;
        String imageUrl = s3Service.uploadMultipartFile(imageReceive.getMultipartFile(),fileName);

        //Mongo DB 저장하는거
        ImageCollection imageCollection = ImageCollection.builder()
                .imageName(imageReceive.getImageName())
                .imageUrl(imageUrl)
        .build();
        mongoTemplate.insert(imageCollection);

        return new ResponseEntity<>(imageUrl, HttpStatus.OK);
    }

    public ResponseEntity<List<Map<String,String>>> getAllImage(){
        List<Map<String, String>> result = new ArrayList<>();
        GetAllImage getAllImage = new GetAllImage();
        List<ImageCollection> imageFindResult = mongoTemplate.findAll(ImageCollection.class, "image");
        for(ImageCollection imageCollection : imageFindResult) {
            Map<String,String> temp = new HashMap<>();
//            imageCollection.getImageName(),imageCollection.getImageUrl()
            temp.put(imageCollection.getImageName(),imageCollection.getImageUrl());
            result.add(temp);
        }
        return new ResponseEntity<>(result,HttpStatus.OK);
    }
}
