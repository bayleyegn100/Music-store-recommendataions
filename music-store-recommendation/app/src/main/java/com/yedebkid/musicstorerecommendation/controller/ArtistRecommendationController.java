package com.yedebkid.musicstorerecommendation.controller;

import com.yedebkid.musicstorerecommendation.model.ArtistRecommendation;
import com.yedebkid.musicstorerecommendation.repository.ArtistRecommendationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class ArtistRecommendationController {
    @Autowired
    ArtistRecommendationRepository artistRecommendationRepository;

    @GetMapping("/artistRecommendation")
    public List<ArtistRecommendation> getAllArtistRecommendations() {
        return artistRecommendationRepository.findAll();
    }

    @GetMapping("/artistRecommendation/{id}")
    public ArtistRecommendation getArtistRecommendationById(@PathVariable Long id) {
        Optional<ArtistRecommendation> returnVal = artistRecommendationRepository.findById(id);
        if (returnVal.isPresent()) {
            return returnVal.get();
        } else {
            return null;
        }
    }

    @PostMapping("/artistRecommendation")
    @ResponseStatus(HttpStatus.CREATED)
    public ArtistRecommendation addArtistRecommendation(@RequestBody ArtistRecommendation artistRecommendation) {
        return artistRecommendationRepository.save(artistRecommendation);
    }

    @PutMapping("/artistRecommendation/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateArtistRecommendation(@PathVariable Long id, @RequestBody ArtistRecommendation artistRecommendation) {
        artistRecommendationRepository.save(artistRecommendation);
    }

    @DeleteMapping("/artistRecommendation/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteArtistById(@PathVariable Long id) {
        artistRecommendationRepository.deleteById(id);
    }

}
