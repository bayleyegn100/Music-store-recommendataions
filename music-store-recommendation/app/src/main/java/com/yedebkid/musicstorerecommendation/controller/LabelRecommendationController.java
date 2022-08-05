package com.yedebkid.musicstorerecommendation.controller;

import com.yedebkid.musicstorerecommendation.model.LabelRecommendation;
import com.yedebkid.musicstorerecommendation.repository.LabelRecommendationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class LabelRecommendationController {
    @Autowired
    LabelRecommendationRepository labelRecommendationRepository;

    @GetMapping("/labelRecommendation")
    public List<LabelRecommendation> getAllLabelRecommendations() {
        return labelRecommendationRepository.findAll();
    }

    @GetMapping("/labelRecommendation/{id}")
    public LabelRecommendation getLabelRecommendationById(@PathVariable Long id) {
        Optional<LabelRecommendation> returnVal = labelRecommendationRepository.findById(id);
        if (returnVal.isPresent()) {
            return returnVal.get();
        } else {
            return null;
        }
    }

    @PostMapping("/labelRecommendation")
    @ResponseStatus(HttpStatus.CREATED)
    public LabelRecommendation addLabelRecommendation(@RequestBody LabelRecommendation labelRecommendation) {
        return labelRecommendationRepository.save(labelRecommendation);
    }

    @PutMapping("/labelRecommendation/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateLabelRecommendationById(@PathVariable Long id, @RequestBody LabelRecommendation labelRecommendation) {
        labelRecommendationRepository.save(labelRecommendation);
    }

    @DeleteMapping("/labelRecommendation/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteLabelRecommendationById(@PathVariable Long id) {
        labelRecommendationRepository.deleteById(id);
    }

}
