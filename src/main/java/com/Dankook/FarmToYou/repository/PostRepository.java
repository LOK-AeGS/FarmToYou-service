package com.Dankook.FarmToYou.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.Dankook.FarmToYou.data.entity.PostEntity;

@Repository
public interface PostRepository extends MongoRepository<PostEntity, String> {
    List<PostEntity> findByFarmerId(String farmerId);
} 