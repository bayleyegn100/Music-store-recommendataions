package com.yedebkid.musicstorerecommendation.controller;

import com.yedebkid.musicstorerecommendation.model.AlbumRecommendation;
import com.yedebkid.musicstorerecommendation.repository.AlbumRecommendationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class AlbumRecommendationController {
    @Autowired
    AlbumRecommendationRepository albumRecommendationRepository;

    @GetMapping("/albumRecommendation")
    public List<AlbumRecommendation> getAllAlbumRecommendation() {
        return albumRecommendationRepository.findAll();
    }

    @GetMapping(value = "/albumRecommendation/{id}")
    public AlbumRecommendation getAlbumRecommendationById(@PathVariable Long id) {
        Optional<AlbumRecommendation> returnVal = albumRecommendationRepository.findById(id);
        if (returnVal.isPresent()) {
            return returnVal.get();
        } else {
            return null;
        }
    }

    @PostMapping("/albumRecommendation")
    @ResponseStatus(HttpStatus.CREATED)
    public AlbumRecommendation addAlbumRecommendation(@RequestBody AlbumRecommendation albumRecommendation) {
        return albumRecommendationRepository.save(albumRecommendation);
    }

    @PutMapping("/albumRecommendation/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateAlbumRecommendationById(@PathVariable Long id, @RequestBody AlbumRecommendation albumRecommendation) {
        albumRecommendationRepository.save(albumRecommendation);
    }

    @DeleteMapping("/albumRecommendation/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAlbumRecommendationById(@PathVariable Long id) {
        albumRecommendationRepository.deleteById(id);
    }

}
