package com.Dankook.FarmToYou.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.Dankook.FarmToYou.data.entity.ExperienceEntity;

@Repository
public interface ExperienceRepository extends MongoRepository<ExperienceEntity, String> {
} 