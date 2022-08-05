package com.yedebkid.musicstorerecommendation.repository;

import com.yedebkid.musicstorerecommendation.model.ArtistRecommendation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArtistRecommendationRepository extends JpaRepository<ArtistRecommendation, Long> {
}

