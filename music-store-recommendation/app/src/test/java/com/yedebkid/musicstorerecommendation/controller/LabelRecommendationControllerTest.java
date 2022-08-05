package com.yedebkid.musicstorerecommendation.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yedebkid.musicstorerecommendation.model.LabelRecommendation;
import com.yedebkid.musicstorerecommendation.repository.LabelRecommendationRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(LabelRecommendationController.class)
public class LabelRecommendationControllerTest  {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private LabelRecommendationRepository albumRepo;

    private LabelRecommendation recommendedLabel;
    private ObjectMapper mapper = new ObjectMapper();
    private String allLabelRecommendationJson;
    private String recommendedLabelJson;
    private List<LabelRecommendation> recommendationList = new ArrayList<>();

    @Before
    public void setup() throws Exception {
        this.albumRepo=mock(LabelRecommendationRepository.class);
        recommendedLabel = new LabelRecommendation();
        recommendedLabel.setId(19L);
        recommendedLabel.setLabelId(3L);
        recommendedLabel.setLiked(true);
        recommendedLabel.setUserId(123L);

        LabelRecommendation recommendedLabel1 = new LabelRecommendation();
        recommendedLabel1.setLabelId(3L);
        recommendedLabel1.setLiked(true);
        recommendedLabel1.setUserId(123L);

        List<LabelRecommendation> recommendedList = Arrays.asList(recommendedLabel);
        Optional<LabelRecommendation> findByIdResult = Optional.of(recommendedLabel);

        doReturn(recommendedList).when(albumRepo).findAll();
        doReturn(recommendedLabel).when(albumRepo).save(recommendedLabel1);
        doReturn(findByIdResult).when(albumRepo).findById(19L);

    }

    @Test
    public void getLabelRecommendationByIdShouldReturnARecommendedLabel() throws Exception {
        Optional<LabelRecommendation> actualRecommendedLabel = albumRepo.findById(19L);
        LabelRecommendation expectedRecommendedLabel = recommendedLabel;
        assertEquals(expectedRecommendedLabel, actualRecommendedLabel.get());

    }

    @Test
    public void getLabelRecommendationByIdStatusShouldReturnOkForNonExistingId() throws Exception {

        doReturn(Optional.empty()).when(albumRepo).findById(21L);

        mockMvc.perform(
                        get("/labelRecommendation/21"))
                .andExpect(status().isOk()
                );

    }

    @Test
    public void updateLabelRecommendationByIdsShouldUpdateRecommendationAndReturn200StatusCode() throws Exception {
        recommendedLabel = new LabelRecommendation();
        recommendedLabel.setId(19L);
        recommendedLabel.setLabelId(3L);
        recommendedLabel.setLiked(true);
        recommendedLabel.setUserId(123L);

        recommendedLabelJson = mapper.writeValueAsString(recommendedLabel);
        mockMvc.perform(
                put("/labelRecommendation")
                        .content(recommendedLabelJson)
                        .contentType(MediaType.APPLICATION_JSON)
        );

    }

    @Test
    public void deleteLabelRecommendationByShouldDeleteARecommendationAndReturn200StatusCode() throws Exception {
        mockMvc.perform(delete("/labelRecommendation/11")).andExpect(status().isNoContent());
    }

    @Test
    public void getAllLabelRecommendationShouldReturnAListAnd200() throws Exception {

        List<LabelRecommendation> actualRecommendedLabel = albumRepo.findAll();
        assertEquals(1, actualRecommendedLabel.size());

    }

    @Test
    public void addLabelRecommendationShouldReturnNewRecommendationAnd201() throws Exception {

        LabelRecommendation expectedRecommendationResult = new LabelRecommendation();
        expectedRecommendationResult.setLabelId(3L);
        expectedRecommendationResult.setLiked(true);
        expectedRecommendationResult.setUserId(123L);

        LabelRecommendation recommendedLabelWithId = new LabelRecommendation();
        recommendedLabelWithId.setId(19L);
        recommendedLabelWithId.setLabelId(3L);
        recommendedLabelWithId.setLiked(true);
        recommendedLabelWithId.setUserId(123L);

        LabelRecommendation actualRecommendedLabel = albumRepo.save(expectedRecommendationResult);
        assertEquals(recommendedLabelWithId, actualRecommendedLabel);
    }

}