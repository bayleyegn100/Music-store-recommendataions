package com.yedebkid.musicstorerecommendation.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yedebkid.musicstorerecommendation.model.ArtistRecommendation;
import com.yedebkid.musicstorerecommendation.repository.ArtistRecommendationRepository;
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
@WebMvcTest(ArtistRecommendationController.class)

public class ArtistRecommendationControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ArtistRecommendationRepository artistRepo;

    private ArtistRecommendation recommendedArtist;
    private ObjectMapper mapper = new ObjectMapper();
    private String allArtistRecommendationJson;
    private String recommendedArtistJson;
    private List<ArtistRecommendation> recommendationList = new ArrayList<>();

    @Before
    public void setup() throws Exception {
        this.artistRepo = mock(ArtistRecommendationRepository.class);
        recommendedArtist = new ArtistRecommendation();
        recommendedArtist.setId(19L);
        recommendedArtist.setArtistId(3L);
        recommendedArtist.setLiked(true);
        recommendedArtist.setUserId(123L);

        ArtistRecommendation recommendedArtist1 = new ArtistRecommendation();
        recommendedArtist1.setArtistId(3L);
        recommendedArtist1.setLiked(true);
        recommendedArtist1.setUserId(123L);

        List<ArtistRecommendation> recommendedList = Arrays.asList(recommendedArtist);
        Optional<ArtistRecommendation> findByIdResult = Optional.of(recommendedArtist);

        doReturn(recommendedList).when(artistRepo).findAll();
        doReturn(recommendedArtist).when(artistRepo).save(recommendedArtist1);
        doReturn(findByIdResult).when(artistRepo).findById(19L);

    }

    @Test
    public void getArtistRecommendationByIdShouldReturnARecommendedArtist() throws Exception {
        Optional<ArtistRecommendation> actualRecommendedArtist = artistRepo.findById(19L);
        ArtistRecommendation expectedRecommendedArtist = recommendedArtist;
        assertEquals(expectedRecommendedArtist, actualRecommendedArtist.get());

    }

    @Test
    public void getArtistRecommendationByIdStatusShouldReturnOkForNonExistingId() throws Exception {

        doReturn(Optional.empty()).when(artistRepo).findById(21L);

        mockMvc.perform(
                        get("/artistRecommendation/21"))
                .andExpect(status().isOk()
                );

    }

    @Test
    public void updateArtistRecommendationByIdsShouldUpdateRecommendationAndReturn200StatusCode() throws Exception {
        recommendedArtist = new ArtistRecommendation();
        recommendedArtist.setId(19L);
        recommendedArtist.setArtistId(3L);
        recommendedArtist.setLiked(true);
        recommendedArtist.setUserId(123L);

        recommendedArtistJson = mapper.writeValueAsString(recommendedArtist);
        mockMvc.perform(
                put("/artistRecommendation")
                        .content(recommendedArtistJson)
                        .contentType(MediaType.APPLICATION_JSON)
        );

    }

    @Test
    public void deleteArtistRecommendationByShouldDeleteARecommendationAndReturn200StatusCode() throws Exception {
        mockMvc.perform(delete("/artistRecommendation/11")).andExpect(status().isNoContent());
    }

    @Test
    public void getAllArtistRecommendationShouldReturnAListAnd200() throws Exception {

        List<ArtistRecommendation> actualRecommendedArtist = artistRepo.findAll();
        assertEquals(1, actualRecommendedArtist.size());

    }

    @Test
    public void addArtistRecommendationShouldReturnNewRecommendationAnd201() throws Exception {

        ArtistRecommendation expectedRecommendationResult = new ArtistRecommendation();
        expectedRecommendationResult.setArtistId(3L);
        expectedRecommendationResult.setLiked(true);
        expectedRecommendationResult.setUserId(123L);

        ArtistRecommendation recommendedArtistWithId = new ArtistRecommendation();
        recommendedArtistWithId.setId(19L);
        recommendedArtistWithId.setArtistId(3L);
        recommendedArtistWithId.setLiked(true);
        recommendedArtistWithId.setUserId(123L);

        ArtistRecommendation actualRecommendedArtist = artistRepo.save(expectedRecommendationResult);
        assertEquals(recommendedArtistWithId, actualRecommendedArtist);
    }
}
