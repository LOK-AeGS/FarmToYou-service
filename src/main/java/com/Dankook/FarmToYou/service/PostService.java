package com.Dankook.FarmToYou.service;


import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Dankook.FarmToYou.data.dto.response.GetAllPost;
import com.Dankook.FarmToYou.data.dto.response.GetAllSeed;
import com.Dankook.FarmToYou.data.entity.FarmerEntity;
import com.Dankook.FarmToYou.data.entity.PostEntity;
import com.Dankook.FarmToYou.data.entity.SeedEntity;

@Service
public class PostService {

    @Autowired
    MongoTemplate mongoTemplate;

    //체험형 부스 목록 중 최신순으로 10개 받아오기
    public ResponseEntity<List<GetAllPost>> getAllPost(){
        List<GetAllPost> getAllPosts = new ArrayList<>();
        Query query = new Query().limit(10);
        List<PostEntity> allPosts = mongoTemplate.find(query,PostEntity.class,"Post");


        for(PostEntity post : allPosts){
            GetAllPost getAllPost = new GetAllPost();
            getAllPost.setContent(post.getContent());
            getAllPost.setTitle(post.getTitle());
            getAllPost.setStartTime(post.getStartTimeEndTime().get(0));
            getAllPost.setStartTime(post.getStartTimeEndTime().get(1));
            getAllPost.setImageUrl(post.getImageUrl());

            FarmerEntity farmer = mongoTemplate.findOne(Query.query(Criteria.where("id").is(new ObjectId(post.getFarmerId()))), FarmerEntity.class);

            getAllPost.setFarmerName(farmer.getName());

            getAllPosts.add(getAllPost);

        }


        return new ResponseEntity<>(getAllPosts,HttpStatus.OK);
    }


    //종자 판매 목록 중 최신순으로 10개 받아오기
    //List<ImageCollection> imageFindResult = mongoTemplate.findAll(ImageCollection.class, "image");
    public ResponseEntity<List<GetAllSeed>> getAllSeed(){
        List<GetAllSeed> seedList = new ArrayList<>();
        Query query = new Query().limit(10);


        List<SeedEntity> allSeed = mongoTemplate.find(query, SeedEntity.class, "Seed");
        for(SeedEntity seed:allSeed){
            GetAllSeed getAllSeed = new GetAllSeed();
            getAllSeed.setNumberOfSeed(String.valueOf(seed.getSeedId()));
            getAllSeed.setCategory(seed.getSeedCategory());
            // SeedDesc로부터 way/caution 추출
            if (seed.getSeedDesc() != null) {
                getAllSeed.setWay(seed.getSeedDesc().getTitle());
                getAllSeed.setCaution(seed.getSeedDesc().getContent());
            } else {
                getAllSeed.setWay("");
                getAllSeed.setCaution("");
            }
            getAllSeed.setPrice(seed.getSeedPrice());
            getAllSeed.setName(seed.getSeedName());
            seedList.add(getAllSeed);
        }


        return new ResponseEntity<>(seedList,HttpStatus.OK);
    }

    public ResponseEntity<Object> getLatestSeeds() {
        // 최신순(내림차순)으로 10개 SeedEntity 조회
        List<SeedEntity> seeds = mongoTemplate.find(
            new Query().with(Sort.by(Sort.Direction.DESC, "_id")).limit(10),
            SeedEntity.class,
            "Seed"
        );
        return new ResponseEntity<>(seeds, HttpStatus.OK);
    }

    // 판매 상세페이지
    public ResponseEntity<Object> getSeedDetail(String seedId) {
        SeedEntity seed = mongoTemplate.findById(seedId, SeedEntity.class, "Seed");
        if (seed == null) {
            return new ResponseEntity<>("Seed not found", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(seed, HttpStatus.OK);
    }

    // 종자 검색
    public ResponseEntity<Object> searchSeeds(String query) {
        List<SeedEntity> seeds = mongoTemplate.find(
            Query.query(Criteria.where("name").regex(query, "i")),
            SeedEntity.class,
            "Seed"
        );
        return new ResponseEntity<>(seeds, HttpStatus.OK);
    }

    // 체험형 부스 상세페이지
    public ResponseEntity<Object> getExperienceDetail(String expId) {
        PostEntity post = mongoTemplate.findById(expId, PostEntity.class, "Post");
        if (post == null) {
            return new ResponseEntity<>("Experience not found", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(post, HttpStatus.OK);
    }

    // 체험형 부스 검색
    public ResponseEntity<Object> searchExperiences(String query) {
        List<PostEntity> posts = mongoTemplate.find(
            Query.query(Criteria.where("title").regex(query, "i")),
            PostEntity.class,
            "Post"
        );
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    public ResponseEntity<Object> getLatestExperiences() {
        List<PostEntity> posts = mongoTemplate.find(
            new Query().with(Sort.by(Sort.Direction.DESC, "_id")).limit(10),
            PostEntity.class,
            "Post"
        );
        if (posts.isEmpty()) {
            return new ResponseEntity<>("Experience not found", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

}
