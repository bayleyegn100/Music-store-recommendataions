package com.yedebkid.musicstorerecommendation.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yedebkid.musicstorerecommendation.model.AlbumRecommendation;
import com.yedebkid.musicstorerecommendation.repository.AlbumRecommendationRepository;
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
@WebMvcTest(AlbumRecommendationController.class)
public class AlbumRecommendationControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private AlbumRecommendationRepository albumRepo;

    private AlbumRecommendation recommendedAlbum;
    private ObjectMapper mapper = new ObjectMapper();
    private String allAlbumRecommendationJson;
    private String recommendedAlbumJson;
    private List<AlbumRecommendation> recommendationList = new ArrayList<>();

    @Before
    public void setup() throws Exception {
        this.albumRepo=mock(AlbumRecommendationRepository.class);
        recommendedAlbum = new AlbumRecommendation();
        recommendedAlbum.setId(19L);
        recommendedAlbum.setAlbumId(3L);
        recommendedAlbum.setLiked(true);
        recommendedAlbum.setUserId(123L);

        AlbumRecommendation recommendedAlbum1 = new AlbumRecommendation();
        recommendedAlbum1.setAlbumId(3L);
        recommendedAlbum1.setLiked(true);
        recommendedAlbum1.setUserId(123L);

        List<AlbumRecommendation> recommendedList = Arrays.asList(recommendedAlbum);
        Optional<AlbumRecommendation> findByIdResult = Optional.of(recommendedAlbum);

        doReturn(recommendedList).when(albumRepo).findAll();
        doReturn(recommendedAlbum).when(albumRepo).save(recommendedAlbum1);
        doReturn(findByIdResult).when(albumRepo).findById(19L);

    }

    @Test
    public void getAlbumRecommendationByIdShouldReturnARecommendedAlbum() throws Exception {
        Optional<AlbumRecommendation> actualRecommendedAlbum = albumRepo.findById(19L);
        AlbumRecommendation expectedRecommendedAlbum = recommendedAlbum;
        assertEquals(expectedRecommendedAlbum, actualRecommendedAlbum.get());

    }

    @Test
    public void getAlbumRecommendationByIdStatusShouldReturnOkForNonExistingId() throws Exception {

        doReturn(Optional.empty()).when(albumRepo).findById(21L);

        mockMvc.perform(
                        get("/albumRecommendation/21"))
                .andExpect(status().isOk()
                );

    }

    @Test
    public void updateAlbumRecommendationByIdsShouldUpdateRecommendationAndReturn200StatusCode() throws Exception {
        recommendedAlbum = new AlbumRecommendation();
        recommendedAlbum.setId(19L);
        recommendedAlbum.setAlbumId(3L);
        recommendedAlbum.setLiked(true);
        recommendedAlbum.setUserId(123L);

        recommendedAlbumJson = mapper.writeValueAsString(recommendedAlbum);
        mockMvc.perform(
                        put("/albumRecommendation")
                                .content(recommendedAlbumJson)
                                .contentType(MediaType.APPLICATION_JSON)
                );

    }

    @Test
    public void deleteAlbumRecommendationByShouldDeleteARecommendationAndReturn200StatusCode() throws Exception {
        mockMvc.perform(delete("/albumRecommendation/11")).andExpect(status().isNoContent());
    }

    @Test
    public void getAllAlbumRecommendationShouldReturnAListAnd200() throws Exception {

        List<AlbumRecommendation> actualRecommendedAlbum = albumRepo.findAll();
        assertEquals(1, actualRecommendedAlbum.size());

    }

    @Test
    public void addAlbumRecommendationShouldReturnNewRecommendationAnd201() throws Exception {

        AlbumRecommendation expectedRecommendationResult = new AlbumRecommendation();
        expectedRecommendationResult.setAlbumId(3L);
        expectedRecommendationResult.setLiked(true);
        expectedRecommendationResult.setUserId(123L);

        AlbumRecommendation recommendedAlbumWithId = new AlbumRecommendation();
        recommendedAlbumWithId.setId(19L);
        recommendedAlbumWithId.setAlbumId(3L);
        recommendedAlbumWithId.setLiked(true);
        recommendedAlbumWithId.setUserId(123L);

        AlbumRecommendation actualRecommendedAlbum = albumRepo.save(expectedRecommendationResult);
        assertEquals(recommendedAlbumWithId, actualRecommendedAlbum);
    }

}