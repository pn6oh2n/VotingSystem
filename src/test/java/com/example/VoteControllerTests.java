package com.example;

import com.example.model.Vote;
import org.junit.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import static com.example.Util.API_V1;
import static com.example.Util.getTodayHourOfDay;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class VoteControllerTests extends BaseTests {

    private static final String URL = API_V1 + "/votes";

    @Test
    public void testGetResults() throws Exception {
        getCreatedVote(true);
        getCreatedVote(true);
        mockMvc.perform(get(URL)
                .header(HttpHeaders.AUTHORIZATION, HTTP_AUTH_ADMIN))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON)).andDo(print());
    }

    @Test
    public void testAddVote() throws Exception {
        Vote vote = getCreatedVote(true);
        mockMvc.perform(post(URL)
                .header(HttpHeaders.AUTHORIZATION, HTTP_AUTH_ADMIN)
                .param("idRestaurant", vote.getRestaurant().getId().toString()))
                .andExpect(status().isOk());
    }

    @Test
    public void testAddVoteAgain() throws Exception {
        Vote vote = getCreatedVote(true);
        mockMvc.perform(post(URL)
                .header(HttpHeaders.AUTHORIZATION, HTTP_AUTH_ADMIN)
                .param("idRestaurant", vote.getRestaurant().getId().toString()))
                .andExpect(status().isOk());
        if (getTodayHourOfDay() >= 11) {
            mockMvc.perform(post(URL)
                    .header(HttpHeaders.AUTHORIZATION, HTTP_AUTH_ADMIN)
                    .param("idRestaurant", vote.getRestaurant().getId().toString()))
                    .andExpect(status().isAlreadyReported());
        } else {
            mockMvc.perform(post(URL)
                    .header(HttpHeaders.AUTHORIZATION, HTTP_AUTH_ADMIN)
                    .param("idRestaurant", vote.getRestaurant().getId().toString()))
                    .andExpect(status().isOk());
        }
    }
}