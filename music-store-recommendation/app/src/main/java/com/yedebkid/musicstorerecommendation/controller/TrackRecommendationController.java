package com.yedebkid.musicstorerecommendation.controller;

import com.yedebkid.musicstorerecommendation.model.TrackRecommendation;
import com.yedebkid.musicstorerecommendation.repository.TrackRecommendationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class TrackRecommendationController {
    @Autowired
    TrackRecommendationRepository trackRecommendationRepository;

    @GetMapping("/tracksRecommendation")
    public List<TrackRecommendation> getAllTrackRecommendations() {
        return trackRecommendationRepository.findAll();
    }

    @GetMapping("/trackRecommendation/{id}")
    public TrackRecommendation getTrackRecommendationById(@PathVariable Long id) {
        Optional<TrackRecommendation> returnVal = trackRecommendationRepository.findById(id);
        if (returnVal.isPresent()) {
            return returnVal.get();
        } else {
            return null;
        }
    }

    @PostMapping("/trackRecommendation")
    @ResponseStatus(HttpStatus.CREATED)
    public TrackRecommendation addTrackRecommendation(@RequestBody TrackRecommendation trackRecommendation) {
        return trackRecommendationRepository.save(trackRecommendation);
    }

    @PutMapping("/trackRecommendation/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateTrackRecommendationById(@PathVariable Long id, @RequestBody TrackRecommendation trackRecommendation) {
        trackRecommendationRepository.save(trackRecommendation);
    }

    @DeleteMapping("/trackRecommendation/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTrackRecommendationById(@PathVariable Long id) {
        trackRecommendationRepository.deleteById(id);
    }

}
