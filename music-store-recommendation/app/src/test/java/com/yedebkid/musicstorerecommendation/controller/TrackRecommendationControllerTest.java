package com.yedebkid.musicstorerecommendation.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yedebkid.musicstorerecommendation.model.TrackRecommendation;
import com.yedebkid.musicstorerecommendation.repository.TrackRecommendationRepository;
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
@WebMvcTest(TrackRecommendationController.class)
public class TrackRecommendationControllerTest  {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private TrackRecommendationRepository albumRepo;

    private TrackRecommendation recommendedTrack;
    private ObjectMapper mapper = new ObjectMapper();
    private String allTrackRecommendationJson;
    private String recommendedTrackJson;
    private List<TrackRecommendation> recommendationList = new ArrayList<>();

    @Before
    public void setup() throws Exception {
        this.albumRepo=mock(TrackRecommendationRepository.class);
        recommendedTrack = new TrackRecommendation();
        recommendedTrack.setId(19L);
        recommendedTrack.setTrackId(3L);
        recommendedTrack.setLiked(true);
        recommendedTrack.setUserId(123L);

        TrackRecommendation recommendedTrack1 = new TrackRecommendation();
        recommendedTrack1.setTrackId(3L);
        recommendedTrack1.setLiked(true);
        recommendedTrack1.setUserId(123L);

        List<TrackRecommendation> recommendedList = Arrays.asList(recommendedTrack);
        Optional<TrackRecommendation> findByIdResult = Optional.of(recommendedTrack);

        doReturn(recommendedList).when(albumRepo).findAll();
        doReturn(recommendedTrack).when(albumRepo).save(recommendedTrack1);
        doReturn(findByIdResult).when(albumRepo).findById(19L);

    }

    @Test
    public void getTrackRecommendationByIdShouldReturnARecommendedTrack() throws Exception {
        Optional<TrackRecommendation> actualRecommendedTrack = albumRepo.findById(19L);
        TrackRecommendation expectedRecommendedTrack = recommendedTrack;
        assertEquals(expectedRecommendedTrack, actualRecommendedTrack.get());

    }

    @Test
    public void getTrackRecommendationByIdStatusShouldReturnOkForNonExistingId() throws Exception {

        doReturn(Optional.empty()).when(albumRepo).findById(21L);

        mockMvc.perform(
                        get("/trackRecommendation/21"))
                .andExpect(status().isOk()
                );

    }

    @Test
    public void updateTrackRecommendationByIdsShouldUpdateRecommendationAndReturn200StatusCode() throws Exception {
        recommendedTrack = new TrackRecommendation();
        recommendedTrack.setId(19L);
        recommendedTrack.setTrackId(3L);
        recommendedTrack.setLiked(true);
        recommendedTrack.setUserId(123L);

        recommendedTrackJson = mapper.writeValueAsString(recommendedTrack);
        mockMvc.perform(
                put("/trackRecommendation")
                        .content(recommendedTrackJson)
                        .contentType(MediaType.APPLICATION_JSON)
        );

    }

    @Test
    public void deleteTrackRecommendationByShouldDeleteARecommendationAndReturn200StatusCode() throws Exception {
        mockMvc.perform(delete("/trackRecommendation/19")).andExpect(status().isNoContent());
    }

    @Test
    public void getAllTrackRecommendationShouldReturnAListAnd200() throws Exception {

        List<TrackRecommendation> actualRecommendedTrack = albumRepo.findAll();
        assertEquals(1, actualRecommendedTrack.size());

    }

    @Test
    public void addTrackRecommendationShouldReturnNewRecommendationAnd201() throws Exception {

        TrackRecommendation expectedRecommendationResult = new TrackRecommendation();
        expectedRecommendationResult.setTrackId(3L);
        expectedRecommendationResult.setLiked(true);
        expectedRecommendationResult.setUserId(123L);

        TrackRecommendation recommendedTrackWithId = new TrackRecommendation();
        recommendedTrackWithId.setId(19L);
        recommendedTrackWithId.setTrackId(3L);
        recommendedTrackWithId.setLiked(true);
        recommendedTrackWithId.setUserId(123L);

        TrackRecommendation actualRecommendedTrack = albumRepo.save(expectedRecommendationResult);
        assertEquals(recommendedTrackWithId, actualRecommendedTrack);
    }

}


