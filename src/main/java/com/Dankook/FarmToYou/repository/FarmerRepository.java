package com.Dankook.FarmToYou.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.Dankook.FarmToYou.data.entity.FarmerEntity;

@Repository
public interface FarmerRepository extends MongoRepository<FarmerEntity, String> {
} 