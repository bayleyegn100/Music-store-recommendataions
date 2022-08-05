package com.yedebkid.musicstorerecommendation.repository;

import com.yedebkid.musicstorerecommendation.model.LabelRecommendation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LabelRecommendationRepository extends JpaRepository<LabelRecommendation, Long> {
}

