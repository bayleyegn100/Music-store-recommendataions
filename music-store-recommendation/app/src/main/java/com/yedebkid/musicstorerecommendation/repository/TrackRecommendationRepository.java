package com.yedebkid.musicstorerecommendation.repository;

import com.yedebkid.musicstorerecommendation.model.TrackRecommendation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrackRecommendationRepository extends JpaRepository<TrackRecommendation, Long> {
}

